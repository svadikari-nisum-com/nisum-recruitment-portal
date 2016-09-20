package com.nisum.employee.ref.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Test;

import com.nisum.employee.ref.exception.ServiceException;


public class CTCCalculatorTest {

	public CTCCalculator ctcCalculator;
	
	@Test
	public void ctcGreaterThanZero() throws ServiceException
	{
		ctcCalculator = new CTCCalculator(3000);
		assertNotNull(ctcCalculator);
		assertEquals("200",""+ctcCalculator.getProfessionalTaxMonthly());
		
	}
	
	@Test
	public void ctcLessThanZero() throws ServiceException
	{
		try
		{
			ctcCalculator = new CTCCalculator(0);
			
		}catch(ServiceException eee){
			Assert.assertNull(ctcCalculator);
		}
	}
	
}
