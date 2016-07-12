package com.nisum.employee.ref.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nisum.employee.ref.convert.ClientInfoConverter;
import com.nisum.employee.ref.convert.UserInfoConverter;
import com.nisum.employee.ref.domain.ClientInfo;
import com.nisum.employee.ref.repository.ClientInfoRepository;
import com.nisum.employee.ref.view.ClientInfoDTO;
import com.nisum.employee.ref.view.UserInfoDTO;

import lombok.Getter;
import lombok.Setter;

@Service
public class ClientInfoService implements IClientInfoService {

	@Autowired
	@Setter
	@Getter
	private ClientInfoRepository clientInfoRepository;

	@Autowired
	private ClientInfoConverter clientInfoConverter;

	@Autowired
	private UserInfoConverter userInfoConverter;

	public List<ClientInfoDTO> getClientDetails() {
		return clientInfoConverter.convertToDTOs(clientInfoRepository.getClientDetails());
	}

	public List<ClientInfoDTO> getClientDetailsByClient(String clientName) {
		return clientInfoConverter.convertToDTOs(clientInfoRepository.getClientDetailsByClient(clientName));
	}

	public List<String> getClientNames() {
		return clientInfoRepository.getClientNames();
	}

	public List<String> getInterviewerNames() {
		List<String> interviewerNames = new ArrayList<String>();
		List<ClientInfo> clients = (List<ClientInfo>) clientInfoRepository.getClientDetails();
		for (ClientInfo clientInfo : clients) {
			interviewerNames.add(clientInfo.getInterviewers().getTechnicalRound1().get(0).getName());
		}
		return interviewerNames;
	}

	public List<ClientInfoDTO> getClientById(String clientId) {
		return clientInfoConverter.convertToDTOs(clientInfoRepository.getClientById(clientId));
	}

	// ---Admin

	public List<UserInfoDTO> fetchAllUsers() {
		return userInfoConverter.convertToDTOs(clientInfoRepository.fetchAllUsers());
	}

	public void deleteClient(String client) {
		clientInfoRepository.deleteClient(client);
	}

	public void createClient(ClientInfoDTO clientInfoDTO) {
		clientInfoRepository.createClient(clientInfoConverter.convertToEntity(clientInfoDTO));
	}

	public void updateClient(ClientInfoDTO clientInfoDTO) {
		clientInfoRepository.updateClient(clientInfoConverter.convertToEntity(clientInfoDTO));
	}
}
