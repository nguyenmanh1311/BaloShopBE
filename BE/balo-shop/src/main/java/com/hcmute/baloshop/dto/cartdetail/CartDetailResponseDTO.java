package com.hcmute.baloshop.dto.cartdetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailResponseDTO {
    private Long id;
    private Long productId;
    private String image;
    private String name;
    private Long quantity;
    private Long price;
}
