package tw.edu.chit.struts.action.report.course;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.edu.chit.model.CsCore;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseAction;

public class CsCoreChart extends BaseAction{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {	
		
		response.setContentType("text/xml; charset=UTF-8");		
		CourseManager manager = (CourseManager) getBean("courseManager");
		//HttpSession session = request.getSession(false);
		PrintWriter out=response.getWriter();		
		response.setHeader("Content-disposition","attachment;filename=CsCore2XML4One.xml");
		
		out.println ("<chart>");
		out.println("	<axis_category size='12' font='arial, 新細明體' color='555555' alpha='0' />");
		out.println("	<axis_ticks value_ticks='' category_ticks='' font='arial, 新細明體'/>");
		out.println("	<axis_value alpha='0' min='0' font='arial, 新細明體' />");
		out.println ("	<chart_border bottom_thickness='0' left_thickness='0' />");
		out.println ("	<chart_data>");		
		
		Map dtime=manager.ezGetMap("SELeCT d.*, c.DeptNo, c.ClassName FROM Dtime d, Class c WHERE d.depart_class=c.ClassNo AND d.Oid="+request.getParameter("Oid"));
		
		
		
		Map csno=manager.ezGetMap("SELECT d.*, c.chi_name, c.eng_name FROM Dtime d, Csno c WHERE " +
		"d.cscode=c.cscode AND Sterm='"+manager.getSchoolTerm()+"' AND d.cscode = '"+dtime.get("cscode")+"' AND d.depart_class='"+dtime.get("depart_class")+"'LIMIT 1");
		
		
		
		Map all=getCourse(csno, dtime.get("DeptNo").toString());		
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
		out.println ("			<string>"+csno.get("cscode")+": "+csno.get("chi_name")+"</string>");
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
		out.println ("		</row>");
		
		
		
		/*
		out.println ("		<row>");
		out.println ("			<string>課程核心能力指標</string>");
		out.println ("			<number>"+dall.get("s1")+"</number>");
		out.println ("			<number>"+dall.get("s2")+"</number>");
		out.println ("			<number>"+dall.get("s3")+"</number>");
		out.println ("			<number>"+dall.get("s4")+"</number>");
		out.println ("			<number>"+dall.get("s5")+"</number>");
		out.println ("			<number>"+dall.get("s6")+"</number>");
		out.println ("			<number>"+dall.get("s7")+"</number>");
		out.println ("			<number>"+dall.get("s8")+"</number>");
		out.println ("			<number>"+dall.get("s9")+"</number>");
		out.println ("			<number>"+dall.get("sa")+"</number>");
		out.println ("			<number>"+dall.get("sb")+"</number>");
		out.println ("		</row>");
		*/
		
		
		out.println ("	</chart_data>");
		
		
		out.println("	<chart_grid_h alpha='20' color='000000' thickness='1' type='dashed' />");
		out.println("	<chart_grid_v alpha='5' color='000000' thickness='20' type='solid' />");
		
		out.println ("	<chart_pref line_thickness='1' point_size='5' fill_shape='true' type='line' grid='circular' />");		
		//out.println("	<chart_pref point_shape='circle' point_size='5' fill_shape='false' grid='circular' />");		
		out.println("	<chart_rect bevel='bevel1' x='60' y='40' width='350' height='225' positive_color='008888' positive_alpha='10' />");
		out.println("	<chart_transition type='zoom' delay='0.5' duration='0.5' order='series' />");
		out.println("	<chart_type>polar</chart_type>");		
		
		out.println("	<draw>");
		out.println ("		<text shadow='shadow2' color='cccccc' alpha='25' rotation='0' size='24' x='300' y='-30' " +
				"width='390' height='295' h_align='right' v_align='bottom' font='arial, 新細明體'>" +dtime.get("ClassName")+": "+csno.get("chi_name")+"</text>");
		out.println ("		<circle bevel='bevel1' layer='background' x='235' y='150' radius='170' fill_alpha='0' line_color='000000' line_alpha='5' line_thickness='40' />");
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
	
	/**3
	 * 取個人核心
	 * @param student_no
	 * @return
	 */
	private Map getCourse(Map csno, String DeptNo){
		
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
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		List CsCore=manager.hqlGetBy("FROM CsCore WHERE deptNo='"+DeptNo+"'");	
		
		Map map=new HashMap();;
		//System.out.println(csno);
		
		String chi_name="";
		if(csno.get("chi_name")!=null){
			chi_name=csno.get("chi_name").toString();
		}
		
		//核心課程迴圈
		for(int i=0; i<CsCore.size(); i++){
			
				if(chi_name.indexOf(((CsCore)CsCore.get(i)).getKeyWord())>-1){
					map.put("s1", ((CsCore)CsCore.get(i)).getS1());
					map.put("s2", ((CsCore)CsCore.get(i)).getS2());
					map.put("s3", ((CsCore)CsCore.get(i)).getS3());
					map.put("s4", ((CsCore)CsCore.get(i)).getS4());
					map.put("s5", ((CsCore)CsCore.get(i)).getS5());
					map.put("s6", ((CsCore)CsCore.get(i)).getS6());
					map.put("s7", ((CsCore)CsCore.get(i)).getS7());
					map.put("s8", ((CsCore)CsCore.get(i)).getS8());
					map.put("s9", ((CsCore)CsCore.get(i)).getS9());
					map.put("sa", ((CsCore)CsCore.get(i)).getSa());
					map.put("sb", ((CsCore)CsCore.get(i)).getSb());
				}
				
		}			
		return map;
	}
	
	/**
	 * 取系核心
	 * @param DeptNo
	 * @return
	 */
	private Map getDept(String DeptNo){
		
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
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		//系所核心
		List CsCore=manager.hqlGetBy("FROM CsCore WHERE deptNo='"+DeptNo+"'");
		
		Map map;
		
		for(int i=0; i<CsCore.size(); i++){			
			s1=s1+((CsCore)CsCore.get(i)).getS1();
			s2=s2+((CsCore)CsCore.get(i)).getS2();
			s3=s3+((CsCore)CsCore.get(i)).getS3();
			s4=s4+((CsCore)CsCore.get(i)).getS4();
			s5=s5+((CsCore)CsCore.get(i)).getS5();
			s6=s6+((CsCore)CsCore.get(i)).getS6();
			s7=s7+((CsCore)CsCore.get(i)).getS7();
			s8=s8+((CsCore)CsCore.get(i)).getS8();
			s9=s9+((CsCore)CsCore.get(i)).getS9();
			sa=sa+((CsCore)CsCore.get(i)).getSa();
			sb=sb+((CsCore)CsCore.get(i)).getSb();
		}
		
		map=new HashMap();
		map.put("s1", s1/CsCore.size());
		map.put("s2", s2/CsCore.size());
		map.put("s3", s3/CsCore.size());
		map.put("s4", s4/CsCore.size());
		map.put("s5", s5/CsCore.size());
		map.put("s6", s6/CsCore.size());
		map.put("s7", s7/CsCore.size());
		map.put("s8", s8/CsCore.size());
		map.put("s9", s9/CsCore.size());
		map.put("sa", sa/CsCore.size());
		map.put("sb", sb/CsCore.size());		
		return map;
	}

}
