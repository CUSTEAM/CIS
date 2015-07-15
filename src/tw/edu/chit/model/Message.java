package tw.edu.chit.model;

import java.util.Date;

/**
 * Message entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Message implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String category;
	private String sender;
	private String content;
	private Date startDate;
	private Date dueDate;
	private String title;
	private String receiver;
	private String unit;
	private String open;

	// Constructors

	/** default constructor */
	public Message() {
	}

	/** minimal constructor */
	public Message(String category, Date startDate, Date dueDate) {
		this.category = category;
		this.startDate = startDate;
		this.dueDate = dueDate;
	}

	/** full constructor */
	public Message(String category, String sender, String content,
			Date startDate, Date dueDate, String title, String receiver,
			String unit, String open) {
		this.category = category;
		this.sender = sender;
		this.content = content;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.title = title;
		this.receiver = receiver;
		this.unit = unit;
		this.open = open;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getOpen() {
		return this.open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

}