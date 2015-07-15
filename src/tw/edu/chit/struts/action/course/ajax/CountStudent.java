package tw.edu.chit.struts.action.course.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;

public class CountStudent extends HttpServlet{

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());

		CourseManager manager=(CourseManager) ctx.getBean("courseManager");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out=response.getWriter();

		//初進入
		if(request.getParameter("dept").equals("all")){

			// 研究所
			List Te=manager.CountDepartClass("11G%", "3");
			List He=manager.CountDepartClass("21G%", "3");
			int Tecount=countStudent(Te);
			int Hecount=countStudent(He);

			// 日2技
			List TtwoG=manager.CountDepartClass("142%", "3");
			List HtwoG=manager.CountDepartClass("242%", "3");
			int TtwoGcount=countStudent(TtwoG);
			int HtwoGcount=countStudent(HtwoG);

			// 日4技
			List T4G=manager.CountDepartClass("164%", "5");
			List H4G=manager.CountDepartClass("264%", "5");
			int T4Gcount=countStudent(T4G);
			int H4Gcount=countStudent(H4G);

			// 日2專
			List T2T=manager.CountDepartClass("112%", "3");
			List H2T=manager.CountDepartClass("212%", "3");
			int T2Tcount=countStudent(T2T);
			int H2Tcount=countStudent(H2T);

			// 夜研究所
			List nTe=manager.CountDepartClass("12G%", "3");
			List nHe=manager.CountDepartClass("22G%", "3");
			int nTecount=countStudent(nTe);
			int nHecount=countStudent(nHe);

			// 夜2技
			List nTtwoG=manager.CountDepartClass("152%", "3");
			List nHtwoG=manager.CountDepartClass("252%", "3");
			int nTtwoGcount=countStudent(nTtwoG);
			int nHtwoGcount=countStudent(nHtwoG);

			// 夜4技
			List nT4G=manager.CountDepartClass("154%", "5");
			List nH4G=manager.CountDepartClass("254%", "5");
			int nT4Gcount=countStudent(nT4G);
			int nH4Gcount=countStudent(nH4G);

			// 夜2專
			List nT2T=manager.CountDepartClass("122%", "3");
			List nH2T=manager.CountDepartClass("222%", "3");
			int nT2Tcount=countStudent(nT2T);
			int nH2Tcount=countStudent(nH2T);

			// 在職研究所
			List jTe=manager.CountDepartClass("18G%", "3");
			List jHe=manager.CountDepartClass("28G%", "3");
			int jTecount=countStudent(jTe);
			int jHecount=countStudent(jHe);

			// 在職夜2技
			List jTtwoG=manager.CountDepartClass("182%", "3");
			List jHtwoG=manager.CountDepartClass("282%", "3");
			int jTtwoGcount=countStudent(jTtwoG);
			int jHtwoGcount=countStudent(jHtwoG);

			// 在職夜4技
			List jT4G=manager.CountDepartClass("184%", "5");
			List jH4G=manager.CountDepartClass("284%", "5");
			int jT4Gcount=countStudent(jT4G);
			int jH4Gcount=countStudent(jH4G);

			// 在職夜2專
			List jT2T=manager.CountDepartClass("192%", "3");
			List jH2T=manager.CountDepartClass("292%", "3");
			int jT2Tcount=countStudent(jT2T);
			int jH2Tcount=countStudent(jH2T);

			// 學院研究所
			List sTe=manager.CountDepartClass("13G%", "3");
			List sHe=manager.CountDepartClass("23G%", "3");
			int sTecount=countStudent(sTe);
			int sHecount=countStudent(sHe);

			// 學院2技
			List sTtwoG=manager.CountDepartClass("172%", "3");
			List sHtwoG=manager.CountDepartClass("272%", "3");
			int sTtwoGcount=countStudent(sTtwoG);
			int sHtwoGcount=countStudent(sHtwoG);

			// 學院4技
			List sT4G=manager.CountDepartClass("1?4%", "5");
			List sH4G=manager.CountDepartClass("2?4%", "5");
			int sT4Gcount=countStudent(sT4G);
			int sH4Gcount=countStudent(sH4G);

			// 學院2專
			List sT2T=manager.CountDepartClass("132%", "3");
			List sH2T=manager.CountDepartClass("232%", "3");
			int sT2Tcount=countStudent(sT2T);
			int sH2Tcount=countStudent(sH2T);
			ServletContext context = request.getSession().getServletContext();
			String schoolName=(String) context.getAttribute("SchoolName_ZH");
			// HTML
			if(request.getParameter("type").equals("html")){
				out.println("<html>");
				out.println("<head><title>"+schoolName+" - 學生統計</title>" +
						"<style>" +
						"BODY { " +
						"FONT-FAMILY: Arial;" +
						"}" +

						"td {" +
						"FONT-SIZE: 10px;" +
						"}" +
						"" +
						"</style>" +
						"<MEAT HTTP-EQUIV='Pragma' CONTENT='no-cache'>" +
						"</head>");

				out.println("<body>");



				out.println("<script>");
				out.println("function jumpMenu(url){");
				out.println("  parent.location=url;");
				out.println("  ");
				out.println("}");
				out.println("</script>");
				out.println("<form name='form1' id='form1'><table><tr><td>");
				out.println("  <select name='dept' onchange='jumpMenu(this.value)'>");
				out.println("    <option value='#'>請選擇科系</option>");
				out.println("    <option value='/CIS/CountStudent?type=html&dept=all'>所有科系</option>");
				out.println("    <option value='/CIS/CountStudent?type=html&dept=1'>機械工程系</option>");
				out.println("    <option value='/CIS/CountStudent?type=html&dept=2'>電機工程系</option>");
				out.println("    <option value='/CIS/CountStudent?type=html&dept=3'>電子工程系</option>");
				out.println("    <option value='/CIS/CountStudent?type=html&dept=4'>工業工程管理系</option>");
				out.println("    <option value='/CIS/CountStudent?type=html&dept=5'>建築工程系</option>");
				out.println("    <option value='/CIS/CountStudent?type=html&dept=6'>土木工程系</option>");
				out.println("    <option value='/CIS/CountStudent?type=html&dept=7'>企業管理系</option>");
				out.println("    <option value='/CIS/CountStudent?type=html&dept=8'>國際貿易系</option>");
				out.println("    <option value='/CIS/CountStudent?type=html&dept=9'>財務金融系</option>");
				out.println("    <option value='/CIS/CountStudent?type=html&dept=A'>航空機械系</option>");
				out.println("    <option value='/CIS/CountStudent?type=html&dept=B'>航空電子系</option>");
				out.println("    <option value='/CIS/CountStudent?type=html&dept=C'>航空服務管理系</option>");
				out.println("    <option value='/CIS/CountStudent?type=html&dept=D'>資訊管理系</option>");
				out.println("    <option value='/CIS/CountStudent?type=html&dept=E'>資訊工程系</option>");
				out.println("    <option value='/CIS/CountStudent?type=html&dept=F'>食品科學系</option>");
				out.println("    <option value='/CIS/CountStudent?type=html&dept=H'>生物科技系</option>");
				out.println("    <option value='/CIS/CountStudent?type=html&dept=I'>餐飲管理系</option>");
				out.println("    <option value='/CIS/CountStudent?type=html&dept=J'>觀光事業管理系</option>");
				out.println("    <option value='/CIS/CountStudent?type=html&dept=K'>國際企業系</option>");
				out.println("  </select>");
				if(request.getParameter("type").equals("html")){
					out.print("<a href='/CIS/CountStudentByClass?dept="+request.getParameter("dept")+"' target='_blank'>班級人數明細</a>");
				}
				out.println("</form></td></tr></table> ");
			}
			// Excel
			if(request.getParameter("type").equals("excel")){
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-disposition","attachment;filename=list4Student.xls");
			}

			// Word
			if(request.getParameter("type").equals("word")){
				response.setContentType("application/vnd.ms-word");
				response.setHeader("Content-Disposition","attachment;filename=list4Student.doc");
			}




			out.println("<table cellpadding='0' cellspacing='1' bgcolor='CFE69F'>");
			out.println("  <tr height='26'>");
			out.println("    <td width='158' height='26' colspan='2' bgcolor='#CFE69F' align='center'>校區</td>");
			out.println("    <td width='131' colspan='2' bgcolor='#ffffff' align='center'>台北校區</td>");
			out.println("    <td width='126' colspan='2' bgcolor='#CFE69F' align='center'>新竹校區</td>");
			out.println("    <td width='136' colspan='2' bgcolor='#ffffff' align='center'>部制小計</td>");
			out.println("    <td width='122' colspan='2' bgcolor='#CFE69F' align='center'>部合計</td>");
			out.println("  </tr>");
			out.println("  <tr height='23' >");
			out.println("    <td height='23' bgcolor='#f0fcd7' align='center'>部</td>");
			out.println("    <td bgcolor='#ffffff' align='center'>學制</td>");
			out.println("    <td bgcolor='#f0fcd7' align='center'>班級數</td>");
			out.println("    <td bgcolor='#f0fcd7' align='center'>人數</td>");
			out.println("    <td bgcolor='#ffffff' align='center'>班級數</td>");
			out.println("    <td bgcolor='#ffffff' align='center'>人數</td>");
			out.println("    <td bgcolor='#f0fcd7' align='center'>班級數</td>");
			out.println("    <td bgcolor='#f0fcd7' align='center'>人數</td>");
			out.println("    <td bgcolor='#ffffff' align='center'>班級數</td>");
			out.println("    <td bgcolor='#ffffff' align='center'>人數</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='89' rowspan='4' bgcolor='#f0fcd7'>&nbsp;日間部&nbsp;</td>");
			out.println("    <td bgcolor='#FFFFFF'>研究所</td>");
			out.println("    <td bgcolor='#ffffff'>"+Te.size()+"</td>");
			out.println("    <td bgcolor='#ffffff'>"+Tecount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+He.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+Hecount+"</td>");
			out.println("    <td bgcolor='#ffffff'>"+(Te.size()+He.size())+"</td>");
			out.println("    <td bgcolor='#ffffff'>"+(Tecount+Hecount)+"</td>");
			out.println("    <td rowspan='4' align='center' bgcolor='#f0fcd7'>"+(TtwoG.size()+HtwoG.size()+T4G.size()+H4G.size()+T2T.size()+H2T.size()+Te.size()+He.size())+"</td>");
			out.println("    <td rowspan='4' align='center' bgcolor='#f0fcd7'>"+(TtwoGcount+HtwoGcount+T4Gcount+H4Gcount+T2Tcount+H2Tcount+Tecount+Hecount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='22' bgcolor='#f0fcd7'>二技</td>");



			out.println("    <td bgcolor='#f0fcd7'>"+TtwoG.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+TtwoGcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+HtwoG.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+HtwoGcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(TtwoG.size()+HtwoG.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(TtwoGcount+HtwoGcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='22' bgcolor='#FFFFFF'>四技</td>");



			out.println("    <td bgcolor='#ffffff'>"+T4G.size()+"</td>");
			out.println("    <td bgcolor='#ffffff'>"+T4Gcount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+H4G.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+H4Gcount+"</td>");
			out.println("    <td bgcolor='#ffffff'>"+(T4G.size()+H4G.size())+"</td>");
			out.println("    <td bgcolor='#ffffff'>"+(T4Gcount+H4Gcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='23' bgcolor='#f0fcd7'>二專</td>");


			out.println("    <td bgcolor='#f0fcd7'>"+T2T.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+T2Tcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+H2T.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+H2Tcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(T2T.size()+H2T.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(T2Tcount+H2Tcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='89' rowspan='4' bgcolor='#FFFFFF'>&nbsp;進修部&nbsp;</td>");
			out.println("    <td bgcolor='#FFFFFF'>研究所</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+nTe.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+nTecount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+nHe.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+nHecount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(nTe.size()+nHe.size())+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(nTecount+nHecount)+"</td>");
			out.println("    <td rowspan='4' align='center' bgcolor='#FFFFFF'>"+(nTtwoG.size()+nHtwoG.size()+nT4G.size()+nH4G.size()+nT2T.size()+nH2T.size()+nTe.size()+nHe.size())+"</td>");
			out.println("    <td rowspan='4' align='center' bgcolor='#FFFFFF'>"+(nTtwoGcount+nHtwoGcount+nT4Gcount+nH4Gcount+nT2Tcount+nH2Tcount+nTecount+nHecount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='22' bgcolor='#f0fcd7'>二技</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+nTtwoG.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+nTtwoGcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+nHtwoG.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+nHtwoGcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(nTtwoG.size()+nHtwoG.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(nTtwoGcount+nHtwoGcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='22' bgcolor='#FFFFFF'>四技</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+nT4G.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+nT4Gcount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+nH4G.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+nH4Gcount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(nT4G.size()+nH4G.size())+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(nT4Gcount+nH4Gcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='23'>");
			out.println("    <td height='23' bgcolor='#f0fcd7'>二專</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+nT2T.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+nT2Tcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+nH2T.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+nH2Tcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(nT2T.size()+nH2T.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(nT2Tcount+nH2Tcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='89' rowspan='4' bgcolor='#f0fcd7'>&nbsp;在職專班&nbsp;</td>");
			out.println("    <td bgcolor='#FFFFFF'>研究所</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+jTe.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+jTecount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+jHe.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+jHecount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(jTe.size()+jHe.size())+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(jTecount+jHecount)+"</td>");
			out.println("    <td rowspan='4' align='center' bgcolor='#f0fcd7'>"+(jTtwoG.size()+jHtwoG.size()+jT4G.size()+jH4G.size()+jT2T.size()+jH2T.size()+jTe.size()+jHe.size())+"</td>");
			out.println("    <td rowspan='4' align='center' bgcolor='#f0fcd7'>"+(jTtwoGcount+jHtwoGcount+jT4Gcount+jH4Gcount+jT2Tcount+jH2Tcount+jTecount+jHecount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='22' bgcolor='#f0fcd7'>二技</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+jTtwoG.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+jTtwoGcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+jHtwoG.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+jHtwoGcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(jTtwoG.size()+jHtwoG.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(jTtwoGcount+jHtwoGcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='22' bgcolor='#FFFFFF'>四技</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+jT4G.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+jT4Gcount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+jH4G.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+jH4Gcount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(jT4G.size()+jH4G.size())+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(jT4Gcount+jH4Gcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='23'>");
			out.println("    <td height='23' bgcolor='#f0fcd7'>二專</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+jT2T.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+jT2Tcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+jH2T.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+jH2Tcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(jT2T.size()+jH2T.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(jT2Tcount+jH2Tcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td width='86' height='89' rowspan='4' bgcolor='#FFFFFF'>&nbsp;進修學院/專校&nbsp;</td>");
			out.println("    <td bgcolor='#FFFFFF'>研究所</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+sTe.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+sTecount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+sHe.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+sHecount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(sTe.size()+sHe.size())+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(sTecount+sHecount)+"</td>");
			out.println("    <td rowspan='4' align='center' bgcolor='#ffffff'>"+(sTtwoG.size()+sHtwoG.size()+sT4G.size()+sH4G.size()+sT2T.size()+sH2T.size()+sTe.size()+sHe.size())+"</td>");
			out.println("    <td rowspan='4' align='center' bgcolor='#ffffff'>"+(sTtwoGcount+sHtwoGcount+sT4Gcount+sH4Gcount+sT2Tcount+sH2Tcount+sTecount+sHecount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='22' bgcolor='#f0fcd7'>二技</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+sTtwoG.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+sTtwoGcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+sHtwoG.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+sHtwoGcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(sTtwoG.size()+sHtwoG.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(sTtwoGcount+sHtwoGcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='22' bgcolor='#FFFFFF'>四技</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+sT4G.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+sT4Gcount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+sH4G.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+sH4Gcount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(sT4G.size()+sH4G.size())+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(sT4Gcount+sH4Gcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='23'>");
			out.println("    <td height='23' bgcolor='#f0fcd7'>二專</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+sT2T.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+sT2Tcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+sH2T.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+sH2Tcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(sT2T.size()+nH2T.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(sT2Tcount+nH2Tcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td width='86' height='89' rowspan='4' bgcolor='#f0fcd7'>&nbsp;校區/學制小計&nbsp;</td>");
			out.println("    <td bgcolor='#FFFFFF'>研究所</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(Te.size()+nTe.size()+jTe.size()+sTe.size())+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(Tecount+nTecount+jTecount+sTecount)+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(He.size()+nHe.size()+jHe.size()+sHe.size())+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(Hecount+nHecount+jHecount+sHecount)+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(Te.size()+nTe.size()+jTe.size()+sTe.size()+He.size()+nHe.size()+jHe.size()+sHe.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(Tecount+nTecount+jTecount+sTecount+Hecount+nHecount+jHecount+sHecount)+"</td>");
			out.println("    <td colspan='2' rowspan='4' bgcolor='#f0fcd7'>　</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='22' bgcolor='#f0fcd7'>二技</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(TtwoG.size()+nTtwoG.size()+jTtwoG.size()+sTtwoG.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(TtwoGcount+nTtwoGcount+jTtwoGcount+sTtwoGcount)+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(HtwoG.size()+nHtwoG.size()+jHtwoG.size()+sHtwoG.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(HtwoGcount+nHtwoGcount+jHtwoGcount+sHtwoGcount)+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(TtwoG.size()+nTtwoG.size()+jTtwoG.size()+sTtwoG.size()+HtwoG.size()+nHtwoG.size()+jHtwoG.size()+sHtwoG.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(TtwoGcount+nTtwoGcount+jTtwoGcount+sTtwoGcount+HtwoGcount+nHtwoGcount+jHtwoGcount+sHtwoGcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='22' bgcolor='#FFFFFF'>四技</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(T4G.size()+nT4G.size()+jT4G.size()+sT4G.size())+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(T4Gcount+nT4Gcount+jT4Gcount+sT4Gcount)+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(H4G.size()+nH4G.size()+jH4G.size()+sH4G.size())+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(H4Gcount+nH4Gcount+jH4Gcount+sH4Gcount)+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(T4G.size()+nT4G.size()+jT4G.size()+sT4G.size()+H4G.size()+nH4G.size()+jH4G.size()+sH4G.size())+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(T4Gcount+nT4Gcount+jT4Gcount+sT4Gcount+H4Gcount+nH4Gcount+jH4Gcount+sH4Gcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='23'>");
			out.println("    <td height='23' bgcolor='#f0fcd7'>二專</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(T2T.size()+nT2T.size()+jT2T.size()+sT2T.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(T2Tcount+nT2Tcount+jT2Tcount+sT2Tcount)+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(H2T.size()+nH2T.size()+jH2T.size()+sH2T.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(H2Tcount+nH2Tcount+jH2Tcount+sH2Tcount)+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(T2T.size()+nT2T.size()+jT2T.size()+sT2T.size()+H2T.size()+nH2T.size()+jH2T.size()+sH2T.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(T2Tcount+nT2Tcount+jT2Tcount+sT2Tcount+H2Tcount+nH2Tcount+jH2Tcount+sH2Tcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr>");
			out.println("    <td width='158' height='30' colspan='2' rowspan='4' bgcolor='#FFFFFF'>&nbsp;校區小計/全校總計&nbsp;</td>");

			int TClassCount=Te.size()+nTe.size()+jTe.size()+sTe.size()+
			TtwoG.size()+nTtwoG.size()+jTtwoG.size()+sTtwoG.size()+
			T4G.size()+nT4G.size()+jT4G.size()+sT4G.size()+
			T2T.size()+nT2T.size()+jT2T.size()+sT2T.size();

			int TStudentCount=Tecount+nTecount+jTecount+sTecount+
			TtwoGcount+nTtwoGcount+jTtwoGcount+sTtwoGcount+
			T4Gcount+nT4Gcount+jT4Gcount+sT4Gcount+
			T2Tcount+nT2Tcount+jT2Tcount+sT2Tcount;

			int HClassCount=He.size()+nHe.size()+jHe.size()+sHe.size()+
			HtwoG.size()+nHtwoG.size()+jHtwoG.size()+sHtwoG.size()+
			H4G.size()+nH4G.size()+jH4G.size()+sH4G.size()+
			H2T.size()+nH2T.size()+jH2T.size()+sH2T.size();

			int HStudentCount=Hecount+nHecount+jHecount+sHecount+
			HtwoGcount+nHtwoGcount+jHtwoGcount+sHtwoGcount+
			H4Gcount+nH4Gcount+jH4Gcount+sH4Gcount+
			H2Tcount+nH2Tcount+jH2Tcount+sH2Tcount;

			out.println("    <td rowspan='4' bgcolor='#FFFFFF'>"+TClassCount+"</td>");
			out.println("    <td rowspan='4' bgcolor='#FFFFFF'>"+TStudentCount+"</td>");
			out.println("    <td rowspan='4' bgcolor='#FFFFFF'>"+HClassCount+"</td>");
			out.println("    <td rowspan='4' bgcolor='#FFFFFF'>"+HStudentCount+"</td>");
			out.println("    <td colspan='2' rowspan='4' bgcolor='#FFFFFF'>　</td>");
			out.println("    <td rowspan='4' bgcolor='#FFFFFF' align='center'>"+(TClassCount+HClassCount)+"</td>");
			out.println("    <td rowspan='4' bgcolor='#FFFFFF' align='center'>"+(TStudentCount+HStudentCount)+"</td>");
			out.println("  </tr>");
			out.println("</table>");
			List totalStudent= manager.CountDepartClass();
			if(request.getParameter("type").equals("html")){

				out.println("<br>" +
						"	<table cellpadding='0' cellspacing='1' bgcolor='CFE69F'>" +
						"		<tr>" +
						"			<td bgcolor='#FFFFFF'>" +
						"				<font size='-1'>&nbsp;&nbsp;以上統計不包含 "+(countStudent(totalStudent)-(TStudentCount+HStudentCount))+" 名跨校, 延修和其它不在此列表的學生&nbsp;&nbsp;" +
						"				輸出<a href='/CIS/CountStudent?type=excel&dept="+request.getParameter("dept")+"'>excel</a>報表&nbsp;&nbsp;輸出<a href='/CIS/CountStudent?type=word&dept="+request.getParameter("dept")+"'>word</a>報表" +
						"			</td>" +
						"		</tr>" +
						"	</table>");
			}




			out.println("</body>");
			out.println("</html>");
		}else{

			String dept=request.getParameter("dept");
			// 研究所
			List Te=manager.CountDepartClass("11G"+dept+"%", "3");
			List He=manager.CountDepartClass("21G"+dept+"%", "3");
			int Tecount=countStudent(Te);
			int Hecount=countStudent(He);

			// 日2技
			List TtwoG=manager.CountDepartClass("142"+dept+"%", "3");
			List HtwoG=manager.CountDepartClass("242"+dept+"%", "3");
			int TtwoGcount=countStudent(TtwoG);
			int HtwoGcount=countStudent(HtwoG);

			// 日4技
			List T4G=manager.CountDepartClass("164"+dept+"%", "5");
			List H4G=manager.CountDepartClass("264"+dept+"%", "5");
			int T4Gcount=countStudent(T4G);
			int H4Gcount=countStudent(H4G);

			// 日2專
			List T2T=manager.CountDepartClass("112"+dept+"%", "3");
			List H2T=manager.CountDepartClass("212"+dept+"%", "3");
			int T2Tcount=countStudent(T2T);
			int H2Tcount=countStudent(H2T);

			// 夜研究所
			List nTe=manager.CountDepartClass("12G"+dept+"%", "3");
			List nHe=manager.CountDepartClass("22G"+dept+"%", "3");
			int nTecount=countStudent(nTe);
			int nHecount=countStudent(nHe);

			// 夜2技
			List nTtwoG=manager.CountDepartClass("152"+dept+"%", "3");
			List nHtwoG=manager.CountDepartClass("252"+dept+"%", "3");
			int nTtwoGcount=countStudent(nTtwoG);
			int nHtwoGcount=countStudent(nHtwoG);

			// 夜4技
			List nT4G=manager.CountDepartClass("154"+dept+"%", "5");
			List nH4G=manager.CountDepartClass("254"+dept+"%", "5");
			int nT4Gcount=countStudent(nT4G);
			int nH4Gcount=countStudent(nH4G);

			// 夜2專
			List nT2T=manager.CountDepartClass("122"+dept+"%", "3");
			List nH2T=manager.CountDepartClass("222"+dept+"%", "3");
			int nT2Tcount=countStudent(nT2T);
			int nH2Tcount=countStudent(nH2T);

			// 在職研究所
			List jTe=manager.CountDepartClass("18G"+dept+"%", "3");
			List jHe=manager.CountDepartClass("28G"+dept+"%", "3");
			int jTecount=countStudent(jTe);
			int jHecount=countStudent(jHe);

			// 在職夜2技
			List jTtwoG=manager.CountDepartClass("182"+dept+"%", "3");
			List jHtwoG=manager.CountDepartClass("282"+dept+"%", "3");
			int jTtwoGcount=countStudent(jTtwoG);
			int jHtwoGcount=countStudent(jHtwoG);

			// 在職夜4技
			List jT4G=manager.CountDepartClass("184"+dept+"%", "5");
			List jH4G=manager.CountDepartClass("284"+dept+"%", "5");
			int jT4Gcount=countStudent(jT4G);
			int jH4Gcount=countStudent(jH4G);

			// 在職夜2專
			List jT2T=manager.CountDepartClass("192"+dept+"%", "3");
			List jH2T=manager.CountDepartClass("292"+dept+"%", "3");
			int jT2Tcount=countStudent(jT2T);
			int jH2Tcount=countStudent(jH2T);

			// 學院研究所
			List sTe=manager.CountDepartClass("13G"+dept+"%", "3");
			List sHe=manager.CountDepartClass("23G"+dept+"%", "3");
			int sTecount=countStudent(sTe);
			int sHecount=countStudent(sHe);

			// 學院2技
			List sTtwoG=manager.CountDepartClass("172"+dept+"%", "3");
			List sHtwoG=manager.CountDepartClass("272"+dept+"%", "3");
			int sTtwoGcount=countStudent(sTtwoG);
			int sHtwoGcount=countStudent(sHtwoG);

			// 學院4技
			List sT4G=manager.CountDepartClass("1?4"+dept+"%", "5");
			List sH4G=manager.CountDepartClass("2?4"+dept+"%", "5");
			int sT4Gcount=countStudent(sT4G);
			int sH4Gcount=countStudent(sH4G);

			// 學院2專
			List sT2T=manager.CountDepartClass("132"+dept+"%", "3");
			List sH2T=manager.CountDepartClass("232"+dept+"%", "3");
			int sT2Tcount=countStudent(sT2T);
			int sH2Tcount=countStudent(sH2T);


			// Excel
			if(request.getParameter("type").equals("excel")){
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-disposition","attachment;filename=list4Student.xls");
			}

			// Word
			if(request.getParameter("type").equals("word")){
				response.setContentType("application/vnd.ms-word");
				response.setHeader("Content-Disposition","attachment;filename=list4Student.doc");
			}


			ServletContext context = request.getSession().getServletContext();
			String schoolName=(String) context.getAttribute("SchoolName_ZH");
			out.println("<html>");
			out.println("<head><title>"+schoolName+" - 學生統計</title>" +
					"<style>" +
					"BODY { " +
					"FONT-FAMILY: Arial;" +
					"}" +

					"td {" +
					"FONT-SIZE: 10px;" +
					"}" +
					"" +
					"</style>" +
					"<MEAT HTTP-EQUIV='Pragma' CONTENT='no-cache'>" +
					"</head>");

			out.println("<body>");

			out.println("<script>");
			out.println("function jumpMenu(url){");
			out.println("  parent.location=url;");
			out.println("  ");
			out.println("}");
			out.println("</script>");
			out.println("<form name='form1' id='form1'><table><tr><td>");
			out.println("  <select name='dept' onchange='jumpMenu(this.value)'>");
			out.println("    <option value='#'>請選擇科系</option>");
			out.println("    <option value='/CIS/CountStudent?type=html&dept=all'>所有科系</option>");
			out.println("    <option value='/CIS/CountStudent?type=html&dept=1'>機械工程系</option>");
			out.println("    <option value='/CIS/CountStudent?type=html&dept=2'>電機工程系</option>");
			out.println("    <option value='/CIS/CountStudent?type=html&dept=3'>電子工程系</option>");
			out.println("    <option value='/CIS/CountStudent?type=html&dept=4'>工業工程管理系</option>");
			out.println("    <option value='/CIS/CountStudent?type=html&dept=5'>建築工程系</option>");
			out.println("    <option value='/CIS/CountStudent?type=html&dept=6'>土木工程系</option>");
			out.println("    <option value='/CIS/CountStudent?type=html&dept=7'>企業管理系</option>");
			out.println("    <option value='/CIS/CountStudent?type=html&dept=8'>國際貿易系</option>");
			out.println("    <option value='/CIS/CountStudent?type=html&dept=9'>財務金融系</option>");
			out.println("    <option value='/CIS/CountStudent?type=html&dept=A'>航空機械系</option>");
			out.println("    <option value='/CIS/CountStudent?type=html&dept=B'>航空電子系</option>");
			out.println("    <option value='/CIS/CountStudent?type=html&dept=C'>航空服務管理系</option>");
			out.println("    <option value='/CIS/CountStudent?type=html&dept=D'>資訊管理系</option>");
			out.println("    <option value='/CIS/CountStudent?type=html&dept=E'>資訊工程系</option>");
			out.println("    <option value='/CIS/CountStudent?type=html&dept=F'>食品科學系</option>");
			out.println("    <option value='/CIS/CountStudent?type=html&dept=H'>生物科技系</option>");
			out.println("    <option value='/CIS/CountStudent?type=html&dept=I'>餐飲管理系</option>");
			out.println("    <option value='/CIS/CountStudent?type=html&dept=J'>觀光事業管理系</option>");
			out.println("    <option value='/CIS/CountStudent?type=html&dept=K'>國際企業系</option>");
			out.println("  </select>");
			if(request.getParameter("type").equals("html")){
				out.print("<a href='/CIS/CountStudentByClass?dept="+request.getParameter("dept")+"' target='_blank'>班級人數明細</a>");
			}
			out.println("</form></td></tr></table>");



			out.println("<table cellpadding='0' cellspacing='1' bgcolor='CFE69F'>");
			out.println("  <tr height='26'>");
			out.println("    <td width='158' height='26' colspan='2' bgcolor='#CFE69F' align='center'>校區</td>");
			out.println("    <td width='131' colspan='2' bgcolor='#ffffff' align='center'>台北校區</td>");
			out.println("    <td width='126' colspan='2' bgcolor='#CFE69F' align='center'>新竹校區</td>");
			out.println("    <td width='136' colspan='2' bgcolor='#ffffff' align='center'>部制小計</td>");
			out.println("    <td width='122' colspan='2' bgcolor='#CFE69F' align='center'>部合計</td>");
			out.println("  </tr>");
			out.println("  <tr height='23' >");
			out.println("    <td height='23' bgcolor='#f0fcd7' align='center'>部</td>");
			out.println("    <td bgcolor='#ffffff' align='center'>學制</td>");
			out.println("    <td bgcolor='#f0fcd7' align='center'>班級數</td>");
			out.println("    <td bgcolor='#f0fcd7' align='center'>人數</td>");
			out.println("    <td bgcolor='#ffffff' align='center'>班級數</td>");
			out.println("    <td bgcolor='#ffffff' align='center'>人數</td>");
			out.println("    <td bgcolor='#f0fcd7' align='center'>班級數</td>");
			out.println("    <td bgcolor='#f0fcd7' align='center'>人數</td>");
			out.println("    <td bgcolor='#ffffff' align='center'>班級數</td>");
			out.println("    <td bgcolor='#ffffff' align='center'>人數</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='89' rowspan='4' bgcolor='#f0fcd7'>&nbsp;日間部&nbsp;</td>");
			out.println("    <td bgcolor='#FFFFFF'>研究所</td>");
			out.println("    <td bgcolor='#ffffff'>"+Te.size()+"</td>");
			out.println("    <td bgcolor='#ffffff'>"+Tecount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+He.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+Hecount+"</td>");
			out.println("    <td bgcolor='#ffffff'>"+(Te.size()+He.size())+"</td>");
			out.println("    <td bgcolor='#ffffff'>"+(Tecount+Hecount)+"</td>");
			out.println("    <td rowspan='4' align='center' bgcolor='#f0fcd7'>"+(TtwoG.size()+HtwoG.size()+T4G.size()+H4G.size()+T2T.size()+H2T.size()+Te.size()+He.size())+"</td>");
			out.println("    <td rowspan='4' align='center' bgcolor='#f0fcd7'>"+(TtwoGcount+HtwoGcount+T4Gcount+H4Gcount+T2Tcount+H2Tcount+Tecount+Hecount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='22' bgcolor='#f0fcd7'>二技</td>");



			out.println("    <td bgcolor='#f0fcd7'>"+TtwoG.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+TtwoGcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+HtwoG.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+HtwoGcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(TtwoG.size()+HtwoG.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(TtwoGcount+HtwoGcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='22' bgcolor='#FFFFFF'>四技</td>");



			out.println("    <td bgcolor='#ffffff'>"+T4G.size()+"</td>");
			out.println("    <td bgcolor='#ffffff'>"+T4Gcount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+H4G.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+H4Gcount+"</td>");
			out.println("    <td bgcolor='#ffffff'>"+(T4G.size()+H4G.size())+"</td>");
			out.println("    <td bgcolor='#ffffff'>"+(T4Gcount+H4Gcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='23' bgcolor='#f0fcd7'>二專</td>");


			out.println("    <td bgcolor='#f0fcd7'>"+T2T.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+T2Tcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+H2T.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+H2Tcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(T2T.size()+H2T.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(T2Tcount+H2Tcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='89' rowspan='4' bgcolor='#FFFFFF'>&nbsp;進修部&nbsp;</td>");
			out.println("    <td bgcolor='#FFFFFF'>研究所</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+nTe.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+nTecount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+nHe.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+nHecount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(nTe.size()+nHe.size())+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(nTecount+nHecount)+"</td>");
			out.println("    <td rowspan='4' align='center' bgcolor='#FFFFFF'>"+(nTtwoG.size()+nHtwoG.size()+nT4G.size()+nH4G.size()+nT2T.size()+nH2T.size()+nTe.size()+nHe.size())+"</td>");
			out.println("    <td rowspan='4' align='center' bgcolor='#FFFFFF'>"+(nTtwoGcount+nHtwoGcount+nT4Gcount+nH4Gcount+nT2Tcount+nH2Tcount+nTecount+nHecount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='22' bgcolor='#f0fcd7'>二技</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+nTtwoG.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+nTtwoGcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+nHtwoG.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+nHtwoGcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(nTtwoG.size()+nHtwoG.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(nTtwoGcount+nHtwoGcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='22' bgcolor='#FFFFFF'>四技</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+nT4G.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+nT4Gcount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+nH4G.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+nH4Gcount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(nT4G.size()+nH4G.size())+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(nT4Gcount+nH4Gcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='23'>");
			out.println("    <td height='23' bgcolor='#f0fcd7'>二專</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+nT2T.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+nT2Tcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+nH2T.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+nH2Tcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(nT2T.size()+nH2T.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(nT2Tcount+nH2Tcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='89' rowspan='4' bgcolor='#f0fcd7'>&nbsp;在職專班&nbsp;</td>");
			out.println("    <td bgcolor='#FFFFFF'>研究所</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+jTe.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+jTecount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+jHe.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+jHecount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(jTe.size()+jHe.size())+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(jTecount+jHecount)+"</td>");
			out.println("    <td rowspan='4' align='center' bgcolor='#f0fcd7'>"+(jTtwoG.size()+jHtwoG.size()+jT4G.size()+jH4G.size()+jT2T.size()+jH2T.size()+jTe.size()+jHe.size())+"</td>");
			out.println("    <td rowspan='4' align='center' bgcolor='#f0fcd7'>"+(jTtwoGcount+jHtwoGcount+jT4Gcount+jH4Gcount+jT2Tcount+jH2Tcount+jTecount+jHecount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='22' bgcolor='#f0fcd7'>二技</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+jTtwoG.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+jTtwoGcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+jHtwoG.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+jHtwoGcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(jTtwoG.size()+jHtwoG.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(jTtwoGcount+jHtwoGcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='22' bgcolor='#FFFFFF'>四技</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+jT4G.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+jT4Gcount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+jH4G.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+jH4Gcount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(jT4G.size()+jH4G.size())+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(jT4Gcount+jH4Gcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='23'>");
			out.println("    <td height='23' bgcolor='#f0fcd7'>二專</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+jT2T.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+jT2Tcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+jH2T.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+jH2Tcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(jT2T.size()+jH2T.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(jT2Tcount+jH2Tcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td width='86' height='89' rowspan='4' bgcolor='#FFFFFF'>&nbsp;進修學院/專校&nbsp;</td>");
			out.println("    <td bgcolor='#FFFFFF'>研究所</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+sTe.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+sTecount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+sHe.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+sHecount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(sTe.size()+sHe.size())+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(sTecount+sHecount)+"</td>");
			out.println("    <td rowspan='4' align='center' bgcolor='#ffffff'>"+(sTtwoG.size()+sHtwoG.size()+sT4G.size()+sH4G.size()+sT2T.size()+sH2T.size()+sTe.size()+sHe.size())+"</td>");
			out.println("    <td rowspan='4' align='center' bgcolor='#ffffff'>"+(sTtwoGcount+sHtwoGcount+sT4Gcount+sH4Gcount+sT2Tcount+sH2Tcount+sTecount+sHecount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='22' bgcolor='#f0fcd7'>二技</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+sTtwoG.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+sTtwoGcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+sHtwoG.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+sHtwoGcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(sTtwoG.size()+sHtwoG.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(sTtwoGcount+sHtwoGcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='22' bgcolor='#FFFFFF'>四技</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+sT4G.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+sT4Gcount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+sH4G.size()+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+sH4Gcount+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(sT4G.size()+sH4G.size())+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(sT4Gcount+sH4Gcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='23'>");
			out.println("    <td height='23' bgcolor='#f0fcd7'>二專</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+sT2T.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+sT2Tcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+sH2T.size()+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+sH2Tcount+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(sT2T.size()+nH2T.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(sT2Tcount+nH2Tcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td width='86' height='89' rowspan='4' bgcolor='#f0fcd7'>&nbsp;校區/學制小計&nbsp;</td>");
			out.println("    <td bgcolor='#FFFFFF'>研究所</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(Te.size()+nTe.size()+jTe.size()+sTe.size())+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(Tecount+nTecount+jTecount+sTecount)+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(He.size()+nHe.size()+jHe.size()+sHe.size())+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(Hecount+nHecount+jHecount+sHecount)+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(Te.size()+nTe.size()+jTe.size()+sTe.size()+He.size()+nHe.size()+jHe.size()+sHe.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(Tecount+nTecount+jTecount+sTecount+Hecount+nHecount+jHecount+sHecount)+"</td>");
			out.println("    <td colspan='2' rowspan='4' bgcolor='#f0fcd7'>　</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='22' bgcolor='#f0fcd7'>二技</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(TtwoG.size()+nTtwoG.size()+jTtwoG.size()+sTtwoG.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(TtwoGcount+nTtwoGcount+jTtwoGcount+sTtwoGcount)+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(HtwoG.size()+nHtwoG.size()+jHtwoG.size()+sHtwoG.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(HtwoGcount+nHtwoGcount+jHtwoGcount+sHtwoGcount)+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(TtwoG.size()+nTtwoG.size()+jTtwoG.size()+sTtwoG.size()+HtwoG.size()+nHtwoG.size()+jHtwoG.size()+sHtwoG.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(TtwoGcount+nTtwoGcount+jTtwoGcount+sTtwoGcount+HtwoGcount+nHtwoGcount+jHtwoGcount+sHtwoGcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='22'>");
			out.println("    <td height='22' bgcolor='#FFFFFF'>四技</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(T4G.size()+nT4G.size()+jT4G.size()+sT4G.size())+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(T4Gcount+nT4Gcount+jT4Gcount+sT4Gcount)+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(H4G.size()+nH4G.size()+jH4G.size()+sH4G.size())+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(H4Gcount+nH4Gcount+jH4Gcount+sH4Gcount)+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(T4G.size()+nT4G.size()+jT4G.size()+sT4G.size()+H4G.size()+nH4G.size()+jH4G.size()+sH4G.size())+"</td>");
			out.println("    <td bgcolor='#FFFFFF'>"+(T4Gcount+nT4Gcount+jT4Gcount+sT4Gcount+H4Gcount+nH4Gcount+jH4Gcount+sH4Gcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr height='23'>");
			out.println("    <td height='23' bgcolor='#f0fcd7'>二專</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(T2T.size()+nT2T.size()+jT2T.size()+sT2T.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(T2Tcount+nT2Tcount+jT2Tcount+sT2Tcount)+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(H2T.size()+nH2T.size()+jH2T.size()+sH2T.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(H2Tcount+nH2Tcount+jH2Tcount+sH2Tcount)+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(T2T.size()+nT2T.size()+jT2T.size()+sT2T.size()+H2T.size()+nH2T.size()+jH2T.size()+sH2T.size())+"</td>");
			out.println("    <td bgcolor='#f0fcd7'>"+(T2Tcount+nT2Tcount+jT2Tcount+sT2Tcount+H2Tcount+nH2Tcount+jH2Tcount+sH2Tcount)+"</td>");
			out.println("  </tr>");
			out.println("  <tr>");
			out.println("    <td width='158' height='30' colspan='2' rowspan='4' bgcolor='#FFFFFF'>&nbsp;校區小計/全校總計&nbsp;</td>");

			int TClassCount=Te.size()+nTe.size()+jTe.size()+sTe.size()+
			TtwoG.size()+nTtwoG.size()+jTtwoG.size()+sTtwoG.size()+
			T4G.size()+nT4G.size()+jT4G.size()+sT4G.size()+
			T2T.size()+nT2T.size()+jT2T.size()+sT2T.size();

			int TStudentCount=Tecount+nTecount+jTecount+sTecount+
			TtwoGcount+nTtwoGcount+jTtwoGcount+sTtwoGcount+
			T4Gcount+nT4Gcount+jT4Gcount+sT4Gcount+
			T2Tcount+nT2Tcount+jT2Tcount+sT2Tcount;

			int HClassCount=He.size()+nHe.size()+jHe.size()+sHe.size()+
			HtwoG.size()+nHtwoG.size()+jHtwoG.size()+sHtwoG.size()+
			H4G.size()+nH4G.size()+jH4G.size()+sH4G.size()+
			H2T.size()+nH2T.size()+jH2T.size()+sH2T.size();

			int HStudentCount=Hecount+nHecount+jHecount+sHecount+
			HtwoGcount+nHtwoGcount+jHtwoGcount+sHtwoGcount+
			H4Gcount+nH4Gcount+jH4Gcount+sH4Gcount+
			H2Tcount+nH2Tcount+jH2Tcount+sH2Tcount;

			out.println("    <td rowspan='4' bgcolor='#FFFFFF'>"+TClassCount+"</td>");
			out.println("    <td rowspan='4' bgcolor='#FFFFFF'>"+TStudentCount+"</td>");
			out.println("    <td rowspan='4' bgcolor='#FFFFFF'>"+HClassCount+"</td>");
			out.println("    <td rowspan='4' bgcolor='#FFFFFF'>"+HStudentCount+"</td>");
			out.println("    <td colspan='2' rowspan='4' bgcolor='#FFFFFF'>　</td>");
			out.println("    <td rowspan='4' bgcolor='#FFFFFF' align='center'>"+(TClassCount+HClassCount)+"</td>");
			out.println("    <td rowspan='4' bgcolor='#FFFFFF' align='center'>"+(TStudentCount+HStudentCount)+"</td>");
			out.println("  </tr>");
			out.println("</table>");
			List totalStudent= manager.CountDepartClass();
			if(request.getParameter("type").equals("html")){

				String deptName;
				switch(dept.charAt(0)){
				case '1':
					deptName="機械工程系";
					break;
				case '2':
					deptName="電機工程系";
					break;
				case '3':
					deptName="電子工程系";
					break;
				case '4':
					deptName="工業工程管理系";
					break;
				case '5':
					deptName="建築工程系";
					break;
				case '6':
					deptName="土木工程系";
					break;
				case '7':
					deptName="企業管理系";
					break;
				case '8':
					deptName="國際貿易系";
					break;
				case '9':
					deptName="財務金融系";
					break;
				case 'A':
					deptName="航空機械系";
					break;
				case 'B':
					deptName="航空電子系";
					break;
				case 'C':
					deptName="航空服務管理系";
					break;
				case 'D':
					deptName="資訊管理系";
					break;
				case 'E':
					deptName="資訊工程系";
					break;
				case 'F':
					deptName="食品科學系";
					break;
				case 'G':
					deptName="機械工程系";
					break;
				case 'H':
					deptName="生物科技系";
					break;
				case 'I':
					deptName="餐飲管理系";
					break;
				case 'J':
					deptName="觀光事業管理系";
					break;
				case 'K':
					deptName="國際企業系";
					break;

				default:
					deptName="什麼？";
			}

				out.println("<br>" +
						"	<table cellpadding='0' cellspacing='1' bgcolor='CFE69F'>" +
						"		<tr>" +
						"			<td bgcolor='#FFFFFF'>" +
						"				<font size='-1'>&nbsp;&nbsp;以上統計" +
						"只包含 <font color=red>" +
						deptName +
						"</font>" +
						"" +
						" 學生, 不包含本校其餘 "+(countStudent(totalStudent)-(TStudentCount+HStudentCount))+" 名跨校, 延修和其它不在此列表的學生&nbsp;&nbsp;" +
						"<br>&nbsp;&nbsp;輸出<a href=/CIS/CountStudent?type=excel&dept="+dept+">excel</a>報表&nbsp;&nbsp;輸出<a href=/CIS/CountStudent?type=word&dept="+dept+">word</a>報表" +
						"			</td>" +
						"		</tr>" +
						"	</table>");
			}




			out.println("</body>");
			out.println("</html>");


			out.close();



		}


	}

	private int countStudent(List list){
		int tmp=0;

		Object obj[]=list.toArray();

		for(int i=0; i<obj.length; i++){
			if(!((Map)obj[i]).get("co").equals(null)){
				tmp=tmp+Integer.parseInt(((Map)obj[i]).get("co").toString());
			}
			//tmp=tmp+1;
		}

		return tmp;
	}

}
