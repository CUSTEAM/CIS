package tw.edu.chit.model;

/**
 * PageLink entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PageLink implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String label;
	private String url;
	private String type;
	private String notes;
	private String icon;

	// Constructors

	/** default constructor */
	public PageLink() {
	}

	/** full constructor */
	public PageLink(String label, String url, String type, String notes,
			String icon) {
		this.label = label;
		this.url = url;
		this.type = type;
		this.notes = notes;
		this.icon = icon;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}