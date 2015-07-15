package tw.edu.chit.model;

/**
 * StudDocAttach entity. @author MyEclipse Persistence Tools
 */

public class StudDocAttach implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String sn;
	private Short attNo;
	private String fileName;
	private Integer attOid;

	// Constructors

	/** default constructor */
	public StudDocAttach() {
	}

	/** full constructor */
	public StudDocAttach(String sn, Short attNo, String fileName, Integer attOid) {
		this.sn = sn;
		this.attNo = attNo;
		this.fileName = fileName;
		this.attOid = attOid;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getSn() {
		return this.sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Short getAttNo() {
		return this.attNo;
	}

	public void setAttNo(Short attNo) {
		this.attNo = attNo;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getAttOid() {
		return this.attOid;
	}

	public void setAttOid(Integer attOid) {
		this.attOid = attOid;
	}

}