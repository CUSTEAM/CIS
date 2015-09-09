package tw.edu.chit.model;

/**
 * ClassInCharge generated by MyEclipse - Hibernate Tools
 */
public class ClassInCharge implements java.io.Serializable {

	private static final long serialVersionUID = -2203609361444544206L;

	private Integer oid;
	private Integer empOid;
	private String classNo;
	private Integer classOid;
	private String moduleOids;

	public ClassInCharge() {
	}

	public ClassInCharge(Integer oid, Integer empOid, String classNo) {
		this.oid = oid;
		this.empOid = empOid;
		this.classNo = classNo;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getEmpOid() {
		return this.empOid;
	}

	public void setEmpOid(Integer empOid) {
		this.empOid = empOid;
	}

	public String getClassNo() {
		return this.classNo;
	}

	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}

	public Integer getClassOid() {
		return classOid;
	}

	public void setClassOid(Integer classOid) {
		this.classOid = classOid;
	}

	public String getModuleOids() {
		return moduleOids;
	}

	public void setModuleOids(String moduleOids) {
		this.moduleOids = moduleOids;
	}

}