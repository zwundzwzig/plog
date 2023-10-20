package com.sokuri.plog.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class CoordinateDto {
  private final double lat;
  private final double lng;
  private String buildingName;
}
