package tw.edu.chit.testcase.score;

import java.text.DecimalFormat;

import junit.framework.TestCase;

public class ScoreRoundFormatTest extends TestCase {

	/**
	 * 測試小數點第2位進位
	 */
	public void testScoreFormat() {
		DecimalFormat df = new DecimalFormat("##0.0");
		assertEquals("74.5", df.format(74.451f));
		assertEquals("76.3", df.format(76.251f));
		assertEquals("60.1", df.format(60.051f));
		assertEquals("74.4", df.format(74.401f));
		assertEquals("76.2", df.format(76.201f));
		assertEquals("60.0", df.format(60.001f));
		assertEquals("75.0", df.format(74.951f));
	}

}
