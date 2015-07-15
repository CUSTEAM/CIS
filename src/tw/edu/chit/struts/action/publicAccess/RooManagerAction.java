package tw.edu.chit.struts.action.publicAccess;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.NabbrBoro;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 教室借用/管理
 * @author JOHN
 *
 */
public class RooManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		Member me = getUserCredential(request.getSession(false)).getMember();//取得登入資訊
		
		String myDept=manager.ezGetString("SELECT ce.idno2 FROM empl e, CodeEmpl ce WHERE e.unit=ce.idno2 AND e.idno='"+me.getIdno()+"'");
		if(myDept.equals("")){
			myDept="pif";
		}
		String myUnit=manager.ezGetString("SELECT ub.UnitNo FROM empl e, UnitBelong ub WHERE e.Oid=ub.EmpOid AND idno='"+me.getIdno()+"'LIMIT 1");
		
		
		
		if(myUnit.equals("")){
			myDept="pif";
		}
		
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd");		
		//session.setAttribute("boro_user", me.getIdno());
		session.setAttribute("boro_unit_name", me.getUnit2());
		session.setAttribute("boro_username", me.getName());
		
		//若是職員
		if(me.getPriority().equals("A")){
			//Empl empl=(Empl)manager.hqlGetBy("FROM Empl WHERE idno='"+me.getIdno()+"'").get(0);
			//session.setAttribute("boro_unit", empl.getUnit());
			session.setAttribute("boro_unit_name", me.getUnit2());
		}
		
		//全校借用清單
		List list=manager.ezGetBy("SELECT nb.Oid, nb.title, nb.checkIn, nb.boro_date, nb.begin, nb.end, nb.heads, " +
				"c.name as buildName, n.room_id, c1.name as unitName FROM NabbrBoro nb, empl e, Nabbr n, code5 c, CodeEmpl c1 WHERE " +
				"c1.category='Unit' AND c1.idno=e.unit ANd c.category='building' AND c.idno=n.building AND n.room_id=nb.place AND " +
				//"e.idno=nb.boro_user AND nb.boro_date >= '"+sf.format(new Date())+"'");
				"e.idno=nb.boro_user");
		Map map;
		List allBoro=new ArrayList();
		for(int i=0; i<list.size(); i++){
			map=new HashMap();
			map.put("title", ((Map)list.get(i)).get("title"));
			
			if(((Map)list.get(i)).get("checkIn")==null){
				map.put("checkIn", "審核中");
			}else{
				map.put("checkIn", checkIn(((Map)list.get(i)).get("checkIn").toString()));
			}
			map.put("boro_date", ((Map)list.get(i)).get("boro_date"));
			map.put("begin", startEnd(((Map)list.get(i)).get("begin").toString()));
			map.put("end", startEnd(((Map)list.get(i)).get("end").toString()));
			
			
			try{
				if(((Map)list.get(i)).get("checkIn").equals("Y")){
					map.put("doc", "<a href='/CIS/Print/publicAccess/NabbrBoroDoc.do?Oid="+((Map)list.get(i)).get("Oid")+"''><img src='images/ico_file_word.png' border=0></a>");
				}else{
					map.put("doc", "");
				}
			}catch(Exception e){
				map.put("doc", "");
			}
			
			
			
			
			map.put("room_id", ((Map)list.get(i)).get("buildName")+""+((Map)list.get(i)).get("room_id"));
			map.put("unitName", ((Map)list.get(i)).get("unitName"));
			map.put("heads", ((Map)list.get(i)).get("heads"));
			
			allBoro.add(map);
		}
		session.setAttribute("allBoro", allBoro);
		
		//我的借用清單
		list=manager.ezGetBy("SELECT nb.Oid, nb.title, nb.checkIn, nb.boro_date, nb.begin, nb.end, nb.heads, " +
				"c.name as buildName, n.room_id, n.name2, c1.name as unitName FROM NabbrBoro nb, empl e, Nabbr n, code5 c, CodeEmpl c1 WHERE " +
				"c1.category='Unit' AND c1.idno=e.unit ANd c.category='building' AND c.idno=n.building AND " +
				"n.room_id=nb.place AND e.idno=nb.boro_user AND (n.dept='"+myDept+"' OR n.unit='"+myUnit+"') AND " +
				"nb.boro_date >= '"+sf.format(new Date())+"' AND (nb.checkIn IS NULL OR nb.checkIn='')");
		
		List myBoro=new ArrayList();		
		for(int i=0; i<list.size(); i++){
			map=new HashMap();
			map.put("title", ((Map)list.get(i)).get("title"));
			
			map.put("checkIn", 
					"<input type='hidden' name='Oid' value='"+((Map)list.get(i)).get("Oid")+"'>" +
					"<input type='hidden' name='checker' value='"+me.getIdno()+"'>" +
					"<select name='checkIn'><option value='Y'>同意</option><option value='N'>拒絕</option></select>");
			
			map.put("boro_date", ((Map)list.get(i)).get("boro_date"));
			map.put("begin", startEnd(((Map)list.get(i)).get("begin").toString()));
			map.put("end", startEnd(((Map)list.get(i)).get("end").toString()));
			map.put("room_id", ((Map)list.get(i)).get("buildName")+""+((Map)list.get(i)).get("room_id"));
			
			//System.out.println(((Map)list.get(i)).get("checkIn").equals("Y"));
			try{
				if(((Map)list.get(i)).get("checkIn").equals("Y")){
					map.put("doc", "<a href='/CIS/Print/publicAccess/NabbrBoroDoc.do?Oid="+((Map)list.get(i)).get("Oid")+"'><img src='images/ico_file_word.png' border=0></a>");
				}else{
					map.put("doc", "");
				}
			}catch(Exception e){
				map.put("doc", "");
			}
			
			
			map.put("unitName", ((Map)list.get(i)).get("unitName"));
			map.put("heads", ((Map)list.get(i)).get("heads"));
			
			myBoro.add(map);			
		}
		
		session.setAttribute("myBoro", myBoro);
		
		session.setAttribute("build", manager.ezGetBy("SELECT idno, name FROM code5 WHERE category='building' ORDER BY idno")); //大樓
		setContentPage(request.getSession(false), "publicAccess/RooManager.jsp");
		return mapping.findForward("Main");
	}
	
	
	
	/**
	 * 查來借
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listRoom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm roomForm = (DynaActionForm) form;
		
		String term=manager.getSchoolTerm().toString();
		
		String building=(String)roomForm.get("building");
		String floor=(String)roomForm.get("floor");
		String boro=(String)roomForm.get("boro");
		
		/*
		if(building.equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "請選擇建築物"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		List list=manager.ezGetBy("SELECT n.seat, n.room_id, n.remark, n.name2, n.floor, n.Oid as nabbrOid, c1.name as buildName, " +
				"c2.name as deptName, c3.name as unitName FROM(((Nabbr n LEFT OUTER JOIN code5 c1 ON " +
				"c1.category='building' AND c1.idno=n.building)LEFT OUTER JOIN code5 c2 ON " +
				"c2.category='Dept' AND c2.idno=n.dept)LEFT OUTER JOIN code5 c3 ON " +
				"c3.category='Unit' AND c3.idno=n.unit)WHERE n.boro='Y' AND " +//可借的 "Y"
				"n.building LIKE'"+building+"%' AND n.floor LIKE'"+floor+"%'");
		*/

		List list=manager.ezGetBy("SELECT n.boro, n.seat, n.room_id, n.remark, n.name2, n.floor, n.Oid as nabbrOid, c1.name as buildName, " +
				"c2.name as deptName, c3.name as unitName FROM(((Nabbr n LEFT OUTER JOIN code5 c1 ON " +
				"c1.category='building' AND c1.idno=n.building)LEFT OUTER JOIN code5 c2 ON " +
				"c2.category='Dept' AND c2.idno=n.dept)LEFT OUTER JOIN code5 c3 ON " +
				"c3.category='Unit' AND c3.idno=n.unit)WHERE n.boro LIKE'"+boro+"%' AND " +//可借的 "Y"
				"n.building LIKE'"+building+"%' AND n.floor LIKE'"+floor+"%' ORDER BY n.boro DESC LIMIT 10");
		
		List nabbrs=new ArrayList();
		List dtimeClass;
		Map map;
		
		for(int i=0; i<list.size(); i++){
			map=new HashMap();
			map.put("nabbrOid", ((Map)list.get(i)).get("nabbrOid") );
			map.put("room_id", ((Map)list.get(i)).get("room_id") );
			map.put("remark", ((Map)list.get(i)).get("remark") );
			map.put("name2", ((Map)list.get(i)).get("name2"));
			map.put("floor", ((Map)list.get(i)).get("floor") );
			map.put("nabbrOid", ((Map)list.get(i)).get("nabbrOid") );
			map.put("buildName", ((Map)list.get(i)).get("buildName") );
			map.put("deptName", ((Map)list.get(i)).get("deptName") );
			map.put("unitName", ((Map)list.get(i)).get("unitName") );
			map.put("seat", ((Map)list.get(i)).get("seat") );
			map.put("boro", ((Map)list.get(i)).get("boro") );
			if(((Map)list.get(i)).get("boro").equals("Y")){
				map.put("boro2", "<img src=images/building_go.gif>&nbsp;");
			}else{
				map.put("boro2", "<img src=images/building_delete.gif>&nbsp;");
			}
			
			dtimeClass=manager.ezGetBy("SELECT cs.chi_name, c.ClassName, dc.week, dc.begin, dc.end FROM Dtime_class dc, Dtime d, Class c, Csno cs " +
					"WHERE dc.Dtime_oid=d.Oid AND c.ClassNo=d.depart_class AND d.Sterm='"+term+"' AND " +
							"d.cscode=cs.cscode AND dc.place='"+((Map)list.get(i)).get("room_id")+"'");
			
			map.put("dtimeClass", dtimeClass);			
			nabbrs.add(map);
		}
		
		//if(nabbrs.size()>10){
			ActionMessages msg = new ActionMessages();		//建立共用訊息
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "每次顯示10間教室, <font size=1>(如果找不到教室，請將查詢條件縮小)</font>"));
			saveMessages(request, msg);
		//}
		
		session.setAttribute("nabbrs", nabbrs);
		return mapping.findForward("Main");
	}
	
	/**
	 * 借了
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward borrowing(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm roomForm = (DynaActionForm) form;
		
		Member me = getUserCredential(request.getSession(false)).getMember();//取得登入資訊

		//若是職員
		String boroUnit="";
		if(me.getPriority().equals("A")){
			Empl empl=(Empl)manager.hqlGetBy("FROM Empl WHERE idno='"+me.getIdno()+"'").get(0);
			boroUnit=empl.getUnit();
		}
		
		String boro_date=(String)roomForm.get("boro_date");
		
		String begin=(String)roomForm.get("begin");
		String end=(String)roomForm.get("end");
		
		String place=(String)roomForm.get("place");		
		String boro_tel=(String)roomForm.get("boro_tel");
		String boro_mobile=(String)roomForm.get("boro_mobile");
		String heads=(String)roomForm.get("heads");
		String title=(String)roomForm.get("title");
		String remark=(String)roomForm.get("remark");
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		
		NabbrBoro nb=new NabbrBoro();
		
		nb.setBegin(begin);		
		if(!boro_date.equals("")){			
			nb.setBoroDate(sf.parse(manager.convertDate(boro_date)));
		}else{
			nb.setBoroDate(new Date());
		}
		nb.setBoroMobile(boro_mobile);
		nb.setBoroTel(boro_tel);		
		nb.setBoroUnit(Integer.parseInt(boroUnit));		
		nb.setBoroUser(me.getIdno());
		nb.setEnd(end);		
		if(!heads.equals("")){
			nb.setHeads(Integer.parseInt(heads));
		}else{
			nb.setHeads(0);
		}		
		nb.setPlace(place);
		nb.setRemark(remark);
		nb.setSendDate(new Date());
		nb.setTitle(title);		
		manager.updateObject(nb);
		
		
		
		return unspecified(mapping, form,request, response);
	}
	
	/**
	 * 審
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm roomForm = (DynaActionForm) form;
		
		Member me = getUserCredential(request.getSession(false)).getMember();//取得登入資訊
		
		String Oid[]=(String[])roomForm.get("Oid");
		String checkIn[]=(String[])roomForm.get("checkIn");
		String checker[]=(String[])roomForm.get("checker");
		
		try{
			for(int i=0; i<Oid.length; i++){
				NabbrBoro nb=(NabbrBoro)manager.hqlGetBy("FROM NabbrBoro WHERE oid="+Oid[i]).get(0);			
				nb.setChecker(me.getIdno());
				nb.setCheckIn(checkIn[i]);
				manager.updateObject(nb);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return unspecified(mapping, form,request, response);
	}
	

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("ListRoom", "listRoom");
		map.put("Borrowing", "borrowing");
		map.put("CheckIn", "checkin");
		return map;
	}
	
	private String checkIn(String check){
		if(!check.equals("")){
			if(check.equals("Y")){
				check="完成申請";
			}else{
				check="洽管理單位";
			}
		}else{
			check="審核中";
		}
		
		return check;
	}
	
	private String startEnd(String startOrEnd){
		
		if(startOrEnd.trim().equals("1"))
			startOrEnd="08:00";
		if(startOrEnd.trim().equals("2"))
			startOrEnd="09:00";
		if(startOrEnd.trim().equals("3"))
			startOrEnd="10:00";
		if(startOrEnd.trim().equals("4"))
			startOrEnd="11:00";
		if(startOrEnd.trim().equals("5"))
			startOrEnd="12:00";
		if(startOrEnd.trim().equals("6"))
			startOrEnd="13:00";
		if(startOrEnd.trim().equals("7"))
			startOrEnd="14:00";
		if(startOrEnd.trim().equals("8"))
			startOrEnd="15:00";
		if(startOrEnd.trim().equals("9"))
			startOrEnd="16:00";
		if(startOrEnd.trim().equals("10"))
			startOrEnd="17:00";
		if(startOrEnd.trim().equals("11"))
			startOrEnd="18:00";
		if(startOrEnd.trim().equals("12"))
			startOrEnd="19:00";
		if(startOrEnd.trim().equals("13"))
			startOrEnd="20:00";
		if(startOrEnd.trim().equals("14"))
			startOrEnd="21:00";
		if(startOrEnd.trim().equals("15"))
			startOrEnd="22:00";		
		
		return startOrEnd;
	}

}
