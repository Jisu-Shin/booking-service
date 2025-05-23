package com.booking_service.service;

import com.booking_service.domain.Booking;
import com.booking_service.domain.BookingStatus;
import com.booking_service.domain.Concert;
import com.booking_service.domain.Item;
import com.booking_service.dto.BookingCreateRequestDto;
import com.booking_service.dto.BookingListResponseDto;
import com.booking_service.repository.BookingSearch;
import com.booking_service.repository.JpaBookingRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookingServiceTest {
    @Autowired
    EntityManager em;

    @Autowired
    BookingService bookingService;

    @Autowired
    JpaBookingRepository repository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Long custId = 1L;
        Item item = createItem("콘서트", 150000, 20);
        int bookingCount = 2;

        BookingCreateRequestDto requestDto = new BookingCreateRequestDto(custId, item.getId(), bookingCount);

        //when
        Long bookingId = bookingService.book(requestDto);

        //then
        Booking getBooking = repository.findById(bookingId)
                .orElseThrow(()-> new IllegalArgumentException());

        assertEquals(BookingStatus.BOOK, getBooking.getStatus());
        assertEquals(bookingCount*150000, getBooking.getTotalPrice());
        assertEquals(18, item.getStockQuantity());
    }

    private Item createItem(String name, int price, int stockQuantity) {
        Item item = new Concert();
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
        em.persist(item);
        return item;
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Long custId = 1L;
        Item item = createItem("제니 콘서트",220000,50);
        int bookingCount = 4;

        Long bookingId = bookingService.book(new BookingCreateRequestDto(custId, item.getId(), bookingCount));

        //when
        bookingService.cancelBooking(bookingId);

        //then
        Booking getBooking = repository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException());

        assertEquals(BookingStatus.CANCEL, getBooking.getStatus());
        assertEquals(50, item.getStockQuantity());


    }

    @Test
    public void 예약주문_수량초과() throws Exception {
        //given
        Long custId = 1L;
        Item item = createItem("콘서트", 150000, 1);

        int bookingCount = 4;

        //when

        //then
        assertThrows(Exception.class, () -> bookingService.book(new BookingCreateRequestDto(custId, item.getId(), bookingCount)));
    }

    @Test
    public void 검색조건없을경우() throws Exception {
        //given
        Long custId = 1L;
        Item item = createItem("지디콘서트",10000,20);
        bookingService.book(new BookingCreateRequestDto(custId, item.getId(), 1));
        bookingService.book(new BookingCreateRequestDto(custId, item.getId(), 4));
        bookingService.book(new BookingCreateRequestDto(custId, item.getId(), 2));

        //when
        BookingSearch bookingSearch = new BookingSearch();
        List<BookingListResponseDto> bookings = bookingService.findBooking(bookingSearch);

        //then
        assertEquals(3,bookings.size());
    }

    @Test
    public void 예약중_검색() throws Exception {
        //given
        Long custId = 1L;
        Long custId2 = 2L;
        Item item = createItem("지디콘서트",10000,20);
        bookingService.book(new BookingCreateRequestDto(custId, item.getId(), 1));
        bookingService.book(new BookingCreateRequestDto(custId2, item.getId(), 4));
        Long bookId = bookingService.book(new BookingCreateRequestDto(custId2, item.getId(), 2));

        bookingService.cancelBooking(bookId);

        //when
        BookingSearch bookingSearch = new BookingSearch();
        bookingSearch.setBookingStatus(BookingStatus.BOOK);
        List<BookingListResponseDto> bookings = bookingService.findBooking(bookingSearch);
        System.out.println(bookings.size());

        //then
        assertEquals(2,bookings.size());
    }

    @Test
    public void 예약취소_검색() throws Exception {
        //given
        Long custId = 1L;
        Long custId2 = 2L;
        Item item = createItem("지디콘서트",10000,20);
        bookingService.book(new BookingCreateRequestDto(custId, item.getId(), 1));
        bookingService.book(new BookingCreateRequestDto(custId2, item.getId(), 4));
        Long bookId = bookingService.book(new BookingCreateRequestDto(custId2, item.getId(), 2));

        bookingService.cancelBooking(bookId);

        //when
        BookingSearch bookingSearch = new BookingSearch();
        bookingSearch.setBookingStatus(BookingStatus.BOOK);
        List<BookingListResponseDto> bookings = bookingService.findBooking(bookingSearch);
        System.out.println(bookings.size());

        bookingSearch = new BookingSearch();
        bookingSearch.setBookingStatus(BookingStatus.CANCEL);
        List<BookingListResponseDto> cancelBookings = bookingService.findBooking(bookingSearch);

        //then
        assertEquals(1,cancelBookings.size());
    }

//    @Test
//    public void itemId로검색() throws Exception {
//        //given
//        Cust cust1 = createCust("고객1", "01012345678");
//        Item item = createItem("지디콘서트",10000,20);
//        Cust cust2 = createCust("고객2", "01098745612");
//        Item item2 = createItem("에스파콘서트",10000,20);
//        Long bookId1 = bookingService.book(cust1.getId(), item.getId(), 1);
//        Long bookId2 = bookingService.book(cust2.getId(), item2.getId(), 4);
//
//        //when
//        BookingSearch bookingSearch = new BookingSearch();
//        bookingSearch.setItemId(item.getId());
//
//        List<BookingListResponseDto> booking = bookingService.findBooking(bookingSearch);
//        booking.stream().forEach(b->System.out.println(b.getCustName()+","+b.getItemName()));
//
//        //then
//    }

}