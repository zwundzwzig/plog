package com.sokuri.plog.cofig;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@EnableAsync
@Configuration
@Slf4j
public class AsyncConfig implements AsyncConfigurer {

  @Override
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(10);
    executor.setMaxPoolSize(100);
    executor.setQueueCapacity(10);
    executor.setThreadNamePrefix("AsyncThread-");
    executor.initialize();
    return executor;
  }

  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    // 비동기 작업에서 예외 처리를 담당하는 핸들러를 반환
    return new CustomAsyncExceptionHandler();
  }

  class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
      log.error("비동기 작업에서 예외가 발생했습니다: ", throwable);
      log.error("비동기 작업에서 예외가 발생했습니다: " + throwable.getMessage());
      log.error("메소드: " + method.getName());
      log.error("파라미터 : ", objects);

      IntStream.range(0, objects.length).forEach(i -> log.info("인덱스 " + i + ": " + objects[i]));
    }

  }
}
