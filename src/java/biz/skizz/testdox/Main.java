package biz.skizz.testdox;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaSource;

import javax.swing.*;

/**
 * Simple usage:
 * 
 *
 */
public class Main implements Generator {

    private static String usage = 
		"Generate simple test documentation from JUnit source files\n" +
    	"Usage: {0} <source-directory>\n" +
    	"\t<source-directory>: A source directory containing JUnit tests" +
    	"\n" +
    	"TestDox will generate documentation for test cases of the form *Test.java, *TestCase.java\n";
	String directory;
    private MultiplexingGenerator gen = new MultiplexingGenerator();
    private NamePrettifier prettifier = new NamePrettifier();
    public static JFrame gui;

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

        Generator main = new Main();
        if ( args.length == 0 ) {
            gui = new Gui("Test Docs", main);
            gui.show();
            return;
//    		String message = MessageFormat.format(usage, new String[] {Main.class.getName()} );
//    		System.err.println(message);
//    		return;
        }
        File file = new File(args[0]);
        main.generate(file);
    }

    public void generate(File file) {
        try {
            setTestDirectory(file.getCanonicalPath());
            parse();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }
    }
}
