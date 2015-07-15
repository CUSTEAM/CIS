package tw.edu.chit.model;

import java.io.Serializable;
import java.sql.Blob;

public class StdImage implements Serializable {

	private static final long serialVersionUID = 6044475639086907428L;

	private Integer oid;
	private String studentNo;
	private Blob image;
	//private Student student;

	public StdImage() {
	}

	public StdImage(Blob image) {
		this.image = image;
	}
	/*
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	*/
	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

}
