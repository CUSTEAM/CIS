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
import org.apache.commons.lang3.StringUtils;
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

public class BankFeeRegisterAction extends BaseLookupDispatchAction {

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

		setContentPage(session, "fee/BankFeeRegister.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 解析Excel
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
		String feeType = aForm.getString("feeType");
		FormFile file = (FormFile) aForm.get("xlsFile");
		InputStream is = file.getInputStream();
		Workbook workbook = Toolket.getWorkbookJXL(is);
		Sheet sheet = workbook.getSheet(0);
		Cell[] cells = null;
		DynaBean bean = null;
		List<DynaBean> beans = new LinkedList<DynaBean>();
		int rowNum = 3;

		try {
			for (rowNum = 3; rowNum <= sheet.getRows(); rowNum++) {
				try {
					// 查不到Row會Exception,所以只好...
					cells = sheet.getRow(rowNum);
				} catch (Exception e) {
					break;
				}

				if (cells != null
						&& cells[0] != null
						&& StringUtils
								.isNotBlank(cells[0].getContents().trim())
						&& cells[1] != null
						&& StringUtils
								.isNotBlank(cells[1].getContents().trim())) {
					bean = new LazyDynaBean();
					bean.set("feeType", feeType);
					bean.set("year", year);
					bean.set("term", term);
					bean.set("idno", cells[0].getContents().trim());
					bean.set("amount", Integer.valueOf(cells[1].getContents()
							.trim()));
					beans.add(bean);
				} else
					break;
			}
		} catch (Exception e) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "Excel檔案解析失敗:第" + (rowNum + 1) + "筆"));
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
			Map<String, Object> ret = am.txRegisterUpdate(beans, "2"
					.equals(feeType) ? IConstants.FEE_TYPE.RELIEFTUITION : ("3"
					.equals(feeType) ? IConstants.FEE_TYPE.LOAN
					: IConstants.FEE_TYPE.VULNERABLE));
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
