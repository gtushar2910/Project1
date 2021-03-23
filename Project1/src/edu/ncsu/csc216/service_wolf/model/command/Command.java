package edu.ncsu.csc216.service_wolf.model.command;

/**
 * Command class is 
 * @author Varan J Mehta
 *
 */
public class Command {
	 
	/**
	 * @param commandInformation the commandInformation to set
	 */
	public void setCommandInformation(String commandInformation) {
		this.commandInformation = commandInformation;
	}
	/**
	 * @param commandMessage the commandMessage to set
	 */
	public void setCommandMessage(String commandMessage) {
		this.commandMessage = commandMessage;
	}
	/**
	 * 
	 */
	public String commandInformation;
	/**
	 * 
	 */
	public CommandValue cv;
	/**
	 * 
	 */
	public String commandMessage;
	/**
	 * 
	 * @param command
	 * @param commandInformation is a String variable in the Command class
	 * @param commandMessage is a String variable in the Command class 
	 */
	public Command (CommandValue command, String commandInformation, String commandMessage) {
		if (command == null) {
			throw new IllegalArgumentException("A Command MUST have a CommandValue.");
		}
		
		if (commandMessage == null || "".equals(commandMessage)) {
			throw new IllegalArgumentException(" A Command MUST have a commandMessage.");
		}
		
		if((command == Command.CommandValue.ASSIGN || command == Command.CommandValue.HOLD || command == Command.CommandValue.RESOLVE || command == Command.CommandValue.CANCEL) && (commandInformation == null || "".equals(commandInformation))){
			throw new IllegalArgumentException("These commands require an additional piece of information.");
		} 
		
		if((command == Command.CommandValue.INVESTIGATE || command == Command.CommandValue.REOPEN) && commandInformation != null){
			throw new IllegalArgumentException("These commands do NOT require an additional piece of information.");
		}
		this.commandInformation = commandInformation; 
		this.commandMessage = commandMessage;
		this.cv = command; 
	}
	/**
	 * 
	 * @return
	 */
	public CommandValue getCommand() {
		return cv;		
	}
	/**
	 * 
	 * @return
	 */
	public String getCommandInformation() {
		return commandInformation;
		
	}
	/**
	 * 
	 * @return
	 */
	public String getCommandMessage() {
		return commandMessage;
		
	}
	/**
	 * CommandValue class is an enumeration that is stored in the Command class
	 * @author varan
	 *
	 */
	public enum CommandValue { ASSIGN, HOLD, INVESTIGATE, RESOLVE, REOPEN, CANCEL }
	

}
