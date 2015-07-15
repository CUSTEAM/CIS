package tw.edu.chit.model;

import java.util.Date;

/**
 * AmsPersonalVacation entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AmsPersonalVacation implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String vtype;
	private String idno;
	private Integer vyear;
	private Float days;
	private Float remain;
	private Date validFrom;
	private Date validTo;

	// Constructors

	/** default constructor */
	public AmsPersonalVacation() {
	}

	/** full constructor */
	public AmsPersonalVacation(String vtype, String idno, Integer vyear,
			Float days, Float remain, Date validFrom, Date validTo) {
		this.vtype = vtype;
		this.idno = idno;
		this.vyear = vyear;
		this.days = days;
		this.remain = remain;
		this.validFrom = validFrom;
		this.validTo = validTo;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getVtype() {
		return this.vtype;
	}

	public void setVtype(String vtype) {
		this.vtype = vtype;
	}

	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public Integer getVyear() {
		return this.vyear;
	}

	public void setVyear(Integer vyear) {
		this.vyear = vyear;
	}

	public Float getDays() {
		return this.days;
	}

	public void setDays(Float days) {
		this.days = days;
	}

	public Float getRemain() {
		return this.remain;
	}

	public void setRemain(Float remain) {
		this.remain = remain;
	}

	public Date getValidFrom() {
		return this.validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return this.validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

}