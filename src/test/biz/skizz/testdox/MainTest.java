package biz.skizz.testdox;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import junit.framework.TestCase;

public class MainTest extends TestCase {
    private MockDocumentGenerator gen;
    private Main main;

    protected void setUp() throws Exception {

        gen = new MockDocumentGenerator();

        main = new Main();
        main.setTestDirectory("src/test");
        main.addDocumentGenerator(gen);
    }

    public void testMainParsesThisTest() {

        main.parse();

        List descriptions = gen.getTestDescriptions("Main");
        assertNotNull(descriptions);
        assertTrue(descriptions.contains("Main parses this test"));

    }

    public void testMainHandlesMultipleDocumentGenerators() {

        MockDocumentGenerator gen1 = new MockDocumentGenerator();
        MockDocumentGenerator gen2 = new MockDocumentGenerator();

        main.addDocumentGenerator(gen1);
        main.addDocumentGenerator(gen2);

        main.parse();

        assertNotNull(gen1.getTestDescriptions("Main"));
        assertNotNull(gen2.getTestDescriptions("Main"));

    }

    public void testIncludesCommonTestFileNamePatterns() {
		assertTrue(main.isTestClass("FooTest"));
		assertTrue(main.isTestClass("FooTestCase"));
		assertTrue(main.isTestClass("TestFoo"));
    }

	public void testIgnoreNonTestClasses() {
		assertTrue(!main.isTestClass("Foo"));
		assertTrue(!main.isTestClass("FooTestBlah"));
	}

    public void testIgnoreSetUpMethod() {
        main.parse();
        List descriptions = gen.getTestDescriptions("Main");
        assertNotNull(descriptions);
        assertFalse(descriptions.contains("p"));
    }
    
    public void testMainShowsUsageIfNoParameters() {
    	PrintStream oldErr = System.err;
		String result = null;
    	try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			System.setErr(new PrintStream(out));
			Main.main(new String[]{});
			result = out.toString();
		} 
		finally {
			System.setErr(oldErr);
		}
    	assertNotNull(result);
    	assertTrue(result.indexOf(Main.class.getName()) >= 0 );
    	
    	
    }

}
