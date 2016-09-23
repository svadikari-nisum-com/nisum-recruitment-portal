package com.nisum.employee.ref.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import com.nisum.employee.ref.domain.InfoEntity;

@Repository
public class InfoRepository {

	@Autowired
	private MongoOperations mongoOperations;
	
	public List<InfoEntity> retrieveSkills() {
		return mongoOperations.findAll(InfoEntity.class);
	}
	
	public void prepareInfo(InfoEntity info) {
		mongoOperations.save(info);
	}
	
	public void updateInfo(InfoEntity info) {
		mongoOperations.save(info);
	}
}
