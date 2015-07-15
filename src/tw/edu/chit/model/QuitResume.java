package tw.edu.chit.model;

import java.util.Date;

/**
 * 休退記錄明細
 * @author JOHN
 *
 */
public class QuitResume  implements java.io.Serializable {


    // Fields    

     private Integer oid;
     private String departClass;
     private String studentNo;
     private Short occurYear;
     private String occurTerm;
     private Date occurDate;
     private String occurDocno;
     private Short recovYear;
     private String recovTerm;
     private Date recovDate;
     private String recovDocno;


    // Constructors

    /** default constructor */
    public QuitResume() {
    }

	/** minimal constructor */
    public QuitResume(String departClass, String studentNo, Short recovYear, String recovTerm) {
        this.departClass = departClass;
        this.studentNo = studentNo;
        this.recovYear = recovYear;
        this.recovTerm = recovTerm;
    }
    
    /** full constructor */
    public QuitResume(String departClass, String studentNo, Short occurYear, String occurTerm, Date occurDate, String occurDocno, Short recovYear, String recovTerm, Date recovDate, String recovDocno) {
        this.departClass = departClass;
        this.studentNo = studentNo;
        this.occurYear = occurYear;
        this.occurTerm = occurTerm;
        this.occurDate = occurDate;
        this.occurDocno = occurDocno;
        this.recovYear = recovYear;
        this.recovTerm = recovTerm;
        this.recovDate = recovDate;
        this.recovDocno = recovDocno;
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

    public Short getOccurYear() {
        return this.occurYear;
    }
    
    public void setOccurYear(Short occurYear) {
        this.occurYear = occurYear;
    }

    public String getOccurTerm() {
        return this.occurTerm;
    }
    
    public void setOccurTerm(String occurTerm) {
        this.occurTerm = occurTerm;
    }

    public Date getOccurDate() {
        return this.occurDate;
    }
    
    public void setOccurDate(Date occurDate) {
        this.occurDate = occurDate;
    }

    public String getOccurDocno() {
        return this.occurDocno;
    }
    
    public void setOccurDocno(String occurDocno) {
        this.occurDocno = occurDocno;
    }

    public Short getRecovYear() {
        return this.recovYear;
    }
    
    public void setRecovYear(Short recovYear) {
        this.recovYear = recovYear;
    }

    public String getRecovTerm() {
        return this.recovTerm;
    }
    
    public void setRecovTerm(String recovTerm) {
        this.recovTerm = recovTerm;
    }

    public Date getRecovDate() {
        return this.recovDate;
    }
    
    public void setRecovDate(Date recovDate) {
        this.recovDate = recovDate;
    }

    public String getRecovDocno() {
        return this.recovDocno;
    }
    
    public void setRecovDocno(String recovDocno) {
        this.recovDocno = recovDocno;
    }
   








}