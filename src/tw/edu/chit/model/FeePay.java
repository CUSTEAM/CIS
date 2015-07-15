package tw.edu.chit.model;

/**
 * FeePay entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class FeePay extends FeePayBase implements java.io.Serializable {

	private static final long serialVersionUID = 7230455694488796046L;

	private Integer oid;
	private String kind;
	private String departClass;
	private String fcode;
	private Integer money;

	public FeePay() {
	}

	public FeePay(Integer oid, String fcode) {
		this.oid = oid;
		this.fcode = fcode;
	}

	public FeePay(Integer oid, String kind, String departClass, String fcode,
			Integer money) {
		this.oid = oid;
		this.kind = kind;
		this.departClass = departClass;
		this.fcode = fcode;
		this.money = money;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getKind() {
		return this.kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getDepartClass() {
		return this.departClass;
	}

	public void setDepartClass(String departClass) {
		this.departClass = departClass;
	}

	public String getFcode() {
		return this.fcode;
	}

	public void setFcode(String fcode) {
		this.fcode = fcode;
	}

	public Integer getMoney() {
		return this.money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

}