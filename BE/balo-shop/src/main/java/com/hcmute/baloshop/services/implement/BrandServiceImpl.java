package com.hcmute.baloshop.services.implement;

import com.hcmute.baloshop.dto.brand.BrandRequestDTO;
import com.hcmute.baloshop.entities.Brand;
import com.hcmute.baloshop.exceptions.ResourceNotFoundException;
import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.repositories.BrandRepository;
import com.hcmute.baloshop.services.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;
    @Override
    public ResponseEntity<ResponseObject> createNewBrand(BrandRequestDTO request) {
        Optional<Brand> brands = this.brandRepository.findByName(request.getName());
        if (brands.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(new ResponseObject(HttpStatus.NOT_IMPLEMENTED, "Brand is existed!", null));
        }
        Brand brand = new Brand();
        brand.setName(request.getName());
        brand.setDescription(request.getDescription());
        brand.setIsDelete(false);
        Brand saveBrand=this.brandRepository.save(brand);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Create brand successfully!", saveBrand));
    }

    @Override
    public ResponseEntity<ResponseObject> getAllBrand() {
        List<Brand> brands=this.brandRepository.findAllByIsDelete();
        if (brands.isEmpty())
            throw new ResourceNotFoundException("List brand not found!");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Get all brand successfully!", brands));
    }

    @Override
    public ResponseEntity<ResponseObject> updateBrand(Long id, BrandRequestDTO brandRequestDTO) {
        List<Brand> brand=this.brandRepository.findAllByIdAndIsDelete(id);
        if (brand.isEmpty())
            throw new ResourceNotFoundException("Brand not found");
        Brand brand1=brand.get(0);
        if(brandRequestDTO.getName()!=null)
            brand1.setName(brandRequestDTO.getName());
        if(brandRequestDTO.getDescription()!=null)
            brand1.setDescription(brandRequestDTO.getDescription());
        this.brandRepository.save(brand1);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Update brand successfully!", brand1));
    }

    @Override
    public ResponseEntity<ResponseObject> deleteBrand(Long id) {
        List<Brand> brand=this.brandRepository.findAllByIdAndIsDelete(id);
        if (brand.isEmpty())
            throw new ResourceNotFoundException("Brand not found");
        Brand brand1=brand.get(0);
        brand1.setIsDelete(true);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Delete brand successfully!", brand1));
    }

    @Override
    public ResponseEntity<ResponseObject> findBrandById(Long id) {
        List<Brand> brand=this.brandRepository.findAllByIdAndIsDelete(id);
        if (brand.isEmpty())
            throw new ResourceNotFoundException("Brand not found");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Get brand successfully!", brand.get(0)));
    }

}
