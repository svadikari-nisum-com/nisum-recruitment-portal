package com.nisum.employee.ref.service;

import java.util.List;



import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nisum.employee.ref.common.OfferState;
import com.nisum.employee.ref.converter.OfferConverter;
import com.nisum.employee.ref.exception.ServiceException;
import com.nisum.employee.ref.repository.OfferRepository;
import com.nisum.employee.ref.view.OfferDTO;

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
    
	@Override
	public void saveOffer(OfferDTO offer) throws ServiceException {
		 offerRepository.saveOffer(offerConverter.convertToEntity(offer));
		 if(offer.getStatus().equals(OfferState.RELEASED.toString())){
			 try{
				 generateOfferLetter(offer);
				 //TODO send offer letter to candidate as part of email notification.
				 notificationService.sendOfferNotificationMail(offer.getEmailId());
			 }catch(Exception ex){
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
		if(StringUtils.isEmpty(currentStatus)) {
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

}
