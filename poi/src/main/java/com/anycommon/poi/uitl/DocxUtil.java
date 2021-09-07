package com.anycommon.poi.uitl;

import com.aliyun.oss.OSSClient;
import com.anycommon.poi.config.WordConfig;
import com.anycommon.poi.word.Question;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author:niney
 * @email:v.onai1314.cool@163.com word处理
 **/
@Component
public class DocxUtil {


    private static WordConfig wordConfig;

    @Resource
    public void setWordConfig(WordConfig wordConfig) {
        DocxUtil.wordConfig = wordConfig;
    }

    /**
     * 截取passage内容
     *
     * @param html
     * @return
     */
    public static String passageHtml(String html) {
        if (isEmpty(html)) {
            //这些写抛出异常的一些话语
        }
        //截取passage内容
        String passage = html.substring(
                html.indexOf("passage:"), html.indexOf("question:")
        );
        passage = passage.substring(passage.indexOf("<p"), passage.lastIndexOf("<p"));
        return passage;
    }


    /**
     * 截取question内容，并替换图片为网络地址
     *
     * @param html
     * @return
     */
    public static String questionHtml(String html) {
        if (isEmpty(html)) {
            //这些写抛出异常的一些话语
        }
        //截取passage内容
        String question = html.substring(
                html.indexOf("question:"), html.indexOf("information:")
        );
        question = question.substring(question.indexOf("<p"), question.lastIndexOf("<p"));
        return question;
    }

    /**
     * information，并替换图片为网络地址
     *
     * @param html
     * @return
     */
    public static String informationHtml(String html) {
        //截取passage内容
        String information = html.substring(
                html.indexOf("information:"), html.indexOf("option_a:")
        );
        information = information.substring(information.indexOf("<p"), information.lastIndexOf("<p"));
        return information;
    }

    /**
     * 每上传一题就截断上一题内容
     *
     * @param html
     * @return
     */
    public static String htmlCutOff(String html) {
        String as = html.substring(html.indexOf("reference:"));
        return as.substring(as.indexOf("</span>"));
    }



    /**
     *
     */
    public static List<String> imgGet(String filedHtml) {
        if (filedHtml == null ) {
            return null;
        }
        Elements img = Jsoup.parse(filedHtml).select("img");
        List<String> replaceAll = new LinkedList<>();
        for (Element element : img) {
            replaceAll.add(element.attr("src"));
        }
        return replaceAll;
    }


    /**
     *
     */
    public static String imgUpdate(String html, List<String> imgs) {
        if (imgs == null || imgs.size() <= 0) {
            return html;
        }
        Document imgDoc = Jsoup.parse(html);
        Elements img = imgDoc.select("img");
        for (int i = 0; i < img.size(); i++) {
            Attributes attributes = img.get(i).attributes();
            for (Attribute attribute : attributes) {
                if ("src".equals(attribute.getKey())){
                    attribute.setValue(imgs.get(i));
                }
            }
        }
        return imgDoc.outerHtml();
    }

    /**
     * 上传图片到阿里云oss
     *
     * @param fileName
     * @return
     */
    public static String uploadOSS(String fileName) {
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
     * 替换图片
     *
     * @param html
     * @param img
     */
    public static String htmlReplaceImg(String html, List<String> img) {
        if (img == null || img.size() <= 0) {
            return html;
        }
        //判断img出现了几次
        int cnt = strAriseNo(html, "<img src=");
        if (img.size() != cnt) {
            System.out.println("图片次数和img标签次数不匹配");
            return html;
        }
        //图片的替换
        String record = "";
        String recordHtml = html;
        List<String> replaceAll = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {
            record = recordHtml.substring(recordHtml.indexOf("<img src="), recordHtml.indexOf("width="));
            replaceAll.add(record);
            recordHtml = recordHtml.substring(recordHtml.indexOf("height="));
        }
        for (int i = 0; i < cnt; i++) {
            html = html.replaceAll(replaceAll.get(i), "<img src=\"" + img.get(i) + "\" width=");
        }
        return html;
    }

    /**
     * 判断字符串出现了几次
     */
    public static int strAriseNo(String html, String msg) {
        int cnt = 0;
        int offset = 0;
        while ((offset = html.indexOf(msg, offset)) != -1) {
            offset = offset + msg.length();
            cnt++;
        }
        return cnt;
    }

    /**
     * 判断是否为null
     *
     * @param obj
     * @return
     */
    public static Boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj.toString().isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * html 转换成多条上传题目，包含原始图片可以访问找到
     * @param html
     * @return
     * @throws IOException
     */
    public static List<HeroDocxBean> createHtml(List<Question> resultList,String html) throws IOException {
        List<HeroDocxBean> heroDocxBeans = new LinkedList<>();
        //首先判断上传题目有几题
        int no = DocxUtil.strAriseNo(html, "passage:");
        for (int i = 0; i < no; i++) {
            String passage = DocxUtil.passageHtml(html);
            String question = DocxUtil.questionHtml(html);
            String infor = DocxUtil.informationHtml(html);

            HeroDocxBean heroDocxBean = new HeroDocxBean();
            heroDocxBean.setPassage(DocxUtil.imgUpdate(passage,imgGet(resultList.get(i).getPassage())));
            heroDocxBean.setQuestion(DocxUtil.imgUpdate(question,imgGet(resultList.get(i).getQuestion())));
            heroDocxBean.setInformation(DocxUtil.imgUpdate(infor,imgGet(resultList.get(i).getInformation())));
            heroDocxBeans.add(heroDocxBean);
            html = DocxUtil.htmlCutOff(html);
        }
        return heroDocxBeans;
    }
}
