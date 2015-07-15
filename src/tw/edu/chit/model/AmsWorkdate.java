package tw.edu.chit.model;

import java.sql.Time;

/**
 * AmsWorkdate entity. @author MyEclipse Persistence Tools
 */

public class AmsWorkdate implements java.io.Serializable {

	// Fields

	private AmsWorkdateId id;
	private String dateType;
	private String type;
	private Time setIn;
	private Time setOut;
	private Time realIn;
	private Time realOut;
	private String shift;

	// Constructors

	/** default constructor */
	public AmsWorkdate() {
	}

	/** minimal constructor */
	public AmsWorkdate(AmsWorkdateId id) {
		this.id = id;
	}

	/** full constructor */
	public AmsWorkdate(AmsWorkdateId id, String dateType, String type,
			Time setIn, Time setOut, Time realIn, Time realOut, String shift) {
		this.id = id;
		this.dateType = dateType;
		this.type = type;
		this.setIn = setIn;
		this.setOut = setOut;
		this.realIn = realIn;
		this.realOut = realOut;
		this.shift = shift;
	}

	// Property accessors

	public AmsWorkdateId getId() {
		return this.id;
	}

	public void setId(AmsWorkdateId id) {
		this.id = id;
	}

	public String getDateType() {
		return this.dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Time getSetIn() {
		return this.setIn;
	}

	public void setSetIn(Time setIn) {
		this.setIn = setIn;
	}

	public Time getSetOut() {
		return this.setOut;
	}

	public void setSetOut(Time setOut) {
		this.setOut = setOut;
	}

	public Time getRealIn() {
		return this.realIn;
	}

	public void setRealIn(Time realIn) {
		this.realIn = realIn;
	}

	public Time getRealOut() {
		return this.realOut;
	}

	public void setRealOut(Time realOut) {
		this.realOut = realOut;
	}

	public String getShift() {
		return this.shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

}