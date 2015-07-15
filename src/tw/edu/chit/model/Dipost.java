package tw.edu.chit.model;

import java.util.Date;

/**
 * Dipost entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Dipost extends DipostBase implements java.io.Serializable {

	private static final long serialVersionUID = -1422399195677583541L;

	private Integer oid;
	private String studentNo;
	private String officeNo;
	private String acctNo;
	private Integer money;
	private String kind;
	private String modifier;
	private Date lastModified;
	private String schoolYear;
	private String schoolTerm;

	public Dipost() {
	}

	public Dipost(String studentNo, Date lastModified) {
		this.studentNo = studentNo;
		this.lastModified = lastModified;
	}

	public Dipost(String studentNo, String officeNo, String acctNo,
			Integer money, String kind, Date lastModified, String schoolYear,
			String schoolTerm) {
		this.studentNo = studentNo;
		this.officeNo = officeNo;
		this.acctNo = acctNo;
		this.money = money;
		this.kind = kind;
		this.lastModified = lastModified;
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getStudentNo() {
		return this.studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getOfficeNo() {
		return this.officeNo;
	}

	public void setOfficeNo(String officeNo) {
		this.officeNo = officeNo;
	}

	public String getAcctNo() {
		return this.acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public Integer getMoney() {
		return this.money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public String getKind() {
		return this.kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
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

}