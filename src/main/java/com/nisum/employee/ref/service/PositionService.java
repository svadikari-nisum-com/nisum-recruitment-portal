package com.nisum.employee.ref.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nisum.employee.ref.converter.PositionConverter;
import com.nisum.employee.ref.domain.Position;
import com.nisum.employee.ref.domain.PositionAggregate;
import com.nisum.employee.ref.repository.PositionRepository;
import com.nisum.employee.ref.view.PositionDTO;

@Service
public class PositionService implements IPositionService{

	@Autowired
	private PositionRepository positionRepository;
	

	@Autowired
	private PositionConverter positionConverter;
	
	
	@Override
	public void preparePosition(PositionDTO position) {
		positionRepository.preparePosition(positionConverter.convertToEntity(position));
	}
	@Override
	public void updatePosition(Position position) {
		positionRepository.updatePosition(position);
	}
	@Override
	public List<PositionDTO> retrievePositionByClient(String client) {
		return positionConverter.convertToDTOs((positionRepository.retrievePositionByClient(client)));
	}
	@Override
	public List<PositionDTO> retrieveAllPositions() {
		return positionConverter.convertToDTOs((positionRepository.retrieveAllPositions()));
	}
	@Override
	public List<PositionDTO> retrievePositionsbasedOnDesignation(String designation) {
		return positionConverter.convertToDTOs((positionRepository.retrievePositionsbasedOnDesignation(designation)));
	}
	@Override
	public PositionDTO retrievePositionsbasedOnJobCode(String jobcode) {
		return positionConverter.convertToDTO((positionRepository.retrievePositionsbasedOnJobCode(jobcode)));
	}
	@Override
	public Position deletePositionBasedOnJC(String jobcode) {
		return positionRepository.deletePositionBasedOnJC(jobcode);
	}
	@Override
	public List<PositionDTO> retrievePositionbasedOnLocation(String location) {
		return positionConverter.convertToDTOs((positionRepository.retrievePositionbasedOnLocation(location)));
	}
	
	@Override
	public List<PositionAggregate> retrieveAllPositionsAggregate() {
		return positionRepository.retrieveAllPositionsAggregate();
	}

}