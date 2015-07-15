package tw.edu.chit.struts.action.deptassist.reserve;

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

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.DtimeReserve;
import tw.edu.chit.model.DtimeReserveOpencs;
import tw.edu.chit.model.DtimeReserveReady;
import tw.edu.chit.model.DtimeReserveTeacher;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 課程管理
 * @author shawn
 */
public class DtimeManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		try{
			//單一課程管理
			if(!request.getParameter("Oid").equals("")){			
				
				request.setAttribute("aDtimeReserve", manager.ezGetMap("SELECT c.chi_name, e.cname, d.* FROM " +
						"Dtime_reserve_ready d LEFT OUTER JOIN empl e ON d.techid=e.idno, Csno c " +
						"WHERE c.cscode=d.cscode AND d.Oid="+request.getParameter("Oid")));
				
				request.setAttribute("teachers", manager.ezGetBy("SELECT e.cname, d.* FROM " +
						"Dtime_reserve_teacher d, empl e WHERE e.idno=d.teach_id AND " +
						"d.Dtime_reserve_oid="+request.getParameter("Oid")));
				
				request.setAttribute("opencs", manager.ezGetBy("SELECT * FROM Dtime_reserve_opencs WHERE Dtime_oid="+request.getParameter("Oid")));
			}
			
		}catch(Exception e){
			//e.printStackTrace();
			//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			//error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", e));
			//saveErrors(request, error);
		}
		
		setContentPage(request.getSession(false), "assistant/DtimeManager.jsp");		
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
		
		String year=dForm.getString("year");//開課學年
		String term=dForm.getString("term");//開課學期
		
		String depart_class=dForm.getString("classLess");
		String grade="";		
		//int tmp;
		
		try{
			//取年級
			grade=manager.ezGetString("SELECT Grade FROM Class WHERE ClassNo='"+depart_class+"'");
		}catch(Exception e){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "班級資訊無法找到相對應的入學年度"));
			saveErrors(request, error);
			return unspecified(mapping, form, request, response);
		}
		
		if(year.trim().equals("")||depart_class.length()!=6){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必需指定入學年及科系"));
			saveErrors(request, error);
			return unspecified(mapping, form, request, response);
		}
		
		//取年級相對應的入學年度......
		int inyear=Integer.parseInt(year);
		inyear=inyear-Integer.parseInt(grade)+1;
		
		
		List reserve=manager.ezGetBy("SELECT c.chi_name, d.* FROM Dtime_reserve d, Csno c WHERE " +
		"c.cscode NOT IN(SELECT cscode FROM Dtime_reserve_ready WHERE depart_class='"+depart_class+"' AND year='"+year+"') AND " +
		"c.cscode=d.cscode AND d.depart_class='"+depart_class.substring(0, 4)+"' AND d.year='"+inyear+"' AND d.term='"+term+"' AND d.grade='"+grade+"' " +
		"ORDER BY term, opt");		
		
		List reserved=manager.ezGetBy("SELECT d.grade, d.term, d.cscode, c.chi_name, d.Oid FROM Dtime_reserve d, Csno c " +
				"WHERE d.cscode=c.cscode AND d.depart_class='"+depart_class.substring(0, 4)+"'");
		
		List opt1=manager.ezGetBy("SELECT c.chi_name, e.cname, d.* FROM " +//必修
				"Dtime_reserve_ready d LEFT OUTER JOIN empl e ON d.techid=e.idno, Csno c " +
				"WHERE c.cscode=d.cscode AND d.depart_class='"+depart_class+"' AND " +
				"d.year='"+year+"' AND d.term='"+term+"' ORDER BY term, opt");
		
		request.setAttribute("reserve", reserve);
		request.setAttribute("reserved", reserved);
		request.setAttribute("opt1", opt1);

		return mapping.findForward("Main");
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
		
		ActionMessages error = new ActionMessages();
		
		//String year=dForm.getString("year"); //最頂端
		//String classLess=dForm.getString("classLess");
		
		//給予列表再點選
		String checked[]=dForm.getStrings("checked");
		String Select_Limit[]=dForm.getStrings("Select_Limit");
		String term=dForm.getString("term");
		String cscode[]=dForm.getStrings("cscode");
		String year=dForm.getString("year");
		String depart_class=dForm.getString("classLess");
		//String grade="";		
		//int tmp;
		/*
		try{
			depart_class=dForm.getString("classLess");
			//grade=manager.ezGetString("SELECT Grade FROM Class WHERE ClassNo='"+depart_class+"'");
			
			if(term.equals("1")){
				tmp=manager.getSchoolYear()+2;
			}else{
				tmp=manager.getSchoolYear()+1;
			}
			tmp=tmp-Integer.parseInt(grade);		
			year=String.valueOf(tmp);
			
		}catch(Exception e){
			//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "班級資訊無法找到相對應的入學年度"));
			saveErrors(request, error);
		}
		*/
		
		DtimeReserve reserve;
		DtimeReserveReady ready;
		
		
		float credit;
		int thour;
		float credited;
		int thoured;
		
		Map limit;
		for(int i=0; i<cscode.length; i++){
			
			if(!checked[i].trim().equals("")){
				//點選建立
				if(Select_Limit[i].trim().equals("")){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必需指定人數"));	
					saveErrors(request, error);
					return query(mapping, form, request, response);
				}
				
				reserve=(DtimeReserve) manager.hqlGetBy("FROM DtimeReserve WHERE Oid="+checked[i]).get(0);
				ready=new DtimeReserveReady();
				ready.setAdditionType(reserve.getAdditionType());
				ready.setCredit(reserve.getCredit());
				ready.setCscode(reserve.getCscode());
				ready.setCyber(reserve.getCyber());
				ready.setDepartClass(depart_class);
				ready.setOpt(reserve.getOpt());
				ready.setTerm(term);
				ready.setThour(reserve.getThour());
				
				ready.setYear(Integer.parseInt(year));
				ready.setSelectLimit(Short.parseShort(Select_Limit[i]));				
				
				
				/*時數規劃造成很大困擾
				limit=limit(year, term, depart_class);				
				if(limit==null){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請先建立時數規劃"));	
					saveErrors(request, error);
					return query(mapping, form, request, response);
				}

				credit=Float.parseFloat(limit.get("credit").toString());
				thour=Integer.parseInt(limit.get("thour").toString());
				
				credited=countCredit(year, term, depart_class);
				thoured=countThour(year, term, depart_class);
				
				System.out.println(year+", "+term);
				
				
				
				if(ready.getCredit()+credited>credit||ready.getThour()+thoured>thour){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "本班"+year+"學年第"+term+"學期規劃"+credit+"學分, "+thour+
							"時數。目前已排"+credited+"學分及"+thoured+"時數, 不足增加"+ready.getCscode()+"的"+ready.getCredit()+"學分, "+ready.getThour()+"時數。"));
					saveErrors(request, error);
					return query(mapping, form, request, response);
				}
				*/
				
				try{					
					manager.updateObject(ready);
				}catch(Exception e){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "重複建立"+ready.getCscode()));
					saveErrors(request, error);
					return query(mapping, form, request, response);
				}								
			}			
			
			//輸入建立
			if(!cscode[i].trim().equals("")){
				if(Select_Limit[i].trim().equals("")){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必需指定人數"));	
					saveErrors(request, error);
					return query(mapping, form, request, response);
				}
				reserve=(DtimeReserve) manager.hqlGetBy("FROM DtimeReserve WHERE Oid="+cscode[i]).get(0);
				ready=new DtimeReserveReady();
				ready.setAdditionType(reserve.getAdditionType());
				ready.setCredit(reserve.getCredit());
				ready.setCscode(reserve.getCscode());
				ready.setCyber(reserve.getCyber());
				ready.setDepartClass(depart_class);
				ready.setOpt(reserve.getOpt());
				ready.setTerm(term);
				ready.setThour(reserve.getThour());
				ready.setYear(Integer.parseInt(year));
				ready.setSelectLimit(Short.parseShort(Select_Limit[i]));
				
				/*
				limit=limit(year, term, depart_class);
				if(limit==null){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請先建立時數規劃"));	
					saveErrors(request, error);
					return query(mapping, form, request, response);
				}
				
				credit=Float.parseFloat(limit.get("credit").toString());
				thour=Integer.parseInt(limit.get("thour").toString());
				credited=countCredit(year, term, depart_class);
				thoured=countThour(year, term, depart_class);
				if(ready.getCredit()+credited>credit||
						ready.getThour()+thoured>thour){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "本班"+year+"學年第"+term+"學期規劃"+credit+"學分, "+thour+
							"時數。目前已排"+credited+"學分及"+thoured+"時數, 不足增加"+ready.getCscode()+"的"+ready.getCredit()+"學分, "+ready.getThour()+"時數。"));
					saveErrors(request, error);
					return query(mapping, form, request, response);
				}
				*/
				
				try{
					manager.updateObject(ready);
				}catch(Exception e){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "重複建立"+ready.getCscode()));
					saveErrors(request, error);
					return query(mapping, form, request, response);
				}
			}			
		}
		
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
		DynaActionForm dForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");		
		String Oid[]=dForm.getStrings("Oid");
		String Select_Limit[]=dForm.getStrings("Select_Limit");
		
		ActionMessages error = new ActionMessages();
		if(Select_Limit[0].trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必需指定人數"));	
			saveErrors(request, error);
			return query(mapping, form, request, response);
		}
		
		if(!error.isEmpty()){			
			saveErrors(request, error);
			request.setAttribute("aDtimeReserve", manager.ezGetMap("SELECT c.chi_name, e.cname, d.* FROM " +
					"Dtime_reserve_ready d LEFT OUTER JOIN empl e ON d.techid=e.idno, Csno c " +
					"WHERE c.cscode=d.cscode AND d.Oid="+Oid[0]));	
			
			request.setAttribute("teachers", manager.ezGetBy("SELECT e.cname, d.* FROM " +
					"Dtime_reserve_teachers d, empl e WHERE e.idno=d.idno AND " +
					"d.Dtime_reserve_oid="+Oid[0]));
			return unspecified(mapping, form, request, response);
		}
		
		DtimeReserveReady dtime=(DtimeReserveReady) manager.hqlGetBy("FROM DtimeReserveReady WHERE Oid="+Oid[0]).get(0);		
		dtime.setSelectLimit(Short.parseShort(Select_Limit[0]));		
		manager.updateObject(dtime);
		
		String Dtime_reserve_opencs_Oid[]=dForm.getStrings("Dtime_reserve_opencs_Oid");
		String Cidno[]=dForm.getStrings("Cidno");
		String Sidno[]=dForm.getStrings("Sidno");
		String Didno[]=dForm.getStrings("Didno");
		String grades[]=dForm.getStrings("grades");
		String ClassNo[]=dForm.getStrings("ClassNo");
		
		//跨選規則
		DtimeReserveOpencs dro;
		for(int i=0; i<Dtime_reserve_opencs_Oid.length; i++){	
			//新增
			if(Dtime_reserve_opencs_Oid[i].trim().equals("")&&
			!Cidno[i].trim().equals("")&&
			!Sidno[i].trim().equals("")&&
			!Didno[i].trim().equals("")&&
			!grades[i].trim().equals("")&&
			!ClassNo[i].trim().equals("")){
				
				dro=new DtimeReserveOpencs();
				dro.setDtimeOid(dtime.getOid());
				dro.setCidno(Cidno[i]);
				dro.setClassNo(ClassNo[i]);
				dro.setDidno(Didno[i]);
				dro.setGrade(grades[i]);
				dro.setSidno(Sidno[i]);				
				manager.updateObject(dro);
			}
			
			if(!Dtime_reserve_opencs_Oid[i].trim().equals("")){
				
				if(Cidno[i].trim().equals("")){
					//刪除
					manager.executeSql("DELETE FROM Dtime_reserve_opencs WHERE Oid="+Dtime_reserve_opencs_Oid[i]);
				}else{
					//修改
					dro=(DtimeReserveOpencs)manager.hqlGetBy("FROM DtimeReserveOpencs WHERE Oid="+Dtime_reserve_opencs_Oid[i]).get(0);
					dro.setDtimeOid(dtime.getOid());
					dro.setCidno(Cidno[i]);
					dro.setClassNo(ClassNo[i]);
					dro.setDidno(Didno[i]);
					dro.setGrade(grades[i]);
					dro.setSidno(Sidno[i]);
					manager.updateObject(dro);
				}				
			}
		}		
		
		//多教師
		String Dtime_reserve_teachers_oid[]=dForm.getStrings("Dtime_reserve_teachers_Oid");		
		String techids[]=dForm.getStrings("techid");
		String thours[]=dForm.getStrings("thour");		
		
		DtimeReserveTeacher drt;
		for(int i=0; i<Dtime_reserve_teachers_oid.length; i++){
			
			if(Dtime_reserve_teachers_oid[i].equals("")){
				//新增
				if(!techids[i].equals("") && !thours[i].equals("")){
					//建立
					if(!manager.checkDtimeReserve(dtime.getOid().toString(), techids[i], dtime.getYear().toString(), 
						dtime.getTerm(), Float.parseFloat(thours[i]), dtime.getCredit(), dtime.getSelectLimit(), dtime.getAdditionType())){
						
						error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", 
						manager.ezGetString("SELECT cname FROM empl WHERE idno='"+techids[i]+"'")+
						"同仁上限為 "+manager.sumEmplLimit(techids[i])+"時數，"+
						"已規劃 "+manager.sumDtimeReserve(dtime.getOid().toString(), techids[i], dtime.getYear().toString(), dtime.getTerm())+"時數, " +
						"不足以增加本次規劃 "+thours[i]+"時數"
								
						));	
						saveErrors(request, error);			
					}else{
						drt=new DtimeReserveTeacher();						
						drt.setDtimeReserveOid(dtime.getOid());
						drt.setHours(Float.parseFloat(thours[i]));
						drt.setTeachId(techids[i]);
						drt.setFillscore(Byte.parseByte("1"));
						drt.setTeach(Byte.parseByte("1"));
						manager.updateObject(drt);
					}
				}				
			}else{
				if(techids[i].equals("") || thours[i].equals("")){
					//刪除
					manager.executeSql("DELETE FROM Dtime_reserve_teacher WHERE Oid="+Dtime_reserve_teachers_oid[i]);
				}else{
					//更新
					if(!manager.checkDtimeReserve(dtime.getOid().toString(), techids[i], dtime.getYear().toString(), 
							dtime.getTerm(), Float.parseFloat(thours[i]), dtime.getCredit(), dtime.getSelectLimit(), dtime.getAdditionType())){
							//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
							error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", 
							manager.ezGetString("SELECT cname FROM empl WHERE idno='"+techids[i]+"'")+
							"同仁上限為 "+manager.sumEmplLimit(techids[i])+"時數，"+
							"已規劃 "+manager.sumDtimeReserve(dtime.getOid().toString(), techids[i], dtime.getYear().toString(), dtime.getTerm())+"時數, " +
							"不足以增加本次規劃 "+thours[i]+"時數"
									
							));	
							saveErrors(request, error);			
						}else{
							drt=(DtimeReserveTeacher)manager.hqlGetBy("FROM DtimeReserveTeacher WHERE Oid="+Dtime_reserve_teachers_oid[i]).get(0);
							drt.setDtimeReserveOid(dtime.getOid());
							drt.setHours(Float.parseFloat(thours[i]));
							drt.setTeachId(techids[i]);
							manager.updateObject(drt);							
						}
				}
			}
		}
		return unspecified(mapping, form, request, response);
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
		dForm.set("classLess", classLess);
		
		String Oid[]=dForm.getStrings("Oid");		
		map=manager.ezGetMap("SELECT year, term FROM Dtime_reserve_ready WHERE Oid="+Oid[0]);
		dForm.set("year", map.get("year").toString());
		dForm.set("term", map.get("term").toString());
		
		manager.executeSql("DELETE FROM Dtime_reserve_ready WHERE Oid="+Oid[0]);
		//manager.executeSql("DELETE FROM Dtime_reserve_teachers WHERE Dtime_reserve_oid="+Oid[0]);
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","已刪除"));
		saveMessages(request, msg);
		return query(mapping, form, request, response);
	}
	
	/**
	 * 離開
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward leave(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		DynaActionForm dForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");

		String classLess=dForm.getString("classLess");
		Map map=manager.ezGetMap("SELECT * FROM Class WHERE ClassNo='"+classLess+"'");
		
		dForm.set("campusInCharge2", map.get("CampusNo"));
		dForm.set("schoolInCharge2", map.get("SchoolNo"));
		dForm.set("deptInCharge2", map.get("DeptNo"));
		dForm.set("classInCharge2", map.get("ClassNo"));
		return query(mapping, form, request, response);
	}
	
	private Map limit(String year, String term, String depart_class){
		System.out.println(depart_class);
		
		try{
			CourseManager manager = (CourseManager) getBean("courseManager");Clazz c=(Clazz)manager.hqlGetBy("FROM Clazz WHERE ClassNo='"+depart_class+"'").get(0);
			
			
			
			
			
			
			
			
			
			
			
			return manager.ezGetMap("SELECT d.credit, d.thour FROM Dtime_reserve_option d WHERE " +
					"d.year='"+year+"' AND d.term='"+term+"' AND d.dept='"+c.getCampusNo()+
					c.getSchoolNo()+c.getDeptNo()+"' AND d.grade='"+c.getGrade()+"'");
		}catch(Exception e){
			Map map=new HashMap();
			map.put("credit", "0.0f");
			map.put("thour", "0");
			return map;
		}
	}
	
	private float countCredit(String year, String term, String depart_class){
		CourseManager manager = (CourseManager) getBean("courseManager");
		//Clazz c=(Clazz)manager.hqlGetBy("FROM Clazz WHERE ClassNo='"+depart_class+"'").get(0);
		try{
			return Float.parseFloat(manager.ezGetString("SELECT SUM(d.credit) FROM Dtime_reserve_ready d WHERE " +
					"d.year='"+year+"' AND d.term='"+term+"' AND d.depart_class='"+depart_class+"'"));
		}catch(Exception e){
			return 0;
		}
	}
	
	private int countThour(String year, String term, String depart_class){
		CourseManager manager = (CourseManager) getBean("courseManager");
		try{
			return Integer.parseInt(manager.ezGetString("SELECT SUM(d.thour) FROM Dtime_reserve_ready d WHERE " +
					"d.year='"+year+"' AND d.term='"+term+"' AND d.depart_class='"+depart_class+"'"));
		}catch(Exception e){
			return 0;
		}
	}

	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Create", "create");
		map.put("Save", "save");
		map.put("Delete", "delete");
		map.put("Leave", "leave");		
		return map;
	}
}
