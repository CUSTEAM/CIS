package tw.edu.chit.model;

import java.util.Date;

/**
 * EpsFree entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EpsFree implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String uid;
	private String tag;
	private String title;
	private String content;
	private Date editime;
	private String file;

	// Constructors

	/** default constructor */
	public EpsFree() {
	}

	/** minimal constructor */
	public EpsFree(Date editime) {
		this.editime = editime;
	}

	/** full constructor */
	public EpsFree(String uid, String tag, String title, String content,
			Date editime, String file) {
		this.uid = uid;
		this.tag = tag;
		this.title = title;
		this.content = content;
		this.editime = editime;
		this.file = file;
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

	public String getFile() {
		return this.file;
	}

	public void setFile(String file) {
		this.file = file;
	}

}