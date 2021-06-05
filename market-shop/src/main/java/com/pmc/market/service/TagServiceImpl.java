package com.pmc.market.service;

import com.pmc.market.error.exception.EntityNotFoundException;
import com.pmc.market.model.dto.ShopTagResponseDto;
import com.pmc.market.model.dto.TagRequestDto;
import com.pmc.market.model.dto.TagResponseDto;
import com.pmc.market.repository.ShopTagRepository;
import com.pmc.market.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final ShopTagRepository shopTagRepository;

    @Override
    public List<TagResponseDto> findAll() {
        return tagRepository.findAll().stream().map(TagResponseDto::of).collect(Collectors.toList());
    }

    @Override
    public void makeTag(TagRequestDto tagRequestDto) {
        tagRepository.save(tagRequestDto.toEntity());
    }

    @Override
    public void deleteTag(long id) {
        try {
            tagRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("해당하는 태그가 없습니다.");
        }
    }

    @Override
    public List<TagResponseDto> findByWord(String searchWord) {
        return tagRepository.findByName(searchWord).stream().map(TagResponseDto::of).collect(Collectors.toList());
    }

    @Override
    public ShopTagResponseDto findByTagName(String tagName) {
        return ShopTagResponseDto.of(shopTagRepository.findByTagName(tagName));
    }

    @Override
    public ShopTagResponseDto findByTagId(long id) {
        return ShopTagResponseDto.of(shopTagRepository.findByTagId(id));
    }
}
