package tw.edu.chit.model;

import java.util.Date;

/**
 * BankFeePay entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class BankFeePay implements java.io.Serializable {

	private static final long serialVersionUID = 4797703023538019752L;

	private Integer oid;
	private String schoolYear;
	private String schoolTerm;
	private String accountNo;
	private Date payDate;
	private String studentNo;
	private Date lastModified;

	public BankFeePay() {
	}

	public BankFeePay(Integer oid, String schoolYear, String schoolTerm,
			String accountNo, Date payDate, Date lastModified) {
		this.oid = oid;
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.accountNo = accountNo;
		this.payDate = payDate;
		this.lastModified = lastModified;
	}

	public BankFeePay(Integer oid, String schoolYear, String schoolTerm,
			String accountNo, Date payDate, String studentNo, Date lastModified) {
		this.oid = oid;
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.accountNo = accountNo;
		this.payDate = payDate;
		this.studentNo = studentNo;
		this.lastModified = lastModified;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getSchoolYear() {
		return this.schoolYear;
	}

	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}

	public String getSchoolTerm() {
		return this.schoolTerm;
	}

	public void setSchoolTerm(String schoolTerm) {
		this.schoolTerm = schoolTerm;
	}

	public String getAccountNo() {
		return this.accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Date getPayDate() {
		return this.payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getStudentNo() {
		return this.studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

}