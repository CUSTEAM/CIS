package tw.edu.chit.model;

/**
 * Just generated by MyEclipse - Hibernate Tools
 */
public class Just extends JustBase implements java.io.Serializable {

	private static final long serialVersionUID = 8422205208304081897L;

	private Integer oid;
	private String departClass;
	private String studentNo;
	private Double teacherScore;
	private Double deptheaderScore;
	private Double militaryScore;
	private Double dilgScore;
	private Double desdScore;
	private Double meetingScore;
	private Double totalScore;
	private String comCode1;
	private String comName1;
	private String comCode2;
	private String comName2;
	private String comCode3;
	private String comName3;

	/** default constructor */
	public Just() {
	}

	/** full constructor */
	public Just(String departClass, String studentNo, Double teacherScore,
			Double deptheaderScore, Double militaryScore, Double dilgScore,
			Double desdScore, Double meetingScore, Double totalScore,
			String comCode1, String comName1, String comCode2, String comName2,
			String comCode3, String comName3) {
		this.departClass = departClass;
		this.studentNo = studentNo;
		this.teacherScore = teacherScore;
		this.deptheaderScore = deptheaderScore;
		this.militaryScore = militaryScore;
		this.dilgScore = dilgScore;
		this.desdScore = desdScore;
		this.meetingScore = meetingScore;
		this.totalScore = totalScore;
		this.comCode1 = comCode1;
		this.comName1 = comName1;
		this.comCode2 = comCode2;
		this.comName2 = comName2;
		this.comCode3 = comCode3;
		this.comName3 = comName3;
	}

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

	public String getStudentNo() {
		return this.studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public Double getTeacherScore() {
		return this.teacherScore;
	}

	public void setTeacherScore(Double teacherScore) {
		this.teacherScore = teacherScore;
	}

	public Double getDeptheaderScore() {
		return this.deptheaderScore;
	}

	public void setDeptheaderScore(Double deptheaderScore) {
		this.deptheaderScore = deptheaderScore;
	}

	public Double getMilitaryScore() {
		return this.militaryScore;
	}

	public void setMilitaryScore(Double militaryScore) {
		this.militaryScore = militaryScore;
	}

	public Double getDilgScore() {
		return this.dilgScore;
	}

	public void setDilgScore(Double dilgScore) {
		this.dilgScore = dilgScore;
	}

	public Double getDesdScore() {
		return this.desdScore;
	}

	public void setDesdScore(Double desdScore) {
		this.desdScore = desdScore;
	}

	public Double getMeetingScore() {
		return this.meetingScore;
	}

	public void setMeetingScore(Double meetingScore) {
		this.meetingScore = meetingScore;
	}

	public Double getTotalScore() {
		return this.totalScore;
	}

	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
	}

	public String getComCode1() {
		return this.comCode1;
	}

	public void setComCode1(String comCode1) {
		this.comCode1 = comCode1;
	}

	public String getComName1() {
		return this.comName1;
	}

	public void setComName1(String comName1) {
		this.comName1 = comName1;
	}

	public String getComCode2() {
		return this.comCode2;
	}

	public void setComCode2(String comCode2) {
		this.comCode2 = comCode2;
	}

	public String getComName2() {
		return this.comName2;
	}

	public void setComName2(String comName2) {
		this.comName2 = comName2;
	}

	public String getComCode3() {
		return this.comCode3;
	}

	public void setComCode3(String comCode3) {
		this.comCode3 = comCode3;
	}

	public String getComName3() {
		return this.comName3;
	}

	public void setComName3(String comName3) {
		this.comName3 = comName3;
	}

}