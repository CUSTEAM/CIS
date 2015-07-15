package tw.edu.chit.model;

/**
 * Rmark entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Rmark implements java.io.Serializable {

	// Fields

	private String studentNo;
	private String remarkName;
	private String military;
	private String certificate;

	// Constructors

	/** default constructor */
	public Rmark() {
	}

	/** minimal constructor */
	public Rmark(String studentNo) {
		this.studentNo = studentNo;
	}

	/** full constructor */
	public Rmark(String studentNo, String remarkName, String military,
			String certificate) {
		this.studentNo = studentNo;
		this.remarkName = remarkName;
		this.military = military;
		this.certificate = certificate;
	}

	// Property accessors

	public String getStudentNo() {
		return this.studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getRemarkName() {
		return this.remarkName;
	}

	public void setRemarkName(String remarkName) {
		this.remarkName = remarkName;
	}

	public String getMilitary() {
		return this.military;
	}

	public void setMilitary(String military) {
		this.military = military;
	}

	public String getCertificate() {
		return this.certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

}