package tw.edu.chit.model.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.Student;
import tw.edu.chit.util.Global;

public class UserCredential {
	
	public static final String PrioEmployee	= "A";
	public static final String PrioStudent	= "C";
	public static final String PrioGraduate	= "G";
	
	/**
	 * Module oid's stored in the column "ModuleOids" of table "ClassInCharge"
	 * to identify modules the authority applied.
	 * Since TeachAffair(教務) includes Registration(學籍) with module oid 1,
	 * Score(成績) with module oid 2 and Course(課程) with module oid 3,
	 * the authorities has to combine these three using delimilter "|".
	 * When stored into database additional delimiters are surrounded to the
	 * head and tail of whole string
	 */
	public static final String AuthorityOnTeachAffair = "1|2|3";
	
	/**
	 * Module oid stored in the column "ModuleOids" of table "ClassInCharge"
	 * to identify modules the authority applied.
	 * For StudAffair(學務) the module oid is 5
	 */
	public static final String AuthorityOnStudAffair = "5";

	/**
	 * Module oid stored in the column "ModuleOids" of table "ClassInCharge"
	 * to identify modules the authority applied.
	 * For Drillmaster(教官) the module oid is 88
	 */
	public static final String AuthorityOnDrillmaster = "88";

	/**
	 * Module oid stored in the column "ModuleOids" of table "ClassInCharge"
	 * to identify modules the authority applied.
	 * For department Drillmaster(系教官) the module oid is 89
	 */
	public static final String AuthorityOnDeptDrillmaster = "89";

	/**
	 * Module oid stored in the column "ModuleOids" of table "ClassInCharge"
	 * to identify modules the authority applied.
	 * For Chairman(系主任) and DeptAssistant(系助理) the module oid is 87
	 */
	public static final String AuthorityOnChairman = "87";

	/**
	 * Module oid stored in the column "ModuleOids" of table "ClassInCharge"
	 * to identify modules the authority applied.
	 * For Tutor(導師) the module oid is 86
	 */
	public static final String AuthorityOnTutor  = "86";
	
	private Member	member = null;
	private List	roles;
	
	// The following are related to class-incharge for modules on TeachAffair(學籍,成績,課程)
	private String	classInChargeSqlFilter;
	private Clazz[]	classAry;
	private Code5[]			campusInCharge;
	private Code5[][]		schoolInCharge;
	private Code5[][][]		deptInCharge;
	private Clazz[][][][]	classInCharge;
	private Map<String,String> javaScriptArray = new HashMap<String,String>();

	// The following are related to class-incharge for modules on StudAffair(學務)
	private String	classInChargeSqlFilterSAF;
	private Clazz[]	classArySAF;
	private Code5[]			campusInChargeSAF;
	private Code5[][]		schoolInChargeSAF;
	private Code5[][][]		deptInChargeSAF;
	private Clazz[][][][]	classInChargeSAF;
	private Map<String,String> javaScriptArraySAF = new HashMap<String,String>();

	// The following are related to class-incharge for modules on Chairman(系主任)
	private String	classInChargeSqlFilterC;
	private Clazz[]	classAryC;
	private Code5[]			campusInChargeC;
	private Code5[][]		schoolInChargeC;
	private Code5[][][]		deptInChargeC;
	private Clazz[][][][]	classInChargeC;
	private Map<String,String> javaScriptArrayC = new HashMap<String,String>();
	
	// The following are related to class-incharge for modules on Tutor(導師)
	private String	classInChargeSqlFilterT;
	private Clazz[]	classAryT;
	private Code5[]			campusInChargeT;
	private Code5[][]		schoolInChargeT;
	private Code5[][][]		deptInChargeT;
	private Clazz[][][][]	classInChargeT;
	private Map<String,String> javaScriptArrayT = new HashMap<String,String>();

	// The following are related to class-incharge for modules on Drillmaster(教官)
	private String	classInChargeSqlFilterM;
	private Clazz[]	classAryM;
	private Code5[]			campusInChargeM;
	private Code5[][]		schoolInChargeM;
	private Code5[][][]		deptInChargeM;
	private Clazz[][][][]	classInChargeM;
	private Map<String,String> javaScriptArrayM = new HashMap<String,String>();

	// Added to support student login
	private Student student = null;	
	private Clazz	studentClass = null;
	
	private Graduate gstmd = null;
	
	/**
	 * Used for employee login
	 * @param member
	 * @param roles
	 * @param classAry      class (班級) array incharged for modules on TeachAffair(學籍,成績,課程)
	 * @param campuses		campus(校區) array incharged for modules on TeachAffair(學籍,成績,課程)
	 * @param schools		school(學制) array incharged for modules on TeachAffair(學籍,成績,課程), indexed by campus
	 * @param depts		department(科系) array incharged for modules on TeachAffair(學籍,成績,課程), indexed by campus and school
	 * @param classes  		class (班級) array incharged for modules on TeachAffair(學籍,成績,課程), indexed by campus, school and dept
	 * @param classArySAF 	class (班級) array incharged for modules on StudAffair(學務)
	 * @param campusesSAF	campus(校區) array incharged for modules on StudAffair(學務)
	 * @param schoolsSAF	school(學制) array incharged for modules on StudAffair(學務), indexed by campus
	 * @param deptsSAF	department(科系) array incharged for modules on StudAffair(學務), indexed by campus and school
	 * @param classesSAF  	class (班級) array incharged for modules on StudAffair(學務), indexed by campus, school and dept
	 * @param classAryC 	class (班級) array incharged for modules on Chairman(系主任)
	 * @param campusesC		campus(校區) array incharged for modules on Chairman(系主任)
	 * @param schoolsC		school(學制) array incharged for modules on Chairman(系主任), indexed by campus
	 * @param deptsC	department(科系) array incharged for modules on Chairman(系主任), indexed by campus and school
	 * @param classesC  	class (班級) array incharged for modules on Chairman(系主任), indexed by campus, school and dept
	 * @param classAryM 	class (班級) array incharged for modules on Drillmaster(教官)
	 * @param campusesM		campus(校區) array incharged for modules on Drillmaster(教官)
	 * @param schoolsM		school(學制) array incharged for modules on Drillmaster(教官), indexed by campus
	 * @param deptsM	department(科系) array incharged for modules on Drillmaster(教官), indexed by campus and school
	 * @param classesM  	class (班級) array incharged for modules on Drillmaster(教官), indexed by campus, school and dept
	 * @param classAryT 	class (班級) array incharged for modules on Tutor(導師)
	 * @param campusesT		campus(校區) array incharged for modules on Tutor(導師)
	 * @param schoolsT		school(學制) array incharged for modules on Tutor(導師), indexed by campus
	 * @param deptsT	department(科系) array incharged for modules on Tutor(導師), indexed by campus and school
	 * @param classesT  	class (班級) array incharged for modules on Tutor(導師), indexed by campus, school and dept
	 */
	public UserCredential(Member member,
						  Clazz[] 	classAry,
						  Code5[]   campuses, 
						  Code5[][]  schools, 
						  Code5[][][]  depts, 
						  Clazz[][][][] classes,
						  Clazz[] 	classArySAF,
						  Code5[]   campusesSAF, 
						  Code5[][]  schoolsSAF, 
						  Code5[][][]  deptsSAF, 
						  Clazz[][][][] classesSAF,
						  Clazz[] 	classAryC,
						  Code5[]   campusesC, 
						  Code5[][]  schoolsC, 
						  Code5[][][]  deptsC, 
						  Clazz[][][][] classesC,
						  Clazz[] 	classAryM,
						  Code5[]   campusesM, 
						  Code5[][]  schoolsM, 
						  Code5[][][]  deptsM, 
						  Clazz[][][][] classesM,
						  Clazz[] 	classAryT,
						  Code5[]   campusesT, 
						  Code5[][]  schoolsT, 
						  Code5[][][]  deptsT, 
						  Clazz[][][][] classesT) {
		
		this.member   = member;
		this.roles    = roles;
		
		this.classAry = classAry;
		this.campusInCharge = campuses;
		this.schoolInCharge = schools;
		this.deptInCharge   = depts;
	    this.classInCharge  = classes;

	    buildJsArraySchools(javaScriptArray, schools);
	    buildJsArrayDepts(javaScriptArray, depts);
	    buildJsArrayClasses(javaScriptArray, classes);

		this.classArySAF = classArySAF;
		this.campusInChargeSAF = campusesSAF;
		this.schoolInChargeSAF = schoolsSAF;
		this.deptInChargeSAF   = deptsSAF;
	    this.classInChargeSAF  = classesSAF;
	    
	    fillClassInfo(this.classArySAF);
	    buildJsArraySchools(javaScriptArraySAF, schoolsSAF);
	    buildJsArrayDepts(javaScriptArraySAF, deptsSAF);
	    buildJsArrayClasses(javaScriptArraySAF, classesSAF);

		this.classAryC = classAryC;
		this.campusInChargeC = campusesC;
		this.schoolInChargeC = schoolsC;
		this.deptInChargeC   = deptsC;
	    this.classInChargeC  = classesC;
	    
	    buildJsArraySchools(javaScriptArrayC, schoolsC);
	    buildJsArrayDepts(javaScriptArrayC, deptsC);
	    buildJsArrayClasses(javaScriptArrayC, classesC);

		this.classAryM = classAryM;
		this.campusInChargeM = campusesM;
		this.schoolInChargeM = schoolsM;
		this.deptInChargeM   = deptsM;
	    this.classInChargeM  = classesM;
	    
		this.classAryT = classAryT;
		this.campusInChargeT = campusesT;
		this.schoolInChargeT = schoolsT;
		this.deptInChargeT   = deptsT;
	    this.classInChargeT  = classesT;
	    
	    buildJsArraySchools(javaScriptArrayT, schoolsT);
	    buildJsArrayDepts(javaScriptArrayT, deptsT);
	    buildJsArrayClasses(javaScriptArrayT, classesT);

	    buildJsArraySchools(javaScriptArrayM, schoolsM);
	    buildJsArrayDepts(javaScriptArrayM, deptsM);
	    buildJsArrayClasses(javaScriptArrayM, classesM);

		this.classInChargeSqlFilter	   = buildSqlFilter(classAry);
		this.classInChargeSqlFilterSAF = buildSqlFilter(classArySAF);
		this.classInChargeSqlFilterT   = buildSqlFilter(classAryT);
		this.classInChargeSqlFilterC   = buildSqlFilter(classAryC);
		this.classInChargeSqlFilterM   = buildSqlFilter(classAryM);
		//System.out.println("classFilterSAF=" + this.classInChargeSqlFilterSAF);
	}
	
	/**
	 * used for student login
	 * @param student student info for this user
	 * @param studentClass class the user belong
	 */
	public UserCredential(Student student, Clazz studentClass) {
		this.student = student;
		this.studentClass = studentClass;
		//MemberManager manager = (MemberManager)Global.context.getBean("memberManager");
		//this.member = manager.findMemberByAccount(student.getStudentNo());
		this.member = student;
	}
	
	public UserCredential(Graduate gstmd, Clazz studentClass) {
		this.gstmd = gstmd;
		this.member = gstmd;
	}
	
	public static void buildJsArraySchools(Map<String,String> javaScriptArray, Code5[][] schools) {

		StringBuffer name = new StringBuffer();
		StringBuffer id   = new StringBuffer();
	    name.append("new Array(");
	    id.append("new Array(");
	    
	    for (int i=0; i < schools.length; i++) {
	    	name.append("new Array(");
	    	id.append("new Array(");
	    	for (int j=0; j < schools[i].length; j++) {
	    		name.append("\"").append(schools[i][j].getName()).append("\",");
	    		id.append("\"").append(schools[i][j].getIdno()).append("\",");
	    	}
		    if (name.charAt(name.length()-1) == ',') {
		    	name.setLength(name.length()-1);
		    	id.setLength(id.length()-1);
		    }
		    name.append("),");
		    id.append("),");
	    }
	    if (name.charAt(name.length()-1) == ',') {
	    	name.setLength(name.length()-1);
	    	id.setLength(id.length()-1);
	    }
	    name.append(")");
	    id.append(")");
	    javaScriptArray.put("schoolName", name.toString());
	    javaScriptArray.put("schoolId",   id.toString());
	}
	
	public static void buildJsArrayDepts(Map<String,String> javaScriptArray, Code5[][][] depts) {
		
		StringBuffer name = new StringBuffer();
		StringBuffer id   = new StringBuffer();
	    name.append("new Array(");
	    id.append("new Array(");
	    
	    for (int i=0; i < depts.length; i++) {
	    	name.append("new Array(");
	    	id.append("new Array(");
	    	for (int j=0; j < depts[i].length; j++) {
		    	name.append("new Array(");
		    	id.append("new Array(");	    		
	    		for (int k=0; k < depts[i][j].length; k++) {
		    		name.append("\"").append(depts[i][j][k].getName()).append("\",");
		    		id.append("\"").append(depts[i][j][k].getIdno()).append("\",");
	    		}
			    if (name.charAt(name.length()-1) == ',') {
			    	name.setLength(name.length()-1);
			    	id.setLength(id.length()-1);
			    }
			    name.append("),");
			    id.append("),");	    		
	    	}
		    if (name.charAt(name.length()-1) == ',') {
		    	name.setLength(name.length()-1);
		    	id.setLength(id.length()-1);
		    }
		    name.append("),");
		    id.append("),");
	    }
	    if (name.charAt(name.length()-1) == ',') {
	    	name.setLength(name.length()-1);
	    	id.setLength(id.length()-1);
	    }
	    name.append(")");
	    id.append(")");
	    javaScriptArray.put("deptName", name.toString());
	    javaScriptArray.put("deptId",   id.toString());
	}
	
	public static void buildJsArrayClasses(Map<String,String> javaScriptArray, Clazz[][][][] classes) {
		
		StringBuffer name = new StringBuffer();
		StringBuffer id   = new StringBuffer();
	    name.append("new Array(");
	    id.append("new Array(");
	    
	    for (int i=0; i < classes.length; i++) {
	    	name.append("new Array(");
	    	id.append("new Array(");
	    	for (int j=0; j < classes[i].length; j++) {
		    	name.append("new Array(");
		    	id.append("new Array(");	    		
	    		for (int k=0; k < classes[i][j].length; k++) {
			    	name.append("new Array(");
			    	id.append("new Array(");	    		
	    			for (int m=0; m < classes[i][j][k].length; m++) {
			    		name.append("\"").append(classes[i][j][k][m].getShortName()).append("\",");
			    		id.append("\"").append(classes[i][j][k][m].getClassNo()).append("\",");
	    			}
				    if (name.charAt(name.length()-1) == ',') {
				    	name.setLength(name.length()-1);
				    	id.setLength(id.length()-1);
				    }
				    name.append("),");
				    id.append("),");	    			    			
	    		}
			    if (name.charAt(name.length()-1) == ',') {
			    	name.setLength(name.length()-1);
			    	id.setLength(id.length()-1);
			    }
			    name.append("),");
			    id.append("),");	    		
	    	}
		    if (name.charAt(name.length()-1) == ',') {
		    	name.setLength(name.length()-1);
		    	id.setLength(id.length()-1);
		    }
		    name.append("),");
		    id.append("),");
	    }
	    if (name.charAt(name.length()-1) == ',') {
	    	name.setLength(name.length()-1);
	    	id.setLength(id.length()-1);
	    }
	    name.append(")");
	    id.append(")");
	    javaScriptArray.put("className", name.toString());
	    javaScriptArray.put("classId",   id.toString());		
	}
	
	private String buildSqlFilter(Clazz[] classAry) {

		StringBuffer  classFilter = new StringBuffer("(");
		for (int i=0; i < classAry.length; i++) {
			classFilter.append("'").append(classAry[i].getClassNo()).append("',");
		}
		//classFilter.insert(0, "(");
		int length = classFilter.length();
		if (length > 1) {
			classFilter.setLength(length-1);	// Truncate the tailing ','
		} else {
			classFilter.append("''");			// There is NO classs-in-charge, insert an empty classNo to fulfill the SQL grammer
		}
		classFilter.append(")");
		return classFilter.toString();
	}

	private void fillClassInfo(Clazz[] classes) {
		for (Clazz clazz : classes) {
			clazz.setCampusName(Global.Campus.getProperty(clazz.getCampusNo()));
			clazz.setSchoolName(Global.School.getProperty(clazz.getSchoolNo()));
			clazz.setDeptName(Global.Dept.getProperty(clazz.getDeptNo()));
			clazz.setClassFullName(Global.ClassFullName.getProperty(clazz.getClassNo()));			
		}
	}
	
	public Member getMember() {
		return member;
	}
	
	@SuppressWarnings("unchecked")
	public List getRoles() {
		return roles;
	}


	/**
	 * get campuses incharged by this user for modules on TeachAffair(學籍,成績,課程) 
	 * @return
	 */
	public Code5[] getCampusInCharge() {
		return campusInCharge;
	}

	/**
	 * get schools incharged by this user for modules on TeachAffair(學籍,成績,課程), indexed by campus
	 * @return
	 */
	public Code5[][] getSchoolInCharge() {
		return schoolInCharge;
	}
	
	/**
	 * get departments incharged by this user for modules on TeachAffair(學籍,成績,課程), indexed by campus and school
	 * @return
	 */
	public Code5[][][] getDeptInCharge() {
		return deptInCharge;
	}

	/**
	 * get classes incharged by this user for modules on TeachAffair(學籍,成績,課程), indexed by campus, school and department
	 * @return
	 */
	public Clazz[][][][] getClassInCharge() {
		return classInCharge;
	}

	/**
	 * get a listing of class number incharged by this user for modules on TeachAffair(學籍,成績,課程)
	 * can be used directly in SQL where clause with 'IN' operator
	 * @return a String with the form "('XXXXX', 'YYYYY', ...)"
	 */
	public String getClassInChargeSqlFilter() {
		return classInChargeSqlFilter;
	}

	/**
	 * get a map with keys "schoolName", "schoolId", "deptName", "deptId", "className", "classId"
	 * and the correspondent string of JavaScript array initialization statement 
	 * to support dynamic update of HTML <SELECT> quadruple of campuse, school, dept and class
	 * for modules on TeachAffair(學籍,成績,課程)
	 * @return
	 */
	public Map getJavaScriptArray() {
		return javaScriptArray;
	}
	
	public Code5[] getCampusInChargeC() {
		return campusInChargeC;
	}

	public Code5[] getCampusInChargeM() {
		return campusInChargeM;
	}

	public Clazz[][][][] getClassInChargeC() {
		return classInChargeC;
	}

	public Clazz[][][][] getClassInChargeM() {
		return classInChargeM;
	}

	public String getClassInChargeSqlFilterC() {
		return classInChargeSqlFilterC;
	}

	public String getClassInChargeSqlFilterM() {
		return classInChargeSqlFilterM;
	}

	public Code5[][][] getDeptInChargeC() {
		return deptInChargeC;
	}

	public Code5[][][] getDeptInChargeM() {
		return deptInChargeM;
	}

	public Map<String, String> getJavaScriptArrayC() {
		return javaScriptArrayC;
	}

	public Map<String, String> getJavaScriptArrayM() {
		return javaScriptArrayM;
	}

	public Code5[][] getSchoolInChargeC() {
		return schoolInChargeC;
	}

	public Code5[][] getSchoolInChargeM() {
		return schoolInChargeM;
	}

	public Clazz[] getClassInChargeAry() {
		return classAry;
	}
	
	public boolean isClassInCharge(String classNo) {
		return (classInChargeSqlFilter.indexOf("'" + classNo + "'") >= 0);
	}


	/**
	 * get campuses incharged by this user for modules on StudAffair(學務) 
	 * @return
	 */
	public Code5[] getCampusInChargeSAF() {
		return campusInChargeSAF;
	}

	/**
	 * get schools incharged by this user for modules on StudAffair(學務), indexed by campus
	 * @return
	 */
	public Code5[][] getSchoolInChargeSAF() {
		return schoolInChargeSAF;
	}
	
	/**
	 * get departments incharged by this user for modules on StudAffair(學務), indexed by campus and school
	 * @return
	 */
	public Code5[][][] getDeptInChargeSAF() {
		return deptInChargeSAF;
	}

	/**
	 * get classes incharged by this user for modules on StudAffair(學務), indexed by campus, school and department
	 * @return
	 */
	public Clazz[][][][] getClassInChargeSAF() {
		return classInChargeSAF;
	}

	/**
	 * get a listing of class number incharged by this user for modules on StudAffair(學務)
	 * can be used directly in SQL where clause with 'IN' operator
	 * @return a String with the form "('XXXXX', 'YYYYY', ...)"
	 */
	public String getClassInChargeSqlFilterSAF() {
		return classInChargeSqlFilterSAF;
	}

	/**
	 * get a map with keys "schoolName", "schoolId", "deptName", "deptId", "className", "classId"
	 * and the correspondent string of JavaScript array initialization statement 
	 * to support dynamic update of HTML <SELECT> quadruple of campuse, school, dept and class
	 * for modules on StudAffair(學務)
	 * @return
	 */
	public Map getJavaScriptArraySAF() {
		return javaScriptArraySAF;
	}
	
	public Clazz[] getClassInChargeArySAF() {
		return classArySAF;
	}
	
	public boolean isClassInChargeSAF(String classNo) {
		return (classInChargeSqlFilterSAF.indexOf("'" + classNo + "'") >= 0);
	}
	


	/**
	 * get campuses incharged by this user for modules on Tutor(導師) 
	 * @return
	 */
	public Code5[] getCampusInChargeT() {
		return campusInChargeT;
	}

	/**
	 * get schools incharged by this user for modules on Tutor(導師), indexed by campus
	 * @return
	 */
	public Code5[][] getSchoolInChargeT() {
		return schoolInChargeT;
	}
	
	/**
	 * get departments incharged by this user for modules on Tutor(導師), indexed by campus and school
	 * @return
	 */
	public Code5[][][] getDeptInChargeT() {
		return deptInChargeT;
	}

	/**
	 * get classes incharged by this user for modules on Tutor(導師), indexed by campus, school and department
	 * @return
	 */
	public Clazz[][][][] getClassInChargeT() {
		return classInChargeT;
	}

	/**
	 * get a listing of class number incharged by this user for modules on Tutor(導師)
	 * can be used directly in SQL where clause with 'IN' operator
	 * @return a String with the form "('XXXXX', 'YYYYY', ...)"
	 */
	public String getClassInChargeSqlFilterT() {
		return classInChargeSqlFilterT;
	}

	/**
	 * get a map with keys "schoolName", "schoolId", "deptName", "deptId", "className", "classId"
	 * and the correspondent string of JavaScript array initialization statement 
	 * to support dynamic update of HTML <SELECT> quadruple of campuse, school, dept and class
	 * for modules on Tutor(導師)
	 * @return
	 */
	public Map getJavaScriptArrayT() {
		return javaScriptArrayT;
	}
	
	public Clazz[] getClassInChargeAryT() {
		return classAryT;
	}
	
	public boolean isClassInChargeT(String classNo) {
		return (classInChargeSqlFilterT.indexOf("'" + classNo + "'") >= 0);
	}

	
	public Student getStudent() {
		return student;
	}
	
	public Graduate getGstudent() {
		return gstmd;
	}

	public Clazz getStudentClass() {
		return studentClass;
	}
	
	public boolean isEmployee() {
		return (this.student==null && this.gstmd==null);
	}

	public Clazz[] getClassAryC() {
		return classAryC;
	}

	public void setClassAryC(Clazz[] classAryC) {
		this.classAryC = classAryC;
	}
}
