package com.hcmute.baloshop.services;

import com.hcmute.baloshop.dto.cartdetail.category.CategoryCreationDTO;
import com.hcmute.baloshop.models.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    ResponseEntity<ResponseObject> getAllCategory();
    ResponseEntity<ResponseObject> getCategoryById(Long id);
    ResponseEntity<ResponseObject> createCategory(CategoryCreationDTO categoryDTO);
    ResponseEntity<ResponseObject> updateCategory(Long id, CategoryCreationDTO categoryDTO);
    ResponseEntity<ResponseObject> deleteCategoryById(Long id);

}
