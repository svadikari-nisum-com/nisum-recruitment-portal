package com.nisum.employee.ref.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mongodb.gridfs.GridFSDBFile;
import com.nisum.employee.ref.domain.Profile;
import com.nisum.employee.ref.exception.ServiceException;
import com.nisum.employee.ref.view.ProfileDTO;

public interface IProfileService {
	void createCandidate(ProfileDTO candidate) throws ServiceException;

	void updateCandidate(ProfileDTO candidate);
	void updateCandidateStatus(String email,String status);

	List<ProfileDTO> retrieveCandidateDetails(String emailId);

	List<ProfileDTO> retrieveProfileByJobCode(String jobcodeProfile);

	List<ProfileDTO> retrieveProfileByProfileCreatedBy(String profilecreatedBy);

	List<ProfileDTO> retrieveAllProfiles();

	Profile deleteProfileBasedOnEmailId(String emailId);

	void saveResume(MultipartFile multipartFile, String candidateId) throws ServiceException;

	String[] getResume(String emailId) throws ServiceException;
	
	List<GridFSDBFile> getFileData(String emailId) throws ServiceException;

	void deleteCandidate(String emailId); 
	
}
