package tw.edu.chit.model;

import java.util.Date;

/**
 * AmsShiftTime entity. @author MyEclipse Persistence Tools
 */

public class AmsShiftTime implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private Date in1;
	private Date in2;
	private Date in3;
	private Date in4;
	private Date in5;
	private Date in6;
	private Date in7;
	private Date out1;
	private Date out2;
	private Date out3;
	private Date out4;
	private Date out5;
	private Date out6;
	private Date out7;
	private Integer eat;

	// Constructors

	/** default constructor */
	public AmsShiftTime() {
	}

	/** minimal constructor */
	public AmsShiftTime(String id) {
		this.id = id;
	}

	/** full constructor */
	public AmsShiftTime(String name, Date in1, Date in2, Date in3, Date in4,
			Date in5, Date in6, Date in7, Date out1, Date out2, Date out3,
			Date out4, Date out5, Date out6, Date out7, Integer eat) {
		this.name = name;
		this.in1 = in1;
		this.in2 = in2;
		this.in3 = in3;
		this.in4 = in4;
		this.in5 = in5;
		this.in6 = in6;
		this.in7 = in7;
		this.out1 = out1;
		this.out2 = out2;
		this.out3 = out3;
		this.out4 = out4;
		this.out5 = out5;
		this.out6 = out6;
		this.out7 = out7;
		this.eat = eat;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getIn1() {
		return this.in1;
	}

	public void setIn1(Date in1) {
		this.in1 = in1;
	}

	public Date getIn2() {
		return this.in2;
	}

	public void setIn2(Date in2) {
		this.in2 = in2;
	}

	public Date getIn3() {
		return this.in3;
	}

	public void setIn3(Date in3) {
		this.in3 = in3;
	}

	public Date getIn4() {
		return this.in4;
	}

	public void setIn4(Date in4) {
		this.in4 = in4;
	}

	public Date getIn5() {
		return this.in5;
	}

	public void setIn5(Date in5) {
		this.in5 = in5;
	}

	public Date getIn6() {
		return this.in6;
	}

	public void setIn6(Date in6) {
		this.in6 = in6;
	}

	public Date getIn7() {
		return this.in7;
	}

	public void setIn7(Date in7) {
		this.in7 = in7;
	}

	public Date getOut1() {
		return this.out1;
	}

	public void setOut1(Date out1) {
		this.out1 = out1;
	}

	public Date getOut2() {
		return this.out2;
	}

	public void setOut2(Date out2) {
		this.out2 = out2;
	}

	public Date getOut3() {
		return this.out3;
	}

	public void setOut3(Date out3) {
		this.out3 = out3;
	}

	public Date getOut4() {
		return this.out4;
	}

	public void setOut4(Date out4) {
		this.out4 = out4;
	}

	public Date getOut5() {
		return this.out5;
	}

	public void setOut5(Date out5) {
		this.out5 = out5;
	}

	public Date getOut6() {
		return this.out6;
	}

	public void setOut6(Date out6) {
		this.out6 = out6;
	}

	public Date getOut7() {
		return this.out7;
	}

	public void setOut7(Date out7) {
		this.out7 = out7;
	}

	public Integer getEat() {
		return this.eat;
	}

	public void setEat(Integer eat) {
		this.eat = eat;
	}

}