package tw.edu.chit.struts.action.ex.Leo;

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

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import tw.edu.chit.model.FileName;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class TextStmdAction extends BaseLookupDispatchAction{

	/**
	 * 初始資料
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		Toolket.resetCheckboxCookie(response, "FN");
		Toolket.resetCheckboxCookie(response, "FNList");
		Toolket.resetCheckboxCookie(response, "FN_List");
		
		String UserIdno = user.getMember().getIdno();                    // 取得使用者身分證字號
		session.setAttribute("UserName", user.getMember().getName());    // 取得使用者姓名
		session.setAttribute("UserUnit", user.getMember().getUnit2());   // 取得使用者單位
		/*
		List<FileName> FN = manager.ezGetBy(
				"Select FN.Oid, FN.Name FN_Name, C5.name FN_Type, C5.idno, CU.name Unit " +
				"From File_Name FN, File_User FU, code5 C5, code5 CU " +
				"Where FN.FN_Type = C5.Oid " +
				"  And FN.`Oid` = FU.`FN_Oid` " +
				"  And FU.`FU_Type` = '551'" +
				"  And FN.FN_Unit = CU.Oid" +
				"  And FU.`Empl_Idno` = '"+UserIdno+"' " +
				"Order By CU.Oid,C5.idno" 
		);
		
		List<FileName> FNList = manager.ezGetBy(
				" Select FD.Oid, FD.schoolYear, FD.schoolTerm, FD.Data_name, R.name ShareType, FD.lastModified, " +
				"        FD.DocNo, FD.MeetingType, FD.MeetingDate, RC.name, FN.Name FN_Name " +
				" From File_DataList FD, code5 R, Rccode RC, File_Name FN " +
				" Where FD.ShareType = R.Oid " +
				"   And FD.FN_Oid = FN.Oid " +
				"   And FD.approve = RC.Oid " +
				"   And FN.FN_Type = '550' " +
				"   And FD.approve = '98' "
		); */
		
		//session.setAttribute("Fn", FN);		
		//session.setAttribute("FileName", "");
		//session.setAttribute("FN_List", FNList);
		//session.setAttribute("FN_Type",  "550");
		//session.setAttribute("myOpen", "close");	
		
		setContentPage(request.getSession(false), "ex/TextStmd.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 啟動上傳作業
	 */
	public ActionForward GoOn(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		Toolket.resetCheckboxCookie(response, "FN");		
		
		//String teacherId = user.getMember().getAccount();		
		String ids = Toolket.getSelectedIndexFromCookie(request, "FN");
		String id_s = ids.substring(1, ids.length()-1);		
		
		String schoolYear = manager.getNowBy("School_year");                       // 取得學年度             
		String schoolTerm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);       // 取得學期
		String myType = manager.ezGetString(
				"Select FN_Type From File_Name Where Oid = '"+id_s+"'");    //取得資料類別	
		String FN_name = manager.ezGetString("Select Name From File_Name Where Oid = '"+id_s+"'");
		List<FileName> FNList = manager.ezGetBy(
				" Select FD.Oid, FD.schoolYear, FD.schoolTerm, FD.Data_name, R.name ShareType, FD.lastModified, " +
				"        FD.DocNo, FD.MeetingType, FD.MeetingDate, RC.name, FN.Name FN_Name " +
				" From File_DataList FD, code5 R, Rccode RC, File_Name FN " +
				" Where FD.ShareType = R.Oid " +
				"   And FD.FN_Oid = FN.Oid " +
				"   And FD.approve = RC.Oid " +
				"  And FN.Oid = '"+id_s+"'"
				
				//"   And FN.FN_Type = '550' " //+
				//"   And FD.approve = '98' "
		); 
				
		session.setAttribute("FN_Oid", id_s);
		session.setAttribute("FileName", FN_name);
		session.setAttribute("FN_List", FNList);
		session.setAttribute("myOpen", "open");
		session.setAttribute("showType", "Query");
		session.setAttribute("FN_name",  manager.ezGetString(
				"Select Name From File_Name Where Oid = '"+id_s+"'"));   //取得會議/法規之名稱
		session.setAttribute("FN_Type",  myType);
		session.setAttribute("schoolYear",  schoolYear);
		session.setAttribute("schoolTerm",  schoolTerm);
		session.setAttribute("ShareType", manager.ezGetBy(
				"SELECT Oid, name FROM code5 Where category = 'FileUpload' Order By Oid"));    //取得版本型態選項			
		
		setContentPage(request.getSession(false), "FILE/File_Upload.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 儲存資料
	 */
	public ActionForward UploadFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		ActionMessages messages = new ActionMessages();
		
		String UserIdno = user.getMember().getIdno();    // 取得使用者身分證字號
		String UserName = user.getMember().getName();    // 取得使用者姓名
		
		session.setAttribute("myOpen", "close");
		String FN_Type = aForm.getString("FN_Type");
		String FN_Oid = aForm.getString("FN_Oid");
		String FN_name = aForm.getString("FN_name");
		String schoolYear = aForm.getString("schoolYear");
		String schoolTerm = aForm.getString("schoolTerm");
		
		FormFile myImage=(FormFile) aForm.get("myImage");
		Date lastModified = new Date();
		
//--------------------E-mail相關資料建立開始------------------------------------------------------------
		List myList;
		
		if(FN_Type.equals("550")){  //判斷是否為法規
			 myList = manager.ezGetBy(
					" Select E.Email, E.cname From empl E " +
                    " Where E.Oid in('1558','75','1179','2126')" );  //'75','1179','1856','2126'			   
		}else{
			 myList = manager.ezGetBy(
					" Select E.Email, E.cname From File_User FU, empl E " +
                    " Where FU.Empl_Idno = E.idno " +
                    "   And FU.FN_Oid = '"+FN_Oid+"'");
		}
		
		String myString = "<font color=red><b>有新增資料上傳</b></font>";
		if(aForm.getString("showType").equals("Modify")){
			myString = "<font color=blue><b>有資料更新異動</b></font>";
		}		
		
		StringBuilder sb=new StringBuilder("<p>您好</p>");
		sb.append(FN_name + ",<br>");
		sb.append("於" + lastModified + myString + "。<br><br>");
		if(FN_Type.equals("550")){  //判斷是否為法規
			sb.append("請至校務資訊系統/法規資料審核作業，執行審核動作。<br><br>");
		}else{
			sb.append("如需要進行檢閱,請上校務資訊系統下載。<br><br>");
		}
		sb.append("資料上傳者:"+UserName+"。<br><br>");
		
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
//--------------------E-mail相關資料建立結束------------------------------------------------------------	
		
		String ShareType = aForm.getString("ShareType");
		String Data_name = aForm.getString("Data_name");
		String KeyWords = aForm.getString("KeyWords");		
		
		String MeetingDate= aForm.getString("MeetingDate");
		String DocNo= aForm.getString("DocNo");
		String DocDate = aForm.getString("DocDate");
		String MeetingType= aForm.getString("MeetingType");
		String approve = "96";
		if(FN_Type.equals("549")){  //判斷是否為會議記錄
			approve = "97";
		}		
		
		String folderName=FN_name;
		if(FN_Type.equals("549")){  //判斷是否為會議記錄
			folderName=FN_name +"_"+ schoolYear+schoolTerm;
		}
		
		String Data_URL = "/home/mysql/cisftp/LawMeeting/"+folderName;//+"/"+myImage.getFileName();		
		
		if(aForm.getString("showType").equals("Modify")){
			
			manager.executeSql(
					" Update File_DataList " +
	                " Set schoolYear='"+schoolYear+"', schoolTerm='"+schoolTerm+"', ShareType='"+ShareType+"', Data_name='"+Data_name+"', " +
	                "     KeyWords='"+KeyWords+"', MeetingDate='"+MeetingDate+"', DocNo='"+DocNo+"', DocDate='"+DocDate+"', MeetingType='"+MeetingType+"', " +
	                "     approve='"+approve+"', User='"+UserIdno+"'" +
                    " Where Oid='"+aForm.getString("FL_Oid")+"'");			
	        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "修改完成"));
			
//--------------------發送Email 開始------------------------------------------------------------
	        /* 暫時不發送
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
			); */
	        
//--------------------發送Email 結束------------------------------------------------------------
	        
			setContentPage(request.getSession(false), "FILE/File_Upload.jsp");	
			return unspecified(mapping, form, request, response);
			
		}else{
			
			
			String pristineData = aForm.get("myImage").toString();
			
			String SqlDataName = manager.ezGetString(
					" Select Data_name From File_DataList " +
					" Where FN_Oid='"+FN_Oid+"' " +
					"   And schoolYear='"+schoolYear+"' " +
					"   And schoolTerm='"+schoolTerm+"' " +
					"   And Data_name='"+Data_name+"'");
			
			
			//System.out.println(pristineData.substring(pristineData.length()-3));
			if(!SqlDataName.equals("")){
				messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "檔案名稱已存在"));
				saveErrors(request, messages);
			
			}else if(!pristineData.substring(pristineData.length()-3).equals("pdf")){
				messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "上傳失敗，僅限上傳PDF格式檔案"));
				saveErrors(request, messages);
				
			/*}else if(!pristineData.substring(pristineData.length()-3).equals("PDF")){
				messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "上傳失敗，僅限上傳PDF格式檔案"));
				saveErrors(request, messages);*/
			}else{		
			
				manager.uploadFileFTPServer( //FTP上傳
						myImage, session.getServletContext().getRealPath("/UserFiles/").toString(), "", "", folderName);
				
				manager.executeSql(
					"Insert Into File_DataList(" +
					"       FN_Oid, schoolYear, schoolTerm, ShareType, Data_name, Data_URL, KeyWords, pristineData, " +
					"       approve, MeetingDate, DocNo, DocDate, MeetingType, User) " +
					"Values(" +
					"    '"+FN_Oid+"','"+schoolYear+"','"+schoolTerm+"','"+ShareType+"','"+Data_name+"','"+Data_URL+"', " +
				    "    '"+KeyWords+"','"+pristineData+"','"+approve+"','"+MeetingDate+"','"+DocNo+"','"+DocDate+"'," +
				    "    '"+MeetingType+"','"+UserIdno+"')");
					
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "上傳完成"));
				
//--------------------發送Email------------------------------------------------------------
				/*  暫時不發送
				saveMessages(request, messages);
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
				*/
//--------------------發送Email------------------------------------------------------------
			}				
			
			setContentPage(request.getSession(false), "FILE/File_Upload.jsp");	
			return unspecified(mapping, form, request, response);
		}		
		//return mapping.findForward("Main");
		 
	}
	
	/**
	 * 查詢
	 */
	public ActionForward Query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, "FN");
		Toolket.resetCheckboxCookie(response, "FNList");
		
		String FN_Oid = aForm.getString("FN_Oid");
		String FN_Type = aForm.getString("FN_Type");
		String FN_name = aForm.getString("FN_name");
		String schoolYear = aForm.getString("schoolYear");
		String schoolTerm = aForm.getString("schoolTerm");		
		String ShareType = aForm.getString("ShareType");
		String Data_name = aForm.getString("Data_name");
		String KeyWords = aForm.getString("KeyWords");
		
		String SelYear = " And FD.schoolYear = '"+schoolYear+"' ";
		String SelTerm = " And FD.schoolTerm = '"+schoolTerm+"' ";
		String SelType = " And FD.ShareType = '"+ShareType+"' ";
		String SelDataName = " And FD.Data_name Like ('%"+ Data_name +"%') ";
		if(schoolYear.equals("")){
			SelYear = "";			
		}
		if(schoolTerm.equals("")){
			SelTerm = "";			
		}			
		if(Data_name.equals("")){
			SelDataName = "";	
		}
		
		List<FileName> FNList = manager.ezGetBy(
				" Select FD.Oid, FD.schoolYear, FD.schoolTerm, FD.Data_name, R.name ShareType, FD.lastModified, " +
				"        FD.DocNo, FD.MeetingType, FD.MeetingDate, RC.name, FN.Name FN_Name " +
				" From File_DataList FD, code5 R, Rccode RC, File_Name FN " +
				" Where FD.ShareType = R.Oid " +
				"   And FD.FN_Oid = FN.Oid" +
				"   And FD.approve = RC.Oid" +
				"   And FD.FN_Oid = '"+FN_Oid+"' "
			    +  SelYear +  SelTerm + SelDataName + SelType );    

        session.setAttribute("FileName", FN_name);
		session.setAttribute("FN_List", FNList);
		session.setAttribute("myOpen", "open");
		session.setAttribute("showType", "Query");
		
		setContentPage(request.getSession(false), "FILE/File_Upload.jsp");
		return mapping.findForward("Main");
	}	
	
	
	/**
	 * 啟動修改
	 */
	public ActionForward Modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		Toolket.resetCheckboxCookie(response, "FN");
		Toolket.resetCheckboxCookie(response, "FNList");
		
		String teacherId = user.getMember().getAccount();		
		String Fids = Toolket.getSelectedIndexFromCookie(request, "FNList");
		String FL_id = Fids.substring(1, Fids.length()-1);
		String FN_id = manager.ezGetString("Select FN_Oid From File_DataList Where Oid = '"+FL_id+"'");
		
		session.setAttribute("ShareType", manager.ezGetBy("SELECT Oid, name FROM code5 Where category = 'FileUpload' Order By Oid"));  //取得分享型態選項		
		session.setAttribute("myOpen", "open");
		session.setAttribute("showType", "Modify");
		session.setAttribute("FL_Oid", FL_id);
		session.setAttribute("FN_Oid", FN_id);		
		session.setAttribute("FN_name",  manager.ezGetString(
				"Select Name From File_Name Where Oid = '"+FN_id+"'"));      //取得法規/會議名稱
		session.setAttribute("FN_Type",  manager.ezGetString(
				"Select FN_Type From File_Name Where Oid = '"+FN_id+"'"));   //取得檔案類型(法規/會議)
		session.setAttribute("schoolYear", manager.ezGetString(
				"Select schoolYear From File_DataList Where Oid = '"+FL_id+"'"));
		session.setAttribute("schoolTerm", manager.ezGetString(
				"Select schoolTerm From File_DataList Where Oid = '"+FL_id+"'"));
		session.setAttribute("ShareType_SQL", manager.ezGetString(
				"Select ShareType From File_DataList Where Oid = '"+FL_id+"'"));//取得分享型態選項
		session.setAttribute("Data_name", manager.ezGetString(
				"Select Data_name From File_DataList Where Oid = '"+FL_id+"'"));
		session.setAttribute("KeyWords", manager.ezGetString(
				"Select KeyWords From File_DataList Where Oid = '"+FL_id+"'"));
		session.setAttribute("approve", manager.ezGetString(
				"Select approve From File_DataList Where Oid = '"+FL_id+"'"));
		session.setAttribute("approveTemp", manager.ezGetString(
				"Select approveTemp From File_DataList Where Oid = '"+FL_id+"'"));
		session.setAttribute("MeetingDate", manager.ezGetString(
				"Select MeetingDate From File_DataList Where Oid = '"+FL_id+"'"));
		session.setAttribute("DocNo", manager.ezGetString(
				"Select DocNo From File_DataList Where Oid = '"+FL_id+"'"));
		session.setAttribute("DocDate", manager.ezGetString(
				"Select DocDate From File_DataList Where Oid = '"+FL_id+"'"));
		session.setAttribute("MeetingType", manager.ezGetString(
				"Select MeetingType From File_DataList Where Oid = '"+FL_id+"'"));
		
		setContentPage(request.getSession(false), "FILE/File_Upload.jsp");
		return mapping.findForward("Main");
	}	
	
	/**
	 * 刪除資料
	 */
	public ActionForward Delete(ActionMapping mapping, ActionForm form,
		   HttpServletRequest request, HttpServletResponse response)
	       throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		ActionMessages messages = new ActionMessages();
		Toolket.resetCheckboxCookie(response, "FN");
		Toolket.resetCheckboxCookie(response, "FNList");
		
		String teacherId = user.getMember().getAccount();		
		String Fids = Toolket.getSelectedIndexFromCookie(request, "FNList");
		String FL_id = Fids.substring(1, Fids.length()-1);
		String approve = manager.ezGetString("Select approve From File_DataList Where Oid = '"+FL_id+"'");
		
		if(approve.equals("97")){
			messages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "該檔案經審核後核可，不可刪除。"));
			saveErrors(request, messages);
		}else{
			manager.executeSql("DELETE FROM File_DataList Where Oid='"+FL_id+"'");	
		}
		
		setContentPage(request.getSession(false), "FILE/File_Upload.jsp");
		return unspecified(mapping, form, request, response);
		//return mapping.findForward("Main");
	}

	@Override
	protected Map getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("Create", "Create");		
		map.put("Query", "Query");
		map.put("Delete", "Delete");
		map.put("UploadFile", "UploadFile");
		map.put("Modify", "Modify");
		map.put("GoOn", "GoOn");
		return map;
	}

}
