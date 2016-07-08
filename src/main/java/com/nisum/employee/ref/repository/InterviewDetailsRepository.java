package com.nisum.employee.ref.repository;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.nisum.employee.ref.domain.InterviewDetails;
import com.nisum.employee.ref.domain.InterviewFeedback;

@Repository
public class InterviewDetailsRepository {

	@Autowired
	private MongoOperations mongoOperation;

	public InterviewDetails getInterviewDetailsById(String CandidateId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("candidateEmail").is(CandidateId));
		InterviewDetails intDetails = mongoOperation.findOne(query, InterviewDetails.class);
		return intDetails;
	}

	public void scheduleInterview(InterviewDetails interviewDetails) {
		mongoOperation.save(interviewDetails);
	}

	public void saveFeedback(InterviewFeedback interviewFeedback) {
		mongoOperation.save(interviewFeedback);
	}

	public void updateinterviewDetails(InterviewDetails interviewDetails) {
		Query query = new Query();
		query.addCriteria(Criteria.where("candidateEmail").is(interviewDetails.getCandidateEmail()));
		InterviewDetails interview = mongoOperation.findOne(query, InterviewDetails.class);
		query.fields().include("candidateEmail");
		Update update = new Update();

		update.set("candidateName", interviewDetails.getCandidateName());
		update.set("currentPositionId", interview.getCurrentPositionId());
		update.set("positionId", interviewDetails.getPositionId());
		update.set("candidateSkills", interviewDetails.getCandidateSkills());
		update.set("clientName", interview.getClientName());
		update.set("designation", interviewDetails.getDesignation());
		update.set("hrAssigned", interviewDetails.getHrAssigned());
		mongoOperation.updateFirst(query, update, InterviewDetails.class);
	}

	public List<InterviewDetails> getInterview(String interviewerId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("interviewerId")
				.regex(Pattern.compile(interviewerId, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		List<InterviewDetails> checkDetails = mongoOperation.find(query, InterviewDetails.class);
		return checkDetails;
	}

	public List<InterviewDetails> getInterviewByInterviewer(String interviewerEmail) {
		Query query = new Query();
		query.addCriteria(Criteria.where("interviewerEmail")
				.regex(Pattern.compile(interviewerEmail, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		List<InterviewDetails> checkDetails = mongoOperation.find(query, InterviewDetails.class);
		return checkDetails;
	}

	public List<InterviewDetails> getInterviewByInterviewerAndJobCode(String interviewerEmail, String jobCode) {
		Query query = new Query();
		query.addCriteria(Criteria.where("interviewerEmail")
				.regex(Pattern.compile(interviewerEmail, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
				.and("currentPositionId").regex(Pattern.compile(jobCode, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		List<InterviewDetails> checkDetails = mongoOperation.find(query, InterviewDetails.class);
		return checkDetails;
	}

	public List<InterviewDetails> getAll() {
		List<InterviewDetails> checkDetails = mongoOperation.findAll(InterviewDetails.class, "InterviewDetails");
		return checkDetails;
	}

	public List<InterviewDetails> getInterviewByJobCode(String jobCode) {
		Query query = new Query();
		query.addCriteria(
				Criteria.where("positionId").regex(Pattern.compile(jobCode, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		List<InterviewDetails> checkDetails = mongoOperation.find(query, InterviewDetails.class);
		return checkDetails;
	}

	public List<InterviewDetails> getInterviewByCandidateId(String candidateId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("candidateEmail")
				.regex(Pattern.compile(candidateId, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		List<InterviewDetails> checkDetails = mongoOperation.find(query, InterviewDetails.class);
		return checkDetails;
	}

	public List<InterviewDetails> getInterviewByClient(String client) {
		Query query = new Query();
		query.addCriteria(
				Criteria.where("clientName").regex(Pattern.compile(client, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		List<InterviewDetails> checkDetails = mongoOperation.find(query, InterviewDetails.class);
		return checkDetails;
	}

	public List<InterviewDetails> getInterviewByProgress(String progress) {
		Query query = new Query();
		query.addCriteria(
				Criteria.where("progress").regex(Pattern.compile(progress, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		List<InterviewDetails> checkDetails = mongoOperation.find(query, InterviewDetails.class);
		return checkDetails;
	}

	public List<InterviewDetails> getInterviewBySkill(String skill) {
		Query query = new Query();
		query.addCriteria(Criteria.where("candidateSkills").is(skill));
		List<InterviewDetails> checkDetails = mongoOperation.find(query, InterviewDetails.class);
		return checkDetails;
	}

	public List<InterviewDetails> getInterviewByDesignation(String designation) {
		Query query = new Query();
		query.addCriteria(Criteria.where("designation").is(designation));
		List<InterviewDetails> checkDetails = mongoOperation.find(query, InterviewDetails.class);
		return checkDetails;
	}

	public List<InterviewDetails> getInterviewByinterviewId(String interviewId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(interviewId));
		List<InterviewDetails> checkDetails = mongoOperation.find(query, InterviewDetails.class);
		return checkDetails;
	}
}
