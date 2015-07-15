package tw.edu.chit.model;

import java.util.Date;

/**
 * SalaryTax entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SalaryTax implements java.io.Serializable {

	// Fields

	private Integer oid;
	private Short tyear;
	private String idno;
	private Date tdate;
	private Integer totalAmt;
	private Integer hspecial;
	private Integer classTeacher;
	private Integer othersOut1;
	private Integer notax;
	private Integer shouldTax;
	private Integer realPay;
	private Integer taxPay;
	private String remark;

	// Constructors

	/** default constructor */
	public SalaryTax() {
	}

	/** full constructor */
	public SalaryTax(Short tyear, String idno, Date tdate, Integer totalAmt,
			Integer hspecial, Integer classTeacher, Integer othersOut1,
			Integer notax, Integer shouldTax, Integer realPay, Integer taxPay,
			String remark) {
		this.tyear = tyear;
		this.idno = idno;
		this.tdate = tdate;
		this.totalAmt = totalAmt;
		this.hspecial = hspecial;
		this.classTeacher = classTeacher;
		this.othersOut1 = othersOut1;
		this.notax = notax;
		this.shouldTax = shouldTax;
		this.realPay = realPay;
		this.taxPay = taxPay;
		this.remark = remark;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Short getTyear() {
		return this.tyear;
	}

	public void setTyear(Short tyear) {
		this.tyear = tyear;
	}

	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public Date getTdate() {
		return this.tdate;
	}

	public void setTdate(Date tdate) {
		this.tdate = tdate;
	}

	public Integer getTotalAmt() {
		return this.totalAmt;
	}

	public void setTotalAmt(Integer totalAmt) {
		this.totalAmt = totalAmt;
	}

	public Integer getHspecial() {
		return this.hspecial;
	}

	public void setHspecial(Integer hspecial) {
		this.hspecial = hspecial;
	}

	public Integer getClassTeacher() {
		return this.classTeacher;
	}

	public void setClassTeacher(Integer classTeacher) {
		this.classTeacher = classTeacher;
	}

	public Integer getOthersOut1() {
		return this.othersOut1;
	}

	public void setOthersOut1(Integer othersOut1) {
		this.othersOut1 = othersOut1;
	}

	public Integer getNotax() {
		return this.notax;
	}

	public void setNotax(Integer notax) {
		this.notax = notax;
	}

	public Integer getShouldTax() {
		return this.shouldTax;
	}

	public void setShouldTax(Integer shouldTax) {
		this.shouldTax = shouldTax;
	}

	public Integer getRealPay() {
		return this.realPay;
	}

	public void setRealPay(Integer realPay) {
		this.realPay = realPay;
	}

	public Integer getTaxPay() {
		return this.taxPay;
	}

	public void setTaxPay(Integer taxPay) {
		this.taxPay = taxPay;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}