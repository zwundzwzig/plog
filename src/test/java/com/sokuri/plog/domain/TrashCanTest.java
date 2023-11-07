package com.sokuri.plog.domain;

import com.sokuri.plog.repository.TrashRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
class TrashCanTest {
  @Autowired
  TrashRepository trashRepository;

  @Test
  @DisplayName("geometry 조회 테스트")
  void select_geometry_test() {
    List<TrashCan> trash = trashRepository.findAll();

    Geometry point = trash.get(0).getGeolocation();
    System.out.println(point);

    Assertions.assertThat(point).isInstanceOf(Geometry.class);
  }

  @Test
  @DisplayName("geometry 저장 테스트")
  void save_geometry_test() throws ParseException {
    double longitude = 126.013;
    double latitude = 33.013;

    String parsingPoint = String.format("POINT(%f %f)", longitude, latitude);
    Point point = (Point) new WKTReader().read(parsingPoint);

    TrashCan dummy1 = TrashCan.builder().gu("test1").geolocation(point).build();
    TrashCan dummy2 = TrashCan.builder().gu("test2").geolocation(point).build();
    trashRepository.saveAll(List.of(dummy1, dummy2));
  }
}
