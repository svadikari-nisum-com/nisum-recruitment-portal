package com.nisum.employee.ref.service;

import java.util.List;





import javax.mail.MessagingException;

import com.nisum.employee.ref.domain.InterviewDetails;
import com.nisum.employee.ref.domain.InterviewFeedback;
import com.nisum.employee.ref.domain.InterviewSchedule;
import com.nisum.employee.ref.view.InterviewDetailsDTO;
import com.nisum.employee.ref.view.InterviewFeedbackDTO;
import com.nisum.employee.ref.view.InterviewScheduleDTO;

public interface IInterviewDetailsService {
	InterviewDetails  saveFeedback(InterviewFeedback interviewFeedback) throws MessagingException;
	
	InterviewDetails  scheduleInterview(InterviewSchedule interviewSchedule) throws Exception;
	
	List<InterviewDetailsDTO> getInterview(String interviewerId);
	
	List<InterviewDetailsDTO> getInterviewByInterviewer(String interviewerEmail) ;
		
	List<InterviewDetailsDTO> getAll();
	
	List<InterviewDetailsDTO> getInterviewByJobCode(String jobCode);
	
	List<InterviewDetailsDTO> getInterviewByCandidateId(String candidateId);
	
	InterviewDetails enrichInterviewDetails(InterviewDetails interviewDetails2 ,InterviewSchedule interviewSchedule);
	
	InterviewDetails enrichInterviewDetails2(InterviewDetails interviewDetails2 ,InterviewFeedback interviewFeedback);

	InterviewDetails createInterview(InterviewDetails interviewDetails);

	List<InterviewDetailsDTO> getInterviewByClient(String client);

	List<InterviewDetailsDTO> getInterviewByProgress(String progress);

	List<InterviewDetailsDTO> getInterviewBySkill(String progress);

	List<InterviewDetailsDTO> getInterviewByDesignation(String designation);

	List<InterviewDetailsDTO> getInterviewByinterviewId(String interviewId);

	void updateInterviewDetails(InterviewDetails interviewDetails);

}
