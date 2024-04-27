package com.SSAssignment.onboardify.trainingcontentmanagementservice.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SSAssignment.onboardify.trainingcontentmanagementservice.dto.Document;
import com.SSAssignment.onboardify.trainingcontentmanagementservice.dto.Folder;
import com.SSAssignment.onboardify.trainingcontentmanagementservice.repository.DocumentRepository;
import com.SSAssignment.onboardify.trainingcontentmanagementservice.repository.FolderRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TrainingContentServiceImpl implements TrainingManagementServiceInt{

	@Autowired
	private FolderRepository folderRepository;
	
	@Autowired
	private DocumentRepository documentRepository;
	
	@Override
	public ResponseEntity<Object> getAvailableFolders(String teamName, String folderName) {
		List<Folder> folders = folderRepository.findByTeamNameAndFolderName(teamName, folderName);
		if(folders.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("There are no folders with name " + folderName + " under team " + teamName);
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(folders);
	}

	@Override
	public ResponseEntity<Object> addFolder(Folder folder) {
		Folder existingFolder = null;
		try {
			existingFolder = folderRepository.findByTeamNameAndFolderNameAndParentId(folder.getTeamName(),folder.getFolderName(),folder.getParentId());
		}catch(Exception ex) {}
		if(existingFolder!=null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Folder " + folder.getFolderName() + " already exists inside folder with id: " + folder.getParentId() + ", for team " + folder.getTeamName());
		} else {
			try {
				folderRepository.save(folder);
				return ResponseEntity.status(HttpStatus.CREATED)
						.body("Folder " + folder.getFolderName() + " added for team " + folder.getTeamName() + " with folder id " + folder.getFolderId());
			}catch(Exception ex) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Unable to add folder. Server has encountered a problem");
			}
		}
	}

	@Override
	public ResponseEntity<Object> getDocument(String teamName, long documentId) {
		Optional<Document> documentOptional = documentRepository.findByTeamNameAndDocumentId(teamName, documentId);
        if (documentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Requested document not found");
        }

        Document document = documentOptional.get();

        // Set content type and headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("filename", document.getDocumentName());

        return new ResponseEntity<>(document.getContent(), headers, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> addDocument(HttpServletRequest httpServletRequest, MultipartFile content) {
		Document existingDocument = null;
		String teamName = httpServletRequest.getParameter("teamName");
		String documentName = httpServletRequest.getParameter("documentName");
		long folderId = Long.valueOf(httpServletRequest.getParameter("folderId"));
		byte[] contentBytes = null;
		try {
			contentBytes = (content).getBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			existingDocument = documentRepository.findByTeamNameAndDocumentNameAndFolderId(teamName, documentName, folderId);
		}catch(Exception ex) {}
		if(existingDocument!=null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Document " + documentName + " already exists inside folder with id: " + folderId + ", for team " + teamName);
		} else {
			try {
				Document document = new Document();
	            document.setDocumentName(documentName);
	            document.setContent(contentBytes);
	            document.setCreatorName(httpServletRequest.getParameter("creatorName"));
	            document.setTeamName(teamName);
	            document.setFolderId(folderId);
				documentRepository.save(document);
				return ResponseEntity.status(HttpStatus.CREATED)
						.body("Document " + document.getDocumentName() + " added for team " + document.getTeamName() + " with document id " + document.getFolderId());
			}catch(Exception ex) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Unable to add folder. Server has encountered a problem");
			}
		}
	}

}
