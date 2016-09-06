package com.nisum.employee.ref.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import com.nisum.employee.ref.common.ErrorCodes;
import com.nisum.employee.ref.converter.ClientInfoConverter;
import com.nisum.employee.ref.converter.UserInfoConverter;
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
	
	@Autowired
	private MessageSourceAccessor messageSourceAccessor;

	public List<ClientInfoDTO> getClientDetails() {
		return clientInfoConverter.convertToDTOs(clientInfoRepository.getClientDetails());
	}

	public List<ClientInfoDTO> getClientById(String clientId) {
		return clientInfoConverter.convertToDTOs(clientInfoRepository.getClientById(clientId));
	}

	public List<UserInfoDTO> fetchAllUsers() {
		return userInfoConverter.convertToDTOs(clientInfoRepository.fetchAllUsers());
	}

	public void deleteClient(String client) {
		clientInfoRepository.deleteClient(client);
	}

	public void createClient(ClientInfoDTO clientInfoDTO) {
		List<ClientInfo> clients = (List<ClientInfo>) clientInfoRepository.getClientDetails();
		for (ClientInfo clientInfo : clients) {
			if(clientInfo.getClientName().equalsIgnoreCase(clientInfoDTO.getClientName())) {
				clientInfoDTO.addError(ErrorCodes.NRP0004, messageSourceAccessor.getMessage(ErrorCodes.NRP0004));
				return;
			}
		} 
		if(!clientInfoDTO.hasErrors()) {
			clientInfoRepository.createClient(clientInfoConverter.convertToEntity(clientInfoDTO));
		}
	}

	public void updateClient(ClientInfoDTO clientInfoDTO) {
		clientInfoRepository.updateClient(clientInfoConverter.convertToEntity(clientInfoDTO));
	}
}
