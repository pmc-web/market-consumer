package com.pmc.market.service;

import com.pmc.market.model.dto.SearchResponseDto;

import java.util.List;

public interface SearchService {
    List<SearchResponseDto> getPopularList(long daysAgo, int limit);

    void addSearchList(String word);
}
