package tw.edu.chit.struts.action.registration;

import java.util.ArrayList;
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

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class CheckeOutAction extends BaseLookupDispatchAction{

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		setContentPage(request.getSession(false), "registration/CheckOut.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward checkOut(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
		HttpServletResponse response) throws Exception {
		DynaActionForm cForm = (DynaActionForm) form;
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String checkOpt=cForm.getString("checkOpt");
		String classLess=cForm.getString("classLess");//查詢條
		String year=manager.getSchoolYear().toString();
		String term=manager.getSchoolTerm().toString();
		
		if(checkOpt.equals("")){
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "請選擇查核項目"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		//重複修課學生查核
		Map map;
		List tmp;
		List list;
		
		if(checkOpt.equals("reSelect")){
			session.setAttribute("checkOpt", "reSelect");
			//尋找所有學生
			ActionMessages msg = new ActionMessages();		//建立共用訊息
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","查核完成!"));
			saveMessages(request, msg);
			session.setAttribute("reSelect", manager.checkReSelected(classLess));
			
		}
		
		//當學期選課異常
		if(checkOpt.equals("SelectException")){
			session.setAttribute("checkOpt", "SelectException");
			
			
			ActionMessages msg = new ActionMessages();		//建立共用訊息
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","查核完成!"));
			saveMessages(request, msg);
			session.setAttribute("SelectException", manager.checkReSelectedNow(classLess, year, term));
		}
		
		//下學期選課異常
		if(checkOpt.equals("SelectExceptionNext")){
			
			if(term.equals("1")){
				term="2";
			}else{
				term="1";
				year=String.valueOf(manager.getSchoolYear()+1);
			}
			
			session.setAttribute("checkOpt", "SelectException");
			List SelectException=new ArrayList();
			tmp=manager.ezGetBy("SELECT student_no, student_name FROM stmd WHERE depart_class LIKE'"+classLess+"%'");
			String cscode;
			//取一人選課
			for(int i=0; i<tmp.size(); i++){
				
				list=manager.ezGetBy("SELECT * FROM Seld s, Dtime d WHERE s.Dtime_oid=d.Oid AND d.Sterm='"+term+"' AND " +
				"s.student_no='"+((Map)tmp.get(i)).get("student_no")+"'");
				
				for(int j=0; j<list.size(); j++){
					cscode=((Map)list.get(j)).get("cscode").toString();
					for(int k=0; k<list.size(); k++){						
						if(((Map)list.get(k)).get("cscode").equals(cscode)&& !((Map)list.get(k)).get("Oid").equals(((Map)list.get(j)).get("Oid"))){							
							map=new HashMap();
							map.put("student_no", ((Map)tmp.get(i)).get("student_no"));
							map.put("student_name", ((Map)tmp.get(i)).get("student_name"));
							map.put("cscode", cscode);
							map.put("chi_name", "當期重複修: "+cscode);
							
							map.put("school_year", year);
							map.put("school_term", term);							
							SelectException.add(map);
						}
					}
					
					if(manager.ezGetInt("SELECT COUNT(*)FROM ScoreHist WHERE student_no='"+((Map)tmp.get(i)).get("student_no")+"' AND " +
					"cscode='"+((Map)list.get(j)).get("cscode")+"' AND score>=60" )>0){
						map=new HashMap();
						map.put("student_no", ((Map)tmp.get(i)).get("student_no"));
						map.put("student_name", ((Map)tmp.get(i)).get("student_name"));
						map.put("cscode", cscode);
						map.put("chi_name", "過去曾修: "+cscode);
						map.put("score", "及格以上");
						map.put("school_year", year);
						map.put("school_term", term);
						SelectException.add(map);
					}					
				}				
			}			
			
			ActionMessages msg = new ActionMessages();		//建立共用訊息
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","查核完成!"));
			saveMessages(request, msg);
			session.setAttribute("SelectException", SelectException);
		}		
		
		//入學文號
		if(checkOpt.equals("entrno")){
			session.setAttribute("checkOpt", "entrno");
			String stdno;
			list=new ArrayList();
			
			List students=manager.ezGetBy("SELECT student_no FROM stmd WHERE depart_class LIKE '"+classLess+"%'");
			
			for(int i=0; i<students.size(); i++){
				stdno=((Map)students.get(i)).get("student_no").toString();				
				tmp=manager.ezGetBy("SELECT * FROM entrno e WHERE e.first_stno<='"+stdno+"' AND e.second_stno>='"+stdno+"'");
				
				
				
				if(tmp.size()>1){					
					for(int j=0; j<tmp.size(); j++){						
						((Map)tmp.get(j)).put("student_no", stdno);						
					}
					list.addAll(tmp);
				}
				
				if(tmp.size()==0){
					list.addAll(tmp);
				}
				
				//if(!getEntrNo(stdno)){
					//list.addAll(tmp);
				//}
				
				
				
			}
			session.setAttribute("entrno", list);			
			//System.out.println(list.size());
		}
	
	return mapping.findForward("Main");

}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("CourseCheckOpt", "checkOut");
		return map;
	}
	
	private boolean getEntrNo(String student_no){
		
		int num=Integer.parseInt(student_no.substring(student_no.length()-3, student_no.length()));
		String dum=student_no.substring(0, student_no.length()-3);
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		List list=manager.ezGetBy("SELECT * FROM entrno WHERE " +
				"first_stno LIKE '"+dum+"%' AND " +
				"LENGTH(first_stno)="+student_no.length());
		
		int begin;
		int end;
		for(int i=0; i<list.size(); i++){
			
			begin=Integer.parseInt(((Map)list.get(i)).get("first_stno").toString().substring(student_no.length()-3, student_no.length()));
			end=Integer.parseInt(((Map)list.get(i)).get("second_stno").toString().substring(student_no.length()-3, student_no.length()));
			
			if(num>=begin&&num<=end){
				if(!((Map)list.get(i)).get("permission_no").equals("")){
					System.out.println(((Map)list.get(i)).get("permission_no")+"?");
					return true;
				}else{
					return false;
				}
			}
		}		
		return false;
	}
	
	
}