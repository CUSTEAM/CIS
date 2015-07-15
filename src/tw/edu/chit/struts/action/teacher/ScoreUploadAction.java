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
import tw.edu.chit.model.Regs;
import tw.edu.chit.model.Regstime;
import tw.edu.chit.model.Rrate;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class ScoreUploadAction extends BaseLookupDispatchAction {
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

			Map initValue = new HashMap();
			initValue.put("teacherName", teacherName);
			initValue.put("teacherId", teacherId);
			initValue.put("scoretype", scoretype);
			session.setAttribute("TchScoreUploadInfo", initValue);
			
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
		ActionMessages messages = new ActionMessages();

		String selclass = aForm.getString("selclass").trim();
		String cscode = aForm.getString("cscode").trim();
		String scoretype = aForm.getString("scoretype");
		int  dtimeoid = Integer.parseInt(aForm.getString("dtimeoid").trim());
		String teacherName = ((Map)session.getAttribute("TchScoreUploadInfo")).get("teacherName").toString();
		String teacherId = ((Map)session.getAttribute("TchScoreUploadInfo")).get("teacherId").toString();
		String departClass = "";
		
		if(selclass.trim().equals("")) {
			return mapping.findForward("Main");
		}
		ScoreManager sm = (ScoreManager)getBean("scoreManager");
		String classname = sm.findClassName(selclass);
		String cscodeName = sm.findCourseName(cscode);

		session.removeAttribute("TchScoreInPrint");
		//log.debug("=======> input->departClass=" + departClass);
		//scoretype=> 1:期中考,2:平時&期末,3:學期成績,9:列印空白成績冊,8:列印平時&期末成績,7:列印學期成績,6:列印期中成績
		if(scoretype.equals("1")||scoretype.equals("2")||scoretype.equals("3")) {
		//if(scoretype.equals("1")||scoretype.equals("3")) {
			messages = sm.chkTchScoreUploadOpened(scoretype, selclass);
			if(!messages.isEmpty()) {
				saveMessages(request, messages);
				setContentPage(session, "teacher/TeachClassChoose.jsp");
				return mapping.findForward("Main");
			} 
		}
		
		ActionMessages msgs = sm.patchSeldRegs(selclass, cscode);
		if(! msgs.isEmpty()){
			saveErrors(request, messages);
			setContentPage(session, "teacher/TeachClassChoose.jsp");
			return mapping.findForward("Main");
		}
		
		List<Regs> regslist = sm.findRegsScore(selclass, cscode);
		//log.debug("=======> input->SelInEdit=" + seldlist.size());
		Map initValue = new HashMap();
		
		if(regslist.size() > 0){
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
			List<Regstime> rtList = new ArrayList();
			//rtList = sm.findRegstime(selclass, cscode, "", index);
			rtList = sm.findRegstime(dtimeoid, selclass, cscode, "", index);
			
			if(!rtList.isEmpty()){
				nows=rtList.get(0).getTtime();
				initValue.put("scoreNotUpload", "0");
				log.debug("ScoreUpload->scoreNotUpload:0");
			}else{
				if(index.equals("2")){
					//rtList = sm.findRegstime(selclass, cscode, "", "3");
					rtList = sm.findRegstime(dtimeoid, selclass, cscode, "", "3");
					if(!rtList.isEmpty()){
						//nows=rtList.get(0).getTtime();
						initValue.put("scoreNotUpload", "0");
					}else{
						initValue.put("scoreNotUpload", "1");
					}
				}else if(index.equals("3")){
					//rtList = sm.findRegstime(selclass, cscode, "", "2");
					rtList = sm.findRegstime(dtimeoid, selclass, cscode, "", "2");
					if(!rtList.isEmpty()){
						//nows=rtList.get(0).getTtime();
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
			initValue.put("Dtime_oid", regslist.get(0).getDtimeOid());
			initValue.put("departClass", selclass);
			initValue.put("depClassName", classname);
			initValue.put("cscode", cscode);
			initValue.put("cscodeName", cscodeName);
			initValue.put("opmode", "saveregs");
			
			if(scoretype.equals("2") || scoretype.equals("8")) {
				//List<Rrate> ratelist = sm.findScoreRate(selclass, cscode);
				List<Rrate> ratelist = sm.findScoreRate(dtimeoid);
				if(ratelist.size() == 0) {
					//create Rrate record
						Rrate rrate = sm.createRrate(selclass, cscode);
						session.setAttribute("TchScoreRate", rrate);
				} else if(ratelist.size() > 0) {
					String[] rateArray = new String[10];
					
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
					session.setAttribute("TchScoreRateArray", rateArray);
					session.setAttribute("TchScoreRate", ratelist.get(0));

				}
			}
			if(scoretype.equals("9") || scoretype.equals("8") || scoretype.equals("7") || scoretype.equals("6")) {	
				//log.debug("Entering Score Print!");
				int len = regslist.size();
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
				String[] score19 = new String[len];
				String[] score20 = new String[len];
				String[] score21 = new String[len];
				String[] score22 = new String[len];
				String[] score23 = new String[len];
				
				int scoresint = 0;
				int totalscore = 0;
				int nopass = 0;
				
				Regs regs;
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
				for(Iterator<Regs> regsIter=regslist.iterator(); regsIter.hasNext(); ) {
					regs = regsIter.next();
					studentNos[rcount] = regs.getStudentNo();
					studentNames[rcount] = regs.getStudentName();
					if(regs.getScore01() != null) {
						score01[rcount] = regs.getScore01().toString();
					} else {
						score01[rcount] = "";
					}

					if(regs.getScore02() != null) {
						score02[rcount] = regs.getScore02().toString();
					} else {
						score02[rcount] = "";
					}
					
					if(regs.getScore03() != null) {
						score03[rcount] = regs.getScore03().toString();
					} else {
						score03[rcount] = "";
					}
					
					if(regs.getScore04() != null) {
						score04[rcount] = regs.getScore04().toString();
					} else {
						score04[rcount] = "";
					}
					if(regs.getScore05() != null) {
						score05[rcount] = regs.getScore05().toString();
					} else {
						score05[rcount] = "";
					}
					
					if(regs.getScore06() != null) {
						score06[rcount] = regs.getScore06().toString();
					} else {
						score06[rcount] = "";
					}
					
					if(regs.getScore07() != null) {
						score07[rcount] = regs.getScore07().toString();
					} else {
						score07[rcount] = "";
					}
					
					if(regs.getScore08() != null) {
						score08[rcount] = regs.getScore08().toString();
					} else {
						score08[rcount] = "";
					}
					
					if(regs.getScore09() != null) {
						score09[rcount] = regs.getScore09().toString();
					} else {
						score09[rcount] = "";
					}
					
					if(regs.getScore10() != null) {
						score10[rcount] = regs.getScore10().toString();
					} else {
						score10[rcount] = "";
					}
					
					if(regs.getScore16() != null) {
						score16[rcount] = regs.getScore16().toString();
					} else {
						score16[rcount] = "";
					}
					
					if(regs.getScore17() != null) {
						score17[rcount] = regs.getScore17().toString();
					} else {
						score17[rcount] = "";
					}
					
					if(regs.getScore19() != null) {
						score19[rcount] = regs.getScore19().toString();
					} else {
						score19[rcount] = "";
					}
					
					if(regs.getScore20() != null) {
						score20[rcount] = regs.getScore20().toString();
					} else {
						score20[rcount] = "";
					}
					
					if(regs.getScore21() != null) {
						score21[rcount] = regs.getScore21().toString();
					} else {
						score21[rcount] = "";
					}
					
					if(regs.getScore22() != null) {
						score22[rcount] = regs.getScore22().toString();
					} else {
						score22[rcount] = "";
					}
					
					if(regs.getScore23() != null) {
						score23[rcount] = regs.getScore23().toString();
					} else {
						score23[rcount] = "";
					}
					
					if(scoretype.equals("6")) {
						if(!score19[rcount].equals("")) {
							scoresint = (int)Math.round(regs.getScore19());
						}else {
							scoresint = 0;
						}
						score19[rcount] = "" + scoresint;
						totalscore = totalscore + scoresint;
						if (scoresint < 60){
							nopass++;
							score19[rcount] = score19[rcount] + "*";
						}
						
					}else if(scoretype.equals("7")||scoretype.equals("8")) {
						if(!score23[rcount].equals("")) {
							scoresint = (int)Math.round(regs.getScore23());
						}else {
							scoresint = 0;
						}
						score23[rcount] = "" + scoresint;
						totalscore = totalscore + scoresint;
						if (scoresint < 60){
							nopass++;
							score23[rcount] = score23[rcount] + "*";
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
					//List<Rrate> rrateList = sm.findScoreRate(selclass, cscode);
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
					pmap.put("score19", score19);
					pmap.put("score20", score20);
					pmap.put("score21", score21);
					pmap.put("score22", score22);
					pmap.put("score23", score23);
					session.setAttribute("TchScorePrint", pmap);
				} else if(scoretype.equals("7")) {
					pmap.put("score23", score23);
				} else if(scoretype.equals("6")) {
					pmap.put("score19", score19);
				}
				pmap.put("totalstu",len );
				pmap.put("avgscore", Math.round(totalscore / len));
				pmap.put("pass", len-nopass);
				pmap.put("nopass", nopass);
				pmap.put("now", nows);
				session.setAttribute("TchScorePrint", pmap);

			}
			
			session.setAttribute("TchScoreUploadInfo", initValue);
			session.setAttribute("TchScoreInEdit", regslist);
			if (scoretype.equals("1")) {
				setContentPage(session, "teacher/ScoreMidTermEdit1.jsp");
			} else if (scoretype.equals("2")) {
				session.setMaxInactiveInterval(7200);	//2Hr. for session timeout
				setContentPage(session, "teacher/ScoreFinalTermEdit.jsp");
			} else if (scoretype.equals("3")) {
				setContentPage(session, "teacher/ScoreThisTermEdit.jsp");
			} else if(scoretype.equals("9") || scoretype.equals("8") || scoretype.equals("7") || scoretype.equals("6")) {
				setContentPage(session, "teacher/TeachClassChoose.jsp");
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
		setContentPage(request.getSession(false), "Directory.jsp");
		return mapping.findForward("Main");
	}

	
}
