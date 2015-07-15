package tw.edu.chit.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * StdScore entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class StdScore implements java.io.Serializable {

	private static final long serialVersionUID = 400764482265009551L;

	private Integer parentOid;
	private String studentNo;
	private Float totalCredits;
	private Float passCredits;
	private Float average;
	private Double just;
	private ScoreStatus status;
	private Integer pos;

	public StdScore() {
	}
	
	public StdScore(String studentNo, Float totalCredits, Float passCredits,
			Float average, Double just, Integer pos) {
		this.studentNo = studentNo;
		this.totalCredits = totalCredits;
		this.passCredits = passCredits;
		this.average = average;
		this.just = just;
		this.pos = pos;
	}

	/** full constructor */
	public StdScore(String studentNo, Float totalCredits, Float passCredits,
			Float average, Double just, ScoreStatus status, Integer pos) {
		this.studentNo = studentNo;
		this.totalCredits = totalCredits;
		this.passCredits = passCredits;
		this.average = average;
		this.just = just;
		this.status = status;
		this.pos = pos;
	}

	public Integer getParentOid() {
		return this.parentOid;
	}

	public void setParentOid(Integer parentOid) {
		this.parentOid = parentOid;
	}

	public String getStudentNo() {
		return this.studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public Float getTotalCredits() {
		return this.totalCredits;
	}

	public void setTotalCredits(Float totalCredits) {
		this.totalCredits = totalCredits;
	}

	public Float getPassCredits() {
		return this.passCredits;
	}

	public void setPassCredits(Float passCredits) {
		this.passCredits = passCredits;
	}

	public Float getAverage() {
		return this.average;
	}

	public void setAverage(Float average) {
		this.average = average;
	}

	public Double getJust() {
		return just;
	}

	public void setJust(Double just) {
		this.just = just;
	}

	public ScoreStatus getStatus() {
		return status;
	}

	public void setStatus(ScoreStatus status) {
		this.status = status;
	}

	public Integer getPos() {
		return this.pos;
	}

	public void setPos(Integer pos) {
		this.pos = pos;
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}