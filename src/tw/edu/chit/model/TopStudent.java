package tw.edu.chit.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * TopStudent entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TopStudent implements java.io.Serializable {

	private static final long serialVersionUID = -4751328151661854367L;

	private Integer oid;
	private Integer parentOid;
	private String studentNo;
	private Float average;
	private Integer pos;
	
	private ClassScoreSummary classScoreSummary;

	/** default constructor */
	public TopStudent() {
	}

	/** full constructor */
	public TopStudent(String studentNo, Float average, Integer pos) {
		this.studentNo = studentNo;
		this.average = average;
		this.pos = pos;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
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

	public Float getAverage() {
		return this.average;
	}

	public void setAverage(Float average) {
		this.average = average;
	}

	public Integer getPos() {
		return this.pos;
	}

	public void setPos(Integer pos) {
		this.pos = pos;
	}

	public ClassScoreSummary getClassScoreSummary() {
		return classScoreSummary;
	}

	public void setClassScoreSummary(ClassScoreSummary classScoreSummary) {
		this.classScoreSummary = classScoreSummary;
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