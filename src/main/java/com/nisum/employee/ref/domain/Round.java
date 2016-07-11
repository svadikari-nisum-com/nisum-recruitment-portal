package com.nisum.employee.ref.domain;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Round {
	public Round() {}
	private String roundName;
	private InterviewSchedule interviewSchedule;
	private InterviewFeedback interviewFeedback;
}
