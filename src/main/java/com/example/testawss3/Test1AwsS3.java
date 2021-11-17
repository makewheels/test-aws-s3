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

public class Test1AwsS3 {
    public static void main(String[] args) {
        BasicAWSCredentials credentials = new BasicAWSCredentials("AKIA3T7OC6BJUXVN3NXV",
                "z1HzN4QK8j8l0kT0H9uawxLnNO03zXv/tgRIfk23");
        AwsClientBuilder.EndpointConfiguration configuration = new AwsClientBuilder.EndpointConfiguration(
                "s3.ap-northeast-1.amazonaws.com", "ap-northeast-1"
        );
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(configuration)
                .build();

        long time = System.currentTimeMillis();
        PutObjectResult putObjectResult = s3Client.putObject("test-s3-bucket-tokyo",
                "test/" + time + ".txt",
                UUID.randomUUID() + " " + time);
        System.out.println(JSON.toJSONString(putObjectResult));
    }
}
