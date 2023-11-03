package com.sokuri.plog.service;

import com.sokuri.plog.domain.TrashCan;
import com.sokuri.plog.domain.dto.CoordinateDto;
import com.sokuri.plog.domain.dto.trash.SheetTrashRequest;
import com.sokuri.plog.domain.eums.TrashType;
import com.sokuri.plog.repository.TrashRepository;
import com.sokuri.plog.utils.GoogleApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TrashService {
  private final TrashRepository trashRepository;
  private final GoogleApiUtil googleApiUtil;

  @SneakyThrows
  @Async
  public List<CoordinateDto> setPublicTrashCan() {
    List<CoordinateDto> response = new ArrayList<>();
    List<Map<String, Object>> list = googleApiUtil.getDataFromSheet().values().stream().toList();
    List<TrashCan> dataList = list.stream()
            .map(data -> TrashCan.builder()
                    .gu(data.get("자치구명").toString())
                    .roadName(data.get("도로명").toString())
                    .detail(data.get("세부 위치(상세 주소)").toString())
                    .spot(data.get("설치 지점").toString())
                    .type(
                            data.get("수거 쓰레기 종류(일반 쓰레기 / 재활용 쓰레기)").toString().contains("+") ? TrashType.MULTI
                                    : data.get("수거 쓰레기 종류(일반 쓰레기 / 재활용 쓰레기)").toString().contains(TrashType.TRASH.getValue()) ? TrashType.TRASH
                                    : data.get("수거 쓰레기 종류(일반 쓰레기 / 재활용 쓰레기)").toString().contains(TrashType.PUBLIC.getValue()) ? TrashType.PUBLIC
                                    : data.get("수거 쓰레기 종류(일반 쓰레기 / 재활용 쓰레기)").toString().contains(TrashType.CIGARETTE.getValue()) ? TrashType.CIGARETTE
                                    : data.get("수거 쓰레기 종류(일반 쓰레기 / 재활용 쓰레기)").toString().contains(TrashType.CROSSWALK.getValue()) ? TrashType.CROSSWALK
                                    : data.get("수거 쓰레기 종류(일반 쓰레기 / 재활용 쓰레기)").toString().contains(TrashType.STATION.getValue()) ? TrashType.STATION
                                    : data.get("수거 쓰레기 종류(일반 쓰레기 / 재활용 쓰레기)").toString().contains(TrashType.GARBAGE.getValue()) ? TrashType.GARBAGE
                                    : TrashType.ETC
                    ).build())
            .toList();
    trashRepository.saveAll(dataList);
    return response;
  }
}
