package edu.ncsu.csc216.service_wolf.model.command;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.ncsu.csc216.service_wolf.model.command.Command.CommandValue;
import edu.ncsu.csc216.service_wolf.model.incident.Incident;

public class CommandTest {

	@Test
	public void testSetCommandInformation() {
		Command c = null;
		try {
			c = new Command(CommandValue.HOLD, "Java", "Awaiting Caller");
			c.setCommandInformation("Java not installed correctly");
			assertEquals("Java not installed correctly", c.getCommandInformation());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	@Test
	public void testSetCommandMessage() {
		Command c = null;
		try {
			c = new Command(CommandValue.HOLD, "Java not installed correctly", "Awaiting");
			c.setCommandMessage("Awaiting Caller");
			assertEquals("Awaiting Caller", c.getCommandMessage());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	@Test
	public void testCommand() {
		Command c = null;

		try {
			c = new Command(null, "Java not installed correctly", "Awaiting Caller");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(c);
		}

		try {
			c = new Command(CommandValue.HOLD, "Java not installed correctly", "");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(c);
		}

		try {
			c = new Command(CommandValue.HOLD, "Java not installed correctly", "");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(c);
		}

		try {
			c = new Command(CommandValue.HOLD, "", "Awaiting Caller");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(c);
		}

		try {
			c = new Command(CommandValue.REOPEN, "Java not installed correctly", "Awaiting Caller");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(c);
		}

		try {
			c = new Command(CommandValue.HOLD, "Java not installed correctly", "Awaiting Caller");
			assertNotNull(c);
		} catch (IllegalArgumentException e) {
			fail();
		}
 
	}

	@Test
	public void testGetCommand() {
		Command c = null;
		try {
			c = new Command(CommandValue.HOLD, "Java not installed correctly", "Awaiting Caller");
			assertNotNull(c);
			assertEquals(CommandValue.HOLD, c.getCommand());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	@Test
	public void testGetCommandInformation() {
		Command c = null;
		try {
			c = new Command(CommandValue.HOLD, "Java not installed correctly", "Awaiting Caller");
			assertNotNull(c);
			assertEquals("Java not installed correctly", c.getCommandInformation());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	@Test
	public void testGetCommandMessage() {
		Command c = null;
		try {
			c = new Command(CommandValue.HOLD, "Java not installed correctly", "Awaiting Caller");
			assertNotNull(c);
			assertEquals("Awaiting Caller", c.getCommandMessage());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

}
