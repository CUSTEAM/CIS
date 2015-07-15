package tw.edu.chit.struts.action.publicAccess;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.FileDataSource;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import sun.misc.BASE64Encoder;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.Toolket;

public class MyCsTableAction extends BaseLookupDispatchAction{
	BASE64Encoder enc = new sun.misc.BASE64Encoder();
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//ActionMessages msg = new ActionMessages();		//建立共用訊息
		//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		UserCredential c = (UserCredential) session.getAttribute("Credential");
		
		//TODO 查詢別人的功能
		if(session.getAttribute("allClass")==null)
		if(c.getStudent()==null){
			session.setAttribute("allClass", manager.ezGetBy("SELECT cl.ClassName, c.chi_name, dc.* FROM Dtime d, " +
			"Dtime_class dc, Csno c, Class cl WHERE cl.ClassNo=d.depart_class AND dc.Dtime_oid=d.Oid AND " +
			"d.cscode=c.cscode AND d.Sterm='"+manager.getSchoolTerm()+"' AND d.techid='"+c.getMember().getIdno()+"'"));
		}else{
			session.setAttribute("allClass", manager.ezGetBy("SELECT cl.ClassName, c.chi_name, dc.* FROM Dtime d, " +
					"Dtime_class dc, Csno c, Class cl, Seld s WHERE s.Dtime_oid=d.Oid AND cl.ClassNo=d.depart_class AND " +
					"dc.Dtime_oid=d.Oid AND d.cscode=c.cscode AND d.Sterm='"+manager.getSchoolTerm()+"' AND " +
					"s.student_no='"+c.getStudent().getAccount()+"'"));			
		}
		
		

		setContentPage(request.getSession(false), "publicAccess/MyCsTable.jsp");
		return mapping.findForward("Main");
	}
	
	
	
	//TODO 擴充查詢其他條件
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		
		return map;
	}

}


