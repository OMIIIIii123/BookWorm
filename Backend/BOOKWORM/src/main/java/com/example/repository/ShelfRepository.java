package com.example.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.MyShelf;

public interface ShelfRepository extends JpaRepository<MyShelf,Integer>{
	List<MyShelf> findByCustomerIdAndTranType (int userId, char tranType);
	
	List<MyShelf> findByCustomerIdAndTranTypeIn (int CustomerId , List<Character> tranTypes);
	
	  boolean existsByCustomerIdAndProductProductIdAndTranType(
	            int customerId,
	            int productId,
	            char tranType
	    );
	  
	  Optional<MyShelf> findByCustomerIdAndProductProductIdAndTranType(int CustomerId, int ProductId, char tranType);
	  

}
