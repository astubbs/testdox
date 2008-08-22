package com.thoughtworks.testdox;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class NamePrettifier {

    private Logger log = Logger.getLogger(NamePrettifier.class);

    String[] sufixList = { "Test", "Behaviour" };
    String[] prefixList = { "Test", "Should" };

    public String prettifyTestClass(String className) {
        String title = className;
        List<String> suffixs = Arrays.asList(sufixList);
        List<String> prefixs = Arrays.asList(prefixList);
        for (String sufix : suffixs) {
            title = removeSuffix(title, sufix);
        }
        for (String prefix : prefixs) {
            title = removePrefix(title, prefix);
        }
        return title;
    }

    /**
     * @param title
     * @param prefixToRemove
     * @return
     */
    private String removePrefix(String title, String prefixToRemove) {
        if (prefixToRemove != null && title.startsWith(prefixToRemove)) {
            title = title.substring(prefixToRemove.length());
        }
        return title;
    }

    /**
     * @param title
     * @param suffixToRemove
     * @return
     */
    private String removeSuffix(String title, String suffixToRemove) {
        if (suffixToRemove != null && title.endsWith(suffixToRemove)) {
            title = title.substring(0, title.lastIndexOf(suffixToRemove));
        }
        return title;
    }

    public void setTestSuffix(String suffix) {
        this.sufixList = new String[1];
        this.sufixList[0] = suffix;
    }

    public void setSuffix(String suffix) {
        this.sufixList = new String[1];
        this.sufixList[0] = suffix;
    }

    public void setPrefix(String prefix) {
        this.prefixList = new String[1];
        this.prefixList[0] = prefix;
    }

    public String prettifyTestMethod(String methodName) {
        StringBuffer buf = new StringBuffer();
        String testMethod = methodName;
        // don't want to remove BDD words 'should' or 'behaviour'
        /*
         * List<String> prefixs = Arrays.asList(prefixList); for(String prefix :
         * prefixs){ testMethod = StringUtils.removeStart(testMethod,
         * prefix.toLowerCase()); }
         */
        testMethod = StringUtils.removeStart(testMethod, "test");
        int pos = 0;
        while (pos < testMethod.length()) {
            char ch = testMethod.charAt(pos);
            if (pos == 0) {
                buf.append(Character.toUpperCase(ch));
            } else if (Character.isUpperCase(ch)) {
                buf.append(" ");
                buf.append(Character.toLowerCase(ch));
            } else {
                buf.append(ch);
            }
            pos++;
        }
        String prettyName = buf.toString().trim();
        if (prettyName.length() == 0) {
            log.warn("Bad method name[" + methodName
                    + "] - pretty length is zero. Rename your method.");
        }
        return prettyName;
    }

    public boolean isATestMethod(String testMethod) {
        List<String> suffixs = Arrays.asList(sufixList);
        List<String> prefixs = Arrays.asList(prefixList);
        for (String sufix : suffixs) {
            if (testMethod.toLowerCase().endsWith(sufix.toLowerCase())) {
                return true;
            }
        }
        for (String prefix : prefixs) {
            if (testMethod.toLowerCase().startsWith(prefix.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
