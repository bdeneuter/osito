package org.osito.javafx;

import java.util.ResourceBundle;

public class MessageService {

	private static MessageService instance = new MessageService();
	
	private ResourceBundle bundle;

	public static MessageService messageService() {
		return instance;
	}
	
	public String getText(MessageKey key) {
		return getBundle().getString(key.getValue());
	}

	private ResourceBundle getBundle() {
		if (bundle == null) {
			bundle = ResourceBundle.getBundle("dialogs");			
		}
		return bundle;
	}

}
