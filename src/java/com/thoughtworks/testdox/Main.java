package com.thoughtworks.testdox;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaSource;
import com.thoughtworks.testdox.ConsoleGenerator;
import com.thoughtworks.testdox.DocumentGenerator;
import com.thoughtworks.testdox.Generator;
import com.thoughtworks.testdox.Gui;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.text.MessageFormat;

/**
 * Simple usage:
 *
 *
 */
public class Main implements Generator {

    private static String usage =
		"Generate simple test documentation from JUnit source files\n" +
    	"Usage: {0} [-txt text-output-file] [-html html-output-file] <source-directory>\n" +
    	"\t<source-directory>: A source directory containing JUnit tests" +
    	"\n" +
    	"TestDox will generate documentation for test cases of the form *Test.java, *TestCase.java\n";
	String directory;
    private MultiplexingGenerator gen = new MultiplexingGenerator();
    private NamePrettifier prettifier = new NamePrettifier();
    public static JFrame gui;

    private File inputFile;

    public Main() {
        gen.addGenerator( new ConsoleGenerator() );
    }

    public void setTestDirectory(String directory) {
        this.directory = directory;
    }

    public void addDocumentGenerator(DocumentGenerator generator) {
        gen.addGenerator(generator);
    }

    public void parse() {
        JavaDocBuilder builder = new JavaDocBuilder();
        builder.addSourceTree(new File(directory));
        doSources(builder.getSources());
    }

    private void doSources(JavaSource[] sources) {
        for (int i = 0; i < sources.length; i++) {
            doClasses(sources[i].getClasses());
        }
    }

    void doClasses(JavaClass[] classes) {
        for (int j = 0; j < classes.length; j++) {
            JavaClass aClass = classes[j];
            if ( isTestClass(aClass.getName())) {
	    	String prettyName = prettifier.prettifyTestClass(aClass.getName());
                gen.startClass(prettyName);
                doMethods(aClass.getMethods());
		gen.endClass(prettyName);
            }
        }
    }

    boolean isTestClass(String className) {
		return className.endsWith("Test") ||
			className.endsWith("TestCase") ||
			className.startsWith("Test");
	}

	private void doMethods(JavaMethod[] methods) {
        for (int k = 0; k < methods.length; k++) {

            String name = methods[k].getName();
            if (prettifier.isATestMethod(name)) {
                gen.onTest( prettifier.prettifyTestMethod( name ) );
            }
        }
    }

    public static void main(String[] args) {

        Main main = new Main();
        try {
            main.processArguments(args);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void showUsage() {
        String message = MessageFormat.format(usage, new String[] {Main.class.getName()} );
        System.err.println(message);
    }

    public void generate() {
        try {
            setTestDirectory(inputFile.getCanonicalPath());
            parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addGenerator(DocumentGenerator generator) {
        gen.addGenerator(generator);
    }

    public void reset() {
        gen.clear();
    }

    public void processArguments(String[] args) throws IOException {

        if ( args.length == 0 ) {
            gui = new Gui("Test Docs", this);
            gui.show();
            return;
        }

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if ( arg.equals("--help") || arg.equals("-h") ) {
                showUsage();
                return;
            }
            else if ( arg.equals("-txt") ) {
                i++;
                checkParameterExists(args, i, "Expected a fileName to follow the -txt argument");
                PrintStream out = new PrintStream(new FileOutputStream(new File(args[i]), true));
                ConsoleGenerator textGenerator = new ConsoleGenerator(out);
                gen.addGenerator(textGenerator);
            }
            else if ( arg.equals("-html") ) {
                i++;
                checkParameterExists(args, i, "Expected a fileName to follow the -html argument");
                PrintWriter out = new PrintWriter(new FileWriter(new File(args[i]), true));
                HtmlDocumentGenerator htmlGenerator = new HtmlDocumentGenerator(out);
                gen.addGenerator(htmlGenerator);
            }
            else {
                checkParameterExists(args, i, "Expected a fileName to follow the -txt argument");
                setInputFile(new File(args[i]));
            }
        }

        if ( inputFile == null ) {
            throw new RuntimeException("Expected an inputFile");
        }

        generate();

    }

    private void checkParameterExists(String[] args, int i, String message) {
        if ( args.length < i ) {
            throw new RuntimeException("Too few arguments: " + message);
        }
    }

    public void setInputFile(File file) {
        this.inputFile = file;
    }
}
