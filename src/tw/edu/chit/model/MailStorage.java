package tw.edu.chit.model;

import java.util.Date;

/**
 * MailStorage entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MailStorage implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String realFrom;
	private String sendFrom;
	private String subject;
	private String receiver;
	private String content;
	private Date sendDate;
	private Date realDate;
	private String realAddress;

	// Constructors

	/** default constructor */
	public MailStorage() {
	}

	/** minimal constructor */
	public MailStorage(Date realDate) {
		this.realDate = realDate;
	}

	/** full constructor */
	public MailStorage(String realFrom, String sendFrom, String subject,
			String receiver, String content, Date sendDate, Date realDate,
			String realAddress) {
		this.realFrom = realFrom;
		this.sendFrom = sendFrom;
		this.subject = subject;
		this.receiver = receiver;
		this.content = content;
		this.sendDate = sendDate;
		this.realDate = realDate;
		this.realAddress = realAddress;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getRealFrom() {
		return this.realFrom;
	}

	public void setRealFrom(String realFrom) {
		this.realFrom = realFrom;
	}

	public String getSendFrom() {
		return this.sendFrom;
	}

	public void setSendFrom(String sendFrom) {
		this.sendFrom = sendFrom;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Date getRealDate() {
		return this.realDate;
	}

	public void setRealDate(Date realDate) {
		this.realDate = realDate;
	}

	public String getRealAddress() {
		return this.realAddress;
	}

	public void setRealAddress(String realAddress) {
		this.realAddress = realAddress;
	}

}