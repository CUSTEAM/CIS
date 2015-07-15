package tw.edu.chit.struts.action.fee;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;

import tw.edu.chit.model.FeeCode;
import tw.edu.chit.model.FeePay;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class ClassFeePayAction extends BaseLookupDispatchAction {

	private static String FEE_PAY_LIST = "feePays";
	private static String FEE_KIND_1 = "feeKind1";
	private static String FEE_KIND_2 = "feeKind2";
	private static String FEE_KIND_INFO = "FeeKindInfo";

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		Toolket.resetCheckboxCookie(response, FEE_KIND_INFO);
		session.removeAttribute(FEE_PAY_LIST);
		session.removeAttribute(FEE_KIND_1);
		session.removeAttribute(FEE_KIND_2);
		((DynaActionForm) form).initialize(mapping);

		setContentPage(session, "fee/ClassFeePayInfo.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	@SuppressWarnings("unchecked")
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		Toolket.resetCheckboxCookie(response, FEE_KIND_INFO);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		session.removeAttribute(FEE_PAY_LIST);
		session.removeAttribute(FEE_KIND_1);
		session.removeAttribute(FEE_KIND_2);

		FeePay pay = new FeePay();
		pay.setDepartClass(processClassInfo(aForm));
		Example example = Example.create(pay).ignoreCase().enableLike(
				MatchMode.ANYWHERE);
		Projection projection = Projections.groupProperty("departClass");
		List<String> paies = (List<String>) am.findSQLWithCriteria(
				FeePay.class, example, projection, null);
		List<DynaBean> beans = new LinkedList<DynaBean>();
		DynaBean bean = null;
		for (String p : paies) {
			bean = new LazyDynaBean();
			bean.set("departClass", p);
			bean.set("departClassName", Toolket.getClassFullName(p));
			beans.add(bean);
		}
		session.setAttribute(FEE_PAY_LIST, beans);

		setContentPage(session, "fee/ClassFeePayInfo.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	@SuppressWarnings("unchecked")
	public ActionForward classFeePayList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		String departClass = request.getParameter("dc");
		if (StringUtils.isBlank(departClass))
			departClass = (String) request.getAttribute("dc");
		aForm.set("classNo", departClass);

		FeePay pay = new FeePay();
		pay.setDepartClass(departClass);
		Example example = Example.create(pay).ignoreCase().enableLike(
				MatchMode.ANYWHERE);
		List<FeePay> paies = (List<FeePay>) am.findSQLWithCriteria(
				FeePay.class, example, null, null);

		List<FeePay> feeKind1 = new LinkedList<FeePay>();
		List<FeePay> feeKind2 = new LinkedList<FeePay>();
		int total1 = 0, total2 = 0;
		for (FeePay p : paies) {
			p.setKindName(Toolket.getFeeKind(p.getKind()));
			p.setDepartClassName(Toolket.getClassFullName(p.getDepartClass()));
			p.setFeeCodeName(Toolket.getFeeCode(p.getFcode()));
			if ("1".equals(p.getKind())) {
				feeKind1.add(p);
				total1 += p.getMoney();
			} else if ("2".equals(p.getKind())) {
				feeKind2.add(p);
				total2 += p.getMoney();
			}
		}

		session.setAttribute(FEE_KIND_1, Collections.EMPTY_LIST);
		session.setAttribute(FEE_KIND_2, feeKind2);
		if (feeKind2.isEmpty()) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "查無任何代辦費資料,可自行新增班級代辦費,謝謝"));
			saveMessages(request, messages);
		}

		session.setAttribute("total1", total1);
		session.setAttribute("total2", total2);
		setContentPage(session, "fee/ClassFeePayInfo.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	public ActionForward chooseAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.set("feeCode", "1");
		aForm.set("money", "0");
		aForm.set("kind", "2");
		String[] feeCodes = new String[0];
		String[] feeCodeNames = new String[0];

		List<FeeCode> codes = am.findFeeCodeBy(new FeeCode());
		for (FeeCode f : codes) {
			feeCodes = (String[]) ArrayUtils.add(feeCodes, f.getNo());
			feeCodeNames = (String[]) ArrayUtils.add(feeCodeNames, f.getName());
		}
		aForm.set("feeCodes", feeCodes);
		aForm.set("feeCodeNames", feeCodeNames);

		setContentPage(request.getSession(false), "fee/ClassFeePayAdd.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	@SuppressWarnings("unchecked")
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		Toolket.resetCheckboxCookie(response, FEE_KIND_INFO);
		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();

		FeePay pay = new FeePay();
		pay.setDepartClass(aForm.getString("classNo"));
		pay.setKind(aForm.getString("kind"));
		pay.setFcode(aForm.getString("feeCode"));
		Example example = Example.create(pay).ignoreCase().enableLike(
				MatchMode.ANYWHERE);
		List<String> paies = (List<String>) am.findSQLWithCriteria(
				FeePay.class, example, null, null);

		if (paies.isEmpty()) {
			pay.setMoney(Integer.parseInt(aForm.getString("money").trim()));
			try {
				am.saveObject(pay);
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "新增成功"));
				saveMessages(request, messages);
				request.setAttribute("dc", pay.getDepartClass());
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "新增失敗"));
				saveErrors(request, messages);
			}
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "輸入班級之收費種類與代碼已重複,請重新輸入,謝謝"));
			saveMessages(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}

		return classFeePayList(mapping, form, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		String[] oids = Toolket.getSelectedIndexFromCookie(request,
				FEE_KIND_INFO).split("\\|");
		ActionMessages messages = new ActionMessages();

		try {
			for (String oid : oids) {
				if (StringUtils.isNotBlank(oid))
					am.removeObject(FeePay.class, Integer.valueOf(oid));
			}

			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "刪除成功"));
			saveMessages(request, messages);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "刪除失敗"));
			saveErrors(request, messages);
		}

		return search(mapping, form, request, response);
	}

	public ActionForward chooseUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.set("kind", "2");
		String[] feeCodes = new String[0];
		String[] feeCodeNames = new String[0];

		List<FeeCode> codes = am.findFeeCodeBy(new FeeCode());
		for (FeeCode f : codes) {
			feeCodes = (String[]) ArrayUtils.add(feeCodes, f.getNo());
			feeCodeNames = (String[]) ArrayUtils.add(feeCodeNames, f.getName());
		}
		aForm.set("feeCodes", feeCodes);
		aForm.set("feeCodeNames", feeCodeNames);

		String oid = Toolket.getSelectedIndexFromCookie(request, FEE_KIND_INFO)
				.replaceAll("\\|", "");
		FeePay pay = (FeePay) am.getObject(FeePay.class, Integer.valueOf(oid));
		aForm.set("kind", pay.getKind());
		aForm.set("feeCode", pay.getFcode());
		aForm.set("money", pay.getMoney().toString());
		session.setAttribute("feePay4U", pay);

		setContentPage(session, "fee/ClassFeePayUpdate.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		String oid = aForm.getString("feePayOid");
		ActionMessages messages = new ActionMessages();

		FeePay pay = (FeePay) am.getObject(FeePay.class, Integer.valueOf(oid));
		pay.setKind(aForm.getString("kind"));
		pay.setFcode(aForm.getString("feeCode"));
		pay.setMoney(Integer.parseInt(aForm.getString("money").trim()));

		try {
			am.saveObject(pay);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "更新成功"));
			saveMessages(request, messages);
			request.setAttribute("dc", pay.getDepartClass());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "更新失敗,請檢查是否已有重複資料,謝謝"));
			saveErrors(request, messages);
		}

		return classFeePayList(mapping, form, request, response);
	}

	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return search(mapping, form, request, response);
	}

	public ActionForward courseFee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.set("kind", "2");

		setContentPage(session, "fee/ClassFeePayClassAdd.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	public ActionForward courseFeeAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		String classNo = processClassInfo(aForm);
		ActionMessages messages = new ActionMessages();
		
		if (StringUtils.isNotBlank(classNo) && classNo.length() == 6 || StringUtils.isNotBlank(classNo) && classNo.length() == 7) {
			FeePay fp = new FeePay();
			fp.setKind("2"); // 代辦費
			fp.setDepartClass(classNo);
			List<FeePay> fps = am.findFeePayBy(fp);
			System.out.println(":::"+fps);
			if (!fps.isEmpty()) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "班級代辦費資料已存在"));
				saveMessages(request, messages);
			} else {
				fp.setFcode("0");
				fp.setMoney(0);
				am.saveObject(fp);
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "班級代辦費資料已新增成功"));
				saveMessages(request, messages);
			}

			request.setAttribute("dc", classNo);
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "班級資料不完整"));
			saveMessages(request, messages);
			request.setAttribute("dc", "");
		}

		return classFeePayList(mapping, form, request, response);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("fee.add.courseFee", "courseFee");
		map.put("fee.add.courseFeeAdd", "courseFeeAdd");
		map.put("fee.class.search", "search");
		map.put("classFeePayList", "classFeePayList");
		map.put("fee.add.choose", "chooseAdd");
		map.put("fee.add.sure", "add");
		map.put("fee.update.choose", "chooseUpdate");
		map.put("fee.update.sure", "update");
		map.put("fee.delete", "delete");
		map.put("fee.back", "back");
		return map;
	}

	private String processClassInfo(DynaActionForm form) {
		String campusInCharge2 = (String) form.get("campusInCharge2");
		String schoolInCharge2 = (String) form.get("schoolInCharge2");
		String deptInCharge2 = (String) form.get("deptInCharge2");
		String departClass = (String) form.get("classInCharge2");
		schoolInCharge2 = schoolInCharge2.equalsIgnoreCase("All") ? ""
				: schoolInCharge2;
		deptInCharge2 = deptInCharge2.equalsIgnoreCase("All") ? ""
				: deptInCharge2;
		departClass = departClass.equalsIgnoreCase("All") ? "" : departClass;
		return departClass.length() == 6 || departClass.length() == 7 ? departClass : campusInCharge2
				+ schoolInCharge2 + deptInCharge2;
	}

}
