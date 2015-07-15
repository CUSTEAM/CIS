package tw.edu.chit.struts.action.score;

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

public class ScoreInputAction extends BaseLookupDispatchAction {

	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("scrInput","input");
		map.put("scrCheck","check");
		map.put("OK","save");
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
			
			String campus = aForm.getString("campusInCharge2").trim();
			String school = aForm.getString("schoolInCharge2").trim();
			String dept = aForm.getString("deptInCharge2").trim();
			String myclass = aForm.getString("classInCharge2").trim();
			String cscode = aForm.getString("cscode").trim();
			String dtimeoid = aForm.getString("dtimeoid").trim();
			String scoretype = aForm.getString("scoretype");
			String yn = aForm.getString("yn");
			
			session.removeAttribute("ScoreInputInfo");
			if (!(campus.equals("") || school.equals("") || dept.equals("") || myclass.equals("")))
			{
				ScoreManager sm = (ScoreManager)getBean("scoreManager");
				String departClass = myclass;
				//log.debug("=======> unspe->departClass=" + departClass);
				List<Dtime> dtimelist = sm.findCscodeByClass(departClass);
				
				List cscodelist = new ArrayList();
				Dtime dtime;
				for (Iterator<Dtime> dtimeIter = dtimelist.iterator(); dtimeIter.hasNext();) {
					Map cmap = new HashMap();
					dtime = dtimeIter.next();
					cmap.put("cscode", dtime.getCscode());
					cmap.put("chiName", dtime.getChiName2());
					cmap.put("dtimeoid", dtime.getOid());
					cscodelist.add(cmap);
				}
				session.setAttribute("ScoreInputInfo", cscodelist);
			}
			Map initValue = new HashMap();
			initValue.put("mode", "");
			initValue.put("campus", campus);
			initValue.put("school", school);
			initValue.put("dept", dept);
			initValue.put("clazz", myclass);
			initValue.put("cscode", cscode);
			initValue.put("dtimeoid", dtimeoid);
			initValue.put("yn", yn);
			initValue.put("scoretype", scoretype);
			session.setAttribute("ScoreInputInit", initValue);
			
			session.removeAttribute("ScoreInputFormMap");
			session.removeAttribute("ScoreInEdit");
			
			setContentPage(session, "score/ScoreInput.jsp");
			return mapping.findForward("Main");
	}

	
	public ActionForward input(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm)form;
		ResourceBundle bundle = Toolket.getBundle(request);
		ActionMessages messages = new ActionMessages();

		String campus = aForm.getString("campusInCharge2").trim();
		String school = aForm.getString("schoolInCharge2").trim();
		String dept = aForm.getString("deptInCharge2").trim();
		String myclass = aForm.getString("classInCharge2").trim();
		String cscode = aForm.getString("cscode").trim();
		String dtimeoid = aForm.getString("dtimeoid").trim();
		String scoretype = aForm.getString("scoretype");
		String yn = aForm.getString("yn");
		String classname = "";
		String cscodeName = "";

		Map initValue = new HashMap();
		//initValue.putAll((Map)session.getAttribute("ScoreInputInit"));
		initValue.put("mode", "Input");
		initValue.put("campus", campus);
		initValue.put("school", school);
		initValue.put("dept", dept);
		initValue.put("clazz", myclass);
		initValue.put("cscode", cscode);
		initValue.put("dtimeoid", dtimeoid);
		initValue.put("yn", yn);
		initValue.put("scoretype", scoretype);

		
		if (!(campus.equals("") || school.equals("") || dept.equals("") || myclass.equals("") || dtimeoid.equals("") || scoretype.equals("")))
		{
			ScoreManager sm = (ScoreManager)getBean("scoreManager");
			String departClass = myclass;
			classname = sm.findClassName(departClass);
			cscodeName = sm.findCourseName(cscode);
			
			log.debug("=======> input->departClass=" + departClass);
			//List<Seld> seldlist = sm.findSeldScoreByInputForm(departClass, cscode);
			List<Seld> seldlist = sm.findSeldScoreByInputForm(dtimeoid);
			ActionMessages msgs = sm.patchSeldRegs(departClass, cscode);
			if(! msgs.isEmpty()){
				saveErrors(request, messages);
			}
			

			log.debug("=======> input->SelInEdit=" + seldlist.size());
			if(seldlist.size() > 0){
				cscodeName = sm.findCourseName(seldlist.get(0).getCscode());
				initValue.put("Dtime_oid", seldlist.get(1).getDtimeOid());
				initValue.put("depClassName", classname);
				initValue.put("cscodeName", cscodeName);

				session.setAttribute("ScoreInEdit", seldlist);
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.NoSeldScoreFound"));
				saveMessages(request, messages);
				
			}
			// session.removeAttribute("ScoreInputInfo");
			
		} else {
			ActionMessages errors = new ActionMessages();

			if (campus.equals("")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ScoreInput.MustSelect", bundle.getString("campusInCharge")));
			}
			if (school.equals("")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ScoreInput.MustSelect", bundle.getString("schoolInCharge")));
			}
			if (dept.equals("")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ScoreInput.MustSelect", bundle.getString("deptInCharge")));
			}
			if (myclass.equals("")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ScoreInput.MustSelect", bundle.getString("classInCharge")));
			}
			if (cscode.equals("")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ScoreInput.MustSelect", bundle.getString("course")));
			}
			if (scoretype.equals("")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ScoreInput.MustSelect", bundle.getString("scoretype")));
			}
			saveErrors(request, errors);
			session.removeAttribute("ScoreInEdit");
			
		}
		
		session.removeAttribute("ScoreInputFormMap");
		session.setAttribute("ScoreInputInit", initValue);
		
		// log.debug("=======> input->yn=" + yn);
		setContentPage(session, "score/ScoreInput.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward check(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		return modeop(mapping, form, request, response, "Check");
	}

	public ActionForward save(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		ActionMessages messages = validateInputForUpdate(aForm, Toolket.getBundle(request));

		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			// request.removeAttribute("ScoreInputInit");
			
			session.setAttribute("ScoreInputFormMap", aForm.getMap());
			//session.setAttribute("ScoreInput", aForm.getStrings("scrinput"));
			return mapping.findForward("Main");
		} else {
			ActionMessages errors = new ActionMessages();
			try {
				List<Seld> scores = (List<Seld>)session.getAttribute("ScoreInEdit");
				ScoreManager sm = (ScoreManager)getBean("scoreManager");
				
				errors = sm.updateScoreInput(aForm.getMap(), scores);
				if (!errors.isEmpty()) {
					
					saveErrors(request, errors);
					// request.removeAttribute("ScoreInputInit");
					
					session.setAttribute("ScoreInputFormMap", aForm.getMap());
					//session.setAttribute("ScoreInput", aForm.getStrings("scrinput"));
					return mapping.findForward("Main");
				}
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.CreateSuccessful"));
				saveMessages(request, messages);
				aForm.initialize(mapping);
				
				String scoretype = ((Map)session.getAttribute("ScoreInputInit")).get("scoretype").toString();
				Map initMap = new HashMap();
				initMap.put("scoretype", scoretype);
				session.setAttribute("ScoreInputInit", initMap);
				
				session.removeAttribute("ScoreInEdit");
				session.removeAttribute("ScoreInputFormMap");
				session.removeAttribute("ScoreInputInfo");
				return mapping.findForward("Main");	
			} catch(Exception e) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				session.setAttribute("ScoreInputFormMap", aForm.getMap());
				return mapping.findForward("Main");
			}
		}
	}

	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		String scoretype = ((Map)session.getAttribute("ScoreInputInit")).get("scoretype").toString();
		Map initMap = new HashMap();
		initMap.put("scoretype", scoretype);
		session.setAttribute("ScoreInputInit", initMap);
		
		// session.removeAttribute("ScoreInputInit");
		session.removeAttribute("ScoreInEdit");
		session.removeAttribute("ScoreInputFormMap");
		session.removeAttribute("ScoreInputInfo");
		setContentPage(request.getSession(false), "score/ScoreInput.jsp");
		return mapping.findForward("Main");
	}

	
	
	//Private Method Here ============================>>
	private ActionForward modeop(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, String mode) {
		
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm)form;
		ResourceBundle bundle = Toolket.getBundle(request);
		ActionMessages messages = new ActionMessages();

		String campus = aForm.getString("campusInCharge2").trim();
		String school = aForm.getString("schoolInCharge2").trim();
		String dept = aForm.getString("deptInCharge2").trim();
		String myclass = aForm.getString("classInCharge2").trim();
		String cscode = aForm.getString("cscode").trim();
		String dtimeoid = aForm.getString("dtimeoid").trim();
		String scoretype = aForm.getString("scoretype");
		String yn = aForm.getString("yn");
		String classname = "";
		String cscodeName = "";

		Map initValue = new HashMap();
		//initValue.putAll((Map)session.getAttribute("ScoreInputInit"));
		initValue.put("mode", mode);
		initValue.put("campus", campus);
		initValue.put("school", school);
		initValue.put("dept", dept);
		initValue.put("clazz", myclass);
		initValue.put("cscode", cscode);
		initValue.put("dtimeoid", dtimeoid);
		initValue.put("yn", yn);
		initValue.put("scoretype", scoretype);

		
		if (!(campus.equals("") || school.equals("") || dept.equals("") || myclass.equals("") || cscode.equals("") || scoretype.equals("")))
		{
			ScoreManager sm = (ScoreManager)getBean("scoreManager");
			String departClass = myclass;
			classname = sm.findClassName(departClass);
			cscodeName = sm.findCourseName(cscode);
			
			log.debug("=======> input->departClass=" + departClass);
			//List<Seld> seldlist = sm.findSeldScoreByInputForm(departClass, cscode);
			List<Seld> seldlist = sm.findSeldScoreByInputForm(dtimeoid);
			if(seldlist.size() > 0){
				initValue.put("Dtime_oid", seldlist.get(1).getDtimeOid());
				initValue.put("depClassName", classname);
				initValue.put("cscodeName", cscodeName);

				session.setAttribute("ScoreInEdit", seldlist);
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.NoSeldScoreFound"));
				saveMessages(request, messages);
			}
			// session.removeAttribute("ScoreInputInfo");
			
		} else {
			ActionMessages errors = new ActionMessages();

			if (campus.equals("")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ScoreInput.MustSelect", bundle.getString("campusInCharge")));
			}
			if (school.equals("")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ScoreInput.MustSelect", bundle.getString("schoolInCharge")));
			}
			if (dept.equals("")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ScoreInput.MustSelect", bundle.getString("deptInCharge")));
			}
			if (myclass.equals("")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ScoreInput.MustSelect", bundle.getString("classInCharge")));
			}
			if (cscode.equals("")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ScoreInput.MustSelect", bundle.getString("course")));
			}
			if (scoretype.equals("")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ScoreInput.MustSelect", bundle.getString("scoretype")));
			}
			saveErrors(request, errors);
			
		}
		
		session.removeAttribute("ScoreInputFormMap");
		session.setAttribute("ScoreInputInit", initValue);
		
		// log.debug("=======> input->yn=" + yn);
		setContentPage(session, "score/ScoreInput.jsp");
		return mapping.findForward("Main");

	}

	private ActionMessages validateInputForUpdate(DynaActionForm form, ResourceBundle bundle) {
		
		ActionMessages errors = new ActionMessages();
		validateFieldFormat(form, errors, bundle);
		ScoreManager sm = (ScoreManager)getBean("scoreManager");
		// sm.validateScoreHistCreate(form.getMap(), errors, bundle);
		return errors;
	}
	
	private void validateFieldFormat(DynaActionForm form, ActionMessages errors, ResourceBundle bundle) {

		String[] buff = form.getStrings("scrinput");
		String[] stus = form.getStrings("studentNo");
		int[] score = new int[buff.length];
		
		for(int i=0; i<buff.length; i++) {
			if(buff[i].equals("")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", bundle.getString("Score")));
				break;
			} else {
				try {
					score[i] = Math.round(Float.parseFloat(buff[i]));
					if(score[i] > 100) {
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidScoreFormat", stus[i]));							
						break;
					}
				} catch(Exception e) {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidScoreFormat", stus[i]));							
				}
			}
		}
	}
}
