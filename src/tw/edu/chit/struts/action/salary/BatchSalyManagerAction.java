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

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class BatchSalyManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Toolket.resetCheckboxCookie(response, "salys");
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		request.setAttribute("taxTable", manager.ezGetBy("SELECT * FROM Salary_taxtable"));
		
		
		
		
		session.removeAttribute("saly_mode");
		session.removeAttribute("allSaly");
		session.removeAttribute("aSaly");
		session.removeAttribute("index");
		
		
		setContentPage(request.getSession(false), "salary/BatchSalyManager.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 查詢
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Toolket.resetCheckboxCookie(response, "salys");
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm sForm = (DynaActionForm) form;
		
		String idno=sForm.getString("idno");
		String sdate=sForm.getString("sdate");
		
		if(sdate.trim().length()>6){
			sdate=manager.convertDate(sForm.getString("sdate"));
		}
		
		if(sdate.trim().length()<=6 && idno.trim().equals("")){//若無指定員工而日期空白給予錯誤
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","必須指定日期"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		List list=manager.ezGetBy("SELECT s.*, e.cname, e.sname FROM Saly s, empl e WHERE e.idno=s.idno AND s.idno LIKE '"+idno+"%' AND " +
				"s.sdate LIKE '"+sdate+"%' ORDER BY s.seqno, s.sdate DESC");
		List list1=manager.ezGetBy("SELECT s.*, e.cname, e.sname FROM Saly s, dempl e WHERE e.idno=s.idno AND s.idno LIKE '"+idno+"%' AND " +
				"s.sdate LIKE '"+sdate+"%' ORDER BY s.seqno, s.sdate DESC");		
		session.setAttribute("allSaly", list);
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",sdate+"查詢完成"));
		if(list1.size()>0){
			list.addAll(list1);
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",", 同時發現"+list1.size()+"個已離職卻未清除的教職員"));
		}
		
		session.setAttribute("saly_mode", "list");
		session.setAttribute("realPay", manager.ezGetInt("SELECT SUM(real_pay) FROM Saly WHERE idno LIKE '"+idno+"%' AND  sdate LIKE'"+sdate+"%'"));
		
		
		saveMessages(request, msg);
		return mapping.findForward("Main");
	}
	
	/**
	 * 新增
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm sForm = (DynaActionForm) form;
		
		String idno=sForm.getString("idno");
		String sdate=sForm.getString("sdate");
		String seqno=sForm.getString("seqno");
		String monthly_pay=sForm.getString("monthly_pay");
		String inslast=sForm.getString("inslast");
		String study=sForm.getString("study");
		String technical=sForm.getString("technical");
		String hspecial=sForm.getString("hspecial");
		String spec_study=sForm.getString("spec_study");
		String difference=sForm.getString("difference");
		String substitute_course=sForm.getString("substitute_course");
		String overhour_pay=sForm.getString("overhour_pay");
		String class_teacher_pay=sForm.getString("class_teacher_pay");
		String transportation=sForm.getString("transportation");
		String night_transport=sForm.getString("night_transport");
		String supervisor_test=sForm.getString("supervisor_test");
		String night_overhour_pay=sForm.getString("night_overhour_pay");
		String others_in_1=sForm.getString("others_in_1");
		String others_in_2=sForm.getString("others_in_2");
		String notax=sForm.getString("notax");
		String payamt=sForm.getString("payamt");
		String public_insure=sForm.getString("public_insure");
		String spouse_insure=sForm.getString("spouse_insure");
		String tax_pay=sForm.getString("tax_pay");
		String borrow=sForm.getString("borrow");
		String others_out_1=sForm.getString("others_out_1");
		String others_out_2=sForm.getString("others_out_2");
		String dedamt=sForm.getString("dedamt");
		String real_pay=sForm.getString("real_pay");
		String teach_over=sForm.getString("teach_over");
		String hour_pay=sForm.getString("hour_pay");
		String family_no=sForm.getString("family_no");
		String Oid=sForm.getString("Oid");
		
		
		
		return mapping.findForward("Main");
	}
	
	/**
	 * 修改某人某次
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
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm sForm = (DynaActionForm) form;
		
		List list=getCheckBox(request);
		for(int i=0; i<list.size(); i++){
			System.out.println(list.get(i));
		}
		
		session.setAttribute("index", ((Map)list.get(0)).get("index"));
		session.setAttribute("aSaly", list.get(0));
		
		session.setAttribute("saly_mode", "edit");
		return mapping.findForward("Main");
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Create", "create");
		map.put("Modify", "modify");
		
		map.put("Update", "update");
		map.put("Prev", "prev");
		map.put("Next", "next");
		
		map.put("Cancel", "cancel");
		return map;
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
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","動作已取消"));
		saveMessages(request, msg);
		return query(mapping, form, request, response);
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
		
		List list=(List)session.getAttribute("allSaly");
		int i=Integer.parseInt(session.getAttribute("index").toString());
		i=i+1;
		
		if(i>=list.size()){
			i=list.size();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","後面沒了"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		session.setAttribute("aSaly", getSaly(((Map)list.get(i)).get("Oid").toString()));
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",((Map)list.get(i)).get("cname")+" 的薪資"));
		
		session.setAttribute("index", i);
		saveMessages(request, msg);		
		
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
		
		List list=(List)session.getAttribute("allSaly");
		int i=Integer.parseInt(session.getAttribute("index").toString());		
		i=i-1;
		if(i<0){
			i=0;
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","前面沒了"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		session.setAttribute("aSaly", getSaly(((Map)list.get(i)).get("Oid").toString()));
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",((Map)list.get(i)).get("cname")+" 的薪資"));
		session.setAttribute("index", i);//目前順序
		saveMessages(request, msg);		
		
		return mapping.findForward("Main");
	}
	
	/**
	 * 取一筆
	 * @param idno
	 * @return
	 */
	private Map getSaly(String Oid){
		CourseManager manager = (CourseManager) getBean("courseManager");		
		return manager.ezGetMap("SELECT ce1.name as category, e.cname, e.sname, ce.name, ss.* FROM Saly ss, " +
				"(empl e LEFT OUTER JOIN CodeEmpl ce ON e.unit=ce.idno AND (ce.category='Unit' || ce.category='UnitTeach'))LEFT OUTER JOIN CodeEmpl ce1 ON e.category=ce1.idno AND ce1.category='EmpCategory'" +
				"WHERE ss.Oid = '"+Oid+"' AND e.idno=ss.idno");		
	}
	
	/**
	 * 取勾選
	 * @param request
	 * @return
	 */
	private List getCheckBox(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		String tmps = Toolket.getSelectedIndexFromCookie(request, "salys");
		
		List allSaly = (List) session.getAttribute("allSaly");
		Map map;
		//Map tmpEmpl;
		List list=new ArrayList();
		String Oid;
		for(int i=0; i<allSaly.size(); i++){
			Oid=((Map)allSaly.get(i)).get("Oid").toString();
			if (Toolket.isValueInCookie(Oid, tmps)){				
				((Map)allSaly.get(i)).put("index", i);//順序
				list.add(allSaly.get(i));
			}
		}
		return list;
	}

}
