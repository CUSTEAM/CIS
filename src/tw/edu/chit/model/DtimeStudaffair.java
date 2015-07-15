package tw.edu.chit.model;

import java.util.Date;

/**
 * DtimeStudaffair entity. @author MyEclipse Persistence Tools
 */

public class DtimeStudaffair implements java.io.Serializable {

	// Fields

	private Integer oid;
	private Integer dtimeOid;
	private Short isRollCall;
	private Date tdate;
	private String modifierId;

	// Constructors

	/** default constructor */
	public DtimeStudaffair() {
	}

	/** minimal constructor */
	public DtimeStudaffair(Integer dtimeOid, Date tdate) {
		this.dtimeOid = dtimeOid;
		this.tdate = tdate;
	}

	/** full constructor */
	public DtimeStudaffair(Integer dtimeOid, Short isRollCall, Date tdate,
			String modifierId) {
		this.dtimeOid = dtimeOid;
		this.isRollCall = isRollCall;
		this.tdate = tdate;
		this.modifierId = modifierId;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getDtimeOid() {
		return this.dtimeOid;
	}

	public void setDtimeOid(Integer dtimeOid) {
		this.dtimeOid = dtimeOid;
	}

	public Short getIsRollCall() {
		return this.isRollCall;
	}

	public void setIsRollCall(Short isRollCall) {
		this.isRollCall = isRollCall;
	}

	public Date getTdate() {
		return this.tdate;
	}

	public void setTdate(Date tdate) {
		this.tdate = tdate;
	}

	public String getModifierId() {
		return this.modifierId;
	}

	public void setModifierId(String modifierId) {
		this.modifierId = modifierId;
	}

}