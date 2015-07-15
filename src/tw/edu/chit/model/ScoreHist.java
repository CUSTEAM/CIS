package tw.edu.chit.model;

public class ScoreHist extends ScoreHistBase implements java.io.Serializable {

	private static final long serialVersionUID = 1565394940283110476L;

	private Integer oid;
	private String studentNo;
	private Short schoolYear;
	private String schoolTerm;
	private String stdepartClass;
	private String evgrType;
	private String cscode;
	private String opt;
	private Float credit;
	private Float score;

	// Constructors

	/** default constructor */
	public ScoreHist() {
	}
	
	public ScoreHist(String studentNo) {
		this.studentNo = studentNo;
	}

	/** minimal constructor */
	public ScoreHist(Integer oid) {
		this.oid = oid;
	}

	/** full constructor */
	public ScoreHist(Integer oid, String studentNo, Short schoolYear,
			String schoolTerm, String stdepartClass, String evgrType,
			String cscode, String opt, Float credit, Float score) {
		this.oid = oid;
		this.studentNo = studentNo;
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.stdepartClass = stdepartClass;
		this.evgrType = evgrType;
		this.cscode = cscode;
		this.opt = opt;
		this.credit = credit;
		this.score = score;
	}

	// Property accessors

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

	public Short getSchoolYear() {
		return this.schoolYear;
	}

	public void setSchoolYear(Short schoolYear) {
		this.schoolYear = schoolYear;
	}

	public String getSchoolTerm() {
		return this.schoolTerm;
	}

	public void setSchoolTerm(String schoolTerm) {
		this.schoolTerm = schoolTerm;
	}

	public String getStdepartClass() {
		return this.stdepartClass;
	}

	public void setStdepartClass(String stdepartClass) {
		this.stdepartClass = stdepartClass;
	}

	public String getEvgrType() {
		return this.evgrType;
	}

	public void setEvgrType(String evgrType) {
		this.evgrType = evgrType;
	}

	public String getCscode() {
		return this.cscode;
	}

	public void setCscode(String cscode) {
		this.cscode = cscode;
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

	public Float getScore() {
		return this.score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

}
