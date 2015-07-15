package tw.edu.chit.struts.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;
import org.hibernate.Hibernate;

import tw.edu.chit.model.Register;
import tw.edu.chit.model.RegistrationCard;
import tw.edu.chit.model.StdImage;
import tw.edu.chit.model.StdImage4t;
import tw.edu.chit.model.TempStmd;
import tw.edu.chit.service.CourseManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;


public class FreshmanAction extends BaseLookupDispatchAction{
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {
		
		//HttpSession session = request.getSession(false);
		//CourseManager manager = (CourseManager) getBean("courseManager");
		
		//System.out.println("hello");
		
		//setContentPage(request.getSession(false), "Freshman.jsp");
		return mapping.findForward("Freshman");
	}
	
	/**
	 * 查詢
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward verification(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {		
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		
		
		DynaActionForm eForm = (DynaActionForm) form;
		String field[]=eForm.getStrings("field");
		
		Map map=manager.ezGetMap("SELECT r.*, s.*, c.ClassName FROM (TempStmd s LEFT OUTER JOIN Class c ON c.ClassNo=s.depart_class)" +
				"LEFT OUTER JOIN RegistrationCard r ON r.Oid=s.RegistrationCardOid WHERE s.idno='"+field[0]+"'");
		
		if(map==null){
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","驗證資料輸入有誤")); 
			saveErrors(request, error);
			return mapping.findForward("Freshman");
		}
		
		
		request.setAttribute("std", map);
		request.setAttribute("ident", manager.ezGetBy("SELECT idno, name FROM code5 WHERE category='Identity'"));
		request.setAttribute("imageOid", manager.ezGetString("SELECT Oid FROM StdImage4T WHERE idno='"+map.get("idno")+"'"));
		
		ActionMessages msg = new ActionMessages();
		msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1",map.get("student_name")+"同學您好，請依序輸入下列資料。"));
		saveMessages(request, msg);
		return mapping.findForward("Freshman");
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response)throws Exception {		
		
		CourseManager manager = (CourseManager) getBean("courseManager");
		HttpSession session = request.getSession(false);
		
		DynaActionForm eForm = (DynaActionForm) form;
		String field[]=eForm.getStrings("field");
		
		
		if(field[0].trim().equals("")){
			ActionMessages error = new ActionMessages();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","驗證資料輸入有誤")); 
			saveErrors(request, error);
			return mapping.findForward("Freshman");
		}
		
		TempStmd stmd=(TempStmd) manager.hqlGetBy("FROM TempStmd WHERE idno='"+field[0]+"'").get(0);
		RegistrationCard card;
		try{			
			card=(RegistrationCard) manager.hqlGetBy("FROM RegistrationCard WHERE Oid='"+stmd.getRegistrationCardOid()+"'").get(0);
			
		}catch(Exception e){
			card=new RegistrationCard();
			card.setSchoolYear(manager.getSchoolYear().toString());
			card.setStudentNo(stmd.getStudentNo());		
			manager.updateObject(card);
			//System.out.println(card.getOid());
			stmd.setRegistrationCardOid(card.getOid());//為了那個智障資料表
		}
		
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		card=(RegistrationCard) manager.hqlGetBy("FROM RegistrationCard WHERE " +
				"Oid='"+stmd.getRegistrationCardOid()+"'").get(0);
		for(int i=0; i<field.length; i++){			
			stmd.setStudentEname(field[2]);
			stmd.setBirthCounty(field[4]);
			stmd.setSchlName(field[7]);			
			try{
				stmd.setGradyear(Short.parseShort(field[8]));
			}catch(Exception e){
				//stmd.setGradyear(Short.parseShort(field[8]));
			}
			stmd.setGraduStatus(field[9]);
			stmd.setGradDept(field[10]);
			stmd.setEmail(field[15]);
			stmd.setPermPost(field[16]);
			stmd.setPermAddr(field[17]);
			stmd.setTelephone(field[18]);
			stmd.setCellPhone(field[19]);
			stmd.setParentName(field[20]);
			stmd.setCurrPost(field[24]);
			stmd.setCurrAddr(field[25]);
			
			
			card.setBirthCountry(field[3]);
			card.setAborigine(field[5]);
			card.setForeignPlace(field[6]);
			card.setArmyType(field[11]);
			card.setArmyLevel(field[12]);
			
			if(!field[13].trim().equals(""))
			card.setArmyIn(sf.parse(field[13]));
			
			if(!field[14].trim().equals(""))
			card.setArmyOut(sf.parse(field[14]));
			
			card.setParentRelationship(field[21]);
			card.setParentAge(field[22]);
			card.setParentCareer(field[23]);
			card.setEmergentPhone(field[26]);
			card.setEmergentCell(field[27]);
			
			card.setMemberTitle1(field[28]);
			card.setMemberName1(field[29]);
			card.setMemberAge1(field[30]);
			card.setMemberCareer1(field[31]);
			
			card.setMemberTitle2(field[32]);
			card.setMemberName2(field[33]);
			card.setMemberAge2(field[34]);
			card.setMemberCareer2(field[35]);
			
			card.setMemberTitle3(field[36]);
			card.setMemberName3(field[37]);
			card.setMemberAge3(field[38]);
			card.setMemberCareer3(field[39]);
			
			card.setMemberTitle4(field[40]);
			card.setMemberName4(field[41]);
			card.setMemberAge4(field[42]);
			card.setMemberCareer4(field[43]);
			
			card.setWorkPlace1(field[44]);
			card.setWorkTitle1(field[45]);
			
			if(!field[46].trim().equals(""))
			card.setWorkBegin1(sf.parse(field[46]));
			
			if(!field[47].trim().equals(""))
			card.setWorkEnd1(sf.parse(field[47]));
			
			card.setWorkPlace2(field[48]);
			card.setWorkTitle2(field[49]);
			
			if(!field[50].trim().equals(""))
			card.setWorkBegin2(sf.parse(field[50]));
			
			if(!field[51].trim().equals(""))
			card.setWorkEnd2(sf.parse(field[51]));
			
		}
		
		ActionMessages error = new ActionMessages();
		FormFile stdimage=(FormFile) eForm.get("stdimage");
		//System.out.println("stdimage="+stdimage.getFileName());
		if(!stdimage.getFileName().equals("")){
			
			String path=session.getServletContext().getRealPath("/UserFiles/").toString();
			if(manager.uploadImage2APServer(stdimage, path, stmd.getIdno())){
				
				//File file=new File(path+stmd.getIdno()+".jpg");
				try{					
					manager.executeSql("DELETE FROM StdImage4T WHERE idno='"+stmd.getIdno()+"'"); //無論如何只殺不留
					StdImage4t image=new StdImage4t();
					InputStream stream=new FileInputStream(path+"/"+stmd.getIdno()+".jpg");
					
					Blob photo = Hibernate.createBlob(stream);
					image.setImage(photo);
					image.setIdno(stmd.getIdno());
					image.setStudentNo(stmd.getStudentNo());
					manager.updateObject(image);
					photo=null;
					stream.close();					
					stmd.setImageOid(image.getOid()); //存照片Oid					
				}catch(Exception e){
					//e.printStackTrace();
					error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","請檢查是否為jpg檔案格式")); 				
				}
			}else{
				error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","照片比例不適當，學生證照片高度與寬度比例應為3:2"));
			}
		}
		
		try{
			manager.updateObject(stmd);
			manager.updateObject(card);
			Register regs=(Register) manager.hqlGetBy("FROM Register WHERE Idno='"+stmd.getIdno()+"'").get(0);
			regs.setNewStudentReg("1");
			manager.updateObject(regs);			
		}catch(Exception e){
			e.printStackTrace();
			error.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1","資料上傳有誤")); 				
		}		
		
		Map map=manager.ezGetMap("SELECT r.*, s.*, c.ClassName FROM (TempStmd s LEFT OUTER JOIN Class c ON c.ClassNo=s.depart_class)" +
				"LEFT OUTER JOIN RegistrationCard r ON r.Oid=s.RegistrationCardOid WHERE s.idno='"+field[0]+"'");
		
		request.setAttribute("std", map);
		request.setAttribute("ident", manager.ezGetBy("SELECT idno, name FROM code5 WHERE category='Identity'"));
		request.setAttribute("imageOid", manager.ezGetString("SELECT Oid FROM StdImage4T WHERE idno='"+map.get("idno")+"'"));
		
		if(!error.isEmpty()){
			saveErrors(request, error);
		}else{
			ActionMessages msg = new ActionMessages();
			msg.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Course.messageN1", "資料已儲存"));
			saveMessages(request, msg);
		}
		
		
		
		return mapping.findForward("Freshman");
	}
	
	private Integer saveImage(FormFile myImage, String studentNo, String idno) throws FileNotFoundException, IOException{
		CourseManager manager = (CourseManager) getBean("courseManager");
		manager.executeSql("DELETE FROM StdImage4T WHERE idno='"+idno+"'"); //無論如何只殺不留
		StdImage4t image=new StdImage4t();
		InputStream stream= myImage.getInputStream();
		Blob photo = Hibernate.createBlob(stream);
		image.setImage(photo);
		image.setIdno(idno);
		image.setStudentNo(studentNo);
		manager.updateObject(image);
		stream.close();
		
		return image.getOid();
	}
	
	@Override
	protected Map getKeyMethodMap() {
		Map map=new HashMap();
		map.put("Verification", "verification");
		map.put("Save", "save");
		return map;
	}
}
