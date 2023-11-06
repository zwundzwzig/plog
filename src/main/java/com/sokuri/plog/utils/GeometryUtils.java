package com.sokuri.plog.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeometryUtils {
  public Point createPoint(Double latitude, Double longitude) {
//    GeometryFactory geometryFactory = new GeometryFactory();
    GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
    Coordinate coordinate = new Coordinate(longitude, latitude);
    return geometryFactory.createPoint(coordinate);
  }
}
