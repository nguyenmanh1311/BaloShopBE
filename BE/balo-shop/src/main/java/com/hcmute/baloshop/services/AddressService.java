package com.hcmute.baloshop.services;

import com.hcmute.baloshop.dto.address.AddressRequestDTO;
import com.hcmute.baloshop.dto.address.AddressRequestUpdateDTO;
import com.hcmute.baloshop.models.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface AddressService {
    ResponseEntity<ResponseObject> createNewAddress(AddressRequestDTO request);
    ResponseEntity<ResponseObject> updateAddress(Long id,AddressRequestUpdateDTO request);
    ResponseEntity<ResponseObject> getAllAddressByUserId(Long userId);
    ResponseEntity<ResponseObject> deleteAddressById(Long id);

    ResponseEntity<ResponseObject> getAddressById(Long id);
}
