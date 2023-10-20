package com.sokuri.plog.domain.converter;

import com.sokuri.plog.domain.dto.CoordinateDto;
import com.sokuri.plog.domain.dto.MapResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class RoadNameAddressToCoordinateConverter {
  @Value("${naver.maps.clientId}")
  private String NAVER_MAPS_CLIENT_ID;
  @Value("${naver.maps.clientSecret}")
  private String NAVER_MAPS_CLIENT_SECRET;
  private final WebClient webClient;

  public RoadNameAddressToCoordinateConverter(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl("https://naveropenapi.apigw.ntruss.com/map-geocode/v2").build();
  }

  public static String setAddressForSummary(String fullAddress) {
    String[] address = fullAddress.split(" ");
    String cityOrDo = address[0];

    if (cityOrDo.length() > 3) cityOrDo = cityOrDo.substring(0, 2) + cityOrDo.substring(cityOrDo.length() - 1);
    return address.length > 1 ? cityOrDo + " " + address[1]
            : null;
  }

  public Mono<CoordinateDto> convertAddressToCoordinate(String roadAddress) {
    return webClient.get()
            .uri("/geocode?query={address}", roadAddress)
            .header("X-NCP-APIGW-API-KEY-ID", NAVER_MAPS_CLIENT_ID)
            .header("X-NCP-APIGW-API-KEY", NAVER_MAPS_CLIENT_SECRET)
            .retrieve()
            .bodyToMono(MapResponseDto.class)
            .flatMap(response -> {
              if (response.getAddresses() != null && !response.getAddresses().isEmpty()) {
                MapResponseDto.AddressDto addressDto = response.getAddresses().get(0);

                String buildingName = addressDto.getAddressElements().stream()
                        .filter(s -> s.getTypes().contains("BUILDING_NAME"))
                        .findFirst()
                        .map(MapResponseDto.AddressDto.AddressElement::getLongName)
                        .orElse(null);

                return Mono.just(
                        new CoordinateDto(
                                Double.parseDouble(addressDto.getY()),
                                Double.parseDouble(addressDto.getX()),
                                buildingName
                        )
                );
              } else {
                return Mono.error(new RuntimeException("주소를 찾을 수 없습니다."));
              }
            });
  }

  public Flux<CoordinateDto> convertMultipleAddressesToCoordinate(List<String> addresses) {
    return Flux.fromIterable(addresses)
            .flatMap(address -> webClient.get()
                    .uri("/geocode?query={address}", address)
                    .header("X-NCP-APIGW-API-KEY-ID", NAVER_MAPS_CLIENT_ID)
                    .header("X-NCP-APIGW-API-KEY", NAVER_MAPS_CLIENT_SECRET)
                    .retrieve()
                    .bodyToMono(CoordinateDto.class))
            .doOnError(error -> System.err.println("Error occurred: " + error.getMessage()));
  }
}
