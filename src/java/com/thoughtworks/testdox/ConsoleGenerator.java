package com.thoughtworks.testdox;

import java.io.PrintWriter;
import java.io.PrintStream;


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
    }
    public void onTest(String name) {
        out.println("    - " + name);
    }

    public void endClass(String name) {
    }
}
