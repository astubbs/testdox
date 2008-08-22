package com.thoughtworks.testdox.ant;

import com.thoughtworks.testdox.HtmlDocumentGenerator;

import java.io.PrintWriter;

/**
 * @author Chris Stevenson skizz@codehaus.org
 * @created 10-Dec-2003 22:06:30
 */
public class XDocGenerator extends HtmlDocumentGenerator {

    String preamble =
            "<document>\n" +
            "   <properties>\n" +
            "       <title>TestDox Report</title>\n" +
            "   </properties>\n" +
            "   <body>\n";

    String post =
            "  </body>\n" +
            "</document>\n";

    public XDocGenerator(PrintWriter out) {
        super(out);
    }

    public void startRun() {
        out.println(preamble);
    }

    public void endRun() {
        out.println(post);
        out.flush();
    }

	public void startClass(String name) {
		out.println("       <section name=\"" + name + "\">");
		out.println("           <ul>");
	}
	public void endClass(String name) {
		out.println("           </ul>");
        out.println("       </section>");
	}
	public void onTest(String name) {
		out.println("               <li>" + name + "</li>");
	}

}
