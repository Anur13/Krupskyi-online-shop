package com.andrew.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class Product {
    private String name;
    private double price;
    private LocalDate date;
    private long id;
    private String description;
}
