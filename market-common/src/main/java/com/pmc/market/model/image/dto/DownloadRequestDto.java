package com.pmc.market.model.image.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DownloadRequestDto {
    private String bucketName;
    private String downloadFileName;
    private String localFileLocation;
}
