package tw.edu.chit.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * LifeCounseling entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class LifeCounseling implements java.io.Serializable {

	private static final long serialVersionUID = -3142383882590703451L;

	private Integer oid;
	private Integer parentOid;
	private String schoolYear;
	private String schoolTerm;
	private Short week;
	private Short node1;
	private Short node2;
	private Short node3;
	private Short node4;
	private Short node5;
	private Short node6;
	private Short node7;
	private Short node8;
	private Short node9;
	private Short node10;
	private Short node11;
	private Short node12;
	private Short node13;
	private Short node14;
	private Integer pos;

	private Empl empl;

	public LifeCounseling() {
	}

	public LifeCounseling(Integer oid, Integer parentOid, Short week,
			Integer pos) {
		this.oid = oid;
		// this.idno = idno;
		this.parentOid = parentOid;
		this.week = week;
		this.pos = pos;
	}

	public LifeCounseling(Integer oid, Integer parentOid, Short week,
			Short node1, Short node2, Short node3, Short node4, Short node5,
			Short node6, Short node7, Short node8, Short node9, Short node10,
			Short node11, Short node12, Short node13, Short node14, Integer pos) {
		this.oid = oid;
		// this.idno = idno;
		this.parentOid = parentOid;
		this.week = week;
		this.node1 = node1;
		this.node2 = node2;
		this.node3 = node3;
		this.node4 = node4;
		this.node5 = node5;
		this.node6 = node6;
		this.node7 = node7;
		this.node8 = node8;
		this.node9 = node9;
		this.node10 = node10;
		this.node11 = node11;
		this.node12 = node12;
		this.node13 = node13;
		this.node14 = node14;
		this.pos = pos;
	}

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}

	public String getSchoolTerm() {
		return schoolTerm;
	}

	public void setSchoolTerm(String schoolTerm) {
		this.schoolTerm = schoolTerm;
	}

	public Integer getParentOid() {
		return this.parentOid;
	}

	public void setParentOid(Integer parentOid) {
		this.parentOid = parentOid;
	}

	public Short getWeek() {
		return this.week;
	}

	public void setWeek(Short week) {
		this.week = week;
	}

	public Short getNode1() {
		return this.node1;
	}

	public void setNode1(Short node1) {
		this.node1 = node1;
	}

	public Short getNode2() {
		return this.node2;
	}

	public void setNode2(Short node2) {
		this.node2 = node2;
	}

	public Short getNode3() {
		return this.node3;
	}

	public void setNode3(Short node3) {
		this.node3 = node3;
	}

	public Short getNode4() {
		return this.node4;
	}

	public void setNode4(Short node4) {
		this.node4 = node4;
	}

	public Short getNode5() {
		return this.node5;
	}

	public void setNode5(Short node5) {
		this.node5 = node5;
	}

	public Short getNode6() {
		return this.node6;
	}

	public void setNode6(Short node6) {
		this.node6 = node6;
	}

	public Short getNode7() {
		return this.node7;
	}

	public void setNode7(Short node7) {
		this.node7 = node7;
	}

	public Short getNode8() {
		return this.node8;
	}

	public void setNode8(Short node8) {
		this.node8 = node8;
	}

	public Short getNode9() {
		return this.node9;
	}

	public void setNode9(Short node9) {
		this.node9 = node9;
	}

	public Short getNode10() {
		return this.node10;
	}

	public void setNode10(Short node10) {
		this.node10 = node10;
	}

	public Short getNode11() {
		return this.node11;
	}

	public void setNode11(Short node11) {
		this.node11 = node11;
	}

	public Short getNode12() {
		return this.node12;
	}

	public void setNode12(Short node12) {
		this.node12 = node12;
	}

	public Short getNode13() {
		return this.node13;
	}

	public void setNode13(Short node13) {
		this.node13 = node13;
	}

	public Short getNode14() {
		return this.node14;
	}

	public void setNode14(Short node14) {
		this.node14 = node14;
	}

	public Integer getPos() {
		return this.pos;
	}

	public void setPos(Integer pos) {
		this.pos = pos;
	}

	public Empl getEmpl() {
		return empl;
	}

	public void setEmpl(Empl empl) {
		this.empl = empl;
	}

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}