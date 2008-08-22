package com.thoughtworks.testdox;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;

import junit.framework.TestCase;

import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;

/**
 * Created by IntelliJ IDEA.
 * User: skizz
 * Date: May 9, 2003
 * Time: 3:38:22 PM
 * To change this template use Options | File Templates.
 */
public class DocumentGeneratorTest extends TestCase {

    List<String> messages = new ArrayList<String>();
    private Main main;
    
    public void setUp()
    {
        main = new Main();
        main.addDocumentGenerator(new Foo());
    }
    
    private class Foo implements DocumentGenerator {
        public void startClass(String name) {
            messages.add("startClass(" + name + ")");
        }
    	public void endClass(String name) {
    	    messages.add("endClass(" + name + ")");
    	}
        public void onTest(String name) {
            messages.add("onTest(" + name + ")");
        }
        public void endGeneration() {
            // TODO Auto-generated method stub
            
        }
        public void endRun() {
        	messages.add("endRun()");
            
        }
        public void startPackage(String name) {
            // TODO Auto-generated method stub
            
        }
        
        public void startRun() {
	           messages.add("startRun()");
        }
    }

    public void testStartRunAndEndRunAreCalled() {
        main.doSources(new JavaSource[] {});
        assertTrue(messages.contains("startRun()"));
        assertTrue(messages.contains("endRun()"));
    }    

    public void testStartClassAndEndClassAreCalled() {
        Main main = new Main();
        main.addDocumentGenerator(new Foo());
        JavaClass[] aClass = new JavaClass[1];
        aClass[0] = new JavaClass();
        aClass[0].setName("ClassNameTest");
        main.doClasses(aClass, Mockito.mock(JavaSource.class), new ArrayList<String>());
        JavaSource mockJavaSource = Mockito.mock(JavaSource.class);
        main.doClasses(aClass, mockJavaSource, new ArrayList<String>());
        assertTrue(messages.contains("startClass(ClassName)"));
        assertTrue(messages.contains("endClass(ClassName)"));
    }
}
