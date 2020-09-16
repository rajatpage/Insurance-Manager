package newLearning;

import java.util.HashSet;

import jade.content.Concept;

/*
 * After changing the architecture, it is used to send user information between UserInterfaceAgent and InsuranceManager
 */
public class InsuranceInfo implements Concept{

	private Insuarance meeting;
	
	private HashSet<String> attendees;
	
	public Insuarance getMeeting() {
		return meeting;
	}

	public void setMeeting(Insuarance meeting) {
		this.meeting = meeting;
	}

	public HashSet<String> getAttendees() {
		return attendees;
	}

	public void setAttendees(HashSet<String> attendees) {
		this.attendees = attendees;
	}
}
