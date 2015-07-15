<%@page import="com.businessobjects.samples.JRCHelperSample,
com.crystaldecisions.report.web.viewer.CrystalReportViewer,
com.crystaldecisions.reports.sdk.ReportClientDocument,
com.crystaldecisions.sdk.occa.report.application.OpenReportOptions,
com.crystaldecisions.sdk.occa.report.lib.ReportSDKExceptionBase,
com.crystaldecisions.sdk.occa.report.reportsource.IReportSource,
com.crystaldecisions.report.web.viewer.CrPrintMode,
java.util.ArrayList,
java.util.List,
tw.edu.chit.model.report.ScoreNotUpload"%>
<%@ taglib uri="/crystal-tags-reportviewer.tld" prefix="crv" %>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="java.util.Iterator"/>
<%@ include file="/taglibs.jsp" %>
<%
	// This sample code calls methods from the JRCHelperSample class, which 
	// contains examples of how to use the BusinessObjects APIs. You are free to 
	// modify and distribute the source code contained in the JRCHelperSample class. 

	try {
		session.removeAttribute("reportSource");
		String reportName = "pages/CRreports/ScoreNotUploadReport.rpt";
		//ReportClientDocument clientDoc = (ReportClientDocument) session.getAttribute(reportName);
		//避免抓到session中原來儲存的報表
		ReportClientDocument clientDoc = null;
		if (clientDoc == null) {
			// Report can be opened from the relative location specified in the CRConfig.xml, or the report location
			// tag can be removed to open the reports as Java resources or using an absolute path
			// (absolute path not recommended for Web applications).

			clientDoc = new ReportClientDocument();
			if (clientDoc == null){
				out.println("clientDoc create failure !");
				throw new java.lang.Exception("clientDoc create failure !");
			}
			// Open report
			clientDoc.open(reportName, OpenReportOptions._openAsReadOnly);

			
			// ****** BEGIN POPULATE WITH POJO SNIPPET ****************  
			{
				// **** POPULATE MAIN REPORT ****
				{

					 // Populate POJO data source
					 String className = "tw.edu.chit.model.report.ScoreNotUpload";
					 
					 // Look up existing table in the report to set the datasource for and obtain its alias.  This table must
					 // have the same schema as the Resultset that is being pushed in at runtime.  The table could be created
					 // from a Field Definition File, a Command Object, or regular database table.  As long the Resultset
					 // schema has the same field names and types, then the Resultset can be used as the datasource for the table.
					 String tableAlias = "ScoreNotUpload";

					 //Create a dataset based on the class tw.edu.chit.model.report.ScoreNotUpload
					 //If the class does not have a basic constructor with no parameters, make sure to adjust that manually
					 
					 List snuList = (List)session.getAttribute("ScoreNotUploadList");
					 //List dataSet = (List)session.getAttribute("ScoreNotUploadList");
					 List dataSet = new ArrayList();
					 //dataSet.addAll(snuList);
					 for(Iterator<Map> snuIter=snuList.iterator(); snuIter.hasNext();){
					 	Map snuMap = snuIter.next();
					 	dataSet.add(new ScoreNotUpload(
					 	snuMap.get("departClass").toString(), 
					 	snuMap.get("deptClassName").toString(),
					 	snuMap.get("cscode").toString(), 
					 	snuMap.get("cscodeName").toString(),
					 	snuMap.get("teacherID").toString(), 
					 	snuMap.get("teacherName").toString(),
					 	snuMap.get("memo").toString()
					 	));
					 }
					 
					 //Push the resultset into the report (the POJO resultset will then be the runtime datasource of the report)
					 JRCHelperSample.passPOJO(clientDoc, dataSet, className, tableAlias, "");
				}


			}
			// ****** END POPULATE WITH POJO SNIPPET ****************		
			
			
			// Store the report document in session
			session.setAttribute(reportName, clientDoc);

		}

				
			// ****** BEGIN CONNECT CRYSTALREPORTPAGEVIEWER SNIPPET ****************  
			{
				// Create the CrystalReportViewer object
				CrystalReportViewer crystalReportPageViewer = new CrystalReportViewer();

				//	set the reportsource property of the viewer
				IReportSource reportSource = clientDoc.getReportSource();				
				crystalReportPageViewer.setReportSource(reportSource);

				// set viewer attributes
				crystalReportPageViewer.setOwnPage(true);
				crystalReportPageViewer.setOwnForm(true);
				crystalReportPageViewer.setDisplayGroupTree(false);
				crystalReportPageViewer.setHasRefreshButton(true);
				crystalReportPageViewer.setHasLogo(false);
				crystalReportPageViewer.setPrintMode(CrPrintMode.PDF);
				//ActiveX列印目前還無法正常工作--Sean Johnson (CR4E Product Manager) 2006-Oct-6
				//crystalReportPageViewer.setPrintMode(CrPrintMode.ACTIVEX);
				// Apply the viewer preference attributes

				session.removeAttribute("reportSource");
				
				// Process the report
				crystalReportPageViewer.processHttpRequest(request, response, application, null); 
				
			}
			// ****** END CONNECT CRYSTALREPORTPAGEVIEWER SNIPPET ****************		
			


	} catch (ReportSDKExceptionBase e) {
	    out.println(e);
	} 
	
%>