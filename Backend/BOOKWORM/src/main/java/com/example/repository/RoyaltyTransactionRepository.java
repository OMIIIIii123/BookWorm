package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.RoyaltyTransaction;

public interface RoyaltyTransactionRepository
        extends JpaRepository<RoyaltyTransaction, Integer> {
}
