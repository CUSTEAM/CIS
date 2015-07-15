package tw.edu.chit.model;

import java.util.Date;

/**
 * LiteracyType entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class LiteracyType implements java.io.Serializable {

	private static final long serialVersionUID = 8678766008927858934L;

	private Integer oid;
	private String code;
	private String name;
	private String modifier;
	private Date lastModified;

	public LiteracyType() {
	}

	public LiteracyType(Integer oid, String code, String name) {
		this.oid = oid;
		this.code = code;
		this.name = name;
	}

	public LiteracyType(Integer oid, String code, String name, String modifier,
			Date lastModified) {
		this.oid = oid;
		this.code = code;
		this.name = name;
		this.modifier = modifier;
		this.lastModified = lastModified;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

}