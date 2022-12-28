package com.hcmute.baloshop.services.implement;

import com.hcmute.baloshop.dto.cart.CartRequestDTO;
import com.hcmute.baloshop.entities.Cart;
import com.hcmute.baloshop.entities.CartDetail;
import com.hcmute.baloshop.entities.User;
import com.hcmute.baloshop.exceptions.AlreadyExistException;
import com.hcmute.baloshop.exceptions.ResourceNotFoundException;
import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.repositories.CartDetailRepository;
import com.hcmute.baloshop.repositories.CartRepository;
import com.hcmute.baloshop.repositories.UserRepository;
import com.hcmute.baloshop.services.CartService;
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
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Override
    public ResponseEntity<ResponseObject> getAllCart() {
        return null;
    }
    @Override
    public ResponseEntity<ResponseObject> getCartById(Long id) {
        Optional<Cart> carts=this.cartRepository.findAllById(id);
        if(!carts.isPresent()){
            throw new ResourceNotFoundException("Cart not found");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Get cart successfully", carts));
    }

    @Override
    public ResponseEntity<ResponseObject> getCartByUserId(Long id) {
        List<Cart> carts=this.cartRepository.findAllByUserId(id);
        if(carts.isEmpty()){
            throw new ResourceNotFoundException("Cart not found");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Get cart successfully", carts));
    }

    @Override
    public ResponseEntity<?> getCartByUserId(Long id, Boolean status) {
        Optional<User> userFound = this.userRepository.findById(id);
        if(!userFound.isPresent()){
            throw new ResourceNotFoundException("User not found");
        }
        List<Cart> carts = this.cartRepository.findCartByUserIdAndStatus(id, status);
        if(carts.isEmpty()){
            Cart cart = new Cart();
            cart.setUserId(id);
            cart.setStatus(false);
            Cart save = this.cartRepository.save(cart);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK,"Get cart successfully!", save));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK,"Get cart successfully!", carts.get(0)));
    }

    @Override
    public ResponseEntity<ResponseObject> createCart(CartRequestDTO cartRequestDTO) {
        Optional<User> users=this.userRepository.findById(cartRequestDTO.getUserId());
        if(!users.isPresent()){
            throw new ResourceNotFoundException("User not found");
        }
        List<Cart> carts=this.cartRepository.findCartOfUser(cartRequestDTO.getUserId());
        if(!carts.isEmpty()){
            throw new AlreadyExistException("Cart already exist");
        }
        Cart cart=new Cart();
        cart.setUserId(cartRequestDTO.getUserId());
        cart.setStatus(cartRequestDTO.getStatus());
        Cart saveCart=this.cartRepository.save(cart);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Create cart successfully!", saveCart));
    }
    @Override
    public ResponseEntity<ResponseObject> updateCart(Long id, CartRequestDTO cartRequestDTO) {
        Optional<Cart> carts=this.cartRepository.findAllById(id);
        if(!carts.isPresent()){
            throw new ResourceNotFoundException("Cart not found");
        }
        Cart cart=carts.get();
        cart.setUserId(cartRequestDTO.getUserId());
        cart.setGrandTotal(cartRequestDTO.getGrandTotal());
        cart.setStatus(cartRequestDTO.getStatus());
        this.cartRepository.save(cart);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Update cart successfully", cart));
    }
    @Override
    public ResponseEntity<ResponseObject> deleteCartById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> calTotalPrice(Long id) {
        Optional<Cart> carts=this.cartRepository.findAllById(id);
        if(!carts.isPresent()){
            throw new ResourceNotFoundException("Cart not found");
        }
        List<CartDetail> cartDetailList=this.cartDetailRepository.findALLByCartId(id);
        if(cartDetailList.isEmpty()){
            throw new ResourceNotFoundException("Cart Detail not found");
        }
        Long sum= 0L;
        for(CartDetail item :cartDetailList){
            sum+=item.getPrice() *item.getQuantity();
        }
        Cart cart=carts.get();
        cart.setGrandTotal(sum);
        this.cartRepository.save(cart);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Find product successfully!", cart));
    }
}
