package com.anycommon.poi.word.impl;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSClient;
import com.anycommon.poi.config.WordConfig;
import com.anycommon.poi.word.Question;
import com.anycommon.poi.word.WordFileService;
import org.apache.poi.ss.formula.functions.T;
import org.docx4j.Docx4J;
import org.docx4j.Docx4jProperties;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Component
public class WordFileServiceImpl implements WordFileService {
    private static final Logger logger = LoggerFactory.getLogger(WordFileServiceImpl.class);

    static {
        String property = System.getProperty("user.dir");
        File word = new File(property + "/poi/word");
        File html = new File(property + "/poi/html");
        if (!word.exists() && word.mkdir()) {
            logger.info("创建单文件夹成功！创建后的文件夹路径为：" + word.getPath() + ",文件夹的上级目录为：" + word.getParent());
        }
        if (!word.exists() && html.mkdir()) {
            logger.info("创建单文件夹成功！创建后的文件夹路径为：" + html.getPath() + ",文件夹的上级目录为：" + html.getParent());
        }
    }

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
     */
    @Override
    public <T, K> List<T> importWord(List<T> resultList, Class<K> clz, InputStream in) throws Exception {
        // word转html
        docx2html(in, htmlPath);
        // 解析html
        return readHTML2Class(resultList, clz);
    }

    /**
     * 解析html
     *
     * @param resultList
     * @param clz
     * @param <T>
     * @param <K>
     */
    private <T, K> List<T> readHTML2Class(List<T> resultList, Class<K> clz) throws IOException {
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
    private void docx2html(InputStream in, String htmlPath) throws Exception {
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
