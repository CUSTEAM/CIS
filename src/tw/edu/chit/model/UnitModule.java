package tw.edu.chit.model;



/**
 * UnitModule generated by MyEclipse - Hibernate Tools
 */

public class UnitModule  implements java.io.Serializable {


    // Fields    

     private Integer oid;
     private String unitNo;
     private Integer moduleOid;


    // Constructors

    /** default constructor */
    public UnitModule() {
    }

    
    /** full constructor */
    public UnitModule(Integer oid, String unitNo, Integer moduleOid) {
        this.oid = oid;
        this.unitNo = unitNo;
        this.moduleOid = moduleOid;
    }

   
    // Property accessors

    public Integer getOid() {
        return this.oid;
    }
    
    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getUnitNo() {
        return this.unitNo;
    }
    
    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    public Integer getModuleOid() {
        return this.moduleOid;
    }
    
    public void setModuleOid(Integer moduleOid) {
        this.moduleOid = moduleOid;
    }
   








}