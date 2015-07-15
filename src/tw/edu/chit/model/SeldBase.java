package tw.edu.chit.model;

public class SeldBase {
    // Fields    

    private String studentName;	//學生姓名
    private String cscode;		//科目代碼
    private String cscodeName;	//科目名稱
    private String departClass; //班級代碼
    private String departClassName; //班級名稱
    private String stdepartClass;	//學生所在班級

   // Constructors

  
   // Property accessors

   public String getStudentName() {
       return this.studentName;
   }
   
   public void setStudentName(String studentName) {
       this.studentName = studentName;
   }

   public String getCscode() {
       return this.cscode;
   }
   
   public void setCscode(String cscode) {
       this.cscode = cscode;
   }

   
   public String getCscodeName() {
       return this.cscodeName;
   }
   
   public void setCscodeName(String cscodeName) {
       this.cscodeName = cscodeName;
   }

   public String getDepartClass() {
	   return departClass;
   }

   public void setDepartClass(String departClass) {
	   this.departClass = departClass;
   }

   public String getDepartClassName() {
	   return departClassName;
   }

   public void setDepartClassName(String departClassName) {
	   this.departClassName = departClassName;
   }
   
   public String getStdepartClass() {
	   return stdepartClass;
   }

   public void setStdepartClass(String stdepartClass) {
	   this.stdepartClass = stdepartClass;
   }

}
