package tw.edu.chit.model;

import java.util.Date;

/**
 * AmsWorkdateId entity. @author MyEclipse Persistence Tools
 */

public class AmsWorkdateId implements java.io.Serializable {

	// Fields

	private String idno;
	private Date wdate;

	// Constructors

	/** default constructor */
	public AmsWorkdateId() {
	}

	/** full constructor */
	public AmsWorkdateId(String idno, Date wdate) {
		this.idno = idno;
		this.wdate = wdate;
	}

	// Property accessors

	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public Date getWdate() {
		return this.wdate;
	}

	public void setWdate(Date wdate) {
		this.wdate = wdate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AmsWorkdateId))
			return false;
		AmsWorkdateId castOther = (AmsWorkdateId) other;

		return ((this.getIdno() == castOther.getIdno()) || (this.getIdno() != null
				&& castOther.getIdno() != null && this.getIdno().equals(
				castOther.getIdno())))
				&& ((this.getWdate() == castOther.getWdate()) || (this
						.getWdate() != null
						&& castOther.getWdate() != null && this.getWdate()
						.equals(castOther.getWdate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getIdno() == null ? 0 : this.getIdno().hashCode());
		result = 37 * result
				+ (getWdate() == null ? 0 : this.getWdate().hashCode());
		return result;
	}

}