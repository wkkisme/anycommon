package com.anycommon.oss.config;


import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.Credentials;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * heroland-competition-parent
 *
 * @author wangkai
 * @date 2020/6/19
 */

@Configuration
@PropertySource(value = "classpath:oss-${spring.profiles.active}.properties")
public class OssConfig {

    @Value("${platform.oss.endpoint}")
    private String endpoint;

    @Value("${platform.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${platform.oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${platform.oss.bucketName}")
    private String bucketName;




    /**
     * 获取私有云 OssClient
     *
     * @return OSSClient
     */
    @Bean
    public OSSClient getOssClient() {

        return new OSSClient(endpoint, new DefaultCredentialProvider(new Credentials() {
            @Override
            public String getAccessKeyId() {
                return accessKeyId;
            }

            @Override
            public String getSecretAccessKey() {
                return accessKeySecret;
            }

            @Override
            public String getSecurityToken() {
                return null;
            }

            @Override
            public boolean useSecurityToken() {
                return false;
            }
        }), new ClientConfiguration().setSupportCname(false));


    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
