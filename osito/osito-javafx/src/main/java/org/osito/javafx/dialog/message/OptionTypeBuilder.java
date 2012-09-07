package org.osito.javafx.dialog.message;

import org.osito.javafx.MessageKey;

public class OptionTypeBuilder {

	private OptionType optionType;
	private MessageKey messageKey;

	OptionTypeBuilder(MessageKey messageKey, OptionType optionType) {
		this.messageKey = messageKey;
		this.optionType = optionType;
	}

}
