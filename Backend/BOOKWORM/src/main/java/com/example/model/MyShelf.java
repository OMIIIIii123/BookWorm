package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "my_shelf")
public class MyShelf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shelf_id")
    private Integer shelfId;

    @Column(name = "user_id", nullable = false)
    private Integer customerId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "expiry_date")
    private LocalDate productExpiryDate;

    // ✅ ADD THIS (VERY IMPORTANT)
    @Column(name = "tran_type", length = 1, nullable = false)
    private char tranType;   // P / R / L

    // ========================
    // Getters & Setters
    // ========================

    public Integer getShelfId() {
        return shelfId;
    }

    public void setShelfId(Integer shelfId) {
        this.shelfId = shelfId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDate getProductExpiryDate() {
        return productExpiryDate;
    }

    public void setProductExpiryDate(LocalDate productExpiryDate) {
        this.productExpiryDate = productExpiryDate;
    }

    public char getTranType() {
        return tranType;
    }

    public void setTranType(char tranType) {
        this.tranType = tranType;
    }
}
