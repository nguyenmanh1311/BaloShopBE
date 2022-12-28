package com.hcmute.baloshop.services.implement;

import com.hcmute.baloshop.dto.address.AddressRequestDTO;
import com.hcmute.baloshop.dto.address.AddressRequestUpdateDTO;
import com.hcmute.baloshop.dto.address.AddressResponseDTO;
import com.hcmute.baloshop.entities.*;
import com.hcmute.baloshop.exceptions.ResourceNotFoundException;
import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.repositories.AddressRepository;
import com.hcmute.baloshop.repositories.UserRepository;
import com.hcmute.baloshop.services.AddressService;
import com.hcmute.baloshop.services.ImageProductStorageService;
import com.hcmute.baloshop.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.dynamic.DynamicType;
import org.apache.commons.math3.analysis.function.Add;
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
@Slf4j
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public ResponseEntity<ResponseObject> createNewAddress(AddressRequestDTO request) {
        Optional<User> user =this.userRepository.findById(request.getUserId());
        if(!user.isPresent())
            throw new ResourceNotFoundException("User not found");
        Address address=new Address();
        address.setAddressLine(request.getAddressLine());
        address.setDistrict(request.getDistrict());
        address.setProvince(request.getProvince());
        address.setWard(request.getWard());
        address.setFullName(request.getFullName());
        address.setPhone(request.getPhone());
        address.setUserId(request.getUserId());
        address.setDistrictId(request.getDistrictId());
        address.setWardId(request.getWardId());
        address.setProvinceId(request.getProvinceId());
        address.setIsDelete(false);
        this.addressRepository.save(address);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Create address successfully!", address));
    }

    @Override
    public ResponseEntity<ResponseObject> updateAddress(Long id,AddressRequestUpdateDTO request) {
        List<Address> getAddress = this.addressRepository.findAllByIdAndIsDelete(id);
        if (getAddress.isEmpty())
            throw new ResourceNotFoundException("Not found address");
        Address address=getAddress.get(0);
        address.setAddressLine(request.getAddressLine());
        address.setDistrict(request.getDistrict());
        address.setProvince(request.getProvince());
        address.setWard(request.getWard());
        address.setFullName(request.getFullName());
        address.setPhone(request.getPhone());
        address.setDistrictId(request.getDistrictId());
        address.setWardId(request.getWardId());
        address.setProvinceId(request.getProvinceId());
        this.addressRepository.save(address);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Update address successfully!", address));
    }

    @Override
    public ResponseEntity<ResponseObject> getAllAddressByUserId(Long userId) {
        Optional<User> user =this.userRepository.findById(userId);
        if(!user.isPresent())
            throw new ResourceNotFoundException("User not found");
        List<Address> addresses=this.addressRepository.findAllByUserIdAndIsDelete(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Find address successfully!", addresses));
    }
    @Override
    public ResponseEntity<ResponseObject> deleteAddressById(Long id) {
        Optional<Address> address = this.addressRepository.findById(id);
        if (address.isPresent()) {
            address.get().setIsDelete(true);
            this.addressRepository.save(address.get());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK, "Delete Address successfully!", address));
        }
        throw new ResourceNotFoundException("Not found address");
    }

    @Override
    public ResponseEntity<ResponseObject> getAddressById(Long id) {
        Optional<Address> address = this.addressRepository.findById(id);
        if (!address.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK, "Get Address successfully!", address.get()));
        }
        throw new ResourceNotFoundException("Not found address");
    }
}
