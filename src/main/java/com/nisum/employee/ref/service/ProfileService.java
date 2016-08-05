package com.nisum.employee.ref.service;

import java.util.List;











import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.gridfs.GridFSDBFile;
import com.nisum.employee.ref.common.ErrorCodes;
import com.nisum.employee.ref.converter.ProfileConverter;
import com.nisum.employee.ref.domain.InterviewDetails;
import com.nisum.employee.ref.domain.Profile;
import com.nisum.employee.ref.repository.InterviewDetailsRepository;
import com.nisum.employee.ref.repository.ProfileRepository;
import com.nisum.employee.ref.view.ProfileDTO;

@Service
public class ProfileService implements IProfileService{

	@Autowired
	ProfileRepository profileRepository;
	
	@Autowired
	ProfileConverter profileConverter;
	
	@Autowired
	private InterviewDetailsRepository interviewDetailsRepository;
	
	@Autowired
	private MessageSourceAccessor messageSourceAccessor;


	@Override
	public String createCandidate(ProfileDTO candidate) throws Exception {
		List<ProfileDTO> profilesList = profileConverter.convertToDTOs((profileRepository.retrieveAllProfiles()));
		for(ProfileDTO profiles : profilesList){
			if(profiles.getEmailId().equals(candidate.getEmailId())){
				profiles.addError(ErrorCodes.NRP0003, messageSourceAccessor.getMessage(ErrorCodes.NRP0003));
				  return profiles.getErrors().get(0).getCode();
			}
		}
		profileRepository.createCandidate(profileConverter.convertToEntity(candidate));
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
		//return profileConverter.convertToDTOs((profileRepository.retrieveAllProfiles()));
		List<ProfileDTO> profileDetails = profileConverter.convertToDTOs((profileRepository.retrieveAllProfiles()));
		 for (ProfileDTO profileDTO : profileDetails) {
			 String emailId = profileDTO.getEmailId();
			 InterviewDetails interviewDetails = interviewDetailsRepository.getInterviewDetailsById(emailId);
			 String interviewProgress = interviewDetails.getProgress();
			 profileDTO.setInterviewProgress(interviewProgress);
		 }
		return profileDetails;
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