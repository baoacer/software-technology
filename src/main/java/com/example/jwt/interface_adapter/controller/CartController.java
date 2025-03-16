package com.example.jwt.interface_adapter.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.application.usecase.product.AddCartItemUseCase;
import com.example.jwt.application.usecase.product.GetUserCartUseCase;
import com.example.jwt.dto.request.CartItemRequest;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "${cors.allowed-origins}")
@RequestMapping("/api/v1/carts")
// @RequiredArgsConstructor
public class CartController {

  // @GetMapping("/{userId}")
  // public ResponseEntity<CartDto> getUserCart(@PathVariable String userId) {
  // return new ResponseEntity<>(
  // this.getUserCartUseCase.execute(userId),
  // HttpStatus.OK);
  // }

  // @PostMapping("/add")
  // public ResponseEntity<Map<String, String>> addProductToCart(@RequestBody
  // CartItemRequest item) {
  // this.addCartItemUseCase.execute(item);
  // return new ResponseEntity<>(
  // Map.of("message", "Product added to cart successfully"),
  // HttpStatus.CREATED);
  // }
}
