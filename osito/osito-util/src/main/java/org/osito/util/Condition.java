package org.osito.util;

public interface Condition {

	boolean validate();
	
	Throwable throwableToThrowAfterTimeout();
	
}
