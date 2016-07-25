package com.nisum.employee.ref.controller;

import java.util.List;




import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.nisum.employee.ref.common.OfferState;
import com.nisum.employee.ref.service.OfferService;
import com.nisum.employee.ref.view.OfferDTO;

@Controller
public class OfferController {

	@Autowired
	private OfferService offerService;

	@ResponseBody
	@RequestMapping(value = "/save-offer", method = RequestMethod.POST)
	public ResponseEntity<OfferDTO> saveOfferDetails(@RequestBody OfferDTO offer) {
		offerService.saveOffer(offer);
		return new ResponseEntity<OfferDTO>(offer, HttpStatus.OK);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@Secured({ "ROLE_ADMIN", "ROLE_USER", "ROLE_HR", "ROLE_RECRUITER",
			"ROLE_MANAGER", "ROLE_INTERVIEWER" })
	@RequestMapping(value = "/upload-offer-letter", method = RequestMethod.POST)
	public ResponseEntity<String> uploadOfferLetter(
			HttpServletRequest request,
			@RequestParam(value = "file") MultipartFile multipartFile,
			@RequestParam(value = "candidateId", required = true) String candidateId)
			throws Exception {
		offerService.saveResumeInBucket(multipartFile, candidateId);
		return new ResponseEntity<String>("Resume Uploaded Successfully",
				HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_HR", "ROLE_MANAGER", "ROLE_RECRUITER" })
	@RequestMapping(value = "/offers", method = RequestMethod.GET)
	public ResponseEntity<List<OfferDTO>> getOffers() throws Exception {
		return new ResponseEntity<List<OfferDTO>>(offerService.getOffers(),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/offer/nextStatuses", method = RequestMethod.GET)
	public ResponseEntity<List<OfferState>> getNextStatuses(
			@RequestParam(value = "currentStatus", required = false) String currentStatus)
			throws Exception {
		List<OfferState> offerStatuses = offerService
				.getNextStatuses(currentStatus);
		return new ResponseEntity<List<OfferState>>(offerStatuses,
				HttpStatus.OK);
	}
}
