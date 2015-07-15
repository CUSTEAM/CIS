package tw.edu.chit.model;
/**
 * 裝載考試日程的額外資訊
 * @author JOHN
 * @see tw.edu.chit.model.ExamDate
 */
public class ExamDateBase  {

	private String area="";
	private String schoolType="";
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getSchoolType() {
		return schoolType;
	}
	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}
}