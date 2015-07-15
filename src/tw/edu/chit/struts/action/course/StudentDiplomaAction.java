package tw.edu.chit.struts.action.course;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.Graduate;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class StudentDiplomaAction extends BaseAction {

	/**
	 * 學位證明書補發
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request request javax.servlet.http.HttpServletRequest object
	 * @param response response javax.servlet.http.HttpServletResponse object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ServletContext context = this.servlet.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.servlet
						.getServletContext());
		MemberManager mm = (MemberManager) ctx
				.getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);

		HttpSession session = request.getSession(false);
		List pageList = (List) session.getAttribute("students");
		String studentNo = (String) ((Map) pageList.get(0)).get("student_no");
		Graduate graduate = mm.findGraduateByStudentNo(studentNo);
		if (graduate == null) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.messageN1", "查無學生畢業相關資料"));
			saveErrors(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		} else {
			File reportFile = new File(context
					.getRealPath("/WEB-INF/reports/Diploma.jasper"));
			// 需要時再編譯即可
			if (!reportFile.exists()) {
			JasperReportUtils.initJasperReportsClasspath(request);
			JasperReportUtils.compileJasperReports(context
					.getRealPath("/WEB-INF/reports/Diploma.jrxml"));
			reportFile = new File(context
					.getRealPath("/WEB-INF/reports/Diploma.jasper"));
			if (!reportFile.exists())
				throw new JRRuntimeException(
						"查無\"Diploma.jasper\"檔案，請電洽電算中心，謝謝！！");
			}

			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObject(reportFile.getPath());
			Map<String, String> parameters = new HashMap<String, String>();

			String departClass = graduate.getDepartClass();
			boolean isMaster = Toolket.isMasterClass(departClass);
			boolean isAssociate = Toolket.isAssocaiteClass(departClass);
			String school = StringUtils.substring(departClass, 2, 3);
			String academicDegree = isMaster ? "碩士" : (isAssociate ? "副學士"
					: "學士");
			String noStr = isMaster ? "碩" : (isAssociate ? "專" : "技");
			String program = isMaster ? "" : ("4".equals(school) ? "四年制"
					: "二年制");
			parameters.put("A", graduate.getOccurYear().toString());
			parameters.put("B", graduate.getStudentNo());
			parameters.put("C", graduate.getIdno());
			parameters.put("D", graduate.getStudentName());
			Calendar cal = Calendar.getInstance();
			cal.setTime(graduate.getBirthday());
			parameters.put("E",
					processDateFormat(cal.get(Calendar.YEAR) - 1911)); // 年
			parameters.put("F", processDateFormat(cal.get(Calendar.MONTH) + 1)); // 月
			parameters.put("G", processDateFormat(cal.get(Calendar.DATE))); // 日
			parameters.put("N", noStr);
			parameters.put("O", academicDegree);
			parameters.put("P", academicDegree);
			parameters.put("M", program);
			parameters.put("H", isMaster ? Toolket
					.getMasterDepartName(departClass)
					+ " 碩士班 " : Toolket.getDepartName(departClass) + " "
					+ Toolket.getGroup(graduate.getDivi()));
			parameters.put("I", Toolket.getFormalAcademicDegreeName(StringUtils
					.substring(departClass, 3, 4), isMaster, isAssociate));
			cal.setTime(graduate.getOccurDate());
			parameters.put("J",
					processDateFormat(cal.get(Calendar.YEAR) - 1911));
			parameters.put("K", processDateFormat(cal.get(Calendar.MONTH) + 1));
			parameters.put("L", "");

			byte[] bytes = JasperRunManager.runReportToPdf(jasperReport,
					parameters, new JREmptyDataSource());
			JasperReportUtils.printPdfToFrontEnd(response, bytes);
		}

		return null;
	}

	private String processDateFormat(int digit) {
		String[] hanzi = { "零", "壹", "貳", "叁", "肆", "伍", "陸", "柒", "捌", "玖" };
		String ret = "";
		String digitStr = String.valueOf(digit);
		StringBuilder chineseDigit = new StringBuilder();
		if (digitStr.length() <= 2) {
			for (int i = 0; i < digitStr.length(); i++) {
				int ii = Integer.parseInt(String.valueOf(digitStr.charAt(i)));
				chineseDigit.append(hanzi[ii]).append("拾");
			}
			ret = StringUtils.substring(chineseDigit.toString(), 0,
					chineseDigit.toString().length() - 1);
		} else if (digitStr.length() > 2) {
			for (int i = 0; i < digitStr.length(); i++) {
				int ii = Integer.parseInt(String.valueOf(digitStr.charAt(i)));
				chineseDigit.append(hanzi[ii]);
			}
			ret = chineseDigit.toString();
		}
		return ret;
	}

}
