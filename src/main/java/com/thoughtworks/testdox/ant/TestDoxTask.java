package com.thoughtworks.testdox.ant;

import com.thoughtworks.testdox.HtmlDocumentGenerator;
import com.thoughtworks.testdox.Main;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * @author Chris Stevenson skizz@codehaus.org
 * @created 10-Dec-2003 20:20:45
 */
public class TestDoxTask extends Task {

    private File dir;
    private File output;
    private String propertyName;

    public void setProject(Project project) {
        this.project = project;
    }

    public void setDir(File dir) {
        this.dir = dir;
    }

    public void setOutput(File output) {
        this.output = output;
    }

    public void execute() {
        log("Processing files from " + dir);
        Main main = new Main();
        try {
            main.setInputFile(dir);
            PrintWriter out = new PrintWriter(new FileWriter(output));
            main.addDocumentGenerator(new XDocGenerator(out));
            main.generate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new BuildException(e);
        }
    }

}
