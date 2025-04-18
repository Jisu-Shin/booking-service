package com.booking_service.api;

import com.booking_service.dto.BookingCreateRequestDto;
import com.booking_service.dto.BookingListResponseDto;
import com.booking_service.repository.BookingSearch;
import com.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class BookingApiController {

    private final BookingService bookingService;

    @PostMapping("/{id}/cancel")
    public Long cancelBooking(@PathVariable("id")Long id) {
        return bookingService.cancelBooking(id);
    }

    @GetMapping("/search")
    public List<BookingListResponseDto> searchBooking(@ModelAttribute BookingSearch bookingSearch) {
        return bookingService.findBooking(bookingSearch);
    }

    @PostMapping("/new")
    public Long booking(@RequestBody BookingCreateRequestDto requestDto) {
        return bookingService.book(requestDto);
    }

}
