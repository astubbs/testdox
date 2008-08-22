package com.thoughtworks.testdox;

import junit.framework.TestCase;

public class NamePrettifierTest extends TestCase {
    private NamePrettifier namePrettifier;

    protected void setUp() throws Exception {
        super.setUp();
        namePrettifier = new NamePrettifier();
    }

    public void testTitleHasSensibleDefaults() {
        assertEquals( "Foo", namePrettifier.prettifyTestClass("FooTest"));
        assertEquals( "Foo", namePrettifier.prettifyTestClass("TestFoo"));
        assertEquals( "Foo", namePrettifier.prettifyTestClass("TestFooTest"));
    }

    public void testCaterForUserDefinedSuffix() {
        namePrettifier.setSuffix("TestCase");
        namePrettifier.setPrefix(null);
        assertEquals( "Foo", namePrettifier.prettifyTestClass("FooTestCase"));
        assertEquals( "TestFoo", namePrettifier.prettifyTestClass("TestFoo"));
        assertEquals( "FooTest", namePrettifier.prettifyTestClass("FooTest"));
    }

    public void testCaterForUserDefinedPrefix() {
        namePrettifier.setSuffix(null);
        namePrettifier.setPrefix("XXX");
        assertEquals( "Foo", namePrettifier.prettifyTestClass("XXXFoo"));
        assertEquals( "TestXXX", namePrettifier.prettifyTestClass("TestXXX"));
        assertEquals( "XXX", namePrettifier.prettifyTestClass("XXXXXX"));
    }

    public void testTestNameIsConvertedToASentence() {
        assertEquals( "This is a test", namePrettifier.prettifyTestMethod("testThisIsATest"));
        assertEquals( "Database_column_spec is set correctly", namePrettifier.prettifyTestMethod("testDatabase_column_specIsSetCorrectly"));
        assertEquals( "Database_column_spec is set correctly", namePrettifier.prettifyTestMethod("testdatabase_column_specIsSetCorrectly"));
        assertEquals( "Should calculate foo", namePrettifier.prettifyTestMethod("shouldCalculateFoo"));
        assertEquals( "Should coumn_spec be set to false", namePrettifier.prettifyTestMethod("shouldCoumn_specBeSetToFalse"));
        assertEquals( "Should set coumn_spec be to false", namePrettifier.prettifyTestMethod("shouldSetCoumn_specBeToFalse"));
        assertEquals( "Shouldcoumn_spec be set to false", namePrettifier.prettifyTestMethod("shouldcoumn_specBeSetToFalse"));
    }
    
    public void testBadMethodName(){
    	assertEquals( "", namePrettifier.prettifyTestMethod("test"));
    }

    public void testIsATestIsFalseForNonTestMethods() {
        assertFalse(namePrettifier.isATestMethod("setUp"));
        assertFalse(namePrettifier.isATestMethod("tearDown"));
        assertFalse(namePrettifier.isATestMethod("foo"));
    }
    
    public void testIsATestIsTrueForTestMethods() {
        assertTrue(namePrettifier.isATestMethod("fooTest"));
        assertTrue(namePrettifier.isATestMethod("shouldCalculateBars"));
        assertTrue(namePrettifier.isATestMethod("testFooBars"));
    }
}
