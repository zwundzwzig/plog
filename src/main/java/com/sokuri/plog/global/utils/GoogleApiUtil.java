package com.sokuri.plog.global.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.sokuri.plog.global.dto.sheet.GoogleSheetResponseDto;
import com.sokuri.plog.global.dto.sheet.GoogleSheetsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.ValueRange;

import jakarta.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class GoogleApiUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    private static String CREDENTIALS_FILE_PATH;
    private static String TOKENS_DIRECTORY_PATH;
    private static String APPLICATION_NAME;
    private static String SPREAD_SHEET_ID;
    private static String SPREAD_SHEET_RANGE;
    private static int SERVER_PORT;

    @PostConstruct
    public void initializeVariable() {
        SERVER_PORT = Integer.parseInt(applicationContext.getEnvironment().getProperty("server.port"));
        CREDENTIALS_FILE_PATH = applicationContext.getEnvironment().getProperty("api.google.credentials-file-path");
        TOKENS_DIRECTORY_PATH = applicationContext.getEnvironment().getProperty("api.google.tokens-directory-path");
        APPLICATION_NAME = applicationContext.getEnvironment().getProperty("api.google.app-name");
        SPREAD_SHEET_ID = applicationContext.getEnvironment().getProperty("api.google.spread-sheet-id");
        SPREAD_SHEET_RANGE = applicationContext.getEnvironment().getProperty("api.google.spread-sheet-range");
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;
    }

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    /**
     * Global instance of the scopes required by this quickstart. If modifying these
     * scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS, SheetsScopes.DRIVE);

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GoogleApiUtil.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(
                        new java.io.File(System.getProperty("user.home"), TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline").build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(SERVER_PORT + 8).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public Map<Object, Map<String, Object>> getDataFromSheet() throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        Sheets service = getSheetService();
        ValueRange response = service.spreadsheets().values().get(SPREAD_SHEET_ID, SPREAD_SHEET_RANGE).execute();
        List<List<Object>> values = response.getValues();
        List<String> keyList = values.get(0).stream().map(Object::toString).collect(Collectors.toList());
        Map<Object, Map<String, Object>> storeDataFromGoogleSheet = new HashMap<>();
        if (values == null || values.isEmpty()) System.out.println("No data found.");
        else IntStream.range(1, values.size())
                .forEach(i -> {
                    Map<String, Object> mapForReturnData = new HashMap<>();
                    IntStream.range(0, values.get(i).size())
                            .forEach(j -> {
                                String cleanedValue = values.get(i).get(j).toString().replace("\n", "");
                                mapForReturnData.put(keyList.get(j), cleanedValue);
                            });
                    storeDataFromGoogleSheet.put(i, mapForReturnData);
                });
        return storeDataFromGoogleSheet;
    }

    private Sheets getSheetService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new Sheets.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport))
                .setApplicationName(APPLICATION_NAME).build();
    }

    private Drive getDriveService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new Drive.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport))
                .setApplicationName(APPLICATION_NAME).build();
    }

    public GoogleSheetResponseDto createGoogleSheet(GoogleSheetsDto request)
            throws GeneralSecurityException, IOException {
        Sheets service = getSheetService();
        SpreadsheetProperties spreadsheetProperties = new SpreadsheetProperties();
        spreadsheetProperties.setTitle(request.getSheetName());
        SheetProperties sheetProperties = new SheetProperties();
        sheetProperties.setTitle(request.getSheetName());
        Sheet sheet = new Sheet().setProperties(sheetProperties);
        Spreadsheet spreadsheet = new Spreadsheet().setProperties(spreadsheetProperties)
                .setSheets(Collections.singletonList(sheet));
        Spreadsheet createdResponse = service.spreadsheets().create(spreadsheet).execute();
        final GoogleSheetResponseDto googleSheetResponseDTO = new GoogleSheetResponseDto();
        ValueRange valueRange = new ValueRange().setValues(request.getDataToBeUpdated());
        service.spreadsheets().values().update(createdResponse.getSpreadsheetId(), "A1", valueRange)
                .setValueInputOption("RAW").execute();
        googleSheetResponseDTO.setSpreadSheetId(createdResponse.getSpreadsheetId());
        googleSheetResponseDTO.setSpeadSheetUrl(createdResponse.getSpreadsheetUrl());

        Drive driveService = getDriveService();
        request.getEmails().forEach(emailAddress -> {
            Permission permission = new Permission().setType("user").setRole("writer").setEmailAddress(emailAddress);
            try {
                driveService.permissions().create(createdResponse.getSpreadsheetId(), permission)
                        .setSendNotificationEmail(true).setEmailMessage("Google Sheet Permission testing");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return googleSheetResponseDTO;
    }
}