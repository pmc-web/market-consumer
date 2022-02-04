//package com.pmc.market.service;
//
//import com.google.cloud.storage.Acl;
//import com.google.cloud.storage.Blob;
//import com.google.cloud.storage.BlobInfo;
//import com.google.cloud.storage.Storage;
//import com.pmc.market.model.image.dto.DownloadRequestDto;
//import com.pmc.market.model.image.dto.UploadRequestDto;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class GCSService {
//
//    private static final String DEFAULT_GCS_URL = "https://storage.googleapis.com/";
//    private final Storage storage;
//
//    @Value("${gcp.bucket:market-universe-storage2}")
//    private String bucketName;
//
//    public Blob downloadFileFromGCS(DownloadRequestDto downloadReqDto) {
//        Blob blob = storage.get(downloadReqDto.getBucketName(), downloadReqDto.getDownloadFileName());
//        blob.downloadTo(Paths.get("market-common/" + downloadReqDto.getLocalFileLocation()));
//        return blob;
//    }
//
//    public BlobInfo uploadFileToGCS(UploadRequestDto uploadRequestDto) throws IOException {
//        FileInputStream inputStream = new FileInputStream(new File("market-common/" + uploadRequestDto.getLocalFileLocation()));
//        BlobInfo blobInfo = storage.create(
//                BlobInfo.newBuilder(uploadRequestDto.getBucketName(), uploadRequestDto.getUploadFileName())
//                        .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllAuthenticatedUsers(), Acl.Role.READER))))
//                        .build(), inputStream);
//        return blobInfo;
//    }
//
//    public String uploadFile(InputStream inputStream, String fileName) {
//        BlobInfo blobInfo = storage.create(
//                BlobInfo.newBuilder(bucketName, fileName)
//                        .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllAuthenticatedUsers(), Acl.Role.READER))))
//                        .build(), inputStream);
//        log.debug("image upload success {}", blobInfo.getBlobId());
//        return DEFAULT_GCS_URL + bucketName + "/" + fileName;
//    }
//}
