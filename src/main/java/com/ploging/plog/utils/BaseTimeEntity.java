package com.ploging.plog.utils;

import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

  @CreatedDate
  @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
  private LocalDateTime createDate;

  @LastModifiedDate
  @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
  private LocalDateTime modifiedDate;

}