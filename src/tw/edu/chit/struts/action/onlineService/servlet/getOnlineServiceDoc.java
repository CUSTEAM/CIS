package tw.edu.chit.struts.action.onlineService.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.model.Clazz;
import tw.edu.chit.service.CourseManager;

/**
 * 將word模版轉變換特定值後拋出
 * 
 * @author JOHN  
 */
public class getOnlineServiceDoc extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		
		String docNo=request.getParameter("docNo");
		
		Map aDoc=manager.ezGetMap("SELECT * FROM OnlineService4Reg WHERE doc_no='"+docNo+"'");
		
		Map dept=manager.ezGetMap("SELECT d.*, c.ClassName, s.student_no, s.student_name, s.idno, " +
				"s.birthday, s.entrance, s.occur_status, s.occur_year, s.occur_term FROM dept d, Gstmd s, Class c WHERE d.no=c.Dept AND " +
				"c.ClassNo=s.depart_class AND s.student_no='"+aDoc.get("student_no")+"'");
		
		if(dept==null){			
			dept=manager.ezGetMap("SELECT d.*, c.ClassName, s.student_no, s.student_name, s.idno, " +
					"s.birthday, s.entrance, s.occur_status, s.occur_year, s.occur_term FROM dept d, stmd s, Class c WHERE d.no=c.Dept AND " +
					"c.ClassNo=s.depart_class AND s.student_no='"+aDoc.get("student_no")+"'");
		}		
		
		if(dept==null){//王八蛋			
			dept=manager.ezGetMap("SELECT d.*, c.ClassName, s.student_no, s.student_name, s.idno, " +
					"s.birthday, s.entrance, s.occur_status, s.occur_year, s.occur_term FROM dept d, stmd s, Class c WHERE d.no=c.Dept AND " +
					"c.ClassNo=s.depart_class LIMIT 1");
		}	
		
		Map school;
		Map type;
		
		try{
			school=getSchool(dept.get("school_name").toString());
		}catch(Exception e){
			school=getSchool(null);
		}
		try{
			type=getType(String.valueOf(dept.get("school_name")));
		}catch(Exception e){
			type=getType(null);
		}		
		
		String J="非在校生";
		if(manager.ezGetInt("SELECT COUNT(*)FROM stmd WHERE student_no='"+dept.get("student_no").toString()+"'")>0){
			J=String.valueOf(dept.get("ClassName"));
		}
		
		String N="", O="", P="", Q="", R="", S="", T="", U="", V="", W="", X="", 		
		Y1=String.valueOf(aDoc.get("tcv")), Y1_1=String.valueOf(aDoc.get("tcv_army")), Y2=String.valueOf(aDoc.get("tcvigr")),Y3=String.valueOf(aDoc.get("gcr")),
		Y4=String.valueOf(aDoc.get("cscna")),Y5=String.valueOf(aDoc.get("tev")),Y6=String.valueOf(aDoc.get("gcev"));		
		
		String Y11="■";
		if(aDoc.get("tcv")==null || aDoc.get("tcv").equals("")){
			Y11="□";
			Y1="0";
		}
		String Y11_1="■";
		if(aDoc.get("tcv_army")==null || aDoc.get("tcv_army").equals("")){
			Y11_1="□";
			Y1_1="0";
		}
		
		String Y12="■";
		if(aDoc.get("tcvigr")==null || aDoc.get("tcvigr").equals("")){
			Y12="□";
			Y2="0";
		}
		String Y13="■";
		if(aDoc.get("gcr")==null || aDoc.get("gcr").equals("")){
			Y13="□";
			Y3="0";
		}
		String Y14="■";
		if(aDoc.get("cscna")==null || aDoc.get("cscna").equals("")){
			Y14="□";
			Y4="0";
		}
		String Y15="■";
		if(aDoc.get("tev")==null || aDoc.get("tev").equals("")){
			Y15="□";
			Y5="0";
		}
		String Y16="■";
		if(aDoc.get("gcev")==null || aDoc.get("gcev").equals("")){
			Y16="□";
			Y6="0";
		}
		String Z1="", Z2="", Z3="", Z4="", Z5="", Z6="";
		
		if(aDoc.get("tev_n")!=null && !aDoc.get("tev_n").equals("")){
			if(aDoc.get("tev_n")!=null && !aDoc.get("tev_n").equals("")){
				Z1=aDoc.get("tev_n").toString();
			}		
			Z2=dept.get("birthday").toString();		
			if(aDoc.get("tev_ak")!=null && !aDoc.get("tev_ak").equals("")){
				Z3=aDoc.get("tev_ak").toString();
			}		
			if(dept.get("occur_year")!=null && !dept.get("occur_year").equals("")){
				Z4=dept.get("occur_year").toString();
			}		
			if(aDoc.get("tev_pb")!=null && !aDoc.get("tev_pb").equals("")){
				Z5=aDoc.get("tev_pb").toString();
			}
			Z6=dept.get("entrance").toString();
		}		
		
		try{
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
			Calendar c=Calendar.getInstance();
			c.setTime(sf.parse(dept.get("birthday").toString()));
			N=String.valueOf(c.get(Calendar.YEAR)-1911);
			O=String.valueOf(c.get(Calendar.MONTH)+1);
			P=String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		}catch(Exception e){
			N="?";
		}
		try{
			Q=String.valueOf(dept.get("entrance")).substring(0, 2);
			R=String.valueOf(dept.get("entrance")).substring(2, 4);
		}catch(Exception e){
			
		}
		try{
			if(dept.get("occur_status").equals("6")){//畢生
				if(dept.get("occur_term").equals("1")){
					T="1";
				}else{
					T="6";
				}
				try{
					S=String.valueOf(Integer.parseInt(dept.get("occur_year").toString())+1);
				}catch(Exception e){
					S="無記錄";
				}
				
				
				
			}
		}catch(Exception e){
			
		}
		try{
			U=String.valueOf(aDoc.get("telphone"));
			V=String.valueOf(aDoc.get("cellPhone"));
			W=String.valueOf(aDoc.get("address"));
			X=String.valueOf(aDoc.get("email"));
		}catch(Exception e){
			
		}
		
		Calendar c=Calendar.getInstance();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date=sf.parse(String.valueOf(aDoc.get("send_time")));			
			c.setTime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
		String[] A = new String[] {"#A#", "#B#", "#C#", "#D#", "#E#", "#F#", "#G#", "#H#", "#I#", "#J#", "#K#", "#L#", "#M#", 
				"#N#", "#O#", "#P#", "#Q#", "#R#", "#S#", "#T#", "#U#", "#V#", "#W#", "#X#", "#Y1#", "#Y2#", "#Y3#", 
				"#Y4#", "#Y5#", "#Y6#", "#Y11#","#Y12#","#Y13#","#Y14#","#Y15#","#Y16#", "#Z1#", "#Z2#", "#Z3#", "#Z4#", 
				"#Z5#", "#Z6#", "#pay#", "#docNo#", "#yy#", "#MM#", "#dd#", "#Y1_1#", "#Y11_1#", "#Y6#"};
		
		String[] B = new String[] {String.valueOf(type.get("A")), String.valueOf(type.get("B")), String.valueOf(type.get("C")), 
				String.valueOf(school.get("D")), String.valueOf(school.get("E")), String.valueOf(school.get("F")), String.valueOf(school.get("G")), 
				String.valueOf(school.get("H")), String.valueOf(dept.get("fname")), J, dept.get("student_no").toString(), 
				String.valueOf(dept.get("student_name")), String.valueOf(dept.get("idno")), 
				N, O, P, Q, R, S, T, U, V, W, X, Y1, Y2, Y3, Y4, Y5, Y6, Y11, Y12, Y13, Y14, Y15, Y16, Z1, Z2, Z3, Z4, Z5, Z6, 
				String.valueOf(aDoc.get("total_pay")), String.valueOf(aDoc.get("doc_no")), String.valueOf(c.get(Calendar.YEAR)-1911), 
				String.valueOf(c.get(Calendar.MONTH)+1), String.valueOf(c.get(Calendar.DAY_OF_MONTH)), Y1_1, Y11_1, Y6};
		
		/*
		String[] B = new String[] {String.valueOf(type.get("A")), String.valueOf(type.get("B")),String.valueOf(type.get("C")), 
				String.valueOf(school.get("D")),String.valueOf(school.get("E")),String.valueOf(school.get("F")),
				String.valueOf(school.get("G")),String.valueOf(school.get("H")), dept.get("fname").toString(), 
				dept.get("ClassName").toString(), dept.get("student_no").toString(), dept.get("student_name").toString(), 
				dept.get("idno").toString(), N, O, P, Q, R, S, T, U, V, W, X, Y0, Y1, Y2, Y3, Y4, Y5, Y6, Y7, Y8};
		*/
		setProcess(A, B, "/WEB-INF/reports/onlineService/petition.xml", response, request);
	}

	public synchronized void setProcess(String[] A, String[] B,
			String filename, HttpServletResponse response,
			HttpServletRequest request) {

		try {
			/*
			 * 檢查傳入的A長度與B長度是否一致
			 */
			if (A.length != B.length)
				throw new ReplaceWordException();

			/*
			 * 設定成用msword打開
			 */
			response.setContentType("application/msword");
			response.setHeader("Content-disposition", "attachment; filename=document"+(int)(Math.random()*49)+".doc");

			/*
			 * 設定Servlet輸出
			 */
			ServletOutputStream stream = response.getOutputStream();

			/*
			 * 讀取xml檔
			 */
			ServletContext context = this.getServletContext();
			File file = new File(context.getRealPath(filename));
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(file));

			/*
			 * 從串流中讀取248個位元組(byte)
			 */
			byte[]buff=new byte[248];

			try {
				String Variable = "";
				while(in.read(buff, 0, buff.length)!=-1) {
					/*
					 * 把讀入的byte轉成UTF-8 再轉成String
					 */
					Variable=Variable+new String(buff, "UTF-8");

					/*
					 * 把剛剛放在buff的內容清空
					 */
					buff = new byte[248];
				}

				/*
				 * 開始進行轉換...
				 */
				for (int i=0; i<A.length; i++) {
					try{
						Variable=Variable.replaceAll(A[i], B[i]);
					}catch(Exception e){
						e.printStackTrace();
					}
				}

				/*
				 * 把轉換完的字串去掉前後空白(trim())再轉成UTF-8 bytes
				 */
				byte[] tmp = Variable.trim().getBytes("UTF-8");
				/*
				 * 開始寫入檔案
				 */
				stream.write(tmp);
			} finally {
				in.close();
				stream.close();
				/* 20120718 Mark BY yichen  ===========BEGIN======================== */
				// 不知道為什麼這邊寫了一行檔案製作完後將格式檔刪除，此段直接導致每次系統啟動後只能執行此程式一次，因為第二次以後就找不到格式檔了.....
				/*try{										
					file.delete();
					
				}catch(Exception e){
					System.out.println("檔案刪除失敗");
				}*/
				/* 20120718 Mark BY yichen  ============END========================= */				
			}

		} catch (ReplaceWordException e1) {
			//System.out.println("傳入的A陣列或B陣列發生錯誤"); // 20120718 Mark BY yichen
			e1.printStackTrace(); // 20120718 Modi BY yichen
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e3) {
			e3.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class ReplaceWordException extends Exception {

	}
	
	/**
	 * 取學制
	 * @param clazz
	 * @return
	 */
	private Map getSchool(String dept){		
		//System.out.println(dept); // 20120718 Mark BY yichen
		Map map=new HashMap();
		map.put("D", "□");//研
		map.put("E", "□");//2技
		map.put("F", "□");//4技
		map.put("G", "□");//2專
		map.put("H", "□");//5專		
		if(dept==null){
			return map;
		}
		//二專
		if(dept.indexOf("二專")!=-1){
			map.put("G", "■");//2專
		}
		//五專
		if(dept.indexOf("五專")!=-1){
			map.put("H", "■");//5專
		}
		//二技
		if(dept.indexOf("二技")!=-1){
			map.put("E", "■");//2技
		}
		
		//四技
		if(dept.indexOf("四")!=-1){
			map.put("F", "■");//4技
		}
		//研
		if(dept.indexOf("碩")!=-1){
			map.put("D", "■");//研
		}
		
		return map;
	}
	
	/**
	 * 取部制
	 * @param clazz
	 * @return
	 */
	private Map getType(String dept){
		//System.out.println(dept);
		Map map=new HashMap();
		map.put("A", "□");
		map.put("B", "□");
		map.put("C", "□");
		if(dept==null){
			return map;
		}
		if(dept.indexOf("日")!=-1||dept.equals("碩士班")){
			map.put("A", "■");
		}		
		if(dept.indexOf("進修部")!=-1 || dept.indexOf("在職")!=-1 || dept.indexOf("夜")!=-1){
			map.put("B", "■");
		}		
		if(dept.indexOf("進修專校")!=-1||dept.indexOf("學院")!=-1){
			map.put("C", "■");
		}		
		return map;
	}
	

	

}
