package tw.edu.chit.model;

import java.util.Date;

public class FlwWork implements java.io.Serializable {

	private static final long serialVersionUID = 8422205208304081899L;
	
	private Integer oid;
	private Date date;
	private String classNo;
	private String areaNo;
	private Integer counts;

	public FlwWork() {
	}

	public FlwWork(Date date, String classNo, String areaNo, Integer counts) {
		this.date = date;
		this.classNo = classNo;
		this.areaNo = areaNo;
		this.counts = counts;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getClassNo() {
		return this.classNo;
	}

	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}

	public String getAreaNo() {
		return this.areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	public Integer getCounts() {
		return this.counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	}

}