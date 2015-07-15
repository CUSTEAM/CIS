package tw.edu.chit.struts.action.deptassist.reserve;

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

import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.model.Csno;
import tw.edu.chit.model.DtimeReserve;
import tw.edu.chit.model.Module;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;

/**
 * 課程規劃
 * @author shawn
 */
public class DtimeReserveManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		if(request.getParameter("Dtime_reserve_oid")!=null){			
			
			if(request.getParameter("Dtime_reserve_oid")!=null){
				//刪除
				Map reserve=manager.ezGetMap("SELECT cscode, depart_class, year, grade FROM Dtime_reserve WHERE Oid="+request.getParameter("Dtime_reserve_oid"));
				manager.executeSql("DELETE FROM Dtime_reserve WHERE Oid="+request.getParameter("Dtime_reserve_oid"));
				DynaActionForm dForm = (DynaActionForm) form;
				Map map=manager.ezGetMap("SELECT * FROM Class WHERE ClassNo LIKE'"+reserve.get("depart_class")+"%' LIMIT 1");
				dForm.set("campusInCharge2", map.get("CampusNo"));
				dForm.set("schoolInCharge2", map.get("SchoolNo"));
				dForm.set("deptInCharge2", map.get("DeptNo"));
				dForm.set("classInCharge2", map.get("ClassNo"));
				dForm.set("classLess", reserve.get("depart_class").toString());
				dForm.set("year", reserve.get("year").toString());
				dForm.set("grade", reserve.get("grade").toString());
				
				ActionMessages msg = new ActionMessages();
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","已刪除"+reserve.get("year")+"年入學之學生的"+reserve.get("grade")+"年級課程"+reserve.get("cscode")));
				saveMessages(request, msg);
				return query(mapping, form, request, response);
			}
			
		}
		setContentPage(request.getSession(false), "assistant/DtimeReserve.jsp");		
		return mapping.findForward("Main");
	}	
	
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		DynaActionForm dForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String year=dForm.getString("year");		
		String cidno=dForm.getString("campusInCharge2");
		String sidno=dForm.getString("schoolInCharge2");
		String didno=dForm.getString("deptInCharge2");
		//String depart_class=dForm.getString("classLess");
		String depart_class=cidno+sidno+didno;
		String grade=dForm.getString("grade");
		
		if(year.trim().equals("")||depart_class.length()!=4){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必需指定入學年及科系"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		List opt1=manager.ezGetBy("SELECT c.chi_name, d.* FROM Dtime_reserve d, Csno c WHERE c.cscode=d.cscode " +
		"AND d.depart_class='"+depart_class+"' AND grade='" +grade+"' AND "+
		"year='"+year+"' ORDER BY term, opt");
				
		request.setAttribute("opt1", opt1);
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
	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		DynaActionForm dForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String year=dForm.getString("year");
		String grade=dForm.getString("grade");
		//String classLess=dForm.getString("classLess");
		String terms[]=dForm.getStrings("terms");
		String cscode[]=dForm.getStrings("cscode");
		String opt[]=dForm.getStrings("opt");
		String credit[]=dForm.getStrings("credit");
		String thour[]=dForm.getStrings("thour");
		String cyber[]=dForm.getStrings("cyber");
		String additionType[]=dForm.getStrings("additionType");
		
		String cidno=dForm.getString("campusInCharge2");
		String sidno=dForm.getString("schoolInCharge2");
		String didno=dForm.getString("deptInCharge2");
		String classLess=cidno+sidno+didno;
		
		ActionMessages error = new ActionMessages();
		if(year.trim().equals("")){			
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必需指定入學年及科系"));			
		}
		
		//自重建立課程代碼
		if(cscode[0].trim().equals("")){
			/*
			String chi_name[]=dForm.getStrings("chi_name");
			if(chi_name[0].trim().equals("")){
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必需指定課程名稱"));
			}else{
				String code;
				try{
					code=manager.ezGetString("SELECT cscode FROM Csno c WHERE c.cscode LIKE 'H%' ORDER BY cscode DESC LIMIT 1");
					int tmp=Integer.parseInt(code.substring(1, code.length()));
					tmp=tmp+1;//找到第n個並+1
					code=String.valueOf(tmp);//幾位數?					
					tmp=code.length();
					for(int i=4; i>tmp; i--){
						System.out.println(i);
						code="0"+code;
					}
					code="H"+code;
				}catch(Exception e){
					//TODO 下1個英文字
					code="H0001";
				}
				Csno cs=new Csno();
				cs.setChiName(chi_name[0]);
				cs.setCscode(code);
				if(manager.ezGetInt("SELECT COUNT(*)FROM Csno WHERE chi_name='"+chi_name[0].trim()+"'")>0){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "課程名稱已存在"));
				}else{
					manager.updateObject(cs);//建立課程代碼
					cscode[0]=code;
				}
			}
			*/
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必需指定已存在的課程代碼"));	
		}
		
		if(terms[0].trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必需指定學期"));			
		}
		
		if(credit[0].trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必需指定學分"));			
		}
		
		if(thour[0].trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必需指定時數"));			
		}
		
		if(manager.ezGetInt("SELECT COUNT(*)FROM Dtime_reserve_option WHERE year='"+year+"' AND dept='"+classLess+"' AND grade='"+grade+"' AND term='"+terms[0]+"' AND open='0'")>0){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "已關閉"+year+"年度第"+terms[0]+"學期課程規劃"));
		}
		
		if(!error.isEmpty()){
			saveErrors(request, error);
			return query(mapping, form, request, response);
		}
		
		try{
			DtimeReserve dtime=new DtimeReserve();
			dtime.setAdditionType(additionType[0]);
			
			if(!credit[0].trim().equals("")){
				dtime.setCredit(Float.parseFloat(credit[0]));
			}
			dtime.setCscode(cscode[0]);
			dtime.setCyber(cyber[0]);
			dtime.setDepartClass(classLess);
			dtime.setOpt(opt[0]);		
			dtime.setTerm(terms[0]);
			dtime.setGrade(grade);
			if(!thour[0].trim().equals("")){
				dtime.setThour(Short.parseShort(thour[0]));
			}		
			dtime.setYear(Integer.parseInt(year));
			manager.updateObject(dtime);
		}catch(Exception e){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "儲存失敗，請檢查是否重複開課"));	
			saveErrors(request, error);
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
		//HttpSession session = request.getSession(false);
		DynaActionForm dForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String year=dForm.getString("year");
		String grade=dForm.getString("grade");
		//String classLess=dForm.getString("classLess");
		
		String cidno=dForm.getString("campusInCharge2");
		String sidno=dForm.getString("schoolInCharge2");
		String didno=dForm.getString("deptInCharge2");
		String classLess=cidno+sidno+didno;
		
		String Oid[]=dForm.getStrings("Oid");		
		String terms[]=dForm.getStrings("terms");		
		String cscode[]=dForm.getStrings("cscode");
		String opt[]=dForm.getStrings("opt");
		String credit[]=dForm.getStrings("credit");
		String thour[]=dForm.getStrings("thour");
		String cyber[]=dForm.getStrings("cyber");
		String additionType[]=dForm.getStrings("additionType");		
		//System.out.println(classLess);
		
		for(int i=1; i<Oid.length; i++){			
			//檢查是否被關閉						
			if(terms[i].equals("")||opt[i].equals("")||cscode[i].trim().equals("")||credit[i].trim().equals("")){
				manager.executeSql("DELETE FROM Dtime_reserve WHERE Oid="+Oid[i]);
			}else{				
				
				try{					
					DtimeReserve dtime=(DtimeReserve) manager.hqlGetBy("FROM DtimeReserve WHERE Oid="+Oid[i]).get(0);
					dtime.setAdditionType(additionType[i]);
					if(!credit[i].trim().equals("")){
						dtime.setCredit(Float.parseFloat(credit[i]));
					}
					dtime.setCscode(cscode[i]);
					dtime.setCyber(cyber[i]);
					dtime.setOpt(opt[i]);					
					
					dtime.setTerm(terms[i]);				
					if(!thour[i].trim().equals("")){
						try{
							dtime.setThour(Short.parseShort(thour[i]));
						}catch(Exception e){
							dtime.setThour(Short.parseShort("0"));
						}
					}
					
					//檢查時數規劃
					if(manager.ezGetInt("SELECT COUNT(*)FROM Dtime_reserve_option WHERE " +
					"year='"+year+"' AND dept='"+classLess+"' AND grade='"+grade+"' AND term='"+terms[i]+"' AND open='0'")>0){					
						ActionMessages msg = new ActionMessages();
						msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "部分課程未套用新設定，請返回主選單「時數規劃」檢查該學期是否開放修改"));
						saveMessages(request, msg);					
					}else{
						manager.updateObject(dtime);
					}
					
				}catch(Exception e){
					ActionMessages error = new ActionMessages();
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "儲存失敗，請檢查重複"));	
					saveErrors(request, error);
				}				
			}
		}
		
		return query(mapping, form, request, response);
	}
	
		
	/**
	 * xxx
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
		dForm.set("year", dForm.get("year"));
		String Oid=dForm.getString("Oid");
		manager.executeSql("DELETE FROM Dtime_reserve WHERE Oid="+Oid);
		manager.executeSql("DELETE FROM Dtime_reserve_teachers WHERE Dtime_reserve_oid="+Oid);
		//manager.executeSql("DELETE FROM Dtime_reserve WHERE Oid='"+Oid);
		return query(mapping, form, request, response);
	}
	
	public ActionForward copy(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {		
		DynaActionForm dForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");		
		String year=dForm.getString("year");
		String grade=dForm.getString("grade");
		//String classLess=dForm.getString("classLess");		
		String cidno=dForm.getString("campusInCharge2");
		String sidno=dForm.getString("schoolInCharge2");
		String didno=dForm.getString("deptInCharge2");
		String classLess=cidno+sidno+didno;
		try{
			int tmp=Integer.parseInt(year)-1;	
			List list=manager.hqlGetBy("FROM DtimeReserve WHERE year='"+tmp+"' AND depart_class='"+classLess+"' AND grade='"+grade+"'");
			//System.out.println("FROM DtimeReserve WHERE d.year='"+tmp+"' AND d.depart_class='"+classLess+"' AND d.grade='"+grade+"'");
			for(int i=0; i<list.size(); i++){
				
				DtimeReserve d=new DtimeReserve();
				d.setAdditionType(((DtimeReserve)list.get(i)).getAdditionType());
				d.setCredit(((DtimeReserve)list.get(i)).getCredit());
				d.setCscode(((DtimeReserve)list.get(i)).getCscode());
				d.setCyber(((DtimeReserve)list.get(i)).getCyber());
				d.setDepartClass(((DtimeReserve)list.get(i)).getDepartClass());
				d.setGrade(((DtimeReserve)list.get(i)).getGrade());
				d.setOpt(((DtimeReserve)list.get(i)).getOpt());
				d.setTerm(((DtimeReserve)list.get(i)).getTerm());
				d.setThour(((DtimeReserve)list.get(i)).getThour());
				d.setYear(((DtimeReserve)list.get(i)).getYear()+1);
				
				manager.updateObject(d);
			}			
			
		}catch(Exception e){
			e.printStackTrace();
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "儲存失敗，請檢查重複"));	
			saveErrors(request, error);
		}
		
		
		
		
		return query(mapping, form, request, response);
	}

	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Create", "create");
		map.put("Save", "save");
		map.put("Delete", "delete");
		map.put("CopyLastYear", "copy");
		
		return map;
	}
}
