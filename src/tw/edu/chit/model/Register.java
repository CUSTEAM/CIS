package tw.edu.chit.model;

import java.util.Date;


/**
 * Register entity. @author MyEclipse Persistence Tools
 */

public class Register  implements java.io.Serializable {


    // Fields    

     private Integer oid;
     private String schoolYear;
     private String schoolTerm;
     private String idno;
     private String campusCode;
     private String schoolType;
     private String studentName;
     private String serialNo;
     private String realClassNo;
     private String realStudentNo;
     private String virClassNo;
     private String virStudentNo;
     private Integer tuitionFee;
     private String tuitionAccountNo;
     private Integer tuitionAmount;
     private Date tuitionDate;
     private Integer agencyFee;
     private String agencyAccountNo;
     private Integer agencyAmount;
     private Date agencyDate;
     private String newStudentReg;
     private Integer reliefTuitionAmount;
     private Integer loanAmount;
     private Integer vulnerableAmount;
     private String isRegist;
     private String type;
     private String modifier;
     private Date lastModified;


    // Constructors

    /** default constructor */
    public Register() {
    }

	/** minimal constructor */
    public Register(String schoolYear, String schoolTerm, String idno, String type, String modifier, Date lastModified) {
        this.schoolYear = schoolYear;
        this.schoolTerm = schoolTerm;
        this.idno = idno;
        this.type = type;
        this.modifier = modifier;
        this.lastModified = lastModified;
    }
    
    /** full constructor */
    public Register(String schoolYear, String schoolTerm, String idno, String campusCode, String schoolType, String studentName, String serialNo, String realClassNo, String realStudentNo, String virClassNo, String virStudentNo, Integer tuitionFee, String tuitionAccountNo, Integer tuitionAmount, Date tuitionDate, Integer agencyFee, String agencyAccountNo, Integer agencyAmount, Date agencyDate, String newStudentReg, Integer reliefTuitionAmount, Integer loanAmount, Integer vulnerableAmount, String isRegist, String type, String modifier, Date lastModified) {
        this.schoolYear = schoolYear;
        this.schoolTerm = schoolTerm;
        this.idno = idno;
        this.campusCode = campusCode;
        this.schoolType = schoolType;
        this.studentName = studentName;
        this.serialNo = serialNo;
        this.realClassNo = realClassNo;
        this.realStudentNo = realStudentNo;
        this.virClassNo = virClassNo;
        this.virStudentNo = virStudentNo;
        this.tuitionFee = tuitionFee;
        this.tuitionAccountNo = tuitionAccountNo;
        this.tuitionAmount = tuitionAmount;
        this.tuitionDate = tuitionDate;
        this.agencyFee = agencyFee;
        this.agencyAccountNo = agencyAccountNo;
        this.agencyAmount = agencyAmount;
        this.agencyDate = agencyDate;
        this.newStudentReg = newStudentReg;
        this.reliefTuitionAmount = reliefTuitionAmount;
        this.loanAmount = loanAmount;
        this.vulnerableAmount = vulnerableAmount;
        this.isRegist = isRegist;
        this.type = type;
        this.modifier = modifier;
        this.lastModified = lastModified;
    }

   
    // Property accessors

    public Integer getOid() {
        return this.oid;
    }
    
    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getSchoolYear() {
        return this.schoolYear;
    }
    
    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getSchoolTerm() {
        return this.schoolTerm;
    }
    
    public void setSchoolTerm(String schoolTerm) {
        this.schoolTerm = schoolTerm;
    }

    public String getIdno() {
        return this.idno;
    }
    
    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getCampusCode() {
        return this.campusCode;
    }
    
    public void setCampusCode(String campusCode) {
        this.campusCode = campusCode;
    }

    public String getSchoolType() {
        return this.schoolType;
    }
    
    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public String getStudentName() {
        return this.studentName;
    }
    
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSerialNo() {
        return this.serialNo;
    }
    
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getRealClassNo() {
        return this.realClassNo;
    }
    
    public void setRealClassNo(String realClassNo) {
        this.realClassNo = realClassNo;
    }

    public String getRealStudentNo() {
        return this.realStudentNo;
    }
    
    public void setRealStudentNo(String realStudentNo) {
        this.realStudentNo = realStudentNo;
    }

    public String getVirClassNo() {
        return this.virClassNo;
    }
    
    public void setVirClassNo(String virClassNo) {
        this.virClassNo = virClassNo;
    }

    public String getVirStudentNo() {
        return this.virStudentNo;
    }
    
    public void setVirStudentNo(String virStudentNo) {
        this.virStudentNo = virStudentNo;
    }

    public Integer getTuitionFee() {
        return this.tuitionFee;
    }
    
    public void setTuitionFee(Integer tuitionFee) {
        this.tuitionFee = tuitionFee;
    }

    public String getTuitionAccountNo() {
        return this.tuitionAccountNo;
    }
    
    public void setTuitionAccountNo(String tuitionAccountNo) {
        this.tuitionAccountNo = tuitionAccountNo;
    }

    public Integer getTuitionAmount() {
        return this.tuitionAmount;
    }
    
    public void setTuitionAmount(Integer tuitionAmount) {
        this.tuitionAmount = tuitionAmount;
    }

    public Date getTuitionDate() {
        return this.tuitionDate;
    }
    
    public void setTuitionDate(Date tuitionDate) {
        this.tuitionDate = tuitionDate;
    }

    public Integer getAgencyFee() {
        return this.agencyFee;
    }
    
    public void setAgencyFee(Integer agencyFee) {
        this.agencyFee = agencyFee;
    }

    public String getAgencyAccountNo() {
        return this.agencyAccountNo;
    }
    
    public void setAgencyAccountNo(String agencyAccountNo) {
        this.agencyAccountNo = agencyAccountNo;
    }

    public Integer getAgencyAmount() {
        return this.agencyAmount;
    }
    
    public void setAgencyAmount(Integer agencyAmount) {
        this.agencyAmount = agencyAmount;
    }

    public Date getAgencyDate() {
        return this.agencyDate;
    }
    
    public void setAgencyDate(Date agencyDate) {
        this.agencyDate = agencyDate;
    }

    public String getNewStudentReg() {
        return this.newStudentReg;
    }
    
    public void setNewStudentReg(String newStudentReg) {
        this.newStudentReg = newStudentReg;
    }

    public Integer getReliefTuitionAmount() {
        return this.reliefTuitionAmount;
    }
    
    public void setReliefTuitionAmount(Integer reliefTuitionAmount) {
        this.reliefTuitionAmount = reliefTuitionAmount;
    }

    public Integer getLoanAmount() {
        return this.loanAmount;
    }
    
    public void setLoanAmount(Integer loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getVulnerableAmount() {
        return this.vulnerableAmount;
    }
    
    public void setVulnerableAmount(Integer vulnerableAmount) {
        this.vulnerableAmount = vulnerableAmount;
    }

    public String getIsRegist() {
        return this.isRegist;
    }
    
    public void setIsRegist(String isRegist) {
        this.isRegist = isRegist;
    }

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public String getModifier() {
        return this.modifier;
    }
    
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getLastModified() {
        return this.lastModified;
    }
    
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
   








}