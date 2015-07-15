package tw.edu.chit.model;

import java.util.Date;

/**
 * Dept entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Dept implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String no;
	private String sname;
	private String schoolName;
	private String fname;
	private String dname;
	private String engname;
	private Date lastEditTime;
	private String lastEditUser;

	// Constructors

	/** default constructor */
	public Dept() {
	}

	/** minimal constructor */
	public Dept(String no, String engname, Date lastEditTime) {
		this.no = no;
		this.engname = engname;
		this.lastEditTime = lastEditTime;
	}

	/** full constructor */
	public Dept(String no, String sname, String schoolName, String fname,
			String dname, String engname, Date lastEditTime, String lastEditUser) {
		this.no = no;
		this.sname = sname;
		this.schoolName = schoolName;
		this.fname = fname;
		this.dname = dname;
		this.engname = engname;
		this.lastEditTime = lastEditTime;
		this.lastEditUser = lastEditUser;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getSname() {
		return this.sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getSchoolName() {
		return this.schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getDname() {
		return this.dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getEngname() {
		return this.engname;
	}

	public void setEngname(String engname) {
		this.engname = engname;
	}

	public Date getLastEditTime() {
		return this.lastEditTime;
	}

	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public String getLastEditUser() {
		return this.lastEditUser;
	}

	public void setLastEditUser(String lastEditUser) {
		this.lastEditUser = lastEditUser;
	}

}