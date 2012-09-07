package org.osito.javafx;

@SuppressWarnings("serial")
public class ApplicationException extends RuntimeException {

	private MessageKey key;

	public ApplicationException(MessageKey key) {
		super(key.getValue());
		this.key = key;
	}

	public MessageKey getKey() {
		return key;
	}
}
