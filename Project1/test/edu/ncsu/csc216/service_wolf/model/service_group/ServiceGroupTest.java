package edu.ncsu.csc216.service_wolf.model.service_group;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import edu.ncsu.csc216.service_wolf.model.command.Command;
import edu.ncsu.csc216.service_wolf.model.command.Command.CommandValue;
import edu.ncsu.csc216.service_wolf.model.incident.Incident;
import edu.ncsu.csc216.service_wolf.model.io.ServiceGroupsReader;

public class ServiceGroupTest {

	private final String incident1 = "test-files/incidents1.txt";

	@Test
	public void testServiceGroup() {

		ServiceGroup sg = null;

		try {
			sg = new ServiceGroup("");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(sg);
		}

		try {
			sg = new ServiceGroup("CSC IT");
			assertNotNull(sg);
			assertEquals("CSC IT", sg.getServiceGroupName());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	@Test
	public void testSetIncidentCounter() {
		ArrayList<ServiceGroup> al = ServiceGroupsReader.readServiceGroupsFile(incident1);
		al.get(0).setIncidentCounter();
		assertEquals(10, Incident.counter);
	}

	@Test
	public void testSetServiceGroupName() {
		ServiceGroup sg = null;
		try {
			sg = new ServiceGroup("CSC");
			assertNotNull(sg);
			sg.setServiceGroupName("CSC IT");
			assertEquals("CSC IT", sg.getServiceGroupName());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	@Test
	public void testGetServiceGroupName() {
		ServiceGroup sg = null;
		try {
			sg = new ServiceGroup("CSC IT");
			assertEquals("CSC IT", sg.getServiceGroupName());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	@Test
	public void testAddIncident() {
		// 2,Canceled,Piazza,sesmith5,0,Unowned,Not an Incident

		ArrayList<ServiceGroup> al = ServiceGroupsReader.readServiceGroupsFile(incident1);
		ArrayList<String> logs = new ArrayList<String>();
		logs.add("Started");
		Incident in = new Incident(2, "Canceled", "Piazza", "sesmith5", 0, "Unowned", "Not an Incident", logs);
		try {
			al.get(0).addIncident(in);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(4, al.get(0).getIncidents().size());
		}
	}

	@Test
	public void testGetIncidents() {
		ArrayList<ServiceGroup> al = ServiceGroupsReader.readServiceGroupsFile(incident1);
		assertEquals(4, al.get(0).getIncidents().size());
	}

	@Test
	public void testGetIncidentById() {
		ArrayList<ServiceGroup> al = ServiceGroupsReader.readServiceGroupsFile(incident1);
		assertEquals(Incident.CANCELED_NAME, al.get(0).getIncidentById(2).getState());
	}

	@Test
	public void testExecuteCommand() {
		ArrayList<ServiceGroup> al = ServiceGroupsReader.readServiceGroupsFile(incident1);

		try {
			Command c = new Command(CommandValue.ASSIGN, "Need Additional Investigation", "cgurley");
			al.get(0).getIncidents().get(0).update(c);
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals(Incident.CANCELED_NAME,al.get(0).getIncidents().get(0).getState());
		}
		
		al = ServiceGroupsReader.readServiceGroupsFile(incident1);
		 
		try {
			Command c = new Command(CommandValue.CANCEL, "An incident does not require additional investigation", "not an incident");
			al.get(0).getIncidents().get(1).update(c);
			assertEquals(Incident.CANCELED_NAME,al.get(0).getIncidents().get(1).getState());
		} catch (UnsupportedOperationException e) {
			fail();
		}
		
		al = ServiceGroupsReader.readServiceGroupsFile(incident1);
		try {
			Command c = new Command(CommandValue.ASSIGN, "Need Additional Investigation", "cgurley");
			al.get(0).getIncidents().get(1).update(c);
			assertEquals(Incident.IN_PROGRESS_NAME,al.get(0).getIncidents().get(1).getState());
		} catch (UnsupportedOperationException e) {
			fail();
		}
		
//		al = ServiceGroupsReader.readServiceGroupsFile(incident1);
//		try {
//			Command c = new Command(CommandValue.REOPEN, null, "cgurley");
//			al.get(1).getIncidents().get(0).update(c);
//			assertEquals(Incident.IN_PROGRESS_NAME,al.get(1).getIncidents().get(0).getState());
//		} catch (UnsupportedOperationException e) {
//			fail();
//		}
		
		al = ServiceGroupsReader.readServiceGroupsFile(incident1);
		try {
			Command c = new Command(CommandValue.CANCEL, "An incident does not require additional investigation", "unnecessary to resolve");
			al.get(0).getIncidents().get(2).update(c);
			assertEquals(Incident.CANCELED_NAME,al.get(0).getIncidents().get(2).getState());
		} catch (UnsupportedOperationException e) {
			fail();
		}
		 
		al = ServiceGroupsReader.readServiceGroupsFile(incident1);
		try {
			Command c = new Command(CommandValue.HOLD, "Caller Input is required", "Awaiting Caller");
			al.get(0).getIncidents().get(3).update(c);
			assertEquals(Incident.ON_HOLD_NAME,al.get(0).getIncidents().get(3).getState());
		} catch (UnsupportedOperationException e) {
			fail();
		}
		
		al = ServiceGroupsReader.readServiceGroupsFile(incident1);
		try {
			Command c = new Command(CommandValue.ASSIGN, "Need Additional Investigation", "cgurley");
			al.get(2).getIncidents().get(0).update(c);
			assertEquals(Incident.IN_PROGRESS_NAME,al.get(2).getIncidents().get(0).getState());
		} catch (UnsupportedOperationException e) {
			fail();
		}
		
		al = ServiceGroupsReader.readServiceGroupsFile(incident1);
		try {
			Command c = new Command(CommandValue.CANCEL, "No Need of Additional Investigation", "canceled by the caller");
			al.get(2).getIncidents().get(0).update(c);
			assertEquals(Incident.CANCELED_NAME,al.get(2).getIncidents().get(0).getState());
		} catch (UnsupportedOperationException e) {
			fail();
		}
		
		al = ServiceGroupsReader.readServiceGroupsFile(incident1);
		try {
			Command c = new Command(CommandValue.RESOLVE, "An incident is resolved through the application of a satisfactory fix", "Resolved");
			al.get(2).getIncidents().get(0).update(c);
			assertEquals(Incident.RESOLVED_NAME,al.get(2).getIncidents().get(0).getState());
		} catch (UnsupportedOperationException e) {
			fail();
		}
		
		al = ServiceGroupsReader.readServiceGroupsFile(incident1);
		try {
			Command c = new Command(CommandValue.REOPEN, null, "cgurley");
			al.get(0).getIncidents().get(2).update(c);
			assertEquals(Incident.IN_PROGRESS_NAME,al.get(0).getIncidents().get(2).getState());
		} catch (UnsupportedOperationException e) {
			fail();
		}
	}

	@Test
	public void testDeleteIncidentById() {
		ArrayList<ServiceGroup> al = ServiceGroupsReader.readServiceGroupsFile(incident1);
		assertEquals(4, al.get(0).getIncidents().size());
		al.get(0).deleteIncidentById(2);
		assertEquals(3, al.get(0).getIncidents().size());
	}

}
