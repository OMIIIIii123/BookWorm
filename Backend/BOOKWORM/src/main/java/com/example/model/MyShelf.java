package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "my_shelf")
public class MyShelf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shelf_id")
    private int shelfId;

    // userId maps to customer_id column (because old DB column name is customer_id)
    @Column(name = "user_id", nullable = false)
    private int userId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "tran_type", length = 1, nullable = false)
    private char tranType; // e.g. 'P' = purchase, 'R' = rent, 'L' = library

    @Column(name = "expiry_date")
    private LocalDate productExpiryDate;

    // -------- GETTERS & SETTERS --------

    public int getShelfId() {
        return shelfId;
    }

    public void setShelfId(int shelfId) {
        this.shelfId = shelfId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public char getTranType() {
        return tranType;
    }

    public void setTranType(char tranType) {
        this.tranType = tranType;
    }

    public LocalDate getProductExpiryDate() {
        return productExpiryDate;
    }

    public void setProductExpiryDate(LocalDate productExpiryDate) {
        this.productExpiryDate = productExpiryDate;
    }
}
