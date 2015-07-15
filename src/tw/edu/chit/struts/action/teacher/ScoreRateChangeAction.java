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

public class ScoreRateChangeAction extends BaseLookupDispatchAction {
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
			 {

			HttpSession session = request.getSession(false);
			DynaActionForm aForm = (DynaActionForm)form;
			session.setAttribute("sessionInterval", session.getMaxInactiveInterval());

			UserCredential user = (UserCredential)session.getAttribute("Credential");
			
			String teacherName = user.getMember().getName();
			String teacherId = user.getMember().getAccount();
			String departClass = "";
			String cscode = "";
			
			ScoreManager sm = (ScoreManager)getBean("scoreManager");
			List<Dtime> dtimelist = sm.findDtimeTeacherByTeacher(teacherId);
			
			String campusNmae = "";
			List<Optime1> opList = sm.findOptime("0");
			
			Map initValue = new HashMap();
			initValue.put("teacherName", teacherName);
			initValue.put("teacherId", teacherId);
			initValue.put("optime", opList);
			session.setAttribute("ScoreRateChangeInfo", initValue);

			List<Map> dtLists = new ArrayList<Map>();
			Map dtMap = new HashMap();
			float[] scorerate = {0.3f, 0.3f, 0.4f};
			session.setAttribute("defaultRate", scorerate);

			Calendar now = Calendar.getInstance();
			for(Dtime dtime:dtimelist){
				dtMap = new HashMap();
				dtMap.put("oid", dtime.getOid());
				dtMap.put("departClass", dtime.getDepartClass());
				dtMap.put("cscode", dtime.getCscode());
				dtMap.put("chiName2", dtime.getChiName2());
				dtMap.put("className2", dtime.getClassName2());
				
				Calendar limit = (Toolket.getDateOfWeek(dtime.getDepartClass().substring(0, 2), 4))[0];
				if(now.compareTo(limit) >= 0){
					dtMap.put("updateable", false);
					//session.setAttribute("TchScoreRateUpdatable", "0");
				}else{
					dtMap.put("updateable", true);
				}

				List<Rrate> ratelist = sm.findScoreRate(dtime.getOid());
				if(ratelist.size() == 0) {
					//create Rrate record
						Rrate rrate = sm.createRrate(dtime.getOid());
						dtMap.put("rateN", "" + 0.3f);
						dtMap.put("rateM", "" + 0.3f);
						dtMap.put("rateF", "" + 0.4f);
				} else if(ratelist.size() > 0) {
					dtMap.put("rateN", "" + ratelist.get(0).getRateN());
					dtMap.put("rateM", "" + ratelist.get(0).getRateM());
					dtMap.put("rateF", "" + ratelist.get(0).getRateF());
				}
				dtLists.add(dtMap);
			}
			
			session.setAttribute("ScoreRateUpdate4Choose", dtLists);
			
			session.removeAttribute("ScoreRateInEdit");
			session.removeAttribute("ScoreRateFormMap");
			setContentPage(session, "teacher/ScoreRateChange.jsp");
			return mapping.findForward("Main");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm)form;
		ResourceBundle bundle = Toolket.getBundle(request);
		ActionMessages messages = new ActionMessages();
		ScoreManager sm = (ScoreManager)getBean("scoreManager");
		
		List<Map> dtList = new ArrayList<Map>();
		dtList = (List)session.getAttribute("ScoreRateUpdate4Choose");

		String[] rateN = aForm.getStrings("rateN");
		String[] rateM = aForm.getStrings("rateM");
		String[] rateF = aForm.getStrings("rateF");
		String[] changed = aForm.getStrings("changed");
		
		ActionMessages err = new ActionMessages();
		String departClass = "";
		String cscode = "";
		String deptClassName = "";
		String cscodeName = "";
		int Oid = 0;
		List<Rrate> rrates = null;
		Rrate rrate = null;
		float[] rate_input = new float[13];
		float frateN = 0f, frateM = 0f, frateF = 0f;
		int cnt = 0;

		for (Map dtMap : dtList) {
			if (((Boolean) dtMap.get("updateable"))) {
				frateN = Float.parseFloat(rateN[cnt]);
				frateM = Float.parseFloat(rateM[cnt]);
				frateF = Float.parseFloat(rateF[cnt]);
				
				//log.debug((frateN + frateN + frateN));
				if((frateN + frateM + frateF) > 1.0f){
					err.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"MessageN1", "成績比例總和必須等於1,班級:" + 
							deptClassName + ", 科目:" + cscodeName));
				}
				cnt++;
			}
		}
		
		if(!err.isEmpty()){
			saveMessages(request, err);
			setContentPage(session, "teacher/ScoreRateChange.jsp");
			return mapping.findForward("Main");
		}
		
		cnt = 0;
		for (Map dtMap : dtList) {
			if (((Boolean) dtMap.get("updateable"))) {
				if(changed[cnt].trim().equals("1")){
					Oid = (Integer)dtMap.get("oid");
					departClass = dtMap.get("departClass").toString();
					cscode = dtMap.get("cscode").toString();
					deptClassName = dtMap.get("className2").toString();
					cscodeName = dtMap.get("chiName2").toString();
					
					frateN = Float.parseFloat(rateN[cnt]);
					frateM = Float.parseFloat(rateM[cnt]);
					frateF = Float.parseFloat(rateF[cnt]);
					
					rrates = sm.findScoreRate(Oid);
					if (!rrates.isEmpty()) {
						rrate = rrates.get(0);
						rate_input[0] = rrate.getRate01();
						rate_input[1] = rrate.getRate02();
						rate_input[2] = rrate.getRate03();
						rate_input[3] = rrate.getRate04();
						rate_input[4] = rrate.getRate05();
						rate_input[5] = rrate.getRate06();
						rate_input[6] = rrate.getRate07();
						rate_input[7] = rrate.getRate08();
						rate_input[8] = rrate.getRate09();
						rate_input[9] = rrate.getRate10();
						rate_input[10] = Float.parseFloat(rateN[cnt]);
						rate_input[11] = Float.parseFloat(rateM[cnt]);
						rate_input[12] = Float.parseFloat(rateF[cnt]);
						sm.updateRrate(rrate, rate_input);
					} else {
						err.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"MessageN1", "系統錯誤:不存在的成績比例紀錄,班級:" + 
								departClass + "/" + deptClassName + ", 科目:" + cscode + "/" + cscodeName));
					}
				}
				cnt++;
			}
		}
		if (!err.isEmpty()) {
			messages.add(err);
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.ModifySuccessful"));
		}
		saveMessages(request, messages);
		setContentPage(session, "teacher/ScoreRateChange.jsp");
		//return mapping.findForward("Main");
		return unspecified(mapping, form , request, response);

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
