package com.sokuri.plog.domain.dto.trash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SheetTrashRequest {
  private String gu;
  private String roadName;
  private String detail;
  private String spot;
  private String type;
}
