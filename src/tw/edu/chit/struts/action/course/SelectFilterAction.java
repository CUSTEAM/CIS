package tw.edu.chit.struts.action.course;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
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

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.SeldCouFilter;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;
/**
 * 篩選作業
 * @author JOHN
 *
 */
public class SelectFilterAction extends BaseLookupDispatchAction{

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Toolket.resetCheckboxCookie(response, "selectFilter");
		HttpSession session = request.getSession(false);
		session.setAttribute("filterMode", ""); //模式		
		
		setContentPage(request.getSession(false), "course/SelectFilter.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 查詢篩選結果
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward courseFilterList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm SelectFilterForm = (DynaActionForm) form;

		Toolket.resetCheckboxCookie(response, "selectFilter");
		Toolket.resetCheckboxCookie(response, "selectFilterCouList");
		session.removeAttribute("table");
		session.removeAttribute("chair");
		session.removeAttribute("selectFilterCouList");
		session.removeAttribute("selectFilterList");
		session.removeAttribute("selFilter");//移除課程篩選作業的查詢結果
		session.removeAttribute("stuFilter");//移除課程篩選作業的查詢結果

		CourseManager manager = (CourseManager) getBean("courseManager");
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		session.setAttribute("filterMode", "FilterSearch");	//模式

		String term = (String) SelectFilterForm.get("sterm");
		String choseType = (String) SelectFilterForm.get("choseType");
		String open = (String) SelectFilterForm.get("open");
		String elearning = (String) SelectFilterForm.get("elearning");
		String classLess = (String) SelectFilterForm.get("classLess");

		// 載入使用者負責班級
		UserCredential credential = (UserCredential) session.getAttribute("Credential");
		Clazz[] classes = credential.getClassInChargeAry();

		//呼叫搜尋
		session.setAttribute("selectFilterCouList", manager.getCouFilter(
				classes, "", "", term, choseType, open, elearning, classLess));

		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","搜尋完成!"));
		saveMessages(request, msg);

		//setContentPage(request.getSession(false), "course/SelectFilter.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 篩選復原
	 */
	public ActionForward courseFilterComeback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		//DynaActionForm SelectFilterForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");

		List Checked = getDtimFilterList(request); // 勾到的課程
		SeldCouFilter sel;
		for(int i=0; i<Checked.size(); i++){
			sel=(SeldCouFilter) Checked.get(i);
			//進行恢復
			manager.rollbackDtime(sel.getDtimeOid().toString());
		}



		ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息


		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","共恢復"+Checked.size()+"門課程, "+"? 筆學生選課"));
		saveMessages(request, msg);

		session.removeAttribute("table");
		session.removeAttribute("chair");
		session.removeAttribute("selectFilterCouList");
		session.removeAttribute("selectFilterList");
		session.removeAttribute("selFilter");//移除課程篩選作業的查詢結果
		session.removeAttribute("stuFilter");//移除課程篩選作業的查詢結果
		setContentPage(request.getSession(false), "course/SelectFilter.jsp");
		return mapping.findForward("Main");

	}


	/**
	 * 取得搜尋列表
	 */
	public ActionForward courseFilterSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		DynaActionForm SelectFilterForm = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, "selectFilter");
		Toolket.resetCheckboxCookie(response, "selectFilterCouList");
		CourseManager manager = (CourseManager) getBean("courseManager");
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		session.setAttribute("filterMode", "search");	//模式

		session.removeAttribute("table");
		session.removeAttribute("chair");
		session.removeAttribute("selectFilterCouList");
		session.removeAttribute("selectFilterList");
		session.removeAttribute("selFilter");//移除課程篩選作業的查詢結果
		session.removeAttribute("stuFilter");//移除課程篩選作業的查詢結果

		String term = (String) SelectFilterForm.get("sterm");
		String choseType = (String) SelectFilterForm.get("choseType");
		String open = (String) SelectFilterForm.get("open");
		String elearning = (String) SelectFilterForm.get("elearning");
		String classLess = (String) SelectFilterForm.get("classLess");

		// 載入使用者負責班級
		UserCredential credential = (UserCredential) session.getAttribute("Credential");
		Clazz[] classes = credential.getClassInChargeAry();

		//呼叫搜尋
		session.setAttribute("selectFilterList", manager.getDtimeBy(
				classes, "", "", term, choseType, open, elearning, classLess, "", ""));

		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","搜尋完成!"));
		saveMessages(request, msg);

		setContentPage(request.getSession(false), "course/SelectFilter.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 重設
	 */
	public ActionForward clear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm SelectFilterForm = (DynaActionForm) form;
		Toolket.resetCheckboxCookie(response, "selectFilter");
		Toolket.resetCheckboxCookie(response, "selectFilterCouList");
		SelectFilterForm.set("sterm", "");
		SelectFilterForm.set("choseType", "%");
		SelectFilterForm.set("classLess", "");
		SelectFilterForm.set("open", "%");
		SelectFilterForm.set("elearning", "%");

		HttpSession session = request.getSession(false);

		session.removeAttribute("selectFilterList");
		session.setAttribute("filterMode", "");	//模式
		session.setAttribute("filterType", "");	//型態
		session.removeAttribute("table");
		session.removeAttribute("chair");
		session.removeAttribute("selectFilterCouList");
		session.removeAttribute("selectFilterList");
		session.removeAttribute("selFilter");//移除課程篩選作業的查詢結果
		session.removeAttribute("stuFilter");//移除課程篩選作業的查詢結果

		setContentPage(request.getSession(false), "course/SelectFilter.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 取消
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.setAttribute("filterMode", "search");	//模式
		session.setAttribute("filterType", "");	//型態
		//session.removeAttribute("selectFilterList");

		setContentPage(request.getSession(false), "course/SelectFilter.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 取得普通勾選欄位
	 */
	private List getDtimEditList(HttpServletRequest request) {
		//get cookie change to oid
		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "selectFilter");
		List dtimes = (List) session.getAttribute("selectFilterList");
		CourseManager manager = (CourseManager) getBean("courseManager");
		List<Dtime> selDtimes=new ArrayList<Dtime>();
		Map map;
		for(int i=0; i<dtimes.size(); i++){
			map = (Map)dtimes.get(i);
			if (Toolket.isValueInCookie(map.get("oid").toString(), oids)) {
				selDtimes.addAll((List<Dtime>) manager.getDtimeBy(map.get("oid").toString()));
			}
		}

		return selDtimes;
	}

	/**
	 * 取得篩選查詢的勾選欄位
	 */
	private List getDtimFilterList(HttpServletRequest request) {
		//get cookie change to oid
		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "selectFilterCouList");
		List dtimes = (List) session.getAttribute("selectFilterCouList");
		CourseManager manager = (CourseManager) getBean("courseManager");
		List<Dtime> selDtimes=new ArrayList<Dtime>();
		Map map;
		for(int i=0; i<dtimes.size(); i++){
			map = (Map)dtimes.get(i);
			if (Toolket.isValueInCookie(map.get("oid").toString(), oids)) {
				selDtimes.addAll((List) manager.SeldCouFilter(map.get("oid").toString()));
			}
		}

		return selDtimes;
	}

	/**
	 * 準備進行學生篩選作業 - 預備執行
	 */
	public ActionForward studentFilter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息

		HttpSession session = request.getSession(false);
		session.setAttribute("filterMode", "delete");	//模式
		session.setAttribute("filterType", "stu");	//型態
		//DynaActionForm SelectFilterForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		session.removeAttribute("selFilter");//移除課程篩選作業的查詢結果
		session.removeAttribute("stuFilter");//移除課程篩選作業的查詢結果

		List Checked = getDtimEditList(request); // 勾到的課程
		List selFilter=new ArrayList(); // 預備裝欲篩選的課程 (直接存取勾到的課程會有問題)********

		Dtime dtime;

		// 循序證明人數(如果有的話...裝入)
		if(Checked.size()>0){
			for(int i=0; i<Checked.size(); i++){
				dtime=(Dtime) Checked.get(i);
				//System.out.print(dtime.getStuSelect()+", ");
				//System.out.println(dtime.getSelectLimit());
				if(manager.ezGetInt("SELECT COUNT(*)FROM Seld WHERE Dtime_oid='"+dtime.getOid()+"'")>dtime.getSelectLimit()){
					selFilter.add(dtime); //裝入課程					
				}				
			}
			String selBuf[]=new String[selFilter.size()]; // 預備裝欲篩選的學生
			for(int i=0; i<selFilter.size(); i++){
				dtime=(Dtime) selFilter.get(i);
				//System.out.println(dtime.getOid());
				selBuf[i]=dtime.getOid().toString();
			}
			session.setAttribute("selFilter", selFilter); //將裝好的課存入
			if(selFilter.size()>0){
				if(selBuf.length>0){
					session.setAttribute("stuFilter", manager.getSeldBy(selBuf));//將學生裝好存入 *************
				}
			}
		}
		setContentPage(request.getSession(false), "course/SelectFilter.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 準備課程篩選作業 - 預備執行
	 */
	public ActionForward courseFilter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息

		HttpSession session = request.getSession(false);
		session.setAttribute("filterMode", "delete");	//模式
		session.setAttribute("filterType", "cou");	//型態
		session.removeAttribute("selFilter");//移除學生篩選作業的查詢結果
		session.removeAttribute("stuFilter");//移除學生篩選作業的查詢結果
		//DynaActionForm SelectFilterForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");

		List Checked = getDtimEditList(request); // 勾到的課程
		List selFilter=new ArrayList(); // 預備裝欲篩選的課程 (直接存取勾到的課程會有問題)

		Dtime dtime;
		// 循序證明人數(如果有的話...裝入)
		if(Checked.size()>0){
			for(int i=0; i<Checked.size(); i++){
				dtime=(Dtime) Checked.get(i);

				selFilter.add(dtime); //裝入課程
			}

			String selBuf[]=new String[selFilter.size()]; // 預備裝欲篩選的學生
			for(int i=0; i<selFilter.size(); i++){
				dtime=(Dtime) selFilter.get(i);
				selBuf[i]=dtime.getOid().toString();
			}
			session.setAttribute("selFilter", selFilter); //將裝好的課存入
			if(selFilter.size()>0){
				if(selBuf.length>0){
					session.setAttribute("stuFilter", manager.getSeldBy(selBuf));//將學生裝好存入
				}
			}
		}

		setContentPage(request.getSession(false), "course/SelectFilter.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 對通識課和體育課處理選課篩選
	 * @param dtime
	 * @param students
	 * @return
	 */
	private Map studentFilter4Opt23(Dtime dtime, List students){
		//CourseManager manager = (CourseManager) getBean("courseManager");
		Map map;
		List luckier=new ArrayList(); //選中者
		
		int n[]=getMath(students.size(), dtime.getSelectLimit());		
		
		for(int i=0; i<n.length; i++){			
			//若學生位置為亂數產位置相同則進入選中名單			
			luckier.add(students.get(n[i]));
			students.set(n[i], "ImGone");
		}
		
		for(int i=0; i<students.size(); i++){
			if(students.get(i).equals("ImGone")){
				students.remove(i);
				i--;
			}
		}
		
		map=new HashMap();
		map.put("table", luckier);
		map.put("chair", students);

		return map;
	}

	/**
	 * 進行篩選 - 確定執行
	 */
	public ActionForward courseFiltration(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		session.setAttribute("filterMode", "");	//模式
		//DynaActionForm SelectFilterForm = (DynaActionForm) form;
		CourseManager manager = (CourseManager) getBean("courseManager");

		List selFilter=(List) session.getAttribute("selFilter"); //課程容器
		//List stuFilter=(List) session.getAttribute("stuFilter"); //學生容器
		//取得現在學期 - 若是下學期則昇級作業
		Dtime dtime;
		//課程篩選作業 - 無條件篩選
		if(session.getAttribute("filterType").equals("cou")){
			int stuTmp=0;
			for(int i=0; i<selFilter.size(); i++){
				dtime=(Dtime) selFilter.get(i);//課程
				String []buf={dtime.getOid().toString()};
				List students=manager.getSeldBy(buf); //學生
				stuTmp=stuTmp+students.size();
				manager.doFilter(students, dtime.getOid().toString(), "D");
			}
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","一共刪除"+selFilter.size()+"門課程, 同時刪除"+stuTmp+"名學生...的選課"));
			saveMessages(request, msg);
		}

		List displayTable=new ArrayList();
		List displayChair=new ArrayList();

		//學生篩選作業 - 優先順序篩選 - 抓到的dtime和seld都是人數過多
		if(session.getAttribute("filterType").equals("stu")){
			//System.out.println("學生篩選作業開始進行...");

			//交出一筆課程和該課程的選課學生********中選
			for(int i=0; i<selFilter.size(); i++){

				List table=new ArrayList(); //中選名單
				List chair=new ArrayList(); //篩選名單

				dtime=(Dtime) selFilter.get(i);//課程
				String []buf={dtime.getOid().toString()};

				String term=manager.getSchoolTerm().toString();

				List students=new ArrayList();
				//第二期需要將學生做模擬昇級作業
				if(term.trim().equals("2")){
					System.out.println("work?");
					students=promotion(manager.getSeldBy(buf), "p"); //學生
				}else{
					students=manager.getSeldBy(buf);
				}
				
				Map getLess;
				//決定是否篩選本班
				//只篩選必修課，若體育為必修也略過
				//又要求不篩體育
				//if(dtime.getOpt().equals("1") && dtime.getChiName2().indexOf("體育")<0 ){	
				//System.out.println(dtime.getOpt());
				//System.out.println(dtime.getChiName2());
				if(dtime.getOpt().equals("1") || dtime.getOpt().equals("2") || dtime.getChiName2().indexOf("體育一")>0 || dtime.getChiName2().indexOf("體育二")>0){
					//System.out.println("走必修");
					getLess=studentFilter(dtime, students); // 走必修				
				}else{
					//System.out.println("走通識");
					getLess=studentFilter4Opt23(dtime, students); // 走通識
				}
				//System.out.println(dtime.getChiName2());
				table.addAll( (List) ((Map)getLess).get("table") );
				chair.addAll( (List) ((Map)getLess).get("chair") );
				//System.out.println("本次中選人數"+table.size());
				//System.out.println("本次篩選人數"+chair.size());
				try{
					manager.doFilter(chair, dtime.getOid().toString(), "F");
				}catch(Exception e){
					//System.out.println(dtime.getChiName2());
					continue;
				}				
				displayTable.addAll( (List) ((Map)getLess).get("table") );
				displayChair.addAll( (List) ((Map)getLess).get("chair") );
			}

			//處理怪怪的錯誤
			for(int i=0; i<displayTable.size(); i++){
				if(displayTable.get(i).equals("ImGone")){
					displayTable.remove(i);
					i--;
				}
			}
			session.setAttribute("table", displayTable);
			session.setAttribute("chair", displayChair);
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","一共篩選 "+selFilter.size()+"門課程, 總計 "+displayChair.size()+"人被篩選"));
			saveMessages(request, msg);
		}
		setContentPage(request.getSession(false), "course/SelectFilter.jsp");
		return mapping.findForward("Main");
	}

	/**
	 * 對必修課程處理選課
	 * @param dtime 一筆課程
	 * @param students 篩選的學生
	 * @return 中選和篩選的名單 - map型態
	 */
	private Map studentFilter(Dtime dtime, List students){
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		Map map;
		List luckier=new ArrayList(); //中選者
		List gradeTmp;//各年級中選暫存
		//處理本班優先
		//判斷本班人數超過的情形
		String DeptNo=manager.ezGetString("SELECT DeptNo FROM Class WHERE ClassNo='"+dtime.getDepartClass()+"'");
		//立即處理低修高 *保證準
		int countIdiots=0;
		List idiots=new ArrayList();
		for(int i=0; i<students.size(); i++){//無條件加入idiots
			int stGrade=Integer.parseInt(((Map)students.get(i)).get("Grade").toString()); //學生年級
			int csGrade=manager.ezGetInt("SELECT Grade FROM Class WHERE ClassNo='"+dtime.getDepartClass()+"'"); //課程年級
			if(stGrade<csGrade){
				idiots.add(students.get(i));
				students.set(i, "ImGone");
				countIdiots++;
			}
		}
		for(int i=0; i<students.size(); i++){
			if(students.get(i).equals("ImGone")){
				students.remove(i);
				i--;
			}
		}

		int origin=0; //本班計數器
		for(int i=0; i<students.size(); i++){
			if(((Map)students.get(i)).get("ClassNo").equals(dtime.getDepartClass())){
				origin++;
			}
		}
		
		if(origin>0){
			// 如果本班(開課班)選課人數多於開課限制人數(課程人數上限不足應付本班人數)
			if(origin>dtime.getSelectLimit()){	
				//扔掉非本班
				if(origin<students.size()){					
					for(int i=0; i<students.size(); i++){						
						if(((Map)students.get(i)).get("ClassNo").equals(dtime.getDepartClass())){
							luckier.add(students.get(i)); //假裝本班全部中選
							students.set(i, "ImGone");
						}
					}
				}else{
					for(int i=0; i<students.size(); i++){						
						luckier.add(students.get(i)); //假裝本班全部中選
						students.set(i, "ImGone");						
					}
				}
				
				for(int i=0; i<students.size(); i++){
					if(students.get(i).equals("ImGone")){
						students.remove(i);
						i--;
					}
				}
				
				
				//有非本班的情況
				int num[]=getMath(luckier.size(), dtime.getSelectLimit());
				/*	for(int i=0; i<num.length; i++){
					//System.out.print(num[i]+", ");
				}	*/
				//用課程人數去篩				
				for(int i=0; i<num.length; i++){
					idiots.add(luckier.get(num[i]));
					luckier.set(num[i], "ImGone"); //放掉					
				}
				/*	for(int i=0; i<luckier.size(); i++){
					System.out.println(luckier.get(i));
				}	*/
				for(int i=0; i<luckier.size(); i++){
					if(luckier.get(i).equals("ImGone")){
						luckier.remove(i);
						i--;
					}
				}
				
				for(int i=0; i<students.size(); i++){
					if(students.get(i).equals("ImGone")){
						students.remove(i);
						i--;
					}
				}
				students.addAll(luckier);
				map=new HashMap();
				map.put("table", idiots);
				map.put("chair", students);
				return map;

			}

			// 如果本班(開課班)選課人數少於開課限制人數(課程人數上限足夠應付本班人數)
			if(origin<=dtime.getSelectLimit()){
				for(int i=0; i<students.size(); i++){
					if(((Map)students.get(i)).get("ClassNo").equals(dtime.getDepartClass())){
						
						luckier.add(students.get(i));
						students.set(i, "ImGone");
					}
				}
				for(int i=0; i<students.size(); i++){
					if(students.get(i).equals("ImGone")){
						students.remove(i);
						i--;
					}
				}
			}

			//開始處理本系優先
			int deptTmp=0;
			for(int i=0; i<students.size(); i++){//測試次數為剩下人數
				if(((Map)students.get(i)).get("DeptNo").toString().equals(DeptNo)){	
					deptTmp++;
				}
			}
			if(deptTmp>0){

				//開始處理本系各年級人數
				Map gradeMap=countGrade(students);
				//4年級
				if( Integer.parseInt(gradeMap.get("four").toString()) <= dtime.getSelectLimit()-luckier.size()){
					//如果本科系人數足夠填補剩下名額
					for(int i=0; i<students.size(); i++){//無條件加入luckier
						if(((Map)students.get(i)).get("DeptNo").toString().equals(DeptNo)){
							if(Integer.parseInt(((Map)students.get(i)).get("Grade").toString())>=4){
								luckier.add(students.get(i));
								students.set(i, "ImGone");
							}
						}
					}
				}else{
					//如果本科系人數不足填補剩下名額則要生出亂數處理本科的人
					int fourNum[]=getMath(Integer.parseInt(gradeMap.get("four").toString()), dtime.getSelectLimit()-luckier.size());
					
					gradeTmp=new ArrayList();
					
					for(int i=0; i<students.size(); i++){
						if(((Map)students.get(i)).get("DeptNo").toString().equals(DeptNo)){
							if(Integer.parseInt(((Map)students.get(i)).get("Grade").toString())>=4){
								gradeTmp.add(students.get(i));
								students.set(i, "ImGone");
							}
						}
					}

					for(int i=0; i<fourNum.length; i++){
						//若學生位置為亂數產位置相同則進入中選
						try{
							luckier.add(students.get(fourNum[i]));
							gradeTmp.set(fourNum[i], "ImGone");
						}catch(Exception e){
							//System.out.println("已略過問題");
						}
						
					}
					//暫存區全部丟回students
					students.addAll(gradeTmp);
					//4年級暫存區至此失效
				}
				//處理students裡的"ImGone"
				for(int i=0; i<students.size(); i++){
					if(students.get(i).equals("ImGone")){
						students.remove(i);
						i--;
					}
				}				
				//3年級
				if( Integer.parseInt(gradeMap.get("three").toString()) <= dtime.getSelectLimit()-luckier.size()){//如果夠
					for(int i=0; i<students.size(); i++){//無條件加入luckier
						if(((Map)students.get(i)).get("DeptNo").toString().equals(DeptNo)){							
							if(((Map)students.get(i)).get("Grade").equals("3")){
								luckier.add(students.get(i));
								students.set(i, "ImGone");
							}
						}
					}
				}else{
					int fourNum[]=getMath(Integer.parseInt(gradeMap.get("three").toString()), dtime.getSelectLimit()-luckier.size());

					gradeTmp=new ArrayList();
					for(int i=0; i<students.size(); i++){
						if(((Map)students.get(i)).get("Grade").equals("3")){
							gradeTmp.add(students.get(i));
							students.set(i, "ImGone");
						}
					}
					
					if(gradeTmp.size()>0)
					for(int i=0; i<fourNum.length; i++){
						//若學生位置為亂數產位置相同則進入中選名單
						luckier.add(students.get(fourNum[i]));
						
						gradeTmp.set(fourNum[i], "ImGone");
					}
					//暫存區全部丟回students
					students.addAll(gradeTmp);
					//3年級暫存區至此失效
				}
				//處理students裡的"ImGone"
				for(int i=0; i<students.size(); i++){
					if(students.get(i).equals("ImGone")){
						students.remove(i);
						i--;
					}
				}
				
				//2年級
				if( Integer.parseInt(gradeMap.get("two").toString()) <= dtime.getSelectLimit()-luckier.size()){//如果夠
					for(int i=0; i<students.size(); i++){//無條件加入luckier
						if(((Map)students.get(i)).get("DeptNo").toString().equals(DeptNo)){	
							if(((Map)students.get(i)).get("Grade").equals("2")){
								luckier.add(students.get(i));
								students.set(i, "ImGone");
							}
						}
					}
				}else{
					int fourNum[]=getMath(Integer.parseInt(gradeMap.get("two").toString()), dtime.getSelectLimit()-luckier.size());

					gradeTmp=new ArrayList();
					//System.out.println(students.size()+", "+fourNum.length);
					for(int i=0; i<students.size(); i++){
						if(((Map)students.get(i)).get("Grade").equals("2")){
							gradeTmp.add(students.get(i));
							students.set(i, "ImGone");
						}
					}
					//System.out.println(gradeTmp.size());
					if(gradeTmp.size()>0)
					for(int i=0; i<fourNum.length; i++){
						//若學生位置為亂數產位置相同則進入中選名單
						luckier.add(students.get(fourNum[i]));
						gradeTmp.set(fourNum[i], "ImGone");
					}
					students.addAll(gradeTmp);
				}
				for(int i=0; i<students.size(); i++){
					if(students.get(i).equals("ImGone")){
						students.remove(i);
						i--;
					}
				}
				
				//1年級
				if( Integer.parseInt(gradeMap.get("one").toString()) <= dtime.getSelectLimit()-luckier.size()){//如果夠
					for(int i=0; i<students.size(); i++){//無條件加入luckier
						if(((Map)students.get(i)).get("DeptNo").toString().equals(DeptNo)){	
							if(((Map)students.get(i)).get("Grade").equals("1")){
								luckier.add(students.get(i));
								students.set(i, "ImGone");
							}
						}
					}
				}else{
					int fourNum[]=getMath(Integer.parseInt(gradeMap.get("one").toString()), dtime.getSelectLimit()-luckier.size());
					gradeTmp=new ArrayList();
					for(int i=0; i<students.size(); i++){
						if(((Map)students.get(i)).get("Grade").equals("1")){
							gradeTmp.add(students.get(i));
							students.set(i, "ImGone");
						}
					}

					for(int i=0; i<fourNum.length; i++){
						//若學生位置為亂數產位置相同則進入中選名單
						luckier.add(students.get(fourNum[i]));
						gradeTmp.set(fourNum[i], "ImGone");
					}
					//暫存區全部丟回students
					students.addAll(gradeTmp);
					//3年級暫存區至此失效
				}
				//處理students裡的"ImGone"
				for(int i=0; i<students.size(); i++){
					if(students.get(i).equals("ImGone")){
						students.remove(i);
						i--;
					}
				}
			}
		}
		//開始處理同部制優先
		if(dtime.getSelectLimit()>luckier.size()){
			int schTmp=0;
			
				for(int i=0; i<students.size(); i++){//測試次數為剩下人數
					if(((Map)students.get(i)).get("ClassNo").toString().subSequence(1, 3).equals(dtime.getDepartClass().subSequence(1, 3))){
						schTmp++;
					}
				}
			if(schTmp>0){
				/*
				//本部人數足夠則全部中選
				if(schTmp<=dtime.getSelectLimit()-luckier.size()){
					for(int i=0; i<students.size(); i++){
						if(((Map)students.get(i)).get("ClassNo").toString().subSequence(1, 3).equals(dtime.getDepartClass().subSequence(1, 3))){
							luckier.add(students.get(i));
							students.set(i, "ImGone");
						}
					}
					for(int i=0; i<students.size(); i++){
						if(students.get(i).equals("ImGone")){
							students.remove(i);
							i--;
						}
					}
					students.addAll(idiots);//高修低
					map=new HashMap();
					map.put("table", luckier);
					map.put("chair", students);

					return map;
				}
				*/
			//如果還有名額
			if(dtime.getSelectLimit()>luckier.size()){

				//本部人數多於剩餘人數時要做年級優先
				//開始處理本系各年級人數
				Map gradeMap=countGrade(students);

				//4年級
				if( Integer.parseInt(gradeMap.get("four").toString()) <= dtime.getSelectLimit()-luckier.size()){//如果夠
					for(int i=0; i<students.size(); i++){//無條件加入luckier
						if(((Map)students.get(i)).get("ClassNo").toString().subSequence(1, 3).equals(dtime.getDepartClass().subSequence(1, 3))){
							
							if(Integer.parseInt(((Map)students.get(i)).get("Grade").toString())>=4){
								luckier.add(students.get(i));
								students.set(i, "ImGone");
							}
						}
					}
				}else{
					int fourNum[]=getMath(Integer.parseInt(gradeMap.get("four").toString()), dtime.getSelectLimit()-luckier.size());

					gradeTmp=new ArrayList();
					for(int i=0; i<students.size(); i++){						
						if(Integer.parseInt(((Map)students.get(i)).get("Grade").toString())>=4){
							gradeTmp.add(students.get(i));
							students.set(i, "ImGone");
						}
					}

					for(int i=0; i<fourNum.length; i++){
						//若學生位置為亂數產位置相同則進入中選名單
						luckier.add(students.get(fourNum[i]));
						gradeTmp.set(fourNum[i], "ImGone");
					}
					//暫存區全部丟回students
					students.addAll(gradeTmp);
					//3年級暫存區至此失效
				}
				//處理students裡的"ImGone"
				for(int i=0; i<students.size(); i++){
					if(students.get(i).equals("ImGone")){
						students.remove(i);
						i--;
					}
				}

				//3年級
				if( Integer.parseInt(gradeMap.get("three").toString()) <= dtime.getSelectLimit()-luckier.size()){//如果夠
					for(int i=0; i<students.size(); i++){//無條件加入luckier
						if(((Map)students.get(i)).get("ClassNo").toString().subSequence(1, 3).equals(dtime.getDepartClass().subSequence(1, 3))){
						
							if(Integer.parseInt(((Map)students.get(i)).get("Grade").toString())==3){
								luckier.add(students.get(i));
								students.set(i, "ImGone");
							}
						}

					}
				}else{
					int fourNum[]=getMath(Integer.parseInt(gradeMap.get("three").toString()), dtime.getSelectLimit()-luckier.size());

					gradeTmp=new ArrayList();
					for(int i=0; i<students.size(); i++){
						if(Integer.parseInt(((Map)students.get(i)).get("Grade").toString())==3){
							gradeTmp.add(students.get(i));
							students.set(i, "ImGone");
						}
					}

					for(int i=0; i<fourNum.length; i++){
						//若學生位置為亂數產位置相同則進入中選名單
						luckier.add(students.get(fourNum[i]));
						gradeTmp.set(fourNum[i], "ImGone");
					}
					//暫存區全部丟回students
					students.addAll(gradeTmp);
					//3年級暫存區至此失效
				}
				//處理students裡的"ImGone"
				for(int i=0; i<students.size(); i++){
					if(students.get(i).equals("ImGone")){
						students.remove(i);
						i--;
					}
				}

				//2年級
				if( Integer.parseInt(gradeMap.get("two").toString()) <= dtime.getSelectLimit()-luckier.size()){//如果夠
					for(int i=0; i<students.size(); i++){//無條件加入luckier
						if(((Map)students.get(i)).get("ClassNo").toString().subSequence(1, 3).equals(dtime.getDepartClass().subSequence(1, 3))){
							
							if(Integer.parseInt(((Map)students.get(i)).get("Grade").toString())==2){
								luckier.add(students.get(i));
								students.set(i, "ImGone");
							}
						}
					}
				}else{
					int fourNum[]=getMath(Integer.parseInt(gradeMap.get("two").toString()), dtime.getSelectLimit()-luckier.size());
					gradeTmp=new ArrayList();
					for(int i=0; i<students.size(); i++){
						
						if(Integer.parseInt(((Map)students.get(i)).get("Grade").toString())==2){
							gradeTmp.add(students.get(i));
							students.set(i, "ImGone");
						}
					}

					for(int i=0; i<fourNum.length; i++){
						//若學生位置為亂數產位置相同則進入中選名單
						luckier.add(students.get(fourNum[i]));
						gradeTmp.set(fourNum[i], "ImGone");
					}
					//暫存區全部丟回students
					students.addAll(gradeTmp);
					//3年級暫存區至此失效
				}
				//處理students裡的"ImGone"
				for(int i=0; i<students.size(); i++){
					if(students.get(i).equals("ImGone")){
						students.remove(i);
						i--;
					}
				}

				//1年級
				if( Integer.parseInt(gradeMap.get("one").toString()) <= dtime.getSelectLimit()-luckier.size()){//如果夠
					for(int i=0; i<students.size(); i++){//無條件加入luckier
						if(((Map)students.get(i)).get("ClassNo").toString().subSequence(1, 3).equals(dtime.getDepartClass().subSequence(1, 3))){
							
							if(Integer.parseInt(((Map)students.get(i)).get("Grade").toString())==1){
								luckier.add(students.get(i));
								students.set(i, "ImGone");
							}
						}
					}
				}else{
					int fourNum[]=getMath(Integer.parseInt(gradeMap.get("one").toString()), dtime.getSelectLimit()-luckier.size());

					gradeTmp=new ArrayList();
					for(int i=0; i<students.size(); i++){
						
						if(Integer.parseInt(((Map)students.get(i)).get("Grade").toString())==1){
							gradeTmp.add(students.get(i));
							students.set(i, "ImGone");
						}
					}

					for(int i=0; i<fourNum.length; i++){
						//若學生位置為亂數產位置相同則進入中選名單
						luckier.add(students.get(fourNum[i]));
						gradeTmp.set(fourNum[i], "ImGone");
					}
					//暫存區全部丟回students
					students.addAll(gradeTmp);
					//3年級暫存區至此失效
				}
				//處理students裡的"ImGone"
				for(int i=0; i<students.size(); i++){
					if(students.get(i).equals("ImGone")){
						students.remove(i);
						i--;
					}
				}
			}
		}
	}

		//開始處理剩下的名額
		if(dtime.getSelectLimit()>luckier.size()){
			Map gradeMap=countGrade(students);

			//4年級
			if( Integer.parseInt(gradeMap.get("four").toString()) <= dtime.getSelectLimit()-luckier.size()){//如果夠

				for(int i=0; i<students.size(); i++){//無條件加入luckier
					
					if(Integer.parseInt(((Map)students.get(i)).get("Grade").toString())>=4){
						luckier.add(students.get(i));
						students.set(i, "ImGone");
					}
				}
			}else{
				int fourNum[]=getMath(Integer.parseInt(gradeMap.get("four").toString()), dtime.getSelectLimit()-luckier.size());
				gradeTmp=new ArrayList();
				for(int i=0; i<students.size(); i++){
					if(Integer.parseInt(((Map)students.get(i)).get("Grade").toString())>=4){
						gradeTmp.add(students.get(i));
						students.set(i, "ImGone");
					}
				}

				for(int i=0; i<fourNum.length; i++){
					//若學生位置為亂數產位置相同則進入中選名單
					luckier.add(students.get(fourNum[i]));
					gradeTmp.set(fourNum[i], "ImGone");
				}
				//暫存區全部丟回students
				students.addAll(gradeTmp);
				//3年級暫存區至此失效
			}
			//處理students裡的"ImGone"
			for(int i=0; i<students.size(); i++){
				if(students.get(i).equals("ImGone")){
					students.remove(i);
					i--;
				}
			}

			//3年級
			if( Integer.parseInt(gradeMap.get("three").toString()) <= dtime.getSelectLimit()-luckier.size()){//如果夠
				for(int i=0; i<students.size(); i++){//無條件加入luckier
					if(Integer.parseInt(((Map)students.get(i)).get("Grade").toString())==3){
						luckier.add(students.get(i));
						students.set(i, "ImGone");
					}
				}
			}else{
				int fourNum[]=getMath(Integer.parseInt(gradeMap.get("three").toString()), dtime.getSelectLimit()-luckier.size());
				gradeTmp=new ArrayList();
				for(int i=0; i<students.size(); i++){
					if(Integer.parseInt(((Map)students.get(i)).get("Grade").toString())==3){
						gradeTmp.add(students.get(i));
						students.set(i, "ImGone");
					}
				}

				for(int i=0; i<fourNum.length; i++){
					//若學生位置為亂數產位置相同則進入中選名單
					luckier.add(students.get(fourNum[i]));
					gradeTmp.set(fourNum[i], "ImGone");
				}
				//暫存區全部丟回students
				students.addAll(gradeTmp);
				//3年級暫存區至此失效
			}
			//處理students裡的"ImGone"
			for(int i=0; i<students.size(); i++){
				if(students.get(i).equals("ImGone")){
					students.remove(i);
					i--;
				}
			}

			//2年級
			if( Integer.parseInt(gradeMap.get("two").toString()) <= dtime.getSelectLimit()-luckier.size()){//如果夠
				for(int i=0; i<students.size(); i++){//無條件加入luckier
					if(Integer.parseInt(((Map)students.get(i)).get("Grade").toString())==2){
						luckier.add(students.get(i));
						students.set(i, "ImGone");
					}
				}
			}else{
				int fourNum[]=getMath(Integer.parseInt(gradeMap.get("two").toString()), dtime.getSelectLimit()-luckier.size());
				gradeTmp=new ArrayList();
				for(int i=0; i<students.size(); i++){
					if(Integer.parseInt(((Map)students.get(i)).get("Grade").toString())==2){
						gradeTmp.add(students.get(i));
						students.set(i, "ImGone");
					}
				}

				for(int i=0; i<fourNum.length; i++){
					//若學生位置為亂數產位置相同則進入中選名單
					luckier.add(students.get(fourNum[i]));
					gradeTmp.set(fourNum[i], "ImGone");
				}
				//暫存區全部丟回students
				students.addAll(gradeTmp);
				//3年級暫存區至此失效
			}
			//處理students裡的"ImGone"
			for(int i=0; i<students.size(); i++){
				if(students.get(i).equals("ImGone")){
					students.remove(i);
					i--;
				}
			}

			//1年級
			if( Integer.parseInt(gradeMap.get("one").toString()) <= dtime.getSelectLimit()-luckier.size()){//如果夠
				for(int i=0; i<students.size(); i++){//無條件加入luckier
					if(Integer.parseInt(((Map)students.get(i)).get("Grade").toString())==1){
						luckier.add(students.get(i));
						students.set(i, "ImGone");
					}
				}
			}else{
				int fourNum[]=getMath(Integer.parseInt(gradeMap.get("one").toString()), dtime.getSelectLimit()-luckier.size());
				gradeTmp=new ArrayList();
				for(int i=0; i<students.size(); i++){
					if(Integer.parseInt(((Map)students.get(i)).get("Grade").toString())==1){
						gradeTmp.add(students.get(i));
						students.set(i, "ImGone");
					}
				}

				for(int i=0; i<fourNum.length; i++){
					//若學生位置為亂數產位置相同則進入中選名單
					luckier.add(students.get(fourNum[i]));
					gradeTmp.set(fourNum[i], "ImGone");
				}
				//暫存區全部丟回students
				students.addAll(gradeTmp);
				//3年級暫存區至此失效
			}
			//處理students裡的"ImGone"
			for(int i=0; i<students.size(); i++){
				if(students.get(i).equals("ImGone")){
					students.remove(i);
					i--;
				}
			}
		}

		students.addAll(idiots);//高修低
		map=new HashMap();
		map.put("table", luckier);
		map.put("chair", students);

		return map;
	}

	/**
	 * 昇/降級
	 * @param list
	 * @return
	 */
	private List promotion(List<Map>list, String type){
		/*
		if(type.trim().equals("p")){
			System.out.println("昇級進行中...");
		}else{
			System.out.println("降級進行中...");
		}
		*/
		/*for(int i=0; i<list.size(); i++){
			String grade=new String(((Map)list.get(i)).get("ClassNo").toString());
			StringBuffer gradeBuf=new StringBuffer(grade);

			Integer newGrade;
			if(type.equals("d")){
				if(grade.length()==7){
					newGrade=Integer.parseInt(grade.substring(5, 6).toString())-1;
				}else{
					newGrade=Integer.parseInt(grade.substring(4, 5).toString())-1;
				}
			}else{
				if(grade.length()==7){
					newGrade=Integer.parseInt(grade.substring(5, 6).toString())+1;
				}else{
					newGrade=Integer.parseInt(grade.substring(4, 5).toString())+1;
				}
			}

			gradeBuf.setCharAt(4, newGrade.toString().charAt(0));
			((Map)list.get(i)).put("ClassNo", gradeBuf.toString());

		}*/
		CourseManager manager = (CourseManager) getBean("courseManager");
		if(type.equals("d")){
			
			for(int i=0; i<list.size(); i++){			
				list.get(i).put("Grade", Integer.parseInt(list.get(i).get("Grade").toString())-1);
			}
		}else{
			for(int i=0; i<list.size(); i++){	
				Clazz c=(Clazz) manager.hqlGetBy("FROM Clazz WHERE ClassNo='"+list.get(i).get("ClassNo")+"'").get(0);
				list.get(i).put("Grade", Integer.parseInt(c.getGrade())+1);
				try{
					list.get(i).put("ClassNo", manager.ezGetString("SELECT ClassNo FROM Class WHERE CampusNo='"+
					c.getCampusNo()+"'AND SchoolNo='"+c.getSchoolNo()+"'AND DeptNo='"+
					c.getDeptNo()+"'AND Grade='"+(Integer.parseInt(c.getGrade())+1)+"'AND SeqNo='"+c.getSeqNo()+"'"));
				}catch(Exception e){
					//
				}
			}
		}
		
		return list;
	}

	/**
	 * 抓一個陣列中各年級的人數
	 * @param tortoise
	 * @return 一門課各年級的選課人數
	 */
	private Map countGrade(List tortoise){

		int one=0;
		int two=0;
		int three=0;
		int four=0;
		Map map=new HashMap();;

		for(int i=0; i<tortoise.size(); i++){
			
			if(Integer.parseInt(((Map)tortoise.get(i)).get("Grade").toString())>=4){
				four++;
			}
			if(Integer.parseInt(((Map)tortoise.get(i)).get("Grade").toString())==3){
				three++;
			}
			if(Integer.parseInt(((Map)tortoise.get(i)).get("Grade").toString())==2){
				two++;
			}
			if(Integer.parseInt(((Map)tortoise.get(i)).get("Grade").toString())==1){
				one++;
			}

			map.put("one", one);
			map.put("two", two);
			map.put("three", three);
			map.put("four", four);


		}
		return map;
	}

	/**
	 * 產生'不重複亂數'的方法
	 * @param studentLess 剩下人數
	 * @param seldLess 剩下名額
	 * @return 亂數陣列
	 */
	private int[] getMath(int studentLess, int seldLess){
		int num[]=new int[seldLess]	; //亂數陣列
		for(int i=0; i<seldLess; i++) {
	    	int n=(int)(Math.random()*studentLess); //亂數範圍應該是剩下的學生數目

	    	num[i]=n;

	    	for(int j=0; j<i; j++){
	    		if(num[i]==num[j]){
	    			i--;    			
	    		}
	    	}
		}
		return num;
	}
	
	//寄送通知
	public ActionForward send(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Toolket.resetCheckboxCookie(response, "selectFilter");
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		List list=(List)session.getAttribute("selectFilterCouList");
		
		InternetAddress addr;
		InternetAddress address[]=new InternetAddress[1];
		
		Map map;
		String content;
		List filter;
		int con=0;
		for(int i=0; i<list.size(); i++){			
			filter=manager.ezGetBy("SELECT student_no FROM Seld_stuFilter WHERE Dtime_oid='"+((Map)list.get(i)).get("dtimeOid")+"'");			
			for(int j=0; j<filter.size(); j++){				
				map=manager.ezGetMap("SELECT student_name, Email FROM stmd WHERE student_no='"+
						((Map)filter.get(j)).get("student_no")+"' AND Email IS NOT NULL AND Email!=''");
				if(map!=null){
					try{
						//content=map.get("student_name")+"同學您好<br><br>您所選的課程中，"+((Map)list.get(i)).get("chiName2")+"已超過人數上限。請在下一階段選課重新選擇課程，謝謝！";
						addr=new InternetAddress(map.get("Email").toString(), map.get("student_name").toString(), "BIG5");
						//addr=new InternetAddress("hsiao@cc.cust.edu.tw", "蕭國裕", "BIG5");
						address[0]=addr;
						manager.sendMail("CIS", "chit!@#", "www.cust.edu.tw", "CIS@www.cust.edu.tw", "中華科技大學校務資訊系統", new Date(), "選課篩選通知", 
						map.get("student_name")+"同學您好<br><br>您所選的課程中，"+((Map)list.get(i)).get("chiName2")+"已超過人數上限。請在下一階段選課重新選擇課程，謝謝！", 
						address, null);
						con=con+1;
					}catch(Exception e){e.printStackTrace();}
				}
			}
		}
		
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","已寄送"+con+"位學生"));
		saveMessages(request, msg);
		return mapping.findForward("Main");
	}

	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("CourseFilterSearch", "courseFilterSearch");
		map.put("Clear", "clear");
		map.put("StudentFilter", "studentFilter");
		map.put("CourseFilter", "courseFilter");
		map.put("CourseFiltration", "courseFiltration");
		map.put("Cancel", "cancel");
		map.put("CourseFilterList", "courseFilterList");
		map.put("CourseFilterComeback", "courseFilterComeback");
		map.put("SendMail", "send");

		return map;
	}

}