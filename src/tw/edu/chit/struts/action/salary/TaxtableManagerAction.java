package tw.edu.chit.struts.action.salary;

import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;
import org.hibernate.Hibernate;

import tw.edu.chit.model.SalaryTaxtable;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;

public class TaxtableManagerAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		request.setAttribute("taxTable", manager.ezGetBy("SELECT * FROM Salary_taxtable"));
		
		setContentPage(request.getSession(false), "salary/TaxtableManager.jsp");
		return mapping.findForward("Main");
	}
	
	/**
	 * 更新
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//HttpSession session = request.getSession(false);
		CourseManager manager = (CourseManager) getBean("courseManager");
		DynaActionForm stForm = (DynaActionForm) form;		
		FormFile inputExcel=(FormFile)stForm.get("inputExcel");
		InputStream stream= inputExcel.getInputStream();
		SalaryTaxtable stable;
		List table=new ArrayList();
		if (inputExcel!=null)
		try{	          
	        POIFSFileSystem excel = new POIFSFileSystem(stream);
	        HSSFWorkbook wb = new HSSFWorkbook(excel);
	        HSSFSheet sheet;
	        HSSFRow row;
	        HSSFCell cell;
	        //String tmp = "";
	        sheet = wb.getSheetAt(0);
	        Vector[] vec = new Vector[sheet.getPhysicalNumberOfRows()];	        
	        
	        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++){	        	
	        	row = sheet.getRow(i);
	            vec[i] = new Vector();
	            
	            for (int j = 0; j < row.getPhysicalNumberOfCells(); j++){
	            	
	            	cell = row.getCell((short)j);
	            	if(cell!=null){
	            		System.out.println(cell.getRichStringCellValue());
	            	}else{
	            		System.out.println("null!");
	            	}	            	
	            }	        	
	            System.out.println("------------------------------------------");
	            System.out.println("");
	        }
	        
	    }catch (Exception e){
	    	e.printStackTrace();
	    }

		
		
		
		
		
		
		
		setContentPage(request.getSession(false), "salary/TaxtableManager.jsp");
		return unspecified(mapping, form, request, response);
	}
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Update", "update");
		return map;
	}

}
