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
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class ScoreMidTermEdit1Action extends BaseLookupDispatchAction {
	private double[] scr19f, scr20f;
	
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("StartUpload","save");
		map.put("Cancel", "cancel");
		return map;		
	}
	
	
	public ActionForward save(ActionMapping mapping,
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
		int dtimeoid = 0;
		ActionMessages messages = validateInputForUpdate(aForm, Toolket.getBundle(request));
		
		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			// request.removeAttribute("ScoreInputInit");
			// log.debug("=======> Teacher Input score ValidateError='");
	
			session.setAttribute("TchScoreUploadFormMap", aForm.getMap());
			//session.setAttribute("ScoreInput", aForm.getStrings("scrinput"));
			return mapping.findForward("Main");
		} else {
			ActionMessages errors = new ActionMessages();
			try {
				List<Regs> regs = (List<Regs>)session.getAttribute("TchScoreInEdit");
				ScoreManager sm = (ScoreManager)getBean("scoreManager");
				dtimeoid = regs.get(0).getDtimeOid();
				
				Map calscore = new HashMap();
				//log.debug("ScoreMidTermEdit1Action->scr19f.size():" + scr19f.length);
				calscore.put("scr19f", scr19f);
				calscore.put("scr20f", scr20f);

				//errors = sm.updateScoreInput(aForm.getMap(), scores);
				errors = sm.updateTchScoreInput(aForm.getMap(), regs, calscore);
				if (!errors.isEmpty()) {
					
					saveErrors(request, errors);
					// request.removeAttribute("ScoreInputInit");
					
					session.setAttribute("TchScoreUploadFormMap", aForm.getMap());
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

				errors = sm.updateRegsTime(dtimeoid, departClass, cscode, teacherId, scoretype, nows);
				if (!errors.isEmpty()) {
					
					saveErrors(request, errors);
				}
				
				int count = 0;
				String[] studentNames = new String[regs.size()];
				Regs myregs;
				String[] score = (String[]) aForm.getMap().get("scr19");
				String[] studentNos = (String[]) aForm.getMap().get("studentNo");
				int scoresint = 0;
				int totalscore = 0;
				int totalstu = score.length;
				int nopass = 0;
				
				for (Iterator regsIter = regs.iterator(); regsIter.hasNext();) {
					myregs = (Regs)regsIter.next();
					studentNames[count++] = myregs.getStudentName(); 
				}
				
				for (int i=0; i < score.length; i++) {
					scoresint = Integer.parseInt(score[i]);
					totalscore = totalscore + scoresint;
					if (scoresint < 60){
						nopass++;
						score[i] = score[i] + "*";
					}
				}
				
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.CreateSuccessful"));
				saveMessages(request, messages);
				aForm.initialize(mapping);
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
				pmap.put("score", score);
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

	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		//session.removeAttribute("TchScoreMidInfo");
		//session.removeAttribute("TchScoreMidInEdit");
		session.removeAttribute("TchScoreUploadFormMap");
		setContentPage(request.getSession(false), "teacher/TeachClassChoose.jsp");
		return mapping.findForward("Main");
	}

	
	
	//Private Method Here ============================>>

	private ActionMessages validateInputForUpdate(DynaActionForm form, ResourceBundle bundle) {
		
		ActionMessages errors = new ActionMessages();
		validateFieldFormat(form, errors, bundle);
		ScoreManager sm = (ScoreManager)getBean("scoreManager");
		// sm.validateScoreHistCreate(form.getMap(), errors, bundle);
		return errors;
	}
	
	private void validateFieldFormat(DynaActionForm form, ActionMessages errors, ResourceBundle bundle) {

		String[] buff = form.getStrings("scr19");
		String[] stus = form.getStrings("studentNo");
		scr19f = new double[buff.length];
		scr20f = new double[buff.length];
		
		for(int i=0; i<buff.length; i++) {
			if(buff[i].equals("")) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.FieldCantEmpty", bundle.getString("Score")));
				break;
			} else {
				try {
					scr19f[i] = (double)Math.round(Double.parseDouble(buff[i]));
					scr20f[i] = Math.round(scr19f[i]* 0.3 * 10) / 10.0;
					if(scr19f[i] > 100) {
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidScoreFormat", stus[i]));							
						break;
					}
				} catch(Exception e) {
					e.printStackTrace();
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.InvalidScoreFormat", stus[i]));							
				}
			}
		}
	}

}
