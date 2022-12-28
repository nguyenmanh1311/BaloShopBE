package com.hcmute.baloshop.controller;

import com.hcmute.baloshop.dto.cartdetail.category.CategoryCreationDTO;
import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/category")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    @Autowired
    private final CategoryService categoryService;
    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllCategory(){
        return this.categoryService.getAllCategory();
    }
    //@RolesAllowed({"Admin", "Salesperson", "Editor"})
    @PostMapping
    public ResponseEntity<ResponseObject> createNewCategory(@ModelAttribute CategoryCreationDTO creationDTO) {
        return this.categoryService.createCategory(creationDTO);
    }
    //@RolesAllowed({"Admin", "Salesperson", "Editor"})
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseObject> updateCategory(@PathVariable("id") Long id,
                                                         @RequestBody CategoryCreationDTO creationDTO) {
        return this.categoryService.updateCategory(id, creationDTO);
    }
    //@RolesAllowed({"Admin", "Salesperson", "Editor"})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseObject> deleteCategoryById(@PathVariable("id") Long id) {
        return this.categoryService.deleteCategoryById(id);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getCategoryById(@PathVariable("id") Long id) {
        return this.categoryService.getCategoryById(id);
    }

}
