package tw.edu.chit.struts.action.teacher;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import tw.edu.chit.dao.ScoreDAO;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.Regstime;
import tw.edu.chit.model.Seld;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class SportRatAction extends BaseLookupDispatchAction{

	public ActionForward unspecified(ActionMapping mapping,
			 					 ActionForm form,
			 					 HttpServletRequest request,
			 					 HttpServletResponse response)
			throws Exception {


		HttpSession session = request.getSession(false);
		//DynaActionForm sportForm = (DynaActionForm) form;

		
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		CourseManager manager = (CourseManager) getBean("courseManager");
		Member me = getUserCredential(request.getSession(false)).getMember();//取得登入資訊
		String term=manager.getNowBy("School_term"); //取得現在學期

		session.setAttribute("mystudents", manager.myStudents(manager.getSport(me.getIdno(), term)));
		/*
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","歡迎使用!"));
		saveMessages(request, msg);
		*/
		setContentPage(request.getSession(false), "teacher/SportRat.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 儲存成績資訊
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm sportForm = (DynaActionForm) form;

		ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		CourseManager manager = (CourseManager) getBean("courseManager");

		String[] seldOid=(String[])sportForm.get("seldOid");			//seldOid
		String[] score=(String[])sportForm.get("score");			//術科
		String[] score1=(String[])sportForm.get("score1");			//平時
		String[] score2=(String[])sportForm.get("score2");			//學科
		String[] score3=(String[])sportForm.get("score3");			//學期
		String[] dtimeOid=(String[])sportForm.get("dtimeOid");			//學期
		String[] studentNo=(String[])sportForm.get("studentNo");			//學期

		Seld seld;
		for(int i=0; i<seldOid.length; i++){
			ActionMessages errors = new ActionMessages();//20120628 Add yichen
			try{
                ScoreManager smanager = (ScoreManager)getBean("scoreManager"); //20120628 Add yichen
				seld=(Seld)manager.hqlGetBy("FROM Seld WHERE Oid="+seldOid[i]).get(0);
				//seld=new Seld();
				//seld.setOid(Integer.parseInt(seldOid[i]));
				seld.setStudentNo(studentNo[i]);
				seld.setDtimeOid(Integer.parseInt(dtimeOid[i]));
				if(!score1[i].trim().equals("")){
					seld.setScore1(Double.parseDouble(score1[i]));
				}

				if(!score2[i].trim().equals("")){
					seld.setScore2(Double.parseDouble(score2[i]));
				}
				if(!score3[i].trim().equals("")){
					seld.setScore3(Double.parseDouble(score3[i]));
				}
				if(!score[i].trim().equals("")){
					seld.setScore(Double.parseDouble(score[i]));
				}
				
				if(!score[i].trim().equals("")&& !score1[i].trim().equals("")&& !score2[i].trim().equals("")&& !score3[i].trim().equals("")){
					Dtime dtime=(Dtime)manager.hqlGetBy("FROM Dtime WHERE Oid="+dtimeOid[i]).get(0);
					List tmp=manager.hqlGetBy("FROM Regstime WHERE departClass='"+dtime.getDepartClass()+"' AND cscode='"+dtime.getCscode()+"' AND ind='"+3+"'");
					
                    /* 20120628 Mark BY yichen  ===========begin========================*/
					/*Regstime regstime;
					if(tmp.size()>0){
						regstime=(Regstime)tmp.get(0);
					}else{
						regstime =new Regstime();
					}
					
					regstime.setCscode(dtime.getCscode());
					regstime.setDepartClass(dtime.getDepartClass());
					regstime.setIdno(dtime.getTechid());					
					
					Calendar c=Calendar.getInstance();					
					c.setTime(new Date());
					c.set(Calendar.YEAR, c.get(Calendar.YEAR)-1911);
					
					SimpleDateFormat sf=new SimpleDateFormat("yyy/MM/dd HH:mm:ss");
					
					regstime.setTtime(sf.format(c.getTime()));
					regstime.setInd("3");
					manager.updateObject(regstime);*/
					/* 20120628 Mark BY yichen  ============end========================*/
                    /* 20120628 Add BY yichen  ===========begin========================*/					
					int dtimeoid = Integer.parseInt(dtimeOid[i]);
					String departClass = dtime.getDepartClass();
					String cscode = dtime.getCscode();
					String teacherId = dtime.getTechid();
					String scoretype = "3";
					
					Calendar c=Calendar.getInstance();					
					c.setTime(new Date());
					c.set(Calendar.YEAR, c.get(Calendar.YEAR)-1911);					
					SimpleDateFormat sf=new SimpleDateFormat("yyy/MM/dd HH:mm:ss");
					String nows =sf.format(c.getTime());
					
					errors = smanager.updateRegsTime(dtimeoid, departClass, cscode, teacherId, scoretype, nows);
					if (!errors.isEmpty()) {
						saveErrors(request, errors);
					}
					/* 20120628 Add BY yichen  ============end=========================*/	                                       
				}				
				
				manager.updateObject(seld);				
			}catch(Exception e){
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","遭預到一些問題"+studentNo[i]+"儲存失敗"));
			}
		}

		Member me = getUserCredential(request.getSession(false)).getMember();//取得登入資訊
		String term=manager.getNowBy("School_term"); //取得現在學期
		session.setAttribute("mystudents", manager.myStudents(manager.getSport(me.getIdno(), term)));

		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","儲存成功"));
		
		
		
		
		
		
		
		
		
		

		//記錄regstime
		List classes=manager.ezGetBy("SELECT d.Oid, c.cscode, c.eng_name, c.chi_name, cl.ClassName, de.EngName, cl.Grade, cl.ClassNo " +
				"FROM Dtime d, Csno c, Class cl, DepartmentInfo de WHERE de.Category=cl.DeptNo AND c.chi_name LIKE '%體育%' AND " +
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
				if(manager.ezGetInt("SELECT COUNT(*)FROM Regstime WHERE depart_class='"+((Map)classes.get(i)).get("ClassNo")+"' AND " +
					"cscode='"+((Map)classes.get(i)).get("cscode")+"'")==0){
					Regstime r=new Regstime();
					r.setCscode(((Map)classes.get(i)).get("cscode").toString());
					r.setDepartClass(((Map)classes.get(i)).get("ClassNo").toString());
					r.setIdno(me.getIdno());
					r.setTtime(time.substring(1, time.length()));
					r.setInd("3");
					manager.updateObject(r);					
				}
				
				
				
			}
		}
		
		
		
		
		
		
		
		
		
		
		saveMessages(request, msg);

		setContentPage(request.getSession(false), "teacher/SportRat.jsp");
		return mapping.findForward("Main");
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("Save", "save");
		return map;
	}

}