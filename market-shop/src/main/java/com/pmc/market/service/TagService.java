package com.pmc.market.service;

import com.pmc.market.model.dto.ShopTagResponseDto;
import com.pmc.market.model.dto.TagRequestDto;
import com.pmc.market.model.dto.TagResponseDto;

import java.util.List;

public interface TagService {
    List<TagResponseDto> findAll();

    void makeTag(TagRequestDto tagRequestDto);

    void deleteTag(long id);

    List<TagResponseDto> findByWord(String searchWord);

    ShopTagResponseDto findByTagName(String tagName);

    ShopTagResponseDto findByTagId(long id);
}
