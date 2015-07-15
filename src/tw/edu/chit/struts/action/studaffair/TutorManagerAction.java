package tw.edu.chit.struts.action.studaffair;

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
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.model.ClassInCharge;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class TutorManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		
		session.setAttribute("allDept", manager.ezGetBy("SELECT idno as deptNo, name as deptName FROM code5 WHERE category='Dept'"));
		setContentPage(request.getSession(false), "studaffair/TutorManager.jsp");
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
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		DynaActionForm tuForm = (DynaActionForm) form;
		String allDept=tuForm.getString("allDept");
		/*
		session.setAttribute("tutors", manager.ezGetBy("SELECT cl.Oid, c.ClassNo, c.ClassName, cl.EmpOid, e.idno, e.cname FROM stmd s, " +
				"((Class c LEFT OUTER JOIN ClassInCharge cl ON c.ClassNo=cl.ClassNo AND cl.ModuleOids = '|86|') " +
				"LEFT OUTER JOIN empl e ON e.Oid=cl.EmpOid) WHERE s.depart_class=c.ClassNo AND " +
				"c.DeptNo='"+allDept+"' GROUP BY s.depart_class ORDER BY c.DeptNo, c.ClassNo"));
		*/
		//改寫上面的SQL的語法
		session.setAttribute("tutors", manager.ezGetBy("SELECT c.Oid,c.ClassNo,c.ClassName,e.Oid as EmpOid,e.idno,e.cname,c.tutor FROM stmd s , " +
		        "(Class c LEFT OUTER JOIN empl e ON c.tutor=e.idno) WHERE s.depart_class=c.ClassNo and c.DeptNO = '"+allDept+"' GROUP BY s.depart_class"));
		
		return mapping.findForward("Main");
	}
	
	
	
	/**
	 * 完成
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
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		MemberManager mm = (MemberManager) getBean("memberManager");
		MemberDAO dao = (MemberDAO) getBean("memberDAO");
		
		DynaActionForm tuForm = (DynaActionForm) form;
		
		String Oid[]=tuForm.getStrings("Oid");
		String idno[]=tuForm.getStrings("idno");
		String ClassNo[]=tuForm.getStrings("ClassNo");
		String ClassName[]=tuForm.getStrings("ClassName");
		
		ClassInCharge c;
		int empOid = 0;
		//TODO 導師永遠為|86|？
		for(int i=0; i<Oid.length; i++){
			try{
				//批次更新Class表 - tutor的導師
				    //System.out.println("UPDATE Class SET tutor='"+idno[i]+"' where ClassNo='"+ClassNo[i]+"'");
					manager.executeSql("UPDATE Class SET tutor='"+idno[i]+"' where Oid='"+Oid[i]+"'");
					String cicOid = manager.ezGetString("SELECT Oid FROM ClassInCharge WHERE ClassNo='"+ClassNo[i]+"' AND ClassOid='"+Oid[i]+"' AND ModuleOids='|86|' limit 1"); //取ClassInCharge的Oid
					String emplOid = manager.ezGetString("SELECT Oid FROM empl WHERE idno='"+idno[i]+"'"); //取empl的Oid
					//System.out.println("Class-Oid:"+Oid[i]+"empl-Oid:"+emplOid);
					if(!Oid[i].equals("")&&!emplOid.equals("")){ //判斷Class-Oid、empl-Oid是否存在
						if(cicOid.equals("")){ //判斷ClassInCharge是否存在
							//System.out.println("INSERT INTO ClassInCharge (ClassNo, EmpOid, ClassOid, ModuleOids) VALUES ('"+ClassNo[i]+"', '"+emplOid+"', '"+Oid[i]+"', '|86|')");
							manager.executeSql("INSERT INTO ClassInCharge (ClassNo, EmpOid, ClassOid, ModuleOids) VALUES ('"+ClassNo[i]+"', '"+emplOid+"', '"+Oid[i]+"', '|86|')");
						}else{
							//System.out.println("UPDATE ClassInCharge SET ClassNo='"+ClassNo[i]+"',EmpOid='"+emplOid+"',ClassOid='"+Oid[i]+"' WHERE Oid='"+cicOid+"' AND ModuleOids='|86|'");
							manager.executeSql("UPDATE ClassInCharge SET ClassNo='"+ClassNo[i]+"',EmpOid='"+emplOid+"',ClassOid='"+Oid[i]+"' WHERE Oid='"+cicOid+"' AND ModuleOids='|86|'");
						}
					}
				/*ClassInCharge不在使用
				if(!Oid[i].trim().equals("")){//若原先有的部份
					c=(ClassInCharge) manager.hqlGetBy("FROM ClassInCharge WHERE Oid='"+Oid[i]+"'").get(0);		
					//System.out.println(idno[i]);
					if(!idno[i].trim().equals("")){//若有設上教職員
						c.setClassNo(ClassNo[i]);
						c.setClassOid(manager.ezGetInt("SELECT Oid FROM Class WHERE ClassNo='"+ClassNo[i]+"'"));
						c.setEmpOid(manager.ezGetInt("SELECT Oid FROM empl WHERE idno='"+idno[i]+"'"));
						c.setModuleOids("|86|");
						System.out.println(ClassName[i]+"="+idno[i]);
						manager.updateObject(c);
						
					}else{//若沒設上教職員						
						manager.executeSql("DELETE FROM ClassInCharge WHERE Oid='"+Oid[i]+"'");
						//沒有擔任導師
						if(manager.hqlGetBy("FROM ClassInCharge WHERE moduleOids like '%|86|%' And empOid="+c.getEmpOid()).isEmpty()){
							mm.removeEmployeeFromGroup(c.getEmpOid(),  "T2");
						}

					}
					
				}else{//若原先沒有的部份
					c=new ClassInCharge();
					
					if(!idno[i].trim().equals("")){//若有設上教職員
						c.setClassNo(ClassNo[i]);
						c.setClassOid(manager.ezGetInt("SELECT Oid FROM Class WHERE ClassNo='"+ClassNo[i]+"'"));
						c.setEmpOid(manager.ezGetInt("SELECT Oid FROM empl WHERE idno='"+idno[i]+"'"));
						c.setModuleOids("|86|");
						
						manager.updateObject(c);
						
						// AuthorityOnTutor=86=導師
						empOid = mm.findEmplByIdno(idno[i]).getOid();
						List<Empl> empls = mm.findEmplByGroup(UserCredential.AuthorityOnTutor);
						boolean bFlag = false;
						for (Empl empl : empls) {
							if (empOid == empl.getOid().intValue()) {
								bFlag = true;
								break;
							}
						}
						if (!bFlag && !dao.findClassesInChargesByEmployeeModuleOids(empOid,
										UserCredential.AuthorityOnTutor).isEmpty()) // 查不到任何導師權限時
							mm.addEmployeeToGroup(empOid, "T2"); // T2=導師

					}
				}			
				*/
				

			}catch(Exception e){
				e.printStackTrace();
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", ClassName[i]+"設定時出現問題"));
			}
					
			if(!error.isEmpty()){
				saveErrors(request, error);
			}
		}		
		return query(mapping, form, request, response);
	}
	
	
	

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("OK", "ok");
		return map;
	}

}
