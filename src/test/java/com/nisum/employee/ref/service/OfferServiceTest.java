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
		fail("Not yet implemented");
	}

	@Test
	public void testGenerateOfferLetter() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveResumeInBucket() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNextStatuses() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOffers() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOffer() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOffersByJobcode() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOffersByManagerId() {
		fail("Not yet implemented");
	}

	private OfferDTO getOfferDTO()
	{
		OfferDTO offerDTO = new OfferDTO();
		
		offerDTO.setCandidateName("Naga");
		
		
		return offerDTO;
	}
}
