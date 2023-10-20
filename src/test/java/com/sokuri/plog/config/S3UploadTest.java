package com.sokuri.plog.config;

import com.amazonaws.services.s3.AmazonS3Client;
import com.sokuri.plog.utils.S3Upload;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class S3UploadTest {

  @InjectMocks
  private S3Upload s3Upload;

  @Mock
  private AmazonS3Client amazonS3Client;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

//  @Test
//  void testUploadToAws() throws IOException {
//    // Mock 파일 데이터 생성
//    byte[] fileContent = "test file content".getBytes();
//    MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "filename.txt", "text/plain", fileContent);
//
//    // Amazon S3에 업로드된 파일의 URL
//    String mockS3ImageUrl = "http://mock-s3-bucket-url/filename.txt";
//
//    // Amazon S3 클라이언트의 동작을 Mock으로 설정
//    when(amazonS3Client.getUrl(anyString(), anyString())).thenReturn(URI.create(mockS3ImageUrl).toURL());
//
//    // S3에 파일 업로드 테스트
//    String uploadedImageUrl = s3Upload.uploadToAws(mockMultipartFile, "testDir");
//
//    // 업로드된 파일 URL이 올바르게 반환되었는지 검증
//    assertEquals(mockS3ImageUrl, uploadedImageUrl);
//
//    // Amazon S3 클라이언트의 putObject 메서드가 호출되었는지 검증
//    verify(amazonS3Client).putObject(any(PutObjectRequest.class));
//
//    // 업로드 후 임시 파일이 삭제되었는지 검증 (Mock으로 설정한 상태에서 삭제가 되지 않으므로 호출 횟수를 0으로 설정)
//    verify(s3Upload, times(0)).removeNewFile(any(File.class));
//  }

//  @Test
//  void testConvert() throws IOException {
//    // Mock 파일 데이터 생성
//    byte[] fileContent = "test file content".getBytes();
//    MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "filename.txt", "text/plain", fileContent);
//
//    // convert 메서드 호출
//    Optional<File> convertedFileOptional = s3Upload.convert(mockMultipartFile);
//
//    // 변환된 파일이 존재하는지 확인
//    assertTrue(convertedFileOptional.isPresent());
//
//    // 변환된 파일의 내용이 올바른지 확인
//    File convertedFile = convertedFileOptional.get();
//    assertEquals("filename.txt", convertedFile.getName());
//    assertEquals("test file content", new String(Files.readAllBytes(convertedFile.toPath())));
//
//    // 변환된 파일을 삭제해야 하므로 removeNewFile 메서드가 호출되었는지 확인
//    verify(s3Upload).removeNewFile(any(File.class));
//  }
}
