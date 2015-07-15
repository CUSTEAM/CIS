package tw.edu.chit.struts.action.studaffair;

import tw.edu.chit.model.domain.UserCredential;

public class ClassInChargeSetup4DrillmasterAction extends ClassInChargeSetupAction {
	
	public ClassInChargeSetup4DrillmasterAction() {
		authorityTarget = UserCredential.AuthorityOnDrillmaster;
	}

}
