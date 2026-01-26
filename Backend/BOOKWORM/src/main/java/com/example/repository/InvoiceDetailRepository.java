package com.example.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.model.InvoiceDetail;

@Repository
public interface InvoiceDetailRepository 
        extends JpaRepository<InvoiceDetail, Integer> {

    List<InvoiceDetail> findByInvoice_InvoiceId(Integer invoiceId);
}
