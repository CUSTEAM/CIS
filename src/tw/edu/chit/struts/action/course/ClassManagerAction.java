package tw.edu.chit.struts.action.course;

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

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class ClassManagerAction extends BaseLookupDispatchAction{

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		request.setAttribute("AllCampus", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Campus'"));
		request.setAttribute("AllSchool", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='School'"));
		request.setAttribute("AllDept", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Dept'"));
		request.setAttribute("AllSchoolType", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Stype'"));
		setContentPage(request.getSession(false), "course/ClassManager.jsp");
		
		
		return mapping.findForward("Main");
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm cForm = (DynaActionForm) form;
		
		
		List list=manager.ezGetBy("SELECT cl.*, c5.name as CampusName, c51.name as DeptName, c52.name as SchoolName, c53.name as SchoolTypeName, " +
				"c54.name as ClassTypeName FROM ((((Class cl LEFT OUTER JOIN code5 c5 ON c5.idno=cl.CampusNo AND c5.category='Campus')" +
				"LEFT OUTER JOIN code5 c51 ON cl.DeptNo=c51.idno AND c51.category='Dept')LEFT OUTER JOIN code5 c52 ON cl.SchoolNo=c52.idno AND " +
				"c52.category='School')LEFT OUTER JOIN code5 c53 ON cl.SchoolType=c53.idno AND c53.category='Stype')LEFT OUTER JOIN code5 c54 ON " +
				"cl.Type=c54.idno AND c54.category='ClassType' WHERE " +
				"cl.ClassNo LIKE'"+cForm.getMap().get("ClassNo")+"%' AND cl.ClassName LIKE'"+cForm.getMap().get("ClassName")+"%' AND "+
				"cl.CampusNo LIKE'"+cForm.getMap().get("CampusNo")+"%' AND cl.SchoolNo LIKE'"+cForm.getMap().get("SchoolNo")+"%' AND "+
				"cl.DeptNo LIKE'"+cForm.getMap().get("DeptNo")+"%' AND cl.Grade LIKE'"+cForm.getMap().get("Grade")+"%' AND "+
				"cl.Type LIKE'"+cForm.getMap().get("Type")+"%' AND cl.SchoolType LIKE'"+cForm.getMap().get("SchoolType")+"%' " +
				"ORDER BY cl.SchoolType, cl.ClassNo");		
		
		/*
		List list=manager.ezGetBy("SELECT cl.* FROM Class cl WHERE " +
				"cl.ClassNo LIKE'"+cForm.getMap().get("ClassNo")+"%' AND cl.ClassName LIKE'"+cForm.getMap().get("ClassName")+"%' AND "+
				"cl.CampusNo LIKE'"+cForm.getMap().get("CampusNo")+"%' AND cl.SchoolNo LIKE'"+cForm.getMap().get("SchoolNo")+"%' AND "+
				"cl.DeptNo LIKE'"+cForm.getMap().get("DeptNo")+"%' AND cl.Grade LIKE'"+cForm.getMap().get("Grade")+"%' AND "+
				"cl.Type LIKE'"+cForm.getMap().get("Type")+"%' AND cl.SchoolType LIKE'"+cForm.getMap().get("SchoolType")+"%' " +
				"ORDER BY cl.SchoolType, cl.ClassNo");
		*/
		List classes=new ArrayList();
		Map map;
		int countClass=0;
		int countStudent=0;
		//SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd hh:mm");
		for(int i=0; i<list.size(); i++){
			int tmp=manager.ezGetInt("SELECT COUNT(*)FROM stmd WHERE depart_class='"+((Map)list.get(i)).get("ClassNo")+"'");
			map=new HashMap();
			map.put("ClassNo", ((Map)list.get(i)).get("ClassNo"));
			map.put("ClassName", ((Map)list.get(i)).get("ClassName"));
			map.put("Oid", ((Map)list.get(i)).get("Oid"));
			map.put("SchoolTypeName", ((Map)list.get(i)).get("SchoolTypeName"));
			map.put("CampusName", ((Map)list.get(i)).get("CampusName"));
			map.put("SchoolName", ((Map)list.get(i)).get("SchoolName"));
			map.put("DeptName", ((Map)list.get(i)).get("DeptName"));
			map.put("Grade", ((Map)list.get(i)).get("Grade"));
			map.put("ClassTypeName", ((Map)list.get(i)).get("ClassTypeName"));
			map.put("ShortName", ((Map)list.get(i)).get("ShortName"));
			map.put("Dept", ((Map)list.get(i)).get("Dept"));
			map.put("editime", ((Map)list.get(i)).get("editime"));
			map.put("editor", manager.ezGetString("SELECT cname FROM empl WHERE idno='"+((Map)list.get(i)).get("editor")+"'"));
			//if(map.get("editor")==null){
				//map.put("editor", manager.ezGetString("SELECT cname FROM dempl WHERE idno='"+((Map)list.get(i)).get("editor")+"'"));
			//}
			map.put("Type", ((Map)list.get(i)).get("Type"));
			map.put("count", tmp);
			countStudent=countStudent+tmp;
			countClass=countClass+1;
			classes.add(map);			
		}
		session.setAttribute("classes", classes);
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", countClass+"班 共 "+countStudent+"位學生"));
		saveMessages(request, msg);		
		
		return unspecified(mapping, form, request, response);
	}
	
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		DynaActionForm cForm = (DynaActionForm) form;
		String ClassNo=cForm.getString("ClassNo");
		String ClassName=cForm.getString("ClassName");
		String CampusNo=cForm.getString("CampusNo");
		String SchoolNo=cForm.getString("SchoolNo");
		String SchoolType=cForm.getString("SchoolType");
		String DeptNo=cForm.getString("DeptNo");
		String Grade=cForm.getString("Grade");
		String Type=cForm.getString("Type");
		String ShortName=cForm.getString("ShortName");		
		
		if(ClassNo.trim().equals("")|| ClassName.trim().equals("")|| ShortName.trim().equals("")){
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "班級代碼、班級名稱、簡稱，不可為空"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		UserCredential c = (UserCredential) session.getAttribute("Credential");		
		//manager.executeSql
		try{
			manager.executeSql("INSERT INTO Class(" +
					"ClassNo, " +
					"ClassName, " +
					"CampusNo, " +
					"SchoolNo, " +
					"SchoolType, " +
					"DeptNo, " +
					"Grade, " +
					"Type, " +
					"ShortName, " +
					"Dept, " +
					"editor)VALUES('"+
					ClassNo+	"', '" +
					ClassName+	"', '"+
					CampusNo+	"', '"+
					SchoolNo+	"', '"+
					SchoolType+	"', '"+
					DeptNo+		"', '"+
					Grade+		"', '"+
					Type+		"', '"+
					ShortName+	"', '"+
					ClassNo.substring(0, 3)+"', '"+
					c.getMember().getIdno()+"');");
			ActionMessages msg = new ActionMessages();		//建立共用訊息
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "建立完成"));
			saveMessages(request, msg);
		}catch(Exception e){
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "建立失敗"));
			saveErrors(request, error);
		}		
		
		return query(mapping, form, request, response);
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm cForm = (DynaActionForm) form;
		
		String Oid[]=cForm.getStrings("Oid");
		String checked[]=cForm.getStrings("checked");
		String Types[]=cForm.getStrings("Types");
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		for(int i=0; i<Oid.length; i++){
			if(!checked[i].equals(""))
			manager.executeSql("UPDATE Class SET Type='"+Types[i]+"', editor='"+c.getMember().getIdno()+"' WHERE Oid='"+Oid[i]+"'");			
		}
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "修改完成"));
		saveMessages(request, msg);
		return query(mapping, form, request, response);
	}		
	
	public ActionForward clear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		DynaActionForm cForm = (DynaActionForm) form;
		cForm.set("ClassNo", "");
		cForm.set("ClassName", "");
		cForm.set("CampusNo", "");
		cForm.set("SchoolNo", "");
		cForm.set("DeptNo", "");
		cForm.set("Grade", "");
		cForm.set("Type", "");
		cForm.set("ShortName", "");
		cForm.set("SchoolType", "");		
		
		session.removeAttribute("classes");
		
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Create", "create");
		map.put("Save", "save");
		map.put("Clear", "clear");
		return map;
	}

}
