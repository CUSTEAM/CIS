package tw.edu.chit.model;

/**
 * Nabbr entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Nabbr implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String dept;
	private String unit;
	private String building;
	private String floor;
	private String name2;
	private String roomId;
	private String boro;
	private String remark;
	private Short seat;

	// Constructors

	/** default constructor */
	public Nabbr() {
	}

	/** minimal constructor */
	public Nabbr(String building, String floor, String roomId) {
		this.building = building;
		this.floor = floor;
		this.roomId = roomId;
	}

	/** full constructor */
	public Nabbr(String dept, String unit, String building, String floor,
			String name2, String roomId, String boro, String remark, Short seat) {
		this.dept = dept;
		this.unit = unit;
		this.building = building;
		this.floor = floor;
		this.name2 = name2;
		this.roomId = roomId;
		this.boro = boro;
		this.remark = remark;
		this.seat = seat;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getDept() {
		return this.dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getBuilding() {
		return this.building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getFloor() {
		return this.floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getName2() {
		return this.name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getRoomId() {
		return this.roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getBoro() {
		return this.boro;
	}

	public void setBoro(String boro) {
		this.boro = boro;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Short getSeat() {
		return this.seat;
	}

	public void setSeat(Short seat) {
		this.seat = seat;
	}

}