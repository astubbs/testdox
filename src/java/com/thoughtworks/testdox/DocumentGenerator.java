package com.thoughtworks.testdox;

/**
 * The difference between endRun and endGeneration is?
 */
public interface DocumentGenerator {

    void startClass(String name);
    void onTest(String name);
    void endClass(String name);
    void startPackage(String name);
    void endGeneration();
    void startRun();
    void endRun();

}
