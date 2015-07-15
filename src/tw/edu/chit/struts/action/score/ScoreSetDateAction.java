package tw.edu.chit.struts.action.score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.dao.ScoreDAO;
import tw.edu.chit.dao.StudAffairDAO;
import tw.edu.chit.model.Code1;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.Keep;
import tw.edu.chit.model.Optime1;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class ScoreSetDateAction extends BaseLookupDispatchAction {
	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Create","Add");
		//map.put("Query","Query");
		map.put("Delete","Delete");
		map.put("DeleteConfirm", "DelConfirm");
		map.put("Cancel", "cancel");
		map.put("Modify","Modify");
		return map;		
	}
	
	public static final String Level_Score = "0";
	public static final String Level_MidleScoreUpload = "1";
	public static final String Level_FinalScoreUpload = "2";
	public static final String Level_EducatedScoreUpload = "3";
	public static final String Level_SummerScoreUpload = "4";
	public static final String Level_ConductUpload = "5";
	public static final String Level_CoanswUpload = "6";
	
	
	/**
	 * @comment Action預設之執行方法
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm dynForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);		
		ScoreManager sm = (ScoreManager)getBean("scoreManager");

		String level = dynForm.getString("level");
		String[] levelName = {"期中", "期末", "畢業", "暑修", "操行", "教學評量"};
		List<Optime1> optimes = new ArrayList<Optime1>();
		if(level.trim().equals("")||level.trim().equals(Level_Score)) {
			optimes = sm.findOptime(Level_Score);
		}else{
			optimes = sm.findOptime(level);
		}
		setOptimeEctData(optimes);
		//log.debug("optimes size:" + optimes.size());
		session.setAttribute("ScoreSetDateLevel", level);
		session.setAttribute("ScoreSetDate", optimes);
		setContentPage(session, "score/ScoreSetDate.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward Add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		DynaActionForm dynForm = (DynaActionForm)form;
		String level = dynForm.getString("level");
		List<Code5> setDateLevels = new ArrayList();
		
		ScoreManager sm = (ScoreManager)getBean("scoreManager");
		if(level.trim().equals("")||level.trim().equals(Level_Score)) {
			setDateLevels = sm.findSetDateLevel("0");
		}else{
			setDateLevels = sm.findSetDateLevel(level);			
		}
		List<Code5> c5List = sm.findCampusDepartment();
		
		session.setAttribute("setDateLevel", setDateLevels);
		session.setAttribute("Department", c5List);
		session.removeAttribute("ScoreSetDateEdit");
		setContentPage(session, "score/ScoreSetDateAdd.jsp");
		return mapping.findForward("Main");

	}
	
	/*
	public ActionForward Query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		//Toolket.resetCheckboxCookie(response, "StudConduct");
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		
		String studentNo  = aForm.getString("studentNo");
		String studentName  = aForm.getString("studentName");
		String clazz  = aForm.getString("clazz");
		String downYear  = aForm.getString("downYear");
		
		Map stuMap = new HashMap();
		stuMap.put("studentNo", studentNo);
		
		ScoreManager scm = (ScoreManager)getBean("scoreManager");
		StudAffairManager sm = (StudAffairManager)getBean("studAffairManager");
		ActionMessages msgs = new ActionMessages();

		if(studentNo.trim().equals("") && downYear.trim().equals("") &&
				studentName.trim().equals("") && clazz.trim().equals("")){
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Message.MustInput", "學號or學生姓名or班級or定察學年"));
			this.saveMessages(request, msgs);
			setContentPage(session, "studaffair/StudInspected.jsp");
			return mapping.findForward("Main");

		}
			
		List<Keep> keepList= sm.findStudInspectedByForm(aForm.getMap());
		
			
		session.setAttribute("StudInspectedList", keepList);
 		setContentPage(session, "studaffair/StudInspected.jsp");

		return mapping.findForward("Main");
	}
	*/

	public ActionForward Delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		
		List<Optime1> optimes = (List<Optime1>)getOptimeSelectedList(request);	//Records selected for delete
		if(optimes.isEmpty()){
	 		setContentPage(session, "score/ScoreSetDate.jsp");
	 		return mapping.findForward("Main");
		}
		
		session.setAttribute("ScoreSetDateDelete", optimes);
		setContentPage(session, "score/ScoreSetDateDelete.jsp");
		return new ActionForward(mapping.findForward("Main").getPath(), true);
		
	}
	
	public ActionForward DelConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		List<Optime1> optimes = (List<Optime1>)session.getAttribute("ScoreSetDateDelete");
		ScoreManager sm = (ScoreManager)getBean("scoreManager");
		ActionMessages errs = sm.delOptime(optimes, Toolket.getBundle(request, "messages.score"));
		session.removeAttribute("ScoreSetDateDelete");
		// no undeleteScores will happen even if delete failure
		if (errs.isEmpty()) {
			session.removeAttribute("ScoreSetDate");
			optimes = sm.findOptime(Level_Score);
			session.setAttribute("ScoreSetDate", optimes);
			errs.add(ActionMessages.GLOBAL_MESSAGE, 
							new ActionMessage("Message.DeleteSuccessful"));
			saveMessages(request, errs);
			setContentPage(session, "score/ScoreSetDate.jsp");
			return mapping.findForward("Main");
		} else {
			saveErrors(request, errs);
			setContentPage(session, "score/ScoreSetDate.jsp");
			return mapping.findForward("Main");
		}
		
	}
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		request.getSession(false).removeAttribute("ScoreSetDateDelete");
		setContentPage(request.getSession(false), "score/ScoreSetDate.jsp");
		return mapping.findForward("Main");
	}

	
	public ActionForward Modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		DynaActionForm aForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		List<Optime1> optimes = (List<Optime1>)getOptimeSelectedList(request);
		setOptimeEctData(optimes);
		
		session.removeAttribute("ScoreSetDateModify");
		
		session.setAttribute("ScoreSetDateInEdit", optimes);
		setContentPage(session, "score/ScoreSetDateModify.jsp");
		return mapping.findForward("Main");

	}

	
	//Private Method Here ============================>>
	private List getOptimeSelectedList(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		String oids = Toolket.getSelectedIndexFromCookie(request, "ScoreSetDate");
		List<Optime1> optimes = (List<Optime1>)session.getAttribute("ScoreSetDate");
		List<Optime1> selOptimes = new ArrayList<Optime1>();
		Optime1 optime;
		
		for (Iterator<Optime1> optimeIter = optimes.iterator(); optimeIter.hasNext();) {
			optime = optimeIter.next();
			if (Toolket.isValueInCookie(optime.getOid().toString(), oids)) {
				selOptimes.add(optime);
			}
		}
		return selOptimes;
	}
	

	private void setOptimeEctData(List<Optime1> optimes){
		ScoreDAO dao = (ScoreDAO)getBean("scoreDAO");
		String[] levelName = {"期中", "期末", "畢業", "暑修", "操行", "教學評量"};
		String hql = "FROM Code5 c Where c.category='SchoolType' Group by name order by name";
		List<Code5> tmpList = dao.submitQuery(hql);
		if(!optimes.isEmpty()){
			for(Optime1 times:optimes){
				for(Code5 code:tmpList){
					if(times.getDepart().trim().equals(code.getIdno())){
						times.setDepartName(code.getName().substring(2));
						break;
					}
				}
				times.setLevelName(levelName[Integer.parseInt(times.getLevel())-1]);
			}
		}
		return;
	}
}
