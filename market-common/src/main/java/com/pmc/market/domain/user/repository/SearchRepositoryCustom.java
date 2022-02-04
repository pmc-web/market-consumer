package com.pmc.market.domain.user.repository;


import com.pmc.market.domain.user.dto.SearchResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface SearchRepositoryCustom {

    List<SearchResponseDto> findPopular(LocalDateTime date, int limit);
}
