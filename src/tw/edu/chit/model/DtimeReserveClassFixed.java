package tw.edu.chit.model;

/**
 * DtimeReserveClassFixed generated by MyEclipse Persistence Tools
 */

public class DtimeReserveClassFixed implements java.io.Serializable {

	// Fields

	private Integer oid;

	private String begin;

	private String end;

	private String week;

	private String cscode;

	private String cidno;

	private String sidno;

	private String didno;

	private String grade;

	private String classNo;

	// Constructors

	/** default constructor */
	public DtimeReserveClassFixed() {
	}

	/** full constructor */
	public DtimeReserveClassFixed(String begin, String end, String week,
			String cscode, String cidno, String sidno, String didno,
			String grade, String classNo) {
		this.begin = begin;
		this.end = end;
		this.week = week;
		this.cscode = cscode;
		this.cidno = cidno;
		this.sidno = sidno;
		this.didno = didno;
		this.grade = grade;
		this.classNo = classNo;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
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

	public String getWeek() {
		return this.week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getCscode() {
		return this.cscode;
	}

	public void setCscode(String cscode) {
		this.cscode = cscode;
	}

	public String getCidno() {
		return this.cidno;
	}

	public void setCidno(String cidno) {
		this.cidno = cidno;
	}

	public String getSidno() {
		return this.sidno;
	}

	public void setSidno(String sidno) {
		this.sidno = sidno;
	}

	public String getDidno() {
		return this.didno;
	}

	public void setDidno(String didno) {
		this.didno = didno;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getClassNo() {
		return this.classNo;
	}

	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}

}