package tw.edu.chit.struts.action.personnel;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.dao.StudAffairDAO;
import tw.edu.chit.model.AssessPaper;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.service.StudAffairManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.HibernateQueryResultDataSource;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.JasperReportUtils;
import tw.edu.chit.util.Toolket;

public class AssessPaperAction  extends BaseLookupDispatchAction {

	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Print",			"print");
		map.put("PrintPreview",		"print");
		map.put("Query",			"query");
		map.put("Cancel", 			"cancel");
		return map;
	}

	private static char[] words = {'1','2','3','4','5','6','7','8','9',
			'A','B','C','D','E','F','G','H','I','J','K','L','M','N','P',
			'Q','R','S','T','U','V','W','X','Y','Z'}; 
	
	/**
	 * Use Fisher-Yates shuffle to create random password
	 */
	static Random rng = new Random();
	private String getRandom(int len){
		String ret = "";
		char[] vws = words.clone();
		for(int i=vws.length; i>1; i--){
			int j = rng.nextInt(i);
			char tmp = vws[j];
			vws[j] = vws[i-1];
			vws[i-1] = tmp;
		}
		for(int k=0; k<len; k++){
			ret += vws[k];
		}
		return ret;
	}
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.initialize(mapping);
		UserCredential user = (UserCredential)session.getAttribute("Credential");

		String idno = user.getMember().getIdno();
		Map papers = mm.getAssessPaperByIdno(idno);
		
		session.setAttribute("AssessPaperList", papers.get("papers"));
		session.setAttribute("AssessPaperTotal", papers.get("total"));
		session.setAttribute("AssessPaperAvg", papers.get("avgScore"));
		
		setContentPage(session, "personnel/AssessPaperPrint.jsp");
		return mapping.findForward("Main");
	}
	
	public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws JRException, IOException, ServletException{
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
		StudAffairDAO dao = (StudAffairDAO) getBean("studAffairDAO");
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		UserCredential user = (UserCredential)session.getAttribute("Credential");

		String qty = aForm.getString("qty").trim();
		boolean setupPrinter = false;
		boolean isPrint = false;
		
		ActionMessages messages = new ActionMessages();
		
		
		if(!qty.equals("")){
			String idno = user.getMember().getIdno();
			String prefix = idno.substring(idno.length()-5);
			int q = Integer.parseInt(qty);
			String[] codes = new String[q];
			String rano = prefix + this.getRandom(4);
			String hql ="From AssessPaper Where serviceNo='" + rano + "' And idno='" + idno + "'";
			
			//建立服務編號
			boolean isUniq = false;
			for(int j=0; j<q; j++){
				while(true){
					rano = prefix + this.getRandom(4);
					isUniq = true;
					if(dao.submitQuery(hql).isEmpty()){
						//檢查服務編號是否重複
						for(int k=0; k<j; k++){
							if(rano==codes[k]){
								isUniq = false;
								break;
							}
						}
						if(isUniq){
							codes[j] = rano;
							break;
						}
					}
				}
			}
			//Insert into DB
			ActionMessages msg = mm.addNewAssessPaper(idno, codes);
			if(!msg.isEmpty()){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","滿意度調查列印資料新增失敗：" + msg));
				saveErrors(request, messages);
				setContentPage(request, "personnel/AssessPaperPrint.jsp");
				return mapping.findForward(IConstants.ACTION_MAIN_NAME);
			}
			
			//TODO: print the paper in pdf format
			String reportSourceFile = "/WEB-INF/reports/AssessPaper.jrxml";
			String reportCompiledFile = "/WEB-INF/reports/AssessPaper.jasper";
			List<Object> printData = new ArrayList<Object>();
			ServletContext context = request.getSession().getServletContext();
			JasperReportUtils.initJasperReportsClasspath(request);

			printData = fillPrintData(codes);

			DecimalFormat df = new DecimalFormat(",##0.0");
			File reportFile = null;
			reportFile = new File(context.getRealPath(reportCompiledFile));
			// 需要時再編譯即可
			// if (!reportFile.exists()) {
				JasperReportUtils.compileJasperReports(context
						.getRealPath(reportSourceFile));
				reportFile = new File(context
						.getRealPath(reportCompiledFile));
				if (!reportFile.exists())
					throw new JRRuntimeException(
							"查無\"AssessPaper.jasper\"檔案，請電洽電算中心，謝謝！！");
			// }

			Map<String, String> parameters = new HashMap<String, String>();

			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObject(reportFile.getPath());
						
			parameters.put("idno", idno);
			parameters.put("userName", user.getMember().getName());
			parameters.put("PrintDate", Toolket.Date2Str(new Date()));	 // 列印日期
			
			String[] fields = { "serviceNo0","serviceNo1","serviceNo2","serviceNo3","serviceNo4","serviceNo5",
					"serviceNo6","serviceNo7","serviceNo8","serviceNo9"};
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, parameters,
					new HibernateQueryResultDataSource(printData,
							fields));
			// jasperPrint.
			// 列印或預覽
			if (isPrint) {
				JasperPrintManager.printReport(jasperPrint,
						setupPrinter);						
			} else {
				byte[] bytes = JasperRunManager.runReportToPdf(
						jasperReport, parameters,
						new HibernateQueryResultDataSource(printData,
								fields));
				
				Calendar td = Calendar.getInstance();
				String ran = "" + (td.get(Calendar.MINUTE)) + (td.get(Calendar.SECOND)) + 
							(td.get(Calendar.MILLISECOND));
				String ranDir = "/WEB-INF/reports/temp/" + ran;
				
				File tempdir = new File(context
						.getRealPath(ranDir));
				if(!tempdir.exists()) tempdir.mkdirs();

				OutputStream os = new BufferedOutputStream(
						new FileOutputStream(new File(context
								.getRealPath(ranDir + "/AssessPaper.pdf"))));
				JasperExportManager.exportReportToPdfStream(
						jasperPrint, os);
				JasperReportUtils.printPdfToFrontEnd(response, bytes);
				os.close();
				Toolket.deleteDIR(tempdir);
				return null;
			}

			
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"MessageN1","請輸入欲列印張數!"));
			saveErrors(request, messages);
		}
		setContentPage(request, "personnel/AssessPaperPrint.jsp");
		return unspecified(mapping, form, request, response);
	}
	
	public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		HttpSession session = request.getSession(false);
		session.removeAttribute("AssessPaperList");
		session.removeAttribute("AssessPaperTotal");
		session.removeAttribute("AssessPaperAvg");
		return mapping.findForward("back");
		
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response){
		MemberManager mm = (MemberManager) getBean(IConstants.MEMBER_MANAGER_BEAN_NAME);
		StudAffairManager sm = (StudAffairManager) getBean("studAffairManager");
		HttpSession session = request.getSession(false);
		DynaActionForm aForm = (DynaActionForm) form;
		aForm.initialize(mapping);
		UserCredential user = (UserCredential)session.getAttribute("Credential");

		String idno = user.getMember().getIdno();
		Map papers = mm.getAssessPaperByIdno(idno);
		
		session.setAttribute("AssessPaperList", papers.get("papers"));
		session.setAttribute("AssessPaperTotal", papers.get("total"));
		session.setAttribute("AssessPaperAvg", papers.get("avgScore"));
		
		setContentPage(session, "personnel/AssessPaperPrint.jsp");
		return mapping.findForward("Main");
	}
	
	
	private List<Object> fillPrintData(String[] iList){
		List<Object> rtList = new ArrayList();
		String no = "";
		for(int i=0; i<(iList.length)/10; i++){
			Object[] obj = new Object[10];
			for(int j=0; j<10; j++){
				no = "serviceNo" + (j%10);
				obj[j] = iList[i*10+j];
			}
			rtList.add(obj);
		}
		return rtList;
	}

}
