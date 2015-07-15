package tw.edu.chit.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * FailStudents1 entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class FailStudents2 implements java.io.Serializable {

	private static final long serialVersionUID = 3675045421412382537L;

	private Integer parentOid;
	private String studentNo;
	private Float totalCredits;
	private Float passCredits;
	private Float average;

	/** default constructor */
	public FailStudents2() {
	}

	public FailStudents2(String studentNo, Float totalCredits,
			Float passCredits, Float average) {
		super();
		this.studentNo = studentNo;
		this.totalCredits = totalCredits;
		this.passCredits = passCredits;
		this.average = average;
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
		return average;
	}

	public void setAverage(Float average) {
		this.average = average;
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