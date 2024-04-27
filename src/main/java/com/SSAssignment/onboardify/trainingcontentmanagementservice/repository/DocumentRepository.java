package com.SSAssignment.onboardify.trainingcontentmanagementservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SSAssignment.onboardify.trainingcontentmanagementservice.dto.Document;

import jakarta.transaction.Transactional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long>{
	
	@Transactional
	Document findByTeamNameAndDocumentNameAndFolderId(String teamName, String folderName, long folderId);

	@Transactional
	Optional<Document> findByTeamNameAndDocumentId(String teamName, long documentId);
	
}
