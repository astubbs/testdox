package com.thoughtworks.testdox;

import junit.framework.TestCase;

import java.io.IOException;

public class SaveAsGuiTest extends TestCase {
    private SaveAsGui gui;
    private static final String HELPFUL_TEXT = "Some helpful text";
    private static final String SAVE_TEXT = "Save HTML";

    protected void setUp() throws Exception {
        super.setUp();
        gui = new SaveAsGui(HELPFUL_TEXT, SAVE_TEXT) {
            public DocumentGenerator createDocumentGenerator() {
                return null;
            }
        };
    }

    public void testClickSaveAsSetsFileText() throws IOException {
        gui.fileChooser = GuiTestUtil.selectSrcChooser;
        gui.saveAsButton.doClick();
        assertEquals( GuiTestUtil.selectedFile.getCanonicalPath(), gui.fileName.getText() );
    }

    public void testLabelHasHelpfulText() throws IOException {
        assertEquals( HELPFUL_TEXT, gui.titledBorder.getTitle() );
    }

    public void testSaveAsHasMeaningfulText() throws IOException {
        assertEquals( SAVE_TEXT, gui.saveAsButton.getText() );
    }

    public void testHtmlSaveAsGuiReturnsHtmlDocGenerator() {
        HtmlSaveAsGui gui = new HtmlSaveAsGui();
        gui.fileName.setText("test.html");
        DocumentGenerator documentGenerator = gui.createDocumentGenerator();
        assertNotNull(documentGenerator);
        assertTrue(documentGenerator instanceof HtmlDocumentGenerator);
    }

    public void testTextSaveAsGuiReturnsTextGenerator() {
        TextSaveAsGui gui = new TextSaveAsGui();
        gui.fileName.setText("test.txt");
        DocumentGenerator documentGenerator = gui.createDocumentGenerator();
        assertNotNull(documentGenerator);
        assertTrue(documentGenerator instanceof ConsoleGenerator);
    }
}
