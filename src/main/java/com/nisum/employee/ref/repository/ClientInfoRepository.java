package com.nisum.employee.ref.repository;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.nisum.employee.ref.domain.ClientInfo;
import com.nisum.employee.ref.domain.UserInfo;
import com.nisum.employee.ref.util.Constants;

@Repository
public class ClientInfoRepository {

	private static final String _ID = "_id";

	@Autowired
	private MongoOperations mongoOperations;

	public List<ClientInfo> getClientDetails() {
		return mongoOperations.findAll(ClientInfo.class);
	}

	public List<ClientInfo> getClientById(String clientId) {
		Query query = new Query();
		query.addCriteria(
				Criteria.where(_ID).regex(Pattern.compile(clientId, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
		return mongoOperations.find(query, ClientInfo.class);
	}

	public List<UserInfo> fetchAllUsers() {
		List<UserInfo> allUsers = mongoOperations.findAll(UserInfo.class);
		return allUsers;
	}

	public void deleteClient(String client) {
		Query query = new Query();
		query.addCriteria(Criteria.where(_ID).regex(client));
		mongoOperations.remove(mongoOperations.findOne(query, ClientInfo.class));
	}

	public void createClient(ClientInfo clientInfo) {
		mongoOperations.save(clientInfo);
	}

	public void updateClient(ClientInfo clientInfo) {
		mongoOperations.save(clientInfo, Constants.clientInfo);
	}
}
