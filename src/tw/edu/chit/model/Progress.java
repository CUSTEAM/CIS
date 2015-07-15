package tw.edu.chit.model;

import java.util.Date;

/**
 * Progress entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Progress implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String name;
	private String parameter;
	private Byte running;
	private Date ddate;

	// Constructors

	/** default constructor */
	public Progress() {
	}

	/** minimal constructor */
	public Progress(String name, Byte running, Date ddate) {
		this.name = name;
		this.running = running;
		this.ddate = ddate;
	}

	/** full constructor */
	public Progress(String name, String parameter, Byte running, Date ddate) {
		this.name = name;
		this.parameter = parameter;
		this.running = running;
		this.ddate = ddate;
	}

	// Property accessors

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

	public String getParameter() {
		return this.parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public Byte getRunning() {
		return this.running;
	}

	public void setRunning(Byte running) {
		this.running = running;
	}

	public Date getDdate() {
		return this.ddate;
	}

	public void setDdate(Date ddate) {
		this.ddate = ddate;
	}

}