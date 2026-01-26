//package com.example.services;
//
//import com.example.model.Cart;
//import com.example.model.Customer;
//import com.example.model.Product;
//import com.example.repository.CartRepository;
//import com.example.repository.CustomerRepository;
//import com.example.repository.ProductRepository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class CartService implements ICartService {
//
//    @Autowired
//    private CartRepository cartRepository;
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    // 1️⃣ Add product or update quantity
//    @Override
//    public Cart addOrUpdateProduct(int customerId, Product product, int quantity) {
//
//        Optional<Cart> existing =
//                cartRepository.findByCustomerCustomerIdAndProductProductId(
//                        customerId,
//                        product.getProductId()
//                );
//
//        if (existing.isPresent()) {
//            Cart cart = existing.get();
//            cart.setQuantity(cart.getQuantity() + quantity);
//            return cartRepository.save(cart);
//        }
//
//        Customer customer = customerRepository.findById(customerId)
//                .orElseThrow(() -> new RuntimeException("Customer not found"));
//
//        Cart cart = new Cart();
//        cart.setCustomer(customer);
//        cart.setProduct(product);
//        cart.setQuantity(quantity);
//
//        return cartRepository.save(cart);
//    }
//
//    // 2️⃣ Get all cart items of a customer
//    @Override
//    public List<Cart> getCartByCustomerId(int customerId) {
//        return cartRepository.findByCustomerCustomerId(customerId);
//    }
//
//    // 3️⃣ Get specific product from cart
//    @Override
//    public Optional<Cart> getCartItem(int customerId, int productId) {
//        return cartRepository.findByCustomerCustomerIdAndProductProductId(
//                customerId,
//                productId
//        );
//    }
//
//    // 4️⃣ Remove single product from cart
//    @Override
//    @Transactional
//    public void removeProduct(int customerId, int productId) {
//        cartRepository.deleteByCustomerCustomerIdAndProductProductId(
//                customerId,
//                productId
//        );
//    }
//
//    // 5️⃣ Clear entire cart
//    @Override
//    @Transactional
//    public void clearCart(int customerId) {
//        cartRepository.deleteByCustomerCustomerId(customerId);
//    }
//
//    // 6️⃣ Calculate cart total
//    @Override
//    public BigDecimal calculateCartTotal(int customerId) {
//        List<Cart> cartItems = getCartByCustomerId(customerId);
//
//        BigDecimal total = BigDecimal.ZERO;
//
//        for (Cart item : cartItems) {
//            BigDecimal price = item.getProduct().getPrice();
//            BigDecimal itemTotal =
//                    price.multiply(BigDecimal.valueOf(item.getQuantity()));
//            total = total.add(itemTotal);
//        }
//
//        return total;
//    }
//}




package com.example.service;

import com.example.model.Cart;
import com.example.model.Customer;
import com.example.model.Product;
import com.example.repository.CartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    // 1️⃣ Add product or update quantity
    @Override
    public Cart addOrUpdateProduct(int userId, int productId, int quantity) {

        Optional<Cart> existing =
                cartRepository.findByCustomerUserIdAndProductProductId(
                        userId, productId
                );

        if (existing.isPresent()) {
            Cart cart = existing.get();
            cart.setQuantity(cart.getQuantity() + quantity);
            return cartRepository.save(cart);
        }

        // BASIC: create proxy objects using only IDs
        Customer customer = new Customer();
        customer.setUserId(userId);//  maybe here

        Product product = new Product();
        product.setProductId(productId);

        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setProduct(product);
        cart.setQuantity(quantity);

        return cartRepository.save(cart);
    }

    // 2️⃣ Get all cart items
    @Override
    public List<Cart> getCartByuserId(int userId) {
        return cartRepository.findByCustomerUserId(userId);
    }

    // 3️⃣ Get one cart item
    @Override
    public Optional<Cart> getCartItem(int customerId, int productId) {
        return cartRepository.findByCustomerUserIdAndProductProductId(
                customerId, productId
        );
    }

    // 4️⃣ Remove one product
    @Override
    @Transactional
    public void removeProduct(int customerId, int productId) {
        cartRepository.deleteByCustomerUserIdAndProductProductId(
                customerId, productId
        );
    }

    // 5️⃣ Clear cart
    @Override
    @Transactional
    public void clearCart(int userId) {
        cartRepository.deleteByCustomerUserId(userId);
    }

    // 6️⃣ Calculate total
    @Override
    public BigDecimal calculateCartTotal(int userId) {

        List<Cart> cartItems = getCartByuserId(userId);
        BigDecimal total = BigDecimal.ZERO;

        for (Cart item : cartItems) {
            BigDecimal price = item.getProduct().getBasePrice(); // must exist
            BigDecimal itemTotal =
                    price.multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(itemTotal);
        }
        return total;
    }
    
    
    //=========================================================================================================
    
    @Override
    @Transactional
    public void reduceQuantity(int userId, int productId) {

        Cart cart = cartRepository
                .findByCustomerUserIdAndProductProductId(userId, productId)
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        if (cart.getQuantity() > 1) {
            cart.setQuantity(cart.getQuantity() - 1);
            cartRepository.save(cart);
        } else {
            // quantity == 1 → remove row
            cartRepository.delete(cart);
        }
    }

}

