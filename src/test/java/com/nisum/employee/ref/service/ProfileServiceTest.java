package com.nisum.employee.ref.service;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mongodb.gridfs.GridFSDBFile;
import com.nisum.employee.ref.convert.ProfileConverter;
import com.nisum.employee.ref.domain.Profile;
import com.nisum.employee.ref.repository.ProfileRepository;
import com.nisum.employee.ref.util.ExceptionHandlerAdviceUtil;
import com.nisum.employee.ref.view.ProfileDTO;

@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceTest {

	@InjectMocks
	private ProfileService profileService;
	
	@Spy
	private ProfileConverter profileConverter = new ProfileConverter();

	@Mock
	private ProfileRepository profileRepository;

	private List<Profile> profiles;

	@Before
	public void setUp() throws Exception {
		MockMvcBuilders.standaloneSetup(profileRepository)
				.setHandlerExceptionResolvers(ExceptionHandlerAdviceUtil.createExceptionResolver()).build();

		profiles = new ArrayList<>();
		Profile profile = new Profile();
		profile.setEmailId("dprasad@nisum.com");
		profiles.add(profile);
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testPrepareCandidate() throws Exception {
		Profile profile = new Profile();
		profile.setEmailId("dprasad@gmail.com");

		when(profileRepository.retrieveAllProfiles()).thenReturn(profiles);
		doNothing().when(profileRepository).prepareCandidate(profile);
		profileService.prepareCandidate(profile);
	}
	
	@Test(expected=Exception.class)
	public void prepareCandidateThrowsException() throws Exception {
		Profile profile = new Profile();
		profile.setEmailId("dprasad@nisum.com");

		when(profileRepository.retrieveAllProfiles()).thenReturn(profiles);
		doNothing().when(profileRepository).prepareCandidate(profile);
		profileService.prepareCandidate(profile);
	}

	@Test
	public void testUpdateCandidate() {
		Profile candidate = new Profile();
		candidate.setEmailId("dprasad@nisum.com");

		doNothing().when(profileRepository).updateCandidate(candidate);
		profileService.updateCandidate(candidate);
	}

	@Test
	public void testUpdateCandidateStatus() {
		doNothing().when(profileRepository).updateCandidateStatus(any(String.class), any(String.class));
		profileService.updateCandidateStatus("dprasad@nisum.com", "status");
	}

	@Test
	public void testRetrieveCandidateDetails() {
		when(profileRepository.retrieveCandidateDetails(any(String.class))).thenReturn(profiles);
		List<ProfileDTO> expectedProfiles = profileService.retrieveCandidateDetails("dprasad@nisum.com");
		assertNotNull(expectedProfiles);
		assertEquals(expectedProfiles.get(0).getEmailId(), profiles.get(0).getEmailId());
	}

	@Test
	public void testRetrieveProfileByJobCode() {
		when(profileRepository.retrieveProfileByJobCode(any(String.class))).thenReturn(profiles);
		List<ProfileDTO> expectedProfiles = profileService.retrieveProfileByJobCode("56565");
		assertNotNull(expectedProfiles);
		assertEquals(expectedProfiles.get(0).getEmailId(), profiles.get(0).getEmailId());
	}

	@Test
	public void testRetrieveProfileByProfileCreatedBy() {
		when(profileRepository.retrieveProfileByProfileCreatedBy(any(String.class))).thenReturn(profiles);
		List<ProfileDTO> expectedProfiles = profileService.retrieveProfileByProfileCreatedBy("Durga");
		assertNotNull(expectedProfiles);
		assertEquals(expectedProfiles.get(0).getEmailId(), profiles.get(0).getEmailId());
	}

	@Test
	public void testRetrieveAllProfiles() {
		when(profileRepository.retrieveAllProfiles()).thenReturn(profiles);
		List<ProfileDTO> expectedProfiles = profileService.retrieveAllProfiles();
		assertNotNull(expectedProfiles);
		assertEquals(expectedProfiles.get(0).getEmailId(), profiles.get(0).getEmailId());
	}

	@Test
	public void testDeleteProfileBasedOnEmailId() {
		Profile profile = new Profile();
		profile.setEmailId("dprasad@nisum.com");

		when(profileRepository.deleteProfileBasedOnEmailId(any(String.class))).thenReturn(profile);
		Profile expectedProfile = profileService.deleteProfileBasedOnEmailId("dprasad@nisum.com");
		assertNotNull(expectedProfile);
		assertEquals(expectedProfile.getEmailId(), profile.getEmailId());
	}
	
	/**
	 * Test case not yet implemented completely.
	 */
	@Ignore
	public void testSaveResume() throws IOException {
		FileInputStream inputFile = new FileInputStream("D:/WorkDocuments/resume.txt");
		MockMultipartFile file = new MockMultipartFile("file", "resume", "multipart/form-data", inputFile);

		doNothing().when(profileRepository).saveResumeInBucket(file, any(String.class));
		profileService.saveResume(file, "9999");
	}

	@Test
	public void testGetResume() throws Exception {
		String[] resumes = { "durga" };
		when(profileRepository.getResume(any(String.class))).thenReturn(resumes);
		String[] expectedResumes = profileService.getResume("dprasad@nisum.com");
		assertNotNull(expectedResumes);
		assertEquals(expectedResumes[0], resumes[0]);
	}

	@Test
	public void testGetFileData() throws Exception {
		List<GridFSDBFile> gridFSDBFiles = new ArrayList<>();
		when(profileRepository.getData(any(String.class))).thenReturn(gridFSDBFiles);
		List<GridFSDBFile> expGridFSDBFile = profileService.getFileData("dprasad@nisum.com");
		assertNotNull(expGridFSDBFile);
	}

}
