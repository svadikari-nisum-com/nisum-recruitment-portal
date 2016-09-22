package com.nisum.employee.ref.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.nisum.employee.ref.common.OfferState;
import com.nisum.employee.ref.converter.OfferConverter;
import com.nisum.employee.ref.domain.Offer;
import com.nisum.employee.ref.exception.ServiceException;
import com.nisum.employee.ref.repository.OfferRepository;
import com.nisum.employee.ref.view.OfferDTO;
import com.nisum.employee.ref.view.PositionDTO;
import com.nisum.employee.ref.view.UserInfoDTO;

@RunWith(MockitoJUnitRunner.class)
public class OfferServiceTest {

	@Mock
	private OfferRepository offerRepository;

	@Mock
	private OfferConverter offerConverter;

	@Mock
	private GenerateOfferService generateOfferService;
	
	@Mock
	private UserService userService;

	@Mock
	private INotificationService notificationService;
	@Mock
	private PositionService positionService;
	
	@InjectMocks
	private OfferService offerService;
	
	@Test
	public void testSaveOffer() throws ServiceException {
		
		PositionDTO position =  new PositionDTO();
		position.setLocation("Hyd");
		
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		userInfoDTO.setName("Naga");
		
		Mockito.when(offerConverter.convertToEntity(getOfferDTO())).thenReturn(getOffer());
		Mockito.doNothing().when(offerRepository).saveOffer(getOffer());
		Mockito.doNothing().when(offerRepository).updateInterviewDetails(getOffer());
		Mockito.when(positionService.retrievePositionByJobCode(Mockito.anyString())).thenReturn(position);
		Mockito.when(userService.retrieveUserByRoleAndLocation(Mockito.anyString(), Mockito.anyString())).thenReturn(Arrays.asList(userInfoDTO));
		
		offerService.saveOffer(getOfferDTO());
		
	}

	@Test
	public void testGenerateNotification() throws ServiceException {
		
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		userInfoDTO.setName("Naga");
		Mockito.when(userService.retrieveUserByName(Mockito.anyString())).thenReturn(Arrays.asList(userInfoDTO));
		Mockito.doNothing().when(notificationService).sendOfferNotificationMail(Mockito.anyString(), Mockito.anyString(),Mockito.anyString(), Mockito.anyString());
		offerService.generateNotification("Naga","Naga","Naga");
	}

	@Test
	public void testGenerateOfferLetter() throws ServiceException {
		
		Mockito.doNothing().when(generateOfferService).generateOffer(getOfferDTO());
		offerService.generateOfferLetter(getOfferDTO());
	}

	@Test
	public void testSaveResumeInBucket() throws ServiceException {
		
		Mockito.doNothing().when(offerRepository).saveResumeInBucket(Mockito.anyObject(),Mockito.anyString());
		offerService.saveResumeInBucket(getMultipartFile(), "Naga");
	}

	@Test
	public void testGetNextStatuses() {
		
		List<OfferState> states = offerService.getNextStatuses("Initiated");
		Assert.assertNotNull(states);
	}

	@Test
	public void testGetOffers() {
		
		Mockito.when(offerRepository.getOffers()).thenReturn(Arrays.asList(getOffer()));
		Mockito.when(offerConverter.convertToDTOs(Arrays.asList(getOffer()))).thenReturn(Arrays.asList(getOfferDTO()));
		List<OfferDTO> offerDTO = offerService.getOffers();
		
		Assert.assertNotNull(offerDTO);
		Assert.assertTrue(null, offerDTO.get(0).getCandidateName().equals("Naga"));
	}

	@Test
	public void testGetOffer() {
		
		Mockito.when(offerRepository.getOffer(Mockito.anyString())).thenReturn(getOffer());
		Mockito.when(offerConverter.convertToDTO(getOffer())).thenReturn(getOfferDTO());
		OfferDTO offerDTO = offerService.getOffer("nbolla@nisum.com");
		Assert.assertNotNull(offerDTO);
		Assert.assertTrue(null, offerDTO.getCandidateName().equals("Naga"));
		
	}

	@Test
	public void testGetOffersByJobcode() {
		
		Mockito.when(offerRepository.getOffersByJobcode("Naga")).thenReturn(Arrays.asList(getOffer()));
		Mockito.when(offerConverter.convertToDTOs(Arrays.asList(getOffer()))).thenReturn(Arrays.asList(getOfferDTO()));
		offerService.getOffersByJobcode("Naga");
	}

	@Test
	public void testGetOffersByManagerId() {
		
		PositionDTO position = new PositionDTO();
		position.setJobcode("Naga");
		Mockito.when(positionService.retrieveAllPositions()).thenReturn(Arrays.asList(position));
		Mockito.when(offerRepository.getOffersByJobcode("Naga")).thenReturn(Arrays.asList(getOffer()));
		Mockito.when(offerConverter.convertToDTOs(Arrays.asList(getOffer()))).thenReturn(Arrays.asList(getOfferDTO()));
		offerService.getOffersByManagerId("Naga");
		
	}

	private OfferDTO getOfferDTO()
	{
		OfferDTO offerDTO = new OfferDTO();
		
		offerDTO.setCandidateName("Naga");
		offerDTO.setStatus("Initiated");
		offerDTO.setJobcodeProfile("Ram");
		offerDTO.setReportingManager("Bolla");
		
		return offerDTO;
	}
	private Offer getOffer()
	{
		Offer offer = new Offer();
		offer.setCandidateName("Naga");
		offer.setStatus("Initiated");
		offer.setJobcodeProfile("Ram");
		offer.setReportingManager("Bolla");
		
		return offer;
	}
	public MultipartFile getMultipartFile()
	{
		MultipartFile multipartFile = new MockMultipartFile("Naga.txt", "Hi Heloo".getBytes());
		return multipartFile;
	}
}
