package com.sokuri.plog.service;

import com.sokuri.plog.domain.TrashCan;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class TrashServiceTest {
  @Autowired
  EntityManager entityManager;

  private Geometry wktToGeometry(String wellKnownText) throws ParseException {
    return new WKTReader().read(wellKnownText);
  }

  @Test
  @DisplayName("쓰레기통 위치 insert 테스트")
  public void shouldInsertAndSelectPoints() throws ParseException {
    TrashCan trash = TrashCan.builder()
            .gu("강남구")
            .geolocation((Point) wktToGeometry("POINT (38 122)"))
            .build();

    entityManager.persist(trash);
    entityManager.flush();
    TrashCan result = entityManager.find(TrashCan.class, trash.getId());

    assertEquals("POINT (1 1)", result.getGeolocation().toString());
    assertTrue(trash.getGeolocation() instanceof Point);
  }
}
