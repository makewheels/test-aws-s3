package com.example.testawss3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class Run {
    public static void main(String[] args) {
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withClientConfiguration()
                .build();
    }
}
