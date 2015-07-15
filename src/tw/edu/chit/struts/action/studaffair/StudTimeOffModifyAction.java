package tw.edu.chit.struts.action.studaffair;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Code5;
import tw.edu.chit.model.Dilg;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Global;
import tw.edu.chit.util.Toolket;

public class StudTimeOffModifyAction extends BaseLookupDispatchAction {
	
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("OK", "save");
		map.put("Cancel", "cancel");
		return map;
	}
		
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("StudTimeOffEdit");
		session.removeAttribute("StudTimeOffInEdit");
		setContentPage(request.getSession(false), "studaffair/StudTimeOff.jsp");
		return mapping.findForward("Main");
	}

	@SuppressWarnings("unchecked")
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		List<Dilg> dilgList = (List<Dilg>)session.getAttribute("StudTimeOffInEdit");
		DynaActionForm dynForm = (DynaActionForm)form;
		
		ActionMessages messages = validateInputForModify(dynForm, dilgList, Toolket
				.getBundle(request));

		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			session.setAttribute("StudTimeOffEdit", dynForm.getMap());
			return mapping.findForward("Main");
		} else {
			try {
				//String studentNo = dynForm.getString("studentNo");
				//ScoreManager scm = (ScoreManager)getBean("scoreManager");
				//Student stu = scm.findStudentByStudentNo(studentNo);
				StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
				sm.modifyStudTimeOffList(dynForm.getMap(), dilgList);
				
				double justScore = 0d;
				String studentNo = ""; 
				Date sddate = new Date();
				ActionMessages errs = new ActionMessages();
				for(Dilg dilg:dilgList){
					studentNo = dilg.getStudentNo();
					sddate = dilg.getDdate();
					
					justScore = sm.calDilgScoreByStudent(studentNo, "0");
					
					//System.out.println(justScore);
					
					//errs = sm.modifyJustDilgScore(studentNo, justScore);
					
					//取代sm.modifyJustDilgScore(studentNo, justScore)該元件執行有誤  Leo20120307
					CourseManager manager = (CourseManager) getBean("courseManager");
					String sqlstudent_no = manager.ezGetString("Select student_no From just Where student_no='" + studentNo + "' ");
					double SeltotalScore = Double.parseDouble(manager.ezGetString("Select total_score From just Where student_no='" + studentNo + "' "));
					double totalScore = SeltotalScore+justScore;
					int myScore= (int)(totalScore+0.5);
					if(sqlstudent_no.equals("")){
						errs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"Message.MessageN1", "找不到[" + studentNo + "]該學生的操行成績!"));
					}else{				
						//System.out.println(myScore);
						manager.executeSql("Update just Set total_score="+myScore+" Where student_no='"+studentNo+"'");
					}                                       
					//=======================================================================
					
					if(errs.isEmpty()){
						errs = sm.modifySeldDilgPeriod(studentNo, sddate);
						if(!errs.isEmpty()){
							messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
									"Message.CourseN1", "更新[" + studentNo + "]扣考節數失敗!"));
							messages.add(errs);
						}
					}else{
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"Message.CourseN1", "更新[" + studentNo + "]操行曠缺加減分失敗!"));
						messages.add(errs);
					}

				}
				
				if(messages.isEmpty()){
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"Message.ModifySuccessful"));
				}
				
				saveMessages(request, messages);
				
				// form.initialize(mapping);
				session.removeAttribute("StudTimeOffEdit");
				session.removeAttribute("StudTimeOffInEdit");
				session.removeAttribute("StudTimeOffList");
				// session.removeAttribute("StudTimeoffInit");
				// request.getParameterMap().clear();
				// request.getParameterMap().remove("name");
				setContentPage(session,"studaffair/StudTimeOff.jsp");
				return mapping.findForward("Main");
			} catch (Exception e) {
				e.printStackTrace();
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				session.setAttribute("StudTimeOffEdit", dynForm.getMap());
				return mapping.findForward("Main");
			}
		}
	}

	private ActionMessages validateInputForModify(DynaActionForm form,
			List<Dilg> dilglist, ResourceBundle bundle) {
		// dilg:old data , form: new fill data,
		// form->studentNo,ddate can't modify
		ActionMessages errors = new ActionMessages();
		validateFieldFormat(form, errors, bundle);
		//if(errors.isEmpty()) {
		//	StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
		//	sm.validateStudTimeOffModify(form.getMap(), dilg, errors, bundle);
		//}
		return errors;
	}

	private void validateFieldFormat(DynaActionForm form,
			ActionMessages errors, ResourceBundle bundle) {
		
		String[] abs0 = form.getStrings("abs0");
		String[] abs1 = form.getStrings("abs1");
		String[] abs2 = form.getStrings("abs2");
		String[] abs3 = form.getStrings("abs3");
		String[] abs4 = form.getStrings("abs4");
		String[] abs5 = form.getStrings("abs5");
		String[] abs6 = form.getStrings("abs6");
		String[] abs7 = form.getStrings("abs7");
		String[] abs8 = form.getStrings("abs8");
		String[] abs9 = form.getStrings("abs9");
		String[] abs10 = form.getStrings("abs10");
		String[] abs11 = form.getStrings("abs11");
		String[] abs12 = form.getStrings("abs12");
		String[] abs13 = form.getStrings("abs13");
		String[] abs14 = form.getStrings("abs14");
		String[] abs15 = form.getStrings("abs15");
		String[][] abs = {abs0,abs1,abs2,abs3,abs4,abs5,abs6,abs7,
				abs8,abs9,abs10,abs11,abs12,abs13,abs14,abs15};
		String str = "";
		
		int i, num, begin, end, count;
		
		List<Code5> tfTypeList = Global.TimeOffList;
		int tflen = tfTypeList.size();
		
		for(i=0; i<16; i++) {
			for(int j=0; j<abs[i].length; j++) {
				str = abs[i][j];
				if(!StringUtils.isNumeric(str) && !str.trim().equals("")) {
					errors.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Message.InputNumericOnly","1-"+(tflen-1)));
				} else if(StringUtils.isNumeric(str) && !str.trim().equals("")){
					num = Integer.parseInt(str);
					if(num<0 || num > tflen-1) {
						errors.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Message.NumberOutOfRange","1-"+(tflen-1)));
					}
				}
			}
		}
		
		return;
	}

}
