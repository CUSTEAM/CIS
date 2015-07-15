package tw.edu.chit.model;

/**
 * Dtimestamp entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Dtimestamp implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String cidno;
	private String sidno;
	private String dsvalue;
	private Integer dsreal;
	private Integer dsweek;
	private String dsbegin;
	private String dsend;
	private String updater;

	// Constructors

	/** default constructor */
	public Dtimestamp() {
	}

	/** full constructor */
	public Dtimestamp(String cidno, String sidno, String dsvalue,
			Integer dsreal, Integer dsweek, String dsbegin, String dsend,
			String updater) {
		this.cidno = cidno;
		this.sidno = sidno;
		this.dsvalue = dsvalue;
		this.dsreal = dsreal;
		this.dsweek = dsweek;
		this.dsbegin = dsbegin;
		this.dsend = dsend;
		this.updater = updater;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getCidno() {
		return this.cidno;
	}

	public void setCidno(String cidno) {
		this.cidno = cidno;
	}

	public String getSidno() {
		return this.sidno;
	}

	public void setSidno(String sidno) {
		this.sidno = sidno;
	}

	public String getDsvalue() {
		return this.dsvalue;
	}

	public void setDsvalue(String dsvalue) {
		this.dsvalue = dsvalue;
	}

	public Integer getDsreal() {
		return this.dsreal;
	}

	public void setDsreal(Integer dsreal) {
		this.dsreal = dsreal;
	}

	public Integer getDsweek() {
		return this.dsweek;
	}

	public void setDsweek(Integer dsweek) {
		this.dsweek = dsweek;
	}

	public String getDsbegin() {
		return this.dsbegin;
	}

	public void setDsbegin(String dsbegin) {
		this.dsbegin = dsbegin;
	}

	public String getDsend() {
		return this.dsend;
	}

	public void setDsend(String dsend) {
		this.dsend = dsend;
	}

	public String getUpdater() {
		return this.updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

}