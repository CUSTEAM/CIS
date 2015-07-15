package tw.edu.chit.struts.action.teacher;

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

import tw.edu.chit.model.Member;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.SummerManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class SummerRatAction extends BaseLookupDispatchAction{

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		//DynaActionForm sportForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		Member me = getUserCredential(request.getSession(false)).getMember();//取得登入資訊
		
		List SdtimeList=manager.ezGetBy("SELECT seqno, depart_class, cscode FROM Sdtime WHERE techid='"+me.getIdno()+"'");
		session.setAttribute("summerStudents", manager.mySunStudents(SdtimeList));
		
		
		setContentPage(request.getSession(false), "teacher/SummerRat.jsp");
		return mapping.findForward("Main");

	}
	
	/**
	 * 儲存
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm sportForm = (DynaActionForm) form;

		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		SummerManager summerManager=(SummerManager) getBean("summerManager");
		String[] studentNo=(String[])sportForm.get("studentNo");	//學號
		String[] departClass=(String[])sportForm.get("departClass");//課代
		String[] cscode=(String[])sportForm.get("cscode");			//班代
		
		String[] sc1=(String[])sportForm.get("sc1");			//成績
		String[] sc2=(String[])sportForm.get("sc2");			//成績
		String[] sc3=(String[])sportForm.get("sc3");			//成績
		String[] sc4=(String[])sportForm.get("sc4");			//成績
		String[] medScore=(String[])sportForm.get("medScore");			//成績
		String[] endScore=(String[])sportForm.get("endScore");			//成績
		String[] freeScore=(String[])sportForm.get("freeScore");			//成績
		String[] score=(String[])sportForm.get("score");			//成績
		
		
		for(int i=0; i<studentNo.length; i++){
			
			//檢查每個欄位是否空值
			if(sc1[i].equals("")){
				sc1[i]=null;
			}
			if(sc2[i].equals("")){
				sc2[i]=null;
			}
			if(sc3[i].equals("")){
				sc3[i]=null;
			}
			if(sc4[i].equals("")){
				sc4[i]=null;
			}
			if(medScore[i].equals("")){
				medScore[i]=null;
			}
			if(endScore[i].equals("")){
				endScore[i]=null;
			}
			if(freeScore[i].equals("")){
				freeScore[i]=null;
			}
			
			if(!score[i].trim().equals("")){
				int x=(int) Float.parseFloat(score[i]);
				/*
				summerManager.saveOrUpdate("UPDATE Sseld SET score='"+x+"', sc1='"+sc1[i]+"', sc2='"+sc2[i]+"', sc3='"+sc3[i]+"', " +
						"sc4='"+sc4[i]+"', med_score='"+medScore[i]+"', end_score='"+endScore[i]+"', free_score='"+freeScore[i]+"' " +
						"WHERE csdepart_class='"+departClass[i]+"' AND cscode='"+cscode[i]+"' AND student_no='"+studentNo[i]+"'");
				*/
				summerManager.saveOrUpdate("UPDATE Sseld SET score='"+x+"', sc1="+sc1[i]+", sc2="+sc2[i]+", sc3="+sc3[i]+", " +
						"sc4="+sc4[i]+", med_score="+medScore[i]+", end_score="+endScore[i]+", free_score="+freeScore[i]+" " +
						"WHERE csdepart_class='"+departClass[i]+"' AND cscode='"+cscode[i]+"' AND student_no='"+studentNo[i]+"'");
			}else{
				//summerManager.saveOrUpdate("UPDATE Sseld SET score=null " +
						//"WHERE csdepart_class='"+departClass[i]+"' AND cscode='"+cscode[i]+"' AND student_no='"+studentNo[i]+"'");
				
				summerManager.saveOrUpdate("UPDATE Sseld SET score=null, sc1="+sc1[i]+", sc2="+sc2[i]+", sc3="+sc3[i]+", " +
						"sc4="+sc4[i]+", med_score="+medScore[i]+", end_score="+endScore[i]+", free_score="+freeScore[i]+" " +
						"WHERE csdepart_class='"+departClass[i]+"' AND cscode='"+cscode[i]+"' AND student_no='"+studentNo[i]+"'");
			}
		}
		
		
		Member me = getUserCredential(request.getSession(false)).getMember();//取得登入資訊
		List SdtimeList=manager.ezGetBy("SELECT seqno, depart_class, cscode FROM Sdtime WHERE techid='"+me.getIdno()+"'");
		session.setAttribute("summerStudents", manager.mySunStudents(SdtimeList));


		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","儲存完成!"));
		saveMessages(request, msg);
		
		setContentPage(request.getSession(false), "teacher/SummerRat.jsp");
		return mapping.findForward("Main");
	}
	
	
	
	
	
	
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Save", "save");
		return map;
	}

}
