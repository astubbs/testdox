package com.thoughtworks.testdox;

import javax.swing.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class HtmlSaveAsGui extends SaveAsGui {

    public HtmlSaveAsGui() {
        super("Generate HTML", "Save HTML");
    }

    public DocumentGenerator createDocumentGenerator() {
        try {
            return new HtmlDocumentGenerator(new PrintWriter( new FileWriter(fileName.getText()), true) );
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error creating file", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
}
