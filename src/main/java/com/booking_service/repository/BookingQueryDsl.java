package com.booking_service.repository;

import com.booking_service.domain.Booking;

import java.util.List;

public interface BookingQueryDsl {
    public List<Booking> findAll(BookingSearch bookingSearch);
}
