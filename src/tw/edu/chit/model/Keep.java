package tw.edu.chit.model;



/**
 * Keep generated by MyEclipse - Hibernate Tools
 */

public class Keep extends KeepBase implements java.io.Serializable {


    // Fields    

     private Integer oid;
     private String departClass;
     private String studentNo;
     private Short downYear;
     private Short downTerm;
     private Short upYear;
     private Short upTerm;


    // Constructors

    /** default constructor */
    public Keep() {
    }

	/** minimal constructor */
    public Keep(String departClass, String studentNo, Short downYear, Short downTerm) {
        this.departClass = departClass;
        this.studentNo = studentNo;
        this.downYear = downYear;
        this.downTerm = downTerm;
    }
    
    /** full constructor */
    public Keep(String departClass, String studentNo, Short downYear, Short downTerm, Short upYear, Short upTerm) {
        this.departClass = departClass;
        this.studentNo = studentNo;
        this.downYear = downYear;
        this.downTerm = downTerm;
        this.upYear = upYear;
        this.upTerm = upTerm;
    }

   
    // Property accessors

    public Integer getOid() {
        return this.oid;
    }
    
    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getDepartClass() {
        return this.departClass;
    }
    
    public void setDepartClass(String departClass) {
        this.departClass = departClass;
    }

    public String getStudentNo() {
        return this.studentNo;
    }
    
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public Short getDownYear() {
        return this.downYear;
    }
    
    public void setDownYear(Short downYear) {
        this.downYear = downYear;
    }

    public Short getDownTerm() {
        return this.downTerm;
    }
    
    public void setDownTerm(Short downTerm) {
        this.downTerm = downTerm;
    }

    public Short getUpYear() {
        return this.upYear;
    }
    
    public void setUpYear(Short upYear) {
        this.upYear = upYear;
    }

    public Short getUpTerm() {
        return this.upTerm;
    }
    
    public void setUpTerm(Short upTerm) {
        this.upTerm = upTerm;
    }
   








}