package com.example.jwt.interface_adapter.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
