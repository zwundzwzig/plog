package com.sokuri.plog.domain.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateToStringConverter {
  public static String dateToString(String date) {
    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    LocalDateTime dateTime = LocalDateTime.parse(date, inputFormatter);

    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초");
    return dateTime.format(outputFormatter);

  }
}
