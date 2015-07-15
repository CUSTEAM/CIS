package tw.edu.chit.model;

/**
 * DtimeEx entity. @author MyEclipse Persistence Tools
 */

public class DtimeEx implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String departClass;
	private String cscode;
	private String techid;
	private String opt;
	private Float credit;
	private Short thour;
	private Short stuSelect;
	private String sterm;
	private Short selectLimit;
	private Boolean open;
	private String elearning;
	private String extrapay;
	private String crozz;
	private Integer periodLimit;
	private Float coansw;
	private String introduction;
	private String syllabi;
	private String syllabiSub;

	// Constructors

	/** default constructor */
	public DtimeEx() {
	}

	/** minimal constructor */
	public DtimeEx(String departClass, String cscode, String opt, Float credit,
			String sterm, Short selectLimit, Boolean open, String elearning,
			String extrapay) {
		this.departClass = departClass;
		this.cscode = cscode;
		this.opt = opt;
		this.credit = credit;
		this.sterm = sterm;
		this.selectLimit = selectLimit;
		this.open = open;
		this.elearning = elearning;
		this.extrapay = extrapay;
	}

	/** full constructor */
	public DtimeEx(String departClass, String cscode, String techid,
			String opt, Float credit, Short thour, Short stuSelect,
			String sterm, Short selectLimit, Boolean open, String elearning,
			String extrapay, String crozz, Integer periodLimit, Float coansw,
			Integer simples, String introduction, String syllabi,
			String syllabiSub) {
		this.departClass = departClass;
		this.cscode = cscode;
		this.techid = techid;
		this.opt = opt;
		this.credit = credit;
		this.thour = thour;
		this.stuSelect = stuSelect;
		this.sterm = sterm;
		this.selectLimit = selectLimit;
		this.open = open;
		this.elearning = elearning;
		this.extrapay = extrapay;
		this.crozz = crozz;
		this.periodLimit = periodLimit;
		this.coansw = coansw;
		this.introduction = introduction;
		this.syllabi = syllabi;
		this.syllabiSub = syllabiSub;
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

	public Short getStuSelect() {
		return this.stuSelect;
	}

	public void setStuSelect(Short stuSelect) {
		this.stuSelect = stuSelect;
	}

	public String getSterm() {
		return this.sterm;
	}

	public void setSterm(String sterm) {
		this.sterm = sterm;
	}

	public Short getSelectLimit() {
		return this.selectLimit;
	}

	public void setSelectLimit(Short selectLimit) {
		this.selectLimit = selectLimit;
	}

	public Boolean getOpen() {
		return this.open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public String getElearning() {
		return this.elearning;
	}

	public void setElearning(String elearning) {
		this.elearning = elearning;
	}

	public String getExtrapay() {
		return this.extrapay;
	}

	public void setExtrapay(String extrapay) {
		this.extrapay = extrapay;
	}

	public String getCrozz() {
		return this.crozz;
	}

	public void setCrozz(String crozz) {
		this.crozz = crozz;
	}

	public Integer getPeriodLimit() {
		return this.periodLimit;
	}

	public void setPeriodLimit(Integer periodLimit) {
		this.periodLimit = periodLimit;
	}

	public Float getCoansw() {
		return this.coansw;
	}

	public void setCoansw(Float coansw) {
		this.coansw = coansw;
	}

	public String getIntroduction() {
		return this.introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getSyllabi() {
		return this.syllabi;
	}

	public void setSyllabi(String syllabi) {
		this.syllabi = syllabi;
	}

	public String getSyllabiSub() {
		return this.syllabiSub;
	}

	public void setSyllabiSub(String syllabiSub) {
		this.syllabiSub = syllabiSub;
	}

}