package com.thoughtworks.testdox;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: stevcc
 * Date: 11-Jun-2003
 * Time: 20:17:50
 * To change this template use Options | File Templates.
 */
public interface Generator {
    public void setInputFile(File file);
    public void generate();
}
