package tw.edu.chit.struts.action.fee;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import tw.edu.chit.service.AdminManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class BankFeeRegister4CAction extends BaseLookupDispatchAction {

	/**
	 * 
	 */
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.initialize(mapping);
		session.removeAttribute("registerUpdateList");

		Map<String, Integer> m = Toolket.getNextYearTerm();
		Integer year = m.get(IConstants.PARAMETER_SCHOOL_YEAR);
		Integer sterm = m.get(IConstants.PARAMETER_SCHOOL_TERM);
		aForm.set("year", year.toString());
		aForm.set("sterm", sterm.toString());
		aForm.set("years", Toolket.getYearArray1(Toolket.getNextYearTerm().get(
				IConstants.PARAMETER_SCHOOL_YEAR).toString(), 3));

		setContentPage(session, "fee/BankFeeRegister4C.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 
	 */
	public ActionForward parse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();

		// TODO 要不要檢查XLS?
		String year = aForm.getString("year");
		String term = aForm.getString("sterm");
		String campusCode = aForm.getString("campusCode");
		String feeType = aForm.getString("feeType");
		FormFile file = (FormFile) aForm.get("xlsFile");
		InputStream is = file.getInputStream();
		Workbook workbook = Toolket.getWorkbookJXL(is);
		Sheet sheet = workbook.getSheet(0);
		Cell[] cells = null;
		DynaBean bean = null;
		List<DynaBean> beans = new LinkedList<DynaBean>();
		// DateFormat df = new SimpleDateFormat("yyyyMMdd");
		int rowNum = 1;

		try {
			for (rowNum = 1; rowNum <= sheet.getRows(); rowNum++) {
				try {
					// 查不到Row會Exception,所以只好...
					cells = sheet.getRow(rowNum);
				} catch (Exception e) {
					break;
				}

				if ("1".equals(campusCode)) {
					if (cells != null && cells[0] != null && cells[1] != null) {
						bean = new LazyDynaBean();
						bean.set("feeType", feeType);
						bean.set("year", year);
						bean.set("term", term);
						bean.set("studentNo", cells[6].getContents().trim());
						bean.set("idno", cells[9].getContents().trim());
						bean.set("accountNo", cells[7].getContents().trim());
						bean.set("amount", Integer.valueOf(cells[30]
								.getContents().trim()));
						bean.set("payDate", Toolket.parseDateSerial(cells[28]
								.getContents().trim().replaceAll("'", "")));
						beans.add(bean);
					}
				} else if ("2".equals(campusCode)) {
					bean = new LazyDynaBean();
					bean.set("feeType", feeType);
					bean.set("year", year);
					bean.set("term", term);
					bean.set("studentNo", cells[6].getContents().trim());
					bean.set("idno", cells[8].getContents().trim());
					bean.set("accountNo", cells[7].getContents().trim());
					bean.set("amount", Integer.valueOf(cells[20].getContents()
							.trim()));
					bean.set("payDate", Toolket.parseDateSerial(cells[15]
							.getContents().trim()));
					beans.add(bean);
				}
			}
		} catch (Exception e) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "Excel檔案解析失敗:第" + (rowNum) + "筆"));
			saveErrors(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}

		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Course.errorN1", "Excel檔案解析完成"));
		saveMessages(request, messages);

		session.setAttribute("registerUpdateCount", beans.size());
		session.setAttribute("registerUpdateList", beans);
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public ActionForward makeSure(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();
		String feeType = aForm.getString("feeType");

		try {
			List<DynaBean> beans = (List<DynaBean>) session
					.getAttribute("registerUpdateList");
			Map<String, Object> ret = am.txRegisterUpdate(beans, "0"
					.equals(feeType) ? IConstants.FEE_TYPE.TUITION
					: IConstants.FEE_TYPE.AGENCY);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "共執行完成" + (Integer) ret.get("counts")
							+ "筆資料,未完成學號:" + (String) ret.get("idnos")));
			saveMessages(request, messages);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "Excel檔案解析完成"));
			saveErrors(request, messages);
		}

		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Parse", "parse");
		map.put("makeSure", "makeSure");
		return map;
	}

}
