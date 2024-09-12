package com.ecommerce.onlinefilestoragemicroservice.service;

import com.ecommerce.onlinefilestoragemicroservice.exception.ResourceNotFoundException;
import com.ecommerce.onlinefilestoragemicroservice.model.FileMetadata;
import com.ecommerce.onlinefilestoragemicroservice.repository.FileMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@Service
public class S3FileStorageService {

    private final S3Client s3Client;
    private final String bucketName;

    @Autowired
    private FileMetadataRepository fileMetadataRepository;

    public S3FileStorageService(@Value("${cloud.aws.s3.bucket}") String bucketName,
                                @Value("${cloud.aws.credentials.access-key}") String accessKey,
                                @Value("${cloud.aws.credentials.secret-key}") String secretKey) {
        this.bucketName = bucketName;
        this.s3Client = S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    // Store file in S3
    public FileMetadata storeFile(MultipartFile file) {
        validateFile(file);

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        try {
            s3Client.putObject(
                    PutObjectRequest.builder().bucket(bucketName).key(fileName).build(),
                    RequestBody.fromBytes(file.getBytes())
            );

            URL fileUrl = s3Client.utilities().getUrl(GetUrlRequest.builder().bucket(bucketName).key(fileName).build());

            FileMetadata fileMetadata = new FileMetadata(file.getOriginalFilename(), fileUrl.toString());
            return fileMetadataRepository.save(fileMetadata);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }

    // Update file metadata
    public FileMetadata updateFile(Long id, MultipartFile file) {
        // Retrieve the existing file metadata
        FileMetadata existingMetadata = fileMetadataRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File not found with id: " + id));

        // Delete the old file from S3 before updating
        deleteFileFromS3(existingMetadata.getFileUrl());

        // Validate the new file
        validateFile(file);

        // Generate a new unique file name
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        try {
            // Upload the new file to S3 bucket
            s3Client.putObject(
                    PutObjectRequest.builder().bucket(bucketName).key(fileName).build(),
                    RequestBody.fromBytes(file.getBytes())
            );

            // Get the URL of the newly uploaded file
            URL fileUrl = s3Client.utilities().getUrl(GetUrlRequest.builder().bucket(bucketName).key(fileName).build());

            // Update the existing metadata with new file name and file URL
            existingMetadata.setFileName(file.getOriginalFilename());
            existingMetadata.setFileUrl(fileUrl.toString());

            // Save the updated metadata to the database
            return fileMetadataRepository.save(existingMetadata);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }


    // Delete file from S3
    public void deleteFile(Long id) {
        FileMetadata fileMetadata = fileMetadataRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File not found with id: " + id));

        deleteFileFromS3(fileMetadata.getFileUrl());
        fileMetadataRepository.delete(fileMetadata);
    }

    private void deleteFileFromS3(String fileUrl) {
        String key = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);

        s3Client.deleteObject(
                DeleteObjectRequest.builder().bucket(bucketName).key(key).build()
        );
    }

    // List all files
    public List<FileMetadata> listFiles() {
        return fileMetadataRepository.findAll();
    }

    // Validate file for emptiness and type
    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        if (!file.getContentType().startsWith("image/")) {
            throw new RuntimeException("Only image files are allowed");
        }
    }

    public String downloadFile(Long id) {
        FileMetadata fileMetadata = fileMetadataRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File not found with id: " + id));
        return fileMetadata.getFileUrl();
    }

    public FileMetadata getFileMetadata(Long id) {
        return fileMetadataRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File not found with id: " + id));
    }
}
