package tw.edu.chit.model;

import java.util.Date;

/**
 * TimeOffUpload entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TimeOffUpload extends TimeOffUploadBase implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String teachId;
	private Integer dtimeOid;
	private Date teachDate;
	private Short teachWeek;
	private Date uploadTime;
	private String modifierId;

	// Constructors

	/** default constructor */
	public TimeOffUpload() {
	}

	/** minimal constructor */
	public TimeOffUpload(String teachId, Integer dtimeOid, Date teachDate,
			Short teachWeek, Date uploadTime) {
		this.teachId = teachId;
		this.dtimeOid = dtimeOid;
		this.teachDate = teachDate;
		this.teachWeek = teachWeek;
		this.uploadTime = uploadTime;
	}

	/** full constructor */
	public TimeOffUpload(String teachId, Integer dtimeOid, Date teachDate,
			Short teachWeek, Date uploadTime, String modifierId) {
		this.teachId = teachId;
		this.dtimeOid = dtimeOid;
		this.teachDate = teachDate;
		this.teachWeek = teachWeek;
		this.uploadTime = uploadTime;
		this.modifierId = modifierId;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getTeachId() {
		return this.teachId;
	}

	public void setTeachId(String teachId) {
		this.teachId = teachId;
	}

	public Integer getDtimeOid() {
		return this.dtimeOid;
	}

	public void setDtimeOid(Integer dtimeOid) {
		this.dtimeOid = dtimeOid;
	}

	public Date getTeachDate() {
		return this.teachDate;
	}

	public void setTeachDate(Date teachDate) {
		this.teachDate = teachDate;
	}

	public Short getTeachWeek() {
		return this.teachWeek;
	}

	public void setTeachWeek(Short teachWeek) {
		this.teachWeek = teachWeek;
	}

	public Date getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getModifierId() {
		return this.modifierId;
	}

	public void setModifierId(String modifierId) {
		this.modifierId = modifierId;
	}

}