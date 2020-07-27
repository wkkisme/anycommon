package com.anycommon.poi.word;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSClient;
import com.anycommon.poi.config.WordConfig;
import org.docx4j.Docx4J;
import org.docx4j.Docx4jProperties;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * word文档解析试卷试题工具类
 *
 * @author wangkai
 * @date 2020-07-27 10:44
 */
@Component
public class WordUtils {

    @Resource
    private WordConfig wordConfig;

    /**
     * word文档中图片存放本地位置
     */
    @Value("${platform.poi.wordLocalAddress}")
    private String wordImgUrl;

    /**
     * word文档中图片存放本地位置
     */
    @Value("${platform.poi.htmlLocalAddress}")
    private String htmlPath;


    /**
     * 测试
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("===========执行开始===========");
        long begin = System.currentTimeMillis();
        List<Question> questionList = new ArrayList<>();
//        questionList = importWord(questionList, Question.class, new FileInputStream("null"), htmlPath);
//        questionList.forEach(e -> {
//            System.out.println(e.toString());
//        });
        long end = System.currentTimeMillis();
        System.out.println("===========执行结束，时间：" + (end - begin) + "ms===========");
    }


    /**
     * 导入word文档获取试题列表
     *
     * @param resultList
     * @param clz        clz
     * @param <T>
     * @param <K>
     * @param htmlPath   html存放路径
     */
    public  <T, K> List<T> importWord(List<T> resultList, Class<K> clz, InputStream in, String htmlPath) throws Exception {
        // word转html
        docx2html(in, htmlPath);
        // 解析html
        return readHTML2Class(htmlPath, resultList, clz);
    }

    /**
     * 解析html
     *
     * @param htmlPath
     * @param resultList
     * @param clz
     * @param <T>
     * @param <K>
     */
    private <T, K> List<T> readHTML2Class(String htmlPath, List<T> resultList, Class<K> clz) throws IOException {
        // 属性map
        Map<String, String> propertyMap = new HashMap<>();
        // 实体map列表
        List<Map<String, String>> questionMapList = new ArrayList<>();
        // 当前传入实体的属性名列表
        List<String> fieldString = getFieldString(clz);

        File input = new File(htmlPath);
        Document doc = Jsoup.parse(input, "UTF-8", "");
        // 获取html标签<p>节点
        Elements links = doc.select("p");
        // 当前遍历的属性
        String thisProperName = "";

        for (int i = 0; i < links.size(); i++) {
            Element link = links.get(i);
            // 属性值
            String propertyValue = link.html();
            // 属性名，可能参杂属性值
            String propertyStr = link.text();

            if (propertyValue.contains("<img")) {
                Elements img = link.select("img");
                String src = img.attr("src");

                // 获取图片，上传到阿里云oss
                src = uploadOss(src);
                img.attr("src", src);

                propertyValue = link.html();
            }

            int index = propertyStr.indexOf(":");
            if (index != -1) {
                // 代表有可能为当前传入实体的属性
                String propertyName = propertyStr.substring(0, index);
                if (fieldString.contains(propertyName)) {
                    // 去除“:”后如果对应字段存在于实体属性列表中，代表为对应属性
                    propertyValue = propertyStr.substring(index + 1);
                    thisProperName = propertyName;
                }
            }

            String propertyMapValue = (propertyMap.get(thisProperName) == null ? "" : propertyMap.get(thisProperName));
            // 拼接属性值
            propertyMapValue += propertyValue;
            propertyMap.put(thisProperName, propertyMapValue);

            if (i > 10 && thisProperName.equals("id")) {
                questionMapList.add(propertyMap);
                propertyMap = new LinkedHashMap<>();
            }
        }

        // 解析questionMapList，注入属性值
        questionMapList.forEach(e -> {
            Question question = JSON.parseObject(JSON.toJSONString(e), Question.class);
            resultList.add((T) question);
        });

        return resultList;
    }

    /**
     * 将word文档转化成html
     *
     * @param htmlPath htmlPath
     */
    private  void docx2html(InputStream in, String htmlPath) throws Exception {
        File file = new File(htmlPath);
        if (!file.exists()) {
            try {
                boolean newFile = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        WordprocessingMLPackage load = Docx4J.load(in);
        HTMLSettings htmlSettings = Docx4J.createHTMLSettings();
        htmlSettings.setImageDirPath(wordImgUrl);
        htmlSettings.setImageTargetUri(wordImgUrl);
        htmlSettings.setWmlPackage(load);
        String userCss = "html, body, div, span,font, h1, h2, h3, h4, h5, h6, p, a, img,  ol, ul, li, table, caption, tbody, tfoot, thead, tr, th, td " +
                "{ margin: 10px; padding: 10px; border: 0;}" +
                "body {line-height: 1;} ";

        htmlSettings.setUserCSS(userCss);
        OutputStream os;
        os = new FileOutputStream(htmlPath);
        Docx4jProperties.setProperty("docx4j.Convert.Out.HTML.OutputMethodXML", true);
        Docx4J.toHTML(htmlSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);
    }

    /**
     * 上传图片到阿里云oss
     *
     * @param fileName fileName
     * @return String
     */
    public String uploadOss(String fileName) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 生成上传文件名
        String finalFileName = System.currentTimeMillis() + "" + new SecureRandom().nextInt(0x0400) + suffixName;
        String objectName = sdf.format(new Date()) + "/" + finalFileName;
        File file = new File(fileName);
        OSSClient ossClient = wordConfig.getOssClient();
        String bucketName = wordConfig.getBucketName();
        ossClient.putObject(bucketName, objectName, file);
        // 设置URL过期时间为1小时。
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000 * 100);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        URL url = ossClient.generatePresignedUrl(bucketName, objectName, expiration);
        ossClient.shutdown();
        return url.toString();
    }

    /**
     * 获取类的属性集合
     *
     * @param clazz clazz
     * @return List<Field>
     */
    public static List<Field> getFields(Class<?> clazz) {
        if (clazz == null) {
            return Collections.emptyList();
        }

        Field[] fields = clazz.getDeclaredFields();
        return Arrays.asList(fields);
    }

    /**
     * 获取类里面属性名称
     *
     * @param clazz clazz
     * @return List<String>
     */
    public static List<String> getFieldString(Class<?> clazz) {
        List<Field> list = getFields(clazz);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(Field::getName).collect(Collectors.toList());
    }

}
