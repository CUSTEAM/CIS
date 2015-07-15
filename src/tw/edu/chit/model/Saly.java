package tw.edu.chit.model;

import java.util.Date;

/**
 * Saly entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Saly implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String idno;
	private Date sdate;
	private Short seqno;
	private Integer monthlyPay;
	private Integer inslast;
	private Integer study;
	private Integer technical;
	private Integer hspecial;
	private Integer specStudy;
	private Integer difference;
	private Integer substituteCourse;
	private Integer overhourPay;
	private Integer classTeacherPay;
	private Integer transportation;
	private Integer nightTransport;
	private Integer supervisorTest;
	private Integer nightOverhourPay;
	private Integer othersIn1;
	private Integer othersIn2;
	private Integer notax;
	private Integer payamt;
	private Integer publicInsure;
	private Integer spouseInsure;
	private Integer taxPay;
	private Integer borrow;
	private Integer othersOut1;
	private Integer othersOut2;
	private Integer dedamt;
	private Integer realPay;
	private Integer teachOver;
	private Integer hourPay;
	private Integer familyNo;

	// Constructors

	/** default constructor */
	public Saly() {
	}

	/** minimal constructor */
	public Saly(String idno, Date sdate) {
		this.idno = idno;
		this.sdate = sdate;
	}

	/** full constructor */
	public Saly(String idno, Date sdate, Short seqno, Integer monthlyPay,
			Integer inslast, Integer study, Integer technical,
			Integer hspecial, Integer specStudy, Integer difference,
			Integer substituteCourse, Integer overhourPay,
			Integer classTeacherPay, Integer transportation,
			Integer nightTransport, Integer supervisorTest,
			Integer nightOverhourPay, Integer othersIn1, Integer othersIn2,
			Integer notax, Integer payamt, Integer publicInsure,
			Integer spouseInsure, Integer taxPay, Integer borrow,
			Integer othersOut1, Integer othersOut2, Integer dedamt,
			Integer realPay, Integer teachOver, Integer hourPay,
			Integer familyNo) {
		this.idno = idno;
		this.sdate = sdate;
		this.seqno = seqno;
		this.monthlyPay = monthlyPay;
		this.inslast = inslast;
		this.study = study;
		this.technical = technical;
		this.hspecial = hspecial;
		this.specStudy = specStudy;
		this.difference = difference;
		this.substituteCourse = substituteCourse;
		this.overhourPay = overhourPay;
		this.classTeacherPay = classTeacherPay;
		this.transportation = transportation;
		this.nightTransport = nightTransport;
		this.supervisorTest = supervisorTest;
		this.nightOverhourPay = nightOverhourPay;
		this.othersIn1 = othersIn1;
		this.othersIn2 = othersIn2;
		this.notax = notax;
		this.payamt = payamt;
		this.publicInsure = publicInsure;
		this.spouseInsure = spouseInsure;
		this.taxPay = taxPay;
		this.borrow = borrow;
		this.othersOut1 = othersOut1;
		this.othersOut2 = othersOut2;
		this.dedamt = dedamt;
		this.realPay = realPay;
		this.teachOver = teachOver;
		this.hourPay = hourPay;
		this.familyNo = familyNo;
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

	public Date getSdate() {
		return this.sdate;
	}

	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}

	public Short getSeqno() {
		return this.seqno;
	}

	public void setSeqno(Short seqno) {
		this.seqno = seqno;
	}

	public Integer getMonthlyPay() {
		return this.monthlyPay;
	}

	public void setMonthlyPay(Integer monthlyPay) {
		this.monthlyPay = monthlyPay;
	}

	public Integer getInslast() {
		return this.inslast;
	}

	public void setInslast(Integer inslast) {
		this.inslast = inslast;
	}

	public Integer getStudy() {
		return this.study;
	}

	public void setStudy(Integer study) {
		this.study = study;
	}

	public Integer getTechnical() {
		return this.technical;
	}

	public void setTechnical(Integer technical) {
		this.technical = technical;
	}

	public Integer getHspecial() {
		return this.hspecial;
	}

	public void setHspecial(Integer hspecial) {
		this.hspecial = hspecial;
	}

	public Integer getSpecStudy() {
		return this.specStudy;
	}

	public void setSpecStudy(Integer specStudy) {
		this.specStudy = specStudy;
	}

	public Integer getDifference() {
		return this.difference;
	}

	public void setDifference(Integer difference) {
		this.difference = difference;
	}

	public Integer getSubstituteCourse() {
		return this.substituteCourse;
	}

	public void setSubstituteCourse(Integer substituteCourse) {
		this.substituteCourse = substituteCourse;
	}

	public Integer getOverhourPay() {
		return this.overhourPay;
	}

	public void setOverhourPay(Integer overhourPay) {
		this.overhourPay = overhourPay;
	}

	public Integer getClassTeacherPay() {
		return this.classTeacherPay;
	}

	public void setClassTeacherPay(Integer classTeacherPay) {
		this.classTeacherPay = classTeacherPay;
	}

	public Integer getTransportation() {
		return this.transportation;
	}

	public void setTransportation(Integer transportation) {
		this.transportation = transportation;
	}

	public Integer getNightTransport() {
		return this.nightTransport;
	}

	public void setNightTransport(Integer nightTransport) {
		this.nightTransport = nightTransport;
	}

	public Integer getSupervisorTest() {
		return this.supervisorTest;
	}

	public void setSupervisorTest(Integer supervisorTest) {
		this.supervisorTest = supervisorTest;
	}

	public Integer getNightOverhourPay() {
		return this.nightOverhourPay;
	}

	public void setNightOverhourPay(Integer nightOverhourPay) {
		this.nightOverhourPay = nightOverhourPay;
	}

	public Integer getOthersIn1() {
		return this.othersIn1;
	}

	public void setOthersIn1(Integer othersIn1) {
		this.othersIn1 = othersIn1;
	}

	public Integer getOthersIn2() {
		return this.othersIn2;
	}

	public void setOthersIn2(Integer othersIn2) {
		this.othersIn2 = othersIn2;
	}

	public Integer getNotax() {
		return this.notax;
	}

	public void setNotax(Integer notax) {
		this.notax = notax;
	}

	public Integer getPayamt() {
		return this.payamt;
	}

	public void setPayamt(Integer payamt) {
		this.payamt = payamt;
	}

	public Integer getPublicInsure() {
		return this.publicInsure;
	}

	public void setPublicInsure(Integer publicInsure) {
		this.publicInsure = publicInsure;
	}

	public Integer getSpouseInsure() {
		return this.spouseInsure;
	}

	public void setSpouseInsure(Integer spouseInsure) {
		this.spouseInsure = spouseInsure;
	}

	public Integer getTaxPay() {
		return this.taxPay;
	}

	public void setTaxPay(Integer taxPay) {
		this.taxPay = taxPay;
	}

	public Integer getBorrow() {
		return this.borrow;
	}

	public void setBorrow(Integer borrow) {
		this.borrow = borrow;
	}

	public Integer getOthersOut1() {
		return this.othersOut1;
	}

	public void setOthersOut1(Integer othersOut1) {
		this.othersOut1 = othersOut1;
	}

	public Integer getOthersOut2() {
		return this.othersOut2;
	}

	public void setOthersOut2(Integer othersOut2) {
		this.othersOut2 = othersOut2;
	}

	public Integer getDedamt() {
		return this.dedamt;
	}

	public void setDedamt(Integer dedamt) {
		this.dedamt = dedamt;
	}

	public Integer getRealPay() {
		return this.realPay;
	}

	public void setRealPay(Integer realPay) {
		this.realPay = realPay;
	}

	public Integer getTeachOver() {
		return this.teachOver;
	}

	public void setTeachOver(Integer teachOver) {
		this.teachOver = teachOver;
	}

	public Integer getHourPay() {
		return this.hourPay;
	}

	public void setHourPay(Integer hourPay) {
		this.hourPay = hourPay;
	}

	public Integer getFamilyNo() {
		return this.familyNo;
	}

	public void setFamilyNo(Integer familyNo) {
		this.familyNo = familyNo;
	}

}