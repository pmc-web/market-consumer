package com.pmc.market.service;

public interface SearchService {
    void getPopularList(int limit);

    void addSearchList(String word);
}
