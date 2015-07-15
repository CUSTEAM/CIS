package tw.edu.chit.model.report;

public class ScoreNotUpload {
	private String departClass;
	private String deptClassName;
	private String cscode;
	private String subjectName;
	private String techId;
	private String cname;
	private String memo;
	/**
	 * @param departClass
	 * @param deptClassName
	 * @param cscode
	 * @param subjectName
	 * @param techId
	 * @param cname
	 */
	public ScoreNotUpload(String departClass, String deptClassName, String cscode, String subjectName, String techId, String cname, String memo) {
		super();
		this.departClass = departClass;
		this.deptClassName = deptClassName;
		this.cscode = cscode;
		this.subjectName = subjectName;
		this.techId = techId;
		this.cname = cname;
		this.memo = memo;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCscode() {
		return cscode;
	}
	public void setCscode(String cscode) {
		this.cscode = cscode;
	}
	public String getDepartClass() {
		return departClass;
	}
	public void setDepartClass(String departClass) {
		this.departClass = departClass;
	}
	public String getDeptClassName() {
		return deptClassName;
	}
	public void setDeptClassName(String deptClassName) {
		this.deptClassName = deptClassName;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getTechId() {
		return techId;
	}
	public void setTechId(String techId) {
		this.techId = techId;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
