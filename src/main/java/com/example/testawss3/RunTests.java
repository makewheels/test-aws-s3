package com.example.testawss3;

import com.example.testawss3.s3.S3Config;
import com.example.testawss3.s3.S3Service;

public class RunTests {
    public static void main(String[] args) {
        S3Config aliyunConfig = new S3Config("oss-cn-beijing.aliyuncs.com",
                "LTAI5t8WesPNu1dDLaRmSWt8", "a45nM6ra07b203fXoZ7TKf3POJyYEi",
                "common-objects",
                "common-objects.oss-cn-beijing.aliyuncs.com",
                null,
                "common-objects.oss-cn-beijing-internal.aliyuncs.com");

        S3Service aliyunService = new S3Service();
        aliyunService.init(aliyunConfig);

        System.out.println(aliyunService.getCdnUrl("test/1637129048541.txt"));
    }
}
