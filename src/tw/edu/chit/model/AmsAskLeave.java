package tw.edu.chit.model;

/**
 * AmsAskLeave entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AmsAskLeave implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String score;

	// Constructors

	/** default constructor */
	public AmsAskLeave() {
	}

	/** full constructor */
	public AmsAskLeave(String name, String score) {
		this.name = name;
		this.score = score;
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

	public String getScore() {
		return this.score;
	}

	public void setScore(String score) {
		this.score = score;
	}

}