package com.HutechB6.Ecommerce.controller;

import com.HutechB6.Ecommerce.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class UploadController {

    private FirebaseService firebaseService;

    public UploadController(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @PostMapping("/upload-to-images")
    public ResponseEntity<String> uploadFileToImages(@RequestParam("file") MultipartFile file) throws IOException {
            String fileUrl = firebaseService.uploadImages(file);
            return ResponseEntity.ok(fileUrl);
    }
    @PostMapping("/upload-to-avatar")
    public ResponseEntity<String> uploadFileToAvatars(@RequestParam("file") MultipartFile file) throws IOException {
        String fileUrl = firebaseService.uploadAvatars(file);
        return ResponseEntity.ok(fileUrl);
    }

}
