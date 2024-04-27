package com.SSAssignment.onboardify.trainingcontentmanagementservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SSAssignment.onboardify.trainingcontentmanagementservice.dto.Folder;

@Repository
public interface FolderRepository  extends JpaRepository<Folder, Long> {

	Folder findByTeamNameAndFolderNameAndParentId(String teamName, String folderName, long parentId);
	
	List<Folder> findByTeamNameAndFolderName(String teamName, String folderName);
}
