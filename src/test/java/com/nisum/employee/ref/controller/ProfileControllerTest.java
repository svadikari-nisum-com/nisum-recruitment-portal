package com.nisum.employee.ref.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nisum.employee.ref.domain.Profile;
import com.nisum.employee.ref.service.IProfileService;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;
import com.nisum.employee.ref.util.MockTestUtil;
import com.nisum.employee.ref.view.ProfileDTO;

@RunWith(MockitoJUnitRunner.class)
public class ProfileControllerTest {
	private MockMvc mockMvc;

    @Mock
    private IProfileService profileService;
    @InjectMocks
    private ProfileController profileController = new ProfileController();

   @Before
   public void init() {
	    mockMvc = MockMvcBuilders.standaloneSetup(profileController)
			      .setHandlerExceptionResolvers(ExceptionHandlerAdviceUtil.createExceptionResolver()).build();
   }
   
   @Test
   public void testRetrieveProfile() throws Exception
   {
	   ProfileDTO profile = new ProfileDTO();
	   List<ProfileDTO> positionsDetails  = new ArrayList<>();
	   profile.setEmailId("karanam@nisum.com");
	   positionsDetails.add(profile);
	
	   //By Email
		Mockito.when(
				(profileService).retrieveCandidateDetails(any(String.class)))
				.thenReturn(positionsDetails);
		mockMvc.perform(get("/profile").param("emailId", "skaranam@nisum.com")).andExpect(status().isOk());

		
		//jobcodeProfile
		Mockito.when(
				(profileService).retrieveProfileByJobCode("Rama"))
				.thenReturn(positionsDetails);
		 mockMvc.perform(
					get("/profile").param("jobcodeProfile", "Rama")).andExpect(status().isOk()); 
		 
		 //profilecreatedBy
		 Mockito.when(
					(profileService).retrieveProfileByProfileCreatedBy("Naga"))
					.thenReturn(positionsDetails);
			 mockMvc.perform(
						get("/profile").param("profilecreatedBy", "Naga")).andExpect(status().isOk()); 
		
		//All Profiles
		 Mockito.when(
					(profileService).retrieveAllProfiles())
					.thenReturn(positionsDetails);
			 mockMvc.perform(
						get("/profile").contentType(MediaType.APPLICATION_JSON).
						content(MockTestUtil.convertToJsonFormat(new Profile()))).andExpect(status().isOk()); 
   }
   
   @Test
   public void testRegisterUser() throws Exception {
	  ProfileDTO profile = new ProfileDTO();
	  profile.setCandidateName("swathi");
	  doNothing().when(profileService).createCandidate(profile);
	  mockMvc.perform(post("/profile").contentType(MediaType.APPLICATION_JSON).content(MockTestUtil.convertToJsonFormat(new ProfileDTO()))).andExpect(status().isOk()); 

   }
   
   @Test
   public void testUpdateProfile() throws Exception {
	  ProfileDTO profile = new ProfileDTO();
	  profile.setCandidateName("swathi");
	  
	  //By profile
	  doNothing().when(profileService).updateCandidate(profile);
		 mockMvc.perform(
					put("/profile").contentType(MediaType.APPLICATION_JSON).
					content(MockTestUtil.convertToJsonFormat(new ProfileDTO()))).andExpect(status().isOk()); 
   }
   
   @Test
   public void deleteCandidate() throws Exception {
	   doNothing().when(profileService).deleteCandidate("sra@gmail.com");;
		mockMvc.perform(delete("/profile").param("emailId", "sra@gmail.com")).andExpect(status().isOk());
   }
   
   @Test
   public void testUpdateProfileStatus() throws Exception
   {
	   ProfileDTO profile = new ProfileDTO();
		  profile.setCandidateName("swathi");
		  
		  //By emailId, status
		  doNothing().when(profileService).updateCandidateStatus("skaran@gmail.com","true");
			mockMvc.perform(put("/profile/status").param("emailId", "sk@gmail.com").param("status", "true")).andExpect(status().isOk());
   }
   
}
