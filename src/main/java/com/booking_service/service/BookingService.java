package com.booking_service.service;

import com.booking_service.domain.Booking;
import com.booking_service.domain.Item;
import com.booking_service.dto.BookingCreateRequestDto;
import com.booking_service.dto.BookingListResponseDto;
import com.booking_service.repository.BookingSearch;
import com.booking_service.repository.JpaBookingRepository;
import com.booking_service.repository.JpaItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookingService {

    private final JpaBookingRepository jpaBookingRepository;
    private final JpaItemRepository jpaItemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long book(BookingCreateRequestDto requestDto) {
        //엔티티조회
        Item item = jpaItemRepository.findById(requestDto.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("해당 공연이 없습니다: " + requestDto.getItemId()));

        //예약 생성
        Booking booking = Booking.createBooking(requestDto.getCustId(), item, requestDto.getCount());

        //예약 저장
        jpaBookingRepository.save(booking);

        return booking.getId();
    }

    /**
     * 예약 취소
     */
    @Transactional
    public Long cancelBooking(Long bookingId) {
        //예약 엔티티 조회
        Booking booking = jpaBookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약이 없습니다: " + bookingId));

        // 예약 취소
        booking.cancel();
        return bookingId;
    }

    /**
     * 검색
     */
    public List<BookingListResponseDto> findBooking(BookingSearch bookingSearch) {
        return jpaBookingRepository.findAll(bookingSearch).stream()
                .map(BookingListResponseDto::new)
                .collect(Collectors.toList());
    }
}
