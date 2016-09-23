package com.nisum.employee.ref.repository;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.nisum.employee.ref.domain.InterviewDetails;

@Repository
public class InterviewRepository{
	
	@Autowired
	private MongoOperations mongoOperations;
	
	
	public void save(InterviewDetails interview) {
		mongoOperations.save(interview);
	}
	
	public List<InterviewDetails> interviewCheck(String candidateId) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("candidateId").regex(Pattern.compile(candidateId, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		List<InterviewDetails> checkDetails = mongoOperations.find(query, InterviewDetails.class);
		return checkDetails;
	}

}
