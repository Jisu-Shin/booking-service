package com.booking_service.repository;

import com.booking_service.domain.Booking;
import com.booking_service.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaItemRepository extends JpaRepository<Item, Long> {
    List<Booking> findByNameLike(String search);
}
