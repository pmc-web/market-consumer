package com.pmc.market.controller;

import com.pmc.market.model.DownloadRequestDto;
import com.pmc.market.service.GCSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Blob;

@RestController
@RequiredArgsConstructor
public class GCSController {

    private final GCSService gcsService;

    @PostMapping("gcs/download")
    public ResponseEntity<?> loadDownloadFromStroage(@RequestBody DownloadRequestDto downloadReqDto) {
        Blob fileFormGCS = gcsService.downloadFileFromGCS(downloadReqDto);
        return ResponseEntity.ok(fileFormGCS.toString());
    }
}
