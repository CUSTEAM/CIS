package tw.edu.chit.struts.action.personnel;

import java.util.HashMap;
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
import org.apache.struts.upload.FormFile;

import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Member;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class EditProfileAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {		
		
		HttpSession session = request.getSession(false);		
		CourseManager manager = (CourseManager) getBean("courseManager");
		//DynaActionForm cForm = (DynaActionForm) form;
		Member me = getUserCredential(request.getSession(false)).getMember();		
		session.setAttribute("myProfile", manager.ezGetMap("SELECT cname, ename, idno, Email, czip, caddr, pzip, paddr, CellPhone, telephone FROM empl WHERE idno='"+me.getIdno()+"'"));
		setContentPage(request.getSession(false), "personnel/EditProfile.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {		

		HttpSession session = request.getSession(false);		
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm cForm = (DynaActionForm) form;
		Member me = getUserCredential(request.getSession(false)).getMember();
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息		
		String ename=cForm.getString("ename");
		String Email=cForm.getString("Email");
		String czip=cForm.getString("czip");
		String caddr=cForm.getString("caddr");
		String pzip=cForm.getString("pzip");
		//String cname=cForm.getString("cname");
		String paddr=cForm.getString("paddr");
		String CellPhone=cForm.getString("CellPhone");
		String telephone=cForm.getString("telephone");		
		
		Empl e=(Empl) manager.hqlGetBy("FROM Empl WHERE idno='"+me.getIdno()+"'").get(0);
		
		e.setEname(ename);
		e.setEmail(Email);
		e.setCzip(czip);
		e.setCaddr(caddr);
		e.setPzip(pzip);
		e.setPaddr(paddr);
		e.setCellPhone(CellPhone);
		e.setTelephone(telephone);
		
		manager.updateObject(e);
		//TODO *****人事同步，若不再同步請移除以下區塊
		//boolean isHost=manager.syncTest();//驗證為正式伺服機
		if(manager.syncTest()){//驗證同步伺服器
			manager.syncEmpl(e, "U");
		}
		
		FormFile uplFile=(FormFile) cForm.get("image");
		boolean normal=false;
		if(!uplFile.getFileName().equals("")){
			
			normal=manager.uploadImage2APServer(uplFile, session.getServletContext().getRealPath("/UserFiles/").toString(), me.getIdno());
			
			if(!normal){
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","請選擇長寬比例為3:2, 寬度大於139像素的個人照片檔案, 以供識別證列印, "));
			}
			
			if(normal)
			if(!manager.uploadImage2FTPServer(session.getServletContext().getRealPath("/UserFiles/").toString(), me.getIdno()+".jpg")){
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","影像檔儲存失敗"));
			}
			if(!error.isEmpty()){
				saveErrors(request, error);
			}
		}		
		return unspecified(mapping, form, request, response);
	}
	
	public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {		
		//CourseManager manager = (CourseManager) getBean("courseManager");
		//DynaActionForm cForm = (DynaActionForm) form;
		//Member me = getUserCredential(request.getSession(false)).getMember();		
		//request.setAttribute("myProfile", manager.ezGetMap("SELECT cname, ename, idno, Email, czip, caddr, pzip, paddr, CellPhone, telephone FROM empl WHERE idno='"+me.getIdno()+"'"));
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Save", "save");
		map.put("Cancel", "cancel");
		return map;
	}

}
