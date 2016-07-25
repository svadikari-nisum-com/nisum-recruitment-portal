package com.nisum.employee.ref.service;

import java.util.List;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nisum.employee.ref.common.OfferState;
import com.nisum.employee.ref.converter.OfferConverter;
import com.nisum.employee.ref.repository.OfferRepository;
import com.nisum.employee.ref.view.OfferDTO;

@Service
public class OfferService implements IOfferService { 
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private OfferConverter offerConverter;
    
	@Override
	public void saveOffer(OfferDTO offer) {
		 offerRepository.saveOffer(offerConverter.convertToEntity(offer));
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

}
