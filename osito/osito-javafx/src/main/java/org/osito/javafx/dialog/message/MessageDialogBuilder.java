package org.osito.javafx.dialog.message;

import org.osito.javafx.MessageKey;

public class MessageDialogBuilder {

	public static MessageDialogBuilder aMessageDialog() {
		return new MessageDialogBuilder();
	}

	public static MessageDialogBuilder aConfirmDialog() {
		return new MessageDialogBuilder();
	}
	
	public MessageBuilder withMessage(MessageKey message) {
		return new MessageBuilder(message);
	}
	
}
