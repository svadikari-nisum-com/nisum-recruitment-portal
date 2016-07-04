package com.nisum.employee.ref.repository;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.nisum.employee.ref.domain.InterviewDetails;

@Repository
public class InterviewRepository{
	
	@Autowired
	private MongoOperations mongoOperation;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void save(InterviewDetails interview) {
		mongoOperation.save(interview);
	}
	
	public List<InterviewDetails> interviewCheck(String candidateId) {
		MongoOperations mongoOperations = (MongoOperations) mongoTemplate;
		Query query = new Query();
		query.addCriteria(Criteria.where("candidateId").regex(Pattern.compile(candidateId, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		List<InterviewDetails> checkDetails = mongoOperations.find(query, InterviewDetails.class);
		return checkDetails;
	}

}
