package tw.edu.chit.struts.action.portfolio.teacher;

import java.io.PrintWriter;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMNode;
import org.apache.axiom.soap.SOAP11Constants;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class UcanReportAction extends BaseLookupDispatchAction {
	
	static List result;
	static String tag;
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*
		// ActionMessages msg = new ActionMessages(); //建立共用訊息
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		request.setAttribute("AllCampus",manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Campus'"));
		request.setAttribute("AllSchool",manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='School'"));
		request.setAttribute("AllDept", manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Dept'"));
		request.setAttribute("AllSchoolType",manager.ezGetBy("SELECT idno, name FROM code5 WHERE Category='Stype'"));
		setContentPage(request.getSession(false), "portfolio/teacher/UcanReport.jsp");
		
		ActionMessages msg = new ActionMessages();		//建立共用訊息
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "UCAN提供的SOAP服務只允許查詢30天區間的資料"));
		saveMessages(request, msg);
		
		
		return mapping.findForward("Main");
	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		// UserCredential c = (UserCredential)
		// session.getAttribute("Credential");
		// String Uid=c.getMember().getAccount();

		DynaActionForm f = (DynaActionForm) form;
		String type = f.getString("type");
		String classes = f.getString("CampusNo") + f.getString("SchoolNo")
				+ f.getString("DeptNo") + f.getString("Grade")
				+ f.getString("ClassNo");

		String begin = f.getString("begin");
		String end = f.getString("end");
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sf1=new SimpleDateFormat("yyyy-MM-dd");
		begin=manager.convertDate(begin);
		end=manager.convertDate(end);
		
		Date sDate=sf1.parse(begin);
		Date eDate=sf1.parse(end);
		

		if (begin.trim().equals("") || end.trim().equals("")) {
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "UCAN提供的SOAP服務必須指定期間"));
			saveErrors(request, error);
			return mapping.findForward("Main");
		}

		response.setContentType("text/html; charset=big5");
		response.setContentType("application/vnd.ms-excel");
		Date d = new Date();
		response.setHeader("Content-disposition","attachment;filename=List4Student" + d.getTime() + ".xls");
		PrintWriter out = response.getWriter();

		//List list;

		EndpointReference target = new EndpointReference("http://210.64.24.228:9001/iService.asmx");
		if (type.equals("1")) {
			//職業興趣探索結果
			
			tag="alsOccupationDataResult";
			ServiceClient sender = new ServiceClient();

			//
			Options options = new Options();
			options.setSoapVersionURI(SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
			options.setAction("http://tempuri.org/alsOccupationData");
			options.setTo(target);
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
			sender.setOptions(options);

			//
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace omNs = fac.createOMNamespace("http://tempuri.org/", "");
			OMElement data = fac.createOMElement("alsOccupationData", omNs);
			
			OMElement inner = fac.createOMElement("SchoolCode", omNs);			
			inner.setText("1145");
			data.addChild(inner);
			
			inner = fac.createOMElement("sDate", omNs);			
			inner.setText(sf.format(sDate));
			data.addChild(inner);
			
			inner = fac.createOMElement("eDate", omNs);			
			inner.setText(sf.format(eDate));
			data.addChild(inner);			
			
			inner = fac.createOMElement("Encode", omNs);
			inner.setText("1");			
			data.addChild(inner);			
			
			OMElement element = sender.sendReceive(data);
			Iterator i1 = element.getChildElements();			
			
			while (i1.hasNext()) {
				OMNode n1 = (OMNode) i1.next();
				if (n1.getType() == OMNode.ELEMENT_NODE) {
					OMElement e1 = (OMElement) n1;
					//System.out.println(e1.getText());
					out.println(e1.getText());
					if (e1.getLocalName().equals("alsOccupationDataResponse")) {
						//result=new ArrayList();
						//System.out.println(e1.getText());
						printXMLTree(e1.getText());
					}
				}
			}
			out.close();
			sender.cleanupTransport();			
			//out.println("<table border='1'>");
			//for (int i = 0; i < result.size(); i++) {
				//out.println("  <tr>");
				//out.println("    <td>" + result.get(i) + "</td>");
				//out.println("  </tr>");

			//}
			//out.println("</table>");			
			return null;
		}

		if (type.equals("2")) {
			tag="alsResultDataResult";
			ServiceClient sender = new ServiceClient();

			//職場共通職能診斷結果
			Options options = new Options();
			options.setSoapVersionURI(SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
			options.setAction("http://tempuri.org/alsResultData");
			options.setTo(target);
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
			sender.setOptions(options);

			//
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace omNs = fac.createOMNamespace("http://tempuri.org/", "");
			OMElement data = fac.createOMElement("alsResultData", omNs);
			
			OMElement inner = fac.createOMElement("SchoolCode", omNs);			
			inner.setText("1145");
			data.addChild(inner);
			
			inner = fac.createOMElement("sDate", omNs);			
			inner.setText(sf.format(sDate));
			data.addChild(inner);
			
			inner = fac.createOMElement("eDate", omNs);			
			inner.setText(sf.format(eDate));
			data.addChild(inner);			
			
			inner = fac.createOMElement("Encode", omNs);
			inner.setText("1");			
			data.addChild(inner);			
			
			OMElement element = sender.sendReceive(data);
			Iterator i1 = element.getChildElements();			
			
			while (i1.hasNext()) {
				OMNode n1 = (OMNode) i1.next();
				if (n1.getType() == OMNode.ELEMENT_NODE) {
					OMElement e1 = (OMElement) n1;
					out.println(e1.getText());
					if (e1.getLocalName().equals("alsResultDataResponse")) {
						printXMLTree(e1.getText());
					}
				}
			}
			out.close();
			sender.cleanupTransport();	
			return null;
		}
		
		
		if (type.equals("3")) {
			tag="commOccupationDataResult";
			ServiceClient sender = new ServiceClient();

			//專業職能-就業途徑的總分
			Options options = new Options();
			options.setSoapVersionURI(SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
			options.setAction("http://tempuri.org/commOccupationData");
			options.setTo(target);
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
			sender.setOptions(options);

			//
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace omNs = fac.createOMNamespace("http://tempuri.org/", "");
			OMElement data = fac.createOMElement("commOccupationData", omNs);
			
			OMElement inner = fac.createOMElement("SchoolCode", omNs);			
			inner.setText("1145");
			data.addChild(inner);
			
			inner = fac.createOMElement("sDate", omNs);			
			inner.setText(sf.format(sDate));
			data.addChild(inner);
			
			inner = fac.createOMElement("eDate", omNs);			
			inner.setText(sf.format(eDate));
			data.addChild(inner);			
			
			inner = fac.createOMElement("Encode", omNs);
			inner.setText("1");			
			data.addChild(inner);			
			
			OMElement element = sender.sendReceive(data);
			Iterator i1 = element.getChildElements();			
			
			while (i1.hasNext()) {
				OMNode n1 = (OMNode) i1.next();
				if (n1.getType() == OMNode.ELEMENT_NODE) {
					OMElement e1 = (OMElement) n1;
					out.println(e1.getText());
					if (e1.getLocalName().equals("commOccupationDataResponse")) {
						printXMLTree(e1.getText());
					}
				}
			}
			out.close();
			sender.cleanupTransport();	
			return null;
		}
		
		if (type.equals("4")) {
			tag="dgnExploreDataResult";
			ServiceClient sender = new ServiceClient();

			//專業職能診斷第一層平均分數
			Options options = new Options();
			options.setSoapVersionURI(SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
			options.setAction("http://tempuri.org/dgnExploreData");
			options.setTo(target);
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
			sender.setOptions(options);

			//
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace omNs = fac.createOMNamespace("http://tempuri.org/", "");
			OMElement data = fac.createOMElement("dgnExploreData", omNs);
			
			OMElement inner = fac.createOMElement("SchoolCode", omNs);			
			inner.setText("1145");
			data.addChild(inner);
			
			inner = fac.createOMElement("sDate", omNs);			
			inner.setText(sf.format(sDate));
			data.addChild(inner);
			
			inner = fac.createOMElement("eDate", omNs);			
			inner.setText(sf.format(eDate));
			data.addChild(inner);			
			
			inner = fac.createOMElement("Encode", omNs);
			inner.setText("1");			
			data.addChild(inner);			
			
			OMElement element = sender.sendReceive(data);
			Iterator i1 = element.getChildElements();			
			
			while (i1.hasNext()) {
				OMNode n1 = (OMNode) i1.next();
				if (n1.getType() == OMNode.ELEMENT_NODE) {
					OMElement e1 = (OMElement) n1;
					out.println(e1.getText());
					if (e1.getLocalName().equals("dgnExploreDataResponse")) {
						printXMLTree(e1.getText());
					}
				}
			}
			out.close();
			sender.cleanupTransport();	
			
			return null;
		}
		
		
		if (type.equals("5")) {
			tag="ClayerDataResult";
			ServiceClient sender = new ServiceClient();

			//指標對照表
			Options options = new Options();
			options.setSoapVersionURI(SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
			options.setAction("http://tempuri.org/ClayerData");
			options.setTo(target);
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
			sender.setOptions(options);

			//
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace omNs = fac.createOMNamespace("http://tempuri.org/", "");
			OMElement data = fac.createOMElement("ClayerData", omNs);
			
			OMElement inner = fac.createOMElement("SchoolCode", omNs);			
			inner.setText("1145");
			data.addChild(inner);
			
			inner = fac.createOMElement("Encode", omNs);
			inner.setText("1");			
			data.addChild(inner);			
			
			OMElement element = sender.sendReceive(data);
			Iterator i1 = element.getChildElements();			
			
			while (i1.hasNext()) {
				OMNode n1 = (OMNode) i1.next();
				if (n1.getType() == OMNode.ELEMENT_NODE) {
					OMElement e1 = (OMElement) n1;
					out.println(e1.getText());
					if (e1.getLocalName().equals("ClayerDataResponse")) {
						printXMLTree(e1.getText());
					}
				}
			}
			out.close();
			sender.cleanupTransport();	
			return null;
		}
		
		
		if (type.equals("6")) {
			tag="ClusterDataResult";
			ServiceClient sender = new ServiceClient();

			//指標對照表
			Options options = new Options();
			options.setSoapVersionURI(SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
			options.setAction("http://tempuri.org/ClusterData");
			options.setTo(target);
			options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
			sender.setOptions(options);

			//
			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace omNs = fac.createOMNamespace("http://tempuri.org/", "");
			OMElement data = fac.createOMElement("ClusterData", omNs);
			
			OMElement inner = fac.createOMElement("SchoolCode", omNs);			
			inner.setText("1145");
			data.addChild(inner);
			
			inner = fac.createOMElement("Encode", omNs);
			inner.setText("1");			
			data.addChild(inner);			
			
			OMElement element = sender.sendReceive(data);
			Iterator i1 = element.getChildElements();			
			
			while (i1.hasNext()) {
				OMNode n1 = (OMNode) i1.next();
				if (n1.getType() == OMNode.ELEMENT_NODE) {
					OMElement e1 = (OMElement) n1;
					out.println(e1.getText());
					if (e1.getLocalName().equals("ClusterDataResponse")) {
						printXMLTree(e1.getText());
					}
				}
			}
			out.close();
			sender.cleanupTransport();	
			return null;
		}
		
		if (type.equals("7")) {
			
			Options options = new Options();
	        options.setSoapVersionURI(SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
	        options.setAction("http://tempuri.org/OccupationData");
	        options.setTo(target);
	        options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
			
			OMFactory fac = OMAbstractFactory.getOMFactory();
	        OMNamespace omNs = fac.createOMNamespace("http://tempuri.org/", "");
	        OMElement data = fac.createOMElement("OccupationData", omNs);
	        
	        OMElement inner = fac.createOMElement("SchoolCode", omNs);        
	        inner.setText("1145");
	        data.addChild(inner);
	        
	        inner = fac.createOMElement("Encode", omNs);        
	        inner.setText("1");
	        data.addChild(inner);
	        
	        ServiceClient sender = new ServiceClient();
	        sender.setOptions(options);
	        OMElement result = sender.sendReceive(data);
	        System.out.println(result);
	        result=null;
	        data=null;
	        options=null;
			out.close();
			sender.cleanupTransport();
			
			return null;
		}
		
		
		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("Query", "query");
		return map;
	}

	private void printXMLTree(String str) throws DocumentException {
		SAXReader saxReader = new SAXReader();
		Document doc = saxReader.read(new StringReader(str));
		Element root = doc.getRootElement();
		printElement(root);
		return;
	}
	
	private void printElement(Element element) {
		Iterator iter = element.elementIterator();
		while (iter.hasNext()) {
			Element sub = (Element) iter.next();
			printElement(sub);
			//System.out.println(sub.asXML());
			result.add(sub.attributeValue(sub.asXML()));
			//try{
				//if(sub.attributeValue(tag)!=null)
				//result.add(sub.attributeValue(tag));
			//}catch(Exception e){}
		}
		*/
		return null;
	}

	@Override
	protected Map getKeyMethodMap() {
		// TODO Auto-generated method stub
		return null;
	}


}
