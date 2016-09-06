package com.nisum.employee.ref.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nisum.employee.ref.common.OfferState;
import com.nisum.employee.ref.converter.OfferConverter;
import com.nisum.employee.ref.domain.Offer;
import com.nisum.employee.ref.exception.ServiceException;
import com.nisum.employee.ref.repository.OfferRepository;
import com.nisum.employee.ref.view.OfferDTO;
import com.nisum.employee.ref.view.PositionDTO;

@Service
public class OfferService implements IOfferService {

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private OfferConverter offerConverter;

	@Autowired
	private GenerateOfferService offerService;

	@Autowired
	private INotificationService notificationService;
	@Autowired
	private PositionService positionService;

	@Override
	public void saveOffer(OfferDTO offer) throws ServiceException {
		Offer offerEntity = offerConverter.convertToEntity(offer);
		offerRepository.saveOffer(offerEntity);
		offerRepository.updateInterviewDetails(offerEntity);
		if (offer.getStatus().equals(OfferState.RELEASED.toString())) {
			try {
				generateOfferLetter(offer);
				// send offer letter to candidate as part of email notification.
				notificationService.sendOfferNotificationMail(offer);
			} catch (Exception ex) {
				throw new ServiceException(ex);
			}
		}
	}

	@Override
	public void generateOfferLetter(OfferDTO offer) throws ServiceException {
		offerService.generateOffer(offer);
	}

	@Override
	public void saveResumeInBucket(MultipartFile multipartFile,
			String candidateId) {
		offerRepository.saveResumeInBucket(multipartFile, candidateId);
	}

	public List<OfferState> getNextStatuses(String currentStatus) {
		if (StringUtils.isEmpty(currentStatus)){
			currentStatus = OfferState.NOTINITIATED.name();
		}
		return OfferState.getState(currentStatus).next();
	}

	public List<OfferDTO> getOffers() {
		return offerConverter.convertToDTOs(offerRepository.getOffers());
	}

	public OfferDTO getOffer(String emailId) {
		return offerConverter.convertToDTO(offerRepository.getOffer(emailId));
	}

	public List<OfferDTO> getOffersByJobcode(String jobcode) {
		return offerConverter.convertToDTOs(offerRepository
				.getOffersByJobcode(jobcode));
	}

	public List<OfferDTO> getOffersByManagerId(String managerId) {
		List<PositionDTO> li_positionDTO = positionService
				.retrieveAllPositions("hiringManager", managerId);
		List<Offer> li_offerData = new ArrayList<Offer>();

		for (PositionDTO positionDTO : li_positionDTO) {		
			List<Offer> li_offer = offerRepository
					.getOffersByJobcode(positionDTO.getJobcode());
			for (Offer offer : li_offer) {
				li_offerData.add(offer);
			}

		}

		return offerConverter.convertToDTOs(li_offerData);
	}
}
