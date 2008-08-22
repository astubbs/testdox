package com.thoughtworks.testdox;

import java.awt.PopupMenu;
import java.awt.Component;

public interface DocumentGeneratorGui {
    boolean isConfigured();
    DocumentGenerator createDocumentGenerator();
    Component getComponent();

}
