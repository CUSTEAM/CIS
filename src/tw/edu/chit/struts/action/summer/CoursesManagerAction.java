package tw.edu.chit.struts.action.summer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.dao.CourseDAO;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.DtimeClass;
import tw.edu.chit.model.DtimeExam;
import tw.edu.chit.model.DtimeTeacher;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Opencs;
import tw.edu.chit.model.Sdtime;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.SummerManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

/**
 * @author JOHN
 */
public class CoursesManagerAction extends BaseLookupDispatchAction {
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(false);
		SummerManager summerManager = (SummerManager) getBean("summerManager");
		session.setAttribute("welcome", true);
		session.setAttribute("sweek", summerManager.getSsterm());
		Toolket.resetCheckboxCookie(response, "sdtimes");
		session.removeAttribute("SdtimeList");

		session.setAttribute("mode", "");
		setContentPage(request.getSession(false), "summer/CourseManager.jsp");
		return mapping.findForward("Main");

	}
	
	/**
	 * 查詢
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		DynaActionForm sform = (DynaActionForm) form;
		SummerManager manager = (SummerManager) getBean("summerManager");
		Toolket.resetCheckboxCookie(response, "sdtimes");
		session.setAttribute("mode", "list");
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		
		String seqno = (String) sform.get("seqno");
		String opt = (String) sform.get("choseType");
		String departClass = (String) sform.get("departClass");
		String cscode = (String) sform.get("courseNumber");
		String techid = (String) sform.get("teacherId");
		String cname=(String) sform.get("teacherName");
		String status=(String) sform.get("status");
		
		session.setAttribute("SdtimeList", manager.getSdtimeBy(seqno, opt, departClass, cscode, techid, status));
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","查詢完成"));
		saveMessages(request, msg);
		
		return mapping.findForward("Main");

	}
	
	/**
	 * 檢視
	 */
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);		
		SummerManager manager = (SummerManager) getBean("summerManager");
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		
		List selected=getDtimEditList(request);
		if(selected.size()<1){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","您想看哪一個? "));
			saveErrors(request, error);			
		}
		if(selected.size()>1){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","您選擇太多筆了<font size='1'>(某些選取值是在其它分頁中)</font>"));
			saveErrors(request, error);			
		}		
		if(!error.isEmpty()){
			return mapping.findForward("Main");
		}
		
		Map map=(Map)manager.ezGetList("SELECT d.seqno, d.depart_class, d.cscode, d.which, d.techid, d.opt, d.credit, d.thour, " +
				"d.clascode, d.day1, d.day2, d.day3, d.day4, d.day5, d.day6, d.day7, d.stu_select, " +
				"d.Oid, d.status, sa.name, e.cname, c.chi_name FROM Csno c, Sabbr sa, " +
				"Sdtime d LEFT OUTER JOIN empl e ON d.techid=e.idno WHERE c.cscode=d.cscode AND " +
				"sa.no=d.depart_class AND d.Oid='"+((Map)selected.get(0)).get("Oid")+"'").get(0);
		
		
		
		session.setAttribute("sDtime", map);
		session.setAttribute("welcome", false);//顯示主查詢工具
		session.setAttribute("mode", "view");
		
		
		return mapping.findForward("Main");
	}

	protected Map getKeyMethodMap() {

		Map map = new HashMap();
		map.put("Query", "list");
		map.put("Create", "create");
		map.put("Modify", "modify");
		map.put("Cancel", "cancel");
		map.put("View", "view");
		map.put("ModifyRecord", "modifyRecord");
		map.put("CreateRecord", "createRecord");
		map.put("Clear", "clear");
		map.put("Delete", "delete");
		map.put("DeleteConfirm", "deleteConfirm");
		map.put("Complete", "complete");
		map.put("Save", "save");
		map.put("Cancel", "cancel");
		return map;
	}
	/**
	 * 取消
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		session.setAttribute("welcome", true);
		session.setAttribute("mode", "list");
		
		
		return mapping.findForward("Main");
	}
	
	/**
	 * 準備啟動新增
	 */
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		DynaActionForm sform = (DynaActionForm) form;
		SummerManager manager = (SummerManager) getBean("summerManager");
		Toolket.resetCheckboxCookie(response, "sdtimes");
		session.setAttribute("mode", "create");
		session.setAttribute("welcome", false);//不顯示主查詢工具
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		
		
		return mapping.findForward("Main");
	}
	
	/**
	 * 準備啟動刪除
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		DynaActionForm sform = (DynaActionForm) form;
		SummerManager manager = (SummerManager) getBean("summerManager");
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		
		List selected=getDtimEditList(request);
		if(selected.size()<1){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","您要刪除哪一門課程? "));
			saveErrors(request, error);			
		}
				
		for(int i=0; i<selected.size(); i++){
			String tmpOid=((Map)selected.get(i)).get("Oid").toString();
			Map tmpSdtime=(Map)manager.ezGetList("SELECT status, depart_class, cscode FROM Sdtime WHERE Oid='"+tmpOid+"'").get(0);
			String status=tmpSdtime.get("status").toString();
			if(status.equals("0")){
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", tmpSdtime.get("depart_class").toString()+", "+tmpSdtime.get("cscode").toString()+" 並沒有設成退費狀況, 您不可以刪除它<br>"));
				saveErrors(request, error);
			}else{
				manager.removeObject("Sdtime", tmpOid);
			}
		}
		
		if(!error.isEmpty()){
			return mapping.findForward("Main");
		}
		
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","刪除完成!"));
		saveMessages(request, msg);
		
		
		String seqno = (String) sform.get("seqno");
		String opt = (String) sform.get("choseType");
		String departClass = (String) sform.get("departClass");
		String cscode = (String) sform.get("courseNumber");
		String techid = (String) sform.get("teacherId");
		String cname=(String) sform.get("teacherName");
		String status=(String) sform.get("status");
		
		session.setAttribute("SdtimeList", manager.getSdtimeBy(seqno, opt, departClass, cscode, techid, status));		
		
		session.setAttribute("mode", "list");
		session.setAttribute("welcome", true);//顯示主查詢工具
		Toolket.resetCheckboxCookie(response, "sdtimes");
		return mapping.findForward("Main");
	}
	
	/**
	 * 準備啟動修改
	 */
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(false);
		
		SummerManager manager = (SummerManager) getBean("summerManager");
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		
		List selected=getDtimEditList(request);
		if(selected.size()<1){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","您要改哪一個? "));
			saveErrors(request, error);			
		}
		if(selected.size()>1){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","您選擇太多筆了<font size='1'>(某些選取值是在其它分頁中)</font>"));
			saveErrors(request, error);			
		}		
		if(!error.isEmpty()){
			return mapping.findForward("Main");
		}
		
		Map map=(Map)manager.ezGetList("SELECT d.seqno, d.depart_class, d.cscode, d.which, d.techid, d.opt, d.credit, d.thour, " +
				"d.clascode, d.day1, d.day2, d.day3, d.day4, d.day5, d.day6, d.day7, d.stu_select, " +
				"d.Oid, d.status, sa.name, e.cname, c.chi_name FROM Csno c, Sabbr sa, " +
				"Sdtime d LEFT OUTER JOIN empl e ON d.techid=e.idno WHERE c.cscode=d.cscode AND " +
				"sa.no=d.depart_class AND d.Oid='"+((Map)selected.get(0)).get("Oid")+"'").get(0);
		
		
		
		session.setAttribute("sDtime", map);
		
		
		
		
		
		
		session.setAttribute("welcome", false);//不顯示主查詢工具
		session.setAttribute("mode", "edit");
		return mapping.findForward("Main");
	}
	
	/**
	 * 儲存
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		SummerManager manager = (SummerManager) getBean("summerManager");
		DynaActionForm sform = (DynaActionForm) form;
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		
		String seqno = (String) sform.get("seqno");
		String opt = (String) sform.get("choseType");
		String thour = (String) sform.get("thour");
		String credit = (String) sform.get("credit");
		String departClass = (String) sform.get("departClass");
		String cscode = (String) sform.get("courseNumber");
		String techid = (String) sform.get("teacherId");
		//String cname=(String) sform.get("teacherName");
		String place=(String) sform.get("place");
		String status=(String) sform.get("status");		
		
		if(seqno.trim().equals("")||
		   opt.trim().equals("")||
		   thour.trim().equals("")||
		   credit.trim().equals("")||
		   departClass.trim().equals("")||
		   cscode.trim().equals("")||		   
		   status.trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","開課重要資料不能有任何欄位空白<font size='1'>(教師, 地點, 排課..等不確定因素除外)</font>"));
			saveErrors(request, error);	
			return mapping.findForward("Main");
		}
		
		String day1=(String) sform.get("day1");
		String day2=(String) sform.get("day2");
		String day3=(String) sform.get("day3");
		String day4=(String) sform.get("day4");
		String day5=(String) sform.get("day5");
		String day6=(String) sform.get("day6");
		String day7=(String) sform.get("day7");
		String oid=(String) sform.get("oid");
				
		//TODO 開第幾班和人數計數器 是否該儲存
		Sdtime sdtime;
		CourseManager courseManager = (CourseManager) getBean("courseManager");
		
		if(!oid.trim().equals("")){
			sdtime=(Sdtime)courseManager.hqlGetBy("FROM Sdtime WHERE Oid='"+oid+"'").get(0);
		}else{
		sdtime=new Sdtime();
		}
		
		sdtime.setClascode(place);
		sdtime.setCredit(Double.parseDouble(credit));
		sdtime.setDepartClass(departClass);
		sdtime.setTechid(techid);
		sdtime.setOpt(opt);
		sdtime.setThour(Short.parseShort(thour));
		sdtime.setCscode(cscode);
		sdtime.setDay1(day1);
		sdtime.setDay2(day2);
		sdtime.setDay3(day3);
		sdtime.setDay4(day4);
		sdtime.setDay5(day5);
		sdtime.setDay6(day6);
		sdtime.setDay7(day7);
		sdtime.setStatus(status);
		sdtime.setSeqno(Short.parseShort(seqno));
		
		//manager.saveObj(sdtime);
		courseManager.updateObject(sdtime);
		
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","儲存完成!"));
		saveMessages(request, msg);
		session.setAttribute("welcome", true);//顯示主查詢工具
		session.setAttribute("mode", "");
		return mapping.findForward("Main");
	}
	
	/**
	 * 重設
	 */
	public ActionForward clear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		return mapping.findForward("Main");
	}
	
	/**
	 * 取得勾選盒
	 */
	private List getDtimEditList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "sdtimes");
		List dtimes = (List) session.getAttribute("SdtimeList");
		SummerManager manager = (SummerManager) getBean("summerManager");
		List selDtimes=new ArrayList();
		Map map;
		for(int i=0; i<dtimes.size(); i++){
			map = (Map)dtimes.get(i);
			if (Toolket.isValueInCookie(map.get("Oid").toString(), oids)) {
				selDtimes.addAll(manager.ezGetList("SELECT Oid FROM Sdtime WHERE Oid='"+
						map.get("Oid").toString()+"'"));
			}
		}

		return selDtimes;
	}

}
