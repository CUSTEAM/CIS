package tw.edu.chit.model;

import java.util.HashSet;
import java.util.Set;

/**
 * SuggestionDept entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class SuggestionDept implements java.io.Serializable {

	private static final long serialVersionUID = -8986510573862212617L;

	private Integer oid;
	private String campus;
	private String name;

	private Set<SuggestionEmail> suggestionEmail = new HashSet<SuggestionEmail>();

	public SuggestionDept() {
	}

	public SuggestionDept(Integer oid, String campus, String name) {
		this.oid = oid;
		this.campus = campus;
		this.name = name;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getCampus() {
		return this.campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<SuggestionEmail> getSuggestionEmail() {
		return suggestionEmail;
	}

	public void setSuggestionEmail(Set<SuggestionEmail> suggestionEmail) {
		this.suggestionEmail = suggestionEmail;
	}

}