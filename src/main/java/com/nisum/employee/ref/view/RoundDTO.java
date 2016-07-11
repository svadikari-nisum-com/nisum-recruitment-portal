package com.nisum.employee.ref.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class RoundDTO {
	public RoundDTO() {}
	private String roundName;
	 InterviewScheduleDTO interviewSchedule;
	 InterviewFeedbackDTO interviewFeedback;
}
