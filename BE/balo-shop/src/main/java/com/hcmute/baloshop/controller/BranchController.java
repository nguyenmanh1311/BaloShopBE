package com.hcmute.baloshop.controller;

import com.hcmute.baloshop.dto.brand.BrandRequestDTO;
import com.hcmute.baloshop.dto.product.ProductRequestDTO;
import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.services.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/brand")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BranchController {
    @Autowired
    private BrandService branchService;
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ResponseObject> createNewBrand(
            @RequestBody BrandRequestDTO request) {
        return this.branchService.createNewBrand(request);
    }
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getAllBrand() {
        return this.branchService.getAllBrand();
    }
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseObject> updateBrand( @PathVariable(name = "id") Long id,@RequestBody BrandRequestDTO brandRequestDTO) {
        return this.branchService.updateBrand(id, brandRequestDTO);
    }
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseObject> deleteBrand( @PathVariable(name = "id") Long id) {
        return this.branchService.deleteBrand(id);
    }
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getBrandById( @PathVariable(name = "id") Long id) {
        return this.branchService.findBrandById(id);
    }
}
