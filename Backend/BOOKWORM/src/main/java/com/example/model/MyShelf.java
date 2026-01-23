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

    @Column(name = "customer_id", nullable = false)
    private int customerId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "tran_type", length = 1, nullable = false)
    private char tranType;   // e.g. B = Buy, R = Rent

    @Column(name = "expiry_date")
    private LocalDate productExpiryDate;

    // -------- GETTERS & SETTERS --------

    public int getShelfId() {
        return shelfId;
    }

    public void setShelfId(int shelfId) {
        this.shelfId = shelfId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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
