package com.hcmute.baloshop.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MomoPayment {
    private String orderInfo;
    private Long amount;
    private String returnUrl;
    private String extraData;
}
