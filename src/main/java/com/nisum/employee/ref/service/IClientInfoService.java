package com.nisum.employee.ref.service;

import java.util.List;

import com.nisum.employee.ref.domain.ClientInfo;
import com.nisum.employee.ref.domain.UserInfo;

public interface IClientInfoService {
	List<ClientInfo> getClientDetails();

	List<String> getClientNames();

	List<ClientInfo> getClientDetailsByClient(String clientName);

	List<String> getInterviewerNames();

	List<ClientInfo> getClientById(String clientId);

	List<UserInfo> fetchAllUsers();

	void deleteClient(String client);

	void createClient(ClientInfo clientInfo);

	void updateClient(ClientInfo clientInfo);
}
