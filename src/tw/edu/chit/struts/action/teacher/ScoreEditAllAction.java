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
import javax.servlet.jsp.*;

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

public class ScoreEditAllAction extends BaseLookupDispatchAction{
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("Cancel", "cancel");
		map.put("Save","save");
		map.put("SaveAndUpload","save");
		return map;		
	}
	
	private double[] scr01f, scr02f, scr03f, scr04f, scr05f, scr06f, scr07f, scr08f;
	private double[] scr09f, scr10f, scr16f, scr17f, scr18f, scrf, scr1f, scr2f, scr3f;
	private float[] rrate; 
	private int[] scri;
	
	private String[] scorerate,scr01,scr02,scr03,scr04,scr05,scr06,scr07,scr08;
	private String[] scr09,scr10,scr16,scr17,scr18,scr,scr1,scr2,scr3;
	private int nopass;
	
	/*
	 * 教師確認學期成績無誤,上傳時只存入Seld
	 * 從先前存入session中的資料調存
	 * 
	 * @param 
	 */
	//private PageContext pageContext ;
	//private JspFactory _jspxFactory = null;
	
	public ActionForward save(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		//_jspxFactory = JspFactory.getDefaultFactory(); 
		//pageContext = _jspxFactory.getPageContext(this.getServlet(), request, response, 
			   //   null, true, 8192, true); 

		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		Map initMap = (Map)session.getAttribute("TchScoreUploadInfo");
		
		String scoretype = aForm.getString("scoretype");
		String openMode = aForm.getString("openMode");
		int dtimeoid = 0;
		if(!"".equals(aForm.getString("dtimeoid").trim())){
			dtimeoid = Integer.parseInt(aForm.getString("dtimeoid"));
		}
		
		String departClass = initMap.get("departClass").toString();
		String cscode = initMap.get("cscode").toString();
		String teacherId = initMap.get("teacherId").toString();
		String[] students = aForm.getStrings("studentNo");
		List<Seld> seld = (List<Seld>)session.getAttribute("TchScoreInEdit");

		ActionMessages messages = new ActionMessages();
		
		if(seld != null){
			if(dtimeoid==0) dtimeoid=seld.get(0).getDtimeOid();
			
			if(students.length != seld.size() || seld.isEmpty()){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Course.errorN1", "請勿開啟多視窗輸入成績!"));
				saveMessages(request, messages);
				session.removeAttribute("TchScoreInEdit");
				session.removeAttribute("TchScoreUploadPrint");
				session.removeAttribute("TchScorePrint");
				session.removeAttribute("TchScoreUploadFormMap");
				setContentPage(session, "teacher/TeachClassChoose.jsp");
			}else{
				int knt = 0;
				String stNo = "";
				for(Iterator<Seld> seldIter=seld.iterator(); seldIter.hasNext();){
					stNo = seldIter.next().getStudentNo();
					if(!stNo.equals(students[knt++].trim())){
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Course.errorN1",
								"學號核對錯誤：[" + stNo + "],[" + students[knt-1].trim() + "]!"));
						saveMessages(request, messages);
						session.removeAttribute("TchScoreInEdit");
						session.removeAttribute("TchScoreUploadPrint");
						session.removeAttribute("TchScorePrint");
						session.removeAttribute("TchScoreUploadFormMap");
						setContentPage(session, "teacher/TeachClassChoose.jsp");
						break;
					}
				}
				if(!messages.isEmpty()){
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Course.errorN1", "請勿開啟多視窗輸入成績!"));
				}
			}
			log.debug("students:" + students.length + ", seld:" + seld.size());
			
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Course.errorN1", "請勿開啟多視窗輸入成績!"));
			saveMessages(request, messages);
			session.removeAttribute("TchScoreInEdit");
			session.removeAttribute("TchScoreUploadPrint");
			session.removeAttribute("TchScorePrint");
			session.removeAttribute("TchScoreUploadFormMap");
			setContentPage(session, "teacher/TeachClassChoose.jsp");
		}
		if(!messages.isEmpty()){
			return mapping.findForward("Main");
		}
		
		messages = validateInputForUpdate(aForm, Toolket.getBundle(request));
		
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
				calscore.put("scr18f", scr18f);
				calscore.put("scrf", scrf);
				calscore.put("scr1f", scr1f);
				calscore.put("scr2f", scr2f);
				calscore.put("scr3f", scr3f);
				calscore.put("scri", scri);
				calscore.put("rratef", rrate);
				
				if(openMode.charAt(2) == '1')
					sm.updateRrate(scorerate, rrate);
				
				errors = sm.updateScoreInputAll(aForm.getMap(), seld, calscore);
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

				if(openMode.equals("000") || openMode.charAt(0) == '1' || openMode.charAt(1) == '1'){
					/*
					 * 2007/07/03 會議決定教師可保留成績為空白且系統不自動補零,有空白視同完成上傳
					 * 原:檢查期末或學期成績只要有一個為空白就表示未完成上傳
					 */
					ActionMessages msg = new ActionMessages();
					for(int i = 0; i<students.length; i++){
						if(openMode.equals("000")){				//學期
							if(scr[i].trim().equals("")){
								msg.add(ActionMessages.GLOBAL_MESSAGE, 
										new ActionMessage("MessageN1", "請注意!有學生成績未填入!"));
								break;
							}
						}else if(openMode.charAt(0) == '1'){	//期末
							if(scr3[i].trim().equals("")){
								msg.add(ActionMessages.GLOBAL_MESSAGE, 
										new ActionMessage("MessageN1", "請注意!有學生成績未填入!"));
								break;
							}
						}else if(openMode.charAt(1) == '1'){	//期中
							if(scr2[i].trim().equals("")){
								msg.add(ActionMessages.GLOBAL_MESSAGE, 
										new ActionMessage("MessageN1", "請注意!有學生成績未填入!"));
								break;
							}							
						}
						
					}
					saveMessages(request, msg);
					
					//if(messages.isEmpty()){
						errors = sm.updateRegsTime(dtimeoid, departClass, cscode, teacherId, scoretype, nows);
						if (!errors.isEmpty()) {
							saveErrors(request, errors);
						}
						
					//}
				}
				if(messages.isEmpty()){
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.CreateSuccessful"));
					saveMessages(request, messages);

					//產生列印報表資料
					String[] studentNos = (String[]) aForm.get("studentNo");
					int totalstu = seld.size();
					
					int count = 0;
					int nopass = 0;
					double totalscore = 0d;
					String[] studentNames = new String[seld.size()];
					
					Seld myseld = new Seld();
					for (Iterator<Seld> seldIter = seld.iterator(); seldIter.hasNext();) {
						myseld = seldIter.next();
						studentNames[count] = myseld.getStudentName(); 
						if(scoretype.equals("1")){
							totalscore = totalscore + scr2f[count];
							if(scr2f[count] < 60) nopass++;
						}else if(scoretype.equals("2")||scoretype.equals("3")){
							totalscore = totalscore + scrf[count];
							if(scrf[count] < 60) nopass++;
						}
						count++;
					}
					
					Map pmap = new HashMap();
					pmap.put("scoretype", scoretype);
					pmap.put("openMode", openMode);
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
					
					session.removeAttribute("TchScoreInEdit");
					session.removeAttribute("TchScoreUploadFormMap");
					session.setAttribute("onTchUploadPrint", "1");
					session.removeAttribute("sesscnt");
					session.setMaxInactiveInterval(Integer.parseInt(session.getAttribute("sessionInterval").toString()));

					String contentPage = "";
					if(openMode.equals("101")){
						contentPage = "teacher/ScoreUploadPrint.jsp";
					}else if(scoretype.equals("1")){
						contentPage = "teacher/ScoreMidTermPrint1.jsp";
					}else if(scoretype.equals("3")){
						contentPage = "teacher/ScoreThisTermPrint.jsp";
					}else if(openMode.equals("001")){
						setContentPage(session, "teacher/TeachClassChoose.jsp");
						return mapping.findForward("Main");
					} 
					setContentPage(session, contentPage);
					return mapping.findForward("Blank");	
				}
				
				session.removeAttribute("sesscnt");
				session.removeAttribute("TchScoreUploadPrint");
				session.removeAttribute("TchScorePrint");
				session.setMaxInactiveInterval(Integer.parseInt(session.getAttribute("sessionInterval").toString()));
				setContentPage(session, "teacher/TeachClassChoose.jsp");
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
		session.removeAttribute("TchScoreInEdit");
		if(session.getAttribute("onTchUploadPrint")!=null){
			session.removeAttribute("onTchUploadPrint");
		}else{
			session.removeAttribute("TchScoreUploadPrint");
		}
		session.removeAttribute("TchScorePrint");
		session.removeAttribute("TchScoreUploadFormMap");
		session.removeAttribute("TchScoreMidInPrint");
		session.removeAttribute("TchScoreFinalInPrint");
		session.removeAttribute("sesscnt");
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

	private ActionMessages validateInputForUpdate(DynaActionForm form, ResourceBundle bundle) {
		
		ActionMessages errors = new ActionMessages();
		validateFieldFormat(form, errors, bundle);
		ScoreManager sm = (ScoreManager)getBean("scoreManager");
		// sm.validateScoreHistCreate(form.getMap(), errors, bundle);
		return errors;
	}
	
	private void validateFieldFormat(DynaActionForm form, ActionMessages errors, ResourceBundle bundle) {
		String openMode = form.getString("openMode");
		scorerate = form.getStrings("scorerate");

		scr = form.getStrings("scr");
		scr1 = form.getStrings("scr1");
		scr2 = form.getStrings("scr2");
		scr3 = form.getStrings("scr3");
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
		scr18 = form.getStrings("scr18");
		
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
		scr18f = new double[scr18.length];
		scrf = new double[scr.length];
		scr1f = new double[scr1.length];
		scr2f = new double[scr2.length];
		scr3f = new double[scr3.length];
		scri = new int[scr.length];
		
		String[] stus = form.getStrings("studentNo");
		rrate = new float[scorerate.length];
		double total_rate = 0d, all_rate = 0d;
		double rate_nrm = 0d, rate_mdl = 0d, rate_fnl = 0d;
		int kount = 0;
		double total_nscore = 0;
		double percent_nscore = 0;
		double percent_mscore = 0;
		double percent_fscore = 0;
		double term_score = 0;
		nopass = 0;
		
		for(int j=10; j<13; j++) {
			if(!scorerate[j].equals("")) {
				try {
					rrate[j] = Float.parseFloat(scorerate[j]);
					all_rate = (double)((float)all_rate + (float)rrate[j]);
					if(total_rate >1) {
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.AllRateCantLarge1"));
						break;
					}
				} catch(Exception e) {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidScoreRateFormat"));							
					return;
				}
			}
		}
		if(!(all_rate > 0.999 && all_rate < 1.001)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.AllRateMustEqual1"));
			return;
		}
		rate_nrm = rrate[10];
		rate_mdl = rrate[11];
		rate_fnl = rrate[12];
		
		//only check TermScore
		if(openMode.equals("000")){
			kount = 0;
			for(int k=0; k<scr.length; k++) {
				if(scr[k].equals("")){ 
					//scrf[k] = -1.0;
					scrf[k] = 0;
					scri[k] = 0; 
					kount++;
				}else {
					try {
						scrf[k] = Double.parseDouble(scr[k]);
						scri[k] = (int)Math.round(scrf[k]);
						if(scrf[k] == 0d) kount++;
					} catch(Exception e) {
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));							
						return;
					}
				}
			}
			/*
			if(kount == stus.length) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.NoThisTermScore"));
				return;
			}
			*/
			return;
		}
		
		if(openMode.equals("010")) {
			kount = 0;
			for(int k=0; k<scr2.length; k++) {
				if(scr2[k].equals("")){
					//scr2f[k] = -1.0;
					scr2f[k] = 0;
					percent_mscore = 0;
					kount++;
				}
				else {
					try {
						scr2f[k] = Double.parseDouble(scr2[k]);
						percent_mscore = Math.round(Math.round(scr2f[k] * rate_mdl * 1000.0d)/100d) / 10.0d;
						if(scr2f[k] == 0d) kount++;
					} catch(Exception e) {
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));							
						return;
					}
				}
				
				if(!scr17[k].trim().equals("")) {
					scr17f[k] = Double.parseDouble(scr17[k]);
					if(percent_mscore != scr17f[k]) {
						log.debug("Different result between front & backend caculation of middle score percentage!");
						log.debug("front value:" + scr17f[k] + ", backend value:" + percent_mscore);
						scr17f[k] = percent_mscore;
					}
				} else {
					if(scr2[k].trim().equals("")) {
						//scr17f[k] = -1.0;
						scr17f[k] = 0;
					}
					else scr17f[k] = percent_mscore;
				}
			}
			/*
			if(kount == stus.length) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.NoMidleTermScore"));
				return;
			}
			*/
			return;			
		}
		
		if(openMode.equals("100")) {
			kount = 0;
			for(int k=0; k<scr3.length; k++) {
				if(scr3[k].equals("")){
					//scr3f[k] = -1.0;
					scr3f[k] = 0;
					percent_fscore = 0;
					kount++;
				}
				else {
					try {
						scr3f[k] = Double.parseDouble(scr3[k]);
						percent_fscore = Math.round(Math.round(scr3f[k] * rate_fnl * 1000.0d)/100d) / 10.0d;
						if(scr3f[k] == 0d) kount++;
					} catch(Exception e) {
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));							
						return;
					}
				}
				
				if(!scr18[k].trim().equals("")) {
					scr18f[k] = Double.parseDouble(scr18[k]);
					if(percent_fscore != scr18f[k]) {
						log.debug("Different result between front & backend caculation of middle score percentage!");
						log.debug("front value:" + scr18f[k] + ", backend value:" + percent_mscore);
						scr18f[k] = percent_fscore;
					}
				} else {
					if(scr3[k].trim().equals("")){
						//scr18f[k] = -1.0;
						scr18f[k] = 0;
					}
					else scr18f[k] = percent_fscore;
				}
			}
			/*
			if(kount == stus.length) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.NoFinalTermScore"));
				return;
			}
			*/
			return;
		}
		
		
		if(openMode.charAt(2) == '1'){
		//check general (normal) score rate
			for(int j=0; j<rrate.length-3; j++) {
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
					
					if(!scr1[i].trim().equals("")) {
						scr1f[i] = Double.parseDouble(scr1[i]);
						if(total_nscore != scr1f[i]) {
							log.debug("Different result between front & backend caculation of normal score!");
							log.debug("front value:" + scr1f[i] + ", backend value:" + total_nscore);
							scr1f[i] = total_nscore;
						}
					}
					
					percent_nscore = Math.round(Math.round(total_nscore * rate_nrm * 1000.0d)/100d) / 10.0d;
					if(!scr16[i].trim().equals("")) {
						scr16f[i] = Double.parseDouble(scr16[i]);
						if(percent_nscore != scr16f[i]) {
							log.debug("Different result between front & backend caculation of normal score percentage!");
							log.debug("front value:" + scr16f[i] + ", backend value:" + percent_nscore);
							scr16f[i] = percent_nscore;
						}
					}
	
					
					if(!scr2[i].trim().equals("")) {
						scr2f[i] = Double.parseDouble(scr2[i]);
						percent_mscore = Math.round(scr2f[i] * rate_mdl * 10.0d) / 10.0d;
					} else {
						percent_mscore = 0;
						scr2f[i] = -1.0;
					}
					
					
					if(!scr17[i].trim().equals("")) {
						scr17f[i] = Double.parseDouble(scr17[i]);
						if(percent_mscore != scr17f[i]) {
							log.debug("Different result between front & backend caculation of middle score percentage!");
							log.debug("front value:" + scr17f[i] + ", backend value:" + percent_mscore);
							scr17f[i] = percent_mscore;
						}
					} else {
						if(scr2[i].trim().equals("")) scr17f[i] = -1.0;
						else scr17f[i] = percent_mscore;
					}

					
					if(!scr3[i].trim().equals("")) {
						scr3f[i] = Double.parseDouble(scr3[i]);
						percent_fscore = Math.round(Math.round(scr3f[i] * rate_fnl * 1000.0d)/100d) / 10.0d;
					} else {
						percent_fscore = 0;
						scr3f[i] = -1.0;
					}
					
					if(!scr18[i].trim().equals("")) {
						scr18f[i] = Double.parseDouble(scr18[i]);
						if(percent_fscore != scr18f[i]) {
							log.debug("Different result between front & backend caculation of final score percentage!");
							log.debug("front value:" + scr18f[i] + ", backend value:" + percent_fscore);
							scr18f[i] = percent_fscore;
						}
					} else {
						if(scr3[i].trim().equals("")) scr18f[i] = -1.0;
						else scr18f[i] = percent_fscore;
					}
	
					term_score = Math.round(percent_nscore + percent_mscore + percent_fscore);
					if(!scr[i].trim().equals("")) {
						scrf[i] = Double.parseDouble(scr[i]);
						if(term_score != scrf[i]) {
							log.debug("Different result between front & backend caculation of term score percentage!");
							log.debug("front value:" + scrf[i] + ", backend value:" + term_score);
							scrf[i] = term_score;
						}
						
						if(term_score < 60) nopass++;
					}
					scri[i] = (int)Math.round(scrf[i]);
					
					if(scr01f[i]>100 || scr02f[i]>100 || scr03f[i]>100 || scr04f[i]>100 || scr05f[i]>100 || scr06f[i]>100 
							 || scr07f[i]>100 || scr08f[i]>100 || scr09f[i]>100 || scr10f[i]>100 || scr16f[i]>100 || scr17f[i]>100
							 || scr18f[i]>100 || scrf[i]>100 || scr1f[i]>100 || scr2f[i]>100 || scr3f[i]>100) {
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidScoreFormat", stus[i]));							
						break;
					}
					
				} catch(Exception e) {
					e.printStackTrace();
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Exception.generic", e.getMessage()));							
				}
			}
			//log.debug("scr16 size = " + scr16.length);
		}

	}

}
