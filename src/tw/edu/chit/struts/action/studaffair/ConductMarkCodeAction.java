package tw.edu.chit.struts.action.studaffair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import tw.edu.chit.dao.StudAffairDAO;
import tw.edu.chit.model.Code1;
import tw.edu.chit.model.Dilg;
import tw.edu.chit.model.Just;
import tw.edu.chit.model.Module;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Global;
import tw.edu.chit.util.Toolket;

public class ConductMarkCodeAction extends BaseLookupDispatchAction {
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Create",			"add");
		map.put("Query",			"query");
		map.put("Delete",			"delete");
		map.put("DeleteConfirm", 	"delConfirm");
		map.put("Modify",			"modify");
		map.put("OK", 				"save");
		map.put("Cancel", 			"cancel");
		return map;		
	}
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		
		DynaActionForm dynForm = (DynaActionForm)form;
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
			
		setContentPage(session, "studaffair/ConductMarkCode.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		DynaActionForm dynForm = (DynaActionForm)form;
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		Map initMap = new HashMap();
		initMap.put("mode", "Create");
		session.setAttribute("ConductMarkCodeEditInit", initMap);
		
		session.removeAttribute("ConductMarkCodeEdit");
		setContentPage(session, "studaffair/ConductMarkCodeEdit.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		Toolket.resetCheckboxCookie(response, "cooki_ConductMarkCode");
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		Map initMap = new HashMap();
		
		String code = aForm.getString("no");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		List<Code1> code1List = sm.findConductMarkCode(code);
		
		initMap.put("no", code);
		session.setAttribute("ConductMarkCodeInit", initMap);
		
		session.setAttribute("ConductMarkCodeList", code1List);
		setContentPage(session, "studaffair/ConductMarkCode.jsp");
		
		return mapping.findForward("Main");
	}

	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ActionMessages msgs = new ActionMessages();
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		List<Code1> selCodeList = getCode1SelectedList("cooki_ConductMarkCode",request);;
		
		if(selCodeList.size() > 1 || selCodeList.isEmpty()){
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", "請選取一項以茲修改!"));
			saveMessages(request,msgs);
			setContentPage(session, "studaffair/ConductMarkCode.jsp");
		}else{
			session.setAttribute("ConductMarkCodeModify", selCodeList.get(0));
			Map initMap = new HashMap();
			initMap.put("mode", "Modify");
			session.setAttribute("ConductMarkCodeEditInit", initMap);
			setContentPage(session, "studaffair/ConductMarkCodeEdit.jsp");
		}
		
		return mapping.findForward("Main");

	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionMessages msgs = new ActionMessages();
		
		HttpSession session = request.getSession(false);
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		Map initMap = (Map)session.getAttribute("ConductMarkCodeEditInit");
		String mode = initMap.get("mode").toString();
		DynaActionForm aForm = (DynaActionForm)form;
		
		Code1 selCode = (Code1)session.getAttribute("ConductMarkCodeModify");
		if(mode.equalsIgnoreCase("Modify")){
			msgs = sm.modifyConductMarkCode(selCode, aForm.getMap());
		}else if(mode.equalsIgnoreCase("Create")){
			msgs = sm.createConductMarkCode(aForm.getMap());
		}
		if(msgs.isEmpty()){
			String msgStr = "";
			if(mode.equalsIgnoreCase("Modify")){
				msgStr = "操行評語修改成功!";
			}else if(mode.equalsIgnoreCase("Create")){
				msgStr = "操行評語新增成功!";;
			}
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", msgStr));
			saveMessages(request,msgs);
			session.removeAttribute("ConductMarkCodeList");
			setContentPage(session, "studaffair/ConductMarkCode.jsp");
		}else{
			saveMessages(request,msgs);
			setContentPage(session, "studaffair/ConductMarkCodeEdit.jsp");
			Map formMap = aForm.getMap();
			session.setAttribute("ConductMarkCodeEdit", formMap);
		}
		return mapping.findForward("Main");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionMessages msgs = new ActionMessages();
		
		HttpSession session = request.getSession(false);
		
		List<Code1> selCodeList = getCode1SelectedList("cooki_ConductMarkCode",request);	//Records selected for delete
		if(selCodeList.size() > 1 || selCodeList.isEmpty()){
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", "請選取一項以茲刪除!"));
			saveMessages(request,msgs);
			setContentPage(session, "studaffair/ConductMarkCode.jsp");
		}else{
			session.setAttribute("ConductMarkCodeDelete", selCodeList);
			Map initMap = new HashMap();
			initMap.put("mode", "Delete");
			session.setAttribute("ConductMarkCodeEditInit", initMap);
			setContentPage(session, "studaffair/ConductMarkCodeDelete.jsp");
		}
		return new ActionForward(mapping.findForward("Main").getPath(), true);
		
	}
	
	public ActionForward delConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(false);
		List<Code1> selCodes = (List<Code1>)session.getAttribute("ConductMarkCodeDelete");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		ActionMessages errs = sm.delConductMarkCode(selCodes, Toolket.getBundle(request, "messages.studaffair"));
		// no undeleteScores will happen even if delete failure
		if (errs.isEmpty()) {
			session.removeAttribute("ConductMarkCodeList");
			session.removeAttribute("ConductMarkCodeDelete");
			Map initMap = (Map)session.getAttribute("ConductMarkCodeInit");
			DynaActionForm dynForm = (DynaActionForm)form;
			dynForm.set("no", initMap.get("no"));
			//log.debug("delete Just success, bpYear=" + dynForm.getString("bpYear"));
			
			return query(mapping, (ActionForm)dynForm, request, response);
		} else {
			saveErrors(request, errs);
		}
		setContentPage(session, "studaffair/ConductMarkCode.jsp");
		return mapping.findForward("Main");		
	}

	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		Map initMap = (Map)session.getAttribute("ConductMarkCodeEditInit");
		String mode = initMap.get("mode").toString();
		if(mode.equalsIgnoreCase("Delete")){
			session.removeAttribute("ConductMarkCodeDelete");
		}else if(mode.equalsIgnoreCase("Create")){
			session.removeAttribute("ConductMarkCodeEdit");
		}else if(mode.equalsIgnoreCase("Modify")){
			session.removeAttribute("ConductMarkCodeEdit");
			session.removeAttribute("ConductMarkCodeModify");
		}
		setContentPage(request.getSession(false), "studaffair/ConductMarkCode.jsp");
		return mapping.findForward("Main");
	}

	
	private List<Code1> getCode1SelectedList(String cooki_name, HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, cooki_name);
		List<Code1> codeList = (List<Code1>) session
				.getAttribute("ConductMarkCodeList");
		List<Code1> selCodeList = new ArrayList<Code1>();
		Code1 code;
		StudAffairDAO dao = (StudAffairDAO) getBean("studAffairDAO");
		for (Iterator<Code1> codeIter = codeList.iterator(); codeIter.hasNext();) {
			code = codeIter.next();
			if (Toolket.isValueInCookie(code.getOid().toString(), oids)) {
				dao.reload(code);
				selCodeList.add(code);
			}
		}
		return selCodeList;
	}

	
}
