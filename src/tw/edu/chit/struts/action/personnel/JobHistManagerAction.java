package tw.edu.chit.struts.action.personnel;

import java.text.SimpleDateFormat;
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
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.DEmpl;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.EmplSave;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class JobHistManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward editJobHist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(false);
		DynaActionForm emplForm=(DynaActionForm)form;
		CourseManager manager = (CourseManager) getBean("courseManager");
		//修改歷史記錄
		String saveOid[]=emplForm.getStrings("saveOid");
		String saveCategory[]=emplForm.getStrings("saveCategory");
		String saveUnit[]=emplForm.getStrings("saveUnit");
		String savePcode[]=emplForm.getStrings("savePcode");
		String saveDirector[]=emplForm.getStrings("saveDirector");
		String saveSname[]=emplForm.getStrings("saveSname");
		String saveTutor[]=emplForm.getStrings("saveTutor");
		String saveDegree[]=emplForm.getStrings("saveDegree");
		String saveStartDate[]=emplForm.getStrings("saveStartDate");
		String saveEndDate[]=emplForm.getStrings("saveEndDate");
		String saveRemark[]=emplForm.getStrings("saveRemark");
		String saveStatus[]=emplForm.getStrings("saveStatus");
		String idno=emplForm.getString("idno");
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i=0; i<saveOid.length; i++){
					
			if(!saveOid[i].equals("")){//若有oid便更新舊有
				if(!saveStartDate[i].equals("")&&!saveEndDate[i].equals("")){//若期間不為空白則儲存記錄
					
					EmplSave es=(EmplSave) manager.hqlGetBy("FROM EmplSave WHERE Oid='"+saveOid[i]+"'").get(0);
					es.setCategory(saveCategory[i]);
					es.setUnit(saveUnit[i]);
					es.setPcode(savePcode[i]);
					es.setDirector(saveDirector[i]);
					es.setSname(saveSname[i]);
					es.setTutor(saveTutor[i]);
					es.setDegree(saveDegree[i]);
					es.setRemark(saveRemark[i]);
					es.setStatus(saveStatus[i]);
					es.setStartDate(sf.parse(manager.convertDate(saveStartDate[i])));
					es.setEndDate(sf.parse(manager.convertDate(saveEndDate[i])));
					
					manager.updateObject(es);
					
				}else{//若期間為空白則刪除舊有記錄
					manager.executeSql("DELETE FROM Empl_save WHERE Oid='"+saveOid[i]+"'");
				}
			}else{
				if(!saveStartDate[i].equals("")&&!saveEndDate[i].equals("")){//若期間不為空白則儲存記錄
					
					Object o;
					try{
						o=(Empl)manager.hqlGetBy("FROM Empl WHERE idno='"+idno+"'").get(0);
					}catch(Exception e){
						o=(DEmpl)manager.hqlGetBy("FROM DEmpl WHERE idno='"+idno+"'").get(0);
					}					
					EmplSave es=getEmplSave(o);
					es.setIdno(idno);
					es.setCategory(saveCategory[i]);
					es.setUnit(saveUnit[i]);
					es.setPcode(savePcode[i]);
					es.setDirector(saveDirector[i]);
					es.setSname(saveSname[i]);
					es.setTutor(saveTutor[i]);
					es.setDegree(saveDegree[i]);
					es.setRemark(saveRemark[i]);					
					es.setStartDate(sf.parse(manager.convertDate(saveStartDate[i])));
					es.setEndDate(sf.parse(manager.convertDate(saveEndDate[i])));
					
					manager.updateObject(es);
				}				
			}			
		}
		
		Map map=new HashMap();
		map.put("fsidno", idno);
		map.put("fscname", "%");
		map.put("exSearch", "");
		
		List aEmplSave=manager.getEmpl(map, "Empl_save", map.get("exSearch").toString());
		//轉成民國年
		for(int i=0; i<aEmplSave.size(); i++){
			manager.getADorCD(((Map)aEmplSave.get(i)));
		}
		session.setAttribute("aEmplSave", aEmplSave);
		return mapping.findForward("Main");
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("EditJobHist", "editJobHist");
		return map;
	}
	
	/**
	 * 變1個empl_save出來
	 * @param obj Empl or DEmpl...
	 * @return
	 */
	private EmplSave getEmplSave(Object obj){
		CourseManager manager = (CourseManager) getBean("courseManager");
		EmplSave emplsave=new EmplSave();
		//Object aEmpl;
		if(obj.getClass().equals(Empl.class)){
			
			Empl empl=(Empl)manager.hqlGetBy("FROM Empl WHERE Oid='"+((Empl)obj).getOid()+"'").get(0);
			
			emplsave.setBdate(empl.getBdate());
			emplsave.setCaddr(empl.getCaddr());
			emplsave.setCategory(empl.getCategory());
			emplsave.setCellPhone(empl.getCellPhone());
			emplsave.setCname(empl.getCname());
			emplsave.setCzip(empl.getCzip());
			emplsave.setDegree(empl.getDegree());
			emplsave.setDirector(empl.getDirector());
			emplsave.setEmail(empl.getEmail());
			emplsave.setEname(empl.getEname());
			emplsave.setEndDate(empl.getEndDate());
			emplsave.setIdno(empl.getIdno());
			emplsave.setInsno(empl.getInsno());
			emplsave.setPaddr(empl.getPaddr());
			emplsave.setPcode(empl.getPcode());
			emplsave.setPzip(empl.getPzip());
			emplsave.setRemark(empl.getRemark());
			emplsave.setSex(empl.getSex());
			emplsave.setSname(empl.getSname());
			emplsave.setStartDate(empl.getStartDate());
			emplsave.setStatus(empl.getStatus());
			emplsave.setStatusCause(empl.getStatusCause());
			emplsave.setTeachStartDate(empl.getTeachStartDate());
			emplsave.setTelephone(empl.getTelephone());
			emplsave.setTutor(empl.getTutor());
			emplsave.setUnit(empl.getUnit());
			emplsave.setAdate(empl.getAdate());
			
		}else{
			DEmpl empl=(DEmpl)manager.hqlGetBy("FROM DEmpl WHERE Oid='"+((DEmpl)obj).getOid()+"'").get(0);
			
			emplsave.setBdate(empl.getBdate());
			emplsave.setCaddr(empl.getCaddr());
			emplsave.setCategory(empl.getCategory());
			emplsave.setCellPhone(empl.getCellPhone());
			emplsave.setCname(empl.getCname());
			emplsave.setCzip(empl.getCzip());
			emplsave.setDegree(empl.getDegree());
			emplsave.setDirector(empl.getDirector());
			emplsave.setEmail(empl.getEmail());
			emplsave.setEname(empl.getEname());
			emplsave.setEndDate(empl.getEndDate());
			emplsave.setIdno(empl.getIdno());
			emplsave.setInsno(empl.getInsno());
			emplsave.setPaddr(empl.getPaddr());
			emplsave.setPcode(empl.getPcode());
			emplsave.setPzip(empl.getPzip());
			emplsave.setRemark(empl.getRemark());
			emplsave.setSex(empl.getSex());
			emplsave.setSname(empl.getSname());
			emplsave.setStartDate(empl.getStartDate());
			emplsave.setStatus(empl.getStatus());
			emplsave.setStatusCause(empl.getStatusCause());
			emplsave.setTeachStartDate(empl.getTeachStartDate());
			emplsave.setTelephone(empl.getTelephone());
			emplsave.setTutor(empl.getTutor());
			emplsave.setAdate(empl.getAdate());
		}
		
		return emplsave;
	}

}
