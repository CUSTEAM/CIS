package tw.edu.chit.model;

import java.sql.Blob;

/**
 * StudDocUpload entity. @author MyEclipse Persistence Tools
 */

public class StudDocUpload implements java.io.Serializable {

	// Fields

	private Integer oid;
	private Integer docOid;
	private Short attNo;
	private String fileName;
	private Blob attContent;

	// Constructors

	/** default constructor */
	public StudDocUpload() {
	}

	/** minimal constructor */
	public StudDocUpload(String fileName, Blob attContent) {
		this.fileName = fileName;
		this.attContent = attContent;
	}

	/** full constructor */
	public StudDocUpload(Integer docOid, Short attNo, String fileName,
			Blob attContent) {
		this.docOid = docOid;
		this.attNo = attNo;
		this.fileName = fileName;
		this.attContent = attContent;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getDocOid() {
		return this.docOid;
	}

	public void setDocOid(Integer docOid) {
		this.docOid = docOid;
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

	public Blob getAttContent() {
		return this.attContent;
	}

	public void setAttContent(Blob attContent) {
		this.attContent = attContent;
	}

}