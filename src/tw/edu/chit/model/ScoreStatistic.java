package tw.edu.chit.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ScoreStatistic implements java.io.Serializable {

	private static final long serialVersionUID = -6336370715155710537L;

	private Integer parentOid;
	private String cscode;
	private Integer pass;
	private Integer fail;
	private Float average;
	private Integer range1;
	private Integer range2;
	private Integer range3;
	private Integer range4;
	private Integer range5;
	
	public ScoreStatistic() {
	}

	public ScoreStatistic(String cscode) {
		super();
		this.cscode = cscode;
	}

	public Integer getParentOid() {
		return parentOid;
	}

	public void setParentOid(Integer parentOid) {
		this.parentOid = parentOid;
	}

	public String getCscode() {
		return cscode;
	}

	public void setCscode(String cscode) {
		this.cscode = cscode;
	}

	public Integer getPass() {
		return pass;
	}

	public void setPass(Integer pass) {
		this.pass = pass;
	}

	public Integer getFail() {
		return fail;
	}

	public void setFail(Integer fail) {
		this.fail = fail;
	}

	public Float getAverage() {
		return average;
	}

	public void setAverage(Float average) {
		this.average = average;
	}

	public Integer getRange1() {
		return range1;
	}

	public void setRange1(Integer range1) {
		this.range1 = range1;
	}

	public Integer getRange2() {
		return range2;
	}

	public void setRange2(Integer range2) {
		this.range2 = range2;
	}

	public Integer getRange3() {
		return range3;
	}

	public void setRange3(Integer range3) {
		this.range3 = range3;
	}

	public Integer getRange4() {
		return range4;
	}

	public void setRange4(Integer range4) {
		this.range4 = range4;
	}

	public Integer getRange5() {
		return range5;
	}

	public void setRange5(Integer range5) {
		this.range5 = range5;
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
