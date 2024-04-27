package com.SSAssignment.onboardify.trainingcontentmanagementservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SSAssignment.onboardify.trainingcontentmanagementservice.dto.Folder;
import com.SSAssignment.onboardify.trainingcontentmanagementservice.service.TrainingManagementServiceInt;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/training-content-management-service")
public class TrainingContentController {

	@Autowired
	private TrainingManagementServiceInt trainingManagementService;
	
	@GetMapping("/team/{teamName}/folder/{folderName}")
	private ResponseEntity<Object> retrieveAvailableFolders(@PathVariable String teamName, @PathVariable String folderName) {
		 return trainingManagementService.getAvailableFolders(teamName, folderName);
	}
	
	@PostMapping("/folder")
	private ResponseEntity<Object> addFolder(@RequestBody Folder folder) {
		return trainingManagementService.addFolder(folder);
	}
	
	@GetMapping("/team/{teamName}/document/{documentId}")
	private ResponseEntity<Object> retrieveDocument(@PathVariable String teamName, @PathVariable long documentId) {
		 return trainingManagementService.getDocument(teamName, documentId);
	}
	
	@PostMapping("/document")
	private ResponseEntity<Object> addDocument(HttpServletRequest servletRequest, @RequestParam("content") MultipartFile content) {
		return trainingManagementService.addDocument(servletRequest, content);
	}
}
