package tw.edu.chit.model;

/**
 * SalarySset entity. @author MyEclipse Persistence Tools
 */

public class SalarySset implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String idno;
	private Integer sno;
	private Integer monthlyPay;
	private Integer study;
	private Integer technical;
	private Integer hspecial;
	private Integer substituteCourse;
	private Integer classTeacherPay;
	private Integer classTeacherPayN;
	private Integer transportation;
	private Integer othersIn;
	private String othersInNote;
	private Integer overhourPay;
	private Integer nightOverhourPay;
	private Integer classTeacherPayDif;
	private Integer classTeacherPayNDif;
	private Integer overhourPayDif;
	private Integer nightOverhourPayDif;
	private Integer difference;
	private Integer othersOut;
	private String othersOutNote;
	private Integer dedamt;
	private Integer spouseInsure;
	private Integer publicInsure;
	private Integer teachOver;
	private Integer hourPay;
	private Integer familyNo;
	private Integer notax;
	private Integer taxPay;
	private Integer payamt;
	private Integer realPay;
	private Integer publicInsureDif;
	private Integer spouseInsureDif;
	private Integer taxPayDif;
	private Integer hourPayDif;
	private Integer teachOverDif;
	private Integer deposit;
	private Integer depositDif;
	private Integer labor;
	private Integer laborDif;
	private Integer banditry;

	// Constructors

	/** default constructor */
	public SalarySset() {
	}

	/** minimal constructor */
	public SalarySset(String idno) {
		this.idno = idno;
	}

	/** full constructor */
	public SalarySset(String idno, Integer sno, Integer monthlyPay,
			Integer study, Integer technical, Integer hspecial,
			Integer substituteCourse, Integer classTeacherPay,
			Integer classTeacherPayN, Integer transportation, Integer othersIn,
			String othersInNote, Integer overhourPay, Integer nightOverhourPay,
			Integer classTeacherPayDif, Integer classTeacherPayNDif,
			Integer overhourPayDif, Integer nightOverhourPayDif,
			Integer difference, Integer othersOut, String othersOutNote,
			Integer dedamt, Integer spouseInsure, Integer publicInsure,
			Integer teachOver, Integer hourPay, Integer familyNo,
			Integer notax, Integer taxPay, Integer payamt, Integer realPay,
			Integer publicInsureDif, Integer spouseInsureDif,
			Integer taxPayDif, Integer hourPayDif, Integer teachOverDif,
			Integer deposit, Integer depositDif, Integer labor,
			Integer laborDif, Integer banditry) {
		this.idno = idno;
		this.sno = sno;
		this.monthlyPay = monthlyPay;
		this.study = study;
		this.technical = technical;
		this.hspecial = hspecial;
		this.substituteCourse = substituteCourse;
		this.classTeacherPay = classTeacherPay;
		this.classTeacherPayN = classTeacherPayN;
		this.transportation = transportation;
		this.othersIn = othersIn;
		this.othersInNote = othersInNote;
		this.overhourPay = overhourPay;
		this.nightOverhourPay = nightOverhourPay;
		this.classTeacherPayDif = classTeacherPayDif;
		this.classTeacherPayNDif = classTeacherPayNDif;
		this.overhourPayDif = overhourPayDif;
		this.nightOverhourPayDif = nightOverhourPayDif;
		this.difference = difference;
		this.othersOut = othersOut;
		this.othersOutNote = othersOutNote;
		this.dedamt = dedamt;
		this.spouseInsure = spouseInsure;
		this.publicInsure = publicInsure;
		this.teachOver = teachOver;
		this.hourPay = hourPay;
		this.familyNo = familyNo;
		this.notax = notax;
		this.taxPay = taxPay;
		this.payamt = payamt;
		this.realPay = realPay;
		this.publicInsureDif = publicInsureDif;
		this.spouseInsureDif = spouseInsureDif;
		this.taxPayDif = taxPayDif;
		this.hourPayDif = hourPayDif;
		this.teachOverDif = teachOverDif;
		this.deposit = deposit;
		this.depositDif = depositDif;
		this.labor = labor;
		this.laborDif = laborDif;
		this.banditry = banditry;
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

	public Integer getSno() {
		return this.sno;
	}

	public void setSno(Integer sno) {
		this.sno = sno;
	}

	public Integer getMonthlyPay() {
		return this.monthlyPay;
	}

	public void setMonthlyPay(Integer monthlyPay) {
		this.monthlyPay = monthlyPay;
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

	public Integer getSubstituteCourse() {
		return this.substituteCourse;
	}

	public void setSubstituteCourse(Integer substituteCourse) {
		this.substituteCourse = substituteCourse;
	}

	public Integer getClassTeacherPay() {
		return this.classTeacherPay;
	}

	public void setClassTeacherPay(Integer classTeacherPay) {
		this.classTeacherPay = classTeacherPay;
	}

	public Integer getClassTeacherPayN() {
		return this.classTeacherPayN;
	}

	public void setClassTeacherPayN(Integer classTeacherPayN) {
		this.classTeacherPayN = classTeacherPayN;
	}

	public Integer getTransportation() {
		return this.transportation;
	}

	public void setTransportation(Integer transportation) {
		this.transportation = transportation;
	}

	public Integer getOthersIn() {
		return this.othersIn;
	}

	public void setOthersIn(Integer othersIn) {
		this.othersIn = othersIn;
	}

	public String getOthersInNote() {
		return this.othersInNote;
	}

	public void setOthersInNote(String othersInNote) {
		this.othersInNote = othersInNote;
	}

	public Integer getOverhourPay() {
		return this.overhourPay;
	}

	public void setOverhourPay(Integer overhourPay) {
		this.overhourPay = overhourPay;
	}

	public Integer getNightOverhourPay() {
		return this.nightOverhourPay;
	}

	public void setNightOverhourPay(Integer nightOverhourPay) {
		this.nightOverhourPay = nightOverhourPay;
	}

	public Integer getClassTeacherPayDif() {
		return this.classTeacherPayDif;
	}

	public void setClassTeacherPayDif(Integer classTeacherPayDif) {
		this.classTeacherPayDif = classTeacherPayDif;
	}

	public Integer getClassTeacherPayNDif() {
		return this.classTeacherPayNDif;
	}

	public void setClassTeacherPayNDif(Integer classTeacherPayNDif) {
		this.classTeacherPayNDif = classTeacherPayNDif;
	}

	public Integer getOverhourPayDif() {
		return this.overhourPayDif;
	}

	public void setOverhourPayDif(Integer overhourPayDif) {
		this.overhourPayDif = overhourPayDif;
	}

	public Integer getNightOverhourPayDif() {
		return this.nightOverhourPayDif;
	}

	public void setNightOverhourPayDif(Integer nightOverhourPayDif) {
		this.nightOverhourPayDif = nightOverhourPayDif;
	}

	public Integer getDifference() {
		return this.difference;
	}

	public void setDifference(Integer difference) {
		this.difference = difference;
	}

	public Integer getOthersOut() {
		return this.othersOut;
	}

	public void setOthersOut(Integer othersOut) {
		this.othersOut = othersOut;
	}

	public String getOthersOutNote() {
		return this.othersOutNote;
	}

	public void setOthersOutNote(String othersOutNote) {
		this.othersOutNote = othersOutNote;
	}

	public Integer getDedamt() {
		return this.dedamt;
	}

	public void setDedamt(Integer dedamt) {
		this.dedamt = dedamt;
	}

	public Integer getSpouseInsure() {
		return this.spouseInsure;
	}

	public void setSpouseInsure(Integer spouseInsure) {
		this.spouseInsure = spouseInsure;
	}

	public Integer getPublicInsure() {
		return this.publicInsure;
	}

	public void setPublicInsure(Integer publicInsure) {
		this.publicInsure = publicInsure;
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

	public Integer getNotax() {
		return this.notax;
	}

	public void setNotax(Integer notax) {
		this.notax = notax;
	}

	public Integer getTaxPay() {
		return this.taxPay;
	}

	public void setTaxPay(Integer taxPay) {
		this.taxPay = taxPay;
	}

	public Integer getPayamt() {
		return this.payamt;
	}

	public void setPayamt(Integer payamt) {
		this.payamt = payamt;
	}

	public Integer getRealPay() {
		return this.realPay;
	}

	public void setRealPay(Integer realPay) {
		this.realPay = realPay;
	}

	public Integer getPublicInsureDif() {
		return this.publicInsureDif;
	}

	public void setPublicInsureDif(Integer publicInsureDif) {
		this.publicInsureDif = publicInsureDif;
	}

	public Integer getSpouseInsureDif() {
		return this.spouseInsureDif;
	}

	public void setSpouseInsureDif(Integer spouseInsureDif) {
		this.spouseInsureDif = spouseInsureDif;
	}

	public Integer getTaxPayDif() {
		return this.taxPayDif;
	}

	public void setTaxPayDif(Integer taxPayDif) {
		this.taxPayDif = taxPayDif;
	}

	public Integer getHourPayDif() {
		return this.hourPayDif;
	}

	public void setHourPayDif(Integer hourPayDif) {
		this.hourPayDif = hourPayDif;
	}

	public Integer getTeachOverDif() {
		return this.teachOverDif;
	}

	public void setTeachOverDif(Integer teachOverDif) {
		this.teachOverDif = teachOverDif;
	}

	public Integer getDeposit() {
		return this.deposit;
	}

	public void setDeposit(Integer deposit) {
		this.deposit = deposit;
	}

	public Integer getDepositDif() {
		return this.depositDif;
	}

	public void setDepositDif(Integer depositDif) {
		this.depositDif = depositDif;
	}

	public Integer getLabor() {
		return this.labor;
	}

	public void setLabor(Integer labor) {
		this.labor = labor;
	}

	public Integer getLaborDif() {
		return this.laborDif;
	}

	public void setLaborDif(Integer laborDif) {
		this.laborDif = laborDif;
	}

	public Integer getBanditry() {
		return this.banditry;
	}

	public void setBanditry(Integer banditry) {
		this.banditry = banditry;
	}

}