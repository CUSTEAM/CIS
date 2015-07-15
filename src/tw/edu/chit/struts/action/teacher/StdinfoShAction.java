package tw.edu.chit.struts.action.teacher;



import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.model.Member;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
/**
 * 公用學生查詢
 * @author JOHN
 * TODO evgr_time
 */
public class StdinfoShAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, 
			   HttpServletRequest request, HttpServletResponse response)throws Exception {
			
			HttpSession session = request.getSession(false);
			CourseManager manager = (CourseManager) getBean("courseManager");
			
			
			
			
			
			
			setContentPage(request.getSession(false), "teacher/StdinfoSh.jsp");
			return mapping.findForward("Main");
	}
	
	
	
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		CourseManager manager=(CourseManager) getBean("courseManager");
		
		DynaActionForm f=(DynaActionForm) form;
		
		String Cidno=f.getString("Cidno");
		String Sidno=f.getString("Sidno");
		String Didno=f.getString("Didno");
		String Grade=f.getString("Grade");
		String ClassNo=f.getString("ClassNo");
		String studentNo=f.getString("studentNo");
		String studentName=f.getString("studentName");
		String target=f.getString("target");
		String lbegin=f.getString("lbegin");
		String lend=f.getString("lend");
		String sex=f.getString("sex");
		String bbegin=f.getString("bbegin");
		String bend=f.getString("bend");
		
		String ident=f.getString("ident");
		String addr=f.getString("addr");
		String school=f.getString("school");
			
		
		if(target.equals("Gstmd")&&lbegin.trim().equals("")&&lend.trim().equals("")){			
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "離校學生眾多，請至少設定1個年份"));
			saveErrors(request, error);
			return unspecified(mapping, form, request, response);
		}		
		
		StringBuilder sb=new StringBuilder("SELECT s.*,c.ClassName, c5.name,c51.name as team,c3.name as county," +
				"c31.name as province, c52.name as status_name, c53.name as caurse_name," +
				"c54.name as ident_name2 FROM((((((("+target+" s LEFT OUTER JOIN Class c ON " +
				"s.depart_class=c.ClassNo)LEFT OUTER JOIN code5 c5 ON c5.idno=s.ident AND " +
				"c5.category='Identity')LEFT OUTER JOIN code5 c51 ON c51.idno=s.divi AND " +
				"c51.category='GROUP')LEFT OUTER JOIN code3 c3 ON s.birth_county=c3.no)LEFT " +
				"OUTER JOIN code3 c31 ON c31.no=s.birth_province)LEFT OUTER JOIN code5 c52 ON " +
				"s.occur_status=c52.idno AND c52.category='Status')LEFT OUTER JOIN code5 c53 ON " +
				"s.occur_cause=c53.idno AND c53.category='Cause')LEFT OUTER JOIN code5 c54 ON " +
				"s.ident_basic=c54.idno AND c53.category='Identity' WHERE ");
		
		if(!studentNo.trim().equals("")){
			sb.append("s.student_no='"+studentNo+"'");
		}else{
			//if(!Cidno.trim().equals("")){sb.append("c.CampusNo='"+Cidno+"' AND ");}		
			//if(!Sidno.trim().equals("")){sb.append("c.SchoolNo='"+Sidno+"' AND ");}
			//if(!Didno.trim().equals("")){sb.append("c.DeptNo='"+Didno+"' AND ");}
			//if(!Grade.trim().equals("")){sb.append("c.Grade='"+Grade+"' AND ");}
			//if(!ClassNo.trim().equals("")){sb.append("c.ClassNo='"+ClassNo+"' AND ");}
			sb.append("c.ClassNo LIKE'"+Cidno+Sidno+Didno+Grade+ClassNo+"%' AND ");
			if(!lbegin.trim().equals("")){sb.append("s.occur_date>='"+(Integer.parseInt(lbegin)+1911)+"-1-1' AND ");}
			if(!lend.trim().equals("")){sb.append("s.occur_date<='"+(Integer.parseInt(lend)+1911)+"-1-1' AND ");}
			if(!sex.trim().equals("")){sb.append("s.sex='"+sex+"' AND ");}		
			if(!bbegin.trim().equals("")){sb.append("s.birthday>='"+(Integer.parseInt(bbegin)+1911)+"-1-1' AND ");}
			if(!bend.trim().equals("")){sb.append("s.birthday<='"+(Integer.parseInt(bend)+1911)+"-1-1' AND ");}
			if(!ident.trim().equals("")){sb.append("s.ident='"+ident+"' AND ");}
			if(!addr.trim().equals("")){sb.append("(s.curr_addr LIKE'%"+addr+"%' OR s.perm_addr LIKE'%"+addr+"%') AND ");}
			if(!school.trim().equals("")){sb.append("s.schl_name LIKE'%"+school+"%' AND ");}
			if(!studentName.trim().equals("")){sb.append("s.student_name LIKE'%"+studentName+"%' AND ");}
			//sb.delete(sb.length()-5, sb.length());
			sb.append("birthday !='0000-00-00'");//次奧!什麼事都會發生
			sb.append("ORDER BY s.depart_class,s.student_no");
		}
		
		Member me = getUserCredential(request.getSession(false)).getMember();
		manager.executeSql("INSERT INTO User_SQL_hist(ModulName, idno, query)VALUES('StdinfoSh', '"+me.getIdno()+"', \""+sb.toString()+"\");");
		//System.out.println(sb);
		List list=manager.ezGetBy(sb.toString());
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=List4Student.xls");
		Map rmark;
		PrintWriter out = response.getWriter();
		out.println("<style>td{font-size:18px; font-family:微軟正黑體;}</style>");
		out.println("<table border='1'>  ");

		out.println("<tr bgcolor='#dddddd'>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>班級名稱</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>班級代碼</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>姓名</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>學號</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>性別</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>身分證字號</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>出生日期</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>入學年月</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>前學程畢業</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>身份</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>組別</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>出生省縣</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>出生鄉鎮市</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>電話</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>行動電話</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>現居郵編</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>現居地址</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>永久郵編</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>永久地址</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>畢業學校代碼</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>畢業學校名稱</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>畢業科系</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>畢業狀態</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>家長姓名</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>最後變更學年</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>最後變更學期</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>最後變更狀態</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>最後變更原因</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>最後變更日期</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>最後變更文號</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>畢業號</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>輔系/雙主修</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>輔系/雙主修科系</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>電子郵件</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>身份備註</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>英譯姓名</td>");
		out.println("</tr>");		
		for(int i=0; i<list.size(); i++){
			
			if(i%2==0){
				out.println("<tr>");
			}else{
				out.println("<tr bgcolor='#dddddd'>");
			}
			
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+ ((Map)list.get(i)).get("depart_class") + "</td>");
			out.println("<td align='center' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("ClassName") + "</td>");

			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+ ((Map)list.get(i)).get("student_name") + "</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+ ((Map)list.get(i)).get("student_no") + "</td>");

			// 性別
			if (((Map)list.get(i)).get("sex").equals("1")) {
				out.println("<td align='center' style='mso-number-format:\\@' nowrap>男</td>");
			} else {
				out.println("<td align='center' style='mso-number-format:\\@' nowrap>女</td>");
			}
			
			out.println("<td align='center' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("idno") + "</td>");// 身份證
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+ ((Map)list.get(i)).get("birthday") + "</td>"); // 生日
			if (((Map)list.get(i)).get("entrance") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("entrance") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("gradyear") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("gradyear") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("name") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("name") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("team") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("team") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("province") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("province") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("county") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"
						+ ((Map)list.get(i)).get("county") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("telephone") != null) {
				out.println("<td align='left' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("telephone") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("CellPhone") != null) {
				out.println("<td align='left' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("CellPhone") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("curr_post") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("curr_post") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("curr_addr") != null) {
				out.println("<td align='left' style='mso-number-format:\\@' nowrap>"+ ((Map)list.get(i)).get("curr_addr") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("perm_post") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("perm_post") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("perm_addr") != null) {
				out.println("<td align='left' style='mso-number-format:@' nowrap>"+ ((Map)list.get(i)).get("perm_addr") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("schl_code") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("schl_code") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("schl_name") != null) {
				out.println("<td align='left' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("schl_name") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("grad_dept") != null) {
				out.println("<td align='left' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("grad_dept") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("gradu_status") != null) {
				if (((Map)list.get(i)).get("gradu_status").equals("1")) {
					out.println("<td align='center' style='mso-number-format:\\@'>畢</td>");
				} else {
					out.println("<td align='center' style='mso-number-format:\\@'>肆</td>");
				}
			} else {
				out.println("<td align='center' style='mso-number-format:\\@'>啥</td>");
			}

			if (((Map)list.get(i)).get("parent_name") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("parent_name") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("occur_year") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("occur_year") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("occur_term") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("occur_term") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("status_name") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("status_name") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("caurse_name") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("caurse_name") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("occur_date") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("occur_date") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("occur_docno") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("occur_docno") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("occur_graduate_no") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("occur_graduate_no") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("ExtraStatus") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("ExtraStatus") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("ExtraDept") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("ExtraDept") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("Email") != null) {
				out.println("<td align='left' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("Email") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			// out.println("<td align='left'
			// style='mso-number-format:\\@'>"+((Map)list.get(i)).get("ident_remark")+"</td>");
			rmark = manager.ezGetMap("SELECT * FROM Rmark WHERE student_no='"
					+ ((Map)list.get(i)).get("student_no") + "'");
			if (rmark != null) {
				out.println("<td align='left' style='mso-number-format:\\@'>"+ rmark.get("remark_name") + rmark.get("military")+ rmark.get("certificate") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (((Map)list.get(i)).get("student_ename") != null) {
				out.println("<td align='left' style='mso-number-format:\\@'>"+ ((Map)list.get(i)).get("student_ename") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			out.println("	</tr>");			
		}
		out.println("</table>");
		out.close();
		//return unspecified(mapping, form, request, response);
		return null;
	}
	
	public ActionForward clear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		CourseManager manager=(CourseManager) getBean("courseManager");
		DynaActionForm f=(DynaActionForm) form;
		
		//String type=f.getString("type");
		//String Dtime_oid=f.getString("Dtime_oid");
		
		
		
		return unspecified(mapping, form, request, response);
	}

	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Query", "query");
		map.put("Clear", "clear");
		return map;
	}

}