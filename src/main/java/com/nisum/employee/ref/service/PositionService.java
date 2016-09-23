package com.nisum.employee.ref.service;

import java.util.List;

import javax.mail.MessagingException;

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
	@Autowired
	private INotificationService notificationService;
	
	@Override
	public void preparePosition(PositionDTO position) throws MessagingException {		
		notificationService.sendpositionCreationMail(position);		
		positionRepository.preparePosition(positionConverter.convertToEntity(position));
	}
	@Override
	public boolean updatePosition(PositionDTO position) {
		return positionRepository.updatePosition(positionConverter.convertToEntity(position));
	}
	
	@Override
	public List<PositionDTO> retrieveAllPositions(String searchKey, String searchValue) {
		return positionConverter.convertToDTOs((positionRepository.retrieveAllPositions(searchKey, searchValue)));
	}
	
	@Override
	public List<PositionDTO> retrieveAllPositions() {
		return positionConverter.convertToDTOs((positionRepository.retrieveAllPositions()));
	}
	
	@Override
	public PositionDTO deletePositionBasedOnJC(String jobcode) {
		return positionConverter.convertToDTO(positionRepository.deletePositionBasedOnJC(jobcode));
	}
	
	@Override
	public List<PositionAggregate> retrieveAllPositionsAggregate() {
		return positionRepository.retrieveAllPositionsAggregate();
	}
	
	@Override
	public void updatePositionStatus(String jobCode, String status) throws MessagingException {
		List<Position> positions=positionRepository.retrieveAllPositions("_id",jobCode);
		if(!positions.isEmpty()){
		Position position=positions.get(0);		
		position.setStatus(status);
		notificationService.sendpositionStatusChangeMail(position);
		}
		
		positionRepository.updatePositionStatus(jobCode, status);
	}
	
	@Override
	public PositionDTO retrievePositionByJobCode(String jobCode) {
		return positionConverter.convertToDTO((positionRepository.retrievePositionByJobCode(jobCode)));
	}
}