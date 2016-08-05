package com.nisum.employee.ref.controller;

import static org.mockito.Matchers.any;

import static org.mockito.Mockito.doNothing;
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
   public void testRetrieveCandidateDetailsByMail() throws Exception {
	   ProfileDTO profile = new ProfileDTO();
	   List<ProfileDTO> positionsDetails  = new ArrayList<>();
	   profile.setEmailId("karanam@nisum.com");
		positionsDetails.add(profile);
		Mockito.when(
				(profileService).retrieveCandidateDetails(any(String.class)))
				.thenReturn(positionsDetails);
		mockMvc.perform(get("/profile").param("emailId", "skaranam@nisum.com")).andExpect(status().isOk());
   }
   @Test
   public void testRetrieveCandidateDetailsByJobCodeProfile() throws Exception {
	   ProfileDTO profile = new ProfileDTO();
	   List<ProfileDTO> positionsDetails  = new ArrayList<>();
	   ArrayList<String> jobcodeProfile = new ArrayList<>();
	   jobcodeProfile.add("1");
	   profile.setJobcodeProfile(jobcodeProfile);
		positionsDetails.add(profile);
		Mockito.when(
				(profileService).retrieveProfileByJobCode(any(String.class)))
				.thenReturn(positionsDetails);
		 mockMvc.perform(
					get("/profile").contentType(MediaType.APPLICATION_JSON).
					content(MockTestUtil.convertToJsonFormat(new Profile()))).andExpect(status().isOk()); 
   }
   @Test
   public void testRetrieveCandidateDetailsProfileCreatedBy() throws Exception {
	   ProfileDTO profile = new ProfileDTO();
	   List<ProfileDTO> positionsDetails  = new ArrayList<>();
	   profile.setProfilecreatedBy("skaranam@gmail.com");
		positionsDetails.add(profile);
		Mockito.when(
				(profileService).retrieveProfileByProfileCreatedBy(any(String.class)))
				.thenReturn(positionsDetails);
		 mockMvc.perform(
					get("/profile").contentType(MediaType.APPLICATION_JSON).
					content(MockTestUtil.convertToJsonFormat(new Profile()))).andExpect(status().isOk()); 
   }
   @Test
   public void testRetrieveAllProfiles() throws Exception {
	   ProfileDTO profile = new ProfileDTO();
	   List<ProfileDTO> positionsDetails  = new ArrayList<>();
	   profile.setProfilecreatedBy("skaranam@gmail.com");
		positionsDetails.add(profile);
		Mockito.when(
				(profileService).retrieveAllProfiles())
				.thenReturn(positionsDetails);
		 mockMvc.perform(
					get("/profile").contentType(MediaType.APPLICATION_JSON).
					content(MockTestUtil.convertToJsonFormat(new Profile()))).andExpect(status().isOk()); 
   }
   @Test
   public void testRegisterUser() throws Exception {
	  String candidate = "Success";
	  ProfileDTO profile = new ProfileDTO();
	  profile.setCandidateName("swathi");
	  Mockito.when((profileService).createCandidate(any(ProfileDTO.class))).thenReturn(candidate);
	  mockMvc.perform(post("/profile").contentType(MediaType.APPLICATION_JSON).content(MockTestUtil.convertToJsonFormat(new Profile()))).andExpect(status().isOk()); 

   }
   @Test
   public void testUpdateUser() throws Exception {
	  Profile profile = new Profile();
	  profile.setCandidateName("swathi");
	  doNothing().when(profileService).updateCandidate(profile);
		 mockMvc.perform(
					put("/profile").contentType(MediaType.APPLICATION_JSON).
					content(MockTestUtil.convertToJsonFormat(new Profile()))).andExpect(status().isOk()); 
   }
   @Test
   public void testUpdateProfileStatus() throws Exception {
	   doNothing().when(profileService).updateCandidateStatus("skaran@gmail.com","true");
		mockMvc.perform(post("/status").param("emailId", "sk@gmail.com").param("status", "true")).andExpect(status().isOk());

   }
}
