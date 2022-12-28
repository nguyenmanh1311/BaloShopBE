package com.hcmute.baloshop.controller;

import com.hcmute.baloshop.dto.product.ProductFilterRequestDto;
import com.hcmute.baloshop.dto.product.ProductRequestDTO;
import com.hcmute.baloshop.dto.product.ProductsearchDTO;
import com.hcmute.baloshop.entities.Product;
import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.services.ProductService;
import com.hcmute.baloshop.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;


@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {
    @Autowired private ProductService productService;
    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_SIZE = "10";
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ResponseObject> createNewProduct(
            @RequestBody ProductRequestDTO request) {
        return this.productService.createNewProduct(request);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseObject> updateProduct(
            @PathVariable(name = "id") Long id, @RequestBody ProductRequestDTO request) {

        return this.productService.updateProduct(id, request);
    }
    @RequestMapping(value = "cate/{idCate}", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getProductByIdCate(
            @PathVariable(name = "idCate") Long idCate) {
        return this.productService.getProductByCate(idCate);
    }
    @RequestMapping(value = "branch/{idBranch}", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getProductByBranchName(
            @PathVariable(name = "idBranch") Long idBranch) {
        return this.productService.getProductByBranch(idBranch);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getProductById(
            @PathVariable(name = "id") Long id) {
        return this.productService.getProductById(id);
    }
    @RequestMapping(value = "/top8new", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getTop8NewProduct() {
        return this.productService.getTop8Product();
    }
    @RequestMapping(value = "/topselling", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getBestSelling() {
        return this.productService.getTop8BestSelling();
    }
    @RequestMapping(value = "/top4related/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getTop4ralated(@PathVariable(name = "id") Long id) {
        return this.productService.get4ProductRelated(id);
    }
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getAllProduct(
            @RequestParam(value = "q", required = false) String input) {
            return this.productService.getAllProduct();
    }
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getAll(){
        return this.productService.getAll();
    }
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> searchProduct(
            @RequestParam(value = "input") String input) {
        return this.productService.searchProductByKeyWord(input);
    }
    @RequestMapping(value = "/search/bycateandbranch", method = RequestMethod.POST)
    public ResponseEntity<ResponseObject> searchProductByCateAndBranch(
            @RequestBody ProductsearchDTO productsearchDTO) {
        return this.productService.getProductByCateAndBrand(productsearchDTO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseObject> deleteProductById(@PathVariable(name = "id") Long id) {
        return this.productService.deleteProductById(id);
    }
    @PostMapping("filter")
    public ResponseEntity<?> filterProduct(@RequestBody ProductFilterRequestDto request){
        System.out.println(request.toString());
        return this.productService.findByFilter(request);
    }
}

