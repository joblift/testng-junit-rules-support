package de.joblift.testng.junit;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.runners.model.Statement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableSet;

public class TestNGRuleSupportBaseTest extends TestNGRuleSupportBase {


	@Test
	public void testIdentityRule() throws Throwable {
		final AtomicInteger counter = new AtomicInteger(0);
		runWithRules("testIdentityRule", (base, description) -> base, () -> counter.incrementAndGet());
		Assert.assertEquals(counter.get(), 1, "Actual Test code has not been called");
	}

	@Test(expectedExceptions = UnsupportedOperationException.class)
	public void testExceptionThrowing() throws Throwable {
		runWithRules("testExceptionThrowing", (base, description) -> base, () -> {
			throw new UnsupportedOperationException();
		});
		Assert.fail("Exception should have been thrown already");
	}

	@Test
	public void testSimpleRule() throws Throwable {
		final Set<String> steps = new HashSet<>();
		runWithRules("testSimpleRule", (base, description) -> new Statement() {

			@Override
			public void evaluate() throws Throwable {
				steps.add("before");
				base.evaluate();
				steps.add("after");
			}
		}, () -> steps.add("during"));
		Assert.assertEquals(steps, ImmutableSet.of("before", "during", "after"));
	}

}
