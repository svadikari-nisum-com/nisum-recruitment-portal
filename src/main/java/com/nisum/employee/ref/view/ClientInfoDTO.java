package com.nisum.employee.ref.view;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientInfoDTO {
	String clientId;
	String clientName;
	String locations;
	InterviewerDTO interviewerDTO;
}
