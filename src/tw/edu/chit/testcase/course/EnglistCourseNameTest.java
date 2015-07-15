package tw.edu.chit.testcase.course;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import tw.edu.chit.service.CourseManager;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class EnglistCourseNameTest extends TestCase {

	ApplicationContext ac = new FileSystemXmlApplicationContext(
			"D:/workspaces/CIS/WebRoot/WEB-INF/applicationContext.xml");

	public void testEnglishCourseName() {
		assertEquals("", Toolket.processEnglishCourseName(""));
		assertEquals("Oscar Wei", Toolket.processEnglishCourseName("oscar wei"));
		assertEquals("Oscar Wei", Toolket
				.processEnglishCourseName("oscar  wei"));
		assertEquals("Oscar 0 Wei", Toolket
				.processEnglishCourseName("oscar 0  wei"));
		assertEquals("A Oscar 0 Wei", Toolket
				.processEnglishCourseName("a oscar 0  wei"));
		assertEquals("Recent History of China", Toolket
				.processEnglishCourseName("Recent history of china"));
		assertFalse("Doctrine of Dr. Sun Yat-Sen".equals(Toolket
				.processEnglishCourseName("doctrine of dr. sun yat-sen")));
		assertEquals("Theory & Policy of International Trade", Toolket
				.processEnglishCourseName("theory & policy of "
						+ "international trade"));
		assertEquals("Engineering Machanics (I)", Toolket
				.processEnglishCourseName("engineering machanics (I)"));
	}

	public void testUpperEnglishCourseName() {
		assertEquals("FORTRAN Language Programming", Toolket
				.processEnglishCourseName("FORTRAN language programming"));
		assertEquals("AUTO CAD", Toolket.processEnglishCourseName("AUTO CAD"));
		assertEquals("ISO 9000", Toolket.processEnglishCourseName("ISO 9000"));
		assertEquals("F.M.S.", Toolket.processEnglishCourseName("F.M.S."));
		assertEquals("FPGA/CPLD", Toolket.processEnglishCourseName("FPGA/CPLD"));
		assertEquals("Constitution of R.O.C.", Toolket
				.processEnglishCourseName("constitution of R.O.C."));
		assertEquals("Applications of C.I.M Through Networks", Toolket
				.processEnglishCourseName("applications of "
						+ "C.I.M through networks"));
		assertEquals("I/O Interface Technique and Practice", Toolket
				.processEnglishCourseName("I/O interface "
						+ "technique and practice"));
	}

	public void testFirstStopWordEnglishCourseName() {
		assertEquals("The Electrical Engineering of Railway?", Toolket
				.processEnglishCourseName("the electrical "
						+ "engineering of railway?"));
	}

	public void testIncludeChineseOfEnglishCourseName() {
		assertEquals("Building Codes (一)(二)", Toolket
				.processEnglishCourseName("building codes (一)(二)"));
	}

	public void testSpringConfiguration() {
		assertNotNull(ac);
	}

	public void testSpringBeanDefinition() {
		CourseManager cm = (CourseManager) ac
				.getBean(IConstants.COURSE_MANAGER_BEAN_NAME);
		assertNotNull(cm);
	}

	protected void setUp() throws Exception {
		super.setUp();
		// D:\eclipse\workspace\CIS\WebRoot\WEB-INF
		// ac = new
		// ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
		// ac = new FileSystemXmlApplicationContext(
		// "D:/eclipse/workspace/CIS/WebRoot/WEB-INF/applicationContext.xml");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		ac = null;
	}

}
