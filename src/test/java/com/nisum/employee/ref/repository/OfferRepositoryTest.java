package com.nisum.employee.ref.repository;

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

}
