package tw.edu.chit.model;

/**
 * Parameter entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Parameter implements java.io.Serializable {

	private static final long serialVersionUID = 6046581745895000952L;

	private Integer oid;
	private String name;
	private String value;
	private String category;
	private String type;
	private String notes;

	public Parameter() {
	}

	public Parameter(String name) {
		this.name = name;
	}

	public Parameter(String name, String value, String category, String type,
			String notes) {
		this.name = name;
		this.value = value;
		this.category = category;
		this.type = type;
		this.notes = notes;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
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

}