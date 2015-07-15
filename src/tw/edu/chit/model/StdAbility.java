package tw.edu.chit.model;

import java.util.Date;

public class StdAbility extends StdAbilityBase implements java.io.Serializable {

	private static final long serialVersionUID = -3937734045208078932L;

	private Integer oid;
	private String studentNo;
	private Integer abilityNo;
	private String description;
	private String levelDesc;
	private String deptDesc;
	private String modifier;
	private Date lastModified;

	public StdAbility() {
	}

	public StdAbility(Integer oid, String studentNo, Integer abilityNo,
			String description, String levelDesc, String deptDesc,
			String modifier, Date lastModified) {
		super();
		this.oid = oid;
		this.studentNo = studentNo;
		this.abilityNo = abilityNo;
		this.description = description;
		this.levelDesc = levelDesc;
		this.deptDesc = deptDesc;
		this.modifier = modifier;
		this.lastModified = lastModified;
	}

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

	public Integer getAbilityNo() {
		return this.abilityNo;
	}

	public void setAbilityNo(Integer abilityNo) {
		this.abilityNo = abilityNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLevelDesc() {
		return levelDesc;
	}

	public void setLevelDesc(String levelDesc) {
		this.levelDesc = levelDesc;
	}

	public String getDeptDesc() {
		return deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

}