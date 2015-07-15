package tw.edu.chit.model;

import java.util.Date;

/**
 * EpsPages entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EpsPages implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String uid;
	private String tag;
	private Integer tagId;
	private String title;
	private String content;
	private Date editime;

	// Constructors

	/** default constructor */
	public EpsPages() {
	}

	/** minimal constructor */
	public EpsPages(Date editime) {
		this.editime = editime;
	}

	/** full constructor */
	public EpsPages(String uid, String tag, Integer tagId, String title,
			String content, Date editime) {
		this.uid = uid;
		this.tag = tag;
		this.tagId = tagId;
		this.title = title;
		this.content = content;
		this.editime = editime;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Integer getTagId() {
		return this.tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getEditime() {
		return this.editime;
	}

	public void setEditime(Date editime) {
		this.editime = editime;
	}

}