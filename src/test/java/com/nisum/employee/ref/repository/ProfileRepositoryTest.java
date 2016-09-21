package com.nisum.employee.ref.repository;

import static org.mockito.Matchers.any;

import static org.mockito.Matchers.eq;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.nisum.employee.ref.domain.Profile;

@RunWith(MockitoJUnitRunner.class)
public class ProfileRepositoryTest {

	@Mock
	private MongoOperations mongoOperations;
	
	@Mock
	private MongoDbFactory dbFactory;
	
	@Mock
	private GridFS gridFS;
	
	@Mock
	private GridFSInputFile gridFSInputFile;
	
	@Mock
	private GridFSDBFile gridFSDBFile;
	
	@Mock
	private DB db;
	
	@Mock
	private DBObject dBObject;
	
	@InjectMocks
	private ProfileRepository profileRepository;
	
	@Before
	public void init() throws Exception {
		
		
		/*Mockito.doAnswer(new Answer<DB>() {
		    public DB answer(final InvocationOnMock invocation) throws Throwable {
	            return db;
	        }
		}).when(dbFactory).getDb();*/
		
		
	
		
	/*	Mockito.doAnswer(new Answer<GridFSInputFile>() {
		    public GridFSInputFile answer(final InvocationOnMock invocation) throws Throwable {
	            return gridFSInputFile;
	        }
		}).when(gridFS).createFile(Mockito.any(InputStream.class));
		
		Mockito.when(gridFSInputFile.saveChunks()).thenReturn(7);
		Mockito.doNothing().when(gridFSInputFile).save();

		
		Mockito.doAnswer(new Answer<List<GridFSDBFile>>() {
		    public List<GridFSDBFile> answer(final InvocationOnMock invocation) throws Throwable {
	            return Arrays.asList(gridFSDBFile);
	        }
		}).when(gridFS).find(Mockito.any(DBObject.class));*/
		
	}


	@Test
	public final void testPrepareCandidate() {
		Mockito.doNothing().when(mongoOperations).save(Profile.class);
		profileRepository.createCandidate(getProfile());
	}

	@Test
	public final void testUpdateCandidate() {
		Mockito.doNothing().when(mongoOperations).save(Profile.class);
		profileRepository.updateCandidate(getProfile());
	}

	@Test
	public final void testUpdateCandidateStatus() {
		Mockito.when(mongoOperations.upsert(Mockito.any(Query.class), Mockito.any(Update.class), Mockito.eq("Profile"))).thenReturn(null);
		profileRepository.updateCandidateStatus("nbolla@nisum.com", "A");
	}

	@Test
	public final void deleteCandidate() {
		String emailId="skara@nisum.com";
		Profile profile = new Profile();
		profile.setEmailId(emailId);
		profile.setActive(true);
		Mockito.when(mongoOperations.findOne(Mockito.any(Query.class),Mockito.eq(Profile.class))).thenReturn(profile);
		Mockito.doAnswer( new Answer<WriteResult>() {
			@Override
			public WriteResult answer(final InvocationOnMock invocation) throws Throwable
			{
				return null;
			}
		}).when(mongoOperations).updateFirst(Mockito.any(Query.class), Mockito.any(Update.class), Mockito.eq(Profile.class));
		
		profileRepository.deleteCandidate(emailId);
	}
	
	@Test
	public final void testRetrieveCandidateDetails() {
		Mockito.when(mongoOperations.find(Mockito.any(Query.class), Mockito.eq(Profile.class))).thenReturn(Arrays.asList(getProfile()));
		List<Profile> profile = profileRepository.retrieveCandidateDetails("nbolla@nisum.com");
		Assert.assertNotNull(profile);
		Assert.assertSame("SE", profile.get(0).getDesignation());
	}

	@Test
	public final void testRetrieveProfileByJobCode() {
		Mockito.when(mongoOperations.find(Mockito.any(Query.class), Mockito.eq(Profile.class))).thenReturn(Arrays.asList(getProfile()));
		List<Profile> profile = profileRepository.retrieveProfileByJobCode("007");
		Assert.assertNotNull(profile);
		Assert.assertSame("SE", profile.get(0).getDesignation());
	}

	@Test
	public final void testRetrieveProfileByProfileCreatedBy() {
		
		Mockito.when(mongoOperations.find(Mockito.any(Query.class), Mockito.eq(Profile.class))).thenReturn(Arrays.asList(getProfile()));
		List<Profile> profile = profileRepository.retrieveProfileByProfileCreatedBy("Naga");
		Assert.assertNotNull(profile);
		Assert.assertSame("SE", profile.get(0).getDesignation());
	}

	@Test
	public final void testRetrieveAllProfiles() {
		Mockito.when(mongoOperations.find(any(Query.class), eq(Profile.class))).thenReturn(Arrays.asList(getProfile()));
		List<Profile> profile = profileRepository.retrieveAllProfiles();
		Assert.assertNotNull(profile);
		Assert.assertSame("SE", profile.get(0).getDesignation());
		
	}

	@Test
	public final void testDeleteProfileBasedOnEmailId() {
		
		Mockito.doAnswer(new Answer<Profile>() {
		    public Profile answer(final InvocationOnMock invocation) throws Throwable {
	            return getProfile();
	        }
		}).when(mongoOperations).findOne(any(Query.class), eq(Profile.class));
		
		Mockito.when(mongoOperations.remove(Profile.class)).thenReturn(null);
		Profile profile = profileRepository.deleteProfileBasedOnEmailId("nbolla@nisum.com");
		Assert.assertNotNull(profile);
		Assert.assertSame("SE", profile.getDesignation());
		
	}

	@Ignore
	public final void testSaveResumeInBucket() {
		
		//To Do
		/*MultipartFile multipartFile = new MockMultipartFile("Naga.txt", "Hi Heloo".getBytes());
		
		profileRepository.saveResumeInBucket(multipartFile, "7");
		*/
	}

	@Ignore
	public final void testGetResume() throws Exception 
	{
		
		//To Do
		//String[] test = profileRepository.getResume("nbolla@nisum.com");
		
		
	}

	@Ignore
	public final void testGetData() throws Exception 
	{
		
		//To Do
		//List<GridFSDBFile> gridDBFile = profileRepository.getData("nbolla@nisum.com");
		
	}
	
	public Profile getProfile()
	{
		Profile profile = new Profile();
		
		
		profile.setEmailId("nbolla@nisum.com");
		profile.setDesignation("SE");
		return profile;
		
	}
	
	

}
