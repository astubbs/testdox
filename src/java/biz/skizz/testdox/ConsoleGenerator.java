package biz.skizz.testdox;

public class ConsoleGenerator implements DocumentGenerator {

    public void startClass(String name) {
        System.out.println(name);
    }
    public void onTest(String name) {
        System.out.println("    - " + name);
    }
    
    public void endClass(String name) {
    }
}
