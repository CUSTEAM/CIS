package tw.edu.chit.model.domain;

public interface DilgService {
	/**
	 * 設定Dilg 某一節次之狀態
	 * @param period
	 * @param status
	 */
    public void setAbs(int period, Short status);
    /*code as bellow:
     * 
    public void setAbs(int period, Short status){
		switch (period) {
		case 0:
			this.setAbs0(status);
			break;
		case 1:
			this.setAbs1(status);
			break;
		case 2:
			this.setAbs2(status);
			break;
		case 3:
			this.setAbs3(status);
			break;
		case 4:
			this.setAbs4(status);
			break;
		case 5:
			this.setAbs5(status);
			break;
		case 6:
			this.setAbs6(status);
			break;
		case 7:
			this.setAbs7(status);
			break;
		case 8:
			this.setAbs8(status);
			break;
		case 9:
			this.setAbs9(status);
			break;
		case 10:
			this.setAbs10(status);
			break;
		case 11:
			this.setAbs11(status);
			break;
		case 12:
			this.setAbs12(status);
			break;
		case 13:
			this.setAbs13(status);
			break;
		case 14:
			this.setAbs14(status);
			break;
		case 15:
			this.setAbs15(status);
			break;
		}
    }    
    */
    
    
    /**
     * 取得 Dilg 某一節次之狀態
     * @param period
     * @return
     */
    public Short getAbs(int period);
    /*code as bellow:
     * 
    public short getAbs(int period){
    	Short status = null;
		switch (period) {
		case 0:
			status = this.getAbs0();
			break;
		case 1:
			status = this.getAbs1();
			break;
		case 2:
			status = this.getAbs2();
			break;
		case 3:
			status = this.getAbs3();
			break;
		case 4:
			status = this.getAbs4();
			break;
		case 5:
			status = this.getAbs5();
			break;
		case 6:
			status = this.getAbs6();
			break;
		case 7:
			status = this.getAbs7();
			break;
		case 8:
			status = this.getAbs8();
			break;
		case 9:
			status = this.getAbs9();
			break;
		case 10:
			status = this.getAbs10();
			break;
		case 11:
			status = this.getAbs11();
			break;
		case 12:
			status = this.getAbs12();
			break;
		case 13:
			status = this.getAbs13();
			break;
		case 14:
			status = this.getAbs14();
			break;
		case 15:
			status = this.getAbs15();
			break;
		}
		return status;
    }
     */
    

}
