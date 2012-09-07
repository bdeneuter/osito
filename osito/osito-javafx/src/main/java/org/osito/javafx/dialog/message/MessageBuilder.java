package org.osito.javafx.dialog.message;

import org.osito.javafx.MessageKey;

public class MessageBuilder {

	private MessageKey message;

	MessageBuilder(MessageKey message) {
		this.message = message;
	}
	
	public OptionTypeBuilder withOptionType(OptionType optionType) {
		return new OptionTypeBuilder(message, optionType);
	}
	
}
