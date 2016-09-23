package com.nisum.employee.ref.service;

import java.util.List;

import javax.mail.MessagingException;

import com.nisum.employee.ref.domain.PositionAggregate;
import com.nisum.employee.ref.view.PositionDTO;

public interface IPositionService {
	public void preparePosition(PositionDTO position) throws MessagingException;

	public boolean updatePosition(PositionDTO position);

	public PositionDTO deletePositionBasedOnJC(String jobcode);

	public List<PositionAggregate> retrieveAllPositionsAggregate();
	
	List<PositionDTO> retrieveAllPositions(String searchKey, String searchValue);
	
	List<PositionDTO> retrieveAllPositions();
	
	public void updatePositionStatus(String jobCode, String status) throws MessagingException;
	
	public PositionDTO retrievePositionByJobCode(String jobCode);
}
