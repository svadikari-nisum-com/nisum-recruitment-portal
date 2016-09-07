package com.nisum.employee.ref.service;

import java.util.List;

import com.nisum.employee.ref.view.InfoEntityDTO;

public interface IAppInfoService {
	List<InfoEntityDTO> retrieveSkills();
	void prepareInfo(InfoEntityDTO info);
	void updateInfo(InfoEntityDTO info);
	void updateDesigInfo(InfoEntityDTO info);
	void updateInterviewRoundsInfo(InfoEntityDTO info);
}
