package com.nisum.employee.ref.service;

import java.util.List;

import com.nisum.employee.ref.view.ClientInfoDTO;
import com.nisum.employee.ref.view.UserInfoDTO;

public interface IClientInfoService {
	List<ClientInfoDTO> getClientDetails();

	//List<String> getClientNames();

	//List<ClientInfoDTO> getClientDetailsByClient(String clientName);

	//List<String> getInterviewerNames();

	List<ClientInfoDTO> getClientById(String clientId);

	List<UserInfoDTO> fetchAllUsers();

	void deleteClient(String client);

	void createClient(ClientInfoDTO clientInfoDTO);

	void updateClient(ClientInfoDTO clientInfoDTO);
}
