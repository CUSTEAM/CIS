package tw.edu.chit.model;

/**
 * AbilityExamine entity.
 * 
 * 
 * @author MyEclipse Persistence Tools
 */
public class AbilityExamine implements java.io.Serializable {

	private static final long serialVersionUID = -6835584220021324809L;

	private Integer oid;
	private Integer schoolYear;
	private Integer no;
	private String name;

	public AbilityExamine() {
	}

	public AbilityExamine(Integer schoolYear, Integer no) {
		this.schoolYear = schoolYear;
		this.no = no;
	}

	public AbilityExamine(Integer schoolYear, Integer no, String name) {
		this.schoolYear = schoolYear;
		this.no = no;
		this.name = name;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getSchoolYear() {
		return this.schoolYear;
	}

	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Integer getNo() {
		return this.no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}