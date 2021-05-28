package tw.edu.chit.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;

import tw.edu.chit.dao.CourseDAO;
import tw.edu.chit.dao.CourseJdbcDAO;
import tw.edu.chit.dao.MemberDAO;
import tw.edu.chit.dao.MemberJdbcDAO;
import tw.edu.chit.gui.Menu;
import tw.edu.chit.gui.MenuItem;
import tw.edu.chit.model.AbilityExamine;
import tw.edu.chit.model.Aborigine;
import tw.edu.chit.model.AssessPaper;
import tw.edu.chit.model.ClassCadre;
import tw.edu.chit.model.ClassInCharge;
import tw.edu.chit.model.Clazz;
import tw.edu.chit.model.Code5;
import tw.edu.chit.model.CodeEmpl;
import tw.edu.chit.model.ContractTeacher;
import tw.edu.chit.model.DEmpl;
import tw.edu.chit.model.DeptCode4Yun;
import tw.edu.chit.model.Empl;
import tw.edu.chit.model.Employee;
import tw.edu.chit.model.Entrno;
import tw.edu.chit.model.Graduate;
import tw.edu.chit.model.Investigation;
import tw.edu.chit.model.InvestigationG;
import tw.edu.chit.model.LifeCounseling;
import tw.edu.chit.model.Member;
import tw.edu.chit.model.Module;
import tw.edu.chit.model.QuitResume;
import tw.edu.chit.model.Rcact;
import tw.edu.chit.model.Rcbook;
import tw.edu.chit.model.Rcconf;
import tw.edu.chit.model.Rchono;
import tw.edu.chit.model.Rcjour;
import tw.edu.chit.model.Rcpet;
import tw.edu.chit.model.Rcproj;
import tw.edu.chit.model.RecruitSchool;
import tw.edu.chit.model.Register;
import tw.edu.chit.model.RegistrationCard;
import tw.edu.chit.model.Seld;
import tw.edu.chit.model.StdAbility;
import tw.edu.chit.model.StdImage;
import tw.edu.chit.model.StdSkill;
import tw.edu.chit.model.Student;
import tw.edu.chit.model.TeacherOfficeLocation;
import tw.edu.chit.model.TeacherStayTime;
import tw.edu.chit.model.TeacherStayTimeModify;
import tw.edu.chit.model.TempStmd;
import tw.edu.chit.model.UnitBelong;
import tw.edu.chit.model.WwPass;
import tw.edu.chit.model.domain.UserCredential;
import tw.edu.chit.service.CourseManager;
//import tw.edu.chit.service.InvalidAccountException;
import tw.edu.chit.service.MemberManager;
import tw.edu.chit.util.Global;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Parameters;
import tw.edu.chit.util.Toolket;

/**
 * Implementation of MemberManager interface.
 */
public class MemberManagerImpl extends BaseManager implements MemberManager {

	private MemberDAO dao;
	private MemberJdbcDAO jdbcDao;
	private CourseJdbcDAO courseJdbcDao;

	private static final String StatusQuit = "1";
	private static final String StatusFlunkOut = "2";
	private static final String StatusResume = "4";
	private static final String StatusGraduate = "6";
	private static final String StatusDelayed = "8";
	private static final String StatusInSchool = "";

	private static final String EmpStatusQuit = "9";
	private static final String EmpStatusLeaveWithSalary = "1";
	private static final String EmpStatusLeaveWithoutSalary = "2";

	private static final String PrioEmployee = "A";
	private static final String PrioStudent = "C";
	private static final String PrioGstudent = "G";//離校學生

	/**
	 * Set the DAO for communication with the data layer.
	 * 
	 * @param dao
	 */
	public void setMemberDAO(MemberDAO dao) {
		this.dao = dao;
	}

	public void setJdbcDAO(MemberJdbcDAO dao) {
		this.jdbcDao = dao;
	}
	
	public void setCourseJdbcDAO(CourseJdbcDAO courseJdbcDao) {
		this.courseJdbcDao = courseJdbcDao;
	}
	
	private Integer ezGetInt(String sql){
		return courseJdbcDao.StandardSqlQueryForInt(sql);
	}
	
	/**
	 * 
	 * @param entity
	 * @param expression
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List findSQLWithCriteria(Class entity, SimpleExpression... expression)
			throws DataAccessException {
		return dao.getSQLWithCriteria(entity, expression);
	}
	
	/**
	 * 
	 * @param entity
	 * @param expression
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List findSQLWithCriteria(Class entity, Example example,
			Criterion... expression) throws DataAccessException {
		return dao.getSQLWithCriteria(entity, example, expression);
	}

	public UserCredential createUserCredential(String account, String password){
		WwPass user = dao.findWWPassByAccountPassword(account, password);//一般人
		if(user==null){
			//離校學生
			if(ezGetInt("SELECT COUNT(*)FROM Gstmd WHERE student_no='"+account+"' AND idno='"+password+"'")>0){
				user=new WwPass();
				user.setPassword(password);
				user.setPriority("G");
				user.setUsername(user.getUsername());
			}
		}
		
		if (PrioEmployee.equals(user.getPriority())) { // An employee
			Member emp = findMemberByAccount(user.getUsername());
			//System.out.println("emp="+emp);
			//if (emp == null) {
				//return null;
			//}
			Clazz[] classAry = dao.findClassesInChargeByMemberModuleOids(emp.getOid(), UserCredential.AuthorityOnTeachAffair).toArray(new Clazz[0]);
			List<Code5> campuses = new ArrayList<Code5>();
			List<Code5[]> schools = new ArrayList<Code5[]>();
			List<Code5[][]> depts = new ArrayList<Code5[][]>();
			List<Clazz[][][]> classes = new ArrayList<Clazz[][][]>();

			createClassInChargeDataStructure(classAry, campuses, schools, depts, classes);
			Clazz[] classArySAF = dao.findClassesInChargeByMemberModuleOids(emp.getOid(),
							UserCredential.AuthorityOnStudAffair).toArray(
							new Clazz[0]);

			List<Code5> campusesSAF = new ArrayList<Code5>();
			List<Code5[]> schoolsSAF = new ArrayList<Code5[]>();
			List<Code5[][]> deptsSAF = new ArrayList<Code5[][]>();
			List<Clazz[][][]> classesSAF = new ArrayList<Clazz[][][]>();

			createClassInChargeDataStructure(classArySAF, campusesSAF, schoolsSAF, deptsSAF, classesSAF);

			//
			// Construct class-incharge for this employee on Chairman(系主任)
			//
			Clazz[] classAryC = dao.findClassesInChargeByMemberModuleOids(
					emp.getOid(), UserCredential.AuthorityOnChairman).toArray(new Clazz[0]);

			List<Code5> campusesC = new ArrayList<Code5>();
			List<Code5[]> schoolsC = new ArrayList<Code5[]>();
			List<Code5[][]> deptsC = new ArrayList<Code5[][]>();
			List<Clazz[][][]> classesC = new ArrayList<Clazz[][][]>();

			createClassInChargeDataStructure(classAryC, campusesC,
					schoolsC, deptsC, classesC);

			//
			// Construct class-incharge for this employee on Drillmaster(教官)
			//
			Clazz[] classAryM = dao.findClassesInChargeByMemberModuleOids(
					emp.getOid(), UserCredential.AuthorityOnDrillmaster).toArray(new Clazz[0]);

			List<Code5> campusesM = new ArrayList<Code5>();
			List<Code5[]> schoolsM = new ArrayList<Code5[]>();
			List<Code5[][]> deptsM = new ArrayList<Code5[][]>();
			List<Clazz[][][]> classesM = new ArrayList<Clazz[][][]>();

			createClassInChargeDataStructure(classAryM, campusesM, schoolsM, deptsM, classesM);

			//
			// Construct class-incharge for this employee on Tutor(導師)
			//
			Clazz[] classAryT = dao.findClassesInChargeByMemberModuleOids(
					emp.getOid(), UserCredential.AuthorityOnTutor).toArray(new Clazz[0]);

			List<Code5> campusesT = new ArrayList<Code5>();
			List<Code5[]> schoolsT = new ArrayList<Code5[]>();
			List<Code5[][]> deptsT = new ArrayList<Code5[][]>();
			List<Clazz[][][]> classesT = new ArrayList<Clazz[][][]>();

			createClassInChargeDataStructure(classAryT, campusesT, schoolsT, deptsT, classesT);

			return new UserCredential(emp, classAry, campuses
					.toArray(new Code5[0]), schools
					.toArray(new Code5[0][0]), depts
					.toArray(new Code5[0][0][0]), classes
					.toArray(new Clazz[0][0][0][0]), classArySAF,
					campusesSAF.toArray(new Code5[0]), schoolsSAF
							.toArray(new Code5[0][0]), deptsSAF
							.toArray(new Code5[0][0][0]), classesSAF
							.toArray(new Clazz[0][0][0][0]), classAryC,
					campusesC.toArray(new Code5[0]), schoolsC
							.toArray(new Code5[0][0]), deptsC
							.toArray(new Code5[0][0][0]), classesC
							.toArray(new Clazz[0][0][0][0]), classAryM,
					campusesM.toArray(new Code5[0]), schoolsM
							.toArray(new Code5[0][0]), deptsM
							.toArray(new Code5[0][0][0]), classesM
							.toArray(new Clazz[0][0][0][0]), classAryT,
					campusesT.toArray(new Code5[0]), schoolsT
							.toArray(new Code5[0][0]), deptsT
							.toArray(new Code5[0][0][0]), classesT
							.toArray(new Clazz[0][0][0][0]));
		//在校學生
		} else if (PrioStudent.equals(user.getPriority())) {
			
			Student stud = dao.findStudentByStudentNo(account);
			//if (stud == null) {
				//return null;
			//}
			stud.setDepartClass2(Toolket.getClassFullName(stud.getDepartClass()));
			stud.setPassword(user.getPassword());
			stud.setPriority(user.getPriority());
			stud.setAccount(user.getUsername());
			stud.setInformixPass(user.getInformixPass());
			stud.setName(stud.getStudentName());
			return new UserCredential(stud, dao.findClassByClassNo(stud.getDepartClass()));
		//離校學生
		}else if(PrioGstudent.equals(user.getPriority())){			
			Graduate gstmd=dao.findGstudentByStudentNo(account);				
			gstmd.setPassword(user.getPassword());
			gstmd.setPriority(user.getPriority());
			gstmd.setAccount(user.getUsername());				
			gstmd.setName(gstmd.getStudentName());
			return new UserCredential(gstmd, dao.findClassByClassNo(gstmd.getDepartClass()));
			
		}
		

		return null;
	}
	
	public UserCredential createInetUserCredential(String account,
			String password) {

		WwPass user = dao.findWWPassByAccountPassword(account, password);

		if (user == null) {

			return null;

		} else {

			if (PrioEmployee.equals(user.getPriority())) { // An employee

				// Employee emp = dao.findEmployeeByIdno(account);
				Member emp = findMemberByAccount(account);
				if (emp == null) {
					return null;
				}
				// Populate member info
				// emp.setPassword(user.getPassword());

				//
				// Construct class-incharge for this employee on
				// TeachAffair(學籍,成績,課程)
				//

				/*
				 * We don't allow employee of TeachAffair Department to operate
				 * on internet Clazz[] classAry =
				 * dao.findClassesInChargeByMemberModuleOids(emp.getOid(),
				 * UserCredential.AuthorityOnTeachAffair) .toArray(new
				 * Clazz[0]);
				 */
				Clazz[] classAry = new Clazz[0];

				List<Code5> campuses = new ArrayList<Code5>();
				List<Code5[]> schools = new ArrayList<Code5[]>();
				List<Code5[][]> depts = new ArrayList<Code5[][]>();
				List<Clazz[][][]> classes = new ArrayList<Clazz[][][]>();

				createClassInChargeDataStructure(classAry, campuses, schools,
						depts, classes);

				//
				// Construct class-incharge for this employee on StudAffair(學務)
				//

				/*
				 * We don't allow employee of StudAffair Department to operate
				 * on internet Clazz[] classArySAF =
				 * dao.findClassesInChargeByMemberModuleOids(emp.getOid(),
				 * UserCredential.AuthorityOnStudAffair) .toArray(new Clazz[0]);
				 */
				Clazz[] classArySAF = new Clazz[0];

				List<Code5> campusesSAF = new ArrayList<Code5>();
				List<Code5[]> schoolsSAF = new ArrayList<Code5[]>();
				List<Code5[][]> deptsSAF = new ArrayList<Code5[][]>();
				List<Clazz[][][]> classesSAF = new ArrayList<Clazz[][][]>();

				// createClassInChargeDataStructure(classArySAF, campusesSAF,
				// schoolsSAF, deptsSAF, classesSAF);

				//
				// Construct class-incharge for this employee on Chairman(系主任)
				//
				Clazz[] classAryC = dao.findClassesInChargeByMemberModuleOids(
						emp.getOid(), UserCredential.AuthorityOnChairman)
						.toArray(new Clazz[0]);

				List<Code5> campusesC = new ArrayList<Code5>();
				List<Code5[]> schoolsC = new ArrayList<Code5[]>();
				List<Code5[][]> deptsC = new ArrayList<Code5[][]>();
				List<Clazz[][][]> classesC = new ArrayList<Clazz[][][]>();

				createClassInChargeDataStructure(classAryC, campusesC,
						schoolsC, deptsC, classesC);

				//
				// Construct class-incharge for this employee on Drillmaster(教官)
				//
				Clazz[] classAryM = dao.findClassesInChargeByMemberModuleOids(
						emp.getOid(), UserCredential.AuthorityOnDrillmaster)
						.toArray(new Clazz[0]);

				List<Code5> campusesM = new ArrayList<Code5>();
				List<Code5[]> schoolsM = new ArrayList<Code5[]>();
				List<Code5[][]> deptsM = new ArrayList<Code5[][]>();
				List<Clazz[][][]> classesM = new ArrayList<Clazz[][][]>();

				createClassInChargeDataStructure(classAryM, campusesM,
						schoolsM, deptsM, classesM);

				//
				// Construct class-incharge for this employee on Tutor(導師)
				//
				Clazz[] classAryT = dao.findClassesInChargeByMemberModuleOids(
						emp.getOid(), UserCredential.AuthorityOnTutor).toArray(
						new Clazz[0]);

				List<Code5> campusesT = new ArrayList<Code5>();
				List<Code5[]> schoolsT = new ArrayList<Code5[]>();
				List<Code5[][]> deptsT = new ArrayList<Code5[][]>();
				List<Clazz[][][]> classesT = new ArrayList<Clazz[][][]>();

				createClassInChargeDataStructure(classAryT, campusesT,
						schoolsT, deptsT, classesT);

				return new UserCredential(emp, classAry, campuses
						.toArray(new Code5[0]), schools
						.toArray(new Code5[0][0]), depts
						.toArray(new Code5[0][0][0]), classes
						.toArray(new Clazz[0][0][0][0]), classArySAF,
						campusesSAF.toArray(new Code5[0]), schoolsSAF
								.toArray(new Code5[0][0]), deptsSAF
								.toArray(new Code5[0][0][0]), classesSAF
								.toArray(new Clazz[0][0][0][0]), classAryC,
						campusesC.toArray(new Code5[0]), schoolsC
								.toArray(new Code5[0][0]), deptsC
								.toArray(new Code5[0][0][0]), classesC
								.toArray(new Clazz[0][0][0][0]), classAryM,
						campusesM.toArray(new Code5[0]), schoolsM
								.toArray(new Code5[0][0]), deptsM
								.toArray(new Code5[0][0][0]), classesM
								.toArray(new Clazz[0][0][0][0]), classAryT,
						campusesT.toArray(new Code5[0]), schoolsT
								.toArray(new Code5[0][0]), deptsT
								.toArray(new Code5[0][0][0]), classesT
								.toArray(new Clazz[0][0][0][0]));

			} else if (PrioStudent.equals(user.getPriority())) { // A student

				Student stud = dao.findStudentByStudentNo(account);
				if (stud == null) {
					return null;
				}
				stud.setDepartClass2(Toolket.getClassFullName(stud
						.getDepartClass()));
				stud.setPassword(user.getPassword());
				stud.setPriority(user.getPriority());
				stud.setAccount(user.getUsername());
				stud.setInformixPass(user.getInformixPass());
				stud.setName(stud.getStudentName());
				return new UserCredential(stud, dao.findClassByClassNo(stud
						.getDepartClass()));
			}else if(PrioGstudent.equals(user.getPriority())){
				//System.out.println("G");
				Graduate gstmd=dao.findGstudentByStudentNo(account);
				//gstmd.setDepartClass2(Toolket.getClassFullName(stud.getDepartClass()));
				gstmd.setPassword(user.getPassword());
				gstmd.setPriority(user.getPriority());
				gstmd.setAccount(user.getUsername());
				gstmd.setInformixPass(user.getInformixPass());
				gstmd.setName(gstmd.getStudentName());				
				if (gstmd == null) {
					return null;
				}
				return new UserCredential(gstmd, dao.findClassByClassNo(gstmd.getDepartClass()));
				
			}
		}

		return null;
	}

	public UserCredential createInetUserCredential4Parent(String idno, String birthdate, String parentName){
		Date birthday = Toolket.parseDateSerial(birthdate);
		if (birthday == null)
			return null;
		Student stud = dao.findStudentByIdnoBirthdayParentName(idno, birthday, null);
		if (stud == null) {
			return null;
		}
		stud.setDepartClass2(Toolket.getClassFullName(stud.getDepartClass()));
		stud.setPriority("C");
		stud.setAccount(idno);
		stud.setName(stud.getStudentName());
		return new UserCredential(stud, dao.findClassByClassNo(stud.getDepartClass()));
	}

	public void createClassInChargeDataStructure(Clazz[] classAry,
			List<Code5> campuses, List<Code5[]> schools, List<Code5[][]> depts,
			List<Clazz[][][]> classes) {

		String campusNo;
		String schoolNo;
		String deptNo;

		boolean existing;
		
		for (int i = 0; i < classAry.length; i++) {
			campusNo = classAry[i].getCampusNo();
			existing = false;
			for (int j = 0; j < campuses.size(); j++) {								
				if (campusNo.equals(campuses.get(j).getIdno())) {
					existing = true;
					break;
				}
			}
			if (!existing) {
				campuses.add(dao.findCode5ByCategoryIdno("Campus", campusNo));
			}
		}

		for (int k = 0; k < campuses.size(); k++) {
			campusNo = campuses.get(k).getIdno();
			List<Code5> schoolList = new ArrayList<Code5>();
			for (int i = 0; i < classAry.length; i++) {
				if (campusNo.equals(classAry[i].getCampusNo())) {
					schoolNo = classAry[i].getSchoolNo();
					existing = false;
					//去你媽的開機寫死，以後別人不知資料加哪怎麼辦
					for (int j = 0; j < schoolList.size(); j++) {						
						if (schoolNo.equals(schoolList.get(j).getIdno())) {
							existing = true;
							break;
						}
					}
					if (!existing) {
						schoolList.add(dao.findCode5ByCategoryIdno("School",
								schoolNo));
					}
				}
			}
			schools.add(schoolList.toArray(new Code5[0]));
		}

		for (int k = 0; k < campuses.size(); k++) {
			campusNo = campuses.get(k).getIdno();
			List<Code5[]> deptAryList = new ArrayList<Code5[]>();
			Code5[] school = schools.get(k);
			for (int n = 0; n < school.length; n++) {
				schoolNo = school[n].getIdno();
				List<Code5> deptList = new ArrayList<Code5>();
				for (int i = 0; i < classAry.length; i++) {
					if (campusNo.equals(classAry[i].getCampusNo())
							&& schoolNo.equals(classAry[i].getSchoolNo())) {
						deptNo = classAry[i].getDeptNo();
						existing = false;
						for (int j = 0; j < deptList.size(); j++) {
							System.out.println(deptNo+"找");
							System.out.println(deptList.get(j).getIdno());
							System.out.println("---------------------------------------");
							if (deptNo.equals(deptList.get(j).getIdno())) {
								existing = true;
								break;
							}
						}
						if (!existing) {
							deptList.add(dao.findCode5ByCategoryIdno("Dept",
									deptNo));
						}
					}
				}
				deptAryList.add(deptList.toArray(new Code5[0]));
			}
			depts.add(deptAryList.toArray(new Code5[0][0]));
		}

		for (int k = 0; k < campuses.size(); k++) {
			campusNo = campuses.get(k).getIdno();
			List<Clazz[][]> classAryAryList = new ArrayList<Clazz[][]>();
			Code5[] school = schools.get(k);
			for (int n = 0; n < school.length; n++) {
				schoolNo = school[n].getIdno();
				List<Clazz[]> classAryList = new ArrayList<Clazz[]>();
				Code5[] dept = depts.get(k)[n];
				for (int m = 0; m < dept.length; m++) {
					deptNo = dept[m].getIdno();
					List<Clazz> classList = new ArrayList<Clazz>();
					for (int i = 0; i < classAry.length; i++) {
						if (campusNo.equals(classAry[i].getCampusNo())
								&& schoolNo.equals(classAry[i].getSchoolNo())
								&& deptNo.equals(classAry[i].getDeptNo())) {
							classList.add(classAry[i]);
						}
					}
					classAryList.add(classList.toArray(new Clazz[0]));
				}
				classAryAryList.add(classAryList.toArray(new Clazz[0][0]));
			}
			classes.add(classAryAryList.toArray(new Clazz[0][0][0]));
		}

	}

	public Menu createMenuByMember(Member member) {

		List<Module> modules = dao.findModulesByMember(member.getOid(), 1);
		Menu menu = new Menu();
		MenuItem item;
		// Module module, subModule;
		List<Module> subModules;
		Menu subMenu;
		
		// 秘書室行事曆
		//item = new MenuItem(dao.findModulesByName("SecretaryCalendar"));
		//menu.addItem(item);

		/*
		 * for (int i=0; i < modules.size(); i++) { module =
		 * (Module)modules.get(i); log.debug("ModuleOid: " + module.getOid()); }
		 */

		boolean bFlag = false, bFlag1 = false;
		for (int i = 0; i < modules.size(); i++) {

			if (!bFlag)
				bFlag = modules.get(i).getName().equals("EmpEvaluate");
			if (!bFlag1)
				bFlag1 = modules.get(i).getName().equals("EmpTraining");
			
			item = new MenuItem(modules.get(i));
			subModules = dao.findModulesByParentOidMember(modules.get(i)
					.getOid(), member.getOid());
			if (subModules.size() > 0) {
				subMenu = new Menu();
				for (int j = 0; j < subModules.size(); j++) {
					subMenu.addItem(new MenuItem(subModules.get(j)));
				}
				item.setSubMenu(subMenu);
			}
			menu.addItem(item);
		}
		/*
		if (!bFlag) {
			// Add 教職員工考核 and all its sub-menu into menu -- open to all
			// employees
			item = new MenuItem(dao.findModulesByName("EmpEvaluate"));
			subModules = dao.findModulesByParentName("EmpEvaluate");
			if (subModules.size() > 0) {
				subMenu = new Menu();
				for (int j = 0; j < subModules.size(); j++) {
					subMenu.addItem(new MenuItem(subModules.get(j)));
				}
				item.setSubMenu(subMenu);
			}
			menu.addItem(item);
		}
		*/
		/*
		if (bFlag && !bFlag1) {
			// Add 教職員工進修 and all its sub-menu into menu -- open to all
			// employees
			item = new MenuItem(dao.findModulesByName("EmpTraining"));
			subModules = dao.findModulesByParentName("EmpTraining");
			if (!subModules.isEmpty()) {
				subMenu = new Menu();
				for (int j = 0; j < subModules.size(); j++) {
					subMenu.addItem(new MenuItem(subModules.get(j)));
				}
				item.setSubMenu(subMenu);
			}
			menu.addItem(item);
		}
		*/
		// 請透過"職員工"權限加入"一般查詢"與"個人資料"
		// Add 一般查詢 and all its sub-menu into menu -- open to all employees
//		item = new MenuItem(dao.findModulesByName("GeneralQuery"));
//		subModules = dao.findModulesByParentName("GeneralQuery");
//		if (subModules.size() > 0) {
//			subMenu = new Menu();
//			for (int j = 0; j < subModules.size(); j++) {
//				subMenu.addItem(new MenuItem(subModules.get(j)));
//			}
//			item.setSubMenu(subMenu);
//		}
//		menu.addItem(item);

		// Add 個人資料 and all its sub-menu into menu -- open to all employees
//		item = new MenuItem(dao.findModulesByName("Individual"));
//		subModules = dao.findModulesByParentName("Individual");
//		if (subModules.size() > 0) {
//			subMenu = new Menu();
//			for (int j = 0; j < subModules.size(); j++) {
//				subMenu.addItem(new MenuItem(subModules.get(j)));
//			}
//			item.setSubMenu(subMenu);
//		}
//		menu.addItem(item);

		// Add 舊資訊系統登入 into menu -- open to all employees
		//item = new MenuItem(dao.findModulesByName("Informix/Login"));
		//menu.addItem(item);
		
		// 意見反映
		//item = new MenuItem(dao.findModulesByName("OpinionSuggestion"));
		//menu.addItem(item);
		
		// 行事曆
		//item = new MenuItem(dao.findModulesByName("SchoolCalendar"));
		//menu.addItem(item);
		
		return menu;
	}

	public Menu createInetMenuByMember(Member member) {

		List<Module> modules = dao.findModulesByMemberInetUseEnable(member
				.getOid(), 1, (byte) 1, (byte) 1);
		Menu menu = new Menu();
		MenuItem item;
		// Module module, subModule;
		List<Module> subModules;
		Menu subMenu;

		/*
		 * for (int i=0; i < modules.size(); i++) { module =
		 * (Module)modules.get(i); log.debug("ModuleOid: " + module.getOid()); }
		 */

		for (int i = 0; i < modules.size(); i++) {
			// module = (Module)modules.get(i);
			item = new MenuItem(modules.get(i));
			subModules = dao.findModulesByParentOidMemberInetUseEnable(modules
					.get(i).getOid(), member.getOid(), (byte) 1, (byte) 1);
			if (subModules.size() > 0) {
				subMenu = new Menu();
				for (int j = 0; j < subModules.size(); j++) {
					// subModule = (Module)subModules.get(j);
					subMenu.addItem(new MenuItem(subModules.get(j)));
				}
				item.setSubMenu(subMenu);
			}
			menu.addItem(item);
		}

		// Add 個人資料 and all its sub-menu into menu -- open to all employees
		item = new MenuItem(dao.findModulesByName("Individual"));
		subModules = dao.findModulesByParentName("Individual");
		if (subModules.size() > 0) {
			subMenu = new Menu();
			for (int j = 0; j < subModules.size(); j++) {
				subMenu.addItem(new MenuItem(subModules.get(j)));
			}
			item.setSubMenu(subMenu);
		}
		menu.addItem(item);

		// Add 舊資訊系統登入 into menu -- open to all employees
		//item = new MenuItem(dao.findModulesByName("Informix/Login"));
		//menu.addItem(item);

		return menu;
	}

	public Menu createMenuForStudent() {

		Module module = dao.findModulesByName("Student");
		Menu menu = new Menu();
		Menu subMenu;

		if (module != null) {
			MenuItem item = new MenuItem(module);
			List<Module> subModules = dao.findModulesByParentName("Student");
			if (subModules.size() > 0) {
				subMenu = new Menu();
				for (int j = 0; j < subModules.size(); j++) {
					// subModule = (Module)subModules.get(j);
					subMenu.addItem(new MenuItem(subModules.get(j)));
				}
				item.setSubMenu(subMenu);
			}
			menu.addItem(item);
		}

		return menu;
	}

	/**
	 * 依據校區決定是否開放網路選課(進修學院無開放)
	 * 
	 * @param schoolType 校區代碼
	 * 
	 */
	public Menu createMenuForStudentBak(String schoolType) {

		Module module = dao.findModulesByName("Student");
		Menu menu = new Menu();
		Menu subMenu;
		Date now = new Date();
		boolean phase1 = now.after(Parameters.PHASE_1_BEGIN.getTime())
				&& now.before(Parameters.PHASE_1_DEADLINE.getTime());
		boolean phase2 = now.after(Parameters.PHASE_2_BEGIN.getTime())
				&& now.before(Parameters.PHASE_2_DEADLINE.getTime());
		boolean phase3 = now.after(Parameters.PHASE_3_BEGIN.getTime())
				&& now.before(Parameters.PHASE_3_DEADLINE.getTime());
		
		// 下學期課表
		boolean bNTC = now.after(Parameters.DAY_NEW_TERM_COURSES_BEGIN.getTime())
				&& now.before(Parameters.DAY_NEW_TERM_COURSES_END.getTime());
		boolean bNNTC = now.after(Parameters.NIGHT_NEW_TERM_COURSES_BEGIN
				.getTime())
				&& now.before(Parameters.NIGHT_NEW_TERM_COURSES_END.getTime());

		if (module != null) {
			MenuItem item = new MenuItem(module);
			List<Module> subModules = dao.findModulesByParentName("Student");
			if (subModules.size() > 0) {
				subMenu = new Menu();
				for (int j = 0; j < subModules.size(); j++) {
					String moduleName = subModules.get(j).getName();
					// 新學期課表檢查
					if (moduleName.equals("Student/StudentNewTermCourseSearch")) {
						if (schoolType.equals(IConstants.DAY)
								|| schoolType
										.equals(IConstants.HSIN_CHU_CAMPUS)) {
							if (bNTC)
								subMenu
										.addItem(new MenuItem(subModules.get(j)));
						} else if (schoolType.equals(IConstants.NIGHT)) {
							if (bNNTC)
								subMenu
										.addItem(new MenuItem(subModules.get(j)));
						}
						continue;
					}

					// 進修學院不參加網路選課
					if (!schoolType.equals(IConstants.HOLIDAY)) {
						if (moduleName.equals("Student/OnlineAddDelCourse")) {
							if (phase3)
								subMenu
										.addItem(new MenuItem(subModules.get(j)));
						} else if (moduleName
								.equals("Student/PhaseOneAddDelCourse")) {
							if (phase1)
								subMenu
										.addItem(new MenuItem(subModules.get(j)));
						} else if (moduleName
								.equals("Student/PhaseTwoAddDelCourse")) {
							if (phase2)
								subMenu
										.addItem(new MenuItem(subModules.get(j)));

						} else
							subMenu.addItem(new MenuItem(subModules.get(j)));
					} else {
						if (!moduleName.equals("Student/OnlineAddDelCourse")
								&& !moduleName
										.equals("Student/PhaseTwoAddDelCourse")
								&& !moduleName
										.equals("Student/PhaseOneAddDelCourse"))
							subMenu.addItem(new MenuItem(subModules.get(j)));
					}
				}
				item.setSubMenu(subMenu);
			}
			menu.addItem(item);
		}

		return menu;
	}
	
	/**
	 * 依據校區決定是否開放網路選課(進修學院無開放)
	 * 
	 * @param schoolType 校區代碼
	 * 
	 */
	public Menu createMenuForStudent(String schoolType) {

		Module module = dao.findModulesByName("Student");
		Menu menu = new Menu();
		Menu subMenu;
		Date now = new Date();
		// 判斷第1,2,3階段網路選課時間限制
		// TODO 改以資料表維護選課時間限制
		// 第一階段日間部
		boolean bDay1 = now.after(Parameters.DAY_PHASE_1_BEGIN.getTime())
				&& now.before(Parameters.DAY_PHASE_1_DEADLINE.getTime());
		// 第一階段進修推廣部
		boolean bNight1 = now.after(Parameters.NIGHT_PHASE_1_BEGIN.getTime())
				&& now.before(Parameters.NIGHT_PHASE_1_DEADLINE.getTime());
		// 第一階段新竹日夜間部
		boolean hDay1 = now.after(Parameters.HSIN_CHU_PHASE_1_BEGIN.getTime())
				&& now.before(Parameters.HSIN_CHU_PHASE_1_DEADLINE.getTime());
		// 第一階段進修學院　
		boolean hoDay1 = now.after(Parameters.HOLIDAY_PHASE_1_BEGIN.getTime())
				&& now.before(Parameters.HOLIDAY_PHASE_1_DEADLINE.getTime());

		// 第二階段日間部
		boolean bDay2 = now.after(Parameters.DAY_PHASE_2_BEGIN.getTime())
				&& now.before(Parameters.DAY_PHASE_2_DEADLINE.getTime());
		// 第二階段進修推廣部
		boolean bNight2 = now.after(Parameters.NIGHT_PHASE_2_BEGIN.getTime())
				&& now.before(Parameters.NIGHT_PHASE_2_DEADLINE.getTime());
		// 第二階段新竹日夜間部
		boolean hDay2 = now.after(Parameters.HSIN_CHU_PHASE_2_BEGIN.getTime())
				&& now.before(Parameters.HSIN_CHU_PHASE_2_DEADLINE.getTime());
		// 第二階段進修學院　
		boolean hoDay2 = now.after(Parameters.HOLIDAY_PHASE_2_BEGIN.getTime())
				&& now.before(Parameters.HOLIDAY_PHASE_2_DEADLINE.getTime());

		// 第三階段
		boolean bDay3 = now.after(Parameters.DAY_PHASE_3_BEGIN.getTime())
				&& now.before(Parameters.DAY_PHASE_3_DEADLINE.getTime());
		boolean bNight3 = now.after(Parameters.NIGHT_PHASE_3_BEGIN.getTime())
				&& now.before(Parameters.NIGHT_PHASE_3_DEADLINE.getTime());
		boolean hDay3 = now.after(Parameters.HSIN_CHU_PHASE_3_BEGIN.getTime())
				&& now.before(Parameters.HSIN_CHU_PHASE_3_DEADLINE.getTime());
		// 第三階段進修學院
		// TODO 進修學院有第三階段選課嗎?目前指定日間部第一階段,就不會開放了
		boolean hoDay3 = bDay1;

		// 下學期課表
		boolean bNTC = now.after(Parameters.DAY_NEW_TERM_COURSES_BEGIN
				.getTime())
				&& now.before(Parameters.DAY_NEW_TERM_COURSES_END.getTime());
		boolean bNNTC = now.after(Parameters.NIGHT_NEW_TERM_COURSES_BEGIN
				.getTime())
				&& now.before(Parameters.NIGHT_NEW_TERM_COURSES_END.getTime());

		if (module != null) {
			MenuItem item = new MenuItem(module);
			List<Module> subModules = dao.findModulesByParentName("Student");
			if (subModules.size() > 0) {
				subMenu = new Menu();
				for (int j = 0; j < subModules.size(); j++) {
					String moduleName = subModules.get(j).getName();
					// 新學期課表檢查
					if (moduleName.equals("Student/StudentNewTermCourseSearch")) {
						if (schoolType.equals(IConstants.DAY)
								|| schoolType
										.equals(IConstants.HSIN_CHU_CAMPUS)) {
							if (bNTC)
								subMenu
										.addItem(new MenuItem(subModules.get(j)));
						} else if (schoolType.equals(IConstants.NIGHT)) {
							if (bNNTC)
								subMenu
										.addItem(new MenuItem(subModules.get(j)));
						}
						continue;
					}

					// 進修學院不參加網路選課(99.1開始通識選課)
					// if (!schoolType.equals(IConstants.HOLIDAY)) {
					if (moduleName.equals("Student/OnlineAddDelCourse")) {
						// 第三階段
						if (schoolType.equals(IConstants.DAY)) {
							if (bDay3)
								subMenu
										.addItem(new MenuItem(subModules.get(j)));
						} else if (schoolType.equals(IConstants.NIGHT)) {
							if (bNight3)
								subMenu
										.addItem(new MenuItem(subModules.get(j)));
						} else if (schoolType
								.equals(IConstants.HSIN_CHU_CAMPUS)) {
							if (hDay3)
								subMenu
										.addItem(new MenuItem(subModules.get(j)));
						} else if (schoolType.equals(IConstants.HOLIDAY)) {
							if (hoDay3)
								subMenu
										.addItem(new MenuItem(subModules.get(j)));
						}
					} else if (moduleName
							.equals("Student/PhaseOneAddDelCourse")) {
						// 第一階段
						if (schoolType.equals(IConstants.DAY)) {
							if (bDay1)
								subMenu
										.addItem(new MenuItem(subModules.get(j)));
						} else if (schoolType.equals(IConstants.NIGHT)) {
							if (bNight1)
								subMenu
										.addItem(new MenuItem(subModules.get(j)));
						} else if (schoolType
								.equals(IConstants.HSIN_CHU_CAMPUS)) {
							if (hDay1)
								subMenu
										.addItem(new MenuItem(subModules.get(j)));
						} else if (schoolType.equals(IConstants.HOLIDAY)) {
							if (hoDay1)
								subMenu
										.addItem(new MenuItem(subModules.get(j)));
						}
					} else if (moduleName
							.equals("Student/PhaseTwoAddDelCourse")) {
						// 第二階段
						if (schoolType.equals(IConstants.DAY)) {
							if (bDay2)
								subMenu
										.addItem(new MenuItem(subModules.get(j)));
						} else if (schoolType.equals(IConstants.NIGHT)) {
							if (bNight2)
								subMenu
										.addItem(new MenuItem(subModules.get(j)));
						} else if (schoolType
								.equals(IConstants.HSIN_CHU_CAMPUS)) {
							if (hDay2)
								subMenu
										.addItem(new MenuItem(subModules.get(j)));
						} else if (schoolType.equals(IConstants.HOLIDAY)) {
							if (hoDay2)
								subMenu
										.addItem(new MenuItem(subModules.get(j)));
						}

					} else
						subMenu.addItem(new MenuItem(subModules.get(j)));

					// } else {
					// if (!moduleName.equals("Student/OnlineAddDelCourse")
					// && !moduleName
					// .equals("Student/PhaseTwoAddDelCourse")
					// && !moduleName
					// .equals("Student/PhaseOneAddDelCourse"))
					// subMenu.addItem(new MenuItem(subModules.get(j)));
					// }

				}
				item.setSubMenu(subMenu);
			}
			menu.addItem(item);
		}

		return menu;
	}

	public Menu createMenuForParent() {

		Module module = dao.findModulesByName("Parent");
		Menu menu = new Menu();
		Menu subMenu;

		if (module != null) {
			MenuItem item = new MenuItem(module);
			List<Module> subModules = dao.findModulesByParentName("Parent");
			if (subModules.size() > 0) {
				subMenu = new Menu();
				for (int j = 0; j < subModules.size(); j++) {
					// subModule = (Module)subModules.get(j);
					subMenu.addItem(new MenuItem(subModules.get(j)));
				}
				item.setSubMenu(subMenu);
			}
			menu.addItem(item);
		}

		return menu;
	}

	public Member findMemberByAccount(String account) {

		WwPass user = dao.findWWPassByAccount(account);
		if (user != null) {
			
			
			
			if (PrioEmployee.equals(user.getPriority())) { // An nmployee
				Employee emp = dao.findEmployeeByIdno(account);
				//System.out.println("emp"+emp);
				if (emp != null) {
					emp.setUnit2(Toolket.getEmpUnit(emp.getUnit()));
					emp.setIdno(emp.getAccount());
					emp.setBirthDate(Toolket.printNativeDate(emp.getBdate()));
					emp.setPriority(user.getPriority());
					emp.setPassword(user.getPassword());
					emp.setInformixPass(user.getInformixPass());
				}
				//System.out.println("emp"+emp);
				return emp;
			} else if (PrioStudent.equals(user.getPriority())) { // A student
				Student stud = dao.findStudentByStudentNo(account);
				/*
				 * Member member = new Member();
				 * member.setAccount(stud.getStudentNo());
				 * member.setName(stud.getStudentName());
				 * member.setUnit2(Toolket.getClassFullName(stud.getDepartClass()));
				 * member.setIdno(stud.getIdno());
				 * member.setBirthDate(Toolket.printNativeDate(stud.getBirthday()));
				 * member.setPriority(user.getPriority());
				 * member.setPassword(user.getPassword()); return member;
				 */
				stud.setAccount(stud.getStudentNo());
				stud.setName(stud.getStudentName());
				stud.setUnit2(Toolket.getClassFullName(stud.getDepartClass()));
				// member.setIdno(stud.getIdno());
				stud.setBirthDate(Toolket.printNativeDate(stud.getBirthday()));
				stud.setPriority(user.getPriority());
				stud.setPassword(user.getPassword());
				stud.setInformixPass(user.getInformixPass());
				return stud;
			}
		}
		return null;
	}

	/*
	 * public List findModulesByParent(String parentName) {
	 * 
	 * return null; }
	 */

	public List<Clazz> findClassInChargeByMemberAuthority(Integer memberOid,
			String authorityTarget) {

		return dao.findClassesInChargeByMemberModuleOids(memberOid,
				authorityTarget);
	}

	/**
	 * This method differs from findClassInChargeByMemberAuthority() only with
	 * additional user-friendly info has been looked up and feed into Clazz
	 * (actually base class ClazzBase)
	 * 
	 * @param memberOid
	 * @param authorityTarget
	 * @return
	 * 
	 * public List<Clazz> findClassInChargeByMemberAuthority2(Integer
	 * memberOid, String authorityTarget) {
	 * 
	 * List<Clazz> classes =
	 * dao.findClassesInChargeByMemberModuleOids(memberOid, authorityTarget);
	 * 
	 * for (Clazz clazz : classes) {
	 * clazz.setCampusName(Global.Campus.getProperty(clazz.getCampusNo()));
	 * clazz.setSchoolName(Global.School.getProperty(clazz.getSchoolNo()));
	 * clazz.setDeptName(Global.Dept.getProperty(clazz.getDeptNo()));
	 * clazz.setClassFullName(Global.ClassFullName.getProperty(clazz.getClassNo())); }
	 * return classes; }
	 */

	public List<Student> findStudentsInChargeByUnits(String campusNo,
			String schoolNo, String deptNo, String classNo, String classInCharge) {

		List<Student> students = null;
		if (!"".equals(classNo) && !"All".equals(classNo)) {
			students = dao.findStudentsByClazz(classNo);
		} else if (!"".equals(deptNo) && !"All".equals(deptNo)) {
			students = dao.findStudentsInChargeByDept(campusNo, schoolNo,
					deptNo, classInCharge);
		} else if (!"".equals(schoolNo) && !"All".equals(schoolNo)) {
			students = dao.findStudentsInChargeBySchool(campusNo, schoolNo,
					classInCharge);
		} else if (!"".equals(campusNo) && !"All".equals(campusNo)) {
			students = dao
					.findStudentsInChargeByCampus(campusNo, classInCharge);
		} else {
			students = dao.findAllStudentsInCharge(classInCharge);
		}
		return students;
	}

	@SuppressWarnings("unchecked")
	public List<Student> findStudentsByStudentInfoForm(Map formProperties) {

		String name = ((String) formProperties.get("name2")).trim();
		String studentNo = ((String) formProperties.get("studentNo2")).trim();
		String idNo = ((String) formProperties.get("idNo2")).trim();
		String status = (String) formProperties.get("status2");

		String campusNo = (String) formProperties.get("campusInCharge2");
		String schoolNo = (String) formProperties.get("schoolInCharge2");
		String deptNo = (String) formProperties.get("deptInCharge2");
		String classNo = (String) formProperties.get("classInCharge2");

		log.debug("=======> name='" + name + "'");
		log.debug("=======> status='" + status + "'");

		StringBuffer hql = new StringBuffer(
				"select s from Student s, Clazz c where s.departClass = c.classNo ");

		if (!"".equals(name)) {
			hql.append("and s.studentName like '%" + name + "%' ");
		}
		if (!"".equals(studentNo)) {
			hql.append("and s.studentNo like '%" + studentNo + "%' ");
		}
		if (!"".equals(idNo)) {
			hql.append("and s.idno like '%" + idNo + "%' ");
		}
		if (!"".equals(status)) {
			hql.append("and s.occurStatus = '" + status + "' ");
		}

		if (!"".equals(classNo) && !"All".equals(classNo)) {
			hql.append("and c.classNo = '" + classNo + "' ");
			return dao.submitQuery(hql.toString());
		} else if (!"".equals(deptNo) && !"All".equals(deptNo)) {
			hql.append("and c.campusNo = '" + campusNo + "' "
					+ "and c.schoolNo = '" + schoolNo + "' "
					+ "and c.deptNo = '" + deptNo + "' ");
		} else if (!"".equals(schoolNo) && !"All".equals(schoolNo)) {
			hql.append("and c.campusNo = '" + campusNo + "' "
					+ "and c.schoolNo = '" + schoolNo + "' ");
		} else if (!"".equals(campusNo) && !"All".equals(campusNo)) {
			hql.append("and c.campusNo = '" + campusNo + "' ");
		}

		// hql.append(" and s.departClass in " + classInCharge);

		// IF gstmd(Graduate) is merged into stmd(Student)
		// hql += " and (s.occurStatus IS NULL or s.occurStatus = '' or
		// s.occurStatus = '0'"
		// + " or s.occurStatus = '3' or s.occurStatus = '4' or s.occurStatus =
		// '5')";

		return dao.submitQuery(hql.toString());
	}

	@SuppressWarnings("unchecked")
	public List<Student> findStudentsInChargeByStudentInfoForm(
			Map formProperties, String classInCharge) {

		String name = ((String) formProperties.get("name2")).trim();
		String studentNo = ((String) formProperties.get("studentNo2")).trim();
		String idNo = ((String) formProperties.get("idNo2")).trim();
		String status = (String) formProperties.get("status2");

		String campusNo = (String) formProperties.get("campusInCharge2");
		String schoolNo = (String) formProperties.get("schoolInCharge2");
		String deptNo = (String) formProperties.get("deptInCharge2");
		String classNo = (String) formProperties.get("classInCharge2");
		// campusNo = "1";
		// schoolNo = "15";
		// deptNo = "2";
		// classNo = "A";
		// classNo = "1152A";

		log.debug("=======> name='" + name + "'");
		log.debug("=======> status='" + status + "'");

		StringBuffer hql = new StringBuffer(
				"select s from Student s, Clazz c where s.departClass = c.classNo ");

		if (!"".equals(name)) {
			hql.append("and s.studentName like '%" + name + "%' ");
		}
		if (!"".equals(studentNo)) {
			hql.append("and s.studentNo like '%" + studentNo + "%' ");
		}
		if (!"".equals(idNo)) {
			hql.append("and s.idno like '%" + idNo + "%' ");
		}
		if (!"".equals(status)) {
			hql.append("and s.occurStatus = '" + status + "' ");
		}

		if (!"".equals(classNo) && !"All".equals(classNo)) {
			hql.append("and c.classNo = '" + classNo + "' ");
			return dao.submitQuery(hql.toString());
		} else if (!"".equals(deptNo) && !"All".equals(deptNo)) {
			hql.append("and c.campusNo = '" + campusNo + "' "
					+ "and c.schoolNo = '" + schoolNo + "' "
					+ "and c.deptNo = '" + deptNo + "' ");
		} else if (!"".equals(schoolNo) && !"All".equals(schoolNo)) {
			hql.append("and c.campusNo = '" + campusNo + "' "
					+ "and c.schoolNo = '" + schoolNo + "' ");
		} else if (!"".equals(campusNo) && !"All".equals(campusNo)) {
			hql.append("and c.campusNo = '" + campusNo + "' ");
		}

		hql.append(" and s.departClass in " + classInCharge);
		hql.append(" ORDER BY s.studentNo");

		// IF gstmd(Graduate) is merged into stmd(Student)
		// hql += " and (s.occurStatus IS NULL or s.occurStatus = '' or
		// s.occurStatus = '0'"
		// + " or s.occurStatus = '3' or s.occurStatus = '4' or s.occurStatus =
		// '5')";

		return dao.submitQuery(hql.toString());
	}

	/**
	 * 查詢學生資料
	 * 
	 * @commend 包括全校學生
	 * @param formProperties Request Parameters
	 * @return java.util.List List of Student objects
	 */
	@SuppressWarnings("unchecked")
	public List<Student> findStudentsInChargeByStudentInfo(Map formProperties) {

		String name = ((String) formProperties.get("name2")).trim();
		String studentNo = ((String) formProperties.get("studentNo2")).trim();
		String idNo = ((String) formProperties.get("idNo2")).trim();
		String status = (String) formProperties.get("status2");
		String campusNo = (String) formProperties.get("campusInCharge2");
		String schoolNo = (String) formProperties.get("schoolInCharge2");
		String deptNo = (String) formProperties.get("deptInCharge2");
		String classNo = (String) formProperties.get("classInCharge2");

		StringBuffer hql = new StringBuffer(
				"select s from Student s, Clazz c where s.departClass = c.classNo ");
		if (!"".equals(name)) {
			hql.append("and s.studentName like '%" + name + "%' ");
		}
		if (!"".equals(studentNo)) {
			hql.append("and s.studentNo like '%" + studentNo + "%' ");
		}
		if (!"".equals(idNo)) {
			hql.append("and s.idno like '%" + idNo + "%' ");
		}
		if (!"".equals(status)) {
			hql.append("and s.occurStatus = '" + status + "' ");
		}

		if (!"".equals(classNo) && !"All".equals(classNo)) {
			hql.append("and c.classNo = '" + classNo + "' ");
			return dao.submitQuery(hql.toString());
		} else if (!"".equals(deptNo) && !"All".equals(deptNo)) {
			hql.append("and c.campusNo = '" + campusNo + "' "
					+ "and c.schoolNo = '" + schoolNo + "' "
					+ "and c.deptNo = '" + deptNo + "' ");
		} else if (!"".equals(schoolNo) && !"All".equals(schoolNo)) {
			hql.append("and c.campusNo = '" + campusNo + "' "
					+ "and c.schoolNo = '" + schoolNo + "' ");
		} else if (!"".equals(campusNo) && !"All".equals(campusNo)) {
			hql.append("and c.campusNo = '" + campusNo + "' ");
		}

		return dao.submitQuery(hql.toString());
	}

	/**
	 * find student list by criteria DynaActionForm.getMap() for
	 * StudentInfoFormT used for query student info by tutor
	 * 
	 * @param formProperties
	 * @param classInCharge
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Student> findStudentsInChargeByStudentInfoFormT(
			Map formProperties, String classInCharge) {

		String name = ((String) formProperties.get("nameT")).trim();
		String studentNo = ((String) formProperties.get("studentNoT")).trim();
		String idNo = ((String) formProperties.get("idNoT")).trim();
		String status = (String) formProperties.get("status2");

		String campusNo = (String) formProperties.get("campusInChargeT");
		String schoolNo = (String) formProperties.get("schoolInChargeT");
		String deptNo = (String) formProperties.get("deptInChargeT");
		String classNo = (String) formProperties.get("classInChargeT");

		log.debug("=======> name='" + name + "'");
		log.debug("=======> status='" + status + "'");

		StringBuffer hql = new StringBuffer(
				"select s from Student s, Clazz c where s.departClass = c.classNo ");

		if (!"".equals(name)) {
			hql.append("and s.studentName like '%" + name + "%' ");
		}
		if (!"".equals(studentNo)) {
			hql.append("and s.studentNo like '%" + studentNo + "%' ");
		}
		if (!"".equals(idNo)) {
			hql.append("and s.idno like '%" + idNo + "%' ");
		}
		if (!"".equals(status)) {
			hql.append("and s.occurStatus = '" + status + "' ");
		}

		if (!"".equals(classNo) && !"All".equals(classNo)) {
			hql.append("and c.classNo = '" + classNo + "' ");
			return dao.submitQuery(hql.toString());
		} else if (!"".equals(deptNo) && !"All".equals(deptNo)) {
			hql.append("and c.campusNo = '" + campusNo + "' "
					+ "and c.schoolNo = '" + schoolNo + "' "
					+ "and c.deptNo = '" + deptNo + "' ");
		} else if (!"".equals(schoolNo) && !"All".equals(schoolNo)) {
			hql.append("and c.campusNo = '" + campusNo + "' "
					+ "and c.schoolNo = '" + schoolNo + "' ");
		} else if (!"".equals(campusNo) && !"All".equals(campusNo)) {
			hql.append("and c.campusNo = '" + campusNo + "' ");
		}

		hql.append(" and s.departClass in " + classInCharge);

		// IF gstmd(Graduate) is merged into stmd(Student)
		// hql += " and (s.occurStatus IS NULL or s.occurStatus = '' or
		// s.occurStatus = '0'"
		// + " or s.occurStatus = '3' or s.occurStatus = '4' or s.occurStatus =
		// '5')";

		return dao.submitQuery(hql.toString());
	}

	/**
	 * find student list by criteria DynaActionForm.getMap() for
	 * StudentInfoFormC used for query student info by chairman of department
	 * 
	 * @param formProperties
	 * @param classInCharge
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Student> findStudentsInChargeByStudentInfoFormC(
			Map formProperties, String classInCharge) {

		String name = ((String) formProperties.get("nameC")).trim();
		String studentNo = ((String) formProperties.get("studentNoC")).trim();
		String idNo = ((String) formProperties.get("idNoC")).trim();
		String status = (String) formProperties.get("status2");

		String campusNo = (String) formProperties.get("campusInChargeC");
		String schoolNo = (String) formProperties.get("schoolInChargeC");
		String deptNo = (String) formProperties.get("deptInChargeC");
		String classNo = (String) formProperties.get("classInChargeC");

		log.debug("=======> name='" + name + "'");
		log.debug("=======> status='" + status + "'");

		StringBuffer hql = new StringBuffer(
				"select s from Student s, Clazz c where s.departClass = c.classNo ");

		if (!"".equals(name)) {
			hql.append("and s.studentName like '%" + name + "%' ");
		}
		if (!"".equals(studentNo)) {
			hql.append("and s.studentNo like '%" + studentNo + "%' ");
		}
		if (!"".equals(idNo)) {
			hql.append("and s.idno like '%" + idNo + "%' ");
		}
		if (!"".equals(status)) {
			hql.append("and s.occurStatus = '" + status + "' ");
		}

		if (!"".equals(classNo) && !"All".equals(classNo)) {
			hql.append("and c.classNo = '" + classNo + "' ");
			return dao.submitQuery(hql.toString());
		} else if (!"".equals(deptNo) && !"All".equals(deptNo)) {
			hql.append("and c.campusNo = '" + campusNo + "' "
					+ "and c.schoolNo = '" + schoolNo + "' "
					+ "and c.deptNo = '" + deptNo + "' ");
		} else if (!"".equals(schoolNo) && !"All".equals(schoolNo)) {
			hql.append("and c.campusNo = '" + campusNo + "' "
					+ "and c.schoolNo = '" + schoolNo + "' ");
		} else if (!"".equals(campusNo) && !"All".equals(campusNo)) {
			hql.append("and c.campusNo = '" + campusNo + "' ");
		}

		hql.append(" and s.departClass in " + classInCharge);

		// IF gstmd(Graduate) is merged into stmd(Student)
		// hql += " and (s.occurStatus IS NULL or s.occurStatus = '' or
		// s.occurStatus = '0'"
		// + " or s.occurStatus = '3' or s.occurStatus = '4' or s.occurStatus =
		// '5')";

		return dao.submitQuery(hql.toString());
	}

	@SuppressWarnings("unchecked")
	public List<Graduate> findGraduatesInChargeByGraduateInfoForm(
			Map formProperties, String classInCharge) {

		String name = ((String) formProperties.get("name3")).trim();
		String studentNo = ((String) formProperties.get("studentNo3")).trim();
		String idNo = ((String) formProperties.get("idNo3")).trim();
		String status = (String) formProperties.get("status3");

		String campusNo = (String) formProperties.get("campusInCharge3");
		String schoolNo = (String) formProperties.get("schoolInCharge3");
		String deptNo = (String) formProperties.get("deptInCharge3");
		String classNo = (String) formProperties.get("classInCharge3");

		log.debug("=======> name='" + name + "'");
		log.debug("=======> status='" + status + "'");

		StringBuffer hql = new StringBuffer(
				"select s from Graduate s, Clazz c where s.departClass = c.classNo ");

		if (!"".equals(name)) {
			hql.append("and s.studentName like '%" + name + "%' ");
		}
		if (!"".equals(studentNo)) {
			hql.append("and s.studentNo like '%" + studentNo + "%' ");
		}
		if (!"".equals(idNo)) {
			hql.append("and s.idno like '%" + idNo + "%' ");
		}
		if (!"".equals(status)) {
			hql.append("and s.occurStatus = '" + status + "' ");
		}

		if (!"".equals(classNo) && !"All".equals(classNo)) {
			hql.append("and c.classNo = '" + classNo + "' ");
			return dao.submitQuery(hql.toString());
		} else if (!"".equals(deptNo) && !"All".equals(deptNo)) {
			hql.append("and c.campusNo = '" + campusNo + "' "
					+ "and  c.schoolNo = '" + schoolNo + "' "
					+ "and  c.deptNo = '" + deptNo + "' ");
		} else if (!"".equals(schoolNo) && !"All".equals(schoolNo)) {
			hql.append("and  c.campusNo = '" + campusNo + "' "
					+ "and  c.schoolNo = '" + schoolNo + "' ");
		} else if (!"".equals(campusNo) && !"All".equals(campusNo)) {
			hql.append("and  c.campusNo = '" + campusNo + "' ");
		}

		hql.append(" and s.departClass in " + classInCharge);

		// IF gstmd(Graduate) is merged into stmd(Student)
		// hql += " and (s.occurStatus IS NULL or s.occurStatus = '' or
		// s.occurStatus = '0'"
		// + " or s.occurStatus = '3' or s.occurStatus = '4' or s.occurStatus =
		// '5')";

		return dao.submitQuery(hql.toString());
	}

	public Graduate findGraduateByStudentNo(String studentNo) {

		Graduate graduate = dao.findGraduateByStudentNo(studentNo);
		if (graduate == null) {
			return null;
		} else {
			graduate.setDepartClass2(Toolket.getClassFullName(graduate
					.getDepartClass()));
			return graduate;
		}
	}
	
	public Graduate findGraduateByIdno(String idno) {

		Graduate graduate = dao.findGraduateByIdno(idno);
		if (graduate == null) {
			return null;
		} else {
			graduate.setDepartClass2(Toolket.getClassFullName(graduate
					.getDepartClass()));
			return graduate;
		}
	}

	public List<Student> deleteStudents(List<Student> students,
			ResourceBundle bundle) {

		List<Student> undeleted = new ArrayList<Student>();
		Student stu;
		for (Iterator<Student> stuIter = students.iterator(); stuIter.hasNext();) {
			stu = stuIter.next();
			// Reset UndeleteReanon
			stu.setUndeleteReason("");

			// MOVE THE FOLLOWING TO isLeaveProcedureCompleted()
			/*
			 * // Check for librarian if
			 * (jdbcDao.getLibboroCountByReaderNo(stu.getStudentNo()) > 0) {
			 * stu.setUndeleteReason(stu.getUndeleteReason() + "借書未還" + " "); if
			 * (!undeleted.contains(stu)) { undeleted.add(stu); } } // Check for
			 * score history (ScoreHist) and course selection (Seld) records if
			 * (jdbcDao.getScoreHistCountByStudentNo(stu.getStudentNo()) > 0) {
			 * stu.setUndeleteReason(stu.getUndeleteReason() + "有選課及成績記錄" + "
			 * "); if (!undeleted.contains(stu)) { undeleted.add(stu); } } else
			 * if (jdbcDao.getSeldCountByStudentNo(stu.getStudentNo()) > 0) {
			 * stu.setUndeleteReason(stu.getUndeleteReason() + "有選課記錄" + " ");
			 * if (!undeleted.contains(stu)) { undeleted.add(stu); } }
			 * 
			 * if (false) { stu.setUndeleteReason(stu.getUndeleteReason() +
			 * "XXXX" + " "); if (!undeleted.contains(stu)) {
			 * undeleted.add(stu); } } // ...
			 */

			if (isDeleteProcedureCompleted(stu, bundle)) {
				dao.removeWWPassByUsername(stu.getStudentNo());
				dao.removeStudent(stu);
				Toolket.sendWWPassInfoByQueue(stu.getStudentNo(),
						stu.getIdno(), stu.getPriority(), "",
						IConstants.SYNC_DO_TYPE_DELETE);
				Toolket.sendStudentInfoByQueue(stu,
						IConstants.SYNC_DO_TYPE_DELETE);
			} else {
				if (!undeleted.contains(stu)) {
					undeleted.add(stu);
				}
			}
			/*
			 * if ("".equals(stu.getUndeleteReason())) { dao.removeStudent(stu); }
			 */
		}
		return undeleted;
	}

	public List<Graduate> deleteGraduates(List<Graduate> students,
			ResourceBundle bundle) {

		List<Graduate> undeleted = new ArrayList<Graduate>();
		Graduate stu;
		for (Iterator<Graduate> stuIter = students.iterator(); stuIter
				.hasNext();) {
			stu = stuIter.next();
			// Reset UndeleteReanon
			stu.setUndeleteReason("");

			// MOVE THE FOLLOWING TO isLeaveProcedureCompleted()
			/*
			 * // Check for librarian if
			 * (jdbcDao.getLibboroCountByReaderNo(stu.getStudentNo()) > 0) {
			 * stu.setUndeleteReason(stu.getUndeleteReason() + "借書未還" + " "); if
			 * (!undeleted.contains(stu)) { undeleted.add(stu); } } // Check for
			 * score history (ScoreHist) and course selection (Seld) records if
			 * (jdbcDao.getScoreHistCountByStudentNo(stu.getStudentNo()) > 0) {
			 * stu.setUndeleteReason(stu.getUndeleteReason() + "有選課及成績記錄" + "
			 * "); if (!undeleted.contains(stu)) { undeleted.add(stu); } } else
			 * if (jdbcDao.getSeldCountByStudentNo(stu.getStudentNo()) > 0) {
			 * stu.setUndeleteReason(stu.getUndeleteReason() + "有選課記錄" + " ");
			 * if (!undeleted.contains(stu)) { undeleted.add(stu); } }
			 * 
			 * if (false) { stu.setUndeleteReason(stu.getUndeleteReason() +
			 * "XXXX" + " "); if (!undeleted.contains(stu)) {
			 * undeleted.add(stu); } } // ...
			 */

			if (isDeleteProcedureCompleted(stu, bundle)) {
				dao.removeGraduate(stu);
				Toolket.sendGraduateInfoByQueue(stu,
						IConstants.SYNC_DO_TYPE_DELETE);
			} else {
				if (!undeleted.contains(stu)) {
					undeleted.add(stu);
				}
			}
			/*
			 * if ("".equals(stu.getUndeleteReason())) {
			 * dao.removeGraduate(stu); }
			 */
		}
		return undeleted;
	}

	private boolean isDeleteProcedureCompleted(Student stu,
			ResourceBundle bundle) {

		boolean completed = true;
		// TODO Check for librarian
		/*
		 * if (jdbcDao.getLibboroCountByReaderNo(stu.getStudentNo()) > 0) {
		 * stu.setUndeleteReason(stu.getUndeleteReason() + "借書未還" + " ");
		 * completed = false; }
		 */

		// Check for score history (ScoreHist) and course selection (Seld)
		// records
		if (jdbcDao.getScoreHistCountByStudentNo(stu.getStudentNo()) > 0) {
			stu.setUndeleteReason(stu.getUndeleteReason() + "有選課及成績記錄" + "  ");
			completed = false;
		} else if (jdbcDao.getSeldCountByStudentNo(stu.getStudentNo()) > 0) {
			stu.setUndeleteReason(stu.getUndeleteReason() + "有選課記錄" + "  ");
			completed = false;
		}
		// TODO Check for other administration...
		if (false) {
			stu.setUndeleteReason(stu.getUndeleteReason() + "XXXX" + "  ");
			completed = false;
		}
		// ...

		return completed;
	}

	private boolean isDeleteProcedureCompleted(Graduate stu,
			ResourceBundle bundle) {

		boolean completed = true;
		// TODO Check for librarian
		/*
		 * if (jdbcDao.getLibboroCountByReaderNo(stu.getStudentNo()) > 0) {
		 * stu.setUndeleteReason(stu.getUndeleteReason() + "借書未還" + " ");
		 * completed = false; }
		 */

		// Check for score history (ScoreHist) and course selection (Seld)
		// records
		if (jdbcDao.getScoreHistCountByStudentNo(stu.getStudentNo()) > 0) {
			stu.setUndeleteReason(stu.getUndeleteReason() + "有選課及成績記錄" + "  ");
			completed = false;
		} else if (jdbcDao.getSeldCountByStudentNo(stu.getStudentNo()) > 0) {
			stu.setUndeleteReason(stu.getUndeleteReason() + "有選課記錄" + "  ");
			completed = false;
		}
		// TODO Check for other administration...
		if (false) {
			stu.setUndeleteReason(stu.getUndeleteReason() + "XXXX" + "  ");
			completed = false;
		}
		// ...

		return completed;
	}

	public void deleteEmployees(List<Empl> employees, ResourceBundle bundle) {

		for (Empl emp : employees) {
			dao.removeWWPassByUsername(emp.getIdno());
			dao.removeEmpl(emp);
			deleteEmployeeAuthorities(emp);
			Toolket
					.sendEmployeeInfoByQueue(emp,
							IConstants.SYNC_DO_TYPE_DELETE);
			Toolket.sendWWPassInfoByQueue(emp.getIdno(), "", "", "",
					IConstants.SYNC_DO_TYPE_DELETE);
		}
	}

	public void deleteQuitEmployees(List<DEmpl> employees, ResourceBundle bundle) {

		for (DEmpl emp : employees) {
			dao.removeDEmpl(emp);
			Toolket.sendDEmplInfoByQueue(emp, IConstants.SYNC_DO_TYPE_DELETE);
		}
	}

	@SuppressWarnings("unchecked")
	public void validateStudentCreateForm(Map formProperties,
			ActionMessages errors, ResourceBundle bundle) {

		String studentNo = ((String) formProperties.get("studentNo")).trim();
		if (jdbcDao.getStudentCountByStudentNo(studentNo) > 0
				|| jdbcDao.getGraduateCountByStudentNo(studentNo) > 0) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.DuplicateStudentNo"));
		}
		// MOVED TO StudentInfoEditAction.validateFieldFormat()
		/*
		 * String status = ((String)formProperties.get("statusSel")).trim(); if
		 * (!StatusInSchool.equals(status) && !StatusResume.equals(status)) {
		 * errors.add(ActionMessages.GLOBAL_MESSAGE, new
		 * ActionMessage("Message.InvalidStudentStatus")); }
		 */
	}

	@SuppressWarnings("unchecked")
	public void validateGraduateCreateForm(Map formProperties,
			ActionMessages errors, ResourceBundle bundle) {

		String studentNo = ((String) formProperties.get("studentNo")).trim();
		if (jdbcDao.getStudentCountByStudentNo(studentNo) > 0
				|| jdbcDao.getGraduateCountByStudentNo(studentNo) > 0) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.DuplicateStudentNo"));
		}
		// MOVED TO GraduateInfoEditAction.validateFieldFormat()
		/*
		 * String status = ((String)formProperties.get("statusSel")).trim(); if
		 * (StatusInSchool.equals(status) || StatusResume.equals(status)) {
		 * errors.add(ActionMessages.GLOBAL_MESSAGE, new
		 * ActionMessage("Message.InvalidGraduateStatus")); }
		 */
	}

	@SuppressWarnings("unchecked")
	public void validateEmployeeCreateForm(Map formProperties,
			ActionMessages errors, ResourceBundle bundle) {

		String idno = ((String) formProperties.get("idno")).trim();
		if (jdbcDao.getEmplCountByIdno(idno) > 0) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.DuplicateIdNo"));
		}
	}

	@SuppressWarnings("unchecked")
	public void validateStudentModifyForm(Map formProperties, Student student,
			ActionMessages errors, ResourceBundle bundle) {

		String studentNo = ((String) formProperties.get("studentNo")).trim();
		int count = jdbcDao.getStudentCountByStudentNo(studentNo);

		if ((studentNo.equals(student.getStudentNo()) && count > 1)
				|| (!studentNo.equals(student.getStudentNo()) && count > 0)
				|| jdbcDao.getGraduateCountByStudentNo(studentNo) > 0) {

			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.DuplicateStudentNo"));
		}
		String status = ((String) formProperties.get("statusSel")).trim();

		// if 休(Quit),退(FlunkOut),畢(Graduate with graduateNo not empty), check
		// if 離校手續(Leave procedure) is completed
		if (status.equals(StatusQuit)
				|| status.equals(StatusFlunkOut)
				|| (status.equals(StatusGraduate) && !""
						.equals(((String) formProperties.get("graduateNo"))
								.trim()))) {
			if (!isLeaveProcedureCompleted(student, bundle)) {
				if (errors.isEmpty())
					student.setSubmitAutoClear(true);
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"Message.LeaveProcedureUncompleted", student
								.getUndeleteReason()));
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void validateGraduateModifyForm(Map formProperties,
			Graduate student, ActionMessages errors, ResourceBundle bundle) {

		String studentNo = ((String) formProperties.get("studentNo")).trim();
		int count = jdbcDao.getGraduateCountByStudentNo(studentNo);

		if ((studentNo.equals(student.getStudentNo()) && count > 1)
				|| (!studentNo.equals(student.getStudentNo()) && count > 0)
				|| jdbcDao.getStudentCountByStudentNo(studentNo) > 0) {

			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.DuplicateStudentNo"));
		}
		String status = ((String) formProperties.get("statusSel")).trim();
		if (StatusResume.equals(status)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.generic", "復學作業請以\"休學生復學處理\"程序操作"));
		}
	}

	@SuppressWarnings("unchecked")
	public void validateEmployeeModifyForm(Map formProperties, Empl employee,
			ActionMessages errors, ResourceBundle bundle) {

		String idno = ((String) formProperties.get("idno")).trim();
		int count = jdbcDao.getEmplCountByIdno(idno);
		if ((idno.equals(employee.getIdno()) && count > 1)
				|| (!idno.equals(employee.getIdno()) && count > 0)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.DuplicateIdNo"));
		}
	}

	@SuppressWarnings("unchecked")
	public void validateQuitEmployeeModifyForm(Map formProperties,
			DEmpl employee, ActionMessages errors, ResourceBundle bundle) {

		String idno = ((String) formProperties.get("idno")).trim();
		int count = jdbcDao.getDEmplCountByIdno(idno);
		if ((idno.equals(employee.getIdno()) && count > 1)
				|| (!idno.equals(employee.getIdno()) && count > 0)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Message.DuplicateIdNo"));
		}
	}

	private boolean isLeaveProcedureCompleted(Student stu, ResourceBundle bundle) {

		boolean completed = true;
		stu.setUndeleteReason("");
		// TODO Check for librarian
		/*
		 * if (jdbcDao.getLibboroCountByReaderNo(stu.getStudentNo()) > 0) {
		 * stu.setUndeleteReason(stu.getUndeleteReason() + "借書未還" + " ");
		 * completed = false; }
		 */

		// Check for course selection (Seld) records
		if (jdbcDao.getSeldCountByStudentNo(stu.getStudentNo()) > 0) {
			stu.setUndeleteReason(stu.getUndeleteReason() + "有選課記錄尚未清除或轉為歷史檔"
					+ " ");
			completed = false;
		}
		// TODO Check for other administration...
		if (false) {
			stu.setUndeleteReason(stu.getUndeleteReason() + "XXXX" + "  ");
			completed = false;
		}
		// ...

		return completed;
	}

	@SuppressWarnings("unchecked")
	public Student createStudent(Map formProperties) {

		Student student = new Student();
		setStudentProperties(student, formProperties);
		dao.saveStudent(student);
		dao
				.createWWPass(student.getStudentNo(), student.getIdno(),
						PrioStudent);
		Toolket.sendStudentInfoByQueue(student, IConstants.SYNC_DO_TYPE_INSERT);
		Toolket.sendWWPassInfoByQueue(student.getStudentNo(),
				student.getIdno(), PrioStudent, StringUtils.substring(student
						.getIdno(), 1), IConstants.SYNC_DO_TYPE_INSERT);
		if (student.getOccurTerm() != null) {
			CourseManager cm = (CourseManager) Global.context
					.getBean("courseManager");
			cm.txRegenerateAdcdByStudentTerm(student, student.getOccurTerm());
		}
		return student;
	}

	@SuppressWarnings("unchecked")
	public Graduate createGraduate(Map formProperties) {
		Graduate graduate = new Graduate();
		setGraduateProperties(graduate, formProperties);
		dao.saveGraduate(graduate);
		Toolket.sendGraduateInfoByQueue(graduate,
				IConstants.SYNC_DO_TYPE_INSERT);
		return graduate;
	}

	@SuppressWarnings("unchecked")
	public Empl createEmployee(Map formProperties) {

		// 教授,副教授,講師,助理教授,技術教師,特約教師,代課教師,輔導教師,護理教師
		// 依據code5內的Role決定
		String[] roleTeacher = { "20", "21", "22", "23", "40", "47", "48",
				"49", "50", "72", "83" };
		String[] roleDrillmaster = { "19" }; // 教官
		Empl employee = new Empl();
		setEmployeeProperties(employee, formProperties);
		dao.saveEmpl(employee);

		WwPass pass = null;
		if ((pass = dao.findWWPassByAccount(employee.getIdno())) != null) {
			dao.removeWWPass(pass);
			Toolket.sendWWPassInfoByQueue(employee.getIdno(), "", PrioEmployee,
					"", IConstants.SYNC_DO_TYPE_DELETE);
		}
		dao.createWWPass(employee.getIdno(), Toolket.printNativeDate(employee
				.getBdate(), new SimpleDateFormat("yyyyMMdd")), PrioEmployee);
		if (ArrayUtils.contains(roleTeacher, employee.getPcode())
				|| "1".equals(employee.getCategory())
				|| "2".equals(employee.getCategory()))
			addEmployeeToGroup(employee.getOid(), "T1"); // 教師群組
		else if (ArrayUtils.contains(roleDrillmaster, employee.getPcode()))
			addEmployeeToGroup(employee.getOid(), "T3"); // 教官群組
		else if ("3".equals(employee.getCategory()))
			addEmployeeToGroup(employee.getOid(), "E"); // 職員工

		Toolket.sendEmployeeInfoByQueue(employee,
				IConstants.SYNC_DO_TYPE_INSERT);
		Toolket.sendWWPassInfoByQueue(employee.getIdno(), Toolket
				.printNativeDate(employee.getBdate(), new SimpleDateFormat(
						"yyyyMMdd")), PrioEmployee, StringUtils.substring(
				employee.getIdno(), 1), IConstants.SYNC_DO_TYPE_INSERT);
		return employee;
	}

	@SuppressWarnings("unchecked")
	public void txModifyStudent(Map formProperties, Student student,
			boolean autoDeselect) {

		String oldStudentNo = student.getStudentNo();
		setStudentProperties(student, formProperties);
		String status = student.getOccurStatus();
		if (status.equals(StatusQuit)
				|| status.equals(StatusFlunkOut)
				|| (status.equals(StatusGraduate) && !""
						.equals(((String) formProperties.get("graduateNo"))
								.trim()))) {
			dao.removeWWPassByUsername(student.getStudentNo());
			Graduate graduate = moveStudentToGraduate(student);
			// 新增Gstmd,刪除stmd與wwpass
			Toolket.sendWWPassInfoByQueue(student.getStudentNo(), student
					.getPassword(), student.getPriority(), "",
					IConstants.SYNC_DO_TYPE_DELETE);
			Toolket.sendGraduateInfoByQueue(graduate,
					IConstants.SYNC_DO_TYPE_INSERT);
			Toolket.sendStudentInfoByQueue(student,
					IConstants.SYNC_DO_TYPE_DELETE);
			if (autoDeselect) {
				// Auto deselect all courses for that student
				CourseDAO cd = (CourseDAO) Global.context.getBean("courseDAO");
				CourseManager cm = (CourseManager) Global.context
						.getBean("courseManager");
				List<Seld> selds = cd.findSeldByStudentNo(oldStudentNo);
				StringBuffer seldOids = new StringBuffer();
				for (Seld seld : selds) {
					seldOids.append(seld.getOid()).append(",");
				}
				if (seldOids.length() == 0) {
					seldOids.append("0");
				} else {
					seldOids.setLength(seldOids.length() - 1);
				}
				cm.removeSelectedSeld(oldStudentNo, student.getDepartClass(),
						seldOids.toString(), false);
			}
		} else {
			if (!oldStudentNo.equalsIgnoreCase(student.getStudentNo())) {
				dao.updateWWPassUsername(oldStudentNo, student.getStudentNo());
				updateStudentNo4AllModules(oldStudentNo, student.getStudentNo());
				Toolket.sendWWPassInfoByQueue(student.getStudentNo(), student
						.getPassword(), student.getPriority(), "",
						IConstants.SYNC_DO_TYPE_UPDATE);
			}
			dao.saveStudent(student);
			Toolket.sendStudentInfoByQueue(student,
					IConstants.SYNC_DO_TYPE_UPDATE);
			if (student.getOccurTerm() != null) {
				CourseManager cm = (CourseManager) Global.context
						.getBean("courseManager");
				cm.txRegenerateAdcdByStudentTerm(student, student
						.getOccurTerm());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void txModifyGraduate(Map formProperties, Graduate graduate) {

		setGraduateProperties(graduate, formProperties);
		String status = graduate.getOccurStatus();
		if (StatusResume.equals(status) || StatusInSchool.equals(status)
				|| StatusDelayed.endsWith(status)) {
			Student student = moveGraduateToStudent(graduate);
			dao.createWWPass(graduate.getStudentNo(), graduate.getIdno(),
					PrioStudent);
			// 新增stmd與wwpass,刪除gstmd
			Toolket.sendWWPassInfoByQueue(graduate.getStudentNo(), graduate
					.getIdno(), graduate.getPriority(), StringUtils.substring(
					graduate.getIdno(), 1), IConstants.SYNC_DO_TYPE_INSERT);
			Toolket.sendStudentInfoByQueue(student,
					IConstants.SYNC_DO_TYPE_INSERT);
			Toolket.sendGraduateInfoByQueue(graduate,
					IConstants.SYNC_DO_TYPE_DELETE);
			if (student.getOccurTerm() != null) {
				CourseManager cm = (CourseManager) Global.context
						.getBean("courseManager");
				cm.txRegenerateAdcdByStudentTerm(student, student
						.getOccurTerm());
			}
		} else {
			dao.saveGraduate(graduate);
			Toolket.sendGraduateInfoByQueue(graduate,
					IConstants.SYNC_DO_TYPE_UPDATE);
		}
	}

	/*
	 * public void txModifyStudentWithAutoClear(Map formProperties, Student
	 * student) {
	 * 
	 * String oldStudentNo = student.getStudentNo();
	 * setStudentProperties(student, formProperties); String status =
	 * student.getOccurStatus(); if (status.equals(StatusQuit) ||
	 * status.equals(StatusFlunkOut) || (status.equals(StatusGraduate) &&
	 * !"".equals(((String)formProperties.get("graduateNo")).trim()))) { // Auto
	 * deselect all courses for that student CourseDAO cd =
	 * (CourseDAO)Global.context.getBean("courseDAO"); CourseManager cm =
	 * (CourseManager)Global.context.getBean("courseManager"); List<Seld> selds =
	 * cd.findSeldByStudentNo(oldStudentNo); StringBuffer seldOids = new
	 * StringBuffer(); for (Seld seld : selds) {
	 * seldOids.append(seld.getOid()).append(","); } if (seldOids.length() == 0) {
	 * seldOids.append("0"); } else { seldOids.setLength(seldOids.length()-1); }
	 * cm.txRemoveSelectedSeld(oldStudentNo, student.getDepartClass(),
	 * seldOids.toString()); dao.removeWWPassByUsername(student.getStudentNo());
	 * moveStudentToGraduate(student); } else { if
	 * (!oldStudentNo.equalsIgnoreCase(student.getStudentNo())) {
	 * dao.updateWWPassUsername(oldStudentNo, student.getStudentNo());
	 * updateStudentNo4AllModules(oldStudentNo, student.getStudentNo()); }
	 * dao.saveStudent(student); } }
	 */

	private void updateStudentNo4AllModules(String oldStudentNo,
			String newStudentNo) {
		// TODO: seld, regs, just ...
	}

	@SuppressWarnings("unchecked")
	public void txModifyEmployee(Map formProperties, Empl employee) {

		String oldIdno = employee.getIdno();
		setEmployeeProperties(employee, formProperties);
		String status = employee.getStatus();
		if (status.equals(EmpStatusQuit)) {
			dao.removeWWPassByUsername(employee.getIdno());
			deleteEmployeeAuthorities(employee);
			DEmpl dempl = moveEmplToDEmpl(employee);
			Toolket.sendWWPassInfoByQueue(employee.getIdno(), "", PrioEmployee,
					"", IConstants.SYNC_DO_TYPE_DELETE);
			Toolket.sendDEmplInfoByQueue(dempl, IConstants.SYNC_DO_TYPE_INSERT);
			Toolket.sendEmployeeInfoByQueue(employee,
					IConstants.SYNC_DO_TYPE_DELETE);
		} else {
			if (!oldIdno.equalsIgnoreCase(employee.getIdno())) {
				dao.updateWWPassUsername(oldIdno, employee.getIdno());
				// 將新Username以Password傳遞
				Toolket.sendWWPassInfoByQueue(oldIdno, employee.getIdno(),
						PrioEmployee, "", IConstants.SYNC_DO_TYPE_OTHER);
			}
			if (status.equals(EmpStatusLeaveWithSalary)
					|| status.equals(EmpStatusLeaveWithoutSalary)) {
				deleteEmployeeAuthorities(employee);
			}
			dao.saveEmpl(employee);
			Toolket.sendEmployeeInfoByQueue(employee,
					IConstants.SYNC_DO_TYPE_UPDATE);
		}
	}

	private void deleteEmployeeAuthorities(Empl emp) {
		// delete ClassInCharge
		dao.deleteClassInChargeByEmpOid(emp.getOid());

		// TODO others ...

	}

	@SuppressWarnings("unchecked")
	public void txModifyQuitEmployee(Map formProperties, DEmpl dempl) {

		setQuitEmployeeProperties(dempl, formProperties);
		String status = dempl.getStatus();
		if (!status.equals(EmpStatusQuit)) {
			Empl empl = moveDEmplToEmpl(dempl);
			dao.createWWPass(dempl.getIdno(), Toolket.printNativeDate(dempl
					.getBdate(), new SimpleDateFormat("yyyyMMdd")),
					PrioEmployee);
			Toolket.sendEmployeeInfoByQueue(empl,
					IConstants.SYNC_DO_TYPE_INSERT);
			Toolket.sendWWPassInfoByQueue(empl.getIdno(), Toolket
					.printNativeDate(empl.getBdate(), new SimpleDateFormat(
							"yyyyMMdd")), PrioEmployee, StringUtils.substring(
					empl.getIdno(), 1), IConstants.SYNC_DO_TYPE_INSERT);
			Toolket.sendDEmplInfoByQueue(dempl, IConstants.SYNC_DO_TYPE_DELETE);
		} else {
			dao.saveDEmpl(dempl);
			Toolket.sendDEmplInfoByQueue(dempl, IConstants.SYNC_DO_TYPE_UPDATE);
		}
	}

	@SuppressWarnings("unchecked")
	public void modifyEmployeePhoneAndAddress(Map formProperties,
			Employee employee) {

		employee.setEname((String) formProperties.get("ename"));
		employee.setEmail((String) formProperties.get("email"));
		employee.setTelephone((String) formProperties.get("telephone"));
		employee.setCellPhone((String) formProperties.get("cellPhone"));
		employee.setCzip((String) formProperties.get("czip"));
		employee.setCaddr((String) formProperties.get("caddr"));
		employee.setPzip((String) formProperties.get("pzip"));
		employee.setPaddr((String) formProperties.get("paddr"));

		dao.saveEmployee(employee);
	}

	private DEmpl moveEmplToDEmpl(Empl employee) {

		DEmpl quitEmployee = new DEmpl();
		try {
			BeanUtils.copyProperties(employee, quitEmployee);

		} catch (Exception e) {
			e.printStackTrace();
		}
		quitEmployee.setOid(null);
		dao.saveDEmpl(quitEmployee);
		dao.removeEmpl(employee);
		return quitEmployee;
	}

	private Empl moveDEmplToEmpl(DEmpl quitEmployee) {

		Empl employee = new Empl();
		try {
			BeanUtils.copyProperties(quitEmployee, employee);

		} catch (Exception e) {
			e.printStackTrace();
		}
		employee.setOid(null);
		dao.saveEmpl(employee);
		dao.removeDEmpl(quitEmployee);
		return employee;
	}

	@SuppressWarnings("unchecked")
	private void setStudentProperties(Student student, Map formProperties) {

		String buff;
		student.setStudentName(((String) formProperties.get("name")).trim());
		student.setStudentNo(((String) formProperties.get("studentNo")).trim());
		student.setDepartClass(((String) formProperties.get("classInCharge"))
				.trim());
		student.setSex(((String) formProperties.get("sex")).trim());
		student.setBirthday(Toolket.parseDate(((String) formProperties
				.get("birthDate")).trim()));
		student.setIdno(((String) formProperties.get("idNo")).trim());

		buff = ((String) formProperties.get("entrance")).trim();
		if (!"".equals(buff)) {
			student.setEntrance(Toolket.parseYearMonth(buff));
		} else {
			student.setEntrance(null);
		}

		buff = ((String) formProperties.get("gradYear")).trim();
		if (!"".equals(buff)) {
			student.setGradyear(Short.parseShort(buff));
		} else {
			student.setGradyear(null);
		}
		student.setIdent(((String) formProperties.get("entrIdentSel")).trim());
		student.setDivi(((String) formProperties.get("groupSel")).trim());
		student.setIdentBasic(((String) formProperties.get("basicIdentSel"))
				.trim());

		student.setCurrPost(((String) formProperties.get("commZip")).trim());
		student
				.setCurrAddr(((String) formProperties.get("commAddress"))
						.trim());

		student.setSchlName(((String) formProperties.get("gradSchool")).trim());
		student.setGradDept(((String) formProperties.get("gradDept")).trim());
		student.setGraduStatus(((String) formProperties.get("gradOrNot"))
				.trim());

		student.setParentName(((String) formProperties.get("parentName"))
				.trim());
		student.setTelephone(((String) formProperties.get("phone")).trim());

		student.setPermPost(((String) formProperties.get("permZip")).trim());
		student
				.setPermAddr(((String) formProperties.get("permAddress"))
						.trim());

		student.setOccurStatus(((String) formProperties.get("statusSel"))
				.trim());

		buff = ((String) formProperties.get("statusDate")).trim();
		if (!"".equals(buff)) {
			student.setOccurDate(Toolket.parseDate(buff));
		} else {
			student.setOccurDate(null);
		}

		buff = ((String) formProperties.get("statusYear")).trim();
		if (!"".equals(buff)) {
			student.setOccurYear(Short.parseShort(buff));
		} else {
			student.setOccurYear(null);
		}

		student
				.setOccurTerm(((String) formProperties.get("statusTerm"))
						.trim());
		student.setOccurCause(((String) formProperties.get("statusCauseSel"))
				.trim());
		student.setExtraStatus(((String) formProperties.get("extraStatus"))
				.trim());
		student.setExtraDept(((String) formProperties.get("extraDept")).trim());

		student.setOccurDocno(((String) formProperties.get("docNo")).trim());
		student.setOccurGraduateNo(((String) formProperties.get("graduateNo"))
				.trim());
		student.setEmail(((String) formProperties.get("email")).trim());
		student.setCellPhone(((String) formProperties.get("cellPhone")).trim());
		student.setIdentRemark(((String) formProperties.get("identityRemark"))
				.trim());
		student.setStudentEname(((String) formProperties.get("ename")).trim());
	}

	@SuppressWarnings("unchecked")
	private void setGraduateProperties(Graduate student, Map formProperties) {

		String buff;
		student.setStudentName(((String) formProperties.get("name")).trim());
		student.setStudentNo(((String) formProperties.get("studentNo")).trim());
		student.setDepartClass(((String) formProperties.get("classInCharge"))
				.trim());
		student.setSex(((String) formProperties.get("sex")).trim());
		student.setBirthday(Toolket.parseDate(((String) formProperties
				.get("birthDate")).trim()));
		student.setIdno(((String) formProperties.get("idNo")).trim());

		buff = ((String) formProperties.get("entrance")).trim();
		if (!"".equals(buff)) {
			student.setEntrance(Toolket.parseYearMonth(buff));
		} else {
			student.setEntrance(null);
		}

		buff = ((String) formProperties.get("gradYear")).trim();
		if (!"".equals(buff)) {
			student.setGradyear(Short.parseShort(buff));
		} else {
			student.setGradyear(null);
		}
		student.setIdent(((String) formProperties.get("entrIdentSel")).trim());
		student.setDivi(((String) formProperties.get("groupSel")).trim());
		student.setIdentBasic(((String) formProperties.get("basicIdentSel"))
				.trim());

		student.setCurrPost(((String) formProperties.get("commZip")).trim());
		student
				.setCurrAddr(((String) formProperties.get("commAddress"))
						.trim());

		student.setSchlCode(((String) formProperties.get("gradSchool")).trim());
		student.setGraduStatus(((String) formProperties.get("gradOrNot"))
				.trim());

		student.setParentName(((String) formProperties.get("parentName"))
				.trim());
		student.setTelephone(((String) formProperties.get("phone")).trim());

		student.setPermPost(((String) formProperties.get("permZip")).trim());
		student
				.setPermAddr(((String) formProperties.get("permAddress"))
						.trim());

		student.setOccurStatus(((String) formProperties.get("statusSel"))
				.trim());

		buff = ((String) formProperties.get("statusDate")).trim();
		if (!"".equals(buff)) {
			student.setOccurDate(Toolket.parseDate(buff));
		} else {
			student.setOccurDate(null);
		}

		buff = ((String) formProperties.get("statusYear")).trim();
		if (!"".equals(buff)) {
			student.setOccurYear(Short.parseShort(buff));
		} else {
			student.setOccurYear(null);
		}

		student
				.setOccurTerm(((String) formProperties.get("statusTerm"))
						.trim());
		student.setOccurCause(((String) formProperties.get("statusCauseSel"))
				.trim());
		student.setExtraStatus(((String) formProperties.get("extraStatus"))
				.trim());
		student.setExtraDept(((String) formProperties.get("extraDept")).trim());

		student.setOccurDocno(((String) formProperties.get("docNo")).trim());
		student.setOccurGraduateNo(((String) formProperties.get("graduateNo"))
				.trim());
		student.setEmail(((String) formProperties.get("email")).trim());
		student.setCellPhone(((String) formProperties.get("cellPhone")).trim());
		student.setIdentRemark(((String) formProperties.get("identityRemark"))
				.trim());
		student.setStudentEname(((String) formProperties.get("ename")).trim());
	}

	@SuppressWarnings("unchecked")
	private void setEmployeeProperties(Empl employee, Map formProperties) {

		String buff;
		employee.setCname(((String) formProperties.get("cname")).trim());
		employee.setIdno(((String) formProperties.get("idno")).trim()
				.toUpperCase());
		employee.setSex(((String) formProperties.get("sex")).trim());
		employee.setCategory(((String) formProperties.get("category")).trim());

		employee.setEname(((String) formProperties.get("ename")).trim());
		employee.setBdate(Toolket.parseDate(((String) formProperties
				.get("bdate2")).trim()));
		employee.setInsno(((String) formProperties.get("insno")).trim());

		employee.setEmail(((String) formProperties.get("email")).trim());

		employee.setCzip(((String) formProperties.get("czip")).trim());
		employee.setCaddr(((String) formProperties.get("caddr")).trim());
		employee
				.setTelephone(((String) formProperties.get("telephone")).trim());

		employee.setPzip(((String) formProperties.get("pzip")).trim());
		employee.setPaddr(((String) formProperties.get("paddr")).trim());
		employee
				.setCellPhone(((String) formProperties.get("cellPhone")).trim());

		employee.setUnit(((String) formProperties.get("unitSel")).trim());
		employee.setPcode(((String) formProperties.get("pcodeSel")).trim());
		employee.setDegree(((String) formProperties.get("degreeSel")).trim());

		employee.setTutor(((String) formProperties.get("tutorSel")).trim());

		employee.setDirector(((String) formProperties.get("directorSel"))
				.trim());
		employee.setSname(((String) formProperties.get("sname")).trim());

		employee.setStatus(((String) formProperties.get("status")).trim());
		
		employee.setRemark(((String) formProperties.get("remark")).trim());

		buff = ((String) formProperties.get("startDate2")).trim();
		if (!"".equals(buff)) {
			employee.setStartDate(Toolket.parseDate(buff));
		} else {
			employee.setStartDate(null);
		}

		buff = ((String) formProperties.get("endDate2")).trim();
		if (!"".equals(buff)) {
			employee.setEndDate(Toolket.parseDate(buff));
		} else {
			employee.setEndDate(null);
		}

		employee.setStatusCause(((String) formProperties.get("statusCauseSel"))
				.trim());

		buff = ((String) formProperties.get("teachStartDate2")).trim();
		if (!"".equals(buff)) {
			employee.setTeachStartDate(Toolket.parseDate(buff));
		} else {
			employee.setTeachStartDate(null);
		}
	}

	@SuppressWarnings("unchecked")
	private void setQuitEmployeeProperties(DEmpl employee, Map formProperties) {

		String buff;
		employee.setCname(((String) formProperties.get("cname")).trim());
		employee.setIdno(((String) formProperties.get("idno")).trim());
		employee.setSex(((String) formProperties.get("sex")).trim());
		employee.setCategory(((String) formProperties.get("category")).trim());

		employee.setEname(((String) formProperties.get("ename")).trim());
		employee.setBdate(Toolket.parseDate(((String) formProperties
				.get("bdate2")).trim()));
		employee.setInsno(((String) formProperties.get("insno")).trim());

		employee.setEmail(((String) formProperties.get("email")).trim());

		employee.setCzip(((String) formProperties.get("czip")).trim());
		employee.setCaddr(((String) formProperties.get("caddr")).trim());
		employee
				.setTelephone(((String) formProperties.get("telephone")).trim());

		employee.setPzip(((String) formProperties.get("pzip")).trim());
		employee.setPaddr(((String) formProperties.get("paddr")).trim());
		employee
				.setCellPhone(((String) formProperties.get("cellPhone")).trim());

		employee.setUnit(((String) formProperties.get("unitSel")).trim());
		employee.setPcode(((String) formProperties.get("pcodeSel")).trim());
		employee.setDegree(((String) formProperties.get("degreeSel")).trim());

		employee.setTutor(((String) formProperties.get("tutorSel")).trim());

		employee.setDirector(((String) formProperties.get("directorSel"))
				.trim());
		employee.setSname(((String) formProperties.get("sname")).trim());

		employee.setStatus(((String) formProperties.get("status")).trim());

		buff = ((String) formProperties.get("startDate2")).trim();
		if (!"".equals(buff)) {
			employee.setStartDate(Toolket.parseDate(buff));
		} else {
			employee.setStartDate(null);
		}

		buff = ((String) formProperties.get("endDate2")).trim();
		if (!"".equals(buff)) {
			employee.setEndDate(Toolket.parseDate(buff));
		} else {
			employee.setEndDate(null);
		}

		employee.setStatusCause(((String) formProperties.get("statusCauseSel"))
				.trim());

		buff = ((String) formProperties.get("teachStartDate2")).trim();
		if (!"".equals(buff)) {
			employee.setEndDate(Toolket.parseDate(buff));
		} else {
			employee.setEndDate(null);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Entrno> findEntrnoByStartEndNoDocNo(String startNo,
			String endNo, String docNo) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Entrno.class);

		if (!"".equals(startNo)) {
			criteria.add(Expression.ge("firstStno", startNo));
		}
		if (!"".equals(endNo)) {
			criteria.add(Expression.le("secondStno", endNo));
		}
		if (!"".equals(docNo)) {
			criteria.add(Expression.ilike("permissionNo", "%" + docNo + "%"));
		}
		return dao.submitQueryByCriteria(criteria);
	}

	public void deleteEntrnos(List<Entrno> entrnos) {

		for (Iterator<Entrno> noIter = entrnos.iterator(); noIter.hasNext();) {
			try {
				dao.removeEntrno(noIter.next());
				Toolket.sendEntrnoInfoByQueue(noIter.next(),
						IConstants.SYNC_DO_TYPE_DELETE);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
	}

	public Entrno createEntrno(String startNo, String endNo, String docNo) {
		try {
			Entrno entrno = new Entrno();
			entrno.setFirstStno(startNo);
			entrno.setSecondStno(endNo);
			entrno.setPermissionNo(docNo);
			dao.saveObject(entrno);
			Toolket.sendEntrnoInfoByQueue(entrno,
					IConstants.SYNC_DO_TYPE_INSERT);
			return entrno;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	public void modifyEntrno(String startNo, String endNo, String docNo,
			Entrno entrno) {
		entrno.setFirstStno(startNo);
		entrno.setSecondStno(endNo);
		entrno.setPermissionNo(docNo);
		dao.saveObject(entrno);
		Toolket.sendEntrnoInfoByQueue(entrno, IConstants.SYNC_DO_TYPE_UPDATE);
	}

	public void setupClassGroup(String classNo, String groupId) {
		log.debug("=====> classNo=" + classNo + "   groupId=" + groupId);
		dao.updateStudentDiviByClass(classNo, groupId);
		Toolket.sendDiviInfoByQueue(classNo, groupId,
				IConstants.SYNC_DO_TYPE_UPDATE);
		/*
		 * List<Student> students = dao.findStudentsByClazz(classNo); Student
		 * stu; for (Iterator<Student> stuIter = students.iterator();
		 * stuIter.hasNext();) { stu = stuIter.next(); stu.setDivi(groupId);
		 * dao.saveStudent(stu); }
		 */
	}

	private Graduate moveStudentToGraduate(Student student) {

		Graduate graduate = new Graduate();
		BeanUtils.copyProperties(student, graduate);
		graduate.setOid(null);
		dao.saveGraduate(graduate);
		dao.removeStudent(student);
		return graduate;
	}

	private Student moveGraduateToStudent(Graduate graduate) {

		Student student = new Student();
		BeanUtils.copyProperties(graduate, student);
		student.setOid(null);
		dao.saveStudent(student);
		dao.removeGraduate(graduate);
		return student;
	}

	@SuppressWarnings("unchecked")
	public void processQuitResume(Graduate graduate, Map formProperties) {

		QuitResume resume = new QuitResume();

		resume.setStudentNo(graduate.getStudentNo());
		resume.setDepartClass(graduate.getDepartClass());
		resume.setOccurYear(graduate.getOccurYear());
		resume.setOccurTerm(graduate.getOccurTerm());
		resume.setOccurDate(graduate.getOccurDate());
		resume.setOccurDocno(graduate.getOccurDocno());

		short resYear = Short.parseShort(((String) formProperties.get("year2"))
				.trim());
		String resTerm = ((String) formProperties.get("term2")).trim();
		Date resDate = Toolket.parseDate(((String) formProperties.get("date2"))
				.trim());
		String resDocNo = ((String) formProperties.get("docNo2")).trim();
		resume.setRecovYear(resYear);
		resume.setRecovTerm(resTerm);
		resume.setRecovDate(resDate);
		resume.setRecovDocno(resDocNo);
		dao.saveObject(resume);

		Student student = moveGraduateToStudent(graduate);
		student.setOccurStatus(StatusResume);
		student.setOccurYear(resYear);
		student.setOccurTerm(resTerm);
		student.setOccurDate(resDate);
		student.setOccurDocno(resDocNo);
		student.setOccurCause(null);
		dao.saveStudent(student);
		WwPass pass = null;
		if ((pass = dao.findWWPassByAccount(student.getStudentNo())) != null) {
			dao.removeWWPass(pass);
			Toolket.sendWWPassInfoByQueue(student.getStudentNo(), student
					.getIdno(), PrioStudent, StringUtils.substring(student
					.getIdno(), 1), IConstants.SYNC_DO_TYPE_DELETE);
		}
		dao
				.createWWPass(student.getStudentNo(), student.getIdno(),
						PrioStudent);
		Toolket.sendStudentInfoByQueue(student, IConstants.SYNC_DO_TYPE_INSERT);
		Toolket.sendWWPassInfoByQueue(student.getStudentNo(),
				student.getIdno(), PrioStudent, StringUtils.substring(student
						.getIdno(), 1), IConstants.SYNC_DO_TYPE_INSERT);
		Toolket.sendGraduateInfoByQueue(graduate,
				IConstants.SYNC_DO_TYPE_DELETE);
		if (student.getOccurTerm() != null) {
			CourseManager cm = (CourseManager) Global.context
					.getBean("courseManager");
			cm.txRegenerateAdcdByStudentTerm(student, student.getOccurTerm());
		}
	}

	public Employee findEmployeeByIdno(String idno) {

		return dao.findEmployeeByIdno(idno);
	}
	
	public Empl findEmplByIdno(String idno) {
		return dao.findEmplByIdno(idno);
	}

	public DEmpl findDEmplByIdno(String idno) {
		return dao.findDEmplByIdno(idno);
	}

	public List<Clazz> findAllPhysicalClasses() {

		List<Clazz> classes = dao.findClassesByType("P");

		for (Clazz clazz : classes) {
			clazz.setCampusName(Global.Campus.getProperty(clazz.getCampusNo()));
			clazz.setSchoolName(Global.School.getProperty(clazz.getSchoolNo()));
			clazz.setDeptName(Global.Dept.getProperty(clazz.getDeptNo()));
			clazz.setClassFullName(Global.ClassFullName.getProperty(clazz
					.getClassNo()));
		}
		return classes;
	}

	public List<Clazz> findAllClasses() {

		List<Clazz> classes = dao.findAllClasses();

		for (Clazz clazz : classes) {
			clazz.setCampusName(Global.Campus.getProperty(clazz.getCampusNo()));
			clazz.setSchoolName(Global.School.getProperty(clazz.getSchoolNo()));
			clazz.setDeptName(Global.Dept.getProperty(clazz.getDeptNo()));
			clazz.setClassFullName(Global.ClassFullName.getProperty(clazz
					.getClassNo()));
		}
		return classes;
	}

	public void txResetClassInCharge(Integer empOid, String classOids,
			String authorityTarget) {

		List<ClassInCharge> classInCharges = dao
				.findClassesInChargesByEmployeeModuleOids(empOid,
						authorityTarget);
		StringBuffer existedOid = new StringBuffer("|");
		String oid;
		for (ClassInCharge cic : classInCharges) {
			oid = cic.getClassOid().toString();
			if (!Toolket.isValueInCookie(oid, classOids)) {
				dao.removeClassInCharge(cic);
			} else {
				existedOid.append(oid + "|");
			}
		}
		String existedOids = existedOid.toString();
		ClassInCharge cic;
		List<Clazz> classes = findAllClasses();
		for (Clazz clazz : classes) {
			oid = clazz.getOid().toString();
			if (Toolket.isValueInCookie(oid, classOids)) {
				if (existedOids.indexOf("|" + oid + "|") == -1) {
					// NOT IN ClassInCharge YET
					cic = new ClassInCharge();
					cic.setClassNo(clazz.getClassNo());
					cic.setEmpOid(empOid);
					cic.setClassOid(clazz.getOid());
					cic.setModuleOids("|" + authorityTarget + "|");
					dao.saveObject(cic);
				}
			}
		}
	}

	public void txResetStudAffairClassInCharge(Integer empOid,
			String classOids, String authorityTarget, UserCredential operator) {

		List<ClassInCharge> classInCharges = dao
				.findClassesInChargesByEmployeeModuleOids(empOid,
						authorityTarget);
		StringBuffer existedOid = new StringBuffer("|");
		String oid;
		for (ClassInCharge cic : classInCharges) {
			oid = cic.getClassOid().toString();
			if (!Toolket.isValueInCookie(oid, classOids)) {
				dao.removeClassInCharge(cic);
			} else {
				existedOid.append(oid + "|");
			}
		}
		String existedOids = existedOid.toString();
		ClassInCharge cic;
		// List<Clazz> classes = findAllClasses();
		// List<Clazz> classes = findClassInChargeByMemberAuthority(operatorOid,
		// UserCredential.AuthorityOnStudAffair);
		Clazz[] classes = operator.getClassInChargeArySAF();
		for (Clazz clazz : classes) {
			oid = clazz.getOid().toString();
			if (Toolket.isValueInCookie(oid, classOids)) {
				if (existedOids.indexOf("|" + oid + "|") == -1) {
					// NOT IN ClassInCharge YET
					cic = new ClassInCharge();
					cic.setClassNo(clazz.getClassNo());
					cic.setEmpOid(empOid);
					cic.setClassOid(clazz.getOid());
					cic.setModuleOids("|" + authorityTarget + "|");
					dao.saveObject(cic);
				}
			}
		}

		// 自動加(退)導師權限
		if (UserCredential.AuthorityOnTutor.equals(authorityTarget)) {
			// AuthorityOnTutor=86=導師
			List<Empl> empls = findEmplByGroup(UserCredential.AuthorityOnTutor);
			boolean bFlag = false;
			for (Empl empl : empls) {
				if (empOid.equals(empl.getOid())) {
					bFlag = true;
					break;
				}
			}
			if (!bFlag
					&& !dao.findClassesInChargesByEmployeeModuleOids(empOid,
							authorityTarget).isEmpty()) // 查不到任何導師權限時
				addEmployeeToGroup(empOid, "T2"); // T2=導師
		}
	}

	public void batchInsertClassInCharge(Integer empOid, String classNo,
			String authorityTarget) {

		String classNoPattern = classNo.replace('*', '%');
		dao.deleteClassInChargeByEmpOidClassNoPattern(empOid, classNoPattern,
				authorityTarget);
		ClassInCharge cic;
		List<Clazz> classes = dao.findClassByClassNoPattern(classNoPattern);
		for (Clazz clazz : classes) {
			cic = new ClassInCharge();
			cic.setClassNo(clazz.getClassNo());
			cic.setEmpOid(empOid);
			cic.setClassOid(clazz.getOid());
			cic.setModuleOids("|" + authorityTarget + "|");
			dao.saveObject(cic);
		}
	}

	/**
	 * 實作以學生姓名關鍵字查詢學生姓名清單之Service方法
	 * 
	 * @param keyword 學生姓名關鍵字
	 * @return java.util.List List of String objects
	 */
	public List<String> findStudentNameListByKeyword(String keyword) {
		List<Student> stdList = dao.getStudentsByNameKeyword(keyword);
		List<String> nameList = null;
		if (!stdList.isEmpty()) {
			nameList = new ArrayList<String>();
			for (Student std : stdList) {
				nameList.add(std.getStudentName());
			}
		}
		return nameList;
	}

	/**
	 * Find employees by Chinese name, Id number(身份證號碼) or category submitted
	 * from EmployeeInfo.jsp
	 */
	@SuppressWarnings("unchecked")
	public List<Empl> findEmplsByEmployeeInfoForm(Map formProperties) {

		String cname = ((String) formProperties.get("cname2")).trim();
		String idNo = ((String) formProperties.get("idNo2")).trim();
		String category = (String) formProperties.get("category2");

		log.debug("=======> cname='" + cname + "'");
		log.debug("=======> category='" + category + "'");

		StringBuffer hql = new StringBuffer("from Empl e ");

		if (!"".equals(cname)) {
			hql.append("and e.cname like '%" + cname + "%' ");
		}
		if (!"".equals(idNo)) {
			hql.append("and e.idno like '%" + idNo + "%' ");
		}
		if (!"".equals(category)) {
			hql.append("and e.category = '" + category + "' ");
		}

		int start = hql.indexOf(" and ");
		if (start >= 0) {
			hql.replace(start, start + 5, " where ");
		}

		List<Empl> employees = dao.submitQuery(hql.toString());

		for (Empl employee : employees) {
			employee.setSex2(Toolket.getSex(employee.getSex()));
			employee.setCategory2(Toolket
					.getEmpCategory(employee.getCategory()));
			employee.setUnit2(Toolket.getEmpUnit(employee.getUnit()));
			employee.setPcode2(Toolket.getEmpRole(employee.getPcode()));
			employee.setStatus2(Toolket
					.getEmpStatus(employee.getStatus(), true));
		}

		return employees;
	}

	/**
	 * Find quit employees by Chinese name, Id number(身份證號碼) or category
	 * submitted from QuitEmployeeInfo.jsp
	 
	@SuppressWarnings("unchecked")
	public List<DEmpl> findDEmplsByQuitEmployeeInfoForm(Map formProperties) {

		String cname = ((String) formProperties.get("cname3")).trim();
		String idNo = ((String) formProperties.get("idNo3")).trim();
		String category = (String) formProperties.get("category3");

		log.debug("=======> cname='" + cname + "'");
		log.debug("=======> category='" + category + "'");

		StringBuffer hql = new StringBuffer("from DEmpl e ");

		if (!"".equals(cname)) {
			hql.append("and e.cname like '%" + cname + "%' ");
		}
		if (!"".equals(idNo)) {
			hql.append("and e.idno like '%" + idNo + "%' ");
		}
		if (!"".equals(category)) {
			hql.append("and e.category = '" + category + "' ");
		}

		int start = hql.indexOf(" and ");
		if (start >= 0) {
			hql.replace(start, start + 5, " where ");
		}

		List<DEmpl> quitEmployees = dao.submitQuery(hql.toString());

		for (DEmpl employee : quitEmployees) {
			employee.setSex2(Toolket.getSex(employee.getSex()));
			employee.setCategory2(Toolket
					.getEmpCategory(employee.getCategory()));
			employee.setUnit2(Toolket.getEmpUnit(employee.getUnit()));
			employee.setPcode2(Toolket.getEmpRole(employee.getPcode()));
			employee.setStatus2(Toolket
					.getEmpStatus(employee.getStatus(), true));
		}

		return quitEmployees;
	}
	*/
	
	/**
	 * 實作以班級代碼查詢班級所有學生
	 * 
	 * @param classNo 班級代碼
	 * @return java.util.List List of Student objects
	 */
	public List<Student> findStudentsByClassNo(String classNo) {
		return dao.findStudentsByClazz(classNo);
	}
	
	/**
	 * 實作以班級代碼查詢休退畢所有學生
	 * 
	 * @param classNo 班級代碼
	 * @return java.util.List List of Student objects
	 */
	public List<Graduate> findGraduatesByClassNo(String classNo) {
		return dao.findGraduatesByClazz(classNo);
	}

	public List<Code5> findAllUnits() {
		return dao.findCode5ByCategory("Unit");
	}
	
	public List<Code5> findStayTime() {
		return dao.findCode5ByCategory("StayTime");
	}
	
	public void updatStayTime(List<Code5> code5s, List<Code5> code5d){
		for (Code5 code5 : code5s) {
			dao.save(code5);
		}
		for (Code5 code5 : code5d) {
			dao.save(code5);
		}
	}
	
	public List<Code5> findStayTimeDead() {
		return dao.findCode5ByCategory("StayTimeDead");
	}

	public List<Empl> findEmplByGroup(String groupNo) {
		List<Empl> employees = dao.findEmplFromUnitBelongByUnitNo(groupNo);
		for (Empl emp : employees) {
			emp.setUnit2(Toolket.getEmpUnit(emp.getUnit()));
			emp.setPcode2(Toolket.getEmpRole(emp.getPcode()));
		}
		return employees;
	}

	/**
	 * Add a member into a group (NOT Empl.unit, BUT Code5 where category =
	 * 'Unit') Create a UnitBelong record
	 * 
	 * @param empOid Empl.oid
	 * @param unitNo Code5.idno where category = 'Unit'
	 */
	public void addEmployeeToGroup(Integer empOid, String unitNo) {
		UnitBelong u = new UnitBelong();
		u.setEmpOid(empOid);
		u.setUnitNo(unitNo);
		dao.saveObject(u);
	}

	public void removeEmployeeFromGroup(Integer empOid, String unitNo) {
		dao.deleteUnitBelongByEmpOidUnitNo(empOid, unitNo);
	}

	/**
	 * 實作更新學生聯絡電話與住址之Service方法
	 * 
	 * @commend 部分欄位不允許變更,如地址等
	 * @param formProps Form properties
	 * @param student tw.edu.chit.model.Student object
	 * @return tw.edu.chit.model.Student object
	 */
	@SuppressWarnings("unchecked")
	public Student updateStudentPhoneAndAddress(Map formProps, Student student) {
		// student.setStudentEname((String) formProps.get("ename"));
		student.setEmail((String) formProps.get("email"));
		// student.setTelephone((String) formProps.get("telephone"));
		// student.setCellPhone((String) formProps.get("cellPhone"));
		// student.setCurrPost((String) formProps.get("czip"));
		// student.setCurrAddr((String) formProps.get("caddr"));
		// student.setPermPost((String) formProps.get("pzip"));
		// student.setPermAddr((String) formProps.get("paddr"));
		dao.saveStudent(student);
		return student;
	}

	/**
	 * 實作新增/更新教師辦公室分機與留校時間資料之Service方法
	 * 
	 * @param empl tw.edu.chit.model.Empl
	 * @param formProps HTML Form properties
	 * @param stayTimes 留校資料
	 */
	@SuppressWarnings("unchecked")
	public void txUpdateTeacherStayTime(Empl empl, Map formProps,
			List<TeacherStayTime> stayTimes, String year, String term,
			boolean needRecord) {

		System.out.println();
		TeacherOfficeLocation tol = null;
		if (empl.getLocation() != null)
			tol = empl.getLocation();
		else {
			tol = new TeacherOfficeLocation();
			tol.setEmpl(empl);
		}

		tol.setRoomId((String) formProps.get("roomId"));
		tol.setRemark((String) formProps.get("remark"));
		tol.setExtension((String) formProps.get("extPhone"));
		empl.setLocation(tol);
		/*
		if (needRecord) {
			TeacherStayTimeModify modify = new TeacherStayTimeModify();
			modify.setSchoolYear(year);
			modify.setSchoolTerm(term);
			modify.setLastModified(new Date());
			modify.setEmpl(empl);
			empl.getModify().add(modify);
		}
		*/
		dao.saveEmpl(empl);
	}
	
	/**
	 * 
	 */
	public void txSaveStayTime(List<TeacherStayTime> stayTimes) {
		for (TeacherStayTime tst : stayTimes)
			dao.saveObject(tst);
	}
	
	/**
	 * 儲存生活輔導時間
	 * 
	 * @param lcs
	 */
	public void txSaveLifeCounseling(List<LifeCounseling> lcs) {
		for (LifeCounseling lc : lcs)
			dao.saveObject(lc);
	}
	
	/**
	 * 實作刪除教師辦公室分機與留校時間資料之Service方法
	 * 
	 * @param stayTimes 留校資料
	 */
	public void txDeleteTeacherStayTime(List<TeacherStayTime> stayTimes,
			String year, String term) {
		for (TeacherStayTime tst : stayTimes) {
			if (year.equalsIgnoreCase(tst.getSchoolYear())
					&& term.equalsIgnoreCase(tst.getSchoolTerm()))
				dao.removeObject(tst);
		}
	}
	
	/**
	 * 實作刪除導師生活輔導時間資料之Service方法
	 * 
	 * @param lcs 生活輔導時間
	 */
	public void txDeleteLifeCounseling(List<LifeCounseling> lcs) {
		for (LifeCounseling lc : lcs)
			dao.removeObject(lc);
	}
	
	/**
	 * 實作刪除TeacherStayTime清單之Service方法
	 * 
	 * @param empl tw.edu.chit.model.Empl object
	 */
	public void deleteStayTimeByEmpl(List<TeacherStayTime> tst) {
		dao.deleteStayTimeByEmpl(tst);
	}

	/**
	 * 實作以Oid取得Empl物件
	 * 
	 * @param oid Empl Oid
	 * @return tw.edu.chit.model.Empl object
	 */
	public Empl findEmplByOid(Integer oid) {
		return dao.getEmplByOid(oid);
	}
	
	public Empl findEmplFilterBy(Integer oid, String year) {
		return dao.getEmplFilterBy(oid, year);
	}

	public Member resetMemberPassword(String account) {

		Member member = findMemberByAccount(account);
		String password = null;
		if (member != null) {
			if (PrioEmployee.equals(member.getPriority())) {
				password = member.getBirthDate().replace("-", "");
			} else if (PrioStudent.equals(member.getPriority())) {
				password = member.getIdno();
			}
		}
		if (password != null) {
			dao.updateWWPassPassword(account, password);
			member.setPassword(password);
			Toolket.sendWWPassInfoByQueue(account, password, member
					.getPriority(), StringUtils.substring(member.getIdno(), 1),
					IConstants.SYNC_DO_TYPE_UPDATE);
		}
		return member;
	}

	public Member resetMemberInformixPassword(String account) {

		Member member = findMemberByAccount(account);
		String password = null;
		if (member != null) {
			if (PrioEmployee.equals(member.getPriority())) {
				password = member.getBirthDate().replace("-", "");
			} else if (PrioStudent.equals(member.getPriority())) {
				password = member.getIdno();
			}
		}
		if (password != null) {
			dao.updateWWPassInformixPassword(account, password);
			member.setInformixPass(password);
		}
		return member;
	}

	public void changeMemberPassword(Member member, String password) {
		dao.updateWWPassPassword(member.getAccount(), password);
		member.setPassword(password);
	}

	/**
	 * 實作以Empl Oid取得老師留校時間表之Service方法
	 * 
	 * @param oid Empl Oid
	 * @return java.util.List List of TeacherStayTime objects
	 */
	public List<TeacherStayTime> findStayTimeByEmplOid(Integer oid,
			String year, String term) {
		return dao.getStayTimeByEmplOid(oid, year, term);

	}
	
	/**
	 * 實作以Empl Oid取得導師生活輔導時間表之Service方法
	 * 
	 * @param oid
	 * @return
	 */
	public List<LifeCounseling> findLifeCounselingByEmplOid(Integer oid) {
		return dao.getLifeCounselingByEmplOid(oid);
	}

	/**
	 * 實作以學號取得學生資料之Service方法
	 * 
	 * @param no Student NO
	 * @return tw.edu.chit.model.Student object
	 */
	public Student findStudentByNo(String no) {
		return dao.findStudentByStudentNo(no);
	}

	/**
	 * 實作新增/更新導師班級幹部聯繫資料之Service方法
	 * 
	 * @param empl tw.edu.chit.model.Empl object
	 * @param classCadres List of ClassCadre objects
	 */
	public void txUpdateClassCadre(Empl empl, List<ClassCadre> classCadres) {
		empl.setClassCadre(classCadres);
		dao.saveEmpl(empl);
	}

	/**
	 * 實作以Empl取得導師班級幹部聯繫資料之Service方法
	 * 
	 * @param empl tw.edu.chit.model.Empl object
	 * @return java.util.List List of ClassCadre objects
	 */
	public List<ClassCadre> findClassCadreByEmpl(Empl empl) {
		return dao.findClassCadreByEmpl(empl);
	}

	/**
	 * 實作以Oid取得Clazz資料之Service方法
	 * 
	 * @param oid Clazz Oid
	 * @return tw.edu.chit.model.Clazz object
	 */
	public Clazz findClassByOid(Integer oid) {
		return dao.findClassByOid(oid);
	}

	/**
	 * 實作以Empl Oid與班級代碼取得ClassCadre清單之Service方法
	 * 
	 * @param empl tw.edu.chit.model.Empl object
	 * @param classNo 班級代碼
	 * @return java.util.List List of ClassCadre objects
	 */
	public List<ClassCadre> findClassCadreByClassNo(Empl empl, String classNo) {
		return dao.findClassCadreByClassNo(empl, classNo);
	}

	/**
	 * 實作以Student Oid取得Student物件之Service方法
	 * 
	 * @param oid Student Oid
	 * @return tw.edu.chit.model.Student object
	 */
	public Student findStudentByOid(Integer oid) {
		return dao.findStudentByOid(oid);
	}

	/**
	 * 依據Unit代碼取得Empl物件清單
	 * 
	 * @param unit Unit代碼
	 * @return java.util.List List of Empl objects
	 */
	public List<Empl> findTeacherByUnit(String unit) {
		return dao.getTeacherByUnit(unit);
	}

	/**
	 * 儲存Student物件
	 * 
	 * @param student tw.edu.chit.model.Student object
	 */
	public void txUpdateStudent(Student student) {
		dao.saveStudent(student);
		Toolket.sendStudentInfoByQueue(student, IConstants.SYNC_DO_TYPE_UPDATE);
	}
	
	/**
	 * 查詢Student大頭照物件
	 * 
	 * @param image
	 * @return
	 */
	public StdImage findStdImageBy(StdImage image) {
		return dao.getStdImageBy(image);
	}
	
	public List<ContractTeacher> findContractTeacherBy(
			ContractTeacher contractTeacher) throws DataAccessException {
		return dao.getContractTeacherBy(contractTeacher);
	}
	
	public List<ClassInCharge> findClassInChargeBy(ClassInCharge classInCharge)
			throws DataAccessException {
		return dao.getClassInChargeBy(classInCharge);
	}
	
	public List<DeptCode4Yun> findDeptCode4YunBy(DeptCode4Yun deptCode4Yun)
			throws DataAccessException {
		return dao.getDeptCode4YunBy(deptCode4Yun);
	}
	
	public List<Aborigine> findAborigineBy(Aborigine aborigine)
			throws DataAccessException {
		return dao.getAborigineBy(aborigine);
	}
	
	public List<CodeEmpl> findAllUnit() throws DataAccessException {
		return dao.getAllUnit();
	}
	
	public List<CodeEmpl> findCodeEmplByCategory(String category) {
		return dao.findCodeEmplByCategory(category);
	}

	/**
	 * 儲存Student大頭照物件
	 * 
	 * @param image tw.edu.chit.model.StdImage object
	 */
	public void txUpdateStudentImage(StdImage image) {
		dao.saveObject(image);
	}
	
	/**
	 * 查詢某年學生之技能檢定項目
	 * 
	 * @param year 學年
	 * @return java.util.List List of AbilityExamine Objects
	 */
	public List<AbilityExamine> findAbilityExamineBySchoolYear(String year) {
		return dao.getAbilityExamineBySchoolYear(year);
	}

	/**
	 * 以代碼取得技能檢定資料
	 * 
	 * @param no 技能檢定代碼
	 * @return AbilityExamine tw.edu.chit.model.AbilityExamine Object
	 */
	public AbilityExamine findAbilityExamineByNo(Integer no) {
		return dao.getAbilityExamineByNo(no);
	}

	/**
	 * 以學號取得學生所有技能檢定資料
	 * 
	 * @param studentNo 學號
	 * @param abilityCode 技能代碼
	 * @return java.util.List List of StdAbility Objects
	 */
	public List<StdAbility> findStudentAbilityByStudentNoAndAbilityNo(
			String studentNo, String abilityNo) {
		return dao.getStudentAbilityByStudentNoAndAbilityNo(studentNo,
				abilityNo);
	}
	
	/**
	 * 取得學生報部證照資料
	 * 
	 * @param schoolYear 學年
	 * @param schoolTerm 學期
	 * @param studentNo 學號
	 * @param skillCode 證照代碼
	 * @return
	 */
	public List<StdSkill> findStdSkillsBy(String schoolYear, String schoolTerm,
			String studentNo, String skillCode) {
		return dao.getStdSkillsBy(schoolYear, schoolTerm, studentNo, skillCode);
	}
	
	/**
	 * 
	 * @param recruitSchool
	 * @return
	 */
	public List<RecruitSchool> findRecruitSchoolsBy(RecruitSchool recruitSchool) {
		return dao.getRecruitSchoolsBy(recruitSchool);
	}

	/**
	 * 新增學生技能檢定資料
	 * 
	 * @param sas List of tw.edu.chit.model.StdAbility Object
	 */
	public void saveStudentAbility(List<StdAbility> sas) {
		for (StdAbility sa : sas)
			dao.saveObject(sa);
	}

	/**
	 * 刪除學生技能檢定資料
	 * 
	 * @param sas List of tw.edu.chit.model.StdAbility Object
	 */
	public void deleteStdAbility(List<StdAbility> sas) {
		for (StdAbility sa : sas)
			dao.removeObject(sa);
	}
	
	/**
	 * 查詢離職教職員工
	 * 
	 * @param dempl tw.edu.chit.model.DEmpl Object
	 * @return java.util.List List of tw.edu.chit.model.DEmpl Object
	 */
	public List<DEmpl> findDEmplBy(DEmpl dempl) {
		return dao.findDEmplBy(dempl);
	}
	
	public List<Rcact> findRcactsBy(Rcact rcact) throws DataAccessException {
		return dao.getRcactsBy(rcact);
	}
	
	public List<Rcproj> findRcprojsBy(Rcproj rcproj) throws DataAccessException {
		return dao.getRcprojsBy(rcproj);
	}
	
	public List<Rcjour> findRcjoursBy(Rcjour rcjour) throws DataAccessException {
		return dao.getRcjoursBy(rcjour);
	}
	
	public List<Rcconf> findRcconfsBy(Rcconf rcconf) throws DataAccessException {
		return dao.getRcconfsBy(rcconf);
	}
	
	public List<Rcbook> findRcbooksBy(Rcbook rcbook) throws DataAccessException {
		return dao.getRcbooksBy(rcbook);
	}
	
	public List<Rcpet> findRcpetsBy(Rcpet rcpet) throws DataAccessException {
		return dao.getRcpetsBy(rcpet);
	}
	
	public List<Rchono> findRchonosBy(Rchono rchono) throws DataAccessException {
		return dao.getRchonosBy(rchono);
	}
	
	/**
	 * 儲存應屆畢業生出路與通訊資料
	 * 
	 * @param investigation
	 */
	public void txUpdateInvestigation(Investigation investigation) {
		dao.saveObject(investigation);
	}
	
	/**
	 * 儲存應屆畢業生出路與通訊資料
	 * 
	 * @param investigation
	 */
	public void txUpdateInvestigationG(InvestigationG investigationG) {
		dao.saveObject(investigationG);
	}
	
	/**
	 * 刪除應屆畢業生出路與通訊資料
	 * 
	 * @param investigation
	 */
	public void txDeleteInvestigation(Investigation investigation) {
		dao.removeObject(investigation);
	}
	
	/**
	 * 刪除應屆畢業生出路與通訊資料
	 * 
	 * @param investigation
	 */
	public void txDeleteInvestigationG(InvestigationG investigationG) {
		dao.removeObject(investigationG);
	}
	
	/**
	 * 儲存新生學籍卡資料
	 * 
	 * @param registrationCard
	 */
	public void txUpdateRegistrationCard(Student student) {
		dao.saveStudent(student);
		Toolket.sendStudentInfoByQueue(student, IConstants.SYNC_DO_TYPE_UPDATE);
		dao.saveObject(student.getRegistrationCard());
	}
	
	/**
	 * 刪除新生學籍卡資料
	 * 
	 * @param registrationCard
	 */
	public void txDeleteRegistrationCard(RegistrationCard registrationCard) {
		dao.removeObject(registrationCard);
	}
	
	/**
	 * 查詢在校生清單資料
	 * 
	 * @param student tw.edu.chit.model.Student
	 * @return java.util.List List of Student Objects
	 */
	public List<Student> findStudentsBy(Student student) {
		return dao.getStudentsBy(student);
	}

	/**
	 * 查詢離校校生清單資料
	 * 
	 * @param student tw.edu.chit.model.Graduate
	 * @return java.util.List List of Graduate Objects
	 */
	public List<Graduate> findGraduatesBy(Graduate graduate) {
		return dao.getGraduatesBy(graduate);
	}
	
	/**
	 * 更新新生學籍卡
	 * 
	 * @param student
	 * @param rc
	 */
	public void updateRegistrationCard(Student student, RegistrationCard rc) {
		student.setRegistrationCard(rc);
		//rc.setStudent(student);
		dao.save(student);
	}
	
	/**
	 * 卡片登入
	 * @param account
	 * @return
	 * @throws 
	 */
	public UserCredential createUserCredential(String account){
		WwPass user = dao.findWWPassByAccountPassword(account);
		if(user==null){
			//離校學生
			if(ezGetInt("SELECT COUNT(*)FROM Gstmd WHERE student_no='"+account+"'")>0){
				user=new WwPass();
				//user.setPassword(password);
				user.setPriority("G");
				user.setUsername(account);
			}
		}		


		
		if (PrioEmployee.equals(user.getPriority())) { // An employee
			Member emp = findMemberByAccount(account);
			
			Clazz[] classAry = dao.findClassesInChargeByMemberModuleOids(emp.getOid(), UserCredential.AuthorityOnTeachAffair).toArray(new Clazz[0]);
			List<Code5> campuses = new ArrayList<Code5>();
			List<Code5[]> schools = new ArrayList<Code5[]>();
			List<Code5[][]> depts = new ArrayList<Code5[][]>();
			List<Clazz[][][]> classes = new ArrayList<Clazz[][][]>();

			createClassInChargeDataStructure(classAry, campuses, schools, depts, classes);
			Clazz[] classArySAF = dao.findClassesInChargeByMemberModuleOids(emp.getOid(),
							UserCredential.AuthorityOnStudAffair).toArray(
							new Clazz[0]);

			List<Code5> campusesSAF = new ArrayList<Code5>();
			List<Code5[]> schoolsSAF = new ArrayList<Code5[]>();
			List<Code5[][]> deptsSAF = new ArrayList<Code5[][]>();
			List<Clazz[][][]> classesSAF = new ArrayList<Clazz[][][]>();

			createClassInChargeDataStructure(classArySAF, campusesSAF, schoolsSAF, deptsSAF, classesSAF);

			//
			// Construct class-incharge for this employee on Chairman(系主任)
			//
			Clazz[] classAryC = dao.findClassesInChargeByMemberModuleOids(
					emp.getOid(), UserCredential.AuthorityOnChairman).toArray(new Clazz[0]);

			List<Code5> campusesC = new ArrayList<Code5>();
			List<Code5[]> schoolsC = new ArrayList<Code5[]>();
			List<Code5[][]> deptsC = new ArrayList<Code5[][]>();
			List<Clazz[][][]> classesC = new ArrayList<Clazz[][][]>();

			createClassInChargeDataStructure(classAryC, campusesC,
					schoolsC, deptsC, classesC);

			//
			// Construct class-incharge for this employee on Drillmaster(教官)
			//
			Clazz[] classAryM = dao.findClassesInChargeByMemberModuleOids(
					emp.getOid(), UserCredential.AuthorityOnDrillmaster).toArray(new Clazz[0]);

			List<Code5> campusesM = new ArrayList<Code5>();
			List<Code5[]> schoolsM = new ArrayList<Code5[]>();
			List<Code5[][]> deptsM = new ArrayList<Code5[][]>();
			List<Clazz[][][]> classesM = new ArrayList<Clazz[][][]>();

			createClassInChargeDataStructure(classAryM, campusesM, schoolsM, deptsM, classesM);

			//
			// Construct class-incharge for this employee on Tutor(導師)
			//
			Clazz[] classAryT = dao.findClassesInChargeByMemberModuleOids(
					emp.getOid(), UserCredential.AuthorityOnTutor).toArray(new Clazz[0]);

			List<Code5> campusesT = new ArrayList<Code5>();
			List<Code5[]> schoolsT = new ArrayList<Code5[]>();
			List<Code5[][]> deptsT = new ArrayList<Code5[][]>();
			List<Clazz[][][]> classesT = new ArrayList<Clazz[][][]>();

			createClassInChargeDataStructure(classAryT, campusesT, schoolsT, deptsT, classesT);

			return new UserCredential(emp, classAry, campuses
					.toArray(new Code5[0]), schools
					.toArray(new Code5[0][0]), depts
					.toArray(new Code5[0][0][0]), classes
					.toArray(new Clazz[0][0][0][0]), classArySAF,
					campusesSAF.toArray(new Code5[0]), schoolsSAF
							.toArray(new Code5[0][0]), deptsSAF
							.toArray(new Code5[0][0][0]), classesSAF
							.toArray(new Clazz[0][0][0][0]), classAryC,
					campusesC.toArray(new Code5[0]), schoolsC
							.toArray(new Code5[0][0]), deptsC
							.toArray(new Code5[0][0][0]), classesC
							.toArray(new Clazz[0][0][0][0]), classAryM,
					campusesM.toArray(new Code5[0]), schoolsM
							.toArray(new Code5[0][0]), deptsM
							.toArray(new Code5[0][0][0]), classesM
							.toArray(new Clazz[0][0][0][0]), classAryT,
					campusesT.toArray(new Code5[0]), schoolsT
							.toArray(new Code5[0][0]), deptsT
							.toArray(new Code5[0][0][0]), classesT
							.toArray(new Clazz[0][0][0][0]));
		//在校學生
		} else if (PrioStudent.equals(user.getPriority())) {
			
			Student stud = dao.findStudentByStudentNo(account);
			
			stud.setDepartClass2(Toolket.getClassFullName(stud.getDepartClass()));
			stud.setPassword(user.getPassword());
			stud.setPriority(user.getPriority());
			stud.setAccount(user.getUsername());
			stud.setInformixPass(user.getInformixPass());
			stud.setName(stud.getStudentName());
			return new UserCredential(stud, dao.findClassByClassNo(stud.getDepartClass()));
		//離校學生
		}else if(PrioGstudent.equals(user.getPriority())){
			Graduate gstmd=dao.findGstudentByStudentNo(account);
			//gstmd.setDepartClass2(Toolket.getClassFullName(stud.getDepartClass()));
			gstmd.setPassword(user.getPassword());
			gstmd.setPriority(user.getPriority());
			gstmd.setAccount(user.getUsername());
			//gstmd.setInformixPass(user.getInformixPass());
			gstmd.setName(gstmd.getStudentName());				
			
			return new UserCredential(gstmd, dao.findClassByClassNo(gstmd.getDepartClass()));
			
		}
	

		

		
		
		
		
		
		
		
		
		
		
		
		
		return null;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Empl> findEmplByNameLike(String sname){
		String hql = "From Empl Where cname like '" + sname +"%'";
		List<Empl> emplList = dao.submitQuery(hql);
		return emplList;
	}
	
	@SuppressWarnings("unchecked")
	public Empl findEmplByName(String sname){
		Empl empl = null;
		String hql = "From Empl Where cname='" + sname +"'";
		List<Empl> emplList = dao.submitQuery(hql);
		if(!emplList.isEmpty()){
			empl = emplList.get(0); 
		}
		return empl;
	}
	
	/**
	 * 
	 * @param term
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public Criterion findAllTeacherCriterion(String term)
			throws DataAccessException {

		String hql = "SELECT d.techid FROM Dtime d WHERE d.sterm = ? "
				+ "AND d.techid != '' AND d.cscode NOT IN ('50000', 'T0001', 'T0002') "
				+ "GROUP BY d.techid";
		List data = dao.query(hql, new Object[] { term });
		hql = "SELECT dt.teachId FROM Dtime d, DtimeTeacher dt "
				+ "WHERE d.sterm = ? AND d.oid = dt.dtimeOid "
				+ "GROUP BY dt.teachId";
		data.addAll(dao.query(hql, new Object[] { term }));
		List<String> ret = new LinkedList<String>();
		for (Object o : data)
			ret.add((String) o);

		return Restrictions.in("idno", ret);
	}
	
	/**
	 * 
	 * @param term
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<Empl> findAllTeacher(String term) throws DataAccessException {
		return (List<Empl>) dao.getSQLWithCriteria(Empl.class,
				findAllTeacherCriterion(term));
	}
	
	/**
	 * 
	 * @param empl
	 * @return
	 * @throws DataAccessException
	 */
	public List<Empl> findEmplsBy(Empl empl) throws DataAccessException {
		return dao.getEmplsBy(empl);
	}
	
	/**
	 * 
	 * @param workShifts
	 * @return
	 */
	public List<Empl> findEmplByWorkShift(String[] workShifts)
			throws DataAccessException {
		return dao.getEmplByWorkShift(workShifts);
	}
	
	/**
	 * 
	 * 
	 * @param regs
	 * @param tempStmds
	 * @throws DataAccessException
	 */
	public void txSaveRegister(List<Register> regs, List<TempStmd> tempStmds)
			throws DataAccessException {

		Date now = new Date();
		for (Register reg : regs) {
			reg.setLastModified(now);
			dao.save(reg);
		}

		for (TempStmd temp : tempStmds)
			dao.save(temp);
	}
	
	/**
	 * 取得現任教職員工所屬單位名稱
	 * @param idno 身分證號
	 * @return 單位名稱或空白字串
	 */
	public String findEmplUnitByIdno(String idno){
		String unitName = "";
		String hql = "Select c From CodeEmpl c, Empl e Where e.idno=? And e.unit=c.idno And c.category in ('Unit','UnitTeach')";
		List<CodeEmpl> units = dao.query(hql, new Object[]{idno});
		if(!units.isEmpty()){
			unitName = units.get(0).getName();
		}
		return unitName;
	}
	
	public ActionMessages addNewAssessPaper(String idno, String[] codes){
		ActionMessages msg = new ActionMessages();
		Calendar now = Calendar.getInstance();
		Date date = now.getTime();
		for(int i=0; i<codes.length; i++){
			try{
				AssessPaper paper = new AssessPaper();
				paper.setIdno(idno);
				paper.setServiceNo(codes[i]);
				paper.setPrintDate(date);
				dao.saveObject(paper);
			}catch(Exception e){
				msg.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","滿意度調查列印資料新增失敗：" + e.toString()));
				return msg;
			}
		}
		return msg;
	}
	
	public AssessPaper findAssessPaperByNo(String serviceNo){
		AssessPaper paper = null;
		String hql ="From AssessPaper Where serviceNo='" + serviceNo + "'";
		List<AssessPaper> papers = dao.submitQuery(hql);

		if(!papers.isEmpty()){
			paper = papers.get(0);
			String idno = paper.getIdno();
			Empl att = this.findEmplByIdno(idno);
			String attCname = att.getCname();
			String attUnitName = Toolket.getUnitName(att.getUnit());
			Object repoter = null;
			paper.setAttCname(attCname);
			paper.setAttUnitName(attUnitName);
			
			paper.setReporterCname("");
			paper.setReporterUnitName("");
			repoter = this.findStudentByNo(paper.getReporter());
			if(repoter != null){
				Student student = (Student)repoter;
				paper.setReporterCname(student.getStudentName());
				paper.setReporterUnitName(Toolket.getClassFullName(student.getDepartClass()));
				paper.setReporterKind("學生");
			}else{
				repoter = this.findGraduateByStudentNo(paper.getReporter());
				if(repoter != null){
					Graduate student = (Graduate)repoter;
					paper.setReporterCname(student.getStudentName());
					paper.setReporterUnitName(Toolket.getClassFullName(student.getDepartClass()));
					paper.setReporterKind("學生");
				}else{
					repoter = this.findEmplByIdno(paper.getReporter());
					if(repoter != null){
						Empl empl = (Empl)repoter;
						paper.setReporterCname(empl.getCname());
						paper.setReporterUnitName(Toolket.getUnitName(empl.getUnit()));
						paper.setReporterKind("教職員");
					}else{
						repoter = this.findDEmplByIdno(paper.getReporter());
						if(repoter != null){
							DEmpl empl = (DEmpl)repoter;
							paper.setReporterCname(empl.getCname());
							paper.setReporterUnitName(Toolket.getUnitName(empl.getUnit()));
							paper.setReporterKind("教職員");
						}
					}
				}
			}
		}
		
		return paper;
	}
	
	public ActionMessages saveAssessPaperReply(UserCredential user, AssessPaper paper, DynaActionForm form){
		ActionMessages msgs = new ActionMessages();

		String score = form.getString("score").trim();
		String suggestion = form.getString("suggestion").trim();
		String srvTime = form.getString("srvTime").trim();
		String srvEvent = form.getString("srvEvent").trim();
		String description = form.getString("description").trim();
		String sdate = form.getString("sdate").trim();
		Date serviceDate = Toolket.parseFullDate(sdate);
		
		String id = "";
		
		if(user.getStudent() != null){
			id = user.getStudent().getStudentNo();
		}else{
			id = user.getMember().getIdno();
		}
		
		try{
			paper.setReporter(id);
			paper.setServiceDate(serviceDate);
			paper.setScore(Short.parseShort(score));
			paper.setSuggestion(suggestion);
			paper.setSrvTime(srvTime);
			paper.setSrvEvent(srvEvent);
			paper.setDescription(description);
			dao.saveObject(paper);
		}catch (Exception e){
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"MessageN1","滿意度調查資料更 增失敗：" + e.toString()));
		}
		return msgs;
	}
	
	public Map getAssessPaperByIdno(String idno){
		Map paperMap = new HashMap();
		String hql ="From AssessPaper Where idno='" + idno + "' order by printDate DESC";
		List<AssessPaper> papers = dao.submitQuery(hql);
		
		Empl att = this.findEmplByIdno(idno);
		String attCname = att.getCname();
		String attUnitName = Toolket.getUnitName(att.getUnit());
		Object repoter = null;
		
		int total = 0;
		float avgScore = 0.0f;
		
		for(AssessPaper paper:papers){
			if(paper.getScore() != null){
				total++;
				avgScore += paper.getScore();
			}
			paper.setAttCname(attCname);
			paper.setAttUnitName(attUnitName);
			
			paper.setReporterCname("");
			paper.setReporterUnitName("");
			repoter = this.findStudentByNo(paper.getReporter());
			if(repoter != null){
				Student student = (Student)repoter;
				paper.setReporterCname(student.getStudentName());
				paper.setReporterUnitName(Toolket.getClassFullName(student.getDepartClass()));
				paper.setReporterKind("學生");
			}else{
				repoter = this.findGraduateByStudentNo(paper.getReporter());
				if(repoter != null){
					Graduate student = (Graduate)repoter;
					paper.setReporterCname(student.getStudentName());
					paper.setReporterUnitName(Toolket.getClassFullName(student.getDepartClass()));
					paper.setReporterKind("學生");
				}else{
					repoter = this.findEmplByIdno(paper.getReporter());
					if(repoter != null){
						Empl empl = (Empl)repoter;
						paper.setReporterCname(empl.getCname());
						paper.setReporterUnitName(Toolket.getUnitName(empl.getUnit()));
						paper.setReporterKind("教職員");
					}else{
						repoter = this.findDEmplByIdno(paper.getReporter());
						if(repoter != null){
							DEmpl empl = (DEmpl)repoter;
							paper.setReporterCname(empl.getCname());
							paper.setReporterUnitName(Toolket.getUnitName(empl.getUnit()));
							paper.setReporterKind("教職員");
						}
					}
				}
			}
		}

		if(total > 0){
			avgScore = avgScore/total;
		}
		DecimalFormat df = new DecimalFormat(",###.0");

		paperMap.put("papers", papers);
		paperMap.put("total", total);
		paperMap.put("avgScore", df.format(avgScore));
		
		return paperMap;
		
	}
	
	public List<CodeEmpl> getEmplUnits(){
		String hql = "Select c From Empl e, CodeEmpl c Where e.unit=c.idno And c.category like 'Unit%' group by e.unit order by c.sequence";
		List<CodeEmpl> units = dao.submitQuery(hql);
		return units;
	}
	
	public List getAssessPaperReportbyForm(DynaActionForm form){
		String reportType = form.getString("reportType").trim();	//1:統計， 2:優劣
		String unit = form.getString("unit").trim();
		String sdate = form.getString("sdate").trim();
		String edate = form.getString("edate").trim();

		//used for totalScore compare
		class scoreComp implements Comparator {
			public int compare(Object obj1, Object obj2){
				if(obj1 instanceof AssessPaper && obj2 instanceof AssessPaper){
					short score1 = ((AssessPaper) obj1).getScore();
					short score2 = ((AssessPaper) obj1).getScore();
					
					if(score1<score2)	return 1;
					else if(score1==score2) return 0;
					else return -1;
				}
				return 0;
			}
			
			public boolean equals(Object obj){
				return super.equals(obj);
			}
		}

		//e.director = '26':組長 , '103':院秘書      e.category='3':職員工
		String hql = "SELECT e FROM Empl e, CodeEmpl c Where e.unit=c.idno " +
				"And (c.category='Unit' or (c.category='UnitTeach' And e.director in ('26', '103') ) " + 
				"or (c.category='UnitTeach' And e.category='3')) " +
				"And not ((e.sname like '%主任%' And e.idno not in ('F220393782','F224523866')) " +
				" Or e.sname like '%校長%' Or e.sname like '%總務長%'" +
				" Or e.sname like '%學務長%' Or e.sname like '%教務長%' Or e.sname like '%董事長%'" +
				" Or e.sname like '%工友%') ";
		if(!unit.equals("0")){
			hql += " And e.unit='" + unit + "' order by e.oid";
		}else{
			hql += " order by c.sequence, e.oid";
		}
		
		List<Empl> empls = dao.submitQuery(hql);
		
		List<AssessPaper> papers = new ArrayList<AssessPaper>();
		List<AssessPaper> tmps = new ArrayList<AssessPaper>();
		List rets = new ArrayList();
		float utotal = 0f;
		int replied = 0;
		String preUnit = "";
		float avg = 0f;
		Object repoter = null;

		DecimalFormat df = new DecimalFormat(",###.0");
		
		if(!empls.isEmpty()) preUnit = empls.get(0).getUnit();
		Empl emp = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		for(Empl empl:empls){
			utotal = 0f;
			replied = 0;
			int[] scrs = {0,0,0,0,0};
			int[] scrRepli = {0,0,0,0,0};	//記錄教職員、學生、不明人士回覆的人次
			if(reportType.equals("1")){
				hql = "From AssessPaper Where serviceDate between '" + sdate + "' And '" + edate + 
				"' And idno='" + empl.getIdno() + "' order by score";
				papers = dao.submitQuery(hql);
				if(!papers.isEmpty()){
					for(AssessPaper paper:papers){
						if(paper.getScore()!=null){
							scrs[paper.getScore()-1]++;
							
							repoter = this.findStudentByNo(paper.getReporter());
							if(repoter != null){
								//學生: nothing todo
							}else{
								repoter = this.findGraduateByStudentNo(paper.getReporter());
								if(repoter != null){
									//學生: nothing todo
								}else{
									repoter = this.findEmplByIdno(paper.getReporter());
									if(repoter != null){
										scrRepli[paper.getScore()-1]++;
									}else{
										repoter = this.findDEmplByIdno(paper.getReporter());
										if(repoter != null){
											scrRepli[paper.getScore()-1]++;
										}else{
											//查不出身分
											//scrRepli[paper.getScore()-1]++;
										}
									}
								}
							}

							
							utotal += paper.getScore(); 
							replied++;
						}
					}
				}
				Map upaper = new HashMap();
				upaper.put("cname", empl.getCname());
				upaper.put("unitName", Toolket.getUnitName(empl.getUnit()));
				upaper.put("replied", papers.size());
				if(replied>0){
					upaper.put("total", "" + Math.round(utotal));
					upaper.put("average", df.format(utotal/replied));
				}else if(replied==0){
					upaper.put("total", "");
					upaper.put("average", "");
				}
				upaper.put("scores", scrs);
				upaper.put("scrRepli", scrRepli);
				rets.add(upaper);
			}else{
				hql = "From AssessPaper Where serviceDate between '" + sdate + "' And '" + edate + 
						"' And idno='" + empl.getIdno() + "' " +
						" And score in (5,2,1) order by score desc, serviceDate";
				papers = dao.submitQuery(hql);
				if(!papers.isEmpty()){
					if(empl.getUnit().equalsIgnoreCase(preUnit)){
						tmps.addAll(papers);
					}else{
						//同一單位的分數排序
						Collections.sort(tmps, new scoreComp());
						for(AssessPaper paper:tmps){
							emp = this.findEmplByIdno(paper.getIdno());
							paper.setAttCname(emp.getCname());
							paper.setAttUnitName(Toolket.getUnitName(emp.getUnit()));
							paper.setSimpleDate(sdf.format(paper.getServiceDate()));
							
							repoter = this.findStudentByNo(paper.getReporter());
							if(repoter != null){
								paper.setReporterKind("學生");
							}else{
								repoter = this.findGraduateByStudentNo(paper.getReporter());
								if(repoter != null){
									paper.setReporterKind("學生");
								}else{
									repoter = this.findEmplByIdno(paper.getReporter());
									if(repoter != null){
										paper.setReporterKind("教職員");
									}else{
										repoter = this.findDEmplByIdno(paper.getReporter());
										if(repoter != null){
											paper.setReporterKind("教職員");
										}else{
											paper.setReporterKind("");
										}
									}
								}
							}
							rets.add(paper);
						}
						
						preUnit=empl.getUnit();
						tmps.clear();
						tmps.addAll(papers);
						
					}
				}
			}	//end of reportType

		}	//end of empls loop
		
		//加入最後一個單位的資料
		if(reportType.equals("2")){
			Collections.sort(tmps, new scoreComp());
			for(AssessPaper paper:tmps){
				emp = this.findEmplByIdno(paper.getIdno());
				paper.setAttCname(emp.getCname());
				paper.setAttUnitName(Toolket.getUnitName(emp.getUnit()));
				paper.setSimpleDate(sdf.format(paper.getServiceDate()));
				
				repoter = this.findStudentByNo(paper.getReporter());
				if(repoter != null){
					paper.setReporterKind("學生");
				}else{
					repoter = this.findGraduateByStudentNo(paper.getReporter());
					if(repoter != null){
						paper.setReporterKind("學生");
					}else{
						repoter = this.findEmplByIdno(paper.getReporter());
						if(repoter != null){
							paper.setReporterKind("教職員");
						}else{
							repoter = this.findDEmplByIdno(paper.getReporter());
							if(repoter != null){
								paper.setReporterKind("教職員");
							}else{
								paper.setReporterKind("");
							}
						}
					}
				}
				rets.add(paper);
			}
		}
		
		return rets;
	}
	
	
}