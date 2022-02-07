package com.pmc.market.domain.shop.repository;

import com.pmc.market.domain.shop.dto.ShopResponseDto;
import com.pmc.market.domain.shop.entity.Favorite;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;

import static com.pmc.market.domain.shop.entity.QFavorite.favorite;
import static com.pmc.market.domain.shop.entity.QShop.shop;

@RequiredArgsConstructor
public class FavoriteRepositoryImpl implements FavoriteRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Favorite> findFavoriteByShopIdAndUserId(long shopId, long userId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(favorite)
                .where(favorite.shop.id.eq(shopId),
                        favorite.user.id.eq(userId))
                .fetchOne());
    }

    @Override
    public Page<ShopResponseDto> findShopsMostFavoriteCount(Pageable pageable) {
        List<ShopResponseDto> content = queryFactory
                .select(Projections.constructor(ShopResponseDto.class,
                        shop.count().as("likes"),
                        favorite.shop))
                .from(favorite)
                .leftJoin(favorite.shop, shop)
                .groupBy(favorite.shop)
                .orderBy(shop.count().desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(favorite.count())
                .from(favorite)
                .leftJoin(favorite.shop, shop)
                .groupBy(favorite.shop)
                .orderBy(shop.count().desc());
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
