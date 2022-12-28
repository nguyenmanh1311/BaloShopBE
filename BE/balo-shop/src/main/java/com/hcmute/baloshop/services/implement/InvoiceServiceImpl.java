package com.hcmute.baloshop.services.implement;

import com.hcmute.baloshop.dto.invoice.InvoiceRequestDto;
import com.hcmute.baloshop.dto.invoice.InvoiceResponseAdminDto;
import com.hcmute.baloshop.entities.*;
import com.hcmute.baloshop.exceptions.ResourceNotFoundException;
import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.repositories.*;
import com.hcmute.baloshop.services.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired private CartRepository cartRepository;
    @Autowired private CartDetailRepository cartDetailRepository;
    @Autowired private InvoicesRepository invoicesRepository;
    @Autowired private InvoiceDetailRepository invoiceDetailRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private AddressRepository addressRepository;

    public ResponseEntity<ResponseObject> createNewInvoice(Long cartId,Long addressId) {
        Optional<Cart> cart =this.cartRepository.findById(cartId);
        if(!cart.isPresent() ||cart.get().getStatus())
            throw new ResourceNotFoundException("Not found cart by id " + cartId);
        List<CartDetail> cartDetailList=this.cartDetailRepository.findALLByCartId(cartId);
        if(cartDetailList.isEmpty())
            throw new ResourceNotFoundException("Not found cart detail");
        Invoices invoice=new Invoices();
        invoice.setAddressId(addressId);
        invoice.setStatus("Đã đặt hàng");
        invoice.setGrandTotal(cart.get().getGrandTotal());
        invoice.setCartId(cartId);
        invoice.setUserId(cart.get().getUserId());
        invoice.setIsPayment(false);
        this.invoicesRepository.save(invoice);
        for (CartDetail item : cartDetailList){
            InvoiceDetail invoiceDetail=new InvoiceDetail();
            invoiceDetail.setInvoiceId(invoice.getId());
            invoiceDetail.setProductId(item.getProductId());
            invoiceDetail.setQuantity(item.getQuantity());
            invoiceDetail.setPrice(item.getPrice());
            this.invoiceDetailRepository.save(invoiceDetail);
        }
        Cart updateCart = cart.get();
        updateCart.setStatus(true);
        this.cartRepository.save(updateCart);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Create invoice successfully", invoice));
    }

    @Override
    public ResponseEntity<ResponseObject> cancelInvoice(Long id) {
        Optional<Invoices> invoices=this.invoicesRepository.findById(id);
        if(invoices.isPresent()){
            List<InvoiceDetail> invoiceDetails=this.invoiceDetailRepository.findAllByInvoiceId(id);
            this.invoicesRepository.deleteById(id);
            this.invoiceDetailRepository.deleteAllInBatch(invoiceDetails);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK, "Delete invoice successfully!", null));

        }
        throw new ResourceNotFoundException("Not found invoice by id " + id);
    }

    @Override
    public ResponseEntity<ResponseObject> updateStatusInvoice(InvoiceRequestDto invoiceRequestDto) {
        Optional<Invoices> invoices=this.invoicesRepository.findById(invoiceRequestDto.getId());
        if (!invoices.isPresent())
            throw new ResourceNotFoundException("Not found invoice by id " + invoiceRequestDto.getId());
        Invoices invoice=invoices.get();
        if(invoiceRequestDto.getStatus()!=null&& !invoiceRequestDto.getStatus().isEmpty())
            invoice.setStatus(invoiceRequestDto.getStatus());
        this.invoicesRepository.save(invoice);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Update status invoice successfully", invoice));
    }

    @Override
    public ResponseEntity<ResponseObject> findByUserId(Long userId) {
        List<Invoices> invoices=this.invoicesRepository.findAllByUserIdOrderByCreatedDate(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Get successfully", invoices));
    }

    @Override
    public ResponseEntity<ResponseObject> findAll() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<Invoices> invoices=this.invoicesRepository.findAllByOrderByCreatedDate();
        List<Invoices> invoicesList=invoices.stream().sorted(Comparator.comparing(Invoices::getCreatedDate)).collect(Collectors.toList());
        List<InvoiceResponseAdminDto> invoiceResponseDtos=new ArrayList<>();
        for(Invoices item:invoicesList){
            InvoiceResponseAdminDto invoiceResponseDto=new InvoiceResponseAdminDto();
            invoiceResponseDto.setId(item.getId());
            invoiceResponseDto.setGrandTotal(item.getGrandTotal());
            invoiceResponseDto.setStatus(item.getStatus());
            invoiceResponseDto.setCreatedDate(item.getCreatedDate().toString());
            invoiceResponseDto.setIsPayment(item.getIsPayment());
            //Tra them user id
            //Tra them address id
            if(item.getAddressId()!=null) {
                Optional<Address> address = this.addressRepository.findById(item.getAddressId());
                if (address.isPresent())
                    invoiceResponseDto.setAddress(address.get());
            }
            Optional<User> user=this.userRepository.findById(item.getUserId());
            if(!user.isPresent())
                throw new ResourceNotFoundException("User not found");
            invoiceResponseDto.setFullName(user.get().getFullName());
            invoiceResponseDtos.add(invoiceResponseDto);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Get successfully", invoiceResponseDtos));
    }

    @Override
    public ResponseEntity<ResponseObject> getTop8InvoiceNearest() {
        //Commit
        List<Invoices> invoices=this.invoicesRepository.findTop8ByOrderByCreatedDate();
        List<InvoiceResponseAdminDto> invoiceResponseDtos=new ArrayList<>();
        for(Invoices item:invoices){
            InvoiceResponseAdminDto invoiceResponseDto=new InvoiceResponseAdminDto();
            invoiceResponseDto.setId(item.getId());
            invoiceResponseDto.setGrandTotal(item.getGrandTotal());
            invoiceResponseDto.setStatus(item.getStatus());
            invoiceResponseDto.setCreatedDate(item.getCreatedDate().toString());
            invoiceResponseDto.setIsPayment(item.getIsPayment());
            Optional<User> user=this.userRepository.findById(item.getUserId());
            if(!user.isPresent())
                throw new ResourceNotFoundException("User not found");
            if(item.getAddressId()!=null) {
                Optional<Address> address = this.addressRepository.findById(item.getAddressId());
                if (address.isPresent())
                    invoiceResponseDto.setAddress(address.get());
            }
            invoiceResponseDto.setFullName(user.get().getFullName());
            invoiceResponseDtos.add(invoiceResponseDto);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Get successfully", invoiceResponseDtos));
    }

    @Override
    public ResponseEntity<ResponseObject> getInvoiceById(Long id) {
        Optional<Invoices> invoiceFound = this.invoicesRepository.findById(id);
        if(invoiceFound.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK, "Get successfully", invoiceFound.get()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.NOT_FOUND, "Không tìm thấy hóa đơn", null));

    }

    @Override
    public ResponseEntity<ResponseObject> changeStatusPayment(Long id) {
        Optional<Invoices> invoiceFound = this.invoicesRepository.findById(id);
        if(invoiceFound.isPresent()){
            var invoice =invoiceFound.get();
            invoice.setIsPayment(true);
            this.invoicesRepository.save(invoice);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK, "Update payment successfully", invoice));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.NOT_FOUND, "Không tìm thấy hóa đơn", null));
    }
}
