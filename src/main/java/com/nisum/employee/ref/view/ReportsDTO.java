package com.nisum.employee.ref.view;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ReportsDTO {

	private List<ProfileDTO> profiles;
	private List<InterviewDetailsDTO> interviewDetails;
}
