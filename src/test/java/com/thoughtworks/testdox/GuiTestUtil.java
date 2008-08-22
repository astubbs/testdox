package com.thoughtworks.testdox;

import javax.swing.*;
import java.io.File;
import java.awt.*;

public class GuiTestUtil {

    public static File selectedFile = new File("src");

    public static JFileChooser selectSrcChooser = new JFileChooser() {
        public File getSelectedFile() {
            return selectedFile;
        }

        public int showOpenDialog(Component parent) throws HeadlessException {
            return JFileChooser.APPROVE_OPTION;
        }

        public int showSaveDialog(Component parent) throws HeadlessException {
            return JFileChooser.APPROVE_OPTION;
        }
    };

}
