package com.thoughtworks.testdox;

import java.util.ArrayList;
import java.util.List;

public class MultiplexingGenerator extends AbstractGenerator {

    List<DocumentGenerator> generators = new ArrayList<DocumentGenerator>();

    public void addGenerator(DocumentGenerator gen) {
        generators.add(gen);
    }

    public void startPackage(String name) {
        for (int i = 0; i < generators.size(); i++) {
            DocumentGenerator generator = generators.get(i);
            generator.startPackage(name);
        }
    }

    public void startClass(String name) {
        for (int i = 0; i < generators.size(); i++) {
            DocumentGenerator generator = generators.get(i);
            generator.startClass(name);
        }
    }
    public void onTest(String name) {
        for (int i = 0; i < generators.size(); i++) {
            DocumentGenerator generator = generators.get(i);
            generator.onTest(name);
        }
    }
    public void endClass(String name) {
        for (int i = 0; i < generators.size(); i++) {
            DocumentGenerator generator = generators.get(i);
            generator.endClass(name);
        }
    }

    public void endGeneration() {
        for (int i = 0; i < generators.size(); i++) {
            DocumentGenerator generator = generators.get(i);
            generator.endGeneration();
        }
    }
	
    public void startRun() {
        for (int i = 0; i < generators.size(); i++) {
            DocumentGenerator generator = generators.get(i);
            generator.startRun();
        }
    }

    public void endRun() {
        for (int i = 0; i < generators.size(); i++) {
            DocumentGenerator generator = generators.get(i);
            generator.endRun();
        }
    }

    public void clear() {
        generators.clear();
    }
}
