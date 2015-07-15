package tw.edu.chit.model;

/**
 * This class supplement fields needed to display student info in a
 * user-friendly fashion. Namely, display attribute label instead code,
 * localized string instead English. DO NOT put these fields in Hibernate
 * mapping for there is not correspondent columns in database.
 * 
 * @author James F. Chiang
 * 
 */
public class StudentBase extends Member {

	private String sex2 = "";
	private String departClass2 = "";
	private String occurStatus2 = "";
	private String undeleteReason = "";
	private String birthday2 = "";
	private String gradSchlName = "";
	private String identBasicName = "";
	private boolean submitAutoClear = false;

	// private Investigation investigation;
	// private InvestigationG investigationG;
	private RegistrationCard registrationCard;

	public String getUndeleteReason() {
		return undeleteReason;
	}

	public void setUndeleteReason(String undeleteReason) {
		this.undeleteReason = undeleteReason;
	}

	public String getBirthday2() {
		return birthday2;
	}

	public void setBirthday2(String birthday2) {
		this.birthday2 = birthday2;
	}

	public String getGradSchlName() {
		return gradSchlName;
	}

	public void setGradSchlName(String gradSchlName) {
		this.gradSchlName = gradSchlName;
	}

	public String getIdentBasicName() {
		return identBasicName;
	}

	public void setIdentBasicName(String identBasicName) {
		this.identBasicName = identBasicName;
	}

	public String getDepartClass2() {
		return departClass2;
	}

	public void setDepartClass2(String departClass2) {
		this.departClass2 = departClass2;
	}

	public String getOccurStatus2() {
		return occurStatus2;
	}

	public void setOccurStatus2(String occurStatus2) {
		this.occurStatus2 = occurStatus2;
	}

	public String getSex2() {
		return sex2;
	}

	public void setSex2(String sex2) {
		this.sex2 = sex2;
	}

	public boolean isSubmitAutoClear() {
		return submitAutoClear;
	}

	public void setSubmitAutoClear(boolean submitAutoClear) {
		this.submitAutoClear = submitAutoClear;
	}

//	public Investigation getInvestigation() {
//		return investigation;
//	}
//
//	public void setInvestigation(Investigation investigation) {
//		this.investigation = investigation;
//	}
//
//	public InvestigationG getInvestigationG() {
//		return investigationG;
//	}
//
//	public void setInvestigationG(InvestigationG investigationG) {
//		this.investigationG = investigationG;
//	}

	public RegistrationCard getRegistrationCard() {
		return registrationCard;
	}

	public void setRegistrationCard(RegistrationCard registrationCard) {
		this.registrationCard = registrationCard;
	}
}
