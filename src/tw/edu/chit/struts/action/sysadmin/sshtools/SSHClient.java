package tw.edu.chit.struts.action.sysadmin.sshtools;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * 可切換 使用者(root) 的 SSH 客戶端
 */
public class SSHClient {

	// 預設的提示字串
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final String DEFAULT_PASSPS = "(Password|密碼)[:：] ";
	private static final String ANSI_CONTROL = "\\[[0-9]{0,2};?[0-9]{0,2}m";
	private static final String REGEX_KEYCHARS = "[]{}()^$?+*.&|";

	private boolean isroot; // 檢查目前是否為 root
	private boolean connected; // 檢查目前是否連線中
	private String userps; // user 的 shell 提示字串
	private String rootps; // root 的 shell 提示字串
	private Session session; // SSH 連線
	private Scanner sshin; // SSH 輸入端
	private PrintStream sshout; // SSH 輸出端
	private StringBuffer conbuf; // 最後一個指令的執行結果

	/**
	 * 建立 SSH 連線
	 * 
	 * @param host 主機
	 * @param user 帳號
	 * @param password 密碼
	 */
	public SSHClient(String host, String user, String password) {
		this(host, user, password, DEFAULT_CHARSET);
	}

	/**
	 * 建立 SSH 連線, 採用指定的編碼方式輸出
	 * 
	 * @param host 主機
	 * @param user 帳號
	 * @param password 密碼
	 * @param charset 字串編碼
	 */
	public SSHClient(String host, String user, String password, String charset) {
		PipedInputStream ppis;
		PipedOutputStream ppos;

		try {
			// 設定連線方式
			JSch jsch = new JSch();
			session = jsch.getSession(user, host);
			session.setPassword(password);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			ChannelShell ch = (ChannelShell) session.openChannel("shell");

			// 建立輸入端
			ppis = new PipedInputStream();
			ppos = new PipedOutputStream();
			ppis.connect(ppos);
			ch.setInputStream(ppis);
			sshout = new PrintStream(ppos);

			// 建立輸出端
			ppis = new PipedInputStream();
			ppos = new PipedOutputStream();
			ppis.connect(ppos);
			ch.setOutputStream(ppos);
			sshin = new Scanner(ppis, charset);

			// 連線到主機 (會 block)
			ch.connect();
			while (!ch.isConnected()) {
				if (ch.isClosed())
					break;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
			}

			// 尋找相同的兩列視為 user 提示字串
			conbuf = new StringBuffer();
			sshout.print("\n\n");
			sshout.flush();
			String prev = "";
			String line = sshin.nextLine();
			while (line.indexOf(user) == -1 || !line.equals(prev)) {
				conbuf.append(prev);
				conbuf.append('\n');
				prev = line;
				line = sshin.nextLine();
			}
			conbuf.delete(0, 1);

			// 拿掉多出來的兩個空白行, 因 print("\n\n") 造成, 純粹美觀
			if (conbuf.substring(conbuf.length() - 2).equals("\n\n")) {
				conbuf.delete(conbuf.length() - 2, conbuf.length());
			}

			// 紀錄 user 提示字串
			int home = line.indexOf('~');
			userps = escapeRegex(line.substring(0, home));
			connected = true;
		} catch (JSchException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 切換到 user, 並且移動到 user 目錄
	 * 
	 * @param username
	 *            user 帳號
	 * @param password
	 *            root 密碼
	 */
	public boolean switchUser(String username, String password) {
		return switchUser(username, password, DEFAULT_PASSPS);
	}

	/**
	 * 切換到 root 並且移動到 root 家目錄, 偵測自訂的提示字串
	 * 
	 * @param password
	 *            root 密碼
	 * @param userps
	 *            登入後提示字串
	 * @param passps
	 *            密碼輸入提示字串
	 */
	public boolean switchUser(String username, String password, String passps) {
		String line;

		// 搜尋登入成功的提示字串
		sshout.print("su "+username+"\n");
		sshout.flush();

		// 搜尋密碼輸入的提示字串
		sshin.findWithinHorizon(passps, 0);
		sshout.print(password);
		sshout.print('\n');
		sshout.flush();

		// 檢查是否登入成功
		sshout.print("echo $?\n");
		sshout.flush();
		do {
			line = sshin.nextLine();
		} while (!line.matches("^[0-9]+$"));
		isroot = (Integer.parseInt(line) == 0);

		// 記錄 root 提示字串
		if (isroot) {
			sshout.print("\n");
			line = sshin.nextLine();
			int home = line.indexOf('~');
			rootps = escapeRegex(line.substring(0, home));
		}
		return isroot;
	}

	/**
	 * 執行一個指令, 傳回結束代碼 (只能執行不需要輸入的指令)
	 * 
	 * @param command
	 *            指令字串
	 * @return 結束代碼
	 */
	public int execute(String command) {
		String currps = isroot ? rootps : userps;

		// 發送指令
		sshout.print(command);
		sshout.print("\n\n");
		sshout.flush();

		// 跳到指令之後
		sshin.findWithinHorizon(currps, 0);
		sshin.nextLine();

		// 接收輸出, 注意 currps 因為有設計 Regex 逸脫不可以用 indexOf 判斷
		currps = "^" + currps + ".+";
		conbuf.delete(0, conbuf.length());
		String line = sshin.nextLine();
		line = line.replaceAll(ANSI_CONTROL, "");
		while (!line.matches(currps)) {
			conbuf.append(line);
			conbuf.append('\n');
			line = sshin.nextLine();
			line = line.replaceAll(ANSI_CONTROL, "");
		}

		// 送出取得回傳值的指令
		sshout.print("echo $?\n");
		sshout.flush();

		// 跳到指令之後
		do {
			line = sshin.nextLine();
		} while (!line.matches("^[0-9]+$"));
		System.out.println("\n遠端訊息回應start----------------\n"+getLastOutput()+"----------------遠端訊息回應end\n");
		return Integer.parseInt(line);
	}

	/**
	 * 傳回最後一個指令的輸出畫面
	 * 
	 * @return 輸出畫面
	 */
	public String getLastOutput() {
		return conbuf.toString();
	}

	/**
	 * 檢查連線狀態
	 * 
	 * @return 連線成功或失敗
	 */
	public boolean isConnected() {
		return connected;
	}

	/**
	 * 結束 ssh 連線
	 */
	public void close() {
		if (isroot)
			sshout.print("exit");
		session.disconnect();
		connected = false;
	}

	// 逸脫 Regex 的特殊字元
	private String escapeRegex(String s) {
		char ch;
		String result = "";

		for (int i = 0; i < s.length(); i++) {
			ch = s.charAt(i);
			if (REGEX_KEYCHARS.indexOf(ch) > -1)
				result += "\\";
			result += ch;
		}

		return result;
	}
	
	public void changePasswd(String username, String password){
		String line;
		sshout.print("passwd "+username+"\n");
		sshout.flush();
		
		// 搜尋密碼輸入的提示字串
		sshin.findWithinHorizon("New UNIX password: ", 0);
		sshout.print(password);
		sshout.print('\n');
		sshout.flush();
		
		
		sshin.findWithinHorizon("Retype new UNIX password: ", 0);
		sshout.print(password);
		sshout.print('\n');
		sshout.flush();
		
		
		// 檢查是否登入成功
		sshout.print("echo $?\n");
		sshout.flush();		
		System.out.println("遠端訊息回應: "+getLastOutput());
	}

}
