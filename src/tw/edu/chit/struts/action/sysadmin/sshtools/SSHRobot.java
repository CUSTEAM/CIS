package tw.edu.chit.struts.action.sysadmin.sshtools;

import java.util.Scanner;
import java.util.Vector;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * 原始的SSH機器人
 * TODO 提示字元, 切換root, 批次...
 */
public class SSHRobot extends Task {

	private boolean abort; // 指令有錯誤時是否要中止動作
	private boolean silent; // 是否靜音
	private String host; // 對方主機
	private String user; // 使用者名稱
	private String userpwd; // 密碼
	//private String rootpwd; // root密碼
	private Vector<String> commands; // 指令集
	
	/**
	 * 產生 SSH 機器人
	 */
	public SSHRobot() {
		abort = true;
		silent = true;
		commands = new Vector<String>();
	}

	/**
	 * 設定主機 (必要)
	 * @param host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 設定帳號 (必要)
	 * @param user 帳號
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * 設定密碼 (必要)
	 * @param userpwd 密碼
	 */
	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	/**
	 * 設定root密碼 (選用, 沒填則不切換 root)
	 * @param rootpwd 密碼
	 
	public void setRootpwd(String rootpwd) {
		this.rootpwd = rootpwd;
	}
	*/
	
	/**
	 * 設定指令執行錯誤時是否需要中止
	 * @param abort 是否中止
	 */
	public void setAbort(boolean abort) {
		this.abort = abort;
	}

	/**
	 * 設定是否要靜音, 不顯示執行狀況 (預設值: true)
	 * @param silent 是否靜音
	 */
	public void setSilent(boolean silent) {
		this.silent = silent;
	}

	/**
	 * 讀取要執行的指令集
	 */
	public void addText(String nested) {
		//System.out.println(nested);
		String line;
		//String decoded = getProject().replaceProperties(nested);
		//Scanner scanner = new Scanner(decoded);
		Scanner scanner = new Scanner(nested);
		while (scanner.hasNextLine()) {
			try {
				line = scanner.nextLine().trim();
				if (!line.equals(""))
					commands.add(line);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * 執行 Task
	 */
	@Override
	public void execute() throws BuildException {
		checkParam();

		int i;
		int exit;
		

		SSHClient sshc = new SSHClient(host, user, userpwd);

		if (sshc.isConnected()) {
			System.out.println(sshc.getLastOutput());			
			for (i = 0; i < commands.size(); i++) {
				exit = sshc.execute(commands.get(i));
				if (!silent)
					System.out.println(sshc.getLastOutput());
				if (exit != 0 && abort)
					break;
			}
			sshc.close();
		} else {
			throw new RuntimeException("連線失敗");
		}
	}

	// 檢查參數格式
	private void checkParam() throws BuildException {
		if (host == null)
			throw new RuntimeException("host為必要屬性");
		if (user == null)
			throw new RuntimeException("user為必要屬性");
		if (userpwd == null)
			throw new RuntimeException("userpwd為必要屬性");
		if (commands == null)
			throw new RuntimeException("沒有要執行的指令");
		if (commands.size() == 0)
			throw new RuntimeException("沒有要執行的指令");
	}

}
