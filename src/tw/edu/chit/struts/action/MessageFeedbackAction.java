package tw.edu.chit.struts.action;

import java.text.SimpleDateFormat;
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

import tw.edu.chit.model.Message;
import tw.edu.chit.model.MessageFeedback;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;

public class MessageFeedbackAction extends BaseLookupDispatchAction{
	
	/**
	 * 新增回應
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward feedback(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
			
			//ActionMessages msg = new ActionMessages();
			//ActionMessages error = new ActionMessages();
			DynaActionForm aForm = (DynaActionForm)form;
			HttpSession session = request.getSession(false);
			
			String Oid[]=(String[])aForm.get("Oid");
			String message[]=(String[])aForm.get("message");
			CourseManager manager = (CourseManager) getBean("courseManager");
			AdminManager aManager= (AdminManager) getBean("adminManager");
			String username= ((UserCredential)session.getAttribute("Credential")).getMember().getName();
			
			
			for(int i=0; i<Oid.length; i++){
				
				if(!Oid[i].trim().equals("")&& !message[i].trim().equals("")){
					try{
						
						MessageFeedback mf=new MessageFeedback();
						mf.setContent(message[i]);
						mf.setMessageOid(Integer.parseInt(Oid[i]));
						mf.setSender(username);
						//System.out.println(message[i]);
						manager.updateObject(mf);
						
					}catch(Exception e){
						e.printStackTrace();
						//失敗則用sql
						//System.out.println("INSERT INTO Message_feedback(Sender, Content, MessageOid)VALUES('"+username+"', '"+message[i]+"', '"+Oid[i]+"')");
					}
				}
				
			}
			//List messageList = aManager.findMessagesByCategory("Course");
			//request.setAttribute("MessageList", messageList);
			//System.out.println(mapping.getForward());
			setContentPage(request.getSession(false), "BulletinBoard.jsp");
			
			List messageList;
			try{
				messageList=aManager.findMessagesByCategory( session.getAttribute("directory").toString());
			}catch(Exception e){
				//TODO 錯誤的指引頁要處理
				messageList=aManager.findMessagesByCategory("");
			}
			request.setAttribute("MessageList", messageList);
			return mapping.findForward("Main");
		}

	/**
	 * 新增話題
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward post(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		ActionMessages msg = new ActionMessages();
		ActionMessages error = new ActionMessages();
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		AdminManager aManager= (AdminManager) getBean("adminManager");
		CourseManager manager = (CourseManager) getBean("courseManager");
		Date date=new Date();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		
		String content=aForm.getString("content");		
		String StartDate=aForm.getString("StartDate");		
		String DueDate=aForm.getString("DueDate");		
		
		
		String Category=aForm.getString("Category");
		String title=aForm.getString("title");
		String receiver=aForm.getString("receiver");
		String unit=aForm.getString("unit");
		if(receiver.trim().equals("")){
			receiver="未指定";
		}
		
		if(content.trim().length()<5|| StartDate.trim().equals("") || DueDate.trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "請完整輸入開始、結束日期，內容請再敘述詳細一點(5)，謝謝！"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		if(sf.parse(manager.convertDate(DueDate)).before(date)){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "時間順序矛盾!"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		if(sf.parse(manager.convertDate(DueDate)).before(sf.parse(manager.convertDate(StartDate)))){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "時間順序矛盾!"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		try{
			
			date=sf.parse(manager.convertDate(StartDate));		
			Message message=new Message();		
			//message.setCategory(session.getAttribute("directory").toString());
			message.setContent(content);
			message.setSender(((UserCredential)session.getAttribute("Credential")).getMember().getName());		
			message.setStartDate(date);		
			date=sf.parse(manager.convertDate(DueDate));
			message.setDueDate(date);
			
			message.setCategory(Category);
			message.setTitle(title);
			message.setReceiver(receiver);
			message.setUnit(unit);
			message.setOpen("1");
			
			manager.updateObject(message);
			
		}catch (Exception e){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "儲存時發生錯誤!"));
			saveErrors(request, error);
			List messageList;
			try{
				messageList=aManager.findMessagesByCategory( session.getAttribute("directory").toString());
			}catch(Exception e1){
				//TODO 錯誤的指引頁要處理
				messageList=aManager.findMessagesByCategory("");
			}
			request.setAttribute("MessageList", messageList);
			return mapping.findForward("Main");
		}		
		
		List messageList;
		try{
			messageList=aManager.findMessagesByCategory( session.getAttribute("directory").toString());
		}catch(Exception e){
			//TODO 錯誤的指引頁要處理
			messageList=aManager.findMessagesByCategory("");
		}
		
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "訊息已發佈"));
		saveMessages(request, msg);
		
		request.setAttribute("MessageList", messageList);
		return mapping.findForward("Main");
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Feedback", "feedback");
		map.put("Post", "post");
		return map;
	}

}
