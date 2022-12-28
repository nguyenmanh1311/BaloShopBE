package com.hcmute.baloshop.services;


import com.hcmute.baloshop.dto.product.ProductFilterRequestDto;
import com.hcmute.baloshop.dto.product.ProductRequestDTO;
import com.hcmute.baloshop.dto.product.ProductsearchDTO;
import com.hcmute.baloshop.models.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    ResponseEntity<ResponseObject> createNewProduct(ProductRequestDTO request);
    ResponseEntity<ResponseObject> updateProduct(Long id,ProductRequestDTO request);
    ResponseEntity<ResponseObject> getAllProduct();
    ResponseEntity<ResponseObject> searchProductByKeyWord(String input);
    ResponseEntity<ResponseObject> deleteProductById(Long id);
    ResponseEntity<ResponseObject> getProductByCate(Long idCate);
    ResponseEntity<ResponseObject> getProductByBranch(Long idBranch);
    ResponseEntity<ResponseObject> getProductById(Long id);
    ResponseEntity<ResponseObject> getTop8Product();
    ResponseEntity<ResponseObject> getProductByCateAndBrand(ProductsearchDTO productsearchDTO);
    ResponseEntity<ResponseObject> get4ProductRelated(Long productId);
    ResponseEntity<ResponseObject> getTop8BestSelling();
    ResponseEntity<ResponseObject> getAll();
    ResponseEntity<?> findByFilter(ProductFilterRequestDto requestDto);
}
