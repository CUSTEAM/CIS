package tw.edu.chit.struts.action.studaffair;

import tw.edu.chit.model.domain.UserCredential;

public class ClassInChargeSetup4ChairmanAction extends ClassInChargeSetupAction {
	
	public ClassInChargeSetup4ChairmanAction() {
		authorityTarget = UserCredential.AuthorityOnChairman;
	}

}
