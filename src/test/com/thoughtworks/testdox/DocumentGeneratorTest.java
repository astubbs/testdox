package com.thoughtworks.testdox;

import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;
import com.thoughtworks.testdox.DocumentGenerator;
import com.thoughtworks.testdox.Main;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: skizz
 * Date: May 9, 2003
 * Time: 3:38:22 PM
 * To change this template use Options | File Templates.
 */
public class DocumentGeneratorTest extends TestCase {

    List messages = new ArrayList();
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

        public void startRun() {
            messages.add("startRun()");
        }

        public void endRun() {
            messages.add("endRun()");
        }

        public void onTest(String name) {
            messages.add("onTest(" + name + ")");
        }
    }

    public void testStartRunAndEndRunAreCalled() {
        main.doSources(new JavaSource[] {});
        assertTrue(messages.contains("startRun()"));
        assertTrue(messages.contains("endRun()"));
    }

    public void testStartClassAndEndClassAreCalled() {
        JavaClass[] aClass = new JavaClass[1];
        aClass[0] = new JavaClass();
        aClass[0].setName("ClassNameTest");
        main.doClasses(aClass);
        assertTrue(messages.contains("startClass(ClassName)"));
        assertTrue(messages.contains("endClass(ClassName)"));
    }
}
