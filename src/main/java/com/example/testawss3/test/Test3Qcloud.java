package com.example.testawss3.test;

import com.alibaba.fastjson.JSON;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectResult;

import java.util.UUID;

public class Test3Qcloud {
    public static void main(String[] args) {
        BasicAWSCredentials credentials = new BasicAWSCredentials("AKIDrYdYFhurY0DBwRggSzHL9hz3rPhjW4MP",
                "pOilY5MMyVlkSu50f4C4fgT1JL8fwxjG");
        AwsClientBuilder.EndpointConfiguration configuration = new AwsClientBuilder.EndpointConfiguration(
                "cos.ap-beijing.myqcloud.com", null
        );
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(configuration)
                .build();

        long time = System.currentTimeMillis();
        PutObjectResult putObjectResult = s3Client.putObject("bucket-1253319037",
                "test/" + time + ".txt",
                UUID.randomUUID() + " " + time);
        System.out.println(JSON.toJSONString(putObjectResult));
    }
}
