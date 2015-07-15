package tw.edu.chit.struts.action.score;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.MasterData;
import tw.edu.chit.model.Stavg;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class ScoreMasterAction extends BaseLookupDispatchAction {

	public static final String STUDENT_INFO = "stdInfo";
	public static final String STUDENT_INFO_AJAX = "StudentInfoAjax";

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 處理第一次進入碩士成績資料
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

		((DynaActionForm) form).initialize(mapping);
		setContentPage(request.getSession(false), "score/ScoreMaster.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = validateInputForUpdate(aForm, Toolket
				.getBundle(request));

		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		} else {
			try {
				ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);
				MasterData md = new MasterData();
				md = setFormProps(md, aForm.getMap());
				sm.addMasterData(md);
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.CreateSuccessful"));
				saveMessages(request, messages);
				aForm.initialize(mapping);

			} catch (Exception e) {
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(IConstants.ACTION_MAIN_NAME);
			}
		}
		
		setContentPage(request.getSession(false), "score/ScoreMaster.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 負責更新所選取之MasterData物件
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = validateInputForUpdate(aForm, Toolket
				.getBundle(request));

		if (!messages.isEmpty()) {
			saveErrors(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		} else {
			try {
				ScoreManager sm = (ScoreManager) getBean("scoreManager");
				MasterData md = sm.findMasterDataById(Integer.valueOf(aForm
						.getString("oid")));
				md = setFormProps(md, aForm.getMap());
				sm.modifyMasterData(md);
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.ModifySuccessful"));
				saveMessages(request, messages);
				aForm.initialize(mapping);

			} catch (Exception e) {
				ActionMessages errors = new ActionMessages();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Exception.generic", e.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(IConstants.ACTION_MAIN_NAME);
			}
		}
		setContentPage(request.getSession(false), "score/ScoreMaster.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 負責刪除所選取之MasterData物件
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		try {
			ScoreManager sm = (ScoreManager) getBean("scoreManager");
			MasterData md = sm.findMasterDataById(Integer.valueOf(aForm
					.getString("oid")));
			sm.deleteMasterData(md);
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.DeleteSuccessful"));
			saveMessages(request, messages);
			aForm.initialize(mapping);

		} catch (Exception e) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Exception.generic", e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}
		setContentPage(request.getSession(false), "score/ScoreMaster.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 處理非同步取得MasterData資料表內資料
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward findStdByNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String studentNo = request.getParameter("stdNo").toUpperCase();
		ScoreManager sm = (ScoreManager) getBean(IConstants.SCORE_MANAGER_BEAN_NAME);

		if (StringUtils.isNotBlank(studentNo)) {

			MasterData master = sm.findMasterByStudentNo(studentNo);
			response.setContentType("text/xml");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			if (master != null) {

				Graduate graduate = sm.findGraduateByStudentNo(studentNo);
				if (graduate == null) {
					Student student = sm.findStudentByStudentNo(studentNo);
					graduate = new Graduate();
					BeanUtils.copyProperties(graduate, student);
				}
				DecimalFormat df = new DecimalFormat(",##0.0");

				// 建立JSON型態格式資料,
				// 如{info:"", year:"", term:""....}
				StringBuffer buf = new StringBuffer("{");
				buf.append("info:\"").append(graduate.getStudentName()).append(
						"   ").append(
						Toolket.getClassFullName(graduate.getDepartClass()))
						.append("\",");
				buf.append("oid:\"").append(master.getOid()).append("\",");
				buf.append("year:\"").append(master.getSchoolYear()).append(
						"\",");
				buf.append("term:\"").append(master.getSchoolTerm()).append(
						"\",");
				buf.append("chThesis:\"").append(
						master.getThesesChiname().replaceAll("\\r", "")
								.replaceAll("\\n", " ")).append("\",");
				buf.append("enThesis:\"").append(
						master.getThesesEngname().replaceAll("\\r", "")
								.replaceAll("\\n", " ")).append("\",");
				buf.append("thesisScore:\"").append(master.getThesesScore())
						.append("\",");
				// 即時計算學業平均
				List<Stavg> stavgs = sm.findStavgBy(new Stavg(studentNo));
				float scoreTotal = 0.0F;
				int m = 0;
				for (Stavg stavg : stavgs) {
					if (stavg.getScore() == null || stavg.getScore() == 0.0F) {
						m++; // 避免有學期平均成績為0加總後總成績計算錯誤
						continue;
					}
					scoreTotal += stavg.getScore();
				}

				String average = new DecimalFormat(",##0.0").format((scoreTotal
						/ (stavgs.size() - m) + 0.001D));
				buf.append("evgrScore:\"").append(average).append("\",");
				// 即時計算畢業成績
				float gradScore = (Float.parseFloat(average) / (float) 2)
						+ (master.getThesesScore() / (float) 2);
				buf.append("gradScore:\"")
						.append(df.format(gradScore + 0.001D)).append("\",");
				buf.append("add:\"").append("true").append("\",");
				buf.append("del:\"").append("").append("\",");
				buf.append("upd:\"").append("").append("\",");
				try {
					String dateFormat = sdf.format(master.getOnlineFileDate());
					dateFormat = String.valueOf(Integer.parseInt(StringUtils
							.substring(dateFormat, 0, 4)) - 1911)
							+ StringUtils.substring(dateFormat, 4);
					buf.append("online:\"").append(dateFormat).append("\",");
				} catch (Exception pe) {
					buf.append("online:\"").append("\",");
				}
				buf.append("remark:\"").append(
						StringUtils.defaultString(master.getRemark(), ""))
						.append("\"");
				buf.append("}");
				log.info(buf.toString());
				request.getSession(false).setAttribute(STUDENT_INFO,
						buf.toString());
				out.print(buf.toString());
			} else {
				Student student = sm.findStudentByStudentNo(studentNo);
				if (student != null) {
					List<Stavg> stavgs = sm.findStavgBy(new Stavg(studentNo));
					float scoreTotal = 0.0F;
					int m = 0;
					for (Stavg stavg : stavgs) {
						if (stavg.getScore() == null
								|| stavg.getScore() == 0.0F) {
							m++; // 避免有學期平均成績為0加總後總成績計算錯誤
							continue;
						}
						scoreTotal += stavg.getScore();
					}

					String average = "";
					if ((stavgs.size() - m) > 0)
						average = new DecimalFormat(",##0.0")
								.format((scoreTotal / (stavgs.size() - m) + 0.001D));
					
					request.getSession(false).setAttribute(
							STUDENT_INFO_AJAX,
							student.getStudentName()
									+ "  "
									+ Toolket.getClassFullName(student
											.getDepartClass()));
					StringBuffer buf = new StringBuffer("{");
					buf
							.append("info:\"")
							.append(student.getStudentName())
							.append(
									"3".equals(student.getOccurStatus()) ? "(轉入生)"
											: "(非轉入生)").append("   ").append(
									Toolket.getClassFullName(student
											.getDepartClass())).append("\",");
					buf.append("oid:\"").append(student.getOid()).append("\",");
					buf.append("year:\"").append("\",");
					buf.append("term:\"").append("\",");
					buf.append("chThesis:\"").append("\",");
					buf.append("enThesis:\"").append("\",");
					buf.append("thesisScore:\"").append("\",");
					buf.append("evgrScore:\"").append(average).append("\",");
					buf.append("gradScore:\"").append("\",");
					buf.append("online:\"").append("\",");
					buf.append("add:\"").append("").append("\",");
					buf.append("del:\"").append("true").append("\",");
					buf.append("upd:\"").append("true").append("\",");
					buf.append("remark:\"").append("\"");
					buf.append("}");
					log.info(buf.toString());
					out.print(buf.toString());
				} else {
					out.print("查無此人!!");
				}
			}

			out.flush();
			return null;
		}
		
		setContentPage(request.getSession(false), "score/ScoreMaster.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("score.scoreMaster.findStudentByNo", "findStdByNo"); // 查詢學生
		map.put("score.scoreMaster.add", "add"); // 新增
		map.put("score.scoreMaster.update", "update"); // 更新
		map.put("score.scoreMaster.delete", "delete"); // 刪除
		return map;
	}

	private ActionMessages validateInputForUpdate(DynaActionForm form,
			ResourceBundle bundle) {
		ActionMessages errors = new ActionMessages();
		validateFieldFormat(form, errors, bundle);
		return errors;
	}

	private void validateFieldFormat(DynaActionForm form,
			ActionMessages errors, ResourceBundle bundle) {
		// TODO 未完成Form Validation
	}

	// 將Form內所有Property資料轉進至MasterData物件
	@SuppressWarnings("unchecked")
	private MasterData setFormProps(MasterData md, Map props) {

		md
				.setStudentNo(((String) props.get("student_no")).trim()
						.toUpperCase());
		md.setSchoolYear(Short.valueOf(((String) props.get("school_year"))
				.trim()));
		md.setSchoolTerm(Short.valueOf(((String) props.get("school_term"))
				.trim()));
		md.setThesesChiname(((String) props.get("chineseThesis")).trim());
		md.setThesesEngname(((String) props.get("englishThesis")).trim());
		md.setThesesScore(Float.valueOf(((String) props.get("thesisScore"))
				.trim()));
		md.setEvgr1Score(Float
				.valueOf(((String) props.get("evgrScore")).trim()));
		md.setGraduateScore(Float.valueOf(((String) props.get("gradScore"))
				.trim()));
		try {
			md.setOnlineFileDate(Toolket.parseDate(((String) props
					.get("onlineFileDate")).trim()));
		} catch (Exception pe) {
			md.setOnlineFileDate(null);
		}
		md.setRemark(((String) props.get("remark")).trim());
		return md;
	}

}
