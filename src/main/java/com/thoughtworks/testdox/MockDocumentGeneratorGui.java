package com.thoughtworks.testdox;

import javax.swing.*;
import java.awt.Component;

public class MockDocumentGeneratorGui implements DocumentGeneratorGui {
    private DocumentGenerator generator;
    private Component component;
    private boolean createDocumentGeneratorWasCalled;

    public MockDocumentGeneratorGui(DocumentGenerator generator, Component component) {
        this.generator = generator;
        this.component = component;
    }

    public boolean isConfigured() {
        return true;
    }

    public DocumentGenerator createDocumentGenerator() {
        createDocumentGeneratorWasCalled = true;
        return generator;
    }

    public Component getComponent() {
        return component;
    }

    boolean createDocumentGeneratorWasCalled() {
        return createDocumentGeneratorWasCalled;
    }

}
