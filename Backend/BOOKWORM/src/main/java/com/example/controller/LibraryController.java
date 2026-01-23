package com.example.controller;

import java.util.List;

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
@RequestMapping("/api/library")
public class LibraryController {

	private final ShelfService shelfserv;
	public LibraryController(ShelfService shelfserv) {
		this.shelfserv = shelfserv;
	}
	//library
		@GetMapping("/{CustomerId}")
		public List<MyShelf> getmylibrary( @PathVariable  int CustomerId){
			return shelfserv.getmylibrary(CustomerId);
		}
		   @PostMapping("/add")
		    public MyShelf addToLibrary(@RequestBody ShelfRequest req) {
		        return shelfserv.addToShelf(
		                req.getProductId(),
		                req.getCustomerId(),
		                req.getTranType(),   // 'P' or 'R'
		                req.getRentDays()
		        );
		       }
		    @DeleteMapping("/remove")
	        public String removeFromLibrary(@RequestBody ShelfRequest req) {
	            shelfserv.removeFromShelf(req.getCustomerId(), req.getProductId(), req.getTranType());
	            return "Removed Successfully";
	        }
}
