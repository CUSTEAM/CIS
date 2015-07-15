package tw.edu.chit.struts.action.student.flw;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;

import tw.edu.chit.model.FlwStmdWorkData;
import tw.edu.chit.model.FlwWork;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class FlowerAction extends BaseLookupDispatchAction {

	static Calendar cal = null;

	static {
		cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2010);
		cal.set(Calendar.MONTH, Calendar.NOVEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 7);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
	}

	@Override
	@SuppressWarnings("unchecked")
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.initialize(mapping);
		session.removeAttribute("flwWorkList");
		session.removeAttribute("stmdFlwWorkList");
		Toolket.resetCheckboxCookie(response, "flwWorkListIndex");
		Toolket.resetCheckboxCookie(response, "stmdFlwWorkListIndex");
		ActionMessages messages = new ActionMessages();

		Student student = (Student) getUserCredential(session).getStudent();
		student = mm.findStudentByNo(student.getStudentNo());
		student.setDepartClass2(Toolket.getClassFullName(student
				.getDepartClass()));

		if (!student.getDepartClass().startsWith("164")
				|| !Toolket.isNewStudentClass(student.getDepartClass())) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "對不起，必須是台北日間部四技新生入學者才可執行花博班次預約"));
			saveMessages(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		} else {
			Date date = null;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String[] labels = new String[1], values = new String[1];
			labels[0] = "";
			values[0] = "";
			String sql = "SELECT s.Date D FROM FLW_Work s GROUP BY s.Date ORDER By Oid";
			List<Map> ret = (List<Map>) am.findBySQL(sql, new Object[] {});
			for (Map data : ret) {
				date = (Date) data.get("D");
				labels = (String[]) ArrayUtils.add(labels, df.format(date)
						+ " (" + Toolket.getWeekInfo(date) + ")");
				values = (String[]) ArrayUtils.add(values, df.format(date));
			}
			aForm.set("workDateLabels", labels);
			aForm.set("workDateValues", values);

			FlwStmdWorkData fswd = new FlwStmdWorkData();
			fswd.setStudentNo(student.getStudentNo());
			Example example = Example.create(fswd);
			List<Order> orders = new LinkedList<Order>();
			orders.add(Order.asc("oid"));
			List<FlwStmdWorkData> fswds = (List<FlwStmdWorkData>) am
					.findSQLWithCriteria(FlwStmdWorkData.class, example, null,
							orders);

			List<DynaBean> beans = new LinkedList<DynaBean>();
			DynaBean bean = null;
			FlwWork work = null;
			for (FlwStmdWorkData f : fswds) {
				bean = new LazyDynaBean();
				work = (FlwWork) am.getObject(FlwWork.class, f.getParentOid());
				bean.set("oid", f.getOid());
				bean.set("date", work.getDate());
				bean.set("classNo", work.getClassNo());
				bean.set("areaNo", work.getAreaNo());
				beans.add(bean);
			}

			Calendar now = Calendar.getInstance();
			boolean isOver = now.after(cal);
			if (isOver) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "對不起，已超過可修改預約時間"));
				saveMessages(request, messages);
			}

			session.setAttribute("stmdFlwIsOver", isOver);
			session.setAttribute("studentInfo", student);
			session.setAttribute("stmdFlwWorkList", beans);
			session.setAttribute("stmdFlwWorkListCounts", beans.size());
		}

		setContentPage(session, "student/StudentFlower.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	@SuppressWarnings("unchecked")
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, "flwWorkListIndex");
		String workDate = aForm.getString("workDate");
		ActionMessages messages = new ActionMessages();

		if (StringUtils.isNotBlank(workDate)) {

			// 查詢某天班次資料
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			FlwWork work = new FlwWork();
			work.setDate(df.parse(workDate));
			Example example = Example.create(work);
			List<Order> orders = new LinkedList<Order>();
			orders.add(Order.asc("oid"));
			List<FlwWork> works = (List<FlwWork>) am.findSQLWithCriteria(
					FlwWork.class, example, null, orders);

			// 學生已預約資料
			Student student = (Student) getUserCredential(session).getStudent();
			FlwStmdWorkData fswd = new FlwStmdWorkData();
			fswd.setStudentNo(student.getStudentNo());
			example = Example.create(fswd);
			List<FlwStmdWorkData> fswds = (List<FlwStmdWorkData>) am
					.findSQLWithCriteria(FlwStmdWorkData.class, example, null,
							orders);
			Set<Integer> parentOidData = new HashSet<Integer>();
			Set<String> classNoData = new HashSet<String>();
			for (FlwStmdWorkData f : fswds) {
				parentOidData.add(f.getParentOid());
				work = (FlwWork) am.getObject(FlwWork.class, Integer.valueOf(f
						.getParentOid()));
				classNoData.add(df.format(work.getDate()) + " "
						+ work.getClassNo());
			}

			List<DynaBean> beans = new LinkedList<DynaBean>();
			DynaBean bean = null;
			FlwStmdWorkData stmdWork = null;
			int counts = 0, pointedCounts = 0;
			boolean flag = true;
			String classNo = null;

			for (FlwWork fw : works) {
				// 選過會排除不能再選(用選過的parentOid判斷是否選過)
				if (!parentOidData.contains(fw.getOid())) {

					// 排除同日期同節次資料
					flag = true;
					for (String m : classNoData) {
						if (m.startsWith(workDate)) {
							classNo = m.split(" ")[1];
							if (fw.getClassNo().equals(classNo)) {
								flag = false;
								break;
							}
						}
					}

					if (flag) {
						stmdWork = new FlwStmdWorkData();
						stmdWork.setParentOid(fw.getOid());
						example = Example.create(stmdWork);
						counts = ((List<FlwStmdWorkData>) am
								.findSQLWithCriteria(FlwStmdWorkData.class,
										example, null, null)).size();
						if (counts < fw.getCounts()) {
							bean = new LazyDynaBean();
							bean.set("data", fw);

							bean.set("counts", counts);
							beans.add(bean);
						}
					}
				} else
					pointedCounts++;
			}

			// 在前端擋住節次次數上限2次用
			session.setAttribute("flwWorkListPointedCounts", pointedCounts);

			session.removeAttribute("flwWorkList");
			if (beans.isEmpty()) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "當天已無可預約的班次，請選擇其他班次，謝謝。"));
				saveMessages(request, messages);
			} else
				session.setAttribute("flwWorkList", beans);
		}

		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	@SuppressWarnings("unchecked")
	public ActionForward send(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		ActionMessages messages = new ActionMessages();

		String studentNo = ((Student) getUserCredential(session).getStudent())
				.getStudentNo().trim();
		String[] index = Toolket.getSelectedIndexFromCookie(request,
				"flwWorkListIndex").split("\\|");

		List<FlwStmdWorkData> works = new LinkedList<FlwStmdWorkData>();
		FlwWork fw = null;
		FlwStmdWorkData work = null;
		FlwStmdWorkData stmdWork = new FlwStmdWorkData();
		Set<String> checkData = new HashSet<String>();
		Example example = null;
		boolean flag = true, isOver = false;
		int counts = 0;
		isOver = false;
		for (String ind : index) {
			if (StringUtils.isNotBlank(ind)) {
				fw = (FlwWork) am.getObject(FlwWork.class, Integer
						.parseInt(ind));

				counts = 0;
				isOver = false;
				stmdWork.setParentOid(fw.getOid());
				example = Example.create(stmdWork);
				counts = ((List<FlwStmdWorkData>) am.findSQLWithCriteria(
						FlwStmdWorkData.class, example, null, null)).size();
				isOver = (counts + 1) > fw.getCounts();
				if (isOver) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Course.errorN1", "當天預約人數已超過"
									+ fw.getCounts() + "人"));
					saveMessages(request, messages);
					flag = false;
					break;
				} else if (!checkData.contains(fw.getClassNo())) {
					checkData.add(fw.getClassNo());
					work = new FlwStmdWorkData();
					work.setParentOid(Integer.parseInt(ind));
					work.setStudentNo(studentNo);
					work.setLastModified(new Date());
					works.add(work);

				} else {
					messages
							.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("Course.errorN1",
											"同一天不可預約相同節次資料"));
					saveMessages(request, messages);
					flag = false;
					break;
				}
			}
		}

		try {
			if (flag) {
				for (FlwStmdWorkData fswd : works) {
					// 查詢時會排除選過的,且會排除同日期同節次資料
					// TODO 要檢查是否已預約過?檢查是否衝?
					am.saveObject(fswd);
				}
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "新增成功"));
				saveMessages(request, messages);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "錯誤訊息:" + e.getMessage()));
			saveErrors(request, messages);
		}

		return unspecified(mapping, form, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		ActionMessages messages = new ActionMessages();

		String[] index = Toolket.getSelectedIndexFromCookie(request,
				"stmdFlwWorkListIndex").split("\\|");
		for (String ind : index) {
			if (StringUtils.isNotBlank(ind)) {
				try {
					am
							.removeObject(FlwStmdWorkData.class, Integer
									.valueOf(ind));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Course.errorN1", "錯誤訊息:"
									+ e.getMessage()));
					saveErrors(request, messages);
				}
			}

		}

		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Course.errorN1", "刪除成功"));
		saveMessages(request, messages);
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Search", "search");
		map.put("Send", "send");
		map.put("Delete", "delete");
		return map;
	}

}
