package tw.edu.chit.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class AmsMeetingData implements Serializable {

	private static final long serialVersionUID = 3795904628480762593L;

	private Integer meetingOid;
	private String idno;
	private String emplName; // 省去Empl找Name
	private String category;
	private String unit;
	private String status;
	private String sn;

	public AmsMeetingData() {
	}

	public Integer getMeetingOid() {
		return meetingOid;
	}

	public void setMeetingOid(Integer meetingOid) {
		this.meetingOid = meetingOid;
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getEmplName() {
		return emplName;
	}

	public void setEmplName(String emplName) {
		this.emplName = emplName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
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
