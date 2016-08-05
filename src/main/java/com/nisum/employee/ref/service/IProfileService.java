package com.nisum.employee.ref.service;

import java.util.List;




import org.springframework.web.multipart.MultipartFile;

import com.mongodb.gridfs.GridFSDBFile;
import com.nisum.employee.ref.domain.Profile;
import com.nisum.employee.ref.view.ProfileDTO;

public interface IProfileService {
	String createCandidate(ProfileDTO candidate) throws Exception;

	void updateCandidate(Profile candidate);
	void updateCandidateStatus(String email,String status);

	List<ProfileDTO> retrieveCandidateDetails(String emailId);

	List<ProfileDTO> retrieveProfileByJobCode(String jobcodeProfile);

	List<ProfileDTO> retrieveProfileByProfileCreatedBy(String profilecreatedBy);

	List<ProfileDTO> retrieveAllProfiles();

	Profile deleteProfileBasedOnEmailId(String emailId);

	void saveResume(MultipartFile multipartFile, String candidateId);

	String[] getResume(String emailId) throws Exception;
	
	List<GridFSDBFile> getFileData(String emailId) throws Exception;
	
	
}
