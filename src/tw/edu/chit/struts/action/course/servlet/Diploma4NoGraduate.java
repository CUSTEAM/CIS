package tw.edu.chit.struts.action.course.servlet;

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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tw.edu.chit.service.CourseManager;

/**
 * 將word模版轉變換特定值後拋出
 * 
 * @author JOHN 
 * TODO 解決office2000含以下無法正確解析xml格式文件
 * 
 */
public class Diploma4NoGraduate extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);

		String[] A = new String[] { 
				"#leader#",
				"#ey#",
				"#em#",
				"#iny#",
				"#inm#",
				"#school#",
				"#by#",
				"#bm#",
				"#bd#",
				"#name#", 
				"#stNo#", 
				"#grade#", 
				"#term#", 
				"#departName#", 
				"#date#" };
		
		//煩
		String leader="教 務 長";
		Map map=(Map)((List)session.getAttribute("students")).get(0);		
		
		//學生英文姓名
		String student_ename="English name is not ready";
		if(map.get("student_ename")!=null){
			student_ename=map.get("student_ename").toString();
		}
		
		String ClassName=map.get("ClassName").toString();
		String studentNo=map.get("student_no").toString();
		String deptNo="";
		
		Map aStudent=manager.ezGetMap("SELeCT * FROM stmd WHERE student_no='"+studentNo+"'");//學生詳細
		if(aStudent==null){
			aStudent=manager.ezGetMap("SELeCT * FROM Gstmd WHERE student_no='"+studentNo+"'");//學生詳細
			deptNo=manager.ezGetString("SELECT depart_class FROM Gstmd WHERE student_no='"+studentNo+"'").substring(0, 4);
		}else{
			deptNo=manager.ezGetString("SELECT depart_class FROM stmd WHERE student_no='"+studentNo+"'").substring(0, 4);
		}
		
		if(request.getParameter("type").equals("chi")){//中文
			
			Date date=new Date();
			Calendar n=Calendar.getInstance();
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
			String inYear;
			String inMonth;
			StringBuilder entrance;
			
			String ch=manager.convertDate(sf.format(date));
			try {
				n.setTime(sf.parse(ch));
			} catch (ParseException e1) {
				n.setTime(new Date());
			}
			
			//班級代碼前4碼以得英文名稱		
			StringBuilder date1=new StringBuilder("this month of ");
			//月先
			if(aStudent.get("occur_term")!=null){
				if(aStudent.get("occur_term").equals("1")){
					//date.append("February,");
					date1.append("January,");
				}else{
					date1.append("June,");
				}
			}else{
				date1.append("not set");
			}
			try {
				date1.append(" "+english_number(Integer.parseInt(aStudent.get("occur_year").toString())+1911));
			} catch (NumberFormatException e) {				
				e.printStackTrace();
			} catch (Exception e) {				
				e.printStackTrace();
			}		

			//系所名稱
			String deptName="";	
			try{
				entrance=new StringBuilder(aStudent.get("entrance").toString());
			}catch(Exception e){
				entrance=new StringBuilder("9999");
			}
			deptName=manager.ezGetString("SELECT fname FROM dept WHERE no='"+aStudent.get("depart_class").toString().substring(0, 4)+"'");
							
			if(deptName==null){
				deptName=aStudent.get("dept").toString();
			}
			
			try {
				date=sf.parse(manager.convertDate(aStudent.get("birthday").toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//入年
			Calendar c=Calendar.getInstance();
			c.setTime(date);
			
			Integer x=Integer.parseInt(entrance.substring(entrance.length()-2, entrance.length()).toString());
			inMonth=x.toString();
			entrance.delete(entrance.length()-2, entrance.length());
			inYear=entrance.toString();
			
			//修年
			Calendar c1=Calendar.getInstance();
			try {
				if(aStudent.get("occur_date")==null){
					c1.setTime(n.getTime());
				}else{
					
					c1=Calendar.getInstance();
					c1.setTime(sf.parse(manager.convertDate (aStudent.get("occur_date").toString())));
				}
				
			} catch (ParseException e1) {
				c1.setTime(new Date());
			}
			
			int nx=manager.ezGetBy("SELECT school_year FROM ScoreHist WHERE " +
					"student_no='"+aStudent.get("student_no")+"' " +
					"GROUP BY School_year, School_term").size();
			
			String nterm="壹";
			if(nx%2==0){
				nterm="貳";
			}
			if(nx<2){
				nx=2;
			}
			
			/* #grade# 修業年
			 * #term# 修業學期
			 * #by# 生年
			 * bm# 生月of年
			 * #bd# 生日of月 
			 * #departName# 部制名稱
			 * "#date#" 發放日期
			 * #stNo# 學號
			 * #name#
			 * #school# 部
			 * #iny# 入學年
			 * #inm# 人學月
			 * #ey# 修業年
			 * #em# 修業月
			 * #leader# 主管
			 */
			
			if(manager.ezGetString("SELECT CampusNo FROM Class WHERE ClassNo='"+aStudent.get("depart_class")+"'").equals("2")){
				leader="新竹分部主任";
			}
			String[] B = new String[] {
						leader, 
						manager.numtochinese(c1.get(Calendar.YEAR)+"", false),
						"柒",
						manager.numtochinese(inYear, false),
						manager.numtochinese(inMonth, false),
						manager.ezGetString("SELECT name FROM code5 WHERE category='SchoolType' AND idno='"+aStudent.get("depart_class").toString().substring(0, 2)+"'").substring(2),
						manager.numtochinese(c.get(Calendar.YEAR)+"", false),
						manager.numtochinese(c.get(Calendar.MONTH)+1+"", false),
						manager.numtochinese(c.get(Calendar.DAY_OF_MONTH)+"", false),
						aStudent.get("student_name").toString(),
						aStudent.get("student_no").toString(), 
						manager.numtochinese(nx/2+"", false), 
						nterm, 
						deptName, 
						manager.numtochinese(n.get(Calendar.YEAR)+"", false)+"年"+
						manager.numtochinese(n.get(Calendar.MONTH)+1+"", false)+"月"+
						manager.numtochinese(n.get(Calendar.DAY_OF_MONTH)+"", false)+"日", 
					};			
			
			setProcess(A, B, "/WEB-INF/reports/registration/ungraduate/ungraduateChi.xml", response, request);
		
		
		//若是英文
		}else{//英文
			
			System.out.println(aStudent);
			//map=(Map)((List)session.getAttribute("students")).get(0);	
			//學生英文姓名
			student_ename="English name is not ready";
			if(aStudent.get("student_ename")!=null){
				student_ename=aStudent.get("student_ename").toString();
			}
			
			String deptName="";		
			try{
				deptName=manager.ezGetString("SELECT engname FROM dept WHERE no='"+deptNo+"'");
			}catch(Exception e){
				deptName="department name is not ready.";
			}
			
			
			/* 
			 * #ey# 現在年
			 * #em# 洋名
			 * #iny# 中名
			 * #inm# 生日
			 * #school# 系所名
			 * #by# 開始年
			 * #bm# 結束年
			 */
			SimpleDateFormat sf=new SimpleDateFormat("MMMMM yyyy", Locale.US);//列印日期, 
			SimpleDateFormat sf1=new SimpleDateFormat("MMMMM dd, yyyy", Locale.US);//生日,
			SimpleDateFormat sf2=new SimpleDateFormat("yyyy-MM-dd", Locale.US);//生日
			//SimpleDateFormat sf3=new SimpleDateFormat("MMMMM yyyy", Locale.US);//最後日期
			Date bdate=new Date();
			try {
				bdate=sf2.parse(aStudent.get("birthday").toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Date occur_date=new Date();
			try {
				occur_date=sf2.parse(aStudent.get("occur_date").toString());
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			StringBuilder entrance=new StringBuilder(aStudent.get("entrance").toString());
			Integer x=Integer.parseInt(entrance.substring(entrance.length()-2, entrance.length()).toString());
			String inMonth=x.toString();
			entrance.delete(entrance.length()-2, entrance.length());
			String inYear=entrance.toString();
			int x1=(Integer.parseInt(inYear)+1911);
			String[] B = new String[] {
					"",
					sf.format(new Date()),
					student_ename,
					aStudent.get("student_name").toString(),
					sf1.format(bdate),
					deptName,
					x1+"",
					sf.format(occur_date),
					"#bd#",
					"#name#", 
					"#stNo#", 
					"#grade#", 
					"#term#", 
					"#departName#", 
					"#date#" 
			};			

			
			setProcess(A, B, "/WEB-INF/reports/registration/ungraduate/ungraduateEng.xml", response, request);
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
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
			}

		} catch (ReplaceWordException e1) {
			System.out.println("傳入的A陣列或B陣列發生錯誤");
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
	
	//轉英文數字
	
	private String d1(char x) { // single digit terms
		String n="";
		switch(x) {
			case '0': n= ""; break;
			case '1': n= " One "; break;
			case '2': n= " Two "; break;
			case '3': n= " Three "; break;
			case '4': n= " Four "; break;
			case '5': n= " Five "; break;
			case '6': n= " Six "; break;
			case '7': n= " Seven "; break;
			case '8': n= " Eight "; break;
			case '9': n= " Nine "; break;
			default: n = "Not a Number";
		}
		return n;
	}

	//轉英文
	static String[] to_19 = { "zero",  "one",   "two",  "three", "four",   "five",   "six",
        "seven", "eight", "nine", "ten",   "eleven", "twelve", "thirteen",
        "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen" };
    static String[] tens  = { "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
    static String[] denom = { "",
        "thousand",     "million",         "billion",       "trillion",       "quadrillion",
        "quintillion",  "sextillion",      "septillion",    "octillion",      "nonillion",
        "decillion",    "undecillion",     "duodecillion",  "tredecillion",   "quattuordecillion",
        "sexdecillion", "septendecillion", "octodecillion", "novemdecillion", "vigintillion" };


    private String convert_nn(int val) throws Exception {
        if (val < 20)
            return to_19[val];
        for (int v = 0; v < tens.length; v++) {
            String dcap = tens[v];
            int dval = 20 + 10 * v;
            if (dval + 10 > val) {
                if ((val % 10) != 0)
                    return dcap + "-" + to_19[val % 10];
                return dcap;
            }        
        }
        throw new Exception("Should never get here, less than 100 failure");
    }
    // convert a value < 1000 to english, special cased because it is the level that kicks 
    // off the < 100 special case.  The rest are more general.  This also allows you to
    // get strings in the form of "forty-five hundred" if called directly.
    private String convert_nnn(int val) throws Exception {
        String word = "";
        int rem = val / 100;
        int mod = val % 100;
        if (rem > 0) {
            word = to_19[rem] + " hundred";
            if (mod > 0) {
                word = word + " ";
            }
        }
        if (mod > 0) {
            word = word + convert_nn(mod);
        }
        return word;
    }
    
    private String english_number(int val) throws Exception {
        if (val < 100) {
            return convert_nn(val);
        }
        if (val < 1000) {
            return convert_nnn(val);
        }
        for (int v = 0; v < denom.length; v++) {
            int didx = v - 1;
            int dval = new Double(Math.pow(1000, v)).intValue();
            if (dval > val) {
                int mod = new Double(Math.pow(1000, didx)).intValue();
                int l = val / mod;
                int r = val - (l * mod);
                String ret = convert_nnn(l) + " " + denom[didx];
                if (r > 0) {
                	//分隔符號為 "and", ","
                    ret = ret + " and " + english_number(r);
                }
                return ret;
            }
        }
        throw new Exception("Should never get here, bottomed out in english_number");
    }


	

}
