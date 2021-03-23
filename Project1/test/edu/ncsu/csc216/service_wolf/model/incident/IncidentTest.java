package edu.ncsu.csc216.service_wolf.model.incident;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.service_wolf.model.command.Command;
import edu.ncsu.csc216.service_wolf.model.command.Command.CommandValue;

public class IncidentTest {

	@Before
	public void setUp() throws Exception {
		// Reset the counter at the beginning of every test.
		Incident.setCounter(0);
	}

	@Test
	public void testIncidentStringStringString() {
		Incident in = new Incident("Moodle down", "sesmith5", "No Status");
		assertEquals(Incident.NEW_NAME, in.getState());
		assertEquals(Incident.UNOWNED, in.getOwner());
	}

	@Test
	public void testIncidentIntStringStringStringIntStringStringArrayListOfString() {
		try {
			ArrayList<String> logs = new ArrayList<String>();
			logs.add("Set up piazza for Spring 2021");
			logs.add("Canceled; not an NC State IT service");
			Incident in = new Incident(2, "Canceled", "Piazza", "sesmith5", 0, "Unowned", "Not an Incident", logs);
			assertEquals(Incident.CANCELED_NAME, in.getState());
			assertEquals(Incident.UNOWNED, in.getOwner());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetId() {
		try {
			Incident in = new Incident("Moodle down", "sesmith5", "No Status");
			assertEquals(Incident.NEW_NAME, in.getState());
			assertEquals(0, in.getId());
			Incident in1 = new Incident("Piazza", "sesmith5", "No Status");
			assertEquals(1, in1.getId());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetState() {
		try {
			Incident in = new Incident("Moodle down", "sesmith5", "No Status");
			assertEquals(Incident.NEW_NAME, in.getState());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetTitle() {
		try {
			Incident in = new Incident("Moodle down", "sesmith5", "No Status");
			assertEquals(Incident.NEW_NAME, in.getState());
			assertEquals("sesmith5", in.getCaller());
			assertEquals("Moodle down", in.getTitle());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetCaller() {
		try {
			Incident in = new Incident("Moodle down", "sesmith5", "No Status");
			assertEquals(Incident.NEW_NAME, in.getState());
			assertEquals("sesmith5", in.getCaller());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetReopenCount() {
		try {
			Incident in = new Incident("Moodle down", "sesmith5", "No Status");
			assertEquals(Incident.NEW_NAME, in.getState());
			in.update(new Command(CommandValue.ASSIGN, "cgurley", "Need Additional Investigation"));
			assertEquals(Incident.IN_PROGRESS_NAME, in.getState());
			assertEquals("cgurley", in.getOwner());
			in.update(new Command(CommandValue.RESOLVE, "Permanently Solved", "Solved"));
			assertEquals(Incident.RESOLVED_NAME, in.getState());
			assertEquals("Permanently Solved", in.getStatusDetails());
			assertEquals(0, in.getReopenCount());
			in.update(new Command(CommandValue.REOPEN, null, "cgurley"));
			assertEquals(Incident.IN_PROGRESS_NAME, in.getState());
			assertEquals(1, in.getReopenCount());
		} catch (IllegalArgumentException iae) {
			fail();
		}
	}

	@Test
	public void testGetOwner() {
		try {
			Incident in = new Incident("Moodle down", "sesmith5", "No Status");
			assertEquals(Incident.NEW_NAME, in.getState());
			in.update(new Command(CommandValue.ASSIGN, "cgurley", "Need Additional Investigation"));
			assertEquals(Incident.IN_PROGRESS_NAME, in.getState());
			assertEquals("cgurley", in.getOwner());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetStatusDetails() {
		try {
			Incident in = new Incident("Moodle down", "sesmith5", "No Status");
			assertEquals(Incident.NEW_NAME, in.getState());
			in.update(new Command(CommandValue.ASSIGN, "cgurley", "Need Additional Investigation"));
			assertEquals(Incident.IN_PROGRESS_NAME, in.getState());
			assertEquals("cgurley", in.getOwner());
			in.update(new Command(CommandValue.HOLD, "Awaiting Caller", "Caller Input is required"));
			assertEquals(Incident.ON_HOLD_NAME, in.getState());
			assertEquals(Incident.HOLD_AWAITING_CALLER, in.getStatusDetails());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testAddMessageToIncidentLog() {
		try {
			Incident.setCounter(1);
			ArrayList<String> logs = new ArrayList<String>();
			logs.add("Set up piazza for Spring 2021");
			logs.add("Canceled; not an NC State IT service");
			Incident in = new Incident(2, "Canceled", "Piazza", "sesmith5", 0, "Unowned", "Not an Incident", logs);
			String incidentLogs[] = in.getIncidentLogMessages().split("\\r?\\n?[-]");
			assertEquals(2, incidentLogs.length - 1);
			in.addMessageToIncidentLog("Canceled; Due to Unknown Reason");
			incidentLogs = in.getIncidentLogMessages().split("\\r?\\n?[-]");
			assertEquals(3, incidentLogs.length - 1);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testIncrementCounter() {
		try {
			Incident.setCounter(1);
			Incident.incrementCounter();
			assertEquals(2, Incident.counter);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testSetCounter() {
		try {
			Incident.setCounter(1);
			assertEquals(1, Incident.counter);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetIncidentLogMessages() {
		try {
			Incident.setCounter(1);
			ArrayList<String> logs = new ArrayList<String>();
			logs.add("Set up piazza for Spring 2021");
			logs.add("Canceled; not an NC State IT service");
			Incident in = new Incident(2, "Canceled", "Piazza", "sesmith5", 0, "Unowned", "Not an Incident", logs);
			String incidentLogs[] = in.getIncidentLogMessages().split("\\r?\\n?[-]");
			assertEquals(2, incidentLogs.length - 1);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testToString() {
		try {
			ArrayList<String> logs = new ArrayList<String>();
			logs.add("Set up piazza for Spring 2021");
			logs.add("Canceled; not an NC State IT service");
			Incident in = new Incident(2, "Canceled", "Piazza", "sesmith5", 0, "Unowned", "Not an Incident", logs);
			String str = "* 2,Canceled,Piazza,sesmith5,0,Unowned,Not an Incident\n" + "- Set up piazza for Spring 2021\n"
					+ "- Canceled; not an NC State IT service\n";
			assertEquals(str, in.toString());
		} catch (IllegalArgumentException iae) {
			fail();
		}
	}

	@Test
	public void testUpdate() {
		try {
			Incident in = new Incident("Moodle down", "sesmith5", "No Status");
			assertEquals(Incident.NEW_NAME, in.getState());
			in.update(new Command(CommandValue.ASSIGN, "cgurley", "Need Additional Investigation"));
			assertEquals(Incident.IN_PROGRESS_NAME, in.getState());
			assertEquals("cgurley", in.getOwner());
			in.update(new Command(CommandValue.RESOLVE, "Permanently Solved", "Solved"));
			assertEquals(Incident.RESOLVED_NAME, in.getState());
			assertEquals("Permanently Solved", in.getStatusDetails());
			in.update(new Command(CommandValue.REOPEN, null, "cgurley"));
			assertEquals(Incident.IN_PROGRESS_NAME, in.getState());
		} catch (IllegalArgumentException iae) {
			fail();
		}

		try {
			Incident in = new Incident("Moodle down", "sesmith5", "No Status");
			assertEquals(Incident.NEW_NAME, in.getState());
			in.update(new Command(CommandValue.ASSIGN, "cgurley", "Need Additional Investigation"));
			assertEquals(Incident.IN_PROGRESS_NAME, in.getState());
			assertEquals("cgurley", in.getOwner());
			in.update(new Command(CommandValue.HOLD, "Awaiting Caller", "Caller Input is required"));
			assertEquals(Incident.ON_HOLD_NAME, in.getState());
			assertEquals(Incident.HOLD_AWAITING_CALLER, in.getStatusDetails());
			in.update(new Command(CommandValue.INVESTIGATE, null, "cgurley"));
			assertEquals(Incident.IN_PROGRESS_NAME, in.getState());
			in.update(
					new Command(CommandValue.CANCEL, "No Need of Additional Investigation", "canceled by the caller"));
			assertEquals(Incident.CANCELED_NAME, in.getState());
		} catch (IllegalArgumentException iae) {
			fail();
		}

	}

}
