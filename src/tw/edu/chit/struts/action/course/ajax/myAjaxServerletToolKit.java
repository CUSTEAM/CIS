package tw.edu.chit.struts.action.course.ajax;

public class myAjaxServerletToolKit {

	// 報部表格系所代碼轉換
	public String chDept(){

		return null;
	}

	// 報部表格部制代碼轉換
	public String chSchoolType(){

		return null;
	}

	//判斷畢業班
	public String confirmGraduate(String schoolName){
		if(schoolName.equals("12")){
			schoolName="0";
		}else if(schoolName.equals("15")){
			schoolName="0";
		}else if(schoolName.equals("1G")){
			schoolName="0";
		}else if(schoolName.equals("22")){
			schoolName="1";
		}else if(schoolName.equals("32")){
			schoolName="2";
		}else if(schoolName.equals("42")){
			schoolName="0";
		}else if(schoolName.equals("52")){
			schoolName="1";
		}else if(schoolName.equals("54")){
			schoolName="0";
		}else if(schoolName.equals("64")){
			schoolName="0";
		}else if(schoolName.equals("72")){
			schoolName="2";
		}else if(schoolName.equals("82")){
			schoolName="2";
		}else if(schoolName.equals("8G")){
			schoolName="2";
		}else if(schoolName.equals("92")){
			schoolName="2";
		}

		return schoolName;

	}
}
