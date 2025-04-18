package com.booking_service.repository;

import com.booking_service.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBookingRepository extends JpaRepository<Booking, Long>, BookingQueryDsl {
}
