package tw.edu.chit.struts.action.student;

import java.io.InputStream;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;
import org.hibernate.Hibernate;

import tw.edu.chit.model.StdImage;
import tw.edu.chit.model.Student;
import tw.edu.chit.service.AdminManager;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class StudentPhoneAndAddressAction extends BaseLookupDispatchAction {

	public static final String STUDENT_INFO = "stdInfo";

	/**
	 * 處理學生第一次進入時動作
	 * 
	 * @commend 取得Student物件
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		Student student = null;
		try {
			student = (Student) getUserCredential(session).getStudent();
			String isNewStudent = "no";
			AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
			String sterm = am.findTermBy(IConstants.PARAMETER_SCHOOL_TERM);
			if ("1".equals(sterm)
					&& Toolket.isNewStudentClass(student.getDepartClass()))
				isNewStudent = "yes";
			session.setAttribute("isNewStudent", isNewStudent);
		} catch (Exception e) {
			log.error("學生資訊:" + getUserCredential(session));
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "學生資料有誤,請洽電算中心,謝謝!!"));
			saveErrors(request, errors);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}
		session.setAttribute(STUDENT_INFO, student);
		setContentPage(session, "student/StudentPhoneAndAddress.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 修改學生聯絡電話與地址
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		HttpSession session = request.getSession(false);
		Student student = (Student) getUserCredential(session).getStudent();
		ActionMessages messages = new ActionMessages();
		try {
			MemberManager mm = (MemberManager) getBean("memberManager");
			student = mm.updateStudentPhoneAndAddress(aForm.getMap(), student);
			FormFile image = (FormFile) aForm.get("image");
			
			if (image != null && StringUtils.isNotBlank(image.getFileName())) {
				
				if (!ArrayUtils.contains(IConstants.VALID_IMAGE_TYPE,
						StringUtils.substringAfter(image.getFileName(), ".")
								.toLowerCase())) {
					messages
							.add(
									ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("Course.errorN1",
											"目前系統只接受JPEG、JPG、PNG與GIF副檔名之照片格式,不要上傳生活照ㄛ,謝謝!"));
					saveMessages(request, messages);
					return mapping.findForward(IConstants.ACTION_MAIN_NAME);
				} else if ((51 * 1024) < image.getFileSize()) {
					// 超過50 KBytes
					// 感覺用image.getFileSize()不準確,所以下面多使用Blob來判斷大小
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Course.errorN1",
									"請檢查上傳的照片大小是否過大(建議在50KB以下),謝謝!"));
					saveMessages(request, messages);
					return mapping.findForward(IConstants.ACTION_MAIN_NAME);
				} else {	
					InputStream is = image.getInputStream();
					StdImage tmp = new StdImage();
					tmp.setStudentNo(student.getStudentNo());
					StdImage stdImage = mm.findStdImageBy(tmp);
					if (stdImage != null) {
						try {
							Blob blob = Hibernate.createBlob(is);
							if ((51 * 1024) < blob.length()) {
								// 超過50 KBytes
								messages
										.add(
												ActionMessages.GLOBAL_MESSAGE,
												new ActionMessage(
														"Course.errorN1",
														"請檢查上傳的照片大小是否過大(建議在50KB以下),謝謝!"));
								saveMessages(request, messages);
								return mapping
										.findForward(IConstants.ACTION_MAIN_NAME);
							}
							stdImage.setImage(blob);
							mm.txUpdateStudentImage(stdImage);
						} catch (Exception e) {
							// log.error(e.getMessage(), e);
							messages
									.add(
											ActionMessages.GLOBAL_MESSAGE,
											new ActionMessage(
													"Exception.generic",
													"儲存照片時發生錯誤,請檢查上傳的照片大小是否過大(建議在50KB以下),謝謝!"));
							saveErrors(request, messages);
							return mapping
									.findForward(IConstants.ACTION_MAIN_NAME);
						}
					} else {
						tmp.setImage(Hibernate.createBlob(is));
						mm.txUpdateStudentImage(tmp);
					}
				}
			}
			request.setAttribute(STUDENT_INFO, student);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.ModifySuccessful"));
			saveMessages(request, messages);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Exception.generic", e.getMessage()));
			saveErrors(request, messages);
			return mapping.findForward(IConstants.ACTION_MAIN_NAME);
		}
		request.setAttribute("PhoneAndAddress", aForm.getMap());
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 處理點選"取消"時動作
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Save", "save");
		map.put("Cancel", "cancel");
		return map;
	}
	
}
