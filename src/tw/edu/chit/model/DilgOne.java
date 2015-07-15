package tw.edu.chit.model;

import java.util.Date;

/**
 * DilgOne entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class DilgOne implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String departClass;
	private String studentNo;
	private Integer weekDay;
	private Date ddate;
	private String daynite;
	private Short period;
	private Short abs;
	private Byte noExam;

	// Constructors

	/** default constructor */
	public DilgOne() {
	}

	/** minimal constructor */
	public DilgOne(String departClass, String studentNo, Short period,
			Byte noExam) {
		this.departClass = departClass;
		this.studentNo = studentNo;
		this.period = period;
		this.noExam = noExam;
	}

	/** full constructor */
	public DilgOne(String departClass, String studentNo, Integer weekDay,
			Date ddate, String daynite, Short period, Short abs, Byte noExam) {
		this.departClass = departClass;
		this.studentNo = studentNo;
		this.weekDay = weekDay;
		this.ddate = ddate;
		this.daynite = daynite;
		this.period = period;
		this.abs = abs;
		this.noExam = noExam;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getDepartClass() {
		return this.departClass;
	}

	public void setDepartClass(String departClass) {
		this.departClass = departClass;
	}

	public String getStudentNo() {
		return this.studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public Integer getWeekDay() {
		return this.weekDay;
	}

	public void setWeekDay(Integer weekDay) {
		this.weekDay = weekDay;
	}

	public Date getDdate() {
		return this.ddate;
	}

	public void setDdate(Date ddate) {
		this.ddate = ddate;
	}

	public String getDaynite() {
		return this.daynite;
	}

	public void setDaynite(String daynite) {
		this.daynite = daynite;
	}

	public Short getPeriod() {
		return this.period;
	}

	public void setPeriod(Short period) {
		this.period = period;
	}

	public Short getAbs() {
		return this.abs;
	}

	public void setAbs(Short abs) {
		this.abs = abs;
	}

	public Byte getNoExam() {
		return this.noExam;
	}

	public void setNoExam(Byte noExam) {
		this.noExam = noExam;
	}

}