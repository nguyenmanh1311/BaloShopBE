package com.hcmute.baloshop.controller;

import com.hcmute.baloshop.dto.user.UserCreateRequest;
import com.hcmute.baloshop.dto.user.UserRequestDTO;
import com.hcmute.baloshop.entities.User;
import com.hcmute.baloshop.models.ResponseObject;
import com.hcmute.baloshop.services.ImageProductStorageService;
import com.hcmute.baloshop.services.UserService;
import com.hcmute.baloshop.utils.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired private ImageProductStorageService storageService;
    @RolesAllowed({"ROLE_ADMIN"})
    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllUser(){
        return this.userService.getAllUser();
    }

    @RequestMapping(value = "image/{fileName}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImageUser(@PathVariable("fileName") String fileName) {
        byte[] bytes = storageService.readFileContent(fileName);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
    }
    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getUserById(@PathVariable(name = "userId") Long userId) {
        return  this.userService.getById(userId);
    }
//    @RequestMapping(value = "", method = RequestMethod.POST)
//    public ResponseEntity<ResponseObject> updateInfo(@RequestParam UserRequestDTO userRequestDTO) {
//        return  this.userService.updateInfo(userRequestDTO);
//    }
    @RolesAllowed({"ROLE_ADMIN"})
    @RequestMapping(value = "status/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseObject> updateStatus( @PathVariable(name = "userId") Long userId) {
        return this.userService.updateStatus(userId);
    }
    @RolesAllowed({"ROLE_ADMIN"})
    @RequestMapping(value = "admin/create", method = RequestMethod.POST)
    public ResponseEntity<ResponseObject> updateStatus(@RequestBody UserCreateRequest userCreateRequest) {
        return this.userService.createNewAccount(userCreateRequest);
    }
    @GetMapping("admin/{id}")
    public ResponseEntity<ResponseObject> getAllAdmin(@PathVariable(name = "id") Long id){
        return this.userService.getAllAdmin(id);
    }
}
