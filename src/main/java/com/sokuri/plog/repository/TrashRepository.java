package com.sokuri.plog.repository;

import com.sokuri.plog.domain.TrashCan;
import com.sokuri.plog.domain.dto.CoordinateDto;
import com.sokuri.plog.domain.dto.trash.SearchNearbyTrashCanResponse;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public interface TrashRepository extends JpaRepository<TrashCan, Long> {
  @Query(value = "SELECT tc.id AS seq, tc.detail AS detail, tc.roadName AS roadName, tc.spot AS spot, tc.type AS type" +
          ", ST_X(tc.geolocation) AS longitude" +
          ", ST_Y(tc.geolocation) AS latitude" +
          ", ST_Distance_Sphere(tc.geolocation, :point) AS distanceFromMyPosition " +
          "FROM TrashCan tc " +
          "WHERE ST_CONTAINS(ST_BUFFER(tc.geolocation, :radius), :point) " +
          "AND tc.geolocation IS NOT NULL")
  List<Map<String, Object>> findRoutesWithinRadius(@Param("point") Point point, @Param("radius") int radius);

  default List<SearchNearbyTrashCanResponse> findNearbyTrashCanList(Point point, int radius) {
    List<Map<String, Object>> result = findRoutesWithinRadius(point, radius);
    return result.stream().map(row -> SearchNearbyTrashCanResponse.builder()
            .seq((Long) row.get("seq"))
            .detail(row.get("detail") != null ? row.get("detail").toString() : null)
            .roadName(row.get("roadName") != null ? row.get("roadName").toString() : null)
            .spot(row.get("spot") != null ? row.get("spot").toString() : null)
            .type(row.get("type") != null ? row.get("type").toString() : null)
            .geolocation(new CoordinateDto((Double) row.get("latitude"), (Double) row.get("longitude")))
            .distanceFromMyPosition((Double) row.get("distanceFromMyPosition"))
            .build()).collect(Collectors.toList());
  }
}
