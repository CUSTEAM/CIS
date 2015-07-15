package tw.edu.chit.struts.action.sysadmin;

import tw.edu.chit.model.domain.UserCredential;

public class ClassInChargeSetup4StudAffairAction extends ClassInChargeSetupAction {

	public ClassInChargeSetup4StudAffairAction() {
		authorityTarget = UserCredential.AuthorityOnStudAffair;
	}
}
