package edu.ncsu.csc216.service_wolf.model.io;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import edu.ncsu.csc216.service_wolf.model.service_group.ServiceGroup;

public class ServiceGroupWriterTest {
	private final String incident1 = "test-files/incidents1.txt";

	@Test
	public void testServiceGroupWriter() {
		ServiceGroupWriter sgw = new ServiceGroupWriter();
		assertNotNull(sgw);
	}

	@Test
	public void testWriteServiceGroupsToFile() {
		ArrayList<ServiceGroup> al = ServiceGroupsReader.readServiceGroupsFile(incident1);
		try {
			ServiceGroupWriter.writeServiceGroupsToFile("test1.txt", al);
		} catch (Exception e) {
			fail();
		}
	}

}
