package biz.skizz.testdox;

public interface DocumentGenerator {

    void startClass(String name);
    void onTest(String name);
    void endClass(String name);

}
