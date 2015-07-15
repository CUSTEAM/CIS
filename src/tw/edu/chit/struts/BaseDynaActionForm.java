package tw.edu.chit.struts;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class BaseDynaActionForm extends DynaActionForm {

	public void reset(ActionMapping mapping, HttpServletRequest request) { 
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	} 
}
