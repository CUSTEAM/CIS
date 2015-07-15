package tw.edu.chit.struts.action.course;

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

import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.Opencs;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class OpenCourseBatchServlet extends BaseLookupDispatchAction{

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);

		ActionMessages msg = new ActionMessages();		//建立共用訊息
		ActionMessages error = new ActionMessages();	//建立共用錯誤訊息

		List dtimeBatchList=(List)session.getAttribute("dtimeBatchList"); //取得剛得到的加強型dtimeList
		Object obj[]=dtimeBatchList.toArray();
		String dtimeOid[] = new String[dtimeBatchList.size()];


		for(int i=0; i<obj.length; i++){

			dtimeOid[i]=(String) ((Map)obj[i]).get("oid").toString(); //取得一筆的oid

			// 當發現 跨選規則有更動即動作
			if(request.getParameter("checkCross"+dtimeOid[i]).trim().equals("1")){
				manager.RemoveOpencsBy(dtimeOid[i]);

				String cidno[]=request.getParameterValues("cidno"+dtimeOid[i]); //設一個班級容器名為oid
				String sidno[]=request.getParameterValues("sidno"+dtimeOid[i]); //設一個班級容器名為oid
				String didno[]=request.getParameterValues("didno"+dtimeOid[i]); //設一個班級容器名為oid
				String grade[]=request.getParameterValues("grade"+dtimeOid[i]); //設一個班級容器名為oid
				String departClass[]=request.getParameterValues("departClass"+dtimeOid[i]); //設一個班級容器名為oid

				for (int j=0; j<departClass.length; j++){
					if(!cidno[j].equals("")|| !sidno[j].equals("")|| !didno[j].equals("")|| !grade[j].equals("")|| !departClass[j].equals("")){ //以校區為準若有值則開始更新
						Opencs opencs=new Opencs();
						opencs.setDtimeOid(Integer.parseInt(dtimeOid[i]));
						opencs.setCidno(cidno[j]);
						opencs.setSidno(sidno[j]);
						opencs.setDidno(didno[j]);
						opencs.setGrade(grade[j]);
						opencs.setClassNo(departClass[j]);
						opencs.setDepartClass("");
						manager.saveOpencsBy(opencs);

					}

				}
			}

			//當發現基本資料有更動即動作
			if(request.getParameter("checkBasic"+dtimeOid[i]).trim().equals("1")){
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","checkBasic"+dtimeOid[i]+"欄位有更動"));
				saveMessages(request, msg);

				Dtime dtime = (Dtime) manager.getDtimeByHql(dtimeOid[i]).get(0);

				dtime.setOpt(request.getParameter("choseType"+dtimeOid[i]).trim()); //選別
				dtime.setOpen(Byte.parseByte(request.getParameter("open"+dtimeOid[i]).trim())); // 開放
				dtime.setElearning(request.getParameter("elearning"+dtimeOid[i]).trim()); // 遠距
				dtime.setThour(Short.parseShort(request.getParameter("thour"+dtimeOid[i]).trim())); //時數
				dtime.setCredit(Float.parseFloat(request.getParameter("credit"+dtimeOid[i]).trim())); //學分
				dtime.setSelectLimit(Short.parseShort(request.getParameter("selectLimit"+dtimeOid[i]).trim())); //上限
				dtime.setExtrapay(request.getParameter("extrapay"+dtimeOid[i]).trim()); //電腦實習費

				manager.updateDtime(dtime);

			}

			//當發現教師有更動即動作
			if(request.getParameter("checkTecher"+dtimeOid[i]).trim().equals("1")){
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","checkTecher"+dtimeOid[i]+"欄位有更動"));
				saveMessages(request, msg);

			}

			//當發現排課有更動即動作
			if(request.getParameter("checkClass"+dtimeOid[i]).trim().equals("1")){
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","checkClass"+dtimeOid[i]+"欄位有更動"));
				saveMessages(request, msg);

			}

			//當發現排考有更動即動作
			if(request.getParameter("checkExam"+dtimeOid[i]).trim().equals("1")){
				msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","checkExam"+dtimeOid[i]+"欄位有更動"));
				saveMessages(request, msg);

			}
		}
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","開課作業批次維護完成!"));
		saveMessages(request, msg);
		saveErrors(request, error);

		String cscode = request.getParameter("courseNumber");
		String techid = request.getParameter("teacherId");
		String term = request.getParameter("sterm");
		String choseType = request.getParameter("choseType");
		String open = request.getParameter("open");
		String elearning = request.getParameter("elearning");
		String classLess = request.getParameter("classLess");
		System.out.println("classLess="+classLess+cscode+techid+term);
		UserCredential credential = (UserCredential) session.getAttribute("Credential");
		Clazz[] classes = credential.getClassInChargeAry();

		if(techid.length()<2){
			techid="";
		}
		if(cscode.length()<2){
			cscode="";
		}
		if(classLess.equals("")){
			classLess="%";
		}


		//呼叫搜尋
		session.setAttribute("dtimeBatchList", manager.getDtimForBatch(
				classes, cscode, techid, term, choseType, open, elearning, classLess));



		setContentPage(request.getSession(false), "course/OpenCourseBatch.jsp");
		return mapping.findForward("Main");
	}

	@Override
	protected Map getKeyMethodMap() {

		return null;
	}
}
