package tw.edu.chit.model;

/**
 * CounselingCode entity. @author MyEclipse Persistence Tools
 */

public class CounselingCode implements java.io.Serializable {

	// Fields

	private Integer itemNo;
	private String itype;
	private String itemName;
	private Integer sequence;

	// Constructors

	/** default constructor */
	public CounselingCode() {
	}

	/** full constructor */
	public CounselingCode(String itype, String itemName, Integer sequence) {
		this.itype = itype;
		this.itemName = itemName;
		this.sequence = sequence;
	}

	// Property accessors

	public Integer getItemNo() {
		return this.itemNo;
	}

	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}

	public String getItype() {
		return this.itype;
	}

	public void setItype(String itype) {
		this.itype = itype;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

}