package com.SSAssignment.onboardify.trainingcontentmanagementservice.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.SSAssignment.onboardify.trainingcontentmanagementservice.dto.Folder;

import jakarta.servlet.http.HttpServletRequest;

public interface TrainingManagementServiceInt {

	ResponseEntity<Object> getAvailableFolders(String teamName, String folderPath);

	ResponseEntity<Object> addFolder(Folder folder);

	ResponseEntity<Object> getDocument(String teamName, long documentId);

	ResponseEntity<Object> addDocument(HttpServletRequest httpServletRequest, MultipartFile content);

}
