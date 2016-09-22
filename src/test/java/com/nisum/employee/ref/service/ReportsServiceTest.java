package com.nisum.employee.ref.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nisum.employee.ref.common.OfferState;
import com.nisum.employee.ref.domain.InterviewDetails;
import com.nisum.employee.ref.domain.Profile;
import com.nisum.employee.ref.domain.ReportsVO;
import com.nisum.employee.ref.exception.ServiceException;
import com.nisum.employee.ref.repository.PositionRepository;
import com.nisum.employee.ref.repository.ProfileRepository;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;
import com.nisum.employee.ref.view.OfferDTO;
import com.nisum.employee.ref.view.PositionDTO;

@RunWith(MockitoJUnitRunner.class)
public class ReportsServiceTest {

	@InjectMocks
	private ReportsService reportsService;

	@Mock
	private ProfileRepository profileRepository;
	
	@Mock
	private PositionRepository positionRepository;
	

	@Mock
	private InterviewDetailsService interviewDetailsService;

	@Mock
	private ReportsVO reportsVO;

	@Mock
	PositionService positionService;
	
	@Mock
	OfferService offerService;

	@Before
	public void setUp() throws Exception {
		MockMvcBuilders.standaloneSetup(profileRepository, interviewDetailsService)
				.setHandlerExceptionResolvers(ExceptionHandlerAdviceUtil.createExceptionResolver()).build();
	}

	@Test
	public void testGetDataByJobCode() {
		List<Profile> profiles = new ArrayList<>();
		Profile profile = new Profile();
		profile.setEmailId("dprasad@nisum.com");
		profiles.add(profile);

		when(profileRepository.retrieveProfileByJobCode(any(String.class))).thenReturn(profiles);

		List<InterviewDetails> interviewDetailsList = new ArrayList<>();
		InterviewDetails interviewDetails = new InterviewDetails();
		interviewDetails.setCandidateEmail("dprasad@nisum.com");
		interviewDetailsList.add(interviewDetails);

		when(interviewDetailsService.getInterviewByJobCode(any(String.class))).thenReturn(interviewDetailsList);

		reportsVO = reportsService.getDataByJobCode("Java");
		assertNotNull(reportsVO);
	}
	
	@Test
	public void testGetReportByHiringManager() throws ParseException, ServiceException {
		List<PositionDTO> positions = new ArrayList<>();
		PositionDTO position = new PositionDTO();
		position.setHiringManager("Aliza Zaffar ");
		position.setJobcode("DEV_GAP-GID_HYD_382016_642");
		position.setFunctionalGroup("DEV");
		position.setNoOfPositions(2);
		positions.add(position);
		when(positionService.retrieveAllPositions(any(String.class),any(String.class))).thenReturn(positions); 
		List<String> positionsIds = new ArrayList<String>();
		
		for(PositionDTO pos : positions){
			positionsIds.add(pos.getJobcode());
		}
		
		List<InterviewDetails> interviewDetailsList = new ArrayList<>();
		InterviewDetails interviewDetails = new InterviewDetails();
		interviewDetails.setCandidateEmail("kushal@gmail.com_990");
		interviewDetails.setPositionId(positionsIds);
		interviewDetails.setProgress("Hr Round Scheduled");
		interviewDetailsList.add(interviewDetails);
		when(interviewDetailsService.getInterviewByJobCode(any(String.class))).thenReturn(interviewDetailsList);	   
		List<OfferDTO> offers = new ArrayList<OfferDTO>();
		OfferDTO offerDto = new OfferDTO();
		offerDto.setEmailId("kushal@gmail.com");
		offerDto.setJobcodeProfile("DEV_GAP-GID_HYD_382016_642");
		offerDto.setStatus(OfferState.RELEASED.name());
		offers.add(offerDto);
		when(offerService.getOffersByJobcode(any(String.class))).thenReturn(offers);
		
		List<ReportsVO> reportsVO = reportsService.getReportByHiringManager("Aliza Zaffar ","rgangadhari@nisum.com");
		
		assertNotNull(reportsVO);
	}

}
