package com.anycommon.oss.service;

import com.anycommon.oss.dto.OssDTO;
import org.apache.commons.fileupload.FileItem;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;


/**
 * oss 服务
 * @author wangkai
 */
public interface OssService {
    /**
     * 上传文件到oss 私有化
     *
     * @param file file
     * @return OssDTO
     */
    OssDTO storePrivate(FileItem file);

    /**
     * 上传文件到oss 共有化
     *
     * @param file file
     * @return OssDTO key 为：
     */
    OssDTO storePublic(FileItem file);

    /**
     * 上传文件到oss
     *
     * @param file     file
     * @param fileName 自定义文件名
     * @return OssDTO
     */
    OssDTO storePrivateByFileName(FileItem file, String fileName);


    /**
     * 上传文件用byteArrayInputStream形式 私有化
     *
     * @param byteArrayInputStream byteArrayInputStream
     * @param fileName             fileName
     * @return OssDTO
     */
    OssDTO storePrivateByByteArrayInputStream(ByteArrayInputStream byteArrayInputStream, String fileName);

    /**
     * 上传文件用byteArrayInputStream形式 公共
     *
     * @param byteArrayInputStream byteArrayInputStream
     * @param fileName             fileName
     * @return OssDTO
     */
    OssDTO storePublicByByteArrayInputStream(ByteArrayInputStream byteArrayInputStream, String fileName);

    /**
     * InputStream
     * @param key key
     * @return
     */
    InputStream getInputStreamByKey(String key);


    /**
     *  删除
     * @param keys keys
     */
    void delete(List<String> keys);




}
