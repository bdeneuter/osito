package org.osito.util;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.osito.util.Poller.aPoller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PollerTest {

	@Mock
	private Runnable runnable;
	
	@Mock
	private Assertion assertion;
	
	@Test
	public void doRun_TryToExecuteTheRunnableUntilItRunsWithoutException() {
		
		doThrow(RuntimeException.class).
		doNothing().
		when(runnable).run();
		
		aPoller().doRun(runnable);
		
		verify(runnable, times(2)).run();
	}

	@Test(expected = IllegalStateException.class)
	public void doRun_WhenRunnableNotExecutedSuccessfulBeforeTimeout_ThenThrowLastException() {
		
		doThrow(IllegalStateException.class).when(runnable).run();
		
		aPoller().withTimeout(2000).doRun(runnable);
		
	}

	@Test
	public void doAssert_TryToExecuteAssertionUntilItIsValid() throws Exception {
		
		doThrow(AssertionError.class).
		doNothing().
		when(assertion).doAssert();
		
		aPoller().doAssert(assertion);
		
		verify(assertion, times(2)).doAssert();
	}
	
	@Test(expected = AssertionError.class)
	public void doAssert_WhenAssertionNotValidBeforeTimeout_ThenThrowAssertionError() {
		
		doThrow(AssertionError.class).when(assertion).doAssert();
		
		aPoller().withTimeout(2000).doAssert(assertion);
		
	}
	
}
