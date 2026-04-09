package com.example.demo.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    private String orderId;
    private String product;
    private Integer quantity;
    private String status;

}