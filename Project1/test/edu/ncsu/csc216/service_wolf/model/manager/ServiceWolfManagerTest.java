package edu.ncsu.csc216.service_wolf.model.manager;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.service_wolf.model.command.Command;
import edu.ncsu.csc216.service_wolf.model.command.Command.CommandValue;
import edu.ncsu.csc216.service_wolf.model.incident.Incident;
import edu.ncsu.csc216.service_wolf.model.io.ServiceGroupWriter;
import edu.ncsu.csc216.service_wolf.model.io.ServiceGroupsReader;
import edu.ncsu.csc216.service_wolf.model.service_group.ServiceGroup;

public class ServiceWolfManagerTest {

	private final String incident1 = "test-files/incidents1.txt";

	private final String outputFile = "test-files/out-incidents1.txt";
	
	@Before
	public void setUp() throws Exception {
		//Reset the counter at the beginning of every test.
		ServiceWolfManager.getInstance().resetManager();
	}

	@Test
	public void testGetInstance() {
		ServiceWolfManager swm = ServiceWolfManager.getInstance();
		assertNotNull(swm);

		ServiceWolfManager swm1 = ServiceWolfManager.getInstance();
		assertNotNull(swm1);

		assertEquals(swm, swm1);
	}

	@Test
	public void testSaveToFile() {

		try {
			ServiceWolfManager.getInstance().loadFromFile(incident1);
			ServiceWolfManager.getInstance().saveToFile(outputFile);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testLoadFromFile() {
		try {
			ServiceWolfManager.getInstance().loadFromFile(incident1);
			assertEquals(3, ServiceWolfManager.getInstance().getServiceGroupList().length);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetIncidentsAsArray() {
		try {
			ServiceWolfManager.getInstance().loadFromFile(incident1);
			assertEquals("2", ServiceWolfManager.getInstance().getIncidentsAsArray()[0][0]);
			assertEquals(Incident.NEW_NAME, ServiceWolfManager.getInstance().getIncidentsAsArray()[1][1]);
			assertEquals("Set up Jenkins VMs", ServiceWolfManager.getInstance().getIncidentsAsArray()[2][2]);
			assertEquals("No Status", ServiceWolfManager.getInstance().getIncidentsAsArray()[3][3]);
		} catch (Exception e) {
			fail();
		}
	} 

	@Test
	public void testGetIncidentById() {
		try {
			ServiceWolfManager.getInstance().loadFromFile(incident1);
			assertEquals(3, ServiceWolfManager.getInstance().getIncidentById(3).getId());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testExecuteCommand() {
		ServiceWolfManager.getInstance().loadFromFile(incident1);
		try {
			Command c = new Command(CommandValue.ASSIGN, "Need Additional Investigation", "cgurley");
			ServiceWolfManager.getInstance().executeCommand(2, c);
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals(Incident.CANCELED_NAME, ServiceWolfManager.getInstance().getIncidentById(2).getState());
		}
	}

	@Test
	public void testDeleteIncidentById() {
		try {
			ServiceWolfManager.getInstance().loadFromFile(incident1);
			assertEquals(4, ServiceWolfManager.getInstance().getIncidentsAsArray().length);
			ServiceWolfManager.getInstance().deleteIncidentById(3);
			assertEquals(3, ServiceWolfManager.getInstance().getIncidentsAsArray().length);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testAddIncidentToServiceGroup() {
		ServiceWolfManager.getInstance().loadFromFile(incident1);
		ServiceWolfManager.getInstance().loadServiceGroup("OIT");
		try {
			ServiceWolfManager.getInstance().addIncidentToServiceGroup("Piazza", "sesmith5", "Not an Incident");
			assertEquals(2, ServiceWolfManager.getInstance().getIncidentsAsArray().length);
		} catch (IllegalArgumentException iae) {
			fail();
		}
	}

	@Test
	public void testLoadServiceGroup() {
		ServiceWolfManager.getInstance().loadFromFile(incident1);
		ServiceWolfManager.getInstance().loadServiceGroup("OIT");
		assertEquals(Incident.IN_PROGRESS_NAME, ServiceWolfManager.getInstance().getIncidentById(1).getState());
	}

	@Test
	public void testGetServiceGroupName() {
		ServiceWolfManager.getInstance().loadFromFile(incident1);
		assertEquals("CSC IT", ServiceWolfManager.getInstance().getServiceGroupName());
	}

	@Test
	public void testGetServiceGroupList() {
		try {
			ServiceWolfManager.getInstance().loadFromFile(incident1);
			assertEquals(3, ServiceWolfManager.getInstance().getServiceGroupList().length);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testClearServiceGroups() {
		try {
			ServiceWolfManager.getInstance().loadFromFile(incident1);
			assertEquals(3, ServiceWolfManager.getInstance().getServiceGroupList().length);
			ServiceWolfManager.getInstance().clearServiceGroups();
			assertEquals(0, ServiceWolfManager.getInstance().getServiceGroupList().length);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testEditServiceGroup() {
		ServiceWolfManager.getInstance().loadFromFile(incident1);
		try {
			ServiceWolfManager.getInstance().editServiceGroup("OIT");
			fail();
		} catch (IllegalArgumentException iae) {
			ServiceWolfManager.getInstance().editServiceGroup("Major");
			assertEquals("Major", ServiceWolfManager.getInstance().getServiceGroupName());
		}

	}

	@Test
	public void testAddServiceGroup() {
		ServiceWolfManager.getInstance().loadFromFile(incident1);
		try {
			ServiceWolfManager.getInstance().addServiceGroup("");
			fail();
		} catch (IllegalArgumentException e) {
			try {
				ServiceWolfManager.getInstance().addServiceGroup("CSC IT");
				fail();
			} catch (IllegalArgumentException iae) {
				ServiceWolfManager.getInstance().addServiceGroup("CSC CO");
				assertEquals(4, ServiceWolfManager.getInstance().getServiceGroupList().length);
			}
		}
	}

	@Test
	public void testDeleteServiceGroup() {
		try {
			ServiceWolfManager.getInstance().loadFromFile(incident1);
			assertEquals(3, ServiceWolfManager.getInstance().getServiceGroupList().length);
			ServiceWolfManager.getInstance().deleteServiceGroup();
			assertEquals(2, ServiceWolfManager.getInstance().getServiceGroupList().length);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testResetManager() {
		try {
			ServiceWolfManager.getInstance().loadFromFile(incident1);
			assertEquals(3, ServiceWolfManager.getInstance().getServiceGroupList().length);
			ServiceWolfManager.getInstance().resetManager();
			assertEquals(0, ServiceWolfManager.getInstance().getServiceGroupList().length);
		} catch (Exception e) {
			fail();
		}
	}

}
