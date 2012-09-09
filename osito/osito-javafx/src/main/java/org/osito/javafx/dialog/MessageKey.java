package org.osito.javafx.dialog;

import static org.osito.javafx.dialog.MessageService.messageService;

public enum MessageKey {
	CLOSE, 
	OK, 
	CANCEL, 
	NO, 
	YES,
	ERROR,
	QUESTION;
	
	public String getText() {
		return messageService().getText(this);
	}
}
