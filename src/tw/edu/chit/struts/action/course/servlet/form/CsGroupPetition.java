package tw.edu.chit.struts.action.course.servlet.form;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
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

import tw.edu.chit.service.CourseManager;

/**
 * 將word模版轉變換特定值後拋出
 * 
 * @author JOHN TODO 解決office2000含以下無法正確解析xml格式文件
 * 
 */
public class CsGroupPetition extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		WebApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		CourseManager manager = (CourseManager) ctx.getBean("courseManager");
		HttpSession session = request.getSession(false);

		
		
		
		
		
		String[] A = new String[] { "#student_ename#", "#student_name#", "#departName#", "#date#", "#minor#" };
		String[] B = new String[] { "", "", "", "", ""};
		
		setProcess(A, B, "/WEB-INF/reports/course/csgroup/petition.xml", response, request);
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
