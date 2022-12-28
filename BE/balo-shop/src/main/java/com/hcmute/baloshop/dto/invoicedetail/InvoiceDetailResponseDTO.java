package com.hcmute.baloshop.dto.invoicedetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDetailResponseDTO {
    private Long id;
    private String productName;
    private String productImage;
    private Long invoiceId;
    private Long quantity;
    private Long price;
}
