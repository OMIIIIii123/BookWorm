package com.example.service;

import java.math.BigDecimal;
import java.util.List;

import com.example.model.InvoiceResponseDTO;
import com.example.model.Invoice;
import com.example.model.InvoicePreviewResponseDTO;


public interface IInvoiceService {

  
	InvoicePreviewResponseDTO previewInvoice(Integer userId);


    
    InvoiceResponseDTO generateInvoice(Integer userId);

    
    List<Invoice> getInvoicesByCustomer(Integer userId);

    
    Invoice getInvoiceById(Integer invoiceId);
}
