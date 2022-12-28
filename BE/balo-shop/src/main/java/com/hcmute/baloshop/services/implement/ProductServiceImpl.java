package com.hcmute.baloshop.services.implement;

import com.hcmute.baloshop.dto.product.ProductDTO;
import com.hcmute.baloshop.dto.product.ProductFilterRequestDto;
import com.hcmute.baloshop.dto.product.ProductRequestDTO;
import com.hcmute.baloshop.dto.product.ProductsearchDTO;
import com.hcmute.baloshop.entities.Brand;
import com.hcmute.baloshop.entities.Category;
import com.hcmute.baloshop.entities.ImageProduct;
import com.hcmute.baloshop.entities.Product;
import com.hcmute.baloshop.exceptions.ResourceNotFoundException;
import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.repositories.BrandRepository;
import com.hcmute.baloshop.repositories.CategoryRepository;
import com.hcmute.baloshop.repositories.ImageProductRepository;
import com.hcmute.baloshop.repositories.ProductRepository;
import com.hcmute.baloshop.services.ImageProductStorageService;
import com.hcmute.baloshop.services.ProductService;

import com.hcmute.baloshop.utils.Utils;
import com.sun.xml.bind.v2.runtime.reflect.Lister;
import jdk.javadoc.doclet.Doclet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    @Autowired private ImageProductStorageService storageService;
    @Autowired
    private ImageProductRepository imageProductRepository;
    private final BrandRepository brandRepository;

    @Override
    public ResponseEntity<ResponseObject> createNewProduct(ProductRequestDTO request) {
        Product p = new Product();
        if(request.getCategoryId() != null)
            p.setCategoryId(request.getCategoryId());
        if(request.getDescription() != null)
            p.setDescription(request.getDescription());
        if(request.getDiscountId() != null)
            p.setDiscountId(request.getDiscountId());
        if(request.getImage() != null)
            p.setImage((request.getImage()));
        if(request.getName() != null)
            p.setName(request.getName());
        if(request.getPrice() != null)
            p.setPrice(request.getPrice());
        if(request.getStandCost() != null)
            p.setStandCost(request.getStandCost());
        if(request.getBranchId() != null)
            p.setBranchId(request.getBranchId());
        p.setIsDelete(false);
        this.productRepository.save(p);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Create product successfully!", p));
    }
    @Override
    public ResponseEntity<ResponseObject> updateProduct(Long id, ProductRequestDTO request) {
        List<Product> getProduct = this.productRepository.findAllByIdAndIsDelete(id);
        if (getProduct.isEmpty())
            throw new ResourceNotFoundException("Not found product by id " + id);
        Product p = getProduct.get(0);
        if(request.getCategoryId() != null)
            p.setCategoryId(request.getCategoryId());
        if(request.getDescription() != null)
            p.setDescription(request.getDescription());
        if(request.getDiscountId() != null)
            p.setDiscountId(request.getDiscountId());
        if(request.getImage() != null)
            p.setImage((request.getImage()));
        if(request.getName() != null)
            p.setName(request.getName());
        if(request.getPrice() != null)
            p.setPrice(request.getPrice());
        if(request.getStandCost() != null)
            p.setStandCost(request.getStandCost());
        if(request.getBranchId() != null)
            p.setBranchId(request.getBranchId());
        this.productRepository.save(p);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Update product successfully!", p));
    }

    @Override
    public ResponseEntity<ResponseObject> getAllProduct() {
        List<Product> products = this.productRepository.findAll();
        if(products.isEmpty())
            throw new ResourceNotFoundException("Not found product");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "All product", products));
    }

    @Override
    public ResponseEntity<ResponseObject> searchProductByKeyWord(String input) {
        List<Product> products = this.productRepository.searchProductByKeyWord(input);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "All product", products));
    }

    @Override
    public ResponseEntity<ResponseObject> deleteProductById(Long id) {
        List<Product> product = this.productRepository.findAllByIdAndIsDelete(id);
        if (!product.isEmpty()) {
            Product product1=product.get(0);
            product1.setIsDelete(true);
            this.productRepository.save(product1);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK, "Delete product successfully!", product1));
        }
        throw new ResourceNotFoundException(Utils.PRODUCT_NOT_FOUND);
    }

    @Override
    public ResponseEntity<ResponseObject> getProductByCate(Long idCate) {
        List<Product> product = this.productRepository.findByCategoryId(idCate);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Find product successfully!", product));
    }

    @Override
    public ResponseEntity<ResponseObject> getProductByBranch(Long idBranch) {
        List<Product> product = this.productRepository.findByBranchId(idBranch);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Find product successfully!", product));
    }

    @Override
    public ResponseEntity<ResponseObject> getProductById(Long id) {
        List<Product> product =this.productRepository.findAllByIdAndIsDelete(id);
        if(product.isEmpty())
            throw new ResourceNotFoundException("Not found product by Id " + id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Find product successfully!", product.get(0)));
    }

    @Override
    public ResponseEntity<ResponseObject> getTop8Product() {
        List<Product> products =this.productRepository.findTop8();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Find product successfully!", products));
    }

    @Override
    public ResponseEntity<ResponseObject> getProductByCateAndBrand(ProductsearchDTO productsearchDTO){
        List<Product> products=new ArrayList<>();
        if(productsearchDTO.getCategoryId()!=null && productsearchDTO.getBranchId()!=null)
            products= this.productRepository.findAllByCategoryIdAndAndBranchId(productsearchDTO.getCategoryId(),productsearchDTO.getBranchId());
        if(productsearchDTO.getCategoryId()!=null && productsearchDTO.getBranchId()==null)
            products= this.productRepository.findByCategoryId(productsearchDTO.getCategoryId());
        if(productsearchDTO.getCategoryId()==null && productsearchDTO.getBranchId()!=null)
            products= this.productRepository.findByBranchId(productsearchDTO.getBranchId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Find product successfully!", products));

    }

    @Override
    public ResponseEntity<ResponseObject> get4ProductRelated(Long productId) {
        Optional<Product> product=this.productRepository.findById(productId);
        if(!product.isPresent())
            throw new ResourceNotFoundException("Product not found");
        List<Product> products=this.productRepository.findProductRelated(product.get().getCategoryId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Find product successfully!", products));
    }

    @Override
    public ResponseEntity<ResponseObject> getTop8BestSelling() {
        List<Long> listId=this.productRepository.getTop8BestSelling();
        List<Product> products=new ArrayList<>();
        for (var item :listId){
            Optional<Product> p=this.productRepository.findById(item);

            products.add(p.get());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Find product successfully!", products));
    }

    @Override
    public ResponseEntity<ResponseObject> getAll() {
        List<Product> products = this.productRepository.findAll();
        if(products.isEmpty())
            throw new ResourceNotFoundException("Not found product");
        List<ProductDTO> productDTOS=new ArrayList<>();
        for(Product item:products){
            ProductDTO productDTO=new ProductDTO();
            productDTO.setId(item.getId());
            productDTO.setDiscountId(item.getDiscountId());
            productDTO.setName(item.getName());
            productDTO.setPrice(item.getPrice());
            productDTO.setStandCost(item.getStandCost());
            productDTO.setImage(item.getImage());
            productDTO.setDescription(item.getDescription());
            if(item.getCategoryId()!=null){
                Optional<Category> category=this.categoryRepository.findById(item.getCategoryId());
                if (category.isPresent()){
                    productDTO.setCateName(category.get().getName());
                }
            }
            if(item.getBranchId()!=null){
            Optional<Brand> brand=this.brandRepository.findById(item.getBranchId());
            if (brand.isPresent()){
                productDTO.setBrandName(brand.get().getName());
            }}
            productDTOS.add(productDTO);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "All product", productDTOS));
    }

    @Override
    public ResponseEntity<?> findByFilter(ProductFilterRequestDto requestDto) {
        Long[] categoryArr;
        if(requestDto.getCategoryIds().length==0){
            List<Category> categories=this.categoryRepository.findAll();
            categoryArr=new Long[categories.size()];
            for (int i = 0; i < categories.size(); i++) {
                categoryArr[i]=categories.get(i).getId();
            };
        }
        else {
            categoryArr=requestDto.getCategoryIds();
        }
        System.out.println(Arrays.toString(categoryArr));
        Long[] brandArr;
        if(requestDto.getBrandIds().length==0){
            List<Brand> brands=this.brandRepository.findAll();
            brandArr=new Long[brands.size()];
            for (int i = 0; i < brands.size(); i++) {
                brandArr[i]=brands.get(i).getId();
            };
        }
        else {
            brandArr=requestDto.getBrandIds();
        }
        System.out.println(Arrays.toString(brandArr));

        if(requestDto.getMinPrice()==null){
            requestDto.setMinPrice(0L);
        }
        if(requestDto.getMaxPrice()==null || requestDto.getMaxPrice() == 0L){
            requestDto.setMaxPrice(Long.MAX_VALUE);
        }
        System.out.println(requestDto.getMinPrice());
        System.out.println(requestDto.getMaxPrice());
        List<Product> products= this.productRepository.findAllByFilter(categoryArr,
                brandArr,requestDto.getMinPrice(),requestDto.getMaxPrice());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "All product", products));
    }
}
