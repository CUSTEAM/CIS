package tw.edu.chit.model;

import java.util.Date;

public class FlwStmdWorkData implements java.io.Serializable {

	private static final long serialVersionUID = 8422205208304081895L;

	private Integer oid;
	private Integer parentOid;
	private String studentNo;
	private Date lastModified;

	public FlwStmdWorkData() {
	}

	public FlwStmdWorkData(Integer oid, Integer parentOid, String studentNo,
			Date lastModified) {
		super();
		this.oid = oid;
		this.parentOid = parentOid;
		this.studentNo = studentNo;
		this.lastModified = lastModified;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getParentOid() {
		return parentOid;
	}

	public void setParentOid(Integer parentOid) {
		this.parentOid = parentOid;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

}