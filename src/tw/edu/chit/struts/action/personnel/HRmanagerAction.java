package tw.edu.chit.struts.action.personnel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
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
import org.hibernate.Hibernate;

import tw.edu.chit.model.DEmpl;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.EmplSave;
import tw.edu.chit.model.MailStorage;
import tw.edu.chit.model.UnitBelong;
import tw.edu.chit.model.WwPass;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.struts.action.publicAccess.mail.MailMessage;
import tw.edu.chit.struts.action.publicAccess.mail.SMTPClient;
import tw.edu.chit.util.Toolket;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class HRmanagerAction extends BaseLookupDispatchAction{
	
	private void saveMoreInforMateion(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");

		//自動完成員工分類
		session.setAttribute("allCategory", manager.ezGetBy("SELECT idno, name FROM CodeEmpl WHERE category='EmpCategory' ORDER BY sequence"));
		//自動完成所有單位
		//session.setAttribute("allUnit", manager.ezGetBy("SELECT idno, name FROM CodeEmpl WHERE (category='Unit' OR category='UnitTeach') ORDER BY sequence"));
		session.setAttribute("allUnit", manager.ezGetBy("SELECT id as idno, name FROM CODE_DEPT"));
		session.setAttribute("allUnitModule", manager.ezGetBy("SELECT id as idno, sname as name FROM CODE_UNIT"));
		
		//自動完成所有職級
		List allPcode=manager.ezGetBy("SELECT idno, name FROM CodeEmpl WHERE category='TeacherRole' ORDER BY sequence");		
		allPcode.addAll(manager.ezGetBy("SELECT idno, name FROM CodeEmpl WHERE category='EmplRoleStaff' ORDER BY sequence"));
		session.setAttribute("allPcode", allPcode);
		//自動完成所有導師
		session.setAttribute("allTutor", manager.ezGetBy("SELECT idno, name FROM CodeEmpl WHERE category='Tutor' ORDER BY sequence"));
		//自動完成兼任職級
		session.setAttribute("allDirector", manager.ezGetBy("SELECT idno, name FROM CodeEmpl WHERE category='EmplRoleDirector' ORDER BY sequence"));
		//自動完成離職原因
		session.setAttribute("allStatusCause", manager.ezGetBy("SELECT idno, name FROM CodeEmpl WHERE category='StatusCause' ORDER BY sequence"));
		//自動完成任職狀態
		session.setAttribute("allStatus", manager.ezGetBy("SELECT idno, name FROM CodeEmpl WHERE category='EmpStatus' ORDER BY sequence"));
		
		//聘書管理
		//自動教師分類
		session.setAttribute("allType", manager.ezGetBy("SELECT id, name FROM Pecode WHERE type='pccode6' ORDER BY sequence"));
		session.setAttribute("allLevel", manager.ezGetBy("SELECT id, name FROM Pecode WHERE type='pccode7' ORDER BY sequence"));
		session.setAttribute("allMarkup", manager.ezGetBy("SELECT id, name FROM Pecode WHERE type='pccode8' ORDER BY sequence"));
		
		//學歷		
		session.setAttribute("allDegree", manager.ezGetBy("SELECT idno, name FROM CodeEmpl WHERE category='degree' ORDER BY sequence"));
		session.setAttribute("allPeDegree", manager.ezGetBy("SELECT id, name FROM Pecode WHERE type='pccode5' ORDER BY sequence"));
		//證照
		session.setAttribute("allLicence", manager.ezGetBy("SELECT id, name FROM Pecode WHERE type='pccode10' ORDER BY sequence"));
	}
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		clearAll(request, response);				
		HttpSession session = request.getSession(false);		
		CourseManager manager = (CourseManager) getBean("courseManager");
		/* 20120809 Mark BY yichen  ===========BEGIN======================== */
		/*boolean isHost=manager.syncTest();//驗證為正式伺服機
		session.setAttribute("isHost", isHost);*/
		/* 20120809 Mark BY yichen  ============END========================= */
		session.setAttribute("isHost", false); // 20120809 Modi BY yichen
		saveMoreInforMateion(request);
		
		session.setAttribute("showPath", manager.ezGetString("SELeCT Value FROM Parameter WHERE Category='ftp-empl-photo' AND Name='showPath'"));
				
		/* 20120809 Mark BY yichen  ===========BEGIN======================== */
		//TODO *****人事同步，若不再同步請移除以下區塊
		/*if(!isHost){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","同步連線失效, 請連繫電算中心 (若是測試環境請忽略這則訊息)"));
			saveErrors(request, error);
		}else{
			ActionMessages msg = new ActionMessages();		//建立共用訊息
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","同步服務正常運作中"));
			saveMessages(request, msg);
		}*/
		/* 20120809 Mark BY yichen  ============END========================= */
		setContentPage(request.getSession(false), "personnel/HRManager.jsp");
		return mapping.findForward("Main");
	}	
	
	/**
	 * 查詢列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward goContinue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		clearAll(request, response);
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		DynaActionForm emplForm = (DynaActionForm) form;		
		Map map=emplForm.getMap();		
		List empls=new ArrayList();
		
		//若無啟動進階搜尋
		if(map.get("exSearch").toString().equals("")){			
			if(emplForm.getString("fsidno").equals("") && emplForm.getString("fscname").equals("")){
				session.setAttribute("editMode", "edit");
				session.setAttribute("Dtype", "new");
				session.setAttribute("empls", empls);
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","新進人員"));
				saveMessages(request, msg);
				return mapping.findForward("Main");
			}			
			
			if(map.get("onjob").equals("")){
				empls=manager.getEmpl(map, "empl", "");
				empls.addAll(manager.getEmpl(map, "dempl", ""));
			}else{
				if(map.get("onjob").equals("empl")){//取在職
					empls=manager.getEmpl(map, map.get("onjob").toString(), "");
				}
				if(map.get("onjob").equals("dempl")){//取離職
					empls=manager.getEmpl(map, map.get("onjob").toString(), "");
				}
			}
			
		}else{
			
			if(map.get("onjob").equals("")){
				empls=manager.getEmpl(map, "empl", map.get("exSearch").toString());
				empls.addAll(manager.getEmpl(map, "dempl", map.get("exSearch").toString()));
			}else{
				
				//取在職
				if(map.get("onjob").equals("empl")){
					empls=manager.getEmpl(map, map.get("onjob").toString(), map.get("exSearch").toString());
				}
				//取離職
				if(map.get("onjob").equals("dempl")){
					empls=manager.getEmpl(map, map.get("onjob").toString(), map.get("exSearch").toString());
				}
			}			
		}
		
		if(empls.size()>1){//查詢結果為多人即進入清單列表
			session.setAttribute("editMode", "list");
			session.setAttribute("empls", empls);
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","查詢完成, 進入列表模式"));
			saveMessages(request, msg);
		}
		
		if(empls.size()==1){//查詢結果為1人即進入編輯模式
			session.setAttribute("editMode", "edit");
			//將日期轉為民國年
			session.setAttribute("aEmpl", manager.getADorCD((Map)empls.get(0)));//存入	
			//查詢歷史紀錄	
			getMoreInformateion(request, map, ((Map)empls.get(0)).get("idno").toString());
			
			if(manager.ezGetInt("SELECT COUNT(*)FROM empl WHERE idno='"+((Map)empls.get(0)).get("idno")+"'")>0){
				session.setAttribute("Dtype", "on");
				session.setAttribute("empls", empls);
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","查詢完成, 進入在職人員異動模式"));
				saveMessages(request, msg);
			}else{
				session.setAttribute("Dtype", "off");
				session.setAttribute("empls", empls);
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","查詢完成, 進入離職人員異動模式"));
				saveMessages(request, msg);
			}
		}		
		return mapping.findForward("Main");
	}	

	/**
	 * 異動教職員
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * 離職日+1
	 */
	public ActionForward editEmpl(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");	
		DynaActionForm emplForm = (DynaActionForm) form;
		session.setAttribute("editMode", "leave");
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		
		Map map=emplForm.getMap();//map的所有均為民國年
		Empl empl=(Empl)manager.hqlGetBy("FROM Empl WHERE idno='"+map.get("idno")+"'").get(0);//取原empl		
		//null檢查
		empl=checkNull(empl);		
		//若: 學歷、員工分類....有異動要存歷史
		if(checkChange(empl, map)){
			//立即取得尚未變更的資料
			Map oldEmpl=((Map)manager.ezGetBy("SELECT * FROM empl WHERE idno='"+map.get("idno")+"'").get(0));
			
			//留存離開日期
			if(map.get("EndDate").equals("")){//若未建立真正離職日				
				oldEmpl.put("EndDate", sf.format(more1day(new Date(), -1)));//建西元的離職日				
			}else{//若有建立離職日
				Date d=more1day(sf.parse(manager.convertDate(map.get("EndDate").toString())),-1);				
				oldEmpl.put("EndDate", sf.format(d));//將民國轉西元				
			}
			//留存本次的備註
			oldEmpl.put("Remark", map.get("Remark"));//儲存最後一次所下的註記
			oldEmpl=manager.getADorCD(oldEmpl);//轉民國年
			session.setAttribute("oldEmpl", oldEmpl);//舊的準備提供儲存			
			//清掉舊資訊
			//map.put("StartDate", manager.convertDate(sf.format(new Date())));//把日期弄新(民國)
			
			map.put("StartDate", map.get("EndDate").toString());//把日期弄新(民國)
			//map.put("EndDate", oldEmpl.get("EndDate").toString());//把日期弄新(民國)			
			map.put("EndDate", "");//離職日期清掉
			map.put("Remark", "");//把備註弄掉
		}
		//System.out.println(map.get("image").getClass());
		session.setAttribute("aEmpl", map);//新的待存
		//System.out.println(map);
		//若是離職則查找帳號和開課
		if(map.get("Status").equals("9")){
			session.setAttribute("myAccount", manager.ezGetMap("SELECT username, password " +
					"FROM wwpass WHERE username='"+map.get("idno")+"'"));//帳號資料待刪
			
			session.setAttribute("myCs", manager.ezGetBy("SELECT cs.chi_name, d.Sterm, c.ClassName " +
			"FROM Dtime d, Csno cs, Class c WHERE cs.cscode=d.cscode AND c.ClassNo=d.depart_class " +
			"AND d.techid='"+map.get("idno")+"' ORDER BY d.Sterm"));//有開課？			
		}
		
		return mapping.findForward("Main");
	}
	
	/**
	 * 異動離職教職員
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editDempl(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		DynaActionForm emplForm = (DynaActionForm) form;		
		session.setAttribute("editMode", "leave");
		//SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		
		Map map=emplForm.getMap();
		DEmpl empl=(DEmpl)manager.hqlGetBy("FROM DEmpl WHERE idno='"+map.get("idno")+"'").get(0);//取原empl		
		//null檢查
		empl=checkNull(empl);
		map.put("StartDate", map.get("EndDate"));
		map.put("EndDate", "");	

		//若是復職則查找帳號
		if(!map.get("Status").equals("9")){
			//Map myAccount=new HashMap();
			map.put("idno", map.get("idno"));
			map.put("password", map.get("bdate").toString().replace("-", ""));
			session.setAttribute("myAccount", map);//帳號資料待新增			
		}
		session.setAttribute("aEmpl", map);//新的待存
		//session.setAttribute("aEmpl", emplForm.getMap());
		session.setAttribute("editMode", "back");		
		return mapping.findForward("Main");
	}	

	/**
	 * 取消
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//HttpSession session = request.getSession(false);
		clearAll(request, response);		
		
		return mapping.findForward("Main");
	}	

	/**
	 * 以勾選取得人員
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		//DynaActionForm emplForm = (DynaActionForm) form;
		
		List checked=getCheckBox(request);
		
		if(checked.size()!=1){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","要修改哪一筆員工資料呢? "));
			saveErrors(request, error);
			return mapping.findForward("Main");	
		}
		
		session.setAttribute("editMode", "edit");
		
		Map map=new HashMap();
		Map aEmpl;
		if(((Map)checked.get(0)).get("table").equals("empl")){//若為在職人員
			session.setAttribute("Dtype",  "on");
			map.put("fsidno", ((Map)checked.get(0)).get("idno"));
			map.put("fscname", "");//為了...
			map.put("exSearch", "");
			aEmpl=((Map)manager.getEmpl(map, "empl", "").get(0));
			
		}else{
			session.setAttribute("Dtype",  "off");
			map.put("fsidno", ((Map)checked.get(0)).get("idno"));
			map.put("fscname", "");//為了...
			map.put("exSearch", "");
			aEmpl=((Map)manager.getEmpl(map, "dempl", "").get(0));
			
		}
		
		aEmpl=manager.getADorCD(aEmpl);//西元換民國
		session.setAttribute("aEmpl", aEmpl);
		
		
		//查詢歷史紀錄
		getMoreInformateion(request, map, aEmpl.get("idno").toString());
		/*
		map.put("fsidno", ((Map)checked.get(0)).get("idno"));
		List aEmplSave=manager.getEmpl(map, "Empl_save", "");
		//轉成民國
		for(int i=0; i<aEmplSave.size(); i++){
			getADorCD(((Map)aEmplSave.get(i)));
		}
		session.setAttribute("aEmplSave", aEmplSave);
		*/
		
		
		return mapping.findForward("Main");
	}
	
	/**
	 * 取得勾選盒
	 * @param request
	 * @return
	 */
	private List getCheckBox(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		String emps = Toolket.getSelectedIndexFromCookie(request, "emps");
		
		List empList = (List) session.getAttribute("empls");
		Map map;
		//Map tmpEmpl;
		List list=new ArrayList();
		//String Oid;
		String idno;
		for(int i=0; i<empList.size(); i++){
			idno=((Map)empList.get(i)).get("idno").toString();
			if (Toolket.isValueInCookie(idno, emps)){
				map=new HashMap();				
				//若查到在離職員工中有他
				if(manager.ezGetInt("SELECT COUNT(*)FROM empl WHERE idno='"+idno+"'")>0){					
					map.put("idno", ((Map)empList.get(i)).get("idno"));
					map.put("table", "empl");
				//否則就是在職
				}else{
					map.put("idno", ((Map)empList.get(i)).get("idno"));
					map.put("table", "dempl");
				}
				list.add(map);
			}
		}
		return list;
	}
	
	/**
	 * 清除所有
	 * @param request
	 */
	private void clearAll(HttpServletRequest request, HttpServletResponse response){
		Toolket.resetCheckboxCookie(response, "emps");
		HttpSession session = request.getSession(false);
		session.removeAttribute("editMode");
		session.removeAttribute("empls");
		session.removeAttribute("Dtype");
		session.removeAttribute("aEmpl");
		session.removeAttribute("aEmplSave");
		//session.removeAttribute("saveHist");
		session.removeAttribute("oldEmpl");
		session.removeAttribute("hist");
		
	}
	
	/**
	 * 換1個Empl回來
	 * @param map
	 * @return
	 */
	private Empl getEmpl(Map map, boolean newEmpl){
		CourseManager manager = (CourseManager) getBean("courseManager");
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		//Date date;
		Empl empl;
		if(newEmpl){
			empl=new Empl();
		}else{
			empl=(Empl)manager.hqlGetBy("FROM Empl WHERE idno='"+map.get("idno")+"'").get(0);
		}
		
		try {
			empl.setBdate(sf.parse(map.get("bdate").toString()));
		} catch (ParseException e) {
			empl.setBdate(null);
		}
		
		empl.setCaddr(map.get("caddr").toString());
		empl.setCategory(map.get("category").toString());
		empl.setCellPhone(map.get("CellPhone").toString());
		empl.setCname(map.get("cname").toString());
		empl.setCzip(map.get("czip").toString());
		empl.setDegree(map.get("Degree").toString());
		empl.setEmail(map.get("Email").toString());
		empl.setEname(map.get("ename").toString());		
		try {
			empl.setEndDate(sf.parse(map.get("EndDate").toString()));
		} catch (Exception e1) {
			empl.setEndDate(null);
		}
		
		empl.setDirector(map.get("Director").toString());
		empl.setIdno(map.get("idno").toString());
		empl.setInsno(map.get("insno").toString());
		empl.setPaddr(map.get("paddr").toString());
		empl.setPcode(map.get("pcode").toString());
		empl.setPzip(map.get("pzip").toString());
		empl.setRemark(map.get("Remark").toString());
		empl.setSex(map.get("sex").toString());
		empl.setSname(map.get("sname").toString());				
		try {
			empl.setStartDate(sf.parse(map.get("StartDate").toString()));
		} catch (Exception e) {
			empl.setStartDate(null);
		}
		
		try{
			empl.setAdate(sf.parse(map.get("Adate").toString()));
		}catch(Exception e){
			empl.setAdate(null);
		}
		
		empl.setStatus(map.get("Status").toString());
		empl.setStatusCause(map.get("StatusCause").toString());
		
		if(!map.get("TeachStartDate").equals("")){
			try {
				empl.setTeachStartDate(sf.parse(map.get("TeachStartDate").toString()));
			} catch (ParseException e) {
				empl.setTeachStartDate(null);
			}
		}
		
		empl.setTelephone(map.get("telephone").toString());
		empl.setTutor(map.get("Tutor").toString());
		empl.setUnit(map.get("unit").toString());
		empl.setUnit_module(map.get("unit_module").toString());
		
		return empl;
	}
	
	/**
	 * 換1個Dempl回來
	 * @param map
	 * @return
	 */
	private DEmpl getDEmpl(Map map, boolean newDempl){
		CourseManager manager = (CourseManager) getBean("courseManager");
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		DEmpl dempl;
		if(newDempl){
			dempl=new DEmpl();
		}else{
			dempl=(DEmpl)manager.hqlGetBy("FROM DEmpl WHERE idno='"+map.get("idno")+"'").get(0);
		}
		
		if(!map.get("bdate").equals("")){			
			try {
				dempl.setBdate(sf.parse(map.get("bdate").toString()));
			} catch (ParseException e) {
				dempl.setBdate(null);//此處有疑慮
			}
		}
		
		dempl.setCaddr(map.get("caddr").toString());
		dempl.setCategory(map.get("category").toString());
		dempl.setCellPhone(map.get("CellPhone").toString());
		dempl.setCname(map.get("cname").toString());
		dempl.setCzip(map.get("czip").toString());
		dempl.setDegree(map.get("Degree").toString());
		dempl.setEmail(map.get("Email").toString());
		dempl.setEname(map.get("ename").toString());
		
		
		try {
			dempl.setEndDate(sf.parse((String)map.get("EndDate")));
		} catch (Exception e) {
			dempl.setEndDate(null);
		}
		
		
		dempl.setDirector(map.get("Director").toString());
		dempl.setIdno(map.get("idno").toString());
		dempl.setInsno(map.get("insno").toString());
		dempl.setPaddr(map.get("paddr").toString());
		dempl.setPcode(map.get("pcode").toString());
		dempl.setPzip(map.get("pzip").toString());
		dempl.setRemark(map.get("Remark").toString());
		dempl.setSex(map.get("sex").toString());
		dempl.setSname(map.get("sname").toString());
		
		if(!map.get("StartDate").equals("")){
			try {
				dempl.setStartDate(sf.parse(map.get("StartDate").toString()));
			} catch (ParseException e) {
				dempl.setStartDate(null);
			}
		}
		
		try {
			dempl.setAdate(sf.parse((String)map.get("Adate")));
		} catch (Exception e) {
			dempl.setAdate(null);
		}
		
		dempl.setStatus(map.get("Status").toString());
		dempl.setStatusCause(map.get("StatusCause").toString());
		
		//if(!map.get("TeachStartDate").equals("")){
		try {
			dempl.setTeachStartDate(sf.parse(map.get("TeachStartDate").toString()));
		} catch (ParseException e) {
			dempl.setTeachStartDate(null);
		}
		//}
		
		dempl.setTelephone(map.get("telephone").toString());
		dempl.setTutor(map.get("Tutor").toString());
		dempl.setUnit(map.get("unit").toString());
		if(map.get("unit").toString().equals("")) {
			dempl.setUnit(map.get("unit_module").toString());
		}
		
		return dempl;
	}
	
	
	
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Continue", "goContinue");
		map.put("EditEmpl", "editEmpl");
		map.put("EditDempl", "editDempl");
		map.put("Modify", "modify");
		map.put("Cancel", "cancel");
		map.put("EditEmplConfirm", "editEmplConfirm");
		map.put("EditDemplConfirm", "editDemplConfirm");
		map.put("Complete", "complete");
		map.put("Create", "create");
		map.put("NewEmplConfirm", "newEmplConfirm");
		return map;
	}
	
	
	
	/**
	 * 確定異動在職人員
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editEmplConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");		
		Map aEmpl=(Map)session.getAttribute("aEmpl");
		Map oldEmpl=(Map)session.getAttribute("oldEmpl");
		aEmpl=(Map)manager.getADorCD(aEmpl);//轉西元
		if(oldEmpl!=null){//若需要留存歷史則存一筆歷史
			oldEmpl=(Map)session.getAttribute("oldEmpl");
			oldEmpl=manager.getADorCD(oldEmpl);//轉西元
			manager.updateObject(manager.getEmplSave(oldEmpl, true));//生一個Empl_save出來
		}
		
		String today=sf.format(more1day(new Date(), 1));//民國今天...可是後來說要加1天
		
		//若是離職....注意空白才會用到加1天的今天呀
		if(aEmpl.get("Status").equals("9")){
			if(aEmpl.get("EndDate").equals("")){//若未建立離職日期
				aEmpl.put("EndDate", today);//就存今天
			}			
			manager.executeSql("DELETE FROM empl WHERE idno='"+aEmpl.get("idno")+"'");//立即刪除			
			DEmpl dempl=getDEmpl(aEmpl, true);			
			manager.updateObject(dempl);//轉到離職資料表
						
			
		}else{//若是異動資料			
			Empl empl=getEmpl(aEmpl, false);//由前面傳來的
			if(oldEmpl!=null){//若有記錄歷史資料，現在就重新來			
				if(aEmpl.get("StartDate").equals("")){
					empl.setStartDate(sf.parse(today));
					empl.setEndDate(null);
				}
			}
			manager.updateObject(empl);//立即異動
			
			
		}
		
		if(session.getAttribute("hist")!=null)//若有歷史記錄
		saveHist((List)session.getAttribute("hist"), aEmpl);
		
		//按鈕浮現記錄			
		((DynaActionForm) form).getMap().put("fsidno", aEmpl.get("idno"));
		((DynaActionForm) form).getMap().put("fscname", aEmpl.get("cname"));
		((DynaActionForm) form).getMap().put("exSearch", "");
		request.setAttribute("editOver", "");
		session.setAttribute("editMode", "leave");
		
		//存照片，改存db
		FormFile uplFile=(FormFile)aEmpl.get("image");
		if(!uplFile.getFileName().equals(""))
		try{
			Empl empl=getEmpl(aEmpl, false);		    
			BufferedImage srcImage, destImage;
			double width, height;
			Graphics2D g2d;		
			int size=139;
			try {
				srcImage = ImageIO.read(uplFile.getInputStream());
				width=srcImage.getWidth();
			    height=srcImage.getHeight();				    	
				double y=size/width;
				height=height*y;	
		    	destImage = new BufferedImage(size, (int) height, BufferedImage.TYPE_INT_RGB);
			    g2d = (Graphics2D) destImage.getGraphics();			    
			    g2d.scale(y, y);		    
			    g2d.drawImage(srcImage,0,0,null);		  
					
			}catch (IOException e1) {			
				g2d=null;
			    System.gc();
			    destImage=null;
			    srcImage=null;
				e1.printStackTrace();			
			}
			ByteArrayOutputStream os = new ByteArrayOutputStream();		
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);		
			encoder.encode(destImage);
			Blob photo = Hibernate.createBlob(os.toByteArray());
			empl.setPortrait(photo);
			manager.updateObject(empl);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return goContinue(mapping, form, request, response);
	}
	
	/**
	 * 確定異動離職人員
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editDemplConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		Map aEmpl=manager.getADorCD((Map)session.getAttribute("aEmpl"));//做同步的empl	
		
		if(!aEmpl.get("Status").equals("9")){//非離職狀態要新增在職並刪除離職
			//Map myAccount=(Map)session.getAttribute("myAccount");//拿
			
			Empl empl=getEmpl(aEmpl, true);//變			
			manager.updateObject(empl);//加
			
			//manager.clearPo(empl);//清掉	
			manager.syncDEmpl((DEmpl)manager.hqlGetBy("FROM DEmpl WHERE idno='"+empl.getIdno()+"'").get(0), "D");
			manager.executeSql("DELETE FROM dempl WHERE idno='"+empl.getIdno()+"'");//殺
			manager.updateObject(getWwpass((Map)session.getAttribute("myAccount")));//加	
					
			manager.clearPo(empl);
		}else{//若未更動則...
			DEmpl dempl=getDEmpl(aEmpl, false);
			manager.updateObject(dempl);
		}

		//按鈕浮現記錄			
		((DynaActionForm) form).getMap().put("fsidno", aEmpl.get("idno"));
		((DynaActionForm) form).getMap().put("fscname", aEmpl.get("cname"));
		((DynaActionForm) form).getMap().put("exSearch", "");
		request.setAttribute("editOver", "");
		session.setAttribute("editMode", "leave");		
		return goContinue(mapping, form, request, response);
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
	public ActionForward complete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		//HttpSession session = request.getSession(false);
		//CourseManager manager = (CourseManager) getBean("courseManager");	
		
		
		return unspecified(mapping, form, request, response);
	}
	
	/**
	 * 新增教職員
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm emplForm = (DynaActionForm) form;
		emplForm.set("idno", emplForm.get("idno").toString().toUpperCase());//有人不會分大小寫
		
		//姓名證號空白驗證
		if(emplForm.getString("idno").equals("") || emplForm.getString("cname").equals("") || emplForm.getString("bdate").equals("")){			
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","姓名、身分證、生日不可空白"));
		}
		
		//身份證驗證
		if(!manager.checkIdno(emplForm.getString("idno"))){			
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","身分證字號驗證失敗"));
		}
		
		//已存人員
		if(manager.ezGetInt("SELECT COUNT(*)FROM empl WHERE idno='"+emplForm.getString("idno")+"'")>0 || 
				manager.ezGetInt("SELECT COUNT(*)FROM dempl WHERE idno='"+emplForm.getString("idno")+"'")>0){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","曾為本校員工"));
		}
		
		
		if(!error.isEmpty()){
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		emplForm.getMap().put("password", emplForm.getMap().get("bdate").toString().replace("-", ""));
		
		session.setAttribute("aEmpl", emplForm.getMap());
		session.setAttribute("editMode", "add");
		return mapping.findForward("Main");
	}
	
	/**
	 * 確定新增
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward newEmplConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");	
		
		Map aEmpl=(Map)session.getAttribute("aEmpl");
		
		aEmpl=(Map)manager.getADorCD(aEmpl);//轉西元
		Empl empl=getEmpl(aEmpl, true);
		
		ServletContext context = request.getSession().getServletContext();
		String schoolName=(String) context.getAttribute("SchoolName_ZH");
		manager.updateObject(empl);
		WwPass wwpass=getWwpass(aEmpl);
		manager.updateObject(wwpass);
		
		
		if(!empl.getEmail().equals("")){
			//登入郵件伺服器帳號
			String username=manager.ezGetString("SELECT Value FROM Parameter WHERE Name='username' AND Category='smtp'");
			//登入郵件伺服器密碼
			String password=manager.ezGetString("SELECT Value FROM Parameter WHERE Name='password' AND Category='smtp'");
			//郵件伺服器
			String mailServer =manager.ezGetString("SELECT Value FROM Parameter WHERE Name='mailServer' AND category='smtp'");
			//系統郵件地址
			String mailAddress=manager.ezGetString("SELECT Value FROM Parameter WHERE Name='mailAddress' AND category='smtp'");// 寄件伺服器
			
			//String myEmail=mailForm.getString("myEmail");// 寄件人(回郵信箱)
			//String myName=mailForm.getString("myName");// 寄件人(回郵姓名)
			//String subject=mailForm.getString("subject");
			//String content=mailForm.getString("content");		
			//String myDate=manager.convertDate(mailForm.getString("myDate"));
			
			//Map me=(Map)session.getAttribute("me");//取自己
			//處理寄件人
			Map myInfo=new HashMap();
			
			myInfo.put("myName", schoolName+"校務資訊系統");		
			myInfo.put("myEmail", mailAddress);
			
			List list=new ArrayList();
			list.add(aEmpl);			
			
			//處理收件人
			List to=new ArrayList();
			Map map;
			StringBuilder sb=new StringBuilder();
			for(int i=0; i<list.size(); i++){
				map=new HashMap();
				if(((Map)list.get(i)).get("Email")!=null){
					map.put("cname", ((Map)list.get(i)).get("cname"));
					map.put("Email", ((Map)list.get(i)).get("Email"));
					to.add(map);
					sb.append(((Map)list.get(i)).get("cname")+"<"+((Map)list.get(i)).get("Email")+">, ");
				}
			}
			//若有郵件信箱則自己也一封
			/*
			if(me.get("Email")!=null && me.get("Email")!=""){
				map=new HashMap();
				map.put("cname", me.get("cname"));
				map.put("Email", me.get("Email"));
				to.add(map);
				list.add(map);
				sb.append(me.get("cname")+" <"+me.get("Email")+"> ");
			}
			*/
			
			MailMessage message = new MailMessage();
			map=new HashMap();
			map.put("myName", schoolName+"校務資訊系統");
			map.put("myEmail", mailAddress);
			message.setFrom(map);// 寄件人(回郵信箱)
			//message.setFrom("=?UTF-8?B?"+enc.encode(myName.getBytes())+"?="+" <"+myEmail+">");// 寄件人(回郵信箱)			
			//message.setFrom("=?UTF-8?B?"+enc.encode(myName.getBytes())+"?= <"+myEmail+">");// 寄件人(回郵信箱)		
			//message.setTo(to);// 收件人(收郵信箱)
			String server=mailServer;// 處理郵件伺服器 - 存於資料庫中
			message.setSubject("您的"+schoolName+"校務系統帳戶己建立");// 郵件主題
			message.setContent(aEmpl.get("cname").toString()+"您好, 感謝您成為"+schoolName+"的一份子。<br>" +
					"<a href='http://ap.cust.edu.tw/CIS'>"+schoolName+"校務資訊系統</a>未來將為您提供各種服務。<br><br>" +
					"你的系統帳號為: "+wwpass.getUsername()+
					"<br>登入密碼: "+wwpass.getPassword()+"<br><br><br><font size=1>注意︰本郵件是由"+schoolName+"校務系統自動產生與發送，請勿直接回覆。</font>");// 郵件內容		
			message.setDatafrom(myInfo);// 寄件人，顯示用
			message.setDatato(to);// 收件人，顯示用		
			message.setUser(username);// 帳號
			message.setPassword(password);// 密碼

			SMTPClient smtp = new SMTPClient(server, 25);
			boolean flag;
			flag = smtp.sendMail(message, server, list);
			if (flag) {
				saveMessages(request, msg);
				//儲存備份			
				MailStorage mail=new MailStorage();
				mail.setContent("");//
				
				mail.setRealDate(new Date());
							
				mail.setReceiver(sb.toString());			
				mail.setSubject("您的"+schoolName+"校務系統帳戶己建立");			
				manager.updateObject(mail);
				session.removeAttribute("sendList");
				Toolket.resetCheckboxCookie(response, "senders");
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","系統帳號密碼己寄至"+empl.getEmail()+"<br>"));
			} else {
				System.out.println("郵件發送失敗");
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","寄件失敗, 若問題持續發生請找電算中心")); 
				saveErrors(request, error);
				//return mapping.findForward("Main");
			}
		}
		//end
		
		//若是 「專、兼、軍護」 教師則自動增加 T1 權限
		if(empl.getCategory().equals("1") || empl.getCategory().equals("2") || empl.getCategory().equals("4")){
			UnitBelong ub=new UnitBelong();
			ub.setEmpOid(empl.getOid());
			ub.setUnitNo("T1");
			manager.updateObject(ub);
		}
		
		
		
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","新增完成, 進入列表模式"));
		saveMessages(request, msg);
		return unspecified(mapping, form, request, response);
	}
	
	/**
	 * 變1組帳號密碼
	 * @param map
	 * @return
	 */
	private WwPass getWwpass(Map map){
		WwPass wwpass=new WwPass();
		wwpass.setInformixPass(map.get("password").toString());
		wwpass.setPassword(map.get("password").toString());
		wwpass.setPriority("A");
		wwpass.setUpdated(new Date());
		wwpass.setUsername(map.get("idno").toString());
		return wwpass;
	}
	
	/**
	 * 煩死人
	 * @param empl
	 * @return
	 */
	private Empl checkNull(Empl empl){
		if(empl.getDegree()==null){empl.setDegree("");}
		if(empl.getCategory()==null){empl.setCategory("");}
		if(empl.getUnit()==null){empl.setUnit("");}
		if(empl.getPcode()==null){empl.setPcode("");}
		if(empl.getDirector()==null){empl.setDirector("");}
		if(empl.getTutor()==null){empl.setTutor("");}
		if(empl.getStatus()==null){empl.setStatus("");}
		
		return empl;
	}
	
	/**
	 * 煩死人Ⅱ
	 * @param empl
	 * @return
	 */
	private DEmpl checkNull(DEmpl empl){
		if(empl.getDegree()==null){empl.setDegree("");}
		if(empl.getCategory()==null){empl.setCategory("");}
		if(empl.getUnit()==null){empl.setUnit("");}
		if(empl.getPcode()==null){empl.setPcode("");}
		if(empl.getDirector()==null){empl.setDirector("");}
		if(empl.getTutor()==null){empl.setTutor("");}
		if(empl.getStatus()==null){empl.setStatus("");}
		
		return empl;
	}
	
	/**
	 * 敏感性異動
	 * @param obj
	 * @param map
	 * @return
	 */
	private boolean checkChange(Object obj, Map map){
		try{
			Empl empl=(Empl)obj;
			if( !empl.getDegree().equals(map.get("Degree"))||				
				!empl.getCategory().equals(map.get("category"))||//員工分類: 1)專任教師 2)兼任教師 3)職員工 4)軍護教師
				!empl.getUnit().equals(map.get("unit"))||//單位
				!empl.getUnit_module().equals(map.get("unit_module"))||//單位
				!empl.getPcode().equals(map.get("pcode"))||//級職代碼:當員工分類
				!empl.getDirector().equals(map.get("Director"))||//兼任職位
				!empl.getTutor().equals(map.get("Tutor"))||//兼任導師
				!empl.getStatus().equals(map.get("Status"))){
				return true;
			}			
		}catch(Exception e){
			
			DEmpl empl=(DEmpl)obj;
			if( !empl.getDegree().equals(map.get("Degree"))||				
				!empl.getCategory().equals(map.get("category"))||//員工分類: 1)專任教師 2)兼任教師 3)職員工 4)軍護教師
				!empl.getUnit().equals(map.get("unit"))||//單位
				!empl.getPcode().equals(map.get("pcode"))||//級職代碼:當員工分類
				!empl.getDirector().equals(map.get("Director"))||//兼任職位
				!empl.getTutor().equals(map.get("Tutor"))||//兼任導師
				!empl.getStatus().equals(map.get("Status"))){
				return true;
			}	
		}
		
		return false;
	}
	
	/**
	 * 存歷年記錄
	 * @param hist
	 * @throws ParseException 
	 */
	private void saveHist(List hist, Map aEmpl) throws ParseException{
		CourseManager manager = (CourseManager) getBean("courseManager");
		EmplSave emplSave;
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0; i<hist.size(); i++){			
			
			//若沒有Oid則判斷為1個新的
			if(((Map)hist.get(i)).get("saveOid").equals("")){
				emplSave=new EmplSave();				
				emplSave=manager.getEmplSave(aEmpl, true);
				if(!((Map)hist.get(i)).get("saveStartDate").equals("")&& 
						!((Map)hist.get(i)).get("saveEndDate").equals("")){					
					
					emplSave.setCategory(((Map)hist.get(i)).get("saveCategory").toString());
					emplSave.setUnit(((Map)hist.get(i)).get("saveUnit").toString());
					emplSave.setPcode(((Map)hist.get(i)).get("savePcode").toString());
					emplSave.setDirector(((Map)hist.get(i)).get("saveDirector").toString());
					emplSave.setSname(((Map)hist.get(i)).get("saveSname").toString());
					emplSave.setTutor(((Map)hist.get(i)).get("saveTutor").toString());
					
					
					emplSave.setStartDate(sf.parse(manager.convertDate(((Map)hist.get(i)).get("saveStartDate").toString())));
					emplSave.setEndDate(sf.parse(manager.convertDate(((Map)hist.get(i)).get("saveEndDate").toString())));
					
					
					
					emplSave.setRemark(((Map)hist.get(i)).get("saveRemark").toString());
					emplSave.setDegree(((Map)hist.get(i)).get("saveDegree").toString());
					manager.updateObject(emplSave);
				}
				
			}else{				
				//判斷是不是要刪的
				if(((Map)hist.get(i)).get("saveStartDate").equals("")&& 
						((Map)hist.get(i)).get("saveEndDate").equals("")){
					manager.executeSql("DELETE FROM Empl_save WHERE Oid='"+((Map)hist.get(i)).get("saveOid")+"'");
				}else{
					
					if(((Map)hist.get(i)).get("saveOid").equals("")){
						
						emplSave=manager.getEmplSave(aEmpl, false);					
						emplSave.setOid(Integer.parseInt(((Map)hist.get(i)).get("saveOid").toString()));					
						emplSave.setCategory(((Map)hist.get(i)).get("saveCategory").toString());
						emplSave.setUnit(((Map)hist.get(i)).get("saveUnit").toString());
						emplSave.setPcode(((Map)hist.get(i)).get("savePcode").toString());
						emplSave.setDirector(((Map)hist.get(i)).get("saveDirector").toString());
						emplSave.setSname(((Map)hist.get(i)).get("saveSname").toString());
						emplSave.setTutor(((Map)hist.get(i)).get("saveTutor").toString());					
						emplSave.setStartDate(sf.parse(manager.convertDate(((Map)hist.get(i)).get("saveStartDate").toString())));
						emplSave.setEndDate(sf.parse(manager.convertDate(((Map)hist.get(i)).get("saveEndDate").toString())));
						emplSave.setRemark(((Map)hist.get(i)).get("saveRemark").toString());
						emplSave.setDegree(((Map)hist.get(i)).get("saveDegree").toString());
						manager.updateObject(emplSave);						
					}
					
				}
			}
			
		}
	}
	
	/**
	 * 取歷史資訊
	 * @param request
	 * @param map
	 */
	private void getMoreInformateion(HttpServletRequest request, Map map, String idno){
		CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);		
		//任職歷程
		List aEmplSave=manager.getEmpl(map, "Empl_save", map.get("exSearch").toString());
		//將歷程轉成民國年
		for(int i=0; i<aEmplSave.size(); i++){
			manager.getADorCD(((Map)aEmplSave.get(i)));
		}
		session.setAttribute("aEmplSave", aEmplSave);//儲存歷程
		
		//聘書歷程
		List contracts=manager.ezGetBy("SELECT ec.*, c.name as deptName " +
				"FROM Empl_contract ec, CodeEmpl c WHERE ec.idno='"+idno+"' AND ec.deptNo=c.idno AND (c.category='UnitTeach' OR c.category='Unit')");
		for(int i=0; i<contracts.size(); i++){
			try{//開始日期
				((Map)contracts.get(i)).put("start_date", manager.convertDate(((Map)contracts.get(i)).get("start_date").toString()));
			}catch(Exception e){
				
			}
			try{//結束日期
				((Map)contracts.get(i)).put("end_date", manager.convertDate(((Map)contracts.get(i)).get("end_date").toString()));
			}catch(Exception e){
				
			}
		}
		session.setAttribute("contracts", contracts);
		
		//證照
		List licences=manager.ezGetBy("SELECT * FROM Empl_licence WHERE idno='"+idno+"'");
		session.setAttribute("licences", licences);
		
		//學歷
		List degree=manager.ezGetBy("SELECT * FROM Empl_grdschl WHERE idno='"+idno+"' ORDER BY sequence");
		session.setAttribute("degrees", degree);
		
		//實務
		List pehists=manager.ezGetBy("SELECT * FROM Empl_pehist WHERE idno='"+idno+"'");
		for(int i=0; i<pehists.size(); i++){
			try{
				((Map)pehists.get(i)).put("begin", manager.convertDate(((Map)pehists.get(i)).get("begin").toString()));
				((Map)pehists.get(i)).put("end", manager.convertDate(((Map)pehists.get(i)).get("end").toString()));
			}catch(Exception e){
				
			}			
		}
		session.setAttribute("pehists", pehists);		
	}
	
	/**
	 * 加n天？
	 * @param date
	 * @return
	 */
	private Date more1day(Date date, int n){
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, n);
		return c.getTime();
	}
}
