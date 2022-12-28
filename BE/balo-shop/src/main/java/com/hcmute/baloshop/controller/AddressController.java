package com.hcmute.baloshop.controller;

import com.hcmute.baloshop.dto.address.AddressRequestDTO;
import com.hcmute.baloshop.dto.address.AddressRequestUpdateDTO;
import com.hcmute.baloshop.dto.product.ProductRequestDTO;
import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.services.AddressService;
import com.hcmute.baloshop.services.AuthenticationService;
import com.hcmute.baloshop.services.ProductService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/address")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @GetMapping("{id}")
    public ResponseEntity<ResponseObject> getAddressById(@PathVariable("id")Long id){
        return addressService.getAddressById(id);
    }
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ResponseObject> createNewAddress(
            @RequestBody AddressRequestDTO request) {
        return this.addressService.createNewAddress(request);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseObject> updateAdress(
            @PathVariable(name = "id") Long id, @RequestBody AddressRequestUpdateDTO request) {
        return this.addressService.updateAddress(id, request);
    }
    @RequestMapping(value = "user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getAddressByUserId(
            @PathVariable(name = "userId") Long userId) {
        return this.addressService.getAllAddressByUserId(userId);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseObject> deleteAddressById(@PathVariable(name = "id") Long id) {
        return this.addressService.deleteAddressById(id);
    }
}
