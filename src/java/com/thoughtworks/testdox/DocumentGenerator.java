package com.thoughtworks.testdox;

public interface DocumentGenerator  {

    void startClass(String name);
    void onTest(String name);
    void endClass(String name);
    void startRun();
    void endRun();

}
