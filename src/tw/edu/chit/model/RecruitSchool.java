package tw.edu.chit.model;

/**
 * RecruitSchool entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RecruitSchool implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String no;
	private String name;
	private String localNo;
	private String typeNo;
	private String classNo;
	private String serialNo;
	private String zip;
	private String address;
	private String url;
	private String tel;
	private String connector;

	// Constructors

	/** default constructor */
	public RecruitSchool() {
	}
	
	public RecruitSchool(String no) {
		this.no = no;
	}

	/** full constructor */
	public RecruitSchool(String no, String name, String localNo, String typeNo,
			String classNo, String serialNo, String zip, String address,
			String url, String tel, String connector) {
		this.no = no;
		this.name = name;
		this.localNo = localNo;
		this.typeNo = typeNo;
		this.classNo = classNo;
		this.serialNo = serialNo;
		this.zip = zip;
		this.address = address;
		this.url = url;
		this.tel = tel;
		this.connector = connector;
	}

	// Property accessors

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

	public String getLocalNo() {
		return this.localNo;
	}

	public void setLocalNo(String localNo) {
		this.localNo = localNo;
	}

	public String getTypeNo() {
		return this.typeNo;
	}

	public void setTypeNo(String typeNo) {
		this.typeNo = typeNo;
	}

	public String getClassNo() {
		return this.classNo;
	}

	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}

	public String getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getConnector() {
		return this.connector;
	}

	public void setConnector(String connector) {
		this.connector = connector;
	}

}