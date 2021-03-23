package edu.ncsu.csc216.service_wolf.model.io;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import edu.ncsu.csc216.service_wolf.model.incident.Incident;
import edu.ncsu.csc216.service_wolf.model.service_group.ServiceGroup;

public class ServiceGroupsReaderTest {

	private final String incident1 = "test-files/incidents1.txt";
	private final String incident5 = "test-files/incidents5.txt";
  
	@Test
	public void testServiceGroupsReader() {
		ServiceGroupsReader sgr = new ServiceGroupsReader();
		assertNotNull(sgr);
	}

	@Test
	public void testReadServiceGroupsFile() {
		
		ArrayList<ServiceGroup> al = ServiceGroupsReader.readServiceGroupsFile(incident1);
		assertEquals(3, al.size());
		assertEquals("CSC IT", al.get(0).getServiceGroupName());
		assertEquals("ITECS", al.get(1).getServiceGroupName());
		assertEquals("OIT", al.get(2).getServiceGroupName());
		assertEquals(4, al.get(0).getIncidents().size());
		assertEquals(Incident.CANCELED_NAME, al.get(0).getIncidentById(2).getState());

		ArrayList<ServiceGroup> al1 = ServiceGroupsReader.readServiceGroupsFile(incident5);
		if (al1.get(0).getIncidents().size() != 0)
			fail();
		
		
		

	}

}
