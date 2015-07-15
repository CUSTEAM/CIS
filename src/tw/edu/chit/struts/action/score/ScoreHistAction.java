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
import tw.edu.chit.model.Student;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

/**
 * 
 * @author Jason
 * 
 */
public class ScoreHistAction extends BaseLookupDispatchAction {

	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("score.histadd", "scr_histadd");
		map.put("Forward.ScoreHistAdd", "scr_histadd");
		map.put("score.histqry", "scr_histqry");
		map.put("Forward.ScoreHistQuery", "scr_histqry");
		map.put("score.histdel", "scr_histdelverify");
		map.put("score.histdelconfirm", "scr_histdelconfirm");
		map.put("score.histdelcancel", "scr_histdelcancel");
		map.put("score.histmodify", "scr_histmodify");
		return map;
	}

	/**
	 * @comment Action預設之執行方法
	 * 
	 * @param mapping
	 *            org.apache.struts.action.ActionMapping object
	 * @param form
	 *            org.apache.struts.action.ActionForm object
	 * @param request
	 *            javax.servlet.http.HttpServletRequest object
	 * @param response
	 *            javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		setContentPage(request.getSession(false), "score/ScoreHist.jsp");
		return mapping.findForward("Main");

	}

	/**
	 * 
	 * 
	 * @param ActionMapping
	 *            mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ActionForward scr_histadd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession(false);
		Map<String, String> initValue = null;
		Object o = session.getAttribute("ScoreHistEdit");
		if (o != null) {
			initValue = (Map<String, String>) session
					.getAttribute("ScoreHistEdit");
		} else {
			initValue = new HashMap<String, String>();
			initValue.put("mode", "Create");
		}

		session.setAttribute("readOnly", "");
		session.setAttribute("ScoreHistEdit", initValue);
		setContentPage(session, "score/ScoreHistEdit.jsp");
		return mapping.findForward("Main");

	}

	public ActionForward scr_histqry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		Toolket.resetCheckboxCookie(response, "ScoreHist");
		DynaActionForm aForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");

		String depart_class = "";
		String student_no = aForm.getString("student_no");
		String student_name = aForm.getString("student_name");
		String school_year = aForm.getString("school_year");
		String school_term = aForm.getString("school_term");

		log.debug("=======> student_no=" + student_no);
		log.debug("=======> student_name=" + student_name);
		HttpSession session = request.getSession(false);
		UserCredential credential = (UserCredential) session
				.getAttribute("Credential");
		String classInCharge = credential.getClassInChargeSqlFilter();

		if (student_no.equals("")) {
			session.removeAttribute("ScoreStuMap");

			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.ScoreHist.CantFindStudent"));
			saveMessages(request, messages);
			setContentPage(session, "score/ScoreHist.jsp");
		} else {
			//----------  Leo Start  ---------
			// 取得該學生所有學期的平均分數資料
			List myList = manager.ezGetBy("Select school_year, school_term, score, total_credit From Stavg Where student_no = '"+student_no+"'");
			session.setAttribute("StavgList", myList);
			//----------  Leo End    ---------
			
			ScoreManager sm = (ScoreManager) getBean("scoreManager");
			List<Student> students = sm.findStudentsByStudentInfoForm(aForm
					.getMap(), classInCharge);
			List<Graduate> graduates = sm.findGraduateByHistInfoForm(aForm
					.getMap(), classInCharge);
			log.debug("=======> students=" + students.size());
			log.debug("=======> graduates=" + graduates.size());
			if (students.size() == 0 && graduates.size() == 0) {
				// can't not find students
				session.removeAttribute("ScoreStuMap");
				session.removeAttribute("ScoreInfoList");
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.ScoreHist.CantFindStudent"));
				setContentPage(session, "score/ScoreHist.jsp");
			} else if ((students.size() == 1 && graduates.size() == 0)
					|| (students.size() == 0 && graduates.size() == 1)) {
				// Only one student or graduate find

				List<ScoreHist> scorehist = sm.findScoreHistByStudentInfoForm(
						aForm.getMap(), classInCharge);
				log.debug("=======> scorehist.size()=" + scorehist.size());

				Map scrhistStu = new HashMap();
				if (scorehist.size() > 0) {
					if (students.size() > 0) {
						scrhistStu.put("studentName", students.get(0)
								.getStudentName());
						scrhistStu.put("departClass", students.get(0)
								.getDepartClass());
						scrhistStu.put("depClassName", Toolket
								.getClassFullName(students.get(0)
										.getDepartClass()));
						scrhistStu.put("studentNo", students.get(0)
								.getStudentNo());
					} else if (graduates.size() > 0) {
						scrhistStu.put("studentName", graduates.get(0)
								.getStudentName());
						scrhistStu.put("departClass", graduates.get(0)
								.getDepartClass());
						scrhistStu.put("depClassName", Toolket
								.getClassFullName(graduates.get(0)
										.getDepartClass()));
						scrhistStu.put("studentNo", graduates.get(0)
								.getStudentNo());
					}
				}
				scrhistStu.put("schoolYear", school_year);
				scrhistStu.put("schoolTerm", school_term);

				session.setAttribute("ScoreStuMap", scrhistStu);

				session.setAttribute("ScoreInfoList", scorehist);
				setContentPage(session, "score/ScoreHist.jsp");
			} else {
				// query many students' score , must select one ?
				if (students.size() > 0) {
					log.debug("=======> student_no="
							+ students.get(0).getStudentNo());
					log.debug("=======> student_name="
							+ students.get(0).getStudentName());
				} else if (graduates.size() > 0) {
					log.debug("=======> student_no="
							+ graduates.get(0).getStudentNo());
					log.debug("=======> student_name="
							+ graduates.get(0).getStudentName());
				}

				session.setAttribute("ScoreMoreStudents", students);
				setContentPage(session, "score/ScrHistMoreStu.jsp");
				session.setAttribute("ScoreMoreGraduate", graduates);
				setContentPage(session, "score/ScrHistMoreStu.jsp");
			}
		}
		return mapping.findForward("Main");
	}

	public ActionForward scr_histdelverify(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		List<ScoreHist> selScores = getScoresSelectedList(request); // Records
																	// selected
																	// for
																	// delete
		Toolket.setAllCheckboxCookie(response, "ScoreDelete", selScores.size()); // Set
																					// cookie
																					// of
																					// record
																					// count
																					// for
																					// delete
		HttpSession session = request.getSession(false);
		session.setAttribute("ScoreHistDelete", selScores);
		setContentPage(session, "score/ScoreHistDelete.jsp");
		return new ActionForward(mapping.findForward("Main").getPath(), true);

	}

	public ActionForward scr_histdelconfirm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		HttpSession session = request.getSession(false);
		ActionMessages msgs = new ActionMessages();
		List<ScoreHist> selScores = (List<ScoreHist>) session
				.getAttribute("ScoreHistDelete");
		ScoreManager sm = (ScoreManager) getBean("scoreManager");
		int len = selScores.size();
		String[][] scores = new String[len][3];
		int cnt = 0;
		for (ScoreHist score : selScores) {
			scores[cnt][0] = score.getStudentNo();
			scores[cnt][1] = "" + score.getSchoolYear();
			scores[cnt][2] = score.getSchoolTerm();
			cnt++;
		}
		List undeletedScores = sm.deleteScores(selScores, Toolket.getBundle(
				request, "messages.score"));

		for (int j = 0; j < len; j++) {
			msgs = sm.recalAvgScore(scores[j][0], scores[j][1], scores[j][2]);
		}
		session.removeAttribute("ScoreHistDelete");
		// no undeleteScores will happen even if delete failure
		if (undeletedScores.isEmpty() && msgs.isEmpty()) {
			return scr_histqry(mapping, form, request, response);
		} else {
			this.saveErrors(request, msgs);
			session.setAttribute("UndeletedScoreInfo", undeletedScores);
			setContentPage(session, "score/ScoreHistUndelete.jsp");
			return mapping.findForward("Main");
		}

	}

	public ActionForward scr_histdelcancel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.getSession(false).removeAttribute("ScoreHistDelete");
		setContentPage(request.getSession(false), "score/ScoreHist.jsp");
		return mapping.findForward("Main");
	}

	@SuppressWarnings("unchecked")
	public ActionForward scr_histmodify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		DynaActionForm aForm = (DynaActionForm) form;
		List<ScoreHist> selScores = getScoresSelectedList(request);
		ScoreHist score = selScores.get(0);
		Map<String, String> initValue = new HashMap<String, String>();
		initValue.put("mode", "Modify");
		initValue.put("studentfound", "true");
		initValue.put("cscodefound", "true");
		initValue.put("classfound", "true");
		initValue.put("student_name", aForm.getString("student_name"));
		initValue.put("student_class", aForm.getString("student_class"));
		initValue
				.put("student_classname", aForm.getString("student_classname"));

		setScoreInitValue(score, initValue);
		HttpSession session = request.getSession(false);
		session.setAttribute("readOnly", "readonly");
		session.setAttribute("ScoreHistEdit", initValue);
		session.setAttribute("ScoreInEdit", score);
		setContentPage(session, "score/ScoreHistEdit.jsp");
		return mapping.findForward("Main");

	}

	// Private Method Here ============================>>
	private List getScoresSelectedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "ScoreHist");
		List<ScoreHist> scores = (List<ScoreHist>) session
				.getAttribute("ScoreInfoList");
		List<ScoreHist> selScores = new ArrayList<ScoreHist>();
		ScoreHist score;
		ScoreDAO dao = (ScoreDAO) getBean("scoreDAO");
		for (Iterator<ScoreHist> scrIter = scores.iterator(); scrIter.hasNext();) {
			score = scrIter.next();
			if (Toolket.isValueInCookie(score.getOid().toString(), oids)) {
				dao.reload(score);
				selScores.add(score);
			}
		}
		return selScores;
	}

	private List getScoreDeletedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request,
				"StudentDelete");
		List<Student> students = (List<Student>) session
				.getAttribute("StudentInfoDelete");
		List<Student> selStudents = new ArrayList<Student>();
		Student stu;
		for (Iterator<Student> stuIter = students.iterator(); stuIter.hasNext();) {
			stu = stuIter.next();
			if (Toolket.isValueInCookie(stu.getOid().toString(), oids)) {
				selStudents.add(stu);
			}
		}
		return selStudents;
	}

	private void setScoreInitValue(ScoreHist score, Map initValue) {

		initValue.put("student_no", score.getStudentNo());
		initValue.put("school_year", score.getSchoolYear());
		initValue.put("school_term", score.getSchoolTerm());
		initValue.put("evgr_type", score.getEvgrType());
		initValue.put("cscode", score.getCscode());
		initValue.put("opt", score.getOpt());
		initValue.put("credit", score.getCredit());
		initValue.put("score", score.getScore());
		initValue.put("stdepart_class", score.getStdepartClass());
		initValue.put("cscode_name", score.getCscodeName());
		initValue.put("stdeptclass_name", score.getStDepClassName());
	}

}
