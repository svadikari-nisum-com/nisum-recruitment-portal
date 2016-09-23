package com.nisum.employee.ref.view;

import java.util.List;

import com.nisum.employee.ref.domain.TimeSlots;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterviewRoundsDTO {
	private String emailId;
	private String name;
	private int noOfRoundsScheduled;
	List<TimeSlots> timeSlots;
}
