package tw.edu.chit.model;

import java.util.Date;

public class AmsMeetingAskLeaveBase {
	private String emplName;
	private String askleaveName;
	private String meetingName;
	private Date meetingDate;
	
	public String getEmplName() {
		return emplName;
	}
	public void setEmplName(String emplName) {
		this.emplName = emplName;
	}
	public String getAskleaveName() {
		return askleaveName;
	}
	public void setAskleaveName(String askleaveName) {
		this.askleaveName = askleaveName;
	}
	public String getMeetingName() {
		return meetingName;
	}
	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}
	public Date getMeetingDate() {
		return meetingDate;
	}
	public void setMeetingDate(Date meetingDate) {
		this.meetingDate = meetingDate;
	}
	
	
}
