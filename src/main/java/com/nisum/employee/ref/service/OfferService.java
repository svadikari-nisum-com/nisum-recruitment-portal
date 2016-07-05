package com.nisum.employee.ref.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nisum.employee.ref.domain.Offer;
import com.nisum.employee.ref.repository.OfferRepository;

@Service
public class OfferService implements IOfferService { 
	
	@Autowired
	private OfferRepository offerRepository;
    
	@Override
	public void saveOffer(Offer offer) {
		 offerRepository.saveOffer(offer);
	}
    
	@Override
	public void saveResumeInBucket(MultipartFile multipartFile,
			String candidateId) {
		offerRepository.saveResumeInBucket(multipartFile, candidateId);
	}

}
