package tw.edu.chit.struts.action.registration;

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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import tw.edu.chit.model.TempStmd;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class ImportNewStudentAction extends BaseLookupDispatchAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.initialize(mapping);
		session.removeAttribute("importStmdList");

		Map<String, Integer> m = Toolket.getNextYearTerm();
		Integer year = m.get(IConstants.PARAMETER_SCHOOL_YEAR);
		Integer sterm = m.get(IConstants.PARAMETER_SCHOOL_TERM);
		aForm.set("year", year.toString());
		aForm.set("sterm", sterm.toString());
		aForm.set("years", Toolket.getYearArray1(Toolket.getNextYearTerm().get(
				IConstants.PARAMETER_SCHOOL_YEAR).toString(), 3));

		setContentPage(session, "registration/ImportNewStmd.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 匯入並建立新生正式學籍資料
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward parse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		StudAffairManager sam = (StudAffairManager) getBean(IConstants.STUD_AFFAIR_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();

		Map<String, Integer> m = Toolket.getYearTermInfo(IConstants.THIS_YEAR);
		Integer year = m.get(IConstants.PARAMETER_SCHOOL_YEAR);
		Integer sterm = m.get(IConstants.PARAMETER_SCHOOL_TERM);

		// TODO 要不要檢查XLS?
		FormFile file = (FormFile) aForm.get("xlsFile");
		InputStream is = file.getInputStream();
		Workbook workbook = Toolket.getWorkbookJXL(is);
		Sheet sheet = workbook.getSheet(0);
		Cell[] cells = null;
		
		List<TempStmd> tempStmds = new LinkedList<TempStmd>();

		List<TempStmd> saveStudents = new LinkedList<TempStmd>();

		String departClass = null, studentNo = null;
		String studentName = null, idno = null;
		String virClassNo = null, virStudentNo = null;

		String modifier = getUserCredential(session).getMember().getIdno();
		
		/*
		String hql = "Select s From Register r, TempStmd s Where r.idno=s.idno And " +
			" r.schoolYear=? And r.schoolTerm=? And r.idno=? And r.virClassNo=? And r.virStudentNo=?" ;
		*/
		String hql = "Select s From TempStmd s Where s.idno=?" ;
		Object o = null;
		int index = 0;

		

		try {
			for (int rowNum = 2; rowNum <= sheet.getRows(); rowNum++) {
				try {
					// 查不到Row會Exception,所以只好...
					cells = sheet.getRow(rowNum);
				} catch (Exception e) {
					break;
				}

				index = rowNum + 1;
				if (cells != null && cells[1] != null && cells[2] != null
						&& cells[4] != null) {

					// 用Iteger.toString(Integer.valueOf())只要是驗證是否輸入為數字?
					departClass = cells[1].getContents().trim().toUpperCase();
					studentNo = cells[2].getContents().trim().toUpperCase();
					studentName = cells[3].getContents().trim();
					idno = cells[4].getContents().trim().toUpperCase();
					
					tempStmds = am.find(hql, new Object[]{idno});
					if(!tempStmds.isEmpty()){
						TempStmd stmd = tempStmds.get(0);
						stmd.setDepartClass(departClass);
						stmd.setStudentNo(studentNo);
						//am.saveObject(stmd);
						saveStudents.add(stmd);
						
					}else{
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"Course.errorN1", "Excel檔案解析失敗,錯誤位置:第" + index + "行,身分證號：" + idno + " 學生的暫存檔找不到！"));
						saveErrors(request, messages);
						break;
					}
					
				}
			}
			
			if(messages.isEmpty()){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "Excel檔案解析完成"));
				saveMessages(request, messages);
			}
		} catch (Exception e) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "Excel檔案解析失敗,錯誤位置:第" + index + "行"));
			saveErrors(request, messages);
		}

		session.setAttribute("newImpStmdCount", saveStudents.size());
		session.setAttribute("importStmdList", saveStudents);
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward makeSureTransfer(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		ActionMessages messages = new ActionMessages();
		//Object data = session.getAttribute("newStmdList");
		Object data = session.getAttribute("importStmdList");

		if (data != null) {
			//List<Student> students = (List<Student>) data;
			List<TempStmd> tempstmds = (List<TempStmd>) session
			.getAttribute("importStmdList");

			try {
				Map info = am.txImportNewStudent(tempstmds);
				int count = Integer.parseInt(info.get("importCount").toString());
				StringBuffer buf = new StringBuffer();
				if(info.get("nonImport")!=null){
					List<String> nonImport = (List<String>)info.get("nonImport");
					for(String stn:nonImport){
						buf.append(stn + ", ");
					}
				}
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "新生資料轉入學籍檔成功，共：" + count + "筆!" + 
						(buf.length()>0?" 學號已存在新生："+buf.toString():"")));
				saveMessages(request, messages);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "新生資料轉入學籍檔失敗"));
				saveErrors(request, messages);
			}
		}

		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("parseNewStmd", "parse");
		map.put("makeSureTransfer", "makeSureTransfer");
		return map;
	}

}
