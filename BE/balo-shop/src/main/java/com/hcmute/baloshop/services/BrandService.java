package com.hcmute.baloshop.services;

import com.hcmute.baloshop.dto.brand.BrandRequestDTO;
import com.hcmute.baloshop.models.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface BrandService {
    ResponseEntity<ResponseObject> createNewBrand(BrandRequestDTO request);
    ResponseEntity<ResponseObject> getAllBrand();
    ResponseEntity<ResponseObject> updateBrand(Long id,BrandRequestDTO brandRequestDTO);
    ResponseEntity<ResponseObject> deleteBrand(Long id);
    ResponseEntity<ResponseObject> findBrandById(Long id);
}
