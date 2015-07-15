package tw.edu.chit.struts.action.course;

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

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class OpenCourseBatchAction extends BaseLookupDispatchAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm batchForm = (DynaActionForm) form;
		session.removeAttribute("dtimeList");
		session.removeAttribute("dtimeBatchList");

		batchForm.set("courseNumber", "");
		batchForm.set("teacherId", "");
		batchForm.set("sterm", "");
		batchForm.set("choseType", "");
		batchForm.set("open", "");
		batchForm.set("elearning", "");
		batchForm.set("classLess", "");
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","批次維護模式暫不提供 <font color=red>時間</font> 相關的修改, 如: 排課、排教師、教室..."));
		saveMessages(request, msg);

		setContentPage(request.getSession(false), "course/OpenCourseBatch.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 查詢介面
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm batchForm = (DynaActionForm) form;
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		Toolket.resetCheckboxCookie(response, "dtimes");
		session.removeAttribute("dtimeBatchList");

		String cscode = (String) batchForm.get("courseNumber");
		String techid = (String) batchForm.get("teacherId");
		String term = (String) batchForm.get("sterm");
		String choseType = (String) batchForm.get("choseType");
		String open = (String) batchForm.get("open");
		String elearning = (String) batchForm.get("elearning");
		String classLess = (String) batchForm.get("classLess");

		UserCredential credential = (UserCredential) session.getAttribute("Credential");
		Clazz[] classes = credential.getClassInChargeAry();
		CourseManager manager = (CourseManager) getBean("courseManager");

		if(classLess.length()<4|| classLess.equals("")){
			//classLess="%";
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","範圍過大會造成執行緩慢! 請至少選一種科系!"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}

		//動作完成呼叫搜尋
		session.setAttribute("dtimeBatchList", manager.getDtimForBatch(classes, cscode, techid, term, choseType, open, elearning, classLess));


		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.setCourseName.serachComplete"));
		saveMessages(request, msg);


		return mapping.findForward("Main");
	}

	/**
	 * 重設
	 */
	public ActionForward clear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm setUpDtime = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, "dtimes");
		setUpDtime.set("courseNumber", "");
		setUpDtime.set("courseName", "");
		setUpDtime.set("teacherId", "");
		setUpDtime.set("teacherName", "");
		setUpDtime.set("sterm", "");
		setUpDtime.set("choseType", "%");
		setUpDtime.set("classLess", "");
		setUpDtime.set("open", "%");
		setUpDtime.set("elearning", "%");

		HttpSession session = request.getSession(false);

		//boolean createMode = false;
		//boolean checkError = false;
		//boolean editMode = false;

		session.removeAttribute("dtimeBatchList");

		return mapping.findForward("Main");
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("Query", "list");
		map.put("Clear", "clear");
		return map;
	}
}