package tw.edu.chit.struts.action.course;

import static tw.edu.chit.util.IConstants.ACTION_MAIN_NAME;
import static tw.edu.chit.util.IConstants.ADMIN_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.COURSE_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.MEMBER_MANAGER_BEAN_NAME;
import static tw.edu.chit.util.IConstants.PARAMETER_NEXT_TERM;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.model.Adcd;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class BatchProcessAdcdListAction extends BaseLookupDispatchAction {

	public static final String SELD_ADCD_LIST = "seldAdcdList";

	/**
	 * 查詢科目選課學生清單
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

		Integer dtimeOid = Integer.valueOf(request.getParameter("oid"));
		CourseManager cm = (CourseManager) getBean(COURSE_MANAGER_BEAN_NAME);		
		request.getSession(false).setAttribute(SELD_ADCD_LIST,
				doSomething(cm.findAdcdByDtimeOid(dtimeOid)));
		setContentPage(request.getSession(false), "course/AdcdList.jsp");
		return mapping.findForward(ACTION_MAIN_NAME);
	}
	
	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setContentPage(request.getSession(false), "course/BatchProcess.jsp");
		return mapping.findForward(ACTION_MAIN_NAME);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Back", "back");
		return map;
	}
	
	private List<DynaBean> doSomething(List<Adcd> adcds) {
		MemberManager mm = (MemberManager) getBean(MEMBER_MANAGER_BEAN_NAME);
		AdminManager am = (AdminManager) getBean(ADMIN_MANAGER_BEAN_NAME);
		String term = am.findTermBy(PARAMETER_NEXT_TERM);
		List<DynaBean> ret = new LinkedList<DynaBean>();
		for (Adcd adcd : adcds) {
			DynaBean db = new LazyDynaBean();
			Student student = mm.findStudentByNo(adcd.getStudentNo());
			if (student != null) {
				String departClass = student.getDepartClass();
				// 如果term為代表新學期,則學生之班級代碼須轉換^__^"
				if ("1".equals(term)) 
					departClass = Toolket.processClassNoUp(departClass);
				
				db.set("studentNo", student.getStudentNo());
				db.set("studentName", student.getStudentName());
				db.set("departClass", departClass);
				db.set("className", Toolket.getClassFullName((departClass)));
				db.set("adddraw", adcd.getAdddraw().equals("A") ? "加選" : "退選");
			} else {
				// 查無Student則查詢畢業生(休學生)資料
				Graduate graduate = mm.findGraduateByStudentNo(adcd
						.getStudentNo());
				db.set("studentNo", graduate.getStudentNo());
				db.set("studentName", graduate.getStudentName());
				db.set("departClass", graduate.getDepartClass());
				db.set("className", Toolket.getClassFullName((adcd
						.getStudepartClass())));
				db.set("adddraw", adcd.getAdddraw().equals("A") ? "加選" : "退選");
			}
			ret.add(db);
		}
		return ret;
	}

}
