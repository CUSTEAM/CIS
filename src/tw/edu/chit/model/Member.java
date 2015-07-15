package tw.edu.chit.model;

import java.util.Date;

/**
 * Base class of Employee and Student
 * @author James Chiang
 *
 */
public class Member {
	
	private Integer oid;
    private String account; 
    private String name;	
    private String idno = "";
    private String unit2 = "";
    private String birthDate = "";
    private String priority = "";
    private String password = "";
    private String informixPass = "";


	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Member() {}
    
    public Member(String account) {
    	this.account = account;
    }

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 
	 * @return The Chinese name
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
    public String getUnit2() {
		return unit2;
	}

	public void setUnit2(String unit2) {
		this.unit2 = unit2;
	}

	/**
	 * 
	 * @return 身份證號
	 */
	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getInformixPass() {
		return informixPass;
	}

	public void setInformixPass(String informixPass) {
		this.informixPass = informixPass;
	}


}
