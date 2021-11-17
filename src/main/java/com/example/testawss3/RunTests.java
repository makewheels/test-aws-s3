package com.example.testawss3;

import com.alibaba.fastjson.JSON;
import com.example.testawss3.s3.S3Config;
import com.example.testawss3.s3.S3Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RunTests {
    public static void main(String[] args) {
        S3Config awsConfig = new S3Config("s3.ap-northeast-1.amazonaws.com",
                "s3.ap-northeast-1.amazonaws.com", "ap-northeast-1",
                "AKIA3T7OC6BJUXVN3NXV", "z1HzN4QK8j8l0kT0H9uawxLnNO03zXv/tgRIfk23",
                "test-s3-bucket-tokyo",
                "test-s3-bucket-tokyo.s3.ap-northeast-1.amazonaws.com",
                "d2n6gh33aydtfl.cloudfront.net",
                "test-s3-bucket-tokyo.s3.ap-northeast-1.amazonaws.com");

        S3Config aliyunConfig = new S3Config("oss-cn-beijing.aliyuncs.com",
                "oss-cn-beijing-internal.aliyuncs.com", "cn-beijing",
                "LTAI5t8WesPNu1dDLaRmSWt8", "a45nM6ra07b203fXoZ7TKf3POJyYEi",
                "common-objects",
                "common-objects.oss-cn-beijing.aliyuncs.com",
                "ALIYUN_CDN_DOMAIN",
                "common-objects.oss-cn-beijing-internal.aliyuncs.com");

        S3Config tencentConfig = new S3Config("cos.ap-beijing.myqcloud.com",
                "cos.ap-beijing.myqcloud.com", "ap-beijing",
                "AKIDrYdYFhurY0DBwRggSzHL9hz3rPhjW4MP", "pOilY5MMyVlkSu50f4C4fgT1JL8fwxjG",
                "bucket-1253319037",
                "bucket-1253319037.cos.ap-beijing.myqcloud.com",
                "bucket-1253319037.file.myqcloud.com",
                "bucket-1253319037.cos.ap-beijing.myqcloud.com");

        S3Config baiduConfig = new S3Config("s3.bj.bcebos.com",
                "s3.bj.bcebos.com", "bj",
                "a579f885d23e496f9b4b5a4a86d8d7f1", "e5549857b8a44311ace833971bf57614",
                "common-bucket",
                "common-bucket.bj.bcebos.com",
                "common-bucket.cdn.bcebos.com",
                "common-bucket.bj.bcebos.com");

        S3Config huaweiConfig = new S3Config("obs.cn-north-4.myhuaweicloud.com",
                "obs.cn-north-4.myhuaweicloud.com", "cn-north-4",
                "7BWE8R0DACRORD89D2U5", "OyJbgelzyedw3VA94QNugoAq9NWtFPMUzbecNnmt",
                "test-beijing-bucket",
                "test-beijing-bucket.obs.cn-north-4.myhuaweicloud.com",
                "HUAWEI_CDN_DOMAIN",
                "common-objects.oss-cn-beijing-internal.aliyuncs.com");

        RunTests runTests = new RunTests();
        runTests.runService(awsConfig);

    }

    public void runService(S3Config config) {
        S3Service s3Service = new S3Service();
        s3Service.init();

        String key = "test/" + System.currentTimeMillis() + ".txt";
        System.out.println(JSON.toJSONString(s3Service.putObject(key, UUID.randomUUID().toString())));
        System.out.println(s3Service.doesObjectExist(key));
        System.out.println(JSON.toJSONString(s3Service.getObject(key)));
        System.out.println(JSON.toJSONString(s3Service.listObjects("test/")));
        List<String> keys = new ArrayList<>();
        keys.add(key);
//        s3Service.deleteObject(key);
        System.out.println(JSON.toJSONString(s3Service.deleteObjects(keys)));

        System.out.println(s3Service.getUrl(key));
        System.out.println(s3Service.getCdnUrl(key));
        System.out.println(s3Service.getInternalUrl(key));

        System.out.println(s3Service.doesObjectExist(key));
    }
}
