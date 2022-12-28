package com.hcmute.baloshop.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponeDTO {
    private Long id;
    private Long userId;
    private Long grandTotal;
    private Boolean status;
}
