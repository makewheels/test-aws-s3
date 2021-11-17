package com.example.testawss3;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.Data;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class S3Service {
    private String bucketName;
    private String endpoint;

    private AmazonS3 amazonS3;

    public boolean doesObjectExist(String key) {
        return amazonS3.doesObjectExist(bucketName, key);
    }

    public S3Object getObject(String key) {
        return amazonS3.getObject(bucketName, key);
    }

    public ObjectListing listObjects(String prefix) {
        return amazonS3.listObjects(bucketName, prefix);
    }

    public PutObjectResult putObject(String key, File file) {
        return amazonS3.putObject(bucketName, key, file);
    }

    public void deleteObject(String key) {
        amazonS3.deleteObject(bucketName, key);
    }

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

    public String getUrl(String key) {
        return amazonS3.getUrl(bucketName, key).toString();
    }

    public String generatePresignedUrl(String key, Duration duration, HttpMethod httpMethod) {
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, key)
                .withMethod(httpMethod)
                .withExpiration(Date.from(Instant.now().plus(duration)));
        return amazonS3.generatePresignedUrl(request).toString();
    }

}
