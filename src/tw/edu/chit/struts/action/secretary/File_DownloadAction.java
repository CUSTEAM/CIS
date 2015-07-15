package tw.edu.chit.struts.action.secretary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.FileName;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class File_DownloadAction extends BaseLookupDispatchAction {

	/**
	 * 初始資料
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session
				.getAttribute("Credential");
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		Toolket.resetCheckboxCookie(response, "FN");
		Toolket.resetCheckboxCookie(response, "FNList");
		Toolket.resetCheckboxCookie(response, "FN_List");
		Toolket.resetCheckboxCookie(response, "FA_List");

		String UserIdno = user.getMember().getIdno(); // 取得使用者身分證字號
		int CMsize = manager.ezGetBy(
		        "Select FN.Oid, FN.Name From File_Name FN, File_User FU " +
		        "Where FN.Oid = FU.FN_Oid   And FU.FU_Type = '645'   And FU.Empl_Idno = '"+UserIdno+"'").size();
		session.setAttribute("UserName", user.getMember().getName()); // 取得使用者姓名
		session.setAttribute("UserUnit", user.getMember().getUnit2()); // 取得使用者單位
		session.setAttribute("FN_Unit",manager.ezGetBy(
				"Select Oid, name From code5 Where category='FileUnit' Order By Oid")); // 取得所屬單位選項
		session.setAttribute("FN_type",manager.ezGetBy(
				"Select Oid, name From code5 Where category='FileScheme' And sequence='1'")); // 取得類別
		session.setAttribute("FD_ShareType",manager.ezGetBy(
				"Select Oid, name From code5 Where category='FileUpload' And sequence='1'")); // 取得版本別
		session.setAttribute("Committee",manager.ezGetBy(
		        "Select FN.Oid, FN.Name From File_Name FN, File_User FU " +
		        "Where FN.Oid = FU.FN_Oid   And FU.FU_Type = '645'   And FU.Empl_Idno = '"+UserIdno+"'")); // 取得版本別
		session.setAttribute("CMsize", CMsize);
		session.setAttribute("myOpen", "close");
		
		
		

		setContentPage(request.getSession(false), "FILE/File_Download.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 執行查詢動作
	 */
	public ActionForward Query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, "FN");
		Toolket.resetCheckboxCookie(response, "FNList");

		String UserIdno = user.getMember().getIdno(); // 取得使用者身分證字號
		String SelKeyWords = aForm.getString("Data_name");
		String SelUnit = aForm.getString("FN_Unit");
		String SelType = aForm.getString("FN_type");
		String Sel_ShareType = aForm.getString("FD_ShareType");
		String CM_FN = aForm.getString("CM_FN");
		String Badte = manager.ezGetString("Select CM_Bdate From File_User Where FN_Oid = '"+CM_FN+"' And Empl_Idno = '"+UserIdno+"'");
		String Eadte = manager.ezGetString("Select CM_Edate From File_User Where FN_Oid = '"+CM_FN+"' And Empl_Idno = '"+UserIdno+"'");		

		String Sel_KW = "  And (FD.KeyWords Like '%" + SelKeyWords + "%' or FN.Name Like '%" + SelKeyWords + "%') ";
		String Sel_UT = "  And FN.FN_Unit = '" + SelUnit + "' ";
		
		if (SelKeyWords.equals("")) {
			Sel_KW = "";
		}
		if (SelUnit.equals("")) {
			Sel_UT = "";
		}
				
		List<FileName> FNList = null;

		if(!CM_FN.equals("")){			
			 FNList = manager.ezGetBy(
					"Select FD.Oid, FD.schoolYear, FD.schoolTerm, FD.Data_name, C.name, FD.lastModified," +
					"        FD.DocNo, FD.MeetingType, FD.MeetingDate, FN.Name FN_Name " +
					"From File_DataList FD, File_User FU, code5 C, File_Name FN " +
					"Where FD.FN_Oid = FU.FN_Oid " +
					"  And FD.FN_Oid = FN.Oid " +
					"  And FD.ShareType = C.Oid " +
					"  And FD.approve = '97' " +
					"  And FN.Oid = '"+CM_FN+"' " +
					"  And FD.MeetingDate BETWEEN '"+Badte+"' AND '"+Eadte+"'" +
					" GROUP by FD.Oid  "+
					" Order By C.name, FN.Name, FD.lastModified DESC ");			
		}else{			
			FNList = manager.ezGetBy(
					"Select FD.Oid, FD.schoolYear, FD.schoolTerm, FD.Data_name, C.name, FD.lastModified," +
					"        FD.DocNo, FD.MeetingType, FD.MeetingDate, FN.Name FN_Name " +
					"From File_DataList FD, File_User FU, code5 C, File_Name FN " +
					"Where FD.FN_Oid = FU.FN_Oid " +
					"  And FD.FN_Oid = FN.Oid " +
					"  And FD.ShareType = C.Oid " +
					"  And FD.approve = '97' " +
					"  And FU_Type <> '645'" +
					"  And FU.Empl_Idno = '"+ UserIdno + "' " +
					"  And FN.FN_Type = '"+ SelType+ "' " +
					"  And FD.ShareType = '"+ Sel_ShareType+ "'"					
					+ Sel_KW + Sel_UT + 
					"Order By C.name, FN.Name, FD.lastModified DESC ");			
		}
		
		session.setAttribute("FN_List", FNList);
		session.setAttribute("FN_Type", SelType);				
		session.setAttribute("myOpen", "open");
		session.setAttribute("showType", "Query");
		setContentPage(request.getSession(false), "FILE/File_Download.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 下載
	 * 
	 * @throws IOException
	 */
	public ActionForward Download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session
				.getAttribute("Credential");
		ActionMessages messages = new ActionMessages();
		Toolket.resetCheckboxCookie(response, "FN");
		Toolket.resetCheckboxCookie(response, "FNList");
		
		String UserIdno = user.getMember().getIdno(); // 取得使用者身分證字號
		String ids = Toolket.getSelectedIndexFromCookie(request, "FNList");
		String FL_id = ids.substring(1, ids.length() - 1);
		String FN_id = manager
				.ezGetString("Select FN_Oid From File_DataList Where Oid = '"
						+ FL_id + "'");

		String FN_User = manager.ezGetString(
				" Select Empl_Idno From File_User " +
				" Where FN_Oid = '"+ FN_id + "'" +
				"   And Empl_Idno = '"+ UserIdno +"'" +
				"   And FU_Type = '551'");
		String SelType = manager.ezGetString(
				"Select FN_Type From File_Name Where Oid = '"+ FN_id +"'");
		String FD_ShareType = manager.ezGetString(
				"Select ShareType From File_DataList Where Oid = '"+ FL_id +"'");
		String FN_Share = manager.ezGetString(
				"Select Share From File_Name Where Oid = '"+ FN_id + "'");
		String URL = manager.ezGetString(
				"Select Data_URL From File_DataList Where Oid = '"+ FL_id +"'");
		String pristineData = manager.ezGetString(
				"Select pristineData From File_DataList Where Oid = '"+ FL_id +"'");

		if (!FD_ShareType.equals("563")&& SelType.equals("550") && !FN_User.equals(UserIdno)) {
			messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"Course.messageN1", "此檔案非最新版本，僅提供條列查詢;如需下載檢視，請洽所屬單位。"));
			saveErrors(request, messages);
			setContentPage(request.getSession(false), "FILE/File_Download.jsp");
			return mapping.findForward("Main");
		} else if (!FN_Share.equals("638") && SelType.equals("549") && !FN_User.equals(UserIdno)) {
			messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
					"Course.messageN1", "此會議之會議記錄，僅提供條列查詢;如需下載檢視，請洽所屬單位。"));
			saveErrors(request, messages);
			
			setContentPage(request.getSession(false), "FILE/File_Download.jsp");
			return mapping.findForward("Main");
		} else {
			manager.DownloadFileFTPServer(URL, pristineData, session.getServletContext().getRealPath("/UserFiles/").toString());
			try {

				response.setContentType("application/octet-stream; charset=utf-8");

				String path = session.getServletContext().getRealPath("/UserFiles/") + "/" + pristineData;
				File file = new File(path);
				if (!file.exists()) {
					if (!file.exists()) {
						file = new File(path);
					}
				}

				try {
					String realName = java.net.URLEncoder.encode(file.getName(), "utf-8");
					response.setHeader("Content-Disposition", "attachment; filename=\"" + realName + "\"");
					if (file.exists()) {
						FileInputStream in = new FileInputStream(file);
						OutputStream out = response.getOutputStream();
						byte[] b = new byte[100];
						int len;
						while ((len = in.read(b)) > 0) {
							out.write(b, 0, len);
						}
						if (in != null) {
							in.close();
						}

						if (out != null) {
							out.close();
						}
						file.delete();
					}
				} catch (Exception e) {
					System.out.println(path + " generate errors!");
					e.printStackTrace();
				}

			} catch (Exception e) {

				response.setHeader("Content-Disposition", "attachment;filename=ProjectReport.doc");
				response.setContentType("application/vnd.ms-word; charset=UTF-8");
				PrintWriter out = response.getWriter();

				out.println("<html>");
				out.println("<body>");
				out.println("下載檔案方式不正確！");
				out.println("</body>");
				out.println("</html>");

				out.close();

			}

			return null;
		}
		// System.out.println(session.getServletContext().getRealPath("/UserFiles/")+"/"+pristineData);		
	}

	@Override
	protected Map getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("Query", "Query");
		map.put("Download", "Download");
		return map;
	}

}
