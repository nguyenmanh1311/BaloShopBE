package com.hcmute.baloshop.dto.invoice;

import com.hcmute.baloshop.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponseAdminDto {
    //
    private Long id;
    private String FullName;
    private Long grandTotal;
    private String status;
    private String createdDate;
    private Address address;
    private Boolean isPayment;
}
