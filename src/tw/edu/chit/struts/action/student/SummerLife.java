package tw.edu.chit.struts.action.student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.model.Member;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.SummerManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class SummerLife extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		SummerManager summerManager = (SummerManager) getBean("summerManager");		
		CourseManager manager=(CourseManager)getBean("courseManager");
		
		Member me = getUserCredential(request.getSession(false)).getMember();//取得登入資訊		
		
		//查扣考
		String studentNo=me.getAccount();
		List mycs=manager.ezGetBy("SELECT s.seqno, s.csdepart_class, s.cscode, sa.name, c.chi_name, d.thour  " +
				"FROM Sseld s, Sdtime d, Sabbr sa, Csno c WHERE student_no='"+studentNo+"' " +
				"AND d.cscode=s.cscode AND d.depart_class=s.csdepart_class AND d.status='0' AND " +
				"sa.no=d.depart_class AND c.cscode=d.cscode " +
				"GROUP BY s.cscode");
		
		
		List dilgs=new ArrayList();
		Map map;
		
		for(int i=0; i<mycs.size(); i++){
			
			map=new HashMap();
			map.put("csdepart_class", ((Map)mycs.get(i)).get("csdepart_class"));
			map.put("cscode", ((Map)mycs.get(i)).get("cscode"));
			map.put("name", ((Map)mycs.get(i)).get("name"));
			map.put("chi_name", ((Map)mycs.get(i)).get("chi_name"));
			map.put("thour", ((Map)mycs.get(i)).get("thour"));
			
			int x;
			if((Integer.parseInt(((Map)mycs.get(i)).get("thour").toString())%3!=0)){
				x=(int) (Integer.parseInt(((Map)mycs.get(i)).get("thour").toString())/3+((Integer.parseInt(((Map)mycs.get(i)).get("thour").toString())%3)/3))+1;
			}else{
				x=(int) (Integer.parseInt(((Map)mycs.get(i)).get("thour").toString())/3+((Integer.parseInt(((Map)mycs.get(i)).get("thour").toString())%3)/3));
			}
			int tmp=summerManager.getSdilgBy(studentNo, ((Map)mycs.get(i)).get("csdepart_class").toString(), ((Map)mycs.get(i)).get("cscode").toString(), ((Map)mycs.get(i)).get("seqno").toString());
			map.put("nonthour", tmp);
			map.put("overthour", x);
			
			if(tmp>=x){
				map.put("status", "<font color='red'>扣考</font>");
			}else{
				map.put("status", "<font color='green'>正常</font>");
			}
			
			dilgs.add(map);
		}
		
		
		session.setAttribute("dilgs", dilgs);
		
		//查缺曠
		List myDilgTmp=summerManager.ezGetList("SELECT * FROM Sdilg WHERE student_no='"+studentNo+"'");
		Map myDilgMap;
		List myDilg=new ArrayList();
		for(int i=0; i<myDilgTmp.size(); i++){
			myDilgMap=new HashMap();
			myDilgMap.put("ddate", ((Map)myDilgTmp.get(i)).get("ddate"));
			myDilgMap.put("abs1", summerManager.getTimeOffType(((Map)myDilgTmp.get(i)).get("abs1").toString()));
			myDilgMap.put("abs2", summerManager.getTimeOffType(((Map)myDilgTmp.get(i)).get("abs2").toString()));
			myDilgMap.put("abs3", summerManager.getTimeOffType(((Map)myDilgTmp.get(i)).get("abs3").toString()));
			myDilgMap.put("abs4", summerManager.getTimeOffType(((Map)myDilgTmp.get(i)).get("abs4").toString()));
			myDilgMap.put("abs5", summerManager.getTimeOffType(((Map)myDilgTmp.get(i)).get("abs5").toString()));
			myDilgMap.put("abs6", summerManager.getTimeOffType(((Map)myDilgTmp.get(i)).get("abs6").toString()));
			myDilgMap.put("abs7", summerManager.getTimeOffType(((Map)myDilgTmp.get(i)).get("abs7").toString()));
			myDilgMap.put("abs8", summerManager.getTimeOffType(((Map)myDilgTmp.get(i)).get("abs8").toString()));
			myDilgMap.put("abs9", summerManager.getTimeOffType(((Map)myDilgTmp.get(i)).get("abs9").toString()));
			myDilgMap.put("abs10", summerManager.getTimeOffType(((Map)myDilgTmp.get(i)).get("abs10").toString()));
			myDilgMap.put("abs11", summerManager.getTimeOffType(((Map)myDilgTmp.get(i)).get("abs11").toString()));
			
			myDilg.add(myDilgMap);
		}
		
		
		session.setAttribute("myDilg", myDilg);

		session.setAttribute("myScore", summerManager.ezGetList("SELECT d.seqno, c.chi_name, c.cscode, sa.name, sa.no, d.opt, " +
				"d.thour, d.credit, s.score FROM Sseld s, Csno c, Sdtime d, Sabbr sa " +
				"WHERE s.student_no='"+studentNo+"' AND c.cscode=d.cscode AND d.cscode=s.cscode AND " +
				"s.csdepart_class=d.depart_class AND sa.no=d.depart_class AND d.status='0'"));
		
		
		setContentPage(request.getSession(false), "student/SummerLife.jsp");
		return mapping.findForward("Main");

	}

	
	protected Map getKeyMethodMap() {
		
		return null;
	}

}
