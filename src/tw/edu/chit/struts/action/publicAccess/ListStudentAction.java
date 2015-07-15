package tw.edu.chit.struts.action.publicAccess;

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

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class ListStudentAction extends BaseLookupDispatchAction{

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		request.setAttribute("AllCampus", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Campus'"));
		request.setAttribute("AllSchool", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='School'"));
		request.setAttribute("AllDept", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Dept'"));
		request.setAttribute("AllSchoolType", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Stype'"));
		setContentPage(request.getSession(false), "publicAccess/ListStudents.jsp");
		
		return mapping.findForward("Main");
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm cForm = (DynaActionForm) form;
		
		
		/*List list=manager.ezGetBy("SELECT cl.*, c5.name as CampusName, c51.name as DeptName, c52.name as SchoolName, c53.name as SchoolTypeName, " +
				"c54.name as ClassTypeName FROM ((((Class cl LEFT OUTER JOIN code5 c5 ON c5.idno=cl.CampusNo AND c5.category='Campus')" +
				"LEFT OUTER JOIN code5 c51 ON cl.DeptNo=c51.idno AND c51.category='Dept')LEFT OUTER JOIN code5 c52 ON cl.SchoolNo=c52.idno AND " +
				"c52.category='School')LEFT OUTER JOIN code5 c53 ON cl.SchoolType=c53.idno AND c53.category='Stype')LEFT OUTER JOIN code5 c54 ON " +
				"cl.Type=c54.idno AND c54.category='ClassType' WHERE " +
				"cl.ClassNo LIKE'"+cForm.getMap().get("ClassNo")+"%' AND cl.ClassName LIKE'"+cForm.getMap().get("ClassName")+"%' AND "+
				"cl.CampusNo LIKE'"+cForm.getMap().get("CampusNo")+"%' AND cl.SchoolNo LIKE'"+cForm.getMap().get("SchoolNo")+"%' AND "+
				"cl.DeptNo LIKE'"+cForm.getMap().get("DeptNo")+"%' AND cl.Grade LIKE'"+cForm.getMap().get("Grade")+"%' AND "+
				"cl.Type LIKE'"+cForm.getMap().get("Type")+"%' AND cl.SchoolType LIKE'"+cForm.getMap().get("SchoolType")+"%' " +
				"ORDER BY cl.SchoolType, cl.ClassNo");*/
		/*
		List list=manager.ezGetBy("SELECT cl.*, c5.name as CampusName, c51.name as DeptName, c52.name as SchoolName, c53.name as SchoolTypeName, " +
				"c54.name as ClassTypeName FROM ((((Class cl LEFT OUTER JOIN code5 c5 ON c5.idno=cl.CampusNo AND c5.category='Campus')" +
				"LEFT OUTER JOIN code5 c51 ON cl.DeptNo=c51.idno AND c51.category='Dept')LEFT OUTER JOIN code5 c52 ON cl.SchoolNo=c52.idno AND " +
				"c52.category='School')LEFT OUTER JOIN code5 c53 ON cl.SchoolType=c53.idno AND c53.category='Stype')LEFT OUTER JOIN code5 c54 ON " +
				"cl.Type=c54.idno AND c54.category='ClassType' WHERE " +
				"cl.ClassNo LIKE'"+cForm.getMap().get("ClassNo")+"%' AND cl.ClassName LIKE'"+cForm.getMap().get("ClassName")+"%' AND "+
				"cl.CampusNo LIKE'"+cForm.getMap().get("CampusNo")+"%' AND cl.SchoolNo LIKE'"+cForm.getMap().get("SchoolNo")+"%' AND "+
				"cl.DeptNo LIKE'"+cForm.getMap().get("DeptNo")+"%' AND cl.Grade LIKE'"+cForm.getMap().get("Grade")+"%' AND "+
				"cl.ClassName NOT LIKE'%延修%' AND cl.ClassName NOT LIKE'%短期%' AND cl.ClassName NOT LIKE'%選讀%' AND cl.ClassName NOT LIKE'%跨校%' AND cl.ClassName NOT LIKE'%學分%' AND "
				+ "cl.SchoolType LIKE'"+cForm.getMap().get("SchoolType")+"%' " +
				"ORDER BY cl.SchoolType, cl.ClassNo");
		*/
		
		
		List<Map>classes=manager.ezGetBy("SELECT c.`Type` as ClassTypeName, st.depart_class, c.ClassName, (select cs.name from CODE_SCHOOL cs where cs.id=c.SchoolNo)as SchoolName, cd.name as DeptName, cst.name as SchoolTypeName, cc.name as "
				+ "CampusName, st.depart_class, c.ClassName, c.Grade, c.ShortName, COUNT(*)as cnt, (SELECT COUNT(*)FROM stmd WHERE "
				+ "depart_class=st.depart_class AND sex='1')as m FROM (((stmd st LEFT OUTER JOIN Class c ON "
				+ "c.CampusNo LIKE'"+cForm.getMap().get("CampusNo")+"%' AND c.ClassNo=st.depart_class AND c.SchoolNo LIKE'"+cForm.getMap().get("SchoolNo")+"%' AND c.DeptNo LIKE'"+cForm.getMap().get("DeptNo")+"%'AND c.Grade LIKE'"+cForm.getMap().get("Grade")+"%')LEFT OUTER JOIN CODE_CAMPUS cc ON cc.id=c.CampusNo)"
				+ "LEFT OUTER JOIN CODE_SCHOOL_TYPE cst ON c.SchoolType=cst.type)LEFT OUTER JOIN "
				+ "CODE_DEPT cd ON c.DeptNo=cd.id WHERE (c.ClassName NOT LIKE'%延修%' AND c.ClassName NOT LIKE'%短期%' AND c.ClassName NOT LIKE'%選讀%' AND c.ClassName NOT LIKE'%跨校%' AND c.ClassName NOT LIKE'%學分%')OR c.ClassNo IS NULL "
				+ "GROUP BY st.depart_class ORDER BY c.SchoolType, c.DeptNo, c.ClassNo");
		
		int countClass=0;
		int countStudent=0;
		for(int i=0; i<classes.size(); i++){
			countClass++;
			countStudent+=Integer.parseInt(classes.get(i).get("cnt").toString());
		}
		/*List classes=new ArrayList();
		Map map;
		
		
		for(int i=0; i<list.size(); i++){
			int tmp=manager.ezGetInt("SELECT COUNT(*)FROM stmd WHERE depart_class='"+((Map)list.get(i)).get("ClassNo")+"'");		
			if(tmp>0){
				
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
				
				map.put("Type", ((Map)list.get(i)).get("Type"));
				map.put("count", tmp);			
				int gst=manager.ezGetInt("SELECT COUNT(*)FROM stmd WHERE depart_class='"+((Map)list.get(i)).get("ClassNo")+"' AND sex='2'");			
				map.put("girl", gst);
				
				countStudent=countStudent+tmp;
							
				if(cForm.get("Type").equals("P")){
					if(tmp>0){
						countClass=countClass+1;
						classes.add(map);	
					}
				}else{
					countClass=countClass+1;
					classes.add(map);	
				}
			}
		}*/
		session.setAttribute("classes", classes);
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", countClass+"班 共 "+countStudent+"位學生"));
		saveMessages(request, msg);		
		
		return unspecified(mapping, form, request, response);
	}
	
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
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
	
	public ActionForward print(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		DynaActionForm cForm = (DynaActionForm) form;
		//String ClassNo=cForm.getString("ClassNo");
		//String ClassName=cForm.getString("ClassName");
		String CampusNo=cForm.getString("CampusNo");
		String SchoolNo=cForm.getString("SchoolNo");
		String SchoolType=cForm.getString("SchoolType");
		String DeptNo=cForm.getString("DeptNo");
		String Grade=cForm.getString("Grade");
		String week=cForm.getString("week");
		//String ShortName=cForm.getString("ShortName");
		
		
		
		
		//HttpSession session = request.getSession(false);
		
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=List4Student.xls");
		CourseManager manager = (CourseManager) getBean("courseManager");
		String sterm=manager.getSchoolTerm().toString();
		PrintWriter out=response.getWriter();
		
		List list=manager.ezGetBy("SELECT cl.ClassNo, cl.ClassName FROM Class cl WHERE " +
				"cl.ClassNo LIKE'"+cForm.getMap().get("ClassNo")+"%' AND cl.ClassName LIKE'"+cForm.getMap().get("ClassName")+"%' AND "+
				"cl.CampusNo LIKE'"+cForm.getMap().get("CampusNo")+"%' AND cl.SchoolNo LIKE'"+cForm.getMap().get("SchoolNo")+"%' AND "+
				"cl.DeptNo LIKE'"+cForm.getMap().get("DeptNo")+"%' AND cl.Grade LIKE'"+cForm.getMap().get("Grade")+"%' AND "+
				"cl.Type LIKE'"+cForm.getMap().get("Type")+"%' AND cl.SchoolType LIKE'"+cForm.getMap().get("SchoolType")+"%' " +
				"ORDER BY cl.SchoolType, cl.ClassNo");
		
		
		
		out.println("<style>td{font-size:18px;}</style>");
		List students;
		List selds;
		String str[];
		int x;
		int y;
		for(int i=0; i<list.size(); i++){
			students=manager.ezGetBy("SELECT s.student_no, s.student_name FROM " +
					"stmd s WHERE s.depart_class='"+((Map)list.get(i)).get("ClassNo")+"'");
			
			if(students.size()<1){
				continue;
			}
			out.println("<table border='1'>");
			out.println("  <tr>");
			out.println("    <td colspan='16'>"+((Map)list.get(i)).get("ClassName")+"</td>");			
			out.println("  </tr>");
			out.println("  <tr>");			
			out.println("    <td>學號</td>");
			out.println("    <td>姓名</td>");
			out.println("    <td>1</td>");
			out.println("    <td>2</td>");
			out.println("    <td>3</td>");
			out.println("    <td>4</td>");
			out.println("    <td>5</td>");
			out.println("    <td>6</td>");
			out.println("    <td>7</td>");
			out.println("    <td>8</td>");
			out.println("    <td>9</td>");
			out.println("    <td>10</td>");
			out.println("    <td>1</td>");
			out.println("    <td>2</td>");
			out.println("    <td>3</td>");
			out.println("    <td>4</td>");
			out.println("  </tr>");
			
			for(int j=0; j<students.size(); j++){
				str=new String[]{"","","","","","","","","","","","","","",};
				selds=manager.ezGetBy("SELECT dc.* FROM Seld s, Dtime d, Dtime_class dc WHERE " +
				"dc.Dtime_oid=d.Oid AND dc.week='"+week+"' AND " +
				"d.Oid=s.Dtime_oid AND d.Sterm='"+sterm+"' AND " +
				"s.student_no='"+((Map)students.get(j)).get("student_no")+"'");
				
				for(int k=0; k<selds.size(); k++){
					x=Integer.parseInt(((Map)selds.get(k)).get("begin").toString())-1;
					y=Integer.parseInt(((Map)selds.get(k)).get("end").toString())-1;
					str[x]="*";
					str[y]="*";
					if(y-x>=2){
						for(int k1=0; k1<(y-x); k1++){
							str[x+k1]="*";
						}
					}
				}
				
				out.println("  <tr>");
				out.println("    <td>"+((Map)students.get(j)).get("student_no")+"&nbsp</td>");
				out.println("    <td>"+((Map)students.get(j)).get("student_name")+"</td>");
				
				for(int k=0; k<str.length; k++){
					out.println("    <td>"+str[k]+"</td>");
					
				}
				out.println("  </tr>");				
			}
			out.println("  </table>");
			out.println("  <p style='page-break-before:always;'></p>");
		}
		
		
		
		//<p style="page-break-before:always;"></p>
		return null;
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Create", "create");
		map.put("Save", "save");
		map.put("Print", "print");
		map.put("Clear", "clear");
		return map;
	}

}
