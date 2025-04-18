package com.booking_service.repository;

import com.booking_service.domain.Booking;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.booking_service.domain.QBooking.booking;

@Repository
public class BookingQueryDslImpl implements BookingQueryDsl {

    private final JPAQueryFactory queryFactory;
    public BookingQueryDslImpl(EntityManager entityManager) {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Booking> findAll(BookingSearch bookingSearch) {

        BooleanBuilder builder = new BooleanBuilder();
        if (bookingSearch.getBookingStatus() != null) {
            builder.and(booking.status.eq(bookingSearch.getBookingStatus()));
        }

        if (bookingSearch.getItemId() != null) {
            builder.and(booking.item.id.eq(bookingSearch.getItemId()));
        }

        return queryFactory
                .selectFrom(booking)
                .where(builder)
                .limit(100)
                .fetch();
    }
}
