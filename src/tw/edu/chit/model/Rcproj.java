package tw.edu.chit.model;

import java.util.Date;

/**
 * Rcproj entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Rcproj implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String idno;
	private Short schoolYear;
	private String projno;
	private String projname;
	private String kindid;
	private String typeid;
	private String bdate;
	private String edate;
	private String jobid;
	private String money;
	private String budgetid1;
	private String unitname;
	private String budgetid2;
	private String favorunit;
	private String authorunit1;
	private String authorunit2;
	private String coopunit1;
	private String coopunit2;
	private Date lastModified;
	private String intor;
	private String GMoney;
	private String BMoney;
	private String OMoney;
	private String SMoney;
	private String fullTime;
	private String partTime;
	private String GTrainee;
	private String BTrainee;
	private String OTrainee;
	private String turnIn;
	private String turnInG;
	private String turnInB;
	private String turnInO;
	private String turnOut;
	private String turnOutG;
	private String turnOutB;
	private String turnOutO;
	private String approve;
	private String approveTemp;
	private String budgetidState;

	// Constructors

	/** default constructor */
	public Rcproj() {
	}

	/** minimal constructor */
	public Rcproj(String idno, Date lastModified) {
		this.idno = idno;
		this.lastModified = lastModified;
	}

	/** full constructor */
	public Rcproj(String idno, Short schoolYear, String projno,
			String projname, String kindid, String typeid, String bdate,
			String edate, String jobid, String money, String budgetid1,
			String unitname, String budgetid2, String favorunit,
			String authorunit1, String authorunit2, String coopunit1,
			String coopunit2, Date lastModified, String intor, String GMoney,
			String BMoney, String OMoney, String SMoney, String fullTime,
			String partTime, String GTrainee, String BTrainee, String OTrainee,
			String turnIn, String turnInG, String turnInB, String turnInO,
			String turnOut, String turnOutG, String turnOutB, String turnOutO,
			String approve, String approveTemp, String budgetidState) {
		this.idno = idno;
		this.schoolYear = schoolYear;
		this.projno = projno;
		this.projname = projname;
		this.kindid = kindid;
		this.typeid = typeid;
		this.bdate = bdate;
		this.edate = edate;
		this.jobid = jobid;
		this.money = money;
		this.budgetid1 = budgetid1;
		this.unitname = unitname;
		this.budgetid2 = budgetid2;
		this.favorunit = favorunit;
		this.authorunit1 = authorunit1;
		this.authorunit2 = authorunit2;
		this.coopunit1 = coopunit1;
		this.coopunit2 = coopunit2;
		this.lastModified = lastModified;
		this.intor = intor;
		this.GMoney = GMoney;
		this.BMoney = BMoney;
		this.OMoney = OMoney;
		this.SMoney = SMoney;
		this.fullTime = fullTime;
		this.partTime = partTime;
		this.GTrainee = GTrainee;
		this.BTrainee = BTrainee;
		this.OTrainee = OTrainee;
		this.turnIn = turnIn;
		this.turnInG = turnInG;
		this.turnInB = turnInB;
		this.turnInO = turnInO;
		this.turnOut = turnOut;
		this.turnOutG = turnOutG;
		this.turnOutB = turnOutB;
		this.turnOutO = turnOutO;
		this.approve = approve;
		this.approveTemp = approveTemp;
		this.budgetidState = budgetidState;
	}

	// Property accessors

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

	public Short getSchoolYear() {
		return this.schoolYear;
	}

	public void setSchoolYear(Short schoolYear) {
		this.schoolYear = schoolYear;
	}

	public String getProjno() {
		return this.projno;
	}

	public void setProjno(String projno) {
		this.projno = projno;
	}

	public String getProjname() {
		return this.projname;
	}

	public void setProjname(String projname) {
		this.projname = projname;
	}

	public String getKindid() {
		return this.kindid;
	}

	public void setKindid(String kindid) {
		this.kindid = kindid;
	}

	public String getTypeid() {
		return this.typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public String getBdate() {
		return this.bdate;
	}

	public void setBdate(String bdate) {
		this.bdate = bdate;
	}

	public String getEdate() {
		return this.edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public String getJobid() {
		return this.jobid;
	}

	public void setJobid(String jobid) {
		this.jobid = jobid;
	}

	public String getMoney() {
		return this.money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getBudgetid1() {
		return this.budgetid1;
	}

	public void setBudgetid1(String budgetid1) {
		this.budgetid1 = budgetid1;
	}

	public String getUnitname() {
		return this.unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public String getBudgetid2() {
		return this.budgetid2;
	}

	public void setBudgetid2(String budgetid2) {
		this.budgetid2 = budgetid2;
	}

	public String getFavorunit() {
		return this.favorunit;
	}

	public void setFavorunit(String favorunit) {
		this.favorunit = favorunit;
	}

	public String getAuthorunit1() {
		return this.authorunit1;
	}

	public void setAuthorunit1(String authorunit1) {
		this.authorunit1 = authorunit1;
	}

	public String getAuthorunit2() {
		return this.authorunit2;
	}

	public void setAuthorunit2(String authorunit2) {
		this.authorunit2 = authorunit2;
	}

	public String getCoopunit1() {
		return this.coopunit1;
	}

	public void setCoopunit1(String coopunit1) {
		this.coopunit1 = coopunit1;
	}

	public String getCoopunit2() {
		return this.coopunit2;
	}

	public void setCoopunit2(String coopunit2) {
		this.coopunit2 = coopunit2;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getIntor() {
		return this.intor;
	}

	public void setIntor(String intor) {
		this.intor = intor;
	}

	public String getGMoney() {
		return this.GMoney;
	}

	public void setGMoney(String GMoney) {
		this.GMoney = GMoney;
	}

	public String getBMoney() {
		return this.BMoney;
	}

	public void setBMoney(String BMoney) {
		this.BMoney = BMoney;
	}

	public String getOMoney() {
		return this.OMoney;
	}

	public void setOMoney(String OMoney) {
		this.OMoney = OMoney;
	}

	public String getSMoney() {
		return this.SMoney;
	}

	public void setSMoney(String SMoney) {
		this.SMoney = SMoney;
	}

	public String getFullTime() {
		return this.fullTime;
	}

	public void setFullTime(String fullTime) {
		this.fullTime = fullTime;
	}

	public String getPartTime() {
		return this.partTime;
	}

	public void setPartTime(String partTime) {
		this.partTime = partTime;
	}

	public String getGTrainee() {
		return this.GTrainee;
	}

	public void setGTrainee(String GTrainee) {
		this.GTrainee = GTrainee;
	}

	public String getBTrainee() {
		return this.BTrainee;
	}

	public void setBTrainee(String BTrainee) {
		this.BTrainee = BTrainee;
	}

	public String getOTrainee() {
		return this.OTrainee;
	}

	public void setOTrainee(String OTrainee) {
		this.OTrainee = OTrainee;
	}

	public String getTurnIn() {
		return this.turnIn;
	}

	public void setTurnIn(String turnIn) {
		this.turnIn = turnIn;
	}

	public String getTurnInG() {
		return this.turnInG;
	}

	public void setTurnInG(String turnInG) {
		this.turnInG = turnInG;
	}

	public String getTurnInB() {
		return this.turnInB;
	}

	public void setTurnInB(String turnInB) {
		this.turnInB = turnInB;
	}

	public String getTurnInO() {
		return this.turnInO;
	}

	public void setTurnInO(String turnInO) {
		this.turnInO = turnInO;
	}

	public String getTurnOut() {
		return this.turnOut;
	}

	public void setTurnOut(String turnOut) {
		this.turnOut = turnOut;
	}

	public String getTurnOutG() {
		return this.turnOutG;
	}

	public void setTurnOutG(String turnOutG) {
		this.turnOutG = turnOutG;
	}

	public String getTurnOutB() {
		return this.turnOutB;
	}

	public void setTurnOutB(String turnOutB) {
		this.turnOutB = turnOutB;
	}

	public String getTurnOutO() {
		return this.turnOutO;
	}

	public void setTurnOutO(String turnOutO) {
		this.turnOutO = turnOutO;
	}

	public String getApprove() {
		return this.approve;
	}

	public void setApprove(String approve) {
		this.approve = approve;
	}

	public String getApproveTemp() {
		return this.approveTemp;
	}

	public void setApproveTemp(String approveTemp) {
		this.approveTemp = approveTemp;
	}

	public String getBudgetidState() {
		return this.budgetidState;
	}

	public void setBudgetidState(String budgetidState) {
		this.budgetidState = budgetidState;
	}

}