package com.hcmute.baloshop.controller;

import com.hcmute.baloshop.dto.image.ImageProductRequestDto;
import com.hcmute.baloshop.dto.image.ImageProductsRequestDto;
import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.services.ImageProductService;
import com.hcmute.baloshop.services.ImageProductStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/image_product")
@CrossOrigin(origins = "*")
public class ImageProductController {

    @Autowired
    private ImageProductService imageProductService;

    @Autowired private ImageProductStorageService storageService;

    @RequestMapping(value = "/{fileName}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImageProduct(@PathVariable("fileName") String fileName) {
        byte[] bytes = storageService.readFileContent(fileName);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
    }

    //@RolesAllowed({"Admin", "Salesperson", "Editor"})
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<ResponseObject> saveImage(
            @RequestParam("image") MultipartFile[] files) {
        String fileName = "";
        List<String> listFile = new ArrayList<>();
        for (MultipartFile file : files) {
            fileName = storageService.storeFile(file);
            listFile.add(fileName);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Store image succcessfully", listFile));
    }
    @RequestMapping(value = "/save-list", method = RequestMethod.POST)
    public ResponseEntity<ResponseObject> saveListImage(
            @RequestBody ImageProductsRequestDto imageProductsRequestDto) {
        return this.imageProductService.createNewListImageProduct(imageProductsRequestDto);
    }
    @RequestMapping(value = "/save/image", method = RequestMethod.POST)
    public ResponseEntity<ResponseObject> saveImageProduct(@RequestBody ImageProductRequestDto imageProductRequestDto) {
        return this.imageProductService.createNewImageProduct(imageProductRequestDto);
    }
    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getAllImageByProductId(@PathVariable("id") Long id) {
        return this.imageProductService.findAllImageByProductId(id);
    }

    //@RolesAllowed({"Admin", "Salesperson", "Editor"})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseObject> deleteImage(@PathVariable("id") Long id) {
        return this.imageProductService.deleteImageById(id);
    }
    @RequestMapping(value = "/{id}/{path}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseObject> deleteImageProduct(@PathVariable("id") Long id,@PathVariable("path") String path) {
        return this.imageProductService.deleteProductImage(path,id);
    }
}