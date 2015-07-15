package tw.edu.chit.model;

/**
 * SeldExam entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SeldExam implements java.io.Serializable {

	// Fields

	private Integer oid;
	private Integer seldOid;
	private Integer dilgPeriod;
	private Integer dtimeOid;

	// Constructors

	/** default constructor */
	public SeldExam() {
	}

	/** full constructor */
	public SeldExam(Integer seldOid, Integer dilgPeriod, Integer dtimeOid) {
		this.seldOid = seldOid;
		this.dilgPeriod = dilgPeriod;
		this.dtimeOid = dtimeOid;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getSeldOid() {
		return this.seldOid;
	}

	public void setSeldOid(Integer seldOid) {
		this.seldOid = seldOid;
	}

	public Integer getDilgPeriod() {
		return this.dilgPeriod;
	}

	public void setDilgPeriod(Integer dilgPeriod) {
		this.dilgPeriod = dilgPeriod;
	}

	public Integer getDtimeOid() {
		return this.dtimeOid;
	}

	public void setDtimeOid(Integer dtimeOid) {
		this.dtimeOid = dtimeOid;
	}

}