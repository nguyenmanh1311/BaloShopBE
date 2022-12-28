package com.hcmute.baloshop.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilterRequestDto {
    private Long[] categoryIds;
    private Long[] brandIds;
    private Long minPrice;
    private Long maxPrice;

    @Override
    public String toString() {
        return "ProductFilterRequestDto{" +
                "categoryIds=" + Arrays.toString(categoryIds) +
                ", brandIds=" + Arrays.toString(brandIds) +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                '}';
    }
}
