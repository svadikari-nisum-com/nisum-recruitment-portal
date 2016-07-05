package com.nisum.employee.ref.service;

import org.springframework.web.multipart.MultipartFile;

import com.nisum.employee.ref.domain.Offer;

public interface IOfferService {
	
	public void saveOffer(Offer offer);
	public void saveResumeInBucket(MultipartFile multipartFile,
			String candidateId);
}
