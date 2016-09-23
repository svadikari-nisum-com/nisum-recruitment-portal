package com.nisum.employee.ref.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.nisum.employee.ref.domain.Designation;

@Repository
public class DesignationRepository {

	@Autowired
	private MongoOperations mongoOperations;
	
	public List<Designation> retrieveDesignations() {
		return (List<Designation>) mongoOperations.findAll(Designation.class);
	}
	
	public void prepareDesignations(Designation designation) {
		mongoOperations.save(designation);
	}
	
	public void updateDesignations(Designation designation) {
			Query updateQuery = new Query();
			updateQuery.addCriteria(Criteria.where("designation").is(designation.getDesignation()));
			
			Update update = new Update();
			update.set("skills", designation.getSkills());
			update.set("minExpYear", designation.getMinExpYear());
			update.set("maxExpYear", designation.getMaxExpYear());
			
			mongoOperations.updateFirst(updateQuery, update,  Designation.class);		
	}
	public void removeDesignations(String designation) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").regex(designation));
		mongoOperations.remove(mongoOperations.findOne(query, Designation.class));
	}

}
