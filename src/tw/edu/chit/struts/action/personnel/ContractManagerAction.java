package tw.edu.chit.struts.action.personnel;

import java.text.SimpleDateFormat;
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

import tw.edu.chit.model.EmplContract;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class ContractManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		//自動完成所有單位
		session.setAttribute("allUnit", manager.ezGetBy("SELECT idno, name FROM CodeEmpl WHERE (category='UnitTeach' OR category='Unit')ORDER BY sequence"));
		//分類
		session.setAttribute("type", manager.ezGetBy("SELECT id, name FROM Pecode WHERE type='pccode6' ORDER BY sequence"));
		//職級
		session.setAttribute("level", manager.ezGetBy("SELECT id, name FROM Pecode WHERE type='pccode7' ORDER BY sequence"));
		//審定
		session.setAttribute("markup", manager.ezGetBy("SELECT id, name FROM Pecode WHERE type='pccode8' ORDER BY sequence"));
		
		//員工分類
		//session.setAttribute("allCategory", manager.ezGetBy("SELECT idno, name FROM CodeEmpl WHERE category='EmpCategory' ORDER BY sequence"));
		
		setContentPage(request.getSession(false), "personnel/ContractManager.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 尋找聘書
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchContract(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm contractForm = (DynaActionForm) form;		
		Map map=contractForm.getMap();
		
		session.removeAttribute("newContract");
		session.setAttribute("oldContract", manager.getContract(map));		
		
		return mapping.findForward("Main");
	}
	
	/**
	 * 修改聘書
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editContract(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm contractForm = (DynaActionForm) form;
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		
		String Oid[]=contractForm.getStrings("Oid");
		
		String contract_nos[]=contractForm.getStrings("contract_nos");
		String start_dates[]=contractForm.getStrings("start_dates");
		String end_dates[]=contractForm.getStrings("end_dates");
		
		String types[]=contractForm.getStrings("types");
		String levels[]=contractForm.getStrings("levels");
		String markups[]=contractForm.getStrings("markups");
		String license_nos[]=contractForm.getStrings("license_nos");
		String delete[]=contractForm.getStrings("delete");
		
		for(int i=0; i<Oid.length; i++){
			
			if(!delete[i].equals("")){//若勾了就刪懶得再確認
				manager.executeSql("DELETE FROM Empl_contract WHERE Oid='"+Oid[i]+"'");				
			}else{
				try{
					manager.executeSql("UPDATE Empl_contract SET contract_no='"+contract_nos[i]+"', " +
							//"start_date='"+manager.convertDate(start_dates[i])+"', end_date='"+manager.convertDate(end_dates[i])+"', " +
							"start_date='"+start_dates[i]+"', end_date='"+end_dates[i]+"', " +
							"type='"+types[i]+"', level='"+levels[i]+"', markup='"+markups[i]+"', " +
							"license_no='"+license_nos[i]+"' WHERE Oid='"+Oid[i]+"'");
				}catch(Exception e){
					e.printStackTrace();
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","聘書字號"+contract_nos[i]+"修改失敗"));					
				}
				if(!error.isEmpty()){
					saveErrors(request, error);
				}
				
			}
		}		
		
		return searchContract(mapping, form, request, response);
	}
	
	/**
	 * 建立聘書
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward createNewContract(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm contractForm = (DynaActionForm) form;
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息		
		session.removeAttribute("oldContract");
		
		if(contractForm.getString("end_date").equals("")){			
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","請指定到期日"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		Map map=contractForm.getMap();				
		session.setAttribute("newContract", manager.getNewContract(map));
		return mapping.findForward("Main");
	}
	
	/**
	 * 確定建立
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addContract(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm cForm = (DynaActionForm) form;
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息			
		
		String titleText=cForm.getString("titleText");
		String contract_startNo=cForm.getString("contract_startNo");
		String contract_endNo=cForm.getString("contract_endNo");
		
		String category=cForm.getString("category");
		
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		
		if(contract_endNo.trim().equals("")||contract_startNo.trim().equals("")||titleText.trim().equals("")){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","字號不可缺少"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		
		List list=(List)session.getAttribute("newContract");
		Integer start=Integer.parseInt(contract_startNo);
		Integer end=Integer.parseInt(contract_endNo);
		if( (end-start)<list.size()){
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","號碼不夠"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		
		
		for(int i=0; i<list.size(); i++){			
			EmplContract ec=new EmplContract();
			ec.setContractNo(titleText+start);
			ec.setEndDate(sf.parse(((Map)list.get(i)).get("end_date").toString()));
			ec.setIdno(((Map)list.get(i)).get("idno").toString());
			ec.setLevel(((Map)list.get(i)).get("level").toString());
			ec.setLicenseNo(((Map)list.get(i)).get("licence_no").toString());
			ec.setMarkup(((Map)list.get(i)).get("markup").toString());
			ec.setStartDate(sf.parse(((Map)list.get(i)).get("start_date").toString()));
			ec.setType(((Map)list.get(i)).get("type").toString());
			ec.setDeptNo(manager.ezGetString("SELECT c.idno FROM empl e, CodeEmpl c WHERE " +
					"e.unit=c.idno AND c.category='UnitTeach' AND e.idno='"+((Map)list.get(i)).get("idno")+"'"));
			ec.setPartime(category);
			EmplContract oec=(EmplContract)manager.hqlGetBy("FROM EmplContract WHERE idno='"+ec.getIdno()+"' ORDER BY Oid").get(0);
			oec.setExtend("Y");//我忘了這要幹麻的			
			
			
			manager.updateObject(oec);
			manager.updateObject(ec);
			start=start+1;
		}
		
		
		session.removeAttribute("newContract");
		session.removeAttribute("oldContract");
		return mapping.findForward("Main");
	}
	
	/**
	 * 
	 */
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("SearchContract", "searchContract");
		map.put("CreateNewContract", "createNewContract");
		map.put("EditContract", "editContract");
		map.put("AddContract", "addContract");
		
		return map;
	}

}
