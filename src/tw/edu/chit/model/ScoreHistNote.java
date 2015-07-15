package tw.edu.chit.model;

import java.util.Date;

/**
 * ScoreHistNote entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ScoreHistNote implements java.io.Serializable {

	// Fields

	private Integer oid;
	private Integer scoreHistOid;
	private String note;
	private Date officeDate;
	private Integer savedtimeOid;

	// Constructors

	/** default constructor */
	public ScoreHistNote() {
	}

	/** full constructor */
	public ScoreHistNote(Integer scoreHistOid, String note, Date officeDate,
			Integer savedtimeOid) {
		this.scoreHistOid = scoreHistOid;
		this.note = note;
		this.officeDate = officeDate;
		this.savedtimeOid = savedtimeOid;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getScoreHistOid() {
		return this.scoreHistOid;
	}

	public void setScoreHistOid(Integer scoreHistOid) {
		this.scoreHistOid = scoreHistOid;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getOfficeDate() {
		return this.officeDate;
	}

	public void setOfficeDate(Date officeDate) {
		this.officeDate = officeDate;
	}

	public Integer getSavedtimeOid() {
		return this.savedtimeOid;
	}

	public void setSavedtimeOid(Integer savedtimeOid) {
		this.savedtimeOid = savedtimeOid;
	}

}