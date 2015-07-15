package tw.edu.chit.model;

/**
 * SurveyAnswer entity. @author MyEclipse Persistence Tools
 */

public class SurveyAnswer implements java.io.Serializable {

	// Fields

	private Integer oid;
	private Integer surveyId;
	private String sessionId;
	private Integer questionId;
	private String answer;
	private Short isChild;

	// Constructors

	/** default constructor */
	public SurveyAnswer() {
	}

	/** minimal constructor */
	public SurveyAnswer(Integer surveyId, String sessionId, Integer questionId,
			Short isChild) {
		this.surveyId = surveyId;
		this.sessionId = sessionId;
		this.questionId = questionId;
		this.isChild = isChild;
	}

	/** full constructor */
	public SurveyAnswer(Integer surveyId, String sessionId, Integer questionId,
			String answer, Short isChild) {
		this.surveyId = surveyId;
		this.sessionId = sessionId;
		this.questionId = questionId;
		this.answer = answer;
		this.isChild = isChild;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getSurveyId() {
		return this.surveyId;
	}

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Integer getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Short getIsChild() {
		return this.isChild;
	}

	public void setIsChild(Short isChild) {
		this.isChild = isChild;
	}

}