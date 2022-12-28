package com.hcmute.baloshop.services.implement;

import com.hcmute.baloshop.exceptions.FileException;
import com.hcmute.baloshop.exceptions.ResourceNotFoundException;
import com.hcmute.baloshop.services.ImageProductStorageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;

@Service
@Slf4j
public class ImageProductStorageServiceImpl implements ImageProductStorageService {
    private final Path storageFolder = Paths.get("uploads");

    public ImageProductStorageServiceImpl() {
        try {
            Files.createDirectories(storageFolder);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store empty file", e);
        }
    }

    private Boolean isImageFile(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        assert fileExtension != null;
        return Arrays.asList(new String[] {"png", "jpg", "jpeg", "bmp"})
                .contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public String storeFile(MultipartFile file) {
        try {

            log.info("Checking file...");
            // Check file is empty?
            if (file.isEmpty()) {
                log.info("File input is null");
                throw new FileException("Failed to store empty file");
            }

            // Check file is image?
            if (!isImageFile(file)) {
                log.info("You can only upload image file like .png, .jpg, .jpeg, .bmp");
                throw new FileException("You can only upload image file");
            }

            // Check file size less than 5MB?
            float fileSize = file.getSize() / 1_000_000.0f;
            if (fileSize > 5.0f) {
                log.info("File is too large");
                throw new FileException("File size must be less than 5MB");
            }

            // File must be renamed before storage
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName = UUID.randomUUID().toString().replace("-", "");
            generatedFileName = generatedFileName + "." + fileExtension;
            Path destinationFilePath =
                    this.storageFolder.resolve(Paths.get(generatedFileName)).normalize().toAbsolutePath();
            if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
                log.info("Not found path to store file");
                throw new FileException("Cannot store file outside current directory.");
            }
            // Copy file to the destination file path
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception exception) {
                throw new FileException(exception.getMessage());
            }
            log.info("New image was saved, name: {}", generatedFileName);
            return generatedFileName;
        } catch (Exception e) {
            throw new FileException("Failed to store empty file", e);
        }
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try {
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            } else {
                throw new ResourceNotFoundException("Could not read file: " + fileName);
            }
        } catch (IOException ioException) {
            throw new FileException("Could no read file: " + fileName, ioException);
        }
    }

    @Override
    public void deleteFile(String name) {
        try {
            Path storageFolder = Paths.get("uploads/" + name);
            FileSystemUtils.deleteRecursively(storageFolder.toFile());
        } catch (Exception exception) {
            throw new FileException(exception.getMessage());
        }
    }
}