package tw.edu.chit.model;

import java.util.Date;

/**
 * CsCore entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CsCore implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String note1;
	private String note2;
	private String deptNo;
	private String keyWord;
	private Integer entrance;
	private Integer s1;
	private Integer s2;
	private Integer s3;
	private Integer s4;
	private Integer s5;
	private Integer s6;
	private Integer s7;
	private Integer s8;
	private Integer s9;
	private Integer sa;
	private Integer sb;
	private String editor;
	private Date editime;

	// Constructors

	/** default constructor */
	public CsCore() {
	}

	/** minimal constructor */
	public CsCore(Integer s1, Integer s2, Integer s3, Integer s4, Integer s5,
			Integer s6, Integer s7, Integer s8, Integer s9, Integer sa,
			Integer sb, Date editime) {
		this.s1 = s1;
		this.s2 = s2;
		this.s3 = s3;
		this.s4 = s4;
		this.s5 = s5;
		this.s6 = s6;
		this.s7 = s7;
		this.s8 = s8;
		this.s9 = s9;
		this.sa = sa;
		this.sb = sb;
		this.editime = editime;
	}

	/** full constructor */
	public CsCore(String note1, String note2, String deptNo, String keyWord,
			Integer entrance, Integer s1, Integer s2, Integer s3, Integer s4,
			Integer s5, Integer s6, Integer s7, Integer s8, Integer s9,
			Integer sa, Integer sb, String editor, Date editime) {
		this.note1 = note1;
		this.note2 = note2;
		this.deptNo = deptNo;
		this.keyWord = keyWord;
		this.entrance = entrance;
		this.s1 = s1;
		this.s2 = s2;
		this.s3 = s3;
		this.s4 = s4;
		this.s5 = s5;
		this.s6 = s6;
		this.s7 = s7;
		this.s8 = s8;
		this.s9 = s9;
		this.sa = sa;
		this.sb = sb;
		this.editor = editor;
		this.editime = editime;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getNote1() {
		return this.note1;
	}

	public void setNote1(String note1) {
		this.note1 = note1;
	}

	public String getNote2() {
		return this.note2;
	}

	public void setNote2(String note2) {
		this.note2 = note2;
	}

	public String getDeptNo() {
		return this.deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public Integer getEntrance() {
		return this.entrance;
	}

	public void setEntrance(Integer entrance) {
		this.entrance = entrance;
	}

	public Integer getS1() {
		return this.s1;
	}

	public void setS1(Integer s1) {
		this.s1 = s1;
	}

	public Integer getS2() {
		return this.s2;
	}

	public void setS2(Integer s2) {
		this.s2 = s2;
	}

	public Integer getS3() {
		return this.s3;
	}

	public void setS3(Integer s3) {
		this.s3 = s3;
	}

	public Integer getS4() {
		return this.s4;
	}

	public void setS4(Integer s4) {
		this.s4 = s4;
	}

	public Integer getS5() {
		return this.s5;
	}

	public void setS5(Integer s5) {
		this.s5 = s5;
	}

	public Integer getS6() {
		return this.s6;
	}

	public void setS6(Integer s6) {
		this.s6 = s6;
	}

	public Integer getS7() {
		return this.s7;
	}

	public void setS7(Integer s7) {
		this.s7 = s7;
	}

	public Integer getS8() {
		return this.s8;
	}

	public void setS8(Integer s8) {
		this.s8 = s8;
	}

	public Integer getS9() {
		return this.s9;
	}

	public void setS9(Integer s9) {
		this.s9 = s9;
	}

	public Integer getSa() {
		return this.sa;
	}

	public void setSa(Integer sa) {
		this.sa = sa;
	}

	public Integer getSb() {
		return this.sb;
	}

	public void setSb(Integer sb) {
		this.sb = sb;
	}

	public String getEditor() {
		return this.editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public Date getEditime() {
		return this.editime;
	}

	public void setEditime(Date editime) {
		this.editime = editime;
	}

}