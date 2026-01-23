package services;

import com.example.model.Cart;
import com.example.repository.CartRepository;
import com.example.service.CartService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;   // ✅ Repository mocked

    @InjectMocks
    private CartService cartService;         // ✅ Service under test

    @Test
    void addOrUpdateProduct_whenProductNotInCart() {

        // arrange
        when(cartRepository.findByCustomerUserIdAndProductProductId(1, 101))
                .thenReturn(Optional.empty());

        Cart savedCart = new Cart();
        savedCart.setQuantity(2);

        when(cartRepository.save(any(Cart.class)))
                .thenReturn(savedCart);

        // act
        Cart result = cartService.addOrUpdateProduct(1, 101, 2);

        // assert
        assertNotNull(result);
        assertEquals(2, result.getQuantity());

        // verify repository interaction
        verify(cartRepository)
                .findByCustomerUserIdAndProductProductId(1, 101);
        verify(cartRepository)
                .save(any(Cart.class));
    }
}
