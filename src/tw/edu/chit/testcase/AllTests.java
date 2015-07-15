package tw.edu.chit.testcase;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for CIS Course Modules");
        suite.addTestSuite(tw.edu.chit.testcase.course.EnglistCourseNameTest.class);
        suite.addTestSuite(tw.edu.chit.testcase.course.UpgradeClassNoTest.class);
        // suite.addTestSuite(tw.edu.chit.testcase.course.SchoolYearTermTest.class);
        suite.addTestSuite(tw.edu.chit.testcase.score.ScoreRoundFormatTest.class);
        return suite;
	}

}
