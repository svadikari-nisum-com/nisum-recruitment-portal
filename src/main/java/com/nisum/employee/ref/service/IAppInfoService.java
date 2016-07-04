package com.nisum.employee.ref.service;

import java.util.List;

import com.nisum.employee.ref.domain.InfoEntity;

public interface IAppInfoService {
	List<InfoEntity> retrieveSkills();
	void prepareInfo(InfoEntity info);
	void updateInfo(InfoEntity info);
	void updateDesigInfo(InfoEntity info);
	void updateInterviewRoundsInfo(InfoEntity info);
}
