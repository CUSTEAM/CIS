package tw.edu.chit.model;



/**
 * SeldCouFilter generated by MyEclipse - Hibernate Tools
 */

public class SeldCouFilter  implements java.io.Serializable {


    // Fields

     private Integer oid;
     private String departClass;
     private String cscode;
     private String techid;
     private String opt;
     private Float credit;
     private Short thour;
     private Short stuSelect;
     private String sterm;
     private Short selectLimit;
     private Byte open;
     private String elearning;
     private String extrapay;
     private String crozz;
     private Integer auxNo;
     private String syear;
     private String type;
     private Integer dtimeOid;


    // Constructors

    /** default constructor */
    public SeldCouFilter() {
    }

	/** minimal constructor */
    public SeldCouFilter(String departClass, String cscode, String techid, String sterm, Short selectLimit, Integer dtimeOid) {
        this.departClass = departClass;
        this.cscode = cscode;
        this.techid = techid;
        this.sterm = sterm;
        this.selectLimit = selectLimit;
        this.dtimeOid = dtimeOid;
    }

    /** full constructor */
    public SeldCouFilter(String departClass, String cscode, String techid, String opt, Float credit, Short thour, Short stuSelect, String sterm, Short selectLimit, Byte open, String elearning, String extrapay, String crozz, Integer auxNo, String syear, String type, Integer dtimeOid) {
        this.departClass = departClass;
        this.cscode = cscode;
        this.techid = techid;
        this.opt = opt;
        this.credit = credit;
        this.thour = thour;
        this.stuSelect = stuSelect;
        this.sterm = sterm;
        this.selectLimit = selectLimit;
        this.open = open;
        this.elearning = elearning;
        this.extrapay = extrapay;
        this.crozz = crozz;
        this.auxNo = auxNo;
        this.syear = syear;
        this.type = type;
        this.dtimeOid = dtimeOid;
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

    public String getCscode() {
        return this.cscode;
    }

    public void setCscode(String cscode) {
        this.cscode = cscode;
    }

    public String getTechid() {
        return this.techid;
    }

    public void setTechid(String techid) {
        this.techid = techid;
    }

    public String getOpt() {
        return this.opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public Float getCredit() {
        return this.credit;
    }

    public void setCredit(Float credit) {
        this.credit = credit;
    }

    public Short getThour() {
        return this.thour;
    }

    public void setThour(Short thour) {
        this.thour = thour;
    }

    public Short getStuSelect() {
        return this.stuSelect;
    }

    public void setStuSelect(Short stuSelect) {
        this.stuSelect = stuSelect;
    }

    public String getSterm() {
        return this.sterm;
    }

    public void setSterm(String sterm) {
        this.sterm = sterm;
    }

    public Short getSelectLimit() {
        return this.selectLimit;
    }

    public void setSelectLimit(Short selectLimit) {
        this.selectLimit = selectLimit;
    }

    public Byte getOpen() {
        return this.open;
    }

    public void setOpen(Byte open) {
        this.open = open;
    }

    public String getElearning() {
        return this.elearning;
    }

    public void setElearning(String elearning) {
        this.elearning = elearning;
    }

    public String getExtrapay() {
        return this.extrapay;
    }

    public void setExtrapay(String extrapay) {
        this.extrapay = extrapay;
    }

    public String getCrozz() {
        return this.crozz;
    }

    public void setCrozz(String crozz) {
        this.crozz = crozz;
    }

    public Integer getAuxNo() {
        return this.auxNo;
    }

    public void setAuxNo(Integer auxNo) {
        this.auxNo = auxNo;
    }

    public String getSyear() {
        return this.syear;
    }

    public void setSyear(String syear) {
        this.syear = syear;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDtimeOid() {
        return this.dtimeOid;
    }

    public void setDtimeOid(Integer dtimeOid) {
        this.dtimeOid = dtimeOid;
    }









}