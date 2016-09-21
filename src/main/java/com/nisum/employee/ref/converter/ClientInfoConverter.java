package com.nisum.employee.ref.converter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.nisum.employee.ref.domain.ClientInfo;
import com.nisum.employee.ref.domain.Interviewer;
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

			if(clientInfo.getInterviewer() != null)
			{
				if (clientInfo.getInterviewer().getTechnicalRound1() != null && CollectionUtils.isNotEmpty(clientInfo.getInterviewer().getTechnicalRound1())) {
					interviewerDTO.setTechnicalRound1(getRoundInfo(clientInfo.getInterviewer().getTechnicalRound1()));
				}
	
				if (clientInfo.getInterviewer().getTechnicalRound2() != null && CollectionUtils.isNotEmpty(clientInfo.getInterviewer().getTechnicalRound2())) {
					interviewerDTO.setTechnicalRound2(getRoundInfo(clientInfo.getInterviewer().getTechnicalRound2()));
				}
				if (clientInfo.getInterviewer().getManagerRound() != null && CollectionUtils.isNotEmpty(clientInfo.getInterviewer().getManagerRound())) {
					interviewerDTO.setManagerRound(getRoundInfo(clientInfo.getInterviewer().getManagerRound()));
				}
				if (clientInfo.getInterviewer().getHrRound() != null && CollectionUtils.isNotEmpty(clientInfo.getInterviewer().getHrRound())) {
					interviewerDTO.setHrRound(getRoundInfo(clientInfo.getInterviewer().getHrRound()));
				}
	
				
			}
			clientInfoDTO.setInterviewers(interviewerDTO);
		} catch ( Exception  e) {
			log.error(e.getMessage(),e);
			return clientInfoDTO;
		}
		return clientInfoDTO;
	}

	@Override
	public ClientInfo convertToEntity(ClientInfoDTO clientInfoDTO) {
		ClientInfo clientInfo = null;
		Interviewer interviewer = null;
		try {
			clientInfo = new ClientInfo();
			BeanUtils.copyProperties(clientInfo, clientInfoDTO);
			
			interviewer = new Interviewer();

			if(clientInfoDTO.getInterviewers() != null)
			{
				if (clientInfoDTO.getInterviewers().getTechnicalRound1() != null && CollectionUtils.isNotEmpty(clientInfoDTO.getInterviewers().getTechnicalRound1())) {

					interviewer.setTechnicalRound1(getRoundInfoEntity(clientInfoDTO.getInterviewers().getTechnicalRound1()));
				}
	
				if (clientInfoDTO.getInterviewers().getTechnicalRound2() != null && CollectionUtils.isNotEmpty(clientInfoDTO.getInterviewers().getTechnicalRound2())) {
					
					interviewer.setTechnicalRound2(getRoundInfoEntity(clientInfoDTO.getInterviewers().getTechnicalRound2()));
				}
	
				if (clientInfoDTO.getInterviewers().getManagerRound() != null && CollectionUtils.isNotEmpty(clientInfoDTO.getInterviewers().getManagerRound())) {
					interviewer.setManagerRound(getRoundInfoEntity(clientInfoDTO.getInterviewers().getManagerRound()));
				}
				if (clientInfoDTO.getInterviewers().getHrRound() != null && CollectionUtils.isNotEmpty(clientInfoDTO.getInterviewers().getHrRound())) {
					interviewer.setHrRound(getRoundInfoEntity(clientInfoDTO.getInterviewers().getHrRound()));
				}
	
				
			}
			clientInfo.setInterviewer(interviewer);
			
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
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
				log.error(e.getMessage(),e);
			}
		});
		return roundUsers;
	}
	
	private List<RoundUser> getRoundInfoEntity(List<RoundUserDTO> roundUserList) {
		List<RoundUser> roundUsers = new ArrayList<>();
		roundUserList.stream().forEach(roundUserDTO -> {
			RoundUser roundUser = new RoundUser();
			try {
				BeanUtils.copyProperties(roundUser, roundUserDTO);
				roundUserDTO.setSkillSet(roundUserDTO.getSkillSet());
				roundUsers.add(roundUser);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		});
		return roundUsers;
	}

}
