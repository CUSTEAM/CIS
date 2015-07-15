package tw.edu.chit.struts.action.sysadmin;

import tw.edu.chit.model.domain.UserCredential;

public class ClassInChargeSetup4TeachAffairAction extends ClassInChargeSetupAction {

	public ClassInChargeSetup4TeachAffairAction() {
		authorityTarget = UserCredential.AuthorityOnTeachAffair;
	}
}
