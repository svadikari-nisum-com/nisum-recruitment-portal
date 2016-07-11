package com.nisum.employee.ref.view;

import java.util.List;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InterviewFeedbackDTO {
	private String roundName;
	private String interviewerName;
	private String interviewerEmail;
	private String jobcode;
	private String interviewDateTime;
	private String typeOfInterview;
	private List<RatingDTO> rateSkills;
	private String additionalSkills;
	private String strengths;
	private String improvement;
	private String duration;
	private String candidateId;
	private String candidateName;

}
