package com.nisum.employee.ref.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.nisum.employee.ref.exception.ServiceException;
import com.nisum.employee.ref.view.OfferDTO;

public interface IOfferService {
	
	public void saveOffer(OfferDTO offer) throws ServiceException;
	public void generateOfferLetter(OfferDTO offer) throws ServiceException;
	public void saveResumeInBucket(MultipartFile multipartFile,
			String candidateId) throws ServiceException;
	List<OfferDTO> getOffers();
	public List<OfferDTO> getOffersByJobcode(String jobcode);
	public void generateNotification(String name,String candidateName,String subject) throws ServiceException ;
}
