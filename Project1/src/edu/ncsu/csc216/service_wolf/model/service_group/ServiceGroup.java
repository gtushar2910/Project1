package edu.ncsu.csc216.service_wolf.model.service_group;

import java.util.ArrayList;
import java.util.Comparator;

import edu.ncsu.csc216.service_wolf.model.command.Command;
import edu.ncsu.csc216.service_wolf.model.incident.Incident;

/**
 * ServiceGroup Class has a serviceGroupName string variable and a list of
 * incidents
 * 
 * @author Varan J Mehta
 *
 */
public class ServiceGroup {
	/** serviceGroupName is a private variable that */
	private String serviceGroupName;
	/**
	 *  
	 */
	private ArrayList<Incident> incidents;

	/**
	 * ServiceGroup
	 * 
	 * @param serviceGroupName
	 */
	public ServiceGroup(String serviceGroupName) {
		if (serviceGroupName == null || "".equals(serviceGroupName)) {
			throw new IllegalArgumentException("Invalid service group name.");
		}
		this.serviceGroupName = serviceGroupName;
		incidents = new ArrayList<Incident>();
	}

	/**
	 * setIncidentCounter method
	 */
	public void setIncidentCounter() {
		int max = 0;

		for (int i = 0; i < incidents.size(); i++) {
			if (max < incidents.get(i).getId()) {
				max = incidents.get(i).getId();
			}
		}
		Incident.counter = max + 1;
	}

	/**
	 * @param serviceGroupName the serviceGroupName to set
	 */
	public void setServiceGroupName(String serviceGroupName) {
		if (serviceGroupName == null || "".equals(serviceGroupName))
			throw new IllegalArgumentException("ServiceGroup cannot have null name.");
		this.serviceGroupName = serviceGroupName;
	}

	/**
	 * @return the serviceGroupName
	 */
	public String getServiceGroupName() {
		return serviceGroupName;
	}

	public void addIncident(Incident incident) {

		for (int i = 0; i < incidents.size(); i++) {
			if (incident.getId() == incidents.get(i).getId()) {
				throw new IllegalArgumentException("Incident cannot be created..");
			}
		}
		incidents.add(incident);
		incidents.sort(new IncidentComparator());
	}

	public ArrayList<Incident> getIncidents() {
		return incidents;

	}

	public Incident getIncidentById(int incidentId) {
		for (int i = 0; i < incidents.size(); i++) {
			if (incidents.get(i).getId() == incidentId) {
				return incidents.get(i);
			}
		}
		return null;

	}

	public void executeCommand(int incidentId, Command command) {
		for (int i = 0; i < incidents.size(); i++) {
			if (incidents.get(i).getId() == incidentId) {
				incidents.get(i).update(command);
			}
		}
	}

	public void deleteIncidentById(int incidentId) {
		for (int i = 0; i < incidents.size(); i++) {
			if (incidents.get(i).getId() == incidentId) {
				incidents.remove(i);
			}
		}
	}

}

class IncidentComparator implements Comparator<Incident> {

	@Override
	public int compare(Incident o1, Incident o2) {
		// TODO Auto-generated method stub
		return o1.getId() - o2.getId();
	}

}
