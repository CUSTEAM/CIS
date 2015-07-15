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

import tw.edu.chit.model.Nabbr;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class SetRoomAction extends BaseLookupDispatchAction{

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, 
		   HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		Toolket.resetCheckboxCookie(response, "roms");
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		session.setAttribute("dept", manager.ezGetBy("SELECT idno, name FROM code5 WHERE category='Dept' ORDER BY sequence")); //系所	
		session.setAttribute("build", manager.ezGetBy("SELECT idno, name FROM code5 WHERE category='building' ORDER BY idno")); //大樓
		session.setAttribute("unit", manager.ezGetBy("SELECT idno, name FROM code5 WHERE category='Unit' ORDER BY sequence")); //大樓
		
		setContentPage(request.getSession(false), "course/RooManager.jsp");
		return mapping.findForward("Main");
		
	}
	
	/**
	 * 查詢
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Toolket.resetCheckboxCookie(response, "roms");
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm roomForm = (DynaActionForm) form;
		
		
		String roomid=(String)roomForm.get("roomid");
		String name2=(String)roomForm.get("name2");
		String dept=(String)roomForm.get("dept");
		String building=(String)roomForm.get("building");
		String unit=(String)roomForm.get("unit");
		String floor=(String)roomForm.get("floor");
		String boro=(String)roomForm.get("boro");
		
		//StringBuilder sb=new StringBuilder();
		
		List list=manager.ezGetBy("SELECT n.*, c.name as buildName, c1.name as unitName, c2.name as deptName FROM (((Nabbr n LEFT OUTER JOIN code5 c ON " +
				"c.idno=n.building AND c.category='building')LEFT OUTER JOIN code5 c1 ON c1.category='Unit' AND c1.idno=n.unit)LEFT OUTER JOIN code5 c2 " +
				"ON c2.category='Dept' AND c2.idno=n.dept)WHERE n.boro LIKE '"+boro+"%' AND " +
				"(n.dept LIKE'"+dept+"%' OR dept IS NULL) AND (n.unit LIKE'"+unit+"%' OR n.unit IS NULL) AND (building LIKE'"+building+"%' OR building IS NULL) AND " +
				"(floor LIKE'"+floor+"%' OR floor IS NULL) AND (name2 LIKE '"+name2+"%' OR name2 IS NULL) AND (room_id LIKE '"+roomid+"%' OR room_id IS NULL)");		
		
		session.setAttribute("rooms", list);
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","查詢完成"));
		saveMessages(request, msg);
		return mapping.findForward("Main");
	}
	
	/**
	 * 細部設定
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward details(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		//CourseManager manager = (CourseManager) getBean("courseManager");
		//DynaActionForm roomForm = (DynaActionForm) form;
		
		List list=getDtimEditList(request);
		if(list.size()!=1){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "要修改哪一間?"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		session.setAttribute("aRoom", list.get(0));
		return mapping.findForward("Main");
	}
	
	/**
	 * 完成細部設定
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward ok(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm roomForm = (DynaActionForm) form;
		
		String Oid=(String)roomForm.get("aOid");
		String remark=(String)roomForm.get("remark");
		String seat=(String)roomForm.get("seat");
		//String roomid=(String)roomForm.get("roomid");
		String name2=(String)roomForm.get("name2");
		String dept=(String)roomForm.get("dept");
		String building=(String)roomForm.get("building");
		String unit=(String)roomForm.get("unit");
		String floor=(String)roomForm.get("floor");
		String boro=(String)roomForm.get("boro");
		
		Nabbr nabbr=(Nabbr)manager.hqlGetBy("FROM Nabbr WHERE oid="+Oid).get(0);		
		nabbr.setBuilding(building);
		nabbr.setDept(dept);
		nabbr.setFloor(floor);
		nabbr.setName2(name2);
		nabbr.setUnit(unit);		
		nabbr.setBoro(boro);
		nabbr.setRemark(remark);
		nabbr.setSeat(Short.parseShort(seat));
		
		manager.updateObject(nabbr);
		
		Toolket.resetCheckboxCookie(response, "roms");
		
		session.removeAttribute("rooms");
		session.removeAttribute("aRoom");
		return list(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("Query", "list");
		map.put("Create", "create");
		map.put("Modify", "modify");
		map.put("Delete", "delete");
		map.put("Details", "details");
		map.put("Clear", "clear");
		map.put("OK", "ok");
		return map;
	}
	
	public ActionForward clear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Toolket.resetCheckboxCookie(response, "roms");
		HttpSession session = request.getSession(false);
		DynaActionForm roomForm = (DynaActionForm) form;
		session.removeAttribute("rooms");
		session.removeAttribute("aRoom");
		
		roomForm.set("roomid", "");
		roomForm.set("name2", "");
		roomForm.set("dept", "");
		roomForm.set("building", "");
		roomForm.set("unit", "");
		roomForm.set("floor", "");
		
		
		return mapping.findForward("Main");
	}
		
		
	
	/**
	 * 刪除
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Toolket.resetCheckboxCookie(response, "roms");
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		//DynaActionForm setUpDtime = (DynaActionForm) form;
		
		
		
		List list=getDtimEditList(request);		
		if(list.size()!=1){
			//error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "要刪除哪一間?"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		for(int i=0; i<list.size(); i++){
			//TODO 其它會被教室影響的地方
			if(manager.ezGetInt("SELECT COUNT(*)FROM Dtime_class WHERE place='"+((Map)list.get(i)).get("room_id")+"'")<1){
				manager.executeSql("DELETE FROM Nabbr WHERE Oid='"+((Map)list.get(i)).get("Oid")+"'");
			}else{
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "有排課, 不能刪除!"));
				saveErrors(request, error);
				return mapping.findForward("Main");
			}			
		}
		
		
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","刪除完成"));
		saveMessages(request, msg);
		return list(mapping, form, request, response);
	}	
	
	/**
	 * 修改
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Toolket.resetCheckboxCookie(response, "roms");
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm roomForm = (DynaActionForm) form;
		
		String Oid[]=(String[])roomForm.get("Oid");
		//String roomids[]=(String[])roomForm.get("roomids");
		String name2s[]=(String[])roomForm.get("name2s");
		String depts[]=(String[])roomForm.get("depts");
		String buildings[]=(String[])roomForm.get("buildings");
		String units[]=(String[])roomForm.get("units");
		String floors[]=(String[])roomForm.get("floors");
		String boros[]=(String[])roomForm.get("boros");
		
		Nabbr nabbr;
		for(int i=0; i<Oid.length; i++){
			
			nabbr=(Nabbr)manager.hqlGetBy("FROM Nabbr WHERE Oid="+Oid[i]).get(0);
			nabbr.setBuilding(buildings[i]);
			nabbr.setDept(depts[i]);
			nabbr.setFloor(floors[i]);
			nabbr.setName2(name2s[i]);
			nabbr.setBoro(boros[i]);
			//nabbr.setRoomId(roomids[i]);
			nabbr.setUnit(units[i]);
			
			//if(manager.ezGetInt("SELECT COUNT(*)FROM Dtime_class WHERE place='"+))
			
			manager.updateObject(nabbr);
		}
		//msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","修改完成"));
		//saveMessages(request, msg);
		//return mapping.findForward("Main");
		return list(mapping, form, request, response);
	}
	
	/**
	 * 新增
	 */
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Toolket.resetCheckboxCookie(response, "roms");
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm roomForm = (DynaActionForm) form;
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		
		String roomid=(String)roomForm.get("roomid");
		String name2=(String)roomForm.get("name2");
		String dept=(String)roomForm.get("dept");
		String building=(String)roomForm.get("building");
		String unit=(String)roomForm.get("unit");
		String floor=(String)roomForm.get("floor");
		String boro=(String)roomForm.get("boro");
		
		
		if(!roomid.trim().equals("")&& !building.trim().equals("")&& !floor.trim().equals("")&& roomid.trim().length()>=5){
			Nabbr nabbr=new Nabbr();
			
			nabbr.setBuilding(building);
			nabbr.setDept(dept);
			nabbr.setFloor(floor);
			nabbr.setName2(name2);
			nabbr.setRoomId(roomid);
			nabbr.setUnit(unit);
			nabbr.setBoro(boro);
			nabbr.setSeat(Short.parseShort("60"));
			try{
				manager.updateObject(nabbr);
			}catch(Exception e){
				e.printStackTrace();
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "重複"));
				saveErrors(request, error);
				return mapping.findForward("Main");
			}
			
		}else{
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "教室編號、建築物、樓層不可以空白"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		return list(mapping, form, request, response);
	}
	
	/**
	 * 取得勾選欄位
	 * @param request
	 * @return
	 */
	private List getDtimEditList(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		//CourseManager manager = (CourseManager) getBean("courseManager");
		String Oids = Toolket.getSelectedIndexFromCookie(request, "roms");
		
		
		List rooms = (List) session.getAttribute("rooms");
		List checked=new ArrayList();
		Map map;
		for(int i=0; i<rooms.size(); i++){
			map = (Map)rooms.get(i);
			if (Toolket.isValueInCookie(map.get("Oid").toString(), Oids)) {

				checked.add(map);
			}
		}
		
		return checked;
	}
}