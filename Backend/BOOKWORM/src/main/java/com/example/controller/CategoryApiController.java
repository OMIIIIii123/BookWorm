package com.example.controller;


import com.example.model.*;
import com.example.repository.*;
import com.example.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
public class CategoryApiController {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private CategoryService categoryService;

    // =========================
    // GENRES
    // =========================
    @GetMapping("/genres")
    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    // =========================
    // LANGUAGES
    // =========================
    @GetMapping("/languages")
    public List<Language> getLanguages() {
        return languageRepository.findAll();
    }

    // =========================
    // PRODUCTS (flat list)
    // =========================
    @GetMapping("/products")
    public List<Product> getProducts(
            @RequestParam(required = false) Integer genreId,
            @RequestParam(required = false) Integer languageId
    ) {
        return categoryService.getProducts(genreId, languageId);
    }

    // =========================
    // PRODUCTS GROUPED BY GENRE
    // =========================
    @GetMapping("/products/grouped")
    public Map<String, List<Product>> getProductsGrouped(
            @RequestParam(required = false) Integer genreId,
            @RequestParam(required = false) Integer languageId
    ) {
        return categoryService.getProductsGroupedByGenre(
                genreId, languageId);
    }
}

