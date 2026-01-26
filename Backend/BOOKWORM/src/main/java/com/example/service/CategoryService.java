package com.example.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Product;
import com.example.repository.ProductRepository;

@Service
public class CategoryService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProducts(Integer genreId, Integer languageId) {

        if (genreId == null && languageId == null) {
            return productRepository.findAllProducts();
        }
        if (genreId != null && languageId == null) {
            return productRepository.findByGenre(genreId);
        }
        if (genreId == null) {
            return productRepository.findByLanguage(languageId);
        }
        return productRepository.findByGenreAndLanguage(genreId, languageId);
    }
    public Map<String, List<Product>> getProductsGroupedByGenre(
            Integer genreId, Integer languageId) {

        List<Product> products = getProducts(genreId, languageId);

        return products.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getGenre().getGenreDesc()
                ));
    }
}

