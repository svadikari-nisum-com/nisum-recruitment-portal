package com.nisum.employee.ref.service;

import java.util.List;

import com.nisum.employee.ref.domain.Position;
import com.nisum.employee.ref.domain.PositionAggregate;
import com.nisum.employee.ref.view.PositionDTO;

public interface IPositionService {
	public void preparePosition(Position position);

	public void updatePosition(Position position);

	public List<PositionDTO> retrievePositionByClient(String client);

	public List<PositionDTO> retrieveAllPositions();

	public List<PositionDTO> retrievePositionsbasedOnDesignation(String designation);

	public PositionDTO retrievePositionsbasedOnJobCode(String jobcode);

	public Position deletePositionBasedOnJC(String jobcode);

	public List<PositionDTO> retrievePositionbasedOnLocation(String location);

	public List<PositionAggregate> retrieveAllPositionsAggregate();
}
