package tw.edu.chit.model;

/**
 * EpsUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EpsUser implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String path;
	private String uid;
	private String siteName;
	private String siteDescript;
	private String template;
	private String banner;
	private Integer headerStyle;
	private Integer footStyle;
	private Integer counter;
	private String timer;

	// Constructors

	/** default constructor */
	public EpsUser() {
	}

	/** minimal constructor */
	public EpsUser(String uid) {
		this.uid = uid;
	}

	/** full constructor */
	public EpsUser(String path, String uid, String siteName,
			String siteDescript, String template, String banner,
			Integer headerStyle, Integer footStyle, Integer counter,
			String timer) {
		this.path = path;
		this.uid = uid;
		this.siteName = siteName;
		this.siteDescript = siteDescript;
		this.template = template;
		this.banner = banner;
		this.headerStyle = headerStyle;
		this.footStyle = footStyle;
		this.counter = counter;
		this.timer = timer;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getSiteName() {
		return this.siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteDescript() {
		return this.siteDescript;
	}

	public void setSiteDescript(String siteDescript) {
		this.siteDescript = siteDescript;
	}

	public String getTemplate() {
		return this.template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getBanner() {
		return this.banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public Integer getHeaderStyle() {
		return this.headerStyle;
	}

	public void setHeaderStyle(Integer headerStyle) {
		this.headerStyle = headerStyle;
	}

	public Integer getFootStyle() {
		return this.footStyle;
	}

	public void setFootStyle(Integer footStyle) {
		this.footStyle = footStyle;
	}

	public Integer getCounter() {
		return this.counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}

	public String getTimer() {
		return this.timer;
	}

	public void setTimer(String timer) {
		this.timer = timer;
	}

}