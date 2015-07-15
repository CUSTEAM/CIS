package tw.edu.chit.struts.action.registration;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import tw.edu.chit.model.Register;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.TempStmd;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class UploadNewStmd1Action extends BaseLookupDispatchAction {

	static DateFormat df = new SimpleDateFormat("yyyyMMdd");

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.initialize(mapping);
		session.removeAttribute("newStmdList");

		Map<String, Integer> m = Toolket.getNextYearTerm();
		Integer year = m.get(IConstants.PARAMETER_SCHOOL_YEAR);
		Integer sterm = m.get(IConstants.PARAMETER_SCHOOL_TERM);
		aForm.set("year", year.toString());
		aForm.set("sterm", sterm.toString());
		aForm.set("years", Toolket.getYearArray1(Toolket.getNextYearTerm().get(
				IConstants.PARAMETER_SCHOOL_YEAR).toString(), 3));

		setContentPage(session, "registration/UploadNewStmd1.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 上傳新生基本資料(已編班編學號)
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
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();

		// TODO 要不要檢查XLS?
		FormFile file = (FormFile) aForm.get("xlsFile");
		InputStream is = file.getInputStream();
		Workbook workbook = Toolket.getWorkbookJXL(is);
		Sheet sheet = workbook.getSheet(0);
		Cell[] cells = null;
		Student student = null;
		Register reg = null;
		TempStmd tempStmd = null;
		List<Student> students = new LinkedList<Student>();
		List<Register> regs = new LinkedList<Register>();
		List<TempStmd> tempStmds = new LinkedList<TempStmd>();
		String campusCode = null, schoolType = null, schoolYear = null;
		String serialNo = null, departClass = null, studentNo = null;
		String studentName = null, idno = null, sex = null, ident = null, schoolName = null;
		String schoolCode = null, gradDept = null, gradStatus = null;
		String post = null, addr = null, tel = null, cellPhone = null, parentName, email;
		String permPost = null, permAddr = null;
		String modifier = getUserCredential(session).getMember().getIdno();
		int cols = 22;
		String hql = "SELECT SUM(f.money) FROM FeePay f WHERE f.kind = ? AND f.departClass = ?";
		Date birth = null;
		Short entrance = null, gradYear = null;
		Object o = null;
		int index = 0, tuitionFee = 0, agencyFee = 0;

		try {
			for (int rowNum = 3; rowNum <= sheet.getRows(); rowNum++) {
				try {
					// 查不到Row會Exception,所以只好...
					cells = sheet.getRow(rowNum);
				} catch (Exception e) {
					break;
				}

				index = rowNum + 1;
				if (cells != null && cells[0] != null && cells[1] != null
						&& cells[2] != null && cells[3] != null) {

					// 用Iteger.toString(Integer.valueOf())只要是驗證是否輸入為數字?
					campusCode = Integer.toString(Integer.valueOf(cells[0]
							.getContents().trim()));
					schoolType = cells[1].getContents().trim();
					schoolYear = Integer.toString(Integer.valueOf(cells[2]
							.getContents().trim()));
					departClass = cells[3].getContents().trim().toUpperCase();
					studentNo = cells[4].getContents().trim().toUpperCase();
					studentName = cells[5].getContents().trim();
					idno = cells[6].getContents().trim().toUpperCase();
					sex = cells[7].getContents().trim();
					if (StringUtils.isBlank(sex))
						sex = StringUtils.substring(idno, 1, 2);
					birth = Toolket.parseDateSerial(cells[8].getContents()
							.trim());
					entrance = Short.valueOf(cells[9].getContents().trim());
					ident = cells[10].getContents().trim();
					schoolName = cells[11].getContents().trim();
					schoolCode = cells[12].getContents().trim();
					gradDept = cells[13].getContents().trim();
					gradYear = Short.valueOf(cells[14].getContents().trim());
					gradStatus = Integer.toString(Integer.valueOf(cells[15]
							.getContents().trim()));
					post = cells[16].getContents().trim();
					addr = cells[17].getContents().trim();
					tel = cells[18].getContents().trim().replaceAll("-", "")
							.replaceAll("\\(", "").replaceAll("\\)", "");
					cellPhone = cells[19].getContents().trim().replaceAll("-",
							"");
					if(cellPhone.indexOf(']') > 0){
						cellPhone = cellPhone.substring(cellPhone.indexOf(']') + 1);
					}
					
					parentName = cells[20].getContents().trim();
					email = cells[21].getContents().trim();

					if(cells.length>=25){
						//有戶籍地資料，有編班編學號但繳費單上是流水號(台北進修學院)
						serialNo = cells[22].getContents().trim().toUpperCase();
						permPost = cells[23].getContents().trim();
						permAddr = cells[24].getContents().trim();
					}
					
					
					student = new Student();
					student.setDepartClass(departClass);
					student.setDepartClass2(Toolket.getClassFullName(student
							.getDepartClass()));
					student.setStudentNo(studentNo);
					student.setStudentName(studentName);
					student.setIdno(idno);
					student.setSex(sex);
					student.setBirthday(birth);
					student.setEntrance(entrance);
					student.setIdent(ident);
					student.setSchlName(schoolName);
					student.setSchlCode(schoolCode);
					student.setGradDept(gradDept);
					student.setGradyear(gradYear);
					student.setGraduStatus(gradStatus);
					student.setCurrPost(post);
					student.setCurrAddr(addr);
					student.setTelephone(tel);
					student.setCellPhone(cellPhone);
					student.setParentName(parentName);
					student.setEmail(email);
					student.setPermPost(permPost);
					student.setPermAddr(permAddr);
					
					
					tempStmd = new TempStmd();
					tempStmd.setSchoolYear(schoolYear);
					tempStmd.setDepartClass(departClass);
					tempStmd.setStudentNo(studentNo);
					tempStmd.setStudentName(studentName);
					tempStmd.setIdno(idno);
					tempStmd.setSerialNo(serialNo);
					tempStmd.setSex(sex);
					tempStmd.setBirthday(birth);
					tempStmd.setEntrance(entrance);
					tempStmd.setIdent(ident);
					tempStmd.setSchlName(schoolName);
					tempStmd.setSchlCode(schoolCode);
					tempStmd.setGradDept(gradDept);
					tempStmd.setGradyear(gradYear);
					tempStmd.setGraduStatus(gradStatus);
					tempStmd.setCurrPost(post);
					tempStmd.setCurrAddr(addr);
					tempStmd.setTelephone(tel);
					tempStmd.setCellPhone(cellPhone);
					tempStmd.setParentName(parentName);
					tempStmd.setEmail(email);
					tempStmd.setTempNo("");
					tempStmd.setPermPost(permPost);
					tempStmd.setPermAddr(permAddr);

					tuitionFee = 0;
					o = am.find(hql, new Object[] { "1", departClass }).get(0);
					tuitionFee = o == null ? 0 : ((Integer) o).intValue();

					agencyFee = 0;
					o = am.find(hql, new Object[] { "2", departClass }).get(0);
					agencyFee = o == null ? 0 : ((Integer) o).intValue();

					reg = new Register();
					reg.setSchoolYear(schoolYear);
					reg.setSchoolTerm("1");
					reg.setSerialNo(serialNo);
					reg.setIdno(idno);
					reg.setCampusCode(campusCode);
					reg.setSchoolType(schoolType);
					reg.setStudentName(studentName);
					reg.setRealClassNo(departClass);
					reg.setRealStudentNo(studentNo);
					reg.setVirClassNo(departClass);
					reg.setVirStudentNo(studentNo);
					reg.setTuitionFee(tuitionFee);
					reg.setAgencyFee(agencyFee);
					reg.setType("N"); // N:新生
					reg.setModifier(modifier);
					reg.setLastModified(new Date());

					students.add(student);
					regs.add(reg);
					tempStmds.add(tempStmd);
				}
			}

			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "Excel檔案解析完成"));
			saveMessages(request, messages);
		} catch (Exception e) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "Excel檔案解析失敗,錯誤位置:第" + index + "行"));
			saveErrors(request, messages);
		}

		session.setAttribute("newStmdCount", students.size());
		session.setAttribute("newStmdList", students);
		session.setAttribute("newRegisterList", regs);
		session.setAttribute("newTempStmdList", tempStmds);
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
		Object data = session.getAttribute("newTempStmdList");

		if (data != null) {
			//List<Student> students = (List<Student>) data;
			List<Register> regs = (List<Register>) session
					.getAttribute("newRegisterList");
			List<TempStmd> tempstmds = (List<TempStmd>) session
			.getAttribute("newTempStmdList");

			try {

				Register reg = regs.get(0);
				
				List<String> stnos = new ArrayList<String>();
				String schoolYear = reg.getSchoolYear();
				String schoolTerm = reg.getSchoolTerm();
				String campusCode = reg.getCampusCode();
				int cnt = 0;
				List<Register> saveRegs = new ArrayList<Register>();
				List<TempStmd> saveTmps = new ArrayList<TempStmd>();
				
				//只有當新生未填寫學籍卡資料時才可以刪除該筆資料，繳費資料在下次匯入時會回補
				String hql = "DELETE FROM Register r WHERE r.schoolYear = ? AND r.schoolTerm = ? "
						+ "AND r.campusCode = ? AND r.schoolType = ? And r.idno=? AND r.type = 'N' AND " + 
						" (r.newStudentReg is NULL Or r.newStudentReg<>'1')";
				String hql2 = "FROM Register r WHERE r.schoolYear = ? And r.schoolTerm = ? "
					+ "And r.campusCode = ? And r.schoolType = ? And r.idno=? And r.type = 'N'";
				
				for(Iterator<Register> regIter=regs.iterator(); regIter.hasNext();){
					reg = regIter.next();
					if(!am.find(hql2, new Object[] { schoolYear, schoolTerm, reg.getCampusCode(),
							reg.getSchoolType(), reg.getIdno()}).isEmpty()){
						cnt = am.txBulkUpdate(hql, new Object[] { schoolYear, schoolTerm, reg.getCampusCode(),
								reg.getSchoolType(), reg.getIdno()});
						if(cnt > 0 ){
							saveRegs.add(reg);
						}else{
								stnos.add(reg.getIdno().trim().toUpperCase());
						}
					}else{
						saveRegs.add(reg);
					}
				}
								
				/*
				hql = "DELETE FROM Student WHERE Idno = ?";
				Student student = null;
				boolean isFind = false;
				for (Iterator<Student> stIter=students.iterator(); stIter.hasNext();){
					student = stIter.next();
					isFind = false;
					for(String idnod:stnos){
						if(student.getIdno().trim().equalsIgnoreCase(idnod)){
							students.remove(student);
							isFind = true;
							break;
						}
					}
					
					if(!isFind){
						am.txBulkUpdate(hql, new Object[] { student.getIdno() });
					}
				}
				*/
				
				hql = "DELETE FROM TempStmd WHERE SchoolYear = ? AND Idno = ?";
				TempStmd tempstmd = null;
				boolean isFind = false;
				for (Iterator<TempStmd> stIter=tempstmds.iterator(); stIter.hasNext();){
					tempstmd = stIter.next();
					isFind = false;
					for(String idnod:stnos){
						if(tempstmd.getIdno().trim().equalsIgnoreCase(idnod)){
							isFind = true;
							break;
						}
					}
					
					if(!isFind){
						saveTmps.add(tempstmd);
						am.txBulkUpdate(hql, new Object[] { tempstmd.getSchoolYear(), tempstmd.getIdno() });
					}
				}
				/*
				Collection<Register> existRegs = (List<Register>) am.find(hql,
						new Object[] { reg.getSchoolYear(),
								reg.getSchoolTerm(), reg.getCampusCode(),
								reg.getSchoolType() });
				am.deleteAll(existRegs);
				
				hql = "DELETE FROM Student WHERE Idno = ?";
				for (Student student : students)
					am.txBulkUpdate(hql, new Object[] { student.getIdno() });
				*/

				//am.saveOrUpdateAll(students);
				am.saveOrUpdateAll(saveRegs);
				am.saveOrUpdateAll(saveTmps);
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "新生資料轉入註冊檔成功"));
				saveMessages(request, messages);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Course.errorN1", "新生資料轉入註冊檔失敗"));
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
