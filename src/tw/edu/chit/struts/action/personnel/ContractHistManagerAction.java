package tw.edu.chit.struts.action.personnel;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.EmplContract;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class ContractHistManagerAction extends BaseLookupDispatchAction{
		
	public ActionForward editContractHist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");	
		DynaActionForm cForm = (DynaActionForm) form;
		
		String Oid[]=cForm.getStrings("Oid");
		String idno[]=cForm.getStrings("idno");
		String contract_no[]=cForm.getStrings("contract_no");
		String start_date[]=cForm.getStrings("start_date");
		String end_date[]=cForm.getStrings("end_date");
		String type[]=cForm.getStrings("type");
		String level[]=cForm.getStrings("level");
		String markup[]=cForm.getStrings("markup");
		String license_no[]=cForm.getStrings("license_no");
		//String extend[]=cForm.getStrings("extend");
		String deptNo[]=cForm.getStrings("deptNo");
		String partime[]=cForm.getStrings("partime");
		String aIdno=cForm.getString("aIdno");
		
		SimpleDateFormat sf;
		for(int i=0; i<Oid.length; i++){
			
			if(!Oid[i].equals("")){//原先有則更新
				
				if(start_date[i].equals("")||end_date[i].equals("")){//日期不全立即刪除
					manager.executeSql("DELETE FROM Empl_contract WHERE Oid='"+Oid[i]+"'");
				}else{//日期完整則更新
					EmplContract ec=(EmplContract)manager.hqlGetBy("FROM EmplContract WHERE Oid="+Oid[i]).get(0);
					ec.setContractNo(contract_no[i]);
					ec.setDeptNo(deptNo[i]);
					ec.setEndDate(manager.getROCYtoAD(end_date[i]));
					//ec.setExtend(extend[i]);
					ec.setIdno(aIdno);
					ec.setLevel(level[i]);
					ec.setLicenseNo(license_no[i]);
					ec.setMarkup(markup[i]);
					ec.setPartime(partime[i]);
					ec.setStartDate(manager.getROCYtoAD(start_date[i]));
					ec.setType(type[i]);				
					manager.updateObject(ec);
				}
								
			}else{//新增				
				if(!start_date[i].equals("")&&!end_date[i].equals("")){//日期不全不新增
					EmplContract ec=new EmplContract();
					ec.setContractNo(contract_no[i]);
					ec.setDeptNo(deptNo[i]);
					ec.setEndDate(manager.getROCYtoAD(end_date[i]));
					//ec.setExtend(extend[i]);
					ec.setIdno(aIdno);
					ec.setLevel(level[i]);
					ec.setLicenseNo(license_no[i]);
					ec.setMarkup(markup[i]);
					ec.setPartime(partime[i]);
					ec.setStartDate(manager.getROCYtoAD(start_date[i]));
					ec.setType(type[i]);				
					manager.updateObject(ec);
				}				
			}			
		}

		//聘書歷程
		List contracts=manager.ezGetBy("SELECT ec.*, c.name as deptName " +
				"FROM Empl_contract ec, CodeEmpl c WHERE ec.idno='"+aIdno+"' AND ec.deptNo=c.idno AND (c.category='UnitTeach' OR c.category='Unit')");
		for(int i=0; i<contracts.size(); i++){
			//System.out.println(contracts.get(i));
			try{//開始日期
				((Map)contracts.get(i)).put("start_date", manager.convertDate(((Map)contracts.get(i)).get("start_date").toString()));
			}catch(Exception e){
				
			}
			try{//結束日期
				((Map)contracts.get(i)).put("end_date", manager.convertDate(((Map)contracts.get(i)).get("end_date").toString()));
			}catch(Exception e){
				
			}
		}
		
		session.setAttribute("contracts", contracts);
		return mapping.findForward("Main");
	}
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("EditContractHist", "editContractHist");
		return map;
	}

}
