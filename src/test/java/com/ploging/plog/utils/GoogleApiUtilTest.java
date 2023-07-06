package com.ploging.plog.utils;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
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
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
@RequiredArgsConstructor
class GoogleApiUtilTest {

    private static final String APPLICATION_NAME = "sokuri";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens/path";

    /**
     * Global instance of the scopes required by this quickstart. If modifying these
     * scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);

    @Value("${api.google.app-name}")
    private String appName;

    @Value("${api.google.credentials-file-path}")
    private String CREDENTIALS_FILE_PATH;

    @Test
    public void 시트_앱_이름_테스트() {
        assertThat(appName).isEqualTo(APPLICATION_NAME);
    }

    @Test
    public void token_받아오기() throws IOException {
        File initialFile = new File("src/main/resources/" + CREDENTIALS_FILE_PATH);
        InputStream in = new FileInputStream(initialFile);

        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

//         Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(), JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8080).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("ploginglo@gmail.com");

        System.out.println("clientSecrets" + clientSecrets);
    }

    @Test
    public void getCredentialFile_에러유발() throws GeneralSecurityException, IOException {

        // given
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        CREDENTIALS_FILE_PATH += "plog"; // 임의로 path 변경함

        // when
        FileNotFoundException e = assertThrows(FileNotFoundException.class,()->{
            new Sheets.Builder(httpTransport, JSON_FACTORY, GoogleApiUtil.getCredentials(httpTransport))
                    .setApplicationName(appName)
                    .build();
        });

        // then
        assertThat(e.getMessage()).contains("Resource not found");

    }
}