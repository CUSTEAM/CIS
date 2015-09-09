package tw.edu.chit.model;

/**
 * DtimeReserveTeacher generated by MyEclipse Persistence Tools
 */

public class DtimeReserveTeacher implements java.io.Serializable {

	// Fields

	private Integer oid;

	private Integer dtimeReserveOid;

	private String teachId;

	private Float hours;

	private Byte teach;

	private Byte fillscore;

	// Constructors

	/** default constructor */
	public DtimeReserveTeacher() {
	}

	/** minimal constructor */
	public DtimeReserveTeacher(Integer dtimeReserveOid, Float hours,
			Byte teach, Byte fillscore) {
		this.dtimeReserveOid = dtimeReserveOid;
		this.hours = hours;
		this.teach = teach;
		this.fillscore = fillscore;
	}

	/** full constructor */
	public DtimeReserveTeacher(Integer dtimeReserveOid, String teachId,
			Float hours, Byte teach, Byte fillscore) {
		this.dtimeReserveOid = dtimeReserveOid;
		this.teachId = teachId;
		this.hours = hours;
		this.teach = teach;
		this.fillscore = fillscore;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getDtimeReserveOid() {
		return this.dtimeReserveOid;
	}

	public void setDtimeReserveOid(Integer dtimeReserveOid) {
		this.dtimeReserveOid = dtimeReserveOid;
	}

	public String getTeachId() {
		return this.teachId;
	}

	public void setTeachId(String teachId) {
		this.teachId = teachId;
	}

	public Float getHours() {
		return this.hours;
	}

	public void setHours(Float hours) {
		this.hours = hours;
	}

	public Byte getTeach() {
		return this.teach;
	}

	public void setTeach(Byte teach) {
		this.teach = teach;
	}

	public Byte getFillscore() {
		return this.fillscore;
	}

	public void setFillscore(Byte fillscore) {
		this.fillscore = fillscore;
	}

}