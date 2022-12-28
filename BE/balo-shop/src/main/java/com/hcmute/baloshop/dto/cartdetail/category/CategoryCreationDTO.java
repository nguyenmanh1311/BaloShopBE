package com.hcmute.baloshop.dto.cartdetail.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreationDTO {
    private String name;
    private String description;
    private Long parentCategoryId;
    private MultipartFile image;
}
