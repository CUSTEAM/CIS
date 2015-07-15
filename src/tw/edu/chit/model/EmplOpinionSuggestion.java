package tw.edu.chit.model;

import java.util.Date;

/**
 * EmplOpinionSuggestion entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class EmplOpinionSuggestion implements java.io.Serializable {

	private static final long serialVersionUID = -4143592316953050891L;

	private Integer oid;
	private String idno;
	private String email;
	private String topic;
	private Date date;
	private String target;
	private String place;
	private String content;
	private Date lastModified;

	public EmplOpinionSuggestion() {
	}

	public EmplOpinionSuggestion(Integer oid, String idno) {
		this.oid = oid;
		this.idno = idno;
	}

	public EmplOpinionSuggestion(Integer oid, String idno, String email,
			String topic, Date date, String target, String place,
			String content, Date lastModified) {
		this.oid = oid;
		this.idno = idno;
		this.email = email;
		this.topic = topic;
		this.date = date;
		this.target = target;
		this.place = place;
		this.content = content;
		this.lastModified = lastModified;
	}

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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTopic() {
		return this.topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

}