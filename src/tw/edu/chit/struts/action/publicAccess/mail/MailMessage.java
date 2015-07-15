package tw.edu.chit.struts.action.publicAccess.mail;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 電子郵件 (已不再有新類別使用它了)
 * @author JOHN
 *
 */
public class MailMessage {

	private Map from;//寄件人
	private String to;//收件人
	private Map datafrom;//顯示寄件人
	private List datato;//顯示收件人
	private String subject;//主旨
	private String content;//內容
	private Date date;
	private String user;
	private String password;
	
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Map getDatafrom() {
		return datafrom;
	}

	public void setDatafrom(Map datafrom) {
		this.datafrom = datafrom;
	}

	public List getDatato() {
		return datato;
	}

	public void setDatato(List datato) {
		this.datato = datato;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Map getFrom() {
		return from;
	}

	public void setFrom(Map from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

}