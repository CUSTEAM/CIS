package tw.edu.chit.model;

/**
 * FeeCode entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class FeeCode implements java.io.Serializable {

	private static final long serialVersionUID = -3289559847201647949L;

	private Integer oid;
	private String no;
	private String name;

	public FeeCode() {
	}

	public FeeCode(Integer oid, String no, String name) {
		this.oid = oid;
		this.no = no;
		this.name = name;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}