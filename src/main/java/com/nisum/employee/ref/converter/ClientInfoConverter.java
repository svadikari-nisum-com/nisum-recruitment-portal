package com.nisum.employee.ref.converter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import com.nisum.employee.ref.domain.ClientInfo;
import com.nisum.employee.ref.view.ClientInfoDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ClientInfoConverter extends TwowayConverter<ClientInfo, ClientInfoDTO> {

	@Override
	public ClientInfoDTO convertToDTO(ClientInfo clientInfo) {
		ClientInfoDTO clientInfoDTO = null;
		try {
			clientInfoDTO = new ClientInfoDTO();
			BeanUtils.copyProperties(clientInfoDTO, clientInfo);
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage());
			return clientInfoDTO;
		}
		return clientInfoDTO;
	}

	@Override
	public ClientInfo convertToEntity(ClientInfoDTO clientInfoDTO) {
		ClientInfo clientInfo = null;
		try {
			clientInfo = new ClientInfo();
			BeanUtils.copyProperties(clientInfo, clientInfoDTO);
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(e.getMessage());
			return clientInfo;
		} 
		return clientInfo;
	}

	public List<ClientInfoDTO> convertToDTOs(List<ClientInfo> clientInfos) {
		List<ClientInfoDTO> dtos = new ArrayList<>();
		clientInfos.stream().forEach(clientInfo -> dtos.add(convertToDTO(clientInfo)));
		return dtos;
	}

}
