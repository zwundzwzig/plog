package com.sokuri.plog.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class CoordinateDto {
  private final Double lat;
  private final Double lng;
  private String buildingName;
}
