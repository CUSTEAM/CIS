package tw.edu.chit.struts.action.AMS.shift;

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

import tw.edu.chit.model.AmsPersonalVacation;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class EmplHolidayManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		session.setAttribute("allUnit", manager.ezGetBy("SELECT idno, name FROM CodeEmpl WHERE (category='Unit' OR category='UnitTeach') ORDER BY sequence"));
		session.setAttribute("allShift", manager.ezGetBy("SELECT id, name FROM AMS_ShiftTime GROUP BY id"));
		
		
		session.setAttribute("DirectoryPath", manager.ezGetString("SELECT Action FROM Module m WHERE m.Oid=(SELECT ParentOid FROM Module WHERE Action='/AMS/EmplHolidayManager.do')"));
		setContentPage(request.getSession(false), "AMS/shift/EmplHolidayManager.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward addHoliday(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		DynaActionForm eForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		//HttpSession session = request.getSession(false);
		String sid=eForm.getString("sid");
		String sidno=eForm.getString("sidno");
		String sunit=eForm.getString("sunit");
		
		String AdateStart=eForm.getString("AdateStart");
		String AdateEnd=eForm.getString("AdateEnd");
		
		StringBuilder sb=new StringBuilder("SELECT * FROM empl WHERE WorkShift ='"+sid+"' AND "+
				"idno LIKE'"+sidno+"%' AND unit LIKE '"+sunit+"%' AND WorkShift IS NOT NULL AND WorkShift !='' ");
		if(sid.equals("")){
			sb=new StringBuilder("SELECT * FROM empl WHERE WorkShift LIKE'%' AND "+
					"idno LIKE'"+sidno+"%' AND unit LIKE '"+sunit+"%' AND WorkShift IS NOT NULL AND WorkShift !='' ");
		}
		
		if(!AdateEnd.trim().equals("")){
			AdateEnd=manager.convertDate(AdateEnd);
			sb.append("AND Adate<='"+AdateEnd+"'");
		}		
		if(!AdateStart.trim().equals("")){
			AdateStart=manager.convertDate(AdateStart);
			sb.append("AND Adate>='"+AdateStart+"'");
		}
		sb.append("ORDER BY Adate");		
		List hempl=manager.ezGetBy(sb.toString());
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date d1;
		Date d2=new Date();
		int year;
		
		//自動給予建議值
		for(int i=0; i<hempl.size(); i++){			
			if(((Map)hempl.get(i)).get("Adate")!=null && !((Map)hempl.get(i)).get("Adate").equals("")){
				
				d1=sf.parse(((Map)hempl.get(i)).get("Adate").toString());
				year=(int) (((d2.getTime()-d1.getTime())/(1000*60*60*24))/365);				
				((Map)hempl.get(i)).put("Adate", year+"年"+((d2.getTime()-d1.getTime())/(1000*60*60*24))%365+"天");	
				//判斷雇員
				if(((Map)hempl.get(i)).get("sname")!=null && !((Map)hempl.get(i)).get("sname").equals("")){					
					if(((Map)hempl.get(i)).get("sname").toString().indexOf("雇")>=0){
						if(year>=1){
							((Map)hempl.get(i)).put("year", 3);
						}else{
							((Map)hempl.get(i)).put("year", 0);
						}
						continue;
					}					
				}				
				
				//再來都是正式職員
				if(year<1){
					((Map)hempl.get(i)).put("year", 0);
				}
				if(year>=1&& year<=5){
					((Map)hempl.get(i)).put("year", 7);
				}
				if(year>5){
					((Map)hempl.get(i)).put("year", 14);
				}				
			}			
		}	
		
		
		
		request.setAttribute("hempl", hempl);		
		return mapping.findForward("Main");

	}
	
	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		DynaActionForm eForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String idnos[]=eForm.getStrings("idnos");
		String vTypes[]=eForm.getStrings("vTypes");
		String vYears[]=eForm.getStrings("vYears");
		String dayss[]=eForm.getStrings("dayss");
		String validFroms[]=eForm.getStrings("validFroms");
		String validTos[]=eForm.getStrings("validTos");
		
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息		
		AmsPersonalVacation ap;
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		for(int i=1; i<idnos.length; i++){
			
			if(!vTypes[i].trim().equals("")&&
				!vYears[i].trim().equals("")&&
				!dayss[i].trim().equals("")&&
				!validFroms[i].trim().equals("")&&
				!validTos[i].trim().equals("")){
				//continue;
				ap=new AmsPersonalVacation();
				ap.setDays(Float.parseFloat(dayss[i]));
				ap.setIdno(idnos[i]);
				ap.setRemain(Float.parseFloat(dayss[i]));
				ap.setValidFrom(sf.parse(manager.convertDate(validFroms[i])));
				ap.setVtype(vTypes[i]);
				ap.setVyear(Integer.parseInt(vYears[i]));
				ap.setValidTo(sf.parse(manager.convertDate(validTos[i])));
				
				try{
					manager.updateObject(ap);
				}catch(Exception e){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", ap.getIdno()+", "+ap.getVyear()+"年"+ap.getDays()+"天假期建立失敗!<br>"));					
				}
				
				if(!error.isEmpty()){
					saveErrors(request, error);
				}				
			}
		}		
		
		setContentPage(request.getSession(false), "AMS/shift/EmplHolidayManager.jsp");
		return mapping.findForward("Main");

	}
	
	/**
	 * 準備修改
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modifyHoilday(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		DynaActionForm eForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String sid=eForm.getString("sid");
		String sidno=eForm.getString("sidno");
		String sunit=eForm.getString("sunit");
		String vType=eForm.getString("vType");
		
		String AdateStart=eForm.getString("AdateStart");
		String AdateEnd=eForm.getString("AdateEnd");		
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		String now=sf.format(new Date());		
		
		StringBuilder sb=new StringBuilder("SELECT * FROM empl e, AMS_PersonalVacation p WHERE vType LIKE'"+vType+"%' AND p.idno=e.idno AND e.WorkShift ='"+sid+"' AND "+
				"e.idno LIKE'"+sidno+"%' AND e.unit LIKE '"+sunit+"%' AND e.WorkShift IS NOT NULL AND e.WorkShift !='' AND p.validTo>='"+now+"' ");
		
		if(sid.equals("")){
			sb=new StringBuilder("SELECT * FROM empl e, AMS_PersonalVacation p WHERE vType LIKE'"+vType+"%' AND p.idno=e.idno AND e.WorkShift LIKE'%' AND "+
					"e.idno LIKE'"+sidno+"%' AND e.unit LIKE '"+sunit+"%' AND e.WorkShift IS NOT NULL AND e.WorkShift !='' AND p.validTo>='"+now+"' ");
		}
		
		if(!AdateEnd.trim().equals("")){
			AdateEnd=manager.convertDate(AdateEnd);
			sb.append("AND e.Adate<='"+AdateEnd+"'");
		}		
		if(!AdateStart.trim().equals("")){
			AdateStart=manager.convertDate(AdateStart);
			sb.append("AND e.Adate>='"+AdateStart+"'");
		}
		
		sb.append("ORDER BY e.Adate, e.idno, p.vYear, p.vType");		
		//System.out.println(sb);
		List hempl=manager.ezGetBy(sb.toString());
		Date d1;
		Date d2=new Date();
		
		for(int i=0; i<hempl.size(); i++){
			
			if(((Map)hempl.get(i)).get("Adate")!=null && !((Map)hempl.get(i)).get("Adate").equals("")){
				d1=sf.parse(((Map)hempl.get(i)).get("Adate").toString());				
				((Map)hempl.get(i)).put("Adate", ((d2.getTime()-d1.getTime())/(1000*60*60*24))/365+"年"+((d2.getTime()-d1.getTime())/(1000*60*60*24))%365+"天");				
			}	
			
			((Map)hempl.get(i)).put("validFrom", manager.convertDate(((Map)hempl.get(i)).get("validFrom").toString()));
			((Map)hempl.get(i)).put("validTo", manager.convertDate(((Map)hempl.get(i)).get("validTo").toString()));			
			
		}		
		request.setAttribute("mempl", hempl);		
		return mapping.findForward("Main");

	}
	
	/**
	 * 確定修改
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modify(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		DynaActionForm eForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String idnos[]=eForm.getStrings("idnos");
		String vTypes[]=eForm.getStrings("vTypes");
		String vYears[]=eForm.getStrings("vYears");
		String dayss[]=eForm.getStrings("dayss");
		String validFroms[]=eForm.getStrings("validFroms");
		String validTos[]=eForm.getStrings("validTos");
		String Oid[]=eForm.getStrings("Oid");
		
		AmsPersonalVacation ap;
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");		
		
		float oremain;//原推導已休日數
		
		for(int i=1; i<idnos.length; i++){			
			
			if(!vTypes[i].trim().equals("")&&
				!vYears[i].trim().equals("")&&
				!dayss[i].trim().equals("")&&
				!validFroms[i].trim().equals("")&&
				!validTos[i].trim().equals("")){
				//continue;
				ap=(AmsPersonalVacation) manager.hqlGetBy("FROM AmsPersonalVacation WHERE Oid='"+Oid[i]+"'").get(0);				
				oremain=ap.getDays()-ap.getRemain();//原推導餘日數				
				ap.setDays(Float.parseFloat(dayss[i]));
				ap.setIdno(idnos[i]);
				ap.setRemain(Float.parseFloat(dayss[i]));
				ap.setValidFrom(sf.parse(manager.convertDate(validFroms[i])));
				ap.setVtype(vTypes[i]);
				ap.setVyear(Integer.parseInt(vYears[i]));
				ap.setValidTo(sf.parse(manager.convertDate(validTos[i])));
				ap.setRemain(ap.getDays()-oremain);//重新計算已休日數
				
				manager.updateObject(ap);
			}else{
				manager.executeSql("DELETE FROM AMS_PersonalVacation WHERE Oid='"+Oid[i]+"'");				
			}
		}
		
		return modifyHoilday(mapping, form, request, response);
	}
	
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("AddHoliday", "addHoliday");
		map.put("ModifyHoilday", "modifyHoilday");
		map.put("Create", "create");
		map.put("Modify", "modify");		
		return map;
	}
}
