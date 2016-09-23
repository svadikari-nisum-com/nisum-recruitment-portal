package com.nisum.employee.ref.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.nisum.employee.ref.domain.UserInfo;
import com.nisum.employee.ref.exception.ServiceException;

@Repository
public class UserInfoRepository{

	@Autowired
	private MongoOperations mongoOperations;

	public void registerUserByEmailId(String emailId) {
		mongoOperations.save(createUserInfo(emailId));
	}
	
	public List<UserInfo> retrieveUser() {	
		Query query = new Query();
		query.addCriteria(Criteria.where("active").ne(false));		
		  return mongoOperations.find(query,UserInfo.class);		
	}
	public List<UserInfo> retrieveUserById(String userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("emailId").regex(Pattern.compile(userId, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		return mongoOperations.find(query, UserInfo.class);
	}
	
	public List<UserInfo> retrieveUserByName(String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").regex(Pattern.compile(name, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		return mongoOperations.find(query, UserInfo.class);
	}
	
	public UserInfo createUserInfo(String userName) {
		List<String> defualtRoles = new ArrayList<String>();
		defualtRoles.add("ROLE_USER");
		UserInfo userInfo = new UserInfo();
		userInfo.setEmailId(userName);
		userInfo.setActive(true);
		userInfo.setRoles(defualtRoles);
		return userInfo;
	}
	
	public void updateUser(UserInfo user) {
		Query updateQuery = new Query();
		updateQuery.addCriteria(Criteria.where("emailId").is(user.getEmailId()));
		UserInfo user1 = mongoOperations.findOne(updateQuery, UserInfo.class);
		user1.equals(user) ;
		Update update = new Update();
		update.set("name", user.getName());
		update.set("dob", user.getDob());
		update.set("location", user.getLocation());
		update.set("roles", user.getRoles());
		update.set("skills", user.getSkills());
		update.set("clientName", user.getClientName());
		update.set("mobileNumber", user.getMobileNumber());
		update.set("categories", user.getCategories());
		update.set("timeSlots", user.getTimeSlots());
		update.set("categories", user.getCategories());
		update.set("isNotAvailable", user.getIsNotAvailable());
		update.set("interviewRoundsAllocation", user.getInterviewRoundsAllocation());
		update.set("active", user.getActive());
		update.set("skypeId", user.getSkypeId());
		
		mongoOperations.updateFirst(updateQuery, update, UserInfo.class);
	}		
	public void deleteUser(String emailId) {
		Query updateQuery = new Query();
		updateQuery.addCriteria(Criteria.where("emailId").is(emailId));
		UserInfo user1 = mongoOperations.findOne(updateQuery, UserInfo.class);
		if (user1 != null) {
			Update update = new Update();
			update.set("active",false);
			mongoOperations.updateFirst(updateQuery, update, UserInfo.class);
		}
		
	}
	

	public List<UserInfo> retrieveUserByClient(String clientName) {
		Query query = new Query();
		query.addCriteria(Criteria.where("clientName").regex(Pattern.compile(clientName, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		return mongoOperations.find(query, UserInfo.class);
	}
	
	public List<UserInfo> retrieveUserByRole(String role) {
		
		Query query = new Query();
		if(role.equals("IMR"))
		{
			query.addCriteria(Criteria.where("roles").in(Arrays.asList("ROLE_INTERVIEWER","ROLE_MANAGER","ROLE_HR")));
			
		}else
		{
			query.addCriteria(Criteria.where("roles").is(role));
		}
		return mongoOperations.find(query, UserInfo.class);
	}
	public List<UserInfo> retrieveUserByRole(String round,String department) throws ServiceException {
		
		try
		{
		Query query = new Query();
		query.addCriteria(Criteria.where("roles").is("ROLE_INTERVIEWER").and("interviewRoundsAllocation.department").is(department).and("interviewRoundsAllocation.interviewRounds").is(round));
		return mongoOperations.find(query, UserInfo.class);
		}catch (Exception ex){ 
			throw new ServiceException(ex);
		}
	}
	
    public List<UserInfo> getUserInfo(String round,String functionalGroup,String role) throws ServiceException {
		try
		{
			Query query = new Query();
			if(StringUtils.isNotBlank(functionalGroup)){
				query.addCriteria(Criteria.where("interviewRoundsAllocation.department").is(functionalGroup));
			}
			if( StringUtils.isNotBlank(role)) {
					query.addCriteria(Criteria.where("roles").is(role));
			}  
			if(StringUtils.isNotBlank(round)) {
				query.addCriteria(Criteria.where("interviewRoundsAllocation.interviewRounds").is(round));
			}
			return mongoOperations.find(query, UserInfo.class);
		}catch (Exception ex){ 
			throw new ServiceException(ex);
		}
	}
    
    public List<UserInfo> retrieveUserByRoleAndLocation(String role,String location) throws ServiceException {
		try
		{
			Query query = new Query();
			query.addCriteria(Criteria.where("roles").is(role).and("location").is(location));
			return mongoOperations.find(query, UserInfo.class);
		}catch (Exception ex){ 
			throw new ServiceException(ex);
		}
	}
}
