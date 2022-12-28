package com.hcmute.baloshop.dto.cartdetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailChangeQuantityDto {
    private Long id;
    private Long quantity;
}
