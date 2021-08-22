package com.andrew.security;

import com.andrew.entity.Product;
import com.andrew.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class Session {
    private LocalDateTime expireDate;
    private List<Product> cart;
    private User user;
}
