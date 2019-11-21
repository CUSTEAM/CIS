package tw.edu.chit.struts.action.course;

import java.io.PrintWriter;
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

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.struts.action.course.checkOut.ListCounsling;
import tw.edu.chit.util.Toolket;

public class CheckOutAction extends BaseLookupDispatchAction{

	public ActionForward unspecified(ActionMapping mapping,
			 					 ActionForm form,
			 					 HttpServletRequest request,
			 					 HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		boolean editMode=false;
		session.setAttribute("editMode", editMode);
		setContentPage(request.getSession(false), "course/CheckOut.jsp");
		return mapping.findForward("Main");

	}

	public ActionForward courseCheckOpt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm setUpDtime = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, "dtimes");
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		ActionMessages msg = new ActionMessages();

		CourseManager manager = (CourseManager) getBean("courseManager");

		String classLess = (String) setUpDtime.get("classLess"); // area
		//String className = (String) setUpDtime.get("className"); // area
		String sterm = (String) setUpDtime.get("sterm"); // area
		String checkOpt = (String) setUpDtime.get("checkOpt"); // area


		if(checkOpt.equals("none")){

			if(!error.isEmpty()){
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "<br>&nbsp;"));
			}

			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "查詢條件或選項空白"));
			saveErrors(request, error);
		}

		if(!error.isEmpty()){
			return mapping.findForward("Main");
		}		
		
		// 學生選課查核
		if(checkOpt.equals("CheckSeld")){
			/*
			if(classLess.length()<3){
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "指定範圍過大<b>會影響查核效率</b>, 至少指定一種<b>學制</b>, 謝謝!"));
				saveErrors(request, error);
				return mapping.findForward("Main");
			}
			*/
			
			List checkReSelds=manager.checkReSeld(classLess, sterm);
			session.setAttribute("checkReSelds", checkReSelds);
			if(checkReSelds.size()>1){
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", checkReSelds.size()/2+"人次 共"+checkReSelds.size()+"筆選課有問題"));
			}else{
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "沒有學生衝堂"));
			}
			saveMessages(request, msg);

		}

		// 大綱簡介列表
		if(checkOpt.equals("CheckGist")){
			String schoolType = (String) setUpDtime.get("schoolType"); // area			
			/*
			if(classLess.length()<4){
				//至少要選一個校區
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "至少要選一個科系"));
				saveErrors(request, error);
				return mapping.findForward("Main");
			}
			*/
			session.setAttribute("CheckGist", manager.getCheckGist(classLess, sterm));
			String classLesses = null;			
			
			if(classLess.length()<6){				
				classLesses=classLess+"*";
			}else{				
				classLesses=classLess;
			}
			if(schoolType.equals("")){
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "班級代碼 "+classLesses+" 第 "+sterm+" 學期, 查核完成!"));
				saveMessages(request, msg);
			}else{
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "第 "+sterm+" 學期, 查核完成!"));
				saveMessages(request, msg);
			}
		}

		// 學分數查核
		if(checkOpt.equals("CheckCredit")){

			if(classLess.length()<1){
				//至少要選一個校區
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "至少要選一個校區"));
				saveErrors(request, error);
				return mapping.findForward("Main");
			}

			//session.setAttribute("CheckOpt", "CheckCredit");
			String minimum = (String) setUpDtime.get("checkCredit"); // area
			if(minimum.equals("")){
				minimum="999";
			}
			List checkCredit=manager.getCheckCredit(classLess, sterm, minimum);
			session.setAttribute("CheckCredit", checkCredit);
			
			
			
			
			
			
			if(checkCredit.size()>0){
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "共有"+checkCredit.size()+"個學生學分異常"));
			}else{
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "沒有人"));
			}

			saveMessages(request, msg);
		}

		//人數上限查核
		if(checkOpt.equals("CheckSelimit")){
			if(classLess.length()<2){
				//至少要選一個學制
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "至少要選一個學制"));
				saveErrors(request, error);
				return mapping.findForward("Main");
			}

			//session.setAttribute("CheckOpt", "CheckSelimit");

			List CheckSelimitList=manager.getOverSelect(classLess, sterm);
			session.setAttribute("CheckSelimitList", CheckSelimitList);
			if(CheckSelimitList.size()>0){
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "共有"+CheckSelimitList.size()+"門課程被選爆"));
			}else{
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "人數剛好"));
			}

			saveMessages(request, msg);
		}
		
		//教師任課時數
		if(checkOpt.equals("CheckThour")){
			session.setAttribute("CheckOpt", "CheckThour");
			
			List list=manager.ezGetBy("SELECT et.time, et.time_over, c.name, e.sname, e.idno, e.cname, " +
					"SUM(d.credit) as credit, SUM(d.thour) as thour FROM Dtime d, " +
					"(empl e LEFT OUTER JOIN CodeEmpl c " +
					"ON e.unit=c.idno AND (c.category='Unit' OR c.category='UnitTeach'))LEFT OUTER JOIN Empl_techlimit et ON e.idno=et.idno " +
					"WHERE d.techid=e.idno AND d.Sterm='"+sterm+"' GROUP BY e.idno ORDER BY e.unit");			
			
			//處理天
			int day;
			List tmpList;
			float thour, credit, total;
			for(int i=0; i<list.size(); i++){
				day=0;
				total=0;
				
				
				//周5前每天來都只算1天
				try{
					tmpList=manager.ezGetBy("SELECT * FROM(SELECT c.week, COUNT(*) FROM " +
					"Dtime d, Dtime_class c WHERE d.Sterm='"+sterm+"' AND c.week<=5 AND d.Oid=c.Dtime_oid AND " +
					"d.techid='"+((Map)list.get(i)).get("idno")+"' GROUP BY c.week Union SELECT c.week, COUNT(*) FROM Dtime d, " +
					"Dtime_teacher dt, Dtime_class c WHERE d.Oid=dt.Dtime_oid AND d.Sterm='"+sterm+"' AND " +
					"dt.Dtime_oid=c.Dtime_oid AND c.week<=5 AND d.techid='"+((Map)list.get(i)).get("idno")+"' GROUP BY c.week) " +
					"as a GROUP BY week");					
					day=tmpList.size();
				}catch(Exception e){}
				
				//周六早上+1
				try{					
					if(manager.ezGetInt("SELECT COUNT(*) FROM(SELECT c.week, COUNT(*) FROM " +
							"Dtime d, Dtime_class c WHERE d.Sterm='"+sterm+"' AND c.week=6 AND c.end<=4 AND d.Oid=c.Dtime_oid AND " +
							"d.techid='"+((Map)list.get(i)).get("idno")+"' GROUP BY c.week Union SELECT c.week, COUNT(*) FROM Dtime d, " +
							"Dtime_teacher dt, Dtime_class c WHERE d.Oid=dt.Dtime_oid AND d.Sterm='"+sterm+"' AND " +
							"dt.Dtime_oid=c.Dtime_oid AND c.week=6 AND c.end<=4 AND d.techid='"+((Map)list.get(i)).get("idno")+"' GROUP BY c.week) " +
							"as a GROUP BY week")>0){
								day=day+1;
							}					
				}catch(Exception e){}
				
				//周六下午+1
				try{					
					if(manager.ezGetInt("SELECT COUNT(*) FROM(SELECT c.week, COUNT(*) FROM " +
							"Dtime d, Dtime_class c WHERE d.Sterm='"+sterm+"' AND c.week=6 AND c.end>4 AND d.Oid=c.Dtime_oid AND " +
							"d.techid='"+((Map)list.get(i)).get("idno")+"' GROUP BY c.week Union SELECT c.week, COUNT(*) FROM Dtime d, " +
							"Dtime_teacher dt, Dtime_class c WHERE d.Oid=dt.Dtime_oid AND d.Sterm='"+sterm+"' AND " +
							"dt.Dtime_oid=c.Dtime_oid AND c.week=6 AND c.end>4 AND d.techid='"+((Map)list.get(i)).get("idno")+"' GROUP BY c.week) " +
							"as a GROUP BY week")>0){
								day=day+1;
							}				
				}catch(Exception e){}		

				//周日早上+1
				try{
					if(manager.ezGetInt("SELECT COUNT(*) FROM(SELECT c.week, COUNT(*) FROM " +
							"Dtime d, Dtime_class c WHERE d.Sterm='"+sterm+"' AND c.week=7 AND c.end<=4 AND d.Oid=c.Dtime_oid AND " +
							"d.techid='"+((Map)list.get(i)).get("idno")+"' GROUP BY c.week Union SELECT c.week, COUNT(*) FROM Dtime d, " +
							"Dtime_teacher dt, Dtime_class c WHERE d.Oid=dt.Dtime_oid AND d.Sterm='"+sterm+"' AND " +
							"dt.Dtime_oid=c.Dtime_oid AND c.week=7 AND c.end<=4 AND d.techid='"+((Map)list.get(i)).get("idno")+"' GROUP BY c.week) " +
							"as a GROUP BY week")>0){
								day=day+1;
							}					
				}catch(Exception e){}				

				//周日下午+1
				try{					
					if(manager.ezGetInt("SELECT COUNT(*) FROM(SELECT c.week, COUNT(*) FROM " +
							"Dtime d, Dtime_class c WHERE d.Sterm='"+sterm+"' AND c.week=7 AND c.end>4 AND d.Oid=c.Dtime_oid AND " +
							"d.techid='"+((Map)list.get(i)).get("idno")+"' GROUP BY c.week Union SELECT c.week, COUNT(*) FROM Dtime d, " +
							"Dtime_teacher dt, Dtime_class c WHERE d.Oid=dt.Dtime_oid AND d.Sterm='"+sterm+"' AND " +
							"dt.Dtime_oid=c.Dtime_oid AND c.week=7 AND c.end>4 AND d.techid='"+((Map)list.get(i)).get("idno")+"' GROUP BY c.week) " +
							"as a GROUP BY week")>0){
								day=day+1;
							}	
				}catch(Exception e){}
				
				//處理時數
				
				try{
					credit=Float.parseFloat(((Map)list.get(i)).get("credit").toString());
				}catch(Exception e){
					credit=0;
				}
				
				try{
					thour=Float.parseFloat(((Map)list.get(i)).get("thour").toString());
				}catch(Exception e){
					thour=0;
				}
				
				//時數大於學分數，多的除以2
				if(thour>credit){
					total=credit+(thour-credit)/2;
				}else{
					total=thour;
				}
				
				total=total+manager.ezGetInt("SELECT SUM(t.hours)FROM Dtime d, Dtime_teacher t WHERE " +
						"d.Oid=t.Dtime_oid AND d.Sterm='"+sterm+"' AND t.teach_id='"+((Map)list.get(i)).get("idno")+"'");
				
				((Map)list.get(i)).put("day", day);
				((Map)list.get(i)).put("total", total);
			}
			
			session.setAttribute("techs" ,list);
			
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", checkOpt));
			saveMessages(request, msg);			
		}
		
		//不及格比例
		if(checkOpt.equals("tripos")){
			//session.setAttribute("CheckOpt", "tripos");
			
			session.setAttribute("triposList", manager.geTtriposBy(classLess, sterm));
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", checkOpt));
			saveMessages(request, msg);
		}

		// 低修高查核
		if(checkOpt.equals("CheckHeight")){
			if(classLess.length()<1){
				//範圍過大mysql接收太大量查詢會掛
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "指定範圍過大<b>會影響查核效率</b>, 請至少指定一種<b>學制</b>, 謝謝!"));
				saveErrors(request, error);
				return mapping.findForward("Main");
			}
			//TODO 2007/10/17 低修高查核需跨部制
			/*這裡是只查同部制的低修高
			List checkHeight=manager.ezGetBy("SELECT c.ClassName as csClass, cs.cscode, c1.ClassName as stClass, cs.chi_name, st.student_no, st.student_name " +
					"FROm Seld s, Dtime d, stmd st, Class c, Class c1, Csno cs " +
					"WHERE s.Dtime_oid=d.Oid AND s.student_no=st.student_no AND d.depart_class=c.ClassNo AND " +
					"d.cscode=cs.cscode AND c1.ClassNo=st.depart_class AND (c.schoolNo=c1.schoolNo AND c.Grade>c1.Grade) AND " +
					"st.depart_class LIKE '"+classLess+"%'");
			*/
			//這裡是查跨部制的低修高
			List checkHeight=manager.ezGetBy("SELECT c.ClassName as csClass, cs.cscode, " +
					"c1.ClassName as stClass, cs.chi_name,st.student_no, st.student_name " +
					"FROm Seld s, Dtime d, stmd st, Class c, Class c1, Csno cs WHERE " +
					"s.Dtime_oid=d.Oid AND s.student_no=st.student_no AND " +
					"d.depart_class=c.ClassNo AND d.cscode=cs.cscode AND d.Sterm='"+sterm+"' AND "+
					"c1.ClassNo=st.depart_class AND SUBSTRING(c.ClassNo, 5,1) > SUBSTRING(c1.ClassNo, 5,1) AND " +
					"st.depart_class LIKE '"+classLess+"%'");
			
			session.setAttribute("CheckHeight", checkHeight);
			//session.setAttribute("CheckOpt", "CheckHeight");
			if(checkHeight.size()>0){
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "有"+checkHeight.size()+"人次 共"+checkHeight.size()+"筆選課有問題"));
			}else{
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "沒有人"));
			}

			saveMessages(request, msg);

		}		
		
		//班級開課衝堂
		if(checkOpt.equals("CheckClass")){
			if(classLess.length()<1){
				//範圍過大mysql接收太大量查詢會掛
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "指定範圍過大<b>會影響查核效率</b>, 請至少指定一種<b>學制</b>, 謝謝!"));
				saveErrors(request, error);
				return mapping.findForward("Main");
			}
			
			List CheckClass=new ArrayList();
			List courses=manager.ezGetBy(
					"SELECT Oid, depart_class FROM Dtime WHERE Sterm='"+sterm+"' AND depart_class LIKE '"+classLess+"%'");
			
			for(int i=0; i< courses.size(); i++){			
				List dtimeClass=manager.ezGetBy("SELECT week, begin, end FROM Dtime_class WHERE Dtime_oid='"+((Map)courses.get(i)).get("Oid")+"'");
				
				String week[]=new String[dtimeClass.size()];
				String begin[]=new String[dtimeClass.size()];
				String end[]=new String[dtimeClass.size()];
				
				for(int j=0; j<dtimeClass.size(); j++){
					week[j]=((Map)dtimeClass.get(j)).get("week").toString();
					begin[j]=((Map)dtimeClass.get(j)).get("begin").toString();
					end[j]=((Map)dtimeClass.get(j)).get("end").toString();					
				}
				
				CheckClass.addAll(manager.checkReopenClass(((Map)courses.get(i)).get("depart_class").toString(), 
						week, begin, end, sterm, ((Map)courses.get(i)).get("Oid").toString()));				
			}			
			session.setAttribute("CheckClass", CheckClass);
			//session.setAttribute("CheckOpt", "CheckClass");
			if(CheckClass.size()>0){
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "有"+CheckClass.size()+"門開課有問題"));
			}else{
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "沒有衝堂"));
			}
			saveMessages(request, msg);
		}

		//教室開課衝堂
		if(checkOpt.equals("CheckRoom")){
			if(classLess.length()<1){
				//範圍過大mysql接收太大量查詢會掛
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "指定範圍過大<b>會影響查核效率</b>, 請至少指定一種<b>學制</b>, 謝謝!"));
				saveErrors(request, error);
				return mapping.findForward("Main");
			}
			
			List CheckRoom=new ArrayList();
			List courses=manager.ezGetBy(
					"SELECT Oid, depart_class FROM Dtime WHERE Sterm='"+sterm+"' AND depart_class LIKE '"+classLess+"%'");
			
			for(int i=0; i< courses.size(); i++){
				
				List dtimeClass=manager.ezGetBy("SELECT week, begin, end, place FROM Dtime_class WHERE Dtime_oid='"+((Map)courses.get(i)).get("Oid")+"' AND " +
						"place IS NOT NULL AND place<>''");
				
				String place[]=new String[dtimeClass.size()];
				String week[]=new String[dtimeClass.size()];
				String begin[]=new String[dtimeClass.size()];
				String end[]=new String[dtimeClass.size()];
				
				for(int j=0; j<dtimeClass.size(); j++){
					week[j]=((Map)dtimeClass.get(j)).get("week").toString();
					begin[j]=((Map)dtimeClass.get(j)).get("begin").toString();
					end[j]=((Map)dtimeClass.get(j)).get("end").toString();
					place[j]=((Map)dtimeClass.get(j)).get("place").toString();
				}
				
				CheckRoom.addAll(manager.checkReOpenRoom(place, week, begin, end, sterm, ((Map)courses.get(i)).get("Oid").toString()));		
			}			
			
			session.setAttribute("CheckRoom", CheckRoom);
			//session.setAttribute("CheckOpt", "CheckRoom");
			if(CheckRoom.size()>0){
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "有"+CheckRoom.size()+"間教室有問題"));
			}else{
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "沒有重複使用"));
			}
			saveMessages(request, msg);
		}

		//教師開課衝堂
		if(checkOpt.equals("CheckTech")){
			if(classLess.length()<1){
				//範圍過大mysql接收太大量查詢會掛
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "指定範圍過大<b>會影響查核效率</b>, 請至少指定一種<b>學制</b>, 謝謝!"));
				saveErrors(request, error);
				return mapping.findForward("Main");
			}
			
			List checkTech=new ArrayList();
			List courses=manager.ezGetBy(//拿到範圍內的課程Oid, techid
					"SELECT Oid, techid FROM Dtime WHERE Sterm='"+sterm+"' AND depart_class LIKE '"+classLess+"%' AND techid IS NOT NULL AND techid <>''");			
			
			for(int i=0; i< courses.size(); i++){
				
				if(((Map)courses.get(i)).get("techid")!=null && !((Map)courses.get(i)).get("techid").equals("")){
					
					List techers=manager.ezGetBy("SELECT week, begin, end FROM Dtime_class WHERE " +
							"Dtime_oid='"+((Map)courses.get(i)).get("Oid")+"' AND begin IS NOT NULL AND begin <>''");
					
					String week[]=new String[techers.size()];
					String begin[]=new String[techers.size()];
					String end[]=new String[techers.size()];
					
					for(int j=0; j<techers.size(); j++){
						week[j]=((Map)techers.get(j)).get("week").toString();
						begin[j]=((Map)techers.get(j)).get("begin").toString();
						end[j]=((Map)techers.get(j)).get("end").toString();
					}				
					
					try{
						List list=manager.checkEmplReOpen(((Map)courses.get(i)).get("techid").toString(), 
								 week, begin, end, sterm, ((Map)courses.get(i)).get("Oid").toString());	
							if(list.size()>0){
								checkTech.addAll(list);								
							}						
					}catch(NullPointerException e){
						//System.out.println(e);
					}
				}
			}			
			
			session.setAttribute("CheckTech", checkTech);
			//session.setAttribute("CheckOpt", "CheckTech");
			if(checkTech.size()>0){
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "有"+checkTech.size()+"位老師有問題"));
			}else{
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "沒有老師有問題"));
			}
			saveMessages(request, msg);
		}
		
		
		
		//補/退學分費  Leo 20120306 增加單一學生的查核條件
		if(checkOpt.equals("CheckPay")){
			session.setAttribute("CheckOpt", "CheckPay");
			
			String payMoney = (String) setUpDtime.get("payMoney"); // area
			String payHour = (String) setUpDtime.get("payHour"); // area
			String extraPay=(String) setUpDtime.get("extraPay"); // area
			String insurance=(String) setUpDtime.get("insurance"); // area
			String studentNo=(String) setUpDtime.get("studentNo"); // area
			
			if(payMoney.trim().equals("")||payHour.trim().equals("")||extraPay.trim().equals("")|| insurance.trim().equals("")){  //||classLess.trim().length()<=2
				
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "原時數、每時數費用、電腦實習費 必須有值"));
				saveErrors(request, error);
				return mapping.findForward("Main");
				
			}
            if(classLess.trim().length()<=2 && studentNo.trim().equals("")){  
				
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "班級、學生不可空白"));
				saveErrors(request, error);
				return mapping.findForward("Main");
				
			}
			
			session.setAttribute("payMoney", payMoney);
			session.setAttribute("payHour", payHour);
			session.setAttribute("extraPay", extraPay);
			session.setAttribute("insurance", insurance);
			session.setAttribute("classLess", classLess);

            if(studentNo.trim().equals("")){
            	List list=manager.ezGetBy("SELECT st.student_name, s.student_no, d.thour, c.ClassName, c.ClassNo " +
    			"FROM Seld s, Dtime d, stmd st, Class c WHERE c.ClassNo=st.depart_class AND " +
    			"s.Dtime_oid=d.Oid AND st.student_no=s.student_no AND st.depart_class LIKE '"+classLess+"%' AND " +
    			"d.Sterm='"+sterm+"' GROUP BY s.student_no ORDER BY st.student_no");
            	
            	int sumThour;
            	float sumCredit;
            	String extrapay;
            	int sumExtrapay;
    			List myThour;
    			Map map;
    			List allSelds=new ArrayList();
    			for(int i=0; i<list.size(); i++){				
    				sumThour=0;
    				sumCredit=0;
    				sumExtrapay=0;
    				extrapay="";
    				myThour=manager.ezGetBy("SELECT d.* FROM Dtime d, Seld s WHERE d.Oid=s.Dtime_oid AND d. Sterm='"+sterm+"' AND " +
    						"s.student_no='"+((Map)list.get(i)).get("student_no")+"' AND d.cscode!='50000' GROUP BY s.student_no, s.Dtime_oid");				
    				for(int j=0; j<myThour.size(); j++){//時數加總
    					sumThour=sumThour+Integer.parseInt(((Map)myThour.get(j)).get("thour").toString());
    					sumCredit=sumCredit+Float.parseFloat(((Map)myThour.get(j)).get("credit").toString());
    					if(Integer.parseInt(((Map)myThour.get(j)).get("extrapay").toString())>0){
    						sumExtrapay+=1;
    						extrapay="Y";
    					}
    				}

    				if(sumThour!=Integer.parseInt(payHour)){//若時數不等於輸入					
    					map=new HashMap();
    					map.put("student_no", ((Map)list.get(i)).get("student_no"));
    					map.put("student_name", ((Map)list.get(i)).get("student_name"));
    					map.put("ClassNo", ((Map)list.get(i)).get("ClassNo"));
    					map.put("ClassName", ((Map)list.get(i)).get("ClassName"));
    					map.put("sumThour", sumThour);		
    					map.put("sumCredit", sumCredit);
    					map.put("sumExtrapay", sumExtrapay);
    					map.put("Extrapay_Kind", extrapay);
    					allSelds.add(map);					
    				}
    				if(sumThour==Integer.parseInt(payHour) && Integer.parseInt(extraPay)>0 && extrapay!="Y"){//若時數等於輸入	有實習費				
    					map=new HashMap();
    					map.put("student_no", ((Map)list.get(i)).get("student_no"));
    					map.put("student_name", ((Map)list.get(i)).get("student_name"));
    					map.put("ClassNo", ((Map)list.get(i)).get("ClassNo"));
    					map.put("ClassName", ((Map)list.get(i)).get("ClassName"));
    					map.put("sumThour", sumThour);		
    					map.put("sumCredit", sumCredit);
    					map.put("sumExtrapay", sumExtrapay);
    					map.put("Extrapay_Kind", extrapay);
    					allSelds.add(map);					
    				}
    				if(sumThour==Integer.parseInt(payHour) && Integer.parseInt(extraPay)<0 && extrapay=="Y"){//若時數等於輸入	無實習費					
    					map=new HashMap();
    					map.put("student_no", ((Map)list.get(i)).get("student_no"));
    					map.put("student_name", ((Map)list.get(i)).get("student_name"));
    					map.put("ClassNo", ((Map)list.get(i)).get("ClassNo"));
    					map.put("ClassName", ((Map)list.get(i)).get("ClassName"));
    					map.put("sumThour", sumThour);		
    					map.put("sumCredit", sumCredit);
    					map.put("sumExtrapay", sumExtrapay);
    					map.put("Extrapay_Kind", extrapay);
    					allSelds.add(map);					
    				}
    			}
    			session.setAttribute("allSelds", allSelds);
    			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "查詢完成, 共 "+allSelds.size()+"筆"));
    			saveMessages(request, msg);
				
			}else{
				List list=manager.ezGetBy(
						"SELECT st.student_name, s.student_no, d.thour, c.ClassName, c.ClassNo " +
						"FROM Seld s, Dtime d, stmd st, Class c " +
						"WHERE c.ClassNo=st.depart_class " +
						"  AND s.Dtime_oid=d.Oid " +
						"  AND st.student_no=s.student_no " +
						"  AND st.student_no LIKE '"+studentNo+"%' " +
						"  AND d.Sterm='"+sterm+"' " +
						"GROUP BY s.student_no ORDER BY st.student_no");
				
				int sumThour;
				List myThour;
				Map map;
				List allSelds=new ArrayList();
				for(int i=0; i<list.size(); i++){				
					sumThour=0;				
					myThour=manager.ezGetBy("SELECT d.* FROM Dtime d, Seld s WHERE d.Oid=s.Dtime_oid AND d. Sterm='"+sterm+"' AND " +
							"s.student_no='"+((Map)list.get(i)).get("student_no")+"' GROUP BY s.student_no, s.Dtime_oid");				
					for(int j=0; j<myThour.size(); j++){//時數加總
						sumThour=sumThour+Integer.parseInt(((Map)myThour.get(j)).get("thour").toString());
					}
					
					if(sumThour!=Integer.parseInt(payHour)){//若時數不等於輸入					
						map=new HashMap();
						map.put("student_no", ((Map)list.get(i)).get("student_no"));
						map.put("student_name", ((Map)list.get(i)).get("student_name"));
						map.put("ClassNo", ((Map)list.get(i)).get("ClassNo"));
						map.put("ClassName", ((Map)list.get(i)).get("ClassName"));
						map.put("sumThour", sumThour);					
						allSelds.add(map);					
					}
				}
				session.setAttribute("allSelds", allSelds);
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "查詢完成, 共 "+allSelds.size()+"筆"));
				saveMessages(request, msg);
			}
			/*
			List list=manager.ezGetBy("SELECT st.student_name, s.student_no, d.thour, c.ClassName, c.ClassNo " +
					"FROM Seld s, Dtime d, stmd st, Class c WHERE c.ClassNo=st.depart_class AND " +
					"s.Dtime_oid=d.Oid AND st.student_no=s.student_no AND st.depart_class LIKE '"+classLess+"%' AND " +
					"d.Sterm='"+sterm+"' GROUP BY s.student_no ORDER BY st.student_no");
			
			int sumThour;
			List myThour;
			Map map;
			List allSelds=new ArrayList();
			for(int i=0; i<list.size(); i++){				
				sumThour=0;				
				myThour=manager.ezGetBy("SELECT d.* FROM Dtime d, Seld s WHERE d.Oid=s.Dtime_oid AND d. Sterm='"+sterm+"' AND " +
						"s.student_no='"+((Map)list.get(i)).get("student_no")+"' GROUP BY s.student_no, s.Dtime_oid");				
				for(int j=0; j<myThour.size(); j++){//時數加總
					sumThour=sumThour+Integer.parseInt(((Map)myThour.get(j)).get("thour").toString());
				}
				
				if(sumThour!=Integer.parseInt(payHour)){//若時數不等於輸入					
					map=new HashMap();
					map.put("student_no", ((Map)list.get(i)).get("student_no"));
					map.put("student_name", ((Map)list.get(i)).get("student_name"));
					map.put("ClassNo", ((Map)list.get(i)).get("ClassNo"));
					map.put("ClassName", ((Map)list.get(i)).get("ClassName"));
					map.put("sumThour", sumThour);					
					allSelds.add(map);					
				}
			}
			
			
			session.setAttribute("allSelds", allSelds);
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "查詢完成, 共 "+allSelds.size()+"筆"));
			saveMessages(request, msg);*/
		} //*******************************************************************	
		
		//暑修開課建議
		if(checkOpt.equals("SummerOpen")){
			//session.setAttribute("CheckOpt", "SummerOpen");
			//依查詢範圍
			int x;
			if(classLess.length()>=2){
				if(classLess.charAt(2)=='4'){
					x=manager.getSchoolYear()-3;
				}else{
					x=manager.getSchoolYear()-1;
				}
			}else{
				x=manager.getSchoolYear()-3;
			}
			
			List list=manager.ezGetBy("SELECT COUNT(sh.stdepart_class) as sumSt, sh.opt, sh.cscode, sh.stdepart_class, c.chi_name, c5.name as DeptNo, " +
					"sh.credit FROM code5 c5, ScoreHist sh, Csno c, Class cl, stmd s WHERE s.student_no=sh.student_no AND cl.ClassNo=sh.stdepart_class AND sh.cscode<>'99999' AND " +
					"sh.cscode=c.cscode AND sh.score<60 AND sh.school_year>='"+x+"' AND sh.stdepart_class LIKE '"+classLess+"%' AND " +
					"c5.category='Dept' AND c5.idno=cl.DeptNo GROUP BY sh.cscode, cl.DeptNo ORDER BY sh.cscode, sh.student_no, cl.deptNo, sumSt DESC");			
			
			
			List fail=new ArrayList();
			Map map;
			for(int i=0; i<list.size(); i++){
				map=new HashMap();
				
				try{
					if(Integer.parseInt(((Map)list.get(i)).get("sumSt").toString())>=5){
						map.put("sumSt", ((Map)list.get(i)).get("sumSt"));						
						map.put("cscode", ((Map)list.get(i)).get("cscode"));
						map.put("stdepart_class", ((Map)list.get(i)).get("stdepart_class"));
						map.put("chi_name", ((Map)list.get(i)).get("chi_name"));
						map.put("DeptNo", ((Map)list.get(i)).get("DeptNo"));
						
						if(((Map)list.get(i)).get("opt").equals("1")){
							map.put("opt", "必修");
						}else if(((Map)list.get(i)).get("opt").equals("2")){
							map.put("opt", "選修");
						}else if(((Map)list.get(i)).get("opt").equals("3")){
							map.put("opt", "通識");
						}
						
						map.put("thour", manager.ezGetString("SELECT thour FROM Dtime WHERE cscode='"+((Map)list.get(i)).get("cscode")+"'limit 1"));
						map.put("credit", ((Map)list.get(i)).get("credit"));
						fail.add(map);
					}					
				}catch(Exception e){
					
				}
			}			
			session.setAttribute("fail", fail);
			//session.setAttribute("failClassLess", classLess);
		}
		
		//學程重複開課
		if(checkOpt.equals("ReOpenHist")){
			
			List classes=manager.ezGetBy("SELECT * FROM Class WHERE ClassNo LIKE '"+classLess+"%'");			
			int grade;
			StringBuilder classNo;
			List ReOpenHist=new ArrayList();
			List tmp;
			Integer school_year=manager.getSchoolYear();
			int tmpyear;
			//班級
			for(int i=0; i<classes.size(); i++){
				grade=Integer.parseInt(((Map)classes.get(i)).get("Grade").toString());
				//年級
				tmpyear=school_year;
				for(int j=1; j<=grade; j++){
					try{
						Integer x=j;
						classNo=new StringBuilder((((Map)classes.get(i)).get("ClassNo")).toString());						
						classNo.setCharAt(4, x.toString().charAt(0));
						//System.out.println("變化="+classNo);
						
						tmp=manager.ezGetBy("SELECT school_year, school_term, cscode, depart_class " +
								"FROM Savedtime WHERE school_year='"+tmpyear+"' AND depart_class='"+classNo+"'");							
							
							
						ReOpenHist.addAll(getReOpenHist(tmp));
						tmpyear=tmpyear-1;
						
					}catch(Exception e){
						System.out.println("問題出在"+classes.get(i));						
					}					
				}
				
			}
			session.setAttribute("ReOpenHist", ReOpenHist);
			//session.setAttribute("CheckOpt", "ReOpenHist");			
		}
		
		//通識未選
		if(checkOpt.equals("CheckGeneral")){			
			//session.setAttribute("CheckOpt", "CheckGeneral");			
			//int x;
			List list=manager.ezGetBy("SELECT * FROM stmd WHERE depart_class LIKE'"+classLess+"%' GROUP BY depart_class");			
			List noGeneral=new ArrayList();
			List students;
			Map map;			
			//確認查核範圍
			for(int i=0; i<list.size(); i++){
				students=manager.ezGetBy("SELECT s.student_no, s.student_name, c.ClassName FROM stmd s, Class c WHERE " +
				"c.ClassNo=s.depart_class AND s.depart_class ='"+((Map)list.get(i)).get("depart_class")+"'");				
				//確認本班有開通識課
				for(int j=0; j<students.size(); j++){								
					//通識未選					
					if(manager.ezGetInt("SELECT COUNT(*) FROM stmd st, Seld s, Dtime d, Csno c, Class cl " +
							"WHERE cl.ClassNo=st.depart_class AND c.cscode=d.cscode AND st.student_no=s.student_no AND d.Oid=s.Dtime_oid AND " +
							"d.Sterm='"+sterm+"' AND d.opt='3' AND st.student_no='"+((Map)students.get(j)).get("student_no")+"'")<1){						
						map=new HashMap();
						map.put("student_no", ((Map)students.get(j)).get("student_no") );
						map.put("ClassName", ((Map)students.get(j)).get("ClassName") );
						map.put("student_name", ((Map)students.get(j)).get("student_name"));
						map.put("cause", "通識未選");
						noGeneral.add(map);
					}					
				}								
			}
			session.setAttribute("noGeneral", noGeneral);			
		}
		
		//班級學生選課  Leo20120307 增加單一學生的查核條件
		if(checkOpt.equals("ListSeld4Class")){
			session.setAttribute("CheckOpt", "ListSeld4Class");
			String studentNo=(String) setUpDtime.get("studentNo_0");
			
            if(classLess.trim().length()<=2 && studentNo.trim().equals("")){  
				
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "班級、學生不可空白"));
				saveErrors(request, error);
				return mapping.findForward("Main");
				
			} 
			if(studentNo.trim().equals("")){
				List ListSeld4Class=manager.ezGetBy(
						"SELECT c.ClassNo, c.ClassName, st.student_no, st.student_name, " +
						"       d.depart_class, cs.cscode, cs.chi_name, d.opt, d.credit, d.thour " +
						"FROM Class c, Csno cs, Dtime d, Seld s, stmd st " +
						"WHERE d.Oid=s.Dtime_oid " +
						"  AND cs.cscode=d.cscode " +
						"  AND st.depart_class=c.ClassNo " +
						"  AND s.student_no=st.student_no " +
						"  AND st.depart_class='"+classLess+"' " +
						"  AND d.Sterm LIKE'"+sterm+"%' " +
						"ORDER BY st.depart_class, st.student_no");
				session.setAttribute("ListSeld4Class", ListSeld4Class);
			}else{
				List ListSeld4Class=manager.ezGetBy(
						"SELECT c.ClassNo, c.ClassName, st.student_no, st.student_name, " +
						"       d.depart_class, cs.cscode, cs.chi_name, d.opt, d.credit, d.thour " +
						"FROM Class c, Csno cs, Dtime d, Seld s, stmd st " +
						"WHERE d.Oid=s.Dtime_oid " +
						"  AND cs.cscode=d.cscode " +
						"  AND st.depart_class=c.ClassNo " +
						"  AND s.student_no=st.student_no " +
						"  AND s.student_no='"+studentNo+"' " +
						"  AND d.Sterm LIKE'"+sterm+"%' " +
						"ORDER BY st.depart_class, st.student_no");
				session.setAttribute("ListSeld4Class", ListSeld4Class);
			}	
			
			/*
			List ListSeld4Class=manager.ezGetBy("SELECT c.ClassNo, c.ClassName, st.student_no, st.student_name, " +
					"d.depart_class, cs.cscode, cs.chi_name, d.opt, d.credit, d.thour " +
					"FROM Class c, Csno cs, Dtime d, Seld s, stmd st WHERE " +
					"d.Oid=s.Dtime_oid AND cs.cscode=d.cscode AND st.depart_class=c.ClassNo AND " +
					"s.student_no=st.student_no AND st.depart_class='"+classLess+"' AND " +
					"d.Sterm LIKE'"+sterm+"%' ORDER BY st.depart_class, st.student_no");
			session.setAttribute("ListSeld4Class", ListSeld4Class);
			*/
		}
		
		//期中考不及格者輔導紀錄
		if(checkOpt.equals("ListCounseling")){			
			String year=manager.getSchoolYear().toString();
			List<Map>tmp;
			List<Map>care;
			int cnt;
			//有不及格的課程裡面的資訊
			List<Map>list=manager.ezGetBy("SELECT (SELECT COUNT(*)FROM Seld WHERE score<60 AND Dtime_oid=d.Oid)as nopas2, d.Oid, e.cname, c.ClassNo, c.ClassName, cs.cscode, cs.chi_name, COUNT(*)as nopas, " +
			"(SELECT COUNT(*)FROM Seld WHERE Dtime_oid=d.Oid)as total, " +
			"ROUND((SELECT AVG(score2)FROM Seld WHERE Dtime_oid=d.Oid),1)as score2, ROUND((SELECT AVG(score)FROM Seld WHERE Dtime_oid=d.Oid),1)as score FROM Seld s, Dtime d, Class c, Csno cs, empl e WHERE " +
			"e.idno=d.techid AND (s.score2 <60 AND s.score2 IS NOT NULL) AND s.Dtime_oid=d.Oid AND d.cscode=cs.cscode AND d.depart_class=c.ClassNo AND " +
			"d.depart_class LIKE '"+classLess+"%' AND d.Sterm='"+sterm+"' GROUP BY d.Oid ORDER BY c.DeptNo");
			
						
			
			for(int i=0; i<list.size(); i++){
				cnt=0;
				//不及格學生
				tmp=manager.ezGetBy("SELECT c.ClassName,s.student_no,s.student_name,se.score2, se.score FROM stmd s, empl e, Class c, Seld se, " +
				"Dtime d,Csno cs WHERE c.ClassNo=s.depart_class AND se.student_no=s.student_no AND d.Oid=se.Dtime_oid AND " +
				"cs.cscode=d.cscode AND e.idno=d.techid AND (se.score2 <60 AND se.score2 IS NOT NULL) AND d.Oid="+
				list.get(i).get("Oid")+" ORDER BY s.student_no");
				
				for(int j=0; j<tmp.size(); j++){
					care=manager.ezGetBy("SELECT content, DATE_FORMAT(mdate,'%m-%d-%Y')as mdate, e.cname FROM StudCounseling s, empl e WHERE " +
					"e.idno=s.teacherid AND s.itemNo='29' AND s.schoolYear='"+year+"' AND " +
					"s.schoolTerm='"+sterm+"' AND s.ctype='L' AND s.studentNO='"+
					tmp.get(j).get("student_no")+"' AND cscode='"+list.get(i).get("cscode")+"'");
					tmp.get(j).put("care", care);
					tmp.get(j).put("cnt", care.size());
					cnt=cnt+care.size();
				}
				
				list.get(i).put("care", tmp);
				list.get(i).put("cnt", cnt);
				
			}			
			ListCounsling p=new ListCounsling();
			p.print(response, list, year, sterm);			
			
			return null;
		}
		
		//體育未選
		if(checkOpt.equals("CheckSport")){
			//session.setAttribute("CheckOpt", "CheckSport");			
			int x;
			List list=manager.ezGetBy("SELECT * FROM stmd WHERE depart_class LIKE'"+classLess+"%' GROUP BY depart_class");			
			List noSport=new ArrayList();
			List students;
			Map map;			
			//確認查核範圍
			for(int i=0; i<list.size(); i++){
				students=manager.ezGetBy("SELECT s.student_no, s.student_name, c.ClassName FROM stmd s, Class c WHERE " +
				"c.ClassNo=s.depart_class AND s.depart_class ='"+((Map)list.get(i)).get("depart_class")+"'");
				map=new HashMap();
				for(int j=0; j<students.size(); j++){					
					//體育沒選
					if(manager.ezGetInt("SELECT COUNT(*) FROM stmd st, Seld s, Dtime d, Csno c, Class cl " +
					"WHERE cl.ClassNo=st.depart_class AND c.cscode=d.cscode AND st.student_no=s.student_no AND d.Oid=s.Dtime_oid AND " +
					"d.Sterm='"+sterm+"' AND c.chi_name LIKE '%體育%' AND st.student_no='"+((Map)students.get(j)).get("student_no")+"'")<1){
						map=new HashMap();
						map.put("student_no", ((Map)students.get(j)).get("student_no") );
						map.put("ClassName", ((Map)students.get(j)).get("ClassName") );
						map.put("student_name", ((Map)students.get(j)).get("student_name"));
						map.put("cause", "體育未選");						
						noSport.add(map);
					}					
				}		
			}
			session.setAttribute("noSport", noSport);			
		}
		
		//無名選課
		if(checkOpt.equals("CheckNoname")){			
			//session.setAttribute("CheckOpt", "CheckNoname");			
			session.setAttribute("CheckNoname", manager.ezGetBy("SELECT s.student_no, c.chi_name, c.cscode FROM Seld s, Dtime d, Csno c WHERE " +
					"c.cscode=d.cscode AND s.Dtime_oid=d.Oid AND s.student_no NOT IN(SELECT student_no FROM stmd)"));			
		}
		

		//學程申請書
		if(checkOpt.equals("CsGroupDoc")){
			if(classLess.length()<3){
				//至少要選一個校區
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "至少選一個學制"));
				saveErrors(request, error);
				return mapping.findForward("Main");
			}
			List CsGroupDoc=manager.ezGetBy("SELECT student_no, student_name, depart_class FROM stmd WHERE depart_class LIKE '"+classLess+"%'");
			session.setAttribute("CsGroupDoc", CsGroupDoc);
			
			if(CsGroupDoc.size()>1){
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", CsGroupDoc.size()+"名學生"));
			}else{
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "沒有人"));
			}

			saveMessages(request, msg);			
		}
		
		
		//歷史重複選課
		if(checkOpt.equals("ReSelected")){
			
			saveMessages(request, msg);
			session.setAttribute("reSelect", manager.checkReSelected(classLess));			
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "查詢完成"));
			saveMessages(request, msg);				
		}
		
		//本學期
		if(checkOpt.equals("ReSelectedNow")){
			
			saveMessages(request, msg);
			session.setAttribute("reSelectNow", manager.checkReSelectedNow(classLess, manager.getSchoolYear().toString(), sterm));			
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "查詢完成"));
			saveMessages(request, msg);				
		}
		
		session.setAttribute("CheckOpt", checkOpt);
		
		
		session.setAttribute("classLess", classLess);
		return mapping.findForward("Main");
	}
	
	private String chCode(String code, String type){
		
		
		if(type.trim().equals("opt")){
			if(code.equals("1")){
				code="必修";
			}
			if(code.equals("2")){
				code="選修";
			}
			if(code.equals("3")){
				code="通識";
			}			
		}
		
		if(type.trim().equals("adcd")){
			if(code.trim().equals("A")){
				code="加選";
			}
			if(code.trim().equals("D")){
				code="退選";
			}
		}
		
		if(type.trim().equals("extrapay")){
			if(code.trim().equals("0")){
				code="";
			}else{
				code="有";
			}
		}		
		return code;
	}
	
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("CourseCheckOpt", "courseCheckOpt");
		return map;
	}
	
	private List getReOpenHist(List list){
		
		List hist=new ArrayList();
		for(int i=0; i<list.size(); i++){
			
			for(int j=0; j<list.size(); j++){
				
				//不同年, 卻開相同課
				if(((Map)list.get(i)).get("cscode").equals(((Map)list.get(j)).get("cscode"))&& 
						!((Map)list.get(i)).get("school_year").equals( ((Map)list.get(j)).get("school_year"))){					
					hist.add(list.get(j));					
				}				
			}
		}
		return hist;
	}
}