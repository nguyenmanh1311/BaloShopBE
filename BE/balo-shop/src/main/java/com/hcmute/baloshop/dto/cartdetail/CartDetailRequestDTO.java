package com.hcmute.baloshop.dto.cartdetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailRequestDTO {
    private Long productId;
    private Long cartId;
    private Long quantity;
}
