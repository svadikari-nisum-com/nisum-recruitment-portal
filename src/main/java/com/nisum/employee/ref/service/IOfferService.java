package com.nisum.employee.ref.service;

import java.util.List;


import org.springframework.web.multipart.MultipartFile;

import com.nisum.employee.ref.view.OfferDTO;

public interface IOfferService {
	
	public void saveOffer(OfferDTO offer);
	public void saveResumeInBucket(MultipartFile multipartFile,
			String candidateId);
	List<OfferDTO> getOffers();
}
