package com.nisum.employee.ref.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
//@Setter
@AllArgsConstructor
public class Round implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String roundName;
	private InterviewSchedule interviewSchedule;
	private InterviewFeedback interviewFeedback;
}
