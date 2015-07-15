package tw.edu.chit.struts.action.studaffair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import tw.edu.chit.dao.ScoreDAO;
import tw.edu.chit.dao.StudAffairDAO;
import tw.edu.chit.model.Adcd;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.Dilg;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.DtimeClass;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.Dtimes;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;
import tw.edu.chit.util.Global;

public class TeacherTimeOffInputPatchAction  extends BaseLookupDispatchAction {
	
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("StartUpload", "save");
		map.put("Save", "save");
		map.put("Back", "cancel");
		map.put("Cancel", "cancel");
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
		Map initMap = new HashMap();
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
		
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String idno = credential.getMember().getIdno().trim();
		
		Map TimeoffInit = new HashMap();
		String mode = dynForm.getString("mode");
		//log.debug("mode=" + mode);
		TimeoffInit.put("mode", mode);

		if(mode.trim().equalsIgnoreCase("patch")){
			initMap.put("dtOid", dynForm.getString("dtOid"));
			initMap.put("tdate", dynForm.getString("tdate"));
			initMap.put("mode", mode);
			initMap.put("teacherId", dynForm.getString("teacherId"));
			session.setAttribute("SAFTimeOffInputInfo", initMap);
			
			session.removeAttribute("SAFTimeOffInEditB");
			session.removeAttribute("SAFTimeOffFormMapB");

			setContentPage(session, "studaffair/TeacherTimeOffInputPatch.jsp");
			return mapping.findForward("Main");
		}
		
		//Mode save
		if(mode.trim().equalsIgnoreCase("save")){
			return save(mapping, form, request, response);
		}else if(mode.trim().equalsIgnoreCase("cancel")){
			return cancel(mapping, form, request, response);
		}
		
		session.setAttribute("SAFTimeOffInputInfo", TimeoffInit);
		
		session.removeAttribute("SAFTimeOffInEditB");
		session.removeAttribute("SAFTimeOffFormMapB");

		setContentPage(session, "studaffair/TeacherTimeOffInputPatch.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward save(ActionMapping mapping, ActionForm aform,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		DynaActionForm form = (DynaActionForm)aform;
		
		String classInCharge = credential.getClassInChargeSqlFilterT();
		Map initMap = new HashMap();
		if(session.getAttribute("SAFTimeOffInputInfo") != null){
			initMap = (Map)session.getAttribute("SAFTimeOffInputInfo");
		}

		String mode = form.getString("mode");
		String dtOid = form.getString("dtOid");
		String tdate = form.getString("tdate");
		String teacherId = form.getString("teacherId");
		String[] adate = tdate.split("-");
		String[] studentNo = form.getStrings("studentNo");
		String[] abs0 = form.getStrings("st0");
		String[] abs1 = form.getStrings("st1");
		String[] abs2 = form.getStrings("st2");
		String[] abs3 = form.getStrings("st3");
		String[] abs4 = form.getStrings("st4");
		String[] abs5 = form.getStrings("st5");
		String[] abs6 = form.getStrings("st6");
		String[] abs7 = form.getStrings("st7");
		String[] abs8 = form.getStrings("st8");
		String[] abs9 = form.getStrings("st9");
		String[] abs10 = form.getStrings("st10");
		String[] abs11 = form.getStrings("st11");
		String[] abs12 = form.getStrings("st12");
		String[] abs13 = form.getStrings("st13");
		String[] abs14 = form.getStrings("st14");
		String[] abs15 = form.getStrings("st15");
		
		Map fMap = new HashMap();
		fMap.put("dtOid", dtOid);
		fMap.put("tdate", tdate);
		fMap.put("studentNo", studentNo);
		fMap.put("abs0", abs0);
		fMap.put("abs1", abs1);
		fMap.put("abs2", abs2);
		fMap.put("abs3", abs3);
		fMap.put("abs4", abs4);
		fMap.put("abs5", abs5);
		fMap.put("abs6", abs6);
		fMap.put("abs7", abs7);
		fMap.put("abs8", abs8);
		fMap.put("abs9", abs9);
		fMap.put("abs10", abs10);
		fMap.put("abs11", abs11);
		fMap.put("abs12", abs12);
		fMap.put("abs13", abs13);
		fMap.put("abs14", abs14);
		fMap.put("abs15", abs15);
		
		int tfYear = Integer.parseInt(adate[0]);
		int tfMonth = Integer.parseInt(adate[1]);
		int tfDay = Integer.parseInt(adate[2]);
		Calendar ddate = Calendar.getInstance(); 
		ddate.set(tfYear, tfMonth-1, tfDay, 0, 0, 0);
		int iweek = ddate.get(Calendar.DAY_OF_WEEK);
		if(iweek == 1) iweek = 7;
		else iweek--;
		Date sddate = ddate.getTime();
		
		//try {
			Map msgMap = sm.createOrUpdateDilg4Tch(fMap);
			ActionMessages messages = (ActionMessages)msgMap.get("message");
			List chgList = (List)msgMap.get("changes");
			if (!messages.isEmpty()) {
				saveErrors(request, messages);
				return mapping.findForward("Main");

			} else {
				// 建立或更新上傳紀錄 TimeOffUpload
				ActionMessages errs = sm.createOrUpdateTimeOffUpload(teacherId, Integer.parseInt(dtOid), tdate, credential.getMember().getIdno());
				if(!errs.isEmpty()){
					messages.add(errs);
				}
				
				ActionMessages err1 = sm.updateSeldDilgPeriodBatch(sddate, chgList);
				messages.add(err1);
				ActionMessages err2 = sm.updateJustDilgScoreBatch(chgList);
				//=======================================>>>>>>>>>>>>>>>>>>
				//取代sm.modifyJustDilgScore(studentNo, justScore)該元件執行有誤  Leo20120308				
				Map cbMaps = new HashMap();
				//for(Iterator cbIter = cbList.iterator(); cbIter.hasNext();){
				for(Iterator cbIters = chgList.iterator(); cbIters.hasNext();){
					cbMaps = (Map)cbIters.next();
					String studentNo1 = cbMaps.get("studentNo").toString().toUpperCase();
					double dilgScore = sm.calDilgScoreByStudent(studentNo1, "0");
					CourseManager manager = (CourseManager) getBean("courseManager");
					String sqlstudent_no = manager.ezGetString(
							"Select student_no From just Where student_no='" + studentNo1 + "' ");
					double SeltotalScore = Double.parseDouble(
							manager.ezGetString("Select total_score From just Where student_no='" + studentNo1 + "' "));
					double totalScore = SeltotalScore+dilgScore;	
					if(sqlstudent_no.equals("")){
						errs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"Message.MessageN1", "找不到[" + studentNo1 + "]該學生的操行成績!"));
					}else{		
						manager.executeSql(
								"Update just Set total_score="+totalScore+" Where student_no='"+studentNo1+"'");
					}
				//System.out.println("Leo_OK");
				}
				//=======================================================>>>>>>>>>>>>>>>>>>
				messages.add(err2);
			}

			if (messages.isEmpty()) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.CreateSuccessful"));
				initMap.put("dtOid", null);
				log.debug("forward:" + mapping.getForward());
				session.removeAttribute("TFnotUploadList");
				return mapping.findForward("successForward");
			}
			session.setAttribute("SAFTimeOffInputInfo", initMap);
			saveMessages(request, messages);
			setContentPage(session, "studaffair/TeacherTimeOffInputPatch.jsp");
			return mapping.findForward("Main");
		//} catch (Exception e) {
		//	ActionMessages errors = new ActionMessages();
		//	errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
		//			"Exception.generic", e.getMessage()));
		//	log.error(e);
		//	saveErrors(request, errors);
		///	setContentPage(session, "teacher/TimeOffInputBatch.jsp");
		//	return mapping.findForward("Main");
		//}
	}
	
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm dynForm = (DynaActionForm)form;
		String mode = dynForm.getString("mode");
		
		session.removeAttribute("SAFTimeOffInEditB");
		setContentPage(session, "studaffair/TimeOffNotUploadQuery.jsp");
		return mapping.findForward("Main");
	}

	
	private boolean isValidDate(String vYear, String vMonth, String vDay) {
		if (vYear.equals("") || vMonth.equals("") || vDay.equals("")) {
			return false;
		} else if (!(vYear.equals("") || vMonth.equals("") || vDay.equals(""))) {

			int itfYear = Integer.parseInt(vYear.trim()) + 1911;
			int itfMonth = Integer.parseInt(vMonth.trim()) - 1;
			int itfDay = Integer.parseInt(vDay.trim());

			Calendar tfdate = Calendar.getInstance();
			tfdate.clear();
			tfdate.set(itfYear,itfMonth,itfDay, 0, 0, 0);
			
			/*log.debug(tfdate.getTime());
			log.debug(tfdate.get(Calendar.YEAR) + "/" +tfdate.get(Calendar.MONTH)+ "/"+"/" +
					tfdate.get(Calendar.DAY_OF_MONTH) + ", " +itfYear + "/" +
					itfMonth +"/"+itfDay);
			*/
			if (tfdate.get(Calendar.YEAR) != itfYear
				|| tfdate.get(Calendar.MONTH) != itfMonth
				|| tfdate.get(Calendar.DAY_OF_MONTH) != itfDay) {

				return false;
			} else 
				return true;
		}
		return false;
	}
	
	/*
	private ActionMessages validateInput(DynaActionForm form) {
		String[] abs0 = form.getStrings("st0");
		String[] abs1 = form.getStrings("st1");
		String[] abs2 = form.getStrings("st2");
		String[] abs3 = form.getStrings("st3");
		String[] abs4 = form.getStrings("st4");
		String[] abs5 = form.getStrings("st5");
		String[] abs6 = form.getStrings("st6");
		String[] abs7 = form.getStrings("st7");
		String[] abs8 = form.getStrings("st8");
		String[] abs9 = form.getStrings("st9");
		String[] abs10 = form.getStrings("st10");
		String[] abs11 = form.getStrings("st11");
		String[] abs12 = form.getStrings("st12");
		String[] abs13 = form.getStrings("st13");
		String[] abs14 = form.getStrings("st14");
		String[] abs15 = form.getStrings("st15");
		int num;
		
		List absList = new ArrayList();
		absList.add(abs0);
		absList.add(abs1);
		absList.add(abs2);
		absList.add(abs3);
		absList.add(abs4);
		absList.add(abs5);
		absList.add(abs6);
		absList.add(abs7);
		absList.add(abs8);
		absList.add(abs9);
		absList.add(abs10);
		absList.add(abs11);
		absList.add(abs12);
		absList.add(abs13);
		absList.add(abs14);
		absList.add(abs15);
		
		ActionMessages messages = new ActionMessages();
		
		for(Iterator<String[]> absIter = absList.iterator(); absIter.hasNext();) {
			String[] abs = absIter.next();
			if(abs.length > 0) {
				for(int k =0; k < abs.length; k++) {
					if(StringUtils.isNumeric(abs[k]) && !abs[k].trim().equals("")) {
						num = Integer.parseInt(abs[k]);
						if(num<0 || num > 8) {
							messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("Message.NumberOutOfRange","1-8"));
							break;
						}
					}
						
				}
				if(!messages.isEmpty()) return messages;
			}
			
		}

		return messages;
	}
	*/
	private ActionMessages validateInput(DynaActionForm form, List classBookList) {
		String[] abs0 = form.getStrings("st0");
		String[] abs1 = form.getStrings("st1");
		String[] abs2 = form.getStrings("st2");
		String[] abs3 = form.getStrings("st3");
		String[] abs4 = form.getStrings("st4");
		String[] abs5 = form.getStrings("st5");
		String[] abs6 = form.getStrings("st6");
		String[] abs7 = form.getStrings("st7");
		String[] abs8 = form.getStrings("st8");
		String[] abs9 = form.getStrings("st9");
		String[] abs10 = form.getStrings("st10");
		String[] abs11 = form.getStrings("st11");
		String[] abs12 = form.getStrings("st12");
		String[] abs13 = form.getStrings("st13");
		String[] abs14 = form.getStrings("st14");
		String[] abs15 = form.getStrings("st15");
		String[][] abs = {abs0,abs1,abs2,abs3,abs4,abs5,abs6,abs7,
				abs8,abs9,abs10,abs11,abs12,abs13,abs14,abs15};
		String str = "";
		
		int i, num, begin, end, count;
		ActionMessages msg = new ActionMessages();
		
		Map cbMap = new HashMap();
		//cbMap = (Map)classBookList.get(0);
		List baseClassBook = new ArrayList();
		List<Map> classBookAll = new ArrayList();
		
		//log.debug("StudTimeOffInputBatch.validate->classBookList.size():" + classBookList.size());
		for(Iterator<Map> cbIter = classBookList.iterator(); cbIter.hasNext();) {
			cbMap = cbIter.next();
			String cscode = cbMap.get("cscode").toString();
			begin = Integer.parseInt(cbMap.get("begin").toString());
			end = Integer.parseInt(cbMap.get("end").toString());
			
			if(cscode.equals("") && begin < 0 && end < 0) {
				baseClassBook = (List)cbMap.get("classBook");
				classBookAll.addAll(baseClassBook);
				//log.debug("StudTimeOffInputBatch.validate->baseClassBook.size():" + baseClassBook.size());
			} else {
				List<Map> classBook = (List)cbMap.get("classBook");
				classBookAll.addAll(classBook);
				
				//log.debug("StudTimeOffInputBatch.validate->baseClassBook:classBook:" + baseClassBook.size() + ":" + classBook.size());
				//log.debug("StudTimeOffInputBatch.validate->begin:end:" + begin + ":" + end);

				for(i = begin; i < end + 1; i++) {
					//log.debug("StudTimeOffInputBatch.validate->abs" + i + ":" + abs[i].length);
					count = 0;
					for(Iterator cbaIter = classBookAll.iterator(); cbaIter.hasNext();) {
						Map cbaMap = (Map)cbaIter.next();
						str = abs[i][count];
						if(str.equals("x") || str.equals("e") || str.equals("n") || str.equals("w")){
							if(!cbaMap.get("status" + i).toString().equals(str)) {
								//log.debug("StudTimeOffInputBatch.validate->abs[" + i + "][" + count + "]:" + str + "," + cbaMap.get("status" + i).toString());
								msg.add(ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage("Message.ClassBookSaveFormErr"));
								return msg;
							}
						} else if(StringUtils.isNumeric(str) && !str.trim().equals("")){
							num = Integer.parseInt(str);
							if(num<0 || num > 8) {
								msg.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("Message.NumberOutOfRange","1-8"));
								return msg;
							}
						}
						count++;
					}
					
				}
			}
			
		}
		return msg;
	}
	
	private ActionMessages fillFormProperty(DynaActionForm form, List classBookList) {
		String[] abs0 = form.getStrings("st0");
		String[] abs1 = form.getStrings("st1");
		String[] abs2 = form.getStrings("st2");
		String[] abs3 = form.getStrings("st3");
		String[] abs4 = form.getStrings("st4");
		String[] abs5 = form.getStrings("st5");
		String[] abs6 = form.getStrings("st6");
		String[] abs7 = form.getStrings("st7");
		String[] abs8 = form.getStrings("st8");
		String[] abs9 = form.getStrings("st9");
		String[] abs10 = form.getStrings("st10");
		String[] abs11 = form.getStrings("st11");
		String[] abs12 = form.getStrings("st12");
		String[] abs13 = form.getStrings("st13");
		String[] abs14 = form.getStrings("st14");
		String[] abs15 = form.getStrings("st15");
		String[][] abs = {abs0,abs1,abs2,abs3,abs4,abs5,abs6,abs7,
				abs8,abs9,abs10,abs11,abs12,abs13,abs14,abs15};
		String str = "";
		
		int i, num, begin, end, count;
		ActionMessages msg = new ActionMessages();
		
		Map cbMap = new HashMap();
		//cbMap = (Map)classBookList.get(0);
		List baseClassBook = new ArrayList();
		List<Map> classBookAll = new ArrayList();
		
		//log.debug("StudTimeOffInputBatch.fillForm->classBookList.size():" + classBookList.size());
		for(Iterator<Map> cbIter = classBookList.iterator(); cbIter.hasNext();) {
			cbMap = cbIter.next();
			String cscode = cbMap.get("cscode").toString();
			begin = Integer.parseInt(cbMap.get("begin").toString());
			end = Integer.parseInt(cbMap.get("end").toString());
			
			if(cscode.equals("") && begin < 0 && end < 0) {
				baseClassBook = (List)cbMap.get("classBook");
				classBookAll.addAll(baseClassBook);
				//log.debug("StudTimeOffInputBatch.fillForm->baseClassBook.size():" + baseClassBook.size());
			}
			else {
				List<Map> classBook = (List)cbMap.get("classBook");
				classBookAll.addAll(classBook);
				
				//log.debug("StudTimeOffInputBatch.fillForm->baseClassBook:classBook:" + baseClassBook.size() + ":" + classBook.size());
				//log.debug("StudTimeOffInputBatch.fillForm->begin:end:" + begin + ":" + end);

				for(i = begin; i < end + 1; i++) {
					//log.debug("StudTimeOffInputBatch.fillForm->abs" + i + ":" + abs[i].length);
					count = 0;
					for(Iterator cbaIter = classBookAll.iterator(); cbaIter.hasNext();) {
						Map cbaMap = (Map)cbaIter.next();
						str = abs[i][count].trim();
						if(cbaMap.get("isDataChg") == null) {
							cbaMap.put("isDataChg", "N");
						}
							
						if(!str.equals(cbaMap.get("status" + i).toString())) {
							//log.debug("StudTimeOffInputBatch.fillForm->StatusChange");
							cbaMap.put("status" + i, str);
							cbaMap.put("isDataChg", "Y");
						}							
						count++;
					}
					
				}
			}
			
		}
		return msg;
	}
	
	private int chkIsTeaching(String clazz, int weekd, int period){
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
		Calendar cal = Calendar.getInstance();
		Calendar begin = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		
		Time[] dtStamp = sm.getDtimeStamp(clazz, weekd, period);
		if(dtStamp[0] == null || dtStamp[1] == null){
			return 1;
		}
		String[] times = dtStamp[0].toString().split(":");
		begin.set(Calendar.HOUR_OF_DAY, Integer.parseInt(times[0]));
		begin.set(Calendar.MINUTE, Integer.parseInt(times[1]));
		begin.set(Calendar.SECOND, Integer.parseInt(times[2]));
		
		times = dtStamp[1].toString().split(":");
		end.set(Calendar.HOUR_OF_DAY, Integer.parseInt(times[0]));
		end.set(Calendar.MINUTE, Integer.parseInt(times[1]));
		end.set(Calendar.SECOND, Integer.parseInt(times[2]));
		
		if(cal.compareTo(begin) >= 0 && cal.compareTo(end) <= 0){
			return 0;
		}else if(cal.compareTo(begin) < 0){
			return -1;
		}
		return 1;
	}
}
