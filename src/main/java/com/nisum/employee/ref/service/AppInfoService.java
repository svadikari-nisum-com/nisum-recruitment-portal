package com.nisum.employee.ref.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nisum.employee.ref.converter.InfoEntityConverter;
import com.nisum.employee.ref.repository.InfoRepository;
import com.nisum.employee.ref.view.InfoEntityDTO;

@Service
public class AppInfoService implements IAppInfoService {

	@Autowired
	private InfoRepository skillsRequired;

	@Autowired
	private InfoEntityConverter infoEntityConverter;

	@Override
	public List<InfoEntityDTO> retrieveSkills() {
		return infoEntityConverter.convertToDTOs(skillsRequired.retrieveSkills());
	}

	@Override
	public void prepareInfo(InfoEntityDTO info) {
		skillsRequired.prepareInfo(infoEntityConverter.convertToEntity(info));
	}

	@Override
	public void updateInfo(InfoEntityDTO info) {
		skillsRequired.updateInfo(infoEntityConverter.convertToEntity(info));
	}

	@Override
	public void updateDesigInfo(InfoEntityDTO info) {
		skillsRequired.updateInfo(infoEntityConverter.convertToEntity(info));

	}

	@Override
	public void updateInterviewRoundsInfo(InfoEntityDTO info) {
		skillsRequired.updateInfo(infoEntityConverter.convertToEntity(info));
	}
}
