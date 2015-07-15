package tw.edu.chit.model;

/**
 * Savedtime entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Savedtime implements java.io.Serializable {

	// Fields

	private Integer oid;
	private Short schoolYear;
	private Short schoolTerm;
	private String departClass;
	private String cscode;
	private String techid;
	private String opt;
	private Float credit;
	private Short thour;
	private Short week1;
	private Short begin11;
	private Short end11;
	private Short begin12;
	private Short end12;
	private Short week2;
	private Short begin21;
	private Short end21;
	private Short begin22;
	private Short end22;
	private Short week3;
	private Short begin31;
	private Short end31;
	private Short begin32;
	private Short end32;
	private Short stuSelect;

	// Constructors

	/** default constructor */
	public Savedtime() {
	}

	/** minimal constructor */
	public Savedtime(Short schoolYear, Short schoolTerm, String departClass,
			String cscode) {
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.departClass = departClass;
		this.cscode = cscode;
	}

	/** full constructor */
	public Savedtime(Short schoolYear, Short schoolTerm, String departClass,
			String cscode, String techid, String opt, Float credit,
			Short thour, Short week1, Short begin11, Short end11,
			Short begin12, Short end12, Short week2, Short begin21,
			Short end21, Short begin22, Short end22, Short week3,
			Short begin31, Short end31, Short begin32, Short end32,
			Short stuSelect) {
		this.schoolYear = schoolYear;
		this.schoolTerm = schoolTerm;
		this.departClass = departClass;
		this.cscode = cscode;
		this.techid = techid;
		this.opt = opt;
		this.credit = credit;
		this.thour = thour;
		this.week1 = week1;
		this.begin11 = begin11;
		this.end11 = end11;
		this.begin12 = begin12;
		this.end12 = end12;
		this.week2 = week2;
		this.begin21 = begin21;
		this.end21 = end21;
		this.begin22 = begin22;
		this.end22 = end22;
		this.week3 = week3;
		this.begin31 = begin31;
		this.end31 = end31;
		this.begin32 = begin32;
		this.end32 = end32;
		this.stuSelect = stuSelect;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Short getSchoolYear() {
		return this.schoolYear;
	}

	public void setSchoolYear(Short schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Short getSchoolTerm() {
		return this.schoolTerm;
	}

	public void setSchoolTerm(Short schoolTerm) {
		this.schoolTerm = schoolTerm;
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

	public String getTechid() {
		return this.techid;
	}

	public void setTechid(String techid) {
		this.techid = techid;
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

	public Short getThour() {
		return this.thour;
	}

	public void setThour(Short thour) {
		this.thour = thour;
	}

	public Short getWeek1() {
		return this.week1;
	}

	public void setWeek1(Short week1) {
		this.week1 = week1;
	}

	public Short getBegin11() {
		return this.begin11;
	}

	public void setBegin11(Short begin11) {
		this.begin11 = begin11;
	}

	public Short getEnd11() {
		return this.end11;
	}

	public void setEnd11(Short end11) {
		this.end11 = end11;
	}

	public Short getBegin12() {
		return this.begin12;
	}

	public void setBegin12(Short begin12) {
		this.begin12 = begin12;
	}

	public Short getEnd12() {
		return this.end12;
	}

	public void setEnd12(Short end12) {
		this.end12 = end12;
	}

	public Short getWeek2() {
		return this.week2;
	}

	public void setWeek2(Short week2) {
		this.week2 = week2;
	}

	public Short getBegin21() {
		return this.begin21;
	}

	public void setBegin21(Short begin21) {
		this.begin21 = begin21;
	}

	public Short getEnd21() {
		return this.end21;
	}

	public void setEnd21(Short end21) {
		this.end21 = end21;
	}

	public Short getBegin22() {
		return this.begin22;
	}

	public void setBegin22(Short begin22) {
		this.begin22 = begin22;
	}

	public Short getEnd22() {
		return this.end22;
	}

	public void setEnd22(Short end22) {
		this.end22 = end22;
	}

	public Short getWeek3() {
		return this.week3;
	}

	public void setWeek3(Short week3) {
		this.week3 = week3;
	}

	public Short getBegin31() {
		return this.begin31;
	}

	public void setBegin31(Short begin31) {
		this.begin31 = begin31;
	}

	public Short getEnd31() {
		return this.end31;
	}

	public void setEnd31(Short end31) {
		this.end31 = end31;
	}

	public Short getBegin32() {
		return this.begin32;
	}

	public void setBegin32(Short begin32) {
		this.begin32 = begin32;
	}

	public Short getEnd32() {
		return this.end32;
	}

	public void setEnd32(Short end32) {
		this.end32 = end32;
	}

	public Short getStuSelect() {
		return this.stuSelect;
	}

	public void setStuSelect(Short stuSelect) {
		this.stuSelect = stuSelect;
	}

}