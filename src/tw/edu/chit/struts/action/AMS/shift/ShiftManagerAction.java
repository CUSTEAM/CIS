package tw.edu.chit.struts.action.AMS.shift;

import java.text.SimpleDateFormat;
import java.util.HashMap;
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

import tw.edu.chit.model.AmsShiftTime;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 班別管理
 * @author JOHN
 *
 */
public class ShiftManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		request.setAttribute("allShifts", manager.ezGetBy("SELeCT * FROM AMS_ShiftTime"));
		
		setContentPage(request.getSession(false), "AMS/shift/ShiftManager.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm sForm = (DynaActionForm) form;
		
		
		String id[]=sForm.getStrings("id");
		String name[]=sForm.getStrings("name");
		
		String in1[]=sForm.getStrings("in1");
		String in2[]=sForm.getStrings("in2");
		String in3[]=sForm.getStrings("in3");
		String in4[]=sForm.getStrings("in4");
		String in5[]=sForm.getStrings("in5");
		String in6[]=sForm.getStrings("in6");
		String in7[]=sForm.getStrings("in7");
		
		String out1[]=sForm.getStrings("out1");
		String out2[]=sForm.getStrings("out2");
		String out3[]=sForm.getStrings("out3");
		String out4[]=sForm.getStrings("out4");
		String out5[]=sForm.getStrings("out5");
		String out6[]=sForm.getStrings("out6");
		String out7[]=sForm.getStrings("out7");
		
		if(id[0].trim().equals("")||name[0].trim().equals("")){
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "建立失敗, 班別代碼及名稱不可為空白"));
			saveErrors(request, error);
			return unspecified(mapping, form, request, response);
		}
		
		SimpleDateFormat sf=new SimpleDateFormat("HH:mm");
		try{			
			
			
			/*
			manager.executeSql("INSERT INTO AMS_ShiftTime(id, name, in1, in2, in3, in4, in5, in6, in7, out1, out2, out3, " +
					"out4, out5, out6, out7)VALUES('"+
					id[0]+"', '"+
					name[0]+"', '"+
					in1[0]+"', '"+
					in2[0]+"', '"+
					in3[0]+"', '"+
					in4[0]+"', '"+
					in5[0]+"', '"+
					in6[0]+"', '"+
					in7[0]+"', '"+
					out1[0]+"', '"+
					out2[0]+"', '"+
					out3[0]+"', '"+
					out4[0]+"', '"+
					out5[0]+"', '"+
					out6[0]+"', '"+
					out7[0]+"')");
			*/
			
			AmsShiftTime ast=new AmsShiftTime();
			ast.setId(id[0]);
			ast.setName(name[0]);
			
			if(!in1[0].trim().equals("")){ast.setIn1(sf.parse(in1[0]));}
			if(!in2[0].trim().equals("")){ast.setIn2(sf.parse(in2[0]));}
			if(!in3[0].trim().equals("")){ast.setIn3(sf.parse(in3[0]));}
			if(!in4[0].trim().equals("")){ast.setIn4(sf.parse(in4[0]));}
			if(!in5[0].trim().equals("")){ast.setIn5(sf.parse(in5[0]));}
			if(!in6[0].trim().equals("")){ast.setIn6(sf.parse(in6[0]));}
			if(!in7[0].trim().equals("")){ast.setIn7(sf.parse(in7[0]));}
			
			if(!out1[0].trim().equals("")){ast.setOut1(sf.parse(out1[0]));}
			if(!out2[0].trim().equals("")){ast.setOut2(sf.parse(out2[0]));}
			if(!out3[0].trim().equals("")){ast.setOut3(sf.parse(out3[0]));}
			if(!out4[0].trim().equals("")){ast.setOut4(sf.parse(out4[0]));}
			if(!out5[0].trim().equals("")){ast.setOut5(sf.parse(out5[0]));}
			if(!out6[0].trim().equals("")){ast.setOut6(sf.parse(out6[0]));}
			if(!out7[0].trim().equals("")){ast.setOut7(sf.parse(out7[0]));}
			
			manager.updateObject(ast);
			
			
		}catch(Exception e){
			e.printStackTrace();
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "建立失敗, 班別名稱重複"));
			saveErrors(request, error);
		}
		
		return unspecified(mapping, form, request, response);

	}
	
	public ActionForward modify(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm sForm = (DynaActionForm) form;		
		String id[]=sForm.getStrings("id");
		String name[]=sForm.getStrings("name");
		/*
		String in1[]=sForm.getStrings("in1");
		String in2[]=sForm.getStrings("in2");
		String in3[]=sForm.getStrings("in3");
		String in4[]=sForm.getStrings("in4");
		String in5[]=sForm.getStrings("in5");
		String in6[]=sForm.getStrings("in6");
		String in7[]=sForm.getStrings("in7");
		
		String out1[]=sForm.getStrings("out1");
		String out2[]=sForm.getStrings("out2");
		String out3[]=sForm.getStrings("out3");
		String out4[]=sForm.getStrings("out4");
		String out5[]=sForm.getStrings("out5");
		String out6[]=sForm.getStrings("out6");
		String out7[]=sForm.getStrings("out7");		
		*/
		try{			
			for(int i=1; i<name.length; i++){
				//System.out.println("name="+name[i]);
				if(name[i].trim().equals("")){					
					//無名稱立即刪除
					manager.executeSql("DELETE FROM AMS_ShiftTime WHERE id='"+id[i]+"'");
				}else{
					
					manager.executeSql(
							"UPDATE AMS_ShiftTime SET " +							
							//"id='"+id[i]+"', "+
							"name='"+name[i]+"'"+							
							
							//"in1='"+in1[i]+"', "+
							//"in2='"+in2[i]+"', "+
							//"in3='"+in3[i]+"', "+
							//"in4='"+in4[i]+"', "+
							//"in5='"+in5[i]+"', "+
							//"in6='"+in6[i]+"', "+
							//"in7='"+in7[i]+"', "+
							
							//"out1='"+out1[i]+"', "+
							//"out2='"+out2[i]+"', "+
							//"out3='"+out3[i]+"', "+
							//"out4='"+out4[i]+"', "+
							//"out5='"+out5[i]+"', "+
							//"out6='"+out6[i]+"', "+
							//"out7='"+out7[i]+"' "+
							
							"WHERE id='"+id[i]+"'");
				}				
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "建立失敗, 請檢查是否有欄位空白或重複班別"));
			saveErrors(request, error);
		}
		
		
		
		
		
		return unspecified(mapping, form, request, response);

	}	
	
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Create", "create");
		map.put("Modify", "modify");
		return map;
	}

}
