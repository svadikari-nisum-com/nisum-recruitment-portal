package com.nisum.employee.ref.service;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.mockito.Mock;

import com.nisum.employee.ref.converter.OfferConverter;
import com.nisum.employee.ref.repository.OfferRepository;
import com.nisum.employee.ref.view.OfferDTO;

public class OfferServiceTest {

	@Mock
	private OfferRepository offerRepository;

	@Mock
	private OfferConverter offerConverter;

	@Mock
	private GenerateOfferService offerService;
	
	@Mock
	private UserService userService;

	@Mock
	private INotificationService notificationService;
	@Mock
	private PositionService positionService;
	
	
	@Test
	public void testSaveOffer() {
		
		
		
		
		
		
	}

	@Test
	public void testGenerateNotification() {
	}

	@Test
	public void testGenerateOfferLetter() {
	}

	@Test
	public void testSaveResumeInBucket() {
	}

	@Test
	public void testGetNextStatuses() {
	}

	@Test
	public void testGetOffers() {
	}

	@Test
	public void testGetOffer() {
	}

	@Test
	public void testGetOffersByJobcode() {
	}

	@Test
	public void testGetOffersByManagerId() {
	}

	private OfferDTO getOfferDTO()
	{
		OfferDTO offerDTO = new OfferDTO();
		
		offerDTO.setCandidateName("Naga");
		
		
		return offerDTO;
	}
}
