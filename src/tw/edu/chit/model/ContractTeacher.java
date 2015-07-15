package tw.edu.chit.model;

public class ContractTeacher implements java.io.Serializable {

	private static final long serialVersionUID = 8770210013764794342L;

	private Integer oid;
	private String schoolYear;
	private String schoolTerm;
	private String idno;

	public ContractTeacher() {
	}

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}

	public String getSchoolTerm() {
		return schoolTerm;
	}

	public void setSchoolTerm(String schoolTerm) {
		this.schoolTerm = schoolTerm;
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

}