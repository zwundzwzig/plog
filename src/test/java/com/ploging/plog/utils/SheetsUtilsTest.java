package com.ploging.plog.utils;

import com.google.api.services.sheets.v4.Sheets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SheetsUtilsTest {

    @Autowired
    private SheetsUtils sheetsUtils;

    @Test
    public void getsheet_service방식() throws GeneralSecurityException, IOException {
        Sheets sheets = sheetsUtils.getSheetsFromServiceAccount();
        assertThat(sheets.getApplicationName()).isEqualTo("sokuri");
    }

}