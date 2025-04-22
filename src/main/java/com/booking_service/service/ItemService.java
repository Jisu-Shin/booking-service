package com.booking_service.service;

import com.booking_service.domain.Item;
import com.booking_service.dto.ItemGetResponseDto;
import com.booking_service.dto.ItemUpdateRequestDto;
import com.booking_service.repository.JpaItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    public final JpaItemRepository jpaItemRepository;

    @Transactional
    public Long saveItem(Item item) {
        jpaItemRepository.save(item);
        return item.getId();
    }

    public List<ItemGetResponseDto> findAll() {
        return jpaItemRepository.findAll().stream()
                .map(ItemGetResponseDto::new)
                .collect(Collectors.toList());
    }

    public ItemGetResponseDto findById(Long id) {
        Item item = jpaItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 공연이 없습니다."));
        return new ItemGetResponseDto(item);
    }

    @Transactional
    public Long updateItem(ItemUpdateRequestDto requestDto) {
        Item findItem = jpaItemRepository.findById(requestDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 공연이 없습니다."));
        findItem.update(requestDto.getName(), requestDto.getPrice(), requestDto.getStockQuantity());
        return findItem.getId();
    }
}
