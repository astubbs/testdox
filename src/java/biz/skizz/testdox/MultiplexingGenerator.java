package biz.skizz.testdox;

import java.util.ArrayList;
import java.util.List;

public class MultiplexingGenerator implements DocumentGenerator {

    List generators = new ArrayList();

    public void addGenerator(DocumentGenerator gen) {
        generators.add(gen);
    }


    public void startClass(String name) {
        for (int i = 0; i < generators.size(); i++) {
            DocumentGenerator generator = (DocumentGenerator) generators.get(i);
            generator.startClass(name);
        }
    }
    public void onTest(String name) {
        for (int i = 0; i < generators.size(); i++) {
            DocumentGenerator generator = (DocumentGenerator) generators.get(i);
            generator.onTest(name);
        }
    }
    public void endClass(String name) {
        for (int i = 0; i < generators.size(); i++) {
            DocumentGenerator generator = (DocumentGenerator) generators.get(i);
            generator.endClass(name);
        }
    }
}
