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
    private TestGenerator gen;

    protected void setUp() throws Exception {
        gen = new TestGenerator();
        gui = new Gui("foo", gen);
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

        gui.fileChooser = GuiTestUtil.selectSrcChooser;
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
        assertFalse(gui.goButton.isEnabled());
        gui.fileChooser = GuiTestUtil.selectSrcChooser;
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

        public void addGenerator(DocumentGenerator generator) {

        }

        public void reset() {

        }

        public File getFile() {
            return file;
        }
    };

    public void testClickGoButtonRunsIt() {

        gui.path.setText("src");
        gui.goButton.setEnabled(true);
        gui.goButton.doClick();

        assertEquals(GuiTestUtil.selectedFile, gen.getFile());
    }


    public void testGoButtonDisabledIfFileDoesNotExist() {
        gui.path.setText("non-existent-file");
        assertFalse(gui.goButton.isEnabled());
    }

    public void testEnteringPathFreehandEnablesGoButtonAndMakesItDefault() throws IOException, InterruptedException {
        gui.path.setText(GuiTestUtil.selectedFile.getCanonicalPath());
        assertTrue(gui.goButton.isEnabled());
        assertTrue(gui.goButton.isDefaultButton());
    }

    public void testConfiguredDocuemntGeneratorIsAddedToGenerators() throws IOException, InterruptedException {
        MockDocumentGenerator testDocumentGenerator = new MockDocumentGenerator();
        JLabel guiComponent = new JLabel();
        MockDocumentGeneratorGui generatorGui = new MockDocumentGeneratorGui(testDocumentGenerator, guiComponent);
        gui.addDocumentGeneratorGui(generatorGui);
        assertTrue(guiComponent.isVisible());
        gui.goButton.setEnabled(true);
        gui.goButton.doClick();
        assertTrue(generatorGui.createDocumentGeneratorWasCalled());
    }

    public void testUnConfiguredDocuemntGeneratorIsNotAddedToGenerators() throws IOException, InterruptedException {
        MockDocumentGenerator testDocumentGenerator = new MockDocumentGenerator();
        JLabel guiComponent = new JLabel();
        MockDocumentGeneratorGui generatorGui = new MockDocumentGeneratorGui(testDocumentGenerator, guiComponent) {
            public boolean isConfigured() {
                return false;
            }
        };
        gui.addDocumentGeneratorGui(generatorGui);
        gui.goButton.setEnabled(true);
        gui.goButton.doClick();
        assertFalse(generatorGui.createDocumentGeneratorWasCalled());
    }
}
