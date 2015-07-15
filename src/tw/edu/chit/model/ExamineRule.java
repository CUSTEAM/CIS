package tw.edu.chit.model;

/**
 * ExamineRule entity. @author MyEclipse Persistence Tools
 */

public class ExamineRule implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String ruleNo;
	private Short deadline;
	private String whereClause;
	private Integer poid;
	private String comment;

	// Constructors

	/** default constructor */
	public ExamineRule() {
	}

	/** minimal constructor */
	public ExamineRule(String ruleNo, Short deadline, String whereClause,
			String comment) {
		this.ruleNo = ruleNo;
		this.deadline = deadline;
		this.whereClause = whereClause;
		this.comment = comment;
	}

	/** full constructor */
	public ExamineRule(String ruleNo, Short deadline, String whereClause,
			Integer poid, String comment) {
		this.ruleNo = ruleNo;
		this.deadline = deadline;
		this.whereClause = whereClause;
		this.poid = poid;
		this.comment = comment;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getRuleNo() {
		return this.ruleNo;
	}

	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}

	public Short getDeadline() {
		return this.deadline;
	}

	public void setDeadline(Short deadline) {
		this.deadline = deadline;
	}

	public String getWhereClause() {
		return this.whereClause;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	public Integer getPoid() {
		return this.poid;
	}

	public void setPoid(Integer poid) {
		this.poid = poid;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}