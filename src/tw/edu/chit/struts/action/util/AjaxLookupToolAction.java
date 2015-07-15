package tw.edu.chit.struts.action.util;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.helpers.AjaxXmlBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.model.Csno;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class AjaxLookupToolAction extends BaseLookupDispatchAction {

	protected static final Logger log = Logger
			.getLogger(AjaxLookupToolAction.class);

	/**
	 * 依據班級代碼查詢班級基本資料
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
	public ActionForward lookupClassInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		initResponse(response);
		PrintWriter out = response.getWriter();
		try {
			String classNo = request.getParameter("classNo").toUpperCase();
			String classFullName = StringUtils.isNotEmpty(Toolket
					.getClassFullName(classNo)) ? Toolket
					.getClassFullName(classNo) : "查無此班級資料!!";
			out.print(classFullName);
		} catch (Exception e) {
			out.print("查無此班級資料!!");
		}
		out.flush();
		return null;
	}

	/**
	 * 依據科目代碼查詢科目基本資料
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
	public ActionForward lookupCourseInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		initResponse(response);
		PrintWriter out = response.getWriter();

		try {
			String csCode = request.getParameter("csCode").toUpperCase();
			CourseManager sm = (CourseManager) getBean("courseManager");
			Csno csno = sm.findCourseInfoByCscode(csCode);
			if (csno != null) {
				StringBuffer buf = new StringBuffer("{");
				buf.append("cscode:\"").append(csCode).append("\",");
				buf.append("chName:\"").append(csno.getChiName()).append("\",");
				buf.append("enName:\"").append(csno.getEngName()).append("\"");
				buf.append("}");
				out.print(buf.toString());
			} else {
				out.print("查無此科目資料!!");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			out.print("查無此科目資料!!");
		}

		out.flush();
		return null;
	}

	/**
	 * 依據科目代碼與班級代碼查詢開課基本資料
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
	public ActionForward lookupDtimeInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		initResponse(response);
		PrintWriter out = response.getWriter();
		try {
			String classNo = request.getParameter("classNo").toUpperCase();
			String csCode = request.getParameter("csCode").toUpperCase();
			String sterm = request.getParameter("sterm");
			CourseManager cm = (CourseManager) getBean("courseManager");
			Dtime dtime = cm.findDtimeInfoByCsCodeAndClassNo(classNo, csCode,
					sterm);
			if (dtime != null) {
				StringBuffer buf = new StringBuffer("{");
				buf.append("oid:\"").append(dtime.getOid()).append("\",");
				buf.append("techId:\"").append(dtime.getTechid()).append("\",");
				buf.append("opt:\"").append(dtime.getOpt()).append("\",");
				buf.append("credit:\"").append(dtime.getCredit()).append("\",");
				buf.append("hour:\"").append(dtime.getThour()).append("\",");
				// String stuSel = dtime.getStuSelect() == null ? "0" : dtime
				// .getStuSelect().toString();
				String stuSelect = String.valueOf(cm.countSelect(
						dtime.getOid().toString()).toString());
				buf.append("select:\"").append(stuSelect).append("\",");
				buf.append("limit:\"").append(dtime.getSelectLimit()).append(
						"\"");
				buf.append("}");
				log.info(buf.toString());
				out.print(buf.toString());
			} else
				out.print("無此開課紀錄!!");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			out.print("查無此開課資料!!");
		}

		out.flush();
		return null;
	}

	/**
	 * 以學生姓名關鍵字做查詢
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
	public ActionForward lookupStudentNameListByKeyword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		initResponse(response);
		PrintWriter out = response.getWriter();
		try {
			log.info(request.getParameter("key"));
			String nameKeyword = URLDecoder.decode(request.getParameter("key"),
					"UTF-8");
			MemberManager mm = (MemberManager) getBean("memberManager");
			List<String> nameList = mm
					.findStudentNameListByKeyword(nameKeyword);
			// {"names": ["xxx", "yyy", "zzz"] }
			if (nameList != null) {
				StringBuffer buf = new StringBuffer("{\"names\": [");
				for (String name : nameList) {
					buf.append("\"").append(name).append("\",");
				}
				String temp = StringUtils.substringBeforeLast(buf.toString(),
						",");
				temp += "] }";
				// buf.append("] }");
				log.info(temp);
				out.print(temp);
			} else
				out.print("查無學生資料!!");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			out.print("查無學生資料!!");
		}

		out.flush();
		return null;
	}

	/**
	 * 以學生姓名關鍵字做查詢
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
	public ActionForward lookupStudentNameListByKeywordAjaxTag(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		initResponse(response);
		PrintWriter out = response.getWriter();
		try {
			String nameKeyword = request.getParameter("key");
			MemberManager mm = (MemberManager) getBean("memberManager");
			List<String> nameList = mm
					.findStudentNameListByKeyword(nameKeyword);
			// Use Ajaxtag Helper Class
			AjaxXmlBuilder builder = new AjaxXmlBuilder();
			if (nameList != null) {

				for (String name : nameList) {
					builder.addItem(name, name);
				}

				log.info(builder.toString());
				out.print(builder.toString());
			} else
				out.print("查無學生資料!!");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			out.print("查無學生資料!!");
		}

		out.flush();
		out.close();
		return null;
	}

	/**
	 * 以學生學號做查詢
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
	public ActionForward lookupStudentByNo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		initResponse(response);
		PrintWriter out = response.getWriter();
		try {
			String nameKeyword = request.getParameter("key");
			MemberManager mm = (MemberManager) getBean("memberManager");
			Student std = mm.findStudentByNo(nameKeyword);
			if (std != null) {
				StringBuffer buf = new StringBuffer("{");
				buf.append("oid:\"").append(std.getOid()).append("\",");
				buf.append("name:\"").append(
						StringUtils.defaultString(std.getStudentName(), ""))
						.append("\",");
				buf.append("cellPhone:\"").append(
						StringUtils.defaultString(std.getCellPhone(), ""))
						.append("\",");
				buf.append("homePhone:\"").append(
						StringUtils.defaultString(std.getTelephone(), ""))
						.append("\",");
				buf.append("className:\"").append(
						Toolket.getClassFullName(std.getDepartClass())).append(
						"\",");
				buf.append("email:\"").append(
						StringUtils.defaultString(std.getEmail(), "")).append(
						"\"");
				buf.append("}");
				log.info(buf.toString());
				out.print(buf.toString());
			} else
				out.print("查無學生資料!!");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			out.print("查無學生資料!!");
		}
		out.flush();
		return null;
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("lookupClassInfo", "lookupClassInfo");
		map.put("lookupCourseInfo", "lookupCourseInfo");
		map.put("lookupDtimeInfo", "lookupDtimeInfo");
		map.put("lookupStudentNameListByKeyword",
				"lookupStudentNameListByKeyword");
		map.put("lookupStudentByNo", "lookupStudentByNo");
		return map;
	}

	private void initResponse(HttpServletResponse response) throws Exception {
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
	}

	public void test() throws Exception {
		new AjaxXmlBuilder().addItems(Collections.EMPTY_LIST, "", "");
	}

}
