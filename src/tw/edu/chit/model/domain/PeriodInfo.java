package tw.edu.chit.model.domain;

public class PeriodInfo {
//Store the period information for student or teacher every one day
	private String user;	//store student No. or teacher ID
	private String idate;	//Date string
	private String[] infos;	//Array to Store the every period info(ex.1~15 periods)

	//Constructor
	public PeriodInfo(){};
	
	public PeriodInfo(String user, String idate, String[] infos){
		this.user = user;
		this.idate = idate;
		this.infos = infos;
	}
	
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}

	public String[] getInfos() {
		return infos;
	}
	public void setInfos(String[] infos) {
		this.infos = infos;
	}
	
	public String getIdate() {
		return idate;
	}

	public void setIdate(String idate) {
		this.idate = idate;
	}

}
