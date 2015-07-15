package tw.edu.chit.struts.action.teacher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.Regstime;
import tw.edu.chit.model.Seld;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
/**
 * 成績管理
 * @author JOHN
 * TODO evgr_time
 */
public class ScoreManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, 
			   HttpServletRequest request, HttpServletResponse response)throws Exception {
			
			HttpSession session = request.getSession(false);
			CourseManager manager = (CourseManager) getBean("courseManager");
			
			Member me = getUserCredential(request.getSession(false)).getMember();	
			
			
			List myClass=manager.getDtime(me.getIdno(), String.valueOf(manager.getSchoolTerm()));
			/*草尼瑪說系助又不用輸成績了
			if(manager.ezGetInt("SELECT COUNT(*)FROM UnitBelong WHERE UnitNo='T5' AND EmpOid="+me.getOid())>0){
				//系助
				myClass=manager.getDtimeByAssistant(getUserCredential(request.getSession(false)).getClassInChargeSqlFilter(), String.valueOf(manager.getSchoolTerm()));
			}else{
				//任課
				
			}
			*/
			
			for(int i=0; i<myClass.size(); i++){
				((Map)myClass.get(i)).put("type", "");
				if(  ((Map)myClass.get(i)).get("chi_name").toString().indexOf("英文")>=0){
					((Map)myClass.get(i)).put("type", "e");
					continue;
				}
				if(  ((Map)myClass.get(i)).get("chi_name").toString().indexOf("體育")>=0){
					((Map)myClass.get(i)).put("type", "s");
				}
			}
			

			//成績比例編輯期限
			if(session.getAttribute("edper")==null)
			try{
				SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
				String week=manager.ezGetString("SELECT wdate FROM week ORDER BY wdate LIMIT 1");
				Date now=new Date();
				Date edper;
				edper=sf.parse(week);				
				Calendar c=Calendar.getInstance();
				c.setTime(edper);
				c.add(Calendar.DAY_OF_YEAR, 14);
				edper=c.getTime();
				//System.out.println(edper+", "+now);
				if(now.getTime()>edper.getTime()){
					session.setAttribute("edper", sf.format(edper));
				}
			}catch(Exception e){
				ActionMessages error=new ActionMessages();
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "開學日期無法辨識"));
				saveErrors(request, error);
			}
			
			session.setAttribute("myClass", myClass);			
			setContentPage(request.getSession(false), "teacher/ScoreManager.jsp");
			return mapping.findForward("Main");
	}
	
	
	
	public ActionForward editScore(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		CourseManager manager=(CourseManager) getBean("courseManager");
		DynaActionForm f=(DynaActionForm) form;
		
		String type=f.getString("type");
		String Dtime_oid=f.getString("Dtime_oid");
		
		request.setAttribute("students", manager.ezGetBy("SELECT st.student_name, s.* FROM Seld s, stmd st WHERE s.student_no=st.student_no AND s.Dtime_oid='"+Dtime_oid+"' ORDER BY st.student_no"));
		request.setAttribute("csinfo", manager.ezGetMap("SELECT c.ClassName, cs.chi_name FROM Dtime d, Class c, Csno cs WHERE d.depart_class=c.ClassNo AND d.cscode=cs.cscode AND d.Oid="+Dtime_oid));
		request.setAttribute("type", type);
		request.setAttribute("Dtime_oid", Dtime_oid);
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		Date now=new Date();
		//Date target;
		
		String depart_class=manager.ezGetString("SELECT depart_class FROM Dtime WHERE Oid='"+Dtime_oid+"'");
		
		//資料表optime1
		String date1=manager.ezGetString("SELECT end_date FROM optime1 WHERE level='1' AND depart='11'");
		String date2=manager.ezGetString("SELECT end_date FROM optime1 WHERE level='2' AND depart='11'");
		String date3=manager.ezGetString("SELECT end_date FROM optime1 WHERE level='3' AND depart='11'");
		request.setAttribute("sdate1", date1);
		if(manager.getSchoolTerm()==2){
			request.setAttribute("sdate3", date3);
		}
		request.setAttribute("sdate2", date2);
		
		try{//白痴啊改這什麼鬼
			//if(now.getTime()>sf.parse(manager.convertDate(date1)).getTime()){  // 20120719 Mark BY yichen
			/* 20120719 Modi BY yichen  ===========begin========================*/
			//現在時間跟到期時間比較時須將到期時間加上所設定之結束時間(end_time)來做判斷
			//以免系統將時間直接預設為00:00:00導致結束時間當日無法輸入成績。
            String time1=manager.ezGetString("SELECT end_time FROM optime1 WHERE level='1' AND depart='11'");
            String time2=manager.ezGetString("SELECT end_time FROM optime1 WHERE level='2' AND depart='11'");
            String time3=manager.ezGetString("SELECT end_time FROM optime1 WHERE level='3' AND depart='11'");
            Calendar cal0719time1 = Calendar.getInstance();	
            Calendar cal0719time2 = Calendar.getInstance();
            Calendar cal0719time3 = Calendar.getInstance();	
            cal0719time1.setTimeInMillis(sf.parse(manager.convertDate(date1)).getTime());
            cal0719time2.setTimeInMillis(sf.parse(manager.convertDate(date2)).getTime());
            cal0719time3.setTimeInMillis(sf.parse(manager.convertDate(date3)).getTime());
			if (time1.equals("")||time1==null||time1.substring(0,2).equals("24")){		
				cal0719time1.set(Calendar.HOUR_OF_DAY, 23);
				cal0719time1.set(Calendar.MINUTE, 59);
				cal0719time1.set(Calendar.SECOND, 59);
				cal0719time1.set(Calendar.MILLISECOND, 999);
			}
			else {
				cal0719time1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time1.substring(0,2)));
				cal0719time1.set(Calendar.MINUTE, Integer.parseInt(time1.substring(3,5)));
				cal0719time1.set(Calendar.SECOND, Integer.parseInt(time1.substring(6,8)));
				cal0719time1.set(Calendar.MILLISECOND, 999);	
			}
			if (time2.equals("")||time2==null||time2.substring(0,2).equals("24")){		
				cal0719time2.set(Calendar.HOUR_OF_DAY, 23);
				cal0719time2.set(Calendar.MINUTE, 59);
				cal0719time2.set(Calendar.SECOND, 59);
				cal0719time2.set(Calendar.MILLISECOND, 999);
			}
			else {
				cal0719time2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time2.substring(0,2)));
				cal0719time2.set(Calendar.MINUTE, Integer.parseInt(time2.substring(3,5)));
				cal0719time2.set(Calendar.SECOND, Integer.parseInt(time2.substring(6,8)));
				cal0719time2.set(Calendar.MILLISECOND, 999);	
			}
			if (time3.equals("")||time3==null||time3.substring(0,2).equals("24")){		
				cal0719time3.set(Calendar.HOUR_OF_DAY, 23);
				cal0719time3.set(Calendar.MINUTE, 59);
				cal0719time3.set(Calendar.SECOND, 59);
				cal0719time3.set(Calendar.MILLISECOND, 999);
			}
			else {
				cal0719time3.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time3.substring(0,2)));
				cal0719time3.set(Calendar.MINUTE, Integer.parseInt(time3.substring(3,5)));
				cal0719time3.set(Calendar.SECOND, Integer.parseInt(time3.substring(6,8)));
				cal0719time3.set(Calendar.MILLISECOND, 999);	
			}
			if(now.getTime()>cal0719time1.getTime().getTime()){
			/* 20120719 Modi BY yichen  ===========end========================*/
				//現在時間>到期時間不可編輯期中欄位
				request.setAttribute("date1", true);
			}
						
			/* 20120629 Add BY yichen  ===========begin========================*/
			String GradYear=manager.ezGetString("SELECT name FROM code5 WHERE category = 'GradYear' AND idno='"+depart_class.substring(1, 3)+"'");			
			if (GradYear.equals("")||GradYear==null) {  //保險起見找不到畢業年限的時候用原判斷
			/* 20120629 Add BY yichen  ===========end========================*/
				if(depart_class.substring(2, 3).equals(depart_class.substring(4, 5))&&manager.getSchoolTerm()==2){//畢業班
					//if(now.getTime()>sf.parse(manager.convertDate(date3)).getTime()){	// 20120719 Mark BY yichen				
					if(now.getTime()>cal0719time3.getTime().getTime()){	// 20120719 Modi BY yichen
						request.setAttribute("date2", true); 
					}				
				}else{
					//if(now.getTime()>sf.parse(manager.convertDate(date2)).getTime()){ // 20120719 Mark BY yichen				
					if(now.getTime()>cal0719time2.getTime().getTime()){	// 20120719 Modi BY yichen
						request.setAttribute("date2", true);
					}
				}							
			/* 20120629 Add BY yichen  ===========begin========================*/
			}			
			else{ //若找到畢業年限則改成由此判斷
				if(GradYear.equals(depart_class.substring(4, 5))&&manager.getSchoolTerm()==2){//畢業班
					//if(now.getTime()>sf.parse(manager.convertDate(date3)).getTime()){	// 20120719 Mark BY yichen				
					if(now.getTime()>cal0719time3.getTime().getTime()){	// 20120719 Modi BY yichen	
						request.setAttribute("date2", true);  	
					}				
				}else{
					//if(now.getTime()>sf.parse(manager.convertDate(date2)).getTime()){ // 20120719 Mark BY yichen				
					if(now.getTime()>cal0719time2.getTime().getTime()){	// 20120719 Modi BY yichen
						request.setAttribute("date2", true);
					}
				}
			}
			/* 20120629 Add BY yichen  ============end=========================*/
		}catch(Exception e){
			ActionMessages error=new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "未設定截止日期"));
			saveErrors(request, error);
		}
		
		
		request.setAttribute("seldpro", manager.ezGetMap("SELECT * FROM SeldPro WHERE Dtime_oid="+Dtime_oid));
		Map map=manager.ezGetMap("SELECT * FROM SeldPro WHERE Dtime_oid="+Dtime_oid);
		if(map==null){
			map=new HashMap();
			map.put("score1", 30);
			map.put("score2", 30);
			map.put("score3", 40);
		}
		request.setAttribute("seldpro", map);
		
		return unspecified(mapping, form, request, response);
	}
	
	public ActionForward editPro(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		CourseManager manager=(CourseManager) getBean("courseManager");
		DynaActionForm f=(DynaActionForm) form;
		
		String type=f.getString("type");
		String Dtime_oid=f.getString("Dtime_oid");		
		
		String p1=f.getString("p1");
		String p2=f.getString("p2");
		String p3=f.getString("p3");
		
		
		if(!p1.trim().equals("")&&!p2.trim().equals("")&&!p3.trim().equals("")){
			if(Integer.parseInt(p1)+Integer.parseInt(p2)+Integer.parseInt(p3)==100){
				manager.executeSql("DELETE FROM SeldPro WHERE Dtime_oid="+Dtime_oid);
				manager.executeSql("INSERT INTO SeldPro(Dtime_oid, score1, score2, score3)VALUES('"+Dtime_oid+"', '"+p1+"', '"+p2+"', '"+p3+"')");
			}else{
				ActionMessages error=new ActionMessages();
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "百分比欄位相加結果不滿足100"));
				saveErrors(request, error);
			}
		}else{
			ActionMessages error=new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "百分比欄位空白不利於辨識，請儘可能填入數字並且相加結果為100"));
			saveErrors(request, error);
		}
		
		
		request.setAttribute("students", manager.ezGetBy("SELECT st.student_name, s.* FROM Seld s, stmd st WHERE s.student_no=st.student_no AND s.Dtime_oid='"+Dtime_oid+"' ORDER BY st.student_no"));
		request.setAttribute("csinfo", manager.ezGetMap("SELECT c.ClassName, cs.chi_name FROM Dtime d, Class c, Csno cs WHERE d.depart_class=c.ClassNo AND d.cscode=cs.cscode AND d.Oid="+Dtime_oid));
		request.setAttribute("type", type);
		request.setAttribute("Dtime_oid", Dtime_oid);
		
		
		Map map=manager.ezGetMap("SELECT * FROM SeldPro WHERE Dtime_oid="+Dtime_oid);
		if(map==null){
			map=new HashMap();
			map.put("score1", 30);
			map.put("score2", 30);
			map.put("score3", 40);
		}
		request.setAttribute("seldpro", map);
		return unspecified(mapping, form, request, response);
	}
	
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg=new ActionMessages();		//建立共用訊息
		ActionMessages error=new ActionMessages();	//建立共用錯誤訊息
		//HttpSession session=request.getSession(false);
		CourseManager manager=(CourseManager) getBean("courseManager");
		DynaActionForm engForm=(DynaActionForm) form;
		
		String seldOid[]=(String[])engForm.get("seldOid");
		
		String score[]=(String[])engForm.get("score");
		String score1[]=(String[])engForm.get("score1");
		String score2[]=(String[])engForm.get("score2");
		String score3[]=(String[])engForm.get("score3");
		
		String score01[]=(String[])engForm.get("score01");
		String score02[]=(String[])engForm.get("score02");
		String score03[]=(String[])engForm.get("score03");
		String score04[]=(String[])engForm.get("score04");
		String score05[]=(String[])engForm.get("score05");
		String score06[]=(String[])engForm.get("score06");
		String score07[]=(String[])engForm.get("score07");
		String score08[]=(String[])engForm.get("score08");
		String score09[]=(String[])engForm.get("score09");
		String score10[]=(String[])engForm.get("score10");
		
		String score16[]=(String[])engForm.get("score16");
		
		Seld seld;
		for(int i=0; i<seldOid.length; i++){
			
			try{
				seld=(Seld) manager.hqlGetBy("FROM Seld WHERE Oid="+seldOid[i]).get(0);
				if(!score[i].trim().equals("")){
					seld.setScore(Double.parseDouble(score[i]));
				}else{
					seld.setScore(null);
				}				
				if(!score1[i].trim().equals("")){
					seld.setScore1(Double.parseDouble(score1[i]));
				}else{
					seld.setScore1(null);
				}				
				if(!score2[i].trim().equals("")){
					seld.setScore2(Double.parseDouble(score2[i]));
				}else{
					seld.setScore2(null);
				}
				
				if(!score3[i].trim().equals("")){
					seld.setScore3(Double.parseDouble(score3[i]));
				}else{
					seld.setScore3(null);
				}					
				
				if(!score01[i].trim().equals("")){
					seld.setScore01(Double.parseDouble(score01[i]));
				}else{
					seld.setScore01(null);
				}
				
				if(!score02[i].trim().equals("")){
					seld.setScore02(Double.parseDouble(score02[i]));
				}else{
					seld.setScore02(null);
				}
				
				if(!score03[i].trim().equals("")){
					seld.setScore03(Double.parseDouble(score03[i]));
				}else{
					seld.setScore03(null);
				}
				
				if(!score04[i].trim().equals("")){
					seld.setScore04(Double.parseDouble(score04[i]));
				}else{
					seld.setScore04(null);
				}
				
				if(!score05[i].trim().equals("")){
					seld.setScore05(Double.parseDouble(score05[i]));
				}else{
					seld.setScore05(null);
				}
				
				if(!score06[i].trim().equals("")){
					seld.setScore06(Double.parseDouble(score06[i]));
				}else{
					seld.setScore06(null);
				}
				
				try{
					//介面上沒有利用到的欄位
					if(!score07[i].trim().equals("")){
						seld.setScore07(Double.parseDouble(score07[i]));
					}else{
						seld.setScore07(null);
					}
				}catch(Exception e){
					
				}
				
				if(!score08[i].trim().equals("")){
					seld.setScore08(Double.parseDouble(score08[i]));
				}else{
					seld.setScore08(null);
				}
				if(!score09[i].trim().equals("")){
					seld.setScore09(Double.parseDouble(score09[i]));
				}else{
					seld.setScore09(null);
				}
				
				try{
					if(!score10[i].trim().equals("")){
						seld.setScore10(Double.parseDouble(score10[i]));
					}else{
						seld.setScore10(null);
					}
				}catch(Exception e){}				
				
				try{
					if(!score16[i].trim().equals("")){
						seld.setScore16(Double.parseDouble(score16[i]));
					}else{
						seld.setScore16(null);
					}
				}catch(Exception e){}
				
				
				manager.updateObject(seld);
			}catch(Exception e){
				e.printStackTrace();
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "儲存中發現問題: "+e));
			}
		}
		
		if(!error.isEmpty()){
			saveErrors(request, error);
		}else{
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "儲存完成，您可以繼續編輯或 <input type=button name=method value=離開 onClick=location.href='/CIS/Teacher/ScoreManager.do' class=gCancel>"));
			
		}
		saveMessages(request, msg);		
		return editScore(mapping, form, request, response);
	}
	
	/**
	 * 體育
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward ok(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg=new ActionMessages();		//建立共用訊息
		ActionMessages error=new ActionMessages();	//建立共用錯誤訊息
		//HttpSession session=request.getSession(false);
		CourseManager manager=(CourseManager) getBean("courseManager");
		DynaActionForm engForm=(DynaActionForm) form;
		
		String seldOid[]=(String[])engForm.get("seldOid");
		
		String score[]=(String[])engForm.get("score");
		String score1[]=(String[])engForm.get("score1");
		String score2[]=(String[])engForm.get("score2");
		String score3[]=(String[])engForm.get("score3");
		
		Seld seld;
		for(int i=0; i<seldOid.length; i++){
			
			try{
				seld=(Seld) manager.hqlGetBy("FROM Seld WHERE Oid="+seldOid[i]).get(0);
				if(!score[i].trim().equals("")){
					seld.setScore(Double.parseDouble(score[i]));
				}else{
					seld.setScore(null);
				}				
				if(!score1[i].trim().equals("")){
					seld.setScore1(Double.parseDouble(score1[i]));
				}else{
					seld.setScore1(null);
				}				
				if(!score2[i].trim().equals("")){
					seld.setScore2(Double.parseDouble(score2[i]));
				}else{
					seld.setScore2(null);
				}
				
				if(!score3[i].trim().equals("")){
					seld.setScore3(Double.parseDouble(score3[i]));
				}else{
					seld.setScore3(null);
				}
				manager.updateObject(seld);
			}catch(Exception e){
				e.printStackTrace();
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "儲存中發現問題: "+e));
			}
		}
		
		if(!error.isEmpty()){
			saveErrors(request, error);
		}else{
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "儲存完成，您可以繼續編輯或 <input type=button name=method value=離開 onClick=location.href='/CIS/Teacher/ScoreManager.do' class=gCancel>"));
		}
		saveMessages(request, msg);		
		return editScore(mapping, form, request, response);
	}
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Save", "save");
		map.put("OK", "ok");
		map.put("EditScore", "editScore");
		map.put("EditPro", "editPro");
		return map;
	}

}