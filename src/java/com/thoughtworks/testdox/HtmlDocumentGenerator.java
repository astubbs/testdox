package com.thoughtworks.testdox;

import com.thoughtworks.testdox.DocumentGenerator;

import java.io.PrintWriter;

public class HtmlDocumentGenerator implements DocumentGenerator {
	PrintWriter out;
	public HtmlDocumentGenerator(PrintWriter out) {
		this.out = out;
	}
	public void startClass(String name) {
		out.println("<h2>" + name + "</h2>");
		out.println("<ul>");
	}
	public void endClass(String name) {
		out.println("</ul>");
	}
	public void onTest(String name) {
		out.println("<li>" + name + "</li>");
	}
}
