package com.thoughtworks.testdox;

import java.io.PrintWriter;

public class HtmlDocumentGenerator extends AbstractGenerator {
    protected PrintWriter out;

    public HtmlDocumentGenerator(PrintWriter out) {
        this.out = out;
    }

    public void startPackage(String name) {
        super.startPackage(name);
        out.println("<h1>" + name + "</h1>");
    }

    public void startClass(String name) {
        super.startClass(name);
        out.println("<h2>" + name + "</h2>");
        out.println("<ul>");
        out.flush();
    }

    public void endClass(String name) {
        out.println("</ul>");
        out.flush();
    }

    public void onTest(String name) {
        super.onTest(name);
        out.println("<li>" + name + "</li>");
        out.flush();
    }

    public void endGeneration() {
        out
                .println("Total number of test classes: "
                        + getNumberOfTestClasses());
        out.println("Total number of test casses: " + getNumberOfTestCasses());
        out.flush();
        out.close();
        System.out.println("Finished HTML generation...");
    }
}
