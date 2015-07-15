package tw.edu.chit.struts.action.secretary;

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

public class File_SchemeAction extends BaseLookupDispatchAction{

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
				" Select FN.Oid, FN.Name, C5.name FN_Type, CU.name CEname " +
				" From File_Name FN, code5 C5, code5 CU" +
				" Where FN.FN_Type = C5.Oid" +
				"   And FN.FN_Unit = CU.Oid");    
		
		session.setAttribute("Fn", FN);		
		session.setAttribute("myOpen", "close");
		session.setAttribute("myTest", "");
		
		setContentPage(request.getSession(false), "FILE/File_Scheme.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 啟動新增
	 */
	public ActionForward Create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
				
		session.setAttribute("myOpen", "open");	
		session.setAttribute("FN_Oid", "");
		session.setAttribute("FN_name", "");
		session.setAttribute("FN_type", manager.ezGetBy(  //取得類別
				"Select Oid, name From code5 Where category='FileScheme' And sequence='1'"));
		session.setAttribute("OpenLine", manager.ezGetBy(  //取得開放狀態
				"Select Oid, name From code5 Where category='FileScheme' And sequence='3'"));
		session.setAttribute("Share", manager.ezGetBy(  //取得分享狀態
				"Select Oid, name From code5 Where category='FileScheme' And sequence='4'"));
		session.setAttribute("FN_Unit", manager.ezGetBy(  //取得所屬單位選項
				"SELECT Oid, name FROM code5 WHERE category='FileUnit' ORDER BY Oid"));
		session.setAttribute("Share_SQL", "");
		session.setAttribute("OpenLine_SQL", "");
		
		setContentPage(request.getSession(false), "FILE/File_Scheme.jsp");
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
		
		//String teacherId = user.getMember().getAccount();		
		String ids = Toolket.getSelectedIndexFromCookie(request, "FN");
		String id_s = ids.substring(1, ids.length()-1);		
		
		session.setAttribute("FN_type", manager.ezGetBy(  //取得類別
				"Select Oid, name From code5 Where category='FileScheme' And sequence='1'"));
		session.setAttribute("OpenLine", manager.ezGetBy(  //取得開放狀態
				"Select Oid, name From code5 Where category='FileScheme' And sequence='3'"));
		session.setAttribute("Share", manager.ezGetBy(  //取得分享狀態
				"Select Oid, name From code5 Where category='FileScheme' And sequence='4'"));
		session.setAttribute("FN_Unit", manager.ezGetBy(  //取得所屬單位選項
				"SELECT Oid, name FROM code5 WHERE category='FileUnit' ORDER BY Oid"));
		
		session.setAttribute("myOpen", "open");
		session.setAttribute("FN_Oid", id_s);
		session.setAttribute("FN_name", manager.ezGetString(
				"Select Name From File_Name Where Oid='"+id_s+"'"));
		session.setAttribute("FN_type_SQL", manager.ezGetString(
				"Select FN_Type From File_Name Where Oid='"+id_s+"'"));
		session.setAttribute("FN_Unit_SQL", manager.ezGetString(
				"Select FN_Unit From File_Name Where Oid='"+id_s+"'"));
		session.setAttribute("OpenLine_SQL", manager.ezGetString(
				"Select OpenLine From File_Name Where Oid='"+id_s+"'"));
		session.setAttribute("Share_SQL", manager.ezGetString(
				"Select Share From File_Name Where Oid='"+id_s+"'"));
		
		setContentPage(request.getSession(false), "FILE/File_Scheme.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 啟動指定權限_單位搜尋
	 */
	public ActionForward PowerAppoint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		Toolket.resetCheckboxCookie(response, "FN");		
		
		//String teacherId = user.getMember().getAccount();		
		String ids = Toolket.getSelectedIndexFromCookie(request, "FN");
		String id_s = ids.substring(1, ids.length()-1);
		
		session.setAttribute("myOpen", "Power");
		session.setAttribute("allUnit", manager.ezGetBy(  //取得指定權限單位選項
				"Select idno, name FROM CodeEmpl WHERE (category='Unit' OR category='UnitTeach') ORDER BY sequence"));
		session.setAttribute("FU_type", manager.ezGetBy(  //取得權限選項
				"Select Oid, name From code5 Where category='FileScheme' And sequence='2'"));
		session.setAttribute("FN_Oid", id_s);		
		
		setContentPage(request.getSession(false), "FILE/File_Scheme.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 啟動指定權限_人員明細
	 */
	public ActionForward GoOn(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, "FN");
		
		/*
		String teacherId = user.getMember().getAccount();		
		String ids = Toolket.getSelectedIndexFromCookie(request, "FN");
		String id_s = ids.substring(1, ids.length()-1);		
		*/
		String FnOid = aForm.getString("FN_Oid");
		//System.out.println(FnOid);
		String sunit = aForm.getString("sunit");
		String SqlUnit = " Where CE.idno = '"+sunit+"'";
		if(sunit.equals("")){
			SqlUnit = "";
		}
		
		List UserList=new ArrayList();				
		
		UserList=manager.ezGetBy(
				" Select CE.name CE_N, E.idno E_ID, E.sname E_SN, E.cname E_CN, FU.`FU_Type` FU_T, FU.FN_Oid FU_NO" +
				" From empl E" +
				" Join CodeEmpl CE on E.unit = CE.idno" +
				" LEFT Outer Join (Select * From File_User Where FN_Oid = '"+FnOid+"') FU on E.idno = FU.Empl_Idno" +
				  SqlUnit + " Order By CE.`category`");
		
		//System.out.println(UserList);
		request.setAttribute("UserList", UserList);
		session.setAttribute("myOpen", "Power");
		session.setAttribute("myTest", "GoOn");
		session.setAttribute("allUnit", manager.ezGetBy(  //取得權限指定單位
				"SELECT idno, name FROM CodeEmpl WHERE (category='Unit' OR category='UnitTeach') ORDER BY sequence"));
		session.setAttribute("FU_type", manager.ezGetBy(  //取得權限選項
				"Select Oid, name From code5 Where category='FileScheme' And sequence='2'"));
		session.setAttribute("FN_Oid", FnOid);		
		
		setContentPage(request.getSession(false), "FILE/File_Scheme.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 啟動委員任期設定
	 */
	public ActionForward CommitteeAppoint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, "FN");		
		
		
		//String teacherId = user.getMember().getAccount();		
		String ids = Toolket.getSelectedIndexFromCookie(request, "FN");
		String FN_Oid = ids.substring(1, ids.length()-1);		
		
		
		//String FnOid = aForm.getString("FN_Oid");
		/*System.out.println(FnOid);
		String sunit = aForm.getString("sunit");
		String SqlUnit = " Where CE.idno = '"+sunit+"'";
		if(sunit.equals("")){
			SqlUnit = "";
		}
		*/
		
		List CMList=new ArrayList();				
		
		CMList=manager.ezGetBy(
				" Select CE.name CE_name, E.idno E_ID, E.sname E_SN, E.cname E_CN, FU.FN_Oid FU_NO, FU.CM_Bdate BD, FU.CM_Edate ED, FU.Oid FU_Oid" +
				" From empl E, File_User FU, CodeEmpl CE" +
				" Where E.idno = FU.Empl_Idno " +
				"   And E.unit = CE.idno " +
				"   And CE.category in('Unit','UnitTeach')" +
				"   And FU.FN_Oid = '"+FN_Oid+"' " +
				"   And FU.FU_Type = '645'");
		
		//System.out.println(UserList);
		request.setAttribute("CMList", CMList);
		session.setAttribute("myOpen", "CommitteeAppoint");
		session.setAttribute("FN_Oid", FN_Oid);		
		
		setContentPage(request.getSession(false), "FILE/File_Scheme.jsp");
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
		//System.out.println("myTest="+aForm.getString("myTest"));
		
		//System.out.println("myOpen="+aForm.getString("myOpen")+";");
		if(aForm.getString("myOpen").equals("open")){  //判斷要執行存檔的是新增 修改模式(File_Name)
			
			String idno = user.getMember().getAccount();			
			String Oid = aForm.getString("FN_Oid");
			String Name = aForm.getString("FN_name");
			String FN_Type = aForm.getString("FN_type");	
			String FN_Unit = aForm.getString("FN_Unit");
			String OpenLine = aForm.getString("OpenLine");	
			String Share = aForm.getString("Share");
			String myID = manager.ezGetString(  //檢查資料表中是否有重複的續號
					"Select Oid From File_Name Where Oid='"+Oid+"'");
			
			if(myID==""){  //如果沒有重複,則執行新增資料
				manager.executeSql(
						"Insert Into File_Name(Name,FN_Type,FN_Unit,OpenLine,Share) " +
						"Values('"+Name+"','"+FN_Type+"','"+FN_Unit+"','"+OpenLine+"','"+Share+"')");
				String MaxOid = manager.ezGetString("Select Max(Oid) From File_Name");
				manager.executeSql(
						"Insert Into File_User(FN_Oid,Empl_Idno,FU_Type) " +
						"Values('"+MaxOid+"','N122778778','552')," +  //LEO
						"      ('"+MaxOid+"','A224905194','552')," +  //貞伶
						"      ('"+MaxOid+"','D221683087','552')");   //佳瑜
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "新增完成"));				
			}else{         //如果重複,則執行更新
				manager.executeSql(
						"Update File_Name " +
				        "Set Name='"+Name+"', FN_Type='"+FN_Type+"', FN_Unit='"+FN_Unit+"' " +", OpenLine='"+OpenLine+"' " +", Share='"+Share+"' " +
		                "Where Oid='"+myID+"'");
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "修改完成"));
			}
			saveMessages(request, messages);
			
		}else if(aForm.getString("myOpen").equals("Power")){  //判斷要執行的是權限指定(File_User)
			
			if(aForm.getString("myTest").equals("GoOn")){  //如果是透過單位批次方式
				
				String FN_Oid=aForm.getString("FN_Oid");
				String FU_emplidno[]=aForm.getStrings("FU_emplidno");
				String FU_type[]=aForm.getStrings("FU_type");
				
				for(int i=0; i<FU_emplidno.length; i++){				
					String SQL_FuOid =  manager.ezGetString(    //檢查資料表是否已有權限		
							"Select Oid From File_User Where FN_Oid='"+FN_Oid+"' And Empl_Idno='"+FU_emplidno[i]+"'");		
					String emplOid = manager.ezGetString(    //取得員工序號(empl.Oid)				
							"Select Oid From empl Where idno='"+FU_emplidno[i]+"'");
					String upFuOid = manager.ezGetString(    //檢查是否有其他資料上傳權限				
							"Select Oid From File_User " +
							"Where FN_Oid<>'"+FN_Oid+"' And Empl_Idno='"+FU_emplidno[i]+"' And FU_Type='551'");
					String upFuOidAll = manager.ezGetString(    //檢查是否有其他資料上傳權限				
							"Select Count(Oid) From File_User " +
							"Where Empl_Idno='"+FU_emplidno[i]+"' And FU_Type='551'");
					Map dowFuOid = manager.ezGetMap(    //檢查是否有其他資料下載權限				
							"Select Oid From File_User " +
							"Where FN_Oid<>'"+FN_Oid+"' And Empl_Idno='"+FU_emplidno[i]+"' And FU_Type='552'");
				    //System.out.println(FN_Oid+";"+FU_emplidno[i]+";"+upFuOidAll);
					if(!SQL_FuOid.equals("")){  //本來就已經有權限					
						String SQL_FuType =  manager.ezGetString(
								"Select FU_Type From File_User Where FN_Oid='"+FN_Oid+"' And Empl_Idno='"+FU_emplidno[i]+"'");  //檢查原有權限別					
						if(!FU_type[i+1].equals("null")){  //欄位值不為null					
							if(!SQL_FuType.equals(FU_type[i+1])){  //欄位值!=原有權限							
								manager.executeSql("UPDATE File_User SET FU_Type='"+FU_type[i+1]+"' WHERE Oid = '"+SQL_FuOid+"'");  //更新File_User權限別							
								if(upFuOidAll=="0"){  //如果沒有其他資料上傳權限								
									if(FU_type[i+1].equals("551")){  //欄位值=上傳--新增UnitBelong上傳									
										manager.executeSql(" INSERT INTO UnitBelong (EmpOid, UnitNo) VALUES ('"+emplOid+"','40U')");								
									}else if(FU_type[i+1].equals("552")){  //欄位值=讀取--刪除UnitBelong上傳									
										manager.executeSql("DELETE FROM UnitBelong Where EmpOid='"+emplOid+"' And UnitNo='40U'");								
									}					
								}						
							}				
						}else if(FU_type[i+1].equals("null")){  //欄位值=null						
							manager.executeSql("DELETE FROM File_User Where Oid='"+SQL_FuOid+"'");  //從File_User移除資料						
							if(SQL_FuType.equals("551")){  //原有權限別=上傳--刪除UnitBelong上傳 && 刪除UnitBelong讀取							
								if(upFuOid=="0"){  //如果沒有其他資料上傳權限								
									manager.executeSql("DELETE FROM UnitBelong Where EmpOid='"+emplOid+"' And UnitNo='40U'");							
								}							
								//if(dowFuOid==null){  //如果沒有其他資料下載權限								
								//	manager.executeSql("DELETE FROM UnitBelong Where EmpOid='"+emplOid+"' And UnitNo='40D'");							
								//}						
							} //else if(SQL_FuType.equals("552")){  //原有權限別=讀取--刪除UnitBelong讀取							
							//	if(dowFuOid==null){  //如果沒有其他資料下載權限								
							//		manager.executeSql("DELETE FROM UnitBelong Where EmpOid='"+emplOid+"' And UnitNo='40D'");							
							//	}						
							//}					
						}					
					}else if(SQL_FuOid.equals("")){  //本來就無權限					
						if(!FU_type[i+1].equals("null")){	  //欄位值不為null						
							manager.executeSql(" INSERT INTO File_User (FN_Oid, Empl_Idno, FU_Type)" +
								           " VALUES ('"+FN_Oid+"','"+FU_emplidno[i]+"','"+FU_type[i+1]+"')");  //在File_User新增一筆資料						
							if(FU_type[i+1].equals("551")){  //欄位值=上傳--新增UnitBelong上傳 && 新增UnitBelong讀取					
								if(upFuOidAll=="0"){  //如果沒有其他資料上傳權限								
									manager.executeSql(" INSERT INTO UnitBelong (EmpOid, UnitNo) VALUES ('"+emplOid+"','40U')");						
								}							
								//if(dowFuOid==null){  //如果沒有其他資料下載權限								
								//	manager.executeSql(" INSERT INTO UnitBelong (EmpOid, UnitNo) VALUES ('"+emplOid+"','40D')");							
								//}						
							}//else if(FU_type[i+1].equals("552")){  //欄位值=讀取--新增UnitBelong讀取							
							//	if(dowFuOid==null){  //如果沒有其他資料下載權限								
							//		manager.executeSql(" INSERT INTO UnitBelong (EmpOid, UnitNo) VALUES ('"+emplOid+"','40D')");						
							//	}						
							//}					
						}				
					}				
				}			
			}else if(aForm.getString("myTest").equals("")){
				
				String emplIdno = aForm.getString("fsidno");			
				String FN_type1 = aForm.getString("FU_type1");		
				String FN_Oid=aForm.getString("FN_Oid");			
				String SQL_FuOid = manager.ezGetString("Select Oid From File_User Where FN_Oid='"+FN_Oid+"' And Empl_Idno='"+emplIdno+"'");  //檢查資料表是否已有權限			
				String emplOid = manager.ezGetString("Select Oid From empl Where idno='"+emplIdno+"'");  //取得員工序號(empl.Oid)			
				String upFuOid = manager.ezGetString(    //檢查是否有其他資料上傳權限				
						"Select Oid From File_User " +
						"Where FN_Oid<>'"+FN_Oid+"' And Empl_Idno='"+emplIdno+"' And FU_Type='551'");
				String upFuOidAll = manager.ezGetString(    //檢查是否有其他資料上傳權限				
						"Select Count(Oid) From File_User " +
						"Where Empl_Idno='"+emplIdno+"' And FU_Type='551'");
				//Map upFuOid = manager.ezGetMap(
				//	"Select Oid From File_User Where FN_Oid<>'"+FN_Oid+"' And Empl_Idno='"+emplIdno+"' And FU_Type='551'");  //檢查是否有其他資料上傳權限			
				Map dowFuOid = manager.ezGetMap(
					"Select Oid From File_User Where FN_Oid<>'"+FN_Oid+"' And Empl_Idno='"+emplIdno+"' And FU_Type='552'");  //檢查是否有其他資料下載權限			
				if(!SQL_FuOid.equals("")){  //本來就已經有權限				
					String SQL_FuType =  manager.ezGetString("Select FU_Type From File_User Where FN_Oid='"+FN_Oid+"' And Empl_Idno='"+emplIdno+"'");  //檢查原有權限別				
					if(!FN_type1.equals("null")){  //欄位值不為null					
						if(!SQL_FuType.equals(FN_type1)){  //欄位值!=原有權限						
							manager.executeSql("UPDATE File_User SET FU_Type='"+FN_type1+"' WHERE Oid = '"+SQL_FuOid+"'");  //更新File_User權限別						
							if(upFuOidAll=="0"){  //如果沒有其他資料上傳權限							
								if(FN_type1.equals("551")){  //欄位值=上傳--新增UnitBelong上傳								
									manager.executeSql(" INSERT INTO UnitBelong (EmpOid, UnitNo) VALUES ('"+emplOid+"','40U')");							
								}else if(FN_type1.equals("552")){  //欄位值=讀取--刪除UnitBelong上傳								
									manager.executeSql("DELETE FROM UnitBelong Where EmpOid='"+emplOid+"' And UnitNo='40U'");							
								}						
							}					
						}				
					}else if(FN_type1.equals("null")){  //欄位值=null					
						manager.executeSql("DELETE FROM File_User Where Oid='"+SQL_FuOid+"'");  //從File_User移除資料					
						if(SQL_FuType.equals("551")){  //原有權限別=上傳--刪除UnitBelong上傳 && 刪除UnitBelong讀取						
							if(upFuOid=="0"){  //如果沒有其他資料上傳權限							
								manager.executeSql("DELETE FROM UnitBelong Where EmpOid='"+emplOid+"' And UnitNo='40U'");						
							}						
							//if(dowFuOid==null){  //如果沒有其他資料下載權限							
							//	manager.executeSql("DELETE FROM UnitBelong Where EmpOid='"+emplOid+"' And UnitNo='40D'");						
							//}					
						}//else if(SQL_FuType.equals("552")){  //原有權限別=讀取--刪除UnitBelong讀取						
						//	if(dowFuOid==null){  //如果沒有其他資料下載權限							
						//		manager.executeSql("DELETE FROM UnitBelong Where EmpOid='"+emplOid+"' And UnitNo='40D'");						
						//	}					
						//}				
					}			
				}else if(SQL_FuOid.equals("")){  //本來就無權限				
					if(!FN_type1.equals("null")){	  //欄位值不為null					
						manager.executeSql(" INSERT INTO File_User (FN_Oid, Empl_Idno, FU_Type)" +
							           " VALUES ('"+FN_Oid+"','"+emplIdno+"','"+FN_type1+"')");  //在File_User新增一筆資料					
						if(FN_type1.equals("551")){  //欄位值=上傳--新增UnitBelong上傳 && 新增UnitBelong讀取						
							if(upFuOidAll=="0"){  //如果沒有其他資料上傳權限							
								manager.executeSql(" INSERT INTO UnitBelong (EmpOid, UnitNo) VALUES ('"+emplOid+"','40U')");						
							}						
							//if(dowFuOid==null){  //如果沒有其他資料下載權限							
							//	manager.executeSql(" INSERT INTO UnitBelong (EmpOid, UnitNo) VALUES ('"+emplOid+"','40D')");						
							//}					
						}//else if(FN_type1.equals("552")){  //欄位值=讀取--新增UnitBelong讀取						
						//	if(dowFuOid==null){  //如果沒有其他資料下載權限							
						//		manager.executeSql(" INSERT INTO UnitBelong (EmpOid, UnitNo) VALUES ('"+emplOid+"','40D')");
						//	}
						//}
					}
				}
			}			
//-------------------------E-mail相關資料建立開始-----------------------------------------------
			List myList;		
			Date lastModified = new Date();
			String FN_Oid=aForm.getString("FN_Oid");
			String FN_name = manager.ezGetString("Select Name From File_Name Where Oid='"+FN_Oid+"'");
			
			myList = manager.ezGetBy(
					" Select E.Email, E.cname From File_User FU, empl E " +
		            " Where FU.Empl_Idno = E.idno " +
		            "   And FU.FN_Oid = '"+aForm.getString("FN_Oid")+"'" +
		            "   And FU.FU_Type = '551'"
		    );								
			
			StringBuilder sb=new StringBuilder("<p>您好</p>");
			sb.append("<font color=red><b>"+FN_name + "</b></font>會議名稱/法規條例之項目,<br>");
			sb.append("於" + lastModified + "已完成基本設定及權限賦予。<br><br>");
			sb.append("請透過校務資訊系統_會議/法規文件，將相關資料上傳。<br><br>");					
			
			FileDataSource[] file = null;
			Map map=manager.getCISMailServerInfo();
			Date adate=new Date();
			InternetAddress addr;
			List tmp=new ArrayList();
			int s=0;
			if(myList.size()>0){
			    for(int h=0; h<myList.size(); h++){
				    tmp.add(myList.get(h));
			    }
			
			    InternetAddress address[] = new InternetAddress[tmp.size()];
			    if(tmp.size()>0){
				    for(int h=0; h<tmp.size(); h++){
					    try{
						    addr=new InternetAddress(((Map)tmp.get(h)).get("Email").toString(), ((Map)tmp.get(h)).get("cname").toString(), "BIG5");
						    address[h]=addr;	
					    }catch(Exception e){
						    e.printStackTrace();
					    }
				    }		
			    }
			
			    /*  暫時關閉發送MAIL
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
			    );*/
			}
//-------------------------E-mail相關資料建立結束-----------------------------------------------		
		}else if(aForm.getString("myOpen").equals("CommitteeAppoint")){
			String Oid[]=aForm.getStrings("FU_Oid");
			String CM_Bdate[]=aForm.getStrings("startDate");
			String CM_Edate[]=aForm.getStrings("endDate");
			
			for(int i=0; i<Oid.length; i++){
				
				//System.out.println("O='"+Oid[i]+"',B='"+CM_Bdate[i]+"',E='"+CM_Edate[i]+"'");
				
				
				manager.executeSql(
						"UPDATE File_User " +
						"SET CM_Bdate='"+CM_Bdate[i+1]+"', CM_Edate='"+CM_Edate[i+1]+"' " +
						"WHERE Oid = '"+Oid[i]+"'");
				
			}
			
		}
		
		session.setAttribute("myOpen", "close");
		session.setAttribute("myTest", "");
		setContentPage(request.getSession(false), "FILE/File_Scheme.jsp");	
		return unspecified(mapping, form, request, response);
		//return mapping.findForward("Main");	
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
		
		String teacherId = user.getMember().getAccount();		
		String ids = Toolket.getSelectedIndexFromCookie(request, "FN");
		String id_s = ids.substring(1, ids.length()-1);		
		
		manager.executeSql("DELETE FROM File_Name Where Oid='"+id_s+"'");      //刪除文件設定檔
		manager.executeSql("DELETE FROM File_User Where FN_Oid='"+id_s+"'");   //刪除權限設定檔
		
		setContentPage(request.getSession(false), "FILE/File_Scheme.jsp");
		return unspecified(mapping, form, request, response);
		//return mapping.findForward("Main");
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
		map.put("CommitteeAppoint", "CommitteeAppoint");
		map.put("GoOn", "GoOn");
		return map;
	}

}
