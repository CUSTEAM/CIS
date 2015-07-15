package tw.edu.chit.struts.action.personnel;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.EmplContract;
import tw.edu.chit.model.EmplGrdschl;
import tw.edu.chit.model.EmplLicence;
import tw.edu.chit.model.EmplPehist;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 實務經驗管理
 * @author JOHN
 *
 */
public class LicenceManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward editLicence(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");	
		DynaActionForm cForm = (DynaActionForm) form;
		
		String aIdno=cForm.getString("aIdno");
		String Oid[]=cForm.getStrings("Oid");
		//String idno[]=cForm.getStrings("idno");
		String deptNo[]=cForm.getStrings("deptNo");
		//String school_year[]=cForm.getStrings("school_year");
		//String school_term[]=cForm.getStrings("school_term");
		String licence_type[]=cForm.getStrings("licence_type");
		String licence_name[]=cForm.getStrings("licence_name");
		String organ[]=cForm.getStrings("organ");
		String licence_no[]=cForm.getStrings("licence_no");
		
		for(int i=0; i<Oid.length; i++){
			if(Oid[i].equals("")){//新增的
				if(!licence_type[i].equals("")&& !licence_name[i].equals("")){
					EmplLicence el=new EmplLicence();
					el.setDeptNo(deptNo[i]);
					el.setIdno(aIdno);
					el.setLicenceName(licence_name[i]);
					el.setLicenceNo(licence_no[i]);
					el.setLicenceType(licence_type[i]);
					el.setOrgan(organ[i]);
					//el.setSchoolTerm(Short.parseShort(school_term[i]));
					//el.setSchoolYear(Short.parseShort(school_year[i]));
					manager.updateObject(el);
				}
				
			}else{//舊有修改的
				if(!licence_type[i].equals("")&&!licence_name[i].equals("")){
					EmplLicence el=(EmplLicence)manager.hqlGetBy("FROM EmplLicence WHERE Oid="+Oid[i]).get(0);
					el.setDeptNo(deptNo[i]);
					el.setIdno(aIdno);
					el.setLicenceName(licence_name[i]);
					el.setLicenceNo(licence_no[i]);
					el.setLicenceType(licence_type[i]);
					el.setOrgan(organ[i]);
					//el.setSchoolTerm(Short.parseShort(school_term[i]));
					//el.setSchoolYear(Short.parseShort(school_year[i]));
					manager.updateObject(el);
				}else{
					manager.executeSql("DELETE FROM Empl_licence WHERE Oid="+Oid[i]);
				}
				
			}
		}

		//證照
		List licences=manager.ezGetBy("SELECT * FROM Empl_licence WHERE idno='"+aIdno+"'");
		session.setAttribute("licences", licences);
		return mapping.findForward("Main");
	}
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("EditLicence", "editLicence");
		return map;
	}

}
