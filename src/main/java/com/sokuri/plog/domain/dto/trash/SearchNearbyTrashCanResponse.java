package com.sokuri.plog.domain.dto.trash;

import com.sokuri.plog.domain.dto.CoordinateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchNearbyTrashCanResponse {
  Long seq;
  String detail;
  String roadName;
  String spot;
  String type;
  CoordinateDto geolocation;
  Double distanceFromMyPosition;
}
