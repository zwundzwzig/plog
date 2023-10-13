package com.sokuri.plog.config;

import com.amazonaws.services.s3.AmazonS3Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class S3ConnectionTest {

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Test
    public void s3ConnectionTest() {
        System.out.println(amazonS3Client.listBuckets());
        assertThat(amazonS3Client.listBuckets()).isNotNull();
    }
}

