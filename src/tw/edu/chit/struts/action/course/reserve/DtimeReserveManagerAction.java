package tw.edu.chit.struts.action.course.reserve;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.DtimeReserveReady;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 課程規劃
 * @author shawn
 *
 */
public class DtimeReserveManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		if(request.getParameter("Dtime_reserve_oid")!=null){
			
			
			request.setAttribute("aDtimeReserve", manager.ezGetMap("SELECT c.chi_name, e.cname, d.* FROM " +
					"Dtime_reserve_ready d LEFT OUTER JOIN empl e ON d.techid=e.idno, Csno c " +
					"WHERE c.cscode=d.cscode AND d.Oid="+request.getParameter("Dtime_reserve_oid")));	
			
			request.setAttribute("teachers", manager.ezGetBy("SELECT e.cname, d.* FROM " +
					"Dtime_reserve_teachers d, empl e WHERE e.idno=d.idno AND " +
					"d.Dtime_reserve_oid="+request.getParameter("Dtime_reserve_oid")));
		}
		
		setContentPage(request.getSession(false), "course/DtimeReserve.jsp");		
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
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		DynaActionForm dForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String year=dForm.getString("year");
		String depart_class=dForm.getString("classLess");
		
		if(year.trim().equals("")||depart_class.length()!=6){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必需指定入學年及科系"));
			saveErrors(request, error);
			return unspecified(mapping, form, request, response);
		}		
		
		List opt1=manager.ezGetBy("SELECT c.chi_name, e.cname, d.* FROM " +//必修
				"Dtime_reserve_ready d LEFT OUTER JOIN empl e ON d.techid=e.idno, Csno c " +
				"WHERE c.cscode=d.cscode AND d.depart_class='"+depart_class+"' AND year='"+year+"'  ORDER BY term, opt");
				
		request.setAttribute("opt1", opt1);
		return unspecified(mapping, form, request, response);
		//return mapping.findForward("Main");
	}	
	
	/**
	 * 建立
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		DynaActionForm dForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String cscode=dForm.getString("cscode");
		
		String opt=dForm.getString("opt");
		String credit=dForm.getString("credit");
		
		String Select_Limit=dForm.getString("Select_Limit");
		String term=dForm.getString("term");
		String cyber=dForm.getString("cyber");
		//String innovate=dForm.getString("innovate");
		//String license=dForm.getString("license");
		String additionType=dForm.getString("additionType");
		String confirmType=dForm.getString("confirmType");
		
		String techid=dForm.getString("techid");
		String thour=dForm.getString("thour");
		
		String year=dForm.getString("year");
		String depart_class=dForm.getString("classLess");
		
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		if(year.trim().equals("")){			
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必需指定入學年及科系"));			
		}
		
		if(cscode.trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必需指定課程名稱"));			
		}
		
		if(term.trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必需指定學期"));			
		}
		
		if(credit.trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必需指定學分"));			
		}
		
		if(thour.trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必需指定時數"));			
		}
		
		if(Select_Limit.trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必需指定人數上限"));			
		}
		
		if(!error.isEmpty()){
			saveErrors(request, error);
			return query(mapping, form, request, response);
		}
		
		if(!techid.trim().equals(""))
		if(!manager.checkDtimeReserve(null, techid, year, term, Integer.parseInt(thour), Float.parseFloat(credit), Integer.parseInt(Select_Limit), additionType)){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", 
					manager.ezGetString("SELECT cname FROM empl WHERE idno='"+techid+"'")+
					"同仁上限為 "+manager.sumEmplLimit(techid)+"時數，"+
					"已規劃 "+manager.sumDtimeReserve(null, techid, year, term)+"時數, 不足以增加本次規劃 "+thour+"時數"
					
			));	
			saveErrors(request, error);
			return query(mapping, form, request, response);
		}		
		
		DtimeReserveReady dtime=new DtimeReserveReady();
		dtime.setAdditionType(additionType);
		dtime.setConfirmType(confirmType);
		if(!credit.trim().equals("")){
			dtime.setCredit(Float.parseFloat(credit));
		}
		dtime.setCscode(cscode);
		dtime.setCyber(cyber);
		dtime.setDepartClass(depart_class);
		dtime.setOpt(opt);
		
		if(!Select_Limit.trim().equals("")){
			dtime.setSelectLimit(Short.parseShort(Select_Limit));
		}				
		dtime.setTechid(techid);
		dtime.setTerm(term);				
		if(!thour.trim().equals("")){
			dtime.setThour(Short.parseShort(thour));
		}
		
		dtime.setYear(Integer.parseInt(year));
		manager.updateObject(dtime);
		
		return query(mapping, form, request, response);
	}
	
	/**
	 * 儲存
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		//HttpSession session = request.getSession(false);
		DynaActionForm dForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String Oid=dForm.getString("Oid");
		
		String cscode=dForm.getString("cscode");
		
		String opt=dForm.getString("opt");
		String credit=dForm.getString("credit");
		
		String Select_Limit=dForm.getString("Select_Limit");
		String term=dForm.getString("term");
		String cyber=dForm.getString("cyber");
		//String innovate=dForm.getString("innovate");
		//String license=dForm.getString("license");
		String additionType=dForm.getString("additionType");
		String confirmType=dForm.getString("confirmType");
		
		String techid=dForm.getString("techid");
		String thour=dForm.getString("thour");
		
		String techids[]=dForm.getStrings("techids");
		String thours[]=dForm.getStrings("thours");		
		
		DtimeReserveReady dtime=(DtimeReserveReady) manager.hqlGetBy("FROM DtimeReserveReady WHERE Oid="+Oid).get(0);
		dtime.setAdditionType(additionType);
		dtime.setConfirmType(confirmType);
		if(!credit.trim().equals("")){
			dtime.setCredit(Float.parseFloat(credit));
		}
		dtime.setCscode(cscode);
		dtime.setCyber(cyber);
		dtime.setOpt(opt);
		
		if(!Select_Limit.trim().equals("")){
			dtime.setSelectLimit(Short.parseShort(Select_Limit));
		}				
		
		dtime.setTerm(term);				
		if(!thour.trim().equals("")){
			try{
				dtime.setThour(Short.parseShort(thour));
			}catch(Exception e){
				dtime.setThour(Short.parseShort("0"));
			}
		}
		
		if(!techid.trim().equals("")){
			if(!manager.checkDtimeReserve(dtime.getOid().toString(), techid, dtime.getYear().toString(), 
					term, Integer.parseInt(thour), Float.parseFloat(credit), Integer.parseInt(Select_Limit), additionType)){
				ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", 
						manager.ezGetString("SELECT cname FROM empl WHERE idno='"+techid+"'")+
						"同仁上限為 "+manager.sumEmplLimit(techid)+"時數，"+
						"已規劃 "+manager.sumDtimeReserve(dtime.getOid().toString(), techid, dtime.getYear().toString(), term)+"時數, 不足以增加本次規劃 "+thour+"時數"
						
				));	
				saveErrors(request, error);			
			}else{
				dtime.setTechid(techid);
				manager.updateObject(dtime);
			}
		}else{
			dtime.setTechid("");
			manager.updateObject(dtime);
		}
		
		
		String Dtime_reserve_teachers_oid[]=dForm.getStrings("Dtime_reserve_teachers_oid");		
		
		for(int i=0; i<Dtime_reserve_teachers_oid.length; i++){
			
			if(Dtime_reserve_teachers_oid[i].equals("")){
				//新增
				if(!techids[i].equals("") && !thours[i].equals("")){
					//建立
					if(!manager.checkDtimeReserve(dtime.getOid().toString(), techids[i], dtime.getYear().toString(), 
						term, Integer.parseInt(thours[i]), Float.parseFloat(credit), Integer.parseInt(Select_Limit), additionType)){
						ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
						error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", 
						manager.ezGetString("SELECT cname FROM empl WHERE idno='"+techids[i]+"'")+
						"同仁上限為 "+manager.sumEmplLimit(techids[i])+"時數，"+
						"已規劃 "+manager.sumDtimeReserve(dtime.getOid().toString(), techids[i], dtime.getYear().toString(), term)+"時數, " +
						"不足以增加本次規劃 "+thours[i]+"時數"
								
						));	
						saveErrors(request, error);			
					}else{
						manager.executeSql("INSERT INTO Dtime_reserve_teachers" +
						"(Dtime_reserve_oid,idno,thours)VALUES('"+Oid+"', '"+techids[i]+"', '"+thours[i]+"')");
					}
				}				
			}else{
				if(techids[i].equals("") || thours[i].equals("")){
					//刪除
					manager.executeSql("DELETE FROM Dtime_reserve_teachers WHERE Oid="+Dtime_reserve_teachers_oid[i]);
				}else{
					//更新
					if(!manager.checkDtimeReserve(dtime.getOid().toString(), techids[i], dtime.getYear().toString(), 
							term, Integer.parseInt(thours[i]), Float.parseFloat(credit), Integer.parseInt(Select_Limit), additionType)){
							ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
							error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", 
							manager.ezGetString("SELECT cname FROM empl WHERE idno='"+techids[i]+"'")+
							"同仁上限為 "+manager.sumEmplLimit(techids[i])+"時數，"+
							"已規劃 "+manager.sumDtimeReserve(dtime.getOid().toString(), techids[i], dtime.getYear().toString(), term)+"時數, " +
							"不足以增加本次規劃 "+thours[i]+"時數"
									
							));	
							saveErrors(request, error);			
						}else{
							manager.executeSql("UPDATE Dtime_reserve_teachers " +
							"SET idno='"+techids[i]+"', thours='"+thours[i]+"' WHERE Oid="+Dtime_reserve_teachers_oid[i]);
						}
				}
			}
		}	
		
		request.setAttribute("aDtimeReserve", manager.ezGetMap("SELECT c.chi_name, e.cname, d.* FROM " +
				"Dtime_reserve d LEFT OUTER JOIN empl e ON d.techid=e.idno, Csno c " +
				"WHERE c.cscode=d.cscode AND d.Oid="+Oid));	
		
		request.setAttribute("teachers", manager.ezGetBy("SELECT e.cname, d.* FROM " +
				"Dtime_reserve_teachers d, empl e WHERE e.idno=d.idno AND " +
				"d.Dtime_reserve_oid="+Oid));
		return unspecified(mapping, form, request, response);
	}
	
	/**
	 * 返回
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward back(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		DynaActionForm dForm = (DynaActionForm) form;
		String classLess=dForm.getString("classLess");
		Map map=manager.ezGetMap("SELECT * FROM Class WHERE ClassNo='"+classLess+"'");
		dForm.set("campusInCharge2", map.get("CampusNo"));
		dForm.set("schoolInCharge2", map.get("SchoolNo"));
		dForm.set("deptInCharge2", map.get("DeptNo"));
		dForm.set("classInCharge2", map.get("ClassNo"));
		dForm.set("year", dForm.get("year"));
		return query(mapping, form, request, response);
	}
	
	/**
	 * 刪除
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		DynaActionForm dForm = (DynaActionForm) form;
		String classLess=dForm.getString("classLess");
		Map map=manager.ezGetMap("SELECT * FROM Class WHERE ClassNo='"+classLess+"'");
		dForm.set("campusInCharge2", map.get("CampusNo"));
		dForm.set("schoolInCharge2", map.get("SchoolNo"));
		dForm.set("deptInCharge2", map.get("DeptNo"));
		dForm.set("classInCharge2", map.get("ClassNo"));
		dForm.set("year", dForm.get("year"));
		String Oid=dForm.getString("Oid");
		manager.executeSql("DELETE FROM Dtime_reserve WHERE Oid="+Oid);
		manager.executeSql("DELETE FROM Dtime_reserve_teachers WHERE Dtime_reserve_oid="+Oid);
		//manager.executeSql("DELETE FROM Dtime_reserve WHERE Oid='"+Oid);
		return query(mapping, form, request, response);
	}
	
	/**
	 * 建立第1學期
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward writeTerm1(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		DynaActionForm dForm = (DynaActionForm) form;
		String classLess=dForm.getString("classLess");
		
		String Oid[]=dForm.getStrings("Oid");
		String depart_class=dForm.getString("classLess");
		
		manager.executeSql("DELETE FROM Dtime WHERE depart_class='"+depart_class+"' AND Sterm='1'");
		Dtime dtime;
		for(int i=1; i<Oid.length; i++){			
			dtime=new Dtime();
			DtimeReserveReady reserve=(DtimeReserveReady) manager.hqlGetBy("FROM DtimeReserveReady WHERE Oid="+Oid[i]).get(0);
			dtime.setCredit(reserve.getCredit());
			dtime.setCscode(reserve.getCscode());
			dtime.setDepartClass(reserve.getDepartClass());
			dtime.setExtrapay(reserve.getCyber());
			dtime.setOpt(reserve.getOpt());
			dtime.setSelectLimit(reserve.getSelectLimit());
			dtime.setSterm("1");
			dtime.setTechid(reserve.getTechid());
			dtime.setThour(reserve.getThour());
			manager.updateObject(dtime);
		}
		
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","依據課程規劃建立 "+depart_class+"第1學期課程"));
		saveMessages(request, msg);
		
		Map map=manager.ezGetMap("SELECT * FROM Class WHERE ClassNo='"+classLess+"'");
		dForm.set("campusInCharge2", map.get("CampusNo"));
		dForm.set("schoolInCharge2", map.get("SchoolNo"));
		dForm.set("deptInCharge2", map.get("DeptNo"));
		dForm.set("classInCharge2", map.get("ClassNo"));
		dForm.set("year", dForm.get("year"));
		return query(mapping, form, request, response);
	}
	
	/**
	 * 建立第2學期
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward writeTerm2(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		DynaActionForm dForm = (DynaActionForm) form;
		String classLess=dForm.getString("classLess");
		
		String Oid[]=dForm.getStrings("Oid");
		String depart_class=dForm.getString("classLess");
		
		manager.executeSql("DELETE FROM Dtime WHERE depart_class='"+depart_class+"' AND Sterm='2'");
		Dtime dtime;
		for(int i=1; i<Oid.length; i++){			
			dtime=new Dtime();
			DtimeReserveReady reserve=(DtimeReserveReady) manager.hqlGetBy("FROM DtimeReserveReady WHERE Oid="+Oid[i]).get(0);
			dtime.setCredit(reserve.getCredit());
			dtime.setCscode(reserve.getCscode());
			dtime.setDepartClass(reserve.getDepartClass());
			dtime.setExtrapay(reserve.getCyber());
			dtime.setOpt(reserve.getOpt());
			dtime.setSelectLimit(reserve.getSelectLimit());
			dtime.setSterm("2");
			dtime.setTechid(reserve.getTechid());
			dtime.setThour(reserve.getThour());
			manager.updateObject(dtime);
		}
		
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","依據課程規劃建立 "+depart_class+"第2學期課程"));
		saveMessages(request, msg);
		
		Map map=manager.ezGetMap("SELECT * FROM Class WHERE ClassNo='"+classLess+"'");
		dForm.set("campusInCharge2", map.get("CampusNo"));
		dForm.set("schoolInCharge2", map.get("SchoolNo"));
		dForm.set("deptInCharge2", map.get("DeptNo"));
		dForm.set("classInCharge2", map.get("ClassNo"));
		dForm.set("year", dForm.get("year"));
		return query(mapping, form, request, response);
	}

	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Create", "create");
		map.put("Save", "save");
		map.put("Delete", "delete");
		map.put("Back", "back");
		map.put("WriteTerm1", "writeTerm1");
		map.put("WriteTerm2", "writeTerm2");		
		return map;
	}
}
