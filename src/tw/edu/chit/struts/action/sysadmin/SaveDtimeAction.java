package tw.edu.chit.struts.action.sysadmin;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
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

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.DtimeReserve;
import tw.edu.chit.model.DtimeReserveOpencs;
import tw.edu.chit.model.DtimeReserveReady;
import tw.edu.chit.model.DtimeReserveTeacher;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 課程轉歷年
 * @author shawn
 */
public class SaveDtimeAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");		
		setContentPage(request.getSession(false), "sysadmin/SaveDtime.jsp");		
		
		request.setAttribute("term", manager.getSchoolTerm());
		request.setAttribute("year", manager.getSchoolYear());
		
		System.out.println("year!!");
		
		return mapping.findForward("Main");
	}
	
	public ActionForward ok(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String term=manager.getSchoolTerm().toString();
		String year=manager.getSchoolYear().toString();
		
		
		manager.executeSql("DELETE FROM Savedtime WHERE school_term='"+term+"' AND school_year='"+year+"'");
		List list=manager.ezGetBy(
		"SELECT Oid, depart_class, cscode, techid, opt, elearning, " +
		"credit, thour, Introduction, Syllabi, Syllabi_sub " +
		"FROM Dtime WHERE Sterm='"+term+"'");
		
		//計算教學評量
		Map map;
		for(int i=0; i<list.size(); i++){
			map=countAvg((Map)list.get(i));			
			//更新本學期
			manager.executeSql("UPDATE Dtime SET coansw="+map.get("total")+", " +
			"simples="+map.get("sumAns")+" WHERE Oid='"+((Map)list.get(i)).get("Oid")+"'");			
		}
		//寫入歷史
		
		manager.executeSql("INSERT INTO Savedtime(depart_class, cscode, techid, " +
		"opt, credit, thour, Introduction, Syllabi, Syllabi_sub, avg, samples)" +
		"SELECT depart_class, cscode, techid, opt, credit, thour, Introduction, Syllabi, " +
		"Syllabi_sub, coansw, simples FROM Dtime WHERE Sterm='"+term+"'");
		
		manager.executeSql("UPDATE Savedtime SET school_year="+year+", school_term="+term+" WHERE school_year=''");
		
		
		return unspecified(mapping, form, request, response);
	}
	
	/**
	 * 計算教學評量
	 * 重用CIS專案元件
	 * @see tw.edu.chit.service.impl.CourseManagerImpl
	 * @param d
	 * @throws SQLException
	 */
	private Map countAvg(Map d) throws SQLException{		
		CourseManager manager = (CourseManager) getBean("courseManager");	
		NumberFormat nf = NumberFormat.getInstance();
	    nf.setMaximumFractionDigits(1);
	    List coquests;
	    
	    if(!d.get("elearning").equals("0")){//非一般課程
	    	coquests=manager.ezGetBy("SELECT * FROM CoQuestion WHERE type='M' AND (textValue='1'||textValue='2') ORDER BY sequence");
	    }else{	    	
	    	coquests=manager.ezGetBy("SELECT * FROM CoQuestion WHERE type='M' AND textValue='0' ORDER BY sequence");
	    }		
		
		float queSize[]=new float[coquests.size()];		
		List myCoansw=manager.ezGetBy(
		"SELECT * FROM Coansw WHERE (answer NOT LIKE '%111%') AND Dtime_oid="+d.get("Oid"));

		for(int j=0; j<myCoansw.size(); j++){
			String answer=((Map)myCoansw.get(j)).get("answer").toString();
			// 散裝並加總
			for(int k=0; k<queSize.length; k++){
				//五分制和百分制的關鍵點在此
				queSize[k]=queSize[k]+Integer.parseInt( answer.substring(k, k+1))*20;	
			}
		}
		
		Map map=new HashMap();
		map.put("Oid", d.get("Oid"));		
		Map dtime=manager.ezGetMap("SELECT d.techid, d.opt, d.credit, d.elearning, d.depart_class, d.cscode, e.cname " +
		"FROM Dtime d LEFT OUTER JOIN empl e ON d.techid=e.idno " +
		"WHERE d.Oid="+d.get("Oid"));		
		map.put("sumAns", myCoansw.size());		
		float total=0f; // 某科總分
		List score=new ArrayList();// 某科的細節
		Map sMap;
		BigDecimal big;		
		for(int j=0; j<coquests.size(); j++){
			sMap=new HashMap();
			sMap.put("options", ((Map)coquests.get(j)).get("options"));
			total=total+getCoanswTotle(queSize[j], myCoansw.size());
			score.add(sMap);
		}
		total=total/coquests.size();
		map.put("total", roundOff(total, 1));		
		
		if(myCoansw.size()>0){
			return map;
		}else{
			map.put("total", 0);
			map.put("sumAns", 0);
			return map;
		}
	}
	
	/**
	 * 算分數
	 */
	private static Float getCoanswTotle(float queSize, int myCoansw){
		return queSize/myCoansw;
	}
	
	/**
	 * 將float四捨五入至小數第n位
	 */
	public static float roundOff(float f, int n){
		try{
			BigDecimal b=new BigDecimal(f);	
			return b.setScale(n,BigDecimal.ROUND_HALF_UP).floatValue();
		}catch(NumberFormatException e){
			return 0;
		}		
	}

	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("OK", "ok");	
		return map;
	}
}
