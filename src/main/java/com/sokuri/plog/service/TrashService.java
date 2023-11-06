package com.sokuri.plog.service;

import com.sokuri.plog.domain.TrashCan;
import com.sokuri.plog.domain.converter.RoadNameAddressToCoordinateConverter;
import com.sokuri.plog.domain.dto.CoordinateDto;
import com.sokuri.plog.domain.eums.TrashType;
import com.sokuri.plog.repository.TrashRepository;
import com.sokuri.plog.utils.GeometryUtils;
import com.sokuri.plog.utils.GoogleApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TrashService {
  private final TrashRepository trashRepository;
  private final GoogleApiUtil googleApiUtil;
  private final GeometryUtils geometryUtils;
  private final RoadNameAddressToCoordinateConverter roadNameAddressToCoordinateConverter;

  @SneakyThrows
  @Async
  public List<CoordinateDto> setPublicTrashCan() {
    List<CoordinateDto> response = new ArrayList<>();
    List<Map<String, Object>> sheetData = googleApiUtil.getDataFromSheet().values().stream().toList();
    List<TrashCan> trashCans = sheetData.stream()
            .map(this::mapToTrashCan)
            .toList();
    trashRepository.saveAll(trashCans);
    return response;
  }

  private TrashCan mapToTrashCan(Map<String, Object> data) {
    String gu = getStringValue(data, "자치구명");
    String roadName = getStringValue(data, "도로명");
    String detail = getStringValue(data, "세부 위치(상세 주소)");
    String address = getStringValue(data, "소쿠리 도로명 주소");
    String detailSokuri = getStringValue(data, "소쿠리 상세 주소");
    String spot = getStringValue(data, "설치 지점");
    TrashType type = getTrashType(data, "수거 쓰레기 종류(일반 쓰레기 / 재활용 쓰레기)");

    CoordinateDto coordinate = roadNameAddressToCoordinateConverter.convertAddressToCoordinate(address)
            .block();

    return TrashCan.builder()
            .gu(gu)
            .roadName(address)
            .detail(detailSokuri)
            .spot(spot)
            .geolocation(coordinate != null
                    ? geometryUtils.createPoint(coordinate.getLat(), coordinate.getLng())
                    : null)
            .type(type)
            .build();
  }

  private String getStringValue(Map<String, Object> data, String key) {
    return data.getOrDefault(key, "").toString();
  }

  private TrashType getTrashType(Map<String, Object> data, String key) {
    String value = getStringValue(data, key);
    if (value.contains("+")) {
      return TrashType.MULTI;
    } else if (value.contains(TrashType.TRASH.getValue())) {
      return TrashType.TRASH;
    } else if (value.contains(TrashType.PUBLIC.getValue())) {
      return TrashType.PUBLIC;
    } else if (value.contains(TrashType.CIGARETTE.getValue())) {
      return TrashType.CIGARETTE;
    } else if (value.contains(TrashType.CROSSWALK.getValue())) {
      return TrashType.CROSSWALK;
    } else if (value.contains(TrashType.STATION.getValue())) {
      return TrashType.STATION;
    } else if (value.contains(TrashType.GARBAGE.getValue())) {
      return TrashType.GARBAGE;
    } else {
      return TrashType.ETC;
    }
  }
}
