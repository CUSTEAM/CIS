package tw.edu.chit.struts.action.course;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;
import org.hibernate.Hibernate;

import tw.edu.chit.model.Adcd;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Desd;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Entrno;
import tw.edu.chit.model.Gmark;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.QuitResume;
import tw.edu.chit.model.WwPass;
//import tw.edu.chit.model.Regs;
import tw.edu.chit.model.Savedesd;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.StdImage;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.Tran;
import tw.edu.chit.model.Tranno;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.SummerManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

/**
 * 學生管理
 * @author JOHN
 *
 */
public class StudentManagerAction extends BaseLookupDispatchAction{		
	
	/**
	 * 查詢(包含新預備處理事項)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward Continue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm stForm = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, "stus");
		//s=在校，g=離校，a=全部
		session.setAttribute("target", stForm.getMap().get("GorS"));//查詢模式		
		//取得現在學期以便在新建模式下自動完成入學年
		Calendar c= Calendar.getInstance();
		Integer thisYear=c.get(Calendar.YEAR)-1911; //民國年
		String thisMont="08";//暑假
		if(((Map)manager.ezGetBy("SELECT Value FROM Parameter WHERE Name='School_term'").get(0)).equals("2")){
			thisMont="02";//寒假
		}
		
		String studentName = (String) stForm.get("studentName"); //姓名
		String studentNo = (String) stForm.get("studentNo"); //學號
		String exSearch = (String) stForm.get("exSearch"); //進階搜尋開關
		String GorS = (String) stForm.get("GorS"); //查詢在校或非在校以及全部的開關
		
		session.removeAttribute("students");//<--?
		session.removeAttribute("gmarkList");//<-- 再次確定移除 gmarkList
		session.removeAttribute("myGmark"); //<-- 再次確定移除 gmarkList
		session.setAttribute("entrance", thisYear+thisMont); //自動完成入學年
		session.setAttribute("gradyear", thisYear+"06"); //自動完成前學程畢業年度
		
		session.setAttribute("occur_year", stForm.get("occur_year"));//儲存花生學年提供列表使用
		session.setAttribute("occur_term", stForm.get("occur_term"));//儲存花生學期提供列表使用
		session.setAttribute("classNo", stForm.get("classNo"));//儲存班級提供列表使用
		session.setAttribute("GorS", GorS);//儲存班級提供列表使用
		
		//若未開啟進階搜尋
		if(exSearch.equals("")){
			//找不到學生即進入轉入模式
			if( (studentName.trim().equals("")&& studentNo.trim().equals(""))|| studentName.trim().equals("新來的")){
				session.setAttribute("Gtype", "add");
				
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","入學模式啟動"));
				saveMessages(request, msg);
				return mapping.findForward("Main");
			}else{
				//有學生就進入搜尋並判斷是否唯一	
				
				List stmds=manager.getStudentInfo("stmd", stForm.getMap());
				List gstmds=manager.getStudentInfo("Gstmd", stForm.getMap());
				List students=new ArrayList();//用以存放
				 //如果長度是1需要判斷是在校或非在校並且轉入休退或復學模式					
				//session.setAttribute("seq", 0);
				
				if(stmds.size()==1&& gstmds.size()==0){// 在學生有的
					students=stmds; 
					Student stmd=(Student)manager.hqlGetBy("FROM Student WHERE studentNo='"+((Map)students.get(0)).get("student_no")+"'").get(0);
					stmd.setSchlName(manager.ezGetString("SELECT name FROM Recruit_school WHERE no='"+stmd.getSchlCode()+"'"));
					stForm=setStudent(stmd, stForm);
					session.setAttribute("NO", stmd.getStudentNo());
					
					session.setAttribute("Gtype", "editStmd"); //存作業模式
					
					session.setAttribute("students", students); //存查到的學生
					saveOther(studentNo,request);
					
					try{
						request.setAttribute("card_num", manager.ezGetInt("SELECT card_num FROM StmdCardNum WHERE student_no='"+stmd.getStudentNo()+"'"));
					}catch(Exception e){
						request.setAttribute("card_num", 0);
					}					
					
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","在校學生申請異動"));
					saveMessages(request, msg);
					return mapping.findForward("Main");
					
				}
				if(gstmds.size()==1&& stmds.size()==0){ //非在學有的
				//不在學就(也不)一定是休退
					students=gstmds;					
					Graduate gstmd=(Graduate)manager.hqlGetBy("FROM Graduate WHERE studentNo='"+((Map)students.get(0)).get("student_no")+"'").get(0);
					gstmd.setSchlName(manager.ezGetString("SELECT name FROM Recruit_school WHERE no='"+gstmd.getSchlCode()+"'"));
					stForm=setGstudent(gstmd, stForm);
					session.setAttribute("Gtype", "editGstmd"); //存作業模式
					
					session.setAttribute("students", students); //存查到的學生
					saveOther(studentNo,request);
					session.setAttribute("NO", gstmd.getStudentNo());					
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","離校學生申請異動"));
					saveMessages(request, msg);
					return mapping.findForward("Main");
				}
				
				//如果有一票人就到列表去
				//長度大於0才做存值動作
				if(stmds.size()>0){
					students.addAll(stmds);
				}
				if(gstmds.size()>0){
					students.addAll(gstmds);
				}
				
				session.setAttribute("students", students);
				
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","搜尋完成"));
				saveMessages(request, msg);
				return mapping.findForward("Main");//進入列表模式
			}			
		}
		
		if(!exSearch.equals("")){
			List stmds=new ArrayList();
			List gstmds=new ArrayList();
			List students=new ArrayList();//用以存放
			
			//目標若是在校生
			if(GorS.equals("s")){
				stmds=manager.getStudentInfo("stmd", stForm.getMap());
			}
			//目標若是輟學生
			if(GorS.equals("g")){
				gstmds=manager.getStudentInfo("Gstmd", stForm.getMap());
			}
			//目標若是全部學生
			if(GorS.equals("a")){
				stmds=manager.getStudentInfo("stmd", stForm.getMap());
				gstmds=manager.getStudentInfo("Gstmd", stForm.getMap());
			}
			 //如果長度是1需要判斷是在校或非在校並且轉入休退或復學模式
			if((stmds.size()+gstmds.size())==1){
				
				//在學生的話
				if((stmds.size()-gstmds.size())==1){
					//students=manager.getStudentInfo("stmd", stForm.getMap()); 
					students=stmds; 
					Student stmd=(Student)manager.hqlGetBy("FROM Student WHERE studentNo='"+((Map)students.get(0)).get("student_no")+"'").get(0);
					stForm=setStudent(stmd, stForm);
					session.setAttribute("Gtype", "editStmd");
					session.setAttribute("students", students);
					saveOther(studentNo,request);
					//TODO 要拿掉
					session.setAttribute("NO", stmd.getStudentNo());
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","在校學生申請異動"));
					saveMessages(request, msg);
					return mapping.findForward("Main"); //進入離校模式
				}
				
				if((gstmds.size()-stmds.size())==1){
				//不在學就一定是休退
					//students=manager.getStudentInfo("Gstmd", stForm.getMap()); 
					students=gstmds; 
					Graduate gstmd=(Graduate)manager.hqlGetBy("FROM Graduate WHERE studentNo='"+((Map)students.get(0)).get("student_no")+"'").get(0);
					stForm=setGstudent(gstmd, stForm);
					session.setAttribute("Gtype", "editGstmd");
					session.setAttribute("students", students);
					saveOther(studentNo,request);
					//TODO 要拿掉
					session.setAttribute("NO", gstmd.getStudentNo());
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","離校學生申請異動"));
					saveMessages(request, msg);
					return mapping.findForward("Main"); //進入復學模式
				}
			}
			//長度大於0才做存值動作
			if(stmds.size()>0){
				students.addAll(stmds);
			}
			if(gstmds.size()>0){
				students.addAll(gstmds);
			}
			
			session.setAttribute("students", students);			
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","搜尋完成"));
			saveMessages(request, msg);
			return mapping.findForward("Main");//進入列表模式
		}
		
		return mapping.findForward("Main");
	}
	
	/**
	 * 準備建立學生
	 */
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		SummerManager summerManager = (SummerManager) getBean("summerManager");
		DynaActionForm stForm = (DynaActionForm) form;
		
		String iType = (String) stForm.get("iType");
		String TOL_schl_name = (String) stForm.get("TOL_schl_name");
		String TOL_permission_no = (String) stForm.get("TOL_permission_no");
		String studentName = (String) stForm.get("studentName");
		String studentNo = stForm.get("studentNo").toString().toUpperCase();
		String classNo = stForm.get("classNo").toString().toUpperCase();
		String className = (String) stForm.get("className");
		String idno = stForm.get("idno").toString().toUpperCase();
		String entrno = (String)stForm.get("entrno");
		
		String birthday = (String) stForm.get("birthday");
		//String occur_date = (String) stForm.get("occur_date");
		//SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		//頁面偵錯
		if(birthday.trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","必須要有生日"));
			saveErrors(request, error);	//身日必須填寫 - 返回錯誤
		}
		if(idno.trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","必須要有身份證字號")); 
			saveErrors(request, error);	//身份證字號必須填寫 - 返回錯誤
		}
		if(idno.trim().toUpperCase().equals("A123456789")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","請不要拿一條龍先生來開玩笑")); 
			saveErrors(request, error);	//A123456789 是一個正確的身份證字號 - 返回錯誤
		}
		if(studentNo.trim().equals("")|| studentNo.trim().length()<4){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","請檢查學號")); 
			saveErrors(request, error);	//沒有輸入學號或是學號太短 - 返回錯誤
		}
		if(summerManager.ezGetInt("SELECT COUNT(*)FROM stmd WHERE student_no='"+studentNo+"'")>0||
				summerManager.ezGetInt("SELECT COUNT(*)FROM Gstmd WHERE student_no='"+studentNo+"'")>0){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",studentNo+"與在校生學號重複")); 
			saveErrors(request, error);	//學號重複 - 新生轉學生不得和任何在校生重複 - 返回錯誤
		}
		if(summerManager.ezGetInt("SELECT COUNT(*)FROM stmd WHERE idno='"+idno+"'")>0){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",idno+"目前為在校生")); 
			saveErrors(request, error);	//身份證字號重複 - 新生轉學生不得和任何在校生的身份證字號重複 - 返回錯誤
		}
		if(className.trim().equals("")|| className.trim().equals("查無資料")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","班級"+classNo+"不存在"));
			saveErrors(request, error); //班級不存在或是沒輸入 - 返回錯誤
		}
		if(studentName.trim().equals("")|| studentName.trim().length()<2){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","請檢查姓名"));
			saveErrors(request, error);	//姓名太短或沒有輸入 - 返回錯誤
		}
		if(!error.isEmpty()){
			//msg.clear();
			return mapping.findForward("Main");
		}
		if(!Toolket.checkIdno(idno)){
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","請檢查身分證"));
			saveMessages(request, msg);	//身份證驗證失敗 - 返回訊息
		}
		if(iType.equals("tra")){
			if(TOL_schl_name.equals("")||TOL_permission_no.equals("")){
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","日後請別忘了轉入學歷和證號"));
				saveMessages(request, msg);	
				//stForm.set("entrno", ""); //轉學文件空白 - 返回訊息
			}
		}
		if(iType.equals("new")){
			if((entrno.equals(""))){
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","日後請別忘了新生學歷和證號"));
				saveMessages(request, msg);	
			}
		}
		
		//取得基本選課
		//系統設定的本學期，用來索取開課用的
		String sterm=((Map)manager.ezGetBy("SELECT Value FROM Parameter WHERE Name='School_term'").get(0)).get("Value").toString();
		
		session.setAttribute("myDtime1", manager.ezGetBy("SELECT d.*, c.chi_name FROM Dtime d, Csno c WHERE d.depart_class='"+classNo+"' AND " +
							 " d.cscode=c.cscode AND d.Sterm='"+sterm+"' AND d.opt='1' AND d.cscode NOT IN('50000', 'T0001')"));
		session.setAttribute("myDtime2", manager.ezGetBy("SELECT d.*, c.chi_name FROM Dtime d, Csno c WHERE d.depart_class='"+classNo+"' AND " +
				 " d.cscode=c.cscode AND d.Sterm='"+sterm+"' AND d.opt='2' AND d.cscode NOT IN('50000', 'T0001')"));
		//返回整個表單
		session.setAttribute("showBirthday", stForm.get("birthday").toString()); //存一個用來顯示用的日期
		//stForm.getMap().put("birthday", Toolket.parseDate(stForm.get("birthday").toString())); //轉日期		
		
		stForm.getMap().put("occur_year", Short.parseShort(((Map)manager.ezGetBy("SELECT Value FROM Parameter WHERE Name='School_year'").get(0)).get("Value").toString())); //自動完成最後異動學年
		stForm.getMap().put("occur_term", ((Map)manager.ezGetBy("SELECT Value FROM Parameter WHERE Name='School_term'").get(0)).get("Value").toString()); //自動完成最後異動學期
		stForm.getMap().put("occur_date", new Date()); //自動完成最後異動日期		
		
		/*
		if(iType.equals("new")){
			stForm.getMap().put("occur_status", "0"); //自動完成最後異動狀態 - 新生
		}
		*/
		if(iType.equals("tra")){
			stForm.getMap().put("occur_status", "3"); //自動完成最後異動原因 - 轉入
		}
		
		//作業型態
		session.setAttribute("iType", iType);
		session.setAttribute("aStudent", stForm.getMap());
		//設定帳號密碼
		session.setAttribute("username", studentNo);
		session.setAttribute("password", idno);
		session.setAttribute("Gtype", "confirmAdd");
		
		return mapping.findForward("Main");		
	}
	
	/**
	 * 確定建立學生
	 */
	public ActionForward crateNewStudent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		SummerManager summerManager = (SummerManager) getBean("summerManager");
		DynaActionForm stForm = (DynaActionForm) form;
		String myDtime2[]=stForm.getStrings("myDtime2");
		
		Map aStudent=(Map)session.getAttribute("aStudent");
		
		String school_year=aStudent.get("occur_year").toString();//頁面最後變更學年若空白給現在
		String school_term=aStudent.get("occur_term").toString();//頁面最後變更學期
		
		if(school_year.equals("")){
			school_year=manager.getSchoolYear().toString();//頁面最後變更學年若空白給現在
			school_term=manager.getSchoolYear().toString();//頁面最後變更學期
		}
		String iType=(String)session.getAttribute("iType");		
		//檢查權限
		if(!checkMyStudent((String)stForm.get("classNo"), request)){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","你沒有管理 "+(String)stForm.get("classNo")+(String)stForm.get("className")+" 的權限")); 
			saveErrors(request, error);	
			
			return mapping.findForward("Main");
		}
		
		//建立學生
		Student stmd=saveStmd(stForm.getMap());
		
		//建立學生
		try{
			summerManager.saveObj(stmd);

			//TODO**學籍同步** 若不再同步請將if(manager.testOnlineServer())中的行為清除 
			/*
			if(manager.testOnlineServer()){
				try {
					Toolket.sendStudentInfoByQueue(stmd, "I"); // 同步stmd
					Toolket.sendWWPassInfoByQueue(stmd.getStudentNo(), stmd.getIdno(), "C", "", "D"); // 同步wwpass,
																						// 先殺
					String seqno = stmd.getIdno().replaceAll("\\(", "").replaceAll("\\)", "");
					boolean bFlag = StringUtils.isAlpha(StringUtils.substring(
							seqno, 0, 1))
							&& !StringUtils.isNumeric(StringUtils.substring(
									seqno, 0, 1));
					boolean bFlag1 = StringUtils.isAlpha(StringUtils.substring(
							seqno, 1, 2))
							&& !StringUtils.isNumeric(StringUtils.substring(
									seqno, 1, 2));

					if (bFlag)
						seqno = Toolket.replace(StringUtils.substring(seqno, 0,
								1))
								+ StringUtils.substring(seqno, 1);
					if (bFlag1)
						seqno = StringUtils.substring(seqno, 0, 2)
								+ Toolket.replace(StringUtils.substring(seqno,
										2, 3))
								+ StringUtils.substring(seqno, 3);
					Toolket.sendWWPassInfoByQueue(stmd.getStudentNo(), stmd
							.getIdno(), "C", seqno, "I"); // 同步wwqass,
																						// 再裝
				} catch (Exception e) {
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
							"Course.messageN1", "同步發生錯誤 , 請連絡電算中心"));
					saveMessages(request, msg);
				}
			}
			*/
		}catch(Exception e){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "建立學生出現問題, 請洽電算中心")); 
			saveErrors(request, error);
			return mapping.findForward("Main");	
		}
		
		
		//建立帳號密碼
		try{
			saveWwpass(stmd.getStudentNo(), stmd.getIdno());
		}catch(Exception e){
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","建立帳號時出現問題, 請洽電算中心"));
			saveMessages(request, msg);
		}
		
		//建立轉入資格
		Gmark gmark;
		if(iType.trim().equals("tra")){ //如果是轉學進來的
			if(!aStudent.get("TOL_permission_no").equals("")|| !aStudent.get("TOL_permission_no").equals("")){
				try{ //建立學歷證明
					//summerManager.saveObj(tranno);
					saveTranNo(aStudent);
					saveGmark(school_year, school_term, stmd.getStudentNo(), "", "3", "");//寫入Gmark
				}catch(Exception e){
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","建立轉入資格時出現問題, 請洽電算中心"));
					saveMessages(request, msg);
				}
			}			
		}else{
			//建立新生入學資格
			try{
				Entrno entrno=new Entrno();
				entrno.setFirstStno(stmd.getStudentNo());
				entrno.setSecondStno(stmd.getStudentNo());
				entrno.setPermissionNo((String)aStudent.get("entrno"));				

				//寫入Gmark
				gmark=new Gmark();				
				//gmark.setSchoolYear(Short.parseShort(((Map)manager.ezGetBy("SELECT Value FROM Parameter WHERE Name='School_year'").get(0)).get("Value").toString()));
				//gmark.setSchoolTerm(Short.parseShort(((Map)manager.ezGetBy("SELECT Value FROM Parameter WHERE Name='School_term'").get(0)).get("Value").toString()));
				gmark.setSchoolYear(Short.parseShort(school_year));
				gmark.setSchoolTerm(Short.parseShort(school_term));
				
				gmark.setRemark("個別入學");
				gmark.setStudentNo(stmd.getStudentNo());
				
				summerManager.saveObj(entrno);
				summerManager.saveObj(gmark);
				
				//manager.updateObject(entrno);
				//manager.updateObject(gmark);
				
			}catch(Exception e){
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","建立新生入學文號時出現問題, 請洽電算中心"));
				saveMessages(request, msg);
			}			
		}
		//建立選課
		try{
			saveSeld((List)session.getAttribute("myDtime1"), myDtime2, stForm.get("studentNo").toString()); //選課
			saveAdcd(stForm.get("studentNo").toString(), myDtime2); //加選
			//TODO 為有發生選爆的，才發訊息至課務
			/*
			Member me = getUserCredential(request.getSession(false)).getMember();
			Message message=new Message();
			message.setCategory("Course");
			message.setSender(me.getName());
			
			message.setContent((String)stForm.get("studentNo")+(String)stForm.get("studentName")+"已入學, 並且幫 "+(String)stForm.get("studentName")+
					"選了開在"+(String)stForm.get("classNo")+(String)stForm.get("className")+" 的課, 課務同仁請檢查該班課程人數上下限是否受到影響, 謝謝!");
			message.setStartDate(new Date());
			Calendar c=Calendar.getInstance();
			c.setTime(message.getStartDate());
			c.set(Calendar.DAY_OF_YEAR,(c.get(Calendar.DAY_OF_YEAR) + 14));
			message.setDueDate(c.getTime());
			summerManager.saveObj(message);
			*/
				
		}catch(Exception e){
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","建立選課, 成績, 加選記錄時出現問題, 請洽電算中心"));
			saveMessages(request, msg);
		}
		
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","學生建立完成"));
		saveMessages(request, msg);
		removeSession(request, stForm);
		return mapping.findForward("Main");		
	}
	
	/**
	 * 返回
	 */
	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		String Gtype=(String)session.getAttribute("Gtype");
		
		if(Gtype.equals("editGstmd")||Gtype.equals("editStmd")){
			session.removeAttribute("Gtype");
			session.removeAttribute("students");
			DynaActionForm stForm = (DynaActionForm) form;			
			stForm=resetForm(stForm);
		}
		
		if(Gtype.equals("confirmBack")){
			session.setAttribute("Gtype", "editGstmd");
		}
		return mapping.findForward("Main");
	}
	
	/**
	 * 全部取消
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		//HttpSession session = request.getSession(false);
		Toolket.resetCheckboxCookie(response, "stus");
		removeSession(request, form);
		return unspecified(mapping, resetForm((DynaActionForm)form), request, response);
	}

	/**
	 * 取得勾選
	 */
	private Map getStudent(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String students = Toolket.getSelectedIndexFromCookie(request, "stus");
		List studentsList = (List) session.getAttribute("students");
		CourseManager manager = (CourseManager) getBean("courseManager");
		Map studentMap=new HashMap();
		Map map;
		List stmdList=new ArrayList();
		List gstmdList=new ArrayList();
		
		for(int i=0; i<studentsList.size(); i++){
			map = (Map)studentsList.get(i);
			if (Toolket.isValueInCookie(map.get("student_no").toString(), students)) {
				//seq=i;				
				stmdList.addAll(manager.hqlGetBy("FROM Student WHERE studentNo='"+map.get("student_no")+"'"));
				gstmdList.addAll(manager.hqlGetBy("FROM Graduate WHERE studentNo='"+map.get("student_no")+"'"));
			}
			studentMap.put("stmd", stmdList);
			studentMap.put("gstmd", gstmdList);
		}return studentMap;
	}
	
	/**
	 * 以搜尋結果勾選修改學生
	 */
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);
		DynaActionForm stForm = (DynaActionForm) form;
		session.removeAttribute("myGmark");
		session.removeAttribute("gmarkList");		
		Map students=getStudent(request);
		List stmd=(List)students.get("stmd");
		List gstmd=(List)students.get("gstmd");		
		//勾選有問題
		if((stmd.size()+gstmd.size())!=1){//必定等於1
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","要改哪一筆呢? "));
			saveErrors(request, error);
			return mapping.findForward("Main");		
		}		
		//在校生有值
		if(stmd.size()==1){
			Student student=(Student)stmd.get(0);
			student.setSchlName(manager.ezGetString("SELECT name FROM Recruit_school WHERE no='"+student.getSchlCode()+"'"));
			session.setAttribute("NO", student.getStudentNo());
			//session.setAttribute("aStudent", student);
			stForm=setStudent(student, stForm);// 將查詢結果塞入一個Form預備拋給下一頁面
			session.setAttribute("Gtype", "editStmd");// 儲存模式為修改
			saveOther(student.getStudentNo(),request);
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","在校學生申請異動"));
			saveMessages(request, msg);
		}
		//休退畢生有值
		if(gstmd.size()==1){
			Graduate graduate=(Graduate)gstmd.get(0);
			//System.out.println(manager.ezGetString("SELECT name FROM Recruit_school WHERE no='"+graduate.getSchlCode()+"'"));
			graduate.setSchlName(manager.ezGetString("SELECT name FROM Recruit_school WHERE no='"+graduate.getSchlCode()+"'"));
			session.setAttribute("NO", graduate.getStudentNo());
			stForm=setGstudent(graduate, stForm);
			saveOther(graduate.getStudentNo(),request);
			session.setAttribute("Gtype", "editGstmd");
			
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","離校學生申請異動"));
			saveMessages(request, msg);
		}
		
		return mapping.findForward("Main");		
	}
	
	/**
	 * KeyMethodMap
	 */
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Continue", "Continue");
		map.put("Create", "create");
		map.put("CrateNewStudent", "crateNewStudent");
		map.put("Back", "back");
		map.put("Cancel", "cancel");
		map.put("Modify", "modify");
		
		map.put("ChangeGstmd", "changeGstmd");
		map.put("ChangeGstmdConfirm", "changeGstmdConfirm");
		map.put("ChangeStmd", "changeStmd");
		map.put("ChangeStmdConfirm", "changeStmdConfirm");
		
		map.put("First", "first");
		map.put("Prev", "prev");
		map.put("Next", "next");
		map.put("Last", "last");
		
		map.put("MultiDelete", "mdelete");
		map.put("SingleDelete", "sdelete");
		map.put("DeleteConfirm", "deleteConfirm");
		
		map.put("Print", "print");
		return map;
	}
	
	/**
	 * 刪除學生
	 * 危險動作
	 */
	public ActionForward deleteConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		List list=(List)session.getAttribute("mdelete");
		String departClass;
		
		
		
		List data;
		for(int i=0; i<list.size(); i++){
			departClass=null;
			departClass=manager.ezGetString("SELECT depart_class FROM stmd WHERE student_no='"+((Map)list.get(i)).get("student_no")+"'");
			if(departClass==""||departClass==null){
				//System.out.println("SELECT depart_class FROM Gstmd WHERE student_no='"+((Map)list.get(i)).get("student_no")+"'");
				departClass=manager.ezGetString("SELECT depart_class FROM Gstmd WHERE student_no='"+((Map)list.get(i)).get("student_no")+"'");
			}
			//System.out.println("departClass="+departClass);
			if(!checkMyStudent(departClass, request)){//||manager.ezGetInt("SELECT COUNT(*)FROM ScoreHist WHERE student_no='"+((Map)list.get(i)).get("student_no")+"'")>0){
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","您沒有管理 "+departClass+" 的權限, 或是學生已有成績資料, 因此無法刪除 "+((Map)list.get(i)).get("student_no"))); 				
			}else{
				
				
				//選課
				//data=(List) ((Map)list.get(i)).get("Seld");
				//for(int j=0; j<data.size(); j++){				
				//	manager.executeSql("DELETE FROM Seld WHERE Oid='"+((Map)data.get(j)).get("Oid")+"'");	
				//}
				/**********************************************************************************/
				//加退
				//data=(List) ((Map)list.get(i)).get("adcd");
				//for(int j=0; j<data.size(); j++){				
				//	manager.executeSql("DELETE FROM AddDelCourseData WHERE Oid='"+((Map)data.get(j)).get("Oid")+"'");				
				//}
				/**********************************************************************************/
				//帳密
				//data=(List) ((Map)list.get(i)).get("wwpass");
				//for(int j=0; j<data.size(); j++){				
				//	manager.executeSql("DELETE FROM wwpass WHERE username='"+((Map)data.get(j)).get("username")+"'");				
				//}
				/**********************************************************************************/
				//轉班
				//data=(List) ((Map)list.get(i)).get("Tran");
				//for(int j=0; j<data.size(); j++){				
				//	manager.executeSql("DELETE FROM Tran WHERE Oid='"+((Map)data.get(j)).get("Oid")+"'");				
				//}
				/**********************************************************************************/
				//學生備註
				//data=(List) ((Map)list.get(i)).get("Gmark");
				//for(int j=0; j<data.size(); j++){				
				//	manager.executeSql("DELETE FROM Gmark WHERE Oid='"+((Map)data.get(j)).get("Oid")+"'");				
				//}
				/**********************************************************************************/
				//不知道什麼備註
				//data=(List) ((Map)list.get(i)).get("Rmark");
				//for(int j=0; j<data.size(); j++){				
				//	manager.executeSql("DELETE FROM Rmark WHERE Oid='"+((Map)data.get(j)).get("Oid")+"'");				
				//}
				/**********************************************************************************/
				//成績
				//data=(List) ((Map)list.get(i)).get("ScoreHist");
				//for(int j=0; j<data.size(); j++){				
					//manager.executeSql("DELETE FROM ScoreHist WHERE Oid='"+((Map)data.get(j)).get("Oid")+"'");				
				//}
				/**********************************************************************************/
				//選課
				//data=(List) ((Map)list.get(i)).get("just");
				//for(int j=0; j<data.size(); j++){				
				//	manager.executeSql("DELETE FROM Just WHERE student_no='"+((Map)list.get(i)).get("student_no")+"'");				
				//}
				/**********************************************************************************/
				
				
				manager.executeSql("DELETE FROM stmd WHERE student_no='"+((Map)list.get(i)).get("student_no")+"'");
				manager.executeSql("DELETE FROM Gstmd WHERE student_no='"+((Map)list.get(i)).get("student_no")+"'");
				session.removeAttribute("students");
			}
			
		}		
		
		
		
		
		if(!error.isEmpty()){
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		
		
		Toolket.resetCheckboxCookie(response, "stus");
		removeSession(request, form); //移除前次搜尋
		removeSessionForComplete(request, form);		
		setContentPage(request.getSession(false), "registration/StudentManager.jsp");
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","刪除完成"));
		saveMessages(request, msg);	
		
		return mapping.findForward("Main");
	}
	
	/**
	 * 刪除多名學生
	 */
	public ActionForward mdelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		//DynaActionForm stForm = (DynaActionForm) form;
		session.removeAttribute("myGmark");
		session.removeAttribute("gmarkList");		
		Map students=getStudent(request);
		List stmd=(List)students.get("stmd");
		List gstmd=(List)students.get("gstmd");		
		//勾選有問題
		if((stmd.size()+gstmd.size())<1){//必定等於1
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","要刪除哪位學生呢? "));
			saveErrors(request, error);
			return mapping.findForward("Main");		
		}
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		List list=manager.checkDelStd(stmd);
		list.addAll(manager.checkDelStd(gstmd));
		
		session.setAttribute("mdelete", list);		
		
		setContentPage(request.getSession(false), "registration/studentManage/delete/check.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 刪除1名學生
	 */
	public ActionForward sdelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm stForm = (DynaActionForm) form;		
		String studentNo=(String)stForm.get("studentNo");		
		List list=new ArrayList();
		try{
			list.add((Student)manager.hqlGetBy("FROM Student WHERE studentNo='"+studentNo+"'").get(0));
		}catch(Exception e){
			list.add((Graduate)manager.hqlGetBy("FROM Graduate WHERE studentNo='"+studentNo+"'").get(0));
		}		
		list=manager.checkDelStd(list);	
		session.setAttribute("mdelete", list);		
		setContentPage(request.getSession(false), "registration/studentManage/delete/check.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 復學(異動非在校生)作業預備動作
	 */
	public ActionForward changeGstmd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		//SummerManager summerManager = (SummerManager) getBean("summerManager");
		DynaActionForm stForm = (DynaActionForm) form;
		
		String school_year=stForm.getString("occur_year");//頁面最後變更學年若空白給現在
		String school_term=stForm.getString("occur_term");//頁面最後變更學期
		
		if(school_year.equals("")){
			school_year=manager.getSchoolYear().toString();//頁面最後變更學年若空白給現在
			school_term=manager.getSchoolYear().toString();//頁面最後變更學期
		}
		
		String studentNo=(String)stForm.get("studentNo");//學號
		String deadOccur_status=(String)stForm.get("deadOccur_status");//原狀態
		String occur_status=(String)stForm.get("occur_status");//變更狀態
		
		//TODO 要做更好的頁面除錯
		if( (deadOccur_status.equals("6")&&!occur_status.equals("6")) ||//畢業
			(deadOccur_status.equals("2")&&!occur_status.equals("2")) ||//退學
			(deadOccur_status.equals("7")&&!occur_status.equals("7"))){//移轉
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","不可以這樣做！"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		String deadStudentName=(String)stForm.get("deadStudentName");//原姓名
		String studentName=(String)stForm.get("studentName");//變更姓名
		String deadClassNo=(String)stForm.get("deadClassNo");//原班級
		String classNo=(String)stForm.get("classNo");//變更班級
		
		//塞作業型態
		String workType="";
		if(deadOccur_status.equals(occur_status)){
			workType="異動資料";
		}else{
			workType=((Map)manager.ezGetBy("SELECT name FROM code5 WHERE category='Status' AND idno='"+occur_status+"'").get(0)).get("name").toString();
		}
		if(!deadClassNo.substring(1, 3).equals(classNo.substring(1, 3))){//學制代碼必需相同
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","不可跨部制轉系"));
			saveErrors(request, error);
			return mapping.findForward("Main");	
		}
		session.setAttribute("workType", workType);// 顯示用		
		//改名動作
		Gmark gmark;//各種Gmark
		List gmarkList=new ArrayList();
		if(!deadStudentName.equals(studentName)){			
			gmark=new Gmark();
			gmark.setSchoolYear(Short.parseShort(school_year));
			gmark.setSchoolTerm(Short.parseShort(school_term));
			gmark.setRemark(deadStudentName+"改名"+studentName);
			gmark.setStudentNo(studentNo);
			gmarkList.add(gmark);
		}
		
		//轉班動作
		if(!deadClassNo.equals(classNo)){
			//塞Tran
			Tran tran=new Tran();
			tran.setNewDepartClass(classNo);
			tran.setOccurDate(new Date());
			tran.setOccurDocno((String)stForm.get("occur_docno"));
			tran.setOccurTerm(school_term);
			tran.setOccurYear(Short.parseShort(school_year));
			tran.setOldDepartClass(deadClassNo);
			tran.setStudentNo(studentNo);
			
			session.setAttribute("tran", tran); //把轉學歷史存session以供下頁確認
			
			//塞Gmark
			gmark=new Gmark();			
			gmark.setSchoolYear(Short.parseShort(school_year));
			gmark.setSchoolTerm(Short.parseShort(school_term));			
			gmark.setOccurStatus("5");
			gmark.setStudentNo(studentNo);
			gmark.setOccurCause((String)stForm.get("occur_cause"));
			gmark.setRemark(deadClassNo+"轉"+classNo);
			gmarkList.add(gmark);			
		}
		
		//復學動作
		if(occur_status.equals("4")){
			//帳號
			WwPass wwpass=new WwPass();
			wwpass.setInformixPass((String)stForm.get("idno"));
			wwpass.setPassword((String)stForm.get("idno"));
			wwpass.setPriority("C");
			wwpass.setUsername(studentNo);
			manager.updateObject(wwpass);
			session.setAttribute("wwpass", wwpass);
			
			//選課
			List myDtime1=manager.ezGetBy("SELECT d.*, c.chi_name FROM Dtime d, Csno c WHERE d.depart_class='"+classNo+"' AND " +
					 " d.cscode=c.cscode AND d.Sterm='"+school_term+"' AND opt='1' AND d.cscode NOT IN('50000', 'T0001')"); //必修
			
			List myDtime2=manager.ezGetBy("SELECT d.*, c.chi_name FROM Dtime d, Csno c WHERE d.depart_class='"+classNo+"' AND " +
					 " d.cscode=c.cscode AND d.Sterm='"+school_term+"' AND opt='2' AND d.cscode NOT IN('50000', 'T0001')"); //選修
			//塞選課
			session.setAttribute("myDtime1", myDtime1);
			session.setAttribute("myDtime2", myDtime2);
			
			//塞復學資料表
			QuitResume quitresume;
			//取出該生的休學記錄
			List quitList=manager.hqlGetBy("FROM QuitResume WHERE studentNo='"+studentNo+"' AND (recovDate IS NULL OR recovDate='')");
			if(quitList.size()>0){ //若能正確指向就使用該記錄
				quitresume=(QuitResume)quitList.get(0);
			}else{ //若無法正確指向我也沒辦法
				quitresume=new QuitResume();
			}
			quitresume.setRecovDate(new Date());
			quitresume.setRecovYear(Short.parseShort(school_year));
			quitresume.setRecovTerm(school_term);
			quitresume.setRecovDocno((String)stForm.get("occur_cause"));
			quitresume.setStudentNo(studentNo);
			quitresume.setDepartClass(classNo);
			
			session.setAttribute("quitresume", quitresume); //存入休學記錄
			
			//塞Gmark
			gmark=new Gmark();
			gmark.setSchoolYear(Short.parseShort(school_year));
			gmark.setSchoolTerm(Short.parseShort(school_term));
			gmark.setOccurStatus((String)stForm.get("occur_status"));
			gmark.setStudentNo(studentNo);
			gmark.setOccurCause("4");
			
			gmarkList.add(gmark);
		}else{
			//若不是復學
			if(!deadOccur_status.equals(occur_status)){

				//塞Gmark
				gmark=new Gmark();
				gmark.setSchoolYear(Short.parseShort(school_year));
				gmark.setSchoolTerm(Short.parseShort(school_term));
				gmark.setOccurStatus((String)stForm.get("occur_status"));
				gmark.setOccurCause((String)stForm.get("occur_cause"));
				gmark.setStudentNo(studentNo);
				gmarkList.add(gmark);
			}
		}
		
		if(gmarkList.size()>0){
			session.setAttribute("gmarkList", gmarkList);
		}
		
		session.setAttribute("Gtype", "confirmBack");
		session.setAttribute("aStudent", stForm.getMap());
		return mapping.findForward("Main");
	}
	
	/**
	 * 復學(異動非在校生)確定執行
	 */
	public ActionForward changeGstmdConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);		
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm stForm = (DynaActionForm) form;
		String myDtime2[]=(String[])stForm.get("myDtime2");
		
		String studentName=(String)stForm.get("studentName");
		String studentNo=(String)stForm.get("studentNo");
		
		//String school_year=((Map)manager.ezGetBy("SELECT Value FROM Parameter WHERE Name='School_year'").get(0)).get("Value").toString();//學年
		//String school_term=((Map)manager.ezGetBy("SELECT Value FROM Parameter WHERE Name='School_term'").get(0)).get("Value").toString();//學期

		//檢查權限		
		if(checkMyStudent(studentNo, request)){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","你不可以把別人的學生轉到 "+(String)stForm.get("classNo")+(String)stForm.get("className"))); 
			saveErrors(request, error);			
			return mapping.findForward("Main");
		}
		//檢查權限
		if(!checkMyStudent(stForm.getString("classNo"), request)){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","你沒有管理 "+(String)stForm.get("classNo")+(String)stForm.get("className")+" 的權限")); 
			saveErrors(request, error);	
			return mapping.findForward("Main");
		}
		//利用dynactionform來做(因為前面寫的是for dynactionform)
		Map myForm = (Map)session.getAttribute("aStudent");
		if(myForm.get("myImage")!=null && !myForm.get("myImage").toString().trim().equals("")){
			saveImage((FormFile) myForm.get("myImage"), studentNo); //存照片
		}
		
		if(stForm.get("occur_status").equals("4")){ //occur_status為4, 表示他要復學, 要幫他準備一些東西
			Student stmd=saveStmd(myForm);
			stmd.setOccurYear(Short.parseShort(stForm.getString("occur_year")));
			stmd.setOccurTerm(stForm.getString("occur_term"));
			manager.updateObject(stmd);
			manager.executeSql("DELETE FROM Gstmd WHERE student_no='"+stmd.getStudentNo()+"'");

			//TODO**學籍同步** 若不再同步請將if(manager.testOnlineServer())中的行為清除  
			/*
			if(manager.testOnlineServer()){
				try {
					Toolket.sendStudentInfoByQueue(stmd, "I"); // 同步stmd
					Toolket.sendGraduateInfoByQueue(saveGstmd(myForm), "D"); // 同步Gstmd
					Toolket.sendWWPassInfoByQueue(studentNo, stmd.getIdno(), "C", "", "D"); // 同步wwpass, 先殺					
					String seqno = stmd.getIdno().replaceAll("\\(", "").replaceAll("\\)", "");
					
					boolean bFlag = StringUtils.isAlpha(StringUtils.substring(seqno, 0, 1))&& !StringUtils.isNumeric(StringUtils.substring(seqno, 0, 1));
					boolean bFlag1 = StringUtils.isAlpha(StringUtils.substring(seqno, 1, 2))&& !StringUtils.isNumeric(StringUtils.substring(seqno, 1, 2));

					if (bFlag)
					seqno = Toolket.replace(StringUtils.substring(seqno, 0,1))+StringUtils.substring(seqno, 1);
					if (bFlag1)
					seqno = StringUtils.substring(seqno, 0, 2)+ Toolket.replace(StringUtils.substring(seqno,2, 3))+StringUtils.substring(seqno, 3);
					Toolket.sendWWPassInfoByQueue(studentNo, stmd.getIdno(),"C", seqno, "I"); // 同步wwpass, 再裝
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", studentNo + studentName+"同步發生錯誤 , 請連絡電算中心!"));
					saveMessages(request, msg);
				}				
			}
			*/
			//建立系統帳號
			saveWwpass(stForm.get("studentNo").toString(), stForm.get("idno").toString());
			
			//建立選課
			saveSeld((List)session.getAttribute("myDtime1"), myDtime2, stForm.get("studentNo").toString());//選課
			saveAdcd(stForm.get("studentNo").toString(), myDtime2); //加選
			//Member me = getUserCredential(request.getSession(false)).getMember();
			
			//TODO 只為有選爆的情形才發訊息
			/*
			Message message=new Message();
			message.setCategory("Course");
			message.setSender(me.getName());
			
			message.setContent((String)stForm.get("studentNo")+(String)stForm.get("studentName")+"已復學, 並且幫 "+(String)stForm.get("studentName")+
					"選了開在"+(String)stForm.get("classNo")+(String)stForm.get("className")+" 的課, 課務同仁請檢查該班課程人數上下限是否受到影響, 謝謝!");
			message.setStartDate(new Date());
			Calendar c=Calendar.getInstance();
			c.setTime(message.getStartDate());
			c.set(Calendar.DAY_OF_YEAR,(c.get(Calendar.DAY_OF_YEAR) + 14));
			message.setDueDate(c.getTime());
			summerManager.saveObj(message);
			*/
			
			//建立Gmark			
			List gmarkList=(List)session.getAttribute("gmarkList");
			if(gmarkList!=null)
			if(gmarkList.size()>0){
				saveGmark(gmarkList);
			}
			
			//建立tran
			if(session.getAttribute("tran")!=null){
				manager.updateObject((Tran)session.getAttribute("tran"));
			}
			
			//建立QuitResume
			if(session.getAttribute("quitresume")!=null){
				manager.updateObject((QuitResume)session.getAttribute("quitresume"));
			}
			
		}else{ //occur_status不為4，表示他只是異動資料
			Graduate gstmd=(Graduate)manager.hqlGetBy("FROM Graduate WHERE Oid='"+(String)stForm.get("Oid")+"'").get(0); //一定有的啦
			//manager.executeSql("DELETE FROM Gmark WHERE student_no='"+stForm.get("studentNo")+"'");
			List gmarkList=(List)session.getAttribute("gmarkList");
			if(gmarkList!=null)
			if(gmarkList.size()>0){
				saveGmark(gmarkList);
			}
			//建立tran
			if(session.getAttribute("tran")!=null){
				manager.updateObject((Tran)session.getAttribute("tran"));
			}			
			gstmd=saveGstmd(myForm, gstmd);
			manager.updateObject(gstmd);
			//TODO**學籍同步** 若不再同步請將if(manager.testOnlineServer())中的行為清除  
			/*
			if(manager.testOnlineServer()){
				try {
					Toolket.sendGraduateInfoByQueue(gstmd, "U"); // 同步Gstmd
				} catch (Exception e) {
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
							"Course.messageN1", studentNo + studentName
									+ " 同步發生錯誤 , 請連絡電算中心!"));
					saveMessages(request, msg);
				}
			}
			*/
		}
		
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",studentNo+studentName+"完成"));
		saveMessages(request, msg);	//完成		
		removeSessionForComplete(request, form);
		return mapping.findForward("Main");
	}	
	
	/**
	 * 輟學(異動在校生)作業預備動作
	 */
	public ActionForward changeStmd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm stForm = (DynaActionForm) form;
		
		String school_year=stForm.getString("occur_year");
		String school_term=stForm.getString("occur_term");
		
		if(school_year.equals("")){
			school_year=manager.getSchoolYear().toString();
			school_term=manager.getSchoolTerm().toString();
		}
		
		
		
		String changeDate="Y";
		
		String studentNo=(String)stForm.get("studentNo");//學號
		String deadOccur_status=(String)stForm.get("deadOccur_status");//原狀態
		String occur_status=(String)stForm.get("occur_status");//變更狀態		
		String deadStudentName=(String)stForm.get("deadStudentName");//原姓名
		String studentName=(String)stForm.get("studentName");//變更姓名		
		String deadClassNo=(String)stForm.get("deadClassNo");//原班級
		String classNo=(String)stForm.get("classNo");//變更班級
		
		//塞作業型態
		String workType="";
		if(deadOccur_status.equals(occur_status)|| occur_status.equals("0")){// 沒有異動的狀況
			workType="異動資料";
			changeDate="N";
		}else{
			try{
				workType=((Map)manager.ezGetBy("SELECT name FROM code5 WHERE category='Status' AND idno='"+occur_status+"'").get(0)).get("name").toString();
			}catch(Exception e){
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","不正常的操作"));
				saveErrors(request, error);
				return mapping.findForward("Main");	
			}
		}
		
		if(!deadClassNo.substring(1, 3).equals(classNo.substring(1, 3))){//學制代碼必需相同
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","不可跨部制轉系"));
			saveErrors(request, error);
			return mapping.findForward("Main");	
		}
		session.setAttribute("workType", workType);// 顯示用
		
		Gmark gmark;
		List gmarkList=new ArrayList<Gmark>();		
		
		//改名動作
		if(!deadStudentName.equals(studentName)){
			gmark=new Gmark();
			try{
				gmark.setSchoolYear(Short.parseShort(school_year));
				gmark.setSchoolTerm(Short.parseShort(school_term));
			}catch(Exception e){
				gmark.setSchoolYear(Short.parseShort(manager.getSchoolYear().toString()));
				gmark.setSchoolTerm(Short.parseShort(manager.getSchoolTerm().toString()));
			}			
			gmark.setRemark(deadStudentName+"改名"+studentName);
			gmark.setStudentNo(studentNo);			
			gmarkList.add(gmark);
		}
		
		//轉班動作
		if(!deadClassNo.equals(classNo)){
			//塞Tran
			Tran tran=new Tran();
			tran.setNewDepartClass(classNo);
			tran.setOccurDate(new Date());
			tran.setOccurDocno((String)stForm.get("occur_docno"));
			tran.setOccurTerm(school_term);
			tran.setOccurYear(Short.parseShort(school_year));			
			tran.setOldDepartClass(deadClassNo);
			tran.setStudentNo(studentNo);			
			session.setAttribute("tran", tran); //把轉學歷史存session以供下頁確認			
			//塞Gmark
			gmark=new Gmark();
			gmark.setSchoolYear(Short.parseShort(school_year));
			gmark.setSchoolTerm(Short.parseShort(school_term));
			gmark.setOccurStatus("5");
			gmark.setStudentNo(studentNo);
			gmark.setOccurCause((String)stForm.get("occur_cause"));
			gmark.setRemark(deadClassNo+"轉"+classNo);
			gmarkList.add(gmark);
			stForm.set("occur_status", "5");
			
			//尋找他將來的課
			List myDtime1=manager.ezGetBy("SELECT d.*, c.chi_name FROM Dtime d, Csno c WHERE d.depart_class='"+classNo+"' AND " +
					 " d.cscode=c.cscode AND d.Sterm='"+school_term+"' AND opt='1' AND d.cscode NOT IN('50000', 'T0001')"); //必修
			//尋找他現在的課
			List myDtimeAdd=manager.ezGetBy("SELECT d.*, c.chi_name FROM Dtime d, Csno c, Seld s WHERE " +
					"s.Dtime_oid=d.Oid AND s.student_no='"+studentNo+"' AND " +
					 " d.cscode=c.cscode AND d.Sterm='"+school_term+"'");
			
			List myDtime2=manager.ezGetBy("SELECT d.*, c.chi_name FROM Dtime d, Csno c WHERE d.depart_class='"+classNo+"' AND " +
					 " d.cscode=c.cscode AND d.Sterm='"+school_term+"' AND opt='2' AND d.cscode NOT IN('50000', 'T0001')"); //選修
			//塞選課, 剛好和離校生相反
			session.setAttribute("myDtimeAdd", myDtime1);
			session.setAttribute("myDtime2", myDtime2);
			request.setAttribute("myDtime1", myDtimeAdd);
		}		
		//設狀態為畢業，但未給予畢業號
		if(occur_status.equals("6")&& stForm.get("occur_graduate_no").toString().trim().equals("")){
			session.setAttribute("Gtype", "confirmGoodBye");
			session.setAttribute("aStudent", stForm.getMap());
			workType="快要畢業";
			session.setAttribute("workType", workType);// 顯示用			
			if(gmarkList.size()>0){
				session.setAttribute("gmarkList", gmarkList);
			}			
			return mapping.findForward("Main");
		}
		//設狀態為畢業，同時給予畢業號
		if(occur_status.equals("6")&& !stForm.get("occur_graduate_no").toString().trim().equals("")){
			session.setAttribute("Gtype", "confirmGoodBye");
			session.setAttribute("aStudent", stForm.getMap());
			workType="立即畢業";
			session.setAttribute("workType", workType);// 顯示用
			//帳號
			WwPass wwpass=new WwPass();
			wwpass.setInformixPass((String)stForm.get("idno"));
			wwpass.setPassword((String)stForm.get("idno"));
			wwpass.setPriority("C");
			wwpass.setUsername(studentNo);
			manager.updateObject(wwpass);
			session.setAttribute("wwpass", wwpass);			
			//選課
			List myDtime1=manager.ezGetBy("SELECT d.*, c.chi_name FROM Dtime d, Csno c, Seld s WHERE " +
					"s.Dtime_oid=d.Oid AND " +
					"s.student_no='"+studentNo+"' AND " +
					"d.cscode=c.cscode"); //選課清單			
			//塞選課
			session.setAttribute("myDtime1", myDtime1);
			if(gmarkList.size()>0){
				session.setAttribute("gmarkList", gmarkList);
			}			
			return mapping.findForward("Main");
		}

		//休退(移)轉動作
		//2009/12/29 移轉動作不執行休學記錄
		if(!occur_status.equals("3")&& !occur_status.equals("4")&& !occur_status.equals("5")&& !occur_status.equals("")&& !occur_status.equals("7") && !occur_status.equals("0")){
			System.out.println(occur_status);
			//帳號
			WwPass wwpass=new WwPass();
			wwpass.setInformixPass((String)stForm.get("idno"));
			wwpass.setPassword((String)stForm.get("idno"));
			wwpass.setPriority("C");
			wwpass.setUsername(studentNo);
			wwpass.setUpdated(new Date());
			session.setAttribute("wwpass", wwpass);			
			//選課
			List myDtime1=manager.ezGetBy("SELECT d.*, c.chi_name FROM Dtime d, Csno c, Seld s WHERE " +
					"s.Dtime_oid=d.Oid AND " +
					"s.student_no='"+studentNo+"' AND " +
					"d.cscode=c.cscode"); //選課清單			
			//塞選課
			session.setAttribute("myDtime1", myDtime1);
			
			//塞休學資料表
			if(!occur_status.equals("2")){//退學不用
				QuitResume quitresume=new QuitResume();
				quitresume.setDepartClass((String)stForm.get("classNo"));
				quitresume.setOccurDate(new Date());
				quitresume.setOccurDocno((String)stForm.get("occur_docno"));
				quitresume.setOccurTerm(school_term);
				quitresume.setOccurYear(Short.parseShort(school_year));
				quitresume.setStudentNo(studentNo);				
				session.setAttribute("quitresume", quitresume); //存入休學記錄
			}			
			
			//塞Gmark
			gmark=new Gmark();			
			gmark.setSchoolYear(Short.parseShort(school_year));
			gmark.setSchoolTerm(Short.parseShort(school_term));			
			gmark.setOccurStatus((String)stForm.get("occur_status"));
			gmark.setStudentNo(studentNo);
			gmark.setOccurCause("4");
			
			gmarkList.add(gmark);
		}else{
			//若不是休退畢
			if(!deadOccur_status.equals(occur_status)){
				//塞Gmark
				gmark=new Gmark();
				gmark.setSchoolYear(Short.parseShort(school_year));
				gmark.setSchoolTerm(Short.parseShort(school_term));
				gmark.setOccurStatus((String)stForm.get("occur_status"));
				gmark.setOccurCause((String)stForm.get("occur_cause"));
				gmark.setStudentNo(studentNo);
				gmarkList.add(gmark);
			}
		}		
		if(gmarkList.size()>0){
			session.setAttribute("gmarkList", gmarkList);
		}		
		session.setAttribute("Gtype", "confirmGoodBye");
		session.setAttribute("aStudent", stForm.getMap());
		session.setAttribute("changeDate", changeDate);
		return mapping.findForward("Main");
	}	
	
	/**
	 * 輟學(異動在校生)確定執行
	 */
	public ActionForward changeStmdConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm stForm = (DynaActionForm) form;
		
		String studentName=(String)stForm.get("studentName");
		String studentNo=(String)stForm.get("studentNo");
		
		//檢查權限
		if(stForm.get("occur_status").equals("5")){//若為轉班
			if(checkMyStudentNo(studentNo, request)){
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","你不能把別人的學生轉到 "+(String)stForm.get("classNo")+(String)stForm.get("className"))); 
				saveErrors(request, error);				
				removeSession(request, form);
				return mapping.findForward("Main");
			}			
		}else{//若不為轉班
			if(!checkMyStudent((String)stForm.get("classNo"), request)){
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","你沒有管理 "+(String)stForm.get("classNo")+(String)stForm.get("className")+" 的權限")); 
				saveErrors(request, error);				
				return mapping.findForward("Main");
			}
		}
		//異動學年學期
		String school_year=stForm.getString("occur_year");//頁面最後變更學年若空白給現在
		String school_term=stForm.getString("occur_term");//頁面最後變更學期
		
		if(school_year.equals("")){//如果空白才自動給予本學期
			school_year=manager.getSchoolYear().toString();//頁面最後變更學年若空白給現在
			school_term=manager.getSchoolTerm().toString();//頁面最後變更學期
		}
		
		//利用dynactionform來做(因為前面寫的是for dynactionform)
		Map myForm=(Map)session.getAttribute("aStudent");
		// 如有上傳大頭照的動作
		if(myForm.get("myImage")!=null && !myForm.get("myImage").toString().trim().equals("")){
			saveImage((FormFile) myForm.get("myImage"), studentNo); //存照片
		}
		//設狀態為畢業，但未給予畢業號
		if(stForm.get("occur_status").equals("6")&& stForm.get("occur_graduate_no").toString().trim().equals("")){
			Student stmd = (Student)manager.hqlGetBy("FROM Student WHERE studentNo='"+stForm.get("studentNo")+"'").get(0);
			manager.updateObject(saveStmd(stForm.getMap(), stmd));
			//TODO**學籍同步** 若不再同步請將if(manager.testOnlineServer())中的行為清除  
			/*
			if(manager.testOnlineServer()){
				try {
					Toolket.sendStudentInfoByQueue(stmd, "U"); // 同步stmd
				} catch (Exception e) {
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
							"Course.messageN1", studentNo + studentName
									+ " 同步發生錯誤 , 請連絡電算中心!"));
					saveMessages(request, msg);
				}
			}
			*/
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",studentNo+studentName+"資料修改完成"));
			saveMessages(request, msg);	//完成
			//建立Gmark
			List gmarkList=(List)session.getAttribute("gmarkList");			
			if (gmarkList!=null)
			if(gmarkList.size()>0){
				saveGmark(gmarkList);
			}			
			removeSession(request, form); //重設表單並清session
			return mapping.findForward("Main");
		}
		//TODO 畢業生要多一層判斷
		//occur_status不為3, 4, 5, 表示他要輟學, 要幫他辦些事
		if(!stForm.get("occur_status").equals("3")&& !stForm.get("occur_status").equals("4")&& 
				!stForm.get("occur_status").equals("5")&& !stForm.get("occur_status").equals("")){
			Graduate gstmd=new Graduate();
			gstmd=saveGstmd(myForm, gstmd);
			gstmd.setOccurYear(Short.parseShort(school_year));			
			gstmd.setOccurTerm(school_term);
			manager.updateObject(gstmd);
			manager.executeSql("DELETE FROM stmd WHERE student_no='"+gstmd.getStudentNo()+"'");//刪stmd
			delStuProperty(studentNo, school_year, school_term);// 刪有的沒的
			//TODO**學籍同步** 若不再同步請將if(manager.testOnlineServer())中的行為清除 
			/*
			if(manager.testOnlineServer()){
				try {
					Toolket.sendGraduateInfoByQueue(gstmd, "I"); // 同步Gstmd
					Toolket.sendStudentInfoByQueue(saveStmd(myForm), "D"); // 同步stmd
					Toolket.sendWWPassInfoByQueue(studentNo, gstmd.getIdno(), "C",
							"", "D"); // 同步wwpass
				} catch (Exception e) {
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
							"Course.messageN1", studentNo + studentName
									+ " 同步發生錯誤 , 請連絡電算中心!"));
					saveMessages(request, msg);
				}
			}
			*/
			//TODO 只為退完課產生問題的人建立訊息
			/*
			Member me = getUserCredential(request.getSession(false)).getMember();
			Message message=new Message();
			message.setCategory("Course");
			message.setSender(me.getName());
			
			message.setContent((String)stForm.get("studentNo")+(String)stForm.get("studentName")+"已輟學, 並且幫 "+(String)stForm.get("studentName")+
					"退了開在"+(String)stForm.get("classNo")+(String)stForm.get("className")+" 的課(以及所有加選的課), 請課務同仁檢查這些課程人數上下限是否受到影響, 謝謝!");
			message.setStartDate(new Date());
			Calendar c=Calendar.getInstance();
			c.setTime(message.getStartDate());
			c.set(Calendar.DAY_OF_YEAR,(c.get(Calendar.DAY_OF_YEAR) + 14));
			message.setDueDate(c.getTime());
			summerManager.saveObj(message);
			*/
			
			//建立Gmark
			List gmarkList=(List)session.getAttribute("gmarkList");
			//System.out.println(gmarkList.size());
			if (gmarkList!=null)
			if(gmarkList.size()>0){
				saveGmark(gmarkList);
			}
			session.removeAttribute("gmarkList");//<-- 再次確定移除 gmarkList
			session.removeAttribute("myGmark"); //<-- 再次確定移除 gmarkList
			//建立tran
			if(session.getAttribute("tran")!=null){
				manager.updateObject((Tran)session.getAttribute("tran"));
			}			
			//建立QuitResume
			if(session.getAttribute("quitresume")!=null){
				manager.updateObject((QuitResume)session.getAttribute("quitresume"));
			}
		}else{ //occur_status沒重表示為異動資訊而已
			Student stmd=(Student)manager.hqlGetBy("FROM Student WHERE Oid='"+(String)stForm.get("Oid")+"'").get(0); //一定有的啦
			stmd=saveStmd(myForm, stmd);
			//轉班同時處理的動作
			//System.out.println(request.getAttribute("changeDate"));
			if(session.getAttribute("changeDate").equals("Y"))
			if(stForm.get("occur_status").equals("5")){				
				String myDtime2[]=(String[])stForm.get("myDtime2");
				saveSeld((List)session.getAttribute("myDtime1"), myDtime2, stForm.get("studentNo").toString());//選課
				saveAdcd(stForm.get("studentNo").toString(), myDtime2); //加選
			}
					
			manager.updateObject(stmd);
			//TODO**學籍同步** 若不再同步請將if(manager.testOnlineServer())中的行為清除  
			/*
			if(manager.testOnlineServer()){
				try {
					Toolket.sendStudentInfoByQueue(saveStmd(myForm), "U"); // 同步stmd
				} catch (Exception e) {
					msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
							"Course.messageN1", studentNo + studentName
									+ " 同步發生錯誤 , 請連絡電算中心!"));
					saveMessages(request, msg);
				}
			}
			*/
			List gmarkList=(List)session.getAttribute("gmarkList");
			if(gmarkList!=null)
			if(gmarkList.size()>0){
				saveGmark(gmarkList);
			}

			//建立tran
			if(session.getAttribute("tran")!=null){
				manager.updateObject((Tran)session.getAttribute("tran"));
			}
		}
		
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",studentNo+studentName+"資料修改完成"));
		saveMessages(request, msg);	//完成
		removeSessionForComplete(request, form);		
		return mapping.findForward("Main");
		
		//return  Continue(mapping, form,	request, response);
	}
	
	/**
	 * 重設表單
	 */
	private DynaActionForm resetForm(DynaActionForm stForm){
		
		stForm.set("TOL_schl_name", "");//入學資格
		stForm.set("TOL_permission_no", "");//文號
		stForm.set("iType", "");//作業型態
		stForm.set("className", "");//班級名稱
		stForm.set("entrno", "");//入學核準號
		stForm.set("exSearch", "");//進階查詢
		stForm.set("GorS", "");//查詢目標為在學或輟學或全部
		stForm.set("studentName", "");//學生姓名
		stForm.set("studentNo", "");//學號
		stForm.set("classNo", "");//班級代碼
		stForm.set("idno", "");//身份證號
		stForm.set("occur_graduate_no", "");//畢業號
		stForm.set("sex", "");//性別
		stForm.set("birthday", "");//生日		
		stForm.set("entrance", null);//入學年月
		stForm.set("gradyear", null);//前學程畢業年月
		stForm.set("ident", "");//身份
		stForm.set("divi", "");//組別
		stForm.set("birth_province", "");//出生省
		stForm.set("birth_county", "");//出生縣
		stForm.set("parent_name", "");//家長姓名
		stForm.set("gradu_status", "");//前學程畢業狀態
		stForm.set("schl_code", "");//畢業學校代碼
		stForm.set("schl_name", "");//畢業學校名稱
		stForm.set("telephone", "");//行動電話
		stForm.set("curr_post", "");//通訊地郵編
		stForm.set("curr_addr", "");//通訊地址
		stForm.set("perm_post", "");//永久地郵編
		stForm.set("perm_addr", "");//永久地址
		stForm.set("ident_remark", "");//備註
		stForm.set("occur_year", null);//最後學籍變更學年
		stForm.set("occur_term", "");//最後學籍變更學期
		stForm.set("occur_date", "");//最後學籍變更日期
		stForm.set("occur_docno", "");//最後學籍變更文號
		stForm.set("occur_cause", "");//最後學籍變更原因
		stForm.set("occur_status", "");//最後學籍狀態
		stForm.set("grad_dept", "");//畢業科系
		stForm.set("Email", "");//電子郵件
		stForm.set("CellPhone", "");//行動電話
		//stForm.set("gradyear_origin", "");//???
		stForm.set("student_ename", "");//英文姓名
		stForm.set("ident_basic", "");//入學身份
		stForm.set("ExtraStatus", "");//輔修或雙主修
		stForm.set("ExtraDept", ""); // 輔修或雙主修哪一系 		
		stForm.set("myImage", null); // 照片檔		
		return stForm;
	}
	
	/**
	 * 把stmd的映射塞到dynactionform
	 * @param student
	 * @param stForm
	 * @return stForm
	 */
	private DynaActionForm setStudent(Student student, DynaActionForm stForm){
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		stForm.set("idno", student.getIdno());//身份證號
		stForm.set("studentNo", student.getStudentNo());//學號
		stForm.set("studentName", student.getStudentName());//學生姓名
		stForm.set("Oid", student.getOid().toString());//學生主鍵
		
		try{ //班級名稱, 這2種哪個快呢？
			stForm.set("className", ((Map)manager.ezGetBy("SELECT ClassName FROM Class WHERE ClassNo='"+student.getDepartClass()+"'").get(0)).get("ClassName"));//班級名稱
		}catch(Exception e){
			stForm.set("className", "");
		}
		
		stForm.set("classNo", student.getDepartClass());//班級代碼
		stForm.set("sex", student.getSex());//性別
		stForm.set("birthday", Toolket.printDate(student.getBirthday()));//生日
		stForm.set("birth_province", student.getBirthProvince());//出生省
		stForm.set("birth_county", student.getBirthCounty());//出生縣
		stForm.set("curr_post", student.getCurrPost());//通訊地郵編
		stForm.set("curr_addr", student.getCurrAddr());//通訊地址
		stForm.set("perm_post", student.getPermPost());//永久地郵編
		stForm.set("perm_addr", student.getPermAddr());//永久地址
		stForm.set("telephone", student.getTelephone());//電話
		stForm.set("CellPhone", student.getCellPhone());//行動電話
		stForm.set("parent_name", student.getParentName());//家長姓名
		stForm.set("student_ename", student.getStudentEname());//英文姓名
		stForm.set("ExtraStatus", student.getExtraStatus());//輔修或雙主修
		stForm.set("ExtraDept", student.getExtraDept()); // 輔修或雙主修哪一系 
		
		//幾個很令人討厭的short型態
		if(student.getEntrance()!=null&& !student.getEntrance().equals("")){
			stForm.set("entrance", student.getEntrance().toString());//入學年月
		}
		if(student.getGradyear()!=null&& !student.getGradyear().equals("")){
			stForm.set("gradyear", student.getGradyear().toString());//前學程畢業年月
		}
		if(student.getOccurYear()!=null&& !student.getOccurYear().equals("")){
			stForm.set("occur_year", student.getOccurYear().toString());//最後學籍變更學年
		}
		if(student.getOccurDate()!=null&& !student.getOccurDate().equals("")){
			stForm.set("occur_date", Toolket.printDate(student.getOccurDate()));//最後學籍變更日期
		}
		
		stForm.set("ident", student.getIdent());//身份
		stForm.set("divi", student.getDivi());//組別
		stForm.set("schl_code", student.getSchlCode());//畢業學校代碼
		stForm.set("schl_name", student.getSchlName());//畢業學校名稱
		stForm.set("grad_dept", student.getGradDept());//畢業科系
		stForm.set("gradu_status", student.getGraduStatus());//前學程畢業狀態
		stForm.set("occur_status", student.getOccurStatus());//最後學籍狀態
		stForm.set("occur_term", student.getOccurTerm());//最後學籍變更學期
		stForm.set("occur_docno", student.getOccurDocno());//最後學籍變更文號
		stForm.set("occur_cause", student.getOccurCause());//最後學籍變更原因
		stForm.set("occur_graduate_no", student.getOccurGraduateNo());//畢業號
		stForm.set("ident_remark", student.getIdentRemark());//備註
		stForm.set("Email", student.getEmail());//電子郵件
		stForm.set("ident_basic", student.getIdentBasic());//入學身份
		
		/*		
		stForm.set("TOL_schl_name", "");//入學資格
		stForm.set("TOL_permission_no", "");//文號
		stForm.set("iType", "");//作業型態
		stForm.set("entrno", "");//入學核準號
		stForm.set("exSearch", "");//進階查詢
		stForm.set("GorS", "");//查詢目標為在學或輟學或全部
		stForm.set("gradyear_origin", "");//??? 幹!
		*/
		
		return stForm;
	}
	
	/**
	 * 把Gstmd的映射塞到dynactionform
	 * @param gstmd
	 * @param stForm
	 * @return stForm
	 */
	private DynaActionForm setGstudent(Graduate gstmd, DynaActionForm stForm){
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		stForm.set("idno", gstmd.getIdno());//身份證號
		stForm.set("studentNo", gstmd.getStudentNo());//學號
		stForm.set("studentName", gstmd.getStudentName());//學生姓名
		stForm.set("Oid", gstmd.getOid().toString());//學生主鍵
		
		List clazz=manager.ezGetBy("SELECT ClassName FROM Class WHERE ClassNo='"+gstmd.getDepartClass()+"'");
		if(clazz.size()>0)
		stForm.set("className", ((Map)clazz.get(0)).get("ClassName"));//班級名稱
		
		stForm.set("classNo", gstmd.getDepartClass());//班級代碼
		stForm.set("sex", gstmd.getSex());//性別
		stForm.set("birthday", Toolket.printDate(gstmd.getBirthday()));//生日
		stForm.set("birth_province", gstmd.getBirthProvince());//出生省
		stForm.set("birth_county", gstmd.getBirthCounty());//出生縣
		stForm.set("curr_post", gstmd.getCurrPost());//通訊地郵編
		stForm.set("curr_addr", gstmd.getCurrAddr());//通訊地址
		stForm.set("perm_post", gstmd.getPermPost());//永久地郵編
		stForm.set("perm_addr", gstmd.getPermAddr());//永久地址
		stForm.set("telephone", gstmd.getTelephone());//電話
		stForm.set("CellPhone", gstmd.getCellPhone());//行動電話
		stForm.set("parent_name", gstmd.getParentName());//家長姓名
		stForm.set("student_ename", gstmd.getStudentEname());//英文姓名
		stForm.set("ExtraStatus", gstmd.getExtraStatus());//輔修或雙主修
		stForm.set("ExtraDept", gstmd.getExtraDept()); // 輔修或雙主修哪一系 

		//幾個很令人討厭的short型態
		if(gstmd.getEntrance()!=null){
			stForm.set("entrance", gstmd.getEntrance().toString());//入學年月
		}
		if(gstmd.getGradyear()!=null){
			stForm.set("gradyear", gstmd.getGradyear().toString());//前學程畢業年月
		}
		if(gstmd.getOccurYear()!=null){
			stForm.set("occur_year", gstmd.getOccurYear().toString());//最後學籍變更學年
		}
		if(gstmd.getOccurDate()!=null){
			stForm.set("occur_date", Toolket.printDate(gstmd.getOccurDate()));//最後學籍變更日期
		}
		
		stForm.set("ident", gstmd.getIdent());//身份
		stForm.set("divi", gstmd.getDivi());//組別
		stForm.set("schl_code", gstmd.getSchlCode());//畢業學校代碼
		stForm.set("schl_name", gstmd.getSchlName());//畢業學校名稱
		stForm.set("grad_dept", gstmd.getGradDept());//畢業科系
		stForm.set("gradu_status", gstmd.getGraduStatus());//前學程畢業狀態
		stForm.set("occur_status", gstmd.getOccurStatus());//最後學籍狀態
		stForm.set("occur_term", gstmd.getOccurTerm());//最後學籍變更學期
		stForm.set("occur_docno", gstmd.getOccurDocno());//最後學籍變更文號
		stForm.set("occur_cause", gstmd.getOccurCause());//最後學籍變更原因
		stForm.set("occur_graduate_no", gstmd.getOccurGraduateNo());//畢業號
		stForm.set("ident_remark", gstmd.getIdentRemark());//備註
		stForm.set("Email", gstmd.getEmail());//電子郵件
		stForm.set("ident_basic", gstmd.getIdentBasic());//入學身份
		
		/*		
		stForm.set("TOL_schl_name", "");//入學資格
		stForm.set("TOL_permission_no", "");//文號
		stForm.set("iType", "");//作業型態
		stForm.set("entrno", "");//入學核準號
		stForm.set("exSearch", "");//進階查詢
		stForm.set("GorS", "");//查詢目標為在學或輟學或全部
		stForm.set("gradyear_origin", "");//??? 幹!
		*/
		
		return stForm;
	}
	
	/**
	 * 清掉暫存的session
	 */
	private void removeSession(HttpServletRequest request, ActionForm form){		
		HttpSession session = request.getSession(false);
		DynaActionForm stForm = (DynaActionForm) form;		
		String student_no=stForm.getString("studentNo");
		String student_name=stForm.getString("studentName");		
		resetForm(stForm);
		stForm.set("studentNo", student_no);
		stForm.set("studentName", student_name);		
		session.removeAttribute("aStudent");
		session.removeAttribute("birth_province");
		session.removeAttribute("Gtype");
		session.removeAttribute("gradyear");		
		session.removeAttribute("entrance");
		session.removeAttribute("iType");
		session.removeAttribute("myDtime1");
		session.removeAttribute("myDtime2");
		session.removeAttribute("myDtime");
		session.removeAttribute("showBirthday");		
		//session.removeAttribute("students");
		session.removeAttribute("tran");
		session.removeAttribute("workType");
		session.removeAttribute("wwpass");
		session.removeAttribute("username");
		session.removeAttribute("password");		
		session.removeAttribute("gmarkList");		
		session.removeAttribute("quitresume");		
		session.removeAttribute("myGmark");
		session.removeAttribute("gmarkList");
		session.removeAttribute("myRmark");
		session.removeAttribute("myScore");
		session.removeAttribute("myQuitResume");
		session.removeAttribute("myTran");		
		session.removeAttribute("mdelete");
		session.removeAttribute("sdelete");
	}
	
	/**
	 * 不清掉結果的清除
	 * @param request
	 * @param form
	 */
	private void removeSessionForComplete(HttpServletRequest request, ActionForm form){
		
		HttpSession session = request.getSession(false);
		DynaActionForm stForm = (DynaActionForm) form;
		
		//String student_no=stForm.getString("studentNo");
		//String student_name=stForm.getString("studentName");
		
		resetForm(stForm);
		//stForm.set("studentNo", student_no);
		//stForm.set("studentName", student_name);
		session.removeAttribute("birth_province");
		session.removeAttribute("Gtype");
		session.removeAttribute("gradyear");
		session.removeAttribute("gmarkList");
		session.removeAttribute("entrance");
		session.removeAttribute("iType");		
		session.removeAttribute("myDtime1");
		session.removeAttribute("myDtime2");
		session.removeAttribute("myDtime");
		session.removeAttribute("showBirthday");		
		session.removeAttribute("tran");
		session.removeAttribute("workType");
		session.removeAttribute("wwpass");
		session.removeAttribute("username");
		session.removeAttribute("password");		
		session.removeAttribute("quitresume");		
		session.removeAttribute("myGmark");
		session.removeAttribute("myRmark");
		session.removeAttribute("myQuitResume");
		session.removeAttribute("myScore");
		session.removeAttribute("myTran");
		
		session.removeAttribute("mdelete");
		session.removeAttribute("sdelete");
	}
	
	/**
	 * 把dynactionform塞到Graduate映射
	 * 這裏不做Oid的存取
	 * @param aStudent
	 */
	private Graduate saveGstmd(Map aStudent, Graduate gstmd){		
		gstmd.setDepartClass((String)aStudent.get("classNo")); //班級代碼
		gstmd.setStudentNo((String)aStudent.get("studentNo")); //學號
		gstmd.setStudentName((String)aStudent.get("studentName")); //學生姓名
		gstmd.setBirthday(Toolket.parseDate(aStudent.get("birthday").toString())); //出生日
		gstmd.setSex((String)aStudent.get("sex")); //性別
		
		gstmd.setIdno((String)aStudent.get("idno")); //身份證
		if(aStudent.get("entrance")!=null && !aStudent.get("entrance").toString().trim().equals("")){
			gstmd.setEntrance(Short.parseShort((String)aStudent.get("entrance"))); //入學年月
		}
		
		if(aStudent.get("gradyear")!=null && !aStudent.get("gradyear").toString().trim().equals("")){
			gstmd.setGradyear(Short.parseShort((String)aStudent.get("gradyear"))); //前學程畢業年月
		}
		
		gstmd.setIdent((String)aStudent.get("ident")); //入學身份
		gstmd.setDivi((String)aStudent.get("divi")); //組別
		
		gstmd.setBirthProvince((String)aStudent.get("birth_province")); //出生省
		gstmd.setBirthCounty((String)aStudent.get("birth_county")); //出生縣
		gstmd.setCurrPost((String)aStudent.get("curr_post")); //通訊郵編
		gstmd.setCurrAddr((String)aStudent.get("curr_addr")); //通訊地址
		gstmd.setSchlCode((String)aStudent.get("schl_code")); //畢業學校代碼
		
		gstmd.setSchlName((String)aStudent.get("schl_name")); //畢業學校名稱
		gstmd.setGradDept((String)aStudent.get("grad_dept")); //畢業學校科系
		gstmd.setGraduStatus((String)aStudent.get("gradu_status")); //畢業狀況
		gstmd.setParentName((String)aStudent.get("parent_name")); //家長姓名
		gstmd.setTelephone((String)aStudent.get("telephone")); //連絡電話
		
		gstmd.setPermPost((String)aStudent.get("perm_post")); //永久地址郵編
		gstmd.setPermAddr((String)aStudent.get("perm_addr")); //永久地址		
		gstmd.setOccurStatus((String)aStudent.get("occur_status")); //最後一次變更學籍的狀態	
		
		if(!aStudent.get("occur_year").equals("")&& aStudent.get("occur_year")!=null)
		gstmd.setOccurYear(Short.parseShort(String.valueOf(aStudent.get("occur_year")))); //最後一次變更學籍的學年
		if(!aStudent.get("occur_term").equals("")&& aStudent.get("occur_term")!=null)
		gstmd.setOccurTerm(aStudent.get("occur_term").toString()); //最後一次變更學籍的學期
		Date occur_date=Toolket.parseDate(aStudent.get("occur_date").toString());
		gstmd.setOccurDate(occur_date); //最後一次變更學籍的日期
		
		gstmd.setExtraStatus((String)aStudent.get("ExtraStatus")); //輔系或雙主修 -n
		gstmd.setExtraDept((String)aStudent.get("ExtraDept")); //輔系或雙主修系別 -n
		gstmd.setEmail((String)aStudent.get("Email")); //電子郵件信箱
		gstmd.setCellPhone((String)aStudent.get("CellPhone")); //行動電話
		
		gstmd.setOccurGraduateNo((String)aStudent.get("occur_graduate_no")); //畢業號
		gstmd.setOccurCause((String)aStudent.get("occur_cause")); //最後一次變更學籍的原因
		gstmd.setIdentRemark((String)aStudent.get("ident_remark")); //備註
		gstmd.setOccurDocno((String)aStudent.get("occur_docno")); //最後一次變更學籍的文號
		gstmd.setStudentEname((String)aStudent.get("student_ename")); //英文名稱
		return gstmd;
	}

	/**
	 * 把dynactionform塞到Graduate映射
	 * 這裏不做Oid的存取
	 * @param aStudent
	 */
	private Graduate saveGstmd(Map aStudent){
		Graduate gstmd=new Graduate();
		gstmd.setDepartClass((String)aStudent.get("classNo")); //班級代碼
		gstmd.setStudentNo((String)aStudent.get("studentNo")); //學號
		gstmd.setStudentName((String)aStudent.get("studentName")); //學生姓名
		gstmd.setBirthday(Toolket.parseDate(aStudent.get("birthday").toString())); //出生日
		gstmd.setSex((String)aStudent.get("sex")); //性別		
		gstmd.setIdno((String)aStudent.get("idno")); //身份證
		if(aStudent.get("entrance")!=null && !aStudent.get("entrance").toString().trim().equals("")){
			gstmd.setEntrance(Short.parseShort((String)aStudent.get("entrance"))); //入學年月
		}		
		if(aStudent.get("gradyear")!=null && !aStudent.get("gradyear").toString().trim().equals("")){
			gstmd.setGradyear(Short.parseShort((String)aStudent.get("gradyear"))); //前學程畢業年月
		}		
		gstmd.setIdent((String)aStudent.get("ident")); //入學身份
		gstmd.setDivi((String)aStudent.get("divi")); //組別		
		gstmd.setBirthProvince((String)aStudent.get("birth_province")); //出生省
		gstmd.setBirthCounty((String)aStudent.get("birth_county")); //出生縣
		gstmd.setCurrPost((String)aStudent.get("curr_post")); //通訊郵編
		gstmd.setCurrAddr((String)aStudent.get("curr_addr")); //通訊地址
		gstmd.setSchlCode((String)aStudent.get("schl_code")); //畢業學校代碼		
		gstmd.setSchlName((String)aStudent.get("schl_name")); //畢業學校名稱
		gstmd.setGradDept((String)aStudent.get("grad_dept")); //畢業學校科系
		gstmd.setGraduStatus((String)aStudent.get("gradu_status")); //畢業狀況
		gstmd.setParentName((String)aStudent.get("parent_name")); //家長姓名
		gstmd.setTelephone((String)aStudent.get("telephone")); //連絡電話		
		gstmd.setPermPost((String)aStudent.get("perm_post")); //永久地址郵編
		gstmd.setPermAddr((String)aStudent.get("perm_addr")); //永久地址		
		gstmd.setOccurStatus((String)aStudent.get("occur_status")); //最後一次變更學籍的狀態			
		if(!aStudent.get("occur_year").equals("")&& aStudent.get("occur_year")!=null)
		gstmd.setOccurYear(Short.valueOf((String)aStudent.get("occur_year"))); //最後一次變更學籍的學年
		if(!aStudent.get("occur_term").equals("")&& aStudent.get("occur_term")!=null)
		gstmd.setOccurTerm((String)aStudent.get("occur_term")); //最後一次變更學籍的學期		
		Date occur_date=Toolket.parseDate(aStudent.get("occur_date").toString());
		gstmd.setOccurDate(occur_date); //最後一次變更學籍的日期		
		gstmd.setExtraStatus((String)aStudent.get("ExtraStatus")); //輔系或雙主修 -n
		gstmd.setExtraDept((String)aStudent.get("ExtraDept")); //輔系或雙主修系別 -n
		gstmd.setEmail((String)aStudent.get("Email")); //電子郵件信箱
		gstmd.setCellPhone((String)aStudent.get("CellPhone")); //行動電話		
		gstmd.setOccurGraduateNo((String)aStudent.get("occur_graduate_no")); //畢業號
		gstmd.setOccurCause((String)aStudent.get("occur_cause")); //最後一次變更學籍的原因
		gstmd.setIdentRemark((String)aStudent.get("ident_remark")); //備註
		gstmd.setOccurDocno((String)aStudent.get("occur_docno")); //最後一次變更學籍的文號
		gstmd.setStudentEname((String)aStudent.get("student_ename")); //英文名稱
		return gstmd;
	}

	/**
	 * 把dynactionform塞到Stmd映射
	 * 這裏不做Oid的存取
	 * @param aStudent
	 */
	private Student saveStmd(Map aStudent){
		Student stmd=new Student();		
		stmd.setDepartClass((String)aStudent.get("classNo")); //班級代碼
		stmd.setStudentNo((String)aStudent.get("studentNo")); //學號
		stmd.setStudentName((String)aStudent.get("studentName")); //學生姓名
		stmd.setBirthday(Toolket.parseDate((String)aStudent.get("birthday"))); //出生日
		stmd.setSex((String)aStudent.get("sex")); //性別		
		stmd.setIdno((String)aStudent.get("idno")); //身份證
		if(!aStudent.get("entrance").equals("")&& aStudent.get("entrance")!=null){
			stmd.setEntrance(Short.parseShort((String)aStudent.get("entrance"))); //入學年月
		}		
		if(!aStudent.get("gradyear").equals("")&& aStudent.get("gradyear")!=null){
			stmd.setGradyear(Short.parseShort((String)aStudent.get("gradyear"))); //前學程畢業年月
		}		
		stmd.setIdent((String)aStudent.get("ident")); //入學身份
		stmd.setDivi((String)aStudent.get("divi")); //組別		
		stmd.setBirthProvince((String)aStudent.get("birth_province")); //出生省
		stmd.setBirthCounty((String)aStudent.get("birth_county")); //出生縣
		stmd.setCurrPost((String)aStudent.get("curr_post")); //通訊郵編
		stmd.setCurrAddr((String)aStudent.get("curr_addr")); //通訊地址
		stmd.setSchlCode((String)aStudent.get("schl_code")); //畢業學校代碼		
		stmd.setSchlName((String)aStudent.get("schl_name")); //畢業學校名稱
		stmd.setGradDept((String)aStudent.get("grad_dept")); //畢業學校科系
		stmd.setGraduStatus((String)aStudent.get("gradu_status")); //畢業狀況
		stmd.setParentName((String)aStudent.get("parent_name")); //家長姓名
		stmd.setTelephone((String)aStudent.get("telephone")); //連絡電話		
		stmd.setPermPost((String)aStudent.get("perm_post")); //永久地址郵編
		stmd.setPermAddr((String)aStudent.get("perm_addr")); //永久地址		
		stmd.setOccurStatus((String)aStudent.get("occur_status")); //最後一次變更學籍的狀態		
		if(!aStudent.get("occur_year").equals("")&& aStudent.get("occur_year")!=null)
		stmd.setOccurYear(Short.parseShort(aStudent.get("occur_year").toString())); //最後一次變更學籍的學年		
		if(!aStudent.get("occur_term").equals("")&& aStudent.get("occur_term")!=null)
		stmd.setOccurTerm(aStudent.get("occur_term").toString()); //最後一次變更學籍的學期
		Date occur_date=Toolket.parseDate(aStudent.get("occur_date").toString());
		stmd.setOccurDate(occur_date); //最後一次變更學籍的日期
		stmd.setExtraStatus((String)aStudent.get("ExtraStatus")); //輔系或雙主修 -n
		stmd.setExtraDept((String)aStudent.get("ExtraDept")); //輔系或雙主修系別 -n
		stmd.setEmail((String)aStudent.get("Email")); //電子郵件信箱
		stmd.setCellPhone((String)aStudent.get("CellPhone")); //行動電話		
		stmd.setOccurGraduateNo((String)aStudent.get("occur_graduate_no")); //畢業號
		stmd.setOccurCause((String)aStudent.get("occur_cause")); //最後一次變更學籍的原因
		stmd.setIdentRemark((String)aStudent.get("ident_remark")); //備註
		stmd.setOccurDocno((String)aStudent.get("occur_docno")); //最後一次變更學籍的文號
		stmd.setStudentEname((String)aStudent.get("student_ename")); //英文名稱		
		return stmd;
	}
	
	/**
	 * 把dynactionform塞到Stmd映射
	 * @param aStudent
	 */
	private Student saveStmd(Map aStudent, Student stmd){		
		stmd.setDepartClass((String)aStudent.get("classNo")); //班級代碼
		stmd.setStudentNo((String)aStudent.get("studentNo")); //學號
		stmd.setStudentName((String)aStudent.get("studentName")); //學生姓名
		stmd.setBirthday(Toolket.parseDate((String)aStudent.get("birthday"))); //出生日
		stmd.setSex((String)aStudent.get("sex")); //性別		
		stmd.setIdno((String)aStudent.get("idno")); //身份證		
		if(!aStudent.get("entrance").equals("")&& aStudent.get("gradyear")!=null){
			stmd.setEntrance(Short.parseShort((String)aStudent.get("entrance"))); //入學年月
		}		
		if(!aStudent.get("gradyear").equals("")&& aStudent.get("gradyear")!=null){
			stmd.setGradyear(Short.parseShort((String)aStudent.get("gradyear"))); //前學程畢業年月
		}		
		stmd.setIdent((String)aStudent.get("ident")); //入學身份
		stmd.setDivi((String)aStudent.get("divi")); //組別		
		stmd.setBirthProvince((String)aStudent.get("birth_province")); //出生省
		stmd.setBirthCounty((String)aStudent.get("birth_county")); //出生縣
		stmd.setCurrPost((String)aStudent.get("curr_post")); //通訊郵編
		stmd.setCurrAddr((String)aStudent.get("curr_addr")); //通訊地址
		stmd.setSchlCode((String)aStudent.get("schl_code")); //畢業學校代碼		
		stmd.setSchlName((String)aStudent.get("schl_name")); //畢業學校名稱
		stmd.setGradDept((String)aStudent.get("grad_dept")); //畢業學校科系
		stmd.setGraduStatus((String)aStudent.get("gradu_status")); //畢業狀況
		stmd.setParentName((String)aStudent.get("parent_name")); //家長姓名
		stmd.setTelephone((String)aStudent.get("telephone")); //連絡電話		
		stmd.setPermPost((String)aStudent.get("perm_post")); //永久地址郵編
		stmd.setPermAddr((String)aStudent.get("perm_addr")); //永久地址		
		stmd.setOccurStatus((String)aStudent.get("occur_status")); //最後一次變更學籍的狀態	
		if(!aStudent.get("occur_year").equals("")&& aStudent.get("occur_year")!=null)
		stmd.setOccurYear(Short.parseShort(aStudent.get("occur_year").toString())); //最後一次變更學籍的學年
		if(!aStudent.get("occur_term").equals("")&& aStudent.get("occur_term")!=null)
		stmd.setOccurTerm(aStudent.get("occur_term").toString()); //最後一次變更學籍的學期		
		//stmd.setOccurDate(new Date()); //最後一次變更學籍的日期
		Date occur_date=Toolket.parseDate(aStudent.get("occur_date").toString());
		stmd.setOccurDate(occur_date); //最後一次變更學籍的日期		
		stmd.setExtraStatus((String)aStudent.get("ExtraStatus")); //輔系或雙主修 -n
		stmd.setExtraDept((String)aStudent.get("ExtraDept")); //輔系或雙主修系別 -n
		stmd.setEmail((String)aStudent.get("Email")); //電子郵件信箱
		stmd.setCellPhone((String)aStudent.get("CellPhone")); //行動電話		
		stmd.setOccurGraduateNo((String)aStudent.get("occur_graduate_no")); //畢業號
		stmd.setOccurCause((String)aStudent.get("occur_cause")); //最後一次變更學籍的原因
		stmd.setIdentRemark((String)aStudent.get("ident_remark")); //備註
		stmd.setOccurDocno((String)aStudent.get("occur_docno")); //最後一次變更學籍的文號
		stmd.setStudentEname((String)aStudent.get("student_ename")); //英文名稱
		return stmd;
	}
	
	/**
	 * 建立帳號密碼
	 * 1.無論如何均進行刪除 2.建立
	 * @param studentNo
	 * @param idno
	 */
	private void saveWwpass(String studentNo, String idno){
		CourseManager manager = (CourseManager) getBean("courseManager");
		WwPass wwpass=new WwPass();
		wwpass.setPassword(idno);
		wwpass.setPriority("C");
		wwpass.setUsername(studentNo);		
		manager.executeSql("DELETE FROM wwpass WHERE username='"+studentNo+"'");
		manager.updateObject(wwpass);
	}
	
	/**
	 * 建立選課及成績
	 * @param myDtime1
	 * @param myDtime2
	 */
	private void saveSeld(List myDtime1, String[]myDtime2, String studentNo){
		CourseManager manager = (CourseManager) getBean("courseManager");
		Seld seld;
		//Regs regs;
		manager.executeSql("DELETE FROM Seld WHERE student_no='"+studentNo+"'");
		//manager.executeSql("DELETE FROM Regs WHERE student_no='"+studentNo+"'");		
		try{
			for(int i=0; i<myDtime1.size(); i++){
				//必修課程
				seld=new Seld();
				seld.setCredit(Float.parseFloat(((Map)myDtime1.get(i)).get("credit").toString()));
				seld.setDtimeOid(Integer.parseInt(((Map)myDtime1.get(i)).get("Oid").toString()));
				seld.setOpt(((Map)myDtime1.get(i)).get("opt").toString());
				seld.setStudentNo(studentNo);
				manager.updateObject(seld);	
				/*regs=new Regs();
				regs.setDtimeOid(Integer.parseInt(((Map)myDtime1.get(i)).get("Oid").toString()));
				regs.setStudentNo(studentNo);
				manager.updateObject(regs);*/
			}
			//選修課程
			for(int i=0; i<myDtime2.length; i++){
				seld=new Seld();
				Dtime dtime=(Dtime)manager.hqlGetBy("FROM Dtime WHERE Oid='"+myDtime2[i]+"'").get(0);
				seld.setCredit(dtime.getCredit());
				seld.setDtimeOid(dtime.getOid());
				seld.setOpt(dtime.getOpt());
				seld.setStudentNo(studentNo);
				manager.updateObject(seld);				
				/*regs=new Regs();
				regs.setDtimeOid(dtime.getOid());
				regs.setStudentNo(studentNo);
				manager.updateObject(regs);*/
			}
		}catch(Exception e){
			
		}		
	}
	
	/**
	 * 清當學期相關資料
	 * @param studentNo 要休退的人
	 * @param schoolyear 進歷史的學年
	 * @param schoolterm 學期
	 * @return 一堆有的沒的
	 */
	//TODO 2007/9/20尚無任何獎懲記錄因此無法測試正確性
	private void delStuProperty(String studentNo, String schoolYear, String schoolTerm){
		CourseManager manager = (CourseManager) getBean("courseManager");
		manager.executeSql("DELETE FROM Seld WHERE student_no='"+studentNo+"'"); //清選課
		manager.executeSql("DELETE FROM AddDelCourseData WHERE Student_no='"+studentNo+"'"); //清加退
		manager.executeSql("DELETE FROM Regs WHERE student_no='"+studentNo+"'"); //清成績
		manager.executeSql("DELETE FROM wwpass WHERE username='"+studentNo+"'");//清帳號
		manager.executeSql("DELETE FROM Dilg WHERE student_no='"+studentNo+"'"); //清請假
		// 獎懲進歷史
		List desdList=manager.hqlGetBy("FROM Desd WHERE studentNo='"+studentNo+"'");
		for(int i=0; i<desdList.size(); i++){
			Desd desd=(Desd)desdList.get(i);
			Savedesd savedesd=new Savedesd();// 搬
			savedesd.setCnt1(desd.getCnt1());// 塞
			savedesd.setCnt2(desd.getCnt2());
			savedesd.setDdate(desd.getDdate());
			savedesd.setDepartClass(desd.getDepartClass());
			savedesd.setKind1(desd.getKind1());
			savedesd.setKind2(desd.getKind2());
			savedesd.setNo(desd.getNo());
			savedesd.setReason(desd.getReason());
			savedesd.setSchoolYear(Short.parseShort(schoolYear));
			savedesd.setSchoolTerm(Short.parseShort(schoolTerm));
			savedesd.setStudentNo(studentNo);
			manager.updateObject(savedesd); // 存
		}
		manager.executeSql("DELETE FROM desd WHERE student_no='"+studentNo+"'"); //刪
	}
	
	/**
	 * 批次更新Gmark
	 * @param gmarkList
	 */
	private void saveGmark(List gmarkList){
		CourseManager manager = (CourseManager) getBean("courseManager");
		Gmark gmark;
		for(int i=0; i<gmarkList.size(); i++){
			gmark=(Gmark)gmarkList.get(i);
			
			try{
				manager.updateObject(gmark);
			}catch(Exception e){
				//e.printStackTrace();真是猜不透啊
			}			
		}
	}
	
	/**
	 * 置換照片
	 * @param myImage
	 * @param OstudrntNo
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void saveImage(FormFile myImage, String studentNo) throws FileNotFoundException, IOException{
		CourseManager manager = (CourseManager) getBean("courseManager");
		manager.executeSql("DELETE FROM StdImage WHERE studentNo='"+studentNo+"'"); //無論如何只殺不留
		StdImage image=new StdImage();
		InputStream stream= myImage.getInputStream();
		Blob photo = Hibernate.createBlob(stream);
		image.setImage(photo);
		image.setStudentNo(studentNo);
		manager.updateObject(image);
		stream.close();
		photo=null;
	}
	
	/**
	 * 管理權限(以班級為單位)
	 * @param departClass
	 * @param request
	 * @return
	 */
	private boolean checkMyStudent(String departClass, HttpServletRequest request){
		HttpSession session = request.getSession(false);
		UserCredential credential = (UserCredential) session.getAttribute("Credential");
		Clazz[] classes = credential.getClassInChargeAry();
		for(int i=0; i<classes.length; i++){
			if(classes[i].getClassNo().equals(departClass)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 管理權限(以學生為單位)
	 * @param departClass
	 * @param request
	 * @return
	 */
	private boolean checkMyStudentNo(String studentNo, HttpServletRequest request){
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		String departClass=manager.ezGetString("SELECT depart_class FROM stmd WHERE student_no='"+studentNo+"'");
		
		if(departClass==null){
			departClass=manager.ezGetString("SELECT depart_class FROM Gstmd WHERE student_no='"+studentNo+"'");
		}
		
		UserCredential credential = (UserCredential) session.getAttribute("Credential");
		Clazz[] classes = credential.getClassInChargeAry();
		for(int i=0; i<classes.length; i++){
			if(classes[i].getClassNo().equals(departClass)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 建立加選記錄 (學院必選修)
	 */
	private void saveAdcd(String studentNo, String myDtime2[]){
		CourseManager manager = (CourseManager) getBean("courseManager");
		manager.executeSql("DELETE FROM AddDelCourseData WHERE student_no='"+studentNo+"'");
		//Dtime dtime;
		Adcd adcd;
		for(int i=0; i<myDtime2.length; i++){			
			//dtime=(Dtime)manager.hqlGetBy("FROM Dtime WHERE Oid='"+myDtime2[i]+"'").get(0);
			Student stmd=(Student)manager.hqlGetBy("FROM Student WHERE studentNo='"+studentNo+"'").get(0);			
			adcd=new Adcd();
			adcd.setAdddraw("A");
			adcd.setDtimeOid(Integer.parseInt(myDtime2[i]));
			adcd.setStudentNo(studentNo);
			adcd.setStudepartClass(stmd.getDepartClass());			
			manager.updateObject(adcd);
		}
		//dtime=null;
	}
	
	/**
	 * 尋找各項屬於他的記錄
	 * @param studentNo
	 * @param request
	 */
	private void saveOther(String studentNo,HttpServletRequest request){
		CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);
		session.setAttribute("myGmark", manager.getGmark(studentNo)); //存歷年更變記錄
		session.setAttribute("myRmark", manager.ezGetMap("SELECT * FROM Rmark WHERE student_no='"+studentNo+"'")); //存歷年更變記錄
		List list=manager.ezGetBy("SELECT * FROM QuitResume WHERE student_no='"+studentNo+"'");
		for(int i=0; i<list.size(); i++){			
			if(((Map)list.get(i)).get("occur_date")!=null&&!((Map)list.get(i)).get("occur_date").equals("")){
				((Map)list.get(i)).put("occur_date", manager.convertDate(((Map)list.get(i)).get("occur_date").toString()));
			}
			if(((Map)list.get(i)).get("recov_date")!=null&&!((Map)list.get(i)).get("recov_date").equals("")){
				((Map)list.get(i)).put("recov_date", manager.convertDate(((Map)list.get(i)).get("recov_date").toString()));
			}
		}		
		session.setAttribute("myQuitResume", list); //存歷年休退記錄
		
		//歷年成績
		list=manager.ezGetBy("SELECT school_year FROM ScoreHist WHERE student_no='"+studentNo+"' " +
		"GROUP BY school_year ORDER BY school_year");
		List myScoreHist=new ArrayList();
		Map map;
		
		for(int i=0; i<list.size(); i++){			
			map=new HashMap();
			map.put("school_year", ((Map)list.get(i)).get("school_year"));
			map.put("scores", manager.getScoreHistBy(studentNo, ((Map)list.get(i)).get("school_year").toString()));
			
			myScoreHist.add(map);
		}
		session.setAttribute("myScore", myScoreHist); //存歷年成績
		
		//轉班資訊
		session.setAttribute("myTran", manager.ezGetBy("SELECT nc.ClassName as nClass, oc.ClassName as oClass, t.occur_date, " +
				"t.occur_docno, t.occur_year, t.occur_term FROM Tran t, Class nc, Class oc WHERE t.new_depart_class=nc.ClassNo AND " +
				"t.old_depart_class=oc.ClassNo AND t.student_no='"+studentNo+"'"));
		//轉學資訊
		session.setAttribute("myTraNo", manager.ezGetBy("SELECT * FROM Tranno WHERE student_no='"+studentNo+"'"));
	}
	
	/**
	 * 新一個Gmark 轉學唯一...
	 * @param school_year
	 * @param school_term
	 * @param student_no
	 * @param remark
	 * @param occur_status
	 * @param occur_cause
	 */
	private void saveGmark(String school_year, String school_term, String student_no, 
			String remark, String occur_status, String occur_cause){
		CourseManager manager = (CourseManager) getBean("courseManager");
		Gmark g=new Gmark();
		g.setSchoolYear(Short.parseShort(school_year));
		g.setSchoolTerm(Short.parseShort(school_term));
		g.setStudentNo(student_no);
		g.setRemark(remark);
		g.setOccurStatus(occur_status);
		g.setOccurCause(occur_cause);
		manager.updateObject(g);
	}	
	
	/**
	 * 存轉學記錄？
	 * @param aStudent
	 */
	private void saveTranNo(Map aStudent){
		CourseManager manager = (CourseManager) getBean("courseManager");
		Tranno tranno=new Tranno();
		tranno.setPermissionNo(aStudent.get("TOL_permission_no").toString());
		tranno.setSchlName(aStudent.get("TOL_permission_no").toString());
		tranno.setStudentNo(aStudent.get("studentNo").toString());	
		
		manager.updateObject(tranno);		
	}
	
	/**
	 * 印xx
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward print(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//DynaActionForm chForm = (DynaActionForm) form;		
		//HttpSession session = request.getSession(false);
		//CourseManager manager = (CourseManager) getBean("courseManager");		
		//System.out.println(chForm.getString("printoption"));
		response.sendRedirect("/CIS/Print/CountClasses.do");		
		return null;
		//return mapping.findForward("Main");
	}
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		Toolket.resetCheckboxCookie(response, "stus");
		removeSession(request, form); //移除前次搜尋
		removeSessionForComplete(request, form);		
		
		session.setAttribute("occur_status", manager.ezGetBy("SELECT idno, name FROM code5 WHERE category='Status'")); //自動完成"狀態"
		session.setAttribute("cause", manager.ezGetBy("SELECT idno, name FROM code5 WHERE category='Cause'")); //自動完成"原因"
		session.setAttribute("ident", manager.ezGetBy("SELECT idno, name FROM code5 WHERE category='Identity'")); //自動完成"身份"
		session.setAttribute("Group", manager.ezGetBy("SELECT idno, name FROM code5 WHERE category='Group'")); //自動完成"組別"
		session.setAttribute("Dept", manager.ezGetBy("SELECT idno, name FROM code5 WHERE category='Dept'")); //自動完成"輔系雙主修"
		
		session.setAttribute("Group", manager.ezGetBy("SELECT idno, name FROM code5 WHERE category='Group'")); //自動完成"組別"
		session.setAttribute("birth_province", manager.ezGetBy("SELECT no, name FROM code3")); //自動完成"省/國"
		
		//DynaActionForm stForm = resetForm((DynaActionForm) form); //重設表單
		setContentPage(request.getSession(false), "registration/StudentManager.jsp");
		return mapping.findForward("Main");
	}
}
