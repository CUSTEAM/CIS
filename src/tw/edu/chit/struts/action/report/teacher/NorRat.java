package tw.edu.chit.struts.action.report.teacher;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.model.Dtime;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseAction;
import tw.edu.chit.util.Global;

public class NorRat extends BaseAction{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {	
		CourseManager manager = (CourseManager) getBean("courseManager");		
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=NorRatt.xls");

		String dtimeOid = request.getParameter("dtimeOid");
		PrintWriter out = response.getWriter();
		out.println("<html>");

		Map map = manager.getdtimeBy(dtimeOid);
		//System.out.println(map);

		out.println("<style>");
		out.println("<!--table");
		out.println("@page");
		out.println("	{margin:.0in .2in .5in .2in;");
		out.println("	mso-header-margin:.51in;");
		out.println("	mso-footer-margin:.51in;}");
		out.println("-->");
		out.println("</style>");

		String x = "16";
		List list = manager.ezGetBy("SELECT cl.ClassName, st.student_no, st.student_name, "
						+ "s.* "
						+ "FROM Seld s, stmd st, Class cl WHERE s.student_no=st.student_no AND "
						+ "cl.ClassNo=st.depart_class AND s.Dtime_oid='"
						+ dtimeOid + "' ORDER BY st.student_no");		
		int pass=60;
		int sp=0;//總
		int sp1=0;//期中
		int sp2=0;//期末
		
		try{
			if(map.get("depart_class").toString().indexOf("G")>-1){
				pass=70;
			}
			for(int i=0; i<list.size(); i++){
				
				if(((Map)list.get(i)).get("score")!=null && !((Map)list.get(i)).get("score").equals("")){
					if(Float.parseFloat(((Map)list.get(i)).get("score").toString())<pass){
						sp=sp+1;
					}
				}
				if(((Map)list.get(i)).get("score2")!=null && !((Map)list.get(i)).get("score2").equals("")){
					if(Float.parseFloat(((Map)list.get(i)).get("score2").toString())<pass){
						sp1=sp1+1;
					}
				}
				if(((Map)list.get(i)).get("score3")!=null && !((Map)list.get(i)).get("score3").equals("")){
					if(Float.parseFloat(((Map)list.get(i)).get("score3").toString())<pass){
						sp2=sp2+1;
					}
				}
			}
		}catch(Exception e){
			
		}
		
		
		
		
		if(request.getParameter("level").equals("f")){
			
			int page = 1;
			
			SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			List dtimeclass;
			StringBuilder sb;
			Map pro;
			for (int i = 0; i < list.size(); i++) {

				if (i % 42 == 0) {
					out.println("<table><tr></tr><tr><td align='right' colspan='" + x + "'>列印日期 "+ sf.format(new Date()) + ", 第" + page + "頁*</td></tr>" +
							"<tr><td align='center' style='font-size:24px; font-family:標楷體; font-weight: bold;' colspan='"+x+"'>中華科技大學 "+manager.getSchoolYear()+"學年第 "+manager.getSchoolTerm()+"學期 "+map.get("ClassName")+"成績冊</td></tr></table>");
					
					
					out.println("<table border=1>  ");
					
					out.println("  <tr>");
					out.println("		<td align='center' style='font-size:20px;' colspan='"+x+"'>課程名稱: "+ map.get("chi_name") + "</td>");
					out.println("  </tr>");
					
					
					
					if(page==1){
						try{
							dtimeclass=manager.ezGetBy("SELECT * FROM Dtime_class WHERE Dtime_oid="+map.get("Oid"));//上課時間
							sb=new StringBuilder();
							for(int j=0; j<dtimeclass.size(); j++){						
								try{
									sb.append("星期"+manager.getWeekChar(Integer.parseInt(((Map)dtimeclass.get(j)).get("week").toString())));
								}catch(Exception e){
									sb.append(", ");
								}						
								sb.append(((Map)dtimeclass.get(j)).get("begin")+"~");
								sb.append(((Map)dtimeclass.get(j)).get("end")+"節, ");
							}
							if(sb.length()>1){
								sb.delete(sb.length()-2, sb.length());
							}
							out.println("  <tr>");
							out.println("<td align='left' colspan='13'>"+Global.CourseOpt.getProperty(map.get("opt").toString())+"課程, "
									+map.get("credit")+"學分, "+map.get("thour")+"小時, 學生"+manager.ezGetString("SELECT COUNT(*)FROM Seld WHERE Dtime_oid="+map.get("Oid"))+"人, 上課時間:"+
									sb+"</td><td colspan='3'rowspan='2' valign='top'>簽章</td>");
							out.println("  </tr>");
						}catch(Exception e){
							out.println("<td align='left' colspan='13'></td><td colspan='3'rowspan='2' valign='top'>簽章</td>");
							out.println("  </tr>");
						}
						
						
						
						
						
						
						
						
						out.println("  <tr>");
						out.println("		<td align='left' colspan='13'>期中考不及格人數 "+sp1+"人, 期末考不及格人數 "+sp2+"人, 總成績不及格人數 "+sp+"人, 授課教師 "+map.get("cname")+"</td>");
						out.println("  </tr>");
					}
					
					page = page + 1;
					
					
					pro=manager.ezGetMap("SELECT * FROM SeldPro WHERE Dtime_oid="+map.get("Oid"));
					out.println("  <tr>");
					out.println("		<td align='center'>學號</td>");
					out.println("		<td align='center'>姓名</td>");
					out.println("		<td align='center'>平時</td>");
					out.println("		<td align='center'>平時</td>");
					out.println("		<td align='center'>平時</td>");
					out.println("		<td align='center'>平時</td>");
					out.println("		<td align='center'>平時</td>");
					out.println("		<td align='center'>平時</td>");
					out.println("		<td align='center'>平時</td>");
					out.println("		<td align='center'>平時</td>");
					out.println("		<td align='center'>平時</td>");
					out.println("		<td align='center'>平時</td>");

					
					if(pro==null){
						out.println("		<td align='center'>平時30%</td>");
						out.println("		<td align='center'>期中考30%</td>");
						out.println("		<td align='center'>期末考40%</td>");
					}else{
						out.println("		<td align='center'>平時"+pro.get("score1")+"%</td>");
						out.println("		<td align='center'>期中考"+pro.get("score2")+"%</td>");
						out.println("		<td align='center'>期末考"+pro.get("score3")+"%</td>");
					}
					
					out.println("		<td align='center'>總成績</td>");
					out.println("	</tr>");

				}

				if (i % 2 == 1) {
					out.println("  <tr>");
				} else {
					out.println("  <tr bgcolor='#f0fcd7'>");
				}

				out.println("		<td align='left' style='mso-number-format:\\@'>"
						+ ((Map) list.get(i)).get("student_no") + "</td>");
				out.println("		<td align='center'>"
						+ ((Map) list.get(i)).get("student_name") + "</td>");

				// 平時6次
				if (((Map) list.get(i)).get("score01") != null && !((Map) list.get(i)).get("score01").equals("")) {
					out.println("		<td align='center'>"
							+ ((Map) list.get(i)).get("score01") + "</td>");
				} else {
					out.println("		<td align='center'>-</td>");
				}
				if (((Map) list.get(i)).get("score02") != null && !((Map) list.get(i)).get("score02").equals("")) {
					out.println("		<td align='center'>"
							+ ((Map) list.get(i)).get("score02") + "</td>");
				} else {
					out.println("		<td align='center'>-</td>");
				}
				if (((Map) list.get(i)).get("score03") != null && !((Map) list.get(i)).get("score03").equals("")) {
					out.println("		<td align='center'>"
							+ ((Map) list.get(i)).get("score03") + "</td>");
				} else {
					out.println("		<td align='center'>-</td>");
				}
				if (((Map) list.get(i)).get("score04") != null && !((Map) list.get(i)).get("score04").equals("")) {
					out.println("		<td align='center'>"
							+ ((Map) list.get(i)).get("score04") + "</td>");
				} else {
					out.println("		<td align='center'>-</td>");
				}
				if (((Map) list.get(i)).get("score05") != null && !((Map) list.get(i)).get("score05").equals("")) {
					out.println("		<td align='center'>"
							+ ((Map) list.get(i)).get("score05") + "</td>");
				} else {
					out.println("		<td align='center'>-</td>");
				}
				if (((Map) list.get(i)).get("score06") != null && !((Map) list.get(i)).get("score06").equals("")) {
					out.println("		<td align='center'>"
							+ ((Map) list.get(i)).get("score06") + "</td>");
				} else {
					out.println("		<td align='center'>-</td>");
				}
				
				if (((Map) list.get(i)).get("score07") != null && !((Map) list.get(i)).get("score07").equals("")) {
					out.println("		<td align='center'>"+ ((Map) list.get(i)).get("score07") + "</td>");
				} else {
					out.println("		<td align='center'>-</td>");
				}
				

				if (((Map) list.get(i)).get("score08") != null&& !((Map) list.get(i)).get("score08").equals("")) {
					out.println("		<td align='center'>"+ ((Map) list.get(i)).get("score08") + "</td>");
				} else {
					out.println("		<td align='center'>-</td>");
				}
				
				if (((Map) list.get(i)).get("score09") != null&& !((Map) list.get(i)).get("score09").equals("")) {
					out.println("		<td align='center'>"
							+ ((Map) list.get(i)).get("score09") + "</td>");
				} else {
					out.println("		<td align='center'>-</td>");
				}
				
				if (((Map) list.get(i)).get("score10") != null&& !((Map) list.get(i)).get("score10").equals("")) {
					out.println("		<td align='center'>"
							+ ((Map) list.get(i)).get("score10") + "</td>");
				} else {
					out.println("		<td align='center'>-</td>");
				}
				
				if (((Map) list.get(i)).get("score1") != null) {
					out.println("		<td align='right'>"
							+ ((Map) list.get(i)).get("score1") + "</td>");
				} else {
					out.println("		<td align='center'>-</td>");
				}
				

				if (((Map) list.get(i)).get("score2") != null) {
					out.println("		<td align='center'>"
							+ ((Map) list.get(i)).get("score2") + "</td>");
				} else {
					out.println("		<td align='center'>-</td>");
				}			

				if (((Map) list.get(i)).get("score3") != null) {
					out.println("		<td align='center'>"
							+ ((Map) list.get(i)).get("score3") + "</td>");
				} else {
					out.println("		<td align='center'>-</td>");
				}			

				try{
					float f = Float.parseFloat(((Map) list.get(i)).get("score").toString());
					out.println("		<td align='right'>"+ manager.roundOff(f, 0)+"</td>");
				}catch(Exception e){
					out.println("		<td align='center'>-</td>");
				}

				out.println("	</tr>");

			}
			
		}else{
			
			//期中			
			SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			List dtimeclass;
			StringBuilder sb;
			
			out.println("<table><tr></tr><tr><td align='right' colspan='10'>列印日期 "+ sf.format(new Date()) + "</td></tr>" +
			"<tr><td align='center' style='font-size:24px; font-family:標楷體; font-weight: bold;' colspan='10'>中華科技大學 "+
			manager.getSchoolYear()+"學年第 "+manager.getSchoolTerm()+"學期"+map.get("ClassName")+"成績冊</td></tr></table>");
			
			
			out.println("<table border=1>  ");
			
			out.println("  <tr>");
			out.println("		<td align='center' style='font-size:20px;' colspan='10'>課程名稱: "+ map.get("chi_name") + "</td>");
			out.println("  </tr>");
			

			try{
				dtimeclass=manager.ezGetBy("SELECT * FROM Dtime_class WHERE Dtime_oid="+map.get("Oid"));//上課時間
				sb=new StringBuilder();
				for(int j=0; j<dtimeclass.size(); j++){						
					try{
						sb.append("星期"+manager.getWeekChar(Integer.parseInt(((Map)dtimeclass.get(j)).get("week").toString())));
					}catch(Exception e){
						sb.append(", ");
					}						
					sb.append(((Map)dtimeclass.get(j)).get("begin")+"~");
					sb.append(((Map)dtimeclass.get(j)).get("end")+"節, ");
				}
				if(sb.length()>1){
					sb.delete(sb.length()-2, sb.length());
				}
				out.println("  <tr>");
				out.println("<td align='left' colspan='7'>"+Global.CourseOpt.getProperty(map.get("opt").toString())+"課程, "
						+map.get("credit")+"學分, "+map.get("thour")+"小時, 學生"+manager.ezGetString("SELECT COUNT(*)FROM Seld WHERE Dtime_oid="+map.get("Oid"))+"人, 上課時間:"+
						sb+"</td><td colspan='3'rowspan='2' valign='top'>簽章</td>");
				out.println("  </tr>");
			}catch(Exception e){
				out.println("<td align='left' colspan='3'></td><td colspan='3'rowspan='3' valign='top'>簽章</td>");
				out.println("  </tr>");
			}
			
			out.println("  <tr>");
			out.println("		<td align='left' colspan='8'>期中考不及格人數 "+sp1+"人, 授課教師 "+map.get("cname")+"</td>");
			out.println("  </tr>");			
			
			out.println("		<tr>");
			out.println("		<td colspan='5'>");
			
			
			out.println("<table border='1'>");
			out.println("  <tr>");
			out.println("		<td align='center' colspan='2'>學號</td>");
			out.println("		<td align='center' colspan='2'>姓名</td>");
			out.println("		<td align='center'>期中成績</td>");
			out.println("	</tr>");
			
			for (int i = 0; i < 42; i++) {
				
				if(i%2!=0){
					out.println("  <tr bgcolor='#f0fcd7'>");						
				}else{
					out.println("  <tr>");
				}
				
				try{
					out.println("<td align='center' colspan='2' style='mso-number-format:\\@'>"+ ((Map) list.get(i)).get("student_no") + "</td>");
					out.println("<td align='center' colspan='2'>"+ ((Map) list.get(i)).get("student_name") + "</td>");
					if (((Map) list.get(i)).get("score2") != null) {
						out.println("<td align='center'>"+ ((Map) list.get(i)).get("score2") + "</td>");
					} else {
						out.println("<td align='center'>-</td>");
					}
				}catch(IndexOutOfBoundsException e){
					out.println("<td align='center' colspan='2' style='mso-number-format:\\@' bgcolor='#f0fcd7'></td>");
					out.println("<td align='center' colspan='2'></td>");
					out.println("<td align='center'></td>");
				}
				out.println("</tr>");
			}
			
			
			
			out.println("</table></td><td colspan='5'>");
			
			
			
			
			//另外一邊
			out.println("<table border='1'>");
			out.println("  <tr>");
			out.println("		<td align='center' colspan='2'>學號</td>");
			out.println("		<td align='center' colspan='2'>姓名</td>");
			out.println("		<td align='center'>期中成績</td>");
			out.println("	</tr>");
			
			
			if(list.size()>42){
				for (int i = 42; i < list.size(); i++) {
					
					if(i%2==0){
						out.println("  <tr bgcolor='#f0fcd7'>");						
					}else{
						out.println("  <tr>");
					}
					
					
					out.println("<td align='center' colspan='2' style='mso-number-format:\\@'>"+ ((Map) list.get(i)).get("student_no") + "</td>");
					out.println("<td align='center' colspan='2'>"+ ((Map) list.get(i)).get("student_name") + "</td>");
					

					if (((Map) list.get(i)).get("score2") != null) {
						out.println("<td align='center'>"+ ((Map) list.get(i)).get("score2") + "</td>");
					} else {
						out.println("<td align='center'>-</td>");
					}
					out.println("</tr>");
				}
			}
			
			
			
			
			if(list.size()<42){
				for(int i=0; i<42; i++){
					out.println("  <tr>");
					out.println("<td align='center' colspan='2'></td>");
					out.println("<td align='center' colspan='2'></td>");
					out.println("<td align='center'></td>");
					out.println("  </tr>");
					if(i>=42)continue;
				}
			}else{
				
				for(int i=0; i<42-(list.size()-42); i++){
					out.println("  <tr>");
					out.println("<td align='center' colspan='2'></td>");
					out.println("<td align='center' colspan='2'></td>");
					out.println("<td align='center'></td>");
					out.println("  </tr>");
					if(i>=42)continue;
				}
			}			
			out.println("</table>");
			out.println("</td></tr></table>");			
		
		}
		
		

		out.println("</table>");
		
		out.println("</html>");
		
		out.close();
		return null;
	}
	
}