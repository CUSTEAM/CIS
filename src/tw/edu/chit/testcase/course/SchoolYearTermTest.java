package tw.edu.chit.testcase.course;

import junit.framework.TestCase;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Toolket;

public class SchoolYearTermTest extends TestCase {

	public void testSchoolYearTerm() {
		assertEquals("97", Toolket.getNextYearTerm().get(
				IConstants.PARAMETER_SCHOOL_YEAR).toString());
		assertEquals("1", Toolket.getNextYearTerm().get(
				IConstants.PARAMETER_SCHOOL_TERM).toString());
	}

	protected void setUp() throws Exception {
		// 必須initial ApplicationContext才可以Test
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
