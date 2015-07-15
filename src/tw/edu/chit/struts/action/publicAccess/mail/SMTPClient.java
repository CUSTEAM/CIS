package tw.edu.chit.struts.action.publicAccess.mail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import sun.misc.BASE64Encoder;

/**
 * 不使用JavaMail傳送郵件 (已不再有新類別使用它了)
 * @author JOHN
 *
 */
public class SMTPClient{
	
	
	
	private boolean debug = true;
	BASE64Encoder encode = new BASE64Encoder();// 重新編密帳號密碼表頭
	BASE64Encoder enc = new sun.misc.BASE64Encoder();//重新編密帳號密碼表頭
	private Socket socket;
	
	public SMTPClient(String server, int port) throws UnknownHostException, IOException {
		try {
			socket = new Socket(server, 25);
		} catch (SocketException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("已連結伺服器");
		}

	}

	// 註冊
	public void helo(String server, BufferedReader in, BufferedWriter out) throws IOException {
		int result;
		result = getResult(in);
		// 等待 220 回應
		if (result != 220) {
			throw new IOException("連接伺服器失敗");
		}
		//result = sendServer("HELO " + server, in, out);
		result = sendServer("HELO " + server, in, out);
		// HELO 等待 250 回應
		if (result != 250) {
			throw new IOException("登入失敗 ");
		}
	}

	private int sendServer(String str, BufferedReader in, BufferedWriter out)throws IOException {
		out.write(str);
		out.newLine();
		out.flush();
		if (debug) {
			System.out.println("已發送命令:" + str);
		}
		return getResult(in);
	}

	public int getResult(BufferedReader in) {
		String line = "";
		try {
			line = in.readLine();
			if (debug) {
				System.out.println("伺服器執行結果:" + line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 取將態碼
		StringTokenizer st = new StringTokenizer(line, " ");
		return Integer.parseInt(st.nextToken());
	}

	public void authLogin(MailMessage message, BufferedReader in,
			BufferedWriter out) throws IOException {
		int result;
		result = sendServer("AUTH LOGIN", in, out);
		if (result != 334) {
			throw new IOException("登入失敗");
		}

		result = sendServer(encode.encode(message.getUser().getBytes()), in, out);
		if (result != 334) {
			throw new IOException("無此帳號");
		}
		result = sendServer(encode.encode(message.getPassword().getBytes()), in, out);
		if (result != 235) {
			throw new IOException("密碼錯誤");
		}
	}

	// 設置寄件信箱
	public void mailfrom(Map from, BufferedReader in, BufferedWriter out) throws IOException {
		int result;
		//result = sendServer("MAIL FROM:"+from, in, out);
		result = sendServer("MAIL FROM:<"+from.get("myEmail")+">", in, out);
		if (result != 250) {
			throw new IOException("寄件信箱錯誤");
		}
	}

	// 設置收件人 RCPT TO:
	public void rcpt(String touchman, BufferedReader in, BufferedWriter out)
			throws IOException {
		int result;
		result = sendServer("RCPT TO:<"+ touchman+">", in, out);
		if (result != 250) {
			throw new IOException("收件信箱誤");
		}
	}

	// 郵件
	public void data(Map map, List list, String subject, String content, Date date, BufferedReader in, BufferedWriter out) throws IOException {
		int result;
		result = sendServer("DATA", in, out);
		// DATA 命令若收 354 即可進行下步
		if (result != 354) {
			throw new IOException("無法發送郵件");
		}		
		
		StringBuilder sb=new StringBuilder();
		//Map tmp=new HashMap();
		for(int i=0; i<list.size(); i++){
			sb.append("=?UTF-8?B?"+enc.encode(((Map)list.get(i)).get("cname").toString().getBytes())+"?= <"+((Map)list.get(i)).get("Email")+">");			
		}
		
		
		// 若有設定 預約送信
		if(date!=null){			
			out.write("Date: "+date);
			out.newLine();
		}
		
		out.write("MIME-Version: 1.0 ");
		out.newLine();
		out.write("Content-Type: text/html; charset="+System.getProperty("file.encoding")+";");	
		out.newLine();		
		out.write("From: "+"=?UTF-8?B?"+enc.encode(map.get("myName").toString().getBytes())+"?= <"+map.get("myEmail")+">");
		out.newLine();
		out.write("To: "+sb.toString());
		out.newLine();
		out.write("Subject: "+"=?UTF-8?B?"+enc.encode(subject.getBytes())+"?=");
		out.newLine();
		out.newLine();
		out.write(content);
		out.newLine();
		
		// 輸入"."結束
		result = sendServer(".", in, out);
		//System.out.println(result);
		if (result != 250) {
			throw new IOException("發送時發生錯誤");
		}
	}

	// 退出
	public void quit(BufferedReader in, BufferedWriter out) throws IOException {
		int result;
		result = sendServer("QUIT", in, out);
		in.close();
		out.close();
		if (result != 221) {
			throw new IOException("登出失敗");
		}
	}

	// 發送郵件
	public boolean sendMail(MailMessage message, String server, List list) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			helo(server, in, out);// HELO 命令
			authLogin(message, in, out);// AUTH LOGIN 命令
			mailfrom(message.getFrom(), in, out);// MAIL FROM
			for(int i=0; i<list.size(); i++){
				if(((Map)list.get(i)).get("Email")!=null && 
						!((Map)list.get(i)).get("Email").equals("")){					
					rcpt(((Map)list.get(i)).get("Email").toString(), in, out);// RCPT 每個人										
				}
			}
			
			data(message.getDatafrom(), message.getDatato(), message.getSubject(), message.getContent(), message.getDate(), in, out);// DATA
			quit(in, out);// QUIT
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
		return true;
	}
}