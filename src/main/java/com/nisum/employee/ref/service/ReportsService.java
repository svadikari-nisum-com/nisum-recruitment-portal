package com.nisum.employee.ref.service;

import java.util.List;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nisum.employee.ref.convert.InterviewDetailsConverter;
import com.nisum.employee.ref.convert.ProfileConverter;
import com.nisum.employee.ref.repository.ProfileRepository;
import com.nisum.employee.ref.view.InterviewDetailsDTO;
import com.nisum.employee.ref.view.ProfileDTO;
import com.nisum.employee.ref.view.ReportsDTO;

@Service
public class ReportsService {
	
	ReportsDTO reportsDTO = new ReportsDTO();
	
	@Autowired
	ProfileConverter profileConverter;
	
	@Autowired
	InterviewDetailsConverter interviewDetailsConverter;
	
	@Autowired
	ProfileRepository profileRepository;
	
	@Autowired
	InterviewDetailsService interviewDetailsService;
	
	public ReportsDTO getDataByJobCode(String jobcodeProfile) {
		List<ProfileDTO> profiles = profileConverter.convertToDTOs(profileRepository.retrieveProfileByJobCode(jobcodeProfile));
		List<InterviewDetailsDTO> interviewDetails =interviewDetailsService.getInterviewByJobCode(jobcodeProfile);
		reportsDTO.setProfiles(profiles);
		reportsDTO.setInterviewDetails(interviewDetails);
		return reportsDTO;
	}
}
