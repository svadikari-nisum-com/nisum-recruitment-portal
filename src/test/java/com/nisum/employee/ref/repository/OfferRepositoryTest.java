package com.nisum.employee.ref.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import com.nisum.employee.ref.domain.Offer;

@RunWith(MockitoJUnitRunner.class)
public class OfferRepositoryTest {

	
	@Mock
	public MongoOperations mongoOperations;
	
	@Mock
	public MongoDbFactory mongoDbFactory;
	
	@Mock
	public GridFSInputFile gridFSInputFile;
	
	@Mock
	public GridFS gridFS;
	
	
	@InjectMocks
	public OfferRepository offerRepository;
	

	@Before
	public void init() throws Exception {
		
		//Mockito.when(mongoDbFactory.getDb()).thenReturn(MongoDbUtils.getDB(null, null));
		//Mockito.doNothing().when(gridFSInputFile).save();
	}

	@Test
	public final void testSaveOffer() {
		
		Mockito.doNothing().when(mongoOperations).save(Offer.class);
		offerRepository.saveOffer(getOffer());
	}

	@Ignore
	public final void testSaveResumeInBucket() {
		
		//To Do
		//offerRepository.saveResumeInBucket(getMultipartFile(), "7");
		
	}
	
	public Offer getOffer()
	{
		Offer offer = new Offer();
		
		offer.setClient("Nisum");
		offer.setComments("Nice");
		offer.setEmailId("nbolla@nisum.com");
		
		return offer;
	}
	public MultipartFile getMultipartFile()
	{
		MultipartFile multipartFile = new MockMultipartFile("Naga.txt", "Hi Heloo".getBytes());
		return multipartFile;
	}
	
	@Test
	public void getOffersByJobcode()
	{
		Offer offer = new Offer();
		offer.setClient("GAP GID");
		offer.setEmailId("satya@aaa.com");
		offer.setJobcodeProfile("DEV_GAP-GID_HYD_382016_642");
		Mockito.when(mongoOperations.find(any(Query.class), eq(Offer.class))).thenReturn(Arrays.asList(offer));
		List<Offer> offers = offerRepository.getOffersByJobcode("DEV_GAP-GID_HYD_382016_642");
		assertNotNull(offers);
		assertEquals("DEV_GAP-GID_HYD_382016_642", offers.get(0).getJobcodeProfile());
	}

}
