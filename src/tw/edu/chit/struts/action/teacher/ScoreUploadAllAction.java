package tw.edu.chit.struts.action.teacher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.DtimeTeacher;
import tw.edu.chit.model.Optime1;
import tw.edu.chit.model.Regs;
import tw.edu.chit.model.Regstime;
import tw.edu.chit.model.Rrate;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class ScoreUploadAllAction extends BaseLookupDispatchAction {
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("OK","edit");
		map.put("Cancel", "cancel");
		return map;		
	}
	
	public ActionForward unspecified(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
			throws Exception {

			HttpSession session = request.getSession(false);
			DynaActionForm aForm = (DynaActionForm)form;
			session.setAttribute("sessionInterval", session.getMaxInactiveInterval());

			UserCredential user = (UserCredential)session.getAttribute("Credential");
			
			String teacherName = user.getMember().getName();
			String teacherId = user.getMember().getAccount();
			String departClass = "";
			String cscode = "";
			String scoretype = aForm.getString("scoretype");
			
			ScoreManager sm = (ScoreManager)getBean("scoreManager");
			List<Dtime> dtimelist = sm.findDtimeTeacherByTeacher(teacherId);
			session.setAttribute("TeachClassInChoose", dtimelist);
			
			String campusNmae = "";
			List<Optime1> opList = sm.findOptime("0");
			
			Map initValue = new HashMap();
			initValue.put("teacherName", teacherName);
			initValue.put("teacherId", teacherId);
			initValue.put("scoretype", scoretype);
			initValue.put("optime", opList);
			session.setAttribute("TchScoreUploadInfo", initValue);
			
			if(!(scoretype.equals("6") ||scoretype.equals("7")
					||scoretype.equals("8")||scoretype.equals("9"))){
				ActionMessages messages = new ActionMessages();
				for(Iterator<Dtime> dtIter=dtimelist.iterator(); dtIter.hasNext();){
					Dtime dtime = dtIter.next();
					messages = sm.chkTchScoreUploadOpened(scoretype, dtime.getDepartClass());
					if(!messages.isEmpty()){
						//log.debug("removed:" + dtime.getDepartClass());
						dtIter.remove();
					}
				}
			}
			float[] scorerate = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.3f, 0.3f, 0.4f};
			session.setAttribute("defaultRate", scorerate);
			session.removeAttribute("TchScoreInEdit");
			session.removeAttribute("TchScoreUploadPrint");
			session.removeAttribute("TchScorePrint");
			session.removeAttribute("TchScoreUploadFormMap");
			setContentPage(session, "teacher/TeachClassChoose.jsp");
			return mapping.findForward("Main");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm)form;
		ResourceBundle bundle = Toolket.getBundle(request);
		int sesscnt = 0;
		ActionMessages messages = new ActionMessages();
		
		String selclass = aForm.getString("selclass").trim();
		String cscode = aForm.getString("cscode").trim();
		int  dtimeoid = Integer.parseInt(aForm.getString("dtimeoid").trim());
		String scoretype = aForm.getString("scoretype");
		String teacherName = ((Map)session.getAttribute("TchScoreUploadInfo")).get("teacherName").toString();
		String teacherId = ((Map)session.getAttribute("TchScoreUploadInfo")).get("teacherId").toString();
		String departClass = "";
		List<Regstime> rtList = new ArrayList();
		List<Dtime> dtList = (List)session.getAttribute("TeachClassInChoose");
		Map initValue = new HashMap();

		if(selclass.trim().equals("")) {
			return mapping.findForward("Main");
		}
		ScoreManager sm = (ScoreManager)getBean("scoreManager");
		String classname = sm.findClassName(selclass);
		String cscodeName = sm.findCourseName(cscode);

		/* 英語課程因無法判斷是否為語言訓練中心之開課,故取消重導機制
		if(cscodeName.indexOf("英文") != -1 || cscodeName.indexOf("英語") != -1){
			return mapping.findForward("forward2English");
		}else if(cscodeName.indexOf("體育") != -1){
			return mapping.findForward("forward2Sport");
		}
		*/
		if(cscodeName.indexOf("體育") != -1){
			return mapping.findForward("forward2Sport");
		}
		
		session.removeAttribute("TchScoreInPrint");
		//log.debug("=======> input->departClass=" + departClass);
		//scoretype=> 1:期中考,2:平時&期末,3:學期成績,9:列印空白成績冊,8:列印平時&期末成績,7:列印學期成績,6:列印期中成績
		//scoretype=2 係上傳平時及期末成績在這個階段檢查過了期末考輸入時間就不能在輸入了!
		String openMode = "001";
		/*	openMode charAt[0] charAt[1] charAt[2]	用來控制JSP顯示處理資料的模式
		 * 				  |		     |		  |
		 * 				 期末	                     期中	              平時
		 */
		//openMode: 000:學期, 001:平時, 011:平時&期中, 101:平時&期末  
		if(!(scoretype.equals("6") ||scoretype.equals("7")||scoretype.equals("8")||scoretype.equals("9"))){
			if(session.getAttribute("sesscnt") != null){
				sesscnt = Integer.parseInt(session.getAttribute("sesscnt").toString());
			}
			if(sesscnt > 0) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Course.errorN1", "成績上傳:請勿開啟多視窗輸入成績!"));
				saveMessages(request, messages);
				//session.removeAttribute("TchScoreInEdit");
				session.removeAttribute("TchScoreUploadPrint");
				session.removeAttribute("TchScorePrint");
				session.removeAttribute("TchScoreUploadFormMap");
				setContentPage(session, "teacher/TeachClassChoose.jsp");
				return mapping.findForward("Main");
			}else{
				session.setAttribute("sesscnt", "1");
			}
			
			if(scoretype.equals("")){
				messages = sm.chkTchScoreUploadOpened("2", selclass);
				if(!messages.isEmpty()){
					saveMessages(request, messages);
					setContentPage(session, "teacher/TeachClassChoose.jsp");
					return mapping.findForward("Main");
				}else{
					messages = sm.chkTchScoreUploadOpened("1", selclass);
					if(messages.isEmpty()){
						scoretype = "1";
						openMode = "010";
					}else{
						messages = sm.chkTchScoreUploadOpened("22", selclass);
						if(messages.isEmpty()){
							scoretype = "2";
							openMode = "101";
						}
						/*
						else{
							messages = sm.chkTchScoreUploadOpened("2", selclass);
							if(messages.isEmpty()){
								openMode = "001";
							}else{
								openMode = "000";
							}
						}
						*/
					}
				}
			}else{
				messages = sm.chkTchScoreUploadOpened(scoretype, selclass);
				if(messages.isEmpty()){
					if(scoretype.equals("1")){
						openMode = "010";
					}else if(scoretype.equals("2")){
						messages = sm.chkTchScoreUploadOpened("22", selclass);
						if(messages.isEmpty()){
							openMode = "101";
						}else{
							openMode = "001";
						}
					}else if(scoretype.equals("3")){
						openMode = "000";
					}
				}else{
					saveMessages(request, messages);
					setContentPage(session, "teacher/TeachClassChoose.jsp");
					return mapping.findForward("Main");
				}
			}			
			/*
			 * 上傳學期成績只能選擇一種方式,
			 *	1.輸入平時及期末由系統自動計算
			 *	2.或者老師自行計算後輸入學期成績
			*/
			if(openMode.equals("101")){
				if(sm.chkNoMiddleScore(selclass,cscode)){
					//沒有輸入期中成績則強迫使用自行計算學期成績
					openMode = "000";
					scoretype = "3";
				}else{
					rtList = sm.findRegstime(dtimeoid, selclass, cscode, teacherId, "2");
					if(rtList.isEmpty()){
						rtList = sm.findRegstime(dtimeoid, selclass, cscode, teacherId, "3");
						if(!rtList.isEmpty()){
							initValue.put("methodTransfer", "!!! 輸入學期成績方式僅能選擇一種 !!!\\n"+
									"您之前已使用[自行計算學期成績]之方式上傳學期成績\\n如欲改變輸入方式,請洽電算中心!");
							openMode = "000";
							scoretype = "3";
						}
					}
				}
			}else if(openMode.equals("000")){
				rtList = sm.findRegstime(dtimeoid, selclass, cscode, teacherId, "2");
				if(!rtList.isEmpty()){
					initValue.put("methodTransfer", "!!! 輸入學期成績方式僅能選擇一種 !!!\\n"+
							"您之前已使用[系統自動計算學期成績]之方式上傳學期成績\\n如欲改變輸入方式,請洽電算中心!");
					openMode = "101";
					scoretype = "2";
				}
			}
			log.debug("openMode:"+openMode);
		}
		
		/* ActionMessages msgs = sm.patchSeldRegs(selclass, cscode);
		if(! msgs.isEmpty()){
			saveErrors(request, messages);
			setContentPage(session, "teacher/TeachClassChoose.jsp");
			return mapping.findForward("Main");
		}*/
		
		List<Seld> seldlist = sm.findSeldScoreByInputForm("" + dtimeoid);
		//log.debug("=======> input->SelInEdit=" + seldlist.size());
		
		if(seldlist.size() > 0){
			String nows="";
			String index = "";
			if(scoretype.equals("6") || scoretype.equals("1")) {
				index = "1";
			}else if(scoretype.equals("7") || scoretype.equals("3")) {
				index = "3";
			}else if(scoretype.equals("8") || scoretype.equals("2")) {
				index = "2";
			}
			
			//scoreNotUpload:用在控制學期成績輸入時是否帶入平時及期末輸入之成績
			//nows:列印時顯示上傳成績時間
			//List<Regstime> rtList = sm.findRegstime(selclass, cscode, teacherId, index);
			rtList = sm.findRegstime(dtimeoid, selclass, cscode, "", index);
			
			if(!rtList.isEmpty()){
				nows=rtList.get(0).getTtime();
				initValue.put("scoreNotUpload", "0");
				log.debug("ScoreUpload->scoreNotUpload:0");
			}else{
				if(index.equals("2")){
					rtList = sm.findRegstime(dtimeoid, selclass, cscode, "", "3");
					if(!rtList.isEmpty()){
						nows=rtList.get(0).getTtime();
						initValue.put("scoreNotUpload", "0");
					}else{
						initValue.put("scoreNotUpload", "1");
					}
				}else if(index.equals("3")){
					rtList = sm.findRegstime(dtimeoid, selclass, cscode, "", "2");
					if(!rtList.isEmpty()){
						nows=rtList.get(0).getTtime();
						initValue.put("scoreNotUpload", "0");
					}else{
						initValue.put("scoreNotUpload", "1");
					}
				}else{
					initValue.put("scoreNotUpload", "1");
					log.debug("ScoreUpload->scoreNotUpload:1");
				}
			}
							
			initValue.put("teacherName", teacherName);
			initValue.put("teacherId", teacherId);
			initValue.put("scoretype", scoretype);
			initValue.put("Dtime_oid", seldlist.get(0).getDtimeOid());
			initValue.put("departClass", selclass);
			initValue.put("depClassName", classname);
			initValue.put("cscode", cscode);
			initValue.put("cscodeName", cscodeName);
			initValue.put("openMode", openMode);
			//initValue.put("opmode", "saveregs");
			
			//if(scoretype.equals("2") || scoretype.equals("8")) {
			Calendar now = Calendar.getInstance();
			List<Rrate> ratelist = sm.findScoreRate(dtimeoid);
			if(ratelist.size() == 0) {
				//create Rrate record
					Rrate rrate = sm.createRrate(selclass, cscode);
					session.setAttribute("TchScoreRate", rrate);
			} else if(ratelist.size() > 0) {
				String[] rateArray = new String[13];
				rateArray[0] = ratelist.get(0).getRate01().toString();
				rateArray[1] = ratelist.get(0).getRate02().toString();
				rateArray[2] = ratelist.get(0).getRate03().toString();
				rateArray[3] = ratelist.get(0).getRate04().toString();
				rateArray[4] = ratelist.get(0).getRate05().toString();
				rateArray[5] = ratelist.get(0).getRate06().toString();
				rateArray[6] = ratelist.get(0).getRate07().toString();
				rateArray[7] = ratelist.get(0).getRate08().toString();
				rateArray[8] = ratelist.get(0).getRate09().toString();
				rateArray[9] = ratelist.get(0).getRate10().toString();
				rateArray[10] = ratelist.get(0).getRateN().toString();
				rateArray[11] = ratelist.get(0).getRateM().toString();
				rateArray[12] = ratelist.get(0).getRateF().toString();
				session.setAttribute("TchScoreRateArray", rateArray);
				session.setAttribute("TchScoreRate", ratelist.get(0));
			}
			Calendar limit = (Toolket.getDateOfWeek(selclass.substring(0, 2), 4))[0];
			if(now.compareTo(limit) >= 0){
				session.setAttribute("TchScoreRateUpdatable", "0");
			}else{
				//比例取消於上傳時可調整,由ScoreRateChangeAction 處理
				//session.setAttribute("TchScoreRateUpdatable", "1");
				session.setAttribute("TchScoreRateUpdatable", "0");
			}
			log.debug("now:" + now.getTime() + ", limit:" + limit.getTime());
			//}
			if(scoretype.equals("9") || scoretype.equals("8") || scoretype.equals("7") || scoretype.equals("6")) {	
				//log.debug("Entering Score Print!");
				int len = seldlist.size();
				String[] studentNames = new String[len];
				String[] studentNos = new String[len];
				String[] score = new String[len];
				String[] score1 = new String[len];
				String[] score2 = new String[len];
				String[] score3 = new String[len];
				String[] score01 = new String[len];
				String[] score02 = new String[len];
				String[] score03 = new String[len];
				String[] score04 = new String[len];
				String[] score05 = new String[len];
				String[] score06 = new String[len];
				String[] score07 = new String[len];
				String[] score08 = new String[len];
				String[] score09 = new String[len];
				String[] score10 = new String[len];
				String[] score16 = new String[len];
				String[] score17 = new String[len];
				String[] score18 = new String[len];
				
				int scoresint = 0;
				double totalscore = 0d;
				int nopass = 0;
				
				Seld seld;
				int rcount = 0;
				
				/*
				Calendar now = Calendar.getInstance();
				nows = "" + (now.get(Calendar.YEAR)-1911) 
							+ "/" + (now.get(Calendar.MONTH) + 1) 
							+ "/" + now.get(Calendar.DATE) 
							+ " " + now.get(Calendar.HOUR_OF_DAY) 
							+ ":" + now.get(Calendar.MINUTE) 
							+ ":" + now.get(Calendar.SECOND);
				*/
				for(Iterator<Seld> seldIter=seldlist.iterator(); seldIter.hasNext(); ) {
					seld = seldIter.next();
					studentNos[rcount] = seld.getStudentNo();
					studentNames[rcount] = seld.getStudentName();
					if(seld.getScore() != null) {
						score[rcount] = seld.getScore().toString();
					} else {
						score[rcount] = "";
					}
					
					if(seld.getScore1() != null) {
						score1[rcount] = seld.getScore1().toString();
					} else {
						score1[rcount] = "";
					}
					
					if(seld.getScore2() != null) {
						score2[rcount] = seld.getScore2().toString();
					} else {
						score2[rcount] = "";
					}
					
					if(seld.getScore3() != null) {
						score3[rcount] = seld.getScore3().toString();
					} else {
						score3[rcount] = "";
					}
					if(seld.getScore01() != null) {
						score01[rcount] = seld.getScore01().toString();
					} else {
						score01[rcount] = "";
					}

					if(seld.getScore02() != null) {
						score02[rcount] = seld.getScore02().toString();
					} else {
						score02[rcount] = "";
					}
					
					if(seld.getScore03() != null) {
						score03[rcount] = seld.getScore03().toString();
					} else {
						score03[rcount] = "";
					}
					
					if(seld.getScore04() != null) {
						score04[rcount] = seld.getScore04().toString();
					} else {
						score04[rcount] = "";
					}
					if(seld.getScore05() != null) {
						score05[rcount] = seld.getScore05().toString();
					} else {
						score05[rcount] = "";
					}
					
					if(seld.getScore06() != null) {
						score06[rcount] = seld.getScore06().toString();
					} else {
						score06[rcount] = "";
					}
					
					if(seld.getScore07() != null) {
						score07[rcount] = seld.getScore07().toString();
					} else {
						score07[rcount] = "";
					}
					
					if(seld.getScore08() != null) {
						score08[rcount] = seld.getScore08().toString();
					} else {
						score08[rcount] = "";
					}
					
					if(seld.getScore09() != null) {
						score09[rcount] = seld.getScore09().toString();
					} else {
						score09[rcount] = "";
					}
					
					if(seld.getScore10() != null) {
						score10[rcount] = seld.getScore10().toString();
					} else {
						score10[rcount] = "";
					}
					
					if(seld.getScore16() != null) {
						score16[rcount] = seld.getScore16().toString();
					} else {
						score16[rcount] = "";
					}
					
					if(seld.getScore17() != null) {
						score17[rcount] = seld.getScore17().toString();
					} else {
						score17[rcount] = "";
					}
					
					if(seld.getScore18() != null) {
						score18[rcount] = seld.getScore18().toString();
					} else {
						score18[rcount] = "";
					}
					
					
					if(scoretype.equals("6")) {
						if(!score2[rcount].equals("")) {
							scoresint = (int)Math.round(seld.getScore2());
							totalscore = totalscore + seld.getScore2();
						}else {
							scoresint = 0;
						}
						score2[rcount] = "" + scoresint;
						if (scoresint < 60){
							nopass++;
							score2[rcount] = score2[rcount] + "*";
						}
						
					}else if(scoretype.equals("7")||scoretype.equals("8")) {
						if(!score[rcount].equals("")) {
							scoresint = (int)Math.round(seld.getScore());
							totalscore = totalscore + seld.getScore();
						}else {
							scoresint = 0;
						}
						score[rcount] = "" + scoresint;
						if (scoresint < 60){
							nopass++;
							score[rcount] = score[rcount] + "*";
						}
						
					}

					rcount++;
				}
								
				Map pmap = new HashMap();
				pmap.put("scoretype", scoretype);
				pmap.put("opmode", "Print");
				pmap.put("schoolYear", Toolket.getSysParameter("School_year"));
				pmap.put("schoolTerm", Toolket.getSysParameter("School_term"));
				pmap.put("departClass", selclass);
				pmap.put("depClassName", classname);
				pmap.put("teacherName",teacherName);
				pmap.put("cscode",cscode);
				pmap.put("cscodeName",cscodeName);
				pmap.put("studentNo", studentNos);
				pmap.put("studentName", studentNames);
				if(scoretype.equals("9")) {
					session.setAttribute("TchScoreBlankPrint", pmap);
				} else if(scoretype.equals("8")) {
					List<Rrate> rrateList = sm.findScoreRate(dtimeoid);
					Rrate rrate = new Rrate();
					if(!rrateList.isEmpty()){
						rrate = rrateList.get(0);						
					}
					pmap.put("rrate", rrate);
					pmap.put("score01", score01);
					pmap.put("score02", score02);
					pmap.put("score03", score03);
					pmap.put("score04", score04);
					pmap.put("score05", score05);
					pmap.put("score06", score06);
					pmap.put("score07", score07);
					pmap.put("score08", score08);
					pmap.put("score09", score09);
					pmap.put("score10", score10);
					pmap.put("score16", score16);
					pmap.put("score17", score17);
					pmap.put("score18", score18);
					pmap.put("score", score);
					pmap.put("score1", score1);
					pmap.put("score2", score2);
					pmap.put("score3", score3);
					session.setAttribute("TchScorePrint", pmap);
				} else if(scoretype.equals("7")) {
					pmap.put("score", score);
				} else if(scoretype.equals("6")) {
					pmap.put("score2", score2);
				}
				pmap.put("totalstu",len );
				pmap.put("avgscore", Math.round(totalscore / len));
				pmap.put("pass", len-nopass);
				pmap.put("nopass", nopass);
				pmap.put("now", nows);
				session.setAttribute("TchScorePrint", pmap);
				
				String contentPage = "";
				if(scoretype.equals("9")){
					contentPage = "teacher/ScoreBlankPrint.jsp";
				}else if(scoretype.equals("8")){
					contentPage = "teacher/ScoreFinalPrint.jsp";
				}else if(scoretype.equals("7")){
					contentPage = "teacher/ScoreThisPrint.jsp";
				}else if(scoretype.equals("6")){
					contentPage = "teacher/ScoreMiddlePrint.jsp";
				}
				
				setContentPage(session, contentPage);
				return mapping.findForward("Blank");

			}	//End of scoretype=6,7,8,9
			
			session.setAttribute("TchScoreUploadInfo", initValue);
			session.setAttribute("TchScoreInEdit", seldlist);
			if(scoretype.equals("9") || scoretype.equals("8") || scoretype.equals("7") || scoretype.equals("6")) {
				setContentPage(session, "teacher/TeachClassChoose.jsp");
			}else{
				session.setMaxInactiveInterval(7200);	//2Hr. for session timeout
				setContentPage(session, "teacher/ScoreEditAll.jsp");
				return mapping.findForward("Main");
			}
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.NoSeldScoreFound"));
			saveMessages(request, messages);
			setContentPage(session, "teacher/TeachClassChoose.jsp");
		}
		// session.removeAttribute("ScoreInputInfo");
		// log.debug("=======> input->yn=" + yn);
		return mapping.findForward("Main");

	}
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("TeachClassInChoose");
		session.removeAttribute("TchScoreUploadInfo");
		session.removeAttribute("TchScoreMidInEdit");
		session.removeAttribute("TchScoreMidInPrint");
		session.removeAttribute("TchScoreFinalInEdit");
		session.removeAttribute("TchScoreFinalInPrint");
		//session.removeAttribute("sesscnt");
		setContentPage(request.getSession(false), "DirectoryScoreInput.jsp");
		return mapping.findForward("Main");
	}

	
}
