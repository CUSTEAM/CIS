package tw.edu.chit.model;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * This class supplement fields needed to display employee info in a user-friendly fashion.
 * Namely, display attribute label instead code, localized string instead English.
 * DO NOT put these fields in Hibernate mapping for there is not correspondent columns
 * in database.
 * 
 * @author James F. Chiang
 *
 */
public class EmplBase {
	
	private String mode		 = "";
	private String sex2 	 = "";
	private String unit2 	 = "";
	private String pcode2	 = "";
	private String category2 = "";
	private String status2	 = "";
	private String bdate2	 = "";
	private String startDate2 = "";
	private String endDate2	 = "";
	private String teachStartDate2 = "";
	
	private TeacherOfficeLocation location; // 辦公室資料(1對1)
	// 留校時間資料(1對多)
	private List<TeacherStayTime> stayTime = new LinkedList<TeacherStayTime>();
	// 留校時間資料更新記錄(1對多)
	private Set<TeacherStayTimeModify> modify = new LinkedHashSet<TeacherStayTimeModify>();
	private List<LifeCounseling> lifeCounseling = new LinkedList<LifeCounseling>();
	private List<ClassCadre> classCadre; // 導師班級幹部聯繫資料(1對多)
	
	public String getCategory2() {
		return category2;
	}
	public void setCategory2(String category2) {
		this.category2 = category2;
	}
	public String getPcode2() {
		return pcode2;
	}
	public void setPcode2(String pcode2) {
		this.pcode2 = pcode2;
	}
	public String getSex2() {
		return sex2;
	}
	public void setSex2(String sex2) {
		this.sex2 = sex2;
	}
	public String getStatus2() {
		return status2;
	}
	public void setStatus2(String status2) {
		this.status2 = status2;
	}
	public String getUnit2() {
		return unit2;
	}
	public void setUnit2(String unit2) {
		this.unit2 = unit2;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getBdate2() {
		return bdate2;
	}
	public void setBdate2(String bdate2) {
		this.bdate2 = bdate2;
	}
	public String getEndDate2() {
		return endDate2;
	}
	public void setEndDate2(String endDate2) {
		this.endDate2 = endDate2;
	}
	public String getStartDate2() {
		return startDate2;
	}
	public void setStartDate2(String startDate2) {
		this.startDate2 = startDate2;
	}
	public String getTeachStartDate2() {
		return teachStartDate2;
	}
	public void setTeachStartDate2(String teachStartDate2) {
		this.teachStartDate2 = teachStartDate2;
	}
	
	public TeacherOfficeLocation getLocation() {
		return location;
	}

	public void setLocation(TeacherOfficeLocation location) {
		this.location = location;
	}

	public List<TeacherStayTime> getStayTime() {
		return stayTime;
	}

	public void setStayTime(List<TeacherStayTime> stayTime) {
		this.stayTime = stayTime;
	}
	
	public Set<TeacherStayTimeModify> getModify() {
		return modify;
	}
	
	public void setModify(Set<TeacherStayTimeModify> modify) {
		this.modify = modify;
	}
	
	public List<LifeCounseling> getLifeCounseling() {
		return lifeCounseling;
	}
	
	public void setLifeCounseling(List<LifeCounseling> lifeCounseling) {
		this.lifeCounseling = lifeCounseling;
	}
	
	public List<ClassCadre> getClassCadre() {
		return classCadre;
	}

	public void setClassCadre(List<ClassCadre> classCadre) {
		this.classCadre = classCadre;
	}
	
}
