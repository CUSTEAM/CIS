package tw.edu.chit.struts.action.teacher;

import java.util.Date;
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

import tw.edu.chit.model.Rcproj;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class RcprojAction extends BaseLookupDispatchAction {
	
	/**
	 * 初始畫面取得
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		Toolket.resetCheckboxCookie(response, "rcproj");
		
		String teacherId = user.getMember().getAccount();		
		
		session.setAttribute("school_Year", manager.getSchoolYear());      //取得學年度..
		session.setAttribute("TeacherName", user.getMember().getName());   //取得教師姓名
		session.setAttribute("TeacherUnit", user.getMember().getUnit2());  //取得教師單位
		session.setAttribute("YesNo", manager.ezGetBy("Select Oid, name From Rccode Where type='1'"));           //取得是與否
		session.setAttribute("kindid", manager.ezGetBy("Select Oid, name From Rccode " +
				                                       "Where type='6' And sequence = '0' Order By name"));      //取得專案類型
		session.setAttribute("jobid", manager.ezGetBy("Select Oid, name From Rccode Where type='8'"));           //取得工作類別
		session.setAttribute("budgetid1", manager.ezGetBy("Select Oid, name From Rccode Where type='9'"));       //取得主要經濟來源
		session.setAttribute("budgetidState", manager.ezGetBy("Select Oid, name From Rccode Where type='7'"));   //取得經費狀況
		
		
		setContentPage(request.getSession(false), "teacher/Rcproj.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 新增資料
	 */
	public ActionForward Save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		ActionMessages messages = new ActionMessages();
		
		String idno = user.getMember().getAccount();
		String schoolYear = aForm.getString("schoolYear");
		String projno = aForm.getString("projno");
		String projname= aForm.getString("projname");
		String kindid = aForm.getString("kindid");		
		String bdate = aForm.getString("bdate");
		String edate = aForm.getString("edate");
		String jobid = aForm.getString("jobid");
		String money = aForm.getString("money");
		String GMoney = aForm.getString("G_money");
		String BMoney = aForm.getString("B_money");
		String OMoney = aForm.getString("O_money");
		String SMoney = aForm.getString("S_money");
		String budgetid1 = aForm.getString("budgetid1");
		String unitname = aForm.getString("unitname");
		String budgetid2 = aForm.getString("budgetid2");
		String favorunit = aForm.getString("favorunit");
		String authorunit1 = aForm.getString("authorunit1");
		String authorunit2 = aForm.getString("authorunit2");
		String coopunit1 = aForm.getString("coopunit1");
		String coopunit2 = aForm.getString("coopunit2");		
		//String fullTime = aForm.getString("FullTime");
		//String partTime = aForm.getString("PartTime");
		//String GTrainee = aForm.getString("G_trainee");
		//String BTrainee = aForm.getString("B_trainee");
		//String OTrainee = aForm.getString("O_trainee");
		String intor = aForm.getString("intor");
		String turnIn = aForm.getString("turnIn");
		String turnInG = aForm.getString("turnIn_G");
		String turnInB = aForm.getString("turnIn_B");
		String turnInO = aForm.getString("turnIn_O");
		String turnOut = aForm.getString("turnOut");
		String turnOutG = aForm.getString("turnOut_G");
		String turnOutB = aForm.getString("turnOut_B");
		String turnOutO = aForm.getString("turnOut_O");
		String budgetidState = aForm.getString("budgetidState"); //99年新增欄位
		
		String Transfer = aForm.getString("Transfer");
		String Income = aForm.getString("Income");
		String Storage = aForm.getString("Storage");
		String Fruitful[] = request.getParameterValues("Fruitful");
		String chec1 = "0";
		String chec2 = "0";
		String chec3 = "0";
		String chec4 = "0";
		String chec5 = "0";
		String chec6 = "0";
		String chec1text = aForm.getString("chec1text");
		String chec2text = aForm.getString("chec2text");
		String chec3text = aForm.getString("chec3text");
		String chec4text = aForm.getString("chec4text");
		String chec5text = aForm.getString("chec5text");
		String chec6text = aForm.getString("chec6text");
		//System.out.println(Fruitful);
		
		if(Fruitful!=null){
			for(int i=0; i<Fruitful.length; i++){
				if(Fruitful[i].equals("1")){ chec1="1";	 }
				else if(Fruitful[i].equals("2")){ chec2="1";	}
				else if(Fruitful[i].equals("3")){ chec3="1";	}
				else if(Fruitful[i].equals("4")){ chec4="1";	}
				else if(Fruitful[i].equals("5")){ chec5="1";	}
				else if(Fruitful[i].equals("6")){ chec6="1";	}
			}
		}		
		//System.out.println("chec1='"+chec1text +"'; chec2='"+chec2text+"'; chec3='"+chec3text+"'; chec4='"+chec4text+"'; chec5='"+chec5text+"'; chec6='"+chec6text+"'");
		try{
			manager.executeSql(
					"Insert Into Rcproj(" +
					        " idno, school_year, projno, projname, kindid, bdate, edate, jobid, money," +
					        " budgetid1, unitname, budgetid2, favorunit, authorunit1, authorunit2," +
					        " coopunit1, coopunit2, intor," +
					        " G_money, B_money, O_money, S_money," +
					        " turnIn, turnIn_G, turnIn_B, turnIn_O," +
					        " turnOut, turnOut_G, turnOut_B, turnOut_O," +
					        " approve, budgetidState, Transfer, Income, Storage," +
					        " chec1, chec2, chec3, chec4, chec5, chec6, " +
					        " chec1text, chec2text, chec3text, chec4text, chec5text, chec6text) " +
					"Values('"+idno+"','"+schoolYear+"','"+projno+"','"+projname+"','"+kindid+"','"+bdate+"','"+edate+"','"+jobid+"','"+money+"'," +
						   "'"+budgetid1+"','"+unitname+"','"+budgetid2+"','"+favorunit+"','"+authorunit1+"','"+authorunit2+"'," +
						   "'"+coopunit1+"','"+coopunit2+"','"+intor+"'," +
						   "'"+GMoney+"','"+BMoney+"','"+OMoney+"','"+SMoney+"'," +
						   "'"+turnIn+"','"+turnInG+"','"+turnInB+"','"+turnInO+"'," +
						   "'"+turnOut+"','"+turnOutG+"','"+turnOutB+"','"+turnOutO+"'," +
						   "'96','"+budgetidState+"','"+Transfer+"','"+Income+"','"+Storage+"'," +
						   "'"+chec1+"','"+chec2+"','"+chec3+"','"+chec4+"','"+chec5+"','"+chec6+"'," +
						   "'"+chec1text+"','"+chec2text+"','"+chec3text+"','"+chec4text+"','"+chec5text+"','"+chec6text+"')");
			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "新增完成"));
			saveMessages(request, messages);
		}catch(Exception e){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", e));
			saveErrors(request, error);
		}
		
	
		session.removeAttribute("RcprojList");
		return mapping.findForward("Main");
	}
	
	/**
	 * 查詢資料
	 */
	public ActionForward Query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		//Toolket.resetCheckboxCookie(response, "rcact");
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;		
		
		String teacherName = user.getMember().getName();
		String teacherId = user.getMember().getAccount();
		String teacherUnit = user.getMember().getUnit2();
		Integer schoolYear = manager.getSchoolYear();
		String school_Year = aForm.getString("schoolYear");		
		
		List<Rcproj> rcproj = manager.ezGetBy(
				" Select R.Oid, R.idno, R.school_year, R.projno, R.projname, R.kindid, R.bdate, R.edate, R.jobid, R.money," +
				       " R.budgetid1, R.unitname, R.budgetid2, R.favorunit, R.authorunit1, R.authorunit2, R.coopunit1," +
				       " R.coopunit2, Rc.name"+
				" From Rcproj R, Rccode Rc" +
				" Where R.approve = Rc.Oid" +
				  " And R.idno = '"+ teacherId +"'" +
				  " And R.school_year Like ('%"+school_Year+"%')"
		);
				
		session.setAttribute("RcprojList", rcproj);
		
		setContentPage(request.getSession(false), "teacher/Rcproj.jsp");
		return mapping.findForward("Main");
	}	
	
	/**
	 * 檢視資料
	 */
	public ActionForward View(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		Toolket.resetCheckboxCookie(response, "rcproj");		
		
		String teacherId = user.getMember().getAccount();		
		String oids = Toolket.getSelectedIndexFromCookie(request, "rcproj");
		String oid_s = oids.substring(1, oids.length()-1);
		//String myKind = manager.ezGetString("Select kindid From Rcproj Where Oid='"+oid_s+"'");
		
		session.setAttribute("school_Year", manager.getSchoolYear());		//取得學年度
		session.setAttribute("TeacherName", user.getMember().getName());    //取得教師姓名
		session.setAttribute("TeacherUnit", user.getMember().getUnit2());   //取得教師單位
		session.setAttribute("YesNo", manager.ezGetBy("Select Oid, name From Rccode Where type='1'"));           //取得是與否
		session.setAttribute("kindid", manager.ezGetBy("Select Oid, name From Rccode " +
                                                       "Where type='6' And sequence = '0' Order By name"));      //取得專案類型
		session.setAttribute("jobid", manager.ezGetBy("Select Oid, name From Rccode Where type='8'"));           //取得工作類別
		session.setAttribute("budgetid1", manager.ezGetBy("Select Oid, name From Rccode Where type='9'"));       //取得主要經濟來源
		session.setAttribute("budgetidState", manager.ezGetBy("Select Oid, name From Rccode Where type='7'"));   //取得經費狀況
		
		session.setAttribute("oid_s", oid_s);
		session.setAttribute("idno", manager.ezGetString("Select idno From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("school_year", manager.ezGetString("Select school_year From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("projno", manager.ezGetString("Select projno From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("projname", manager.ezGetString("Select projname From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("myKind", manager.ezGetString("Select kindid From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("bdate", manager.ezGetString("Select bdate From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("edate", manager.ezGetString("Select edate From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("jobid_v", manager.ezGetString("Select jobid From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("money", manager.ezGetString("Select money From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("G_money", manager.ezGetString("Select G_money From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("B_money", manager.ezGetString("Select B_money From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("O_money", manager.ezGetString("Select O_money From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("S_money", manager.ezGetString("Select S_money From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("budgetid1_v", manager.ezGetString("Select budgetid1 From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("unitname", manager.ezGetString("Select unitname From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("budgetid2", manager.ezGetString("Select budgetid2 From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("favorunit", manager.ezGetString("Select favorunit From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("authorunit1", manager.ezGetString("Select authorunit1 From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("authorunit2", manager.ezGetString("Select authorunit2 From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("coopunit1", manager.ezGetString("Select coopunit1 From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("coopunit2", manager.ezGetString("Select coopunit2 From Rcproj Where Oid='"+oid_s+"'"));
		//session.setAttribute("FullTime", manager.ezGetString("Select FullTime From Rcproj Where Oid='"+oid_s+"'"));
		//session.setAttribute("PartTime", manager.ezGetString("Select PartTime From Rcproj Where Oid='"+oid_s+"'"));
		//session.setAttribute("G_trainee", manager.ezGetString("Select G_trainee From Rcproj Where Oid='"+oid_s+"'"));
		//session.setAttribute("B_trainee", manager.ezGetString("Select B_trainee From Rcproj Where Oid='"+oid_s+"'"));
		//session.setAttribute("O_trainee", manager.ezGetString("Select O_trainee From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("intor", manager.ezGetString("Select intor From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("turnIn_v", manager.ezGetString("Select turnIn From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("turnOut_v", manager.ezGetString("Select turnOut From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("turnIn_G", manager.ezGetString("Select turnIn_G From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("turnIn_B", manager.ezGetString("Select turnIn_B From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("turnIn_O", manager.ezGetString("Select turnIn_O From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("turnOut_G", manager.ezGetString("Select turnOut_G From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("turnOut_B", manager.ezGetString("Select turnOut_B From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("turnOut_O", manager.ezGetString("Select turnOut_O From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("approve", manager.ezGetString("Select approve From Rcproj Where Oid = '"+oid_s+"'"));
		session.setAttribute("approveTemp", manager.ezGetString("Select approveTemp From Rcproj Where Oid = '"+oid_s+"'"));
		session.setAttribute("budgetidState_v", manager.ezGetString("Select budgetidState From Rcproj Where Oid = '"+oid_s+"'"));
		session.setAttribute("Transfer_v", manager.ezGetString("Select Transfer From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("Income", manager.ezGetString("Select Income From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("Storage", manager.ezGetString("Select Storage From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("chec1", manager.ezGetString("Select chec1 From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("chec2", manager.ezGetString("Select chec2 From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("chec3", manager.ezGetString("Select chec3 From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("chec4", manager.ezGetString("Select chec4 From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("chec5", manager.ezGetString("Select chec5 From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("chec6", manager.ezGetString("Select chec6 From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("chec1text", manager.ezGetString("Select chec1text From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("chec2text", manager.ezGetString("Select chec2text From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("chec3text", manager.ezGetString("Select chec3text From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("chec4text", manager.ezGetString("Select chec4text From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("chec5text", manager.ezGetString("Select chec5text From Rcproj Where Oid='"+oid_s+"'"));
		session.setAttribute("chec6text", manager.ezGetString("Select chec6text From Rcproj Where Oid='"+oid_s+"'"));
		
		
		setContentPage(request.getSession(false), "teacher/Rcproj_View.jsp");
		return mapping.findForward("Main");
	}
	
	@Override
	protected Map<String, String> getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("Create", "Save");
		map.put("Query", "Query");
		map.put("View", "View");
		/*
		map.put("Update", "Update");
		map.put("Delete", "Delete");		
		map.put("Back", "Back");
		*/
		return map;
	}

}
