package tw.edu.chit.model;

/**
 * Seld entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Seld extends SeldBase implements java.io.Serializable {

	private static final long serialVersionUID = 4691695887935320996L;
	
	private Integer oid;
	private String stdepartClass;
	private String studentNo;
	private String csdepartClass;
	private String cscode;
	private Double score;
	private Double score1; //===================Double>>>>>>>>>>>>>>
	private Double score2;
	private Double score3;
	private String opt;
	private Float credit;
	private Integer dtimeOid;
	private Double score01;
	private Double score02;
	private Double score03;
	private Double score04;
	private Double score05;
	private Double score06;
	private Double score07;
	private Double score08;
	private Double score09;
	private Double score10;
	private Double score11;
	private Double score12;
	private Double score13;
	private Double score14;
	private Double score15;
	private Double score16; //===================Double>>>>>>>>>>>>>>
	private Double score17;
	private Double score18;     
	private Integer dilgPeriod = 0;
	private Integer elearnDilg;

	// Constructors

	/** default constructor */
	public Seld() {
	}

	/** minimal constructor */
	public Seld(Integer dtimeOid) {
		this.dtimeOid = dtimeOid;
	}

	/** full constructor */
	public Seld(String stdepartClass, String studentNo, String csdepartClass,
			String cscode, Double score, Double score1, Double score2,
			Double score3, String opt, Float credit, Integer dtimeOid,
			Double score01, Double score02, Double score03, Double score04,
			Double score05, Double score06, Double score07, Double score08,
			Double score09, Double score10, Double score11, Double score12,
			Double score13, Double score14, Double score15, Double score16,
			Double score17, Double score18, Integer dilgPeriod, Integer elearnDilg) {
		this.stdepartClass = stdepartClass;
		this.studentNo = studentNo;
		this.csdepartClass = csdepartClass;
		this.cscode = cscode;
		this.score = score;
		this.score1 = score1;
		this.score2 = score2;
		this.score3 = score3;
		this.opt = opt;
		this.credit = credit;
		this.dtimeOid = dtimeOid;
		this.score01 = score01;
		this.score02 = score02;
		this.score03 = score03;
		this.score04 = score04;
		this.score05 = score05;
		this.score06 = score06;
		this.score07 = score07;
		this.score08 = score08;
		this.score09 = score09;
		this.score10 = score10;
		this.score11 = score11;
		this.score12 = score12;
		this.score13 = score13;
		this.score14 = score14;
		this.score15 = score15;
		this.score16 = score16;
		this.score17 = score17;
		this.score18 = score18;
		this.dilgPeriod = dilgPeriod;
		this.elearnDilg = elearnDilg;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getStdepartClass() {
		return this.stdepartClass;
	}

	public void setStdepartClass(String stdepartClass) {
		this.stdepartClass = stdepartClass;
	}

	public String getStudentNo() {
		return this.studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getCsdepartClass() {
		return this.csdepartClass;
	}

	public void setCsdepartClass(String csdepartClass) {
		this.csdepartClass = csdepartClass;
	}

	public String getCscode() {
		return this.cscode;
	}

	public void setCscode(String cscode) {
		this.cscode = cscode;
	}

	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Double getScore1() {
		return this.score1;
	}

	public void setScore1(Double score1) {
		this.score1 = score1;
	}

	public Double getScore2() {
		return this.score2;
	}

	public void setScore2(Double score2) {
		this.score2 = score2;
	}

	public Double getScore3() {
		return this.score3;
	}

	public void setScore3(Double score3) {
		this.score3 = score3;
	}

	public String getOpt() {
		return this.opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public Float getCredit() {
		return this.credit;
	}

	public void setCredit(Float credit) {
		this.credit = credit;
	}

	public Integer getDtimeOid() {
		return this.dtimeOid;
	}

	public void setDtimeOid(Integer dtimeOid) {
		this.dtimeOid = dtimeOid;
	}

	public Double getScore01() {
		return this.score01;
	}

	public void setScore01(Double score01) {
		this.score01 = score01;
	}

	public Double getScore02() {
		return this.score02;
	}

	public void setScore02(Double score02) {
		this.score02 = score02;
	}

	public Double getScore03() {
		return this.score03;
	}

	public void setScore03(Double score03) {
		this.score03 = score03;
	}

	public Double getScore04() {
		return this.score04;
	}

	public void setScore04(Double score04) {
		this.score04 = score04;
	}

	public Double getScore05() {
		return this.score05;
	}

	public void setScore05(Double score05) {
		this.score05 = score05;
	}

	public Double getScore06() {
		return this.score06;
	}

	public void setScore06(Double score06) {
		this.score06 = score06;
	}

	public Double getScore07() {
		return this.score07;
	}

	public void setScore07(Double score07) {
		this.score07 = score07;
	}

	public Double getScore08() {
		return this.score08;
	}

	public void setScore08(Double score08) {
		this.score08 = score08;
	}

	public Double getScore09() {
		return this.score09;
	}

	public void setScore09(Double score09) {
		this.score09 = score09;
	}

	public Double getScore10() {
		return this.score10;
	}

	public void setScore10(Double score10) {
		this.score10 = score10;
	}

	public Double getScore11() {
		return this.score11;
	}

	public void setScore11(Double score11) {
		this.score11 = score11;
	}

	public Double getScore12() {
		return this.score12;
	}

	public void setScore12(Double score12) {
		this.score12 = score12;
	}

	public Double getScore13() {
		return this.score13;
	}

	public void setScore13(Double score13) {
		this.score13 = score13;
	}

	public Double getScore14() {
		return this.score14;
	}

	public void setScore14(Double score14) {
		this.score14 = score14;
	}

	public Double getScore15() {
		return this.score15;
	}

	public void setScore15(Double score15) {
		this.score15 = score15;
	}

	public Double getScore16() {
		return this.score16;
	}

	public void setScore16(Double score16) {
		this.score16 = score16;
	}

	public Double getScore17() {
		return this.score17;
	}

	public void setScore17(Double score17) {
		this.score17 = score17;
	}

	public Double getScore18() {
		return this.score18;
	}

	public void setScore18(Double score18) {
		this.score18 = score18;
	}

	public Integer getDilgPeriod() {
		return this.dilgPeriod;
	}

	public void setDilgPeriod(Integer dilgPeriod) {
		this.dilgPeriod = dilgPeriod;
	}

	public Integer getElearnDilg() {
		return this.elearnDilg;
	}

	public void setElearnDilg(Integer elearnDilg) {
		this.elearnDilg = elearnDilg;
	}
}