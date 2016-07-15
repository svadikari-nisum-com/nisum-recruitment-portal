package com.nisum.employee.ref.converter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.nisum.employee.ref.domain.ClientInfo;
import com.nisum.employee.ref.domain.RoundUser;
import com.nisum.employee.ref.view.ClientInfoDTO;
import com.nisum.employee.ref.view.InterviewerDTO;
import com.nisum.employee.ref.view.RoundUserDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ClientInfoConverter extends TwowayConverter<ClientInfo, ClientInfoDTO> {

	@Override
	public ClientInfoDTO convertToDTO(ClientInfo clientInfo) {
		ClientInfoDTO clientInfoDTO = null;
		InterviewerDTO interviewerDTO = null;
		try {
			clientInfoDTO = new ClientInfoDTO();
			BeanUtils.copyProperties(clientInfoDTO, clientInfo);

			interviewerDTO = new InterviewerDTO();

			if (CollectionUtils.isNotEmpty(clientInfo.getInterviewers().getTechnicalRound1())) {
				interviewerDTO.setTechnicalRound1(getRoundInfo(clientInfo.getInterviewers().getTechnicalRound1()));
			}

			if (CollectionUtils.isNotEmpty(clientInfo.getInterviewers().getTechnicalRound2())) {
				interviewerDTO.setTechnicalRound2(getRoundInfo(clientInfo.getInterviewers().getTechnicalRound2()));
			}

			if (CollectionUtils.isNotEmpty(clientInfo.getInterviewers().getHrRound())) {
				interviewerDTO.setTechnicalRound2(getRoundInfo(clientInfo.getInterviewers().getHrRound()));
			}

			if (CollectionUtils.isNotEmpty(clientInfo.getInterviewers().getManagerRound())) {
				interviewerDTO.setTechnicalRound2(getRoundInfo(clientInfo.getInterviewers().getManagerRound()));
			}

			clientInfoDTO.setInterviewerDTO(interviewerDTO);
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

	private List<RoundUserDTO> getRoundInfo(List<RoundUser> roundUserList) {
		List<RoundUserDTO> roundUsers = new ArrayList<>();
		roundUserList.stream().forEach(roundUser -> {
			RoundUserDTO roundUserDTO = new RoundUserDTO();
			try {
				BeanUtils.copyProperties(roundUserDTO, roundUser);
				roundUserDTO.setSkillSet(roundUser.getSkillSet());
				roundUsers.add(roundUserDTO);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		});
		return roundUsers;
	}

}
