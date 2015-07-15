package tw.edu.chit.struts.action.registration;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Rmark;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class RmarkManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward editRmark(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		
		HttpSession session = request.getSession(false);
		DynaActionForm stForm=(DynaActionForm)form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		String studentNo=(String)session.getAttribute("NO");
		String remark_name=stForm.getString("remark_name");
		String military=stForm.getString("military");
		String certificate=stForm.getString("certificate");
		
		//任何一項有即更新
		if(!certificate.equals("")||!remark_name.equals("")||!military.equals("")){			
			//若有記錄即更新
			if(manager.ezGetInt("SELECT COUNT(*)FROM Rmark WHERE student_no='"+studentNo+"'")>0){
				Rmark rmark=(Rmark) manager.hqlGetBy("FROM Rmark WHERE studentNo='"+studentNo+"'").get(0);
				rmark.setCertificate(certificate);
				rmark.setRemarkName(remark_name);
				rmark.setMilitary(military);
				manager.updateObject(rmark);
			}else{
			//若無記錄即新增
				Rmark rmark=new Rmark();
				rmark.setCertificate(certificate);
				rmark.setRemarkName(remark_name);
				rmark.setMilitary(military);
				rmark.setStudentNo(studentNo);
				manager.updateObject(rmark);
			}
		}else{
			//刪掉
			manager.executeSql("DELETE FROM Rmark WHERE student_no='"+studentNo+"'");
		}	
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",studentNo+studentNo+"資料修改完成"));
		saveMessages(request, msg);	//完成
		session.setAttribute("myRmark", manager.ezGetMap("SELECT * FROM Rmark WHERE student_no='"+studentNo+"'")); //存歷年更變記錄
		return mapping.findForward("Main");
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("EditRmark", "editRmark");
		return map;
	}

}
