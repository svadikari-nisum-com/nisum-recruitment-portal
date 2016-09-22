package com.nisum.employee.ref.view;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientInfoDTO extends BaseDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String clientId;
	private String clientName;
	private String locations;
	private InterviewerDTO interviewers;
}
