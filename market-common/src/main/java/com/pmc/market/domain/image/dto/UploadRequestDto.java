package com.pmc.market.domain.image.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UploadRequestDto {
    private String bucketName;
    private String uploadFileName;
    private String localFileLocation;
}
