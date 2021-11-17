package com.example.testawss3.test;

import com.alibaba.fastjson.JSON;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectResult;

import java.util.UUID;

public class Test5Baidu {
    public static void main(String[] args) {
        BasicAWSCredentials credentials = new BasicAWSCredentials("a579f885d23e496f9b4b5a4a86d8d7f1",
                "e5549857b8a44311ace833971bf57614");
        AwsClientBuilder.EndpointConfiguration configuration = new AwsClientBuilder.EndpointConfiguration(
                "s3.bj.bcebos.com", null
        );
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(configuration)
                .build();

        long time = System.currentTimeMillis();
        PutObjectResult putObjectResult = s3Client.putObject("common-bucket",
                "test/" + time + ".txt",
                UUID.randomUUID() + " " + time);
        System.out.println(JSON.toJSONString(putObjectResult));
    }
}
