package tw.edu.chit.model;

/**
 * CodeEmpl generated by MyEclipse - Hibernate Tools
 */
public class CodeEmpl implements java.io.Serializable {

	private static final long serialVersionUID = -4037281170183614392L;

	private Integer oid;
	private String idno;
	private String name;
	private String category;
	private Short sequence;
	private String idno2;

	public CodeEmpl() {
	}

	public CodeEmpl(String idno) {
		this.idno = idno;
	}

	public CodeEmpl(String idno, String name, String category, Short sequence) {
		this.idno = idno;
		this.name = name;
		this.category = category;
		this.sequence = sequence;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Short getSequence() {
		return this.sequence;
	}

	public void setSequence(Short sequence) {
		this.sequence = sequence;
	}

	public String getIdno2() {
		return idno2;
	}

	public void setIdno2(String idno2) {
		this.idno2 = idno2;
	}

}