package tw.edu.chit.struts.action.studaffair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 導師評量
 * @author JOHN
 *
 */
public class Coansw4tutorAction extends BaseLookupDispatchAction{
	
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		session.setAttribute("years", manager.ezGetBy("SELECT school_year FROM Coansw4tutor GROUP BY school_year"));
		setContentPage(request.getSession(false), "studaffair/Coansw4tutor.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {		
		
		DynaActionForm f = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String school_year=f.getString("school_year");
		String school_term=f.getString("school_term");
		
		List list=manager.ezGetBy("SELECT cs.ClassNo, cs.ClassName, e.cname, c.* FROM " +
		"Class cs, Coansw4tutor c LEFT OUTER JOIN empl e ON c.techid=e.idno WHERE " +
		"c.school_year='"+school_year+"' AND school_term='"+school_term+"' AND " +
		"cs.ClassNo=c.depart_class GROUP BY depart_class ORDER BY c.depart_class");
		
		
		
		
		List tmp;
		float total;
		for(int i=0; i<list.size(); i++){			
			tmp=manager.ezGetBy("SELECT cs.ClassNo, cs.ClassName, e.cname, c.* FROM " +
			"Class cs, Coansw4tutor c LEFT OUTER JOIN empl e ON c.techid=e.idno WHERE " +
			"c.school_year='"+school_year+"' AND school_term='"+school_term+"' AND c.depart_class='" +
			((Map)list.get(i)).get("depart_class")+"' AND "+
			"cs.ClassNo=c.depart_class GROUP BY c.student_no ORDER BY c.student_no");
			
			total=0;
			for(int j=0; j<tmp.size(); j++){
				total=total+sum(((Map)tmp.get(j)).get("answer").toString());
				//System.out.println( ((Map)tmp.get(j)).get("ClassName")+": "+sum(((Map)tmp.get(j)).get("answer").toString())    );
			}
			((Map)list.get(i)).put("smp", tmp.size());
			((Map)list.get(i)).put("avg", (total/tmp.size())*20);
		}
		
		request.setAttribute("cs", list);		
		return mapping.findForward("Main");
	}
	
	/**
	 * 計算單一問卷
	 * @param coansw
	 * @return
	 */
	private float sum(String coansw){
		
		float sum=0;
		for(int i=0; i<coansw.length(); i++){
			
			sum=sum+Float.parseFloat(String.valueOf(coansw.charAt(i)));
			
		}
		
		
		return sum/coansw.length();
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		return map;
	}
}
