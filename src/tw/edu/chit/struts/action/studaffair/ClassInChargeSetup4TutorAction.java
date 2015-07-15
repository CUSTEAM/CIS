package tw.edu.chit.struts.action.studaffair;

import tw.edu.chit.model.domain.UserCredential;

public class ClassInChargeSetup4TutorAction extends ClassInChargeSetupAction {
	
	public ClassInChargeSetup4TutorAction() {
		authorityTarget = UserCredential.AuthorityOnTutor;
	}

}
