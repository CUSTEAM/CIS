package tw.edu.chit.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * SuggestionEmail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class SuggestionEmail implements java.io.Serializable {

	private static final long serialVersionUID = -8507557254464621320L;

	private Integer parentOid;
	private String name;
	private String email;

	public SuggestionEmail() {
	}

	public SuggestionEmail(Integer parentOid, String name, String email) {
		super();
		this.parentOid = parentOid;
		this.name = name;
		this.email = email;
	}

	public Integer getParentOid() {
		return this.parentOid;
	}

	public void setParentOid(Integer parentOid) {
		this.parentOid = parentOid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}