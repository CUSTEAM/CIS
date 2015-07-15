package tw.edu.chit.model.domain;

import tw.edu.chit.model.Dtime;
import tw.edu.chit.model.DtimeClass;

public class Dtimes {
	private Dtime dtime;
	private DtimeClass[] periods;
	
	/** default constructor */
	public Dtimes() {
	}

	public Dtime getDtime(){
		return dtime;
	}
	
	public void setDtime(Dtime dtime){
		this.dtime = dtime;
	}
	
	public DtimeClass[] getPeriods(){
		return periods;
	}
	
	public void setPeriods(DtimeClass[] periods){
		this.periods = periods;
	}

}
