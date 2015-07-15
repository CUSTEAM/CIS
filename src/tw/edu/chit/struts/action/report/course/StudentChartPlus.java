package tw.edu.chit.struts.action.report.course;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.model.CsCore;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseAction;

public class StudentChartPlus extends BaseAction{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {	
		
		response.setContentType("text/xml; charset=UTF-8");		
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		PrintWriter out=response.getWriter();		
		response.setHeader("Content-disposition","attachment;filename=CsCore2XML4One.xml");				
		
		Map c=null;		
		if(request.getParameter("student_no")==null){
			HttpSession session = request.getSession(false);
			UserCredential me = (UserCredential) session.getAttribute("Credential");
			c=manager.ezGetMap("SELECT * FROM stmd WHERE student_no='"+me.getMember().getAccount()+"'");
			if(c==null){
				c=manager.ezGetMap("SELECT * FROM Gstmd WHERE student_no='"+request.getParameter("student_no")+"'");
			}
		}else{
			c=manager.ezGetMap("SELECT * FROM stmd WHERE student_no='"+request.getParameter("student_no")+"'");			
		}
		
		out.println ("<chart>");
		out.println("	<axis_category size='12' font='arial, 新細明體' color='555555' alpha='0' />");
		out.println("	<axis_ticks value_ticks='' category_ticks='' font='arial, 新細明體'/>");
		out.println("	<axis_value alpha='0' min='6' max='10' font='arial, 新細明體' />");
		out.println ("	<chart_border bottom_thickness='0' left_thickness='0' />");
		out.println ("	<chart_data>");
		
		//學生系所
		String myDept;
		try{
			myDept=manager.ezGetString("SELECT DeptNo FROM Class WHERE ClassNo='"+c.get("depart_class")+"'");
		}catch(Exception e){
			myDept=manager.ezGetString("SELECT DeptNo FROM Class c, Gstmd s WHERE s.depart_class=c.ClassNo AND s.student_no='"+c.get("student_no")+"'");
		}
		
		Map all=getStudent(c, myDept);
		
		out.println ("		<row>");
		out.println ("			<null/>");
		out.println ("			<string>技術</string>");
		out.println ("			<string>知識</string>");
		out.println ("			<string>資格</string>");
		out.println ("			<string>進取</string>");
		out.println ("			<string>溝通</string>");
		out.println ("			<string>團隊</string>");
		out.println ("			<string>科技</string>");
		out.println ("			<string>解決問題</string>");
		out.println ("			<string>自我管理</string>");
		out.println ("			<string>規劃</string>");
		out.println ("			<string>學習</string>");		
		out.println ("		</row>");
		
		out.println ("		<row>");
		out.println ("			<string>成績換算能力指標</string>");
		try{
			out.println ("			<number>"+all.get("s1")+"</number>");
			out.println ("			<number>"+all.get("s2")+"</number>");
			out.println ("			<number>"+all.get("s3")+"</number>");
			out.println ("			<number>"+all.get("s4")+"</number>");
			out.println ("			<number>"+all.get("s5")+"</number>");
			out.println ("			<number>"+all.get("s6")+"</number>");
			out.println ("			<number>"+all.get("s7")+"</number>");
			out.println ("			<number>"+all.get("s8")+"</number>");
			out.println ("			<number>"+all.get("s9")+"</number>");
			out.println ("			<number>"+all.get("sa")+"</number>");
			out.println ("			<number>"+all.get("sb")+"</number>");
			
		}catch(Exception e){
			
		}
		out.println ("		</row>");		
		//TODO 真難看要拿掉
		out.println ("		<row>");
		out.println ("			<string>成績換算能力指標</string>");
		out.println ("			<number>10</number>");
		out.println ("			<number>10</number>");
		out.println ("			<number>10</number>");
		out.println ("			<number>10</number>");
		out.println ("			<number>10</number>");
		out.println ("			<number>10</number>");
		out.println ("			<number>10</number>");
		out.println ("			<number>10</number>");
		out.println ("			<number>10</number>");
		out.println ("			<number>10</number>");
		out.println ("			<number>10</number>");		
		out.println ("		</row>");
		
		
		out.println ("	</chart_data>");
		
		
		out.println("	<chart_grid_h alpha='20' color='000000' thickness='1' type='dashed' />");
		out.println("	<chart_grid_v alpha='5' color='000000' thickness='20' type='solid' />");
		
		out.println ("	<chart_pref line_thickness='1' point_size='5' fill_shape='true' type='line' grid='circular' />");		
		//out.println("	<chart_pref point_shape='circle' point_size='5' fill_shape='false' grid='circular' />");		
		out.println("	<chart_rect bevel='bevel1' x='60' y='40' width='270' height='225' positive_color='008888' positive_alpha='10' />");
		out.println("	<chart_transition type='zoom' delay='0.5' duration='0.5' order='series' />");
		out.println("	<chart_type>polar</chart_type>");		
		
		out.println("	<draw>");
		out.println ("		<text shadow='shadow2' color='cccccc' alpha='25' rotation='0' size='24' x='340' y='-30' " +
				"width='390' height='295' h_align='right' v_align='bottom' font='arial, 新細明體'>" +
				c.get("student_name")+"的核心課程能力指標</text>");
		out.println ("		<circle bevel='bevel1' layer='background' x='195' y='150' radius='170' fill_alpha='0' line_color='000000' line_alpha='5' line_thickness='40' />");
		out.println("	</draw>");
		
		out.println("	<filter>");
		out.println("		<shadow id='shadow1' distance='2' angle='45' color='0' alpha='100' blurX='10' blurY='10' />");
		out.println("		<shadow id='shadow2' distance='1' angle='45' color='0' alpha='50' blurX='3' blurY='3' />");
		out.println("		<bevel id='bevel1' angle='45' blurX='10' blurY='10' distance='5' highlightAlpha='25' highlightColor='ffffff' shadowAlpha='35' type='outer' />");
		out.println("	</filter>");
		
		out.println("	<legend_label layout='vertical' bullet='line' size='12' font='arial, 新細明體' color='555555' alpha='90' />");
		out.println("	<legend_rect x='475' y='0' width='20' height='70' margin='5' fill_alpha='10' />");
		out.println("	<legend shadow='shadow2' x='20' y='100' width='20' height='40' margin='3' fill_alpha='0' layout='vertical' bullet='circle' size='12' color='4e627c' alpha='75' />");
		
		out.println("	<series_color>");
		out.println("		<color>ff4400</color>");
		out.println("		<color>4e627c</color>");
		out.println("	</series_color>");
		out.println("</chart>");
		out.close();
		return null;
	}
	
	/**有效的
	 * 取個人核心
	 * @param student_no
	 * @return
	 */
	private Map getStudent(Map c, String DeptNo){
		CourseManager manager = (CourseManager) getBean("courseManager");		
		
		
		float s1=0;
		float s2=0;
		float s3=0;
		float s4=0;
		float s5=0;
		float s6=0;
		float s7=0;
		float s8=0;
		float s9=0;
		float sa=0;
		float sb=0;
		
		float ss1=0;
		float ss2=0;
		float ss3=0;
		float ss4=0;
		float ss5=0;
		float ss6=0;
		float ss7=0;
		float ss8=0;
		float ss9=0;
		float ssa=0;
		float ssb=0;
		
		
		
		int pass=manager.getPassScore(null, c.get("student_no").toString());//及格標準
		
		//系所核心框架20091203加上通識
		List CsCore=manager.ezGetBy("SELECT * FROM CsCore WHERE (deptNo='"+DeptNo+"' OR deptNo='0')");//系核心課程
		List StScore;		
		
		String key_word;
		//Float total;
		
		Float tmp;
		
		int sumStsize=0;
		//核心課程迴圈
		for(int i=0; i<CsCore.size(); i++){	
			//sumStsize=sumStsize+1;
			key_word=((Map)CsCore.get(i)).get("key_word").toString();			
			StScore=manager.ezGetBy("SELeCT s.score, c.chi_name FROM ScoreHist s, Csno c WHERE " +
					"s.cscode=c.cscode AND s.student_no='"+c.get("student_no")+"' AND " +
					"chi_name LIKE'"+((Map)CsCore.get(i)).get("key_word").toString()+"%' AND s.score>="+pass);
			
			tmp=0f;
			if(StScore.size()>0){
				
				for(int j=0; j<StScore.size(); j++){	
					sumStsize=sumStsize+1;					
					tmp=Float.parseFloat(((Map)StScore.get(j)).get("score").toString());
					ss1=ss1+((Float.parseFloat(((Map)CsCore.get(i)).get("s1").toString())));
					ss2=ss2+((Float.parseFloat(((Map)CsCore.get(i)).get("s2").toString())));
					ss3=ss3+((Float.parseFloat(((Map)CsCore.get(i)).get("s3").toString())));
					ss4=ss4+((Float.parseFloat(((Map)CsCore.get(i)).get("s4").toString())));
					ss5=ss5+((Float.parseFloat(((Map)CsCore.get(i)).get("s5").toString())));
					ss6=ss6+((Float.parseFloat(((Map)CsCore.get(i)).get("s6").toString())));
					ss7=ss7+((Float.parseFloat(((Map)CsCore.get(i)).get("s7").toString())));
					ss8=ss8+((Float.parseFloat(((Map)CsCore.get(i)).get("s8").toString())));
					ss9=ss9+((Float.parseFloat(((Map)CsCore.get(i)).get("s9").toString())));
					ssa=ssa+((Float.parseFloat(((Map)CsCore.get(i)).get("sa").toString())));
					ssb=ssb+((Float.parseFloat(((Map)CsCore.get(i)).get("sb").toString())));
					
					s1=s1+(tmp*(Float.parseFloat(((Map)CsCore.get(i)).get("s1").toString())/100));
					s2=s2+(tmp*(Float.parseFloat(((Map)CsCore.get(i)).get("s2").toString())/100));
					s3=s3+(tmp*(Float.parseFloat(((Map)CsCore.get(i)).get("s3").toString())/100));
					s4=s4+(tmp*(Float.parseFloat(((Map)CsCore.get(i)).get("s4").toString())/100));
					s5=s5+(tmp*(Float.parseFloat(((Map)CsCore.get(i)).get("s5").toString())/100));
					s6=s6+(tmp*(Float.parseFloat(((Map)CsCore.get(i)).get("s6").toString())/100));
					s7=s7+(tmp*(Float.parseFloat(((Map)CsCore.get(i)).get("s7").toString())/100));
					s8=s8+(tmp*(Float.parseFloat(((Map)CsCore.get(i)).get("s8").toString())/100));
					s9=s9+(tmp*(Float.parseFloat(((Map)CsCore.get(i)).get("s9").toString())/100));
					sa=sa+(tmp*(Float.parseFloat(((Map)CsCore.get(i)).get("sa").toString())/100));
					sb=sb+(tmp*(Float.parseFloat(((Map)CsCore.get(i)).get("sb").toString())/100));
				}
			}
		}
		
		int target=sumStsize*CsCore.size();
		Map map=new HashMap();
		
		map.put("s1", (s1/ss1)*10);
		map.put("s2", (s2/ss2)*10);
		map.put("s3", (s3/ss3)*10);
		map.put("s4", (s4/ss4)*10);
		map.put("s5", (s5/ss5)*10);
		map.put("s6", (s6/ss6)*10);
		map.put("s7", (s7/ss7)*10);
		map.put("s8", (s8/ss8)*10);
		map.put("s9", (s9/ss9)*10);
		map.put("sa", (sa/ssa)*10);
		map.put("sb", (sb/ssb)*10);	
		
		return map;
	}
}
