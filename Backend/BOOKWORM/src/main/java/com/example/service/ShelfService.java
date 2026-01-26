package com.example.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.MyShelf;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.repository.ShelfRepository;

@Service
public class ShelfService {

	private final ProductRepository prorepo;
	private final ShelfRepository shelfrepos;
	private final RoyaltyTransactionService royaltyTransactionService;

	public ShelfService(ShelfRepository shelfrepos,
			ProductRepository prorepo,
			RoyaltyTransactionService royaltyTransactionService) {
		this.shelfrepos = shelfrepos;
		this.prorepo = prorepo;
		this.royaltyTransactionService = royaltyTransactionService;
	}

	// MyShelf
	public List<MyShelf> getmyshelf(int userId) {
		return shelfrepos.findByUserIdAndTranType(userId, 'P');
	}

	// My Library
	public List<MyShelf> getmylibrary(int userId) {

		List<MyShelf> list = shelfrepos.findByUserIdAndTranTypeIn(userId, List.of('R', 'L'));
		LocalDate today = LocalDate.now();

		return list.stream()
				.filter(item -> item.getProductExpiryDate() == null ||
						!item.getProductExpiryDate().isBefore(today))
				.toList();
	}

	public List<MyShelf> getAllShelfItems() {
		return shelfrepos.findAll();
	}

	public List<MyShelf> getAllLibraryItems() {
		return shelfrepos.findByTranTypeIn(List.of('R', 'L'));
	}

	public MyShelf addToShelf(int productId, int userId, char tranType, Integer rentDays) {

		boolean alreadyExists = shelfrepos.existsByUserIdAndProductProductIdAndTranType(userId, productId, tranType);
		if (alreadyExists) {
			throw new RuntimeException("Already exists in shelf (Duplicate blocked)");
		}

		Product product = prorepo.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product Not Found"));

		MyShelf shelf = new MyShelf();
		shelf.setUserId(userId);
		shelf.setProduct(product);
		shelf.setTranType(tranType);

		if (tranType == 'P') {
			shelf.setProductExpiryDate(null);
		} else {
			int days = (rentDays == null) ? 0 : rentDays;
			shelf.setProductExpiryDate(LocalDate.now().plusDays(days));
		}

		MyShelf saved = shelfrepos.save(shelf);

		// ✅ Royalty logic
		if (tranType == 'P') {
			// Purchase royalty on offerPrice (or you can use spCost)
			BigDecimal purchaseAmount = (product.getOfferPrice() != null)
					? product.getOfferPrice()
					: product.getSpCost();

			royaltyTransactionService.generateRoyalty(userId, product, 'P', purchaseAmount);
		} else if (tranType == 'R') {
			// Rent royalty on rentPerDay * rentDays
			int days = (rentDays == null) ? 0 : rentDays;

			BigDecimal rentAmount = product.getRentPerDay()
					.multiply(BigDecimal.valueOf(days));

			royaltyTransactionService.generateRoyalty(userId, product, 'R', rentAmount);
		}

		return saved;
	}

	public void removeFromShelf(int userId, int productId, char tranType) {

		MyShelf items = shelfrepos.findByUserIdAndProductProductIdAndTranType(userId, productId, tranType)
				.orElseThrow(() -> new RuntimeException("Item Not Found in Shelf"));

		shelfrepos.delete(items);
	}

	public MyShelf lendFromSubscription(int UserId, int productId) {

		boolean alreadyExists = shelfrepos.existsByUserIdAndProductProductIdAndTranType(
				UserId, productId, 'L');

		if (alreadyExists) {
			throw new RuntimeException("Book already exists in My Library");
		}

		Product product = prorepo.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product Not Found"));

		if (!product.isLibrary()) {
			throw new RuntimeException("Product not allowed in Library");
		}

		MyShelf shelf = new MyShelf();
		shelf.setUserId(UserId);
		shelf.setProduct(product);
		shelf.setTranType('L');

		shelf.setProductExpiryDate(null);

		return shelfrepos.save(shelf);
	}
}
