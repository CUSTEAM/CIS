package tw.edu.chit.struts.action.deptassist;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import tw.edu.chit.model.StdSkill;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class StdSkillAction extends BaseLookupDispatchAction {
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		//修改
		if(request.getParameter("edit")!=null){
			Map skill=manager.ezGetMap("SELECT l.Name, s.*, st.student_name as StudentName FROM " +
					"StdSkill s, stmd st, LicenseCode l WHERE l.Code=s.LicenseCode AND s.StudentNo=st.student_no AND s.Oid="+
					request.getParameter("edit"));
			if(skill==null){
				skill=manager.ezGetMap("SELECT l.Name, s.*, st.student_name as StudentName FROM " +
						"StdSkill s, Gstmd st, LicenseCode l WHERE l.Code=s.LicenseCode AND s.StudentNo=st.student_no AND s.Oid="+
						request.getParameter("edit"));
			}
			try{
				skill.put("AmountDate", manager.convertDate(skill.get("AmountDate").toString()));
			}catch(Exception e){}
			
			try{
				skill.put("LicenseValidDate", manager.convertDate(skill.get("LicenseValidDate").toString()));
			}catch(Exception e){}
			
			try{
				skill.put("CsName", manager.ezGetString("SELECT chi_name FROM Csno WHERE cscode='"+skill.get("Cscode")+"'"));
			}catch(Exception e){}
			try{
				skill.put("TechName", manager.ezGetString("SELECT cname FROM empl WHERE idno='"+skill.get("TechIdno")+"'"));
			}catch(Exception e){}			
			request.setAttribute("skill", skill);
		}
		
		//刪除
		if(request.getParameter("del")!=null){
			manager.executeSql("DELETE FROM StdSkill WHERE Oid="+request.getParameter("del"));
			ActionMessages msg = new ActionMessages();
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","已刪除"));
			saveMessages(request, msg);
			return query(mapping, form, request, response);
		}
		
		

		setContentPage(session, "assistant/StdSkill.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm dForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String classless=dForm.getString("Cidno")+dForm.getString("Sidno")+dForm.getString("Didno")+dForm.getString("Grade")+dForm.getString("ClassNo");
		
		List s=manager.ezGetBy("SELECT l.Type, l.Code, s.Oid, st.student_no, st.student_name, s.SchoolYear, s.SchoolTerm, " +
				"c.ClassName, SUBSTR(l.Name, 1, 15)as name, l.Locale, SUBSTR(l.Level, 1, 15)as Level, " +
				"SUBSTR(s.LicenseValidDate, 1,7)as LicenseValidDate, SUBSTR(s.AmountDate, 1,7)as AmountDate, " +
				"s.AmountType, s.Amount FROM StdSkill s, LicenseCode l, " +
				"Class c, stmd st WHERE c.ClassNo=st.depart_class AND s.studentNo=st.student_no AND " +
				"c.ClassNo LIKE'"+classless+"%' AND " +
				"s.LicenseCode=l.Code AND s.licenseCode LIKE '"+dForm.getString("LicenseCode")+"%' AND " +
				"st.student_no LIKE '"+dForm.getString("StudentNo")+"%' AND s.schoolYear LIKE'"+
				dForm.getString("SchoolYear")+"%' AND s.schoolTerm LIKE '"+dForm.getString("SchoolTerm")+"%'");
		s.addAll(manager.ezGetBy("SELECT l.Type, l.Code, s.Oid, st.student_no, st.student_name, s.SchoolYear, s.SchoolTerm, " +
				"c.ClassName, SUBSTR(l.Name, 1, 15)as name, l.Locale, SUBSTR(l.Level, 1, 15)as Level, " +
				"SUBSTR(s.LicenseValidDate, 1,7)as LicenseValidDate, SUBSTR(s.AmountDate, 1,7)as AmountDate, " +
				"s.AmountType, s.Amount FROM StdSkill s, LicenseCode l, " +
				"Class c, Gstmd st WHERE c.ClassNo=st.depart_class AND s.studentNo=st.student_no AND " +
				"c.ClassNo LIKE'"+classless+"%' AND " +
				"s.LicenseCode=l.Code AND s.licenseCode LIKE '"+dForm.getString("LicenseCode")+"%' AND " +
				"st.student_no LIKE '"+dForm.getString("StudentNo")+"%' AND s.schoolYear LIKE'"+
				dForm.getString("SchoolYear")+"%' AND s.schoolTerm LIKE '"+dForm.getString("SchoolTerm")+"%'"));
		
		for(int i=0; i<s.size(); i++){
			((Map)s.get(i)).put("Name", ((Map)s.get(i)).get("Code")+" "+((Map)s.get(i)).get("Name"));
			((Map)s.get(i)).put("termyear", ((Map)s.get(i)).get("SchoolYear")+" "+((Map)s.get(i)).get("SchoolTerm"));
			((Map)s.get(i)).put("Type", getType(((Map)s.get(i)).get("Type"), "Type"));
			((Map)s.get(i)).put("AmountType", getType(((Map)s.get(i)).get("AmountType"), "AmountType"));
			((Map)s.get(i)).put("act", "<a href='/CIS/DeptAssistant/StdSkill.do?edit="+((Map)s.get(i)).get("Oid")+"'>修改</a> " +
					"<a onClick='confirmSubmit(\"確定刪除嗎?\");' href='/CIS/DeptAssistant/StdSkill.do?del="+((Map)s.get(i)).get("Oid")+"'>刪除</a>");
		}
		
		session.setAttribute("skilist", s);
		//return unspecified(mapping, form, request, response);
		return mapping.findForward("Main");
	}
	
	private String getType(Object o, String type){
		if(type.equals("Type")){
			if(o.equals("1"))return "國際";
			if(o.equals("2"))return "政府";
			if(o.equals("3"))return "語文";
			if(o.equals("4"))return "其他";
		}
		
		if(type.equals("AmountType")){
			if(o.equals("1"))return "報名費";
			if(o.equals("2"))return "獎學金";
			if(o.equals("3"))return "無補助";
			
		}
		
		return "";
	}
	
	//新增
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm dForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		//String Cidno=dForm.getString("Cidno");
		//String Sidno=dForm.getString("Sidno");
		//String Didno=dForm.getString("Didno");
		//String Grade=dForm.getString("Grade");
		//String ClassNo=dForm.getString("ClassNo");
		
		String SchoolYear=dForm.getString("SchoolYear");
		String SchoolTerm=dForm.getString("SchoolTerm");
		String StudentNo=dForm.getString("StudentNo");
		String LicenseCode=dForm.getString("LicenseCode");
		String Amount=dForm.getString("Amount");
		String AmountType=dForm.getString("AmountType");
		String AmountDate=dForm.getString("AmountDate");
		String LicenseNo=dForm.getString("LicenseNo");
		String LicenseValidDate=dForm.getString("LicenseValidDate");
		//String DeptNo=dForm.getString("DeptNo");
		String Cscode=dForm.getString("Cscode");
		String TechIdno=dForm.getString("TechIdno");
		String SerialNo=dForm.getString("SerialNo");
		String CustomNo=dForm.getString("CustomNo");
		String ApplyType=dForm.getString("ApplyType");
		String Pass=dForm.getString("Pass");
		String Reason=dForm.getString("Reason");
		
		if(SchoolYear.equals("")||SchoolTerm.equals("")){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "學年學期"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		if(StudentNo.equals("")){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "學號"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		if(LicenseValidDate.equals("")){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "生效日期"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		if(LicenseCode.equals("")){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "證照代碼"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		
		try{
			StdSkill k=new StdSkill();
			if(!Amount.equals(""))
			k.setAmount(Integer.parseInt(Amount));
			
			if(!AmountDate.equals(""))
			k.setAmountDate(sf.parse(manager.convertDate(AmountDate)));		
			k.setAmountType(AmountType);
			k.setApplyType(ApplyType);
			k.setCscode(Cscode);
			k.setCustomNo(CustomNo);
			k.setDeptNo(manager.ezGetString("SELECT c.DeptNo FROM Class c, stmd s WHERE c.ClassNo=s.depart_class AND s.student_no='"+StudentNo+"'"));
			k.setLicenseCode(LicenseCode);
			k.setLicenseNo(LicenseNo);
			if(!LicenseValidDate.equals(""))
			k.setLicenseValidDate(sf.parse(manager.convertDate(LicenseValidDate)));
			k.setPass(Pass);
			k.setReason(Reason);
			k.setSchoolTerm(SchoolTerm);
			k.setSchoolYear(SchoolYear);
			k.setStudentNo(StudentNo);
			k.setTechIdno(TechIdno);
			k.setSerialNo(SerialNo);
			k.setLastModified(new Date());
			manager.updateObject(k);
		}catch(Exception e){
			e.printStackTrace();
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請檢查欄位"));
			saveErrors(request, error);
			return mapping.findForward("Main");
			
		}
		
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","已儲存"));
		saveMessages(request, msg);
		
		return query(mapping, form, request, response);
	}
	
	//修改
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm dForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");		
		
		String Oid=dForm.getString("Oid");
		String SchoolYear=dForm.getString("SchoolYear");
		String SchoolTerm=dForm.getString("SchoolTerm");
		String StudentNo=dForm.getString("StudentNo");
		String LicenseCode=dForm.getString("LicenseCode");
		String Amount=dForm.getString("Amount");
		String AmountType=dForm.getString("AmountType");
		String AmountDate=dForm.getString("AmountDate");
		String LicenseNo=dForm.getString("LicenseNo");
		String LicenseValidDate=dForm.getString("LicenseValidDate");
		//String DeptNo=dForm.getString("DeptNo");
		String Cscode=dForm.getString("Cscode");
		String TechIdno=dForm.getString("TechIdno");
		String SerialNo=dForm.getString("SerialNo");
		String CustomNo=dForm.getString("CustomNo");
		String ApplyType=dForm.getString("ApplyType");
		String Pass=dForm.getString("Pass");
		String Reason=dForm.getString("Reason");
		
		if(SchoolYear.equals("")||SchoolTerm.equals("")){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "學年學期"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		if(StudentNo.equals("")){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "學號"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		if(LicenseValidDate.equals("")){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "生效日期"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		if(LicenseCode.equals("")){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "證照代碼"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		try{
			StdSkill k=(StdSkill)manager.hqlGetBy("FROM StdSkill WHERE Oid="+Oid).get(0);
			if(!Amount.equals(""))
			k.setAmount(Integer.parseInt(Amount));
			
			if(!AmountDate.equals(""))
			k.setAmountDate(sf.parse(manager.convertDate(AmountDate)));		
			k.setAmountType(AmountType);
			k.setApplyType(ApplyType);
			k.setCscode(Cscode);
			k.setCustomNo(CustomNo);
			k.setDeptNo(manager.ezGetString("SELECT c.DeptNo FROM Class c, stmd s WHERE c.ClassNo=s.depart_class AND s.student_no='"+StudentNo+"'"));
			k.setLicenseCode(LicenseCode);
			k.setLicenseNo(LicenseNo);
			if(!LicenseValidDate.equals(""))
			k.setLicenseValidDate(sf.parse(manager.convertDate(LicenseValidDate)));
			k.setPass(Pass);
			k.setReason(Reason);
			k.setSchoolTerm(SchoolTerm);
			k.setSchoolYear(SchoolYear);
			k.setStudentNo(StudentNo);
			k.setTechIdno(TechIdno);
			k.setSerialNo(SerialNo);
			k.setLastModified(new Date());
			manager.updateObject(k);
		}catch(Exception e){
			e.printStackTrace();
			
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請檢查欄位"));
			saveErrors(request, error);			
		}
		
		
		//更新
		Map skill=manager.ezGetMap("SELECT l.Name, s.*, st.student_name as StudentName FROM " +
				"StdSkill s, stmd st, LicenseCode l WHERE l.Code=s.LicenseCode AND s.StudentNo=st.student_no AND s.Oid="+Oid);
		if(skill==null){
			skill=manager.ezGetMap("SELECT l.Name, s.*, st.student_name as StudentName FROM " +
					"StdSkill s, Gstmd st, LicenseCode l WHERE l.Code=s.LicenseCode AND s.StudentNo=st.student_no AND s.Oid="+
					request.getParameter("edit"));
		}
		try{
			skill.put("AmountDate", manager.convertDate(skill.get("AmountDate").toString()));
		}catch(Exception e){}
		
		try{
			skill.put("LicenseValidDate", manager.convertDate(skill.get("LicenseValidDate").toString()));
		}catch(Exception e){}
		
		try{
			skill.put("CsName", manager.ezGetString("SELECT chi_name FROM Csno WHERE cscode='"+skill.get("Cscode")+"'"));
		}catch(Exception e){}
		try{
			skill.put("TechName", manager.ezGetString("SELECT cname FROM empl WHERE idno='"+skill.get("TechIdno")+"'"));
		}catch(Exception e){}			
		request.setAttribute("skill", skill);
		
		if(error.isEmpty()){
			ActionMessages msg = new ActionMessages();
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","已儲存"));
			saveMessages(request, msg);
		}
		return mapping.findForward("Main");
	}
	
	public ActionForward clear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DynaActionForm dForm = (DynaActionForm) form;
		//System.out.println("clear?");
		//dForm.reset(mapping, request);
		
		dForm.set("SchoolYear", "");
		dForm.set("SchoolTerm", "");
		dForm.set("Cidno", "");
		dForm.set("Sidno", "");
		dForm.set("Didno", "");
		dForm.set("Grade", "");
		dForm.set("ClassNo", "");
		dForm.set("SchoolYear", "");
		dForm.set("SchoolTerm", "");
		dForm.set("StudentNo", "");
		dForm.set("LicenseCode", "");
		dForm.set("Amount", "");
		dForm.set("AmountType", "");
		dForm.set("AmountDate", "");
		dForm.set("LicenseNo", "");
		dForm.set("LicenseValidDate", "");
		dForm.set("DeptNo", "");
		dForm.set("Cscode", "");
		dForm.set("TechIdno", "");
		dForm.set("SerialNo", "");
		dForm.set("CustomNo", "");
		dForm.set("ApplyType", "");
		
		dForm.set("Pass", "");
		dForm.set("Reason", "");
		
		dForm.set("SchoolTerm", "");
		dForm.set("StudentName", "");
		dForm.set("LicenseName", "");
		dForm.set("TechName", "");
		dForm.set("CsName", "");
		
		HttpSession session = request.getSession(false);
		session.removeAttribute("skilist");
		return mapping.findForward("Main");
	}
	
	public ActionForward print(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DynaActionForm dForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		Date date=new Date();
		//SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment;filename="+date.getTime()+".xls");		
		PrintWriter out=response.getWriter();
		
		String classless=dForm.getString("Cidno")+dForm.getString("Sidno")+dForm.getString("Didno")+dForm.getString("Grade")+dForm.getString("ClassNo");
		
		List<Map>s=manager.ezGetBy("SELECT l.Type, l.Code, s.Oid, st.student_no, st.student_name, s.SchoolYear, s.SchoolTerm, " +
				"c.ClassName, SUBSTR(l.Name, 1, 15)as name, l.Locale, SUBSTR(l.Level, 1, 15)as Level, " +
				"SUBSTR(s.LicenseValidDate, 1,7)as LicenseValidDate, s.AmountDate, " +
				"s.AmountType, s.Amount FROM StdSkill s, LicenseCode l, " +
				"Class c, stmd st WHERE c.ClassNo=st.depart_class AND s.studentNo=st.student_no AND " +
				"c.ClassNo LIKE'"+classless+"%' AND " +
				"s.LicenseCode=l.Code AND s.licenseCode LIKE '"+dForm.getString("LicenseCode")+"%' AND " +
				"st.student_no LIKE '"+dForm.getString("StudentNo")+"%' AND s.schoolYear LIKE'"+
				dForm.getString("SchoolYear")+"%' AND s.schoolTerm LIKE '"+dForm.getString("SchoolTerm")+"%'");
		
		s.addAll(manager.ezGetBy("SELECT l.Type, l.Code, s.Oid, st.student_no, st.student_name, s.SchoolYear, s.SchoolTerm, " +
				"c.ClassName, SUBSTR(l.Name, 1, 15)as name, l.Locale, SUBSTR(l.Level, 1, 15)as Level, " +
				"SUBSTR(s.LicenseValidDate, 1,7)as LicenseValidDate, s.AmountDate, " +
				"s.AmountType, s.Amount FROM StdSkill s, LicenseCode l, " +
				"Class c, Gstmd st WHERE c.ClassNo=st.depart_class AND s.studentNo=st.student_no AND " +
				"c.ClassNo LIKE'"+classless+"%' AND " +
				"s.LicenseCode=l.Code AND s.licenseCode LIKE '"+dForm.getString("LicenseCode")+"%' AND " +
				"st.student_no LIKE '"+dForm.getString("StudentNo")+"%' AND s.schoolYear LIKE'"+
				dForm.getString("SchoolYear")+"%' AND s.schoolTerm LIKE '"+dForm.getString("SchoolTerm")+"%'"));
		
		for(int i=0; i<s.size(); i++){
			s.get(i).put("Name", s.get(i).get("Code")+" "+((Map)s.get(i)).get("Name"));
			s.get(i).put("termyear", s.get(i).get("SchoolYear")+" "+((Map)s.get(i)).get("SchoolTerm"));
			s.get(i).put("Type", getType(s.get(i).get("Type"), "Type"));
			s.get(i).put("AmountType", getType(s.get(i).get("AmountType"), "AmountType"));
			
		}
		
		
		out.println ("<!DOCTYPE html>");
		out.println ("<html>");
		out.println ("  <head>");
		out.println ("  <meta http-equiv='content-type' content='text/html; charset=UTF-8'>");
		out.println ("  <style>table, th, td {border: 1px solid ;font-size:18px;}</style>");
		out.println ("  </head>");
		out.println ("  <body>");
		out.println ("<table>");
		out.println ("<tr>");		
		out.println ("<td>學期</td>");
		out.println ("<td>班級</td>");
		out.println ("<td>學號</td>");
		out.println ("<td>姓名</td>");
		out.println ("<td>代碼/名稱</td>");
		out.println ("<td>類別1</td>");
		out.println ("<td>類別2</td>");
		out.println ("<td>補助</td>");
		out.println ("<td>補助日期</td>");
		out.println ("</tr>");
		
		for(int i=0; i<s.size(); i++){
			
			out.println ("<tr>");		
			out.println ("<td>"+s.get(i).get("SchoolYear")+s.get(i).get("SchoolTerm")+"</td>");
			out.println ("<td>"+s.get(i).get("ClassName")+"</td>");
			out.println ("<td>"+s.get(i).get("student_no")+"</td>");
			out.println ("<td>"+s.get(i).get("student_name")+"</td>");
			out.println ("<td>"+s.get(i).get("name")+"</td>");
			out.println ("<td>"+s.get(i).get("Type")+"</td>");
			out.println ("<td>"+s.get(i).get("AmountType")+"</td>");
			out.println ("<td>"+s.get(i).get("Amount")+"</td>");
			out.println ("<td>"+s.get(i).get("AmountDate")+"</td>");
			out.println ("</tr>");
			
		}
		
		
		
		
		
		out.println ("  </body>");
		out.println ("</html>");
		
		
		
		return null;
	}
	

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map map = new HashMap();
		map.put("Query", "query");		
		map.put("Create", "add");
		map.put("Modify", "edit");
		map.put("Clear", "clear");
		map.put("Print", "print");
		return map;
	}

}
