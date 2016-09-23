package com.nisum.employee.ref.repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.WriteResult;
import com.nisum.employee.ref.domain.Position;
import com.nisum.employee.ref.domain.PositionAggregate;

@Repository
public class PositionRepository {

	private static final String _ID = "_id";

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private MongoTemplate mongoTemplate;

	public void save(Position position) {
		mongoOperations.save(position);
	}

	public void preparePosition(Position position) {
		position.setStatus("Draft");
		mongoOperations.save(position);
	}

	public boolean updatePosition(Position position) {
		Query query = new Query();
		query.addCriteria(Criteria.where("jobcode").is(position.getJobcode()));
		query.fields().include("jobcode");
		Update update = new Update();
		update.set("designation", position.getDesignation());
		update.set("minExpYear", position.getMinExpYear());
		update.set("maxExpYear", position.getMaxExpYear());
		update.set("primarySkills", position.getPrimarySkills());
		update.set("secondarySkills", position.getSecondarySkills());
		update.set("interviewRounds", position.getInterviewRounds());
		update.set("jobProfile", position.getJobProfile());
		update.set("location", position.getLocation());
		update.set("noOfPositions", position.getNoOfPositions());
		update.set("client", position.getClient());
		update.set("lastModifiedDate", java.time.LocalDateTime.now());
		update.set("hiringManager", position.getHiringManager());
		update.set("priority", position.getPriority());
		update.set("interviewer", position.getInterviewer());
		update.set("jobType", position.getJobType());
		update.set("functionalGroup", position.getFunctionalGroup());
		update.set("jobHeader", position.getJobHeader());
		WriteResult result = mongoOperations.updateFirst(query, update, Position.class);
		return result!= null && result.getN()== 1 ? true : false;
	}

	public List<Position> retrieveAllPositions(String searchKey, String searchValue) {
		Query query = new Query();
		query.addCriteria(Criteria.where(searchKey).regex(Pattern.compile(searchValue, Pattern.CASE_INSENSITIVE
						| Pattern.UNICODE_CASE)));
		return mongoOperations.find(query,Position.class);
	}

	public List<Position> retrieveAllPositions() {
		return mongoOperations.findAll(Position.class);
	}

	public Position deletePositionBasedOnJC(String jobcode) {
		Query query = new Query();
		query.addCriteria(Criteria.where(_ID).regex(jobcode));
		return mongoOperations.findAndRemove(query, Position.class);
	}

	public List<PositionAggregate> retrieveAllPositionsAggregate() {
		Aggregation agg = newAggregation(
				group("designation").count().as("total"),
				project("total").and("designation").previousOperation(),
				sort(Sort.Direction.DESC, "total"));

		AggregationResults<PositionAggregate> groupResults = mongoTemplate
				.aggregate(agg, Position.class, PositionAggregate.class);
		   return groupResults.getMappedResults();
	}
	
	public void updatePositionStatus(String jobCode, String status) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(jobCode));
		Position position = mongoOperations.findOne(query, Position.class);
		position.setStatus(status);
		if(status.equals("APPROVED")){
			position.setPositionApprovedDt(new Date());
			}
		mongoOperations.save(position);
	}
	
	public Position retrievePositionByJobCode(String jobCode) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(jobCode));
		return mongoOperations.findOne(query,Position.class);
	}
}
