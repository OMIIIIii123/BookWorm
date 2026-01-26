package com.example.controller;
 import java.util.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
 
import org.springframework.web.bind.annotation.RestController;

import com.example.model.MyShelf;
import com.example.model.ShelfRequest;
import com.example.service.ShelfService;

@RestController
@RequestMapping( "/api")
@CrossOrigin(origins="http://localhost:5173")
public class ShelfController {

	private final ShelfService shelfserv;
	public ShelfController(ShelfService shelfserv) {
		this.shelfserv = shelfserv;
	}
	//shelf
	@GetMapping("/shelf/{CustomerId}")
	public List<MyShelf> getmyshelf (@PathVariable int CustomerId){
		return shelfserv.getmyshelf(CustomerId);
	}
	
	@PostMapping("/shelf/add")
	public MyShelf addToShelf(@RequestBody ShelfRequest req) {
		return shelfserv.addToShelf(
				req.getProductId(),
				req.getCustomerId(),
				req.getTranType(),
				req.getRentDays()
				);
	}
//	@DeleteMapping("/shelf/remove")
//public String removeFromShelf(@RequestParam int customerId,
//							@RequestParam int productId,
//							@RequestParam char tranType
//		) {
//		 shelfserv.removeFromShelf(customerId, productId, tranType);
//		 return"Removed Successfully";
//}
//	
	
	@DeleteMapping("/shelf/remove")
	public String removeFromShelf(@RequestBody ShelfRequest req) {

	    shelfserv.removeFromShelf(
	            req.getCustomerId(),
	            req.getProductId(),
	            req.getTranType()
	    );

	    return "Removed Successfully";
	}

}
