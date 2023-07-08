package com.ploging.plog.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GoogleSheetsDto {

    private String sheetName;
    private List<List<Object>> dataToBeUpdated;
    private List<String> emails;

}
