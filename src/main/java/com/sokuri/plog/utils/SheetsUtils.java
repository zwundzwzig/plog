package com.sokuri.plog.utils;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.ServiceAccountCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

@Component
public class SheetsUtils {
    @Value("${api.google.app-name}")
    private String appName;
    @Value("${api.google.service-account-file-path}")
    private String SERVICE_ACCOUNT_FILE_PATH;

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public Sheets getSheetsFromServiceAccount() throws GeneralSecurityException, IOException {
        ServiceAccountCredentials serviceCredentials = ServiceAccountCredentials.fromStream(new FileInputStream("src/main/resources/" + SERVICE_ACCOUNT_FILE_PATH));
        serviceCredentials = (ServiceAccountCredentials) serviceCredentials.createScoped(Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY));

        Sheets service = new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                new HttpCredentialsAdapter(serviceCredentials)
        ).setApplicationName(appName).build();

        return service;
    }
}