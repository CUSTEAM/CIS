package tw.edu.chit.struts.action.teacher.qform;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.FileDataSource;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletContext;
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
import org.apache.struts.upload.FormFile;

import tw.edu.chit.model.AqAnser;
import tw.edu.chit.model.OnlineService4reg;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class LoginQform extends BaseLookupDispatchAction{
	
	
	
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential c = (UserCredential) session.getAttribute("Credential");	
		
		if(request.getParameter("doid")!=null){			
			manager.executeSql("DELETE FROM AQ_anser WHERE Oid="+request.getParameter("doid"));			
		}		
		
		if(request.getParameter("uid")!=null){
			
			AqAnser aq=new AqAnser();
			aq.setIdno(c.getMember().getIdno());
			aq.setUid(request.getParameter("uid"));
			
			if(request.getParameter("id2")!=null){
				//外系
				aq.setUid("Q");
				aq.setUid2(request.getParameter("id2"));
				request.setAttribute("qs", manager.ezGetBy("SELECT * FROM AQ_question WHERE uid='Q'"));
				
			}else{
				//行政單位
				//System.out.println("SELECT * FROM AQ_question WHERE uid='"+request.getParameter("uid")+"'");
				request.setAttribute("qs", manager.ezGetBy("SELECT * FROM AQ_question WHERE uid='"+request.getParameter("uid")+"'"));				
				
			}
			
			try{
				manager.updateObject(aq);
				request.setAttribute("ans_oid", aq.getOid());
				request.setAttribute("qform", manager.ezGetBy("SELECT * FROM AQ_anser WHERE Oid='"+aq.getOid()+"'"));
			}catch(Exception e){
				e.printStackTrace();
				ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "問卷重複作答，有可能您點選了「重新整理」或F5"));
				saveErrors(request, error);
				return mapping.findForward("Main");
			}
			//request.setAttribute("ans_oid", aq.getOid());
			//request.setAttribute("qform", manager.ezGetBy("SELECT * FROM AQ_anser WHERE Oid='"+aq.getOid()+"'"));
			
			
			ActionMessages msg = new ActionMessages();
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","問卷已準備，請作答"));
			saveMessages(request, msg);
			//return unspecified(mapping, form, request, response);
			
		}		
		
		
		//本系免重做
		List qlist=manager.ezGetBy("SELECT * FROM AQ_anser WHERE uid='P' AND idno='"+c.getMember().getIdno()+"'");
		if(qlist.size()<1){
			request.setAttribute("mydept", manager.ezGetBy("SELECT * FROM AQ_unit WHERE id='P'"));
		}

		//外系免重做
		qlist.addAll(manager.ezGetBy("SELECT * FROM AQ_anser WHERE uid='Q' AND idno='"+c.getMember().getIdno()+"'"));
		
		
		//已作答問卷
		qlist.addAll(manager.ezGetBy("SELECT * FROM AQ_anser WHERE uid NOT IN('P', 'Q') AND idno='"+c.getMember().getIdno()+"'"));
		for(int i=0; i<qlist.size(); i++){
			//系用
			if(((Map)qlist.get(i)).get("uid2")!=null){
				((Map)qlist.get(i)).put("name", manager.ezGetString("SELECT name FROM AQ_unit WHERE id2='"+((Map)qlist.get(i)).get("uid2")+"' LIMIT 1"));
			}else{
				((Map)qlist.get(i)).put("name", manager.ezGetString("SELECT name FROM AQ_unit WHERE id='"+((Map)qlist.get(i)).get("uid")+"' AND id2 IS NULL LIMIT 1"));
			}
			
		}
		
		request.setAttribute("qlist", qlist);	
		
		
		StringBuilder sb=new StringBuilder("SELECT * FROM AQ_unit WHERE id NOT IN('P', 'Q', ");
		StringBuilder sb1=new StringBuilder("SELECT * FROM AQ_unit WHERE id='P' AND id2 NOT IN(");
		for(int i=0; i<qlist.size(); i++){			
			sb.append("'"+((Map)qlist.get(i)).get("uid")+"', ");
			sb1.append("'"+((Map)qlist.get(i)).get("uid2")+"', ");
		}		
		sb.delete(sb.length()-2, sb.length());
		sb.append(")");
		
		
		sb1.delete(sb1.length()-2, sb1.length());
		sb1.append(")");
		//System.out.println(sb);
		request.setAttribute("allunit", manager.ezGetBy(sb.toString()));
		try{
			request.setAttribute("other", manager.ezGetBy(sb1.toString()));
		}catch(Exception e){
			request.setAttribute("other", manager.ezGetBy("SELECT * FROM AQ_unit WHERE id='Q'"));
		}
				
		//setContentPage(request.getSession(false), "teacher/AQ_entrant.jsp");
		return mapping.findForward("Main");
	}	
	
	//本系
	public ActionForward Continue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		DynaActionForm sForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential c = (UserCredential) session.getAttribute("Credential");		
		
		AqAnser aq=new AqAnser();
		aq.setIdno(c.getMember().getIdno());
		aq.setUid("P");
		aq.setUid2(sForm.getString("dept"));
		
		try{
			manager.updateObject(aq);
			request.setAttribute("ans_oid", aq.getOid());
			request.setAttribute("qform", manager.ezGetBy("SELECT * FROM AQ_anser WHERE Oid='"+aq.getOid()+"'"));
		}catch(Exception e){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "問卷重複作答，有可能您點選了「重新整理」或F5"));
			saveErrors(request, error);
			return unspecified(mapping, form, request, response);
		}
		
		
		request.setAttribute("qs", manager.ezGetBy("SELECT * FROM AQ_question WHERE uid='P'"));
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","問卷已準備，請作答"));
		saveMessages(request, msg);
			
		return mapping.findForward("Main");
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(false);
		
		DynaActionForm sForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		
		
		String ans_oid=sForm.getString("ans_oid");
		String ans=sForm.getString("ans");
		String dept=sForm.getString("dept");
		String category=sForm.getString("category");
		String years=sForm.getString("years");
		
		session.setAttribute("years", years);
		
		if(ans.trim().equals("")){
			manager.executeSql("DELETE FROM AQ_anser WHERE Oid="+ans_oid);
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "您填寫的是空白問卷"));
			saveErrors(request, error);
			return unspecified(mapping, form, request, response);
			
		}
		
		AqAnser aq=(AqAnser) manager.hqlGetBy("FROM AqAnser WHERE Oid="+ans_oid).get(0);
		aq.setAnser(ans);
		aq.setUnit(dept);
		aq.setCategory(category);
		aq.setYears(years);		
		manager.updateObject(aq);		
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","已完成"));
		saveMessages(request, msg);
		if(manager.ezGetInt("SELECT COUNT(*)FROM AQ_anser WHERE idno='"+c.getMember().getIdno()+"'")>=3){
			session.setAttribute("tqst", false);//解除鎖定
		}
		return unspecified(mapping, form, request, response);
	}
	
	public ActionForward skip(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(false);
		session.setAttribute("tqst", false);//解除鎖定
		
		return unspecified(mapping, form, request, response);
	}
	
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Continue", "Continue");
		map.put("Save", "save");
		map.put("Save", "save");
		map.put("Skip", "skip");
		
		return map;
	}
}
