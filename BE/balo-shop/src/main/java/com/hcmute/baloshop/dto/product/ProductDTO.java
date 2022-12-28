package com.hcmute.baloshop.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private Long discountId;
    private String cateName;
    private String brandName;
    private String name;
    private String code;
    private Long price;
    private Long standCost;
    private String image;
    private String description;
}
