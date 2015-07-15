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

import sun.util.calendar.BaseCalendar.Date;
import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.dao.ScoreDAO;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Regs;
import tw.edu.chit.model.Rrate;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class ScoreFinalTermEditAction extends BaseLookupDispatchAction{
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("SaveTemp", "saveRegs");
		map.put("Cancel", "cancel");
		map.put("BackMenu", "cancel");
		map.put("SaveSeld","save");
		map.put("BackSaveTemp","back");
		return map;		
	}
	
	private double[] scr01f, scr02f, scr03f, scr04f, scr05f, scr06f, scr07f, scr08f;
	private double[] scr09f, scr10f, scr16f, scr17f, scr19f, scr20f, scr21f, scr22f, scr23f;
	private float[] rrate; 
	private int[] scr23i;
	
	private String[] scorerate,scr01,scr02,scr03,scr04,scr05,scr06,scr07,scr08;
	private String[] scr09,scr10,scr16,scr17,scr19,scr20,scr21,scr22,scr23;
	private int nopass;
	
	/*
	 * 教師確認學期成績無誤,上傳時只存入Seld
	 * 從先前存入session中的資料調存
	 * 
	 * @param 
	 */
	public ActionForward save(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		//DynaActionForm aForm = (DynaActionForm)form;
		
		HttpSession session = request.getSession(false);
		Map initMap = (Map)session.getAttribute("TchScoreUploadInfo");
		
		Map aForm = (Map)session.getAttribute("TchScoreUploadFormMap");
		List<Regs> regslist = (List<Regs>)session.getAttribute("TchScoreInEdit");
		Map calscore = (Map)session.getAttribute("TchCalScore");
		scr21f = (double[])calscore.get("scr21f");
		scr23f = (double[])calscore.get("scr23f");
		double totalscore = 0;
		String scoretype = aForm.get("scoretype").toString();
		String departClass = initMap.get("departClass").toString();
		String cscode = initMap.get("cscode").toString();
		String teacherId = initMap.get("teacherId").toString();
		int dtimeoid = 0;
		
		ScoreManager sm = (ScoreManager)getBean("scoreManager");
		
			ActionMessages errors = new ActionMessages();
			ActionMessages messages = new ActionMessages();
			
			//此處檢查可否上傳期末成績並寫入seld檔,特別使用chkTchScoreUploadOpened("22", departClass);
			messages = sm.chkTchScoreUploadOpened("22", departClass);
			if(!messages.isEmpty()) {
				saveMessages(request, messages);
				setContentPage(session, "teacher/TeachClassChoose.jsp");
				return mapping.findForward("Main");
			} 
			
			log.debug("Teacher Upload scoretype=" + scoretype);
			try {
				
				//Update Seld
				int count = 0;
				dtimeoid = regslist.get(0).getDtimeOid();
				for(Iterator regsIter = regslist.iterator(); regsIter.hasNext();) {
					Regs regscore = (Regs)regsIter.next();
					Seld seld = sm.findSeld(regscore.getDtimeOid(), regscore.getStudentNo());
					if(seld == null) {
						errors.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("Message.updateSeldError"));
						errors.add(ActionMessages.GLOBAL_MESSAGE,  
						new ActionMessage(regscore.getStudentNo() +" 資料不在Seld中 !"));
						break;

					} else {
						if(scr21f[count]==-1.0) {
							errors.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("Message.FinalScoreMustInput"));

							break;
						}
						double[] score = {scr21f[count], scr23f[count]};
						errors = sm.updateSeld(seld, "2", score);
						if (!errors.isEmpty()) {
							errors.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("Message.updateSeldError"));

							break;
						}
					}
					count++;
				}
					
				if (!errors.isEmpty()) {
					
					saveErrors(request, errors);
					// setContentPage(session, "teacher/TeachClassChoose.jsp");
					return mapping.findForward("Main");
				}
				
				Calendar now = Calendar.getInstance();
				String nows = "" + (now.get(Calendar.YEAR)-1911) 
							+ "/" + (now.get(Calendar.MONTH) + 1) 
							+ "/" + now.get(Calendar.DATE) 
							+ " " + now.get(Calendar.HOUR_OF_DAY) 
							+ ":" + now.get(Calendar.MINUTE) 
							+ ":" + now.get(Calendar.SECOND);

				errors = sm.updateRegsTime(dtimeoid, departClass, cscode, teacherId, scoretype, nows);
				if (!errors.isEmpty()) {
					
					saveErrors(request, errors);
				}
				
				
				count = 0;
				nopass = 0;
				String[] studentNames = new String[regslist.size()];
				Regs regs;
				String[] studentNos = (String[]) aForm.get("studentNo");
				int totalstu = regslist.size();
				
				for (Iterator regsIter = regslist.iterator(); regsIter.hasNext();) {
					regs = (Regs)regsIter.next();
					studentNames[count] = regs.getStudentName(); 
					totalscore = totalscore + scr23f[count++];
					if(regs.getScore23() < 60) nopass++;
				}
				
				
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.CreateSuccessful"));
				saveMessages(request, messages);
				
				// aForm.initialize(mapping);
				Map pmap = new HashMap();
				pmap.put("scoretype", scoretype);
				pmap.put("opmode", "Print");
				pmap.put("schoolYear", Toolket.getSysParameter("School_year"));
				pmap.put("schoolTerm", Toolket.getSysParameter("School_term"));
				pmap.put("departClass", departClass);
				pmap.put("depClassName", initMap.get("depClassName").toString());
				pmap.put("teacherName",initMap.get("teacherName").toString());
				pmap.put("cscode",cscode);
				pmap.put("cscodeName",initMap.get("cscodeName").toString());
				pmap.put("studentNo", studentNos);
				pmap.put("studentName", studentNames);
				pmap.put("score", calscore);
				pmap.put("totalstu", totalstu);
				pmap.put("totalscore", totalscore);
				pmap.put("avgscore", Math.round(totalscore / totalstu));
				pmap.put("nopass", nopass);
				pmap.put("pass", (totalstu - nopass));
				pmap.put("now", nows);
				session.setAttribute("TchScoreUploadPrint", pmap);
				
				// session.removeAttribute("TchScoreMidInfo");
				session.removeAttribute("TchScoreInEdit");
				session.removeAttribute("TchScoreUploadFormMap");
				session.setMaxInactiveInterval(Integer.parseInt(session.getAttribute("sessionInterval").toString()));
				setContentPage(session, "teacher/TeachClassChoose.jsp");
				return mapping.findForward("Main");	
			} catch(Exception e) {
				e.printStackTrace();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward("Main");
			}
	}

	public ActionForward saveRegs(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		Map initMap = (Map)session.getAttribute("TchScoreUploadInfo");
		
		String scoretype = aForm.getString("scoretype");
		String departClass = initMap.get("departClass").toString();
		String cscode = initMap.get("cscode").toString();
		String teacherId = initMap.get("teacherId").toString();
		
		ActionMessages messages = new ActionMessages();
		
		messages = validateInputForUpdate(aForm, Toolket.getBundle(request), 0);
		
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			// request.removeAttribute("ScoreInputInit");
			// log.debug("=======> Teacher Input score ValidateError='");
			Map formMap = aForm.getMap();
			
			session.setAttribute("TchScoreUploadFormMap", aForm.getMap());
			return mapping.findForward("Main");
		} else {
			ActionMessages errors = new ActionMessages();
			try {
				List<Regs> regs = (List<Regs>)session.getAttribute("TchScoreInEdit");
				Rrate scorerate = (Rrate)session.getAttribute("TchScoreRate");
				ScoreManager sm = (ScoreManager)getBean("scoreManager");
				
				Map calscore = new HashMap();
				calscore.put("scr01f", scr01f);
				calscore.put("scr02f", scr02f);
				calscore.put("scr03f", scr03f);
				calscore.put("scr04f", scr04f);
				calscore.put("scr05f", scr05f);
				calscore.put("scr06f", scr06f);
				calscore.put("scr07f", scr07f);
				calscore.put("scr08f", scr08f);
				calscore.put("scr09f", scr09f);
				calscore.put("scr10f", scr10f);
				calscore.put("scr16f", scr16f);
				calscore.put("scr17f", scr17f);
				calscore.put("scr19f", scr19f);
				calscore.put("scr20f", scr20f);
				calscore.put("scr21f", scr21f);
				calscore.put("scr22f", scr22f);
				calscore.put("scr23f", scr23f);
				calscore.put("scr23i", scr23i);
				calscore.put("rratef", rrate);
				
				sm.updateRrate(scorerate, rrate);
				
				errors = sm.updateTchScoreInput(aForm.getMap(), regs, calscore);
				if (!errors.isEmpty()) {
					
					saveErrors(request, errors);
					// request.removeAttribute("ScoreInputInit");
					
					session.setAttribute("TchScoreInputFormMap", aForm.getMap());
					//session.setAttribute("ScoreInput", aForm.getStrings("scrinput"));
					return mapping.findForward("Main");
				}
				Calendar now = Calendar.getInstance();
				String nows = "" + (now.get(Calendar.YEAR)-1911) 
							+ "/" + (now.get(Calendar.MONTH) + 1) 
							+ "/" + now.get(Calendar.DATE) 
							+ " " + now.get(Calendar.HOUR_OF_DAY) 
							+ ":" + now.get(Calendar.MINUTE) 
							+ ":" + now.get(Calendar.SECOND);

				/*
				errors = sm.updateRegsTime(departClass, cscode, teacherId, scoretype, nows);
				if (!errors.isEmpty()) {
					
					saveErrors(request, errors);
				}
				*/
				
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.CreateSuccessful"));
				saveMessages(request, messages);
				
				ActionMessages msgs = sm.chkTchScoreUploadOpened(scoretype, departClass);
				if(!msgs.isEmpty()) {
					saveMessages(request, msgs);
					initMap.put("canUpload", false);
				} else {
					initMap.put("canUpload", true);
				}
				
				initMap.put("opmode", "saveseld");
				session.setAttribute("TchCalScore", calscore);
				session.setAttribute("TchScoreUploadInfo", initMap);
				// session.removeAttribute("TchScoreMidInEdit");
				session.setAttribute("TchScoreUploadFormMap", aForm.getMap());
				//session.removeAttribute("TchScoreInputFormMap");
				return mapping.findForward("Main");	
			} catch(Exception e) {
				e.printStackTrace();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				session.setAttribute("TchScoreUploadFormMap", aForm.getMap());
				return mapping.findForward("Main");
			}
		}
	}

	public ActionForward back(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		Map initMap = (Map)session.getAttribute("TchScoreUploadInfo");
		initMap.put("opmode", "saveregs");
		
		return mapping.findForward("Main");
	}

	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		//session.removeAttribute("TchScoreMidInfo");
		// session.removeAttribute("TchScoreMidInEdit");
		session.removeAttribute("TchScoreUploadFormMap");
		session.setMaxInactiveInterval(Integer.parseInt(session.getAttribute("sessionInterval").toString()));
		setContentPage(request.getSession(false), "teacher/TeachClassChoose.jsp");
		return mapping.findForward("Main");
	}

	
	
	//Private Method Here ============================>>
	/**
	 * 檢查輸入的資料是否為有效的更新資料
	 * 第三個參數決定是否檢查期中考成績是否已存在
	 * 如果不存在則無法自動計算學期成績
	 * 
	 * @param DynaActionForm form
	 * @param ResourceBundle bundle
	 * @param int chkmidscore
	 */

	private ActionMessages validateInputForUpdate(DynaActionForm form, ResourceBundle bundle, int chkmidscore) {
		
		ActionMessages errors = new ActionMessages();
		validateFieldFormat(form, errors, bundle, chkmidscore);
		ScoreManager sm = (ScoreManager)getBean("scoreManager");
		// sm.validateScoreHistCreate(form.getMap(), errors, bundle);
		return errors;
	}
	
	private void validateFieldFormat(DynaActionForm form, ActionMessages errors, ResourceBundle bundle, int chkmidscore) {
		scorerate = form.getStrings("scorerate");

		scr01 = form.getStrings("scr01");
		scr02 = form.getStrings("scr02");
		scr03 = form.getStrings("scr03");
		scr04 = form.getStrings("scr04");
		scr05 = form.getStrings("scr05");
		scr06 = form.getStrings("scr06");
		scr07 = form.getStrings("scr07");
		scr08 = form.getStrings("scr08");
		scr09 = form.getStrings("scr09");
		scr10 = form.getStrings("scr10");
		scr16 = form.getStrings("scr16");
		scr17 = form.getStrings("scr17");
		scr19 = form.getStrings("scr19");
		scr20 = form.getStrings("scr20");
		scr21 = form.getStrings("scr21");
		scr22 = form.getStrings("scr22");
		scr23 = form.getStrings("scr23");
		
		scr01f = new double[scr01.length];
		scr02f = new double[scr02.length];
		scr03f = new double[scr03.length];
		scr04f = new double[scr04.length];
		scr05f = new double[scr05.length];
		scr06f = new double[scr06.length];
		scr07f = new double[scr07.length];
		scr08f = new double[scr08.length];
		scr09f = new double[scr09.length];
		scr10f = new double[scr10.length];
		scr16f = new double[scr16.length];
		scr17f = new double[scr17.length];
		scr19f = new double[scr19.length];
		scr20f = new double[scr20.length];
		scr21f = new double[scr21.length];
		scr22f = new double[scr22.length];
		scr23f = new double[scr23.length];
		scr23i = new int[scr23.length];
		
		String[] stus = form.getStrings("studentNo");
		rrate = new float[scorerate.length];
		double total_rate = 0d;
		int kount = 0;
		double total_nscore = 0;
		double percent_nscore = 0;
		double percent_mscore = 0;
		double percent_fscore = 0;
		double term_score = 0;
		nopass = 0;
		
		//check general (normal) score rate
		for(int j=0; j<rrate.length; j++) {
			if(!scorerate[j].equals("")) {
				try {
					rrate[j] = Float.parseFloat(scorerate[j]);
					//log.debug("rrate[" + j + "]:"+ rrate[j]);
					//log.debug("total_rate : " + (float)total_rate);
					total_rate = (double)((float)total_rate + (float)rrate[j]);
					//log.debug("total_rate : " + total_rate);
					if(total_rate >1) {
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.TotalRateCantLarge1"));
						break;
					}
				} catch(Exception e) {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidScoreRateFormat"));							
					return;
				}
			}
		}
		
		// log.debug("rate3:" + rrate[2] + " rate4:" +rrate[3] + " total_rate : " + Double.valueOf(total_rate));
		if(!((total_rate > 0.999 && total_rate < 1.001) || (total_rate == (double)(0)))) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.TotalRateMustEqual1"));
			return;
		}
		
		if(chkmidscore == 1) {
			kount = 0;
			for(int k=0; k<scr19.length; k++) {
				if(scr19[k].equals("")) kount++;
				else {
					try {
						if(Double.parseDouble(scr19[k]) == 0) kount++;
					} catch(Exception e) {
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));							
						return;
					}
				}
			}
			if(kount == stus.length) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.NoMidleTermScore"));
				return;
			}
			
		}
		
		for(int i=0; i<stus.length; i++) {
			kount = 0;
			total_nscore = 0;
			
			try {
				if(!scr01[i].trim().equals("")) {
					scr01f[i] = Double.parseDouble(scr01[i]);
					if(total_rate == 0) {
						total_nscore = total_nscore + scr01f[i];
						kount++;
					} else {
						total_nscore = total_nscore + (scr01f[i]*rrate[0]);
					}
				}
				else {
					scr01f[i] = -1.0;
				}
				
				if(!scr02[i].trim().equals("")) {
					scr02f[i] = Double.parseDouble(scr02[i]);
					if(total_rate == 0) {
						total_nscore = total_nscore + scr02f[i];
						kount++;
					} else {
						total_nscore = total_nscore + (scr02f[i]*rrate[1]);
					}
				}
				else {
					scr02f[i] = -1.0;
				}
				
				if(!scr03[i].trim().equals("")) {
					scr03f[i] = Double.parseDouble(scr03[i]);
					if(total_rate == 0) {
						total_nscore = total_nscore + scr03f[i];
						kount++;
					} else {
						total_nscore = total_nscore + (scr03f[i]*rrate[2]);
					}
				}
				else {
					scr03f[i] = -1.0;
				}
				
				if(!scr04[i].trim().equals("")) {
					scr04f[i] = Double.parseDouble(scr04[i]);
					if(total_rate == 0) {
						total_nscore = total_nscore + scr04f[i];
						kount++;
					} else {
						total_nscore = total_nscore + (scr04f[i]*rrate[3]);
					}
				}
				else {
					scr04f[i] = -1.0;
				}
				
				if(!scr05[i].trim().equals("")) {
					scr05f[i] = Double.parseDouble(scr05[i]);
					if(total_rate == 0) {
						total_nscore = total_nscore + scr05f[i];
						kount++;
					} else {
						total_nscore = total_nscore + (scr05f[i]*rrate[4]);
					}
				}
				else {
					scr05f[i] = -1.0;
				}
				
				if(!scr06[i].trim().equals("")) {
					scr06f[i] = Double.parseDouble(scr06[i]);
					if(total_rate == 0) {
						total_nscore = total_nscore + scr06f[i];
						kount++;
					} else {
						total_nscore = total_nscore + (scr06f[i]*rrate[5]);
					}
				}
				else {
					scr06f[i] = -1.0;
				}
				
				if(!scr07[i].trim().equals("")) {
					scr07f[i] = Double.parseDouble(scr07[i]);
					if(total_rate == 0) {
						total_nscore = total_nscore + scr07f[i];
						kount++;
					} else {
						total_nscore = total_nscore + (scr07f[i]*rrate[6]);
					}
				}
				else {
					scr07f[i] = -1.0;
				}
				
				if(!scr08[i].trim().equals("")) {
					scr08f[i] = Double.parseDouble(scr08[i]);
					if(total_rate == 0) {
						total_nscore = total_nscore + scr08f[i];
						kount++;
					} else {
						total_nscore = total_nscore + (scr08f[i]*rrate[7]);
					}
				}
				else {
					scr08f[i] = -1.0;
				}
				
				if(!scr09[i].trim().equals("")) {
					scr09f[i] = Double.parseDouble(scr09[i]);
					if(total_rate == 0) {
						total_nscore = total_nscore + scr09f[i];
						kount++;
					} else {
						total_nscore = total_nscore + (scr09f[i]*rrate[8]);
					}
				}
				else {
					scr09f[i] = -1.0;
				}
				
				if(!scr10[i].trim().equals("")) {
					scr10f[i] = Double.parseDouble(scr10[i]);
					if(total_rate == 0) {
						total_nscore = total_nscore + scr10f[i];
						kount++;
					} else {
						total_nscore = total_nscore + (scr10f[i]*rrate[9]);
					}
				}
				else {
					scr10f[i] = -1.0;
				}
				
				if(total_rate == 0) total_nscore = total_nscore / kount;
				//log.debug("total_nscore=" + total_nscore);
				total_nscore = (Math.round(total_nscore * 10.0d)) / 10.0d;
				
				if(!scr16[i].trim().equals("")) {
					scr16f[i] = Double.parseDouble(scr16[i]);
					if(total_nscore != scr16f[i]) {
						log.debug("Different result between front & backend caculation of normal score!");
						log.debug("front value:" + scr16f[i] + ", backend value:" + total_nscore);
						scr16f[i] = total_nscore;
					}
				}
				
				percent_nscore = Math.round(Math.round(total_nscore * 0.3d * 1000.0d)/100d) / 10.0d;
				if(!scr17[i].trim().equals("")) {
					scr17f[i] = Double.parseDouble(scr17[i]);
					if(percent_nscore != scr17f[i]) {
						log.debug("Different result between front & backend caculation of normal score percentage!");
						log.debug("front value:" + scr17f[i] + ", backend value:" + percent_nscore);
						scr17f[i] = percent_nscore;
					}
				}

				if(!scr19[i].trim().equals("")) {
					scr19f[i] = Double.parseDouble(scr19[i]);
					percent_mscore = Math.round(scr19f[i] * 0.3d * 10.0d) / 10.0d;
				} else {
					percent_mscore = 0;
					scr19f[i] = -1.0;
				}
				
				
				if(!scr20[i].trim().equals("")) {
					scr20f[i] = Double.parseDouble(scr20[i]);
					if(percent_mscore != scr20f[i]) {
						log.debug("Different result between front & backend caculation of middle score percentage!");
						log.debug("front value:" + scr20f[i] + ", backend value:" + percent_mscore);
						scr20f[i] = percent_mscore;
					}
				} else {
					if(scr19[i].trim().equals("")) scr20f[i] = -1.0;
					else scr20f[i] = percent_mscore;
				}

				
				if(!scr21[i].trim().equals("")) {
					scr21f[i] = Double.parseDouble(scr21[i]);
					percent_fscore = Math.round(Math.round(scr21f[i] * 0.4d * 1000.0d)/100d) / 10.0d;
				} else {
					percent_fscore = 0;
					scr21f[i] = -1.0;
				}
				
				if(!scr22[i].trim().equals("")) {
					scr22f[i] = Double.parseDouble(scr22[i]);
					if(percent_fscore != scr22f[i]) {
						log.debug("Different result between front & backend caculation of final score percentage!");
						log.debug("front value:" + scr22f[i] + ", backend value:" + percent_fscore);
						scr22f[i] = percent_fscore;
					}
				} else {
					if(scr21[i].trim().equals("")) scr22f[i] = -1.0;
					else scr22f[i] = percent_fscore;
				}

				term_score = Math.round(percent_nscore + percent_mscore + percent_fscore);
				if(!scr23[i].trim().equals("")) {
					scr23f[i] = Double.parseDouble(scr23[i]);
					if(term_score != scr23f[i]) {
						log.debug("Different result between front & backend caculation of term score percentage!");
						log.debug("front value:" + scr23f[i] + ", backend value:" + term_score);
						scr23f[i] = term_score;
					}
					
					if(term_score < 60) nopass++;
				}
				scr23i[i] = (int)Math.round(scr23f[i]);
				
				if(scr01f[i]>100 || scr02f[i]>100 || scr03f[i]>100 || scr04f[i]>100 || scr05f[i]>100 || scr06f[i]>100 
						 || scr07f[i]>100 || scr08f[i]>100 || scr09f[i]>100 || scr10f[i]>100 || scr16f[i]>100 || scr17f[i]>100
						 || scr19f[i]>100 || scr20f[i]>100 || scr21f[i]>100 || scr22f[i]>100 || scr23f[i]>100) {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidScoreFormat", stus[i]));							
					break;
				}
				
			} catch(Exception e) {
				e.printStackTrace();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));							
			}
		}
		log.debug("scr16 size = " + scr16.length);

	}

}
