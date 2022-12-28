package com.hcmute.baloshop.services.implement;


import com.hcmute.baloshop.dto.image.ImageProductRequestDto;
import com.hcmute.baloshop.dto.image.ImageProductsRequestDto;
import com.hcmute.baloshop.entities.ImageProduct;
import com.hcmute.baloshop.entities.Product;
import com.hcmute.baloshop.exceptions.ResourceNotFoundException;
import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.repositories.ImageProductRepository;
import com.hcmute.baloshop.repositories.ProductRepository;
import com.hcmute.baloshop.services.ImageProductService;
import com.hcmute.baloshop.services.ImageProductStorageService;
import com.hcmute.baloshop.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ImageProductServiceImpl implements ImageProductService {

    @Autowired private ProductRepository productRepository;

    @Autowired private ImageProductRepository imageProductRepository;

    @Autowired private ImageProductStorageService storageService;

    @Override
    public String getPathImage(Long id) {
        Optional<ImageProduct> image = this.imageProductRepository.findById(id);
        if (image.isPresent()) {
            return image.get().getPath();
        }
        throw new ResourceNotFoundException("Image not found!");
    }

    @Override
    public ResponseEntity<ResponseObject> findAllImageByProductId(Long id) {
        Optional<Product> productFound = this.productRepository.findById(id);
        List<String> result = new ArrayList<>();
        if (productFound.isPresent()) {
            List<ImageProduct> imageProductList =
                    this.imageProductRepository.findAllByProductId(id);
            for (ImageProduct itor : imageProductList) {
                result.add(itor.getPath());
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK, "Find image product successfully!", result));
        }
        throw new ResourceNotFoundException("Image not found");
    }

    @Override
    public ResponseEntity<ResponseObject> createNewImageProduct(ImageProductRequestDto imageProductRequestDto) {
        ImageProduct imageProduct=new ImageProduct();
        imageProduct.setPath(imageProductRequestDto.getPath());
        imageProduct.setProductId(imageProductRequestDto.getProductId());
        this.imageProductRepository.save(imageProduct);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Create image product successfully!", imageProduct));
    }

    @Override
    public ResponseEntity<ResponseObject> createNewListImageProduct(ImageProductsRequestDto imageProductsRequestDto) {
        List<ImageProduct> imageProductList=new ArrayList<>();
        for(String path :imageProductsRequestDto.getPath()){
            ImageProduct imageProduct=new ImageProduct();
            imageProduct.setPath(path);
            imageProduct.setProductId(imageProductsRequestDto.getProductId());
            imageProductList.add(imageProduct);
        }
        this.imageProductRepository.saveAll(imageProductList);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Create image product successfully!", imageProductList));
    }

    @Override
    public ResponseEntity<ResponseObject> updateImage(Long id, ImageProductRequestDto imageProductRequestDto) {
        Optional<ImageProduct> imageFound = this.imageProductRepository.findById(id);
        if (imageFound.isPresent()) {
            ImageProduct image = imageFound.get();
            image.setPath(imageProductRequestDto.getPath());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            new ResponseObject(
                                    HttpStatus.OK,
                                    "Update image successfully!",
                                    this.imageProductRepository.save(image)));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Image not found!", null));
    }

    @Override
    public ResponseEntity<ResponseObject> deleteImageById(Long id) {
        Optional<ImageProduct> imageFound = this.imageProductRepository.findById(id);
        if (imageFound.isPresent()) {
            this.imageProductRepository.deleteById(id);
            this.storageService.deleteFile(imageFound.get().getPath());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK, "Delete image product successfully!", null));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Image not found!", null));
    }

    @Override
    public ResponseEntity<ResponseObject> deleteProductImage(String path, Long productId) {
        Optional<ImageProduct> imageProduct=this.imageProductRepository.findAllByPathAndProductId(path,productId);
        if(imageProduct.isPresent()){
            ImageProduct imageProductFound=imageProduct.get();
            this.imageProductRepository.deleteById(imageProductFound.getId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK, "Delete image successfully!", null));
        }
        throw new ResourceNotFoundException("not found image product");
    }
}
