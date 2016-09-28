# testng-junit-rules-support

This is basically a workaround for the fact, that currently testng does not support Junit Rules. Some libraries start to ship with JUnit Rules, though, without providing a non-junit alternative.

With the help of this small project, you can instantiate a rule and execute your testng code like this:

    static final TestRule RULE = ....
    @Test
    public void testWithRule() throws Throwable {
        runWithRules("testSimpleRule", RULE, () -> {
            // insert your test code here. It will be executed
            // in the context of the given RULE
        });
        
    }

You can also execute your code within multiple Rules:

    static final List<TestRule> RULES = ....
    @Test
    public void testWithRule() throws Throwable {
        runWithRules("testSimpleRule", RULES, () -> {
            // insert your test code here. It will be executed
            // in the context of the given RULES
        });
        
    }

## Release
./gradlew release -Prelease.useAutomaticVersion=true 
