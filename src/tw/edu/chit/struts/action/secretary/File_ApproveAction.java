package tw.edu.chit.struts.action.secretary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.FileDataSource;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.FileName;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class File_ApproveAction extends BaseLookupDispatchAction{

	/**
	 * 初始資料
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		Toolket.resetCheckboxCookie(response, "FN");
		
		List<FileName> FN = manager.ezGetBy(
				" Select FD.Oid, FN.Name, RC.name approve, FD.lastModified " +
				" From File_DataList FD, File_Name FN, Rccode RC" +
				" Where FD.FN_Oid = FN.Oid " +
				"   And FD.approve = RC.Oid" +
				"   And FD.approve in('96','98') " +
				" ORDER BY FD.approve, FD.lastModified DESC ");
		
		session.setAttribute("Fn", FN);
		session.setAttribute("myOpen", "close");
		session.setAttribute("myTest", "");
		
		setContentPage(request.getSession(false), "FILE/File_Approve.jsp");
		return mapping.findForward("Main");
	}	
	
	/**
	 * 啟動審核
	 */
	public ActionForward GoOn(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		Toolket.resetCheckboxCookie(response, "FN");
		Toolket.resetCheckboxCookie(response, "FNList");
		
		String Fids = Toolket.getSelectedIndexFromCookie(request, "FN");
		String FL_id = Fids.substring(1, Fids.length()-1);
		String FN_id = manager.ezGetString(
				"Select FN_Oid From File_DataList Where Oid = '"+FL_id+"'");
		
		
		List<FileName> FA_List = manager.ezGetBy(
				"Select RC.name approve, FA.approveTemp, E.cname approveID, FA.lastModified " +
				"From File_ApproveList FA, empl E, Rccode RC " +
				"Where FA.approveID = E.Oid " +
				"  And FA.approve = RC.Oid " +				
				"  And FA.FD_Oid = '"+FL_id+"' " +
				"Order By lastModified DESC");
		
		session.setAttribute("FaList", FA_List);
		session.setAttribute("approve", manager.ezGetBy(
				"Select Oid, name From Rccode Where type='Approve'"));  // 取得審查狀態  
		
		session.setAttribute("myOpen", "open");
		session.setAttribute("showType", "Modify");
		session.setAttribute("FL_Oid", FL_id);
		session.setAttribute("FN_Oid", FN_id);		
		session.setAttribute("FN_name",  manager.ezGetString(   //取得法規/會議名稱
				"Select Name From File_Name Where Oid = '"+FN_id+"'"));
		session.setAttribute("ShareType_SQL", manager.ezGetString(  //取得版本
				"Select ShareType From File_DataList Where Oid = '"+FL_id+"'"));
		session.setAttribute("KeyWords", manager.ezGetString(
				"Select KeyWords From File_DataList Where Oid = '"+FL_id+"'"));		
		session.setAttribute("approve_v", manager.ezGetString(
				"Select approve From File_DataList Where Oid = '"+FL_id+"'"));
		session.setAttribute("approveTemp", manager.ezGetString(
				"Select approveTemp From File_DataList Where Oid = '"+FL_id+"'"));
		session.setAttribute("DocNo", manager.ezGetString(
				"Select DocNo From File_DataList Where Oid = '"+FL_id+"'"));
		session.setAttribute("MeetingType", manager.ezGetString(
				"Select MeetingType From File_DataList Where Oid = '"+FL_id+"'"));
		session.setAttribute("MeetingDate", manager.ezGetString(
				"Select MeetingDate From File_DataList Where Oid = '"+FL_id+"'"));
		
		setContentPage(request.getSession(false), "FILE/File_Approve.jsp");
		return mapping.findForward("Main");
	}	
	
	/**
	 * 儲存資料
	 */
	public ActionForward Save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		ActionMessages messages = new ActionMessages();
		
		String UserIdno = user.getMember().getIdno();  // 取得使用者身分證字號
		String UserName = user.getMember().getName();  // 取得使用者姓名
		String UserOid = user.getMember().getOid().toString();  //取得使用者Oid
		
		String Oid = aForm.getString("FL_Oid");
		String lastModified = manager.ezGetString(
				"Select lastModified From File_DataList Where Oid = '"+Oid+"'");
		String ShareType = manager.ezGetString(
				"Select ShareType From File_DataList Where Oid = '"+Oid+"'");
		String FN_Oid = manager.ezGetString(
				"Select FN_Oid From File_DataList Where Oid = '"+Oid+"'");
		String FN_User = manager.ezGetString(
				"Select User From File_DataList Where Oid = '"+Oid+"'");
		String approve = aForm.getString("approve");
		String approveTemp = aForm.getString("approveTemp");
		String FN_name = aForm.getString("FN_name");
		String CountOid = manager.ezGetString("Select Count(Oid) From File_DataList Where FN_Oid = '"+FN_Oid+"'"); 
		
		manager.executeSql(
				"Update File_DataList " +
		        "Set approve='"+approve+"', approveTemp='"+approveTemp+"' " +
                "Where Oid='"+Oid+"'");
		
		manager.executeSql(
				"Insert Into File_ApproveList (FD_Oid, approve, approveTemp, approveID) " +
                "Values('"+Oid+"','"+approve+"','"+approveTemp+"','"+UserOid+"')");
		
		if(CountOid!="1"){
			if(approve.equals("97") && ShareType.equals("563")){   //如果核可+版本為新版，將同FN_Oid的資料，版本都改為歷史版(ShareType='564')
				manager.executeSql(
						"Update File_DataList Set ShareType='564' " +
						"Where Oid <>'"+Oid+"' And FN_Oid = '"+FN_Oid+"'");
			}
		}
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "完成"));
		
//-------------------------E-mail相關資料建立開始-----------------------------------------------
		List myList;		
		
		if(approve.equals("98")){ //判斷審核結果，不核可，人員Email清單只取上傳權限者(FU.FU_Type = '551')
			myList = manager.ezGetBy(
					" Select E.Email, E.cname From File_User FU, empl E " +
	                " Where FU.Empl_Idno = E.idno " +
	                "   And FU.FN_Oid = '"+aForm.getString("FN_Oid")+"'" +
	                "   And FU.FU_Type = '551'");	
		}else{  //審核結果非不核可，人員Email清單則取可讀取者
			myList = manager.ezGetBy(
					" Select E.Email, E.cname From File_User FU, empl E " +
	                " Where FU.Empl_Idno = E.idno " +
	                "   And FU.FN_Oid = '"+aForm.getString("FN_Oid")+"'");	
		}
		
		StringBuilder sb=new StringBuilder("<p>您好</p>");
		sb.append(FN_name + ",<br>");
		if(approve.equals("98")){  //判斷審核結果，不核可，則發下列訊息
			sb.append("於" + lastModified + "<font color=red><b>上傳的法規資料，審核結果為不核可。</b></font>。<br><br>");
			sb.append("請至校務資訊系統_會議/法規 資料上傳，修改資料後再上傳。<br><br>");
			sb.append("資料審核者:"+UserName+"<br><br>");
		}else{  //判斷審核結果，非不核可，則發下列訊息
			sb.append("於" + lastModified + "<font color=red><b>有新增資料上傳</b></font>。<br><br>");
			sb.append("如需要進行檢閱,請上校務資訊系統下載<br><br>");
			sb.append("資料上傳者:"+ manager.ezGetString("Select cname From empl Where idno = '"+FN_User+"'") +"<br><br>");
			sb.append("資料審核者:"+UserName+"<br><br>");
		}
		
		FileDataSource[] file = null;
		Map map=manager.getCISMailServerInfo();
		Date adate=new Date();
		InternetAddress addr;
		List tmp=new ArrayList();
		int s=0;
		for(int i=0; i<myList.size(); i++){
			tmp.add(myList.get(i));			
		}
		
		InternetAddress address[] = new InternetAddress[tmp.size()];
		
		if(tmp.size()>0){
			for(int i=0; i<tmp.size(); i++){				
				try{
					addr=new InternetAddress(((Map)tmp.get(i)).get("Email").toString(), ((Map)tmp.get(i)).get("cname").toString(), "BIG5");
					address[i]=addr;						
				}catch(Exception e){
					e.printStackTrace();
				}
			}				
		}
		
		/*
		if(approve.equals("97") || approve.equals("98")){  //判斷審核結果，核可或是不核可都要執行；處理中則不須執行。
			manager.sendMail(
					map.get("username").toString(), 
					map.get("password").toString(), 
					map.get("mailServer").toString(), 
					map.get("mailAddress").toString(), 
					"會議/法規資料庫系統通知", 
					adate, 
					FN_name, 
					sb.toString(), 
					address, 
					file
				);
		}*/
//-------------------------E-mail相關資料建立開始-----------------------------------------------		
		
		session.setAttribute("myOpen", "close");
		session.setAttribute("myTest", "");
		setContentPage(request.getSession(false), "FILE/File_Approve.jsp");	
		return unspecified(mapping, form, request, response);
		//return mapping.findForward("Main");	
	}
	
	/**
	 * 下載
	 * @throws IOException 
	 */
	public ActionForward Download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		Toolket.resetCheckboxCookie(response, "FN");
		Toolket.resetCheckboxCookie(response, "FNList");
		DynaActionForm aForm = (DynaActionForm) form;
		
		String Oid = aForm.getString("FL_Oid");		
		
		String FN_id = manager.ezGetString(
				"Select FN_Oid From File_DataList Where Oid = '"+Oid+"'");		
		String URL = manager.ezGetString(
				"Select Data_URL From File_DataList Where Oid = '"+Oid+"'");
		String pristineData = manager.ezGetString(
				"Select pristineData From File_DataList Where Oid = '"+Oid+"'");
		
		manager.DownloadFileFTPServer(URL, pristineData, session.getServletContext().getRealPath("/UserFiles/").toString());
		
		session.setAttribute("myOpen", "close");
		session.setAttribute("showType", "Download");
				
		try {
			response.setContentType("application/octet-stream; charset=utf-8");
			String path = session.getServletContext().getRealPath("/UserFiles/")+ "/" + pristineData;
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

	@Override
	protected Map getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("Create", "Create");		
		map.put("Modify", "Modify");
		map.put("Delete", "Delete");
		map.put("Save", "Save");
		map.put("PowerAppoint", "PowerAppoint");
		map.put("GoOn", "GoOn");
		map.put("Download", "Download");
		return map;
	}

}
