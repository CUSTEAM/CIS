package tw.edu.chit.model;

import java.util.Date;

public class LicenseCode implements java.io.Serializable {
	
	
	private Integer oid;
	private String code;
	private String name;
	private String locale;
	private String level;
	private String type;
	private String deptName;
	private String creator;
	private Date lastModified;
	
	public LicenseCode() {
	}
	
	public LicenseCode(String code) {
		this.code = code;
	}
	
	public LicenseCode(Integer code) {
		this.code =this.code.toString();
	}
	public LicenseCode(Integer oid, String code, String name, Date lastModified) {
		this.oid = oid;
		this.code = code;
		this.name = name;
		this.lastModified = lastModified;
	}

	public LicenseCode(Integer oid, String code, String name,
			String locale, String level, String type,
			String deptName, String creator, Date lastModified) {
		this.oid = oid;
		this.code = code;
		this.name = name;
		this.locale = locale;
		this.level = level;
		this.type = type;
		this.deptName = deptName;
		this.creator = creator;
		this.lastModified = lastModified;
	}
	
	/*
	private static final long serialVersionUID = -8024553326857528916L;

	private Integer oid;
	private String code;
	private String name;
	private LicenseLocale locale;
	private String level;
	private LicenseType type;
	private String deptName;
	private String creator;
	private Date lastModified;

	public LicenseCode() {
	}
	
	public LicenseCode(String code) {
		this.code = code;
	}
	
	public LicenseCode(Integer code) {
		this.code =this.code.toString();
	}

	public LicenseCode(Integer oid, String code, String name, Date lastModified) {
		this.oid = oid;
		this.code = code;
		this.name = name;
		this.lastModified = lastModified;
	}

	public LicenseCode(Integer oid, String code, String name,
			LicenseLocale locale, String level, LicenseType type,
			String deptName, String creator, Date lastModified) {
		this.oid = oid;
		this.code = code;
		this.name = name;
		this.locale = locale;
		this.level = level;
		this.type = type;
		this.deptName = deptName;
		this.creator = creator;
		this.lastModified = lastModified;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LicenseLocale getLocale() {
		return this.locale;
	}

	public void setLocale(LicenseLocale locale) {
		this.locale = locale;
	}

	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public LicenseType getType() {
		return this.type;
	}

	public void setType(LicenseType type) {
		this.type = type;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String dept) {
		this.deptName = dept;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	*/
	public Integer getOid() {
		return oid;
	}
	public void setOid(Integer oid) {
		this.oid = oid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
}