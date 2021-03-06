package tw.edu.chit.struts.action.teacher;

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
import tw.edu.chit.model.DtimeStudaffair;
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
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;
import tw.edu.chit.util.Global;

public class TimeOffInputBatchAction  extends BaseLookupDispatchAction {

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
		map.put("OK", "query");
		map.put("StartUpload", "save");
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
		
		List<Code5> tfTypeList = Global.TimeOffList;
		
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String idno = credential.getMember().getIdno().trim();
		
		Map TimeoffInit = new HashMap();
		String mode = dynForm.getString("mode");
		//log.debug("mode=" + mode);
		TimeoffInit.put("mode", mode);

		//這一段只是用來當老師未點名查詢完成後，點選該課程時，可重導至點名輸入頁面：Mode Redirect: 老師未上傳紀錄重導
		if(mode.trim().equalsIgnoreCase("redirect")){
			boolean isForward = false;
			String dtOids = dynForm.getString("dtOid");
			String tdate = dynForm.getString("tdate");
			Calendar t1Cal = Calendar.getInstance();
			t1Cal.setTime(Toolket.parseFullDate(tdate));
			
			Calendar t2Cal = Calendar.getInstance();
			t2Cal.setTime(t1Cal.getTime());
			t2Cal.add(Calendar.DATE, 8);
			t2Cal.set(Calendar.HOUR_OF_DAY, 23);
			t2Cal.set(Calendar.MINUTE, 59);
			t2Cal.set(Calendar.SECOND, 59);
			t2Cal.set(Calendar.MILLISECOND, 999);
			
			Calendar lCal = Calendar.getInstance();

			if(lCal.compareTo(t2Cal) < 0 && lCal.compareTo(t1Cal) >= 0){
				int tweek = t1Cal.get(Calendar.DAY_OF_WEEK);
				if (tweek == 1)
					tweek = 7;
				else
					tweek--;
				
				List<Dtimes> dtimeslist = new ArrayList();
				if(Toolket.isNumeric(dtOids)){
					dtimeslist = sm.findDtimesByDtimeOid(Integer.parseInt(dtOids));
					for (Dtimes dts:dtimeslist) {
						Dtime dtime = dts.getDtime();
						DtimeClass[] periods = dts.getPeriods();
						for(DtimeClass dc:periods){
							if(dc.getWeek() == tweek){
								isForward = true;
								break;
							}
						}
					}
				}
				
				if(isForward){
					initMap.put("dtOid", dtOids);
					initMap.put("tdate", dynForm.getString("tdate"));
					initMap.put("mode", mode);
					session.setAttribute("TimeOffInputInfo", initMap);
					
					session.removeAttribute("TimeOffInEditB");
					session.removeAttribute("TimeOffFormMapB");

					setContentPage(session, "teacher/TimeOffInputBatch.jsp");
					return mapping.findForward("Main");
				}else{
					mode = "normal";
				}
			}else{
				mode = "normal";
			}
		}
		
		List<Dtimes> dtimelist = new ArrayList();
		Map dtimeMap = new HashMap();
		
		List cscodelist = new ArrayList();
		List dtimes = new ArrayList();
		
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

		TimeoffInit.put("tfWeek", ("" + iweek));

		Dtime nearDtime = null;
		Dtime electDtime = null;
		Calendar[] thisweek = new Calendar[8];	//8天內的日期資料
		for(int i = 0; i < 8; i++){
			thisweek[i] = Calendar.getInstance();
			thisweek[i].set(Calendar.HOUR_OF_DAY, 0);
			thisweek[i].set(Calendar.MINUTE, 0);
			thisweek[i].set(Calendar.SECOND, 0);
			//thisweek[i] = (Calendar)tfdate.clone();
			thisweek[i].add(Calendar.DAY_OF_MONTH, -i);
		}
		final String[] weekp = {"(一)","(二)","(三)","(四)","(五)","(六)","(日)"}; 
		dtimelist = sm.findTeachSubjectByTeacherIdno(credential);
		if (dtimelist.size() > 0) {
			String sterm = Toolket.getSysParameter(IConstants.PARAMETER_SCHOOL_TERM);
			StringBuffer dateArray = new StringBuffer();
			StringBuffer weekArray = new StringBuffer();
			String clazz = "";
			dateArray.append("new Array(");
			weekArray.append("new Array(");
			int weekd = 0, sameWeek = 0, offset = 0, begin = 0, end = 0;
			boolean foundNear = false;
			int foundNearResult = 99;
			boolean isHoliday = false;
			boolean isAppend = false;
			
			for (Dtimes dts:dtimelist) {
				Dtime dtime = dts.getDtime();
				clazz = dtime.getDepartClass();
				Calendar termEndCal = null;
				if(Toolket.chkIsGraduateClass(clazz) && sterm.equals("2")){
					termEndCal = Toolket.getDateOfWeek(clazz.substring(0, 2), 13)[1];
				}else{
					//2009.08.27 學務申請單要求更改只上傳至16週
					//TODO:check !
					termEndCal = Toolket.getDateOfWeek(clazz.substring(0, 2), Toolket.getTimeOffUploadDeadline())[1];
				}
				//Calendar termEndCal = Toolket.getDateOfWeek(clazz.substring(0, 2), 17)[1];
				Calendar termStartCal = Toolket.getDateOfWeek(clazz.substring(0, 2), 1)[0];
				DtimeClass[] periods = dts.getPeriods();
				sameWeek = 0;
				isHoliday = true;
				isAppend = false;
				
				List<DtimeStudaffair>  dtSAFs = sm.getDtimeStudaffair(dtime.getOid());
				DtimeStudaffair ds = new DtimeStudaffair();
				Calendar tCal = Calendar.getInstance();
				int tweek = tCal.get(Calendar.DAY_OF_WEEK);
				boolean isRollCall = true;
				
				for(DtimeClass dtc:periods){
					weekd = dtc.getWeek();
					begin = Integer.parseInt(dtc.getBegin());
					end = Integer.parseInt(dtc.getEnd());
					begin = begin>10 ? begin-10 : begin;
					end = end>10 ? end-10 : end;
					offset = ((iweek - weekd)>0) ?(iweek - weekd):(iweek - weekd + 7);
					
					isRollCall = true;
					if(weekd == iweek && !foundNear && !Toolket.isHoliday(thisweek[0]) && 
							thisweek[0].compareTo(termEndCal)<=0 
							&& thisweek[0].compareTo(termStartCal)>=0){	//今日課程
						//設定目前正在上的課程
						
						//排除不點名的上課日期
						if(!dtSAFs.isEmpty()){
							for(ListIterator<DtimeStudaffair> dsIter=dtSAFs.listIterator(); dsIter.hasNext();){
								ds = dsIter.next();
								tCal.setTime(ds.getTdate());
								if(Toolket.isSameDay(tCal, thisweek[0])){
									isRollCall = false;
									break;
								}
							}
						}

						if(isRollCall){
							for(int k=begin; k<=end; k++){
								foundNearResult = this.chkIsTeaching(clazz, weekd, k);
								if( foundNearResult== 0){
									nearDtime = dtime;
									foundNear = true;
								}else if(foundNearResult < 0 && !foundNear){
									if (nearDtime == null){
										nearDtime = dtime;
									}
									//foundNear = true;
								}
							}
							electDtime = dtime;
						}
					}
					
					isRollCall = true;
					if(sameWeek != weekd && !mode.equalsIgnoreCase("onLine") && 
							((thisweek[offset].compareTo(termEndCal)<=0 && 
							thisweek[offset].compareTo(termStartCal)>=0 &&
							!Toolket.isHoliday(thisweek[offset]))||
							(weekd == iweek && thisweek[0].compareTo(termEndCal)<=0 && 
							thisweek[0].compareTo(termStartCal)>=0 &&
							!Toolket.isHoliday(thisweek[0])))){
						
						if(weekd == iweek && !Toolket.isHoliday(thisweek[0]) && 
								thisweek[0].compareTo(termEndCal)<=0 && 
								thisweek[0].compareTo(termStartCal)>=0){
							
							//排除不點名的上課日期
							if(!dtSAFs.isEmpty()){
								for(ListIterator<DtimeStudaffair> dsIter=dtSAFs.listIterator(); dsIter.hasNext();){
									ds = dsIter.next();
									tCal.setTime(ds.getTdate());
									if(Toolket.isSameDay(tCal, thisweek[0])){
										isRollCall = false;
										break;
									}
								}
							}

							if(isRollCall){
								if(!isAppend){
									dateArray.append("new Array(");
									weekArray.append("new Array(");
								}
								//多加入一個今天的課程
								dateArray.append("\"").append(Toolket.FullDate2Str(thisweek[0].getTime())).append("\",");
								weekArray.append("\"").append(weekp[weekd-1]).append("\",");
								isHoliday = false;
								isAppend = true;
								sameWeek = weekd;
							}

						}
						
						isRollCall = true;
						offset = ((iweek - weekd)>0) ?(iweek - weekd):(iweek - weekd + 7);
						if(!Toolket.isHoliday(thisweek[offset]) && 
								thisweek[offset].compareTo(termEndCal)<=0 && 
								thisweek[offset].compareTo(termStartCal)>=0){
							
							//排除不點名的上課日期
							if(!dtSAFs.isEmpty()){
								for(ListIterator<DtimeStudaffair> dsIter=dtSAFs.listIterator(); dsIter.hasNext();){
									ds = dsIter.next();
									tCal.setTime(ds.getTdate());
									if(Toolket.isSameDay(tCal, thisweek[offset])){
										isRollCall = false;
										break;
									}
								}
							}
							
							if(isRollCall){
								if(!isAppend){
									dateArray.append("new Array(");
									weekArray.append("new Array(");
								}
								isHoliday = false;
								dateArray.append("\"").append(Toolket.FullDate2Str(thisweek[offset].getTime())).append("\",");
								weekArray.append("\"").append(weekp[weekd-1]).append("\",");
								isAppend = true;
								sameWeek = weekd;
							}

						}
					    /*
					    if (dateArray.charAt(dateArray.length()-1) == ',') {
					    	dateArray.setLength(dateArray.length()-1);
					    	weekArray.setLength(weekArray.length()-1);
					    }
					    if(!isAppend){
						    dateArray.append("),");
						    weekArray.append("),");
					    }
					    */
						
					}
					
					isRollCall = true;
					if(weekd == iweek && sameWeek != weekd && mode.equalsIgnoreCase("onLine")
							&& !Toolket.isHoliday(thisweek[0]) && 
							thisweek[0].compareTo(termEndCal)<=0 &&
							thisweek[0].compareTo(termStartCal)>=0){
						//排除不點名的上課日期
						if(!dtSAFs.isEmpty()){
							for(ListIterator<DtimeStudaffair> dsIter=dtSAFs.listIterator(); dsIter.hasNext();){
								ds = dsIter.next();
								tCal.setTime(ds.getTdate());
								if(Toolket.isSameDay(tCal, thisweek[0])){
									isRollCall = false;
									break;
								}
							}
						}
						if(isRollCall){
							//將本課程加入今日課程
							dateArray.append("new Array(");
							weekArray.append("new Array(");
							dateArray.append("\"").append(Toolket.FullDate2Str(thisweek[0].getTime())).append("\",");
							weekArray.append("\"").append(weekp[weekd-1]).append("\",");
						    if (dateArray.charAt(dateArray.length()-1) == ',') {
						    	dateArray.setLength(dateArray.length()-1);
						    	weekArray.setLength(weekArray.length()-1);
						    }
						    dateArray.append("),");
						    weekArray.append("),");
							Map cmap = new HashMap();
							cmap.put("dtimeOid", dtime.getOid());
							cmap.put("cscode", dtime.getCscode());
							cmap.put("className2", dtime.getClassName2());
							cmap.put("chiName2", dtime.getChiName2());
							cscodelist.add(cmap);
							sameWeek = weekd;
						}
					}
				}
			    
			    if(!mode.equalsIgnoreCase("onLine") && !isHoliday && isAppend){
				    if (dateArray.charAt(dateArray.length()-1) == ',') {
				    	dateArray.setLength(dateArray.length()-1);
				    	weekArray.setLength(weekArray.length()-1);
				    }
					dateArray.append("),");
					weekArray.append("),");
					Map cmap = new HashMap();
					cmap.put("dtimeOid", dtime.getOid());
					cmap.put("cscode", dtime.getCscode());
					cmap.put("className2", dtime.getClassName2());
					cmap.put("chiName2", dtime.getChiName2());
					cscodelist.add(cmap);
			    }

			}
		    if (dateArray.charAt(dateArray.length()-1) == ',') {
		    	dateArray.setLength(dateArray.length()-1);
		    	weekArray.setLength(weekArray.length()-1);
		    }
		    dateArray.append(")");
		    weekArray.append(")");
		    Map periodMap = new HashMap();
		    periodMap.put("tdate", dateArray.toString());
		    periodMap.put("tweek", weekArray.toString());
		    
			session.setAttribute("TimeOffPeriod", periodMap);
			session.setAttribute("TeachClasses", cscodelist);
			
			// log.debug("Get DtimeList prop.==> dtimes.Oid=" +
			// (((Dtime)dtimelist.get(0)).getOid()));
		}
		
		initMap.put("teacherName", credential.getMember().getName());
		if(mode.equalsIgnoreCase("onLine")){
			if(nearDtime != null){
				initMap.put("dtOid", nearDtime.getOid());
				initMap.put("tdate", Toolket.FullDate2Str(tfdate.getTime()));
			}else{
				if(electDtime != null){
					initMap.put("dtOid", electDtime.getOid());
					initMap.put("tdate", Toolket.FullDate2Str(tfdate.getTime()));				
				}else{
					initMap.put("dtOid", null);
					initMap.put("tdate", null);
				}
			}
		}else{
			initMap.put("dtOid", null);
			initMap.put("tdate", null);
		}
		initMap.put("mode", mode);
		session.setAttribute("TimeOffInputInfo", initMap);
		
		/*
		if(mode.equalsIgnoreCase("onLine")) {	// 線上點名,取得目前時間的下一節課程點名簿資料
			DynaActionForm dForm = new DynaActionForm();
			dForm.set("mode", "onLine");
			dForm.set("dtOid", nearDtime.getOid());
			dForm.set("tdate", df.format(tfdate.getTime()));
			
			return query(mapping, dForm, request, response);
		}
		*/
		session.removeAttribute("TimeOffInEditB");
		session.removeAttribute("TimeOffFormMapB");

		setContentPage(session, "teacher/TimeOffInputBatch.jsp");
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
		Map initMap = (Map)session.getAttribute("TimeOffInputInfo");

		String mode = form.getString("mode");
		String dtOid = form.getString("dtOid");
		String tdate = form.getString("tdate");
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
				ActionMessages errs = sm.createOrUpdateTimeOffUpload(credential.getMember().getIdno(), Integer.parseInt(dtOid), tdate, "");
				if(!errs.isEmpty()){
					messages.add(errs);
				}
				
				ActionMessages err1 = sm.updateSeldDilgPeriodBatch(sddate, chgList);
				messages.add(err1);
				ActionMessages err2 = sm.updateJustDilgScoreBatch(chgList);
				
				/*/=======================================>>>>>>>>>>>>>>>>>>
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
				*///System.out.println("Leo_OK");
				//}
				//=======================================================>>>>>>>>>>>>>>>>>>
				
				messages.add(err2);
			}

			if (messages.isEmpty()) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.CreateSuccessful"));
				initMap.put("dtOid", null);
			}
			session.setAttribute("TimeOffInputInfo", initMap);
			saveMessages(request, messages);
			setContentPage(session, "teacher/TimeOffInputBatch.jsp");
			if(mode.trim().equalsIgnoreCase("redirect")){
				return mapping.findForward("redirectForward");
			}
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
	
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);

		Map StudTimeoffInit = (Map)session.getAttribute("StudTimeoffInitB");
		DynaActionForm dynForm = (DynaActionForm)form;
		
		String tfYear = dynForm.getString("tfYear");
		String tfMonth = dynForm.getString("tfMonth");
		String tfDay = dynForm.getString("tfDay");
		
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String classInCharge = credential.getClassInChargeSqlFilterSAF();

		String campus = dynForm.getString("campusInChargeSAF").trim();
		String school = dynForm.getString("schoolInChargeSAF").trim();
		String dept = dynForm.getString("deptInChargeSAF").trim();
		String myclass = dynForm.getString("classInChargeSAF").trim();
		String cscode = dynForm.getString("cscode").trim();
		String mode = dynForm.getString("mode");
		
		String daynite;
		
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		ScoreManager scm = (ScoreManager)getBean("scoreManager");
		
		
		StudTimeoffInit.put("campus", campus);
		StudTimeoffInit.put("school", school);
		StudTimeoffInit.put("dept", dept);
		StudTimeoffInit.put("clazz", myclass);
		StudTimeoffInit.put("tfYear", tfYear);
		StudTimeoffInit.put("tfMonth", tfMonth);
		StudTimeoffInit.put("tfDay", tfDay);

		StudTimeoffInit.put("departClass", myclass);
		StudTimeoffInit.put("depClassName", Toolket.getClassFullName(myclass));
		if(!cscode.equals("")) {
			StudTimeoffInit.put("cscode", cscode);
			StudTimeoffInit.put("cscodeName",scm.findCourseName(cscode));
		}
		
		List<Student> studlist = sm.findStudentsByClass(myclass);
		log.debug("TimeOffBatch->studentList.size():" + studlist.size());
		// List subjList = (List<Dtime>)session.getAttribute("StudTimeoffInfoSubject");
		List baseClassBook = new ArrayList();
		List classBookList = new ArrayList();
		List subjList = new ArrayList();
		List dtimelist = new ArrayList();
		List subjects = new ArrayList(); 	//存放所有課程之資訊
		List subjectsInUse = new ArrayList(); 	//存放classBook課程之資訊
		
		Dtime subject;
		int dtime_begin = 0;
		int dtime_end = 0;
		String dtime_chiName = "";
		String sddate = "" + (Integer.parseInt(tfYear) + 1911) + "-" + tfMonth + "-" + tfDay;


		if(!tfYear.equals("") && !tfMonth.equals("") && !tfDay.equals("") && 
				isValidDate(tfYear, tfMonth, tfDay)) {
			//String ddate = tfYear + "-" + tfMonth + "-" + tfDay;
			
			Calendar tfdate = Calendar.getInstance();
			tfdate.clear();

			int itfYear = Integer.parseInt(tfYear) + 1911;
			int itfMonth = Integer.parseInt(tfMonth) - 1;
			int itfDay = Integer.parseInt(tfDay);
			tfdate.set(itfYear,itfMonth,itfDay, 0, 0, 0);
			
			//1:SUNDAY, 2:MONDAY,...
			int iweek = tfdate.get(Calendar.DAY_OF_WEEK);
			if(iweek == 1) iweek = 7;
			else iweek--;
			StudTimeoffInit.put("tfWeek", ("" + iweek));

			//log.debug("tfweek:" + iweek);
			if(mode.equals("ByClass")){
				subjList = sm.findCscodeByClassAtDate(myclass, (""+iweek), "");
				Object[] dtObj = new Object[subjList.size()];
				
				for (Iterator subjIter = subjList.iterator(); subjIter.hasNext();) {
					dtObj = (Object[])subjIter.next();
					subject = (Dtime)dtObj[0];
					dtime_begin = Integer.parseInt(dtObj[1].toString());
					dtime_end = Integer.parseInt(dtObj[2].toString());
					dtime_chiName = dtObj[3].toString();
					
					//log.debug("StudTimeOffSubject->" + subject.getOid() + ":" + cscode + ":" + dtime_chiName + ":" + dtime_begin + ":" + dtime_end);
					Map subjMap = new HashMap();
					
					subjMap.put("cscode", cscode);
					subjMap.put("cscodeName", dtime_chiName);
					subjMap.put("begin", dtime_begin);
					subjMap.put("end", dtime_end);
					subjects.add(subjMap);
				}
				session.setAttribute("StudTimeOffSubject", subjects);
				
				subjList = sm.findCscodeByClassAtDate(myclass, (""+iweek), mode);
				for (Iterator subjIter = subjList.iterator(); subjIter.hasNext();) {
					dtObj = (Object[])subjIter.next();
					subject = (Dtime)dtObj[0];
					dtime_begin = Integer.parseInt(dtObj[1].toString());
					dtime_end = Integer.parseInt(dtObj[2].toString());
					dtime_chiName = dtObj[3].toString();
					
					//log.debug("StudTimeOffSubject->" + subject.getOid() + ":" + cscode + ":" + dtime_chiName + ":" + dtime_begin + ":" + dtime_end);
					Map subjMap = new HashMap();
					
					subjMap.put("cscode", cscode);
					subjMap.put("cscodeName", dtime_chiName);
					subjMap.put("begin", dtime_begin);
					subjMap.put("end", dtime_end);
					subjectsInUse.add(subjMap);
				}
				session.setAttribute("StudTimeOffSubjInUse", subjectsInUse);
				
			}else if(mode.equals("BySubject")) {
				subjList = sm.findCscodeByClassAtDate(myclass, (""+iweek), mode);
				//確認該班在該時間有開這門課程
				boolean isSubjectInClass = false;
				
				Object[] dtObj = new Object[subjList.size()];
				for (Iterator subjIter = subjList.iterator(); subjIter.hasNext();) {
					dtObj = (Object[])subjIter.next();
					subject = (Dtime)dtObj[0];
					if(cscode.equals(subject.getCscode().trim())) {
						dtime_begin = Integer.parseInt(dtObj[1].toString());
						dtime_end = Integer.parseInt(dtObj[2].toString());
						dtime_chiName = dtObj[3].toString();

						Map subjMap = new HashMap();
						
						subjMap.put("cscode", cscode);
						subjMap.put("cscodeName", dtime_chiName);
						subjMap.put("begin", dtime_begin);
						subjMap.put("end", dtime_end);
						subjects.add(subjMap);
						isSubjectInClass = true;
					}
					session.setAttribute("StudTimeOffSubject", subjects);
					session.setAttribute("StudTimeOffSubjInUse", subjects);
				}

				if(!isSubjectInClass){
					ActionMessages msgs = new ActionMessages();
					msgs.add(ActionMessages.GLOBAL_MESSAGE, 
							new ActionMessage("Message.NoSubjectInClassOnDtae", tfYear + "/" + tfMonth + "/" + tfDay));
					saveMessages(request, msgs);

					setContentPage(session, "studaffair/StudTimeOffInputBatch.jsp");
					return mapping.findForward("Main");

				}
			}
			
		
		} else if(tfYear.equals("") || tfMonth.equals("") || tfDay.equals("") || !isValidDate(tfYear, tfMonth, tfDay)) {
				//日期輸入錯誤或輸入不完整
				ActionMessages msgs = new ActionMessages();
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.DateFormatErrOrNotInputYet"));
				saveMessages(request, msgs);
		} else if(myclass.equals("")){
				ActionMessages msgs = new ActionMessages();
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.PleaseSelect", "Message.DepartClass"));
				saveMessages(request, msgs);
				
		} else if(studlist.isEmpty()) {
			//該班無任何學生
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.NoStudentInClass"));
			saveMessages(request, msgs);
			
		}
		
			// 由班級代碼判斷部制為:日間部,進修部(夜間),進修學院專校
		char dyflag = myclass.charAt(1);

		switch (dyflag) {
		case '1':
		case '4':
		case '6':
		case 'A':
		case 'C':
		case 'F':
		case 'a':
		case 'c':
		case 'f':
			daynite = "1";
			break;
		case '2':
		case '5':
		case '8':
		case '9':
		case 'B':
		case 'D':
		case 'H':
		case 'b':
		case 'd':
		case 'h':
			daynite = "2";
			break;
		case '3':
		case '7':
			daynite = "3";
			break;
		default:
			daynite = "1";
		}

		//log.debug("daynite=" + daynite);
		StudTimeoffInit.put("daynite", daynite);
		Object[] dtimeObj = new Object[subjList.size()];
		
		if (mode.equals("BySubject")) {
			if (subjList.size() > 0) {
				int[] period = new int[16];
				for(int j=0; j<16; j++) period[j]=0;
				
				int period_begin = 16;
				int period_end = 16;
				
				/*
				for(Iterator subjIter = subjList.iterator(); subjIter.hasNext();){
					dtimeObj = (Object[])subjIter.next();
					subject = (Dtime)dtimeObj[0];
					dtime_begin = Integer.parseInt(dtimeObj[1].toString());
					dtime_end = Integer.parseInt(dtimeObj[2].toString());
					if (cscode.equals(subject.getCscode().toString())
							&& myclass.equals(subject.getDepartClass()
									.toString())) {

						if(period_begin ==16) period_begin = dtime_begin;
						for(int i=dtime_begin; i<=dtime_end; i++){
							period[i] = 1;
						}
					}
				}
				period_end = dtime_end;
				*/
				
				//同一天同一門選修課可能分散在不同節次上課,
				//所以先統計一下該科目的第一及最後一節的上課區間
				for(Iterator subjIter = subjects.iterator(); subjIter.hasNext();){
					Map subjMap = (Map)subjIter.next();
					
					dtime_begin = Integer.parseInt(subjMap.get("begin").toString());
					dtime_end = Integer.parseInt(subjMap.get("end").toString());
					
					if(period_begin ==16) period_begin = dtime_begin;
					for(int i=dtime_begin; i<=dtime_end; i++){
						period[i] = 1;
					}
				}
				period_end = dtime_end;
				
				//處理點名簿資訊,無論該科目同一天分散在幾節上課,都只會執行for loop一次
				//
				for(Iterator subjIter = subjList.iterator(); subjIter.hasNext();) {
					Map classBookMap = new HashMap();	//存放classbook點名簿資訊
					List classBook = new ArrayList();
					//dtimeObj = (Object[])subjList.get(0);
					dtimeObj = (Object[])subjIter.next();
					subject = (Dtime)dtimeObj[0];
					dtime_begin = Integer.parseInt(dtimeObj[1].toString());
					dtime_end = Integer.parseInt(dtimeObj[2].toString());
					dtime_chiName = dtimeObj[3].toString();
	
					if (cscode.equals(subject.getCscode().toString())
							&& myclass.equals(subject.getDepartClass()
									.toString())) {
	
						Map subjMap = new HashMap();
	
						List<Seld> seldList = sm.findSeldForClassBook(subject
								.getOid(), subject.getDepartClass(), 0);
						
	
						for (Iterator<Seld> seldIter = seldList.iterator(); seldIter
								.hasNext();) {
							Seld seld = seldIter.next();
							Map stuMap = new HashMap();
	
							stuMap.put("studentNo", seld.getStudentNo());
							stuMap.put("studentName", seld.getStudentName());
							for(int k=0; k<16; k++) {
								if(period[k]==1){
									stuMap.put("status" + k , "");
								}else if(period[k]==0){
									stuMap.put("status" + k , "x");
								}
							}
								
							classBook.add(stuMap);
	
						}
						/*
						subjMap.put("cscode", cscode);
						subjMap.put("cscodeName", dtime_chiName);
						subjMap.put("begin", dtime_begin);
						subjMap.put("end", dtime_end);
						subjects.add(subjMap);
						*/
						List<Map> Dilg4ClassBook = sm.findDilg4ClassBook(classBook, sddate, period_begin, period_end);
						//log.debug("Dilg4ClassBook.size()->" + Dilg4ClassBook.size());
						
						String studNo;
						for (Iterator cbIter = classBook.iterator(); cbIter
							.hasNext();) {
							Map cbMap = (Map) cbIter.next();
							studNo = cbMap.get("studentNo").toString();
	
							if(!Dilg4ClassBook.isEmpty()) {
								for(ListIterator<Map> dilg4cbIter = Dilg4ClassBook.listIterator(); dilg4cbIter.hasNext();) {
									//if(dilg4cbIter.nextIndex() < dIndex)
									//	continue;
									Map d4cbMap = dilg4cbIter.next();
									if(studNo.equals(d4cbMap.get("studentNo").toString())) {
										cbMap.put("status" + d4cbMap.get("period").toString(), 
												d4cbMap.get("status").toString());
										//	dIndex = dilg4cbIter.nextIndex();
									}
								}
							} 
						}
						
						classBookMap.put("cscode", subject.getOid());
						classBookMap.put("cscodeName", "");
						classBookMap.put("begin", period_begin);
						classBookMap.put("end", period_end);
						classBookMap.put("classBook", classBook);
						classBookMap.put("classBookInSubject", classBook);
						classBookList.add(classBookMap);
						break;
					}
					
				}
				//log.debug("StudTimeOffBatch.Bysubject.classBookList.size()=>" + classBookList.size());
				
			} else {
				// 該班今日無任何課程
				ActionMessages msgs = new ActionMessages();
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.NoSubjectInClassOnDtae"));
				saveMessages(request, msgs);
			}

		} else if (mode.equals("ByClass")) {
			//將本班所有學生加入baseClassBook
			for (Iterator<Student> stuIter = studlist.iterator(); stuIter
					.hasNext();) {
				Student student = stuIter.next();
				Map stuMap = new HashMap();
				stuMap.put("studentNo", student.getStudentNo());
				stuMap.put("studentName", student.getStudentName());
				for(int kk=0; kk<16; kk++) {
					stuMap.put("status" + kk, "x");
				}

				baseClassBook.add(stuMap);
			}


			if (subjList.size() > 0) {
				//classBook[0]:該班所有學生 , classBook[1...]:其他科目跨選學生
				List[] classBook = new ArrayList[subjList.size()+ 1];
				for(int len=0; len < classBook.length; len++) {
					classBook[len] = new ArrayList();
				}
				//log.debug("TimeOffBatch->classBookArray.length()=" + classBook.length);
				for (Iterator bcbIter = baseClassBook.iterator(); bcbIter.hasNext();) {
					Map stuMap = (Map)bcbIter.next();
					classBook[0].add(stuMap);
				}
				
				int subjCount = 0;
				Map classBookMap = new HashMap();	//存放classbook點名簿資訊
				
				//將baseClassBook加入classBookList
				classBookMap.put("cscode", "");
				classBookMap.put("cscodeName", "");
				classBookMap.put("begin", -1);
				classBookMap.put("end", -1);
				classBookMap.put("classBook", null);
				classBookList.add(classBookMap);

				for (Iterator subjIter = subjList.iterator(); subjIter.hasNext();) {
					dtimeObj = (Object[])subjIter.next();
					subject = (Dtime)dtimeObj[0];
					dtime_begin = Integer.parseInt(dtimeObj[1].toString());
					dtime_end = Integer.parseInt(dtimeObj[2].toString());
					dtime_chiName = dtimeObj[3].toString();
					Map subjMap = new HashMap();
					classBookMap = new HashMap();	//存放classbook點名簿資訊

					subjCount++;

					/*
					//開放選修科目不納入本班點名簿,status="x"
					if(subject.getOpen() == 1) {
						subjMap.put("cscode", subject.getCscode());
						subjMap.put("classBook", classBook);
						classBookList.add(subjMap);

						continue;
					}
					*/
					
					// seldInClass :
					List<Seld> seldInClass = sm.findSeldForClassBook(subject
							.getOid(), subject.getDepartClass(), 1);
					//log.debug("seldInClass.size()->" + seldInClass.size());
					List<Seld> seldNotInClass = sm.findSeldForClassBook(subject
							.getOid(), subject.getDepartClass(), 2);
					//log.debug("seldNotInClass.size()->" + seldNotInClass.size());
					
					List<Map> Dilg4ClassBook = sm.findDilg4ClassBook(classBook[0], sddate, dtime_begin, dtime_end);
					//log.debug("Dilg4ClassBook0.size()->" + Dilg4ClassBook.size());
					/*
					List<Seld> seldAll = new ArrayList();
					seldAll.addAll(seldInClass);
					seldAll.addAll(seldNotInClass);
					session.setAttribute("StudTimeOffInEditBAll", seldAll);
					*/
					
					// 找出退選學生
					List<Adcd> adcdlist = sm.findAdcdForClassBook(subject
							.getOid(), "D");
					//log.debug("adcdlist.size()->" + adcdlist.size());

					//List classBook = new ArrayList();
					//classBook.addAll(baseClassBook);

					try {
					// 處理退選資料
					int kk = 0;
					int dIndex = 0;
					String studNo;
					boolean isFound = false;

					for (Iterator cbIter = classBook[0].iterator(); cbIter
							.hasNext();) {
						Map cbMap = (Map) cbIter.next();
						studNo = cbMap.get("studentNo").toString();
						
						for(kk=dtime_begin; kk<=dtime_end; kk++) {
							cbMap.put("status" + kk, "");
						}

						if(!Dilg4ClassBook.isEmpty()) {
							for(ListIterator<Map> dilg4cbIter = Dilg4ClassBook.listIterator(); dilg4cbIter.hasNext();) {
								//if(dilg4cbIter.nextIndex() < dIndex)
								//	continue;
								Map d4cbMap = dilg4cbIter.next();
								if(studNo.equals(d4cbMap.get("studentNo").toString())) {
									//log.debug("StudTimeOffInputBatch.Dilg->" + studNo + ":" + d4cbMap.get("clazz").toString() + ":" + d4cbMap.get("status").toString());
									cbMap.put("status" + d4cbMap.get("period").toString(), 
											d4cbMap.get("status").toString());
								//	dIndex = dilg4cbIter.nextIndex();
								}
							}
						} 
						if (adcdlist.size() > 0) {
							for (Iterator<Adcd> adcdIter = adcdlist.iterator(); adcdIter
									.hasNext();) {
								Adcd adcd = adcdIter.next();
								if (studNo.equals(adcd.getStudentNo())) {
									for(kk=dtime_begin; kk<=dtime_end; kk++) {
										cbMap.put("status" + kk, "w");
									}
								}
							}
									
						}
					}

					// 處理異常資料(例如該班學生未退選也不在Seld學生選課檔)
					// 
					if(!(subject.getCscode().equals("50000") || subject.getCscode().equals("T0001"))) {
						if ((adcdlist.size() + seldInClass.size()) < classBook[0].size()) {
							//log.debug("Adcd.size()->" + adcdlist.size());
							for (Iterator cbIter = classBook[0].iterator(); cbIter
									.hasNext();) {
								Map cbMap = (Map) cbIter.next();
								isFound = false;
								studNo = cbMap.get("studentNo").toString();
								int sIndex = 0;
	
								for (ListIterator<Seld> seldIter = seldInClass
										.listIterator(); seldIter.hasNext();) {
									if (seldIter.nextIndex() < sIndex)
										continue;
									Seld seld = seldIter.next();
									if (studNo.equals(seld.getStudentNo())) {
										isFound = true;
										// 記錄下一次搜尋的位址
										sIndex = seldIter.nextIndex();
										break;
									}
								}
								if (!isFound) {
									for(kk=dtime_begin; kk<=dtime_end; kk++) {
										cbMap.put("status" + kk, "e");
									}
								}
	
							}
						}
					}
					// 將非開課班之選課學生加入classBook中
					for (Iterator<Seld> seldIter = seldNotInClass.iterator(); seldIter
							.hasNext();) {
						Seld seld = seldIter.next();
						Map cbMap = new HashMap();

						cbMap.put("studentNo", seld.getStudentNo());
						cbMap.put("studentName", seld.getStudentName());
						
						for(int k = 0; k < dtime_begin; k++) {
							cbMap.put("status" + k , "x");
						}
						for(int k = dtime_begin; k < dtime_end + 1; k++) {
							cbMap.put("status" + k , "");
						}
						
						for(int k = dtime_end + 1; k < 16; k++) {
							cbMap.put("status" + k , "x");
						}

						classBook[subjCount].add(cbMap);

					}
					
					Dilg4ClassBook = sm.findDilg4ClassBook(classBook[subjCount], sddate, dtime_begin, dtime_end);
					//log.debug("Dilg4ClassBook.size()->" + Dilg4ClassBook.size());
					dIndex = 0;
					for (Iterator cbIter = classBook[subjCount].iterator(); cbIter.hasNext();) {
						Map cbMap = (Map) cbIter.next();
						studNo = cbMap.get("studentNo").toString();
						if(!Dilg4ClassBook.isEmpty()) {
							for(ListIterator<Map> dilg4cbIter = Dilg4ClassBook.listIterator(); dilg4cbIter.hasNext();) {
								//if(dilg4cbIter.nextIndex() < dIndex)
								//	continue;
								Map d4cbMap = dilg4cbIter.next();

								if(studNo.equals(d4cbMap.get("studentNo").toString())) {
									//log.debug("StudTimeOffInputBatch.Dilg->" + studNo + ":" + d4cbMap.get("clazz").toString() + ":" + d4cbMap.get("status").toString());

									cbMap.put("status" + d4cbMap.get("period").toString(), 
											d4cbMap.get("status").toString());
								//	dIndex = dilg4cbIter.nextIndex();
								}
							}
						}
					}
					
					// 處理扣考資料
					// 1.計算該課程全學期應上課時數(一般:學分數*18週, 畢業班:學分數*14週)
					if(subject.getCredit() >0){
						int limitHours = 0;
						String sterm = Toolket.getSysParameter("School_term");
	
						if (Toolket.chkIsGraduateClass(subject.getDepartClass())
								&& sterm.equals("2")){
							if(subject.getThour() * 14 % 3 > 0)
								limitHours = subject.getThour() * 14 / 3 + 1;
							else
								limitHours = subject.getThour() * 14 / 3;
						}else
							limitHours = subject.getThour() * 18 / 3;
	
						// 2.計算該課程學生曠缺記錄-->該班學生
						int[] timeOffSum = sm.calTimeOffBySubject(classBook[0],
								subject.getOid());
						//log.debug("timeOffSum.size()->" + timeOffSum.length);
	
						int tfIndex = 0;
						for (Iterator cbIter = classBook[0].iterator(); cbIter
								.hasNext();) {
							Map cbMap = (Map) cbIter.next();
	
							// 退選不計算扣考
							for(kk=dtime_begin; kk<=dtime_end; kk++) {
							
								if (cbMap.get("status" + kk).toString().equals("w")) {
									break;
								} else if (timeOffSum[tfIndex] >= limitHours) {
									//log.debug("StudTimeOffInputBatch->limitHours:timeOffSum:" +limitHours+":"+timeOffSum[tfIndex]);
									//cbMap.put("status" + kk, "n");
									//cbMap.put("status" + kk, "");
								}
							}
							tfIndex++;
	
						}
						//3.計算該課程學生曠缺記錄-->跨選學生
						int[] timeOffSumE = sm.calTimeOffBySubject(classBook[subjCount],
								subject.getOid());
						//log.debug("timeOffSum.size()->" + timeOffSumE.length);
	
						tfIndex = 0;
						for (Iterator cbIter = classBook[subjCount].iterator(); cbIter
								.hasNext();) {
							Map cbMap = (Map) cbIter.next();
	
							// 退選不計算扣考
							for(kk=dtime_begin; kk<=dtime_end; kk++) {
							
								if (cbMap.get("status" + kk).toString().equals("w")) {
									break;
								} else if (timeOffSumE[tfIndex] >= limitHours) {
									//cbMap.put("status" + kk, "n");
									//cbMap.put("status" + kk, "");
								}
							}
							tfIndex++;
						}
					}
					
					/*
					// 將該科目之相關資料放入subjMap中,以list方式傳到前台
					subjMap.put("cscode", cscode);
					subjMap.put("cscodeName", dtime_chiName);
					subjMap.put("begin", dtime_begin);
					subjMap.put("end", dtime_end);
					subjects.add(subjMap);
					*/
					classBookMap.put("cscode", subject.getOid());
					classBookMap.put("cscodeName", dtime_chiName);
					classBookMap.put("begin", dtime_begin);
					classBookMap.put("end", dtime_end);
					classBookMap.put("classBook", classBook[subjCount]);
					classBookMap.put("classBookInSubject", classBook);

					classBookList.add(classBookMap);
				

					}catch( Exception e){
						e.printStackTrace();
					}

				}
				//更新該班學生點名簿(classBook[0])資料
				Map cbMap = new HashMap();
				cbMap = (Map)classBookList.get(0);
				cbMap.put("classBook", classBook[0]);
				
			} else {
				// 該班今日無任何課程
				ActionMessages msgs = new ActionMessages();
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.NoSubjectInClassOnDtae"));
				saveMessages(request, msgs);
			}
		}
			
		
		if(classBookList.isEmpty()) {
			// 沒有查到任何開課課程及相關選課學生
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.NoSubjectInClassOnDtae"));
			saveMessages(request, msgs);
		}
		
		
		//session.setAttribute("studTimeOffSubject", subjects);
		session.setAttribute("StudTimeoffInitB", StudTimeoffInit);
		session.setAttribute("StudTimeOffInEditB", classBookList);
		setContentPage(session, "studaffair/StudTimeOffInputBatch.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm dynForm = (DynaActionForm)form;
		String mode = dynForm.getString("mode");
		
		session.removeAttribute("StudTimeOffEditB");
		session.removeAttribute("StudTimeOffInEditB");
		setContentPage(session, "teacher/TimeOffInputBatch.jsp");
		if(mode.trim().equalsIgnoreCase("redirect")){
			return mapping.findForward("redirectForward");
		}
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
