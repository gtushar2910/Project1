package edu.ncsu.csc216.service_wolf.model.incident;

import java.util.ArrayList;

import edu.ncsu.csc216.service_wolf.model.command.Command;

/**
 * Incident class
 * 
 * @author varan
 *
 */
public class Incident {
	/**
	 * incidentid
	 */
	private int incidentId;

	private IncidentState currentState;

	private String title;

	private String caller;

	private int reopenCount;

	private String owner;

	private String statusDetails;

	private ArrayList<String> incidentLog = new ArrayList<String>();

	public static final String NEW_NAME = "New";

	public static final String IN_PROGRESS_NAME = "In Progress";

	public static final String ON_HOLD_NAME = "On Hold";

	public static final String RESOLVED_NAME = "Resolved";

	public static final String CANCELED_NAME = "Canceled";

	public static final String HOLD_AWAITING_CALLER = "Awaiting Caller";

	public static final String HOLD_AWAITING_CHANGE = "Awaiting Change";

	public static final String HOLD_AWAITING_VENDOR = "Awaiting Vendor";

	public static final String RESOLUTION_PERMANENTLY_SOLVED = "Permanently Solved";

	public static final String RESOLUTION_WORKAROUND = "Workaround";

	public static final String RESOLUTION_CALLER_CLOSED = "Caller Closed";

	public static final String CANCELLATION_DUPLICATE = "Duplicate";

	public static final String CANCELLATION_UNNECESSARY = "Unnecessary";

	public static final String CANCELLATION_NOT_AN_INCIDENT = "Not an Incident";

	public static final String CANCELLATION_CALLER_CANCELLED = "Caller Cancelled";

	public static final String UNOWNED = "Unowned";

	public static final String NO_STATUS = "No Status";

	public static int counter = 0;

	public final IncidentState newState = new NewState();

	public final IncidentState inProgressState = new InProgressState();

	public final IncidentState onHoldState = new OnHoldState();

	public final IncidentState resolvedState = new ResolvedState();

	public final IncidentState canceledState = new CanceledState();

	public Incident(String title, String caller, String message) {
		if (title == null || "".equals(title) || caller == null || "".equals(caller) || message == null
				|| "".equals(message)) {
			throw new IllegalArgumentException();
		}

		incidentId = Incident.counter;
		Incident.incrementCounter();
		setState(Incident.NEW_NAME);
		setTitle(title);
		setCaller(caller);
		setOwner(UNOWNED);
		setStatusDetails(NO_STATUS);
		incidentLog = new ArrayList<String>();
		incidentLog.add(message);
	}

	/**
	 * Incident is a constructor that uses varaibles like int, String and ArrayList
	 * 
	 * @param incidentId    incidentId describes the unique Id of a particular
	 *                      incident.
	 * @param state         state descibes the current state for a paticular type of
	 *                      incident in the IncidentState Class.
	 * @param title         title provided by the user on creatin of an incident
	 *                      reoprt.
	 * @param caller        name of the user described by the user on creation of
	 *                      the incident.
	 * @param reopenCount   reopenCount refers to the number of times an incident
	 *                      has been reopened.
	 * @param owner         owner refers to the persn who owns the incident and is
	 *                      responsible for its resolution.
	 * @param statusDetails stausDetails are the details about the status of the
	 *                      incident.
	 * @param incidentLog   is an arrayList that consists of the all the details of
	 *                      the incident state.
	 */
	public Incident(int incidentId, String state, String title, String caller, int reopenCount, String owner,
			String statusDetails, ArrayList<String> incidentLog) {

		if (!checkForConstraints(incidentId, state, title, caller, reopenCount, owner, statusDetails, incidentLog)) {
			throw new IllegalArgumentException("Incident cannot be created.");
		}

		if (incidentId > Incident.counter)
			setCounter(incidentId + 1);

		setId(incidentId);
		setState(state);
		setTitle(title);
		setCaller(caller);
		setReopenCount(reopenCount);
		setOwner(owner);
		setStatusDetails(statusDetails);
		for (int i = 0; i < incidentLog.size(); i++) {
			addMessageToIncidentLog(incidentLog.get(i));
		}

	}

	private boolean checkState(String str) {
		switch (str) {
		case ON_HOLD_NAME:
			return true;
		case RESOLVED_NAME:
			return true;
		case CANCELED_NAME:
			return true;
		case NEW_NAME:
			return true;
		case IN_PROGRESS_NAME:
			return true;
		default:
			return false;
		}
	}

	private boolean checkForConstraints(int incidentId, String state, String title, String caller, int reopenCount,
			String owner, String statusDetails, ArrayList<String> incidentLog) {

		if (incidentLog == null || incidentLog.size() == 0 || incidentId <= 0 || reopenCount < 0 || state == null
				|| "".equals(state))
			return false;

		if (title == null || "".equals(title) || caller == null || "".equals(caller) || owner == null
				|| "".equals(owner) || !checkState(state))
			return false;

		if (state == Incident.ON_HOLD_NAME && !(statusDetails == Incident.HOLD_AWAITING_CALLER
				|| statusDetails == Incident.HOLD_AWAITING_CHANGE || statusDetails == Incident.HOLD_AWAITING_VENDOR)) {
			return false;
		}

		if (state == Incident.RESOLVED_NAME && !(statusDetails == Incident.RESOLUTION_CALLER_CLOSED
				|| statusDetails == Incident.RESOLUTION_PERMANENTLY_SOLVED
				|| statusDetails == Incident.RESOLUTION_WORKAROUND)) {
			return false;
		}

		if (state == Incident.CANCELED_NAME && !(statusDetails == Incident.CANCELLATION_CALLER_CANCELLED
				|| statusDetails == Incident.CANCELLATION_DUPLICATE
				|| statusDetails == Incident.CANCELLATION_NOT_AN_INCIDENT
				|| statusDetails == CANCELLATION_UNNECESSARY)) {
			return false;
		}

		if ((state == Incident.NEW_NAME || state == Incident.IN_PROGRESS_NAME) && statusDetails != Incident.NO_STATUS) {
			return false;
		}

		if ((state == Incident.RESOLVED_NAME || state == Incident.IN_PROGRESS_NAME || state == Incident.ON_HOLD_NAME)
				&& owner == Incident.UNOWNED) {
			return false;
		}

		return !((state == Incident.NEW_NAME || state == Incident.CANCELED_NAME) && owner != Incident.UNOWNED);

	}

	/**
	 * @return the incidentId
	 */
	public int getId() {
		return incidentId;
	}

	/**
	 * @param incidentId the incidentId to set
	 */
	private void setId(int incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the incidentId
	 */
	public String getState() {
		return currentState.getStateName();
	}

	/**
	 * @param incidentId the incidentId to set
	 */
	private void setState(String state) {
		switch (state) {
		case Incident.NEW_NAME:
			currentState = newState;
			break;
		case Incident.CANCELED_NAME:
			currentState = canceledState;
			break;
		case Incident.ON_HOLD_NAME:
			currentState = onHoldState;
			break;
		case Incident.RESOLVED_NAME:
			currentState = resolvedState;
			break;
		case Incident.IN_PROGRESS_NAME:
			currentState = inProgressState;
			break;
		}

	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	private void setTitle(String title) {
		this.title = title;
	}

	/**
	 * getCaller is a gtter mehtod for the caller variable.
	 * 
	 * @return the caller
	 */
	public String getCaller() {
		return caller;
	}

	/**
	 * setCaller is a setter method for the caller variable
	 * 
	 * @param caller the caller to set
	 */
	private void setCaller(String caller) {
		this.caller = caller;
	}

	/**
	 * getReopenCount is a getter method for the reopenCount variable.
	 * 
	 * @return the reopenCount
	 */
	public int getReopenCount() {
		return reopenCount;
	}

	/**
	 * setReopenCount is a setter method for the reopenCount variable.
	 * 
	 * @param reopenCount the reopenCount to set
	 */
	private void setReopenCount(int reopenCount) {
		this.reopenCount = reopenCount;
	}

	/**
	 * getOwner method returns the owner wh is the person responsible for resolving
	 * the reported incident.
	 * 
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * setOwner is a setter method for the owner variable.
	 * 
	 * @param owner the owner to set
	 */
	private void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * getStatusDetails is used to return the details of the status of a reported
	 * incident
	 * 
	 * @return the statusDetails
	 */
	public String getStatusDetails() {
		return statusDetails;
	}

	/**
	 * 
	 * @param statusDetails the statusDetails to set
	 */
	private void setStatusDetails(String statusDetails) {
		this.statusDetails = statusDetails;
	}

	/**
	 * 
	 * @param incidentMessages
	 * @return
	 */
	public int addMessageToIncidentLog(String message) {
		incidentLog.add(message);
		return incidentLog.size();
	}

	public static void incrementCounter() {
		counter++;
	}

	public static void setCounter(int counterValue) {
		counter = counterValue;
	}

	public String getIncidentLogMessages() {
		String str = "";
		for (int i = 0; i < incidentLog.size(); i++) {
			str += "- " + incidentLog.get(i) + "\n";
		}
		return str;
	}

	public String toString() {
		String str = "";
		str += "* " + incidentId + "," + getState() + "," + getTitle() + "," + getCaller() + "," + getReopenCount()
				+ "," + getOwner() + "," + getStatusDetails();
		str += "\n";
		for (int i = 0; i < incidentLog.size(); i++) {
			str += "- " + incidentLog.get(i) + "\n";
		}
		return str;
	}

	public void update(Command command) {
		currentState.updateState(command);

	}

	/**
	 * Interface for states in the Incident State Pattern. All concrete incident
	 * states must implement the IncidentState interface. The IncidentState
	 * interface should be a private interface of the Incident class.
	 *
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
	 */
	private interface IncidentState {
		/**
		 * Update the Incident based on the given Command. An
		 * UnsupportedOperationException is thrown if the Command is not a valid action
		 * for the given state.
		 * 
		 * @param command Command describing the action that will update the Incident's
		 *                state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 *                                       for the given state.
		 */
		void updateState(Command command);

		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		String getStateName();

	}

	/**
	 * NewState class is an inner class of the IncidentState class
	 * 
	 * @author varan
	 *
	 */
	public class NewState implements IncidentState {

		private NewState() {

		}

		public void updateState(Command command) {

			switch (command.getCommand()) {
			case ASSIGN:
				currentState = inProgressState;
				setOwner(command.getCommandInformation());
				incidentLog.add(command.getCommandMessage());
				break;
			case CANCEL:
				currentState = canceledState;
				setOwner("Unowned");
				if(command.getCommandInformation().equals("Caller Canceled"))
					command.setCommandInformation("Caller Cancelled");
				setStatusDetails(command.getCommandInformation());
				incidentLog.add(command.getCommandMessage());
				break;
			default:
				throw new UnsupportedOperationException();

			}
		}

		public String getStateName() {
			return Incident.NEW_NAME;

		}
	}

	/**
	 * ResolvedState class is an inner class of the IncidentState class
	 * 
	 * @author varan
	 *
	 */
	public class ResolvedState implements IncidentState {

		private ResolvedState() {

		}

		public void updateState(Command command) {
			switch (command.getCommand()) {
			case REOPEN:
				currentState = inProgressState;
				setReopenCount(++reopenCount);
				setStatusDetails(Incident.NO_STATUS);
				incidentLog.add(command.getCommandMessage());
				break;
			case CANCEL:
				currentState = canceledState;
				setOwner("Unowned");
				if(command.getCommandInformation().equals("Caller Canceled"))
					command.setCommandInformation("Caller Cancelled");
				setStatusDetails(command.getCommandInformation());
				incidentLog.add(command.getCommandMessage());
				break;
			default:
				throw new UnsupportedOperationException();

			}
		}

		public String getStateName() {
			return Incident.RESOLVED_NAME;

		}
	}

	/**
	 * OnHoldState class is an inner class of the IncidentState class
	 * 
	 * @author varan
	 *
	 */
	public class OnHoldState implements IncidentState {

		private OnHoldState() {

		}

		public void updateState(Command command) {
			switch (command.getCommand()) {
			case INVESTIGATE:
				currentState = inProgressState;
				setStatusDetails("No Status");
				incidentLog.add(command.getCommandMessage());
				break;
			default:
				throw new UnsupportedOperationException();

			}
		}

		public String getStateName() {
			return Incident.ON_HOLD_NAME;

		}
	}

	/**
	 * CanceledState class is an inner class of the IncidentState class
	 * 
	 * @author Varan J Mehta
	 * 
	 */
	public class CanceledState implements IncidentState {

		private CanceledState() {

		}

		public void updateState(Command command) {
			throw new UnsupportedOperationException();
		}

		public String getStateName() {
			return Incident.CANCELED_NAME;

		}
	}

	/**
	 * InProgressState class is an inner class of the IncidentState class
	 * 
	 * @author varan
	 *
	 */
	public class InProgressState implements IncidentState {

		private InProgressState() {

		}

		public void updateState(Command command) {
			switch (command.getCommand()) {
			case HOLD:
				currentState = onHoldState;
				setStatusDetails(command.getCommandInformation());
				incidentLog.add(command.getCommandMessage());
				break;
			case CANCEL:
				currentState = canceledState;
				setOwner("Unowned");
				if(command.getCommandInformation().equals("Caller Canceled"))
					command.setCommandInformation("Caller Cancelled");
				setStatusDetails(command.getCommandInformation());
				incidentLog.add(command.getCommandMessage());
				break;
			case RESOLVE:
				currentState = resolvedState;
				setStatusDetails(command.getCommandInformation());
				incidentLog.add(command.getCommandMessage());
				break;
			case ASSIGN:
				currentState = inProgressState;
				setOwner(command.getCommandInformation());
				incidentLog.add(command.getCommandMessage());
				break;
			default:
				throw new UnsupportedOperationException();

			}
		}

		public String getStateName() {
			return Incident.IN_PROGRESS_NAME;

		}
	}

}
