package tw.edu.chit.model;

import java.util.Date;


/**
 * DtimeExam generated by MyEclipse - Hibernate Tools
 */

public class DtimeExam extends DtimeExamBase  implements java.io.Serializable {



	// Fields    

    private Integer oid;
    private Integer dtimeOid;
    private Date examDate;
    private String examEmpl;
    private String ebegin;
    private String place;


   // Constructors

   /** default constructor */
   public DtimeExam() {
   }

	/** minimal constructor */
   public DtimeExam(Integer dtimeOid) {
       this.dtimeOid = dtimeOid;
   }
   
   /** full constructor */
   public DtimeExam(Integer dtimeOid, Date examDate, String examEmpl, String ebegin, String place) {
       this.dtimeOid = dtimeOid;
       this.examDate = examDate;
       this.examEmpl = examEmpl;
       this.ebegin = ebegin;
       this.place = place;
   }

  
   // Property accessors

   public Integer getOid() {
       return this.oid;
   }
   
   public void setOid(Integer oid) {
       this.oid = oid;
   }

   public Integer getDtimeOid() {
       return this.dtimeOid;
   }
   
   public void setDtimeOid(Integer dtimeOid) {
       this.dtimeOid = dtimeOid;
   }

   public Date getExamDate() {
       return this.examDate;
   }
   
   public void setExamDate(Date examDate) {
       this.examDate = examDate;
   }

   public String getExamEmpl() {
       return this.examEmpl;
   }
   
   public void setExamEmpl(String examEmpl) {
       this.examEmpl = examEmpl;
   }

   public String getEbegin() {
       return this.ebegin;
   }
   
   public void setEbegin(String ebegin) {
       this.ebegin = ebegin;
   }

   public String getPlace() {
       return this.place;
   }
   
   public void setPlace(String place) {
       this.place = place;
   }
  








}