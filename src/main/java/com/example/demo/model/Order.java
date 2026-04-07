package com.example.demo.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record Order(String orderId, String amount) {
}
