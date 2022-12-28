package com.hcmute.baloshop.services.implement;

import com.hcmute.baloshop.dto.cartdetail.category.CategoryCreationDTO;
import com.hcmute.baloshop.dto.cartdetail.category.CategoryResponseDTO;
import com.hcmute.baloshop.entities.Category;
import com.hcmute.baloshop.exceptions.InvalidValueException;
import com.hcmute.baloshop.exceptions.ResourceNotFoundException;
import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.repositories.CategoryRepository;
import com.hcmute.baloshop.services.CategoryService;
import com.hcmute.baloshop.services.ImageProductStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Autowired private ImageProductStorageService storageService;
    @Override
    public ResponseEntity<ResponseObject> getAllCategory() {
        log.info("Fetching list category...");
        List<Category> categoryList = this.categoryRepository.findAllByIsDelete();
        if (categoryList.isEmpty())
            throw new ResourceNotFoundException("List category not found!");
        List<CategoryResponseDTO> result = new ArrayList<>();
        for (Category category : categoryList) {
            CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
            result.add(categoryResponseDTO.build(category));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Find Category successfully!", result));
    }


    @Override
    public ResponseEntity<ResponseObject> getCategoryById(Long id) {

        log.info("Fetching category...");
        List<Category> categoryFound = this.categoryRepository.findAllByIdAndIsDelete(id);
        if (!categoryFound.isEmpty()) {
            CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
            categoryResponseDTO = categoryResponseDTO.build(categoryFound.get(0));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK, "Find Category successfully!", categoryResponseDTO));
        }
        throw new ResourceNotFoundException("Category not found!");
    }

    @Override
    public ResponseEntity<ResponseObject> createCategory(CategoryCreationDTO categoryDTO) {
        Optional<Category> categoryFound = this.categoryRepository.findByName(categoryDTO.getName());
        if (categoryFound.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(new ResponseObject(HttpStatus.NOT_IMPLEMENTED, "Category is existed!", null));
        }

        Category newCategory = new Category();
        newCategory.setName(categoryDTO.getName());
        if (categoryDTO.getImage() != null) {
            String fileName = this.storageService.storeFile(categoryDTO.getImage());
            newCategory.setImage(fileName);
        }
        newCategory.setDescription(categoryDTO.getDescription());
        newCategory.setIsDelete(false);
       // validateCategory(newCategory);
        Category save = this.categoryRepository.save(newCategory);
        log.info("New category was created, id={}", save.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED, "Create new category successfully!", save));
    }


    @Override
    public ResponseEntity<ResponseObject> updateCategory(Long id, CategoryCreationDTO categoryDTO) {
        List<Category> categoryFound = this.categoryRepository.findAllByIdAndIsDelete(id);
        if (!categoryFound.isEmpty()) {
            Category newCategory = categoryFound.get(0);
            Optional<Category> parentCategoryFound = null;

            // check parent category is found
            if (categoryDTO.getParentCategoryId() == null) {
                newCategory.setParentCategory(null);
            } else {
                parentCategoryFound = this.categoryRepository.findById(categoryDTO.getParentCategoryId());
            }

            // check name input is null
            if (categoryDTO.getName() != null) {
                newCategory.setName(categoryDTO.getName());
            }

            // check description is null
            if (categoryDTO.getDescription() != null) {
                newCategory.setDescription(categoryDTO.getDescription());
            }

            // check image input is null
            if (categoryDTO.getImage() != null) {
                this.storageService.deleteFile(categoryFound.get(0).getImage());
                String fileName = this.storageService.storeFile(categoryDTO.getImage());
                newCategory.setImage(fileName);
            }

            // check parent category and set category
            if (parentCategoryFound != null) {
                if (parentCategoryFound.isPresent()) {
                    if (parentCategoryFound.get().getId() == id) {
                        newCategory.setParentCategory(null);
                    } else {
                        newCategory.setParentCategory(parentCategoryFound.get());
                    }
                }
            } else {
                newCategory.setParentCategory(null);
            }
            validateCategory(newCategory);
            log.info("Category was updated, id={}", newCategory.getId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            new ResponseObject(
                                    HttpStatus.OK,
                                    "Update category successfully!",
                                    this.categoryRepository.save(newCategory)));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseObject(HttpStatus.NOT_FOUND, "Category not found!", null));
    }

    @Override
    public ResponseEntity<ResponseObject> deleteCategoryById(Long id) {
        List<Category> categoryFound = this.categoryRepository.findAllByIdAndIsDelete(id);
        if (!categoryFound.isEmpty()) {
            Category category=categoryFound.get(0);
            category.setIsDelete(true);
            this.categoryRepository.save(category);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK, "Delete category successfully!", category));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseObject(HttpStatus.NOT_FOUND, "Category not found!", null));
    }

    private void validateCategory(Category category) {
        if (category.getName().length() >= 255) {
            throw new InvalidValueException(
                    "The length of category's name must be less than 255 characters");
        }
    }




}
