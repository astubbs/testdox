package biz.skizz.testdox;

public class NamePrettifier {

    String suffix = "Test";
    String prefix = "Test";

    public String prettifyTestClass(String className) {

        String title = className;
        if (suffix!=null && title.endsWith(suffix)) {
            title = title.substring(0, title.lastIndexOf(suffix));
        }
        if (prefix!=null && title.startsWith(prefix)) {
            title = title.substring(prefix.length());
        }
        return title;
    }

    public void setTestSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String prettifyTestMethod(String testMethod) {
        StringBuffer buf = new StringBuffer();
        int pos = 4; //after the 'test'
        while (pos<testMethod.length()) {
            char ch = testMethod.charAt(pos);
            if ( pos>4 && Character.isUpperCase(ch) ) {
                buf.append(" ");
                buf.append(Character.toLowerCase(ch));
            }
            else {
                buf.append(ch);
            }
            pos++;
        }
        return buf.toString();
    }

    public boolean isATestMethod(String testMethod) {
        return testMethod.startsWith("test");
    }
}
