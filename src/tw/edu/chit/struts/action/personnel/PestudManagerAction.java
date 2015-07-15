package tw.edu.chit.struts.action.personnel;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.EmplContract;
import tw.edu.chit.model.EmplGrdschl;
import tw.edu.chit.model.EmplPehist;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

/**
 * 實務經驗管理
 * @author JOHN
 *
 */
public class PestudManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward editPeist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");	
		DynaActionForm cForm = (DynaActionForm) form;
		
		String school_year[]=cForm.getStrings("school_year");
		String school_term[]=cForm.getStrings("school_term");
		String idno[]=cForm.getStrings("idno");
		String inst[]=cForm.getStrings("inst");
		String bdate_certname[]=cForm.getStrings("bdate_certname");
		String edate_certno[]=cForm.getStrings("edate_certno");
		String begin[]=cForm.getStrings("begin");
		String end[]=cForm.getStrings("end");
		String deptNo[]=cForm.getStrings("deptNo");
		String Oid[]=cForm.getStrings("Oid");
		
		String aIdno=cForm.getString("aIdno");		
		
		for(int i=0; i<Oid.length; i++){
			if(Oid[i].equals("")){//新增的情況
				
				if(!school_term.equals("")&&!school_year[i].equals("")){//期間不完整不記錄
					EmplPehist ep=new EmplPehist();
					ep.setBdateCertname(bdate_certname[i]);
					ep.setBegin(manager.getADtoAD(manager.convertDate(begin[i])));
					ep.setDeptNo(deptNo[i]);
					ep.setEdateCertno(edate_certno[i]);
					ep.setEnd(manager.getADtoAD(manager.convertDate(end[i])));
					ep.setIdno(aIdno);
					ep.setInst(inst[i]);
					ep.setSchoolTerm(Short.parseShort(school_term[i]));
					ep.setSchoolYear(Short.parseShort(school_year[i]));
					
					manager.updateObject(ep);
				}
				
			}else{//修改刪除的情況
				
				if(school_term.equals("")||school_year[i].equals("")){//期間不完整立即刪除
					manager.executeSql("DELETE FROM Empl_pehist WHERE Oid="+Oid[i]);
				}else{//更新
					EmplPehist ep=(EmplPehist)manager.hqlGetBy("FROM EmplPehist WHERE Oid="+Oid[i]).get(0);
					ep.setBdateCertname(bdate_certname[i]);
					
					ep.setDeptNo(deptNo[i]);
					ep.setEdateCertno(edate_certno[i]);					
					try{
						ep.setBegin(manager.getADtoAD(manager.convertDate(begin[i])));
						ep.setEnd(manager.getADtoAD(manager.convertDate(end[i])));
					}catch(Exception e){
						
					}
					ep.setIdno(aIdno);
					ep.setInst(inst[i]);
					ep.setSchoolTerm(Short.parseShort(school_term[i]));
					ep.setSchoolYear(Short.parseShort(school_year[i]));
					
					manager.updateObject(ep);
				}
				
			}
		}
		
		
		

		//實務
		List pehists=manager.ezGetBy("SELECT * FROM Empl_pehist WHERE idno='"+aIdno+"'");
		for(int i=0; i<pehists.size(); i++){
			try{
				((Map)pehists.get(i)).put("begin", manager.convertDate(((Map)pehists.get(i)).get("begin").toString()));
				((Map)pehists.get(i)).put("end", manager.convertDate(((Map)pehists.get(i)).get("end").toString()));
			}catch(Exception e){
				
			}			
		}
		session.setAttribute("pehists", pehists);
		
		return mapping.findForward("Main");
	}
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("EditPeist", "editPeist");
		return map;
	}

}
