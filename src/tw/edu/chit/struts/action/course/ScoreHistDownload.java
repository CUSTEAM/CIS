package tw.edu.chit.struts.action.course;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class ScoreHistDownload extends BaseDispatchAction {

	/**
	 * 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		Student student = new Student();
		Graduate graduate = new Graduate();
		if (StringUtils.isNotBlank(aForm.getString("no"))) {
			student.setStudentNo(aForm.getString("no"));
			graduate.setStudentNo(aForm.getString("no"));
		}

		if (StringUtils.isNotBlank(aForm.getString("idno"))) {
			student.setIdno(aForm.getString("idno"));
			graduate.setIdno(aForm.getString("idno"));
		}

		List<Student> students = mm.findStudentsBy(student);
		List<Graduate> graduates = mm.findGraduatesBy(graduate);
		List<DynaBean> ret = new LinkedList<DynaBean>();
		DynaBean bean = null;
		for (Student s : students) {
			bean = new LazyDynaBean();
			bean.set("no", s.getStudentNo());
			bean.set("name", s.getStudentName());
			bean.set("sex", Toolket.getSex(s.getSex()));
			bean.set("class", Toolket.getClassFullName(s.getDepartClass()));
			ret.add(bean);
		}

		for (Graduate g : graduates) {
			bean = new LazyDynaBean();
			bean.set("no", g.getStudentNo());
			bean.set("name", g.getStudentName());
			bean.set("sex", Toolket.getSex(g.getSex()));
			bean.set("class", Toolket.getClassFullName(g.getDepartClass()));
			ret.add(bean);
		}
		request.setAttribute("seldList", ret);
		setContentPage(session, "course/StudentScoreHist.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

}
