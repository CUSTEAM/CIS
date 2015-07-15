package tw.edu.chit.struts.action.registration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

import tw.edu.chit.model.Entrno;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class EntranceDocNoAction extends BaseLookupDispatchAction {
	
	private static final String cookieSelected		= "EntranceDocNo";
	private static final String cookieDeleteVerify	= "EntrnoDelete";
	private static final String attribDisplayList	= "EntrnoList";
	private static final String attribDisplayDeleteVerify = "EntranceDocNoDelete";

	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Query",		"list");
		map.put("Forward.List",	"list");
		map.put("Create", 		"create");
		map.put("Delete", 		"deleteVerify");
		map.put("DeleteConfirm","deleteConfirm");
		map.put("Cancel", 		"deleteCancel");
		map.put("Modify",		"modify");
		return map;		
	}

	public ActionForward unspecified(ActionMapping mapping,
			 						 ActionForm form,
			 						 HttpServletRequest request,
			 						 HttpServletResponse response)
			throws Exception {
		
		setContentPage(request.getSession(false), "registration/EntranceDocNo.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward list(ActionMapping mapping,
			  				  ActionForm form,
			  				  HttpServletRequest request,
			  				  HttpServletResponse response)
			throws Exception {

		Toolket.resetCheckboxCookie(response, cookieSelected);
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm)form;
		String startNo = aForm.getString("startNo2").trim();
		String endNo   = aForm.getString("endNo2").trim();
		String docNo   = aForm.getString("docNo2").trim();
		MemberManager mm = (MemberManager)getBean("memberManager");
		List<Entrno> entrno = mm.findEntrnoByStartEndNoDocNo(startNo, endNo, docNo);
		/*
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String Sel_first_stno = " first_stno Like '%"+startNo+"%' " ;
		String Sel_second_stno = " second_stno like '%"+endNo+"%' " ;
		String Sel_permission_no = " permission_no like '%"+docNo+"%'" ;
		String Sql = " Select * From entrno ";
		if(!startNo.equals("")){
			Sql =Sql + "Where" + Sel_first_stno;
		}
		if(!endNo.equals("")){
			Sql =Sql + "Where" + Sel_second_stno;
		}
		if(!docNo.equals("")){
			Sql =Sql + "Where" + Sel_permission_no;
		}
		List<Entrno> entrno = manager.ezGetBy(Sql);
		*/

		session.setAttribute(attribDisplayList, entrno);
		setContentPage(session, "registration/EntranceDocNo.jsp");
		return mapping.findForward("Main");
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward create(ActionMapping mapping,
								ActionForm form,
								HttpServletRequest request,
								HttpServletResponse response)
			throws Exception {

		/*
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();
		
		String startNo = aForm.getString("startNo");
		String endNo = aForm.getString("endNo");
		String docNo = aForm.getString("docNo");
		
		String Sel_Sno=manager.ezGetString(" Select first_stno From entrno Where first_stno='"+startNo+"' ");
		String Sel_Eno=manager.ezGetString(" Select second_stno From entrno Where second_stno='"+endNo+"' ");
		
		manager.executeSql(" INSERT INTO entrno (first_stno, second_stno, permission_no) VALUES ('"+startNo+"','"+endNo+"','"+docNo+"') ");
		
		*/
		
		
		Map initValue = new HashMap();
		initValue.put("mode", "Create");
		HttpSession session = request.getSession(false);
		session.setAttribute("EntranceDocNoEdit", initValue);
		setContentPage(session, "registration/EntranceDocNoEdit.jsp");
		return mapping.findForward("Main");
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward modify(ActionMapping mapping,
								ActionForm form,
								HttpServletRequest request,
								HttpServletResponse response)
			throws Exception {

		List<Entrno> selEntrno = getEntrnoSelectedList(request);
		Entrno entrno = selEntrno.get(0);
		Map initValue = new HashMap();
		initValue.put("mode", "Modify");
		setEntrnoInitValue(entrno, initValue);
		HttpSession session = request.getSession(false);
		session.setAttribute("EntranceDocNoEdit", initValue);
		session.setAttribute("EntrnoInEdit", entrno);
		setContentPage(session, "registration/EntranceDocNoEdit.jsp");
		return mapping.findForward("Main");
	}

	@SuppressWarnings("unchecked")
	public ActionForward deleteVerify(ActionMapping mapping,
			  						  ActionForm form,
			  						  HttpServletRequest request,
			  						  HttpServletResponse response)
			throws Exception {

		List<Entrno> selEntrno = getEntrnoSelectedList(request);
		Toolket.setAllCheckboxCookie(response, cookieDeleteVerify, selEntrno.size());
		HttpSession session = request.getSession(false);
		session.setAttribute(attribDisplayDeleteVerify, selEntrno);
		setContentPage(session, "registration/EntranceDocNoDelete.jsp");
		return new ActionForward(mapping.findForward("Main").getPath(), true);
	}

	@SuppressWarnings("unchecked")
	public ActionForward deleteConfirm(ActionMapping mapping,
			   							ActionForm form,
			   							HttpServletRequest request,
			   							HttpServletResponse response)
			throws Exception {

		List<Entrno> selEntrno = getEntrnoDeletedList(request);
		MemberManager mm = (MemberManager)getBean("memberManager");
		mm.deleteEntrnos(selEntrno);
		HttpSession session = request.getSession(false);
		session.removeAttribute(attribDisplayDeleteVerify);
		return list(mapping, form, request, response);
	}
	
	@SuppressWarnings("unchecked")
	private List getEntrnoSelectedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, cookieSelected);
		List<Entrno> entrno = (List<Entrno>)session.getAttribute(attribDisplayList);
		List<Entrno> selEnrno = new ArrayList<Entrno>();
		Entrno no;
		for (Iterator<Entrno> noIter = entrno.iterator(); noIter.hasNext();) {
			no = noIter.next();
			if (Toolket.isValueInCookie(no.getOid().toString(), oids)) {
				selEnrno.add(no);
			}
		}
		return selEnrno;
	}
	
	public ActionForward deleteCancel(ActionMapping mapping,
			  						  ActionForm form,
			  						  HttpServletRequest request,
			  						  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute(attribDisplayDeleteVerify);
		setContentPage(session, "registration/EntranceDocNo.jsp");
		return mapping.findForward("Main");
	}
	
	@SuppressWarnings("unchecked")
	private List getEntrnoDeletedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, cookieDeleteVerify);
		List<Entrno> entrno = (List<Entrno>)session.getAttribute(attribDisplayDeleteVerify);
		List<Entrno> selEnrno = new ArrayList<Entrno>();
		Entrno no;
		for (Iterator<Entrno> noIter = entrno.iterator(); noIter.hasNext();) {
			no = noIter.next();
			if (Toolket.isValueInCookie(no.getOid().toString(), oids)) {
				selEnrno.add(no);
			}
		}
		return selEnrno;
	}
	
	@SuppressWarnings("unchecked")
	private void setEntrnoInitValue(Entrno entrno, Map initValue) {		
		initValue.put("startNo", entrno.getFirstStno());
		initValue.put("endNo", entrno.getSecondStno());
		initValue.put("docNo", entrno.getPermissionNo());
	};

}
