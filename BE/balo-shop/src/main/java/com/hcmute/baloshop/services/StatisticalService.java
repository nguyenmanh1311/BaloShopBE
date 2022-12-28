package com.hcmute.baloshop.services;

import com.hcmute.baloshop.models.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface StatisticalService {
    ResponseEntity<ResponseObject> getTotal();

    ResponseEntity<ResponseObject> getSevenDayRevenueLineChartData();

    ResponseEntity<ResponseObject> getSevenDayInvoiceQuantityLineChartData();
}
