package com.example.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.Customer;
import com.example.model.Product;
import com.example.model.ProductBeneficiary;
import com.example.model.RoyaltyTransaction;
import com.example.model.UserLibrarySubscription;
import com.example.repository.CustomerRepository;
import com.example.repository.ProductBeneficiaryRepository;
import com.example.repository.ProductRepository;
import com.example.repository.RoyaltyTransactionRepository;
import com.example.repository.UserLibrarySubscriptionRepository;

@Service
public class RoyaltyService {

    private final ProductBeneficiaryRepository productBenRepo;
    private final RoyaltyTransactionRepository royaltyRepo;
    private final ProductRepository productRepo;
    private final CustomerRepository customerRepo;
    private final UserLibrarySubscriptionRepository subscriptionRepo;

    public RoyaltyService(
            ProductBeneficiaryRepository productBenRepo,
            RoyaltyTransactionRepository royaltyRepo,
            ProductRepository productRepo,
            CustomerRepository customerRepo,
            UserLibrarySubscriptionRepository subscriptionRepo
    ) {
        this.productBenRepo = productBenRepo;
        this.royaltyRepo = royaltyRepo;
        this.productRepo = productRepo;
        this.customerRepo = customerRepo;
        this.subscriptionRepo = subscriptionRepo;
    }

    public void generateRoyalty(
            Integer productId,
            Integer userId,
            BigDecimal baseAmount,
            String source,
            Integer subscriptionId
    ) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Customer customer = customerRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        UserLibrarySubscription subscription = null;
        if (subscriptionId != null) {
            subscription = subscriptionRepo.findById(subscriptionId)
                    .orElseThrow(() -> new RuntimeException("Subscription not found"));
        }

        List<ProductBeneficiary> beneficiaries =
                productBenRepo.findByProductProductId(productId);

        for (ProductBeneficiary pb : beneficiaries) {

            RoyaltyTransaction rt = new RoyaltyTransaction();
            rt.setBeneficiary(pb.getBeneficiary());
            rt.setProduct(product);          // 
            rt.setCustomer(customer);        // 
            rt.setBeneficiary(pb.getBeneficiary());
            rt.setSubscription(subscription);//  
            rt.setSource(source);
            rt.setTransactionDate(LocalDate.now());

            royaltyRepo.save(rt);
        }
    }
}
