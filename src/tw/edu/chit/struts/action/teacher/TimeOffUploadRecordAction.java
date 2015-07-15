package tw.edu.chit.struts.action.teacher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import tw.edu.chit.dao.ScoreDAO;
import tw.edu.chit.dao.StudAffairDAO;
import tw.edu.chit.model.TimeOffUpload;
import tw.edu.chit.model.Dilg;
import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.DtimeClass;
import tw.edu.chit.model.domain.Dtimes;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.ScoreManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;
import tw.edu.chit.util.Global;

public class TimeOffUploadRecordAction  extends BaseLookupDispatchAction {

	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("PreviewPrint", "print");
		map.put("Back", "cancel");
		return map;
	}
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm dynForm = (DynaActionForm)form;
		HttpSession session = request.getSession(false);
		
		UserCredential credential = (UserCredential)session.getAttribute("Credential");
		String teach_id = credential.getMember().getIdno().trim();
		
		Map TimeoffInit = new HashMap();

		List<Dtimes> dtimelist = new ArrayList();
		Map dtimeMap = new HashMap();
		
		List cscodelist = new ArrayList();
		List dtimes = new ArrayList();
		
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		Calendar tfdate = Calendar.getInstance();
		tfdate.set(Calendar.HOUR_OF_DAY, 0);
		tfdate.set(Calendar.MINUTE, 0);
		tfdate.set(Calendar.SECOND, 0);
		tfdate.set(Calendar.MILLISECOND, 0);

		// 1:SUNDAY, 2:MONDAY,...
		int iweek = tfdate.get(Calendar.DAY_OF_WEEK);
		if (iweek == 1)
			iweek = 7;
		else
			iweek--;

		TimeoffInit.put("tfWeek", ("" + iweek));

		Dtime nearDtime = null;
		Dtime electDtime = null;
		
		Calendar[] thisweek = new Calendar[8];	//8天內的日期資料
		for(int i = 0; i < 8; i++){
			thisweek[i] = Calendar.getInstance();
			thisweek[i].add(Calendar.DAY_OF_MONTH, -i);
		}
		final String[] weekp = {"(一)","(二)","(三)","(四)","(五)","(六)","(日)"};
		
		Map tfups = sm.findTimeOffUploadRecord(credential);
		
		
		session.setAttribute("TimeOffnotUpload", tfups);
		
		setContentPage(session, "teacher/TimeOffUploadRecord.jsp");
		return mapping.findForward("Main");

	}
		
	
	public ActionForward cancel(ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute("StudTimeOffEditB");
		session.removeAttribute("StudTimeOffInEditB");
		setContentPage(session, "teacher/TimeOffInputBatch.jsp");
		return mapping.findForward("Main");
	}
	
	private int chkIsTeaching(String clazz, int weekd, int period){
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
		Calendar cal = Calendar.getInstance();
		Calendar begin = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		
		Time[] dtStamp = sm.getDtimeStamp(clazz, weekd, period);
		String[] times = dtStamp[0].toString().split(":");
		begin.set(Calendar.HOUR_OF_DAY, Integer.parseInt(times[0]));
		begin.set(Calendar.MINUTE, Integer.parseInt(times[1]));
		begin.set(Calendar.SECOND, Integer.parseInt(times[2]));
		
		times = dtStamp[1].toString().split(":");
		end.set(Calendar.HOUR_OF_DAY, Integer.parseInt(times[0]));
		end.set(Calendar.MINUTE, Integer.parseInt(times[1]));
		end.set(Calendar.SECOND, Integer.parseInt(times[2]));
		
		if(cal.compareTo(begin) >= 0 && cal.compareTo(end) <= 0){
			return 0;
		}else if(cal.compareTo(begin) < 0){
			return -1;
		}
		return 1;
	}
}
