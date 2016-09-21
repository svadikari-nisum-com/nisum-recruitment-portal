/**
 * 
 */
package com.nisum.employee.ref.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author NISUM
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class OfferStateTest {

	@Test
	public void nextOfferStates() throws Exception {
		for (OfferState state : OfferState.values()) {
			assertNotNull(state.next());
		}
		assertEquals(OfferState.getState("Initiated"), OfferState.INITIATED);
	}

	@Test
	public void prevOfferStates() throws Exception {
		for (OfferState state : OfferState.values()) {
			assertNotNull(state.prev());
		}
		assertEquals(OfferState.getState("Initiated"), OfferState.INITIATED);
	}

	@Test
	public void getOfferState() throws Exception {
		assertEquals(OfferState.getState("Initiated"), OfferState.INITIATED);
	}
	
	@Test
	public void getOfferState_Default() throws Exception {
		assertEquals(OfferState.getState("NotValidOfferState"), OfferState.NOTINITIATED);
	}
}
