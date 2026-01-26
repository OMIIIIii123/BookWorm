package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.MyShelf;

public interface ShelfRepository extends JpaRepository<MyShelf, Integer> {

	List<MyShelf> findByUserIdAndTranType(int userId, char tranType);

	List<MyShelf> findByUserIdAndTranTypeIn(int userId, List<Character> tranTypes);

	boolean existsByUserIdAndProductProductIdAndTranType(
			int userId,
			int productId,
			char tranType);

	Optional<MyShelf> findByUserIdAndProductProductIdAndTranType(
			int userId,
			int productId,
			char tranType);

	List<MyShelf> findByTranTypeIn(List<Character> tranTypes);

}
