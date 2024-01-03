package com.sokuri.plog.global.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MapResponseDto {
  private String status;
  private Meta meta;
  private List<AddressDto> addresses;
  private String errorMessage;

  @Data
  public static class Meta {
    private int totalCount;
    private int page;
    private int count;
  }

  @Data
  public static class AddressDto {
    @JsonProperty("roadAddress")
    private String roadAddress;
    @JsonProperty("jibunAddress")
    private String jibunAddress;
    @JsonProperty("englishAddress")
    private String englishAddress;
    @JsonProperty("addressElements")
    private List<AddressElement> addressElements;
    private String x;
    private String y;
    private double distance;

    @Data
    public static class AddressElement {
      private List<String> types;
      private String longName;
      private String shortName;
      private String code;

    }
  }
}