package tw.edu.chit.model;

import java.sql.Blob;
import java.util.Date;

/**
 * Empl entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Empl extends EmplBase implements java.io.Serializable {

	private static final long serialVersionUID = -4662325041672047251L;
	
	private Integer oid;
	private String idno;
	private String cname;
	private String insno;
	private String ename;
	private String sex;
	private Date bdate;
	private String unit;
	private String unit_module;
	public String getUnit_module() {
		return unit_module;
	}

	public void setUnit_module(String unit_module) {
		this.unit_module = unit_module;
	}

	private String category;
	private String pcode;
	private String sname;
	private String telephone;
	private String pzip;
	private String paddr;
	private String czip;
	private String caddr;
	private Date startDate;
	private Date endDate;
	private String statusCause;
	private String degree;
	private String status;
	private String cellPhone;
	private String email;
	private String tutor;
	private String director;
	private Date teachStartDate;
	private String remark;
	private Date adate;
	private String workShift;
	private Blob portrait;

	// Constructors

	/** default constructor */
	public Empl() {
	}

	/** minimal constructor */
	public Empl(String idno) {
		this.idno = idno;
	}

	/** full constructor */
	public Empl(String idno, String cname, String insno, String ename,
			String sex, Date bdate, String unit, String category, String pcode,
			String sname, String telephone, String pzip, String paddr,
			String czip, String caddr, Date startDate, Date endDate,
			String statusCause, String degree, String status, String cellPhone,
			String email, String tutor, String director, Date teachStartDate,
			String remark, Date adate, Blob portrait) {
		this.idno = idno;
		this.cname = cname;
		this.insno = insno;
		this.ename = ename;
		this.sex = sex;
		this.bdate = bdate;
		this.unit = unit;
		this.category = category;
		this.pcode = pcode;
		this.sname = sname;
		this.telephone = telephone;
		this.pzip = pzip;
		this.paddr = paddr;
		this.czip = czip;
		this.caddr = caddr;
		this.startDate = startDate;
		this.endDate = endDate;
		this.statusCause = statusCause;
		this.degree = degree;
		this.status = status;
		this.cellPhone = cellPhone;
		this.email = email;
		this.tutor = tutor;
		this.director = director;
		this.teachStartDate = teachStartDate;
		this.remark = remark;
		this.adate = adate;
		this.portrait=portrait;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getInsno() {
		return this.insno;
	}

	public void setInsno(String insno) {
		this.insno = insno;
	}

	public String getEname() {
		return this.ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBdate() {
		return this.bdate;
	}

	public void setBdate(Date bdate) {
		this.bdate = bdate;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPcode() {
		return this.pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public String getSname() {
		return this.sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPzip() {
		return this.pzip;
	}

	public void setPzip(String pzip) {
		this.pzip = pzip;
	}

	public String getPaddr() {
		return this.paddr;
	}

	public void setPaddr(String paddr) {
		this.paddr = paddr;
	}

	public String getCzip() {
		return this.czip;
	}

	public void setCzip(String czip) {
		this.czip = czip;
	}

	public String getCaddr() {
		return this.caddr;
	}

	public void setCaddr(String caddr) {
		this.caddr = caddr;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatusCause() {
		return this.statusCause;
	}

	public void setStatusCause(String statusCause) {
		this.statusCause = statusCause;
	}

	public String getDegree() {
		return this.degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCellPhone() {
		return this.cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTutor() {
		return this.tutor;
	}

	public void setTutor(String tutor) {
		this.tutor = tutor;
	}

	public String getDirector() {
		return this.director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public Date getTeachStartDate() {
		return this.teachStartDate;
	}

	public void setTeachStartDate(Date teachStartDate) {
		this.teachStartDate = teachStartDate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getAdate() {
		return this.adate;
	}

	public void setAdate(Date adate) {
		this.adate = adate;
	}

	public String getWorkShift() {
		return workShift;
	}

	public void setWorkShift(String workShift) {
		this.workShift = workShift;
	}
	
	public Blob getPortrait() {
		return portrait;
	}

	public void setPortrait(Blob portrait) {
		this.portrait = portrait;
	}
	
	

}