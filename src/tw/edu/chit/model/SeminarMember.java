package tw.edu.chit.model;

import java.util.Date;

/**
 * SeminarMember entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SeminarMember implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String email;
	private String name;
	private String password;
	private Date lastModified;

	// Constructors

	/** default constructor */
	public SeminarMember() {
	}

	/** full constructor */
	public SeminarMember(Integer oid, String email, String name,
			String password, Date lastModified) {
		this.oid = oid;
		this.email = email;
		this.name = name;
		this.password = password;
		this.lastModified = lastModified;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

}