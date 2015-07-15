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

public class File_OutDownloadAction extends BaseLookupDispatchAction {

	/**
	 * 初始資料
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//Toolket.resetCheckboxCookie(response, "FN");
		Toolket.resetCheckboxCookie(response, "FNList");
		//Toolket.resetCheckboxCookie(response, "FN_List");
		//Toolket.resetCheckboxCookie(response, "FA_List");
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		session.setAttribute("FN_Unit",manager.ezGetBy(  //排除沒有法規的單位 sequence = 0
				"Select Oid, name From code5 Where category='FileUnit' And sequence <> '0' Order By Oid")); // 取得所屬單位選項
		/*
		List FNList = manager.ezGetBy(					
				"Select FD.Oid, C.name Unit, FD.DocNo, FD.MeetingType, FD.MeetingDate, FN.Name FN_Name " +					
				"From File_DataList FD, File_User FU, code5 C, File_Name FN " +				
				"Where FD.FN_Oid = FU.FN_Oid " +				
				"  And FD.FN_Oid = FN.Oid " +				
				"  And FN.FN_Unit = C.Oid " +				
				"  And FD.approve = '97' " +				
				"  And FN.FN_Type = '550' " +				
				"  And FN.OpenLine = '637'" +				
				"  And FD.ShareType = '563'"+		
				"Group By FN.Name " +				
		        "Order By C.name, FN.Name DESC ");			
		session.setAttribute("FN_List", FNList);	*/
		
		
		session.setAttribute("myOpen", "close");				
		return mapping.findForward("File_OutDownload");
	}

	/**
	 * 執行查詢動作
	 */
	public ActionForward Query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		//Toolket.resetCheckboxCookie(response, "FN");
		//Toolket.resetCheckboxCookie(response, "FNList");
		//Toolket.resetCheckboxCookie(response, "FN_List");
		//Toolket.resetCheckboxCookie(response, "FA_List");	
		
		Toolket.resetCheckboxCookie(response, "FNList");
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		DynaActionForm aForm = (DynaActionForm) form;		
		//UserCredential user = (UserCredential) session.getAttribute("Credential");		
				
		String SelKeyWords = aForm.getString("Data_name");
		String SelUnit = aForm.getString("FN_Unit");
		
		String Sel_KW = "  And (FD.KeyWords Like '%" + SelKeyWords + "%' or FN.Name Like '%" + SelKeyWords + "%') ";
		String Sel_UT = "  And FN.FN_Unit = '" + SelUnit + "' ";
		
		if (SelKeyWords.equals("")) {
			Sel_KW = "";
		}
		if (SelUnit.equals("")) {
			Sel_UT = "";
		}				
		
		//List<FileName> FNList = null;		
		List FNList = manager.ezGetBy(					
				"Select FD.Oid, C.name Unit, FD.DocNo, FD.MeetingType, FD.MeetingDate, FN.Name FN_Name " +					
				"From File_DataList FD, File_User FU, code5 C, File_Name FN " +				
				"Where FD.FN_Oid = FU.FN_Oid " +				
				"  And FD.FN_Oid = FN.Oid " +				
				"  And FN.FN_Unit = C.Oid " +				
				"  And FD.approve = '97' " +				
				"  And FN.FN_Type = '550' " +				
				"  And FN.OpenLine = '637'" +				
				"  And FD.ShareType = '563'"				
				+ Sel_KW + Sel_UT + 				
				"Group By FN.Name " +				
		        "Order By C.name, FN.Name DESC ");	
		
		session.setAttribute("FN_List", FNList);		
		session.setAttribute("myOpen", "open");
		session.setAttribute("showType", "Query");		
		return mapping.findForward("File_OutDownload");
	}

	/**
	 * 下載
	 * 
	 * @throws IOException
	 */
	public ActionForward Download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		//Toolket.resetCheckboxCookie(response, "FN");
		//Toolket.resetCheckboxCookie(response, "FNList");
		//Toolket.resetCheckboxCookie(response, "FN_List");
		//Toolket.resetCheckboxCookie(response, "FA_List");
		
		Toolket.resetCheckboxCookie(response, "FNList");
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");		
		//DynaActionForm aForm = (DynaActionForm) form;			
		
		//UserCredential user = (UserCredential) session.getAttribute("Credential");
		//ActionMessages messages = new ActionMessages();				
		
		String ids = Toolket.getSelectedIndexFromCookie(request, "FNList");
		String FL_id = ids.substring(1, ids.length() - 1);
		String FN_id = manager.ezGetString(
				"Select FN_Oid From File_DataList Where Oid = '"+ FL_id + "'");		
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
		
			
		manager.DownloadFileFTPServer(URL, pristineData, session
					.getServletContext().getRealPath("/UserFiles/").toString());
		
		
			
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
		Toolket.resetCheckboxCookie(response, "FNList");
		return mapping.findForward("File_OutDownload");	
		//return nul		
		// System.out.println(session.getServletContext().getRealPath("/UserFiles/")+"/"+pristineData)
	
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
