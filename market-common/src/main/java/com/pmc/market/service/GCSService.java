package com.pmc.market.service;

import com.pmc.market.model.DownloadRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.sql.Blob;

@Service
@RequiredArgsConstructor
public class GCSService {
    private final Stroage stroage;

    public Blob downloadFileFromGCS(DownloadRequestDto downloadReqDto) {
        Blob blob = storage.get("버켓이름", "버킷에서 다운로드할 파일 이름");
        blob.downloadTo(Paths.get("로컬에 저장할 파일 이름"));
        return blob;
    }
}
