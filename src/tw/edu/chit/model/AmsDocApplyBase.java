package tw.edu.chit.model;

public class AmsDocApplyBase {
	private String cname;	//Chinese name of applier
	private String docTypeName;
	private String statusName;
	private String askLeaveName;
	private String fscname;	//agent's Chinese name
	private String startYear;
	private String startMonth;
	private String startDay;
	private String startHour;
	private String startMinute;
	private String startSecond;
	private String endYear;
	private String endMonth;
	private String endDay;
	private String endHour;
	private String endMinute;
	private String endSecond;
	private boolean isExpired;
	
	private AmsDocApply revokedDoc;
	
	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getDocTypeName() {
		return this.docTypeName;
	}

	public void setDocTypeName(String docTypeName) {
		this.docTypeName = docTypeName;
	}

	public String getStatusName() {
		return this.statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getAskLeaveName() {
		return this.askLeaveName;
	}

	public void setAskLeaveName(String askLeaveName) {
		this.askLeaveName = askLeaveName;
	}

	public String getFscname() {
		return this.fscname;
	}

	public void setFscname(String fscname) {
		this.fscname = fscname;
	}

	public String getStartYear() {
		return this.startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getStartMonth() {
		return this.startMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public String getStartDay() {
		return this.startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public String getStartHour() {
		return this.startHour;
	}

	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}

	public String getStartMinute() {
		return this.startMinute;
	}

	public void setStartMinute(String startMinute) {
		this.startMinute = startMinute;
	}

	public String getStartSecond() {
		return this.startSecond;
	}

	public void setStartSecond(String startSecond) {
		this.startSecond = startSecond;
	}

	public String getEndYear() {
		return this.endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public String getEndMonth() {
		return this.endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public String getEndDay() {
		return this.endDay;
	}

	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	public String getEndHour() {
		return this.endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}

	public String getEndMinute() {
		return this.endMinute;
	}

	public void setEndMinute(String endMinute) {
		this.endMinute = endMinute;
	}

	public String getEndSecond() {
		return this.endSecond;
	}

	public void setEndSecond(String endSecond) {
		this.endSecond = endSecond;
	}

	public AmsDocApply getRevokedDoc() {
		return this.revokedDoc;
	}

	public void setRevokedDoc(AmsDocApply revokedDoc) {
		this.revokedDoc = revokedDoc;
	}
	
	public boolean getIsExpired() {
		return this.isExpired;
	}

	public void setIsExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}


}
