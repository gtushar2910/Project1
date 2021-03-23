package edu.ncsu.csc216.service_wolf.model.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import edu.ncsu.csc216.service_wolf.model.incident.Incident;
import edu.ncsu.csc216.service_wolf.model.service_group.ServiceGroup;

/**
 * 
 * @author Varan J Mehta
 *
 */
public class ServiceGroupsReader {

	public ServiceGroupsReader() {
		// Constructor for creating the object of ServiceGroupReader
		// As we use static method, right now no initialization done.
	}

	public static ArrayList<ServiceGroup> readServiceGroupsFile(String fileName) {
		ArrayList<ServiceGroup> serviceGroups = new ArrayList<ServiceGroup>();
		File f = new File(fileName);

		if (!f.exists()) {
			throw new IllegalArgumentException("Unable to load file.");
		}

		try {
			FileReader fileReader = new FileReader(f);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String strFile = "";
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				strFile += line + "\n";
			}
			fileReader.close();

			if (strFile.charAt(0) != '#' || strFile.charAt(1) != ' ')
				throw new IllegalArgumentException("Invalid File");

			String[] serviceTokens = strFile.split("\\r?\\n?[#]");
			ServiceGroup sg = null;

			ArrayList<String> Logs = null;
			for (int i = 1; i < serviceTokens.length; i++) {
				sg = new ServiceGroup(serviceTokens[i].substring(0, serviceTokens[i].indexOf("\n")).trim());
				String incidentTokens[] = serviceTokens[i].substring(serviceTokens[i].indexOf("\n") + 1)
						.split("\\r?\\n?[*]");
				for (int j = 1; j < incidentTokens.length; j++) {
					String incident = incidentTokens[j].substring(0, incidentTokens[j].indexOf("\n")).trim();
					String incidentLogs[] = incidentTokens[j].substring(incidentTokens[j].indexOf("\n") + 1)
							.split("\\r?\\n?[-]");
					Logs = new ArrayList<String>();
					for (int k = 1; k < incidentLogs.length; k++) {
						Logs.add(incidentLogs[k].trim());
					}
					Incident in = createIncident(incident, Logs);
					if (in != null) {
						sg.addIncident(in);
					}
				}
				serviceGroups.add(sg);
			}
			return serviceGroups;

		} catch (Exception e1) {
			//throw new IllegalArgumentException("Can not Process File");
			System.out.println("Invalid File");
			return null;
		}
	}

	private static Incident createIncident(String incident, ArrayList<String> logs) {
		String st[] = incident.split(",");
		try {
			Incident in = new Incident(Integer.parseInt(st[0]), st[1], st[2], st[3], Integer.parseInt(st[4]), st[5],
					st[6], logs);
			return in;

		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unused")
	private static ServiceGroup processServiceGroup(String serviceGroupName) {
		return null;
	}

	@SuppressWarnings("unused")
	private static Incident processIncident(String serviceGroupName) {
		return null;
	}
}
