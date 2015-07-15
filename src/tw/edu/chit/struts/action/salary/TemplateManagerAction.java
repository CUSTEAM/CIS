package tw.edu.chit.struts.action.salary;

import java.util.ArrayList;
import java.util.HashMap;
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

import tw.edu.chit.model.SalarySset;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class TemplateManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(false);
		Toolket.resetCheckboxCookie(response, "tmps");
		clear(request);
		
		
		setContentPage(request.getSession(false), "salary/TemplateManager.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 查詢列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward goContinue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Toolket.resetCheckboxCookie(response, "tmps");
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm sForm = (DynaActionForm) form;
		
		String idno=sForm.getString("idno");		
		
		List list=manager.ezGetBy("SELECT ce1.name as category, e.cname, e.sname, e.sex, ce.name, ss.* FROM Salary_sset ss, " +
				"(empl e LEFT OUTER JOIN CodeEmpl ce ON e.unit=ce.idno AND (ce.category='Unit' || ce.category='UnitTeach'))LEFT OUTER JOIN CodeEmpl ce1 ON e.category=ce1.idno AND ce1.category='EmpCategory'" +
				"WHERE ss.idno LIKE '"+idno+"%' AND e.idno=ss.idno ORDER BY ss.sno");		
		List list1=manager.ezGetBy("SELECT ce1.name as category, e.cname, e.sname, e.sex, ce.name, ss.* FROM Salary_sset ss, " +
				"(dempl e LEFT OUTER JOIN CodeEmpl ce ON e.unit=ce.idno AND (ce.category='Unit' || ce.category='UnitTeach'))LEFT OUTER JOIN CodeEmpl ce1 ON e.category=ce1.idno AND ce1.category='EmpCategory'" +
				"WHERE ss.idno LIKE '"+idno+"%' AND e.idno=ss.idno ORDER BY ss.sno");
		
		
		
		
		
		
		list.addAll(list1);		
		
		session.setAttribute("templates", list);
		//若為1人進入編輯模式
		if(list.size()==1){
			
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","準備進行修改"));
			
			saveMessages(request, msg);
			
			session.setAttribute("mode", "edit");
			session.setAttribute("aTemplete", list.get(0));//取唯1人進入編輯模式
		
		}else{//若為多人進入列表模式
			
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","查詢完成"));
			if(list1.size()>0){
				list.addAll(list1);
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",", 同時發現"+list1.size()+"個已離職卻未清除的教職員"));
			}
			saveMessages(request, msg);
			session.setAttribute("mode", "list");//入列表模式
		}
		return mapping.findForward("Main");
	}
	
	/**
	 * 刪除範本
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(false);
		
		clear(request);
		
		
		setContentPage(request.getSession(false), "salary/TemplateManager.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 擇1修改
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		
		List list=getCheckBox(request);
		if(list.size()!=1){//勾選失敗的情形
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","請檢查勾選欄位"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		session.setAttribute("index", ((Map)list.get(0)).get("index"));
		session.setAttribute("aTemplete", getSset(((Map)list.get(0)).get("idno").toString()));//取唯1人進入編輯模式
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",((Map)list.get(0)).get("cname")+" 的薪資設定"));
		saveMessages(request, msg);		
		session.setAttribute("mode", "edit");
		return mapping.findForward("Main");
	}
	
	/**
	 * 取消
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		clear(request);		
		
		return unspecified(mapping, form, request, response);
	}
	
	/**
	 * 更新
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		DynaActionForm sForm = (DynaActionForm) form;
		
		SalarySset set=(SalarySset) manager.hqlGetBy("FROM SalarySset WHERE idno='"+sForm.get("idno")+"'").get(0);
		/*
		set.setBorrow((Integer)sForm.get("borrow"));
		set.setClassTeacherPay((Integer)sForm.get("class_teacher_pay"));
		
		set.setDedamt((Integer)sForm.get("dedamt"));
		set.setDifference((Integer)sForm.get("difference"));		
		set.setFamilyNo((Integer)sForm.get("family_no"));
		
		set.setHourPay((Integer)sForm.get("hour_pay"));
		set.setHspecial((Integer)sForm.get("hspecial"));
		set.setInslast((Integer)sForm.get("inslast"));
		set.setMonthlyPay((Integer)sForm.get("monthly_pay"));
		set.setNightOverhourPay((Integer)sForm.get("night_overhour_pay"));
		
		set.setNightTransport((Integer)sForm.get("night_transport"));
		set.setNotax((Integer)sForm.get("notax"));
		set.setOthersIn1((Integer)sForm.get("others_in_1"));
		set.setOthersIn2((Integer)sForm.get("others_in_2"));
		set.setOthersOut1((Integer)sForm.get("others_out_1"));
		
		set.setOthersOut2((Integer)sForm.get("others_out_2"));
		set.setOverhourPay((Integer)sForm.get("overhour_pay"));
		set.setPayamt((Integer)sForm.get("payamt"));
		set.setPublicInsure((Integer)sForm.get("public_insure"));
		set.setRealPay((Integer)sForm.get("real_pay"));
		
		set.setSno((Integer)sForm.get("sno"));
		set.setSpecStudy((Integer)sForm.get("spec_study"));
		set.setStudy((Integer)sForm.get("study"));
		set.setSubstituteCourse((Integer)sForm.get("substitute_course"));
		set.setSupervisorTest((Integer)sForm.get("supervisor_test"));
		
		set.setTaxPay((Integer)sForm.get("tax_pay"));
		set.setTeachOver((Integer)sForm.get("teach_over"));
		set.setTechnical((Integer)sForm.get("technical"));
		set.setTransportation((Integer)sForm.get("transportation"));		
		set.setSpouseInsure((Integer)sForm.get("spouse_insure"));
		*/
		int g=getTax(sForm.getMap());
		int y=set.getTaxPay();		
		
		if(g!=y){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "驗算時出現問題, 若這個問題持續出現, 請洽電算中心")); 
			saveErrors(request, error);			
		}else{			
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"Course.messageN1", "稅額是: "+getTax(sForm.getMap())+", 驗算正確!"));
			saveMessages(request, msg);			
		}
		
		manager.updateObject(set);
		session.setAttribute("aTemplete", getSset(sForm.getString("idno")));//取唯1人進入編輯模式
		return mapping.findForward("Main");
	}
	
	/**
	 * 下一筆
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward next(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		
		List list=(List)session.getAttribute("templates");
		Integer i=Integer.parseInt(session.getAttribute("index").toString());
		i=i+1;
		
		if(i>=list.size()){
			i=list.size();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","後面沒了"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		session.setAttribute("aTemplete", getSset(((Map)list.get(i)).get("idno").toString()));
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",((Map)list.get(i)).get("cname")+" 的薪資設定"));
		
		session.setAttribute("index", i);
		saveMessages(request, msg);		
		session.setAttribute("mode", "edit");
		return mapping.findForward("Main");
	}
	
	/**
	 * 上一筆
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward prev(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		
		List list=(List)session.getAttribute("templates");
		Integer i=Integer.parseInt(session.getAttribute("index").toString());		
		i=i-1;
		if(i<0){
			i=0;
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","前面沒了"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		session.setAttribute("aTemplete", getSset(((Map)list.get(i)).get("idno").toString()));
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",((Map)list.get(i)).get("cname")+" 的薪資設定"));
		session.setAttribute("index", i);//目前順序
		saveMessages(request, msg);		
		session.setAttribute("mode", "edit");
		return mapping.findForward("Main");
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Continue", "goContinue");
		map.put("Cancel", "cancel");
		map.put("Modify", "modify");
		map.put("Update", "update");
		map.put("Prev", "prev");
		map.put("Next", "next");
		
		map.put("Delete", "delete");
		return map;
	}
	
	/**
	 * 取得勾選盒
	 * @param request
	 * @return
	 */
	private List getCheckBox(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		String tmps = Toolket.getSelectedIndexFromCookie(request, "tmps");
		
		List templates = (List) session.getAttribute("templates");
		Map map;
		//Map tmpEmpl;
		List list=new ArrayList();
		String idno;
		for(int i=0; i<templates.size(); i++){
			idno=((Map)templates.get(i)).get("idno").toString();
			if (Toolket.isValueInCookie(idno, tmps)){				
				((Map)templates.get(i)).put("index", i);//順序
				list.add(templates.get(i));
			}
		}
		return list;
	}
	
	/**
	 * 全部清除
	 * @param request
	 */
	private void clear(HttpServletRequest request){
		
		HttpSession session = request.getSession(false);
		session.removeAttribute("templates");
		session.removeAttribute("mode");
		session.removeAttribute("aTemplete");
		session.removeAttribute("index");
	}
	
	/**
	 * 取一筆
	 * @param idno
	 * @return
	 */
	private Map getSset(String idno){
		CourseManager manager = (CourseManager) getBean("courseManager");
		Map map=manager.ezGetMap("SELECT ce1.name as category, e.cname, e.sname, e.sex, ce.name, ss.* FROM Salary_sset ss, " +
				"(empl e LEFT OUTER JOIN CodeEmpl ce ON e.unit=ce.idno AND (ce.category='Unit' || ce.category='UnitTeach'))LEFT OUTER JOIN CodeEmpl ce1 ON e.category=ce1.idno AND ce1.category='EmpCategory'" +
				"WHERE ss.idno = '"+idno+"' AND e.idno=ss.idno");
		if(map==null){
			map=manager.ezGetMap("SELECT ce1.name as category, e.cname, e.sname, e.sex, ce.name, ss.* FROM Salary_sset ss, " +
					"(dempl e LEFT OUTER JOIN CodeEmpl ce ON e.unit=ce.idno AND (ce.category='Unit' || ce.category='UnitTeach'))LEFT OUTER JOIN CodeEmpl ce1 ON e.category=ce1.idno AND ce1.category='EmpCategory'" +
					"WHERE ss.idno = '"+idno+"' AND e.idno=ss.idno");
		}		
		return map;
	}
	
	/**
	 * 應加
	 * @param map
	 * @return
	 */
	private Integer plus(Map map){
		/*
		int idno=(Integer)map.get("idno");
		int sno=(Integer)map.get("sno");
		int dedamt=(Integer)map.get("dedamt");
		int real_pay=(Integer)map.get("real_pay");		
		int family_no=(Integer)map.get("family_no");
		int Oid=(Integer)map.get("Oid");
		
		int monthly_pay=(Integer)map.get("monthly_pay");
		int inslast=(Integer)map.get("inslast");
		int study=(Integer)map.get("study");
		int technical=(Integer)map.get("technical");
		int hspecial=(Integer)map.get("hspecial");
		int spec_study=(Integer)map.get("spec_study");
		int difference=(Integer)map.get("difference");
		int substitute_course=(Integer)map.get("substitute_course");
		int overhour_pay=(Integer)map.get("overhour_pay");
		int class_teacher_pay=(Integer)map.get("class_teacher_pay");
		int transportation=(Integer)map.get("transportation");
		int night_transport=(Integer)map.get("night_transport");
		int supervisor_test=(Integer)map.get("supervisor_test");
		int night_overhour_pay=(Integer)map.get("night_overhour_pay");
		int others_in_1=(Integer)map.get("others_in_1");
		int others_in_2=(Integer)map.get("others_in_2");
		int notax=(Integer)map.get("notax");
		int payamt=(Integer)map.get("payamt");
		*/
		return 
		(Integer)map.get("monthly_pay")+
		(Integer)map.get("inslast")+
		(Integer)map.get("study")+
		(Integer)map.get("technical")+
		(Integer)map.get("hspecial")+
		(Integer)map.get("spec_study")+
		(Integer)map.get("difference")+
		(Integer)map.get("substitute_course")+
		(Integer)map.get("overhour_pay")+
		(Integer)map.get("class_teacher_pay")+
		(Integer)map.get("transportation")+
		(Integer)map.get("night_transport")+
		(Integer)map.get("supervisor_test")+
		(Integer)map.get("night_overhour_pay")+
		(Integer)map.get("others_in_1")+
		(Integer)map.get("others_in_2");
		//(Integer)map.get("notax");
		//(Integer)map.get("payamt");
	}
	
	/**
	 * 應減
	 * @param map
	 * @return
	 */
	private Integer subtract(Map map){
		/*
		int public_insure=(Integer)map.get("public_insure");//扣公保
		int teach_over=(Integer)map.get("teach_over");//扣健保
		int spouse_insure=(Integer)map.get("spouse_insure");//軍保
		int tax_pay=(Integer)map.get("tax_pay");//所得稅
		int borrow=(Integer)map.get("borrow");//借支
		int others_out_1=(Integer)map.get("others_out_1");
		int others_out_2=(Integer)map.get("others_out_2");
		int hour_pay=(Integer)map.get("hour_pay");//勞保
		*/
		
		return 
		(Integer)map.get("public_insure")+
		(Integer)map.get("teach_over")+
		(Integer)map.get("spouse_insure")+
		(Integer)map.get("tax_pay")+
		(Integer)map.get("borrow")+
		(Integer)map.get("others_out_1")+
		(Integer)map.get("others_out_2")+
		(Integer)map.get("hour_pay");
	}
	
	private Integer getTax(Map map){
		
		int plus=plus(map);//應支
		int subtract=subtract(map);//應扣
		int real_pay=plus-subtract;//實付
		int family_no=(Integer)map.get("family_no");//人口
		int x=0;
		int notax=(Integer)map.get("hspecial")+(Integer)map.get("class_teacher_pay")+(Integer)map.get("notax");//
		int real_tax=plus-notax;//應扣稅額
		/*
		System.out.println("應支"+plus);
		System.out.println("應扣"+subtract);
		System.out.println("實付"+real_pay);
		
		System.out.println("免稅"+notax);
		System.out.println("扣稅"+(plus-notax));
		
		System.out.println("SELECT p"+family_no+" FROM Salary_taxtable WHERE max>="+real_tax+" and mini<="+real_tax);
		*/
		CourseManager manager = (CourseManager) getBean("courseManager");
		try{
			x=manager.ezGetInt("SELECT p"+family_no+" FROM Salary_taxtable WHERE max>="+real_tax+" and mini<="+real_tax);
		}catch(Exception e){
			System.out.println("這位仁兄不用扣稅");
		}
		
		
		return x;
	}

}
