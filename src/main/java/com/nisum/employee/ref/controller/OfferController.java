package com.nisum.employee.ref.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nisum.employee.ref.common.OfferState;
import com.nisum.employee.ref.exception.ServiceException;
import com.nisum.employee.ref.service.OfferService;
import com.nisum.employee.ref.view.OfferDTO;

@Controller
@RequestMapping("/offers")
public class OfferController {

	@Autowired
	private OfferService offerService;

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> saveOfferDetails(@RequestBody OfferDTO offer) throws ServiceException {
		offerService.saveOffer(offer);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER", "ROLE_HR", "ROLE_RECRUITER", "ROLE_MANAGER", "ROLE_INTERVIEWER" })
	@RequestMapping(value = "{candidateId}/upload-offer-letter", method = RequestMethod.POST)
	public ResponseEntity<String> uploadOfferLetter(HttpServletRequest request, @RequestParam(value = "file") MultipartFile multipartFile,
			@PathVariable("candidateId") String candidateId) throws ServiceException {
		offerService.saveResumeInBucket(multipartFile, candidateId);
		return new ResponseEntity<String>("Resume Uploaded Successfully", HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_HR", "ROLE_MANAGER", "ROLE_RECRUITER" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<OfferDTO>> getOffers(@RequestParam(value = "managerEmail", required = false) String managerEmail)
			throws ServiceException {
		List<OfferDTO> li_offerDTO = null;
		if (!StringUtils.isEmpty(managerEmail)) {
			li_offerDTO = offerService.getOffersByManagerId(managerEmail);
		} else {
			li_offerDTO = offerService.getOffers();
		}
		return new ResponseEntity<List<OfferDTO>>(li_offerDTO, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_HR", "ROLE_MANAGER", "ROLE_RECRUITER" })
	@RequestMapping(value = "/{emailId}", method = RequestMethod.GET)
	public ResponseEntity<OfferDTO> getOffer(@PathVariable("emailId") String emailId) throws ServiceException {
		return new ResponseEntity<OfferDTO>(offerService.getOffer(emailId), HttpStatus.OK);
	}

	@RequestMapping(value = "/nextStatuses", method = RequestMethod.GET)
	public ResponseEntity<List<OfferState>> getNextStatuses(@RequestParam(value = "currentStatus", required = false) String currentStatus)
			throws ServiceException {
		List<OfferState> offerStatuses = offerService.getNextStatuses(currentStatus);
		return new ResponseEntity<List<OfferState>>(offerStatuses, HttpStatus.OK);
	}
}
