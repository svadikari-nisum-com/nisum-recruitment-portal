package com.nisum.employee.ref.repository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.nisum.employee.ref.domain.Profile;
import com.nisum.employee.ref.exception.ServiceException;

@Slf4j
@Repository
public class ProfileRepository {
	@Autowired
	private MongoOperations mongoOperations;

	@Autowired
	private MongoDbFactory dbFactory;

	public void createCandidate(Profile candidate) {
		mongoOperations.save(createProfile(candidate));
	}

	public Profile createProfile(Profile candidate) {
		//Profile profile = new Profile();
		candidate.setActive(true);
		return candidate;
	}
	
	public void updateCandidate(Profile candidate) {
		candidate.setPersisted(true);
		mongoOperations.save(candidate);
	}
   
	public void deleteCandidate(String emailId) {
		Query updateQuery = new Query();
		updateQuery.addCriteria(Criteria.where("emailId").is(emailId));
		Profile oldProfile = mongoOperations.findOne(updateQuery, Profile.class);
		if (oldProfile != null) {
			Update update = new Update();
			update.set("active",false);
			mongoOperations.updateFirst(updateQuery, update, Profile.class);
		}
	}
	
	public void updateCandidateStatus(String email, String status) {
		Query query = new Query(Criteria.where("_id").is(email));
		Update update = new Update().set("status", status);
		mongoOperations.upsert(query, update, "Profile");
	}

	public List<Profile> retrieveCandidateDetails(String emailId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("emailId").regex(
				Pattern.compile(emailId, Pattern.CASE_INSENSITIVE
						| Pattern.UNICODE_CASE)));
		List<Profile> candidateDetails = mongoOperations.find(query,
				Profile.class);
		return candidateDetails;
	}

	public List<Profile> retrieveProfileByJobCode(String jobcodeProfile) {
		Query query = new Query();
		query.addCriteria(Criteria.where("jobcodeProfile").regex(
				Pattern.compile(jobcodeProfile, Pattern.CASE_INSENSITIVE
						| Pattern.UNICODE_CASE)));
		List<Profile> candidateDetails = mongoOperations.find(query,
				Profile.class);
		return candidateDetails;
	}

	public List<Profile> retrieveProfileByRecruiterAndJobcode(String recruiterEmail,String jobCode) {
		Query query = new Query();
		query.addCriteria(Criteria.where("hrAssigned").regex(
				Pattern.compile(recruiterEmail, Pattern.CASE_INSENSITIVE
						| Pattern.UNICODE_CASE)).and("jobcodeProfile").regex(
								Pattern.compile(jobCode, Pattern.CASE_INSENSITIVE
										| Pattern.UNICODE_CASE)));
		List<Profile> candidateDetails = mongoOperations.find(query,
				Profile.class);
		return candidateDetails;
	}

	public List<Profile> retrieveProfileByProfileCreatedBy(
			String profilecreatedBy) {
		Query query = new Query();
		query.addCriteria(Criteria.where("profilecreatedBy").regex(
				Pattern.compile(profilecreatedBy, Pattern.CASE_INSENSITIVE
						| Pattern.UNICODE_CASE)));
		List<Profile> candidateDetails = mongoOperations.find(query,
				Profile.class);
		return candidateDetails;
	}

	public List<Profile> retrieveAllProfiles() {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "createDtm"));
		List<Profile> profileDetails = mongoOperations.find(query,
				Profile.class);
		return profileDetails;
	}

	public Profile deleteProfileBasedOnEmailId(String emailId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").regex(emailId));
		Profile profileDetail = mongoOperations.findOne(query, Profile.class);
		mongoOperations.remove(profileDetail);
		return profileDetail;
	}

	public void saveResumeInBucket(MultipartFile multipartFile,
			String candidateId) throws ServiceException {
		DBObject metaData = new BasicDBObject();
		metaData.put("candidateId", candidateId);

		try {
			InputStream inputStream = multipartFile.getInputStream();
			GridFS gridFS = new GridFS(dbFactory.getDb(), "resume");
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

	/*
	 * public void saveResume(MultipartFile multipartFile, String candidateId) {
	 * InputStream inputStream = null; DBObject metaData = new BasicDBObject();
	 * try { metaData.put("candidateId", candidateId); GridFsOperations
	 * gridOperations = mongoConfig.gridFsTemplate();
	 * gridOperations.store(multipartFile.getInputStream(),
	 * multipartFile.getOriginalFilename(), multipartFile.getContentType(),
	 * metaData); } catch (FileNotFoundException e) { e.printStackTrace(); }
	 * catch (Exception e) { e.printStackTrace(); } finally { if (inputStream !=
	 * null) { try { inputStream.close(); } catch (IOException e) {
	 * e.printStackTrace(); } } }
	 * 
	 * }
	 */

	public String[] getResume(String emailId) throws ServiceException {
		/*
		 * GridFsOperations gridOperations = mongoConfig.gridFsTemplate();
		 * List<GridFSDBFile> resume = gridOperations.find(new
		 * Query().addCriteria(Criteria.where("metadata.candidateId")
		 * .is(emailId)));
		 */
        try{
			GridFS gridFS = new GridFS(dbFactory.getDb(), "resume");
			List<GridFSDBFile> resume = gridFS.find(new Query().addCriteria(
					Criteria.where("metadata.candidateId").is(emailId))
					.getQueryObject());
	
			File temp = null;
			if (resume.get(0).getFilename().contains(".pdf".toLowerCase())) {
				temp = File.createTempFile(resume.get(0).getFilename(),
						".pdf".toLowerCase());
			} else if (resume.get(0).getFilename().contains(".doc")) {
				temp = File.createTempFile(resume.get(0).getFilename(), ".doc");
			} else if (resume.get(0).getFilename().contains(".docx")) {
				temp = File.createTempFile(resume.get(0).getFilename(), ".docx");
			} else {
				log.info("Invalid File Type!");
			}
	
			if (temp != null) {
				String tempPath = temp.getAbsolutePath();
				resume.get(0).writeTo(tempPath);
				return new String[] { tempPath, resume.get(0).getFilename() };
			}
			return new String[] {};
        }catch(IOException ex){
        	throw new ServiceException(ex);
        }
	}

	/*
	 * public List<GridFSDBFile> getData(String emailId) throws Exception {
	 * GridFsOperations gridOperations = mongoConfig.gridFsTemplate();
	 * List<GridFSDBFile> resume = gridOperations.find(new
	 * Query().addCriteria(Criteria.where("metadata.candidateId")
	 * .is(emailId)));
	 * 
	 * return (List<GridFSDBFile>) resume; }
	 */

	public List<GridFSDBFile> getData(String emailId) throws ServiceException {
        try{
			GridFS gridFS = new GridFS(dbFactory.getDb(), "resume");
			List<GridFSDBFile> file = gridFS.find(new Query().addCriteria(
					Criteria.where("metadata.candidateId").is(emailId))
					.getQueryObject());
			return (List<GridFSDBFile>) file;
        }catch(MongoException ex){
        	throw new ServiceException(ex);
        }
	}

}
