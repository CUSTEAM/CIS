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

import tw.edu.chit.model.Member;
import tw.edu.chit.model.Regstime;
import tw.edu.chit.model.Seld;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
/**
 * 英文成績管理
 * @author JOHN
 * TODO evgr_time
 */
public class NewEngRatAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, 
			   HttpServletRequest request, HttpServletResponse response)throws Exception {
			
			HttpSession session = request.getSession(false);
			CourseManager manager = (CourseManager) getBean("courseManager");
			
			Member me = getUserCredential(request.getSession(false)).getMember();
			
			List myClass=manager.ezGetBy("SELECT d.Oid, c.chi_name, c.eng_name, cl.ClassName, cl.Grade, cl.ClassNo " +
					"FROM Dtime d, Csno c, Class cl WHERE c.chi_name LIKE '%英%' AND " +
					"d.cscode=c.cscode AND cl.ClassNo=d.depart_class AND d.Sterm="+manager.getSchoolTerm()+" AND d.techid='"+me.getIdno()+"'");
			
			Map map;
			List engRats=new ArrayList();
			List students;
			for(int i=0; i<myClass.size(); i++){
				
				map=new HashMap();
				map.put("Oid", ((Map)myClass.get(i)).get("Oid"));
				map.put("chi_name", ((Map)myClass.get(i)).get("chi_name"));
				map.put("eng_name", ((Map)myClass.get(i)).get("eng_name"));
				map.put("ClassName", ((Map)myClass.get(i)).get("ClassName"));
				map.put("EngName", ((Map)myClass.get(i)).get("EngName"));
				map.put("Grade", ((Map)myClass.get(i)).get("Grade"));
				map.put("ClassNo", ((Map)myClass.get(i)).get("ClassNo"));
				
				students=manager.ezGetBy("SELECT st.student_name, s.* FROM Seld s, stmd st " +
				"WHERE s.student_no=st.student_no AND s.Dtime_oid='"+((Map)myClass.get(i)).get("Oid")+"' ORDER BY st.student_no");				
				map.put("students", students);
				engRats.add(map);
			}
			
			session.setAttribute("engRats", engRats);
			
			setContentPage(request.getSession(false), "teacher/NewEngRat.jsp");
			return mapping.findForward("Main");
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
		
		String score08[]=(String[])engForm.get("score08");
		String score09[]=(String[])engForm.get("score09");
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
				if(!score16[i].trim().equals("")){
					seld.setScore16(Double.parseDouble(score16[i]));
				}else{
					seld.setScore16(null);
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
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "儲存完成"));
			
		}
		
		//記錄regstime
		Member me = getUserCredential(request.getSession(false)).getMember();
		List classes=manager.ezGetBy("SELECT d.Oid, c.cscode, c.eng_name, c.chi_name, cl.ClassName, de.EngName, cl.Grade, cl.ClassNo " +
				"FROM Dtime d, Csno c, Class cl, DepartmentInfo de WHERE de.Category=cl.DeptNo AND c.chi_name LIKE '%英%' AND " +
				"d.cscode=c.cscode AND cl.ClassNo=d.depart_class AND d.Sterm="+manager.getSchoolTerm()+" AND d.techid='"+me.getIdno()+"'");
		int countstd;//班級人數
		int conutsrc;//已評分人數
		SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		//impleDateFormat sf1=new SimpleDateFormat("HH:mm:ss");
		//Date date=new Date();
		Calendar c=Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.YEAR, c.get(Calendar.YEAR)-1911);
		String time=sf.format(c.getTime());
		
		for(int i=0; i<classes.size(); i++){
			countstd=manager.ezGetInt("SELECT COUNT(*)FROM Seld WHERE Dtime_oid='"+((Map)classes.get(i)).get("Oid")+"'" );
			conutsrc=manager.ezGetInt("SELECT COUNT(*)FROM Seld s, Dtime d WHERE s.Dtime_oid=d.Oid AND d.depart_class='"+((Map)classes.get(i)).get("ClassNo")+"' AND " +
					"d.cscode='"+((Map)classes.get(i)).get("cscode")+"' AND s.score3 IS NOT NULL");
						
			if(countstd==conutsrc){
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "，"+((Map)classes.get(i)).get("ClassName")+((Map)classes.get(i)).get("chi_name")+"已全班完成"));
				System.out.println(time.substring(1, time.length()));
				if(manager.ezGetInt("SELECT COUNT(*)FROM Regstime WHERE depart_class='"+((Map)classes.get(i)).get("ClassNo")+"' AND " +
					"cscode='"+((Map)classes.get(i)).get("cscode")+"'")==0){
					Regstime r=new Regstime();
					r.setCscode(((Map)classes.get(i)).get("cscode").toString());
					r.setDepartClass(((Map)classes.get(i)).get("ClassNo").toString());
					r.setIdno(me.getIdno());
					//r.setTtime(manager.convertDate(sf.format(date)));
					r.setTtime(time.substring(1, time.length()));
					r.setInd("3");
					manager.updateObject(r);					
				}
			}
		}		
		saveMessages(request, msg);		
		return unspecified(mapping, form, request, response);
	}

	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Save", "save");
		return map;
	}

}
