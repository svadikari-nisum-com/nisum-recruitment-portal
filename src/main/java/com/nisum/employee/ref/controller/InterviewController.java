package com.nisum.employee.ref.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.nisum.employee.ref.domain.InterviewDetails;
import com.nisum.employee.ref.domain.InterviewFeedback;
import com.nisum.employee.ref.domain.InterviewSchedule;
import com.nisum.employee.ref.exception.ServiceException;
import com.nisum.employee.ref.service.InterviewDetailsService;

@RequestMapping("/interviews")
@Controller
public class InterviewController {
	
	@Autowired
	private InterviewDetailsService interviewDetailsService;
	
	@Secured({"ROLE_ADMIN","ROLE_HR","ROLE_RECRUITER","ROLE_MANAGER","ROLE_INTERVIEWER"})
	@RequestMapping(value="/schedule", method = RequestMethod.POST)
	@ResponseStatus( value = HttpStatus.OK )
	public void createInterviewSchedule(@RequestBody InterviewSchedule interviewSchedule) throws ServiceException {
		interviewDetailsService.scheduleInterview(interviewSchedule);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_HR","ROLE_RECRUITER","ROLE_MANAGER","ROLE_INTERVIEWER"})
	@RequestMapping(value="/schedule", method = RequestMethod.PUT)
	@ResponseStatus( value = HttpStatus.OK )
	public void updateSchedule(@RequestBody InterviewSchedule interviewSchedule) throws ServiceException {

		interviewDetailsService.updateInterview(interviewSchedule);
	}

	@Secured({"ROLE_ADMIN","ROLE_HR","ROLE_RECRUITER","ROLE_MANAGER","ROLE_INTERVIEWER"})
	@RequestMapping(value = "/feedback", method = RequestMethod.POST)
	@ResponseStatus( value = HttpStatus.OK )
	public void saveFeedback(@RequestBody InterviewFeedback interviewFeedback) throws MessagingException{
		interviewDetailsService.saveFeedback(interviewFeedback);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_HR","ROLE_RECRUITER","ROLE_MANAGER","ROLE_INTERVIEWER"})
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus( value = HttpStatus.OK )
	public void createInterviewDetails(@RequestBody InterviewDetails interviewDetails) throws ServiceException{
			interviewDetailsService.createInterview(interviewDetails);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_HR","ROLE_RECRUITER","ROLE_MANAGER","ROLE_INTERVIEWER"})
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getInterviewDetails(@RequestParam(value = "interviewerId", required = false) String interviewerId,@RequestParam(value = "jobCode", required = false) String jobCode,
			@RequestParam(value = "candiateId", required = false) String candiateId, @RequestParam(value = "client", required = false) String client, 
			@RequestParam(value = "progress", required = false) String progress, @RequestParam(value = "skill", required = false) String skill, 
			@RequestParam(value = "designation", required = false) String designation, @RequestParam(value = "interviewId", required = false) String interviewId,
			@RequestParam(value = "interviewerEmail", required = false) String interviewerEmail) 
	{
		
		List<InterviewDetails> checkDetails = null;
		if(!StringUtils.isEmpty(interviewerId))
		{
			checkDetails = interviewDetailsService.getInterview(interviewerId);
		}
		else if ( !(StringUtils.isEmpty(interviewerEmail) || StringUtils.isEmpty(jobCode)) )
		{
			checkDetails = interviewDetailsService.getInterviewByInterviewerAndJobCode(interviewerEmail,jobCode);
		}
		else if(!StringUtils.isEmpty(interviewerEmail))
		{
			checkDetails = interviewDetailsService.getInterviewByInterviewer(interviewerEmail);
			
		}else if(!StringUtils.isEmpty(jobCode))
		{
			checkDetails = interviewDetailsService.getInterviewByJobCode(jobCode);
		}
		else if (!StringUtils.isEmpty(candiateId)) 
		{
			checkDetails = interviewDetailsService.getInterviewByCandidateId(candiateId);
		}
		else if (!StringUtils.isEmpty(client)) 
		{
			checkDetails = interviewDetailsService.getInterviewByClient(client);
		}
		else if (!StringUtils.isEmpty(progress)) 
		{
			checkDetails = interviewDetailsService.getInterviewByProgress(progress);
		}
		else if (!StringUtils.isEmpty(skill)) 
		{
			checkDetails = interviewDetailsService.getInterviewBySkill(skill);
		}
		else if (!StringUtils.isEmpty(designation)) 
		{
			checkDetails = interviewDetailsService.getInterviewByDesignation(designation);
		}
		else if (!StringUtils.isEmpty(interviewId)) 
		{
			checkDetails = interviewDetailsService.getInterviewByinterviewId(interviewId);
		}
		else 
		{
			checkDetails = interviewDetailsService.getAll();
		}
		return (null == checkDetails) ? new ResponseEntity<String>( "Interivew Details are not found", HttpStatus.NOT_FOUND)
				: new ResponseEntity<List<InterviewDetails>>(checkDetails, HttpStatus.OK);
		
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_HR","ROLE_RECRUITER","ROLE_INTERVIEWER"})
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus( value = HttpStatus.OK )
	public void updateIntewrviewDetails(@RequestBody InterviewDetails interviewDetails) {
		interviewDetailsService.updateInterviewDetails(interviewDetails);
	}

}
