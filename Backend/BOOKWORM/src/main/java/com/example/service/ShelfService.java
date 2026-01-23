package com.example.service;
import java.time.LocalDate;
import java.util.*;

import org.springframework.stereotype.Service;

import com.example.model.MyShelf;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.repository.ShelfRepository;

@Service
public class ShelfService {

	 private final ProductRepository prorepo;
	private final ShelfRepository shelfrepos;
	public ShelfService(ShelfRepository selfrepos , ProductRepository prorepo) {
		this.shelfrepos = selfrepos;
		this.prorepo = prorepo;
	}
	//MyShelf
	public List<MyShelf> getmyshelf (int CustomerId){
		return shelfrepos.findByCustomerIdAndTranType(CustomerId, 'P');
	}
	//My Library
	public List<MyShelf> getmylibrary(int customerId) {

	    List<MyShelf> list = shelfrepos.findByCustomerIdAndTranTypeIn(customerId, List.of('R','L'));
	    LocalDate today = LocalDate.now();

	    return list.stream()
	            .filter(item -> item.getProductExpiryDate() == null ||
	                    !item.getProductExpiryDate().isBefore(today))
	            .toList();
	}

	public MyShelf addToShelf(int productId, int customerId , char tranType , Integer rentDays) {
		
		
		//duplicate check 
		 boolean alreadyExists = shelfrepos.existsByCustomerIdAndProductProductIdAndTranType(customerId, productId, tranType);

		    if (alreadyExists) {
		        throw new RuntimeException("Already exists in shelf (Duplicate blocked)");
		    }
		Product product = prorepo.findById(productId).orElseThrow(()-> new RuntimeException("Product Not Found"));
		
		MyShelf shelf = new MyShelf();
		shelf.setCustomerId(customerId);
		shelf.setProduct(product);
		shelf.setTranType(tranType);
		
		
		if(tranType == 'P') {
			shelf.setProductExpiryDate(null);
			
		} else {
			int days = (rentDays==null)?0: rentDays;
			shelf.setProductExpiryDate(LocalDate.now().plusDays(days));
		}
		return shelfrepos.save(shelf);
	}
	
	public void removeFromShelf(int customerId,int productId, char tranType) {
		
		MyShelf items = shelfrepos.findByCustomerIdAndProductProductIdAndTranType(customerId, productId,  tranType)
				.orElseThrow(()-> new RuntimeException("Item Not Found in Shelf"));
		
		shelfrepos.delete(items);
		
		
		
	}
}
