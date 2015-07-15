package tw.edu.chit.model;

/**
 * Rrate entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Rrate extends RrateBase implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String departClass;
	private String cscode;
	private Short total;
	private Float rate01;
	private Float rate02;
	private Float rate03;
	private Float rate04;
	private Float rate05;
	private Float rate06;
	private Float rate07;
	private Float rate08;
	private Float rate09;
	private Float rate10;
	private Float rate11;
	private Float rate12;
	private Float rate13;
	private Float rate14;
	private Float rate15;
	private Float rateN;
	private Float rateM;
	private Float rateF;
	private Integer dtimeOid;

	// Constructors

	/** default constructor */
	public Rrate() {
	}

	/** minimal constructor */
	public Rrate(Integer dtimeOid) {
		this.dtimeOid = dtimeOid;
	}

	/** full constructor */
	public Rrate(String departClass, String cscode, Short total, Float rate01,
			Float rate02, Float rate03, Float rate04, Float rate05,
			Float rate06, Float rate07, Float rate08, Float rate09,
			Float rate10, Float rate11, Float rate12, Float rate13,
			Float rate14, Float rate15, Float rateN, Float rateM, Float rateF,
			Integer dtimeOid) {
		this.departClass = departClass;
		this.cscode = cscode;
		this.total = total;
		this.rate01 = rate01;
		this.rate02 = rate02;
		this.rate03 = rate03;
		this.rate04 = rate04;
		this.rate05 = rate05;
		this.rate06 = rate06;
		this.rate07 = rate07;
		this.rate08 = rate08;
		this.rate09 = rate09;
		this.rate10 = rate10;
		this.rate11 = rate11;
		this.rate12 = rate12;
		this.rate13 = rate13;
		this.rate14 = rate14;
		this.rate15 = rate15;
		this.rateN = rateN;
		this.rateM = rateM;
		this.rateF = rateF;
		this.dtimeOid = dtimeOid;
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

	public String getCscode() {
		return this.cscode;
	}

	public void setCscode(String cscode) {
		this.cscode = cscode;
	}

	public Short getTotal() {
		return this.total;
	}

	public void setTotal(Short total) {
		this.total = total;
	}

	public Float getRate01() {
		return this.rate01;
	}

	public void setRate01(Float rate01) {
		this.rate01 = rate01;
	}

	public Float getRate02() {
		return this.rate02;
	}

	public void setRate02(Float rate02) {
		this.rate02 = rate02;
	}

	public Float getRate03() {
		return this.rate03;
	}

	public void setRate03(Float rate03) {
		this.rate03 = rate03;
	}

	public Float getRate04() {
		return this.rate04;
	}

	public void setRate04(Float rate04) {
		this.rate04 = rate04;
	}

	public Float getRate05() {
		return this.rate05;
	}

	public void setRate05(Float rate05) {
		this.rate05 = rate05;
	}

	public Float getRate06() {
		return this.rate06;
	}

	public void setRate06(Float rate06) {
		this.rate06 = rate06;
	}

	public Float getRate07() {
		return this.rate07;
	}

	public void setRate07(Float rate07) {
		this.rate07 = rate07;
	}

	public Float getRate08() {
		return this.rate08;
	}

	public void setRate08(Float rate08) {
		this.rate08 = rate08;
	}

	public Float getRate09() {
		return this.rate09;
	}

	public void setRate09(Float rate09) {
		this.rate09 = rate09;
	}

	public Float getRate10() {
		return this.rate10;
	}

	public void setRate10(Float rate10) {
		this.rate10 = rate10;
	}

	public Float getRate11() {
		return this.rate11;
	}

	public void setRate11(Float rate11) {
		this.rate11 = rate11;
	}

	public Float getRate12() {
		return this.rate12;
	}

	public void setRate12(Float rate12) {
		this.rate12 = rate12;
	}

	public Float getRate13() {
		return this.rate13;
	}

	public void setRate13(Float rate13) {
		this.rate13 = rate13;
	}

	public Float getRate14() {
		return this.rate14;
	}

	public void setRate14(Float rate14) {
		this.rate14 = rate14;
	}

	public Float getRate15() {
		return this.rate15;
	}

	public void setRate15(Float rate15) {
		this.rate15 = rate15;
	}

	public Float getRateN() {
		return this.rateN;
	}

	public void setRateN(Float rateN) {
		this.rateN = rateN;
	}

	public Float getRateM() {
		return this.rateM;
	}

	public void setRateM(Float rateM) {
		this.rateM = rateM;
	}

	public Float getRateF() {
		return this.rateF;
	}

	public void setRateF(Float rateF) {
		this.rateF = rateF;
	}

	public Integer getDtimeOid() {
		return this.dtimeOid;
	}

	public void setDtimeOid(Integer dtimeOid) {
		this.dtimeOid = dtimeOid;
	}

}