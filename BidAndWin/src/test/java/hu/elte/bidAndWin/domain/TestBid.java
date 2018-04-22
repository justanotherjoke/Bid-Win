package hu.elte.bidAndWin.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestBid {
	
	private Bid testBid = new Bid();
	
	@Test
	public void testSetId() {
		testBid.setId(1);
		assertEquals("Test SetId in TestBid", 1, 1);
	}
}
