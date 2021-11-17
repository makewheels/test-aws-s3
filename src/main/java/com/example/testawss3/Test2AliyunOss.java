package com.example.testawss3;

import com.alibaba.fastjson.JSON;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

import java.util.UUID;

public class Test2AliyunOss {
    public static void main(String[] args) {
        BasicAWSCredentials credentials = new BasicAWSCredentials("LTAI5t8WesPNu1dDLaRmSWt8",
                "a45nM6ra07b203fXoZ7TKf3POJyYEi");
        AwsClientBuilder.EndpointConfiguration configuration = new AwsClientBuilder.EndpointConfiguration(
                "oss-cn-beijing.aliyuncs.com", "cn-beijing"
        );
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(configuration)
                .build();

        long time = System.currentTimeMillis();
        PutObjectResult putObjectResult = s3Client.putObject("common-objects",
                "test/" + time + ".txt",
                UUID.randomUUID() + " " + time);
        System.out.println(JSON.toJSONString(putObjectResult));
    }
}
