package com.hcmute.baloshop.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageProductStorageService {
    String storeFile(MultipartFile file);

    byte[] readFileContent(String fileName);

    void deleteFile(String name);
}