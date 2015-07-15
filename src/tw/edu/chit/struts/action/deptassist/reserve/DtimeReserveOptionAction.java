package tw.edu.chit.struts.action.deptassist.reserve;

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

import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.model.DtimeReserve;
import tw.edu.chit.model.Module;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;

/**
 * 時數管理
 * @author shawn
 *
 */
public class DtimeReserveOptionAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		
		setContentPage(request.getSession(false), "assistant/DtimereserveOption.jsp");		
		return mapping.findForward("Main");
	}	
	
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		DynaActionForm dForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String year=dForm.getString("year");
		//String dept=dForm.getString("classLess");
		String cidno=dForm.getString("campusInCharge2");
		String sidno=dForm.getString("schoolInCharge2");
		String didno=dForm.getString("deptInCharge2");
		//String depart_class=dForm.getString("classLess");
		String dept=cidno+sidno+didno;
		
		if(year.trim().equals("")||dept.length()!=4){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "必需指定入學年及科系"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		request.setAttribute("times", manager.ezGetBy("SELECT * FROM Dtime_reserve_option WHERE dept='"+dept+"' AND year='"+year+"' ORDER BY year"));		
		return mapping.findForward("Main");
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		//HttpSession session = request.getSession(false);
		DynaActionForm dForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String Oid[]=dForm.getStrings("Oid");
		String grades[]=dForm.getStrings("grades");
		String term[]=dForm.getStrings("term");
		String thour[]=dForm.getStrings("thour");
		String credit[]=dForm.getStrings("credit");		
		String open[]=dForm.getStrings("open");	
		
		for(int i=0; i<Oid.length; i++){
			//新增
			if(Oid[i].equals("")){
				if(!thour[i].trim().equals("")&&!credit[i].trim().equals("")){
					
					String year=dForm.getString("year");
					String cidno=dForm.getString("campusInCharge2");
					String sidno=dForm.getString("schoolInCharge2");
					String didno=dForm.getString("deptInCharge2");
					//String depart_class=dForm.getString("classLess");
					String dept=cidno+sidno+didno;
					try{
						manager.executeSql("INSERT INTO Dtime_reserve_option(year, dept, grade, term, thour, credit)" +
								"VALUES('"+year+"', '"+dept+"', '"+grades[i]+"', '"+term[i]+"', '"+thour[i]+"', '"+credit[i]+"')");
					}catch(Exception e){
						e.printStackTrace();
						ActionMessages error = new ActionMessages();
						error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "新增規劃時發現錯誤"));
						saveErrors(request, error);
					}
				}
			}else{				
				if(grades[i].trim().equals("")||term[i].trim().equals("")||thour[i].trim().equals("")||credit[i].trim().equals("")){
					//刪除
					manager.executeSql("DELETE FROM Dtime_reserve_option WHERE Oid="+Oid[i]);
				}
				try{
					//修改
					if(!thour[i].trim().equals("")&&!credit[i].trim().equals("")){
						manager.executeSql("UPDATE Dtime_reserve_option SET grade='"+grades[i]+"', " +
								"term='"+term[i]+"', thour='"+thour[i]+"', credit='"+credit[i]+"', open='"+open[i]+"' WHERE Oid="+Oid[i]);
					}
				}catch(Exception e){
					e.printStackTrace();
					ActionMessages error = new ActionMessages();
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "修改規劃時發現錯誤"));
					saveErrors(request, error);
				}
			}
		}		
		return query(mapping, form, request, response);
	}

	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");		
		map.put("Save", "save");		
		return map;
	}
}
