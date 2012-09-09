package org.osito.javafx;

import org.osito.javafx.dialog.MessageKey;

@SuppressWarnings("serial")
public class ApplicationException extends RuntimeException {

	private MessageKey key;

	public ApplicationException(MessageKey key) {
		super(key.name());
		this.key = key;
	}

	public MessageKey getKey() {
		return key;
	}
}
