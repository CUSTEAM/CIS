package tw.edu.chit.struts.action.score;

import java.util.ArrayList;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.dao.ScoreDAO;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.model.Stavg;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

/**
 * 
 * @author Jason
 *
 */
public class ScoreHistAvgAction extends BaseLookupDispatchAction {

	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("score.histadd","add");
		map.put("Forward.ScoreHistAdd","add");
		map.put("score.histqry","query");
		map.put("Forward.ScoreHistQuery", "query");
		map.put("score.histdel","delverify");
		map.put("score.histdelconfirm", "delconfirm");
		map.put("score.histdelcancel", "delcancel");
		map.put("score.histmodify","modify");
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

		setContentPage(request.getSession(false), "score/ScoreHistAvg.jsp");
		return mapping.findForward("Main");

	}
	
	/**
	 * 
	 * 
	 * @param ActionMapping mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		Map<String, String> initValue = null;
		Object o = session.getAttribute("ScoreHistEdit");
		if (o != null) {
			initValue = (Map<String, String>) session
					.getAttribute("ScoreHistAvgEdit");
		} else {
			initValue = new HashMap<String, String>();
			initValue.put("mode", "Create");
		}

		session.setAttribute("readOnly", "");
		session.setAttribute("ScoreHistAvgEdit", initValue);
		setContentPage(session, "score/ScoreHistAvgEdit.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		Toolket.resetCheckboxCookie(response, "ScoreHistAvg");
		DynaActionForm aForm = (DynaActionForm)form;
		
		String depart_class = "";
		String student_no = aForm.getString("student_no");
		String student_name = aForm.getString("student_name");
		String school_year   = aForm.getString("school_year");
		String school_term  = aForm.getString("school_term");
		boolean isForward = false;
		Map qMap = new HashMap();
		
		// log.debug("=======> student_no=" + student_no);
		// log.debug("=======> student_name=" + student_name);
		HttpSession session = request.getSession(false);
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String classInCharge = credential.getClassInChargeSqlFilter();
		if(session.getAttribute("forward") != null){
			qMap = (Map)session.getAttribute("ScoreAvgQueryForm");
			student_no = qMap.get("student_no").toString();
			school_year = qMap.get("school_year").toString();
			school_term = qMap.get("school_term").toString();
			isForward = true;
			session.removeAttribute("ScoreAvgQueryForm");
			session.removeAttribute("forward");
			//log.debug("isForward:" + isForward + "," + student_no +","+qMap.get("school_year").toString() + "," +qMap.get("school_term").toString());
		}
		if (student_no.equals("")) {
			session.removeAttribute("ScoreAvgStuMap");

			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ScoreHist.CantFindStudent"));
			saveMessages(request, messages);
			setContentPage(session, "score/ScoreHistAvg.jsp");
		} else {
		ScoreManager sm = (ScoreManager)getBean("scoreManager");
		List<Student> students = new ArrayList();
		
		List<Graduate> graduates = new ArrayList();
		
		if(!isForward){
			students = sm.findStudentsByStudentInfoForm(aForm.getMap(), classInCharge);
			graduates = sm.findGraduateByHistInfoForm(aForm.getMap(), classInCharge);
		}else{
			students = sm.findStudentsByStudentInfoForm(qMap, classInCharge);
			graduates = sm.findGraduateByHistInfoForm(qMap, classInCharge);
		}
		// log.debug("=======> students=" + students.size());
		// log.debug("=======> graduates=" + graduates.size());
		if (students.size()==0 && graduates.size()==0) {
			//can't not find students
			session.removeAttribute("ScoreAvgStuMap");
			session.removeAttribute("ScoreAvgInfoList");
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Message.ScoreHist.CantFindStudent"));
			setContentPage(session, "score/ScoreHistAvg.jsp");
		}
		else if ((students.size()==1 && graduates.size()==0) || (students.size()==0 && graduates.size()==1)){
			// Only one student or graduate find
			List<Stavg> scorehist = new ArrayList();
			if(!isForward){
				scorehist = sm.findScoreHistAvgByStudentInfoForm(aForm.getMap(), classInCharge);
			}else{
				scorehist = sm.findScoreHistAvgByStudentInfoForm(qMap, classInCharge);
			}
			log.debug("=======> scorehist.size()=" + scorehist.size());
			
			Map scrhistStu = new HashMap();
			if (scorehist.size()>0) {
				if (students.size()>0) {
					scrhistStu.put("studentName", students.get(0).getStudentName());
					scrhistStu.put("departClass", students.get(0).getDepartClass());
					scrhistStu.put("depClassName", Toolket.getClassFullName(students.get(0).getDepartClass()));
					scrhistStu.put("studentNo", students.get(0).getStudentNo());
				} else if (graduates.size()>0) {
					scrhistStu.put("studentName", graduates.get(0).getStudentName());
					scrhistStu.put("departClass", graduates.get(0).getDepartClass());
					scrhistStu.put("depClassName", Toolket.getClassFullName(graduates.get(0).getDepartClass()));
					scrhistStu.put("studentNo", graduates.get(0).getStudentNo());
				}
			}
			scrhistStu.put("schoolYear", school_year);
			scrhistStu.put("schoolTerm", school_term);
	
			session.setAttribute("ScoreAvgStuMap", scrhistStu);
			
			session.setAttribute("ScoreAvgInfoList", scorehist);
			
			if(!isForward){
				qMap.put("student_no", student_no);
				qMap.put("school_year", school_year);
				qMap.put("school_term", school_term);
				session.setAttribute("ScoreAvgQueryForm", qMap);
			}
			//log.debug("Form Value:" + qMap.get("student_no").toString() +","+qMap.get("school_year").toString() + "," + qMap.get("school_term").toString());

			setContentPage(session, "score/ScoreHistAvg.jsp");
		} else {
			//It's not in use now !!!  query many students' score , must select one ?
			if (students.size()>0) {
				log.debug("=======> student_no=" + students.get(0).getStudentNo());
				log.debug("=======> student_name=" + students.get(0).getStudentName());
			} else if (graduates.size()>0) {
				log.debug("=======> student_no=" + graduates.get(0).getStudentNo());
				log.debug("=======> student_name=" + graduates.get(0).getStudentName());
			}
	
			session.setAttribute("ScoreAvgMoreStudents", students);
			setContentPage(session, "score/ScrHistAvgMoreStu.jsp");
			session.setAttribute("ScoreAvgMoreGraduate", graduates);
			setContentPage(session, "score/ScrHistAvgMoreStu.jsp");			
		}
		}
		return mapping.findForward("Main");
	}

	public ActionForward delverify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		List<Stavg> selScores = getScoresSelectedList(request);	//Records selected for delete
		Toolket.setAllCheckboxCookie(response, "ScoreAvgDelete", selScores.size());	//Set cookie of record count for delete
		HttpSession session = request.getSession(false);
		session.setAttribute("ScoreHistAvgDelete", selScores);
		setContentPage(session, "score/ScoreHistAvgDelete.jsp");
		return new ActionForward(mapping.findForward("Main").getPath(), true);
		
	}
	
	public ActionForward delconfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		ActionMessages msgs = new ActionMessages(); 
		List<Stavg> selScores = (List<Stavg>)session.getAttribute("ScoreHistAvgDelete");
		ScoreManager sm = (ScoreManager)getBean("scoreManager");
		int len = selScores.size();
		String[][] scores = new String[len][3];
		int cnt = 0;
		for(Stavg score:selScores){
			scores[cnt][0] = score.getStudentNo();
			scores[cnt][1] = "" + score.getSchoolYear();
			scores[cnt][2] = score.getSchoolTerm();
			cnt++;
		}
		List undeletedScores = sm.deleteScoresAvg(selScores, Toolket.getBundle(request, "messages.score"));
		
		session.removeAttribute("ScoreHistDelete");
		// no undeleteScores will happen even if delete failure
		if (undeletedScores.isEmpty() && msgs.isEmpty()) {
			return query(mapping, form, request, response);
		} else {
			this.saveErrors(request, msgs);
			session.setAttribute("UndeletedScoreInfo", undeletedScores);
			setContentPage(session, "score/ScoreHistAvgUndelete.jsp");
			return mapping.findForward("Main");
		}
		
	}
	
	public ActionForward delcancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		request.getSession(false).removeAttribute("ScoreHistAvgDelete");
		setContentPage(request.getSession(false), "score/ScoreHistAvg.jsp");
		return mapping.findForward("Main");
	}

	@SuppressWarnings("unchecked")
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaActionForm aForm = (DynaActionForm)form;
		List<Stavg> selScores = getScoresSelectedList(request);
		Stavg score = selScores.get(0);
		Map<String, String> initValue = new HashMap<String, String>();
		initValue.put("mode", "Modify");
		initValue.put("studentfound", "true");
		initValue.put("classfound", "true");
		initValue.put("student_name", aForm.getString("student_name"));
		initValue.put("student_class", aForm.getString("student_class"));
		initValue.put("student_classname", aForm.getString("student_classname"));
		
		setScoreInitValue(score, initValue);
		HttpSession session = request.getSession(false);
		session.setAttribute("readOnly", "readonly");
		session.setAttribute("ScoreHistAvgEdit", initValue);
		session.setAttribute("ScoreAvgInEdit", score);
		setContentPage(session, "score/ScoreHistAvgEdit.jsp");
		return mapping.findForward("Main");

	}

	
	//Private Method Here ============================>>
	private List getScoresSelectedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "ScoreHistAvg");
		List<Stavg> scores = (List<Stavg>)session.getAttribute("ScoreAvgInfoList");
		List<Stavg> selScores = new ArrayList<Stavg>();
		Stavg score;
		ScoreDAO dao =(ScoreDAO)getBean("scoreDAO");
		for (Iterator<Stavg> scrIter = scores.iterator(); scrIter.hasNext();) {
			score = scrIter.next();
			if (Toolket.isValueInCookie(score.getOid().toString(), oids)) {
				dao.reload(score);
				selScores.add(score);
			}
		}
		return selScores;
	}
	
	private void setScoreInitValue(Stavg score, Map initValue) {
		
		initValue.put("student_no", score.getStudentNo());
		initValue.put("school_year", score.getSchoolYear());
		initValue.put("school_term", score.getSchoolTerm());
		initValue.put("credit", score.getTotalCredit());
		initValue.put("score", score.getScore());
		initValue.put("stdepart_class", score.getDepartClass());
		initValue.put("stdeptclass_name", score.getStDepClassName());
	}

}
