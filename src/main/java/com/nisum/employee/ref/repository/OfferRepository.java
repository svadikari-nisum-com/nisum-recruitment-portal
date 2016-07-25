package com.nisum.employee.ref.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import com.nisum.employee.ref.domain.Offer;

@Repository
public class OfferRepository {

	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private MongoDbFactory dbFactory;

	public void saveOffer(Offer offer) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").regex(offer.getEmailId()));
		Offer offerOld = mongoOperations.findOne(query, Offer.class);
		if (offerOld == null) {
			mongoOperations.save(offer);
		} else {
			updateOffer(offer);
		}
	}

	public void saveResumeInBucket(MultipartFile multipartFile,
			String candidateId) {
		DBObject metaData = new BasicDBObject();
		metaData.put("candidateId", candidateId);
		try {
			InputStream inputStream = multipartFile.getInputStream();
			GridFS gridFS = new GridFS(dbFactory.getDb(), "offerletter");
			GridFSInputFile gridFSInputFile = gridFS.createFile(inputStream);
			gridFSInputFile.setMetaData(metaData);
			gridFSInputFile.setFilename(multipartFile.getOriginalFilename());
			gridFSInputFile.setContentType(multipartFile.getContentType());
			gridFSInputFile.saveChunks();
			gridFSInputFile.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Offer> getOffers() {
		return mongoOperations.findAll(Offer.class);
	}

	public void updateOffer(Offer offer) {
		Query query = new Query();
		query.addCriteria(Criteria.where("emailId").is(offer.getEmailId()));
		query.fields().include("emailId");
		Update update = new Update();
		update.set("candidateName", offer.getCandidateName());
		update.set("client", offer.getClient());
		update.set("project", offer.getProject());
		update.set("hrManager", offer.getHrManager());
		update.set("reportingManager", offer.getReportingManager());
		update.set("imigrationStatus", offer.getImigrationStatus());
		update.set("relocationAllowance", offer.getRelocationAllowance());
		update.set("singInBonus", offer.getSingInBonus());
		update.set("comments", offer.getComments());
		update.set("joiningDate", offer.getJoiningDate());
		update.set("location", offer.getLocation());
		update.set("offerLetterName", offer.getOfferLetterName());
		update.set("status", offer.getStatus());
		update.set("jobcodeProfile", offer.getJobcodeProfile());
		mongoOperations.updateFirst(query, update, Offer.class);
	}
}
