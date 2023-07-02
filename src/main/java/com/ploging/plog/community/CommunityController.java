package com.ploging.plog.community;

import com.ploging.plog.utils.GoogleApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequiredArgsConstructor
public class CommunityController {

    private final GoogleApiUtil googleApiUtil;

    @GetMapping("/check")
    public String check() {
        return "Checking API";
    }

    @GetMapping("/getSheetData")
    public void getSheetData() throws GeneralSecurityException, IOException {
        googleApiUtil.getDataFromSheet();
    }

}
