package tw.edu.chit.struts.action.sysadmin;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.EpsField;
import tw.edu.chit.model.EpsTable;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.struts.action.sysadmin.sshtools.SSHClient;

public class TableManager extends BaseLookupDispatchAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		
		CourseManager manager = (CourseManager) getBean("courseManager");		
		
		List allTable=manager.ezGetBy("SELECT * FROM Eps_table");
		List field;
		for(int i=0; i<allTable.size(); i++){
			field=manager.ezGetBy("SELECT * FROM Eps_field WHERE table_oid='"+((Map)allTable.get(i)).get("Oid")+"' ORDER BY sequence");
			((Map)allTable.get(i)).put("field", field);
		}
		
		request.setAttribute("format", manager.ezGetBy("SELECT * FROM Eps_format"));
		request.setAttribute("allTable", allTable);		
		
		setContentPage(request.getSession(false), "portfolio/SysPortfolioManager/TableManager.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 新增
	 */
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		String Oid=aForm.getStrings("Oid")[0];
		String tableName=aForm.getStrings("tableName")[0];
		//String sys[]=aForm.getStrings("sys");
		String tableSeq=aForm.getStrings("tableSeq")[0];
		
		if(!tableName.trim().equals("")&& !tableSeq.trim().equals("")){
			EpsTable table=new EpsTable();
			table.setName(tableName);
			table.setSequence(Integer.parseInt(tableSeq));
			table.setSys(Byte.valueOf("0"));
			try{
				manager.updateObject(table);
			}catch(Exception e){
				e.printStackTrace();
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","建立新欄位時發現錯誤"));
			}
			
		}
		if(!error.isEmpty()){
			saveErrors(request, error);	//身日必須填寫 - 返回錯誤
		}else{
			ActionMessages msg = new ActionMessages();		//建立共用訊息
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","新增完成"));
			saveMessages(request, msg);
		}
		return unspecified(mapping, form, request, response);
	}
	
	/**
	 * 修改、儲存
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		CourseManager manager = (CourseManager) getBean("courseManager");	
		
		DynaActionForm aForm = (DynaActionForm) form;

		String Oid[]=aForm.getStrings("Oid");
		String tableName[]=aForm.getStrings("tableName");
		//String sys[]=aForm.getStrings("sys");
		String tableSeq[]=aForm.getStrings("tableSeq");				
		
		String fieldOid[]=aForm.getStrings("fieldOid");
		String tableOid[]=aForm.getStrings("tableOid");
		String fieldName[]=aForm.getStrings("fieldNames");
		String type[]=aForm.getStrings("type");
		String fieldSeq[]=aForm.getStrings("fieldSeq");
		String size[]=aForm.getStrings("size");		
		
		/*
		System.out.println("fieldOid="+fieldOid.length);
		System.out.println("tableOid="+tableOid.length);
		System.out.println("fieldName="+fieldName.length);
		System.out.println("type="+type.length);
		System.out.println("fieldSeq="+fieldSeq.length);
		System.out.println("size="+size.length);
		*/		
		
		EpsTable table;
		EpsField field;
		
		//處理欄位
		for(int i=0; i<fieldOid.length; i++){			
			//新增
			if(fieldOid[i].trim().equals("")){
				//若檢測通過
				if(
						!fieldName[i].trim().equals("")&&
						!type[i].trim().equals("")&&
						!fieldSeq[i].trim().equals("")&&
						!size[i].trim().equals("")){
					//建立新field
					field=new EpsField();
					field.setName(fieldName[i]);
					field.setSequence(Integer.parseInt(fieldSeq[i]));
					field.setSize(Integer.parseInt(size[i]));					
					field.setTableOid(Integer.parseInt(tableOid[i]));
					field.setType(Integer.parseInt(type[i]));
					try{
						manager.updateObject(field);
					}catch(Exception e){
						error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","建立新欄位時發現錯誤"));						
					}
				}								
				
			}else{				
				//若檢測通過
				if(!fieldOid[i].equals("")&&
						!fieldOid[i].trim().equals("")&&
						!fieldName[i].trim().equals("")&&
						!type[i].trim().equals("")&&
						!fieldSeq[i].trim().equals("")&&
						!size[i].trim().equals("")){					
					//修改或新增field
					field=(EpsField)manager.hqlGetBy("FROM EpsField WHERE Oid='"+fieldOid[i]+"'").get(0);
					field.setName(fieldName[i]);
					field.setSequence(Integer.parseInt(fieldSeq[i]));
					field.setSize(Integer.parseInt(size[i]));
					try{
						manager.updateObject(field);
					}catch(Exception e){
						error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","修改舊欄位時發現錯誤"));						
					}
				}
			}
		}
		
		if(!error.isEmpty()){
			saveErrors(request, error);	//身日必須填寫 - 返回錯誤
		}else{
			ActionMessages msg = new ActionMessages();		//建立共用訊息
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","完成"));
			saveMessages(request, msg);
		}
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Create", "create");
		map.put("Save", "save");
		return map;
	}

}
