package tw.edu.chit.model;

import java.util.Date;

/**
 * Desd entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Desd extends tw.edu.chit.model.DesdBase implements
		java.io.Serializable {

	// Fields

	private Integer oid;
	private String departClass;
	private String studentNo;
	private Date ddate;
	private String no;
	private String reason;
	private String kind1;
	private Short cnt1;
	private String kind2;
	private Short cnt2;
	private String actIllegal;

	// Constructors

	/** default constructor */
	public Desd() {
	}

	/** minimal constructor */
	public Desd(String departClass, String studentNo, Date ddate) {
		this.departClass = departClass;
		this.studentNo = studentNo;
		this.ddate = ddate;
	}

	/** full constructor */
	public Desd(String departClass, String studentNo, Date ddate, String no,
			String reason, String kind1, Short cnt1, String kind2, Short cnt2,
			String actIllegal) {
		this.departClass = departClass;
		this.studentNo = studentNo;
		this.ddate = ddate;
		this.no = no;
		this.reason = reason;
		this.kind1 = kind1;
		this.cnt1 = cnt1;
		this.kind2 = kind2;
		this.cnt2 = cnt2;
		this.actIllegal = actIllegal;
	}
	/*
	public Desd(String departClass, String studentNo, Date ddate, String no,
			String reason, String kind1, Short cnt1, String kind2, Short cnt2,
			String actIllegal) {
		this.departClass = departClass;
		this.studentNo = studentNo;
		this.ddate = ddate;
		this.no = no;
		this.reason = reason;
		this.kind1 = kind1;
		this.cnt1 = cnt1;
		this.kind2 = kind2;
		this.cnt2 = cnt2;
		this.actIllegal = actIllegal;
	}
	*/
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

	public Date getDdate() {
		return this.ddate;
	}

	public void setDdate(Date ddate) {
		this.ddate = ddate;
	}

	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getKind1() {
		return this.kind1;
	}

	public void setKind1(String kind1) {
		this.kind1 = kind1;
	}

	public Short getCnt1() {
		return this.cnt1;
	}

	public void setCnt1(Short cnt1) {
		this.cnt1 = cnt1;
	}

	public String getKind2() {
		return this.kind2;
	}

	public void setKind2(String kind2) {
		this.kind2 = kind2;
	}

	public Short getCnt2() {
		return this.cnt2;
	}

	public void setCnt2(Short cnt2) {
		this.cnt2 = cnt2;
	}

	public String getActIllegal() {
		return this.actIllegal;
	}

	public void setActIllegal(String actIllegal) {
		this.actIllegal = actIllegal;
	}

}