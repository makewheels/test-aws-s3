package com.example.testawss3.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class S3Service {
    private String endpoint;
    private String bucketName;

    private String domain;
    private String cdnDomain;
    private String internalDomain;

    private AmazonS3 amazonS3;

    /**
     * 初始化
     *
     * @param config
     */
    public void init(S3Config config) {
        this.endpoint = config.getEndpoint();
        this.bucketName = config.getBucketName();
        this.domain = config.getDomain();
        this.cdnDomain = config.getCdnDomain();
        this.internalDomain = config.getInternalDomain();

        if (amazonS3 == null) {
            AWSCredentials credentials = new BasicAWSCredentials(config.getAccessKey(), config.getSecretKey());
            AwsClientBuilder.EndpointConfiguration configuration = new AwsClientBuilder.EndpointConfiguration(
                    config.getEndpoint(), config.getRegion());
            amazonS3 = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withEndpointConfiguration(configuration)
                    .build();
        }
    }

    /**
     * object是否存在
     *
     * @param key
     * @return
     */
    public boolean doesObjectExist(String key) {
        return amazonS3.doesObjectExist(bucketName, key);
    }

    /**
     * 获取object
     *
     * @param key
     * @return
     */
    public S3Object getObject(String key) {
        return amazonS3.getObject(bucketName, key);
    }

    /**
     * 根据prefix查询objects
     *
     * @param prefix
     * @return
     */
    public ObjectListing listObjects(String prefix) {
        return amazonS3.listObjects(bucketName, prefix);
    }

    /**
     * 上传File
     *
     * @param key
     * @param file
     * @return
     */
    public PutObjectResult putObject(String key, File file) {
        return amazonS3.putObject(bucketName, key, file);
    }

    /**
     * 上传String content
     *
     * @param key
     * @param content
     * @return
     */
    public PutObjectResult putObject(String key, String content) {
        return amazonS3.putObject(bucketName, key, content);
    }

    /**
     * 删除object
     *
     * @param key
     */
    public void deleteObject(String key) {
        amazonS3.deleteObject(bucketName, key);
    }

    /**
     * 批量删除objects
     *
     * @param keys
     * @return
     */
    public DeleteObjectsResult deleteObjects(List<String> keys) {
        DeleteObjectsRequest request = new DeleteObjectsRequest(bucketName);
        List<DeleteObjectsRequest.KeyVersion> keyVersions = new ArrayList<>(keys.size());
        for (String key : keys) {
            DeleteObjectsRequest.KeyVersion keyVersion = new DeleteObjectsRequest.KeyVersion(key);
            keyVersions.add(keyVersion);
        }
        request.setKeys(keyVersions);
        return amazonS3.deleteObjects(request);
    }

    /**
     * 获取url
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        return amazonS3.getUrl(bucketName, key).toString();
    }

    /**
     * 获取cdn url
     *
     * @param key
     * @return
     */
    public String getCdnUrl(String key) {
        return getUrl(key).replaceFirst(domain, cdnDomain);
    }

    /**
     * 获取internal url
     *
     * @param key
     * @return
     */
    public String getInternalUrl(String key) {
        return getUrl(key).replaceFirst(domain, internalDomain);
    }

    /**
     * 预签名，可指定HttpMethod
     *
     * @param key
     * @param duration
     * @param httpMethod
     * @return
     */
    public String generatePresignedUrl(String key, Duration duration, HttpMethod httpMethod) {
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, key)
                .withMethod(httpMethod)
                .withExpiration(Date.from(Instant.now().plus(duration)));
        return amazonS3.generatePresignedUrl(request).toString();
    }

    /**
     * 预签名下载url
     *
     * @param key
     * @param duration
     * @return
     */
    public String getPresignedDownloadUrl(String key, Duration duration) {
        return generatePresignedUrl(key, duration, HttpMethod.GET);
    }

    /**
     * 预签名下载url，时效一小时
     *
     * @param key
     * @return
     */
    public String getPresignedDownloadUrlForOneHour(String key) {
        return getPresignedDownloadUrl(key, Duration.ofHours(1));
    }

    /**
     * 预签名上传url
     *
     * @param key
     * @param duration
     * @return
     */
    public String getPresignedUploadUrl(String key, Duration duration) {
        return generatePresignedUrl(key, duration, HttpMethod.PUT);
    }

    /**
     * 预签名上传url，时效一小时
     *
     * @param key
     * @return
     */
    public String getPresignedUploadUrlForOneHour(String key) {
        return generatePresignedUrl(key, Duration.ofHours(1), HttpMethod.PUT);
    }
}
