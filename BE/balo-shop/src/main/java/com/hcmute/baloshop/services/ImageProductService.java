package com.hcmute.baloshop.services;

import com.hcmute.baloshop.dto.image.ImageProductRequestDto;
import com.hcmute.baloshop.dto.image.ImageProductsRequestDto;
import com.hcmute.baloshop.entities.ImageProduct;
import com.hcmute.baloshop.models.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ImageProductService {

    String getPathImage(Long id);
    ResponseEntity<ResponseObject> findAllImageByProductId(Long id);

    ResponseEntity<ResponseObject> createNewImageProduct(ImageProductRequestDto imageProductRequestDto);
    ResponseEntity<ResponseObject> createNewListImageProduct(ImageProductsRequestDto imageProductsRequestDto);
    ResponseEntity<ResponseObject> updateImage(Long id,ImageProductRequestDto imageProductRequestDto);

    ResponseEntity<ResponseObject> deleteImageById(Long id);
    ResponseEntity<ResponseObject> deleteProductImage(String path,Long productId);
}
