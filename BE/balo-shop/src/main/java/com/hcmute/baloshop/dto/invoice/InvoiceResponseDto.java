package com.hcmute.baloshop.dto.invoice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponseDto {
    private Long id;
    private Long cartId;
    private Long userId;
    private Long grandTotal;
    private String status;
    private Date createdDate;
    private Date updateDate;
}
