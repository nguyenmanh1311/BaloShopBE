package com.hcmute.baloshop.services.implement;

import com.hcmute.baloshop.dto.cartdetail.CartDetailChangeQuantityDto;
import com.hcmute.baloshop.dto.cartdetail.CartDetailRequestDTO;
import com.hcmute.baloshop.dto.cartdetail.CartDetailResponseDTO;
import com.hcmute.baloshop.entities.Cart;
import com.hcmute.baloshop.entities.CartDetail;
import com.hcmute.baloshop.entities.Product;
import com.hcmute.baloshop.exceptions.ResourceNotFoundException;
import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.repositories.CartDetailRepository;
import com.hcmute.baloshop.repositories.CartRepository;
import com.hcmute.baloshop.repositories.ProductRepository;
import com.hcmute.baloshop.repositories.UserRepository;
import com.hcmute.baloshop.services.CartDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class CartDetailServiceImpl implements CartDetailService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Autowired
    private ProductRepository productRepository;
    @Override
    public ResponseEntity<ResponseObject> getAllCartDetailByCartId(Long id) {
        List<CartDetail> cartDetails=this.cartDetailRepository.findALLByCartId(id);
        if(cartDetails.isEmpty())
            throw new ResourceNotFoundException("Cart detail not found");
        List<CartDetailResponseDTO> cartDetailResponseDTOS=new ArrayList<CartDetailResponseDTO>();
        for(CartDetail cartDetail : cartDetails){
            Optional<Product> product=this.productRepository.findById(cartDetail.getProductId());
            CartDetailResponseDTO cart=new CartDetailResponseDTO();
            cart.setId(cartDetail.getId());
            cart.setProductId(cartDetail.getProductId());
            cart.setName(product.get().getName());
            cart.setImage(product.get().getImage());
            cart.setQuantity(cartDetail.getQuantity());
            cart.setPrice(cartDetail.getPrice());
            cartDetailResponseDTOS.add(cart);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Get  cart detail successfully!", cartDetailResponseDTOS));
    }

    @Override
    public ResponseEntity<ResponseObject> getCartDetailById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> createCartDetail(CartDetailRequestDTO cartDetailRequestDTO) {
        Optional<Product> product=this.productRepository.findById(cartDetailRequestDTO.getProductId());
        if(!product.isPresent())
            throw new ResourceNotFoundException("Product not found");
        Optional<Cart> carts=this.cartRepository.findAllById(cartDetailRequestDTO.getCartId());
        if(!carts.isPresent()){
            throw new ResourceNotFoundException("Cart not found");
        }
        List<CartDetail> cartDetails=this.cartDetailRepository.findALLByCartId(cartDetailRequestDTO.getCartId());
        for(CartDetail cartDetail : cartDetails){
            if(cartDetail.getProductId()==cartDetailRequestDTO.getProductId()){
                cartDetail.setQuantity(cartDetail.getQuantity()+cartDetailRequestDTO.getQuantity());
                CartDetail saveCartDetail=this.cartDetailRepository.save(cartDetail);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject(HttpStatus.OK, "Create cart successfully!", saveCartDetail));
            }
        }
        CartDetail cartDetail=new CartDetail();
        cartDetail.setCartId(cartDetailRequestDTO.getCartId());
        cartDetail.setProductId(cartDetailRequestDTO.getProductId());
        cartDetail.setQuantity(cartDetailRequestDTO.getQuantity());
        cartDetail.setPrice(product.get().getPrice());
        CartDetail saveCartDetail=this.cartDetailRepository.save(cartDetail);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Create cart successfully!", saveCartDetail));
    }

    @Override
    public ResponseEntity<ResponseObject> updateQuantity(CartDetailChangeQuantityDto cartDetailChangeQuantityDto) {
        Optional<CartDetail> cartDetails=this.cartDetailRepository.findById(cartDetailChangeQuantityDto.getId());
        if(!cartDetails.isPresent())
            throw new ResourceNotFoundException("Not found cart detail");
        CartDetail cartDetail=cartDetails.get();
        cartDetail.setQuantity(cartDetailChangeQuantityDto.getQuantity());
        this.cartDetailRepository.save(cartDetail);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Update cart detail successfully!", cartDetail));
    }


    @Override
    public ResponseEntity<ResponseObject> deleteCartDetailById(Long id) {
        Optional<CartDetail> cartDetail=this.cartDetailRepository.findById(id);
        if(!cartDetail.isPresent())
            throw new ResourceNotFoundException("Not found cart detail");
        this.cartDetailRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Delete cart detail successfully!", null));
    }
}
