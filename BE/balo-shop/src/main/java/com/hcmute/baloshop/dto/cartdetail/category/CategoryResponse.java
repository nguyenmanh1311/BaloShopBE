package com.hcmute.baloshop.dto.cartdetail.category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private Long id;
    private String title;
    private String href;
    private String img;
    private List<Long> products;
}
