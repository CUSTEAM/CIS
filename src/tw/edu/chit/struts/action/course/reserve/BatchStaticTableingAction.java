package tw.edu.chit.struts.action.course.reserve;

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

import tw.edu.chit.model.CsCore;
import tw.edu.chit.model.DtimeReserveClassFixed;
import tw.edu.chit.model.DtimeReserveReady;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Global;

/**
 * 
 * @author JOHN
 *
 */
public class BatchStaticTableingAction extends BaseLookupDispatchAction {
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response)throws Exception {		
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		request.setAttribute("allRules", manager.ezGetBy("SELECT d.*, c.chi_name FROM Dtime_reserve_class_fixed d, Csno c WHERE c.cscode=d.cscode ORDER BY Oid DESC"));		
		request.setAttribute("AllCampus", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Campus'"));
		request.setAttribute("AllSchool", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='School'"));
		request.setAttribute("AllDept", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Dept'"));
		request.setAttribute("AllSchoolType", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Stype'"));
		
		setContentPage(request.getSession(false), "course/BatchStaticTableing.jsp");
		return mapping.findForward("Main");
	}	
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm dForm = (DynaActionForm) form;
		
		String Cidno[]=dForm.getStrings("Cidno");
		String Sidno[]=dForm.getStrings("Sidno");
		String Didno[]=dForm.getStrings("Didno");
		String Grade[]=dForm.getStrings("Grade");
		String ClassNo[]=dForm.getStrings("ClassNo");
		
		//String Oid[]=dForm.getStrings("Oid");
		String begin[]=dForm.getStrings("begin");
		String end[]=dForm.getStrings("end");
		String week[]=dForm.getStrings("week");
		String cscode[]=dForm.getStrings("cscode");
		
		if(cscode[0].equals("")){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "課程不可空白"));
			saveErrors(request, error);
			return unspecified(mapping, form, request, response);
		}
		
		DtimeReserveClassFixed d=new DtimeReserveClassFixed();
		d.setBegin(begin[0]);
		d.setCidno(Cidno[0]);
		d.setClassNo(ClassNo[0]);
		d.setCscode(cscode[0]);
		d.setDidno(Didno[0]);
		d.setEnd(end[0]);
		d.setGrade(Grade[0]);
		d.setSidno(Sidno[0]);
		d.setWeek(week[0]);
		manager.updateObject(d);		
		return unspecified(mapping, form, request, response);
	}
	
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm dForm = (DynaActionForm) form;
		
		String Cidno[]=dForm.getStrings("Cidno");
		String Sidno[]=dForm.getStrings("Sidno");
		String Didno[]=dForm.getStrings("Didno");
		String Grade[]=dForm.getStrings("Grade");
		String ClassNo[]=dForm.getStrings("ClassNo");
		
		String Oid[]=dForm.getStrings("Oid");
		String begin[]=dForm.getStrings("begin");
		String end[]=dForm.getStrings("end");
		String week[]=dForm.getStrings("week");
		String cscode[]=dForm.getStrings("cscode");
		
		DtimeReserveClassFixed d;
		for(int i=1; i<Oid.length; i++){
			d=(DtimeReserveClassFixed)manager.hqlGetBy("FROM DtimeReserveClassFixed WHERE Oid="+Oid[i]).get(0);
			d.setBegin(begin[i]);
			d.setCidno(Cidno[i]);
			d.setClassNo(ClassNo[i]);
			d.setCscode(cscode[i]);
			d.setDidno(Didno[i]);
			d.setEnd(end[i]);
			d.setGrade(Grade[i]);
			d.setSidno(Sidno[i]);
			d.setWeek(week[i]);			
			manager.updateObject(d);
		}
		
		return unspecified(mapping, form, request, response);
	}
	
	public ActionForward confirmReTableing(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {	
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm dForm = (DynaActionForm) form;
		
		String year=dForm.getString("year");
		String term=dForm.getString("term");
		
		String Cidno[]=dForm.getStrings("Cidno");
		String Sidno[]=dForm.getStrings("Sidno");
		String Didno[]=dForm.getStrings("Didno");
		String Grade[]=dForm.getStrings("Grade");
		String ClassNo[]=dForm.getStrings("ClassNo");
		
		String Oid[]=dForm.getStrings("Oid");
		String begin[]=dForm.getStrings("begin");
		String end[]=dForm.getStrings("end");
		String week[]=dForm.getStrings("week");
		String cscode[]=dForm.getStrings("cscode");
		
		DtimeReserveClassFixed drcf;
		DtimeReserveReady dt;
		
		/*不可行
		List area;
		for(int i=1; i<Oid.length; i++){
			drcf=(DtimeReserveClassFixed)manager.hqlGetBy("FROM DtimeReserveClassFixed WHERE Oid="+Oid[i]).get(0);
			
			
			area=manager.ezGetBy("SELECT COUNT(*)FROM Dtime_reserve_ready d WHERE " +
			"d.year='"+year+"' AND d.term='"+term+"' AND d.depart_class LIKE'"+
			drcf.getCidno()+drcf.getSidno()+drcf.getDidno()+drcf.getClassNo()+"%' AND d.cscode='"+drcf.getCscode()+"'");
			
			
			for(int j=0; j<area.size(); j++){

				//是否已開課
				if(manager.ezGetInt("SELECT COUNT(*)FROM Dtime_reserve_ready d WHERE " +
						"d.year='"+year+"' AND d.term='"+term+"' AND " +
						"d.depart_class='"+((Map)area.get(j)).get("depart_class")+"' " +
						"AND d.cscode='"+((Map)area.get(j)).get("cscode")+"'")<1){
					//幫忙建
					dt=new DtimeReserveReady();
					dt.setCscode(((Map)area.get(j)).get("cscode").toString());
					dt.setCyber("0");
					dt.setYear(Integer.parseInt(year));
					dt.setTerm(term);
					dt.setOpt("1");
					dt.setSelectLimit(Short.valueOf("0"));
					dt.setDepartClass(((Map)area.get(j)).get("depart_class").toString());
					
				}
				
				
				
				
				
			}
			
			
			
			
			
			
		}
		*/
		
		return unspecified(mapping, form, request, response);
	}
	
	

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Save", "save");
		map.put("Modify", "modify");
		map.put("ConfirmReTableing", "confirmReTableing");		
		return map;
	}
}
