package com.hcmute.baloshop.controller;

import com.hcmute.baloshop.dto.cart.CartRequestDTO;
import com.hcmute.baloshop.dto.cartdetail.CartDetailChangeQuantityDto;
import com.hcmute.baloshop.dto.cartdetail.CartDetailRequestDTO;
import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.services.CartDetailService;
import com.hcmute.baloshop.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cartdetail")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CartDetailController {
    @Autowired
    private CartDetailService cartDetailService;
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ResponseObject> createNewCartDetail(
            @RequestBody CartDetailRequestDTO request) {
        return this.cartDetailService.createCartDetail(request);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getAllCartDetailByCartId(
            @PathVariable(name = "id") Long id) {
        return this.cartDetailService.getAllCartDetailByCartId(id);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseObject> deleteCartDetailById(@PathVariable(name = "id") Long id) {
        return this.cartDetailService.deleteCartDetailById(id);
    }
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<ResponseObject> update(
            @RequestBody CartDetailChangeQuantityDto cartDetailChangeQuantityDto) {
        return this.cartDetailService.updateQuantity(cartDetailChangeQuantityDto);
    }
}
