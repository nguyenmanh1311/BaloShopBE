package com.hcmute.baloshop.services.implement;

import com.hcmute.baloshop.dto.invoicedetail.InvoiceDetailResponseDTO;
import com.hcmute.baloshop.entities.*;
import com.hcmute.baloshop.exceptions.ResourceNotFoundException;
import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.repositories.*;
import com.hcmute.baloshop.services.InvoiceDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InvoiceDetailServiceImpl implements InvoiceDetailService {
    @Autowired
    private final InvoicesRepository invoicesRepository;
    @Autowired
    private final InvoiceDetailRepository invoiceDetailRepository;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final AddressRepository addressRepository;

    @Override
    public ResponseEntity<ResponseObject> getByInvoiceId(Long invoiceId) {
        Optional<Invoices> invoice = this.invoicesRepository.findById(invoiceId);
        if (!invoice.isPresent())
            throw new ResourceNotFoundException("Invoice not found");
        List<InvoiceDetail> invoiceDetailList = this.invoiceDetailRepository.findAllByInvoiceId(invoiceId);
        if (invoiceDetailList.isEmpty())
            throw new ResourceNotFoundException("Invoice detail not found");
        List<InvoiceDetailResponseDTO> invoiceDetailListDto = new ArrayList<>();
        for (InvoiceDetail item : invoiceDetailList) {
            InvoiceDetailResponseDTO invoiceDetailResponseDTO = new InvoiceDetailResponseDTO();
            invoiceDetailResponseDTO.setInvoiceId(invoiceId);
            invoiceDetailResponseDTO.setPrice(item.getPrice());
            invoiceDetailResponseDTO.setQuantity(item.getQuantity());
            invoiceDetailResponseDTO.setId(item.getId());
            Optional<Product> product = this.productRepository.findById(item.getProductId());
            invoiceDetailResponseDTO.setProductName(product.get().getName());
            invoiceDetailResponseDTO.setProductImage(product.get().getImage());
            invoiceDetailListDto.add(invoiceDetailResponseDTO);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Get successfully", invoiceDetailListDto));
    }
}
