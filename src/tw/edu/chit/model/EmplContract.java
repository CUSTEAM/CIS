package tw.edu.chit.model;

import java.util.Date;

/**
 * EmplContract entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EmplContract implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String idno;
	private String contractNo;
	private Date startDate;
	private Date endDate;
	private String type;
	private String level;
	private String markup;
	private String licenseNo;
	private String extend;
	private String deptNo;
	private String partime;

	// Constructors

	/** default constructor */
	public EmplContract() {
	}

	/** minimal constructor */
	public EmplContract(String partime) {
		this.partime = partime;
	}

	/** full constructor */
	public EmplContract(String idno, String contractNo, Date startDate,
			Date endDate, String type, String level, String markup,
			String licenseNo, String extend, String deptNo, String partime) {
		this.idno = idno;
		this.contractNo = contractNo;
		this.startDate = startDate;
		this.endDate = endDate;
		this.type = type;
		this.level = level;
		this.markup = markup;
		this.licenseNo = licenseNo;
		this.extend = extend;
		this.deptNo = deptNo;
		this.partime = partime;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMarkup() {
		return this.markup;
	}

	public void setMarkup(String markup) {
		this.markup = markup;
	}

	public String getLicenseNo() {
		return this.licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getExtend() {
		return this.extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public String getDeptNo() {
		return this.deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getPartime() {
		return this.partime;
	}

	public void setPartime(String partime) {
		this.partime = partime;
	}

}