package com.nisum.employee.ref.domain;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReportsVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<Profile> profiles;
	private List<InterviewDetails> interviewDetails;
	private int noOfOpenPositions;
	private int profilesInTechnicalRound1;
	private int profilesInTechnicalRound2;
	private int profilesInManagerRound;
	private int profilesInHRRound;
	private int offered;
	private int closed;
	private String functionalGrp;
	private String avgProfileTime;
	private String avgRound1Time;
	private String avgRound2Time;
	private String positionId;
	private String avgHRRoundTime;
	private String avgTimeOffered;
	private String avgTimeClosed;
	
	public ReportsVO(){}

}