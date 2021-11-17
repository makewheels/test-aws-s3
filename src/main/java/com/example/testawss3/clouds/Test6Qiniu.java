package com.example.testawss3.clouds;

import com.alibaba.fastjson.JSON;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectResult;

import java.util.UUID;

public class Test6Qiniu {
    public static void main(String[] args) {
        BasicAWSCredentials credentials = new BasicAWSCredentials("Ly7DtQ4VK5LbOsPrRfqx763WQzgZnKjRdi8vRx2s",
                "Fbq5BN2B7CK62Z6SzyBXA329yy0Mhwa6ZhgtP7u4");
        AwsClientBuilder.EndpointConfiguration configuration = new AwsClientBuilder.EndpointConfiguration(
                "s3-cn-north-1.qiniucs.com", "cn-north-1"
        );
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(configuration)
                .build();

        long time = System.currentTimeMillis();
        PutObjectResult putObjectResult = s3Client.putObject("bucket",
                "test/" + time + ".txt",
                UUID.randomUUID() + " " + time);
        System.out.println(JSON.toJSONString(putObjectResult));
    }
}
