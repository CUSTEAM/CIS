package tw.edu.chit.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * This is the super class of actions only display a static HTML document 
 * in content area.
 * Derived class must implement a public default constructor in which calls
 * 		super("doc_path.html");
 * 
 * @author James Chiang
 */
public abstract class BaseDocumentPostAction extends BaseAction {
	
	protected String docPath = null;
	
	public BaseDocumentPostAction(String docPath) {
		this.docPath = docPath;
	}
	
	public ActionForward execute(ActionMapping mapping, 
								 ActionForm form,
								 HttpServletRequest request, 
								 HttpServletResponse response)
			throws Exception {
		
		setContentPage(request, docPath);
		return mapping.findForward("Main");
	}
	
}
