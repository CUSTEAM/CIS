package tw.edu.chit.struts.action.sysadmin;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.struts.action.sysadmin.sshtools.SSHClient;

public class SendTestMailAction extends BaseLookupDispatchAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");		
		//List list=manager.ezGetBy("SELECT c.idno, c.name FROM CodeEmpl c WHERE c.category IN('Unit', 'UnitTeach') ORDER BY c.category");
		request.setAttribute("units", manager.ezGetBy("SELECT c.idno, c.name FROM CodeEmpl c WHERE c.category IN('Unit', 'UnitTeach') ORDER BY c.category"));
		
				
		
		
		
		setContentPage(request.getSession(false), "sysadmin/SendTestMail.jsp");
		return mapping.findForward("Main");

	}
	
	public ActionForward sendMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		/*
		<form-property name="username" type="java.lang.String" initial="" />
		<form-property name="password" type="java.lang.String" initial="" />			
		<form-property name="smtpServer" type="java.lang.String" initial="" />
		
		<form-property name="" type="java.lang.String" initial="" />			
		<form-property name="" type="java.lang.String" initial="" />
		<form-property name="" type="java.lang.String" initial="" />
		<form-property name="" type="java.lang.String" initial="" />
		<form-property name="" type="java.lang.String" initial="" />
		*/
		CourseManager manager = (CourseManager) getBean("courseManager");		
		//request.setAttribute("servers", manager.ezGetBy("SELECT * FROM Parameter p WHERE p.Name='mailServer'"));
		DynaActionForm aForm = (DynaActionForm) form;
		String dsplEmail=aForm.getString("dsplEmail");
		String dsplName=aForm.getString("dsplName");
		String dsplDate=aForm.getString("dsplDate");
		String subject=aForm.getString("subject");
		String content=aForm.getString("content");
		String unit[]=aForm.getStrings("unit");
		
		content=content+"<img src='http://ap.cust.edu.tw/CIS/CountImage?userid=77'>";
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		if(dsplDate.equals("")){
			dsplDate=manager.convertDate(sf.format(date));			
		}
		
		
		List empls;
		
		//List allEmpl=new ArrayList();
		InternetAddress addr;
		InternetAddress address[];
		for(int i=0; i<unit.length; i++){			
			empls=manager.ezGetBy("SELECT cname, Email FROM empl WHERE unit='"+unit[i]+"' AND Email IS NOT NULL AND Email!=''");			
			
			for(int j=0; j<empls.size(); j++){
				address=new InternetAddress[1];
				addr=new InternetAddress(((Map)empls.get(j)).get("Email").toString(), ((Map)empls.get(j)).get("cname").toString(), "BIG5");
				//System.out.println(empls.get(j));
				address[0]=addr;
				
				manager.saveMail(dsplEmail, ((Map)empls.get(i)).get("Email").toString(), subject, address, content, date, ((Map)empls.get(i)).get("Email").toString());
				manager.sendMail("CIS", "chit!@#", "www.cust.edu.tw", dsplEmail, dsplName, sf.parse(manager.convertDate(dsplDate)), subject, content, address, null);				
			}
		}
		
		
		
		
		
		setContentPage(request.getSession(false), "sysadmin/SendTestMail.jsp");
		return unspecified(mapping, form, request, response);

	}
	
	public ActionForward sendCancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		CourseManager manager = (CourseManager) getBean("courseManager");		
		//request.setAttribute("servers", manager.ezGetBy("SELECT * FROM Parameter p WHERE p.Name='mailServer'"));
		
		
				
		
		
		
		setContentPage(request.getSession(false), "sysadmin/SendTestMail.jsp");
		return mapping.findForward("Main");

	}

	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("SendMail", "sendMail");
		map.put("SendCancel", "sendCancel");
		return map;
	}

}
