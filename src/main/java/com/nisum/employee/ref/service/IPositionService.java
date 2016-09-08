package com.nisum.employee.ref.service;

import java.util.List;


import com.nisum.employee.ref.domain.PositionAggregate;
import com.nisum.employee.ref.view.PositionDTO;

public interface IPositionService {
	public void preparePosition(PositionDTO position);

	public boolean updatePosition(PositionDTO position);

	public PositionDTO deletePositionBasedOnJC(String jobcode);

	public List<PositionAggregate> retrieveAllPositionsAggregate();
	
	List<PositionDTO> retrieveAllPositions(String searchKey, String searchValue);
	
	List<PositionDTO> retrieveAllPositions();
	
	public void updateProfileStatus(String jobCode, String status);
}
