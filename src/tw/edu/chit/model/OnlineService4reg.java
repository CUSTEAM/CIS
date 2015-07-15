package tw.edu.chit.model;

import java.util.Date;

/**
 * OnlineService4reg entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class OnlineService4reg implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String studentNo;
	private String status;
	private String telphone;
	private String cellphone;
	private String address;
	private String email;
	private String docNo;
	private Date sendTime;
	private Short tcv;
	private Short tcvArmy;
	private Short tcvigr;
	private Short gcr;
	private Short cscna;
	private Short tev;
	private Short gcev;
	private String tevN;
	private String tevAk;
	private String tevPb;
	private Date expectTime;
	private Date completeTime;
	private Integer totalPay;
	private String getMethod;
	private String note;
	private String terminator;
	private Short connects;
	private String closer;

	// Constructors

	/** default constructor */
	public OnlineService4reg() {
	}

	/** full constructor */
	public OnlineService4reg(String studentNo, String status, String telphone,
			String cellphone, String address, String email, String docNo,
			Date sendTime, Short tcv, Short tcvArmy, Short tcvigr, Short gcr,
			Short cscna, Short tev, Short gcev, String tevN, String tevAk,
			String tevPb, Date expectTime, Date completeTime, Integer totalPay,
			String getMethod, String note, String terminator, Short connects,
			String closer) {
		this.studentNo = studentNo;
		this.status = status;
		this.telphone = telphone;
		this.cellphone = cellphone;
		this.address = address;
		this.email = email;
		this.docNo = docNo;
		this.sendTime = sendTime;
		this.tcv = tcv;
		this.tcvArmy = tcvArmy;
		this.tcvigr = tcvigr;
		this.gcr = gcr;
		this.cscna = cscna;
		this.tev = tev;
		this.gcev = gcev;
		this.tevN = tevN;
		this.tevAk = tevAk;
		this.tevPb = tevPb;
		this.expectTime = expectTime;
		this.completeTime = completeTime;
		this.totalPay = totalPay;
		this.getMethod = getMethod;
		this.note = note;
		this.terminator = terminator;
		this.connects = connects;
		this.closer = closer;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getStudentNo() {
		return this.studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTelphone() {
		return this.telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDocNo() {
		return this.docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Short getTcv() {
		return this.tcv;
	}

	public void setTcv(Short tcv) {
		this.tcv = tcv;
	}

	public Short getTcvArmy() {
		return this.tcvArmy;
	}

	public void setTcvArmy(Short tcvArmy) {
		this.tcvArmy = tcvArmy;
	}

	public Short getTcvigr() {
		return this.tcvigr;
	}

	public void setTcvigr(Short tcvigr) {
		this.tcvigr = tcvigr;
	}

	public Short getGcr() {
		return this.gcr;
	}

	public void setGcr(Short gcr) {
		this.gcr = gcr;
	}

	public Short getCscna() {
		return this.cscna;
	}

	public void setCscna(Short cscna) {
		this.cscna = cscna;
	}

	public Short getTev() {
		return this.tev;
	}

	public void setTev(Short tev) {
		this.tev = tev;
	}

	public Short getGcev() {
		return this.gcev;
	}

	public void setGcev(Short gcev) {
		this.gcev = gcev;
	}

	public String getTevN() {
		return this.tevN;
	}

	public void setTevN(String tevN) {
		this.tevN = tevN;
	}

	public String getTevAk() {
		return this.tevAk;
	}

	public void setTevAk(String tevAk) {
		this.tevAk = tevAk;
	}

	public String getTevPb() {
		return this.tevPb;
	}

	public void setTevPb(String tevPb) {
		this.tevPb = tevPb;
	}

	public Date getExpectTime() {
		return this.expectTime;
	}

	public void setExpectTime(Date expectTime) {
		this.expectTime = expectTime;
	}

	public Date getCompleteTime() {
		return this.completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public Integer getTotalPay() {
		return this.totalPay;
	}

	public void setTotalPay(Integer totalPay) {
		this.totalPay = totalPay;
	}

	public String getGetMethod() {
		return this.getMethod;
	}

	public void setGetMethod(String getMethod) {
		this.getMethod = getMethod;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTerminator() {
		return this.terminator;
	}

	public void setTerminator(String terminator) {
		this.terminator = terminator;
	}

	public Short getConnects() {
		return this.connects;
	}

	public void setConnects(Short connects) {
		this.connects = connects;
	}

	public String getCloser() {
		return this.closer;
	}

	public void setCloser(String closer) {
		this.closer = closer;
	}

}