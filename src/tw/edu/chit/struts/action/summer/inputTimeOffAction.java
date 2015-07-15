package tw.edu.chit.struts.action.summer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

import tw.edu.chit.model.Sdilg;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.SummerManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class inputTimeOffAction extends BaseLookupDispatchAction{

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(false);
		SummerManager summerManager = (SummerManager) getBean("summerManager");
		session.setAttribute("welcome", true);
		session.setAttribute("mode", "");
		
		session.setAttribute("sweek", summerManager.getSsterm());

		setContentPage(request.getSession(false), "summer/inputTimeOff.jsp");
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
		SummerManager summerManager = (SummerManager) getBean("summerManager");
		session.setAttribute("mode", "search"); //模式
		session.setAttribute("welcome", true);

		String seqno = (String) sform.get("seqno");
		String studentNo = (String) sform.get("studentId");
		String studentName = (String) sform.get("studentName");
		String cscode = (String) sform.get("courseNumber");
		String courseName = (String) sform.get("courseName");
		String departClass = (String) sform.get("departClass");
		String classLess = (String) sform.get("classLess");
		String ddate = (String) sform.get("ddate");
		String daynite = (String) sform.get("daynite");
		String stDepartClass = (String) sform.get("stDepartClass");
		String stClassLess = (String) sform.get("stClassLess");

		session.setAttribute("sdilgs", summerManager.getSdilgBy(seqno, departClass, stDepartClass, studentNo, ddate, daynite, cscode));		
		CourseManager manager = (CourseManager) getBean("courseManager");
		session.setAttribute("titleInfo", manager.ezGetMap("SELECT c.chi_name, a.name FROM Sdtime d, Sabbr a, Csno c WHERE " +
				"d.depart_class=a.no AND d.cscode=c.cscode AND d.depart_class='"+departClass+"' AND d.cscode='"+cscode+"'"));
		
		
		setContentPage(request.getSession(false), "summer/inputTimeOff.jsp");
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
		SummerManager summerManager = (SummerManager) getBean("summerManager");
		
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		
		String seqno = (String) sform.get("seqno");
		String studentNo = (String) sform.get("studentId");
		String studentName = (String) sform.get("studentName");
		String cscode = (String) sform.get("courseNumber");
		String courseName = (String) sform.get("courseName");
		String departClass = (String) sform.get("departClass");
		String classLess = (String) sform.get("classLess");
		String ddate = (String) sform.get("ddate");
		String daynite = (String) sform.get("daynite");
		String stDepartClass = (String) sform.get("stDepartClass");
		String stClassLess = (String) sform.get("stClassLess");		
		
		if(seqno.trim().equals("")||departClass.trim().equals("")||courseName.equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1"," 所有欄位不得空白"));
			saveErrors(request, error);
		}
		
		if(!seqno.trim().equals("")&&!courseName.trim().equals("")&&!classLess.trim().equals("")){
			if(summerManager.ezGetInt("SELECT COUNT(*) FROM Sdtime WHERE depart_class='"+departClass+"' AND cscode='"+cscode+"' AND seqno='"+seqno+"'")<1){
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","第"+seqno+"梯次, 沒有在"+classLess+" 開"+courseName));
				saveErrors(request, error);
			}
		}
		
		if(!error.isEmpty()){
			return mapping.findForward("Main");
		}	
		
		DateFormat df=new SimpleDateFormat("yyyy/MM/dd");
		String ddate1=df.format(Toolket.parseDate(ddate));
		
		session.setAttribute("ddate", ddate1);
		session.setAttribute("sdilgs", summerManager.ezGetList("SELECT d.seqno, sd.Oid, s.student_no, st.student_name, sa.name, c.chi_name, sd.abs1, sd.abs2, sd.abs3, sd.abs4, "+
				"sd.abs5, sd.abs6, sd.abs7, sd.abs8, sd.abs9, sd.abs10, sd.abs11, d.depart_class, d.cscode  FROM Sseld s LEFT OUTER JOIN Sdilg sd ON s.student_no=sd.student_no AND sd.ddate " +
				"='"+ddate1+"', Sdtime d, stmd st, Sabbr sa, Csno c WHERE c.cscode=d.cscode AND sa.no=d.depart_class AND st.student_no=s.student_no AND " +
				"s.cscode=d.cscode AND s.csdepart_class=d.depart_class AND d.depart_class='"+departClass+"' AND d.cscode='"+cscode+"' AND d.seqno='"+seqno+"' ORDER BY s.student_no"));
		
		session.setAttribute("mode", "create");//模式
		session.setAttribute("welcome", false);
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		session.setAttribute("titleInfo", manager.ezGetMap("SELECT c.chi_name, a.name FROM Sdtime d, Sabbr a, Csno c WHERE " +
				"d.depart_class=a.no AND d.cscode=c.cscode AND d.depart_class='"+departClass+"' AND d.cscode='"+cscode+"'"));
		
		setContentPage(request.getSession(false), "summer/inputTimeOff.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 儲存
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		DynaActionForm sform = (DynaActionForm) form;
		SummerManager summerManager = (SummerManager) getBean("summerManager");
		DateFormat df=new SimpleDateFormat("yyyy/MM/dd");
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		
		String wseqno[] = (String[]) sform.get("wseqno");
		String wddate[] = (String[]) sform.get("wddate");
		String wdepartClass[] = (String[]) sform.get("wdepartClass");
		String wcscode[] = (String[]) sform.get("wcscode");
		String studentNo[] = (String[]) sform.get("studentNo");
		String abs1[] = (String[]) sform.get("abs1");
		String abs2[] = (String[]) sform.get("abs2");
		String abs3[] = (String[]) sform.get("abs3");
		String abs4[] = (String[]) sform.get("abs4");
		String abs5[] = (String[]) sform.get("abs5");
		String abs6[] = (String[]) sform.get("abs6");
		String abs7[] = (String[]) sform.get("abs7");
		String abs8[] = (String[]) sform.get("abs8");
		String abs9[] = (String[]) sform.get("abs9");
		String abs10[] = (String[]) sform.get("abs10");
		String abs11[] = (String[]) sform.get("abs11");
		String Oid[] = (String[]) sform.get("Oid");
		
		//移除已存在的
		for(int i=0; i<Oid.length; i++){
			if(!Oid[i].trim().equals("")){
				summerManager.removeObject("Sdilg", Oid[i]);
			}
		}
		
		//存入新的
		Sdilg sdilg;
		try{
			for(int i=0; i<studentNo.length; i++){
				
				//哪一國爛招? (懶招)
				if(!abs1[i].trim().equals("")||!abs2[i].trim().equals("")||!abs3[i].trim().equals("")||!abs4[i].trim().equals("")||
						!abs5[i].trim().equals("")||!abs6[i].trim().equals("")||!abs7[i].trim().equals("")||!abs8[i].trim().equals("")||
						!abs9[i].trim().equals("")||!abs10[i].trim().equals("")||!abs11[i].trim().equals("")){
					sdilg=new Sdilg();
					sdilg.setAbs1(abs1[i]);
					sdilg.setAbs2(abs2[i]);
					sdilg.setAbs3(abs3[i]);
					sdilg.setAbs4(abs4[i]);
					sdilg.setAbs5(abs5[i]);
					sdilg.setAbs6(abs6[i]);
					sdilg.setAbs7(abs7[i]);
					sdilg.setAbs8(abs8[i]);
					sdilg.setAbs9(abs9[i]);
					sdilg.setAbs10(abs10[i]);
					sdilg.setAbs11(abs11[i]);
					
					sdilg.setDaynite("");
					sdilg.setDdate(df.parse(wddate[i]));
					sdilg.setDepartClass(wdepartClass[i]);
					sdilg.setSeqno(Short.parseShort(wseqno[i]));
					sdilg.setStudentNo(studentNo[i]);
					Calendar cal=Calendar.getInstance();
					cal.setTime(df.parse(wddate[i]));
					//sdilg.setWeekDay(cal.get(Calendar.DAY_OF_WEEK));
					//sdilg.setWeekNo(cal.get(Calendar.WEEK_OF_YEAR));
					summerManager.saveObj(sdilg);
				}
			}
			
		}catch(Exception e){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","儲存失敗, 請重新輸入"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","儲存完成"));
		saveMessages(request, msg);
		session.setAttribute("mode", ""); //模式
		session.setAttribute("welcome", true);
		setContentPage(request.getSession(false), "summer/inputTimeOff.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 重設
	 */
	public ActionForward clear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		setContentPage(request.getSession(false), "summer/inputTimeOff.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 取消
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		session.setAttribute("mode", "");//模式
		session.setAttribute("welcome", true);
		
		
		setContentPage(request.getSession(false), "summer/inputTimeOff.jsp");
		return mapping.findForward("Main");
	}
	
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("Query", "list");
		map.put("Create", "create");
		map.put("Clear", "clear");
		map.put("Save", "save");
		map.put("Cancel", "cancel");
		return map;
	}

}
