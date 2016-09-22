package com.nisum.employee.ref.repository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

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
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.nisum.employee.ref.domain.InterviewDetails;
import com.nisum.employee.ref.domain.Offer;
import com.nisum.employee.ref.exception.ServiceException;
import com.nisum.employee.ref.util.Constants;

@Slf4j
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
			offer.setPersisted(Boolean.TRUE);
			updateOffer(offer);
		}
	}

	public void saveResumeInBucket(MultipartFile multipartFile,
			String candidateId) throws ServiceException {
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
			throw new ServiceException(e);
		}
	}
	
	
	public void saveOfferInBucket(ByteArrayOutputStream output,String candidateName,String emailId) throws ServiceException {
		DBObject metaData = new BasicDBObject();
		metaData.put("candidateName", candidateName);
		metaData.put("candidateid", emailId);
		try {
			GridFS gridFS = new GridFS(dbFactory.getDb(), "offerletter");
			GridFSInputFile gridFSInputFile = gridFS.createFile(output.toByteArray());
			gridFSInputFile.setMetaData(metaData);
			gridFSInputFile.setFilename(candidateName + Constants.FILE_EXT);
			gridFSInputFile.setContentType(Constants.PDF_CONTENT_TYPE);
			gridFSInputFile.saveChunks();
			gridFSInputFile.save();
		} catch (IOException e) {
			throw new ServiceException(e);
		}
	}

	public List<Offer> getOffers() {
		return mongoOperations.findAll(Offer.class);
	}
	
	public Offer getOffer(String emailId) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").regex(emailId));
		return mongoOperations.findOne(query, Offer.class);
		
		
	}

	
	public void updateOffer(Offer offer) {
		Query query = new Query();
		offer.setPersisted(Boolean.TRUE);
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
	
	public String[] getData(String emailId) throws ServiceException {
		
		try{
			GridFS gridFS = new GridFS(dbFactory.getDb(), "offerletter");
			List<GridFSDBFile> file = gridFS.find(new Query().addCriteria(Criteria.where("metadata.candidateid")
					.is(emailId)).getQueryObject());
			
			File temp = null;
			if (file.get(0).getFilename().contains(Constants.FILE_EXT.toString().toLowerCase())) {
				temp = File.createTempFile(file.get(0).getFilename(), Constants.FILE_EXT.toString().toLowerCase());
			}else {
				log.info("Invalid File Type!");
			}
			
			if (temp != null) {
				String tempPath = temp.getAbsolutePath();
				file.get(0).writeTo(tempPath);
				return new String[] { tempPath, file.get(0).getFilename() };
			} 
			return new String[] {};
		}catch(IOException ex){
			throw new ServiceException(ex);
		}
	}
	
	public List<Offer> getOffersByJobcode(String jobcode) {
		Query query = new Query();
		query.addCriteria(Criteria.where("jobcodeProfile").regex(jobcode));
		List<Offer> offers = mongoOperations.find(query, Offer.class);
		return offers;
	}

	public void updateInterviewDetails(Offer offerEntity) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").regex(offerEntity.getEmailId()));
		InterviewDetails interviewDetails = mongoOperations.findOne(query, InterviewDetails.class);
		if (interviewDetails != null) {
			Update update = new Update();
			update.set("progress", offerEntity.getStatus());
			mongoOperations.updateFirst(query, update, InterviewDetails.class);
		}
	}
}
