package tw.edu.chit.model;

/**
 * WorkDuty entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class WorkDuty implements java.io.Serializable {

	private static final long serialVersionUID = 1712202825279601754L;

	private Integer oid;
	private String code;
	private String name;

	public WorkDuty() {
	}

	public WorkDuty(Integer oid, String code, String name) {
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