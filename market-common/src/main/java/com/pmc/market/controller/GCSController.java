package com.pmc.market.controller;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.pmc.market.model.ResponseMessage;
import com.pmc.market.model.image.dto.DownloadRequestDto;
import com.pmc.market.model.image.dto.UploadRequestDto;
import com.pmc.market.service.GCSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class GCSController {

    private final GCSService gcsService;

    @PostMapping("/gcs/upload")
    public ResponseEntity<ResponseMessage> uploadToStorage(@RequestBody UploadRequestDto uploadRequestDto) throws IOException {
        BlobInfo blobInfo = gcsService.uploadFileToGCS(uploadRequestDto);
        return ResponseEntity.ok(ResponseMessage.success(blobInfo.toString()));
    }

    @PostMapping("/gcs/download")
    public ResponseEntity<ResponseMessage> loadDownloadFromStorage(@RequestBody DownloadRequestDto downloadReqDto) {
        Blob fileFormGCS = gcsService.downloadFileFromGCS(downloadReqDto);
        return ResponseEntity.ok(ResponseMessage.success(fileFormGCS.toString()));
    }
}
