package com.thoughtworks.testdox;

import com.thoughtworks.testdox.DocumentGenerator;

import java.io.PrintWriter;

/**
 * 2003-08-12 out.flush() added at the suggestion of Mike Mason
 */
public class HtmlDocumentGenerator implements DocumentGenerator {
	PrintWriter out;
	public HtmlDocumentGenerator(PrintWriter out) {
		this.out = out;
	}
	public void startClass(String name) {
		out.println("<h2>" + name + "</h2>");
		out.println("<ul>");
        out.flush();
	}
	public void endClass(String name) {
		out.println("</ul>");
        out.flush();
	}
	public void onTest(String name) {
		out.println("<li>" + name + "</li>");
        out.flush();
	}
}
