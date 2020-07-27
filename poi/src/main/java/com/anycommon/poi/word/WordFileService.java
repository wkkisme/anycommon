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
public interface WordFileService {

    /**
     * @param resultList  结果集存放地方
     * @param clz
     * @param in
     * @param htmlPath
     * @param <T>
     * @param <K>
     * @return
     * @throws Exception
     */
     <T, K> List<T> importWord(List<T> resultList, Class<K> clz, InputStream in, String htmlPath) throws Exception;
}
