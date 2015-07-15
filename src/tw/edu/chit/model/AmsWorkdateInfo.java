package tw.edu.chit.model;

public class AmsWorkdateInfo extends AmsWorkdateData {

	private static final long serialVersionUID = -5048071898843941141L;

	private String wdateInfo;
	private String setInInfo;
	private String setOutInfo;
	private String realInInfo;
	private String realOutInfo;
	private String meetingInfo;
	private String workhardInfo;

	private String inDelay; // 遲到訊息
	private String outEarly; // 早退/加班訊息
	private String onDelay = "0"; // 是否遲到?
	private String onEarly = "0"; // 是否早退?
	private String workHard = "0"; // 例假日上班
	private String meeting = "0"; // 是否有重要集會?
	private String holiday = "0"; // 
	private String holidayType; // H or W

	private String commend;
	private String commendDetail; // 狀態明細
	private String status = ""; // 請假狀態

	public AmsWorkdateInfo() {
	}

	public AmsWorkdateInfo(String wdateInfo, String setInInfo,
			String setOutInfo, String realInInfo, String realOutInfo) {
		super();
		this.wdateInfo = wdateInfo;
		this.setInInfo = setInInfo;
		this.setOutInfo = setOutInfo;
		this.realInInfo = realInInfo;
		this.realOutInfo = realOutInfo;
	}

	public String getWdateInfo() {
		return wdateInfo;
	}

	public void setWdateInfo(String wdateInfo) {
		this.wdateInfo = wdateInfo;
	}

	public String getSetInInfo() {
		return setInInfo;
	}

	public void setSetInInfo(String setInInfo) {
		this.setInInfo = setInInfo;
	}

	public String getSetOutInfo() {
		return setOutInfo;
	}

	public void setSetOutInfo(String setOutInfo) {
		this.setOutInfo = setOutInfo;
	}

	public String getRealInInfo() {
		return realInInfo;
	}

	public void setRealInInfo(String realInInfo) {
		this.realInInfo = realInInfo;
	}

	public String getRealOutInfo() {
		return realOutInfo;
	}

	public void setRealOutInfo(String realOutInfo) {
		this.realOutInfo = realOutInfo;
	}

	public String getMeetingInfo() {
		return meetingInfo;
	}

	public void setMeetingInfo(String meetingInfo) {
		this.meetingInfo = meetingInfo;
	}

	public String getWorkhardInfo() {
		return workhardInfo;
	}

	public void setWorkhardInfo(String workhardInfo) {
		this.workhardInfo = workhardInfo;
	}

	public String getMeeting() {
		return meeting;
	}

	public void setMeeting(String meeting) {
		this.meeting = meeting;
	}

	public String getHoliday() {
		return holiday;
	}

	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}

	public String getHolidayType() {
		return holidayType;
	}

	public void setHolidayType(String holidayType) {
		this.holidayType = holidayType;
	}

	public String getInDelay() {
		return inDelay;
	}

	public void setInDelay(String inDelay) {
		this.inDelay = inDelay;
	}

	public String getOutEarly() {
		return outEarly;
	}

	public void setOutEarly(String outEarly) {
		this.outEarly = outEarly;
	}

	public String getOnDelay() {
		return onDelay;
	}

	public void setOnDelay(String onDelay) {
		this.onDelay = onDelay;
	}

	public String getOnEarly() {
		return onEarly;
	}

	public void setOnEarly(String onEarly) {
		this.onEarly = onEarly;
	}

	public String getCommend() {
		return commend;
	}

	public void setCommend(String commend) {
		this.commend = commend;
	}

	public String getCommendDetail() {
		return commendDetail;
	}

	public void setCommendDetail(String commendDetail) {
		this.commendDetail = commendDetail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWorkHard() {
		return workHard;
	}

	public void setWorkHard(String workHard) {
		this.workHard = workHard;
	}

}
