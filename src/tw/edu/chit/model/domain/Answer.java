package tw.edu.chit.model.domain;

import java.util.Date;

public class Answer {
	private Integer qid;	//question
	private Integer cid;	//question content id
	private String answer;
	private String qtype;
	private boolean isChild;
	
	/** default constructor */
	public Answer() {
	}

	/** minimal constructor */
	public Answer(Integer qid, String answer) {
		this.qid = qid;
		this.answer = answer;
	}

	public Answer(Integer qid, Integer cid, String answer, boolean isChild) {
		this.qid = qid;
		this.cid = cid;
		this.answer = answer;
		this.isChild = isChild;
	}

	public Integer getQid() {
		return this.qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	public Integer getCid() {
		return this.cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getQtype() {
		return this.qtype;
	}

	public void setQtype(String qtype) {
		this.qtype = qtype;
	}
	
	public boolean getIsChild() {
		return isChild;
	}

	public void setIsChild(boolean isChild) {
		this.isChild = isChild;
	}


}
