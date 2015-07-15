package tw.edu.chit.struts.action.studaffair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.text.DateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import tw.edu.chit.dao.AmsDAO;
import tw.edu.chit.dao.ScoreDAO;
import tw.edu.chit.dao.StudAffairDAO;
import tw.edu.chit.model.Adcd;
import tw.edu.chit.model.AmsDocApply;
import tw.edu.chit.model.Code1;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.Dilg;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.DtimeStudaffair;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;
import tw.edu.chit.util.Global;

public class DtimeRollCallAction  extends BaseLookupDispatchAction {
	
	
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("Save", "save");
		map.put("Back", "cancel");
		map.put("Cancel", "cancel");
		return map;
	}
	
	/**
	 * @comment Action預設之執行方法
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm dynForm = (DynaActionForm)form;
		
		HttpSession session = request.getSession(false);
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		ScoreManager scm = (ScoreManager) getBean("scoreManager");
		Map DtimeRollCallInit = new HashMap();
		
		String campus = dynForm.getString("campusInChargeSAF").trim();
		String school = dynForm.getString("schoolInChargeSAF").trim();
		String dept = dynForm.getString("deptInChargeSAF").trim();
		String myclass = dynForm.getString("classInChargeSAF").trim();
		String cscode = dynForm.getString("cscode").trim();
		
		DtimeRollCallInit.put("campus", campus);
		DtimeRollCallInit.put("school", school);
		DtimeRollCallInit.put("dept", dept);
		DtimeRollCallInit.put("clazz", myclass);
		
		if(!myclass.equals("")) {
			List dtimelist = new ArrayList();
			List cscodelist = new ArrayList();

			// 查詢該班所有課程
			dtimelist = scm.findCscodeByClass(myclass);
			Dtime dtime;
			for (Iterator<Dtime> dtimeIter = dtimelist.iterator(); dtimeIter
					.hasNext();) {
				dtime = dtimeIter.next();
				Map cmap = new HashMap();
				cmap.put("cscode", dtime.getCscode());
				cmap.put("chiName", dtime.getChiName2());
				cscodelist.add(cmap);
			}
			session.setAttribute("RollCallCourses", cscodelist);
			
			if(!cscode.equals("")){
				//Get Dummy Roll Call information
				DtimeRollCallInit.put("cscode", cscode);
				
				Map TermRollCallInfo = new HashMap();
				TermRollCallInfo.put("className", Toolket.getClassFullName(myclass));
				TermRollCallInfo.put("courseName", scm.findCourseName(cscode));
				
				List<DtimeStudaffair> dslist = new ArrayList<DtimeStudaffair>();
				Toolket.resetCheckboxCookie(response, "TermRollCall");
				dslist = sm.getTermRollCallInfo(myclass, cscode);
				StringBuffer st = new StringBuffer();
				st.append("|");
				for(DtimeStudaffair ds: dslist){
					if(ds.getIsRollCall().shortValue() == (short)1){
						st.append(ds.getOid() + "|");
					}
				}
				st.append("|");
				Toolket.setCookie(response, "TermRollCall", st.toString());
				session.setAttribute("TermRollCallInfo", TermRollCallInfo);
				session.setAttribute("TermRollCall", dslist);
				
			}
		}else{
			session.removeAttribute("RollCallCourses");
			session.removeAttribute("TermRollCall");
		}

		session.setAttribute("DtimeRollCallInit", DtimeRollCallInit);
		setContentPage(session, "studaffair/DtimeRollCall.jsp");
		return mapping.findForward("Main");

	}
	
		
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("RollCallCourses");
		session.removeAttribute("TermRollCall");
		session.removeAttribute("DtimeRollCallInit");
		session.removeAttribute("TermRollCallInfo");
		setContentPage(request.getSession(false), "studaffair/DtimeRollCall.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward save(ActionMapping mapping, ActionForm aform,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		DynaActionForm form = (DynaActionForm)aform;
		
		String classInCharge = credential.getClassInChargeSqlFilterSAF();
		Map StudTimeoffInit = (Map)session.getAttribute("StudTimeoffInitB");

		
		List<DtimeStudaffair> selDS = getRollCallList(request);
		ActionMessages messages = new ActionMessages();

		try {
			sm.txModifyRollCall(selDS, credential.getMember().getIdno());

			if (messages.isEmpty()) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.CreateSuccessful"));
			}
			saveMessages(request, messages);

			session.removeAttribute("RollCallCourses");
			session.removeAttribute("DtimeRollCallInit");
			session.removeAttribute("TermRollCall");
			return mapping.findForward("Main");
		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Exception.generic", e.getMessage()));
			log.error(e);
			saveErrors(request, errors);
			session.removeAttribute("RollCallCourses");
			session.removeAttribute("TermRollCall");
			return mapping.findForward("Main");
		}
	}
	
	
	private List getRollCallList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = "";
		StudAffairDAO dao =(StudAffairDAO)getBean("studAffairDAO");
		List<DtimeStudaffair> DSs = new ArrayList<DtimeStudaffair>();
		
		oids = Toolket.getSelectedIndexFromCookie(request, "TermRollCall");
		DSs = (List<DtimeStudaffair>)session.getAttribute("TermRollCall");
		
		for (DtimeStudaffair ds:DSs ) {
			if (Toolket.isValueInCookie(ds.getOid().toString(), oids)) {
				ds.setIsRollCall((short)1);
			}else{
				ds.setIsRollCall((short)0);
			}
		}
		return DSs;
	}
	
}
