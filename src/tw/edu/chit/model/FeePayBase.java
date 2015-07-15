package tw.edu.chit.model;

import java.io.Serializable;

public class FeePayBase implements Serializable {

	private static final long serialVersionUID = -3894989668304019643L;

	private String departClassName;
	private String kindName;
	private String feeCodeName;

	public FeePayBase() {
	}

	public String getDepartClassName() {
		return departClassName;
	}

	public void setDepartClassName(String departClassName) {
		this.departClassName = departClassName;
	}

	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public String getFeeCodeName() {
		return feeCodeName;
	}

	public void setFeeCodeName(String feeCodeName) {
		this.feeCodeName = feeCodeName;
	}

}
