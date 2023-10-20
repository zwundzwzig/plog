package com.sokuri.plog.utils;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
@RequiredArgsConstructor
class GoogleApiUtilTest {

    @Value("${api.google.app-name}")
    private String APPLICATION_NAME;
    @Value("${api.google.credentials-file-path}")
    private String CREDENTIALS_FILE_PATH;
    @Value("${api.google.tokens-directory-path}")
    private String TOKENS_DIRECTORY_PATH;
    @Value("${spring.mail.username}")
    private String AUTHORIZE_MAIL;
    @Value("${server.port}")
    private int SERVER_PORT;

    @Autowired
    private GoogleApiUtil googleApiUtil;

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    /**
     * Global instance of the scopes required by this quickstart. If modifying these
     * scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);

    @Test
    public void 프로퍼티_가져오는지_테스트() {
        System.out.println(APPLICATION_NAME);
        System.out.println(CREDENTIALS_FILE_PATH);
        System.out.println(TOKENS_DIRECTORY_PATH);
        System.out.println(AUTHORIZE_MAIL);
        System.out.println(SERVER_PORT);

        assertThat(APPLICATION_NAME).isNotBlank();
        assertThat(CREDENTIALS_FILE_PATH).isNotBlank();
        assertThat(TOKENS_DIRECTORY_PATH).isNotBlank();
        assertThat(AUTHORIZE_MAIL).isNotBlank();
        assertThat(SERVER_PORT).isGreaterThan(0);
    }

    @Test
    public void 최초_접근_토큰_받아오기() throws IOException {
        File initialFile = new File("src/main/resources/" + CREDENTIALS_FILE_PATH);
        InputStream in = new FileInputStream(initialFile);

        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(), JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(SERVER_PORT).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize(AUTHORIZE_MAIL);

        System.out.println(clientSecrets);
        System.out.println(credential.getAccessToken());
        System.out.println(credential.getRefreshToken());

        assertThat(clientSecrets).isNotNull();
        assertThat(credential.getAccessToken()).isNotBlank();
        assertThat(credential.getTokenServerEncodedUrl()).isEqualTo("https://accounts.google.com/o/oauth2/token");
        assertThat(credential.getRefreshToken()).isNotNull();
        assertThat(credential.refreshToken()).isTrue();

    }

    @Test
    public void 시트_접근_테스트() throws GeneralSecurityException, IOException {
        Map<Object, Object> sheetData = googleApiUtil.getDataFromSheet();
        AtomicBoolean isFirstRow = new AtomicBoolean(true);

        sheetData.entrySet().stream()
                .forEach(entry -> {
                    if (isFirstRow.getAndSet(false))
                        System.out.println("Column output: " + entry.getKey());
                    else System.out.println("Row output: " + entry.getKey());
        });

        System.out.println("-----------------");

        assertThat(sheetData).isNotNull();
        assertThat(sheetData.size()).isGreaterThan(1);
    }

}