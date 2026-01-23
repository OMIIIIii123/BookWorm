package com.example.controller;

import com.example.model.Cart;
import com.example.service.ICartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    // 1️⃣ Add product to cart / update quantity
    @PostMapping("/add")
    public Cart addToCart(
            @RequestParam int userId,
            @RequestParam int productId,
            @RequestParam int quantity) {

        return cartService.addOrUpdateProduct(userId, productId, quantity);
    }

    // 2️⃣ Get all cart items of a customer
    @GetMapping("/{userId}")
    public List<Cart> getCartByCustomerId(
            @PathVariable int userId) {

        return cartService.getCartByuserId(userId);
    }

    // 3️⃣ Get specific product from cart
    @GetMapping("/{userId}/product/{productId}")
    public Optional<Cart> getCartItem(
            @PathVariable int userId,
            @PathVariable int productId) {

        return cartService.getCartItem(userId, productId);
    }

    // 4️⃣ Remove one product from cart
    @DeleteMapping("/{userId}/product/{productId}")
    public void removeProduct(
            @PathVariable int userId,
            @PathVariable int productId) {

        cartService.removeProduct(userId, productId);
    }

    // 5️⃣ Clear entire cart
    @DeleteMapping("/clear/{userId}")
    public void clearCart(
            @PathVariable int userId) {

        cartService.clearCart(userId);
    }

    // 6️⃣ Calculate cart total
    @GetMapping("/total/{userId}")
    public BigDecimal calculateTotal(
            @PathVariable int userId) {

        return cartService.calculateCartTotal(userId);
    }
    
    //===============================================================================================================
    @PutMapping("/{userId}/product/{productId}/decrease")
    public void reduceQuantity(
            @PathVariable int userId,
            @PathVariable int productId) {

        cartService.reduceQuantity(userId, productId);
    }

}
