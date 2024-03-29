package com.anycommon.poi.word.impl;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSClient;
import com.anycommon.poi.annotation.NoFormat;
import com.anycommon.poi.config.WordConfig;
import com.anycommon.poi.uitl.DocxUtil;
import com.anycommon.poi.uitl.HeroDocxBean;
import com.anycommon.poi.word.Question;
import com.anycommon.poi.word.WordFileService;
import fr.opensagres.poi.xwpf.converter.core.ImageManager;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
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
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class WordFileServiceImpl implements WordFileService {
    private static final Logger logger = LoggerFactory.getLogger(WordFileServiceImpl.class);


    @Resource
    private WordConfig wordConfig;


    /**
     * 导入word文档获取试题列表
     * @param resultList
     * @param clz
     * @param <T>
     * @param <K>
     * @param htmlPath html存放路径
     */
    @Override
    public  <T, K> List<T> importWord(List<T> resultList, Class<K> clz, InputStream in, String htmlPath, String wordImgPath) throws Exception {
        // word转html
        docx2html(in, htmlPath,wordImgPath);
        // 解析html
        String id ="id";
        return readHTML2Class(htmlPath, resultList, clz, id);
    }

//    @Override
//    public List<Question> importWordAfterHandler(List<Question> resultList,String htmlPath) throws Exception {
//        File input = new File(htmlPath);
//        Document doc = Jsoup.parse(input, "UTF-8", "");
//        List<HeroDocxBean> html1 = DocxUtil.createHtml(resultList,doc.outerHtml());
//        for (int i = 0; i < resultList.size(); i++) {
//            resultList.get(i).setQuestion(html1.get(i).getQuestion());
//            resultList.get(i).setInformation(html1.get(i).getInformation());
//            resultList.get(i).setPassage(html1.get(i).getPassage());
//        }
//        return resultList;
//    }
//
    @Override
    public List<Question> importWordAfterHandler(List<Question> resultList,InputStream inputStream) throws Exception {
        // word文件
        XWPFDocument document = new XWPFDocument(inputStream);

        StringWriter stringWriter = new StringWriter();
        XHTMLConverter xhtmlConverter = (XHTMLConverter) XHTMLConverter.getInstance();
        XHTMLOptions options = XHTMLOptions.getDefault();
        xhtmlConverter.convert(document, stringWriter, options);
        String html = new String(stringWriter.toString().getBytes("utf-8"), "utf-8");
        List<HeroDocxBean> html1 = DocxUtil.createHtml(resultList,html);
        for (int i = 0; i < resultList.size(); i++) {
            resultList.get(i).setQuestion(html1.get(i).getQuestion());
            resultList.get(i).setInformation(html1.get(i).getInformation());
            resultList.get(i).setPassage(html1.get(i).getPassage());
        }
        return resultList;
    }

    /**
     * 解析html
     * @param htmlPath
     * @param resultList
     * @param clz
     * @param <T>
     * @param <K>
     */
    private  <T, K> List<T> readHTML2Class(String htmlPath, List<T> resultList, Class<K> clz, String id) throws IOException {
        // 属性map
        Map<String, String> propertyMap = new LinkedHashMap<>();
        // 实体map列表
        List<Map<String, String>> questionMapList = new LinkedList<>();
        // 当前传入实体的属性名列表
        Map<String, Boolean> fieldMap = getFieldMap(clz);
        if (fieldMap == null) {
            throw new RuntimeException("传入实体没有获取到字段信息");
        }

        File input = new File(htmlPath);
        Document doc = Jsoup.parse(input, "UTF-8", "");
        // 获取html标签<p>节点
        Elements links = doc.select("p");
        // 当前遍历的属性
        String thisProperName = "";
        Boolean isSaveFormat = true;

        for (int i = 0; i < links.size(); i++) {
            Element link = links.get(i);
            // 属性值(存格式)
            String propertyValue = link.html();
            // 属性名，可能参杂属性值（存值无格式）
            String propertyText = link.text();

            if (propertyValue.contains("<img")) {
                Elements img = link.select("img");
                String src = img.attr("src");

                // 获取图片，上传到阿里云oss
                if (StringUtils.isNotBlank(src )) {
                    src = uploadOSS(src);
                }
                img.attr("src", src);

                propertyValue = link.html();
            }

            int index = propertyText.indexOf(":");
            if (index != -1) {
                // 代表有可能为当前传入实体的属性
                String propertyName = propertyText.substring(0, index);
                if (fieldMap.get(propertyName) != null) {
                    if (propertyValue.contains("<img") && propertyValue.contains(propertyName)) {
                        propertyValue = propertyValue.substring(propertyValue.indexOf(propertyName+":</span>")+propertyName.length()+8);
                    } else {
                        propertyValue = propertyText.substring(index + 1);
                    }
//                    propertyValue = propertyText.substring(index + 1);
                    thisProperName = propertyName;
                    // 去除“:”后如果对应字段存在于实体属性列表中，代表为对应属性
                    if (fieldMap.get(propertyName)) {
                        // 有@NoFormat注解，不存格式
                        isSaveFormat = false;
                    } else {
                        isSaveFormat = true;
                    }
                }
            } else {
                if (!isSaveFormat) {
                    propertyValue = propertyText;
                }
            }

            String propertyMapValue = (propertyMap.get(thisProperName)==null?"":propertyMap.get(thisProperName));
            // 拼接属性值
            propertyMapValue += propertyValue;
            propertyMap.put(thisProperName, propertyMapValue);

            if ((i > 10 && thisProperName.equals(id)) || i == links.size() - 1) {
                questionMapList.add(propertyMap);
                propertyMap = new LinkedHashMap<>();
            }
        }

        // 解析questionMapList，注入属性值
        questionMapList.forEach(e -> {
            T t = (T) JSON.parseObject(JSON.toJSONString(e), clz);

            resultList.add(t);
        });


        return resultList;
    }

    /**
     * 将word文档转化成html
     *
     * @param htmlPath htmlPath
     */
    private void docx2html(InputStream in, String htmlPath, String wordImgUrl) throws Exception {
        File file = new File(htmlPath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        long begin2 = System.currentTimeMillis();
        WordprocessingMLPackage wordMLPackage = Docx4J.load(in);
        long end2 = System.currentTimeMillis();
        System.out.println("加载word文档内容：" + (end2-begin2));
        HTMLSettings htmlSettings = Docx4J.createHTMLSettings();
        htmlSettings.setImageDirPath(wordImgUrl);
        htmlSettings.setImageTargetUri(wordImgUrl);
        htmlSettings.setWmlPackage(wordMLPackage);
        String userCSS = "html, body, div, span,font, h1, h2, h3, h4, h5, h6, p, a, img,  ol, ul, li, table, caption, tbody, tfoot, thead, tr, th, td " +
                "{ margin: 10px; padding: 10px; border: 0;}" +
                "body {line-height: 1;} ";

        htmlSettings.setUserCSS(userCSS);
        OutputStream os;
        os = new FileOutputStream(htmlPath);
        Docx4jProperties.setProperty("docx4j.Convert.Out.HTML.OutputMethodXML", true);
        long begin1 = System.currentTimeMillis();
        Docx4J.toHTML(htmlSettings, os, Docx4J.FLAG_EXPORT_PREFER_XSL);
        long end1 = System.currentTimeMillis();
        System.out.println("将word文档转化成html：" + (end1-begin1));
    }

    /**
     * 上传图片到阿里云oss
     * @param fileName
     * @return
     */
    public  String uploadOSS(String fileName) {
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
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        // 设置URL过期时间为1小时。
        Date expiration = new Date(System.currentTimeMillis() * 3);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        URL url = ossClient.generatePresignedUrl(bucketName, objectName, expiration);
        return url.toString();
    }

    /**
     * 获取类的属性集合
     * @param clazz
     * @return
     */
    public  List<Field> getFields(Class<?> clazz) {
        if (clazz == null) {
            return Collections.emptyList();
        }

        Field[] fields = clazz.getDeclaredFields();
        return Arrays.asList(fields);
    }

    /**
     * 获取类里面属性名称
     * @param clazz
     * @return
     */
    public  Map<String, Boolean> getFieldMap(Class<?> clazz) {
        Map<String, Boolean> fieldMap = new HashMap<>();
        List<Field> list = getFields(clazz);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        list.forEach(e -> {
            if (e.isAnnotationPresent(NoFormat.class)) {
                fieldMap.put(e.getName(), true);
            } else {
                fieldMap.put(e.getName(), false);
            }
        });
//        return list.stream().map(Field::getName).collect(Collectors.toList());
        return fieldMap;
    }

}
