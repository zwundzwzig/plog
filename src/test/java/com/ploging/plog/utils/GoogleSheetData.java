//package com.ploging.plog.utils;
//
//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.services.sheets.v4.Sheets;
//import com.google.api.services.sheets.v4.SheetsScopes;
//import com.google.api.services.sheets.v4.model.ValueRange;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.security.GeneralSecurityException;
//import java.util.Collections;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class GoogleSheetData {
//    public GoogleSheetData(Sheets sheetsService) {
//        this.sheetsServcie = sheetsService;
//    }
//
//    @Test
//    public List<List<Object>> getSheetData() throws IOException {
//        ValueRange response = sheetsServcie.spreadsheets().values()
//                .get(SPREADSHEET_ID, RANGE)
//                .execute();
//
//        List<List<Object>> values = response.getValues();
//        if (values == null || values.isEmpty()) {
//            throw new RuntimeException("No data found in the sheet.");
//        }
//
//        return values;
//    }
//
////    @Test
////    public void getData() throws IOException, GeneralSecurityException {
////        Sheets service = createSheetsService();
////
////        ValueRange response = service.spreadsheets().values()
////                .get(SPREADSHEET_ID, range)
////                .execute();
////        List<List<Object>> values = response.getValues();
////        if (values == null || values.isEmpty()) {
////            System.out.println("No data found.");
////        } else {
////            for (List<Object> row : values) {
////                for (Object cell : row) {
////                    System.out.print(cell + "\t");
////                }
////                System.out.println();
////            }
////        }
////    }
//
////    @Test
////    private static Sheets createSheetsService() throws IOException, GeneralSecurityException {
////        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
////        Credential credential = authorize(httpTransport);
////        return new Sheets.Builder(httpTransport, JSON_FACTORY, credential)
////                .setApplicationName(APPLICATION_NAME)
////                .build();
////    }
//
//
////    @Test
////    private static Credential authorize(HttpTransport httpTransport) throws IOException {
////        InputStream in = GoogleSheetData.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
////        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
////
////        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
////                httpTransport, JSON_FACTORY, clientSecrets, Collections.singleton(SheetsScopes.SPREADSHEETS_READONLY))
////                .build();
////
////        return new GoogleCredential.Builder()
////                .setTransport(httpTransport)
////                .setJsonFactory(JSON_FACTORY)
////                .setClientSecrets(clientSecrets)
////                .build()
////                .setAccessToken("YOUR_ACCESS_TOKEN"); // 필요한 경우 직접 액세
////
////    }
//}