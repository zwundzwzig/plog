//package com.sokuri.plog.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//
//@SpringBootTest(properties = "spring.cloud.config.enabled=false")
//class RedisConfigTest {
//
//  @Autowired
//  private AdminUserRedisService adminUserRedisService;
//
//  @Autowired
//  private RedisTemplate<String, Object> redisTemplate;
//
//  @DisplayName("token redis 저장 success")
//  @Test
//  void tokenSave(){
//    //given
//    RefreshToken token = RefreshToken.builder()
//            .userId("test")
//            .refreshToken("test_token")
//            .expiredTime(60)    //테스트용 1분
//            .build();
//    //when
//    RefreshToken refreshToken = adminUserRedisService.save(token);
//    //then
//    RefreshToken findToken = adminUserRedisService.findById(token.getUserId());
//    System.out.println(refreshToken.getRefreshToken());
//    System.out.println(findToken.getRefreshToken());
//    assertEquals(refreshToken.getRefreshToken(), findToken.getRefreshToken());
//  }
//
//  @DisplayName("redis template 사용")
//  @Test
//  @Transactional
//  void tokenSaveRedisTemplate(){
//    //given
//    String userId = "tester1";
//
//    RefreshToken token = RefreshToken.builder()
//            .userId(userId)
//            .refreshToken("test_token")
//            .expiredTime(60)    //테스트용 1분
//            .build();
//
//    //when
//    String key = "refreshToken:"+userId;
//    HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
//    hash.put(key, "userId", userId);
//    hash.put(key, "refreshToken", token.getRefreshToken());
//
//    //then
//    Object o = redisTemplate.opsForHash().get(key, "userId");
//    System.out.println(o);
//  }
//}
