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
import tw.edu.chit.dao.StudAffairDAO;
import tw.edu.chit.model.Code1;
import tw.edu.chit.model.Code2;
import tw.edu.chit.model.Module;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class BonusPenaltyReasonCodeAction extends BaseLookupDispatchAction {
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
			
		setContentPage(session, "studaffair/BonusPenaltyReasonCode.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		DynaActionForm dynForm = (DynaActionForm)form;
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		Map initMap = new HashMap();
		initMap.put("mode", "Create");
		session.setAttribute("BonusPenaltyReasonCodeEditInit", initMap);
		
		session.removeAttribute("BonusPenaltyReasonCodeEdit");
		setContentPage(session, "studaffair/BonusPenaltyReasonCodeEdit.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		Toolket.resetCheckboxCookie(response, "cooki_BonusPenaltyReasonCode");
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		Map initMap = new HashMap();
		
		String code = aForm.getString("no");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		List<Code2> code1List = sm.findBonusPenaltyReasonCode(code);
		
		initMap.put("no", code);
		session.setAttribute("BonusPenaltyReasonCodeInit", initMap);
		
		session.setAttribute("BonusPenaltyReasonCodeList", code1List);
		setContentPage(session, "studaffair/BonusPenaltyReasonCode.jsp");
		
		return mapping.findForward("Main");
	}

	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ActionMessages msgs = new ActionMessages();
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		List<Code2> selCodeList = getCode1SelectedList("cooki_BonusPenaltyReasonCode",request);;
		
		if(selCodeList.size() > 1 || selCodeList.isEmpty()){
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", "請選取一項以茲修改!"));
			saveMessages(request,msgs);
			setContentPage(session, "studaffair/BonusPenaltyReasonCode.jsp");
		}else{
			session.setAttribute("BonusPenaltyReasonCodeModify", selCodeList.get(0));
			Map initMap = new HashMap();
			initMap.put("mode", "Modify");
			session.setAttribute("BonusPenaltyReasonCodeEditInit", initMap);
			setContentPage(session, "studaffair/BonusPenaltyReasonCodeEdit.jsp");
		}
		
		return mapping.findForward("Main");

	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionMessages msgs = new ActionMessages();
		
		HttpSession session = request.getSession(false);
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		
		Map initMap = (Map)session.getAttribute("BonusPenaltyReasonCodeEditInit");
		String mode = initMap.get("mode").toString();
		DynaActionForm aForm = (DynaActionForm)form;
		
		Code2 selCode = (Code2)session.getAttribute("BonusPenaltyReasonCodeModify");
		if(mode.equalsIgnoreCase("Modify")){
			msgs = sm.modifyBonusPenaltyReasonCode(selCode, aForm.getMap());
		}else if(mode.equalsIgnoreCase("Create")){
			msgs = sm.createBonusPenaltyReasonCode(aForm.getMap());
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
			session.removeAttribute("BonusPenaltyReasonCodeList");
			setContentPage(session, "studaffair/BonusPenaltyReasonCode.jsp");
		}else{
			saveMessages(request,msgs);
			setContentPage(session, "studaffair/BonusPenaltyReasonCodeEdit.jsp");
			Map formMap = aForm.getMap();
			session.setAttribute("BonusPenaltyReasonCodeEdit", formMap);
		}
		return mapping.findForward("Main");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionMessages msgs = new ActionMessages();
		
		HttpSession session = request.getSession(false);
		
		List<Code2> selCodeList = getCode1SelectedList("cooki_BonusPenaltyReasonCode",request);	//Records selected for delete
		if(selCodeList.size() > 1 || selCodeList.isEmpty()){
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("MessageN1", "請選取一項以茲刪除!"));
			saveMessages(request,msgs);
			setContentPage(session, "studaffair/BonusPenaltyReasonCode.jsp");
		}else{
			session.setAttribute("BonusPenaltyReasonCodeDelete", selCodeList);
			Map initMap = new HashMap();
			initMap.put("mode", "Delete");
			session.setAttribute("BonusPenaltyReasonCodeEditInit", initMap);
			setContentPage(session, "studaffair/BonusPenaltyReasonCodeDelete.jsp");
		}
		return new ActionForward(mapping.findForward("Main").getPath(), true);
		
	}
	
	public ActionForward delConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(false);
		List<Code2> selCodes = (List<Code2>)session.getAttribute("BonusPenaltyReasonCodeDelete");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		ActionMessages errs = sm.delBonusPenaltyReasonCode(selCodes, Toolket.getBundle(request, "messages.studaffair"));
		// no undeleteScores will happen even if delete failure
		if (errs.isEmpty()) {
			session.removeAttribute("BonusPenaltyReasonCodeList");
			session.removeAttribute("BonusPenaltyReasonCodeDelete");
			Map initMap = (Map)session.getAttribute("BonusPenaltyReasonCodeInit");
			DynaActionForm dynForm = (DynaActionForm)form;
			dynForm.set("no", initMap.get("no"));
			//log.debug("delete Just success, bpYear=" + dynForm.getString("bpYear"));
			
			return query(mapping, (ActionForm)dynForm, request, response);
		} else {
			saveErrors(request, errs);
		}
		setContentPage(session, "studaffair/BonusPenaltyReasonCode.jsp");
		return mapping.findForward("Main");		
	}

	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		Map initMap = (Map)session.getAttribute("BonusPenaltyReasonCodeEditInit");
		String mode = initMap.get("mode").toString();
		if(mode.equalsIgnoreCase("Delete")){
			session.removeAttribute("BonusPenaltyReasonCodeDelete");
		}else if(mode.equalsIgnoreCase("Create")){
			session.removeAttribute("BonusPenaltyReasonCodeEdit");
		}else if(mode.equalsIgnoreCase("Modify")){
			session.removeAttribute("BonusPenaltyReasonCodeEdit");
			session.removeAttribute("BonusPenaltyReasonCodeModify");
		}
		setContentPage(request.getSession(false), "studaffair/BonusPenaltyReasonCode.jsp");
		return mapping.findForward("Main");
	}

	
	private List<Code2> getCode1SelectedList(String cooki_name, HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, cooki_name);
		List<Code2> codeList = (List<Code2>) session
				.getAttribute("BonusPenaltyReasonCodeList");
		List<Code2> selCodeList = new ArrayList<Code2>();
		Code2 code;
		StudAffairDAO dao = (StudAffairDAO) getBean("studAffairDAO");
		for (Iterator<Code2> codeIter = codeList.iterator(); codeIter.hasNext();) {
			code = codeIter.next();
			if (Toolket.isValueInCookie(code.getOid().toString(), oids)) {
				dao.reload(code);
				selCodeList.add(code);
			}
		}
		return selCodeList;
	}

}
