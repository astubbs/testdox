package com.thoughtworks.testdox;

import java.io.PrintWriter;
import java.io.PrintStream;

/**
 * 2003-08-12 out.flush() added at the request of Mike Mason
 */
public class ConsoleGenerator implements DocumentGenerator {
    private PrintStream out;

    public ConsoleGenerator() {
        this(System.out);
    }

    public ConsoleGenerator(PrintStream out) {
        this.out = out;
    }

    public void startClass(String name) {
        out.println(name);
        out.flush();
    }
    public void onTest(String name) {
        out.println("    - " + name);
        out.flush();
    }

    public void endClass(String name) {
    }
}
