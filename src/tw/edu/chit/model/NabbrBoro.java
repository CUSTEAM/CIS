package tw.edu.chit.model;

import java.util.Date;

/**
 * NabbrBoro entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class NabbrBoro implements java.io.Serializable {

	// Fields

	private Integer oid;
	private Date boroDate;
	private String begin;
	private String end;
	private String place;
	private Integer boroUnit;
	private String boroUser;
	private String checker;
	private String boroTel;
	private String boroMobile;
	private Date sendDate;
	private Integer heads;
	private String title;
	private String remark;
	private String checkIn;

	// Constructors

	/** default constructor */
	public NabbrBoro() {
	}

	/** minimal constructor */
	public NabbrBoro(Date boroDate) {
		this.boroDate = boroDate;
	}

	/** full constructor */
	public NabbrBoro(Date boroDate, String begin, String end, String place,
			Integer boroUnit, String boroUser, String checker, String boroTel,
			String boroMobile, Date sendDate, Integer heads, String title,
			String remark, String checkIn) {
		this.boroDate = boroDate;
		this.begin = begin;
		this.end = end;
		this.place = place;
		this.boroUnit = boroUnit;
		this.boroUser = boroUser;
		this.checker = checker;
		this.boroTel = boroTel;
		this.boroMobile = boroMobile;
		this.sendDate = sendDate;
		this.heads = heads;
		this.title = title;
		this.remark = remark;
		this.checkIn = checkIn;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Date getBoroDate() {
		return this.boroDate;
	}

	public void setBoroDate(Date boroDate) {
		this.boroDate = boroDate;
	}

	public String getBegin() {
		return this.begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return this.end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Integer getBoroUnit() {
		return this.boroUnit;
	}

	public void setBoroUnit(Integer boroUnit) {
		this.boroUnit = boroUnit;
	}

	public String getBoroUser() {
		return this.boroUser;
	}

	public void setBoroUser(String boroUser) {
		this.boroUser = boroUser;
	}

	public String getChecker() {
		return this.checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public String getBoroTel() {
		return this.boroTel;
	}

	public void setBoroTel(String boroTel) {
		this.boroTel = boroTel;
	}

	public String getBoroMobile() {
		return this.boroMobile;
	}

	public void setBoroMobile(String boroMobile) {
		this.boroMobile = boroMobile;
	}

	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Integer getHeads() {
		return this.heads;
	}

	public void setHeads(Integer heads) {
		this.heads = heads;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCheckIn() {
		return this.checkIn;
	}

	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}

}