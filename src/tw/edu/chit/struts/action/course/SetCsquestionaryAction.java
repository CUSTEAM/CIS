package tw.edu.chit.struts.action.course;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.CoQuestion;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class SetCsquestionaryAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		Toolket.resetCheckboxCookie(response, "stus");
		DynaActionForm setForm = (DynaActionForm) form;
		HttpSession session = request.getSession(false);		
		
		String CoanswStart=manager.convertDate(manager.ezGetString("SELECT  Value FROM Parameter WHERE Name='CoanswStart'"));
		setForm.set("CoanswStart", CoanswStart); // 開始日期
		setForm.set("CoanswEnd", manager.convertDate(manager.ezGetString("SELECT  Value FROM Parameter WHERE Name='CoanswEnd'"))); //結束日期
		setForm.set("CoanswTeacherEnd", manager.convertDate(manager.ezGetString("SELECT  Value FROM Parameter WHERE Name='CoanswTeacherEnd'"))); //結束日期
		setForm.set("CoanswLeaderEnd", manager.convertDate(manager.ezGetString("SELECT  Value FROM Parameter WHERE Name='CoanswLeaderEnd'"))); //結束日期		
		
		List list=manager.ezGetBy("SELECT * FROM CoQuestion WHERE ParentOid='0' AND textValue='0' ORDER BY sequence");
		List nQuest=new ArrayList();
		Map map;
		
		for(int i=0; i<list.size(); i++){
			map=new HashMap();
			map.put("Oid", ((Map)list.get(i)).get("Oid"));
			map.put("options", ((Map)list.get(i)).get("options"));
			map.put("parentOid", ((Map)list.get(i)).get("parentOid"));
			map.put("textValue", ((Map)list.get(i)).get("textValue"));
			map.put("sequence", ((Map)list.get(i)).get("sequence"));
			map.put("value", ((Map)list.get(i)).get("value"));
			
			map.put("subOptions", manager.ezGetBy("SELECT * FROM CoQuestion WHERE ParentOid='"+((Map)list.get(i)).get("Oid")+"'"));
			nQuest.add(map);
		}
		
		List elist=manager.ezGetBy("SELECT * FROM CoQuestion WHERE ParentOid='0' AND textValue='1' ORDER BY sequence");
		List eQuest=new ArrayList();
		Map emap;
		
		for(int i=0; i<elist.size(); i++){
			emap=new HashMap();
			emap.put("Oid", ((Map)elist.get(i)).get("Oid"));
			emap.put("options", ((Map)elist.get(i)).get("options"));
			emap.put("parentOid", ((Map)elist.get(i)).get("parentOid"));
			emap.put("textValue", ((Map)elist.get(i)).get("textValue"));
			emap.put("sequence", ((Map)elist.get(i)).get("sequence"));
			emap.put("value", ((Map)elist.get(i)).get("value"));
			
			emap.put("subOptions", manager.ezGetBy("SELECT * FROM CoQuestion WHERE ParentOid='"+((Map)elist.get(i)).get("Oid")+"'"));
			eQuest.add(emap);
		}
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		Date stuStar=sf.parse(manager.convertDate(CoanswStart));
		//開始評量後不得修改
		if(stuStar.getTime()>new Date().getTime()){
			session.setAttribute("editCoQ", true);
		}else{
			session.setAttribute("editCoQ", false);
		}
		session.setAttribute("eQuest", eQuest);
		session.setAttribute("nQuest", nQuest);
		session.setAttribute("countCoans", manager.ezGetBy("SELECT student_no FROM Coansw GROUP BY student_no").size());
		session.setAttribute("countCoans1", manager.ezGetInt("SELECT COUNT(*) FROM Coansw"));
		setContentPage(request.getSession(false), "course/SetCsQuestionary.jsp");
		
		session.setAttribute("allSchoolType", manager.getAllSchoolType());
		return mapping.findForward("Main");
	}
	
	/**
	 * 設定日期
	 * 資料表 Parameter 必須有 "CoanswStart" & "CoanswEnd"
	 */
	public ActionForward setQuestDate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		System.out.println("hello");
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm setForm = (DynaActionForm) form;
		String CoanswStart=(String)setForm.get("CoanswStart");
		String CoanswEnd=(String)setForm.get("CoanswEnd");
		String editDate=(String)setForm.get("editDate");
		
		String CoanswTeacherEnd=(String)setForm.get("CoanswTeacherEnd");
		String CoanswLeaderEnd=(String)setForm.get("CoanswLeaderEnd");
		
		//if(editDate.equals("1")){
			manager.executeSql("UPDATE Parameter SET Value='"+manager.convertDate(CoanswStart)+"' WHERE Name='CoanswStart'");
			manager.executeSql("UPDATE Parameter SET Value='"+manager.convertDate(CoanswEnd)+"' WHERE Name='CoanswEnd'");
			manager.executeSql("UPDATE Parameter SET Value='"+manager.convertDate(CoanswTeacherEnd)+"' WHERE Name='CoanswTeacherEnd'");
			manager.executeSql("UPDATE Parameter SET Value='"+manager.convertDate(CoanswLeaderEnd)+"' WHERE Name='CoanswLeaderEnd'");
		//}
		
		
		return unspecified(mapping, form, request, response);
	}	
	
	/**
	 * 設定題目
	 */
	public ActionForward setQuest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm setForm = (DynaActionForm) form;
		
		String Oid[]=(String[])setForm.get("Oid");
		String checkDel[]=(String[])setForm.get("checkDel");
		String sequence[]=(String[])setForm.get("sequence");
		String value[]=(String[])setForm.get("value");
		String options[]=(String[])setForm.get("options");
		//修改所有題目
		for(int i=0; i<Oid.length; i++){
			if(checkDel[i].trim().equals("1")){				
				manager.executeSql("DELETE FROM CoQuestion WHERE Oid='"+Oid[i]+"'");
				manager.executeSql("DELETE FROM CoQuestion WHERE parentOid='"+Oid[i]+"'");
			}else{
				manager.executeSql("UPDATE CoQuestion SET sequence='"+sequence[i]+"', value='"+value[i]+"', options='"+options[i]+"' WHERE Oid="+Oid[i]);
			}
		}
		
		//新增一般題目及選項
		String anSequence[]=(String[])setForm.get("anSequence");
		String anValue[]=(String[])setForm.get("anValue");
		String anOptions[]=(String[])setForm.get("anOptions");
		String anTextValue[]=(String[])setForm.get("anTextValue");
		String anType[]=(String[])setForm.get("anType");
		
		CoQuestion coq = new CoQuestion();
		for(int i=0; i<anType.length; i++){
			if(!anSequence[i].trim().equals("")&& !anOptions[i].trim().equals("")&& !anValue[i].trim().equals("")){
				if(anType[i].equals("M")){
					coq = new CoQuestion();
					coq.setOptions(anOptions[i]);
					coq.setParentOid(0);
					coq.setSequence(Integer.parseInt(anSequence[i]));
					coq.setTextValue(anTextValue[i]);
					coq.setType(anType[i]);
					coq.setValue(anValue[i]);
					manager.updateObject(coq);
					//System.out.println(coq.getOid());
				}else{
					manager.executeSql("INSERT INTO CoQuestion(type, options, parentOid, textValue, sequence, value)VALUES(" +
							"'"+anType[i]+"', '"+anOptions[i]+"', '"+coq.getOid()+"', '"+anTextValue[i]+"', '"+anSequence[i]+"', '"+anValue[i]+"')");
				}
			}
		}

		//新增遠距題目及選項
		String aeSequence[]=(String[])setForm.get("aeSequence");
		String aeValue[]=(String[])setForm.get("aeValue");
		String aeOptions[]=(String[])setForm.get("aeOptions");
		String aeTextValue[]=(String[])setForm.get("aeTextValue");
		String aeType[]=(String[])setForm.get("aeType");
		
		coq = new CoQuestion();
		for(int i=0; i<aeOptions.length; i++){	
			
			if(!aeSequence[i].trim().equals("")&& !aeOptions[i].trim().equals("")&& !aeValue[i].trim().equals("")){
				if(aeType[i].equals("M")){
					coq = new CoQuestion();
					coq.setOptions(aeOptions[i]);
					coq.setParentOid(0);
					coq.setSequence(Integer.parseInt(aeSequence[i]));
					coq.setTextValue(aeTextValue[i]);
					coq.setType(aeType[i]);
					coq.setValue(aeValue[i]);
					manager.updateObject(coq);					
				}else{
					manager.executeSql("INSERT INTO CoQuestion(type, options, parentOid, textValue, sequence, value)VALUES(" +
							"'"+aeType[i]+"', '"+aeOptions[i]+"', '"+coq.getOid()+"', '"+aeTextValue[i]+"', '"+aeSequence[i]+"', '"+aeValue[i]+"')");
				}
			}
		}
		//coq=null; //放掉	
		
		//新增選項
		String aSequence[]=(String[])setForm.get("aSequence");
		String aValue[]=(String[])setForm.get("aValue");
		String aOptions[]=(String[])setForm.get("aOptions");
		String aParentOid[]=(String[])setForm.get("aParentOid");
		String aTextValue[]=(String[])setForm.get("aTextValue");
		String aType[]=(String[])setForm.get("aType");
		
		if(aOptions!=null)
		for(int i=0; i<aOptions.length; i++){
			if(!aSequence[i].trim().equals("")&& !aOptions[i].trim().equals("")&& !aValue[i].trim().equals("")){
				coq=new CoQuestion();
				coq.setOptions(aOptions[i]);
				coq.setParentOid(Integer.parseInt(aParentOid[i]));
				coq.setSequence(Integer.parseInt(aSequence[i]));
				coq.setTextValue(aTextValue[i]);
				coq.setType(aType[i]);
				coq.setValue(aValue[i]);
				manager.updateObject(coq);
			}
		}		
		
		setForm.set("aOptions", null);
		setForm.reset(mapping, request);
		return unspecified(mapping, form, request, response);
	}
	
	/**
	 * 列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm setForm = (DynaActionForm) form;		
		String classNo=(String)setForm.get("classNo");
		String techid=(String)setForm.get("techid");
		String schoolType=(String)setForm.get("schoolType");		
		String school_term=setForm.getString("school_term");
		
		if(schoolType.equals("")){
			session.setAttribute("allCoansw", manager.getCoansw4Empl(techid, classNo, school_term));
		}else{
			session.setAttribute("allCoansw", manager.getCoansw4Empl(manager.getDepartClassByType(schoolType), school_term));
		}		
		session.setAttribute("schoolYear", manager.getSchoolYear());
		session.setAttribute("schoolTerm", school_term);		
		return mapping.findForward("Main");
	}

	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("SetQuestDate", "setQuestDate");
		map.put("SetQuest", "setQuest");
		map.put("Query", "query");
		return map;
	}

}
