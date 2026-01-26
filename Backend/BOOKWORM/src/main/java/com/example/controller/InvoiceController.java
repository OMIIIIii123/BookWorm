package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Invoice;
import com.example.model.InvoicePreviewResponseDTO;
import com.example.model.InvoiceResponseDTO;
import com.example.repository.InvoiceDetailRepository;
import com.example.service.IInvoiceService;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private IInvoiceService invoiceService;
    
    
   

    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;


    // Preview invoice total
    @GetMapping("/preview/{userId}")
    public ResponseEntity<InvoicePreviewResponseDTO> previewInvoice(
            @PathVariable Integer userId) {

        return ResponseEntity.ok(invoiceService.previewInvoice(userId));
    }


    // Generate invoice (returns invoice + details)
    @PostMapping("/generate/{userId}")
    public InvoiceResponseDTO generateInvoice(@PathVariable Integer userId) {
        return invoiceService.generateInvoice(userId);
    }

    // Get all invoices of a customer
    @GetMapping("/customer/{userId}")
    public List<Invoice> getInvoicesByCustomer(@PathVariable Integer userId) {
        return invoiceService.getInvoicesByCustomer(userId);
    }

    // Get single invoice by id
    @GetMapping("/id/{invoiceId}")
    public Invoice getInvoiceById(@PathVariable Integer invoiceId) {
        return invoiceService.getInvoiceById(invoiceId);
    }

    @GetMapping("/test")
    public String test() {
        return "Invoice controller working";
    }
    
    

}
