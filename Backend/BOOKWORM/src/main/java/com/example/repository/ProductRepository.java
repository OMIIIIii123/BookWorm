package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Product;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByProductNameContainingIgnoreCaseOrProductEnglishNameContainingIgnoreCase(
            String productName,
            String productEnglishName,
            Pageable pageable
    );

    // Default: All books
    @Query("SELECT p FROM Product p")
    List<Product> findAllProducts();

    // Filter by Genre
    @Query("SELECT p FROM Product p WHERE p.genre.genreId = :genreId")
    List<Product> findByGenre(@Param("genreId") Integer genreId);

    // Filter by Language
    @Query("SELECT p FROM Product p WHERE p.language.languageId = :languageId")
    List<Product> findByLanguage(@Param("languageId") Integer languageId);

    // Filter by Genre + Language
    @Query("""
        SELECT p FROM Product p 
        WHERE p.genre.genreId = :genreId 
        AND p.language.languageId = :languageId
    """)
    List<Product> findByGenreAndLanguage(
            @Param("genreId") Integer genreId,
            @Param("languageId") Integer languageId
    );
}
