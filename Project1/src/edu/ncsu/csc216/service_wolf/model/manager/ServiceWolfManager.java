package edu.ncsu.csc216.service_wolf.model.manager;

import java.util.ArrayList;
import java.util.Comparator;

import edu.ncsu.csc216.service_wolf.model.command.Command;
import edu.ncsu.csc216.service_wolf.model.incident.Incident;
import edu.ncsu.csc216.service_wolf.model.io.ServiceGroupWriter;
import edu.ncsu.csc216.service_wolf.model.io.ServiceGroupsReader;
import edu.ncsu.csc216.service_wolf.model.service_group.ServiceGroup;

/**
 * @author varan
 *
 */
public class ServiceWolfManager {

	private static ServiceWolfManager singleton;

	private ArrayList<ServiceGroup> serviceGroups;

	private ServiceGroup currentServiceGroup;

	private ServiceWolfManager() {
		serviceGroups = new ArrayList<ServiceGroup>();
		currentServiceGroup = null;
	}

	public static ServiceWolfManager getInstance() {
		if (singleton == null) {
			singleton = new ServiceWolfManager();
		}
		return singleton;
	}

	public void saveToFile(String fileName) {
		ServiceGroupWriter.writeServiceGroupsToFile(fileName, serviceGroups);
	}

	@SuppressWarnings("unchecked")
	public void loadFromFile(String fileName) {
		ArrayList<ServiceGroup> groups = ServiceGroupsReader.readServiceGroupsFile(fileName);
		if (groups != null) {
			for (int i = 0; i < groups.size(); i++) {
				if (groups.get(i).getIncidents().size() == 0)
					throw new IllegalArgumentException("Service Group does not contain incidents");
				serviceGroups.add(groups.get(i));
			}
			serviceGroups.sort(new NameComparator());
			currentServiceGroup = groups.get(0);
		}
	}

	public String[][] getIncidentsAsArray() {

		if (currentServiceGroup == null && currentServiceGroup.getIncidents().size() == 0) {
			throw new IllegalArgumentException(
					"Either Current Service Group is Null or It does not contain any incidents");
		}
		String str[][] = new String[currentServiceGroup.getIncidents().size()][4];
		for (int i = 0; i < currentServiceGroup.getIncidents().size(); i++) {
			if (currentServiceGroup.getIncidents().get(i) != null) {
				str[i][0] = String.valueOf(currentServiceGroup.getIncidents().get(i).getId());
				str[i][1] = currentServiceGroup.getIncidents().get(i).getState();
				str[i][2] = currentServiceGroup.getIncidents().get(i).getTitle();
				str[i][3] = currentServiceGroup.getIncidents().get(i).getStatusDetails();
			}
		}
		return str;
	}

	public Incident getIncidentById(int incidentid) {
		if (currentServiceGroup == null && currentServiceGroup.getIncidents().size() == 0) {
			throw new IllegalArgumentException(
					"Either Current Service Group is Null or It does not contain any incidents");
		}
		return currentServiceGroup.getIncidentById(incidentid);
	}

	public void executeCommand(int incidentid, Command command) {
		if (currentServiceGroup == null && currentServiceGroup.getIncidents().size() == 0) {
			throw new IllegalArgumentException(
					"Either Current Service Group is Null or It does not contain any incidents");
		}
		currentServiceGroup.executeCommand(incidentid, command);
	}

	public void deleteIncidentById(int incidentid) {
		if (currentServiceGroup == null && currentServiceGroup.getIncidents().size() == 0) {
			throw new IllegalArgumentException(
					"Either Current Service Group is Null or It does not contain any incidents");
		}
		currentServiceGroup.deleteIncidentById(incidentid);
	}

	public void addIncidentToServiceGroup(String title, String caller, String message) {
		if (currentServiceGroup == null)
			throw new IllegalArgumentException("No service group selected.");
		currentServiceGroup.addIncident(new Incident(title, caller, message));
	}

	public void loadServiceGroup(String serviceGroupName) {
		for (int i = 0; i < serviceGroups.size(); i++) {
			if (serviceGroupName.equals(serviceGroups.get(i).getServiceGroupName())) {
				currentServiceGroup = serviceGroups.get(i);
				currentServiceGroup.setIncidentCounter();
				return;
			}
		}
		throw new IllegalArgumentException("Can not Load Service Group");
	}

	public String getServiceGroupName() {

		if (currentServiceGroup != null)
			return currentServiceGroup.getServiceGroupName();

		return null;
	}

	public String[] getServiceGroupList() {
		String str[] = new String[serviceGroups.size()];

		for (int i = 0; i < serviceGroups.size(); i++) {
			str[i] = serviceGroups.get(i).getServiceGroupName();
		}

		return str;
	}

	public void clearServiceGroups() {
		serviceGroups = new ArrayList<ServiceGroup>();
		currentServiceGroup = null;
	}

	public void editServiceGroup(String serviceGroupName) {

		if (serviceGroupName == null || "".equals(serviceGroupName))
			throw new IllegalArgumentException("Invalid service group name.");

		if (serviceGroups.size() == 0)
			throw new IllegalArgumentException("No service group selected.");

		if (currentServiceGroup == null)
			return;

		for (int i = 0; i < serviceGroups.size(); i++) {
			if (serviceGroupName.equals(serviceGroups.get(i).getServiceGroupName())) {
				throw new IllegalArgumentException("Invalid service group name.");
			}
		}

		ServiceGroup temp = currentServiceGroup;
		temp.setServiceGroupName(serviceGroupName);

		deleteServiceGroup();
		addServiceGroupToListByName(temp);
		loadServiceGroup(currentServiceGroup.getServiceGroupName());

	}

	@SuppressWarnings("unused")
	private void addServiceGroupToListByName(ServiceGroup servicegroup) {
		if (servicegroup == null)
			throw new IllegalArgumentException("Invalid service group.");
		if (servicegroup.getIncidents().size() == 0)
			throw new IllegalArgumentException("Service Group does not contain incidents");

		serviceGroups.add(servicegroup);
		serviceGroups.sort(new NameComparator());
		currentServiceGroup = servicegroup; // serviceGroups.get(0);
	}

	@SuppressWarnings("unchecked")
	public void addServiceGroup(String serviceGroupName) {

		if (serviceGroupName == null || "".equals(serviceGroupName))
			throw new IllegalArgumentException("Invalid service group name.");

		ServiceGroup sg = new ServiceGroup(serviceGroupName);

		for (int i = 0; i < serviceGroups.size(); i++) {
			if (sg.getServiceGroupName().equals(serviceGroups.get(i).getServiceGroupName())) {
				throw new IllegalArgumentException("Invalid service group name.");
			}
		}
		serviceGroups.add(sg);
		serviceGroups.sort(new NameComparator());
		loadServiceGroup(serviceGroupName);
	}

	@SuppressWarnings("unused")
	private void checkDuplicateServiceName(String serviceGroupName) {
		// This method is checking for Duplicate ServiceGroupName.
		// As this functionality us already implemented elsewhere, it is not required
		// here
	}

	public void deleteServiceGroup() {
		if (currentServiceGroup == null) {
			throw new IllegalArgumentException("No service group selected.");
		}

		if (serviceGroups.size() == 0)
			throw new IllegalArgumentException("No service group selected.");

		serviceGroups.remove(currentServiceGroup);

		if (serviceGroups.size() >= 1)
			currentServiceGroup = serviceGroups.get(0);
		else
			currentServiceGroup = null;
	}

	protected void resetManager() {
		singleton = null;
	}
}

@SuppressWarnings("rawtypes")
class NameComparator implements Comparator {
	public int compare(Object o1, Object o2) {
		ServiceGroup s1 = (ServiceGroup) o1;
		ServiceGroup s2 = (ServiceGroup) o2;

		return s1.getServiceGroupName().compareTo(s2.getServiceGroupName());
	}
}
