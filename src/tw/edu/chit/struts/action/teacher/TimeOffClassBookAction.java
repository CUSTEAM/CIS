package tw.edu.chit.struts.action.teacher;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Adcd;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.Dtimes;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Global;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class TimeOffClassBookAction  extends BaseLookupDispatchAction {

	/*
	 * 設計理念：
	 * classBookList點名簿：為方便 DynaActionForm資料填入,所有任一科目均包含0~15節之曠缺狀態
	 * ,狀態為'x'者表示該課課程不是開在這個節次
	 * 
	 * subjList課程資料：全部[byClass]之當天(星期?)課程或指定開放選修[bySubject]之當天(星期?)課程
	 * 
	 */
	
	
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("PreviewPrint", "print");
		map.put("Back", "cancel"                                                                                                                               );
		return map;
	}
	
	/**
	 * @comment Action預設之執行方法
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm dynForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		
		List<Code5> tfTypeList = Global.TimeOffList;
		
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String idno = credential.getMember().getIdno().trim();
		
		List<Dtimes> dtimelist = new ArrayList();
		Map dtimeMap = new HashMap();
		
		List cscodelist = new ArrayList();
		List dtimes = new ArrayList();
		
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		Calendar tfdate = Calendar.getInstance();
		tfdate.set(Calendar.HOUR_OF_DAY, 0);
		tfdate.set(Calendar.MINUTE, 0);
		tfdate.set(Calendar.SECOND, 0);
		tfdate.set(Calendar.MILLISECOND, 0);

		// 1:SUNDAY, 2:MONDAY,...
		int iweek = tfdate.get(Calendar.DAY_OF_WEEK);
		if (iweek == 1)
			iweek = 7;
		else
			iweek--;

		Dtime nearDtime = null;
		Dtime electDtime = null;
		Calendar[] thisweek = new Calendar[8];	//8天內的日期資料
		for(int i = 0; i < 8; i++){
			thisweek[i] = Calendar.getInstance();
			thisweek[i].add(Calendar.DAY_OF_MONTH, -i);
		}
		final String[] weekp = {"(一)","(二)","(三)","(四)","(五)","(六)","(日)"}; 
		dtimelist = sm.findTeachSubject4TchClassbook(credential);
		if (dtimelist.size() > 0) {
			String clazz = "";
			int weekd = 0, sameWeek = 0, offset = 0, begin = 0, end = 0;
			boolean foundNear = false;
			int foundNearResult = 99;
			
			for (Dtimes dts:dtimelist) {
				Dtime dtime = dts.getDtime();
				clazz = dtime.getDepartClass();

				Map cmap = new HashMap();
				cmap.put("dtimeOid", dtime.getOid());
				cmap.put("cscode", dtime.getCscode());
				cmap.put("className2", dtime.getClassName2());
				cmap.put("chiName2", dtime.getChiName2());
				cscodelist.add(cmap);

			}
		    
			session.setAttribute("TeachClassesPrint", cscodelist);
			
			// log.debug("Get DtimeList prop.==> dtimes.Oid=" +
			// (((Dtime)dtimelist.get(0)).getOid()));
		}
		
		Map initMap = new HashMap();
		initMap.put("teacherName", credential.getMember().getName());
		session.setAttribute("TFClassBookPrintInfo", initMap);
		
		setContentPage(session, "teacher/TimeOffClassBook.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward print(ActionMapping mapping, ActionForm aform,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out=response.getWriter();
		StudAffairManager sm = (StudAffairManager)this.getBean("studAffairManager");
		ScoreManager scm = (ScoreManager)this.getBean("scoreManager");
		DynaActionForm daForm = (DynaActionForm)aform;
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		
		Map TimeoffInit = new HashMap();
		
		String dtOid = daForm.getString("dtOid");
		
		Dtime dtime = sm.getDtimeByOid(Integer.parseInt(dtOid));
		
		String courseType = dtime.getOpt();	//課程類別:1=必修, 2=選修, 3=..., 4=...
		int isOpen = dtime.getOpen();		//是否開放選修: 0=不開放, 1=開放, 2=..., 3=..
		String daynite;
		String myclass = dtime.getDepartClass();
		String cscode = dtime.getCscode();
		
		List baseClassBook = new ArrayList();
		List classBookList = new ArrayList();
		List dtimelist = new ArrayList();
		List subjects = new ArrayList(); 	//存放所有課程之資訊
		List subjectsInUse = new ArrayList(); 	//存放classBook課程之資訊
		
		Dtime subject;
		int dtime_begin = 0;
		int dtime_end = 0;
		String dtime_chiName = "";
		String studentNo = "";
		
		List<Student> studlist = sm.findStudentsByClass(myclass);
		
		Map classBookMap = new HashMap();	//存放classbook點名簿資訊
		List<Map> classBook = new ArrayList<Map>();
		
		if(dtime.getCscode().equalsIgnoreCase("50000") || dtime.getCscode().equalsIgnoreCase("T0001")){
			for (Iterator<Student> stuIter = studlist.iterator(); stuIter
					.hasNext();) {
				Student student = stuIter.next();
				Map stuMap = new HashMap();
				stuMap.put("studentNo", student.getStudentNo());
				stuMap.put("studentName", student.getStudentName());

				classBook.add(stuMap);
			}

		}else{
			List<Seld> seldList = sm.findSeldForClassBook(dtime.getOid(), dtime.getDepartClass(), 0);
		
			for (Iterator<Seld> seldIter = seldList.iterator(); seldIter.hasNext();) {
				Seld seld = seldIter.next();
				Map stuMap = new HashMap();
		
				stuMap.put("studentNo", seld.getStudentNo());
				stuMap.put("studentName", seld.getStudentName());
				stuMap.put("memo", "");
				classBook.add(stuMap);
			}
		}
		
				
		classBookMap.put("cscode", dtime.getOid());
		classBookMap.put("cscodeName", "");
		classBookMap.put("classBook", classBook);
		//classBookList.add(classBookMap);

		session.setAttribute("classBookPrint", classBookMap);
		
		Map initMap = new HashMap();
		initMap.put("teacherName", credential.getMember().getName());
		initMap.put("schoolYear", Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR));
		initMap.put("schoolTerm", Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM));
		initMap.put("depClassName", Toolket.getClassFullName(dtime.getDepartClass()));
		initMap.put("departClass", dtime.getDepartClass());
		initMap.put("cscode", dtime.getCscode());
		initMap.put("Oid", dtime.getOid());
		initMap.put("cscodeName", scm.findCourseName(dtime.getCscode()));
		session.setAttribute("TFClassBookPrintInfo", initMap);
		
		setContentPage(session, "teacher/TimeOffClassBookPrint.jsp");
		return mapping.findForward("Blank");
	}
	
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("classBookPrint");
		setContentPage(session, "teacher/TimeOffClassBook.jsp");
		return mapping.findForward("Main");
	}

	public ActionForward print_old(ActionMapping mapping, ActionForm aform,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out=response.getWriter();
		StudAffairManager sm = (StudAffairManager)this.getBean("studAffairManager");
		ScoreManager scm = (ScoreManager)this.getBean("scoreManager");
		DynaActionForm daForm = (DynaActionForm)aform;
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		
		Map TimeoffInit = new HashMap();
		
		String dtOid = daForm.getString("dtOid");
		
		Dtime dtime = sm.getDtimeByOid(Integer.parseInt(dtOid));
		
		String courseType = dtime.getOpt();	//課程類別:1=必修, 2=選修, 3=..., 4=...
		int isOpen = dtime.getOpen();		//是否開放選修: 0=不開放, 1=開放, 2=..., 3=..
		String daynite;
		String myclass = dtime.getDepartClass();
		String cscode = dtime.getCscode();
		
		List baseClassBook = new ArrayList();
		List classBookList = new ArrayList();
		List dtimelist = new ArrayList();
		List subjects = new ArrayList(); 	//存放所有課程之資訊
		List subjectsInUse = new ArrayList(); 	//存放classBook課程之資訊
		
		Dtime subject;
		int dtime_begin = 0;
		int dtime_end = 0;
		String dtime_chiName = "";
		String studentNo = "";
		
		List<Student> studlist = sm.findStudentsByClass(myclass);
		
		Map classBookMap = new HashMap();	//存放classbook點名簿資訊
		List<Map> classBook = new ArrayList<Map>();
		
		
		/**
		 * 2009/02/23 主任指示：只顯示選課學生不顯示退選等註記
		 */
		if (courseType.equals("2") || courseType.equals("3")) {	//選修及通識課程直接抓Seld選修的學生
	
				List<Seld> seldList = sm.findSeldForClassBook(dtime.getOid(), dtime.getDepartClass(), 0);
	
				for (Iterator<Seld> seldIter = seldList.iterator(); seldIter.hasNext();) {
					Seld seld = seldIter.next();
					Map stuMap = new HashMap();
	
					stuMap.put("studentNo", seld.getStudentNo());
					stuMap.put("studentName", seld.getStudentName());
					stuMap.put("memo", "");
					classBook.add(stuMap);
				}
				
				classBookMap.put("cscode", dtime.getOid());
				classBookMap.put("cscodeName", "");
				classBookMap.put("classBook", classBook);
				//classBookList.add(classBookMap);

		}else if (courseType.equals("1")) {	//必修課程抓該班的學生,加入選修(如果開放選修)及註記退選學生
				
				//處理點名簿資訊
				//*** STEP 1: 將學生加入 classBook
				classBookMap = new HashMap();	//存放classbook點名簿資訊
				classBook = new ArrayList<Map>();
				//dtimeObj = (Object[])subjList.get(0);
				Map subjMap = new HashMap();
	
				for (Iterator<Student> stuIter = studlist.iterator(); stuIter.hasNext();) {
					Student student = stuIter.next();
					Map stuMap = new HashMap();
					stuMap.put("studentNo", student.getStudentNo());
					stuMap.put("studentName", student.getStudentName());
					stuMap.put("memo", "");

					classBook.add(stuMap);
				}
				
				List<Adcd> adcdlist = sm.findAdcdForClassBook(dtime.getOid(), "D");	// 找出退選學生
				for(Map stMap:classBook){
					studentNo = stMap.get("studentNo").toString();
					for(Adcd adstu:adcdlist){
						if(adstu.getStudentNo().trim().equalsIgnoreCase(studentNo)){
							stMap.put("memo", "退選");
						}
					}
				}
				
				//加入必修課程非開課班之選修學生
				List<Seld> seldNotInClass = sm.findSeldForClassBook(dtime
						.getOid(), dtime.getDepartClass(), 2);
				for (Iterator<Seld> seldIter = seldNotInClass.iterator(); seldIter.hasNext();) {
					Seld seld = seldIter.next();
					Map stuMap = new HashMap();
	
					stuMap.put("studentNo", seld.getStudentNo());
					stuMap.put("studentName", seld.getStudentName());
					stuMap.put("memo", "");
					classBook.add(stuMap);
				}
				

				classBookMap.put("cscode", dtime.getOid());
				classBookMap.put("cscodeName", "");
				classBookMap.put("classBook", classBook);
				//classBookList.add(classBookMap);
		}
		

		session.setAttribute("classBookPrint", classBookMap);
		
		Map initMap = new HashMap();
		initMap.put("teacherName", credential.getMember().getName());
		initMap.put("schoolYear", Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_YEAR));
		initMap.put("schoolTerm", Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM));
		initMap.put("depClassName", Toolket.getClassFullName(dtime.getDepartClass()));
		initMap.put("departClass", dtime.getDepartClass());
		initMap.put("cscode", dtime.getCscode());
		initMap.put("cscodeName", scm.findCourseName(dtime.getCscode()));
		session.setAttribute("TFClassBookPrintInfo", initMap);
		
		setContentPage(session, "teacher/TimeOffClassBookPrint.jsp");
		return mapping.findForward("Blank");
	}
	
	
	
}
