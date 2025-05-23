package com.booking_service.dto;

import com.booking_service.domain.Concert;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ItemCreateRequestDto {
    private String name;
    private int price;
    private int stockQuantity;

    public Concert toEntity() {
        Concert concert = new Concert();
        concert.setName(name);
        concert.setPrice(price);
        concert.setStockQuantity(stockQuantity);
        return concert;
    }

    @Override
    public String toString() {
        return "ItemCreateRequestDto{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}
