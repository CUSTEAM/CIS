package tw.edu.chit.model;

import java.util.Date;


/**
 * RegistrationCard generated by MyEclipse Persistence Tools
 */

public class RegistrationCard  implements java.io.Serializable {


    // Fields    

     private Integer oid;
     private String schoolYear;
     private String studentNo;
     private String diviName;
     private String birthCountry;
     private String birthPlace;
     private String addrLocation;
     private String armyType;
     private String armyLevel;
     private String inWay;
     private Date armyIn;
     private Date armyOut;
     private String aborigine;
     private String aborigineCode;
     private String foreignPlace;
     private String foreignNo;
     private String beforeSchool;
     private String beforeDept;
     private String gradeYear;
     private String gradeType;
     private String parentAge;
     private String parentCareer;
     private String parentRelationship;
     private String parentPost;
     private String emergentPhone;
     private String emergentCell;
     private String workPlace1;
     private String workTitle1;
     private Date workBegin1;
     private Date workEnd1;
     private String workPlace2;
     private String workTitle2;
     private Date workBegin2;
     private Date workEnd2;
     private String memberTitle1;
     private String memberName1;
     private String memberAge1;
     private String memberCareer1;
     private String memberTitle2;
     private String memberName2;
     private String memberAge2;
     private String memberCareer2;
     private String memberTitle3;
     private String memberName3;
     private String memberAge3;
     private String memberCareer3;
     private String memberTitle4;
     private String memberName4;
     private String memberAge4;
     private String memberCareer4;
     private Date lastModified;


    // Constructors

    /** default constructor */
    public RegistrationCard() {
    }

	/** minimal constructor */
    public RegistrationCard(String schoolYear, String studentNo) {
        this.schoolYear = schoolYear;
        this.studentNo = studentNo;
    }
    
    /** full constructor */
    public RegistrationCard(String schoolYear, String studentNo, String diviName, String birthCountry, String birthPlace, String addrLocation, String armyType, String armyLevel, String inWay, Date armyIn, Date armyOut, String aborigine, String aborigineCode, String foreignPlace, String foreignNo, String beforeSchool, String beforeDept, String gradeYear, String gradeType, String parentAge, String parentCareer, String parentRelationship, String parentPost, String emergentPhone, String emergentCell, String workPlace1, String workTitle1, Date workBegin1, Date workEnd1, String workPlace2, String workTitle2, Date workBegin2, Date workEnd2, String memberTitle1, String memberName1, String memberAge1, String memberCareer1, String memberTitle2, String memberName2, String memberAge2, String memberCareer2, String memberTitle3, String memberName3, String memberAge3, String memberCareer3, String memberTitle4, String memberName4, String memberAge4, String memberCareer4, Date lastModified) {
        this.schoolYear = schoolYear;
        this.studentNo = studentNo;
        this.diviName = diviName;
        this.birthCountry = birthCountry;
        this.birthPlace = birthPlace;
        this.addrLocation = addrLocation;
        this.armyType = armyType;
        this.armyLevel = armyLevel;
        this.inWay = inWay;
        this.armyIn = armyIn;
        this.armyOut = armyOut;
        this.aborigine = aborigine;
        this.aborigineCode = aborigineCode;
        this.foreignPlace = foreignPlace;
        this.foreignNo = foreignNo;
        this.beforeSchool = beforeSchool;
        this.beforeDept = beforeDept;
        this.gradeYear = gradeYear;
        this.gradeType = gradeType;
        this.parentAge = parentAge;
        this.parentCareer = parentCareer;
        this.parentRelationship = parentRelationship;
        this.parentPost = parentPost;
        this.emergentPhone = emergentPhone;
        this.emergentCell = emergentCell;
        this.workPlace1 = workPlace1;
        this.workTitle1 = workTitle1;
        this.workBegin1 = workBegin1;
        this.workEnd1 = workEnd1;
        this.workPlace2 = workPlace2;
        this.workTitle2 = workTitle2;
        this.workBegin2 = workBegin2;
        this.workEnd2 = workEnd2;
        this.memberTitle1 = memberTitle1;
        this.memberName1 = memberName1;
        this.memberAge1 = memberAge1;
        this.memberCareer1 = memberCareer1;
        this.memberTitle2 = memberTitle2;
        this.memberName2 = memberName2;
        this.memberAge2 = memberAge2;
        this.memberCareer2 = memberCareer2;
        this.memberTitle3 = memberTitle3;
        this.memberName3 = memberName3;
        this.memberAge3 = memberAge3;
        this.memberCareer3 = memberCareer3;
        this.memberTitle4 = memberTitle4;
        this.memberName4 = memberName4;
        this.memberAge4 = memberAge4;
        this.memberCareer4 = memberCareer4;
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

    public String getStudentNo() {
        return this.studentNo;
    }
    
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getDiviName() {
        return this.diviName;
    }
    
    public void setDiviName(String diviName) {
        this.diviName = diviName;
    }

    public String getBirthCountry() {
        return this.birthCountry;
    }
    
    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
    }

    public String getBirthPlace() {
        return this.birthPlace;
    }
    
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getAddrLocation() {
        return this.addrLocation;
    }
    
    public void setAddrLocation(String addrLocation) {
        this.addrLocation = addrLocation;
    }

    public String getArmyType() {
        return this.armyType;
    }
    
    public void setArmyType(String armyType) {
        this.armyType = armyType;
    }

    public String getArmyLevel() {
        return this.armyLevel;
    }
    
    public void setArmyLevel(String armyLevel) {
        this.armyLevel = armyLevel;
    }

    public String getInWay() {
        return this.inWay;
    }
    
    public void setInWay(String inWay) {
        this.inWay = inWay;
    }

    public Date getArmyIn() {
        return this.armyIn;
    }
    
    public void setArmyIn(Date armyIn) {
        this.armyIn = armyIn;
    }

    public Date getArmyOut() {
        return this.armyOut;
    }
    
    public void setArmyOut(Date armyOut) {
        this.armyOut = armyOut;
    }

    public String getAborigine() {
        return this.aborigine;
    }
    
    public void setAborigine(String aborigine) {
        this.aborigine = aborigine;
    }

    public String getAborigineCode() {
        return this.aborigineCode;
    }
    
    public void setAborigineCode(String aborigineCode) {
        this.aborigineCode = aborigineCode;
    }

    public String getForeignPlace() {
        return this.foreignPlace;
    }
    
    public void setForeignPlace(String foreignPlace) {
        this.foreignPlace = foreignPlace;
    }

    public String getForeignNo() {
        return this.foreignNo;
    }
    
    public void setForeignNo(String foreignNo) {
        this.foreignNo = foreignNo;
    }

    public String getBeforeSchool() {
        return this.beforeSchool;
    }
    
    public void setBeforeSchool(String beforeSchool) {
        this.beforeSchool = beforeSchool;
    }

    public String getBeforeDept() {
        return this.beforeDept;
    }
    
    public void setBeforeDept(String beforeDept) {
        this.beforeDept = beforeDept;
    }

    public String getGradeYear() {
        return this.gradeYear;
    }
    
    public void setGradeYear(String gradeYear) {
        this.gradeYear = gradeYear;
    }

    public String getGradeType() {
        return this.gradeType;
    }
    
    public void setGradeType(String gradeType) {
        this.gradeType = gradeType;
    }

    public String getParentAge() {
        return this.parentAge;
    }
    
    public void setParentAge(String parentAge) {
        this.parentAge = parentAge;
    }

    public String getParentCareer() {
        return this.parentCareer;
    }
    
    public void setParentCareer(String parentCareer) {
        this.parentCareer = parentCareer;
    }

    public String getParentRelationship() {
        return this.parentRelationship;
    }
    
    public void setParentRelationship(String parentRelationship) {
        this.parentRelationship = parentRelationship;
    }

    public String getParentPost() {
        return this.parentPost;
    }
    
    public void setParentPost(String parentPost) {
        this.parentPost = parentPost;
    }

    public String getEmergentPhone() {
        return this.emergentPhone;
    }
    
    public void setEmergentPhone(String emergentPhone) {
        this.emergentPhone = emergentPhone;
    }

    public String getEmergentCell() {
        return this.emergentCell;
    }
    
    public void setEmergentCell(String emergentCell) {
        this.emergentCell = emergentCell;
    }

    public String getWorkPlace1() {
        return this.workPlace1;
    }
    
    public void setWorkPlace1(String workPlace1) {
        this.workPlace1 = workPlace1;
    }

    public String getWorkTitle1() {
        return this.workTitle1;
    }
    
    public void setWorkTitle1(String workTitle1) {
        this.workTitle1 = workTitle1;
    }

    public Date getWorkBegin1() {
        return this.workBegin1;
    }
    
    public void setWorkBegin1(Date workBegin1) {
        this.workBegin1 = workBegin1;
    }

    public Date getWorkEnd1() {
        return this.workEnd1;
    }
    
    public void setWorkEnd1(Date workEnd1) {
        this.workEnd1 = workEnd1;
    }

    public String getWorkPlace2() {
        return this.workPlace2;
    }
    
    public void setWorkPlace2(String workPlace2) {
        this.workPlace2 = workPlace2;
    }

    public String getWorkTitle2() {
        return this.workTitle2;
    }
    
    public void setWorkTitle2(String workTitle2) {
        this.workTitle2 = workTitle2;
    }

    public Date getWorkBegin2() {
        return this.workBegin2;
    }
    
    public void setWorkBegin2(Date workBegin2) {
        this.workBegin2 = workBegin2;
    }

    public Date getWorkEnd2() {
        return this.workEnd2;
    }
    
    public void setWorkEnd2(Date workEnd2) {
        this.workEnd2 = workEnd2;
    }

    public String getMemberTitle1() {
        return this.memberTitle1;
    }
    
    public void setMemberTitle1(String memberTitle1) {
        this.memberTitle1 = memberTitle1;
    }

    public String getMemberName1() {
        return this.memberName1;
    }
    
    public void setMemberName1(String memberName1) {
        this.memberName1 = memberName1;
    }

    public String getMemberAge1() {
        return this.memberAge1;
    }
    
    public void setMemberAge1(String memberAge1) {
        this.memberAge1 = memberAge1;
    }

    public String getMemberCareer1() {
        return this.memberCareer1;
    }
    
    public void setMemberCareer1(String memberCareer1) {
        this.memberCareer1 = memberCareer1;
    }

    public String getMemberTitle2() {
        return this.memberTitle2;
    }
    
    public void setMemberTitle2(String memberTitle2) {
        this.memberTitle2 = memberTitle2;
    }

    public String getMemberName2() {
        return this.memberName2;
    }
    
    public void setMemberName2(String memberName2) {
        this.memberName2 = memberName2;
    }

    public String getMemberAge2() {
        return this.memberAge2;
    }
    
    public void setMemberAge2(String memberAge2) {
        this.memberAge2 = memberAge2;
    }

    public String getMemberCareer2() {
        return this.memberCareer2;
    }
    
    public void setMemberCareer2(String memberCareer2) {
        this.memberCareer2 = memberCareer2;
    }

    public String getMemberTitle3() {
        return this.memberTitle3;
    }
    
    public void setMemberTitle3(String memberTitle3) {
        this.memberTitle3 = memberTitle3;
    }

    public String getMemberName3() {
        return this.memberName3;
    }
    
    public void setMemberName3(String memberName3) {
        this.memberName3 = memberName3;
    }

    public String getMemberAge3() {
        return this.memberAge3;
    }
    
    public void setMemberAge3(String memberAge3) {
        this.memberAge3 = memberAge3;
    }

    public String getMemberCareer3() {
        return this.memberCareer3;
    }
    
    public void setMemberCareer3(String memberCareer3) {
        this.memberCareer3 = memberCareer3;
    }

    public String getMemberTitle4() {
        return this.memberTitle4;
    }
    
    public void setMemberTitle4(String memberTitle4) {
        this.memberTitle4 = memberTitle4;
    }

    public String getMemberName4() {
        return this.memberName4;
    }
    
    public void setMemberName4(String memberName4) {
        this.memberName4 = memberName4;
    }

    public String getMemberAge4() {
        return this.memberAge4;
    }
    
    public void setMemberAge4(String memberAge4) {
        this.memberAge4 = memberAge4;
    }

    public String getMemberCareer4() {
        return this.memberCareer4;
    }
    
    public void setMemberCareer4(String memberCareer4) {
        this.memberCareer4 = memberCareer4;
    }

    public Date getLastModified() {
        return this.lastModified;
    }
    
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
   








}