package tw.edu.chit.model;

import java.util.Date;

/**
 * RecruitSchedule entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RecruitSchedule implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String name;
	private Integer schoolYear;
	private Date someday;
	private String sometime;
	private String leader;
	private String schoolCode;
	private String item;
	private String staff;
	private String workDescript;
	private String resultDescript;
	private String url;
	private String editor;
	private String feedback;

	// Constructors

	/** default constructor */
	public RecruitSchedule() {
	}

	/** full constructor */
	public RecruitSchedule(String name, Integer schoolYear, Date someday,
			String sometime, String leader, String schoolCode, String item,
			String staff, String workDescript, String resultDescript,
			String url, String editor, String feedback) {
		this.name = name;
		this.schoolYear = schoolYear;
		this.someday = someday;
		this.sometime = sometime;
		this.leader = leader;
		this.schoolCode = schoolCode;
		this.item = item;
		this.staff = staff;
		this.workDescript = workDescript;
		this.resultDescript = resultDescript;
		this.url = url;
		this.editor = editor;
		this.feedback = feedback;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSchoolYear() {
		return this.schoolYear;
	}

	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Date getSomeday() {
		return this.someday;
	}

	public void setSomeday(Date someday) {
		this.someday = someday;
	}

	public String getSometime() {
		return this.sometime;
	}

	public void setSometime(String sometime) {
		this.sometime = sometime;
	}

	public String getLeader() {
		return this.leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getSchoolCode() {
		return this.schoolCode;
	}

	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}

	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getStaff() {
		return this.staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

	public String getWorkDescript() {
		return this.workDescript;
	}

	public void setWorkDescript(String workDescript) {
		this.workDescript = workDescript;
	}

	public String getResultDescript() {
		return this.resultDescript;
	}

	public void setResultDescript(String resultDescript) {
		this.resultDescript = resultDescript;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEditor() {
		return this.editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getFeedback() {
		return this.feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

}