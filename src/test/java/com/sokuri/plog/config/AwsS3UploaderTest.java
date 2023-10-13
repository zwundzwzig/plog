package com.sokuri.plog.config;

import com.sokuri.plog.cofig.S3Upload;
import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@Import(S3MockConfig.class)
@SpringBootTest
class AwsS3UploaderTest {

  @Autowired
  private S3Mock s3Mock;
  @Autowired
  private S3Upload s3Upload;

  @AfterEach
  public void tearDown() {
    s3Mock.stop();
  }

  @Test
  public void upload() throws IOException {
    // given
    String path = "images/demo.png";
    String contentType = "image/png";
    String dirName = "static";

    MockMultipartFile file = new MockMultipartFile("test", path, contentType, "test".getBytes());

    // when
//    String urlPath = s3Upload.uploadToAws(file, dirName);
    String urlPath = s3Upload.uploadToAws(file, dirName);

    // then
    assertThat(urlPath).contains(path);
    assertThat(urlPath).contains(dirName);
    System.out.println(urlPath);
  }
}