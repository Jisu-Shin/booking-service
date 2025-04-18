package com.booking_service.api;

import com.booking_service.dto.ItemCreateRequestDto;
import com.booking_service.dto.ItemGetResponseDto;
import com.booking_service.dto.ItemUpdateRequestDto;
import com.booking_service.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemApiController {

    private final ItemService itemService;

    @GetMapping()
    public List<ItemGetResponseDto> findAll() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public ItemGetResponseDto getItem(@PathVariable("id")Long id){
        return itemService.findById(id);
    }

    @PostMapping("/new")
    public void create(@RequestBody ItemCreateRequestDto requestDto) {
        itemService.saveItem(requestDto.toEntity());
    }

    @PostMapping("/{id}")
    public void update(@PathVariable("id")Long id, ItemUpdateRequestDto requestDto) {
        itemService.updateItem(requestDto);
    }

}
