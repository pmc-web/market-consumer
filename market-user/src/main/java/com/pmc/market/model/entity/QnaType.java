package com.pmc.market.model.entity;

public enum QnaType {
    NORMAL("일반문의", 1);

    private String qnaType;
    private int qnaLevel;

    QnaType(String qnaType, int qnaLevel){
        this.qnaType = qnaType;
        this.qnaLevel = qnaLevel;
    }
}
