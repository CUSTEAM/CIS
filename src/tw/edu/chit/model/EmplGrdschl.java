package tw.edu.chit.model;

/**
 * EmplGrdschl entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EmplGrdschl implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String idno;
	private String no;
	private String schoolName;
	private String chk;
	private String chkno;
	private String deptName;
	private Short sequence;

	// Constructors

	/** default constructor */
	public EmplGrdschl() {
	}

	/** full constructor */
	public EmplGrdschl(String idno, String no, String schoolName, String chk,
			String chkno, String deptName, Short sequence) {
		this.idno = idno;
		this.no = no;
		this.schoolName = schoolName;
		this.chk = chk;
		this.chkno = chkno;
		this.deptName = deptName;
		this.sequence = sequence;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getSchoolName() {
		return this.schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getChk() {
		return this.chk;
	}

	public void setChk(String chk) {
		this.chk = chk;
	}

	public String getChkno() {
		return this.chkno;
	}

	public void setChkno(String chkno) {
		this.chkno = chkno;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Short getSequence() {
		return this.sequence;
	}

	public void setSequence(Short sequence) {
		this.sequence = sequence;
	}

}