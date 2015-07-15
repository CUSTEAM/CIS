package tw.edu.chit.struts.action.course;

import java.util.ArrayList;
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

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.DtimeClass;
import tw.edu.chit.model.DtimeExam;
import tw.edu.chit.model.DtimeTeacher;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Opencs;
import tw.edu.chit.model.Regs;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.SummerManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Global;
import tw.edu.chit.util.Toolket;

/**
 * 課程管理
 * @author JOHN
 *
 */
public class OpenCourseAction extends BaseLookupDispatchAction {
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//HttpSession session = request.getSession(false);

		clearAll(mapping, form, request, response);

		setContentPage(request.getSession(false), "course/OpenCourse.jsp");
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
		map.put("CreateBaseld", "createBaseld");
		map.put("ChangeOpenType", "changeOpenType");
		
		map.put("First", "first");
		map.put("Prev", "prev");
		map.put("Next", "next");
		map.put("Last", "last");
		return map;
	}
	
	/**
	 * 建立基本選課
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward createBaseld(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		SummerManager summerManager = (SummerManager) getBean("summerManager");
		DynaActionForm setUpDtime = (DynaActionForm) form;
		String createSeldType = (String) setUpDtime.get("createSeldType");
		
		//取得勾選的課程
		List list=getDtimEditList(request);
		
		//無選擇的狀況
		if(list.size()<1){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "您要建立哪一門課的學生?"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		Dtime dtime;
			
			//使用者勾選建立下學年學生
		if(createSeldType.equals("next")){
			for(int i=0; i<list.size(); i++){
				dtime=(Dtime)list.get(i);
				StringBuffer stDepartClass=new StringBuffer(dtime.getDepartClass());
				//年級退1
				Integer nextGrade=Character.getNumericValue(stDepartClass.charAt(4))-1;
				char c=nextGrade.toString().charAt(0);				
				stDepartClass.setCharAt(4, c); // <---------
				if(nextGrade<1){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "找不到"+stDepartClass+"這個班級..."));
					saveErrors(request, error);
					return mapping.findForward("Main");
				}
				
				manager.executeSql("DELETE FROM Seld WHERE Dtime_oid='"+dtime.getOid()+"' AND student_no IN(SELECT student_no FROM stmd WHERE depart_class='"+dtime.getDepartClass()+"')");
				manager.executeSql("DELETE FROM Regs WHERE Dtime_oid='"+dtime.getOid()+"' AND student_no IN(SELECT student_no FROM stmd WHERE depart_class='"+dtime.getDepartClass()+"')");			
				
				//清掉下學年本班學生
				manager.executeSql("DELETE FROM Seld WHERE Dtime_oid='"+dtime.getOid()+"' AND student_no IN(SELECT student_no FROM stmd WHERE depart_class='"+stDepartClass+"')");
				manager.executeSql("DELETE FROM Regs WHERE Dtime_oid='"+dtime.getOid()+"' AND student_no IN(SELECT student_no FROM stmd WHERE depart_class='"+stDepartClass+"')");		
				//取得下學年學生
				List tmpStudents=manager.ezGetBy("SELECT student_no FROM stmd WHERE depart_class='"+stDepartClass+"'");
				Seld seld;
				Regs regs;
				
				//重新建立seld, regs
				for(int j=0; j<tmpStudents.size(); j++){
					seld=new Seld();
					seld.setCredit(dtime.getCredit());
					seld.setDtimeOid(dtime.getOid());
					seld.setOpt(dtime.getOpt());
					seld.setStudentNo(((Map)tmpStudents.get(j)).get("student_no").toString());
					seld.setElearnDilg(0);
					summerManager.saveObj(seld);
					
					regs=new Regs();
					regs.setStudentNo(((Map)tmpStudents.get(j)).get("student_no").toString());
					regs.setDtimeOid(dtime.getOid());
					summerManager.saveObj(regs);
				}

				//更新開課主檔內的選課人數以防止有人已使用了這個資料
				Dtime saveDtime=(Dtime)manager.hqlGetBy("FROM Dtime WHERE Oid='"+dtime.getOid()+"'").get(0);
				Integer count=summerManager.ezGetInt("SELECT COUNT(*) FROM Seld WHERE Dtime_oid='"+dtime.getOid()+"'");
				saveDtime.setStuSelect(Short.parseShort(count.toString()));
				try{
					manager.updateObject(saveDtime);
					//summerManager.saveObj(saveDtime);
				}catch(Exception e){
					
				}
				
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "已重新建立"+tmpStudents.size()+"位同學的選課"));
				saveMessages(request, msg);
			}			
		}
			
			//使用者勾選建立本學年學生			
		if(createSeldType.equals("this")){
			for(int i=0; i<list.size(); i++){
				dtime=(Dtime)list.get(i);					
	
				//清掉本班學生seld, regs
				manager.executeSql("DELETE FROM Seld WHERE Dtime_oid='"+dtime.getOid()+"' AND student_no IN(SELECT student_no FROM stmd WHERE depart_class='"+dtime.getDepartClass()+"')");
				manager.executeSql("DELETE FROM Regs WHERE Dtime_oid='"+dtime.getOid()+"' AND student_no IN(SELECT student_no FROM stmd WHERE depart_class='"+dtime.getDepartClass()+"')");
				//重新取得本班學生
				List tmpStudents=manager.ezGetBy("SELECT student_no FROM stmd WHERE depart_class='"+dtime.getDepartClass()+"'");
				
				Seld seld;
				Regs regs;
				for(int j=0; j<tmpStudents.size(); j++){
					//重新建立seld
					seld=new Seld();
					seld.setCredit(dtime.getCredit());
					seld.setDtimeOid(dtime.getOid());
					seld.setOpt(dtime.getOpt());
					seld.setStudentNo(((Map)tmpStudents.get(j)).get("student_no").toString());
					summerManager.saveObj(seld);
					//重新建立regs
					regs=new Regs();
					regs.setStudentNo(((Map)tmpStudents.get(j)).get("student_no").toString());
					regs.setDtimeOid(dtime.getOid());
					
					summerManager.saveObj(regs);
				}
				
				//更新開課主檔內的選課人數以防止有人已使用了這個資料
				Dtime saveDtime=(Dtime)manager.hqlGetBy("FROM Dtime WHERE Oid='"+dtime.getOid()+"'").get(0);
				Integer count=summerManager.ezGetInt("SELECT COUNT(*) FROM Seld WHERE Dtime_oid='"+dtime.getOid()+"'");
				saveDtime.setStuSelect(Short.parseShort(count.toString()));
				try{
					manager.updateObject(saveDtime);
					//summerManager.saveObj(saveDtime);
				}catch(Exception e){
				}
				
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "已重新建立"+tmpStudents.size()+"位同學的選課"));
				saveMessages(request, msg);
			}					
		}

			//勾選刪除本班學生
		if(createSeldType.equals("delete")){
			for(int i=0; i<list.size(); i++){
				dtime=(Dtime)list.get(i);

				//清掉本班學生seld, regs
				manager.executeSql("DELETE FROM Seld WHERE Dtime_oid='"+dtime.getOid()+"' AND student_no IN(SELECT student_no FROM stmd WHERE depart_class='"+dtime.getDepartClass()+"')");
				manager.executeSql("DELETE FROM Regs WHERE Dtime_oid='"+dtime.getOid()+"' AND student_no IN(SELECT student_no FROM stmd WHERE depart_class='"+dtime.getDepartClass()+"')");
				
				//更新選課人數以免有人使用
				Dtime saveDtime=(Dtime)manager.hqlGetBy("FROM Dtime WHERE Oid='"+dtime.getOid()+"'").get(0);
				Integer count=summerManager.ezGetInt("SELECT COUNT(*) FROM Seld WHERE Dtime_oid='"+dtime.getOid()+"'");
				saveDtime.setStuSelect(Short.parseShort(count.toString()));
				manager.updateObject(saveDtime);
			}
			
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "已清除選課"));
			saveMessages(request, msg);
		}
			
			//勾選刪除全部學生
		if(createSeldType.equals("deleteAll")){
			for(int i=0; i<list.size(); i++){
				dtime=(Dtime)list.get(i);
				manager.executeSql("DELETE FROM Seld WHERE Dtime_oid='"+dtime.getOid()+"'");
				manager.executeSql("DELETE FROM Regs WHERE Dtime_oid='"+dtime.getOid()+"'");
				
				Dtime saveDtime=(Dtime)manager.hqlGetBy("FROM Dtime WHERE Oid='"+dtime.getOid()+"'").get(0);
				Integer count=summerManager.ezGetInt("SELECT COUNT(*) FROM Seld WHERE Dtime_oid='"+dtime.getOid()+"'");
				saveDtime.setStuSelect(Short.parseShort(count.toString()));
				manager.updateObject(saveDtime);
			}
		}
		
		setForm((Map)session.getAttribute("mySearchForm"), mapping, form, request, response);
		return  list(mapping, form, request, response);
	}

	/**
	 * 完成
	 */
	public ActionForward complete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		ActionMessages msg = new ActionMessages();		//建立共用訊息		
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.removeCompleteSub"));
		saveMessages(request, msg);
		Toolket.resetCheckboxCookie(response, "dtimes");
		
		//清除排考資訊
		clearAll(mapping, form, request, response);
		setForm((Map)session.getAttribute("mySearchForm"), mapping, form, request, response);
		
		setForm((Map)session.getAttribute("mySearchForm"), mapping, form, request, response);
		return  list(mapping, form, request, response);
	}

	/**
	 * 重設
	 */
	public ActionForward clear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Toolket.resetCheckboxCookie(response, "dtimes");
		clearAll(mapping, form, request, response);

		HttpSession session = request.getSession(false);
		session.setAttribute("editMode", false);
		session.setAttribute("createMode", false);
		session.setAttribute("checkError", false);
		setContentPage(request.getSession(false), "course/OpenCourse.jsp");
		return mapping.findForward("Main");
	}


	/**
	 * 準備啟動新增
	 */
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			HttpSession session = request.getSession(false);
			DynaActionForm setUpDtime = (DynaActionForm) form;
			session.setAttribute("mySearchForm", setMap(setUpDtime.getMap()));
			ActionMessages msg = new ActionMessages();		//建立共用訊息
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息

			String departClass = (String) setUpDtime.get("classInCharge2"); // class
			String cscode = (String) setUpDtime.get("courseNumber");
			String courseName = (String) setUpDtime.get("courseName");
			String techid = (String) setUpDtime.get("teacherId");
			String teacherName = (String) setUpDtime.get("teacherName");
			String term = (String) setUpDtime.get("sterm");
			String opt = (String) setUpDtime.get("choseType");
			String open = (String) setUpDtime.get("open");
			String elearning = (String) setUpDtime.get("elearning");
			//輸入不完整
			if(departClass.equals("All") || departClass.equals("") || cscode.equals("") || term.equals("%")|| opt.equals("%")|| courseName.equals("")){
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.setCourseName.createNullError"));
				saveErrors(request, error);
			}

			//沒有指定課程型態為一般或遠距(選到所有課程)
			if(elearning.equals("%")){
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.NoType", techid));
				saveErrors(request, error);
			}
			//沒有指定課程型態為開放或不開放(選到所有規則)
			if(open.equals("%")){
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.NoRule", techid));
				saveErrors(request, error);
			}
			//開課教師是否存在 - 返回錯誤
			if(techid.trim()!=""){
				//若有設定才檢查
				if(checkTeacherIsEmpty(techid)){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.Noteacher", techid));
					saveErrors(request, error);
				}
			}

			if(!error.isEmpty()){
				return mapping.findForward("Main");
			}
			CourseManager manager = (CourseManager) getBean("courseManager");
			List<Clazz> aDepartClass = manager.getAclassNameForCreateDtime(setUpDtime.get(
					"classInCharge2").toString());
			Clazz aClazz = (Clazz) aDepartClass.get(0);

			if(manager.checkRushCourseForOpenCourse(departClass,cscode, term, "0").size()>0){
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.ReOpenTerm"));
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.Neglect"));
				saveMessages(request, msg);
			}

			session.setAttribute("SdepartClass", departClass);
			session.setAttribute("SclassName2", aClazz.getClassName());
			session.setAttribute("Sterm", term);
			session.setAttribute("Sopt", opt);
			session.setAttribute("Scscode", cscode);
			session.setAttribute("Scscode2", courseName);
			session.setAttribute("Stechid", techid);
			session.setAttribute("StechName", teacherName);
			session.setAttribute("dtimeClasses", null);	//儲存上課時間物件
			session.setAttribute("dtimeClasSize", 0);	//儲存上課時間物件長度(頁面判斷用)
			session.setAttribute("opencsList", null); 				//儲存選課規則物件
			session.setAttribute("opencsListSize", 0);
			session.setAttribute("dtimeTeachers", null); 			//儲存一科目多教師物件
			session.setAttribute("dtimeTeacherSize", 0); //儲存一科目多教師長度(取得按鈕數量的初始值)
			session.setAttribute("dtimeExamList", null); 			//儲存一科目多教師物件
			session.setAttribute("dtimeExamListSize", 0); //儲存一科目多教師長度(取得按鈕數量的初始值)
			session.setAttribute("Sopen", (String) setUpDtime.get("open"));
			session.setAttribute("Selearning", elearning);
			session.setAttribute("Sextrapay", (String) setUpDtime.get("extrapay"));
			boolean createMode = true;
			session.setAttribute("createMode", createMode);

		return mapping.findForward("Main");
	}


	/**
	 * 啟動新增
	 */
	public ActionForward createRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm setUpDtime = (DynaActionForm) form;
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		OpenCourseActionServices openCourseActionService = new OpenCourseActionServices();
		boolean createMode;
		boolean editMode;
		boolean viewMode;
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息

		//取得頁面上的所有物件並設定對應物件
		String departClass = (String) setUpDtime.get("classNo");	//頁面資訊-班級代碼
		String className = (String) setUpDtime.get("className");	//頁面資訊-班級名稱
		String cscode = (String) setUpDtime.get("courseNumber");	//頁面資訊-課程代碼
		String courseName = (String) setUpDtime.get("courseName");	//頁面資訊-課程名稱
		String techid = (String) setUpDtime.get("teacherId");		//頁面資訊-教師代碼
		String teacherName = (String) setUpDtime.get("teacherName");//頁面資訊-教師名稱
		String term = (String) setUpDtime.get("sterm");				//頁面資訊=學期
		String opt=(String)setUpDtime.get("choseType");				//頁面資訊-選別
		String thour = (String) setUpDtime.get("thour");			//頁面資訊-時數
		String credit = (String) setUpDtime.get("credit");			//頁面資訊-學分數
		String selectLimit = (String) setUpDtime.get("selectLimit");//頁面資訊-人數上限
		String[] examDate=(String[])setUpDtime.get("examDate");			//頁面資訊-考試日期
		String[] Ebegin=(String[]) setUpDtime.get("examTime");			//頁面資訊-考試節次
		String[] Etech=(String[]) setUpDtime.get("examEmplId");			//頁面資訊-監考人員
		String[] EtechName=(String[]) setUpDtime.get("examEmplName");	//頁面資訊-監考姓名
		String[] Eplace=(String[])setUpDtime.get("examPlace");			//頁面資訊-考試地點
		String week[] = (String[]) setUpDtime.get("week");			//頁面資訊-上課日
		String begin[] = (String[]) setUpDtime.get("begin");		//頁面資訊-開始節次
		String end[] = (String[]) setUpDtime.get("end");			//頁面資訊-結束節次
		String place[] = (String[]) setUpDtime.get("place");		//頁面資訊-上課地點
		String cidno[]= (String[]) setUpDtime.get("cidno");			//頁面資訊-選課規則-校區
		String sidno[]= (String[]) setUpDtime.get("sidno");			//頁面資訊-選課規則-學制
		String didno[]= (String[]) setUpDtime.get("didno");			//頁面資訊-選課規則-系所
		String grade[]= (String[]) setUpDtime.get("grade");			//頁面資訊-選課規則-年級
		String departClassM[]= (String[]) setUpDtime.get("departClass");//頁面資訊-選課規則-班級
		String techidM[]= (String[]) setUpDtime.get("techidM");		//頁面資訊-一科目多教師-教師代碼
		String hoursM[]= (String[]) setUpDtime.get("hoursM");		//頁面資訊-一科目多教師-授課時數
		String techM[]= (String[]) setUpDtime.get("techM");			//頁面資訊-一科目多教師-授課
		String fillscoreM[]= (String[]) setUpDtime.get("fillscoreM");//頁面資訊-一科目多教師-評分
		String open = (String) setUpDtime.get("open");
		String elearning = (String) setUpDtime.get("elearning");
		String extrapay = (String) setUpDtime.get("extrapay");
		/**
		 * 一般邏輯檢查(外層檢查)
		 */
		// 判斷重複班級重複課程 - 返回訊息
		if(manager.checkRushCourseForOpenCourse(departClass,cscode, term, "0").size()>0){
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.ReOpenTerm"));
			saveMessages(request, msg);
		}
		//開課教師是否存在 - 返回錯誤
		if(techid.trim()!=""){
			//若有設定才檢查
			if(checkTeacherIsEmpty(techid)){
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.Noteacher", techid));
				saveErrors(request, error);
			}
		}
		//	一科目多教師是否存在 - 返回錯誤
		for(int i=0; i<techidM.length; i++){
			if(techidM[i].trim()!=""){
				if(checkTeacherIsEmpty(techidM[i])){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.Noteacher", techidM[i]));
					saveErrors(request, error);
				}
			}
		}
		//	排考教師是否存在  - 返回錯誤
		/*
		for(int i=0; i<Etech.length; i++){
			if(!Etech[i].trim().equals("")){
				if(checkTeacherIsEmpty(Etech[i])){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.openCourse.Noteacher", Etech[i]));
					saveErrors(request, error);
				}
			}
		}

		//	排考教室是否存在 - 返回錯誤		
		for(int i=0; i<Eplace.length; i++){
			if(!Eplace[i].equals("")){
				if(checkPlaceIsEmpty(Eplace[i].trim()).size()<1){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.openCourse.NoRoomSub", Eplace));
					saveErrors(request, error);
				}
			}
		}

		// 教室同時排考
		for(int i=0; i<Eplace.length; i++){
			if(!Eplace[i].equals("")&& !examDate[i].equals("")&& !Ebegin.equals("")){
				String[] tmpStr=Toolket.parseDate(examDate[i]).toLocaleString().split(" ");
				if(manager.checkDtimExamPlace("0", tmpStr[0].toString(),
						Eplace[i], Ebegin[i]).size()>1){	//除本班和任何一班外的多餘班
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","<br>&nbsp;&nbsp;&nbsp;教室重複排考 "+examDate[i]+" 第"+Ebegin[i]+"節 "+Eplace[i]));
					saveMessages(request, msg);
				}
			}
		}

		//	教師同時排考
		for(int i=0; i<Etech.length; i++){
			if(!examDate[i].equals("")&& !Etech[i].equals("")&& !Ebegin.equals("")){
				String[] tmpStr=Toolket.parseDate(examDate[i]).toLocaleString().split(" ");
				if(manager.checkDtimExamIdno("0", tmpStr[0].toString(),
						Etech[i], Ebegin[i]).size()>0){	//除本班外不可有任何多餘班
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","<br>&nbsp;&nbsp;&nbsp;教師重複排考 "+examDate[i]+" 第"+Ebegin[i]+"節 "+Etech[i]));
					saveMessages(request, msg);
				}
			}
		}
		*/
			//	排課教室是否存在 - 返回錯誤
			for(int i=0; i<place.length; i++){
				if(place[i].trim()!=""){
					if(checkPlaceIsEmpty(place[i].trim()).size()<1){
						error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.NoRoomSub", place[i]));
						saveErrors(request, error);
					}
				}
			}

		//檢查排課是否矛盾 - 返回錯誤
		if(week.length>0&& begin.length>0&& end.length>0){
			//若有值才檢查
			for(int i=0; i<week.length; i++){
				if(week[i]!=""&& begin[i]!=""&& end[i]!=""){
					if(openCourseActionService.checkTime(begin, end)){
						error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.timeError"));
						saveErrors(request, error);
					}
				}
			}
		}
		//	檢查排課時數多於開課時數 - 返回錯誤
		if(openCourseActionService.checkThours(Integer.parseInt(thour), begin, end)){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.thourError"));
			saveErrors(request, error);
		}
		//	跨選規則模糊 - 返回錯誤
		for(int i=0; i<cidno.length; i++){
			if(cidno[i]!=""|| didno[i]!=""|| sidno[i]!=""|| grade[i]!=""|| departClassM[i]!=""){
				if(cidno[i]==""|| didno[i]==""|| sidno[i]==""|| grade[i]==""|| departClassM[i]==""){
					String opwrong="第 "+i+" 個選課規則";
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidCodeSelection", opwrong));
					saveErrors(request, error);
				}
			}
		}

		/**
		 * 進階邏輯檢查(內層檢查)
		 */
		//檢查開課時段陣列長度
		int Ttmp=0;//檢查教師的次數
		int Ptmp=0;//檢查教室的次數
		for(int i=0; i<week.length; i++){
			//星期、開始/結束節次有經過設定則需要檢查
			if(!week[i].trim().equals("")&& !begin[i].trim().equals("")&& !end[i].trim().equals("")){
				Ttmp=Ttmp+1;
				//教室有經過設定則需要檢查
				if(!place[i].trim().equals("")){
					Ptmp=Ptmp+1;
				}
			}
		}
		//有設定開課時段
		if(Ttmp>0){
			//有設定授課教師
			if(techid.trim()!=""){
				//檢查教師同時段重複授課
				List checkReOpens=manager.checkEmplReOpen(techid, week, begin, end, term, "0");
				if(checkReOpens.size()>0){
					//Object[] checkReOpens.toArray();
					//訊息已存在則加一個換行標籤
					if(!msg.isEmpty()){
						msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("BR"));
					}
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","<br>&nbsp;&nbsp;&nbsp;&nbsp;輸入的學期內同一時間<font color='red'>教師</font>重複授課"));
					Map aCourse;
					for (int i=0 ; i < checkReOpens.size(); i++) {
						aCourse = (Map)checkReOpens.get(i);
						//msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.emplreopenBeginOrEndSub",aCourse.get("week"),aCourse.get("begin"),aCourse.get("ClassName"),aCourse.get("chi_name")));
						msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN4","<br>&nbsp;&nbsp;&nbsp;&nbsp;",
								"<font style='font-weight:100'>"+aCourse.get("className")+" 星期"+aCourse.get("week"), " 第"+aCourse.get("begin"), "節 ~ 第"+aCourse.get("end")+"節 "+aCourse.get("chi_name")+"</font>"));
					}
					saveMessages(request, msg);
				}
			}
			//有設定教室
			if(Ptmp>0){
				//檢查班級教室重複使用
				List checkReOpenRoom=manager.checkReOpenRoom(place, week, begin, end, term, "0");
				if(checkReOpenRoom.size()>0){
					//Object[] checkReOpens.toArray();
					//訊息已存在則加一個換行標籤
					if(!msg.isEmpty()){
						msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("BR"));
					}
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.ReOpenRoom"));
					Map aRoom;
					for (int i=0 ; i < checkReOpenRoom.size(); i++) {
						aRoom = (Map)checkReOpenRoom.get(i);
						//msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.emplreopenBeginOrEndSub",aRoom.get("week"),aRoom.get("begin"),aRoom.get("place"),aRoom.get("chi_name")));
						msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN4","<br>&nbsp;&nbsp;&nbsp;&nbsp;",
								"<font style='font-weight:100'>"+aRoom.get("className")+" 星期"+aRoom.get("week"), " 第"+aRoom.get("begin"), "節 ~ 第"+aRoom.get("end")+"節 "+aRoom.get("chi_name")+"</font>"));
					}
					saveMessages(request, msg);
				}
			}
			//	檢查班級同一時間重複使用
			List checkReOpenClass=manager.checkReopenClass(departClass, week, begin, end, term, "0");
			if(checkReOpenClass.size()>0){
				//Object[] checkReOpens.toArray();
				//訊息已存在則加一個換行標籤
				if(!msg.isEmpty()){
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("BR"));
				}
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","<br>&nbsp;&nbsp;&nbsp;&nbsp;輸入的學期內<font color='red'>同一時間班級</font>重複開課"));
				Map aClass;
				for (int i=0 ; i < checkReOpenClass.size(); i++) {
					aClass = (Map)checkReOpenClass.get(i);
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN4","<br>&nbsp;&nbsp;&nbsp;&nbsp;",
							"<font style='font-weight:100'>"+aClass.get("className")+" 星期"+aClass.get("week"), " 第"+aClass.get("begin"), "節 ~ 第"+aClass.get("end")+"節 "+aClass.get("chi_name")+"</font>"));
				}
				saveMessages(request, msg);
			}
		}
		
		//有錯誤發生返回編輯介面
		if(!error.isEmpty()){
			createMode = true;
			session.setAttribute("createMode", createMode);
			
			session.setAttribute("SdepartClass", departClass);
			session.setAttribute("SclassName2", className);
			session.setAttribute("Scscode", cscode);
			session.setAttribute("Scscode2", courseName);
			session.setAttribute("Stechid", techid);
			session.setAttribute("StechName", teacherName);
			session.setAttribute("Sterm", term);
			session.setAttribute("Sopt", opt);
			session.setAttribute("Sthour", thour);
			session.setAttribute("Scredit", credit);
			session.setAttribute("SselectLimit", selectLimit);
			session.setAttribute("SexamDate", examDate);
			session.setAttribute("SEbegin", Ebegin);
			session.setAttribute("SEtech", Etech);
			session.setAttribute("SexamEmplName", EtechName);//儲存監考教師名稱
			session.setAttribute("SEplace", Eplace);
			session.setAttribute("Sopen", open); //儲存一科目多教師長度(頁面判斷用)
			session.setAttribute("Selearning", elearning); //儲存一科目多教師長度(頁面判斷用)
			session.setAttribute("Sextrapay", extrapay);

			setContentPage(request.getSession(false), "course/OpenCourse.jsp");
			return mapping.findForward("Main");
		}

		/**
		 * 開始寫入資料庫
		 */
		//CourseDAO dao = (CourseDAO)getBean("courseDAO");
		Dtime aDtime =new Dtime();
		//儲存開課基本資料
		aDtime.setCscode(cscode);
		aDtime.setDepartClass(departClass);
		aDtime.setOpt(opt);
		aDtime.setSelectLimit(Short.parseShort(selectLimit));
		aDtime.setTechid(techid);
		aDtime.setThour(Short.parseShort(thour));
		aDtime.setSterm(term);
		aDtime.setCredit(Float.parseFloat(credit));
		aDtime.setOpen(Byte.parseByte(open));
		aDtime.setElearning(elearning);
		aDtime.setExtrapay(extrapay);
		
		try{
			manager.updateDtime(aDtime);
		}catch(Exception e){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","<br>儲存失敗"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		
		
		aDtime.setChiName2(courseName);
		aDtime.setDepartClass2(className);
		aDtime.setElearning(elearning);

		session.setAttribute("newDtime", aDtime);

		for(int i=0; i<week.length; i++){
			//如果排課時段陣列有值才開始儲存
			if(week[i]!=""&& begin[i]!="" && end[i]!=""){
				DtimeClass aDtimeClass=new DtimeClass();
				aDtimeClass.setBegin(begin[i]);
				aDtimeClass.setDtimeOid(aDtime.getOid());
				aDtimeClass.setEnd(end[i]);
				aDtimeClass.setPlace(place[i]);
				aDtimeClass.setWeek(Integer.parseInt(week[i]));
				manager.saveDtimeClass(aDtimeClass);

				session.setAttribute("dtimeClassCount",
						aDtime.getThour()-manager.countDtimeClassBy(aDtime.getOid().toString())); //取得未排課節次提供頁面警告
			}
		}
		//重新計算未排時數

			List dtimeClasses = manager.getDtimeClassListForOpenCourse(aDtime.getOid().toString());//取得上課時間物件
			session.setAttribute("dtimeClasses", dtimeClasses);	//儲存上課時間物件
			session.setAttribute("dtimeClasSize", dtimeClasses.size());	//儲存上課時間物件長度(頁面判斷用)

		//儲存一科目多教師
		//教師欄位有值則開始儲存
		if(techidM.length>0){
			DtimeTeacher dtimeTeacher=new DtimeTeacher();

			for(int i=0; i<techidM.length; i++){
				if(techidM[i]!=""){
					if(techM[i]==""){
						techM[i]="0";
					}
					if(fillscoreM[i]==""){
						fillscoreM[i]="0";
					}
					if(hoursM[i]==""){
						hoursM[i]="0";
					}
					if(techidM[i]!=""){

						dtimeTeacher.setDtimeOid(aDtime.getOid());
						dtimeTeacher.setTeachId(techidM[i]);
						dtimeTeacher.setHours(Float.parseFloat(hoursM[i]));
						dtimeTeacher.setTeach(Byte.parseByte(techM[i]));
						dtimeTeacher.setFillscore(Byte.parseByte(fillscoreM[i]));
						manager.SaveDtimeTeacher(dtimeTeacher);
					}
				}
			}

			List dtimeTeachers=manager.getDtimeTeacherBy(aDtime.getOid().toString());	//取得一科目多教師物件
			session.setAttribute("dtimeTeachers", dtimeTeachers); 			//儲存一科目多教師物件
			session.setAttribute("dtimeTeacherSize", dtimeTeachers.size()); //儲存一科目多教師長度(頁面判斷用)

		}
		//儲存考試資訊

			List dtimeExamList=new ArrayList();

			for(int i=0; i<examDate.length; i++){
				if(!examDate[i].trim().equals("")){
					DtimeExam dtimeExam=new DtimeExam();

					dtimeExam.setDtimeOid(aDtime.getOid());
					dtimeExam.setExamDate(Toolket.parseDate(examDate[i]));

					dtimeExam.setEbegin(Ebegin[i]);
					dtimeExam.setExamEmpl(Etech[i]);
					dtimeExam.setPlace(Eplace[i]);
					manager.saveDtimeExam(dtimeExam);
					dtimeExam.setExamDate2(examDate[i]);
					dtimeExam.setTechName(EtechName[i]);
					dtimeExamList.add(dtimeExam);
				}
			}
			session.setAttribute("dtimeExamList", dtimeExamList);
			session.setAttribute("dtimeExamListSize", dtimeExamList.size());// 取得按鈕的數量

		//儲存選課規則
		Opencs opencs=new Opencs();

		int tmp=0;
		for(int i=0; i<cidno.length; i++){
			//若任何欄位有值則存
			if(cidno[i]!=""|| sidno[i]!=""|| didno[i]!=""|| grade[i]!=""|| departClassM[i]!=""){
				opencs.setDtimeOid(aDtime.getOid());
				opencs.setCidno(cidno[i]);
				opencs.setSidno(sidno[i]);
				opencs.setDidno(didno[i]);
				opencs.setGrade(grade[i]);
				opencs.setClassNo(departClassM[i]);
				opencs.setDepartClass("");

				manager.saveOpencsBy(opencs);
				tmp=tmp+1;
			}
		}
		//	更新Dtime的跨選欄位
		if(tmp>=1){
			//aDtime.setOid(Integer.parseInt(dtimeOid));
			aDtime.setCrozz("*");
			manager.updateDtime(aDtime);
			//dao.reload(aDtime);
		}

		List opencsList=manager.getOpencs(aDtime.getOid().toString());	//取得選課規則物件
		session.setAttribute("opencsList", opencsList); 				//儲存選課規則物件
		session.setAttribute("opencsListSize", opencsList.size());		//儲存長度(頁按鈕數量)
		session.setAttribute("dtimeClassCount",
				aDtime.getThour()-manager.countDtimeClassBy(aDtime.getOid().toString())); //取得未排課節次提供頁面警告

		if(!msg.isEmpty()){
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.setCourseName.createCompleteSub"));
			saveMessages(request, msg);
		}else{
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.setCourseName.createComplete"));
			saveMessages(request, msg);
		}
		//新增作業全部結束

		viewMode=true;
		createMode=false;
		editMode=true;

		session.setAttribute("SdepartClass", departClass);
		session.setAttribute("SclassName2", className);
		session.setAttribute("Scscode", cscode);
		session.setAttribute("Scscode2", courseName);
		session.setAttribute("Stechid", techid);
		session.setAttribute("StechName", teacherName);
		session.setAttribute("Sterm", term);
		session.setAttribute("Sopt", opt);
		session.setAttribute("Sthour", thour);
		session.setAttribute("Scredit", credit);
		session.setAttribute("SselectLimit", selectLimit);
		session.setAttribute("SexamDate", examDate);
		session.setAttribute("SEbegin", Ebegin);
		session.setAttribute("SEtech", Etech);
		session.setAttribute("SexamEmplName", EtechName);//儲存監考教師名稱
		session.setAttribute("SEplace", Eplace);
		session.setAttribute("Sopen", open); //儲存一科目多教師長度(頁面判斷用)
		session.setAttribute("Selearning", elearning); //儲存一科目多教師長度(頁面判斷用)
		session.setAttribute("Sextrapay", extrapay);

		session.setAttribute("viewMode", viewMode);
		session.setAttribute("createMode", createMode);
		session.setAttribute("editMode", editMode);
		setContentPage(request.getSession(false), "course/OpenCourse.jsp");
		return mapping.findForward("Main");
	}


	/**
	 * 準備啟動刪除
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//取得搜尋列表
		HttpSession session = request.getSession(false);
		//DynaActionForm setUpDtime = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		//取得勾選的項
		List<Dtime> selDtimes = getDtimEditList(request);
		ActionMessages error = new ActionMessages();

		//沒有選擇的情形
		if(selDtimes.size()<1){

			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"Course.setCourseName.modifyTooMuch"));
			saveErrors(request, error);

			return mapping.findForward("Main");
		}
		String dtimeOids[] = new String[selDtimes.size()];
		Dtime dtime;
		for(int i=0; i<selDtimes.size(); i++){
			dtime=(Dtime)selDtimes.get(i);
			dtimeOids[i]=dtime.getOid().toString();
		}


		boolean createMode = false;
		boolean viewMode = false;
		boolean editMode = false;
		boolean deleteMode = true;
		session.setAttribute("delTeachers", manager.getTeachersBy(dtimeOids));
		session.setAttribute("delStudents", manager.getSeldBy(dtimeOids));
		session.setAttribute("deleteDtimeOid", dtimeOids);
		session.setAttribute("createMode", createMode);
		session.setAttribute("viewMode", viewMode);
		session.setAttribute("editMode", editMode);
		session.setAttribute("deleteMode", deleteMode);
		return mapping.findForward("Main");
	}


	/**
	 * 啟動刪除
	 */
	public ActionForward deleteConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息

		HttpSession session = request.getSession(false);
		//DynaActionForm setUpDtime = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");

		String deleteDtimeOid[]=(String[]) session.getAttribute("deleteDtimeOid");

		int counter=0;
		//刪除課程相關
		for(int i=0; i<deleteDtimeOid.length; i++){
			try{
				System.out.println("DELETE FROM Dtime WHERE Oid="+deleteDtimeOid[i]);
				manager.removeDtime(deleteDtimeOid[i]);
				manager.executeSql("DELETE FROM Dtime WHERE Oid="+deleteDtimeOid[i]);
			}catch(Exception e){
				e.printStackTrace();
			}			
			counter=counter+1;
		}

		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.removeComplete", counter));
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.removeCompleteSub"));
		saveMessages(request, msg);
		boolean createMode = false;
		boolean viewMode = false;
		boolean editMode = false;
		boolean deleteMode = false;
		clearAll(mapping, form, request, response);
		
		session.setAttribute("createMode", createMode);
		session.setAttribute("viewMode", viewMode);
		session.setAttribute("editMode", editMode);
		session.setAttribute("deleteMode", deleteMode);
		
		setForm((Map)session.getAttribute("mySearchForm"), mapping, form, request, response);
		return  list(mapping, form, request, response);
	}


	/**
	 * 取得搜尋列表
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		boolean createMode = false;
		boolean checkError = false;
		boolean editMode = false;
		HttpSession session = request.getSession(false);
		DynaActionForm setUpDtime = (DynaActionForm) form;
		
		session.setAttribute("mySearchForm", setMap(setUpDtime.getMap()));
		
		String schoolInCharge2=setUpDtime.getString("schoolInCharge2"); // rule
		String deptInCharge2=setUpDtime.getString("deptInCharge2"); // dept
		String departClass=setUpDtime.getString("classInCharge2"); // class
		String cscode=setUpDtime.getString("courseNumber");
		String techid=setUpDtime.getString("teacherId");
		String term=setUpDtime.getString("sterm");
		String choseType=setUpDtime.getString("choseType");
		String open=setUpDtime.getString("open");
		String elearning=setUpDtime.getString("elearning");
		String classLess=setUpDtime.getString("classLess");
		String readOnlyType=setUpDtime.getString("readOnlyType");
		
		String chi_name=setUpDtime.getString("courseName");
		String cname=setUpDtime.getString("teacherName");
		String Oid=setUpDtime.getString("Oid");		
		Toolket.resetCheckboxCookie(response, "dtimes");
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		ActionMessages msg = new ActionMessages();		//建立共用訊息	
		
		// 正常模式
		if(readOnlyType.trim().equals("false")){
			// 載入使用者負責班級
			UserCredential credential = (UserCredential) session.getAttribute("Credential");
			Clazz[] classes = credential.getClassInChargeAry();

			if (schoolInCharge2.equals("All")) {
				schoolInCharge2 = "%";
			}
			if (deptInCharge2.equals("All")) {
				deptInCharge2 = "%";
			}

			if (departClass.trim().equals("All")|| departClass.trim().equals("")) {
				departClass = "%";
			}

			//搜尋
			try{
				
				if(Oid.trim().equals("")){
					session.setAttribute("dtimeList", manager.getDtimeBy(classes, cscode, techid, term, choseType, open, elearning, classLess, chi_name, cname));
				}else{					
					List dtimeList=manager.ezGetBy("SELECT ce.idno2, d.Select_Limit, d.techid, d.Oid, cs.ClassName, " +
							"d.depart_class, c.chi_name, c.cscode, c.eng_name, e.cname, d.opt, d.credit, d.thour, d.stu_select, d.open, " +
							"d.elearning, d.extrapay, d.crozz FROM (Dtime d LEFT OUTER JOIN empl e ON d.techid=e.idno) LEFT OUTER JOIN " +
							"CodeEmpl ce ON ce.idno=e.unit, Class cs, Csno c WHERE d.cscode=c.cscode AND cs.ClassNo=d.depart_class AND d.Oid="+Oid);
					
					if(dtimeList.size()>0){
						((Map)dtimeList.get(0)).put("oid",((Map)dtimeList.get(0)).get("Oid"));
						((Map)dtimeList.get(0)).put("departClass2", ((Map)dtimeList.get(0)).get("ClassName"));
						((Map)dtimeList.get(0)).put("departClass", ((Map)dtimeList.get(0)).get("depart_class"));
						if(((Map)dtimeList.get(0)).get("chi_name").toString().length()>=8){
							((Map)dtimeList.get(0)).put("chiName2", ((Map)dtimeList.get(0)).get("chi_name").toString().subSequence(0, 8)+"..");
						}else{
							((Map)dtimeList.get(0)).put("chiName2", ((Map)dtimeList.get(0)).get("chi_name"));
						}
						((Map)dtimeList.get(0)).put("chiName3", ((Map)dtimeList.get(0)).get("chi_name"));
						((Map)dtimeList.get(0)).put("cscode", ((Map)dtimeList.get(0)).get("cscode"));
						((Map)dtimeList.get(0)).put("techName", ((Map)dtimeList.get(0)).get("cname"));
						((Map)dtimeList.get(0)).put("credit", ((Map)dtimeList.get(0)).get("credit"));
						((Map)dtimeList.get(0)).put("thour", ((Map)dtimeList.get(0)).get("thour"));
						((Map)dtimeList.get(0)).put("eng_name", ((Map)dtimeList.get(0)).get("eng_name"));
						((Map)dtimeList.get(0)).put("idno2", ((Map)dtimeList.get(0)).get("idno2"));
						((Map)dtimeList.get(0)).put("Select_Limit", ((Map)dtimeList.get(0)).get("Select_Limit"));
						((Map)dtimeList.get(0)).put("techid", ((Map)dtimeList.get(0)).get("techid"));
						((Map)dtimeList.get(0)).put("stuSelect", manager.countSelect(((Map)dtimeList.get(0)).get("Oid").toString()));
						((Map)dtimeList.get(0)).put("openName", manager.getOpen((Boolean)((Map)dtimeList.get(0)).get("open")));
						((Map)dtimeList.get(0)).put("opt2", Global.CourseOpt.getProperty(((Map)dtimeList.get(0)).get("opt").toString()));
						((Map)dtimeList.get(0)).put("elearningName", manager.getElearningName(((Map)dtimeList.get(0)).get("elearning").toString()));
						((Map)dtimeList.get(0)).put("open2", manager.getCross(((Map)dtimeList.get(0)).get("Oid").toString()));
						((Map)dtimeList.get(0)).put("extrapay2", manager.getExtrapay(((Map)dtimeList.get(0)).get("extrapay").toString()));
						((Map)dtimeList.get(0)).put("icon", "<a href='/CIS/StudentSel?Oid="+((Map)dtimeList.get(0)).get("Oid")+"'><img src='images/ico_file_excel1.png' border='0' title='選課學生'></a>" +
								"<a href='/CIS/Print/teacher/SylDoc.do?Oid="+((Map)dtimeList.get(0)).get("Oid")+"'><img src='images/ico_file_excel1.png' border='0' title='綱要下載'></a>" +
								"<a href='/CIS/Print/teacher/IntorDoc.do?Oid="+
								((Map)dtimeList.get(0)).get("Oid")+"'><img src='images/ico_file_excel1.png' border='0' title='簡介下載'></a>");
						
					}
					
					session.setAttribute("dtimeList", dtimeList);
				}
				//}
				
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","搜尋完成!"));
				saveMessages(request, msg);
				
				session.setAttribute("createMode", createMode);
				session.setAttribute("checkError", checkError);
				session.setAttribute("editMode", editMode);
				session.setAttribute("classLess", classLess);
				
				session.setAttribute("xless", classLess);
				
				
				boolean readonly=false;
				session.setAttribute("readonly", readonly);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}else if(readOnlyType.trim().equals("true")){
			//呼叫搜尋
			session.setAttribute("dtimeList", manager.getDtimeBy(
					cscode, techid, term, choseType, open, elearning, classLess, chi_name, cname));

			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","唯讀搜尋完成! 但是這些結果只提供檢視"));
			saveMessages(request, msg);
			session.setAttribute("createMode", createMode);
			session.setAttribute("checkError", checkError);
			session.setAttribute("editMode", editMode);
			boolean readonly=true;
			session.setAttribute("readonly", readonly);
		}
		

		session.setAttribute("xterm", term);
		session.setAttribute("xyear", manager.getSchoolYear());
		
		return mapping.findForward("Main");
	}

	/**
	 * 修改準備啟動
	 */
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);
		ActionMessages error = new ActionMessages();

		Dtime dtime = null;
		//判斷來源是由新增模式進入或查詢模式進入
		if(session.getAttribute("newDtime")!=null&& session.getAttribute("newDtime")!="hi"){
			//由新增模式進入則取得新增模式儲存的po
			dtime=(Dtime) (session.getAttribute("newDtime"));
		}else{
			//查詢模式進來的要過濾掉勾選超過1筆以上資料的情形
			List selDtimes=new ArrayList();
			selDtimes = getDtimEditList(request);
			if (selDtimes.size() != 1) {

				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.setCourseName.modifyTooMuch"));
				saveErrors(request, error);
				return mapping.findForward("Main");

			}
			//使用查詢模式中勾選的oid查一個po

			dtime = (Dtime) selDtimes.get(0);//取得已勾選的課程所有資訊


		}
		//List editDtime = manager.getDtimeForEditDtime(dtime.getOid().toString());	//取得勾選的課程物件
		List empList = manager.getEmplForOpenCourse(dtime.getTechid());	//取得勾選的授課教師物件
		List dtimeExamList = manager.getDtimeExamListForOpenCourse(dtime.getOid().toString());//取得勾選的考試物件

		session.setAttribute("dtimeExamList", dtimeExamList);
		session.setAttribute("dtimeExamListSize", dtimeExamList.size());// 取得按鈕的數量

		//從教師物件中取得相關資訊
		if (empList.size() > 0) {
			//無教師的課程儲存空白資料
			Empl empl = (Empl) empList.get(0);
			session.setAttribute("Stechid", empl.getIdno());
			session.setAttribute("StechName", empl.getCname());
		} else {
			//有教師的課程儲存實體資料
			session.setAttribute("Stechid", "");	//儲存教師代碼
			session.setAttribute("StechName", "");	//儲存教師名稱
		}

		session.setAttribute("dtimeClassCount",
				dtime.getThour()-manager.countDtimeClassBy(dtime.getOid().toString())); //取得未排課節次提供頁面警告

		session.setAttribute("TheDtime", dtime);
		session.setAttribute("oid", dtime.getOid());					//*****儲存課程主鍵*****

		session.setAttribute("SdepartClass", dtime.getDepartClass());	//儲存班級代碼
		session.setAttribute("SclassName2", dtime.getDepartClass2());	//儲存班級名稱
		session.setAttribute("Scscode", dtime.getCscode());				//儲存課程代碼
		session.setAttribute("Scscode2", dtime.getChiName2());			//儲存課程名稱
		session.setAttribute("Sterm", dtime.getSterm());				//儲存學期
		session.setAttribute("Sopt", dtime.getOpt());					//儲存選別
		session.setAttribute("Sthour", dtime.getThour().toString());	//儲存時數
		session.setAttribute("Scredit", dtime.getCredit().toString());	//儲存學分數
		session.setAttribute("SselectLimit", dtime.getSelectLimit().toString());//儲存人數上限
		session.setAttribute("SstuSelect", manager.ezGetInt("SELECT COUNT(*)FROM Seld WHERE Dtime_oid='"+dtime.getOid()+"'"));		//取得已選人數
		session.setAttribute("Sopen", dtime.getOpen());					//取得開放選修
		session.setAttribute("Selearning", dtime.getElearning());		//取得遠距教學
		session.setAttribute("Sextrapay", dtime.getExtrapay());			//取得電腦實習費

		List dtimeClasses = manager.getDtimeClassListForOpenCourse(dtime.getOid().toString());//取得勾選的上課時間物件
		session.setAttribute("dtimeClasses", dtimeClasses);	//儲存上課時間物件
		session.setAttribute("dtimeClasSize", dtimeClasses.size());	//儲存上課時間物件長度(頁面判斷用)

		List dtimeTeachers=manager.getDtimeTeacherBy(dtime.getOid().toString());	//取得一科目多教師物件
		session.setAttribute("dtimeTeachers", dtimeTeachers); 			//儲存一科目多教師物件
		session.setAttribute("dtimeTeacherSize", dtimeTeachers.size()); //儲存一科目多教師長度(頁面判斷用)

		List opencsList=manager.getOpencs(dtime.getOid().toString());	//取得選課規則物件
		session.setAttribute("opencsList", opencsList); 				//儲存選課規則物件
		session.setAttribute("opencsListSize", opencsList.size());		//儲存長度(頁面按摩數量)

		boolean createMode = false;
		boolean editMode = true;
		boolean viewMode= false;
		session.setAttribute("viewMode", viewMode);
		session.setAttribute("editMode", editMode);
		session.setAttribute("createMode", createMode);

		return mapping.findForward("Main");
	}

	/**
	 * 啟動修改模式
	 */
	public ActionForward modifyRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		OpenCourseActionServices openCourseActionService = new OpenCourseActionServices();

		boolean createMode = false;
		boolean editMode = false; //初始設定

		List dtimeClasses=(List) session.getAttribute("dtimeClasses");	//取得排課時段
		List dtimeTeachers=(List) session.getAttribute("dtimeTeachers");	//取得一科目多教師
		List opencsList=(List) session.getAttribute("opencsList");	//取得選課規則
		List dtimeExamList=(List) session.getAttribute("dtimeExamList"); //取得排考

		DynaActionForm setUpDtime = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");

		//取得頁面上的所有物件並設定對應物件
		String departClass = (String) setUpDtime.get("classNo");	//頁面資訊-班級代碼
		String cscode = (String) setUpDtime.get("courseNumber");	//頁面資訊-課程代碼
		String courseName = (String) setUpDtime.get("courseName");	//頁面資訊-課程名稱
		String techid = (String) setUpDtime.get("teacherId");		//頁面資訊-教師代碼
		//String teacherName = (String) setUpDtime.get("teacherName");//頁面資訊-教師名稱
		String term = (String) setUpDtime.get("sterm");				//頁面資訊=學期
		String opt=(String)setUpDtime.get("choseType");				//頁面資訊-選別
		String thour = (String) setUpDtime.get("thour");			//頁面資訊-時數
		String credit = (String) setUpDtime.get("credit");			//頁面資訊-學分數
		String selectLimit = (String) setUpDtime.get("selectLimit");//頁面資訊-人數上限
		String examDate[]=(String[])setUpDtime.get("examDate");			//頁面資訊-考試日期
		String Ebegin[]=(String[]) setUpDtime.get("examTime");			//頁面資訊-考試節次
		String Etech[]=(String[]) setUpDtime.get("examEmplId");			//頁面資訊-監考人員
		String EtechName[]=(String[]) setUpDtime.get("examEmplName");	//頁面資訊-監考姓名
		String Eplace[]=(String[])setUpDtime.get("examPlace");			//頁面資訊-考試地點
		String week[] = (String[]) setUpDtime.get("week");			//頁面資訊-上課日
		String begin[] = (String[]) setUpDtime.get("begin");		//頁面資訊-開始節次
		String end[] = (String[]) setUpDtime.get("end");			//頁面資訊-結束節次
		String place[] = (String[]) setUpDtime.get("place");		//頁面資訊-上課地點
		String cidno[]= (String[]) setUpDtime.get("cidno");			//頁面資訊-選課規則-校區
		String sidno[]= (String[]) setUpDtime.get("sidno");			//頁面資訊-選課規則-學制
		String didno[]= (String[]) setUpDtime.get("didno");			//頁面資訊-選課規則-系所
		String grade[]= (String[]) setUpDtime.get("grade");			//頁面資訊-選課規則-年級
		String departClassM[]= (String[]) setUpDtime.get("departClass");//頁面資訊-選課規則-班級
		String techidM[]= (String[]) setUpDtime.get("techidM");		//頁面資訊-一科目多教師-教師代碼
		String hoursM[]= (String[]) setUpDtime.get("hoursM");		//頁面資訊-一科目多教師-授課時數
		String techM[]= (String[]) setUpDtime.get("techM");			//頁面資訊-一科目多教師-授課
		String fillscoreM[]= (String[]) setUpDtime.get("fillscoreM");//頁面資訊-一科目多教師-評分
		String open=(String)setUpDtime.get("open");					//頁面資訊-開放選修
		String elearning=(String)setUpDtime.get("elearning");		//頁面資訊-遠距教學
		String extrapay=(String)setUpDtime.get("extrapay");			//頁面資訊-電腦實習費

		//取得勾選課程在資料庫中的資料
		String dtimeOid=(String) session.getAttribute("oid").toString();	//session資訊-課程主鍵
		String SdepartClass = (String) session.getAttribute("SdepartClass");	//session資訊-班級代碼
		String Scscode = (String) session.getAttribute("Scscode");	//session資訊-課程代碼
		//String ScourseName = (String) session.getAttribute("ScourseName");	//session資訊-課程名稱
		String Stechid = (String) session.getAttribute("Stechid");		//session資訊-教師代碼
		//String SteacherName = (String) session.getAttribute("SteacherName");//session資訊-教師名稱
		String Sterm = (String) session.getAttribute("Sterm");				//session資訊=學期
		String Sopt=(String)session.getAttribute("Sopt");				//session資訊-選別
		String Sthour = (String) session.getAttribute("Sthour");			//session資訊-時數
		String Scredit = (String) session.getAttribute("Scredit");			//session資訊-學分數
		String SselectLimit = (String) session.getAttribute("SselectLimit");//session資訊-人數上限
		/*
		String SexamDate=(String)session.getAttribute("SexamDate");			//session資訊-考試日期
		String SEbegin=(String) session.getAttribute("SEbegin");			//session資訊-考試節次
		String SEtech=(String) session.getAttribute("SEtech");			//session資訊-監考人員
		String SEplace=(String)session.getAttribute("SEplace");			//session資訊-考試地點
		*/
		String Sopen=(String) session.getAttribute("Sopen").toString();			//session資訊-開放選修
		String Selearning=(String)session.getAttribute("Selearning").toString();	//session資訊-遠距教學
		String Sextrapay=(String)session.getAttribute("Sextrapay").toString();	//session資訊-電腦實習費

		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		//TODO 要重新設計日期的方法
		//DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		/**
		 * 檢查一般邏輯 (外層檢查)
		 */
		//全域檢查尋找開課基本必備資料的空白欄位 - 返回錯誤
		if (courseName.trim().equals("") || departClass.trim().equals("") ||
				thour.trim().equals("") || credit.trim().equals("")|| selectLimit.trim().equals("")) {
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.error.edit"));
			saveErrors(request, error);
		}
		// 判斷重複班級重複課程 - 返回訊息
		if(manager.checkRushCourseForOpenCourse(departClass,cscode, term, dtimeOid.toString()).size()>0){
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.ReOpenTerm"));
			saveMessages(request, msg);
		}
		//開課教師是否存在 - 返回錯誤
		if(techid.trim()!=""){
			//若有設定才檢查
			if(checkTeacherIsEmpty(techid)){
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.Noteacher", techid));
				saveErrors(request, error);
			}
		}
		//一科目多教師是否存在 - 返回錯誤
		for(int i=0; i<techidM.length; i++){
			if(techidM[i].trim()!=""){
				if(checkTeacherIsEmpty(techidM[i])){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.Noteacher", techidM[i]));
					saveErrors(request, error);
				}
			}
		}
		//排考教師是否存在  - 返回錯誤
		/*for(int i=0; i<Etech.length; i++){
			if(!Etech[i].trim().equals("")){
				if(checkTeacherIsEmpty(Etech[i])){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.openCourse.Noteacher", Etech[i]));
					saveErrors(request, error);
				}
			}
		}*/

		//排課教室是否存在 - 返回錯誤
		for(int i=0; i<place.length; i++){
			if(place[i].trim()!=""){
				if(checkPlaceIsEmpty(place[i].trim()).size()<1){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.NoRoomSub", place[i]));
					saveErrors(request, error);
				}
			}
		}

		//排考教室是否存在 - 返回錯誤
		/*for(int i=0; i<Eplace.length; i++){
			if(!Eplace[i].equals("")){
				if(checkPlaceIsEmpty(Eplace[i].trim()).size()<1){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.openCourse.NoRoomSub", Eplace));
					saveErrors(request, error);
				}
			}
		}*/

		//教師同時排考
		/*for(int i=0; i<Etech.length; i++){
			if(!examDate[i].equals("")&& !Etech[i].equals("")&& !Ebegin[i].equals("")){
				String[] tmpStr=Toolket.parseDate(examDate[i]).toLocaleString().split(" ");
				if(manager.checkDtimExamIdno(dtimeOid, tmpStr[0].toString(),
						Etech[i], Ebegin[i]).size()>0){	//除本班外不可有任何多餘班
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","<br>&nbsp;&nbsp;&nbsp;教師重複排考 "+examDate[i]+" 第"+Ebegin[i]+"節 "+Etech[i]));
					saveMessages(request, msg);
				}
			}
		}*/

		//教室同時排考
		/*for(int i=0; i<Eplace.length; i++){
			if(!Eplace[i].equals("")&& !examDate[i].equals("")&& !Ebegin[i].equals("")){
				String[] tmpStr=Toolket.parseDate(examDate[i]).toLocaleString().split(" "); // <--- 不知道怎麼拚湊出來的方法...
				if(manager.checkDtimExamPlace(dtimeOid, tmpStr[0].toString(),
						Eplace[i], Ebegin[i]).size()>2){	//除本班和任何一班外的多餘班
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","<br>&nbsp;&nbsp;&nbsp;教室重複排考 "+examDate[i]+" 第"+Ebegin[i]+"節 "+Eplace[i]));
					saveMessages(request, error);
				}
			}
		}*/

		//檢查排課是否矛盾 - 返回錯誤
		if(week.length>0||begin.length>0||end.length>0){
			//若有值才檢查
			if(openCourseActionService.checkTime(begin, end)){
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.timeError"));
				saveErrors(request, error);
			}
		}
		//檢查排課時數多於開課時數 - 返回錯誤
		if(openCourseActionService.checkThours(Integer.parseInt(thour), begin, end)){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.thourError"));
			saveErrors(request, error);
		}
		//跨選規則模糊 - 返回錯誤
		for(int i=0; i<cidno.length; i++){
			if(cidno[i]!=""|| didno[i]!=""|| sidno[i]!=""|| grade[i]!=""|| departClassM[i]!=""){
				if(cidno[i]==""|| didno[i]==""|| sidno[i]==""|| grade[i]==""|| departClassM[i]==""){
					String opwrong="第 "+i+" 個選課規則";
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidCodeSelection", opwrong));
					saveErrors(request, error);
				}
			}
		}

		//判斷某功能區塊曾遭到修改 - 返回錯誤
		Map map=new HashMap();

	    map=openCourseActionService.checkAllEq(departClass, cscode, techid, term, opt, thour, credit,
				  							   selectLimit, examDate, Ebegin, Etech, Eplace, week,
				  							   begin, end, place, cidno, sidno, didno, grade, departClassM,
				  							   techidM, hoursM, techM, fillscoreM, open, elearning, extrapay,
				  							   SdepartClass, Scscode, Stechid, Sterm, Sopt, Sthour, Scredit,
				  							   SselectLimit, dtimeExamList, dtimeClasses,
				  							   opencsList, dtimeTeachers, Sopen, Selearning, Sextrapay);

		  String a=(map.get("a").toString()), //a為開課基本資料
				 b=(map.get("b").toString()), //b為上課時間地點
				 c=(map.get("c").toString()), //c為一科目多教師
		  		 d=(map.get("d").toString()), //d為考試時間地點
		  		 e=(map.get("e").toString()); //e為選課規則
		  //無修改任何欄位 - 返回錯誤
		  if(a.equals("true")&& b.equals("true")&& c.equals("true")&& d.equals("true")&& e.equals("true")){
			  error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.NoChange"));
				saveErrors(request, error);
		  }

		/**
		 * 檢查輸入內容的重複規則(內層檢查)
		 */
		//檢查開課時段陣列長度
		int Ttmp=0;//檢查教師的次數
		int Ptmp=0;//檢查教室的次數
		for(int i=0; i<week.length; i++){
			//星期、開始/結束節次有經過設定則需要檢查
			if(week[i]!=""&& begin[i]!=""&& end[i]!=""){
				Ttmp=Ttmp+1;
				//教室有經過設定則需要檢查
				if(place[i]!=""){
					Ptmp=Ptmp+1;
				}
			}
		}
		//有設定開課時段
		if(Ttmp>0){
			//有設定授課教師
			if(techid.trim()!=""){
				//檢查教師同時段重複授課
				List checkReOpens=manager.checkEmplReOpen(techid, week, begin, end, term, dtimeOid);
				if(checkReOpens.size()>0){
					//Object[] checkReOpens.toArray();
					//訊息已存在則加一個換行標籤
					if(!msg.isEmpty()){
						msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("BR"));
					}
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.emplreopenBeginOrEnd"));
					Map aCourse;
					for (int i=0 ; i < checkReOpens.size(); i++) {
						aCourse = (Map)checkReOpens.get(i);
						//msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.emplreopenBeginOrEndSub",aCourse.get("week"),aCourse.get("begin"),aCourse.get("ClassName"),aCourse.get("chi_name")));
						msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN4","<br>&nbsp;&nbsp;&nbsp;&nbsp;",
								"<font style='font-weight:100'>"+aCourse.get("className")+" 星期"+aCourse.get("week"), " 第"+aCourse.get("begin"), "節 ~ 第"+aCourse.get("end")+"節 "+aCourse.get("chi_name")+"</font>"));
					}
					saveMessages(request, msg);
				}
			}
			//有設定教室
			if(Ptmp>0){
				//檢查班級教室重複使用
				List checkReOpenRoom=manager.checkReOpenRoom(place, week, begin, end, term, dtimeOid);
				if(checkReOpenRoom.size()>0){
					//Object[] checkReOpens.toArray();
					//訊息已存在則加一個換行標籤
					if(!msg.isEmpty()){
						msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("BR"));
					}
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.ReOpenRoom"));
					Map aRoom;
					for (int i=0 ; i < checkReOpenRoom.size(); i++) {
						aRoom = (Map)checkReOpenRoom.get(i);
						//msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.openCourse.emplreopenBeginOrEndSub",aRoom.get("week"),aRoom.get("begin"),aRoom.get("place"),aRoom.get("chi_name")));
						msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN4","<br>&nbsp;&nbsp;&nbsp;&nbsp;",
								"<font style='font-weight:100'>"+aRoom.get("className")+" 星期"+aRoom.get("week"), " 第"+aRoom.get("begin"), "節 ~ 第"+aRoom.get("end")+"節 "+aRoom.get("chi_name")+"</font>"));
					}
					saveMessages(request, msg);
				}
			}
			//	檢查班級同一時間重複使用
			List checkReOpenClass=manager.checkReopenClass(departClass, week, begin, end, term, dtimeOid);
			if(checkReOpenClass.size()>0){
				//Object[] checkReOpens.toArray();
				//訊息已存在則加一個換行標籤
				if(!msg.isEmpty()){
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("BR"));
				}
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","<br>&nbsp;&nbsp;&nbsp;&nbsp;輸入的學期內<font color='red'>同一時間班級</font>重複開課"));
				Map aClass;
				for (int i=0 ; i < checkReOpenClass.size(); i++) {
					aClass = (Map)checkReOpenClass.get(i);
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN4","<br>&nbsp;&nbsp;&nbsp;&nbsp;",
							"<font style='font-weight:100'>"+aClass.get("className")+" 星期"+aClass.get("week"), " 第"+aClass.get("begin"), "節 ~ 第"+aClass.get("end")+"節 "+aClass.get("chi_name")+"</font>"));
				}
				saveMessages(request, msg);
			}
		}

		session.setAttribute("SdepartClass", (String) setUpDtime.get("classNo"));
		session.setAttribute("Scscode", (String) setUpDtime.get("courseNumber"));
		session.setAttribute("ScourseName", (String) setUpDtime.get("courseName"));
		session.setAttribute("Stechid", (String) setUpDtime.get("teacherId"));
		session.setAttribute("StechName", (String) setUpDtime.get("teacherName"));
		session.setAttribute("Sterm", (String) setUpDtime.get("sterm"));
		session.setAttribute("Sopt", (String) setUpDtime.get("choseType"));
		session.setAttribute("Sthour", (String) setUpDtime.get("thour"));
		session.setAttribute("Scredit", (String) setUpDtime.get("credit"));
		session.setAttribute("SselectLimit", (String) setUpDtime.get("selectLimit"));
		session.setAttribute("Sopen", (String)setUpDtime.get("open"));					//取得開放選修
		session.setAttribute("Selearning", (String)setUpDtime.get("elearning"));		//取得遠距教學
		session.setAttribute("Sextrapay", (String)setUpDtime.get("extrapay"));		//取得遠距教學
		/**
		 * 有錯誤發生返回編輯介面
		 */
		if(!error.isEmpty()){
			editMode = true;

			session.setAttribute("createMode", createMode);
			session.setAttribute("editMode", editMode);
			setContentPage(request.getSession(false), "course/OpenCourse.jsp");
			return mapping.findForward("Main");
		}

		/**
		 * 開始寫入資料庫
		 */
		//CourseDAO dao = (CourseDAO)getBean("courseDAO");
		Dtime aDtime = (Dtime)session.getAttribute("TheDtime");
		//儲存開課基本資料
		if(a.equals("false")){
			//基本資料有修改則更新
			//Dtime aDtime=new Dtime();
			//aDtime.setOid(Integer.parseInt(dtimeOid));
			aDtime.setCscode(cscode);
			aDtime.setDepartClass(departClass);
			aDtime.setOpt(opt);
			aDtime.setSelectLimit(Short.parseShort(selectLimit));
			aDtime.setTechid(techid);
			aDtime.setThour(Short.parseShort(thour));
			aDtime.setSterm(term);
			aDtime.setCredit(Float.parseFloat(credit));
			aDtime.setOpen(Byte.parseByte(open));
			aDtime.setElearning(elearning);
			aDtime.setExtrapay(extrapay);
			manager.updateDtime(aDtime);
			//dao.reload(aDtime);
		}
		//儲存上課時段
		//上課時段修改有修改則
		if(b.equals("false")){
			//=(DtimeClass) session.getAttribute("dtimeClasses");
			//刪除目前存在的時段
			manager.removeDtimeClass(dtimeOid);
			for(int i=0; i<week.length; i++){
				//如果排課時段陣列有值才開始儲存
				if(week[i]!=""&& begin[i]!="" && end[i]!=""){
					DtimeClass aDtimeClass=new DtimeClass();
					aDtimeClass.setBegin(begin[i]);
					aDtimeClass.setDtimeOid(Integer.parseInt(dtimeOid));
					aDtimeClass.setEnd(end[i]);
					aDtimeClass.setPlace(place[i]);
					aDtimeClass.setWeek(Integer.parseInt(week[i]));
					manager.saveDtimeClass(aDtimeClass);

					session.setAttribute("dtimeClassCount",
							aDtime.getThour()-manager.countDtimeClassBy(aDtime.getOid().toString())); //取得未排課節次提供頁面警告
				}
			}
			//重新計算未排時數
			dtimeClasses = manager.getDtimeClassListForOpenCourse(dtimeOid);//取得上課時間物件
			session.setAttribute("dtimeClasses", dtimeClasses);	//儲存上課時間物件
			session.setAttribute("dtimeClasSize", dtimeClasses.size());	//儲存上課時間物件長度(頁面判斷用)
		}
		//儲存一科目多教師
		if(c.equals("false")){
			//清除資料
			manager.RemoveDtimeTeacherBy(dtimeOid);
			//教師欄位有值則開始儲存
			if(techidM.length>0){
				
				DtimeTeacher dtimeTeacher;
				for(int i=0; i<techidM.length; i++){
					if(techidM[i]!=""){
						dtimeTeacher=new DtimeTeacher();
						if(techM[i]==""){
							techM[i]="0";
						}
						if(fillscoreM[i]==""){
							fillscoreM[i]="0";
						}
						if(hoursM[i]==""){
							hoursM[i]="0";
						}

						dtimeTeacher.setDtimeOid(Integer.parseInt(dtimeOid));
						dtimeTeacher.setTeachId(techidM[i]);
						dtimeTeacher.setHours(Float.parseFloat(hoursM[i]));
						dtimeTeacher.setTeach(Byte.parseByte(techM[i]));
						dtimeTeacher.setFillscore(Byte.parseByte(fillscoreM[i]));
						//manager.SaveDtimeTeacher(dtimeTeacher);
						manager.updateObject(dtimeTeacher);
					}
				}
			}
		}
		dtimeTeachers=manager.getDtimeTeacherBy(dtimeOid.toString());	//取得一科目多教師物件
		session.setAttribute("dtimeTeachers", dtimeTeachers); 			//儲存一科目多教師物件
		session.setAttribute("dtimeTeacherSize", dtimeTeachers.size()); //儲存一科目多教師長度(頁面判斷用)
		//儲存考試資訊
		if(d.equals("false")){
			dtimeExamList=new ArrayList();
			manager.RemoveDtimeExamBy(dtimeOid);
			for(int i=0; i<examDate.length; i++){
				if(!examDate[i].trim().equals("")){
					DtimeExam dtimeExam=new DtimeExam();
					dtimeExam.setDtimeOid(Integer.parseInt(dtimeOid));
					dtimeExam.setExamDate(Toolket.parseDate(examDate[i]));

					dtimeExam.setEbegin(Ebegin[i]);
					dtimeExam.setExamEmpl(Etech[i]);
					dtimeExam.setPlace(Eplace[i]);
					manager.saveDtimeExam(dtimeExam);
					dtimeExam.setExamDate2(examDate[i]);
					dtimeExam.setTechName(EtechName[i]);
					dtimeExamList.add(dtimeExam);
				}
			}
			session.setAttribute("dtimeExamList", dtimeExamList);
			session.setAttribute("dtimeExamListSize", dtimeExamList.size());// 取得按鈕的數量
		}

		//儲存選課規則
		if(e.equals("false")){
			//刪除原有資料
			manager.RemoveOpencsBy(dtimeOid);
			
			int tmp=0;
			for(int i=0; i<cidno.length; i++){
				Opencs opencs=new Opencs();
				//若任何欄位有值則存
				if(cidno[i]!=""|| sidno[i]!=""|| didno[i]!=""|| grade[i]!=""|| departClassM[i]!=""){
					opencs.setDtimeOid(Integer.parseInt(dtimeOid));
					opencs.setCidno(cidno[i]);
					opencs.setSidno(sidno[i]);
					opencs.setDidno(didno[i]);
					opencs.setGrade(grade[i]);
					opencs.setClassNo(departClassM[i]);
					opencs.setDepartClass("");					
					manager.saveOpencsBy(opencs);
					tmp=tmp+1;
				}

			}
			//更新Dtime的跨選欄位
			if(tmp>=1){
				aDtime.setCrozz("*");
				manager.updateDtime(aDtime);
			}else{
				aDtime.setCrozz("");
				manager.updateDtime(aDtime);
			}


			opencsList=manager.getOpencs(dtimeOid);	//取得選課規則物件
			session.setAttribute("opencsList", opencsList); 				//儲存選課規則物件
			session.setAttribute("opencsListSize", opencsList.size());		//儲存長度(頁面script判斷要用)
		}

		if(!msg.isEmpty()){
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("BR"));
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.setCourseName.modifyCompleteSub"));
			saveMessages(request, msg);
		}else{
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.setCourseName.modifyComplete"));
			saveMessages(request, msg);
		}
		//修改作業全部結束

		boolean viewMode=true;
		if(session.getAttribute("newDtime")==null){
			session.setAttribute("newDtime", "hi");
		}
		session.setAttribute("viewMode", viewMode);
		setContentPage(request.getSession(false), "course/OpenCourse.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 檢查教師存在
	 */
	private boolean checkTeacherIsEmpty(String techid){
		CourseManager manager = (CourseManager) getBean("courseManager");
		if(manager.checkEmplForOpenCourse(techid).size()<1){
			return true;
		}
		return false;
	}

	/**
	 * 檢查教室存在
	 */
	private List checkPlaceIsEmpty(String place){
		CourseManager manager = (CourseManager) getBean("courseManager");
		List checkPlaceIsEmpty=manager.checkNabbrForOpenCs(place);

		return checkPlaceIsEmpty;
	}

	/**
	 * 取消
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		boolean editMode = false;
		boolean createMode = false;
		boolean deleteMode=false;
		boolean viewMode=false;
		
		session.setAttribute("viewMode", viewMode);
		session.setAttribute("editMode", editMode);
		session.setAttribute("createMode", createMode);
		session.setAttribute("deleteMode", deleteMode);
		
		clearAll(mapping, form, request, response);
		setForm((Map)session.getAttribute("mySearchForm"), mapping, form, request, response);
		
		setForm((Map)session.getAttribute("mySearchForm"), mapping, form, request, response);
		return  list(mapping, form, request, response);
		
		//setContentPage(request.getSession(false), "course/OpenCourse.jsp");
		//return mapping.findForward("Main");
	}
	/*
	private boolean validatInput(DynaActionForm form, DtimeClass dtimeClass) {

		boolean checkDtimeClass = false;
		String week[] = (String[]) form.get("week");
		String begin[] = (String[]) form.get("begin");
		String end[] = (String[]) form.get("end");
		String place[] = (String[]) form.get("place");
		for (int i = 0; i < week.length; i++) {
			if (week[i] != null || week[i].trim().length() > 0) {

			}
		}
		return checkDtimeClass;
	}
	*/
	/**
	 * 檢視開課內容
	 */
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);
		//DynaActionForm setUpDtime = (DynaActionForm) form;

		List selDtimes=new ArrayList();
		if(session.getAttribute("newDtime")!=null){
			selDtimes.add(session.getAttribute("newDtime"));
		}else{
			selDtimes = getDtimEditList(request);
		}

		//此方法只檢查未選定任何一筆 或 超過二筆以上資料的情形並且不回傳其它頁面
		if (selDtimes.size() != 1) {
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.setCourseName.modifyTooMuch"));
			saveErrors(request, error);
		} else {
			Dtime dtime = (Dtime) selDtimes.get(0);//取得已勾選的課程所有資訊

			//List editDtime = manager.getDtimeForEditDtime(dtime.getOid().toString());	//取得勾選的課程物件
			List empList = manager.getEmplForOpenCourse(dtime.getTechid());	//取得勾選的授課教師物件
			List dtimeExamList = manager.getDtimeExamListForOpenCourse(dtime.getOid().toString());//取得勾選的考試物件
			session.setAttribute("dtimeExamList", dtimeExamList); // 儲存考試物件
			session.setAttribute("dtimeExamListSize", dtimeExamList.size());// 取得按鈕的初始數量
			//從考試物件中取得考試日期、時間、監考人員、考試地點
			/*
			if(dtimeExamList.size()>0){
				DtimeExam dtimeExam = (DtimeExam) dtimeExamList.get(0);
				if(dtimeExam.getExamDate2()!=null){
					session.setAttribute("SexamDate", dtimeExam.getExamDate2().toString());
				}
				if(dtimeExam.getEbegin()!=null){
					session.setAttribute("SEbegin", dtimeExam.getEbegin().toString());
				}
				session.setAttribute("SEtech", dtimeExam.getExamEmpl());	//儲存監考教師
				session.setAttribute("SEplace", dtimeExam.getPlace());		//儲存考試地點
				session.setAttribute("SexamEmplName", dtimeExam.getTechName());//儲存監考教師名稱
			}
			*/

			//從教師物件中取得相關資訊
			if (empList.size() > 0) {
				//無教師的課程儲存空白資料
				Empl empl = (Empl) empList.get(0);
				session.setAttribute("Stechid", empl.getIdno());
				session.setAttribute("StechName", empl.getCname());
				}else {
				//有教師的課程儲存實體資料
				session.setAttribute("Stechid", "");	//儲存教師代碼
				session.setAttribute("StechName", "");	//儲存教師名稱
			}

			session.setAttribute("dtimeClassCount",
					dtime.getThour()-manager.countDtimeClassBy(dtime.getOid().toString())); //取得未排課節次提供頁面警告


			session.setAttribute("oid", dtime.getOid());					//*****儲存課程主鍵*****

			session.setAttribute("SdepartClass", dtime.getDepartClass());	//儲存班級代碼
			session.setAttribute("SclassName2", dtime.getDepartClass2());//儲存班級名稱
			session.setAttribute("Scscode", dtime.getCscode());				//儲存課程代碼
			session.setAttribute("Scscode2", dtime.getChiName2());//儲存課程名稱
			session.setAttribute("Sterm", dtime.getSterm());				//儲存學期
			session.setAttribute("Sopt", dtime.getOpt());					//儲存選別
			session.setAttribute("Sthour", dtime.getThour().toString());	//儲存時數
			session.setAttribute("Scredit", dtime.getCredit().toString());	//儲存學分數
			session.setAttribute("SselectLimit", dtime.getSelectLimit().toString());//儲存人數上限
			session.setAttribute("SstuSelect", manager.ezGetInt("SELECT COUNT(*)FROM Seld WHERE Dtime_oid='"+dtime.getOid()+"'"));		//取得已選人數
			session.setAttribute("Sopen", dtime.getOpen());					//取得開放選修
			session.setAttribute("Selearning", dtime.getElearning());		//取得遠距教學
			session.setAttribute("Sextrapay", dtime.getExtrapay());			//取得電腦實習費

			List dtimeClasses = manager.getDtimeClassListForOpenCourse(dtime.getOid().toString());//取得勾選的上課時間物件
			session.setAttribute("dtimeClasses", dtimeClasses);	//儲存上課時間物件
			session.setAttribute("dtimeClasSize", dtimeClasses.size());	//儲存上課時間物件長度(頁面判斷用)

			List dtimeTeachers=manager.getDtimeTeacherBy(dtime.getOid().toString());	//取得一科目多教師物件
			session.setAttribute("dtimeTeachers", dtimeTeachers); 			//儲存一科目多教師物件
			session.setAttribute("dtimeTeacherSize", dtimeTeachers.size()); //儲存一科目多教師長度(頁面判斷用)

			List opencsList=manager.getOpencs(dtime.getOid().toString());	//取得選課規則物件
			session.setAttribute("opencsList", opencsList); 				//儲存選課規則物件
			session.setAttribute("opencsListSize", opencsList.size());		//儲存長度(頁面script判斷要用)

			boolean createMode = false;
			boolean editMode = true;
			boolean viewMode = true;
			session.setAttribute("viewMode", viewMode);
			session.setAttribute("editMode", editMode);
			session.setAttribute("createMode", createMode);
		}

		return mapping.findForward("Main");
	}

	/**
	 * 取得勾選的欄位
	 */
	private List getDtimEditList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "dtimes");
		List dtimes = (List) session.getAttribute("dtimeList");
		CourseManager manager = (CourseManager) getBean("courseManager");

		List selDtimes=new ArrayList();
		int dSeq=0;
		
		Map map;
		for(int i=0; i<dtimes.size(); i++){

			map = (Map)dtimes.get(i);
			if (Toolket.isValueInCookie(map.get("oid").toString(), oids)) {
				dSeq=i;
				selDtimes.addAll((List) manager.getDtimeBy(map.get("oid").toString()));
			}
		}
		session.setAttribute("dSeq", dSeq);//取得勾選順序
		//System.out.println(dSeq);
		return selDtimes;
	}
	
	/**
	 * 批次設定開放規則
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeOpenType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);
		
		List selDtimes = getDtimEditList(request);
		
		if(selDtimes.size()<1){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","要改哪門課的開放選修?"));
			saveErrors(request, error);
			return mapping.findForward("Main");		
		}
		
		Dtime dtime;
		for(int i=0; i<selDtimes.size(); i++){
			dtime=(Dtime)selDtimes.get(i);			
			switch(dtime.getOpen()){
				case 1:dtime.setOpen((byte)0);
					break;
				
				case 0:dtime.setOpen((byte)1);
					break;
				
				default:
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","出現問題, 請洽電算中心 "));
					saveErrors(request, error);
					return list(mapping, form, request, response);
			}
			
			manager.updateDtime(dtime);
		}
		
		setForm((Map)session.getAttribute("mySearchForm"), mapping, form, request, response);
		return  list(mapping, form, request, response);
	}
		
	private void clearAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession(false);
		
		session.removeAttribute("SEtech");
		session.removeAttribute("oid");
		session.removeAttribute("SEplace");
		session.removeAttribute("SexamEmplName");
		session.removeAttribute("SexamDate");
		session.removeAttribute("Selearning");
		session.removeAttribute("dtimeClasSize");
		session.removeAttribute("SexamDate");
		session.removeAttribute("dtimeClassCount");
		
		session.removeAttribute("newDtime");
		session.removeAttribute("Scscode");
		session.removeAttribute("Scscode2");
		session.removeAttribute("Stechid");
		session.removeAttribute("StechName");
		session.removeAttribute("newDtime");
		session.removeAttribute("Selearning");
		session.removeAttribute("dtimeClasSize");
		session.removeAttribute("SexamDate");
		session.removeAttribute("dtimeClassCount");		
		
		session.removeAttribute("dtimeList");
		session.removeAttribute("delTeachers");
		session.removeAttribute("delStudents");		
		
		session.removeAttribute("dtimeList");
		session.removeAttribute("Sterm");
		session.removeAttribute("Sopt");
		session.removeAttribute("Selearning");
		session.removeAttribute("Sopen");
		session.removeAttribute("Selearning");
		session.removeAttribute("dtimeClasSize");
		session.removeAttribute("SexamDate");
		
		session.removeAttribute("newDtime");
		session.removeAttribute("TheDtime");
		session.removeAttribute("dtimeList");
		
		session.setAttribute("checkError", false);		
		session.setAttribute("viewMode", false);
		session.setAttribute("editMode", false);
		session.setAttribute("createMode", false);
		session.setAttribute("deleteMode", false);
		
		DynaActionForm setUpDtime = (DynaActionForm) form;
		setUpDtime.set("campusInCharge2", "All"); // area
		setUpDtime.set("schoolInCharge2", "All"); // rule
		setUpDtime.set("deptInCharge2", "All"); // dept
		setUpDtime.set("classInCharge2", "All"); // class
		setUpDtime.set("courseNumber", "");
		setUpDtime.set("courseName", "");
		setUpDtime.set("teacherId", "");
		setUpDtime.set("teacherName", "");
		setUpDtime.set("sterm", "");
		setUpDtime.set("choseType", "%");
		setUpDtime.set("classLess", "");
		setUpDtime.set("open", "%");
		setUpDtime.set("elearning", "%");
	}
	
	/***
	 * 換一個form回來
	 * @param map
	 * @return
	 */
	private void setForm(Map map, ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){		
		
		//System.out.println("getClass="+map.get("classLess"));
		
		DynaActionForm setUpDtime=(DynaActionForm)form;
		setUpDtime.set("campusInCharge2", map.get("campusInCharge2")); // area
		setUpDtime.set("schoolInCharge2", map.get("schoolInCharge2")); // rule
		setUpDtime.set("deptInCharge2", map.get("deptInCharge2")); // dept
		setUpDtime.set("classInCharge2", map.get("classInCharge2")); // class
		setUpDtime.set("courseNumber", map.get("courseNumber"));
		setUpDtime.set("courseName", map.get("courseName"));
		setUpDtime.set("teacherId", map.get("teacherId"));
		setUpDtime.set("teacherName", map.get("teacherName"));
		setUpDtime.set("sterm", map.get("sterm"));
		setUpDtime.set("choseType", map.get("choseType"));
		setUpDtime.set("classLess", map.get("classLess"));
		setUpDtime.set("open", map.get("open"));
		setUpDtime.set("elearning", map.get("elearning"));
	}
	
	private Map setMap(Map map){
		
		Map newMap=new HashMap();
		
		newMap.put("schoolInCharge2", map.get("schoolInCharge2"));
		newMap.put("deptInCharge2", map.get("deptInCharge2"));
		newMap.put("classInCharge2", map.get("classInCharge2"));
		newMap.put("courseNumber", map.get("courseNumber"));
		newMap.put("teacherId", map.get("teacherId"));
		newMap.put("sterm", map.get("sterm"));
		newMap.put("choseType", map.get("choseType"));
		newMap.put("open", map.get("open"));
		newMap.put("elearning", map.get("elearning"));
		newMap.put("classLess", map.get("classLess"));
		newMap.put("readOnlyType", map.get("readOnlyType"));		
		
		return newMap;
	}
	
}
