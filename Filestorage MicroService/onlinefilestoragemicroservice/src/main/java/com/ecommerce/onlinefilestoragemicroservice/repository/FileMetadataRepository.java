package com.ecommerce.onlinefilestoragemicroservice.repository;

import com.ecommerce.onlinefilestoragemicroservice.model.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
}
