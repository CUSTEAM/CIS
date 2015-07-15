package tw.edu.chit.struts.action.course;

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

import tw.edu.chit.model.CsCore;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Global;

/**
 * 核心課程管理
 * @author JOHN
 *
 */
public class CsCoreManagerAction extends BaseLookupDispatchAction {
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(false);
		session.setAttribute("DeptList", Global.DeptList);		
		
		if(request.getParameter("Oid")!=null){
			request.setAttribute("core", getCore(request.getParameter("Oid")));		
		}
		
		setContentPage(request.getSession(false), "course/CsCoreManager.jsp");
		return mapping.findForward("Main");
	}	
	
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//HttpSession session = request.getSession(false);
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		DynaActionForm cForm = (DynaActionForm) form;
		String deptNo=cForm.getString("deptNo");
		String key_word=cForm.getString("key_word");
		
		if(deptNo.equals("")||key_word.trim().equals("")){
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "科系和關鍵字為建立核心課程必要欄位"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		manager.executeSql("INSERT INTO CsCore (deptNo, key_word)VALUES('"+deptNo+"', '"+key_word+"');");
		
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "建立完成, 繼續完成各項欄位"));
		saveMessages(request, msg);
		
		request.setAttribute("core", manager.ezGetMap("SELECT * FROM Cscore ORDER BY Oid DESC LIMIT 1"));		
		return query(mapping, form, request, response);

	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		DynaActionForm cForm = (DynaActionForm) form;
		String Oid=cForm.getString("Oid");
		ActionMessages error = new ActionMessages();
		
		int sum=0;
		try{
			sum=Integer.parseInt(cForm.getString("sum"));
		}catch(Exception e){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "能力值必須是數值"));
			saveErrors(request, error);
			request.setAttribute("core", getCore(Oid));
			return mapping.findForward("Main");
		}
		
		
		if(sum!=100){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "新的能力值必須為100"));
			saveErrors(request, error);
			request.setAttribute("core", getCore(Oid));
			return mapping.findForward("Main");
		}
		
		String note1=cForm.getString("note1");
		String note2=cForm.getString("note2");
		String deptNo=cForm.getString("deptNo");
		String key_word=cForm.getString("key_word");
		String entrance=cForm.getString("entrance");
		String s1=cForm.getString("s1");
		String s2=cForm.getString("s2");
		String s3=cForm.getString("s3");
		String s4=cForm.getString("s4");
		String s5=cForm.getString("s5");
		String s6=cForm.getString("s6");
		String s7=cForm.getString("s7");
		String s8=cForm.getString("s8");
		String s9=cForm.getString("s9");
		String sa=cForm.getString("sa");
		String sb=cForm.getString("sb");
		
		
		if(deptNo.equals("")||key_word.trim().equals("")){			
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "科系和關鍵字為建立核心課程必要欄位"));
		}
		
		UserCredential c = (UserCredential) session.getAttribute("Credential");	
		
		try{
			CsCore cs=(CsCore) manager.hqlGetBy("FROM CsCore WHERE Oid='"+Oid+"'").get(0);
			cs.setDeptNo(deptNo);
			cs.setEditime(new Date());
			cs.setEditor(c.getMember().getIdno());
			if(!entrance.trim().equals("")){
				cs.setEntrance(Integer.parseInt(entrance));
			}
			cs.setKeyWord(key_word);
			cs.setNote1(note1);
			cs.setNote2(note2);
			
			if(!s1.trim().equals(""))
			cs.setS1(Integer.parseInt(s1));
			
			if(!s2.trim().equals(""))
			cs.setS2(Integer.parseInt(s2));
			
			if(!s3.trim().equals(""))
			cs.setS3(Integer.parseInt(s3));
			
			if(!s4.trim().equals(""))
			cs.setS4(Integer.parseInt(s4));
			
			if(!s5.trim().equals(""))
			cs.setS5(Integer.parseInt(s5));
			
			if(!s6.trim().equals(""))
			cs.setS6(Integer.parseInt(s6));
			
			if(!s7.trim().equals(""))
			cs.setS7(Integer.parseInt(s7));
			
			if(!s8.trim().equals(""))
			cs.setS8(Integer.parseInt(s8));
			
			if(!s9.trim().equals(""))
			cs.setS9(Integer.parseInt(s9));
			
			if(!sa.trim().equals(""))
			cs.setSa(Integer.parseInt(sa));
			
			if(!sb.trim().equals(""))
			cs.setSb(Integer.parseInt(sb));
			
			manager.updateObject(cs);
		}catch(Exception e){
			//error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "欄位出現錯誤"));
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "欄位出現錯誤"));
						
		}
		
		if(!error.isEmpty()){
			saveErrors(request, error);
			request.setAttribute("core", getCore(Oid));
			return mapping.findForward("Main");
		}

		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "修改完成"));
		saveMessages(request, msg);
		request.setAttribute("core", getCore(Oid));
		return mapping.findForward("Main");

	}
	
	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//HttpSession session = request.getSession(false);

		DynaActionForm cForm = (DynaActionForm) form;
		//cForm.set("deptNo", "");
		//cForm.set("key_word", "");
		String deptNo=cForm.getString("deptNo");
		if(!deptNo.trim().equals("")){
			request.setAttribute("cores", getCores(deptNo, ""));
		}
		
		return mapping.findForward("Main");

	}
	
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm cForm = (DynaActionForm) form;
		String Oid=cForm.getString("Oid");
		
		//System.out.println("DELETE FROM CsCore WHERE Oid='"+Oid+"'");
		manager.executeSql("DELETE FROM CsCore WHERE Oid='"+Oid+"'");
		
		//cForm.set("deptNo", "");
		//cForm.set("key_word", "");
		
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "刪除完成，重新取回列表"));
		saveMessages(request, msg);
		return back(mapping, form, request, response);

	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//CourseManager manager = (CourseManager) getBean("courseManager");
		
		DynaActionForm cForm = (DynaActionForm) form;
		String deptNo=cForm.getString("deptNo");
		String key_word=cForm.getString("key_word");
		
		List cores=getCores(deptNo, key_word);
		
		if(cores.size()==1){
			request.setAttribute("core", getCore(((Map)cores.get(0)).get("Oid").toString()));
		}else{
			request.setAttribute("cores", cores);
		}
		
		return mapping.findForward("Main");

	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Create", "create");		
		map.put("Delete", "delete");
		map.put("Query", "query");
		map.put("Save", "save");
		map.put("Back", "back");
		return map;
	}
	
	private Map getCore(String Oid){
		CourseManager manager = (CourseManager) getBean("courseManager");		
		Map map=manager.ezGetMap("SELECT * FROM CsCore WHERE Oid='"+Oid+"'");
		
		map.put("sum", Integer.parseInt(map.get("s1").toString())+
				Integer.parseInt(map.get("s2").toString())+
				Integer.parseInt(map.get("s3").toString())+
				Integer.parseInt(map.get("s4").toString())+
				Integer.parseInt(map.get("s5").toString())+
				Integer.parseInt(map.get("s6").toString())+
				Integer.parseInt(map.get("s7").toString())+
				Integer.parseInt(map.get("s8").toString())+
				Integer.parseInt(map.get("s9").toString())+
				Integer.parseInt(map.get("sa").toString())+
				Integer.parseInt(map.get("sb").toString()));		
		
		return map;
	}
	
	private List getCores(String deptNo, String key_word){
		CourseManager manager = (CourseManager) getBean("courseManager");
		List list=manager.ezGetBy("SELECT * FROM CsCore WHERE key_word LIKE '%"+key_word+"%' AND deptNo LIKE '"+deptNo+"%'");
		String empl;
		for(int i=0; i<list.size(); i++){			
			((Map)list.get(i)).put("dname", manager.ezGetString("SELECT name FROM code5 WHERE category='Dept' AND idno='"+((Map)list.get(i)).get("deptNo")+"'"));
			empl=manager.ezGetString("SELECT cname FROM empl WHERE idno='"+((Map)list.get(i)).get("editor")+"'");
			((Map)list.get(i)).put("empl", empl);
			if(!empl.equals("")){
				((Map)list.get(i)).put("status", "完整");
			}else{
				((Map)list.get(i)).put("status", "");
			}
		}		
		return list;
	}
}
