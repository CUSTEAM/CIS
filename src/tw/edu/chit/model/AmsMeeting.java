package tw.edu.chit.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AmsMeeting entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class AmsMeeting implements java.io.Serializable {

	private static final long serialVersionUID = 986371025913590813L;

	private Integer oid;
	private String schoolYear;
	private String schoolTerm;
	private Date meetingDate;
	private Integer startNode;
	private Integer endNode;
	private String name;
	private String emplType;
	private Integer base;
	private Date lastModified;

	private Set<AmsMeetingData> meetingData = new HashSet<AmsMeetingData>();

	public AmsMeeting() {
	}

	public AmsMeeting(Integer oid, String schoolYear, String schoolTerm,
			Integer startNode, Integer endNode, String name, String emplType,
			Integer base) {
		this.oid = oid;
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.startNode = startNode;
		this.endNode = endNode;
		this.name = name;
		this.emplType = emplType;
		this.base = base;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getSchoolYear() {
		return this.schoolYear;
	}

	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}

	public String getSchoolTerm() {
		return schoolTerm;
	}

	public void setSchoolTerm(String schoolTerm) {
		this.schoolTerm = schoolTerm;
	}

	public Date getMeetingDate() {
		return this.meetingDate;
	}

	public void setMeetingDate(Date meetingDate) {
		this.meetingDate = meetingDate;
	}

	public Integer getStartNode() {
		return this.startNode;
	}

	public void setStartNode(Integer startNode) {
		this.startNode = startNode;
	}

	public Integer getEndNode() {
		return this.endNode;
	}

	public void setEndNode(Integer endNode) {
		this.endNode = endNode;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmplType() {
		return this.emplType;
	}

	public void setEmplType(String emplType) {
		this.emplType = emplType;
	}

	public Integer getBase() {
		return this.base;
	}

	public void setBase(Integer base) {
		this.base = base;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Set<AmsMeetingData> getMeetingData() {
		return meetingData;
	}

	public void setMeetingData(Set<AmsMeetingData> meetingData) {
		this.meetingData = meetingData;
	}

}