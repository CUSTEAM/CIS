package tw.edu.chit.model;

public class AssessPaperBase {
	private Student student;	//反應者:學生
	private Empl empl;			//反應者:職員
	private String reporterCname;		//反應者姓名
	private String reporterUnitName;	//反應者所屬單位名稱
	private String reporterKind;	//反應者身分別:學生 or 職員
	
	private Empl attendant;		//服務人員
	private String attCname;	//服務人員姓名
	private String attUnitName;	//服務人員所屬單位名稱
	private String simpleDate;	//服務日期--簡單格式
	
	public void setStudent(Student student) {
		this.student = student;
	}
	public Student getStudent() {
		return student;
	}
	public void setEmpl(Empl empl) {
		this.empl = empl;
	}
	public Empl getEmpl() {
		return empl;
	}
	public void setAttendant(Empl attendant) {
		this.attendant = attendant;
	}
	public Empl getAttendant() {
		return attendant;
	}
	
	public void setReporterCname(String reporterCname) {
		this.reporterCname = reporterCname;
	}
	public String getReporterCname() {
		return reporterCname;
	}
	public void setReporterUnitName(String reporterUnitName) {
		this.reporterUnitName = reporterUnitName;
	}
	public String getReporterUnitName() {
		return reporterUnitName;
	}
	public void setAttCname(String attCname) {
		this.attCname = attCname;
	}
	public String getAttCname() {
		return attCname;
	}
	public void setAttUnitName(String attUnitName) {
		this.attUnitName = attUnitName;
	}
	public String getAttUnitName() {
		return attUnitName;
	}
	public void setReporterKind(String reporterKind) {
		this.reporterKind = reporterKind;
	}
	public String getReporterKind() {
		return reporterKind;
	}
	public void setSimpleDate(String simpleDate) {
		this.simpleDate = simpleDate;
	}
	public String getSimpleDate() {
		return simpleDate;
	}
}
