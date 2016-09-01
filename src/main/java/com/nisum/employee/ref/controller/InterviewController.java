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
import org.springframework.web.bind.annotation.ResponseBody;

import com.nisum.employee.ref.domain.InterviewDetails;
import com.nisum.employee.ref.domain.InterviewFeedback;
import com.nisum.employee.ref.domain.InterviewSchedule;
import com.nisum.employee.ref.service.InterviewDetailsService;

@RequestMapping("/interview")
@Controller
public class InterviewController {
	
	@Autowired
	private InterviewDetailsService interviewDetailsService;
	
	@Secured({"ROLE_ADMIN","ROLE_HR","ROLE_RECRUITER","ROLE_MANAGER","ROLE_INTERVIEWER"})
	@RequestMapping(value="/schedule", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> createInterviewSchedule(@RequestBody InterviewSchedule interviewSchedule) throws Exception {
		InterviewDetails interviewSchedule2 = interviewDetailsService.scheduleInterview(interviewSchedule);
		return new ResponseEntity<InterviewDetails>(interviewSchedule2, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_HR","ROLE_RECRUITER","ROLE_MANAGER","ROLE_INTERVIEWER"})
	@RequestMapping(value="/schedule", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> updateSchedule(@RequestBody InterviewSchedule interviewSchedule) throws Exception {
		InterviewDetails interviewSchedule2 = interviewDetailsService.updateInterview(interviewSchedule);
		return new ResponseEntity<InterviewDetails>(interviewSchedule2, HttpStatus.OK);
	}

	@Secured({"ROLE_ADMIN","ROLE_HR","ROLE_RECRUITER","ROLE_MANAGER","ROLE_INTERVIEWER"})
	@RequestMapping(value = "/feedback", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> saveFeedback(@RequestBody InterviewFeedback interviewFeedback) {
		try {
			interviewDetailsService.saveFeedback(interviewFeedback);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<InterviewFeedback>(interviewFeedback, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_HR","ROLE_RECRUITER","ROLE_MANAGER","ROLE_INTERVIEWER"})
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> createInterviewDetails(@RequestBody InterviewDetails interviewDetails) {
		try {
			interviewDetailsService.createInterview(interviewDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<InterviewDetails>(interviewDetails, HttpStatus.OK);
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
	@ResponseBody
	public ResponseEntity<?> updateIntewrviewDetails(@RequestBody InterviewDetails interviewDetails) {
		interviewDetailsService.updateInterviewDetails(interviewDetails);
		String successmessage = "{\"msg\":\"Profile successfully Updated\"}";
		return new ResponseEntity<String>(successmessage, HttpStatus.OK);
	}

}
