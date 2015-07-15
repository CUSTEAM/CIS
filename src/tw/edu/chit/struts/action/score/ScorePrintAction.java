package tw.edu.chit.struts.action.score;

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

import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.model.DEmpl;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.DtimeTeacher;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Employee;
import tw.edu.chit.model.Regs;
import tw.edu.chit.model.Regstime;
import tw.edu.chit.model.Rrate;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class ScorePrintAction extends BaseLookupDispatchAction {
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("OK","print");
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
			
			String campus = aForm.getString("campusInCharge2").trim();
			String school = aForm.getString("schoolInCharge2").trim();
			String dept = aForm.getString("deptInCharge2").trim();
			String myclass = aForm.getString("classInCharge2").trim();
			String cscode = aForm.getString("cscode").trim();
			String scoretype = aForm.getString("scoretype");
			String yn = aForm.getString("yn");
			
			session.removeAttribute("ScorePrintInfo");
			if (!(campus.equals("") || school.equals("") || dept.equals("") || myclass.equals("")))
			{
				ScoreManager sm = (ScoreManager)getBean("scoreManager");
				String departClass = myclass;
				//log.debug("=======> unspe->departClass=" + departClass);
				List<Dtime> dtimelist = sm.findCscodeByClass(departClass);
				
				List cscodelist = new ArrayList();
				Dtime dtime;
				for (Iterator<Dtime> dtimeIter = dtimelist.iterator(); dtimeIter.hasNext();) {
					Map cmap = new HashMap();
					dtime = dtimeIter.next();
					cmap.put("cscode", dtime.getCscode());
					cmap.put("chiName", dtime.getChiName2());
					cmap.put("dtimeoid", dtime.getOid());
					cscodelist.add(cmap);
				}
				session.setAttribute("ScorePrintInfo", cscodelist);
			}
			Map initValue = new HashMap();
			initValue.put("mode", "");
			initValue.put("campus", campus);
			initValue.put("school", school);
			initValue.put("dept", dept);
			initValue.put("clazz", myclass);
			initValue.put("cscode", cscode);
			initValue.put("yn", yn);
			initValue.put("scoretype", scoretype);
			session.setAttribute("ScoreInPrintInit", initValue);
						
			session.removeAttribute("ScoreInPrint");
			setContentPage(session, "score/ScorePrint.jsp");
			return mapping.findForward("Main");
	}

	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		
		session.removeAttribute("ScoreInPrint");
		setContentPage(session, "Directory.jsp");
		return mapping.findForward("Main");
	}

	
	public ActionForward print(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm)form;
		ResourceBundle bundle = Toolket.getBundle(request);
		ActionMessages messages = new ActionMessages();

		String campus = aForm.getString("campusInCharge2").trim();
		String school = aForm.getString("schoolInCharge2").trim();
		String dept = aForm.getString("deptInCharge2").trim();
		String myclass = aForm.getString("classInCharge2").trim();

		String cscodedtime = aForm.getString("cscode").trim();
		String[] codeArray = cscodedtime.split("_");
		String cscode = codeArray[0];
		int dtimeoid = Integer.parseInt(codeArray[1]);

		String scoretype = aForm.getString("scoretype");
		String yn = aForm.getString("yn");
		String classname = "";
		String cscodeName = "";
		
		Map initValue = new HashMap();
		//initValue.putAll((Map)session.getAttribute("ScoreInputInit"));
		initValue.put("mode", "Input");
		initValue.put("campus", campus);
		initValue.put("school", school);
		initValue.put("dept", dept);
		initValue.put("clazz", myclass);
		initValue.put("cscode", cscode);
		initValue.put("yn", yn);
		initValue.put("scoretype", scoretype);
		session.setAttribute("ScoreInPrintInit", initValue);
		
		if (!(campus.equals("") || school.equals("") || dept.equals("") || myclass.equals("") || cscode.equals("") || scoretype.equals("")))
		{
			ScoreManager sm = (ScoreManager)getBean("scoreManager");
			CourseManager cm = (CourseManager)getBean("courseManager");
			MemberManager mm = (MemberManager)getBean("memberManager");
			MemberDAO mmDao =  (MemberDAO)getBean("memberDAO");
			String departClass = myclass;
			classname = sm.findClassName(departClass);
			cscodeName = sm.findCourseName(cscode);
			
			/*
			ActionMessages msgs = sm.patchSeldRegs(departClass, cscode);
			if(! msgs.isEmpty()){
				saveErrors(request, messages);
			}
			*/
			//log.debug("=======> input->departClass=" + departClass);
			//List<Regs> regslist = sm.findRegsScore(departClass, cscode);
			//List<Seld> seldlist = sm.findSeldScoreByInputForm(departClass, cscode);
			List<Seld> seldlist = sm.findSeldScoreByInputForm("" + dtimeoid);
			//log.debug("=======> input->SelInEdit=" + seldlist.size());
			if(seldlist.size() > 0){
				/*
				initValue.put("Dtime_oid", regslist.get(1).getDtimeOid());
				initValue.put("depClassName", classname);
				initValue.put("cscodeName", cscodeName);
				*/
				Dtime dtime = cm.findDtimeBy(seldlist.get(0).getDtimeOid());
				String teacherId = dtime.getTechid().trim();
				String teacherName = "";
				
				if(teacherId == null || teacherId.equals("")){
					String hql = "From DtimeTeacher Where dtimeOid=" + dtime.getOid();
					List<DtimeTeacher> dts = mmDao.submitQuery(hql);
					
					for(DtimeTeacher dt:dts){
						Empl empl = mmDao.findEmplByIdno(dt.getTeachId());
						//log.debug("techid=" + empl +":" + dtime);
						if(empl != null){
							teacherName = teacherName + " " +empl.getCname();							
						}else{
							DEmpl dempl = mmDao.findDEmplByIdno(dt.getTeachId());
							if(dempl != null){
								teacherName = teacherName + " " +empl.getCname();								
							}
						}
					}
					
				}else{
					Empl empl = mmDao.findEmplByIdno(dtime.getTechid());
					log.debug("techid=" + empl +":" + dtime);
					if(empl != null){
						teacherName = empl.getCname();
					}else{
						DEmpl dempl = mmDao.findDEmplByIdno(dtime.getTechid());
						if(dempl != null){
							teacherName = dempl.getCname();
						}
					}
				}
				//session.setAttribute("ScoreInPrint", regslist);
				
				if(scoretype.equals("2")) {
					List<Rrate> ratelist = sm.findScoreRate(dtime.getOid());
					if(ratelist.size() == 0) {
						//create Rrate record
							Rrate rrate = sm.createRrate(myclass, cscode);
							session.setAttribute("ScoreRate", rrate);
							String[] rateArray = new String[13];
							
							rateArray[0] = rrate.getRate01().toString();
							rateArray[1] = rrate.getRate02().toString();
							rateArray[2] = rrate.getRate03().toString();
							rateArray[3] = rrate.getRate04().toString();
							rateArray[4] = rrate.getRate05().toString();
							rateArray[5] = rrate.getRate06().toString();
							rateArray[6] = rrate.getRate07().toString();
							rateArray[7] = rrate.getRate08().toString();
							rateArray[8] = rrate.getRate09().toString();
							rateArray[9] = rrate.getRate10().toString();
							rateArray[10] = rrate.getRateN().toString();
							rateArray[11] = rrate.getRateM().toString();
							rateArray[12] = rrate.getRateF().toString();
							session.setAttribute("ScoreRateArray", rateArray);
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
						session.setAttribute("ScoreRateArray", rateArray);
						session.setAttribute("ScoreRate", ratelist.get(0));

					}
				}

				//if(scoretype.equals("1") || scoretype.equals("2")) {	
					// 1:列印期中成績 2:列印平時及期末成績   3:列印學期成績
					//log.debug("Entering Score Print!");
					int len = seldlist.size();
					String[] studentNames = new String[len];
					String[] studentNos = new String[len];
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
					String[] score = new String[len];
					String[] score1 = new String[len];
					String[] score2 = new String[len];
					String[] score3 = new String[len];
					
					int scoresint = 0;
					int totalscore = 0;
					int nopass = 0;
					
					Seld seld;
					int rcount = 0;
					String nows="";
					
					/*
					Calendar now = Calendar.getInstance();
					nows = "" + (now.get(Calendar.YEAR)-1911) 
								+ "/" + (now.get(Calendar.MONTH) + 1) 
								+ "/" + now.get(Calendar.DATE) 
								+ " " + now.get(Calendar.HOUR_OF_DAY) 
								+ ":" + now.get(Calendar.MINUTE) 
								+ ":" + now.get(Calendar.SECOND);
					*/
					
					List<Regstime> rtList = sm.findRegstime(dtimeoid, myclass, cscode, "", scoretype);
					if(!rtList.isEmpty()){
						nows=rtList.get(0).getTtime();
					}
					
					for(Iterator<Seld> seldIter=seldlist.iterator(); seldIter.hasNext(); ) {
						seld = seldIter.next();
						studentNos[rcount] = seld.getStudentNo();
						studentNames[rcount] = seld.getStudentName();
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
						
						if(scoretype.equals("1")) {
							if(!score2[rcount].equals("")) {
								scoresint = (int)Math.round(seld.getScore2());
							}else {
								scoresint = 0;
							}
							score2[rcount] = "" + scoresint;
							totalscore = totalscore + scoresint;
							if (scoresint < 60){
								nopass++;
								score2[rcount] = score2[rcount] + "*";
							}
							
						}else if (scoretype.equals("2")||scoretype.equals("3")){
							if(!score[rcount].equals("")) {
								scoresint = (int)Math.round(seld.getScore());
							}else {
								scoresint = 0;
							}
							score[rcount] = "" + scoresint;
							totalscore = totalscore + scoresint;
							if (scoresint < 60){
								nopass++;
								score[rcount] = score[rcount] + "*";
							}
						}

						rcount++;
					}
									
					Map pmap = new HashMap();
					pmap.put("scoretype", scoretype);
					pmap.put("campus", campus);
					pmap.put("school", school);
					pmap.put("dept", dept);
					pmap.put("clazz", myclass);
					pmap.put("cscode", cscode);
					pmap.put("yn", yn);

					pmap.put("opmode", "Print");
					pmap.put("schoolYear", Toolket.getSysParameter("School_year"));
					pmap.put("schoolTerm", Toolket.getSysParameter("School_term"));
					pmap.put("departClass", myclass);
					pmap.put("depClassName", classname);
					pmap.put("teacherName",teacherName);
					pmap.put("cscode",cscode);
					pmap.put("cscodeName",cscodeName);
					pmap.put("studentNo", studentNos);
					pmap.put("studentName", studentNames);
					if(scoretype.equals("2")) {
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
					} else if(scoretype.equals("1")) {
						pmap.put("score", score2);
					} else if(scoretype.equals("3")) {
						pmap.put("score", score);
					}
					pmap.put("totalstu",len );
					pmap.put("avgscore", Math.round(totalscore / len));
					pmap.put("pass", len-nopass);
					pmap.put("nopass", nopass);
					pmap.put("now", nows);
					session.setAttribute("ScoreInPrint", pmap);
				//}

				
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.NoSeldScoreFound"));
				saveMessages(request, messages);
				
			}
			// session.removeAttribute("ScoreInputInfo");
			
		} else {
			ActionMessages errors = new ActionMessages();

			if (campus.equals("")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ScoreInput.MustSelect", bundle.getString("campusInCharge")));
			}
			if (school.equals("")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ScoreInput.MustSelect", bundle.getString("schoolInCharge")));
			}
			if (dept.equals("")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ScoreInput.MustSelect", bundle.getString("deptInCharge")));
			}
			if (myclass.equals("")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ScoreInput.MustSelect", bundle.getString("classInCharge")));
			}
			if (cscode.equals("")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ScoreInput.MustSelect", bundle.getString("course")));
			}
			if (scoretype.equals("")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ScoreInput.MustSelect", bundle.getString("scoretype")));
			}
			saveErrors(request, errors);
			session.removeAttribute("ScoreInPrint");
			
		}
				
		// log.debug("=======> input->yn=" + yn);
		setContentPage(session, "score/ScorePrint.jsp");
		return mapping.findForward("Main");

	}
	

}
