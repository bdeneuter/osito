package org.osito.javafx.dialog;

import java.util.ResourceBundle;

class MessageService {

	private static MessageService instance = new MessageService();
	
	private ResourceBundle bundle;

	static MessageService messageService() {
		return instance;
	}
	
	String getText(MessageKey key) {
		return getBundle().getString(key.name());
	}

	private ResourceBundle getBundle() {
		if (bundle == null) {
			bundle = ResourceBundle.getBundle("dialogs");			
		}
		return bundle;
	}

}
