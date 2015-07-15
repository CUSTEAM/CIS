package tw.edu.chit.struts.action.course.exception;

public class NoClassDefinedException extends Exception {

	private static final long serialVersionUID = -8315274987561685665L;
	
	public NoClassDefinedException() {
		super();
	}
	
	public NoClassDefinedException(String msg) {
		super(msg);
	}

}
