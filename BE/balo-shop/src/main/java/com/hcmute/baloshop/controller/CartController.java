package com.hcmute.baloshop.controller;

import com.hcmute.baloshop.dto.cart.CartRequestDTO;
import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CartController {
    @Autowired
    private CartService cartService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ResponseObject> createNewCart(
            @RequestBody CartRequestDTO request) {
        return this.cartService.createCart(request);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getCartById(
            @PathVariable(name = "id") Long id) {
        return this.cartService.getCartById(id);
    }
    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCartByUserId(
            @PathVariable(name = "id") Long id) {
        return this.cartService.getCartByUserId(id,false);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseObject> updateCartById(
            @PathVariable(name = "id") Long id, @RequestBody CartRequestDTO request) {
        return this.cartService.updateCart(id, request);
    }
    @RequestMapping(value = "cal/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> calCartTotalPrice(
            @PathVariable(name = "id") Long id) {
        return this.cartService.calTotalPrice(id);
    }
}
