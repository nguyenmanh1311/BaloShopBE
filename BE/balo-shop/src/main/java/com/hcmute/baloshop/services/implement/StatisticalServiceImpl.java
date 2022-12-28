package com.hcmute.baloshop.services.implement;

import com.hcmute.baloshop.dto.statistical.KeyValue;
import com.hcmute.baloshop.dto.statistical.StatisticalResponseDto;
import com.hcmute.baloshop.entities.Invoices;
import com.hcmute.baloshop.entities.Product;
import com.hcmute.baloshop.entities.User;
import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.repositories.InvoicesRepository;
import com.hcmute.baloshop.repositories.ProductRepository;
import com.hcmute.baloshop.repositories.UserRepository;
import com.hcmute.baloshop.services.StatisticalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StatisticalServiceImpl implements StatisticalService {
    private final UserRepository userRepository;
    private final InvoicesRepository invoicesRepository;
    private final ProductRepository productRepository;

    @Override
    public ResponseEntity<ResponseObject> getTotal() {
        StatisticalResponseDto statisticalResponseDto = new StatisticalResponseDto();
        List<User> users = this.userRepository.findAll();
        List<Invoices> invoices = this.invoicesRepository.findAll();
        List<Product> products = this.productRepository.findAll();
        Long total = 0L;
        for (Invoices item : invoices) {
            total += item.getGrandTotal();
        }
        statisticalResponseDto.setTotalUser(users.stream().count());
        statisticalResponseDto.setTotalInvoice(invoices.stream().count());
        statisticalResponseDto.setTotalTurnover(total);
        statisticalResponseDto.setTotalProduct(products.stream().count());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Get statistical successfully!!!", statisticalResponseDto));
    }

    @Override
    public ResponseEntity<ResponseObject> getSevenDayRevenueLineChartData() {
        LocalDateTime now = LocalDateTime.now();
        List<LocalDateTime> sevenDay = new ArrayList<>();
        List<KeyValue> data = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            sevenDay.add(now.minusDays(i));
        }
        List<Invoices> invoicesList = this.invoicesRepository.findAll();
        for (LocalDateTime date : sevenDay) {
            Long sum = 0L;
            for (Invoices invoice : invoicesList) {
                LocalDateTime invoiceDate = Instant.ofEpochMilli(invoice.getCreatedDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
                if (date.getDayOfMonth() == invoiceDate.getDayOfMonth()) {
                    sum += invoice.getGrandTotal();
                }
            }
            data.add(new KeyValue(date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth(), sum));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Get statistical successfully!!!", data));
    }

    @Override
    public ResponseEntity<ResponseObject> getSevenDayInvoiceQuantityLineChartData() {
        LocalDateTime now = LocalDateTime.now();
        List<LocalDateTime> sevenDay = new ArrayList<>();
        List<KeyValue> data = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            sevenDay.add(now.minusDays(i));
        }
        List<Invoices> invoicesList = this.invoicesRepository.findAll();
        for (LocalDateTime date : sevenDay) {
            Long sum = 0L;
            for (Invoices invoice : invoicesList) {
                LocalDateTime invoiceDate = Instant.ofEpochMilli(invoice.getCreatedDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
                if (date.getDayOfMonth() == invoiceDate.getDayOfMonth()) {
                    sum ++;
                }
            }
            data.add(new KeyValue(date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth(), sum));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Get statistical successfully!!!", data));
    }
}
