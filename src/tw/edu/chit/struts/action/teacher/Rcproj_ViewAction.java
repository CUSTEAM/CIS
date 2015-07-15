package tw.edu.chit.struts.action.teacher;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

public class Rcproj_ViewAction extends BaseLookupDispatchAction {
	
	/**
	 * 更新資料
	 */
	public ActionForward Update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
				
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, "rcproj");
		ActionMessages messages = new ActionMessages();
	
		String oids = aForm.getString("oid_s");
        String Table = "Rcproj";		
		manager.removeRcTableBy(Table, oids);  //移除原有資料
		
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
		
		//System.out.println("chec1='"+chec1 +"'; chec2='"+chec2+"'; chec3='"+chec3+"'; chec4='"+chec4+"'; chec5='"+chec5+"'; chec6='"+chec6+"'");
		
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
						
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "更新完成"));
		saveMessages(request, messages);
	
		session.removeAttribute("RcprojList");
		setContentPage(request.getSession(false), "teacher/Rcproj.jsp");
		return mapping.findForward("Main");
		
	}
	
	/**
	 * 刪除功能，不提供給輸入者使用
	 */
	/*
	public ActionForward Delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
				
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		DynaActionForm aForm = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, "rcproj");		
	
		String oids = aForm.getString("oid_s");
		String Table = "Rcproj";
		
		manager.removeRcTableBy(Table, oids);
		
		session.removeAttribute("RcprojList");
		setContentPage(request.getSession(false), "teacher/Rcproj.jsp");
		return mapping.findForward("Main");
		
	}
	*/
	/**
	 * 返回
	 */
	public ActionForward Back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		setContentPage(request.getSession(false), "teacher/Rcproj.jsp");
		return mapping.findForward("Main");
		
	}
	
	/**
	 * 匯出/列印
	 */
	public ActionForward PrintOut(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;
		UserCredential user = (UserCredential) session.getAttribute("Credential");
		ActionMessages messages = new ActionMessages();
		
		//session.setAttribute("idno", aForm.get("idno"));
		session.setAttribute("school_year", aForm.get("schoolYear"));
		session.setAttribute("projno", aForm.get("projno"));
		session.setAttribute("projname", aForm.get("projname"));
		session.setAttribute("kind", aForm.get("kindid"));
		session.setAttribute("kindid", manager.ezGetString("Select name From Rccode Where Oid="+aForm.getString("kindid")));
		session.setAttribute("bdate", aForm.get("bdate"));
		session.setAttribute("edate", aForm.get("edate"));
		session.setAttribute("jobid", manager.ezGetString("Select name From Rccode Where Oid='"+aForm.getString("jobid")+"'"));
		session.setAttribute("money", aForm.get("money"));
		session.setAttribute("G_money", aForm.get("G_money"));
		session.setAttribute("B_money", aForm.get("B_money"));
		session.setAttribute("O_money", aForm.get("O_money"));
		session.setAttribute("S_money", aForm.get("S_money"));
		session.setAttribute("budgetid1", manager.ezGetString("Select name From Rccode Where Oid='"+aForm.getString("budgetid1")+"'"));
		session.setAttribute("unitname", aForm.get("unitname"));
		session.setAttribute("budgetid2", aForm.get("budgetid2"));
		session.setAttribute("favorunit", aForm.get("favorunit"));
		session.setAttribute("authorunit1", aForm.get("authorunit1"));
		session.setAttribute("authorunit2", aForm.get("authorunit2"));
		session.setAttribute("coopunit1", aForm.get("coopunit1"));
		session.setAttribute("coopunit2", aForm.get("coopunit2"));
		//session.setAttribute("FullTime", aForm.get("FullTime"));
		//session.setAttribute("PartTime", aForm.get("PartTime"));
		//session.setAttribute("G_trainee", aForm.get("G_trainee"));
		//session.setAttribute("B_trainee", aForm.get("B_trainee"));
		//session.setAttribute("O_trainee", aForm.get("O_trainee"));
		session.setAttribute("intor", aForm.get("intor"));
		session.setAttribute("turnIn", manager.ezGetString("Select name From Rccode Where Oid='"+aForm.getString("turnIn")+"'"));
		session.setAttribute("turnOut", manager.ezGetString("Select name From Rccode Where Oid='"+aForm.getString("turnOut")+"'"));
		session.setAttribute("turnIn_G", aForm.get("turnIn_G"));
		session.setAttribute("turnIn_B", aForm.get("turnIn_B"));
		session.setAttribute("turnIn_O", aForm.get("turnIn_O"));
		session.setAttribute("turnOut_G", aForm.get("turnOut_G"));
		session.setAttribute("turnOut_B", aForm.get("turnOut_B"));
		session.setAttribute("turnOut_O", aForm.get("turnOut_O"));
		session.setAttribute("budgetidState", manager.ezGetString("Select name From Rccode Where Oid='"+aForm.getString("budgetidState")+"'"));
		
		session.setAttribute("Transfer", manager.ezGetString("Select name From Rccode Where Oid='"+aForm.getString("Transfer")+"'"));
		session.setAttribute("Income", aForm.get("Income"));
		session.setAttribute("Storage", aForm.get("Storage"));		
		String Fruitful[] = request.getParameterValues("Fruitful");
		String chec1 = "0";
		String chec2 = "0";
		String chec3 = "0";
		String chec4 = "0";
		String chec5 = "0";
		String chec6 = "0";
		
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
        session.setAttribute("chec1", chec1);
		session.setAttribute("chec2", chec2);
		session.setAttribute("chec3", chec3);
		session.setAttribute("chec4", chec4);
		session.setAttribute("chec5", chec5);
		session.setAttribute("chec6", chec6);
		session.setAttribute("chec1text", aForm.get("chec1text"));
		session.setAttribute("chec2text", aForm.get("chec2text"));
		session.setAttribute("chec3text", aForm.get("chec3text"));
		session.setAttribute("chec4text", aForm.get("chec4text"));
		session.setAttribute("chec5text", aForm.get("chec5text"));
		session.setAttribute("chec6text", aForm.get("chec6text"));
		
		
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "Course.messageN1", "如果沒有反應,請檢查是否阻擋彈跳視窗"));
		saveMessages(request, messages);
		
		session.removeAttribute("RcprojList");
		setContentPage(request.getSession(false), "teacher/Rcproj_Out.jsp");
		return mapping.findForward("Main");
		
	}

		
	@Override
	protected Map<String, String> getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("Update", "Update");
		map.put("Delete", "Delete");		
		map.put("Back", "Back");
		map.put("PrintOut", "PrintOut");
		
		return map;
	}

}
