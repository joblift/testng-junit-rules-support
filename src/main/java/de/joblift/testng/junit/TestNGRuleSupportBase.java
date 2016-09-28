package de.joblift.testng.junit;


import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.google.common.collect.ImmutableList;


/**
 * A Baseclass for Tests that use JUnit Rules. Does not Support Rules on class level, but at least allows to use them on
 * method level.
 */
public class TestNGRuleSupportBase {

	public interface ExceptionRunnable {

		void run() throws Exception;
	}

	private static final class TestStatement extends Statement {

		private final ExceptionRunnable runnable;


		public TestStatement(ExceptionRunnable runnable) {
			this.runnable = runnable;
		}


		@Override
		public void evaluate() throws Throwable {
			runnable.run();
		}
	}

	private final AtomicInteger counter = new AtomicInteger(0);


	public void runWithRules(TestRule rule, ExceptionRunnable runnable) throws Throwable {
		runWithRules(ImmutableList.of(rule), runnable);
	}


	public void runWithRules(List<TestRule> rules, ExceptionRunnable runnable) throws Throwable {
		runWithRules(createTestName(), rules, runnable);
	}


	private String createTestName() {
		return "test_" + counter.incrementAndGet();
	}


	public void runWithRules(String name, TestRule rule, ExceptionRunnable runnable) throws Throwable {
		runWithRules(createTestName(), ImmutableList.of(rule), runnable);
	}


	public void runWithRules(String name, List<TestRule> rules, ExceptionRunnable runnable) throws Throwable {
		Description description = Description.createTestDescription(getClass(), name);
		TestStatement test = new TestStatement(runnable);
		Statement statement = test;
		for (TestRule r: rules) {
			statement = r.apply(statement, description);
		}
		statement.evaluate();
	}
}
