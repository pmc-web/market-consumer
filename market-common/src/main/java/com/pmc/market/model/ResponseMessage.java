package com.pmc.market.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage<T> {
    private ResponseMessageHeader header;
    private T data;

    public static ResponseMessage fail(String message) {
        return ResponseMessage.builder()
                .header(ResponseMessageHeader.builder()
                        .result(false)
                        .resultCode("")
                        .message(message)
                        .status(HttpStatus.BAD_REQUEST.value()).build())
                .data(null)
                .build();
    }

    public static <T> ResponseMessage success(T data) {
        return ResponseMessage.builder()
                .header(ResponseMessageHeader.builder()
                        .result(true)
                        .resultCode("")
                        .message("")
                        .status(HttpStatus.OK.value()).build())
                .data(data)
                .build();
    }

    /**
     * data 없는 success
     **/
    public static ResponseMessage success() {
        return ResponseMessage.builder()
                .header(ResponseMessageHeader.builder()
                        .result(true)
                        .resultCode("")
                        .message("")
                        .status(HttpStatus.OK.value()).build())
                .data(null)
                .build();
    }

    public static ResponseMessage created(Object data) {
        return ResponseMessage.builder()
                .header(ResponseMessageHeader.builder()
                        .result(true)
                        .resultCode("")
                        .message("생성")
                        .status(HttpStatus.CREATED.value()).build())
                .data(null)
                .build();
    }
}
