package com.pmc.market.service;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.pmc.market.model.image.dto.DownloadRequestDto;
import com.pmc.market.model.image.dto.UploadRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class GCSService {
    private final Storage storage;

    public Blob downloadFileFromGCS(DownloadRequestDto downloadReqDto) {

        Blob blob = storage.get(downloadReqDto.getBucketName(), downloadReqDto.getDownloadFileName());
        blob.downloadTo(Paths.get("market-common/" + downloadReqDto.getLocalFileLocation()));
        return blob;
    }

    public BlobInfo uploadFileToGCS(UploadRequestDto uploadRequestDto) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File("market-common/" + uploadRequestDto.getLocalFileLocation()));
        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(uploadRequestDto.getBucketName(), uploadRequestDto.getUploadFileName())
                        .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllAuthenticatedUsers(), Acl.Role.READER))))
                        .build(), inputStream);
        return blobInfo;
    }
}
