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
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class DegreeHistManagerAction extends BaseLookupDispatchAction{
	//TODO 詢問人事室需不需要畢業日期
	public ActionForward editDegreeHist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");	
		DynaActionForm cForm = (DynaActionForm) form;
		
		String aIdno=cForm.getString("aIdno");
		String idno[]=cForm.getStrings("idno");
		String no[]=cForm.getStrings("no");
		String school_name[]=cForm.getStrings("school_name");
		String chk[]=cForm.getStrings("chk");
		String chkno[]=cForm.getStrings("chkno");
		String dept_name[]=cForm.getStrings("dept_name");
		String Oid[]=cForm.getStrings("Oid");
		String sequence[]=cForm.getStrings("sequence");
		
		for(int i=0; i<Oid.length; i++){
			if(!Oid[i].equals("")){//若是舊有的
				
				if(chkno[i].equals("")){//沒證號立即刪除
					manager.executeSql("DELETE FROM Empl_grdschl WHERE Oid='"+Oid[i]+"'");
				}else{//更新
					EmplGrdschl eg=(EmplGrdschl) manager.hqlGetBy("FROM EmplGrdschl WHERE Oid="+Oid[i]).get(0);
					eg.setChk(chk[i]);
					eg.setChkno(chkno[i]);
					eg.setDeptName(dept_name[i]);
					//eg.setIdno(aIdno);
					eg.setNo(no[i]);
					eg.setSchoolName(school_name[i]);
					eg.setSequence(Short.parseShort(sequence[i]));
					manager.updateObject(eg);
				}
				
			}else{//新增的
				
				if(!chkno[i].equals("")){
					EmplGrdschl eg=new EmplGrdschl();
					eg.setChk(chk[i]);
					eg.setChkno(chkno[i]);
					eg.setDeptName(dept_name[i]);
					eg.setIdno(aIdno);
					eg.setNo(no[i]);
					eg.setSchoolName(school_name[i]);
					eg.setSequence(Short.parseShort(sequence[i]));
					manager.updateObject(eg);
				}
				
			}
		}
		
		session.setAttribute("degrees", manager.ezGetBy("SELECT * FROM Empl_grdschl WHERE idno='"+aIdno+"'"));
		
		
		
		return mapping.findForward("Main");
	}
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("EditDegreeHist", "editDegreeHist");
		return map;
	}

}
