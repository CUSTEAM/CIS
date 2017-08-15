package tw.edu.chit.struts.action.summer;

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

import tw.edu.chit.model.ScoreHist;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.service.SummerManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class CheckOut extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		Toolket.resetCheckboxCookie(response, "fileList");
		SummerManager summerManager = (SummerManager) getBean("summerManager");		
		//CourseManager manager=(CourseManager)getBean("courseManager");	
		session.setAttribute("sweek", summerManager.getSsterm());
		setContentPage(request.getSession(false), "summer/CheckOut.jsp");
		return mapping.findForward("Main");

	}
	
	/**
	 * 建成績
	 */
	public ActionForward FiledSummerScore(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//HttpSession session = request.getSession(false);
		DynaActionForm sform = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		String syear = (String) sform.get("syear");
		//String sterm = (String) sform.get("sterm");
		
		ActionMessages msg = new ActionMessages();
		
		if(syear.equals("")){
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "請指定目標學年、學期"));
			saveMessages(request, msg);		
			return courseCheckOpt(mapping, form, request, response);
			
		}
		
		
		
		
		//int syear=manager.ezGetInt("SELECT Value FROM Parameter WHERE name='School_year'");
		//int sterm=manager.ezGetInt("SELECT Value FROM Parameter WHERE name='School_term'");
		//if(sterm==1)syear--;
		
		//Integer sterm=2; //TODO 目前暑修成績均建入第二學期，若需要寒修是再建 Parameter.
		
		List setScores=getDtimEditList(request);		
		List sseld;		
		int count=0;
		
		//System.out.println(setScores.size());
		for(int i=0; i<setScores.size(); i++){			
			
			sseld=manager.ezGetBy("SELECT * FROM Sseld WHERE seqno='"+((Map)setScores.get(i)).get("seqno")+"' " +
					"AND csdepart_class='"+((Map)setScores.get(i)).get("depart_class")+"' "+
					"AND cscode='"+((Map)setScores.get(i)).get("cscode")+"' ");
			
			
			for(int j=0; j<sseld.size(); j++){				
				if(((Map)sseld.get(j)).get("score")!=null && !((Map)sseld.get(j)).get("score").equals("")){
					//System.out.println("oh");
					try{
						
						ScoreHist sh=new ScoreHist();
						sh.setCredit(Float.parseFloat(((Map)setScores.get(i)).get("credit").toString()));
						sh.setCscode(((Map)setScores.get(i)).get("cscode").toString());
						sh.setEvgrType("3");
						sh.setOpt(((Map)setScores.get(i)).get("opt").toString());
						sh.setSchoolTerm("2");
						sh.setSchoolYear(Short.parseShort(String.valueOf(syear)));
						sh.setScore(Float.parseFloat(((Map)sseld.get(j)).get("score").toString()));
						sh.setStudentNo(((Map)sseld.get(j)).get("student_no").toString());
						sh.setStdepartClass(((Map)sseld.get(j)).get("stdepart_class").toString());
						
						manager.executeSql("DELETE FROM ScoreHist WHERE student_no='"+sh.getStudentNo()+"' AND " +
								"school_year='"+sh.getSchoolYear()+"' AND school_term='"+sh.getSchoolTerm()+"' AND " +
										"cscode='"+sh.getCscode()+"' AND evgr_type='"+sh.getEvgrType()+"'");						
						manager.updateObject(sh);
						count=count+1;
					}catch(Exception e){
						e.printStackTrace();
					}					
				}
			}
		}
		
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "歷史成績建檔完成, 共完成 "+count+"筆"));
		saveMessages(request, msg);		
		return courseCheckOpt(mapping, form, request, response);
	}
	
	/**
	 * 普通查核選項
	 */
	public ActionForward courseCheckOpt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//TODO 已過加退選時間所以沒做暑修查衝堂
		HttpSession session = request.getSession(false);
		SummerManager summerManager = (SummerManager) getBean("summerManager");	
		CourseManager manager=(CourseManager)getBean("courseManager");
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息	
		
		DynaActionForm sform = (DynaActionForm) form;
		String departClass = (String) sform.get("departClass");
		//String classLess = (String) sform.get("classLess");
		String courseNumber = (String) sform.get("courseNumber");
		//String courseName = (String) sform.get("courseName");
		String checkOpt = (String) sform.get("checkOpt");
		String seqno = (String) sform.get("seqno");
		String expLess = (String) sform.get("expLess");
		//String hourPay = (String) sform.get("hourPay");
		//String creditPay = (String) sform.get("creditPay");
		Toolket.resetCheckboxCookie(response, "fileList");		
		
		if(departClass.trim().length()<1){
			//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "班級代碼至少指定1種學制如: 16, 14...164, 142..."));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}
		
		//查扣考
		if(checkOpt.trim().equals("checkExceed")){
			
			List mycs=manager.ezGetBy("SELECT s.seqno, s.student_no, s.csdepart_class, s.cscode, sa.name, c.chi_name, d.thour, st.student_name " +
					"FROM Sseld s, Sdtime d, Sabbr sa, Csno c, stmd st WHERE d.depart_class LIKE'"+departClass+"%' " +
					"AND d.cscode=s.cscode AND d.depart_class=s.csdepart_class AND d.cscode LIKE'"+courseNumber+"%' AND "+
					"sa.no=d.depart_class AND c.cscode=d.cscode AND st.student_no=s.student_no AND s.seqno LIKE '"+seqno+"%'");
			
			List dilgs=new ArrayList();
			Map map;
			
			for(int i=0; i<mycs.size(); i++){				
				
				map=new HashMap();
				map.put("seqno", ((Map)mycs.get(i)).get("seqno"));
				map.put("student_no", ((Map)mycs.get(i)).get("student_no"));
				map.put("csdepart_class", ((Map)mycs.get(i)).get("csdepart_class"));
				map.put("cscode", ((Map)mycs.get(i)).get("cscode"));
				map.put("name", ((Map)mycs.get(i)).get("name"));
				map.put("chi_name", ((Map)mycs.get(i)).get("chi_name"));
				map.put("thour", ((Map)mycs.get(i)).get("thour"));
				map.put("student_name", ((Map)mycs.get(i)).get("student_name"));
				
				//TODO 需重整
				int x;
				if((Integer.parseInt(((Map)mycs.get(i)).get("thour").toString())%3!=0)){
					x=(int) (Integer.parseInt(((Map)mycs.get(i)).get("thour").toString())/3+((Integer.parseInt(((Map)mycs.get(i)).get("thour").toString())%3)/3))+1;
				}else{
					x=(int) (Integer.parseInt(((Map)mycs.get(i)).get("thour").toString())/3+((Integer.parseInt(((Map)mycs.get(i)).get("thour").toString())%3)/3));
				}
				//已曠
				int tmp=summerManager.getSdilgsBy(((Map)mycs.get(i)).get("student_no").toString(), ((Map)mycs.get(i)).get("csdepart_class").toString(), ((Map)mycs.get(i)).get("cscode").toString(), ((Map)mycs.get(i)).get("seqno").toString());
				map.put("nonthour", tmp);
				map.put("overthour", x);
				
				if(tmp>=x){
					map.put("status", "<font color='red'>扣考</font>");
				}else{
					map.put("status", "<font color='green'>正常</font>");
				}
				//沒有輸入差幾節扣考
				if(expLess.equals("")){
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "差幾節呢？"));
					saveErrors(request, error);
					return mapping.findForward("Main");
				}else{
					//判別使用者定義的過濾節數
					if((x-tmp)<=Integer.parseInt(expLess)){
						dilgs.add(map);
					}
				}
			}			
			
			session.setAttribute("dilgs", dilgs);			
			session.setAttribute("checkOpt", "checkExceed");
			
			//msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",checkOpt));
			//saveMessages(request, msg);
		}
		
		//查退費
		if(checkOpt.trim().equals("checkReturnMoney")){		
			//TODO 退費機制目前不存在
			//msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","退費機制目前不存在"));
			//saveMessages(request, msg);	
		}
		
		//成績
		if(checkOpt.trim().equals("checkScoreHist")){			
			session.setAttribute("checkOpt", "checkScoreHist");			
			session.setAttribute("fileList", summerManager.getScoreBy(seqno, departClass, courseNumber));			
			//msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",checkOpt));
			//saveMessages(request, msg);	
		}
		
		//衝堂
		if(checkOpt.trim().equals("checkReOption")){			
			List list=summerManager.ezGetList("SELECT c.chi_name, d.Oid as sdOid, st.student_name, cc.name as ccName, sc.ClassName as scName, s.* FROM " +
					"Sseld s, stmd st, Sdtime d, Sabbr cc, Class sc, Csno c WHERE c.cscode=s.cscode AND sc.ClassNo=st.depart_class AND cc.no=d.depart_class AND " +
					"d.cscode=s.cscode AND d.depart_class=s.csdepart_class AND st.student_no=s.student_no AND s.csdepart_class LIKE '"+departClass+"%'");
			
			List reOption=new ArrayList();
			Map map;
			for(int i=0; i<list.size(); i++){
				Map checkMap=summerManager.checkReOption4Summer(((Map)list.get(i)).get("student_no").toString(), ((Map)list.get(i)).get("sdOid").toString());
				if(checkMap.get("checked").equals("Y")){			
					
					map=new HashMap();
					map.put("student_no", ((Map)list.get(i)).get("student_no"));
					map.put("student_name", ((Map)list.get(i)).get("student_name"));
					map.put("scName", ((Map)list.get(i)).get("scName"));
					
					map.put("cscode", ((Map)list.get(i)).get("cscode"));
					map.put("chi_name", ((Map)list.get(i)).get("chi_name"));
					map.put("ccName", ((Map)list.get(i)).get("ccName"));
					
					map.put("week", checkMap.get("week"));
					map.put("begin", checkMap.get("begin"));
					map.put("end", checkMap.get("end"));
					
					map.put("depart_class", checkMap.get("depart_class"));
					map.put("dcscode", checkMap.get("dcscode"));
					map.put("ddepart_class", checkMap.get("ddepart_class"));
					
					reOption.add(map);
				}
			}			
			session.setAttribute("reOption", reOption);
			session.setAttribute("checkOpt", "checkReOption");	
		}		
		return mapping.findForward("Main");
	}
	
	/**
	 * 取得勾選盒
	 */
	private List getDtimEditList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "fileList");
		List dtimes = (List) session.getAttribute("fileList");
		SummerManager manager = (SummerManager) getBean("summerManager");
		List selDtimes=new ArrayList();
		Map map;
		for(int i=0; i<dtimes.size(); i++){
			map = (Map)dtimes.get(i);
			if (Toolket.isValueInCookie(map.get("Oid").toString(), oids)) {
				selDtimes.addAll(manager.ezGetList("SELECT Oid, depart_class, seqno, cscode, credit, opt FROM Sdtime WHERE Oid='"+
						map.get("Oid").toString()+"'"));
			}
		}

		return selDtimes;
	}
	
	
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("CourseCheckOpt", "courseCheckOpt");
		map.put("FiledSummerScore", "FiledSummerScore");
		return map;
	}

}
