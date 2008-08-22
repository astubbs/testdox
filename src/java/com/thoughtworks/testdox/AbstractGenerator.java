package com.thoughtworks.testdox;

/**
 * @author Antony Stubbs
 */
public class AbstractGenerator implements DocumentGenerator {

    private int numberOfTestClasses = 0;
    private int numberOfTestCasses = 0;

    /*
     * (non-Javadoc)
     * 
     * @see biz.skizz.testdox.DocumentGenerator#endClass(java.lang.String)
     */
    public void endClass(String name) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see biz.skizz.testdox.DocumentGenerator#endGeneration()
     */
    public void endGeneration() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see biz.skizz.testdox.DocumentGenerator#onTest(java.lang.String)
     */
    public void onTest(String name) {
        numberOfTestCasses++;
    }

    /*
     * (non-Javadoc)
     * 
     * @see biz.skizz.testdox.DocumentGenerator#startClass(java.lang.String)
     */
    public void startClass(String name) {
        numberOfTestClasses++;
    }

    /*
     * (non-Javadoc)
     * 
     * @see biz.skizz.testdox.DocumentGenerator#startPackage(java.lang.String)
     */
    public void startPackage(String name) {
    }

    public int getNumberOfTestClasses() {
        return numberOfTestClasses;
    }

    public void setNumberOfTestClasses(int numberOfTestClasses) {
        this.numberOfTestClasses = numberOfTestClasses;
    }

    public int getNumberOfTestCasses() {
        return numberOfTestCasses;
    }

    public void setNumberOfTestCasses(int numberOfTestCasses) {
        this.numberOfTestCasses = numberOfTestCasses;
    }

    public void endRun() {
        // TODO Auto-generated method stub

    }

    public void startRun() {
        // TODO Auto-generated method stub

    }

}
