package tw.edu.chit.model;



/**
 * DepartmentInfo generated by MyEclipse - Hibernate Tools
 */

public class DepartmentInfo  implements java.io.Serializable {


    // Fields    

     private Integer oid;
     private String category;
     private String chiName;
     private String engName;
     private String location;
     private String telephone;


    // Constructors

    /** default constructor */
    public DepartmentInfo() {
    }

	/** minimal constructor */
    public DepartmentInfo(String category, String chiName, String engName, String location) {
        this.category = category;
        this.chiName = chiName;
        this.engName = engName;
        this.location = location;
    }
    
    /** full constructor */
    public DepartmentInfo(String category, String chiName, String engName, String location, String telephone) {
        this.category = category;
        this.chiName = chiName;
        this.engName = engName;
        this.location = location;
        this.telephone = telephone;
    }

   
    // Property accessors

    public Integer getOid() {
        return this.oid;
    }
    
    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }

    public String getChiName() {
        return this.chiName;
    }
    
    public void setChiName(String chiName) {
        this.chiName = chiName;
    }

    public String getEngName() {
        return this.engName;
    }
    
    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getLocation() {
        return this.location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }

    public String getTelephone() {
        return this.telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
   








}