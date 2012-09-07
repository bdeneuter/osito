package org.osito.javafx;

public class MessageKey {

	private String value;
	
	private MessageKey(String value) {
		this.value = value;
	}

	public static MessageKey key(int value) {
		return new MessageKey(Integer.toString(value));
	}
	
	public static MessageKey key(String value) {
		return new MessageKey(value);
	}
	
	public String getValue() {
		return value;
	}
}
