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
import jxl.WorkbookSettings;
import jxl.write.Blank;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import tw.edu.chit.model.NewStmdSno;
import tw.edu.chit.model.Register;
import tw.edu.chit.model.TempStmd;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class UploadNewStmdAction extends BaseLookupDispatchAction {

	static DateFormat df = new SimpleDateFormat("yyyyMMdd");

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.initialize(mapping);
		session.removeAttribute("newStmdList");
		session.removeAttribute("newRegisterList");

		Map<String, Integer> m = Toolket.getNextYearTerm();
		Integer year = m.get(IConstants.PARAMETER_SCHOOL_YEAR);
		Integer sterm = m.get(IConstants.PARAMETER_SCHOOL_TERM);
		aForm.set("year", year.toString());
		aForm.set("sterm", sterm.toString());
		aForm.set("years", Toolket.getYearArray1(Toolket.getNextYearTerm().get(
				IConstants.PARAMETER_SCHOOL_YEAR).toString(), 3));

		setContentPage(session, "registration/UploadNewStmd.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 上傳新生基本資料(未編班編學號)
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

		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);

		// TODO 要不要檢查XLS?
		FormFile file = (FormFile) aForm.get("xlsFile");
		InputStream is = file.getInputStream();
		WorkbookSettings wbs = new WorkbookSettings();
		wbs.setIgnoreBlanks(false);
		Workbook workbook = Toolket.getWorkbookJXL(is, wbs);
		Sheet sheet = workbook.getSheet(0);
		Cell[] cells2 = null;
		Register reg = null;
		TempStmd tempStmd = null;
		List<TempStmd> tempStmds = new LinkedList<TempStmd>();
		List<Register> regs = new LinkedList<Register>();
		String campusCode = null, schoolType = null, schoolYear = null, serialNo = null;
		String studentName = null, idno = null, sex = null, ident = null, schoolName = null;
		String departClass = null, studentNo = null, schoolCode = null, gradDept = null, gradStatus = null, virAccount = null;
		String post = null, addr = null, tel = null, cellPhone = null, parentName, email;
		String modifier = getUserCredential(session).getMember().getIdno();
		String hql = "SELECT SUM(f.money) FROM FeePay f WHERE f.kind = ? AND f.departClass = ?";
		Date birth = null;
		Short entrance = null, gradYear = null;
		Object o = null;
		int index = 0, tuitionFee = 0, agencyFee = 0;
		int cols = 22;
		Cell[] cells = new Cell[cols];
		
		try {
			for (int rowNum = 3; rowNum <= sheet.getRows(); rowNum++) {
				try {
					// 查不到Row會Exception,所以只好...
					cells2 = sheet.getRow(rowNum);
					
					//取得的欄位數會有不足的現象
					if(cells2.length < cols){
						for(int i=0; i<cells2.length; i++){
							cells[i] = cells2[i];
						}
						for(int i=cells2.length; i<cols; i++){
							cells[i] = new Blank(rowNum, i);
						}
					}else if(cells2.length > cols){
						for(int i=0; i<cols; i++){
							cells[i] = cells2[i];
						}
					}else{
						cells = cells2.clone();
					}
					
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
					serialNo = cells[3].getContents().trim();
					/*
					 * 如果各部制沒有給一定的規則，就沒有辦法賦予新生虛擬班級和學號，也就不知道應繳費用
					 * 
					*/
					String[] no = getTempClassStuNo(schoolYear, campusCode, schoolType, serialNo);
					departClass = no[0];
					studentNo = no[1];
					
					if(departClass == null){
						//xx流水號解譯錯誤
						messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
								"Course.errorN1", "Excel檔案解析失敗,錯誤位置:第" + index + "行，流水號錯誤！"));
						saveErrors(request, messages);
						break;
					}
					studentName = cells[4].getContents().trim();
					idno = cells[5].getContents().trim().toUpperCase();
					sex = cells[6].getContents().trim();
					if (StringUtils.isBlank(sex))
						sex = StringUtils.substring(idno, 1, 2);
					birth = Toolket.parseDateSerial(cells[7].getContents()
							.trim());
					entrance = Short.valueOf(cells[8].getContents().trim());
					ident = cells[9].getContents().trim();
					schoolName = cells[10].getContents().trim();
					schoolCode = cells[11].getContents().trim();
					gradDept = cells[12].getContents().trim();
					gradYear = Short.valueOf(cells[13].getContents().trim());
					gradStatus = Integer.toString(Integer.valueOf(cells[14]
							.getContents().trim()));
					post = cells[15].getContents().trim();
					addr = cells[16].getContents().trim();
					tel = cells[17].getContents().trim().replaceAll("-", "")
							.replaceAll("\\(", "").replaceAll("\\)", "");
					cellPhone = cells[18].getContents().trim().replaceAll("-","");
					if(cellPhone.indexOf(']') > 0){
						cellPhone = cellPhone.substring(cellPhone.indexOf(']') + 1);
					}
					
					parentName = cells[19].getContents().trim();
					email = cells[20].getContents().trim();
					virAccount = cells[21].getContents().trim(); // 虛擬帳號
					//tuitionFee = Integer.parseInt(cells[22].getContents().trim());
					//agencyFee = Integer.parseInt(cells[23].getContents().trim());
					
					tempStmd = new TempStmd();
					tempStmd.setSchoolYear(schoolYear);
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
					tempStmd.setTempNo(virAccount);

					
					tuitionFee = 0;
					o = am.find(hql, new Object[] { "1", departClass }).get(0);
					tuitionFee = o == null ? 0 : ((Integer) o).intValue();

					agencyFee = 0;
					o = am.find(hql, new Object[] { "2", departClass }).get(0);
					agencyFee = o == null ? 0 : ((Integer) o).intValue();
					
					
					reg = new Register();
					reg.setSchoolYear(schoolYear);
					reg.setSchoolTerm("1");
					reg.setIdno(idno);
					reg.setCampusCode(campusCode);
					reg.setSchoolType(schoolType);
					reg.setStudentName(studentName);
					reg.setSerialNo(serialNo);
					reg.setRealClassNo(null);
					reg.setRealStudentNo(null);
					reg.setVirClassNo(departClass);
					reg.setVirStudentNo(studentNo);
					reg.setTuitionFee(tuitionFee);
					reg.setTuitionAmount(0);
					reg.setAgencyFee(agencyFee);
					reg.setAgencyAmount(0);
					reg.setLoanAmount(0);
					reg.setReliefTuitionAmount(0);
					reg.setVulnerableAmount(0);
					
					reg.setType("N"); // N:新生
					reg.setModifier(modifier);

					tempStmds.add(tempStmd);
					regs.add(reg);
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

		session.setAttribute("newStmdCount", tempStmds.size());
		session.setAttribute("newStmdList", tempStmds);
		session.setAttribute("newRegisterList", regs);
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
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		HttpSession session = request.getSession(false);
		ActionMessages messages = new ActionMessages();
		Object data = session.getAttribute("newStmdList");

		if (data != null) {
			List<TempStmd> tempstmds = (List<TempStmd>) data;
			List<Register> regs = (List<Register>) session
					.getAttribute("newRegisterList");

			try {
				Register reg = regs.get(0);

				List<String> stnos = new ArrayList<String>();
				String schoolYear = reg.getSchoolYear();
				String schoolTerm = reg.getSchoolTerm();
				String campusCode = reg.getCampusCode();
				int cnt = 0;
				
				//只有當新生未填寫學籍卡資料時才可以刪除該筆資料，繳費資料在下次匯入時會回補
				String hql = "DELETE FROM Register r WHERE r.schoolYear = ? AND r.schoolTerm = ? "
						+ "AND r.campusCode = ? AND r.schoolType = ? And idno=? AND r.type = 'N' AND r.newStudentReg is NULL";
				for(Iterator<Register> regIter=regs.iterator(); regIter.hasNext();){
					reg = regIter.next();
					cnt = am.txBulkUpdate(hql, new Object[] { schoolYear, schoolTerm, campusCode,
							reg.getSchoolType(), reg.getIdno()});
					if(cnt > 0 ){
						//Register deleted ! nothing to do
					}else{
						//可能已經填寫學籍資料，所以沒有刪除!
						stnos.add(reg.getIdno().trim().toUpperCase());
						regs.remove(reg);
					}
				}
				
				hql = "DELETE FROM TempStmd WHERE SchoolYear = ? AND Idno = ?";
				
				TempStmd tempstmd = null;
				boolean isFind = false;
				for (Iterator<TempStmd> stIter=tempstmds.iterator(); stIter.hasNext();){
					tempstmd = stIter.next();
					isFind = false;
					for(String idnod:stnos){
						if(tempstmd.getIdno().trim().equalsIgnoreCase(idnod)){
							tempstmds.remove(tempstmd);
							isFind = true;
							break;
						}
					}
					
					if(!isFind){
						am.txBulkUpdate(hql, new Object[] { tempstmd.getSchoolYear(), tempstmd.getIdno() });
					}
				}

				
				
				/*
				for (TempStmd t : tempStmds) {
					am.txBulkUpdate(hql, new Object[] { t.getSchoolYear(),
							t.getIdno() });
				}
				*/
				mm.txSaveRegister(regs, tempstmds);
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

	private String[] getTempClassStuNo(String schoolYear, String campusCode, String schoolType, String serialNo){
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		String[] no = {"", ""};
		String hql = "From NewStmdSno Where campusCode=? And schoolType=? And rule=?";
		List<NewStmdSno> classnos = (List<NewStmdSno>) am.find(hql,
				new Object[] { campusCode, schoolType, serialNo.substring(0, serialNo.length()-3)});
		 if(!classnos.isEmpty()){
			 NewStmdSno rule = classnos.get(0);
			 no[0] = rule.getClassNo();
			 no[1] = schoolYear +  campusCode + rule.getSchoolNo() + serialNo.substring(serialNo.length()-3);
		 }
		return no;
	}
	
	private String[] getTempClassStuNo_old(String schoolYear, String campusCode, String schoolType, String serialNo){
		String[] no = {campusCode, schoolYear + campusCode};
		
		if(campusCode.equals("1")){
			if(schoolType.equals("D")){
				if(serialNo.length() !=5) {
					if(serialNo.charAt(serialNo.length()-5) =='G'){
						no[0] += "1G" + serialNo.charAt(serialNo.length()-4) + "11";
						no[1] += "G" + serialNo.substring(serialNo.length()-4);
					}else{
						no[0] = null;
						no[1] = null;
					}
					return no;
				}
				//台北日間
				char school = serialNo.charAt(0);
				switch(school){
				case '2':
					//二技
					no[0] += "42" + serialNo.charAt(1) + "11";
					no[1] += "3" + serialNo.substring(1);
					break;
				case '4':
					//四技
					no[0] += "64" + serialNo.charAt(1) + "11";
					no[1] += "4" + serialNo.substring(1);
					break;
				case '5':
					//五專
					no[0] = null;
					no[1] = null;
					break;
				default:
					no[0] = null;
					no[1] = null;
				}
			}else if(schoolType.equals("N")){
				//台北進修
				char school = serialNo.charAt(0);
				switch(school){
				case '2':
					//二技
					no[0] = null;
					no[1] = null;
					break;
				case '4':
					//四技
					no[0] = null;
					no[1] = null;
					break;
				case '5':
					//二專
					no[0] = null;
					no[1] = null;
					break;
				default:
					no[0] = null;
					no[1] = null;
				}
			}else if(schoolType.equals("H")){
				//台北學院
				char school = serialNo.charAt(0);
				switch(school){
				case '3':
					//進專
					no[0] = null;
					no[1] = null;
					break;
				case '7':
					//進院
					no[0] = null;
					no[1] = null;
					break;
				default:
					no[0] = null;
					no[1] = null;
				}
			}

		}else if(campusCode.equals("2")){
			no[0] = null;
			no[1] = null;
		}
		
		return no;
	}
	
	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("parseNewStmd", "parse");
		map.put("makeSureTransfer", "makeSureTransfer");
		return map;
	}

}
