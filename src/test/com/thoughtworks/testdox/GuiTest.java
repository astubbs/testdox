package com.thoughtworks.testdox;

import junit.framework.TestCase;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import com.thoughtworks.testdox.Generator;
import com.thoughtworks.testdox.Gui;

/**
 * Created by IntelliJ IDEA.
 * User: stevcc
 * Date: 11-Jun-2003
 * Time: 19:09:15
 * To change this template use Options | File Templates.
 */
public class GuiTest extends TestCase {
    private Gui gui;
    private static JFileChooser selectSrcChooser = new JFileChooser() {
        public File getSelectedFile() {
            return selectedFile;
        }

        public int showOpenDialog(Component parent) throws HeadlessException {
            return JFileChooser.APPROVE_OPTION;
        }
    };
    private static File selectedFile = new File("src");
    private TestGenerator gen;

    protected void setUp() throws Exception {
        gen = new TestGenerator();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testShowsFileChooserIfBrowseIsClicked() {
        final boolean[] wasShown = new boolean[]{false};

        JFileChooser chooser = new JFileChooser() {
            public int showOpenDialog(Component parent) throws HeadlessException {
                wasShown[0] = true;
                return JFileChooser.CANCEL_OPTION;
            }
        };

        gui = new Gui("foo", gen);
        gui.fileChooser = chooser;
        gui.browseButton.doClick();
        assertNotNull(gui.fileChooser);
        assertTrue(gui.fileChooser.isDirectorySelectionEnabled());
        assertTrue(wasShown[0]);
    }

    public void testSomeGuiDefaults() {
        gui = new Gui("foo", gen);
        assertEquals(JFrame.EXIT_ON_CLOSE, gui.getDefaultCloseOperation());
    }

    public void testSelectedFileIsShownInTextField() {

        gui = new Gui("foo", gen);
        gui.fileChooser = selectSrcChooser;
        gui.browseButton.doClick();

        assertEquals("src", gui.path.getText());
    }

    public void testSelectedFileIsNotShownIfUserClickedCancel() {
        JFileChooser chooser = new JFileChooser() {
            public File getSelectedFile() {
                return null;
            }

            public int showOpenDialog(Component parent) throws HeadlessException {
                return JFileChooser.CANCEL_OPTION;
            }
        };

        gui = new Gui("foo", gen);
        gui.fileChooser = chooser;
        gui.browseButton.doClick();

        assertEquals("", gui.path.getText());
    }

    public void testGoButtonEnableUponUserFileSelection() {
        gui = new Gui("foo", gen);
        assertFalse(gui.goButton.isEnabled());
        gui.fileChooser = selectSrcChooser;
        gui.browseButton.doClick();
        assertTrue(gui.goButton.isEnabled());
    }

    public static class TestGenerator implements Generator {
        private File file;

        public void setInputFile(File file) {
            this.file = file;
        }

        public void generate() {
        }

        public File getFile() {
            return file;
        }
    };

    public void testClickGoButtonRunsIt() {

        gui = new Gui("foo", gen);
        gui.path.setText("src");
        gui.goButton.setEnabled(true);
        gui.goButton.doClick();

        assertEquals(selectedFile, gen.getFile());
    }


    public void testGoButtonDisabledIfFileDoesNotExist() {
        gui = new Gui("foo", gen);
        gui.path.setText("non-existent-file");
        assertFalse(gui.goButton.isEnabled());
    }

    public void testEnteringPathFreehandEnablesGoButtonAndMakesItDefault() throws IOException, InterruptedException {
        gui = new Gui("foo", gen);
        gui.path.setText(selectedFile.getCanonicalPath());
        assertTrue(gui.goButton.isEnabled());
        assertTrue(gui.goButton.isDefaultButton());
    }

}
