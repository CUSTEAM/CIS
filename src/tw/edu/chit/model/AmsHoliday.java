package tw.edu.chit.model;

import java.util.Date;

/**
 * AmsHoliday entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class AmsHoliday implements java.io.Serializable {

	private static final long serialVersionUID = -6390454841282916516L;

	private Integer oid;
	private Date date;
	private String name;
	private String type;
	private Date startTime;
	private Date endTime;
	private String emplType;

	public AmsHoliday() {
	}

	public AmsHoliday(Date date, String name, String type) {
		this.date = date;
		this.name = name;
		this.type = type;
	}

	public AmsHoliday(Date date, String name, String type, Date startTime,
			Date endTime, String emplType) {
		this.date = date;
		this.name = name;
		this.type = type;
		this.startTime = startTime;
		this.endTime = endTime;
		this.emplType = emplType;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getEmplType() {
		return this.emplType;
	}

	public void setEmplType(String emplType) {
		this.emplType = emplType;
	}

}