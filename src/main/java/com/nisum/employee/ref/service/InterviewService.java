package com.nisum.employee.ref.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nisum.employee.ref.domain.InterviewDetails;
import com.nisum.employee.ref.repository.InterviewRepository;

@Service
public class InterviewService implements IInterviewService{
	
	@Autowired
	private InterviewRepository interviewRepository;
	
	@Override
	public void prepareInterview(InterviewDetails interview) {
		interviewRepository.save(interview);
	}
	
	@Override
	public List<InterviewDetails> interviewCheck(String candidateId) {
		return interviewRepository.interviewCheck(candidateId);
	}

}
