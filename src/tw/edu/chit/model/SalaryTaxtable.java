package tw.edu.chit.model;

/**
 * SalaryTaxtable entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SalaryTaxtable implements java.io.Serializable {

	// Fields

	private Integer oid;
	private Integer mini;
	private Integer max;
	private Integer p1;
	private Integer p2;
	private Integer p3;
	private Integer p4;
	private Integer p5;
	private Integer p6;
	private Integer p7;
	private Integer p8;
	private Integer p9;
	private Integer p10;
	private Integer p11;

	// Constructors

	/** default constructor */
	public SalaryTaxtable() {
	}

	/** full constructor */
	public SalaryTaxtable(Integer mini, Integer max, Integer p1, Integer p2,
			Integer p3, Integer p4, Integer p5, Integer p6, Integer p7,
			Integer p8, Integer p9, Integer p10, Integer p11) {
		this.mini = mini;
		this.max = max;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
		this.p5 = p5;
		this.p6 = p6;
		this.p7 = p7;
		this.p8 = p8;
		this.p9 = p9;
		this.p10 = p10;
		this.p11 = p11;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getMini() {
		return this.mini;
	}

	public void setMini(Integer mini) {
		this.mini = mini;
	}

	public Integer getMax() {
		return this.max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getP1() {
		return this.p1;
	}

	public void setP1(Integer p1) {
		this.p1 = p1;
	}

	public Integer getP2() {
		return this.p2;
	}

	public void setP2(Integer p2) {
		this.p2 = p2;
	}

	public Integer getP3() {
		return this.p3;
	}

	public void setP3(Integer p3) {
		this.p3 = p3;
	}

	public Integer getP4() {
		return this.p4;
	}

	public void setP4(Integer p4) {
		this.p4 = p4;
	}

	public Integer getP5() {
		return this.p5;
	}

	public void setP5(Integer p5) {
		this.p5 = p5;
	}

	public Integer getP6() {
		return this.p6;
	}

	public void setP6(Integer p6) {
		this.p6 = p6;
	}

	public Integer getP7() {
		return this.p7;
	}

	public void setP7(Integer p7) {
		this.p7 = p7;
	}

	public Integer getP8() {
		return this.p8;
	}

	public void setP8(Integer p8) {
		this.p8 = p8;
	}

	public Integer getP9() {
		return this.p9;
	}

	public void setP9(Integer p9) {
		this.p9 = p9;
	}

	public Integer getP10() {
		return this.p10;
	}

	public void setP10(Integer p10) {
		this.p10 = p10;
	}

	public Integer getP11() {
		return this.p11;
	}

	public void setP11(Integer p11) {
		this.p11 = p11;
	}

}