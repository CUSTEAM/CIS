package tw.edu.chit.model;

/**
 * DtimeClass generated by MyEclipse - Hibernate Tools
 */

public class DtimeClass implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -8393224877274279381L;
	private Integer oid;
	private Integer dtimeOid;
	private String begin;
	private String end;
	private String place;
	private Integer week;

	// Constructors

	/** default constructor */
	public DtimeClass() {
	}

	/** minimal constructor */
	public DtimeClass(Integer dtimeOid) {
		this.dtimeOid = dtimeOid;
	}

	/** full constructor */
	public DtimeClass(Integer dtimeOid, String begin, String end, String place,
			Integer week) {
		this.dtimeOid = dtimeOid;
		this.begin = begin;
		this.end = end;
		this.place = place;
		this.week = week;
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

	public String getBegin() {
		return this.begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return this.end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Integer getWeek() {
		return this.week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

}