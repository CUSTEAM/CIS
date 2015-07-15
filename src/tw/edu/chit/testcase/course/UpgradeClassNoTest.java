package tw.edu.chit.testcase.course;

import junit.framework.TestCase;
import tw.edu.chit.util.Toolket;

public class UpgradeClassNoTest extends TestCase {
	
	public void testDownGrade() {
		assertEquals("164E01", Toolket.processClassNoDown("164E11"));
		assertEquals("112101", Toolket.processClassNoDown("112111"));
		assertEquals("112102", Toolket.processClassNoDown("112112"));
		assertEquals("112111", Toolket.processClassNoDown("112121"));
		assertEquals("112112", Toolket.processClassNoDown("112122"));
		assertEquals("112121", Toolket.processClassNoDown("112131"));
		assertEquals("112A11", Toolket.processClassNoDown("112A21"));
		assertEquals("115131", Toolket.processClassNoDown("115141"));
	}
	
	public void testUpGrade() {
		assertEquals("164E21", Toolket.processClassNoUp("164E11"));
		assertEquals("112121", Toolket.processClassNoUp("112111"));
		assertEquals("112122", Toolket.processClassNoUp("112112"));
		assertEquals("112131", Toolket.processClassNoUp("112121"));
		assertEquals("112132", Toolket.processClassNoUp("112122"));
		assertEquals("112141", Toolket.processClassNoUp("112131"));
		assertEquals("112A31", Toolket.processClassNoUp("112A21"));
		assertEquals("115151", Toolket.processClassNoUp("115141"));
	}

}
