package tw.edu.chit.struts.action.portfolio;

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

import tw.edu.chit.model.EpsContent;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class EPortfolioManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {

		CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);
			
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		
		String Uid=c.getMember().getAccount();//取得使用者帳號
		if(Uid==null){
			Uid=c.getGstudent().getStudentNo();
		}
		request.setAttribute("myUrl", manager.myPortfolioUrl(Uid));
		
		Map mySite=manager.ezGetMap("SELECT * FROM Eps_user WHERE Uid='"+Uid+"'");		
		if(!manager.portfolioExist(Uid)){
			request.setAttribute("notExist", true);
			ActionMessages msg = new ActionMessages();
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "歡迎啟用歷程檔案"));
			saveMessages(request, msg);	
		}		
		
		if(mySite!=null){
			request.setAttribute("mySite", mySite);
			request.setAttribute("myIndex", manager.ezGetString("SELECT content FROM Eps_pages WHERE title='index' AND Uid='"+Uid+"'"));
		}
		
		String table_oid=request.getParameter("table_oid");
		if(table_oid!=null)
		if(table_oid.equals("")){
			table_oid=null;
		}
		DynaActionForm aForm = (DynaActionForm) form;		
		request.setAttribute("Uid", Uid);
		
		//未建立的提示
		if(!manager.portfolioExist(Uid)){
			request.setAttribute("notExist", true);
			ActionMessages msg = new ActionMessages();
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","請先建立網站! 系統已自動重新定位至「網站管理」"));
			saveMessages(request, msg);	//完成			
						
			request.setAttribute("headers", manager.ezGetBy("SELECT * FROM Eps_template WHERE type='H'"));
			request.setAttribute("footers", manager.ezGetBy("SELECT * FROM Eps_template WHERE type='F'"));
			request.setAttribute("banners", manager.ezGetBy("SELECT * FROM Eps_template WHERE type='B'"));
			
			setContentPage(request.getSession(false), "portfolio/SiteManager.jsp");
			//setContentPage(request.getSession(false), "portfolio/TemplateManager.jsp");
			return mapping.findForward("Main");
		}
		
		//取得所有類別
		//System.out.println("type="+c.getMember().getPriority());
		String category="C";
		if(c.getMember().getPriority().equals("A")){
			category="A";
		}
		
		List allTable=manager.ezGetBy("SELECT * FROM Eps_table WHERE sys='1' AND category='"+category+"' ORDER BY sys, sequence");
		List bllTable=manager.ezGetBy("SELECT * FROM Eps_table WHERE sys='0' AND category='"+category+"' ORDER BY sys, sequence");
		request.setAttribute("allTable", allTable);
		request.setAttribute("bllTable", bllTable);
		request.setAttribute("cllTable", manager.ezGetBy("SELECT tag FROM Eps_free WHERE Uid='"+Uid+"' GROUP BY tag"));
		
		String sys_table;
		Map map=null;
		if(table_oid!=null){//有點選的話給予點選值
			getFormat(request, table_oid, Uid, false);			
			sys_table=manager.ezGetString("SELECT sys_table FROM Eps_table WHERE Oid='"+table_oid+"' AND category='"+category+"'");			
			//系統功能
			if(sys_table!=null){
				//基本資料
				if(sys_table.equals("stmd")){
					
					map=manager.ezGetMap("SELECT s.student_no, s.student_name, s.birthday, s.Email, c.ClassName, d.fname, " +
							"d.school_name FROM(stmd s LEFT OUTER JOIN Class c ON s.depart_class=c.ClassNo) LEFT OUTER JOIN dept d ON c.Dept=d.no " +
							"WHERE s.student_no='"+Uid+"'");
					if(map==null){
						map=manager.ezGetMap("SELECT s.student_no, s.student_name, s.birthday, s.Email, c.ClassName, d.fname, " +
								"d.school_name FROM(Gstmd s LEFT OUTER JOIN Class c ON s.depart_class=c.ClassNo) LEFT OUTER JOIN dept d ON c.Dept=d.no " +
								"WHERE s.student_no='"+Uid+"'");
					}
					request.setAttribute("stmd", map);	
				}
				
				
				//本學期修課
				if(sys_table.equals("Seld")){
					
					request.setAttribute("seld", manager.ezGetBy("SELECT c.chi_name, c.cscode, d.credit, d.thour " +
							"FROM Seld s, Csno c, Dtime d WHERE s.Dtime_oid=d.Oid AND c.cscode=d.cscode AND " +
							"s.student_no='"+Uid+"' AND d.Sterm='"+manager.getSchoolTerm()+"'"));
				}
				
				//獎懲記錄
				if(sys_table.equals("Savedesd")){
					//System.out.println("sys_table="+sys_table);
					List Savedesd=manager.ezGetBy("SELECT d.school_year, d.school_term, c.name as kind1, " +
							"d.cnt1, c1.name as kind2, d.cnt2, c2.name FROM stmd s, ((Savedesd d LEFT OUTER JOIN code5 c ON " +
							"c.category='BonusPenalty' AND c.idno=d.kind1) LEFT OUTER JOIN code5 c1 ON c1.category='BonusPenalty' " +
							"AND c1.idno=d.kind2) LEFT OUTER JOIN code2 c2 ON c2.no=d.reason WHERE s.student_no=d.student_no AND s.student_no='"+Uid+"'");
					if(Savedesd.size()<1){
						Savedesd=manager.ezGetBy("SELECT d.school_year, d.school_term, c.name as kind1, " +
								"d.cnt1, c1.name as kind2, d.cnt2, c2.name FROM Gstmd s, ((Savedesd d LEFT OUTER JOIN code5 c ON " +
								"c.category='BonusPenalty' AND c.idno=d.kind1) LEFT OUTER JOIN code5 c1 ON c1.category='BonusPenalty' " +
								"AND c1.idno=d.kind2) LEFT OUTER JOIN code2 c2 ON c2.no=d.reason WHERE s.student_no=d.student_no AND s.student_no='"+Uid+"'");
					}							
					request.setAttribute("Savedesd", Savedesd);
				}
				
				//歷年操行
				if(sys_table.equals("Savejust")){
					//System.out.println("sys_table="+sys_table);
					List Savejust=manager.ezGetBy("SELECT j.* FROM stmd s, Savejust j WHERE " +
					"j.student_no=s.student_no AND s.student_no='"+Uid+"'");
					if(Savejust.size()<1){
						Savejust=manager.ezGetBy("SELECT j.* FROM Gstmd s, Savejust j WHERE " +
						"j.student_no=s.student_no AND s.student_no='"+Uid+"'");
					}							
					request.setAttribute("Savejust", Savejust);
				}
				
				//歷年成績
				if(sys_table.equals("ScoreHist")){
					List ScoreHist=manager.ezGetBy("SELECT sh.school_year, sh.school_term, c.chi_name, sh.opt, " +
							"sh.credit, sh.evgr_type, sh.score FROM ScoreHist sh, stmd s, Csno c WHERE c.cscode=sh.cscode AND " +
							"sh.student_no=s.student_no AND s.student_no='"+Uid+"' ORDER BY sh.school_year, sh.school_term");
					if(ScoreHist.size()<1){
						ScoreHist=manager.ezGetBy("SELECT sh.school_year, sh.school_term, c.chi_name, sh.opt, " +
								"sh.credit, sh.evgr_type, sh.score FROM ScoreHist sh, Gstmd s, Csno c WHERE c.cscode=sh.cscode AND " +
								"sh.student_no=s.student_no AND s.student_no='"+Uid+"' ORDER BY sh.school_year, sh.school_term");
					}
					for(Object i : ScoreHist){						
						if(((Map)i).get("opt").equals("1")){((Map)i).put("opt", "必修");}						
						if(((Map)i).get("opt").equals("2")){((Map)i).put("opt", "選修");}						
						if(((Map)i).get("opt").equals("3")){((Map)i).put("opt", "通識");}						
						if(!((Map)i).get("evgr_type").equals("1")){
							if(((Map)i).get("evgr_type").equals("2")){((Map)i).put("evgr_type", "隨班附讀");}							
							if(((Map)i).get("evgr_type").equals("3")){((Map)i).put("evgr_type", "暑修");}							
							if(((Map)i).get("evgr_type").equals("4")){((Map)i).put("evgr_type", "跨校");}							
							if(((Map)i).get("evgr_type").equals("5")){((Map)i).put("evgr_type", "待補修");}
							if(((Map)i).get("evgr_type").equals("6")){((Map)i).put("evgr_type", "辦理抵免");}
						}else{
							((Map)i).put("evgr_type", "");
						}						
					}					
					request.setAttribute("ScoreHist", ScoreHist);
				}
				
				//學程資格
				if(sys_table.equals("CsGroup")){					
					Map aStudent=manager.ezGetMap("SELECT st.*, c.*, c5.name as schoolName, c51.name as deptName FROM " +
							"((stmd st LEFT JOIN Class c ON st.depart_class=c.ClassNo)LEFT JOIN " +
							"code5 c5 ON c5.idno=c.SchoolNo)LEFT JOIN code5 c51 ON c51.idno=c.DeptNo WHERE " +
							"c5.category='School' AND c51.category='Dept' AND st.student_no='"+Uid+"'");
					if(aStudent==null){
						aStudent=manager.ezGetMap("SELECT st.*, c.*, c5.name as schoolName, c51.name as deptName FROM " +
								"((Gstmd st LEFT JOIN Class c ON st.depart_class=c.ClassNo)LEFT JOIN " +
								"code5 c5 ON c5.idno=c.SchoolNo)LEFT JOIN code5 c51 ON c51.idno=c.DeptNo WHERE " +
								"c5.category='School' AND c51.category='Dept' AND st.student_no='"+Uid+"'");
					}					
					request.setAttribute("aStudent", aStudent);			
					request.setAttribute("CsGroup", manager.CsGroup4One(Uid, aStudent, true));
				}				
				
				//課表
				if(sys_table.equals("Table")){					
					List Table=new ArrayList();					
					StringBuilder sb;
					for(int i=1; i<=15; i++){
						map=new HashMap();
						
						for(int j=1; j<=7; j++){
							sb=new StringBuilder(manager.ezGetString("SELECT c.chi_name FROM Dtime d, Dtime_class dc, Seld s, Csno c WHERE " +
							"d.Oid=dc.Dtime_oid AND d.Oid=s.Dtime_oid AND c.cscode=d.cscode AND d.Sterm='"+manager.getSchoolTerm()+"' AND " +
							"s.student_no='"+Uid+"' AND dc.week='"+j+"' AND (dc.begin<="+i+" && dc.end>="+i+")"));							
							map.put("c"+j, sb+"&nbsp;");							
						}
						Table.add(map);
					}					
					request.setAttribute("Table", Table);
				}
				
				

				//核心能力指標
				if(sys_table.equals("CsCore")){
					//List CsCore=manager.ezGetBy("");
					request.setAttribute("CsCore", Uid);
				}				
				
				//////////////////////////教師				
				//教師基本資料
				if(sys_table.equals("empl")){
					//map=manager.ezGetMap("SELECT cname, bdate, Email, sname FROM empl WHERE idno='"+Uid+"'");
					request.setAttribute("empl", map);
				}
				///////////////////////////////很多表
				//Rcact
				if(sys_table.equals("Rcact")){					
					request.setAttribute("Rcact", manager.ezGetBy("SELeCT * FROM Rcact WHERE idno='"+Uid+"'"));
				}
				
				//Rcproj 產學
				if(sys_table.equals("Rcproj")){					
					request.setAttribute("Rcproj", manager.ezGetBy("SELeCT * FROM Rcproj WHERE idno='"+Uid+"'"));
				}
				

				//Rcproj 期刊
				if(sys_table.equals("Rcjour")){					
					request.setAttribute("Rcjour", manager.ezGetBy("SELeCT * FROM Rcjour WHERE idno='"+Uid+"'"));
				}
				
				//Rcconf研討會
				if(sys_table.equals("Rcconf")){					
					request.setAttribute("Rcconf", manager.ezGetBy("SELeCT * FROM Rcconf WHERE idno='"+Uid+"'"));
				}				

				//Rcbook出版書籍
				if(sys_table.equals("Rcbook")){	
					//System.out.println("SELeCT * FROM Rcbook WHERE idno='"+Uid+"'");
					request.setAttribute("Rcbook", manager.ezGetBy("SELeCT * FROM Rcbook WHERE idno='"+Uid+"'"));
				}
				
				//Rcpet專利
				if(sys_table.equals("Rcpet")){	
					//System.out.println("SELeCT * FROM Rcpet WHERE idno='"+Uid+"'");
					request.setAttribute("Rcpet", manager.ezGetBy("SELeCT * FROM Rcpet WHERE idno='"+Uid+"'"));
				}

				//Rcpet專利
				if(sys_table.equals("Rchono")){	
					//System.out.println("SELeCT * FROM Rcpet WHERE idno='"+Uid+"'");
					request.setAttribute("Rchono", manager.ezGetBy("SELeCT * FROM Rchono WHERE idno='"+Uid+"'"));
				}
				
				//基本資料
				if(sys_table.equals("empl")){	
					map=manager.ezGetMap("SELECT cname, bdate, Email, sname FROM empl WHERE idno='"+Uid+"'");						
					if(map==null){
						map=manager.ezGetMap("SELECT cname, bdate, Email, sname FROM dempl WHERE idno='"+Uid+"'");
					}
					request.setAttribute("empl", map);
				}
				
			}
			
			if(request.getAttribute("sys")==null)//
			request.setAttribute("sys", request.getParameter("sys"));
			
		}else{//給予第1筆				
			if(allTable.size()>0){				
				getFormat(request, ((Map)allTable.get(0)).get("Oid").toString(), Uid, true);			
				aForm.set("table_oid", ((Map)allTable.get(0)).get("Oid").toString());
				//基本資料
				//if(((Map)allTable.get(0)).get("sys_table").equals("empl")){	//若為教職員則查詢empl					
					if(category.equals("A")){
						//教師
						map=manager.ezGetMap("SELECT cname, bdate, Email, sname FROM empl WHERE idno='"+Uid+"'");						
						if(map==null){
							map=manager.ezGetMap("SELECT cname, bdate, Email, sname FROM dempl WHERE idno='"+Uid+"'");
						}
						request.setAttribute("empl", map);
					}else{
						//學生
						map=manager.ezGetMap("SELECT s.student_no, s.student_name, s.birthday, s.Email, c.ClassName, d.fname, " +
								"d.school_name FROM(stmd s LEFT OUTER JOIN Class c ON s.depart_class=c.ClassNo) LEFT OUTER JOIN dept d ON c.Dept=d.no " +
								"WHERE s.student_no='"+Uid+"'");
						if(map==null){
							map=manager.ezGetMap("SELECT s.student_no, s.student_name, s.birthday, s.Email, c.ClassName, d.fname, " +
									"d.school_name FROM(Gstmd s LEFT OUTER JOIN Class c ON s.depart_class=c.ClassNo) LEFT OUTER JOIN dept d ON c.Dept=d.no " +
									"WHERE s.student_no='"+Uid+"'");
						}
						request.setAttribute("stmd", map);
					}
				//}
				if(map==null){
					map=manager.ezGetMap("SELECT s.student_no, s.student_name, s.birthday, s.Email, c.ClassName, d.fname, " +
							"d.school_name FROM(Gstmd s LEFT OUTER JOIN Class c ON s.depart_class=c.ClassNo) LEFT OUTER JOIN dept d ON c.Dept=d.no " +
							"WHERE s.student_no='"+Uid+"'");
					request.setAttribute("stmd", map);
				}
				request.setAttribute("sys", 1);
			}			
		}
		
		setContentPage(request.getSession(false), "portfolio/EPortfolioManager.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 取某筆
	 * @param request
	 * @param table_oid
	 * @param Uid
	 * @param first
	 */
	private void getFormat(HttpServletRequest request, String table_oid, String Uid, boolean first){
		CourseManager manager = (CourseManager) getBean("courseManager");
		//取得點選的table格式
		
		request.setAttribute("format", manager.ezGetBy("SELECT ef.*, eft.name as alt " +
				"FROM Eps_field ef, Eps_format eft WHERE ef.type=eft.Oid AND table_oid='" +table_oid+"'"+
				"ORDER BY ef.sequence"));
		
		String table_name=manager.ezGetString("SELECT name FROM Eps_table WHERE Oid='"+table_oid+"'");
		
		//取已存資料筆數		
		List list=manager.ezGetBy("SELECT ef.type, ec.sequence as seq, et.name tableName FROM Eps_content ec, Eps_field ef, " +
				"Eps_format eft, Eps_table et WHERE eft.Oid=ef.type AND et.Oid=ec.table_oid AND " +
				"ec.field_oid=ef.Oid AND ec.table_oid='"+table_oid+"' AND ec.Uid='"+Uid+"'GROUP BY ec.sequence");
		
		if(list.size()>0){//該人員有儲存資料的清況
			for(int i=0; i<list.size(); i++){
				String sql="SELECT ec.Oid as ecOid, ec.content, eft.*, ef.name as fieldName, ef.type, ef.size FROM " +
				"Eps_content ec, Eps_format eft, Eps_field ef WHERE " +
				"ec.table_oid='"+table_oid+"' AND ef.Oid=ec.field_oid AND eft.Oid=ef.type AND " +
				"ec.Uid='"+Uid+"' AND ec.sequence='"+((Map)list.get(i)).get("seq")+"' ORDER BY ef.sequence";
				
				((Map)list.get(i)).put("detail", manager.ezGetBy(sql));
				
				if(!first){
					if(i==list.size()-1){
						request.setAttribute("next", Integer.parseInt(((Map)list.get(i)).get("seq").toString())+1);					
					}
				}else{//若是第1筆
					request.setAttribute("next", Integer.parseInt(((Map)list.get(i)).get("seq").toString())+1);					
				}			
			}
		}else{//該人員無儲存資料的清況			
			request.setAttribute("next", 1);	
		}
		
		
		request.setAttribute("table_name", table_name);
		request.setAttribute("myDate", list);
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
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		
		String Uid="";
		Uid=c.getMember().getAccount();
		if(Uid==null){
			Uid=c.getGstudent().getStudentNo();
		}
		String category="C";
		if(c.getMember().getPriority().equals("A")){
			category="A";
		}
		
		DynaActionForm aForm = (DynaActionForm) form;
		String next=aForm.getString("next");
		String table_oid=aForm.getString("table_oid");
		String content[]=aForm.getStrings("aContent");
		String field_oid[]=aForm.getStrings("field_oid");		
		
		if(table_oid!=null){
			if(table_oid.equals(""))
			table_oid=manager.ezGetString("SELECT Oid FROM Eps_table WHERE sys='1' AND category='"+category+"' LIMIT 1");
		}else{
			table_oid=manager.ezGetString("SELECT Oid FROM Eps_table WHERE sys='1' AND category='"+category+"' LIMIT 1");
		}
		
		for(int i=0; i<content.length; i++){
			if(!content[i].trim().equals("")){
				try{
					EpsContent ec=new EpsContent();
					ec.setContent(content[i]);
					ec.setFieldOid(Integer.parseInt(field_oid[i].toString()));
					ec.setSequence(Integer.parseInt(next));
					ec.setTableOid(Integer.parseInt(table_oid));
					ec.setUid(Uid);
					
					manager.updateObject(ec);
				}catch(Exception e){
					e.printStackTrace();
				}				
			}
		}
		if(manager.ezGetInt("SELECT et.sys FROM Eps_table et, Eps_content ec WHERE ec.table_oid=et.Oid AND et.Oid='"+table_oid+"'LIMIT 1")==0){
			request.setAttribute("sys", 0);
		}else{
			request.setAttribute("sys", 1);
		}
		return unspecified(mapping, form, request, response);
	}
	
	/**
	 * 更新
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		DynaActionForm aForm = (DynaActionForm) form;
		String contents[]=aForm.getStrings("contents");
		String Oid[]=aForm.getStrings("Oid");
		
		for(int i=0; i<contents.length; i++){			
			if(contents[i].trim().equals("")){
				//立刻刪除
				manager.executeSql("DELETE FROM Eps_content WHERE Oid='"+Oid[i]+"'");
			}else{
				manager.executeSql("UPDATE Eps_content SET content='"+contents[i]+"' WHERE Oid='"+Oid[i]+"'");
			}			
		}
		//若有帶到...
		String table_oid=request.getParameter("table_oid");
		
		if(table_oid!=null){
			if(table_oid.equals(""))
			table_oid=manager.ezGetString("SELECT Oid FROM Eps_table WHERE sys='1' LIMIT 1");
		}else{
			table_oid=manager.ezGetString("SELECT Oid FROM Eps_table WHERE sys='1' LIMIT 1");
		}
		if(manager.ezGetInt("SELECT et.sys FROM Eps_table et, Eps_content ec WHERE ec.table_oid=et.Oid AND et.Oid='"+table_oid+"'LIMIT 1")==0){
			request.setAttribute("sys", 0);
		}else{
			request.setAttribute("sys", 1);
		}
		
		
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Create", "create");
		map.put("Update", "update");
		return map;
	}

}
