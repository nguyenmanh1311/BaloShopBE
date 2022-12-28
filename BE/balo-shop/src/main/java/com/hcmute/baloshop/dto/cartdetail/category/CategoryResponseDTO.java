package com.hcmute.baloshop.dto.cartdetail.category;

import com.hcmute.baloshop.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String parentCategoryName;
    private String image;

    public CategoryResponseDTO build(Category category) {
        return new CategoryResponseDTO(category.getId(),
                category.getName(),
                category.getDescription(),
                category.getParentCategory() == null ? null: category.getParentCategory().getName(),
                category.getImage());
    }
}
