package tw.edu.chit.struts.action.deptassist;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.AbilityExamine;
import tw.edu.chit.model.StdAbility;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class AbilityExamineEnrollAction extends BaseLookupDispatchAction {

	private static String RESULT_NAME = "result";
	private static String RESULT_COOKIE_NAME = "resultData";
	private static String ABILITY_EXAMINE_RESULT_NAME = "stdAbilityResult";
	private static String ABILITY_EXAMINE_COOKIE_NAME = "stdAbility";
	private static String STUDENT_INFO = "stdInfo";
	private static String SPECIFIED_STD_ABILITY = "modifyStdAbility";
	private static String ABILITY_EXAMINE_YEAR = "year";

	/**
	 * 第一次進入學生技能檢定資料維護
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

		Toolket.resetCheckboxCookie(response, RESULT_COOKIE_NAME);
		CourseManager cm = (CourseManager) getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		String year = cm.getNowBy(IConstants.PARAMETER_SCHOOL_YEAR);
		HttpSession session = request.getSession(false);
		((DynaActionForm) form).initialize(mapping);
		session.setAttribute(ABILITY_EXAMINE_YEAR, year);
		session.removeAttribute(RESULT_NAME);
		session.removeAttribute(ABILITY_EXAMINE_RESULT_NAME);
		session.removeAttribute(SPECIFIED_STD_ABILITY);
		resetForm((DynaActionForm) form, year);
		setContentPage(request.getSession(false),
				"assistant/AbilityExamineEnroll.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 查詢學生技能檢定資料
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Toolket.resetCheckboxCookie(response, RESULT_COOKIE_NAME);
		HttpSession session = request.getSession(false);
		session.removeAttribute(ABILITY_EXAMINE_RESULT_NAME);
		session.removeAttribute(SPECIFIED_STD_ABILITY);
		DynaActionForm aForm = (DynaActionForm) form;

		// exSearch=1代表進階查詢
		ActionMessages messages = new ActionMessages();
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		String exSearch = aForm.getString("exSearch");
		List<DynaBean> ret = new LinkedList<DynaBean>();
		
		
		if (StringUtils.isBlank(exSearch)) 
		{
            String studentNo = aForm.getString("studentNoC");
			Student student = mm.findStudentByNo(studentNo);
			if (student != null) 
			{
				List<StdAbility> sa = mm.findStudentAbilityByStudentNoAndAbilityNo(studentNo,""); // 空白代表全部
				
				DynaBean bean = processStudentToBean(student, sa.size());
			    ret.add(bean);
				/*
				String exst = (String)student.getExtraStatus();  //取得學生(輔系/雙主修)資料
				if(!exst.equals("") && exst!=null)    //當學生有輔系/雙主修資料時,該學生之登錄件數+1
				{
					DynaBean bean = processStudentToBean(student, sa.size()+1);
				    ret.add(bean);
				}
				else
				{
					DynaBean bean = processStudentToBean(student, sa.size());
				    ret.add(bean);
				}
				*/
			    
			    
			} else
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "查無符合資料"));
		} 
		else 
		{
			Toolket.resetCheckboxCookie(response, RESULT_COOKIE_NAME);
			String abilityNo = aForm.getString("abilityNo");
			String Count = aForm.getString("count");
			//System.out.println("1::"+Count + exSearch);
			aForm.set("studentNoC", ""); // 避免查詢單一學生資料
			aForm.set("nameC", "");
			List<Student> students = mm.findStudentsInChargeByStudentInfoFormC(
					aForm.getMap(), getUserCredential(session)
							.getClassInChargeSqlFilterC());
			if (!students.isEmpty()) 
			{				
				for (Student student : students) 
				{					
					//if (StringUtils.isNotBlank(abilityNo)) 
					if (Count.equals("Y"))
					{
						int count = mm.findStudentAbilityByStudentNoAndAbilityNo(student.getStudentNo(), abilityNo).size();
						if (count > 0) // 即時計算技能檢定件數
						{ 
							DynaBean bean = processStudentToBean(student, count);
							ret.add(bean);
						}
					} 
					else if(Count.equals("N"))
					{
						int count = mm.findStudentAbilityByStudentNoAndAbilityNo(student.getStudentNo(), abilityNo).size();
						if (count == 0) // 即時計算技能檢定件數
						{ 
							DynaBean bean = processStudentToBean(student, count);
							ret.add(bean);
						}
					}
					else 
					{					
						int count = mm.findStudentAbilityByStudentNoAndAbilityNo(student.getStudentNo(), abilityNo).size();
						DynaBean bean = processStudentToBean(student, count);
						ret.add(bean);
						/*
						String exst = (String)student.getExtraStatus();    //取得學生(輔系/雙主修)資料
						if(!exst.equals("") && exst!=null)    //當學生有輔系/雙主修資料時,該學生之登錄件數+1
						{
							DynaBean bean = processStudentToBean(student, count+1);
						    ret.add(bean);
						}
						else
						{
							DynaBean bean = processStudentToBean(student, count);
							ret.add(bean);
						}
						*/
																
					}
				}
			} else
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.messageN1", "查無符合資料"));

		}

		if (!messages.isEmpty() || ret.isEmpty()) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.messageN1", "查無符合資料"));
			saveMessages(request, messages);
			return mapping.findForward("Main");
		}

		
		session.setAttribute(RESULT_NAME, ret);
		setContentPage(session, "assistant/AbilityExamineEnroll.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 第一次進入學生技能檢定資料維護
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Toolket.resetCheckboxCookie(response, ABILITY_EXAMINE_COOKIE_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		DynaBean bean = getSelectedDataByIndex(request);
		List<StdAbility> sas = mm.findStudentAbilityByStudentNoAndAbilityNo(
				bean.get("no").toString(), ((DynaActionForm) form).getString("abilityNo"));
		HttpSession session = request.getSession(false);
		//System.out.println(sas.get(0));
					
		DynaActionForm aForm = (DynaActionForm) form;
		String studentNo = (String) bean.get("no");
		Student student = mm.findStudentByNo(studentNo);		
		/*
		if(!student.getExtraStatus().equals("") && student.getExtraStatus()!=null)    //當該學生有輔系/雙主修資料時,將資料加入List
		{
			StdAbility sa = new StdAbility();
			sa.setStudentNo(studentNo);
			sa.setAbilityNo(5);
			sa.setDescription(student.getExtraStatus());
			sa.setLevelDesc(student.getExtraDept());
			sa.setDeptDesc("");
			sa.setLastModified(null);
			sa.setModifier("");
			sas.add(sa);
			aForm.set("studentNoC", studentNo);
		}
		*/

		if (!sas.isEmpty()) 
		{
		  session.setAttribute(STUDENT_INFO, bean);		  
		  session.setAttribute(ABILITY_EXAMINE_RESULT_NAME, processStdAbilityList(sas));		  
		} 
		else 
		{
		  ActionMessages messages = new ActionMessages();
		  messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "查無任何相關技能檢定資料"));
		  saveMessages(request, messages);
		  session.setAttribute(STUDENT_INFO, bean);
		}
		
		setContentPage(session, "assistant/AbilityExamineEnroll.jsp");
		return mapping.findForward("Main");
	}


	
	/**
	 * 新增學生技能檢定資料
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages messages = new ActionMessages();
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		String idno = getUserCredential(session).getMember().getIdno();
		String classInCharge = getUserCredential(session).getClassInChargeSqlFilterC();
		DynaActionForm aForm = (DynaActionForm) form;
		try {
			String[] stdNoA = aForm.getStrings("stdNoA");
			String[] abilityNoA = aForm.getStrings("abilityNoA");
			String[] descA = aForm.getStrings("descA");
			String[] levelA = aForm.getStrings("levelA");
			String[] deptA = aForm.getStrings("deptA");
			StringBuffer buffer = new StringBuffer();
			List<StdAbility> sas = new LinkedList<StdAbility>();
			for (int i = 0; i < stdNoA.length; i++) {
				if (StringUtils.isNotBlank(stdNoA[i].trim())
						&& !mm.findStudentsInChargeByStudentInfoForm(
								preparedForSearch(stdNoA[i].trim()),
								classInCharge).isEmpty()
						&& StringUtils.isNotBlank(abilityNoA[i].trim())) {
					StdAbility sa = new StdAbility();
					sa.setStudentNo(stdNoA[i].trim());
					sa.setAbilityNo(Integer.parseInt(abilityNoA[i]));
					sa.setDescription(descA[i].trim());
					sa.setLevelDesc(levelA[i].trim());
					sa.setDeptDesc(deptA[i].trim());
					sa.setLastModified(new Date());
					sa.setModifier(idno);
					sas.add(sa);
					aForm.set("studentNoC", stdNoA[i]);
				} else {
					if (StringUtils.isNotBlank(stdNoA[i]))
						buffer.append(stdNoA[i] + ", ");
					continue;
				}
			}
			if (!sas.isEmpty())
				mm.saveStudentAbility(sas);

			if (StringUtils.isNotBlank(buffer.toString())) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.messageN1", "查無學生基本資料或項目別未輸入 : "
								+ StringUtils.substringBeforeLast(buffer
										.toString(), ",")));
				saveMessages(request, messages);
				return mapping.findForward("Main");
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.messageN1", "新增完成"));
				saveMessages(request, messages);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		session.removeAttribute(RESULT_NAME);
		setContentPage(session, "assistant/AbilityExamineEnroll.jsp");
		return mapping.findForward("Main");		
	}

	/**
	 * 刪除學生技能檢定資料
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		List<StdAbility> sas = getSelectedStdAbilityByIndex(request);
		ActionMessages messages = new ActionMessages();
		
		
		for(StdAbility sa : sas)
		{
			if(sa.getAbilityNo()!= 1)               //==3 || sa.getAbilityNo()==4 || sa.getAbilityNo()==5)    //當勾選刪除項目編號有3 or 4 or 5時,系助理沒有權限刪除
			{
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Course.errorN1", "您無權限刪除' "+sa.getAbilityName()+" '項目"));
				saveErrors(request, messages);
				return mapping.findForward("Main");
			}
		}
		
		
		try {
			mm.deleteStdAbility(sas);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.messageN1", "刪除完成"));
			saveMessages(request, messages);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", e.getMessage()));
			saveErrors(request, messages);
			return mapping.findForward("Main");
		}
		
		return search(mapping, form, request, response);
	}

	/**
	 * 選擇修改學生技能檢定資料
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        HttpSession session = request.getSession(false);
		StdAbility sa = getSelectedStdAbilityByIndex(request).get(0);
		
		
		ActionMessages messages = new ActionMessages();
		if(sa.getAbilityNo()!= 1){           //==3 || sa.getAbilityNo()==4 || sa.getAbilityNo()==5){    //當勾選修改項目編號有3 or 4 or 5時,系助理沒有權限修改
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Course.errorN1", "您無權限修改' "+sa.getAbilityName()+" '項目"));
			saveErrors(request, messages);
			return mapping.findForward("Main");
		}
		
		
		session.setAttribute(SPECIFIED_STD_ABILITY, sa);
		session.removeAttribute(ABILITY_EXAMINE_RESULT_NAME);
		setContentPage(session, "assistant/AbilityExamineEnroll.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 確定修改學生技能檢定資料
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		String idno = getUserCredential(session).getMember().getIdno();
		String abilityNo = aForm.getString("abilityNoM");
		String desc = aForm.getString("descM");
		String level = aForm.getString("levelM");
		String dept = aForm.getString("deptM");
		StdAbility sa = (StdAbility) session
				.getAttribute(SPECIFIED_STD_ABILITY);
		sa.setAbilityNo(Integer.parseInt(abilityNo));
		sa.setDescription(desc);
		sa.setLevelDesc(level);
		sa.setDeptDesc(dept);
		sa.setLastModified(new Date());
		sa.setModifier(idno);
		List<StdAbility> sas = new LinkedList<StdAbility>();
		sas.add(sa);
		try {
			mm.saveStudentAbility(sas);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.messageN1", "更改完成"));
			saveMessages(request, messages);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.messageN1", e.getMessage()));
			saveErrors(request, messages);
			return mapping.findForward("Main");
		}
		
		session.removeAttribute(SPECIFIED_STD_ABILITY);
		return view(mapping, form, request, response);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Search", "search");
		map.put("makeSure", "create");
		map.put("Delete", "delete");
		map.put("Modify", "modify");
		map.put("View", "view");
		map.put("Update", "update");
		return map;
	}

	@SuppressWarnings("unchecked")
	private DynaBean getSelectedDataByIndex(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String index = Toolket.getSelectedIndexFromCookie(request,
				RESULT_COOKIE_NAME);
		List<DynaBean> result = (List<DynaBean>) session
				.getAttribute(RESULT_NAME);
		DynaBean ret = null;
		for (DynaBean bean : result) {
			if (Toolket.isValueInCookie(bean.get("no").toString(), index)) {
				ret = bean;
				break;
			}
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	private List<StdAbility> getSelectedStdAbilityByIndex(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String index = Toolket.getSelectedIndexFromCookie(request,ABILITY_EXAMINE_COOKIE_NAME);
		List<StdAbility> data = (List<StdAbility>) session.getAttribute(ABILITY_EXAMINE_RESULT_NAME);
		List<StdAbility> sas = new LinkedList<StdAbility>();
				
        /*
		//將該學生的輔系/雙主修資料從List移除
		StdAbility s;
		for(int i=0; i<data.size(); i++)
		{
			s=(StdAbility)data.get(i);
			if(s.getAbilityNo()==5)
			{
				data.remove(i);
				i=i-1;
			}
		}
		*/		
		
		for (StdAbility sa : data) {
			if (Toolket.isValueInCookie(sa.getOid().toString(), index)) {
				sas.add(sa);
			}
		}
		return sas;		
	}
	
	
	/**
	 * 取得技能檢定項目別
	 * @param aes
	 * @return
	 * 學成輔系雙主修(a.getNo()==5)為註冊組執行的項目,系助理不須在下發拉式選項選到
	 * 英語檢定,英語學習護照(a.getNo()==3,4)為語言中心所需填寫的項目,系助理不須在下發拉式選項選到
	 */
	private Map<String, String[]> processAbilityExamine(List<AbilityExamine> aes) {
		
		
		//將非系助理執行的項目從下拉式選項中移除
		AbilityExamine a;
		for(int i=0; i<aes.size(); i++)
		{
			a=(AbilityExamine)aes.get(i);
			if(a.getNo() != 1)            //==3 || a.getNo()==5 || a.getNo()==4)
			{
				aes.remove(i);
				i=i-1;
			}
		}

		
		String[] nos = new String[aes.size() + 1];
		String[] names = new String[aes.size() + 1];
		nos[0] = "";
		names[0] = "";
		int index = 1;
		for (AbilityExamine ae : aes) {
			nos[index] = ae.getNo().toString();
			names[index++] = ae.getName();
		}
		Map<String, String[]> ret = new HashMap<String, String[]>();
		ret.put("AbilityNos", nos);
		ret.put("AbilityNames", names);
		return ret;
	}

	private DynaBean processStudentToBean(Student student, int count) {
		DynaBean bean = new LazyDynaBean();
		bean.set("name", student.getStudentName());
		bean.set("no", student.getStudentNo());
		bean.set("sex", Toolket.getSex(student.getSex()));
		bean.set("class", Toolket.getClassFullName(student.getDepartClass()));
		bean.set("counts", String.valueOf(count));
		
		return bean;
		
	}

	private void resetForm(DynaActionForm form, String year) {
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		Map<String, String[]> map = processAbilityExamine(mm
				.findAbilityExamineBySchoolYear(year));
		form.set("abilityNos", map.get("AbilityNos"));
		form.set("abilityNames", map.get("AbilityNames"));
		form.set("exSearch", "");
		// form.set("studentNoC", "");
		form.set("nameC", "");
		
		
	}

	private List<StdAbility> processStdAbilityList(List<StdAbility> sas) {
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);		
		for (StdAbility sa : sas) {			
			sa.setAbilityName(mm.findAbilityExamineByNo(sa.getAbilityNo()).getName());
			
		}
		return sas;
	}
	

	private Map<String, String> preparedForSearch(String studentNo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("studentNo2", studentNo);
		map.put("name2", "");
		map.put("idNo2", "");
		map.put("status2", "");
		map.put("campusInCharge2", "");
		map.put("schoolInCharge2", "");
		map.put("deptInCharge2", "");
		map.put("classInCharge2", "");
		return map;
	}

}
