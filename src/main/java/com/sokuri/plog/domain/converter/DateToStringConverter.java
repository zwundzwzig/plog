package com.sokuri.plog.domain.converter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

public class DateToStringConverter {
  public static String explainDate(LocalDateTime date) {
    Period period = Period.between(date.toLocalDate(), LocalDate.now());
    Duration duration = Duration.between(date, LocalDateTime.now());
    List<Integer> timeDiffer = List.of(period.getYears(),
            period.getMonths(),
            period.getDays(),
            (int) duration.toHours(),
            (int) duration.toMinutes());

    String[] units = {"년", "달", "일", "시간", "분"};

    for (int i = 0; i < timeDiffer.size(); i++) {
      if (timeDiffer.get(i) != 0) return timeDiffer.get(i) + units[i] + " 전";
    }

    return "방금 전";
  }
}
