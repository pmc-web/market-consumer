package com.pmc.market.domain.user.repository;

import com.pmc.market.domain.user.dto.SearchResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.pmc.market.domain.user.entity.QSearch.search;

@RequiredArgsConstructor
public class SearchRepositoryImpl implements SearchRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SearchResponseDto> findPopular(LocalDateTime date, int limit) {
        return queryFactory
                .select(Projections.constructor(SearchResponseDto.class,
                        search.keyword.count(),
                        search.keyword))
                .from(search)
                .where(search.regDate.after(date))
                .groupBy(search.keyword)
                .orderBy(search.keyword.count().desc())
                .limit(limit)
                .fetch();
    }
}
