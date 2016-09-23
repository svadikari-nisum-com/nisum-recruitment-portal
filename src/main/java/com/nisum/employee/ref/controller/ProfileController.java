package com.nisum.employee.ref.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.gridfs.GridFSDBFile;
import com.nisum.employee.ref.exception.ServiceException;
import com.nisum.employee.ref.service.IProfileService;
import com.nisum.employee.ref.view.ProfileDTO;

@RequestMapping("/profile")
@Slf4j
@Controller
public class ProfileController {

	@Autowired
	private IProfileService profileService;
	
	@Secured({"ROLE_ADMIN","ROLE_USER","ROLE_HR","ROLE_RECRUITER","ROLE_MANAGER","ROLE_INTERVIEWER","ROLE_LOCATIONHEAD"})
	@RequestMapping( method = RequestMethod.GET )
	public ResponseEntity<List<ProfileDTO>> retrieveProfile(@RequestParam(value = "emailId", required = false) String emailId,@RequestParam(value = "jobcodeProfile", 
											required = false) String jobcodeProfile,@RequestParam(value = "profilecreatedBy", required = false) String profilecreatedBy) 
	{
		List<ProfileDTO> positionsDetails = null;
		if (emailId != null && !emailId.isEmpty()) {
			positionsDetails = profileService.retrieveCandidateDetails(emailId);
		} else if (jobcodeProfile != null && !jobcodeProfile.isEmpty()) {
			positionsDetails = profileService.retrieveProfileByJobCode(jobcodeProfile);
		}else if(profilecreatedBy != null && !profilecreatedBy.isEmpty()){
			positionsDetails = profileService.retrieveProfileByProfileCreatedBy(profilecreatedBy);
		}else {
			positionsDetails = profileService.retrieveAllProfiles();
		}
		return (null == positionsDetails) ? new ResponseEntity<List<ProfileDTO>>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<List<ProfileDTO>>(positionsDetails, HttpStatus.OK);
	}

	@Secured({"ROLE_ADMIN","ROLE_USER","ROLE_HR","ROLE_RECRUITER","ROLE_MANAGER","ROLE_INTERVIEWER"})
	@RequestMapping( method = RequestMethod.POST )
	@ResponseBody
	public ResponseEntity<ProfileDTO> registerUser(@RequestBody ProfileDTO candidate) throws ServiceException{
			 profileService.createCandidate(candidate);
			 return new ResponseEntity<ProfileDTO>(candidate,(candidate.hasErrors() ? HttpStatus.BAD_REQUEST : HttpStatus.OK));
	}

	@Secured({"ROLE_ADMIN","ROLE_USER","ROLE_HR","ROLE_RECRUITER","ROLE_MANAGER","ROLE_INTERVIEWER"})
	@RequestMapping( method = RequestMethod.PUT )
	@ResponseBody
	public ResponseEntity<ProfileDTO> updateProfile(@RequestBody ProfileDTO candidate ) {
		
			profileService.updateCandidate(candidate);
			return new ResponseEntity<ProfileDTO>(candidate, HttpStatus.OK);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.DELETE)
	public void deleteProfile(@RequestParam(value = "emailId", required = true) String emailId) throws ServiceException {
		profileService.deleteCandidate(emailId);
	}

	@ResponseStatus(HttpStatus.OK)
//	@Secured({"ROLE_ADMIN","ROLE_USER","ROLE_HR","ROLE_RECRUITER","ROLE_MANAGER","ROLE_INTERVIEWER"})
	@RequestMapping(value = "/status", method = RequestMethod.PUT)
	public ResponseEntity<String> updateProfileStatus(@RequestParam(value = "emailId", required = true) String emailId,
			@RequestParam(value = "status", required = true) String status) throws ServiceException {
		profileService.updateCandidateStatus(emailId, status);
		return new ResponseEntity<String>("Status Updated Successfully", HttpStatus.OK);
	}
	
	//value = "/fileUpload"
	@ResponseStatus(HttpStatus.OK)
	@Secured({"ROLE_ADMIN","ROLE_USER","ROLE_HR","ROLE_RECRUITER","ROLE_MANAGER","ROLE_INTERVIEWER"})
	@RequestMapping( value = "/file", method = RequestMethod.POST)
	public ResponseEntity<String> uploadResume(HttpServletRequest request, Model model, @RequestParam(value = "file") MultipartFile multipartFile, @RequestParam(value = "candidateId", required = true) String candidateId) throws ServiceException {
		profileService.saveResume(multipartFile, candidateId);
		return new ResponseEntity<String>("Resume Uploaded Successfully", HttpStatus.OK);
	}
	
	//value = "/fileDownload"
	@ResponseStatus(HttpStatus.OK)
	@Secured({"ROLE_ADMIN","ROLE_USER","ROLE_HR","ROLE_RECRUITER","ROLE_MANAGER","ROLE_INTERVIEWER"})
	@RequestMapping( value = "/file", method = RequestMethod.GET)
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "candidateId", required = true) String candidateId) throws IOException,ServiceException  {
		List<GridFSDBFile> files = profileService.getFileData(candidateId);
		GridFSDBFile file = files.get(0);
		if (file != null) {
			response.setContentType(file.getContentType());
			response.setContentLength((int) file.getLength());
			IOUtils.copyLarge(file.getInputStream(), response.getOutputStream());
		} else {
			log.info("file does not exist");
		}
	}
}

		
