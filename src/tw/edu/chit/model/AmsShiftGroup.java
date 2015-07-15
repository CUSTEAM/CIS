package tw.edu.chit.model;

import java.util.Date;

/**
 * AmsShiftGroup entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AmsShiftGroup implements java.io.Serializable {

	// Fields

	private Integer id;
	private String oldGroup;
	private String newGroup;
	private Date startDate;
	private Date endDate;

	// Constructors

	/** default constructor */
	public AmsShiftGroup() {
	}

	/** full constructor */
	public AmsShiftGroup(String oldGroup, String newGroup, Date startDate,
			Date endDate) {
		this.oldGroup = oldGroup;
		this.newGroup = newGroup;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOldGroup() {
		return this.oldGroup;
	}

	public void setOldGroup(String oldGroup) {
		this.oldGroup = oldGroup;
	}

	public String getNewGroup() {
		return this.newGroup;
	}

	public void setNewGroup(String newGroup) {
		this.newGroup = newGroup;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}