package tw.edu.chit.model;

import java.util.Date;

/**
 * StudPublicDocExam entity. @author MyEclipse Persistence Tools
 */

public class StudPublicDocExam extends StudDocExamineBase implements java.io.Serializable {

	// Fields

	private Integer oid;
	private Integer docOid;
	private String examId;
	private String ruleNo;
	private Date examDate;
	private String examStatus;
	private String description;

	// Constructors

	/** default constructor */
	public StudPublicDocExam() {
	}

	/** minimal constructor */
	public StudPublicDocExam(Integer docOid, String examId, String ruleNo,
			Date examDate, String examStatus) {
		this.docOid = docOid;
		this.examId = examId;
		this.ruleNo = ruleNo;
		this.examDate = examDate;
		this.examStatus = examStatus;
	}

	/** full constructor */
	public StudPublicDocExam(Integer docOid, String examId, String ruleNo,
			Date examDate, String examStatus, String description) {
		this.docOid = docOid;
		this.examId = examId;
		this.ruleNo = ruleNo;
		this.examDate = examDate;
		this.examStatus = examStatus;
		this.description = description;
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

	public String getExamId() {
		return this.examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getRuleNo() {
		return this.ruleNo;
	}

	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}

	public Date getExamDate() {
		return this.examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public String getExamStatus() {
		return this.examStatus;
	}

	public void setExamStatus(String examStatus) {
		this.examStatus = examStatus;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}