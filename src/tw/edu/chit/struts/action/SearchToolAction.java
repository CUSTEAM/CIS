package tw.edu.chit.struts.action;

import java.text.DateFormat;
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

import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.Message;
import tw.edu.chit.model.WwPass;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.util.Toolket;

public class SearchToolAction extends BaseLookupDispatchAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

			HttpSession session = request.getSession(false);
			CourseManager manager = (CourseManager) getBean("courseManager");
			Member me = getUserCredential(request.getSession(false)).getMember();
			MemberDAO dao = (MemberDAO)getBean("memberDAO");
			//List<Module> modules = dao.findModulesByMember(me.getOid(), 1);

			request.setAttribute("SearchToolForm", form);
			session.setAttribute("piggyName", me.getName());
			session.setAttribute("pigId", me.getAccount());
			session.setAttribute("pigPwd", me.getPassword());
			session.setAttribute("piggysMoudles", manager.ezGetBy("SELECT DISTINCT name, label FROM Module WHERE ParentOid=0"));
			//session.setAttribute("piggysMoudles", modules);
			session.setAttribute("piggyMsgList", manager.getMessageBy(me.getName()));

			return mapping.findForward("continue");
		}

	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Query", "query");
		map.put("Delete", "delete");
		map.put("Create", "create");
		map.put("ModifyRecord", "modifyRecord");
		return map;
	}

	/**
	 * 修改密碼
	 */
	public ActionForward modifyRecord(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm searchForm = (DynaActionForm)form;
		CourseManager manager = (CourseManager) getBean("courseManager");

		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息

		String pigId=searchForm.getString("pigId");
		String pigPwd=searchForm.getString("pigPwd");
		String pigNewPwd=searchForm.getString("pigNewPwd");
		String pigNewPwd2=searchForm.getString("pigNewPwd2");

		String oldPwd=(String) session.getAttribute("pigPwd");	//舊密碼

		List wwpasses=manager.getWwpassBy(pigId);
		WwPass wwpass=(WwPass) wwpasses.get(0);

		//認證失敗
		if(!wwpass.getPassword().equals(pigPwd)){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "現用密碼輸入錯誤"));
			saveErrors(request, error);
		}
		//新密碼確認有誤
		if(!pigNewPwd.equals(pigNewPwd2)){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "確認欄輸入的資料不一致"));
			saveErrors(request, error);
		}
		//沒有輸入資料
		if(pigNewPwd.equals("")||pigNewPwd2.equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "沒有輸入新密碼"));
			saveErrors(request, error);
		}
		//沒有修改密碼
		if(pigNewPwd.equals(wwpass.getPassword())){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "新舊密碼一致"));
			saveErrors(request, error);
		}

		if(!error.isEmpty()){
			return mapping.getInputForward();
		}

		wwpass.setPassword(pigNewPwd);
		wwpass.setUsername(pigId);

		System.out.println(pigId);
		manager.updateWwpass(wwpass);

		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "修改完成, 下次登入立即生效"));
		saveMessages(request, msg);

		searchForm.set("pigPwd", "");
		searchForm.set("pigNewPwd", "");
		searchForm.set("pigNewPwd2", "");
		return mapping.getInputForward();
	}

	/**
	 * 查詢
	 */
	public ActionForward query(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession(false);
		DynaActionForm searchForm = (DynaActionForm)form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		String tab0=searchForm.getString("tab0");
		String cname=searchForm.getString("cname");
		String idno=searchForm.getString("idno");
		String cscode=searchForm.getString("cscode");
		String chiName=searchForm.getString("chiName");
		/*
		if(cname.equals("")){
			cname="%";
		}
		*/
		if(idno.equals("")){
			idno="%";
		}
		if(cscode.equals("")){
			cscode="%";
		}
		if(chiName.equals("")){
			chiName="%";
		}

		/**
		 * 查教師
		 */
		if(tab0.equals("1")){

			if(cname.length()<1){
				ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "姓名欄至少一個字元"));
				saveErrors(request, error);

				return mapping.getInputForward();
			}

			List<Empl> piggies = manager.getEmplBy(idno, cname);
			//session.setAttribute("peoples", manager.getEmplBy(idno, cname));


			for (Empl employee : piggies) {
				employee.setSex2(Toolket.getSex(employee.getSex(), request));
				employee.setCategory2(Toolket.getEmpCategory(employee.getCategory()));
				employee.setUnit2(Toolket.getEmpUnit(employee.getUnit()));
				employee.setPcode2(Toolket.getEmpRole(employee.getPcode()));
				employee.setStatus2(Toolket.getEmpStatus(employee.getStatus(), true));
			}

			session.setAttribute("piggies", piggies);
			session.setAttribute("piggieSize", piggies.size());
			session.setAttribute("tab0", tab0);
		}

		/**
		 * 查課程
		 */
		if(tab0.equals("2")){
			List subjects=manager.findCoursesBy(cscode, chiName, "");
			session.setAttribute("subjects", subjects);
			session.setAttribute("subjectSize", subjects.size());
			session.setAttribute("tab0", tab0);
		}

		if(tab0.equals("4")){

		}


		//session.setAttribute("hiddenField", hiddenField);
		return mapping.getInputForward();
	}

	/**
	 * 新訊息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward create(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm searchForm = (DynaActionForm)form;
		CourseManager manager = (CourseManager) getBean("courseManager");

		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");	//建立日期格式

		String pigsName=session.getAttribute("piggyName").toString();
		String pigsMoudle=searchForm.getString("pigsMoudle");
		Date msgStart=(Date)formatter.parse(searchForm.getString("msgStart"));
		Date msgEnd=(Date)formatter.parse(searchForm.getString("msgEnd"));
		String pigsMsg=searchForm.getString("pigsMsg");

		if(msgEnd.before(msgStart)){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "時間順序矛盾"));
			saveErrors(request, error);
		}

		if(pigsMsg.trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "沒有輸入訊息"));
			saveErrors(request, error);
		}
		//如果有錯就什麼也不做
		if(!error.isEmpty()){
			return mapping.getInputForward();
		}

		Message message=new Message();

		message.setCategory(pigsMoudle);
		message.setSender(pigsName);
		message.setStartDate(msgStart);
		message.setDueDate(msgEnd);
		message.setContent(pigsMsg);

		manager.saveMessageBy(message);
		session.setAttribute("piggyMsgList", manager.getMessageBy(pigsName));

		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "訊息已發佈"));
		saveMessages(request, msg);

		return mapping.getInputForward();
	}

	/**
	 * 刪訊息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm searchForm = (DynaActionForm)form;
		CourseManager manager = (CourseManager) getBean("courseManager");

		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息

		String checkPmsg[]=(String[])searchForm.get("checkPmsg");
		if(checkPmsg.length<1){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "沒有選擇要刪除的訊息"));
			saveErrors(request, error);
			return mapping.getInputForward();
		}
		int rmPmsg=0;
		for(int i=0; i<checkPmsg.length; i++){
			manager.removeMessageBy(checkPmsg[i]);
			rmPmsg=rmPmsg+1;
		}
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "共刪除 "+rmPmsg+" 條訊息"));
		saveMessages(request, msg);
		session.setAttribute("piggyMsgList", manager.getMessageBy(session.getAttribute("piggyName").toString()));
		return mapping.getInputForward();
	}

}
