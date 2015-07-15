package tw.edu.chit.model;

import java.sql.Time;
import java.util.Date;

/**
 * AmsWorkdateId entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class AmsWorkdateData implements java.io.Serializable {

	private static final long serialVersionUID = 2303407705358506148L;

	private String idno;
	private Date wdate;
	private String dateType;
	private String type;
	private Time setIn;
	private Time setOut;
	private Time realIn;
	private Time realOut;
	private String shift;
	private String extra;

	public AmsWorkdateData() {
	}

	public AmsWorkdateData(String idno, Date wdate, String dateType,
			String type, Time setIn, Time setOut, Time realIn, Time realOut,
			String shift) {
		this.idno = idno;
		this.wdate = wdate;
		this.dateType = dateType;
		this.type = type;
		this.setIn = setIn;
		this.setOut = setOut;
		this.realIn = realIn;
		this.realOut = realOut;
		this.shift = shift;
	}

	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public Date getWdate() {
		return this.wdate;
	}

	public void setWdate(Date wdate) {
		this.wdate = wdate;
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

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AmsWorkdateData))
			return false;
		AmsWorkdateData castOther = (AmsWorkdateData) other;

		return ((this.getIdno() == castOther.getIdno()) || (this.getIdno() != null
				&& castOther.getIdno() != null && this.getIdno().equals(
				castOther.getIdno())))
				&& ((this.getWdate() == castOther.getWdate()) || (this
						.getWdate() != null
						&& castOther.getWdate() != null && this.getWdate()
						.equals(castOther.getWdate())))
				&& ((this.getDateType() == castOther.getDateType()) || (this
						.getDateType() != null
						&& castOther.getDateType() != null && this
						.getDateType().equals(castOther.getDateType())))
				&& ((this.getType() == castOther.getType()) || (this.getType() != null
						&& castOther.getType() != null && this.getType()
						.equals(castOther.getType())))
				&& ((this.getSetIn() == castOther.getSetIn()) || (this
						.getSetIn() != null
						&& castOther.getSetIn() != null && this.getSetIn()
						.equals(castOther.getSetIn())))
				&& ((this.getSetOut() == castOther.getSetOut()) || (this
						.getSetOut() != null
						&& castOther.getSetOut() != null && this.getSetOut()
						.equals(castOther.getSetOut())))
				&& ((this.getRealIn() == castOther.getRealIn()) || (this
						.getRealIn() != null
						&& castOther.getRealIn() != null && this.getRealIn()
						.equals(castOther.getRealIn())))
				&& ((this.getRealOut() == castOther.getRealOut()) || (this
						.getRealOut() != null
						&& castOther.getRealOut() != null && this.getRealOut()
						.equals(castOther.getRealOut())))
				&& ((this.getShift() == castOther.getShift()) || (this
						.getShift() != null
						&& castOther.getShift() != null && this.getShift()
						.equals(castOther.getShift())))
				&& ((this.getExtra() == castOther.getExtra()) || (this
						.getExtra() != null
						&& castOther.getExtra() != null && this.getExtra()
						.equals(castOther.getExtra())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getIdno() == null ? 0 : this.getIdno().hashCode());
		result = 37 * result
				+ (getWdate() == null ? 0 : this.getWdate().hashCode());
		result = 37 * result
				+ (getDateType() == null ? 0 : this.getDateType().hashCode());
		result = 37 * result
				+ (getType() == null ? 0 : this.getType().hashCode());
		result = 37 * result
				+ (getSetIn() == null ? 0 : this.getSetIn().hashCode());
		result = 37 * result
				+ (getSetOut() == null ? 0 : this.getSetOut().hashCode());
		result = 37 * result
				+ (getRealIn() == null ? 0 : this.getRealIn().hashCode());
		result = 37 * result
				+ (getRealOut() == null ? 0 : this.getRealOut().hashCode());
		result = 37 * result
				+ (getShift() == null ? 0 : this.getShift().hashCode());
		result = 37 * result
				+ (getExtra() == null ? 0 : this.getExtra().hashCode());
		return result;
	}

}