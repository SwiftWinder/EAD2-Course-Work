package com.ecommerce.onlinefilestoragemicroservice.controller;

import com.ecommerce.onlinefilestoragemicroservice.model.FileMetadata;
import com.ecommerce.onlinefilestoragemicroservice.service.S3FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private S3FileStorageService s3FileStorageService;

    // Endpoint for file upload
    @PostMapping("/upload")
    public ResponseEntity<FileMetadata> uploadFile(@RequestParam("file") MultipartFile file) {
        FileMetadata fileMetadata = s3FileStorageService.storeFile(file);
        return ResponseEntity.ok(fileMetadata);
    }

    // Endpoint for getting file metadata
    @GetMapping("/{id}")
    public ResponseEntity<FileMetadata> getFileMetadata(@PathVariable Long id) {
        FileMetadata fileMetadata = s3FileStorageService.getFileMetadata(id);
        return ResponseEntity.ok(fileMetadata);
    }

    // Endpoint for file download (returns S3 URL)
    @GetMapping("/download/{id}")
    public ResponseEntity<String> downloadFile(@PathVariable Long id) {
        String fileUrl = s3FileStorageService.downloadFile(id);
        return ResponseEntity.ok(fileUrl);
    }

    // Endpoint for updating file metadata
    @PutMapping("/update/{id}")
    public ResponseEntity<FileMetadata> updateFileMetadata(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        FileMetadata updatedFileMetadata = s3FileStorageService.updateFile(id, file);
        return ResponseEntity.ok(updatedFileMetadata);
    }

    // Endpoint for deleting a file
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        s3FileStorageService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint for viewing all files
    @GetMapping("/list")
    public ResponseEntity<List<FileMetadata>> listFiles() {
        List<FileMetadata> fileList = s3FileStorageService.listFiles();
        return ResponseEntity.ok(fileList);
    }
}
