package com.thoughtworks.testdox;

import java.io.PrintWriter;
import java.io.StringWriter;

import junit.framework.TestCase;

public class HtmlDocumentGeneratorTest extends TestCase {

    private StringWriter out;

    private HtmlDocumentGenerator gen;

    protected void setUp() throws Exception {
        super.setUp();
        out = new StringWriter();
        gen = new HtmlDocumentGenerator(new PrintWriter(out));
    }

    public void testShowsHeadingForClass() {
        gen.startClass("Foo");
        assertMatches("<h2>Foo</h2>");
    }

    public void testUnorderedListForMethods() {
        gen.startClass("Foo");
        gen.onTest("ATest");
        gen.endClass("Foo");
        assertMatches("<ul>");
        assertMatches("<li>ATest</li>");
        assertMatches("</ul>");
    }

    private void assertMatches(String pattern) {
        String result = out.toString();
        if (result.indexOf(pattern) == -1) {
            fail("Expected " + pattern + " but got " + result);
        }
    }

}
