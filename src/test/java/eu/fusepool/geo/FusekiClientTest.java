package eu.fusepool.geo;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FusekiClientTest {
	
	FusekiClient client = null;

	@Before
	public void setUp() throws Exception {
		client = new FusekiClient();
	}

	@Test
	public void testSearchSkiRuns() {
		// Search for skiruns within a radius of 10 km from Trento
		List<String> skiruns = client.searchSkiRuns("46.071", "11.120", "10");
		assertTrue(skiruns.size() == 31);
	}

}
