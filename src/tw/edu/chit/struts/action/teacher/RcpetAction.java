package tw.edu.chit.struts.action.teacher;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Rcconf;
import tw.edu.chit.model.Rcpet;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class RcpetAction extends BaseLookupDispatchAction {
	
	/**
	 * 初始畫面資料
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		Toolket.resetCheckboxCookie(response, "rcpet");		
		
		String teacherId = user.getMember().getAccount();		
		
		session.setAttribute("school_Year", manager.getSchoolYear());  //取得學年度
		session.setAttribute("TeacherName", user.getMember().getName());  //取得教師姓名
		session.setAttribute("TeacherUnit", user.getMember().getUnit2());  //取得教師單位
		session.setAttribute("authorno", manager.ezGetBy("Select Oid, name From Rccode Where type='10'"));  //取得作者順序
		session.setAttribute("area", manager.ezGetBy("Select Oid, name From Rccode Where type='13'"));  //取得專利區域
		session.setAttribute("petType", manager.ezGetBy("Select Oid, name From Rccode Where type='14'"));  //取得專利類型
		session.setAttribute("schedule", manager.ezGetBy("Select Oid, name From Rccode Where type='15'"));  //取得進度狀況
		session.setAttribute("depute", manager.ezGetBy("Select Oid, name From Rccode Where type='16'"));  //取得授權與技術轉移
		session.setAttribute("score", manager.ezGetBy("Select Oid, name From Rccode Where type='18'"));  //取得報告分數
		session.setAttribute("proposerType", manager.ezGetBy("Select Oid, name From Rccode Where type='19'"));  //取得申請人類型

		setContentPage(request.getSession(false), "teacher/Rcpet.jsp");
		return mapping.findForward("Main");
	}
	
	
	/**
	 * 新增資料
	 */
	public ActionForward Save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		ActionMessages messages = new ActionMessages();
		Rcpet rcpet = new Rcpet();
		
		String idno = user.getMember().getAccount();
		String schoolYear = aForm.getString("schoolYear");
		String projno = aForm.getString("projno");
		String title = aForm.getString("title");
		String authorno = aForm.getString("authorno");
		String area = aForm.getString("area");
		String petType = aForm.getString("petType");
		String schedule = aForm.getString("schedule");
		String bdate = aForm.getString("bdate");
		String edate = aForm.getString("edate");
		String inst = aForm.getString("inst");
		String certno = aForm.getString("certno");
		String depute = aForm.getString("depute");
		String intor = aForm.getString("intor");
		String score = aForm.getString("score");
		String proposer = aForm.getString("proposer");
		String proposerType = aForm.getString("proposerType");
		String deputeMoney = aForm.getString("deputeMoney");
		String deputeBusiness = aForm.getString("deputeBusiness");
		String deputeSdate = aForm.getString("deputeSdate");
		String deputeEdate = aForm.getString("deputeEdate");
		
		rcpet.setIdno(idno);
		rcpet.setSchoolYear(Short.parseShort(schoolYear));
		rcpet.setProjno(projno);
		rcpet.setTitle(title);
		rcpet.setAuthorno(authorno);
		rcpet.setArea(area);
		rcpet.setPetType(petType);
		rcpet.setSchedule(schedule);
		rcpet.setBdate(bdate);
		rcpet.setEdate(edate);
		rcpet.setInst(inst);
		rcpet.setCertno(certno);
		rcpet.setDepute(depute);
		rcpet.setIntor(intor);
		rcpet.setScore(score);
		rcpet.setProposer(proposer);
		rcpet.setProposerType(proposerType);
		rcpet.setDeputeMoney(deputeMoney);
		rcpet.setLastModified(new Date());
		rcpet.setApprove("96");                          //預設存入狀態=處理中  Rccode.Oid=96 
		rcpet.setDeputeBusiness(deputeBusiness);         //99年新增欄位
		rcpet.setDeputeSdate(deputeSdate);               //99年新增欄位
		rcpet.setDeputeEdate(deputeEdate);               //99年新增欄位
		
				
		manager.updateObject(rcpet);
				
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "新增完成"));
		saveMessages(request, messages);
				
		session.removeAttribute("RcpetList");
		return mapping.findForward("Main");
		
	}
	
	/**
	 * 查詢資料
	 */
	public ActionForward Query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		Toolket.resetCheckboxCookie(response, "rcpet");
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		
		
		String teacherName = user.getMember().getName();
		String teacherId = user.getMember().getAccount();
		String teacherUnit = user.getMember().getUnit2();
		Integer schoolYear = manager.getSchoolYear();
		String school_Year = aForm.getString("schoolYear");
		
		List<Rcpet> rcpet = manager.ezGetBy(
				" Select R.Oid, R.idno, R.school_year, R.projno, R.title, R.authorno, R.area, R.petType, R.schedule," +
				       " R.bdate, R.edate, R.inst, R.certno, R.depute, R.intor, Rc.name"+
				" From Rcpet R, Rccode Rc" +
				" Where R.approve = Rc.Oid " +
				" And R.idno = '"+ teacherId +"'"+
				" And R.school_year Like ('%"+school_Year+"%')");
				
		
		session.setAttribute("RcpetList", rcpet);
		
		setContentPage(request.getSession(false), "teacher/Rcpet.jsp");
		return mapping.findForward("Main");
	}	
	
	/**
	 * 檢視資料
	 */
	public ActionForward View(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		//Toolket.resetCheckboxCookie(response, "rcpet");		
		
		String teacherId = user.getMember().getAccount();		
		String oids = Toolket.getSelectedIndexFromCookie(request, "rcpet");
		String oid_s = oids.substring(1, oids.length()-1);
		
		session.setAttribute("school_Year", manager.getSchoolYear());  //取得學年度
		session.setAttribute("TeacherName", user.getMember().getName());  //取得教師姓名
		session.setAttribute("TeacherUnit", user.getMember().getUnit2());  //取得教師單位
		session.setAttribute("authorno", manager.ezGetBy("Select Oid, name From Rccode Where type='10'"));  //取得作者順序
		session.setAttribute("area", manager.ezGetBy("Select Oid, name From Rccode Where type='13'"));  //取得專利區域
		session.setAttribute("petType", manager.ezGetBy("Select Oid, name From Rccode Where type='14'"));  //取得專利類型
		session.setAttribute("schedule", manager.ezGetBy("Select Oid, name From Rccode Where type='15'"));  //取得進度狀況
		session.setAttribute("depute", manager.ezGetBy("Select Oid, name From Rccode Where type='16'"));  //取得授權與否
		session.setAttribute("score", manager.ezGetBy("Select Oid, name From Rccode Where type='18'"));  //取得報告分數
		session.setAttribute("proposerType", manager.ezGetBy("Select Oid, name From Rccode Where type='19'"));  //取得申請人類型
		
		session.setAttribute("oid_s", oid_s);
		session.setAttribute("idno", manager.ezGetString("Select idno From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("school_year", manager.ezGetString("Select school_year From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("projno", manager.ezGetString("Select projno From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("title", manager.ezGetString("Select title From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("authorno_v", manager.ezGetString("Select authorno From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("area_v", manager.ezGetString("Select area From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("petType_v", manager.ezGetString("Select petType From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("schedule_v", manager.ezGetString("Select schedule From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("bdate", manager.ezGetString("Select bdate From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("edate", manager.ezGetString("Select edate From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("inst", manager.ezGetString("Select inst From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("certno", manager.ezGetString("Select certno From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("depute_v", manager.ezGetString("Select depute From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("intor", manager.ezGetString("Select intor From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("score_v", manager.ezGetString("Select score From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("proposer", manager.ezGetString("Select proposer From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("proposerType_v", manager.ezGetString("Select proposerType From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("deputeMoney", manager.ezGetString("Select deputeMoney From Rcpet Where Oid='"+oid_s+"'"));
		session.setAttribute("approve", manager.ezGetString("Select approve From Rcpet Where Oid = '"+oid_s+"'"));
		session.setAttribute("approveTemp", manager.ezGetString("Select approveTemp From Rcpet Where Oid = '"+oid_s+"'"));
		session.setAttribute("deputeBusiness", manager.ezGetString("Select deputeBusiness From Rcpet Where Oid = '"+oid_s+"'"));    //99年新增欄位
		session.setAttribute("deputeSdate", manager.ezGetString("Select deputeSdate From Rcpet Where Oid = '"+oid_s+"'"));          //99年新增欄位
		session.setAttribute("deputeEdate", manager.ezGetString("Select deputeEdate From Rcpet Where Oid = '"+oid_s+"'"));          //99年新增欄位
		
		setContentPage(request.getSession(false), "teacher/Rcpet_View.jsp");
		return mapping.findForward("Main");
	}
	
	
	@Override
	protected Map<String, String> getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("Create", "Save");
		map.put("Query", "Query");
		map.put("View", "View");
		return map;
	}

}
