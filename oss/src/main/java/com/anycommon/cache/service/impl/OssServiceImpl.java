package com.anycommon.cache.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.anycommon.cache.config.OssConfig;
import com.anycommon.cache.dto.OssDTO;
import com.anycommon.cache.service.OssService;
import com.google.common.collect.Maps;
import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static java.util.UUID.randomUUID;
import static org.apache.commons.io.FilenameUtils.*;

/**
 * @author wangkai
 */
@Service
public class OssServiceImpl implements OssService {
    @Resource
    private OssConfig ossConfig;

    @Resource
    private OSSClient ossClient;

    @Override
    public OssDTO storePrivate(FileItem file) {
        OssDTO ossDTO = new OssDTO();
        String key = getBaseName(file.getName()) + randomUUID() + '.' + getExtension(file.getName());
        ossClient.setBucketAcl(ossConfig.getBucketName(), CannedAccessControlList.Private);
        ossClient.putObject(ossConfig.getBucketName(), key, new ByteArrayInputStream(file.get()));
        ossDTO.setKey(key);
        return ossDTO;
    }

    @Override
    public OssDTO storePublic(FileItem file) {
        OssDTO ossDTO = new OssDTO();
        String key = getBaseName(file.getName()) + randomUUID() + '.' + getExtension(file.getName());
        ossClient.setBucketAcl(ossConfig.getBucketName(), CannedAccessControlList.PublicRead);
        ossClient.putObject(ossConfig.getBucketName(), key, new ByteArrayInputStream(file.get()));
        ossDTO.setUrl(ossConfig.getEndpoint() + "/" + ossConfig.getBucketName() + "/" + key);
        ossDTO.setKey(key);
        return ossDTO;
    }

    @Override
    public OssDTO storePrivateByFileName(FileItem file, String fileName) {
        OssDTO ossDTO = new OssDTO();
        String key = fileName + '.' + getExtension(file.getName());
        ossClient.setBucketAcl(ossConfig.getBucketName(), CannedAccessControlList.Private);
        ossClient.putObject(ossConfig.getBucketName(), key, new ByteArrayInputStream(file.get()));
        ossDTO.setKey(key);
        return ossDTO;
    }

    @Override
    public OssDTO storePrivateByByteArrayInputStream(ByteArrayInputStream byteArrayInputStream, String fileName) {
        OssDTO ossDTO = new OssDTO();
        String key = removeExtension(fileName) + randomUUID() + '.' + getExtension(fileName);
        ossClient.setBucketAcl(ossConfig.getBucketName(), CannedAccessControlList.Private);
        ossClient.putObject(ossConfig.getBucketName(), key, byteArrayInputStream);
        ossDTO.setKey(key);
        return ossDTO;
    }

    @Override
    public OssDTO storePublicByByteArrayInputStream(ByteArrayInputStream byteArrayInputStream, String fileName) {
        OssDTO ossDTO = new OssDTO();
        String key = removeExtension(fileName) + randomUUID() + '.' + getExtension(fileName);
        ossClient.setBucketAcl(ossConfig.getBucketName(), CannedAccessControlList.PublicRead);
        ossClient.putObject(ossConfig.getBucketName(), key, byteArrayInputStream);
        ossDTO.setUrl(ossConfig.getEndpoint() + "/" + ossConfig.getBucketName() + "/" + key);
        ossDTO.setKey(key);
        return ossDTO;
    }

    @Override
    public InputStream getInputStreamByKey(String key) {
        return ossClient.getObject(ossConfig.getBucketName(), key).getObjectContent();
    }

    @Override
    public void delete(List<String> keys) {
        DeleteObjectsRequest request = new DeleteObjectsRequest(ossConfig.getBucketName());
        request.setKeys(keys);
        ossClient.deleteObjects(request);
    }
}
