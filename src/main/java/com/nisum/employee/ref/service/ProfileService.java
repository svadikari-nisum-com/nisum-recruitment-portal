package com.nisum.employee.ref.service;

import java.util.List;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.gridfs.GridFSDBFile;
import com.nisum.employee.ref.convert.ProfileConverter;
import com.nisum.employee.ref.domain.Profile;
import com.nisum.employee.ref.repository.ProfileRepository;
import com.nisum.employee.ref.view.ProfileDTO;

@Service
public class ProfileService implements IProfileService{

	@Autowired
	ProfileRepository profileRepository;
	
	@Autowired
	ProfileConverter profileConverter;

	@Override
	public String prepareCandidate(Profile candidate) throws Exception {
		List<Profile> profiles = profileRepository.retrieveAllProfiles();
		for(Profile pro : profiles){
			if(pro.getEmailId().equals(candidate.getEmailId())){
				throw new Exception("Failed To Create Profile As Candidate Already Exists!");
			}
		}
		profileRepository.prepareCandidate(candidate);
		return "Profile Successfully Created!";
	}

	@Override
	public void updateCandidate(Profile candidate) {
		profileRepository.updateCandidate(candidate);
	}
	public void updateCandidateStatus(String email,String status){
		profileRepository.updateCandidateStatus(email, status);
	}
	@Override
	public List<ProfileDTO> retrieveCandidateDetails(String emailId) {
		return profileConverter.convertToDTOs(profileRepository.retrieveCandidateDetails(emailId));
	}
	@Override
	public List<ProfileDTO> retrieveProfileByJobCode(String jobcodeProfile) {
		return profileConverter.convertToDTOs(profileRepository.retrieveProfileByJobCode(jobcodeProfile));
	}
	@Override
	public List<ProfileDTO> retrieveProfileByProfileCreatedBy(String profilecreatedBy) {
		return profileConverter.convertToDTOs(profileRepository.retrieveProfileByProfileCreatedBy(profilecreatedBy));
	}
	@Override
	public List<ProfileDTO> retrieveAllProfiles() {
		return profileConverter.convertToDTOs((profileRepository.retrieveAllProfiles()));
	}
	@Override
	public Profile deleteProfileBasedOnEmailId(String emailId) {
		return profileRepository.deleteProfileBasedOnEmailId(emailId);
	}
	@Override
	public void saveResume(MultipartFile multipartFile, String candidateId) {
		profileRepository.saveResumeInBucket(multipartFile, candidateId);
	}
	@Override
	public String[] getResume(String emailId) throws Exception {
		return profileRepository.getResume(emailId);
	}
	
	@Override
	public List<GridFSDBFile> getFileData(String emailId) throws Exception {
		
		return profileRepository.getData( emailId);
	}
	
	
}