package com.anycommon.poi.word;

import org.springframework.stereotype.Component;
import java.io.*;
import java.util.*;


/**
 * word文档解析试卷试题
 *
 * @author wangkai
 * @date 2020-07-27 10:44
 */
@Component
public interface WordFileService {

    /**
     * word导入解析
     * @param resultList  结果集存放地方
     * @param clz
     * @param in
     * @param <T>
     * @param <K>
     * @return
     * @throws Exception
     */
     <T, K> List<T> importWord(List<T> resultList, Class<K> clz, InputStream in,String htmlPath, String wordImgPath) throws Exception;
}
