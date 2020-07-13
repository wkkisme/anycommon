package com.anycommon.oss.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.anycommon.oss.config.OssConfig;
import com.anycommon.oss.dto.OssDTO;
import com.anycommon.oss.service.OssService;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static java.util.UUID.randomUUID;
import static org.apache.commons.io.FilenameUtils.*;

/**
 * 默认
 * @author wangkai
 */
@Service("ossService")
public class AliyunOssServiceImpl implements OssService {
    private final static Logger logger = LoggerFactory.getLogger(AliyunOssServiceImpl.class);
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
