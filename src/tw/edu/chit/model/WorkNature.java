package tw.edu.chit.model;

/**
 * WorkNature entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class WorkNature implements java.io.Serializable {

	private static final long serialVersionUID = -6689419439744301550L;

	private Integer oid;
	private String code;
	private String name;

	public WorkNature() {
	}

	public WorkNature(Integer oid, String code, String name) {
		this.oid = oid;
		this.code = code;
		this.name = name;
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

}