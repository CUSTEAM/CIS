package tw.edu.chit.model;

import java.util.Date;

/**
 * MessageFeedback entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MessageFeedback implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String sender;
	private String content;
	private Integer messageOid;
	private Date editDate;

	// Constructors

	/** default constructor */
	public MessageFeedback() {
	}

	/** minimal constructor */
	public MessageFeedback(Integer messageOid) {
		this.messageOid = messageOid;
	}

	/** full constructor */
	public MessageFeedback(String sender, String content, Integer messageOid,
			Date editDate) {
		this.sender = sender;
		this.content = content;
		this.messageOid = messageOid;
		this.editDate = editDate;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getSender() {
		return this.sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getMessageOid() {
		return this.messageOid;
	}

	public void setMessageOid(Integer messageOid) {
		this.messageOid = messageOid;
	}

	public Date getEditDate() {
		return this.editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

}