package com.thoughtworks.testdox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockDocumentGenerator implements DocumentGenerator {

    Map descriptions = new HashMap();
    List currentList;

    public void startClass(String className) {
        currentList = new ArrayList();
        descriptions.put(className, currentList);
    }

    public void onTest(String name) {
        currentList.add(name);
    }

    public void endClass(String name) {
    }

    public List getTestDescriptions(String className) {
        return (List) descriptions.get(className);
    }

    public void endGeneration() {
        // TODO Auto-generated method stub
        
    }

    public void endRun() {
        // TODO Auto-generated method stub
        
    }

    public void startPackage(String name) {
        // TODO Auto-generated method stub
        
    }

    public void startRun() {
        // TODO Auto-generated method stub
        
    }
}
