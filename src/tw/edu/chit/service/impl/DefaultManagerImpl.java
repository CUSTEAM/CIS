package tw.edu.chit.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.FileDataSource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.struts.upload.FormFile;

import tw.edu.chit.dao.DefaultHibernateDAO;
import tw.edu.chit.dao.DefaultJdbcDAO;
import tw.edu.chit.struts.action.portfolio.FtpClient;


/**
 * @author JOHN
 */
public class DefaultManagerImpl {

	private DefaultJdbcDAO jdbcDao;

	private DefaultHibernateDAO hibernateDAO;

	public DefaultJdbcDAO getJdbcDao() {
		return jdbcDao;
	}

	public void setJdbcDao(DefaultJdbcDAO jdbcDao) {
		this.jdbcDao = jdbcDao;
	}

	public DefaultHibernateDAO getHibernateDAO() {
		return hibernateDAO;
	}

	public void setHibernateDAO(DefaultHibernateDAO hibernateDAO) {
		this.hibernateDAO = hibernateDAO;
	}
	
	//資料庫存取

	/**
	 * SQL得 整個查詢列表
	 * @param sql
	 * @return List of Map
	 */
	public List sqlGet(String sql) {
		return jdbcDao.StandardSqlQuery(sql);
	}

	/**
	 * SQL執行
	 */
	public void exSql(String sql) {
		jdbcDao.exQuery(sql);
	}

	/**
	 * SQL得 1個數字
	 */
	public Integer sqlGetInt(String sql) {
		return jdbcDao.sqlGetInt(sql);
	}

	/**
	 * SQL得 1筆記錄
	 */
	public Map sqlGetMap(String sql) {
		try {
			return jdbcDao.sqlGetMap(sql);
		} catch (Exception e) {
			return null;
		}
	}

	public String sqlGetStr(String sql) {
		return jdbcDao.sqlGetString(sql);
	}

	/**
	 * HQL得 1個列表
	 */
	public List hqlGetListBy(String hql) {
		return hibernateDAO.submitQuery(hql);
	}

	/**
	 * HQL更新 1個po
	 */
	public void update(Object po) {
		hibernateDAO.saveObject(po);
	}
	
	
	//陣列轉換

	/**
	 * 以List中的key進行排序
	 */
	public List sortListByKey(List list, final String key) {
		Collections.sort(list, new Comparator<Map>() {
			public int compare(Map o1, Map o2) {
				String s1 = o1.get(key).toString();
				String s2 = o2.get(key).toString();
				if (s1 == null && s2 == null) {
					return 0;
				} else if (s1 == null) {
					return -1;
				} else if (s2 == null) {
					return 1;
				} else {
					return s1.compareTo(s2);
				}
			}
		});
		return list;
	}
	
	/**
	 * 取得不重複亂數陣列
	 * @param studentLess
	 * @param seldLess
	 * TODO 移轉至MathManager
	 * @return
	 */
	public int[] getMath(int in, int out) {
		int num[] = new int[out];
		for (int i = 0; i < out; i++) {
			int n = (int) (Math.random() * in);

			num[i] = n;
			for (int j = 0; j < i; j++) {
				if (num[i] == num[j]) {
					i--;
				}
			}
		}
		return num;
	}
	
	//文字轉換
	
	/**
	 * 將字串中XML文件無法識別的字元置換
	 */
	public String replaceChar4XML(String str){		
		str=str.replaceAll("&", "&amp;");
		str=str.replaceAll("<", "&lt;");
		str=str.replaceAll(">", "&gt;");
		str=str.replaceAll("\"", "&quot;");
		str=str.replaceAll(",", "&apos;");
		//System.out.println(str);
		return str;
	}
	
	//日期轉換
	
	/**
	 * 星期轉換
	 */
	public String dayOfWeek(int w){	
		switch (w){
			case 1: return "7";
			case 2: return "1";
			case 3: return "2";
			case 4: return "3";
			case 5: return "4";
			case 6: return "5";
			case 7: return "6";		
			default: return "?";
		}
	}
	
	/**
	 * 日期轉字串
	 * 同上
	 * @param date
	 * @return
	 */
	public String date2string(Date date){
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");		
		return sf.format(date);
	}
	
	/**
	 * 字串轉日期
	 * 為防止轉換資料庫平台，故將所有轉換統一處理
	 */
	public Date string2date(String date){
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");		
		try {
			return sf.parse(date);
		} catch (ParseException e) {
			return new Date();
		}
	}
	
	/**
	 * 西元年民國年 OR 民國年西元年轉換
	 * @param someday  西元年或民國年的某1天日期 
	 * 分隔符號'/', '-' 都可接受
	 * @return 西元年或民國年的某1天日期
	 * TODO 可以再優化
	 */
	public String convertDate (String someday){
	    SimpleDateFormat d1 = new SimpleDateFormat("yyyy/MM/dd");
	    SimpleDateFormat d2 = new SimpleDateFormat("/MM/dd");
	    Calendar cal = Calendar.getInstance();	    
	    StringBuilder sb=new StringBuilder(someday);
	    for(int i=0; i<sb.length(); i++){	    	
	    	try{Integer.parseInt(sb.charAt(i)+"");}catch(NumberFormatException e){sb.replace(i, i+1, "-");}
	    }
	    
	    someday=sb.toString();
	    try {
	    	try{
	    		cal.setTime(d1.parse(someday));
	    	}catch(Exception e){
	    		d1 = new SimpleDateFormat("yyyy-MM-dd");
	    	    d2 = new SimpleDateFormat("-MM-dd");
	    	    cal.setTime(d1.parse(someday));
	    	}
	    	if(cal.get(Calendar.YEAR)>1492){
	    	  cal.add(Calendar.YEAR,-1911);
	    	}else{
	    	cal.add(Calendar.YEAR,+1911);
	      }
	      return Integer.toString(cal.get(Calendar.YEAR))+d2.format(cal.getTime());
	    } catch (Exception e) {
	      // e.printStackTrace();
	      return "日期格式有誤";
	    }
	}
	
	//檔案存取

	/**
	 * 儲存FormFile物件成本機檔案
	 * @param fileName 欲儲存的(路徑+檔名)
	 * @param 頁面上file FormFile物件
	 * @return 是否成功
	 */
	public boolean fileupload(String fileName, FormFile file)
			throws FileNotFoundException, IOException {

		InputStream stream = null;
		OutputStream bos;
		byte[] buffer = new byte[8192];
		int bytesRead = 0;
		stream = file.getInputStream();
		bos = new FileOutputStream(fileName);
		while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
			bos.write(buffer, 0, bytesRead);
		}
		bos.close();
		stream.close();
		return true;
	}

	/**
	 * 刪除本機檔案
	 */
	public boolean delLocalFile(String fileName) {
		try {
			File file = new File(fileName);
			file.delete();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 將FormFile轉FileDataSource
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public FileDataSource formFile2FileDataSource(FormFile file){		
		byte[] buffer = new byte[8192];
		int bytesRead = 0;		
		String fileHome="tmp/";//web容器中建立tmp資料夾
		File home=new File(fileHome);
		home.mkdirs();//建立單層或多層
		
		File f1=new File(fileHome+file.getFileName());
		try{
			InputStream stream  =  file.getInputStream();			 
			OutputStream bos=new FileOutputStream(f1);	 
			file.getInputStream();
			
			while((bytesRead = stream.read(buffer, 0, 8192)) != -1){
				bos.write(buffer, 0, bytesRead);
			}
			FileDataSource f=new FileDataSource(f1);
			stream.close();
			bos.close();		
			return f;
		}catch(Exception e){
			return null;
		}finally{
			home.delete();//無論如何，結束時一併刪除
		}		
	}
	
	//通訊協議
	
	/**
	 * 電子郵件格式驗證
	 * @param address
	 * @return
	 */
	public boolean validateEmail(String address){		
		String regex="[a-zA-Z][\\w_]+@\\w+(\\.\\w+)+";  
		Pattern p=Pattern.compile(regex);  
		Matcher m=p.matcher(address);		
		return m.matches();   
	}
	
	/**
	 * Email傳送
	 * @param username 登入郵件伺服器帳號
	 * @param password 登入郵件伺服器密碼
	 * @param smtpServer 郵件伺服器
	 * @param dsplEmail 顯示寄件信箱
	 * @param dsplName 顯示寄件人
	 * @param dsplDate 顯示寄件日期
	 * @param subject 主旨
	 * @param content 內容
	 * @param address 收件人
	 * @param files 夾檔
	 */
	public boolean sendMail(
		String username, String password, String smtpServer,
		String dsplEmail, String dsplName, Date dsplDate, 
		String subject, String content, InternetAddress address[], 
		FileDataSource files[]){		
		List list=new ArrayList();		
		for(int i=0; i<address.length; i++){
			if(address[i]!=null && !address[i].equals(""))
			if(validateEmail(address[i].getAddress())){
				list.add(address[i]);
			}
		}
		Collection c=new ArrayList();
		c.addAll(list);			
		try{
			MultiPartEmail email=new HtmlEmail();
			email.setCharset("big5");//本校伺服器僅接受big5,不可用utf8	
			email.setAuthentication(username, password);
			email.setHostName(smtpServer);
			email.setFrom(dsplEmail, dsplName);
			email.setSentDate(dsplDate);
			email.setSubject(subject);
			if(!content.trim().equals(""))
			email.setMsg(content);
			email.setTo(c);			
			if(files!=null)
			if(files.length>0){
				EmailAttachment att=new EmailAttachment();
				String fileName;
				for(int i=0; i<files.length; i++){
					if(files[i]!=null)
					try{
						att = new EmailAttachment();
						att.setPath(files[i].getFile().getAbsolutePath());
						att.setDisposition(EmailAttachment.ATTACHMENT);
						fileName=new String(MimeUtility.encodeText(files[i].getFile().getName(), "big5", "B"));	
						att.setName(fileName);						
						email.attach(att);
					}catch(Exception e){}//finally{}
				}
			}			
			email.send();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}		
		return true;	
	}
	
	/**
	 * FTP上傳
	 * @param FTPHost
	 * @param username 帳號
	 * @param password 密碼
	 * @param baseDir FTP工作路徑
	 * @param ServerDir 網站工作路徑
	 * @param fileName 上傳檔案名稱
	 * @return
	 * @see fileupload, formFile2FileDataSource
	 */
	public boolean uploadImage2FTPServer(String FTPHost, String ServerDir, String username, String password, String baseDir, String fileName){
		try{
			FtpClient ftp=new FtpClient(FTPHost, username, password, baseDir, ServerDir);			
			ftp.connect();
			//if(ftp.isBinaryTransfer())//本校都是要啦，不用測了
			ftp.setBinaryTransfer(true);
			ftp.put(fileName, true);//轉輸完成刪除檔案
			ftp.disconnect();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}		
	}
}
