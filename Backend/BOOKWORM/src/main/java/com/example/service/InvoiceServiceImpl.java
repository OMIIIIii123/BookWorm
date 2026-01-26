package com.example.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Cart;
import com.example.model.Invoice;
import com.example.model.InvoiceDetail;
import com.example.model.InvoicePreviewItemDTO;
import com.example.model.InvoicePreviewResponseDTO;
import com.example.model.InvoiceResponseDTO;
import com.example.model.MyShelf;
import com.example.model.Product;
import com.example.repository.CartRepository;
import com.example.repository.InvoiceDetailRepository;
import com.example.repository.InvoiceRepository;
import com.example.repository.ShelfRepository;

import jakarta.transaction.Transactional;

@Service
public class InvoiceServiceImpl implements IInvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ShelfRepository shelfRepository;

    // ===============================
    // Invoice Preview (P + R + L)
    // ===============================
    @Override
    public InvoicePreviewResponseDTO previewInvoice(Integer userId) {

        List<Cart> cartItems = cartRepository.findByCustomerUserId(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        List<InvoicePreviewItemDTO> previewItems = new ArrayList<>();
        BigDecimal subTotal = BigDecimal.ZERO;

        for (Cart item : cartItems) {

            Product product = item.getProduct();
            InvoicePreviewItemDTO dto = new InvoicePreviewItemDTO();

            dto.setProductId(product.getProductId());
            dto.setProductName(product.getProductName());
            dto.setTranType(item.getTranType());

            // ===== RENT / LEND =====
            if ("R".equals(item.getTranType()) || "L".equals(item.getTranType())) {

                BigDecimal perDay = product.getRentPerDay();
                int days = item.getRentDays();
                BigDecimal total = perDay.multiply(BigDecimal.valueOf(days));

                dto.setQuantity(1);
                dto.setRentDays(days);
                dto.setUnitPrice(perDay);
                dto.setTotalPrice(total);

                subTotal = subTotal.add(total);
            }
            // ===== PURCHASE =====
            else {
                BigDecimal price = getEffectivePrice(product);
                BigDecimal total =
                        price.multiply(BigDecimal.valueOf(item.getQuantity()));

                dto.setQuantity(item.getQuantity());
                dto.setRentDays(0);
                dto.setUnitPrice(price);
                dto.setTotalPrice(total);

                subTotal = subTotal.add(total);
            }

            previewItems.add(dto);
        }

        // ===== TAX (can change later) =====
        BigDecimal tax = BigDecimal.ZERO; // or 0.18 * subtotal if GST
        BigDecimal grandTotal = subTotal.add(tax);

        InvoicePreviewResponseDTO response = new InvoicePreviewResponseDTO();
        response.setItems(previewItems);
        response.setSubTotal(subTotal);
        response.setTax(tax);
        response.setGrandTotal(grandTotal);

        return response;
    }

    // ===============================
    // Generate Invoice (P + R + L)
    // ===============================
    @Override
    @Transactional
    public InvoiceResponseDTO generateInvoice(Integer userId) {

        List<Cart> cartItems = cartRepository.findByCustomerUserId(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty. Cannot generate invoice.");
        }

        LocalDate today = LocalDate.now();
        BigDecimal totalAmount = BigDecimal.ZERO;

        // -------- INVOICE HEADER --------
        Invoice invoice = new Invoice();
        invoice.setUserId(userId);
        invoice.setInvoiceDate(today);
        invoice.setInvoiceAmount(BigDecimal.ZERO);
        invoice = invoiceRepository.save(invoice);

        List<InvoiceDetail> invoiceDetails = new ArrayList<>();

        // -------- PROCESS ITEMS --------
        for (Cart item : cartItems) {

            Product product = item.getProduct();
            InvoiceDetail detail = new InvoiceDetail();

            detail.setInvoice(invoice);
            detail.setProduct(product);
            detail.setProductName(product.getProductName());

            // ===== RENT / LEND =====
            if (item.getTranType() == 'R' || item.getTranType() == 'L') {

                BigDecimal perDay = product.getRentPerDay();
                int days = item.getRentDays();
                BigDecimal total = perDay.multiply(BigDecimal.valueOf(days));

                detail.setTranType(item.getTranType());   // 'R' or 'L'
                detail.setQuantity(1);
                detail.setBasePrice(perDay);              // per day rent
                detail.setSalePrice(total);               // total rent
                detail.setRentNoOfDays(days);

                totalAmount = totalAmount.add(total);

                // Shelf expiry = today + rent days
                MyShelf shelf = new MyShelf();
                shelf.setCustomerId(item.getCustomer().getUserId());
                shelf.setProduct(product);
                shelf.setProductExpiryDate(today.plusDays(days));
                shelf.setTranType(item.getTranType());

                shelfRepository.save(shelf);
            }

            // ===== PURCHASE =====
            else {

                BigDecimal price = getEffectivePrice(product);
                BigDecimal saleTotal =
                        price.multiply(BigDecimal.valueOf(item.getQuantity()));

                detail.setTranType('P');
                detail.setQuantity(item.getQuantity());
                detail.setBasePrice(price);
                detail.setSalePrice(saleTotal);
                detail.setRentNoOfDays(0);

                totalAmount = totalAmount.add(saleTotal);

                MyShelf shelf = new MyShelf();
                shelf.setCustomerId(item.getCustomer().getUserId());
                shelf.setProduct(product);
                shelf.setProductExpiryDate(today);
                shelf.setTranType('P');
                shelfRepository.save(shelf);
            }

            invoiceDetailRepository.save(detail);
            invoiceDetails.add(detail);
        }

        // -------- FINALIZE --------
        invoice.setInvoiceAmount(totalAmount);
        invoiceRepository.save(invoice);

        cartRepository.deleteByCustomerUserId(userId);

        InvoiceResponseDTO response = new InvoiceResponseDTO();
        response.setInvoice(invoice);
        response.setInvoiceDetails(invoiceDetails);

        return response;
    }

    // ===============================
    // Fetch Invoices
    // ===============================
    @Override
    public List<Invoice> getInvoicesByCustomer(Integer userId) {
        return invoiceRepository.findByUserId(userId);
    }

    @Override
    public Invoice getInvoiceById(Integer invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    // ===============================
    // Effective Price Helper
    // ===============================
    private BigDecimal getEffectivePrice(Product product) {

        LocalDate today = LocalDate.now();

        if (product.getOfferPrice() != null
                && product.getOfferExpiryDate() != null
                && !product.getOfferExpiryDate().isBefore(today)) {

            return product.getOfferPrice();
        }

        return product.getSpCost();
    }
}
