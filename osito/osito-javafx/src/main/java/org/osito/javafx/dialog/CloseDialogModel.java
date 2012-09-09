package org.osito.javafx.dialog;

import javafx.util.Callback;

public class CloseDialogModel {

	private Callback<?, ?> callback;
	
	public CloseDialogModel(Callback<?, ?> callback) {
		this.callback = callback;
	}
	
	public void handleClose() {
		callback.call(null);
	}
	
}
