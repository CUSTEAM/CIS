package tw.edu.chit.struts.action.onlineService;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class WorkingManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		try{//這些資料不是我設計的，我只是照著用，這些不是我設計的，我才不會用這種方法設計資料
			request.setAttribute("SchoolAddress_Taipei", manager.ezGetString("SELECT Value FROM Parameter WHERE Name='SchoolAddress_Taipei' AND category='school_address'"));
		}catch(Exception e){
			request.setAttribute("SchoolAddress_Taipei", manager.ezGetString("台北市南港區研究院路三段245號"));
		}
		try{
			request.setAttribute("SchoolAddress_Hsinchu", manager.ezGetString("SELECT Value FROM Parameter WHERE Name='SchoolAddress_Hsinchu' AND category='school_address'"));
		}catch(Exception e){
			request.setAttribute("SchoolAddress_Hsinchu", manager.ezGetString("新竹縣橫山鄉中華街200號"));
		}
		
		try{//這些資料不是我設計的，我只是照著用，這些不是我設計的，我才不會用這種方法設計資料
			request.setAttribute("SchoolPost_Taipei", manager.ezGetString("SELECT Value FROM Parameter WHERE Name='SchoolPost_Taipei'"));
		}catch(Exception e){
			request.setAttribute("SchoolPost_Taipei", manager.ezGetString("11581"));
		}
		try{
			request.setAttribute("SchoolPost_Hsinchu", manager.ezGetString("SELECT Value FROM Parameter WHERE Name='SchoolPost_Hsinchu'"));
		}catch(Exception e){
			request.setAttribute("SchoolPost_Hsinchu", manager.ezGetString("31241"));
		}
		
		
		
		try{
			request.setAttribute("day_working", manager.ezGetString("SELECT name FROM code5 WHERE category='UintWorking' AND idno='B1'"));
		}catch(Exception e){
			request.setAttribute("day_working", "日間部註冊組 分機125、126, 傳真02-2782-7249(08:00~");
		}
		
		try{
			request.setAttribute("night_working", manager.ezGetString("SELECT name FROM code5 WHERE category='UintWorking' AND idno='B3'"));
		}catch(Exception e){
			request.setAttribute("night_working", "進修部教務組136、163(14:30~21:30)");
		}
		
		try{//新竹
			request.setAttribute("Hsinchu", manager.ezGetString("SELECT name FROM code5 WHERE category='UintWorking' AND idno='B4'"));
		}catch(Exception e){
			request.setAttribute("Hsinchu", "教務組225、231, 傳真02-2783-7172(08:20~21:30)");
		}
		
		try{
			request.setAttribute("holiday_working", manager.ezGetString("SELECT name FROM code5 WHERE category='UintWorking' AND idno='B5'"));
		}catch(Exception e){
			request.setAttribute("holiday_working", "進修學院/專校教務組225、231, 傳真02-2782-2872(08:0");
		}
		
		
		setContentPage(request.getSession(false), "onlineService/WorkingManager.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward ok(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;
		String SchoolAddress_Taipei=aForm.getString("SchoolAddress_Taipei");
		String SchoolAddress_Hsinchu=aForm.getString("SchoolAddress_Hsinchu");
		String day_working=aForm.getString("day_working");
		String night_working=aForm.getString("night_working");
		String holiday_working=aForm.getString("holiday_working");
		String SchoolPost_Taipei=aForm.getString("SchoolPost_Taipei");
		String SchoolPost_Hsinchu=aForm.getString("SchoolPost_Hsinchu");
		String Hsinchu=aForm.getString("Hsinchu");	
		
		manager.executeSql("UPDATE Parameter SET Value='"+SchoolPost_Taipei+"' WHERE Name='SchoolPost_Taipei'");
		manager.executeSql("UPDATE Parameter SET Value='"+SchoolPost_Hsinchu+"' WHERE Name='SchoolPost_Hsinchu'");
		
		manager.executeSql("UPDATE Parameter SET Value='"+SchoolAddress_Taipei+"' WHERE Name='SchoolAddress_Taipei' AND category='school_address'");
		manager.executeSql("UPDATE Parameter SET Value='"+SchoolAddress_Hsinchu+"' WHERE Name='SchoolAddress_Hsinchu' AND category='school_address'");
		
		manager.executeSql("UPDATE code5 SET name='"+day_working+"' WHERE category='UintWorking' AND idno='B1'");
		manager.executeSql("UPDATE code5 SET name='"+night_working+"' WHERE category='UintWorking' AND idno='B3'");
		manager.executeSql("UPDATE code5 SET name='"+holiday_working+"' WHERE category='UintWorking' AND idno='B5'");
		
		manager.executeSql("UPDATE code5 SET name='"+Hsinchu+"' WHERE category='UintWorking' AND idno='B4'");
		
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("OK", "ok");
		return map;
	}

}
