package tw.edu.chit.struts.action.teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.dao.ScoreDAO;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class ScoreMidTermAction extends BaseLookupDispatchAction  {
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("OK","edit");
		map.put("Cancel", "cancel");
		return map;		
	}
	
	public ActionForward unspecified(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
			throws Exception {

			HttpSession session = request.getSession(false);
			DynaActionForm aForm = (DynaActionForm)form;
			
			UserCredential user = (UserCredential)session.getAttribute("Credential");
			
			String teacherName = user.getMember().getName();
			String teacherId = user.getMember().getAccount();
			String departClass = "";
			String cscode = "";
			String scoretype = aForm.getString("scoretype");
			
			ScoreManager sm = (ScoreManager)getBean("scoreManager");
			List<Dtime> dtimelist = sm.findDtimeByTeacher(teacherId);
						
			session.setAttribute("TchScoreMidInChoose", dtimelist);

			Map initValue = new HashMap();
			initValue.put("teacherName", teacherName);
			initValue.put("teacherId", teacherId);
			initValue.put("scoretype", scoretype);
			session.setAttribute("TchScoreMidInfo", initValue);
			
			session.removeAttribute("TchScoreMidInEdit");
			session.removeAttribute("TchScoreMidPrint");
			
			setContentPage(session, "teacher/TeachClass.jsp");
			return mapping.findForward("Main");
	}

	
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm)form;
		ResourceBundle bundle = Toolket.getBundle(request);
		ActionMessages messages = new ActionMessages();

		String selclass = aForm.getString("selclass").trim();
		String cscode = aForm.getString("cscode").trim();
		String scoretype = aForm.getString("scoretype");
		int  dtimeoid = Integer.parseInt(aForm.getString("dtimeoid").trim());
		String teacherName = ((Map)session.getAttribute("TchScoreMidInfo")).get("teacherName").toString();
		String teacherId = ((Map)session.getAttribute("TchScoreMidInfo")).get("teacherId").toString();
		String departClass = "";
		
		ScoreManager sm = (ScoreManager)getBean("scoreManager");
		String classname = sm.findClassName(selclass);
		String cscodeName = sm.findCourseName(cscode);

		session.removeAttribute("TchScoreMidPrint");
		//log.debug("=======> input->departClass=" + departClass);

		ActionMessages msgs = new ActionMessages();
		msgs = sm.chkTchScoreUploadOpened(scoretype, selclass);
		if(!msgs.isEmpty()) {
			saveMessages(request, msgs);
			setContentPage(session, "teacher/TeachClass.jsp");
			return mapping.findForward("Main");
		} 
		
		//List<Seld> seldlist = sm.findSeldScoreByInputForm(selclass, cscode);
		List<Seld> seldlist = sm.findSeldScoreByInputForm("" + dtimeoid);
		//log.debug("=======> input->SelInEdit=" + seldlist.size());
		Map initValue = new HashMap();
		
		if(seldlist.size() > 0){
			initValue.put("teacherName", teacherName);
			initValue.put("teacherId", teacherId);
			initValue.put("scoretype", scoretype);
			initValue.put("Dtime_oid", seldlist.get(1).getDtimeOid());
			initValue.put("departClass", selclass);
			initValue.put("depClassName", classname);
			initValue.put("cscode", cscode);
			initValue.put("cscodeName", cscodeName);
			
			session.setAttribute("TchScoreMidInfo", initValue);
			session.setAttribute("TchScoreMidInEdit", seldlist);
			setContentPage(session, "teacher/ScoreMidTermEdit.jsp");
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.NoSeldScoreFound"));
			saveMessages(request, messages);
			setContentPage(session, "teacher/TeachClass.jsp");
		}
		// session.removeAttribute("ScoreInputInfo");
			
		
		// log.debug("=======> input->yn=" + yn);
		return mapping.findForward("Main");

	}
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("TchScoreMidInChoose");
		session.removeAttribute("TchScoreMidInfo");
		session.removeAttribute("TchScoreMidInEdit");
		setContentPage(request.getSession(false), "Directory.jsp");
		return mapping.findForward("Main");
	}

	
}
