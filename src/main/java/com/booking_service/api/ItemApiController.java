package com.booking_service.api;

import com.booking_service.dto.ItemCreateRequestDto;
import com.booking_service.dto.ItemGetResponseDto;
import com.booking_service.dto.ItemUpdateRequestDto;
import com.booking_service.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ItemApiController", description = "예약 항목 관련 rest api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemApiController {

    private final ItemService itemService;

    @Operation(summary = "예약 항목 전체 조회")
    @GetMapping()
    public List<ItemGetResponseDto> findAll() {
        return itemService.findAll();
    }

    @Operation(summary = "예약 항목 생성")
    @PostMapping()
    public Long create(@RequestBody ItemCreateRequestDto requestDto) {
        return itemService.saveItem(requestDto.toEntity());
    }

    @Operation(summary = "예약 항목 단건 조회")
    @GetMapping("/{id}")
    public ItemGetResponseDto getItem(@PathVariable("id")Long id){
        return itemService.findById(id);
    }

    @Operation(summary = "예약 항목 수정")
    @PostMapping("/{id}")
    public Long update(@PathVariable("id")Long id, ItemUpdateRequestDto requestDto) {
        return itemService.updateItem(requestDto);
    }

}
