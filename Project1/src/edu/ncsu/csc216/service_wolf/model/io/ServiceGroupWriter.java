package edu.ncsu.csc216.service_wolf.model.io;

import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ncsu.csc216.service_wolf.model.incident.Incident;
import edu.ncsu.csc216.service_wolf.model.service_group.ServiceGroup;

/**
 * @author Varan J Mehta
 *
 */
public class ServiceGroupWriter {
	 
	public ServiceGroupWriter() {
		//Constructor for creating the object of ServiceGroupWriter
		//As we use static method, right now no initialization done.
	} 
	 
	public static void writeServiceGroupsToFile(String fileName, ArrayList<ServiceGroup> list) {
		try {
			 
			PrintWriter pw = new PrintWriter(fileName);
			for(int i=0;i<list.size();i++) {
				pw.println("# " + list.get(i).getServiceGroupName());
				for(int j=0; j<list.get(i).getIncidents().size(); j++) {
					Incident in = list.get(i).getIncidents().get(j);
					pw.print(in.toString()); 
					//pw.println(in.getIncidentLogMessages());
				}
			
			pw.close();
		}catch(Exception e) {
			throw new IllegalArgumentException("Unable to save file.");
		}
	}
}
