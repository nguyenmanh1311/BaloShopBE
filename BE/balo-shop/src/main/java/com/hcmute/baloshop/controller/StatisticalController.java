package com.hcmute.baloshop.controller;

import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.services.StatisticalService;
import com.hcmute.baloshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/statistical")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StatisticalController {
    @Autowired
    private final StatisticalService statisticalService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getTotal(){
        return this.statisticalService.getTotal();
    }

    @GetMapping("seven_day_recent_line_chart")
    public ResponseEntity<ResponseObject> getSevenDayDataLineChart(){
        return this.statisticalService.getSevenDayRevenueLineChartData();
    }
    @GetMapping("seven_day_recent_line_chart_invoice")
    public ResponseEntity<ResponseObject> getSevenDayInvoiceDataLineChart(){
        return this.statisticalService.getSevenDayInvoiceQuantityLineChartData();
    }
}
